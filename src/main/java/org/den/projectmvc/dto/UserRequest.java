package org.den.projectmvc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private Long id;
    private String name;
    private String surname;
    private String address;
    private String phoneNumber;
    private String email;
    private Boolean isDeleted;
}

