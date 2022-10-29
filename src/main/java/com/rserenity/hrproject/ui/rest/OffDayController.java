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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/offdays")
@CrossOrigin(origins = "http://localhost:8080")
public class OffDayController {

    @Autowired
    OffDayServices offDayServices;

    @Autowired
    UserServices userServices;
    @GetMapping("/getAll")
    public List<OffDayDto> getAllOffDays() {
        return offDayServices.getOffDays();
    }

    @PostMapping("/createoffday/user/{id}/{date}")
    public OffDayDto createOffDay(@PathVariable(value = "id") Long userId, @PathVariable(value = "date") String dateString ) throws Throwable {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, dtf);
        return offDayServices.createOffDay(userId, date);
    }


    @GetMapping("/getoffday/{id}")
    public ResponseEntity<OffDayDto> getOffDayById(@PathVariable Long id) throws Throwable {
        return offDayServices.getOffDayById(id);
    }

    @DeleteMapping("/deleteoffday/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteOffDayById(@PathVariable Long id) throws Throwable {
        return offDayServices.deleteOffDay(id);
    }

    @GetMapping("/getoffdays/{userId}")
    public List<OffDayDto> getOffDaysByUserId(@PathVariable Long userId) throws Throwable {
        return  offDayServices.getOffDaysByUser(userServices.getUserById(userId).getBody());
    }
}
