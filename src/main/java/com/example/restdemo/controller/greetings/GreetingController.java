package com.example.restdemo.controller.greetings;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/greetings")
public class GreetingController {

    private final List<Greeting> greetings;

    public GreetingController() {
        greetings = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            greetings.add(new Greeting(i, "Hello " + i));
        }
    }

    @GetMapping("")
    @ResponseBody
    public List<Greeting> greetingList() {
        return greetings;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Greeting greeting(@PathVariable int id) {
        if (id > greetings.size()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "greeting not found"
            );
        }
        return greetings.get(id);
    }

    @PostMapping("")
    @ResponseBody
    public Greeting addGreeting(@RequestBody GreetingCreateRequest request) {
        if (!StringUtils.isEmpty(request.getContent())) {
            Greeting greeting = new Greeting(greetings.size(), request.getContent());
            greetings.add(greeting);
            return greeting;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "content is required");
    }
}
