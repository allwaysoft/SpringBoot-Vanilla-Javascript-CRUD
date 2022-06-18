package com.example.demo.service;

import com.example.demo.entity.Person;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.ResourceNotFoundException;
import dto.PersonDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import com.example.demo.repository.PersonRepository;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personaRepo;

    @Autowired
    ModelMapper modelMapper;

    public List<PersonDto> getAlls() {
        List<Person> personas = personaRepo.findAll();
        //mapeamos la entidad a dto y lo mostramos en una lista
        return personas.stream().map(persona -> mapperDTO(persona)).collect(Collectors.toList());
    }

    public PersonDto create(PersonDto personaDto) {
        Person persona = mapperEntity(personaDto);
        Person newPersona = personaRepo.save(persona);
        PersonDto personaRes = mapperDTO(newPersona);
        return personaRes;
    }

    public PersonDto update(PersonDto personaDto, long id) {
        Person viejapersona = personaRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Persona", "id", id));
        viejapersona.setName(personaDto.getName());
        viejapersona.setName(personaDto.getName());
        Person nuevaPersona = personaRepo.save(viejapersona);
        return mapperDTO(nuevaPersona);
    }

    public void delete(long id) {
        Person persona = personaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Persona", "id", id));
        personaRepo.delete(persona);
    }

    //convierte la entidad a dto
    public PersonDto mapperDTO(Person persona) {
        PersonDto personaDto = modelMapper.map(persona, PersonDto.class);
        return personaDto;
    }

    //convierte dto a entidad
    public Person mapperEntity(PersonDto personaDTO) {
        Person persona = modelMapper.map(personaDTO, Person.class);
        return persona;
    }

}
