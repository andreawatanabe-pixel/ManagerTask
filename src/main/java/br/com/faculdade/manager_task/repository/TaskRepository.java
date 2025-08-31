package br.com.faculdade.manager_task.repository;

import br.com.faculdade.manager_task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> { }