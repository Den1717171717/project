package org.den.projectmvc.services.user;

import org.den.projectmvc.entities.organization.Department;
import org.den.projectmvc.entities.organization.Organization;
import org.den.projectmvc.entities.user.User;
import org.den.projectmvc.repositories.DepartmentRepository;
import org.den.projectmvc.repositories.OrganizationRepository;
import org.den.projectmvc.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final OrganizationRepository organizationRepository;

    public UserServiceImpl(UserRepository userRepository,
                           DepartmentRepository departmentRepository,
                           OrganizationRepository organizationRepository) {
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.organizationRepository = organizationRepository;
    }



    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        if (id==null|| id <= 0) {
            throw new IllegalArgumentException("User id must be positive");
        }
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User with id " + id + " not found"));
    }


    @Override
    public User create(User entity) {
        if (entity == null) {
            throw new IllegalArgumentException("User must not be null");
        }

        validateBasicFields(entity);
        validateEmailUnique(entity.getEmail(), null);

       //make null in case if id is autoincrement
        entity.setId(null);
        return userRepository.save(entity);
    }

    @Override
    public User update(Long id, User entity) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("User id must be positive");
        }
        else if (entity == null) {
            throw new IllegalArgumentException("User must not be null");
        }
        User existing = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User with id " + id + " not found"));
        validateBasicFields(entity);
        validateEmailUnique(entity.getEmail(), id);

        existing.setName(entity.getName());
        existing.setSurname(entity.getSurname());
        existing.setAddress(entity.getAddress());
        existing.setPhoneNumber(entity.getPhoneNumber());
        existing.setEmail(entity.getEmail());

        return userRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("User id must be positive");
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + id + " not found"));
        user.setDeleted(true);
        userRepository.save(user);
    }



    @Override
    public User findByEmail(String email) {
        if (!StringUtils.hasText(email)) {
            throw new IllegalArgumentException("Email must not be empty");
        }
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User with email " + email + " not found"));
    }

    @Override
    public List<User> findBySurname(String surname) {
        if (!StringUtils.hasText(surname)) {
            throw new IllegalArgumentException("Surname must not be empty");
        }
        return userRepository.findBySurnameIgnoreCase(surname);
    }

    @Override
    public User updateContactInfo(Long userId, String address, String phoneNumber) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User id must be positive");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));

        user.setAddress(address);
        user.setPhoneNumber(phoneNumber);

        return userRepository.save(user);
    }

    @Override
    public User changeEmail(Long userId, String newEmail) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User id must be positive");
        }
        if (!StringUtils.hasText(newEmail)) {
            throw new IllegalArgumentException("Email must not be empty");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));

        validateEmailUnique(newEmail, userId);

        user.setEmail(newEmail);
        return userRepository.save(user);
    }

    @Override
    public User addDepartment(Long userId, Long departmentId) {
        if (userId == null || userId <= 0 || departmentId == null || departmentId <= 0) {
            throw new IllegalArgumentException("Ids must be positive");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Department with id " + departmentId + " not found"));

        user.getDepartments().add(department);
        return userRepository.save(user);
    }

    @Override
    public User removeDepartment(Long userId, Long departmentId) {
        if (userId == null || userId <= 0 || departmentId == null || departmentId <= 0) {
            throw new IllegalArgumentException("Ids must be positive");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Department with id " + departmentId + " not found"));

        user.getDepartments().remove(department);
        return userRepository.save(user);
    }

//    @Override
//    public User addOrganization(Long userId, Long organizationId) {
//        if (userId == null || userId <= 0 || organizationId == null || organizationId <= 0) {
//            throw new IllegalArgumentException("Ids must be positive");
//        }
//
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));
//        Organization organization = organizationRepository.findById(organizationId)
//                .orElseThrow(() -> new IllegalArgumentException("Organization with id " + organizationId + " not found"));
//
//        user.getOrganizations().add(organization);
//        return userRepository.save(user);
//    }

//    @Override
//    public User removeOrganization(Long userId, Long organizationId) {
//        if (userId == null || userId <= 0 || organizationId == null || organizationId <= 0) {
//            throw new IllegalArgumentException("Ids must be positive");
//        }
//
//        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));
//        Organization organization = organizationRepository.findById(organizationId).orElseThrow(() -> new IllegalArgumentException("Organization with id " + organizationId + " not found"));
//
//        user.getOrganizations().remove(organization);
//        return userRepository.save(user);
//    }



    private void validateBasicFields(User user)

    {
        if (!StringUtils.hasText(user.getName())) {
            throw new IllegalArgumentException("User name must not be empty");
        }
        else if (!StringUtils.hasText(user.getSurname())) {
            throw new IllegalArgumentException("User surname must not be empty");
        }
        else if (!StringUtils.hasText(user.getEmail())) {
            throw new IllegalArgumentException("User email must not be empty");
        }

    }


    private void validateEmailUnique(String email, Long currentUserId) {
       userRepository.findByEmail(email).ifPresent(user -> {
           if (user.getId().equals(currentUserId)) {
               return;
           }
           throw new IllegalArgumentException("Email must be unique");
       });
    }
}