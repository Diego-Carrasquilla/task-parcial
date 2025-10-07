package com.academic.repository;

import com.academic.entities.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

// Repositorio para gestionar tareas en memoria
@Repository
public class TaskRepository {
    
    private final List<Task> tasks = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    //guarda una tarea en memoria
    public Task save(Task task) {
        if (task.getId() == null) {
            task.setId(idGenerator.getAndIncrement());
            tasks.add(task);
        } else {
            // Actualizar tarea existente
            int index = findIndexById(task.getId());
            if (index >= 0) {
                tasks.set(index, task);
            } else {
                tasks.add(task);
            }
        }
        return task;
    }
    //busca una tarea por id
    public Optional<Task> findById(Long id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst();
    }
    
    //obtiene todas las tareas
    public List<Task> findAll() {
        return new ArrayList<>(tasks);
    }
    
    //elimina una tarea por id
    public boolean deleteById(Long id) {
        return tasks.removeIf(task -> task.getId().equals(id));
    }
    
    //verifica existencia de tarea por id
    public boolean existsById(Long id) {
        return tasks.stream().anyMatch(task -> task.getId().equals(id));
    }
    //obtiene conteo total de tareas
    public long count() {
        return tasks.size();
    }
    //elimina todas las tareas
    public void deleteAll() {
        tasks.clear();
    }
    
    // Método auxiliar privado
    private int findIndexById(Long id) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}
