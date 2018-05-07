package com.umssonline.auth.controllers;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@RefreshScope
@RequestMapping("/auth/users")
@RestController
public class UserRestController {

    @GetMapping
    public ResponseEntity getAll() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") final Serializable id) {
        return null;
    }

    @PostMapping
    public ResponseEntity save(@RequestBody final Object user) {
        return null;
    }

    @PatchMapping
    public ResponseEntity edit(@RequestBody final Object user) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") final Serializable id) {
        return null;
    }
}
