package com.umssonline.auth.controller;

import com.umssonline.auth.controller.dto.request.CredentialsDto;
import com.umssonline.auth.controller.dto.request.RegisterUserDto;
import com.umssonline.auth.controller.dto.request.UpdateUserDto;
import com.umssonline.auth.controller.dto.response.UserResponseDto;
import com.umssonline.auth.service.UserService;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 6000)
@RefreshScope
@RequestMapping("/users")
@RestController
public class UserRestController {

    //region Properties
    private UserService userService;
    //endregion

    //region Constructors
    public UserRestController(UserService userService) {
        this.userService = userService;
    }
    //endregion

    @GetMapping
    public ResponseEntity<Collection<UserResponseDto>> getAll() {
        Collection<UserResponseDto> userCollection = userService.loadAll();

        if (userCollection.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.emptyList());
        }

        return ResponseEntity.ok(userCollection);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable("user_id") final Long id) {
        UserResponseDto user = userService.detail(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> save(@Valid @RequestBody final RegisterUserDto userDto) {

        UserResponseDto persistedUser = userService.register(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(persistedUser);
    }

    @PatchMapping("/{user_id}")
    public ResponseEntity<UserResponseDto> edit(@PathVariable("user_id") final Long id, @Valid @RequestBody final UpdateUserDto userDto) {

        UserResponseDto editedUser = userService.edit(id, userDto);
        return ResponseEntity.ok(editedUser);
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<Boolean> delete(@PathVariable("user_id") final Long id) {
        boolean wasUnregistered = userService.unregister(id);
        return ResponseEntity.status(HttpStatus.OK).body(wasUnregistered);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> logIn(@Valid @RequestBody final CredentialsDto credentialsDto) {
        UserResponseDto loggedUser = userService.login(credentialsDto.getAccount(), credentialsDto.getPassword());

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
