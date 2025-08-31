package br.com.faculdade.manager_task.controller;

import br.com.faculdade.manager_task.dto.RequestTask;
import br.com.faculdade.manager_task.model.Task;
import br.com.faculdade.manager_task.repository.TaskRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskRepository repo;

    public TaskController(TaskRepository repo) {
        this.repo = repo;
    }

    // Create task
    @PostMapping
    public ResponseEntity<Task> Create(@Valid @RequestBody RequestTask req) {
        Task t = new Task();
        t.setName(req.getName());
        t.setDeliveryDate(req.getDeliveryDate());
        t.setResponsible(req.getResponsible());
        Task salvo = repo.save(t);
        return ResponseEntity.created(URI.create("/api/task/" + salvo.getId())).body(salvo);
    }

    // List task
    @GetMapping
    public List<Task> list() {
        return repo.findAll();
    }

    // Find task By ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> Find(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<Task> Update(@PathVariable Long id, @Valid @RequestBody RequestTask req) {
        return repo.findById(id).map(orig -> {
            orig.setName(req.getName());
            orig.setDeliveryDate(req.getDeliveryDate());
            orig.setResponsible(req.getResponsible());
            return ResponseEntity.ok(repo.save(orig));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> Remove(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}