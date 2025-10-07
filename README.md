# Sistema de Gestión de Tareas Académicas

## Aplicación de los Pilares de la Programación Orientada a Objetos (POO)

### 1. Encapsulamiento

**¿Cómo se protege el acceso directo a los atributos de las clases Task y Student?**

Los atributos se declaran como `private`, impidiendo el acceso directo desde fuera de la clase.

**¿Qué mecanismos se usan para acceder y modificar estos datos?**

Se utilizan métodos **getters** y **setters** públicos que actúan como interfaz controlada. Adicionalmente, existen métodos de negocio específicos como `markAsSubmitted()` que encapsulan lógica compleja sin exponer directamente los atributos internos.

---

### 2. Abstracción

**¿Qué detalles se ocultan en las clases de servicio (TaskService, StudentService)?**

Las clases de servicio ocultan:
- Los detalles de almacenamiento (listas en memoria, que podrían ser fácilmente una base de datos)
- La lógica de negocio compleja (validaciones, generación de IDs, reglas de negocio)
- Las operaciones de manipulación de datos del repositorio

**¿Cómo se simplifica el uso de estas funcionalidades desde los controladores?**

Los controladores solo invocan métodos de alto nivel del servicio (como `createTask()` o `markTaskAsSubmitted()`), sin necesidad de conocer cómo se genera el ID, cómo se valida el estado, o cómo se almacena la información

---

### 3. Herencia

**¿Cómo se podría aplicar si se quisiera extender el comportamiento de las entidades o servicios?**

Aunque el proyecto actual no usa herencia explícita, se podría aplicar en estos casos:

- **Tipos especializados de tareas**: Crear una clase base abstracta `Task` y extenderla con `ProgrammingTask`, `EssayTask`, `ExamTask`, cada una con validaciones y comportamientos específicos.
- **Jerarquía de servicios**: Crear un `BaseService` con operaciones comunes (findAll, findById, save) que sea extendido por `TaskService` y `StudentService`, evitando duplicar código.
- **Diferentes tipos de estudiantes**: Por ejemplo, `RegularStudent` y `ScholarshipStudent` con diferentes privilegios o reglas de entrega.

---

### 4. Polimorfismo

**¿Dónde se podría aplicar polimorfismo en este proyecto si se agregaran diferentes tipos de tareas o estudiantes con comportamientos distintos?**

El polimorfismo permitiría tratar diferentes tipos de objetos de manera uniforme:

- **Diferentes tipos de tareas**: Una interfaz o clase abstracta `Task` permitiría procesar cualquier tipo de tarea (programación, ensayo, examen) sin importar su implementación específica.
- **Sistemas de notificación**: Definir una interfaz `Notifier` con implementaciones como `EmailNotifier`, `SMSNotifier`, `PushNotifier`, permitiendo al servicio notificar sin conocer el medio específico.
- **Validadores**: Diferentes implementaciones de un `TaskValidator` podrían aplicarse según el tipo de tarea, procesándose todas de manera uniforme.

El polimorfismo facilita la extensión del sistema sin modificar código existente, siguiendo el principio Open/Closed(pa que luego diga que no le pongo atencion en clase :3).

---

## Cómo Ejecutar

```bash
cd task-management
mvn spring-boot:run
```
---

## Ejemplos de Uso

**Crear una tarea:**
```bash
POST http://localhost:8080/api/tasks
Body: {"title": "Tarea 1", "description": "Descripción", "dueDate": "2025-10-15"}
```

**Marcar tarea como entregada:**
```bash
PATCH http://localhost:8080/api/tasks/1/submit
```

**Crear un estudiante:**
```bash
POST http://localhost:8080/api/students
Body: {"name": "Juan Pérez", "email": "juan@mail.com", "studentCode": "EST001"}
```

**Obtener tareas pendientes:**
```bash
GET http://localhost:8080/api/tasks/status/pending
```
