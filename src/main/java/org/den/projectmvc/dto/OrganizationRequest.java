package org.den.projectmvc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationRequest {

    private Long id;
    private String name;
    private List<Long> userIds = new ArrayList<>();




}
