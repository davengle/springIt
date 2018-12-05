package com.example.springIt.controller;

import com.example.springIt.domain.Link;
import com.example.springIt.repository.LinkRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/links")
public class LinkController {

    private LinkRepository linkRepository;

    public LinkController(LinkRepository linkRepository){
        this.linkRepository = linkRepository;
    }

    @GetMapping("/foo")
    public String foo(Model model){
        model.addAttribute("pageTitle", "This page is a foo!");
        return "foo";
    }

    @GetMapping("/")
    public List<Link> list(){
        return linkRepository.findAll();
    }

    @PostMapping("/create")
    public Link create(@ModelAttribute Link link){
        return linkRepository.save(link);
    }


    @GetMapping("/{id}")
    public Optional read(@PathVariable Long id){
        return linkRepository.findById(id);
    }

    @PostMapping("/{id}")
    public Link update(@PathVariable Long id, @ModelAttribute Link link){
        return linkRepository.save(link);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        linkRepository.deleteById(id);
    }
}
