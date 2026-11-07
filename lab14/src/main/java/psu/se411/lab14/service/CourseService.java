package psu.se411.lab14.service;

import java.util.Optional;

import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import psu.se411.lab14.model.Course;

import psu.se411.lab14.repository.CourseRepository;
@Service
public class CourseService {

	private CourseRepository courseRepository;
	
	public CourseService(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}

	public Iterable<Course> getAllCourses() {
		return courseRepository.findAll();
	}
	public Optional<Course> getCourseById(Long id) {
		return courseRepository.findById(id);
	}

	public Course createCourse(Course course) {
		return courseRepository.save(course);
		
	}

	public void updateCourse(Long id, Course course) {
		// TODO Auto-generated method stub
		Course existingCourse = courseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Course not found with id: " + id));
		
		
		
		Course updatedCourse = new Course(id,course.code(), course.name(), course.description(), course.credits());
		
		courseRepository.save(updatedCourse);
	}

	public void deleteCourse(Long id) {
		// TODO Auto-generated method stub
		Course existingCourse = courseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Course not found with id: " + id));
		courseRepository.deleteById(id);
	}
}
