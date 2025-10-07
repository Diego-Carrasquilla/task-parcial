package com.academic.service;

import com.academic.entities.Student;
import com.academic.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//Servicio que maneja la lógica de negocio relacionada con estudiantes
@Service
public class StudentService {
    
    private final StudentRepository studentRepository;
    
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    //crea un nuevo estudiante y verifica unicidad de codigo
    public Student createStudent(Student student) {
        if (studentRepository.existsByStudentCode(student.getStudentCode())) {
            throw new IllegalArgumentException(
                "Ya existe un estudiante con el código: " + student.getStudentCode()
            );
        }
        return studentRepository.save(student);
    }
    //obtiene un estudiante por id
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }
    //obtiene un estudiante por codigo
    public Optional<Student> getStudentByCode(String studentCode) {
        return studentRepository.findByStudentCode(studentCode);
    }
    
    //obtiene todos los estudiantes
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    //busqueda de estudiantes por nombre (busqueda parcial)
    public List<Student> searchStudentsByName(String name) {
        return studentRepository.findByNameContaining(name);
    }
    
    //actualiza un estudiante completo y verifica unicidad de codigo
    public Optional<Student> updateStudent(Long id, Student updatedStudent) {
        return studentRepository.findById(id).map(existingStudent -> {
            // Validar si el código cambia y si ya existe
            if (!existingStudent.getStudentCode().equals(updatedStudent.getStudentCode()) 
                && studentRepository.existsByStudentCode(updatedStudent.getStudentCode())) {
                throw new IllegalArgumentException(
                    "Ya existe un estudiante con el código: " + updatedStudent.getStudentCode()
                );
            }
            
            existingStudent.setName(updatedStudent.getName());
            existingStudent.setEmail(updatedStudent.getEmail());
            existingStudent.setStudentCode(updatedStudent.getStudentCode());
            return studentRepository.save(existingStudent);
        });
    }
    //actualizacion parcial de estudiante y verifica unicidad de codigo
    public Optional<Student> partialUpdateStudent(Long id, Student partialStudent) {
        return studentRepository.findById(id).map(existingStudent -> {
            if (partialStudent.getName() != null) {
                existingStudent.setName(partialStudent.getName());
            }
            if (partialStudent.getEmail() != null) {
                existingStudent.setEmail(partialStudent.getEmail());
            }
            if (partialStudent.getStudentCode() != null 
                && !existingStudent.getStudentCode().equals(partialStudent.getStudentCode())) {
                if (studentRepository.existsByStudentCode(partialStudent.getStudentCode())) {
                    throw new IllegalArgumentException(
                        "Ya existe un estudiante con el código: " + partialStudent.getStudentCode()
                    );
                }
                existingStudent.setStudentCode(partialStudent.getStudentCode());
            }
            return studentRepository.save(existingStudent);
        });
    }
    //elimina un estudiante por id 
    public boolean deleteStudent(Long id) {
        return studentRepository.deleteById(id);
    }
    //verifica existencia de estudiante por id
    public boolean existsStudent(Long id) {
        return studentRepository.existsById(id);
    }
    //verifica existencia de estudiante por codigo
    public boolean existsStudentByCode(String studentCode) {
        return studentRepository.existsByStudentCode(studentCode);
    }
    
    //obtiene conteo total de estudiantes
    public long countStudents() {
        return studentRepository.count();
    }
    //elimina todos los estudiantes
    public void deleteAllStudents() {
        studentRepository.deleteAll();
    }
}
