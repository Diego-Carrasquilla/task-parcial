package com.academic.controller;

import com.academic.entities.Task;
import com.academic.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
// Controlador REST para gestionar las operaciones sobre tareas
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    
    private final TaskService taskService;
    
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    //POST para crear una nueva tarea
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task createdTask = taskService.createTask(task);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }
    //GET para obtener todas las tareas
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }
    //GET para obtener una tarea por id
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    //GET para obtener todas las tareas pendientes
    @GetMapping("/status/pending")
    public ResponseEntity<List<Task>> getPendingTasks() {
        List<Task> tasks = taskService.getPendingTasks();
        return ResponseEntity.ok(tasks);
    }
    //GET para obtener todas las tareas entregadas
    @GetMapping("/status/submitted")
    public ResponseEntity<List<Task>> getSubmittedTasks() {
        List<Task> tasks = taskService.getSubmittedTasks();
        return ResponseEntity.ok(tasks);
    }
    //GET para obtener todas las tareas con fecha de entrega antes de una fecha dada
    @GetMapping("/due-before")
    public ResponseEntity<List<Task>> getTasksDueBefore(@RequestParam String date) {
        LocalDate dueDate = LocalDate.parse(date);
        List<Task> tasks = taskService.getTasksDueBefore(dueDate);
        return ResponseEntity.ok(tasks);
    }
    //PUT para actualizar una tarea completa
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        return taskService.updateTask(id, task)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    //PATCH para actualizacion parcial de tarea
    @PatchMapping("/{id}/submit")
    public ResponseEntity<Task> submitTask(@PathVariable Long id) {
        return taskService.markTaskAsSubmitted(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    //PATCH para marcar una tarea como pendiente
    @PatchMapping("/{id}/pending")
    public ResponseEntity<Task> markTaskAsPending(@PathVariable Long id) {
        return taskService.markTaskAsPending(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    //DELETE para eliminar una tarea por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        boolean deleted = taskService.deleteTask(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    //GET para verificar existencia de tarea por id
    @GetMapping("/count")
    public ResponseEntity<Long> countTasks() {
        long count = taskService.countTasks();
        return ResponseEntity.ok(count);
    }
    //DELETE para eliminar todas las tareas
    @DeleteMapping
    public ResponseEntity<Void> deleteAllTasks() {
        taskService.deleteAllTasks();
        return ResponseEntity.noContent().build();
    }
}
