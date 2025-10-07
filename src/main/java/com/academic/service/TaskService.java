package com.academic.service;

import com.academic.entities.Task;
import com.academic.entities.TaskStatus;
import com.academic.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// Servicio para gestionar tareas
@Service
public class TaskService {
    
    private final TaskRepository taskRepository;
    
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    // crea una nueva tarea
    public Task createTask(Task task) {
        if (task.getStatus() == null) {
            task.setStatus(TaskStatus.PENDING);
        }
        return taskRepository.save(task);
    }
    // obtiene una tarea por id
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }
    // obtiene todas las tareas
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    // obtiene todas las tareas pendientes
    public List<Task> getPendingTasks() {
        return taskRepository.findAll().stream()
                .filter(Task::isPending)
                .toList();
    }
    // obtiene todas las tareas entregadas
    public List<Task> getSubmittedTasks() {
        return taskRepository.findAll().stream()
                .filter(Task::isSubmitted)
                .toList();
    }
    // obtiene todas las tareas con fecha de entrega antes de una fecha dada
    public List<Task> getTasksDueBefore(LocalDate date) {
        return taskRepository.findAll().stream()
                .filter(task -> task.getDueDate().isBefore(date))
                .toList();
    }
    // actualiza una tarea completa
    public Optional<Task> updateTask(Long id, Task updatedTask) {
        return taskRepository.findById(id).map(existingTask -> {
            existingTask.setTitle(updatedTask.getTitle());
            existingTask.setDescription(updatedTask.getDescription());
            existingTask.setDueDate(updatedTask.getDueDate());
            if (updatedTask.getStatus() != null) {
                existingTask.setStatus(updatedTask.getStatus());
            }
            return taskRepository.save(existingTask);
        });
    }
    //MARca una tarea como entregada
    public Optional<Task> markTaskAsSubmitted(Long id) {
        return taskRepository.findById(id).map(task -> {
            task.markAsSubmitted();
            return taskRepository.save(task);
        });
    }
    //marca una tarea como pendiente
    public Optional<Task> markTaskAsPending(Long id) {
        return taskRepository.findById(id).map(task -> {
            task.setStatus(TaskStatus.PENDING);
            return taskRepository.save(task);
        });
    }
    //elimina una tarea por id
    public boolean deleteTask(Long id) {
        return taskRepository.deleteById(id);
    }
    //verifica existencia de tarea por id
    public boolean existsTask(Long id) {
        return taskRepository.existsById(id);
    }
    //obtiene conteo total de tareas
    public long countTasks() {
        return taskRepository.count();
    }
    //elimina todas las tareas
    public void deleteAllTasks() {
        taskRepository.deleteAll();
    }
}
