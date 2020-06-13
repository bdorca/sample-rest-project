package com.example.restdemo.controller.greetings;


public class GreetingCreateRequest {

    private final String content;

    public String getContent() {
        return content;
    }

    public GreetingCreateRequest(String content) {
        this.content = content;
    }
}
