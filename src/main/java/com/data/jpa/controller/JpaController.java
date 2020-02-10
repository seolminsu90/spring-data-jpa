package com.data.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.data.jpa.repository.ParentRepository;

@RestController
public class JpaController {

    @Autowired
    private ParentRepository parentRepository;

    @GetMapping("/")
    public void findAll() {
        parentRepository.findAll();
        System.out.println("----------------------------");
        parentRepository.findByAllByEntityGraph();
        System.out.println("----------------------------");
        parentRepository.findByAllByJoinFetch();
    }
}
