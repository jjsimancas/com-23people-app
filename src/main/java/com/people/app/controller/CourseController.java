package com.people.app.controller;

import static com.people.app.config.Constants.*;
import com.people.app.dto.ErrorDto;
import com.people.app.model.Course;
import com.people.app.service.api.PersistenceServiceImpl;
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

    private final PersistenceServiceImpl persistenceServiceImpl;

    public CourseController(PersistenceServiceImpl persistenceServiceImpl) {
        this.persistenceServiceImpl = persistenceServiceImpl;
    }

    @GetMapping(value = "/courses", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public <T> Page getAllcoursesPage(){
        LOGGER.info("[Paging Query]");
        return persistenceServiceImpl.getAllDataByPage(COURSES);
    }

    @GetMapping(value = "/courses/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<?> ListAllcourses(){
        LOGGER.info("[Listing Courses]");
        return persistenceServiceImpl.ListAllData(COURSES);
    }

    @GetMapping(value = "/course/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCoursebyId(@PathVariable("id") int id){
        LOGGER.info("[Query Courses By ID]");
        Object response = persistenceServiceImpl.getDataById(id, COURSES);
        return response == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorDto(HttpStatus.NOT_FOUND.value(), messageNotfound)): ResponseEntity.ok((Course) response);
    }

    @PostMapping(value = "/courses", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCourse(@RequestBody Course course)  {
        LOGGER.info("[Creating Courses]");
        int response = 0;
        try {
            response = persistenceServiceImpl.createCourse(course);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorDto(HttpStatus.BAD_REQUEST.value(), messageRollback));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(value = "/courses/{id}")
    public void updateCourse(@PathVariable("id") int id, @RequestBody Course course){
        LOGGER.info("[Updating Course]");
        persistenceServiceImpl.updateRecord(id, course);
    }

    @DeleteMapping(value = "/courses/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable("id") int id){
        LOGGER.info("[Deleting Course]");
        try{
            persistenceServiceImpl.deleteRecord(id, COURSES);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorDto(HttpStatus.NOT_FOUND.value(), messageNotfound));
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
