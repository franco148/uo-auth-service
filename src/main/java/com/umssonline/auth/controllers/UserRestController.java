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
import java.util.Collections;

@RefreshScope
@RequestMapping("/users")
@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;


    @GetMapping
    public ResponseEntity<Collection<User>> getAll() {
        Collection<User> userCollection = userService.loadAll();

        if (userCollection.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.emptyList());
        }

        return ResponseEntity.ok(userCollection);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<User> getById(@PathVariable("user_id") final Long id) {
        User user = userService.detail(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> save(@Valid @RequestBody final RegisterUserDto user) {
        User converted = modelMapper.map(user, User.class);

        User persistedUser = userService.register(converted);
        return ResponseEntity.status(HttpStatus.CREATED).body(persistedUser);
    }

    @PatchMapping("/{user_id}")
    public ResponseEntity<User> edit(@PathVariable("user_id") final Long id, @Valid @RequestBody final UpdateUserDto user) throws EntityNotFoundException {
        User converted = modelMapper.map(user, User.class);
        converted.setId(id);

        User editedUser = userService.edit(converted);
        return ResponseEntity.ok(editedUser);
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<Boolean> delete(@PathVariable("user_id") final Long id) {
        boolean wasUnregistered = userService.unregister(id);
        return ResponseEntity.status(HttpStatus.OK).body(wasUnregistered);
    }

    @PostMapping("/login")
    public ResponseEntity<User> logIn(@Valid @RequestBody final CredentialsDto credentialsDto) {
        User loggedUser = userService.login(credentialsDto.getAccount(), credentialsDto.getPassword());

        return ResponseEntity.ok(loggedUser);
    }

    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(@Valid @RequestBody final CredentialsDto credentialsDto) {
        boolean wasLogout = userService.logout(credentialsDto.getAccount(), credentialsDto.getPassword());

        return ResponseEntity.ok(wasLogout);
    }

    @PostMapping("/confirm/{user_id}")
    public ResponseEntity<Void> confirmSubscription(@PathVariable("user_id") final Long id) {
        userService.confirmSubscription(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
