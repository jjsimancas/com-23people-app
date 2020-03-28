package com.people.app.controller;

import static com.people.app.config.Constants.*;

import com.people.app.dto.ErrorDto;
import com.people.app.exceptions.BadRequestException;
import com.people.app.model.Student;
import com.people.app.service.api.PersistenseService;
import com.people.app.util.Utils;
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
public class StudentController {

    @Value("${record.not.found}")
    private String messageNotfound;

    @Value("${save.rollback}")
    private String messageRollback;

    @Value("${invalid.rut}")
    private String messageInvalidRut;

    private final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    private final PersistenseService persistenceService;

    public StudentController(PersistenseService persistenceService) {
        this.persistenceService = persistenceService;
    }


    @GetMapping(value = "/students", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Page<? extends Object> getAllStudentPage(){
        LOGGER.info("[Paging Query]");
            return persistenceService.getAllDataByPage(STUDENTS);
    }

    @GetMapping(value = "/students/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<? extends Object> ListAllStudents(){
        LOGGER.info("[Listing Students]");
        return persistenceService.ListAllData(STUDENTS);
    }

    @GetMapping(value = "/students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Object> getStudentbyId(@PathVariable("id") int id){
        LOGGER.info("[Query Student By ID]");
        Object response = null;
        if(Utils.rutVerifier(id)){
            response = persistenceService.getDataById(id, STUDENTS);
        }else{
            ResponseEntity.status(HttpStatus.valueOf(INVALID_RUT)).body(
                    new ErrorDto(INVALID_RUT, messageInvalidRut));
        }
        return response == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorDto(HttpStatus.NOT_FOUND.value(), messageNotfound)): ResponseEntity.ok(response);
    }
    //return 3 = Invalid Rut, return 2 = younger, return 1 = record created
    @PostMapping(value = "/students", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Object> createCourse(@RequestBody Student student) throws BadRequestException {
        LOGGER.info("[Creating Student]");
        int response = 0;
        try {
            response = persistenceService.createRecord(student);
        }catch (Exception ex){
            throw new BadRequestException(messageRollback);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(value = "/students/{id}")
    public ResponseEntity<? extends Object> updateCourse(@PathVariable("id") int id, @RequestBody Student student) throws BadRequestException {
        LOGGER.info("[Updating Student]");
        try{
            persistenceService.updateRecord(id, student);
        }catch (Exception ex){
            throw new BadRequestException(messageNotfound);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping(value = "/students/{id}")
    public ResponseEntity<? extends Object> deleteCourse(@PathVariable("id") int id) throws BadRequestException {
        LOGGER.info("[Deleting Student]");
        try{
            persistenceService.deleteRecord(id, STUDENTS);
        }catch (Exception ex){
            throw new BadRequestException(messageNotfound);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
