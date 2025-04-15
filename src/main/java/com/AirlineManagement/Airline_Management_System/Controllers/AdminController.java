package com.AirlineManagement.Airline_Management_System.Controllers;

import com.AirlineManagement.Airline_Management_System.Entities.AirCraft;
import com.AirlineManagement.Airline_Management_System.Entities.Notification;
import com.AirlineManagement.Airline_Management_System.Misc.Login_Request;
import com.AirlineManagement.Airline_Management_System.Misc.Update_Request;
import com.AirlineManagement.Airline_Management_System.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.management.RuntimeErrorException;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService service;
    @GetMapping("/login")
    ResponseEntity<?> login(@RequestBody Login_Request request){
        try{
            if(request.password.equals("kmc31y8")){
                System.out.println("Admin logged in");
                return ResponseEntity.ok().build();
            }else{
                throw new RuntimeException("Invalid password!");
            }
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/update")
    void update_info(@RequestBody Update_Request req){}
    @GetMapping("/requests")
    List<Notification> get_noti(){return null;}
    @GetMapping("/get-airCrafts")
    List<AirCraft> get_airCrafts(){return null;}
    @PutMapping("/assign-airCraft")
    void assign_aircraft(@RequestBody AirCraft airCraft){}
    @GetMapping("/generate-reports")
    void generate_reports(){}
    @PutMapping("/confirm-booking")
    void confirm_booking(){}
    @PutMapping("/cancel-booking")
    void cancel_booking(){}
}
