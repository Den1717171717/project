package org.den.projectmvc.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.den.projectmvc.entities.user.User;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentRequest {

    private Long id;
    private String name;
    private List<Long> userIds;




}

