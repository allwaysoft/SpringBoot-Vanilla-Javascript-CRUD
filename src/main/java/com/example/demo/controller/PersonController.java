package com.example.demo.controller;

import com.example.demo.service.PersonService;
import dto.PersonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//@CrossOrigin(origins = "http://127.0.0.1:5501")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public ResponseEntity<List<PersonDto>> getPersona() {
        return ResponseEntity.ok().body(personService.getAlls());
    }

    @PostMapping
    public ResponseEntity<PersonDto> addPerson(@Valid @RequestBody PersonDto personaDto) {
        return new ResponseEntity<>(personService.create(personaDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDto> updatePerson(@Valid @RequestBody PersonDto personaDto, @PathVariable long id) {
        return new ResponseEntity<>(personService.update(personaDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePerson(@PathVariable long id) {
        personService.delete(id);
        return new ResponseEntity<>("Persona borrada correctamente", HttpStatus.OK);
    }

}
