package com.sda.studysystem.components;

import com.sda.studysystem.exceptions.AuthorityNotFoundException;
import com.sda.studysystem.exceptions.SchoolNotFoundException;
import com.sda.studysystem.exceptions.UserNotFoundException;
import com.sda.studysystem.models.Authority;
import com.sda.studysystem.models.School;
import com.sda.studysystem.models.User;
import com.sda.studysystem.services.AuthorityService;
import com.sda.studysystem.services.SchoolService;
import com.sda.studysystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.sda.studysystem.utils.Constants.Security.*;

/**
 * Component to initialize data on app startup
 *
 * @author Vinod John
 */
@Component
public class DataInit {
    @Autowired
    private SchoolService schoolService;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void init(){
        initSchoolData();
        initAuthorityData();
        initUserData();
    }

    // PRIVATE METHODS //
    private void initSchoolData() {
        System.out.println("Starting initializing School..");
        School school = new School();
        school.setName("Viljandi University");
        school.setCity("Viljandi");
        school.setPhone("56398563");

        try {
            School searchSchool = schoolService.findSchoolByName(school.getName());
            System.out.println("Cannot pre-initialize school:" + searchSchool.getName());
        } catch (SchoolNotFoundException e) {
            schoolService.createSchool(school);
        }
    }

    private void initAuthorityData() {
        System.out.println("Starting initializing Authority..");
        Authority authorityAdmin = new Authority();
        authorityAdmin.setName(AUTHORITY_ADMIN);
        createAuthority(authorityAdmin);

        Authority authorityTeacher = new Authority();
        authorityTeacher.setName(AUTHORITY_TEACHER);
        createAuthority(authorityTeacher);

        Authority authorityStudent = new Authority();
        authorityStudent.setName(AUTHORITY_STUDENT);
        createAuthority(authorityStudent);
    }

    private void initUserData() {
        System.out.println("Starting initializing User..");

        try {
            Authority authority = authorityService.findAuthorityByName(AUTHORITY_ADMIN);

            User user = new User();
            user.setUserName("admin@study.com");
            user.setPassword("123456");
            user.setAuthority(authority);

            try{
                User resultUser = userService.findUserByUserName(user.getUserName());
                System.out.println("Cannot pre-initialize user:" + resultUser.getUserName());
            } catch (UserNotFoundException e) {
                userService.createUser(user);
            }
        } catch (AuthorityNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
        }

    }

    private void createAuthority(Authority authority) {
        try {
            Authority resultAuthority = authorityService.findAuthorityByName(authority.getName());
            System.out.println("Cannot pre-initialize authority:" + resultAuthority.getName());
        } catch(AuthorityNotFoundException authorityNotFoundException) {
            authorityService.createAuthority(authority);
        }
    }
}
