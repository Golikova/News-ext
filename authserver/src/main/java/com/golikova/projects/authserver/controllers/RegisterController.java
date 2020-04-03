package com.golikova.projects.authserver.controllers;

import com.golikova.projects.authserver.dao.OAuthDAOServiceImpl;
import com.golikova.projects.authserver.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.golikova.projects.authserver.service.CustomUserDetailsService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RegisterController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    OAuthDAOServiceImpl oAuthDAOService;

    @RequestMapping(value = "/register" , method = RequestMethod.POST,
            consumes= MediaType.APPLICATION_JSON_VALUE)
    public String addUser(@RequestBody UserEntity user) {

        System.out.println("User: " + user.toString());

        if(oAuthDAOService.getUserDetails(user.getEmailId()) == null) {

            jdbcTemplate.update("insert into USER (`NAME`, `EMAIL_ID`, `PASSWORD`) values " +
                            "(? ,?, ?);",
                    user.getName(),
                    user.getEmailId(),
                    passwordEncoder.encode(user.getPassword()));
        }

        UserEntity newUser = oAuthDAOService.getUserDetails(user.getEmailId());

        jdbcTemplate.update("insert into ASSIGN_USER_TO_ROLE (`USER_ID`, `ROLE_ID`) values " +
                        "(? ,?);",
                newUser.getId(),
                "2");

        user = oAuthDAOService.getUserDetails(user.getEmailId());

        return "User created successfully!";
    }


}
