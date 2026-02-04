package org.den.projectmvc.services.user;

import org.den.projectmvc.entities.user.User;
import org.den.projectmvc.services.CrudService;

import java.util.List;

public interface UserService extends CrudService<User> {


    User findByEmail(String email);

    List<User> findBySurname(String surname);

    User updateContactInfo(Long userId, String address, String phoneNumber);
    User changeEmail(Long userId, String newEmail);
    User addDepartment(Long userId, Long departmentId);
    User removeDepartment(Long userId, Long departmentId);
//    User addOrganization(Long userId, Long organizationId);
//    User removeOrganization(Long userId, Long organizationId);
}