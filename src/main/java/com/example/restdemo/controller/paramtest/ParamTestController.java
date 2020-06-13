package com.example.restdemo.controller.paramtest;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/other")
public class ParamTestController {

    public ParamTestController() {
    }

    @GetMapping("/paramtest")
    @ResponseBody
    public List<String> greeting(@RequestParam(name = "opt", required = false) String opt,
                                 @RequestParam(name = "req", required = true) String req,
                                 @RequestParam(name = "def", required = true, defaultValue = "true") Boolean def) {
        List<String> ret = new ArrayList<>();
        ret.add("opt:" + (opt == null ? "" : opt) + ";");
        ret.add("req:" + req + ";");
        ret.add("def:" + def.toString() + ";");
        return ret;
    }
}
