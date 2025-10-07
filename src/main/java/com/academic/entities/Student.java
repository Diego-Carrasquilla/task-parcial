package com.academic.entities;

import java.util.Objects;
public class Student {
    
    // Atributos privados 
    private Long id;
    private String name;
    private String email;
    private String studentCode;
    
    // Constructor por defecto
    public Student() {
    }
    
    // Constructor con parámetros
    public Student(Long id, String name, String email, String studentCode) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.studentCode = studentCode;
    }
    
    // Getters y Setters 
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getStudentCode() {
        return studentCode;
    }
    
    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }
    
    // Métodos equals, hashCode y toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", studentCode='" + studentCode + '\'' +
                '}';
    }
}
