package com.module4test.controller;

import com.module4test.model.City;
import com.module4test.model.Country;
import com.module4test.service.ICountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/country")
@CrossOrigin("*")
public class CountryController {
    @Autowired
    ICountryService countryService;

    @GetMapping
    public ResponseEntity<Iterable<Country>> findAllCountry() {
        List<Country> countries = (List<Country>) countryService.findAll();
        if (countries.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Country> findById(@PathVariable Long id){
        Optional<Country> countryOptional =countryService.findById(id);
        if (!countryOptional.isPresent()){
            return new  ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(countryOptional.get(),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Country> save(@RequestBody Country country){
        return new ResponseEntity<>(countryService.save(country), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable Long id,@RequestBody Country country){
        Optional<Country> countryOptional = countryService.findById(id);
        if (!countryOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        country.setId(countryOptional.get().getId());
        return new ResponseEntity<>(countryService.save(country),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Country> deleteCountry(@PathVariable Long id){
        Optional<Country> country = countryService.findById(id);
        if (!country.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        countryService.remove(id);
        return new ResponseEntity<>(country.get(),HttpStatus.OK);
    }

}
