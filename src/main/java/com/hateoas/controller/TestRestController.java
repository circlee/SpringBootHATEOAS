package com.hateoas.controller;

import com.hateoas.resources.Greeting;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/**
 * Created by circlee on 2017. 6. 28..
 */

@RestController
public class TestRestController {

    private static final String TEMPLATE_GREET = "Hello, %s!";
    private static final String TEMPLATE_BYE = "Bye, %s!";
    private static final String TEMPLATE_RUN = "%s do punch!";

    @RequestMapping("/hello")
    public HttpEntity<Greeting> hello(
            @RequestParam(value = "name", required = false, defaultValue = "World") String name) {

        Greeting greeting = new Greeting(String.format(TEMPLATE_GREET, name));
        greeting.add(linkTo(methodOn(this.getClass()).hello(name)).withSelfRel());
        greeting.add(linkTo(methodOn(this.getClass()).bye(name)).withRel("bye"));
        greeting.add(linkTo(methodOn(this.getClass()).punch(name)).withRel("punch"));
        return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
    }

    @RequestMapping("/bye")
    public HttpEntity<Greeting> bye(
            @RequestParam(value = "name", required = false, defaultValue = "World") String name) {
        Greeting greeting = new Greeting(String.format(TEMPLATE_BYE, name));
        greeting.add(linkTo(methodOn(this.getClass()).bye(name)).withSelfRel());
        greeting.add(linkTo(methodOn(this.getClass()).hello(name)).withRel("hello"));
        return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
    }

    @RequestMapping("/punch")
    public HttpEntity<Greeting> punch(
            @RequestParam(value = "name", required = false, defaultValue = "World") String name) {
        Greeting greeting = new Greeting(String.format(TEMPLATE_RUN, name));
        greeting.add(linkTo(methodOn(this.getClass()).punch(name)).withSelfRel());
        greeting.add(linkTo(methodOn(this.getClass()).bye(name)).withRel("run"));
        return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
    }

}
