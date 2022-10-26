package com.rserenity.hrproject.ui.rest;


import com.rserenity.hrproject.business.dto.UserDto;
import com.rserenity.hrproject.business.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {

    @Autowired
    UserServices userServices;


    @GetMapping("/getAllEmployees")
    public List<UserDto> getAllEmployees(){
        List<UserDto> allUsers =  userServices.getAllUsers();
        System.out.println("getAllUsers");
        return allUsers;
    }
}
