package com.people.app.controller;

import com.people.app.model.Course;
import com.people.app.service.api.CourseInfService;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiController {

    private final CourseInfService courseInfService;

    public ApiController(CourseInfService courseInfService) {
        this.courseInfService = courseInfService;
    }

    @GetMapping(value = "/courses", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Page<Course> getAllcoursesPage(){
        return courseInfService.getAllCourseByPage();
    }

    @GetMapping(value = "/courses/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Course> ListAllcourses(){
        return courseInfService.getAllCourses();
    }
}
