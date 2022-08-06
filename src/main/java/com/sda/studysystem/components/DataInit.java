package com.sda.studysystem.components;

import com.sda.studysystem.models.School;
import com.sda.studysystem.services.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Component to initialize data on app startup
 *
 * @author Vinod John
 */
@Component
public class DataInit {
    @Autowired
    private SchoolService schoolService;

    @PostConstruct
    public void init() {
        initSchool();
    }

    // PRIVATE METHODS //
    private void initSchool() {
        System.out.println("Starting initializing School..");
        School school = new School();
        school.setName("Viljandi University");
        school.setCity("Viljandi");
        school.setPhone("56398563");

        try {
            schoolService.createSchool(school);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
