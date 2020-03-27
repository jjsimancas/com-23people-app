package com.people.app.controller;

import static com.people.app.config.Constants.*;
import com.people.app.dto.ErrorDto;
import com.people.app.model.Course;
import com.people.app.service.api.PersistenceServiceImpl;
import com.people.app.service.api.PersistenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {

    @Value("${record.not.found}")
    private String messageNotfound;

    @Value("${save.rollback}")
    private String messageRollback;

    private final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

    private final PersistenseService persistenceService;

    public CourseController(PersistenceServiceImpl persistenceService) {
        this.persistenceService = persistenceService;
    }

    @GetMapping(value = "/courses", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Page<? extends Object> getAllcoursesPage(){
        LOGGER.info("[Paging Query]");
        return persistenceService.getAllDataByPage(COURSES);
    }

    @GetMapping(value = "/courses/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<? extends Object> ListAllcourses(){
        LOGGER.info("[Listing Courses]");
        return persistenceService.ListAllData(COURSES);
    }

    @GetMapping(value = "/course/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Object> getCoursebyId(@PathVariable("id") int id){
        LOGGER.info("[Query Courses By ID]");
        Object response = persistenceService.getDataById(id, COURSES);
        return response == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorDto(HttpStatus.NOT_FOUND.value(), messageNotfound)): ResponseEntity.ok((Course) response);
    }

    @PostMapping(value = "/courses", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Object> createCourse(@RequestBody Course course)  {
        LOGGER.info("[Creating Courses]");
        int response = 0;
        try {
            response = persistenceService.createRecord(course);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorDto(HttpStatus.BAD_REQUEST.value(), messageRollback));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(value = "/courses/{id}")
    public ResponseEntity<? extends Object> updateCourse(@PathVariable("id") int id, @RequestBody Course course){
        LOGGER.info("[Updating Course]");
        try{
            persistenceService.updateRecord(id, course);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorDto(HttpStatus.NOT_FOUND.value(), messageNotfound));
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping(value = "/courses/{id}")
    public ResponseEntity<? extends Object> deleteCourse(@PathVariable("id") int id){
        LOGGER.info("[Deleting Course]");
        try{
            persistenceService.deleteRecord(id, COURSES);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorDto(HttpStatus.NOT_FOUND.value(), messageNotfound));
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
