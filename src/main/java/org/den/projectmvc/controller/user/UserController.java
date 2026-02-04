package org.den.projectmvc.controller.user;


import org.den.projectmvc.dto.UserRequest;
import org.den.projectmvc.entities.user.User;
import org.den.projectmvc.services.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserRequest>> findAll() {

        return ResponseEntity.ok(
                userService.findAll().stream().map(user -> new UserRequest(user.getId() ,
                         user.getName() ,
                         user.getSurname() ,
                         user.getAddress() ,
                         user.getPhoneNumber() ,
                         user.getEmail() , user.getDeleted())).toList() // for demonstration purposes i added method isDeleted,but you should delete field "isDelete" in UserDTO
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<UserRequest> findById(@PathVariable Long id) {
        return ResponseEntity.ok(toDto(userService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<UserRequest> create(@RequestBody User user) { //idk if i should catch by UserDTO or just a User

        userService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(user));
    }

    @PutMapping("{id}")
    public ResponseEntity<UserRequest> update(@PathVariable Long id, @RequestBody User user) {
        User updated = userService.update(id, user);
        return ResponseEntity.ok(toDto(updated));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }



    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }


    private UserRequest toDto(User u) {
        if (u == null)
            return null;
        UserRequest dto = new UserRequest();
        dto.setId(u.getId());
        dto.setName(u.getName());
        dto.setSurname(u.getSurname());
        dto.setAddress(u.getAddress());
        dto.setPhoneNumber(u.getPhoneNumber());
        dto.setEmail(u.getEmail());
        dto.setIsDeleted(u.getDeleted());
        return dto;
    }



}
