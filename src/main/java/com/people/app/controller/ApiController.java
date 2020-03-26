package com.people.app.controller;

import com.people.app.dto.ErrorDto;
import com.people.app.model.Course;
import com.people.app.service.api.CourseInfService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiController {

    @Value("${record.not.found}")
    private String message;

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

    @GetMapping(value = "/course/{id}")
    public ResponseEntity<?> getCoursebyId(@PathVariable("id") int id){
        Course response = courseInfService.getCourseById(id);
        return response == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorDto(HttpStatus.NOT_FOUND.value(), message)): ResponseEntity.ok(response);
    }
}
