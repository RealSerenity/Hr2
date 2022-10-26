package com.rserenity.hrproject.ui.rest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management/api/user")
@CrossOrigin(origins = "http://localhost:8080")
public class UserManagementControl {
}
