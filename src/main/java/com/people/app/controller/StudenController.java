package com.people.app.controller;

import static com.people.app.config.Constants.*;
import com.people.app.service.api.PersistenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudenController {

    private final Logger LOGGER = LoggerFactory.getLogger(StudenController.class);

    private final PersistenseService persistenceService;

    public StudenController(PersistenseService persistenceService) {
        this.persistenceService = persistenceService;
    }


    @GetMapping(value = "/students", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Page<?> getAllStudentPage(){
        LOGGER.info("[Paging Query]");
            return persistenceService.getAllDataByPage(STUDENTS);
    }

    @GetMapping(value = "/students/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<?> ListAllStudents(){
        LOGGER.info("[Listing Students]");
        return persistenceService.ListAllData(STUDENTS);
    }

    /*@GetMapping(value = "/course/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCoursebyId(@PathVariable("id") int id){
        LOGGER.info("[Query Courses By ID]");
        Course response = courseService.getCourseById(id);
        return response == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorDto(HttpStatus.NOT_FOUND.value(), messageNotfound)): ResponseEntity.ok(response);
    }

    @PostMapping(value = "/courses", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCourse(@RequestBody Course course)  {
        LOGGER.info("[Creating Courses]");
        int response = 0;
        try {
            response = courseService.createCourse(course);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorDto(HttpStatus.BAD_REQUEST.value(), messageRollback));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(value = "/courses/{id}")
    public void updateCourse(@PathVariable("id") int id, @RequestBody Course course){
        LOGGER.info("[Updating Course]");
        courseService.updateCourse(id, course);
    }

    @DeleteMapping(value = "/courses/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable("id") int id){
        LOGGER.info("[Deleting Course]");
        try{
            courseService.deleteCourse(id);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorDto(HttpStatus.NOT_FOUND.value(), messageNotfound));
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }*/
}
