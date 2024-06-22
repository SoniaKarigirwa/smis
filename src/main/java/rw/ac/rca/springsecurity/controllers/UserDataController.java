package rw.ac.rca.springsecurity.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import rw.ac.rca.springsecurity.entity.AuthRequest;
import rw.ac.rca.springsecurity.entity.UserData;
import rw.ac.rca.springsecurity.services.JwtService;
import rw.ac.rca.springsecurity.services.UserDataService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserDataController {
    @Autowired
    private UserDataService service;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome! This endpoint is not secure";
    }
    @PostMapping("/addNewUser")
    public String addNewUser(@RequestBody UserData userData) {
        return service.addUser(userData);
    }
    @GetMapping("/getAll")
    public List<UserData> getAllUsers(){
            return  service.listAll();
        }
    @GetMapping("/user/userProfile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userProfile() {
        return "Welcome to User Profile";
    }   @GetMapping("/admin/adminProfile")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }
    @GetMapping("/admin/list")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<UserData> listUsers() {
        return service.listAll();
    }
    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }


}
