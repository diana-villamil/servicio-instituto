package com.instituto.serviciorest;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class StudentController {

    private final StudentRepository repository;

    StudentController(StudentRepository repository) {
        this.repository = repository;
    }


    @GetMapping("/students")
    List<Student> all() {
        return repository.findAll();
    }

    @PostMapping("/students")
    Student newEmployee(@RequestBody Student newStudent) {
        return repository.save(newStudent);
    }

    @GetMapping("/students/{id}")
    Student one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró al esudiante " + id));
    }

    @PutMapping("/students/{id}")
    Student updateStudent(@RequestBody Student newStudent, @PathVariable Long id) {

        return repository.findById(id)
                .map(student -> {
                    student.setName(newStudent.getName());
                    student.setLastName(newStudent.getLastName());
                    student.setCourse(newStudent.getCourse());
                    return repository.save(student);
                })
                .orElseGet(() -> {
                    newStudent.setId(id);
                    return repository.save(newStudent);
                });
    }

    @DeleteMapping("/students/{id}")
    void deleteStudent(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
