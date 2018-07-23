package com.umssonline.auth.controllers;

import com.umssonline.auth.models.dto.CredentialsDto;
import com.umssonline.auth.models.dto.RegisterUserDto;
import com.umssonline.auth.models.dto.UpdateUserDto;
import com.umssonline.auth.models.entity.User;
import com.umssonline.auth.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Collection;

@RefreshScope
@RequestMapping("/users")
@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;


    @GetMapping
    public ResponseEntity getAll() {
        Collection<User> userCollection = userService.loadAll();
        return ResponseEntity.ok(userCollection);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity getById(@PathVariable("user_id") final Long id) {
        User user = userService.detail(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity save(@Valid @RequestBody final RegisterUserDto user) {
        User converted = modelMapper.map(user, User.class);

        User persistedUser = userService.register(converted);
        return ResponseEntity.status(HttpStatus.CREATED).body(persistedUser);
    }

    @PatchMapping("/{user_id}")
    public ResponseEntity edit(@PathVariable("user_id") final Long id, @Valid @RequestBody final UpdateUserDto user) throws EntityNotFoundException {
        User converted = modelMapper.map(user, User.class);
        converted.setId(id);

        User editedUser = userService.edit(converted);
        return ResponseEntity.ok(editedUser);
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity delete(@PathVariable("user_id") final Long id) {
        boolean wasUnregistered = userService.unregister(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(wasUnregistered);
    }

    @PostMapping("/login")
    public ResponseEntity logIn(@Valid @RequestBody final CredentialsDto credentialsDto) throws EntityNotFoundException {
        User loggedUser = userService.login(credentialsDto.getAccount(), credentialsDto.getPassword());

        return ResponseEntity.ok(loggedUser);
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@Valid @RequestBody final CredentialsDto credentialsDto) throws EntityNotFoundException {
        boolean wasLogout = userService.logout(credentialsDto.getAccount(), credentialsDto.getPassword());

        return ResponseEntity.ok(wasLogout);
    }

    @PostMapping("/confirm/{user_id}")
    public ResponseEntity confirmSubscription(@PathVariable("user_id") final Long id) {
        userService.confirmSubscription(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
