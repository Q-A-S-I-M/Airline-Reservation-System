package com.AirlineManagement.Airline_Management_System.Controllers;

import com.AirlineManagement.Airline_Management_System.DTOs.Login_Request;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@RestController
@RequestMapping("/admin")
public class AdminController {
    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody Login_Request request){
        try{
            if(request.password.equals("kmc31y8")&&request.username.equals("saqim")){
                System.out.println("Admin logged in");
                return ResponseEntity.ok().build();
            }else{
                throw new RuntimeException("Invalid password!");
            }
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
