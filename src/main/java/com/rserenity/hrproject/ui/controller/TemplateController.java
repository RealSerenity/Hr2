package com.rserenity.hrproject.ui.controller;

import com.rserenity.hrproject.business.dto.OffDayRequestDto;
import com.rserenity.hrproject.business.dto.UserDto;
import com.rserenity.hrproject.business.services.OffDayRequestServices;
import com.rserenity.hrproject.business.services.OffDayServices;
import com.rserenity.hrproject.business.services.UserServices;
import com.rserenity.hrproject.security.Session.JSessionDto;
import com.rserenity.hrproject.security.Session.JSessionServices;
import com.rserenity.hrproject.security.auth.ApplicationUserRole;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Controller
@RequestMapping("/")
@Log4j2
public class TemplateController {

    @Autowired
    UserServices userServices;

    @Autowired
    JSessionServices jSessionServices;

    @Autowired
    OffDayServices offDayServices;

    @Autowired
    OffDayRequestServices offDayRequestServices;


    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("userDto", UserDto.builder().build());
        return "login";
    }

    @PostMapping("/loginUser")
    public String loginPost(HttpServletRequest request, @ModelAttribute UserDto userDto, RedirectAttributes redirectAttributes) {
        createSession(request.getSession().getId(), userDto.getUsername());
        UserDto user = userServices.loadUserDtoByUsername(userDto.getUsername());
        //employee
        if(user.getGrantedAuthorities().contains(new SimpleGrantedAuthority("ROLE_"+ApplicationUserRole.EMPLOYEE.name())))
        {
            return "redirect:/userPanel";
        }
        //admin-admintrainee
        else{
            return "redirect:/adminPanel";
        }
    }

    // @CurrentSecurityContext(expression="authentication") Authentication authenticatio
    @GetMapping("/userPanel")
    public String userPanel(Model model, HttpServletRequest request) throws Throwable {
//        if(checkSessionIsValid(request)) return "redirect:/login";

        UserDto user = userServices.getUserById(jSessionServices.findByJSessionId(request.getSession().getId()).getUserId()).getBody();
        model.addAttribute("user", user);
        model.addAttribute("offDayRequest", OffDayRequestDto.builder().build());

        model.addAttribute("offDayRequests", offDayRequestServices.findAllofIdOrderByDateDesc(user.getId()));
        model.addAttribute("offDays", offDayServices.getOffDaysByUser(user));

        model.addAttribute("today", LocalDate.now());
        return "userPanel";
    }

    @GetMapping("/adminPanel")
    public String adminPanel(Model model, HttpServletRequest request) throws Throwable {
        if(checkSessionIsValid(request)) return "redirect:/login";

        UserDto admin = userServices.getUserById(jSessionServices.findByJSessionId(request.getSession().getId()).getUserId()).getBody();
        model.addAttribute("admin", admin);
        model.addAttribute("userServices", userServices);
        model.addAttribute("requests",offDayRequestServices.getWaitingRequests());
        return "adminPanel";
    }

    @PostMapping("/createRequest")
    // createRequest?userId=***&date=***
    public String createRequest(@RequestParam(name = "userId") Long userId, @RequestParam(name = "date") String dateString) throws Throwable {
        LocalDate date = dateStringToLocalDate(dateString);
        offDayRequestServices.createRequest(userId, date);
        return "redirect:/userPanel";
    }


    @PostMapping("/acceptRequest")
    public String acceptRequest(@RequestParam(name = "requestId") Long requestId) throws Throwable {
        System.out.println("accept");
        offDayRequestServices.approveRequest(requestId);
        return "redirect:/adminPanel";
    }

    @PostMapping("/denyRequest")
    public String denyRequest(@RequestParam(name = "requestId") Long requestId) throws Throwable {
        System.out.println("deny");
        offDayRequestServices.denyRequest(requestId);
        return "redirect:/adminPanel";
    }

    private void createSession(String jsessionId, String username) {
        JSessionDto jSessionDto = jSessionServices.createJSession(JSessionDto.builder()
                .jSessionId(jsessionId)
                .userId(userServices.loadUserDtoByUsername(username).getId())
                .build());
        log.info("New session created -> " + jSessionDto);
    }

    private LocalDate dateStringToLocalDate(String dateString) throws ParseException {
//        // !!! the dateString coming from ui is null,2022-11-03
//        System.out.println(dateString);
//        //I couldn't understand what caused this issue
//        System.out.println("dateString -> " + dateString.split(",")[1]);

//      // I tried another approach then figured out the problem
//        String[] tokens = dateString.split(" ");
//        Date monthDate = new SimpleDateFormat("MMMM", Locale.ENGLISH).parse(tokens[1]);
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(monthDate);
//        int monthInt = cal.get(Calendar.MONTH)+1;
//        if (monthInt < 10){
//  // ** the reason of adding 0 beginning of the dateString is, for example, monthInt is 9 then the parser throws ParseException ** //
//            dateString = "0"+monthInt;
//        }else{
//            dateString = String.valueOf(monthInt);
//        }
//        dateString = tokens[5] + "-" + dateString+ "-" + tokens[2];
//        LocalDate date = LocalDate.parse(dateString, dtf);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString.split(",")[1], dtf);
    }

    private boolean checkSessionIsValid(HttpServletRequest request){
        return jSessionServices.findByJSessionId(request.getSession().getId()) != null;
    }
}
