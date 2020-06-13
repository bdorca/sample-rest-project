package com.example.restdemo.controller.animal;

import com.example.restdemo.db.AnimalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("animals")
public class AnimalController {

    private final AnimalRepo animalRepo;

    @Autowired
    public AnimalController(AnimalRepo animalRepo) {
        this.animalRepo = animalRepo;
    }

    @GetMapping("favorite")
    public String getFavorite() {
        return animalRepo.getFavorite();
    }
}
