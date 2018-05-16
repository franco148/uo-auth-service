package com.umssonline.auth.controllers;

import com.umssonline.auth.controllers.dto.Credentials;
import com.umssonline.auth.models.User;
import com.umssonline.auth.services.UserService;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;

@RefreshScope
@RequestMapping("/users")
@RestController
public class UserRestController {

    @Resource
    private UserService userService;


    @GetMapping
    public ResponseEntity getAll() {
        Collection<User> userCollection = userService.loadAll();
        return ResponseEntity.ok(userCollection);
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") final Long id) throws Exception {
        User user = userService.detail(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody final User user) {
        User persistedUser = userService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(persistedUser);
    }

    @PatchMapping
    public ResponseEntity edit(@RequestBody final User user) throws Exception {
        User editedUser = userService.edit(user);
        return ResponseEntity.ok(editedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") final Long id) throws Exception {
        boolean wasUnregistered = userService.unregister(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(wasUnregistered);
    }

    @PostMapping("/login")
    public ResponseEntity logIn(@RequestBody final Credentials credentials) throws Exception {
        User loggedUser = userService.login(credentials.getAccount(), credentials.getPassword());

        return ResponseEntity.ok(loggedUser);
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@RequestBody final Credentials credentials) throws Exception {
        boolean wasLogout = userService.logout(credentials.getAccount(), credentials.getPassword());

        return ResponseEntity.ok(wasLogout);
    }
}
