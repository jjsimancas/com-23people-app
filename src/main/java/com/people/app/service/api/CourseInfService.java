package com.people.app.service.api;

import com.people.app.model.Course;
import com.people.app.repository.CourseRepository;
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
public class CourseInfService {

    private final CourseRepository courseRepository;

    public CourseInfService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Page<Course> getAllCourseByPage() {
        Pageable pageRequest = PageRequest.of(0, 5, Sort.by(
                Sort.Order.asc("code")
        ));
        return courseRepository.findAll(pageRequest);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(int id) {
        return courseRepository.findCourseByCode(id);
    }

    public int createCourse(Course course) throws Exception {
        Course response = courseRepository.save(course);
        Optional<Course> validate = courseRepository.findById(response.getCode());
        if (validate.get().getCode() != 0) {
            return 1;
        }
        return 0;
    }

    public void updateCourse(int id, Course course){
        Course query = courseRepository.findById(id).get();
        query.setName(course.getName());
        courseRepository.save(query);
    }

    public void deleteCourse(int id) throws Exception{
        courseRepository.deleteById(id);
    }
}
