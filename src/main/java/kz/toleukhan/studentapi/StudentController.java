package kz.toleukhan.studentapi;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class StudentController {

    private final StudentRepository repository;
    private final String studentName;

    public StudentController(StudentRepository repository,
                             @Value("${app.student-name}") String studentName) {
        this.repository = repository;
        this.studentName = studentName;
    }

    @GetMapping("/info")
    public Map<String, String> info() {
        return Map.of("application", "Student API", "author", studentName);
    }

    @GetMapping("/students")
    public List<Student> getAll() {
        return repository.findAll();
    }

    @GetMapping("/students/{id}")
    public Student getById(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> notFound(id));
    }

    @PostMapping("/students")
    @ResponseStatus(HttpStatus.CREATED)
    public Student create(@RequestBody Student student) {
        student.setId(null);
        return repository.save(student);
    }

    @PutMapping("/students/{id}")
    public Student update(@PathVariable Long id, @RequestBody Student updated) {
        Student student = repository.findById(id).orElseThrow(() -> notFound(id));
        student.setFullName(updated.getFullName());
        student.setEmail(updated.getEmail());
        student.setGroupName(updated.getGroupName());
        return repository.save(student);
    }

    @DeleteMapping("/students/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw notFound(id);
        }
        repository.deleteById(id);
    }

    private ResponseStatusException notFound(Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found with id " + id);
    }
}
