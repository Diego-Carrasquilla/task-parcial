package com.academic.controller;

import com.academic.entities.Student;
import com.academic.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// Controlador REST para gestionar las operaciones sobre estudiantes
@RestController
@RequestMapping("/api/students")
public class StudentController {
    
    private final StudentService studentService;
    
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    //POST para crear un nuevo estudiante
    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody Student student) {
        try {
            Student createdStudent = studentService.createStudent(student);
            return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //GET para obtener todos los estudiantes
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }
    //GET para obtener un estudiante por id
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    //GET para obtener un estudiante por codigo
    @GetMapping("/code/{studentCode}")
    public ResponseEntity<Student> getStudentByCode(@PathVariable String studentCode) {
        return studentService.getStudentByCode(studentCode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    //GET para buscar estudiantes por nombre (busqueda parcial)
    @GetMapping("/search")
    public ResponseEntity<List<Student>> searchStudents(@RequestParam String name) {
        List<Student> students = studentService.searchStudentsByName(name);
        return ResponseEntity.ok(students);
    }
    //PUT para actualizar un estudiante completo
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        try {
            return studentService.updateStudent(id, student)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //PATCH para actualizacion parcial de estudiante
    @PatchMapping("/{id}")
    public ResponseEntity<?> partialUpdateStudent(@PathVariable Long id, @RequestBody Student student) {
        try {
            return studentService.partialUpdateStudent(id, student)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //DELETE para eliminar un estudiante por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        boolean deleted = studentService.deleteStudent(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    // GET para verificar existencia de estudiante por id
    @GetMapping("/exists/code/{studentCode}")
    public ResponseEntity<Boolean> existsByCode(@PathVariable String studentCode) {
        boolean exists = studentService.existsStudentByCode(studentCode);
        return ResponseEntity.ok(exists);
    }
    // GET para verificar existencia de estudiante por codigo
    @GetMapping("/count")
    public ResponseEntity<Long> countStudents() {
        long count = studentService.countStudents();
        return ResponseEntity.ok(count);
    }
    //DELETE para eliminar todos los estudiantes
    @DeleteMapping
    public ResponseEntity<Void> deleteAllStudents() {
        studentService.deleteAllStudents();
        return ResponseEntity.noContent().build();
    }
}
