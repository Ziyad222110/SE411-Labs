package psu.se411.lab14.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;
import psu.se411.lab14.model.Course;
import psu.se411.lab14.service.CourseService;











@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

	private CourseService courseService;

	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	}

	@GetMapping()
	public ResponseEntity<Iterable<Course>> getAllCourses() {
		return ResponseEntity.ok(courseService.getAllCourses());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
		Optional<Course> op = courseService.getCourseById(id);
		if (op.isPresent()) {
			return ResponseEntity.ok(op.get());
}
		return ResponseEntity.notFound().build();
	}
	@PostMapping
    public ResponseEntity<Void> createCourse(@RequestBody @Valid Course course, UriComponentsBuilder ucb) {
        if (course == null) {
            return ResponseEntity.badRequest().build();
        }

        Course savedCourse = courseService.createCourse(course);
        URI uri = ucb.path("/api/v1/courses/{id}").buildAndExpand(savedCourse.id()).toUri();

        return ResponseEntity.created(uri).build();
    }
	
	@PutMapping("/{id}")
    public ResponseEntity<Void> updateCourses(@PathVariable Long id ,@RequestBody Course course) {
        if (course.id() != null) {
            return ResponseEntity.badRequest().build();
        }

     
       try {
    	   		courseService.updateCourse(id,course);
    	   		return ResponseEntity.ok().build();
	   } catch (Exception e) {
		   
		   return ResponseEntity.notFound().build();
	   }
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
		try {
			courseService.deleteCourse(id);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
}
