package com.people.app.repository;

import com.people.app.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends CrudRepository<Course, Integer>, PagingAndSortingRepository<Course, Integer> {

    Page<Course> findAll(Pageable pageable);

    List<Course> findAll();

    Course findCourseByCode(int id);

    <S extends Course> S save(S s);

    void deleteById(Integer integer);
}
