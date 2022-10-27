package com.rserenity.hrproject.ui.controller;

import com.rserenity.hrproject.business.dto.UserDto;
import com.rserenity.hrproject.business.services.OffDayRequestServices;
import com.rserenity.hrproject.business.services.OffDayServices;
import com.rserenity.hrproject.business.services.UserServices;
import com.rserenity.hrproject.security.Session.JSessionDto;
import com.rserenity.hrproject.security.Session.JSessionServices;
import com.rserenity.hrproject.security.auth.ApplicationUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@Controller
@RequestMapping("/")
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
    public String loginPost(HttpServletRequest request, @ModelAttribute UserDto userDto) {
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
        model.addAttribute("offdays", offDayServices.getOffDaysByUser(user));
        model.addAttribute("offdayrequests", offDayRequestServices.getRequestsOfUserId(user.getId()));
//        model.addAttribute("offdayrequests", offDayRequestServices.getRequestsOfUserId(Objects.requireNonNull(user).getId()));

        System.out.println(offDayServices.getOffDaysByUser(user).size());
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
    public String createRequest(HttpServletRequest request, @RequestParam(name = "date") LocalDate date) throws Throwable {
        UserDto user = userServices.getUserById(jSessionServices.findByJSessionId(request.getSession().getId()).getUserId()).getBody();
        if (user != null) {
            offDayRequestServices.createRequest(user.getId(),date);
        }
        return "redirect:/adminPanel";
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
        System.out.println("created session -> " + jSessionDto);
    }

    private boolean checkSessionIsValid(HttpServletRequest request){
        return jSessionServices.findByJSessionId(request.getSession().getId()) != null;
    }
}
