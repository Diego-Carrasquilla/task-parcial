package com.academic.repository;

import com.academic.entities.Student;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class StudentRepository {
    
    private final List<Student> students = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    //aseguramos la persistencia en memoria
    public Student save(Student student) {
        if (student.getId() == null) {
            student.setId(idGenerator.getAndIncrement());
            students.add(student);
        } else {
            // Actualizar estudiante existente
            int index = findIndexById(student.getId());
            if (index >= 0) {
                students.set(index, student);
            } else {
                students.add(student);
            }
        }
        return student;
    }
    //busqueda de estudiante por id
    public Optional<Student> findById(Long id) {
        return students.stream()
                .filter(student -> student.getId().equals(id))
                .findFirst();
    }
    //busqueda de estudiante por codigo
    public Optional<Student> findByStudentCode(String studentCode) {
        return students.stream()
                .filter(student -> student.getStudentCode().equals(studentCode))
                .findFirst();
    }

    //busqueda de estudiante por nombre (busqueda parcial)
    public List<Student> findByNameContaining(String name) {
        return students.stream()
                .filter(student -> student.getName().toLowerCase()
                        .contains(name.toLowerCase()))
                .toList();
    }
    //obtener todos los estudiantes
    public List<Student> findAll() {
        return new ArrayList<>(students);
    }
    
    //eliminar estudiante por id
    public boolean deleteById(Long id) {
        return students.removeIf(student -> student.getId().equals(id));
    }
    
    //verificar existencia por id
    public boolean existsById(Long id) {
        return students.stream().anyMatch(student -> student.getId().equals(id));
    }
    //verificar existencia por codigo
    public boolean existsByStudentCode(String studentCode) {
        return students.stream()
                .anyMatch(student -> student.getStudentCode().equals(studentCode));
    }
    
    //obtener conteo total de estudiantes
    public long count() {
        return students.size();
    }
    //eliminar todos los estudiantes
    public void deleteAll() {
        students.clear();
    }
    
    // Método auxiliar privado
    private int findIndexById(Long id) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}
