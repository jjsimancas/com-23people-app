package com.people.app.service.api;

import com.people.app.model.Course;
import com.people.app.model.Student;
import com.people.app.repository.CourseRepository;
import com.people.app.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersistenceServiceImpl implements PersistenseService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public PersistenceServiceImpl(CourseRepository courseRepository,
                                  StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }


    /*Method for query data with paginated response*/
    public Page<?> getAllDataByPage(int tag) {
        String tagOrder = tag == 0 ? "code" : "rut";
        Pageable pageRequest = PageRequest.of(0, 5, Sort.by(
                Sort.Order.asc(tagOrder)
        ));
        return tagOrder.equals("code") ? courseRepository.findAll(pageRequest) : studentRepository.findAll(pageRequest);
    }

    /*Method for query data with a list of type object*/
    public List<?> ListAllData(int tag) {
        return tag == 0 ? courseRepository.findAll() : studentRepository.findAll();
    }

    /*Method for query courses with a Object filtering by ID*/
    public Object getDataById(int id, int tag) {
        return tag == 0 ? (Course) courseRepository.findCourseByCode(id) : (Student) studentRepository.findStudentByRut(id);
    }

    /*Method for creation of records with int response*/
    public <T> int createCourse(T req) throws Exception {
        if (req instanceof Course) {
            Course course = courseRepository.save((Course) req);
            Optional<Course> validateCourse = courseRepository.findById(course.getCode());
            if (validateCourse.get().getCode() != 0) {
                return 1;
            }
        }
        if (req instanceof Student) {
            Student student = studentRepository.save((Student) req);
            Optional<Student> validateStudent = studentRepository.findById(student.getRut());
            if (validateStudent.get().getRut() != 0) {
                return 1;
            }
        }
        return 0;
    }

    /*Method for update data by type and filtering by ID*/
    public <T> void updateRecord(int id, T req) {
        if (req instanceof Course) {
            Course course = courseRepository.findById(id).get();
            course.setName(((Course) req).getName());
            courseRepository.save(course);
        }
        if (req instanceof Student) {
            Student student = studentRepository.findStudentByRut(id);
            student.setRut(((Student) req).getRut());
            student.setName(((Student) req).getName());
            student.setLastName(((Student) req).getLastName());
            student.setAge(((Student) req).getAge());
        }

    }

    /*Method for delete record by type and filtering by ID*/
    public void deleteRecord(int id, int tag) throws Exception {
        if (tag == 0) {
            courseRepository.deleteById(id);
        } else {
            studentRepository.deleteById(id);
        }
    }
}
