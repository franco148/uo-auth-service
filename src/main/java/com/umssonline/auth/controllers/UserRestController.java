package com.umssonline.auth.controllers;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@RefreshScope
@RequestMapping("/users")
@RestController
public class UserRestController {

    @GetMapping
    public ResponseEntity getAll() {
        return ResponseEntity.ok("all users");
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") final Serializable id) {
        return ResponseEntity.ok(id);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody final Object user) {
        return ResponseEntity.ok(user);
    }

    @PatchMapping
    public ResponseEntity edit(@RequestBody final Object user) {
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") final Serializable id) {
        return null;
    }
}
