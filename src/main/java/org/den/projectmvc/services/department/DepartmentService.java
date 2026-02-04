package org.den.projectmvc.services.department;
import org.den.projectmvc.entities.organization.Department;
import org.den.projectmvc.services.CrudService;

public interface DepartmentService extends CrudService<Department> {
    void assignUserToDepartment(Long userId, Long departmentId);
    void removeUserFromDepartment(Long userId, Long departmentId);
    Department findDepartmentByName(String name);

}
