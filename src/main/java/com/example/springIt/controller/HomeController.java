package com.example.springIt.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {


    @RequestMapping("/")
    public String home(Model model, HttpServletRequest request){
//        return "Hello Davey";
        model.addAttribute("message", "Hello World!");
        return "index";

    }

}
