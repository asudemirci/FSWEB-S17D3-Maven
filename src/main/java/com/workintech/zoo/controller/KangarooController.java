package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/kangaroos")
public class KangarooController {
    private Map<Long, Kangaroo> kangaroos;
@PostConstruct
public void init() {
    kangaroos = new HashMap<>();
    kangaroos.put(1L, new Kangaroo(1L, "Rocky", 1.7, 80.5, "Male", true));
}
@GetMapping
    public Collection<Kangaroo> getAll() {
        return kangaroos.values();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Kangaroo> getById(@PathVariable Long id) {
        Kangaroo kangaroo = kangaroos.get(id);
        if (kangaroo == null) {
            throw new ZooException("Kangaroo with id " + id + " not found!", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(kangaroo);
    }
    @PostMapping
    public ResponseEntity<Kangaroo> create(@RequestBody Kangaroo kangaroo) {
        if (kangaroo.getName() == null || kangaroo.getGender() == null || kangaroo.getId() == 0) {
            throw new ZooException("Kangaroo fields are not valid!", HttpStatus.BAD_REQUEST);
        }
        kangaroos.put(kangaroo.getId(), kangaroo);
        return ResponseEntity.ok(kangaroo);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Kangaroo> update(@PathVariable Long id, @RequestBody Kangaroo kangaroo) {
        if (!kangaroos.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        kangaroo.setId(id);
        kangaroos.put(id, kangaroo);
        return ResponseEntity.ok(kangaroo);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Kangaroo> delete(@PathVariable Long id) {
        Kangaroo removed = kangaroos.remove(id);
        if (removed == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(removed);
    }
}
