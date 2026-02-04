package org.den.projectmvc.services.admin;
import org.den.projectmvc.entities.organization.Department;
import org.den.projectmvc.services.CrudService;

public interface AdminDepartmentService extends CrudService<Department> {
    void assignUserToDepartment(Long userId, Long departmentId);
    void removeUserFromDepartment(Long userId, Long departmentId);



}
