package edu.practice.resourceServer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/test")
public class RestTestController {

    @GetMapping(value = "/hello")
    @PreAuthorize("permitAll()")
    public ResponseEntity<String> helloUnsecured(@RequestParam(name = "name") String nameParam) {
        return new ResponseEntity<String>("hello " + nameParam, HttpStatus.OK);
    }
    
    @GetMapping(value = "/securedHello")
    @PreAuthorize("isFullyAuthenticated()")
    public ResponseEntity<String> helloSecured(@RequestParam(name = "name") String nameParam) {
        return new ResponseEntity<String>("secured hello " + nameParam, HttpStatus.OK);
    }

}