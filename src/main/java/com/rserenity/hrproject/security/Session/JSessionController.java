package com.rserenity.hrproject.security.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@CrossOrigin(origins = "http://localhost:3000")
public class JSessionController {

    @Autowired
    JSessionServices jSessionServices;

    @DeleteMapping("/deleteAll")
    public void DeleteAll(){
        jSessionServices.deleteAll();
    }

    @GetMapping("/getAll")
    public List<JSessionDto> getAllSessions() {
        return  jSessionServices.getAllSessions();
    }
}
