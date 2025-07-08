package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Koala;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/koalas")
public class KoalaController {
    private Map<Long, Koala> koalas;

    @PostConstruct
    public void init() {
        koalas = new HashMap<>();
        koalas.put(1L, new Koala(1L, "Jack", 15.0, 20.0, "Male"));
    }

    @GetMapping
    public Collection<Koala> getAll() {
        return koalas.values();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Koala> getById(@PathVariable Long id) {
        Koala koala = koalas.get(id);
        if (koala == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(koala);
    }
    @PostMapping
    public ResponseEntity<Koala> create(@RequestBody Koala koala) {
        koalas.put(koala.getId(), koala);
        return ResponseEntity.ok(koala);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Koala> update(@PathVariable Long id, @RequestBody Koala koala) {
        if (!koalas.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        koala.setId(id);
        koalas.put(id, koala);
        return ResponseEntity.ok(koala);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Koala> delete(@PathVariable Long id) {
        Koala removed = koalas.remove(id);
        if (removed == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(removed);
    }
}
