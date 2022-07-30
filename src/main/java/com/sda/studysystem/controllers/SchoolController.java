package com.sda.studysystem.controllers;

import com.sda.studysystem.services.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller to handle school related request
 *
 * @author Vinod John
 */
@Controller
@RequestMapping("/school")
public class SchoolController {
    @Autowired
    private SchoolService schoolService;
    @GetMapping
    public String showSchoolListPage(Model model) {
        model.addAttribute("schools", schoolService.findAllSchools());
        return "school/list-school";
    }

}
