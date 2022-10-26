package com.rserenity.hrproject.ui.rest;

import com.rserenity.hrproject.business.dto.OffDayDto;
import com.rserenity.hrproject.business.dto.UserDto;
import com.rserenity.hrproject.business.services.OffDayServices;
import com.rserenity.hrproject.business.services.UserServices;
import com.rserenity.hrproject.data.entity.OffDayEntity;
import com.rserenity.hrproject.data.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/offdays")
@CrossOrigin(origins = "http://localhost:8080")
public class OffDayController {

    @Autowired
    OffDayServices offDayServices;

    @Autowired
    UserServices userServices;
    @GetMapping("/getAll")
    public List<OffDayDto> getAllUsers() {
        return offDayServices.getOffDays();
    }

    @PostMapping("/createoffday/user/{id}")
    public OffDayDto createOffDay(@PathVariable(value = "id") Long userId) throws Throwable {
        return offDayServices.createOffDay(userId);
    }


    @GetMapping("/getoffday/{id}")
    public ResponseEntity<OffDayDto> getOffDayById(@PathVariable Long id) throws Throwable {
        return offDayServices.getOffDayById(id);
    }

    @GetMapping("/getoffdays/{userId}")
    public List<OffDayDto> getOffDaysByUserId(@PathVariable Long userId) throws Throwable {
        return  offDayServices.getOffDaysByUser(userServices.dtoToEntity(userServices.getUserById(userId).getBody()));
    }
}
