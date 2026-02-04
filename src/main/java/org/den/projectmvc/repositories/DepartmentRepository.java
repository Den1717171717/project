package org.den.projectmvc.repositories;

import org.den.projectmvc.entities.organization.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findByName(String name);
}


