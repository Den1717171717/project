package org.den.projectmvc.services.department;

import org.den.projectmvc.entities.organization.Department;
import org.den.projectmvc.entities.user.User;
import org.den.projectmvc.repositories.DepartmentRepository;
import org.den.projectmvc.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Transactional
    @Override
    public void assignUserToDepartment(Long userId, Long departmentId) {
       User user =  userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));
        Department department = departmentRepository.findById(departmentId).orElseThrow(()-> new IllegalArgumentException("Department with id " + departmentId + " not found"));
        user.getDepartments().add(department);
        userRepository.save(user);
        department.getUsers().add(user);
        departmentRepository.save(department);
    }


    @Transactional
    @Override
    public void removeUserFromDepartment(Long userId, Long departmentId) {
        User user =  userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));
        Department department = departmentRepository.findById(departmentId).orElseThrow(()-> new IllegalArgumentException("Department with id " + departmentId + " not found"));
        user.getDepartments().remove(department);
        userRepository.save(user);
        department.getUsers().remove(user);
        departmentRepository.save(department);
    }

    @Override
    public Department findDepartmentByName(String name) {
        return departmentRepository.findByName(name);
    }

    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Override
    public Department findById(Long id) {
        return departmentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Department with id " + id + " not found"));
    }

    @Override
    public Department create(Department entity) {

        return departmentRepository.save(entity);
    }

    @Override
    public Department update(Long id, Department entity) {


        Department department = departmentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Department with id " + id + " not found"));
        department.setName(entity.getName());


       return departmentRepository.save(department);
    }

    @Override
    public void delete(Long id) {
        departmentRepository.deleteById(id);
    }
}

