# Laboratorio Spring Boot. API REST Estudiantes

## Datos del estudiante
- Julian Solorzano
- Desarrollo de Software
- Correo: jtsolorzano@puce.edu.ec

---

## 1. Proyecto creado correctamente con Spring Boot y Kotlin
En este proyecto armé una API REST desde cero utilizando **Spring Boot** y **Kotlin** para registrar y manejar alumnos. Estructuré todo el backend de forma limpia para que sea escalable y fácil de mantener.

## 2. Configuración correcta de H2
Configuré una base de datos **H2** que corre en memoria para salvar los datos rápido durante las pruebas. En el archivo `application.properties` establecí las credenciales necesarias y cambié el puerto por defecto de la aplicación al **2525**.

## 3. Implementación correcta de la entidad student
Creé la entidad **`Student`** como el molde principal para la base de datos. Usé anotaciones de JPA para mapear los campos esenciales de cada alumno:
* Un identificador (`id`) autoincremental.
* El nombre (`name`).
* El correo electrónico (`email`).
* 
## 4. Implementación correcta del repository
Creé la interfaz **`StudentRepository`** heredando de `JpaRepository`. Gracias a esto, nos olvidamos de escribir código SQL a mano, ya que Spring se encarga por debajo de hacer los inserts y las consultas automáticamente.

## 5. Implementación correcta del service
Armé la capa **`StudentService`** para manejar el cerebro del proyecto y conectar el controlador con los datos. Aquí es donde se procesa la información y se realiza el mapeo manual para transformar las entidades en DTOs y viceversa.

## 6. Implementación correcta del controller
Creé el **`StudentController`** para abrir las puertas de la API al exterior en la ruta `/api/students`. Aquí expuse los métodos HTTP necesarios y configuré las respuestas para que devuelvan los estados estándar como `201 Created` y `200 OK`.

## 7. Uso correcto de DTOs
Para proteger la base de datos real y mover la información limpia, implementé dos Objetos de Transferencia de Datos:
* **`StudentRequest`**: Captura los datos que el usuario manda desde fuera al registrarse.
* **`StudentResponse`**: Estructura la información que le mostramos al usuario de vuelta.

---

### Endpoints de la API (Puerto 2525)

#### Crear Estudiante (POST)
* **URL:** http://localhost:2525/api/students
* **JSON enviado:**
```json
src/main/kotlin/com/example/api_rest_estudiantes
├── controller
│   └── StudentController.kt
├── service
│   └── StudentService.kt
├── repository
│   └── StudentRepository.kt
├── entity
│   └── Student.kt
├── dto
│   ├── StudentRequest.kt
│   └── StudentResponse.kt
└── ApiRestEstudiantesApplication.kt
