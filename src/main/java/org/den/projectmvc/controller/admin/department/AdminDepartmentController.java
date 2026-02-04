package org.den.projectmvc.controller.admin.department;


import org.den.projectmvc.dto.DepartmentRequest;
import org.den.projectmvc.entities.organization.Department;
import org.den.projectmvc.entities.user.User;
import org.den.projectmvc.services.department.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/department")
public class AdminDepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/actions")
    public String showDepartmentActions(Model model) {
        model.addAttribute("departments", departmentService.findAll());
        return "admin/department/departmentActions";
    }
    @GetMapping("/findAll")
    public String findAllDepartments(Model model) {
        List<Department> departments = departmentService.findAll();
        List<DepartmentRequest> departmentRequests = departments.stream()
                .map(department -> {
                    List<Long> userIds = department.getUsers().stream()
                            .map(User::getId)
                            .collect(Collectors.toList());

                    return new DepartmentRequest(
                            department.getId(),
                            department.getName(),
                            userIds
                    );
                })
                .collect(Collectors.toList());

        model.addAttribute("departments", departmentRequests);
        return "admin/department/departmentList";
    }
    @PostMapping("/update")
    public String updateDepartment(@RequestParam String oldName,
                                   @RequestParam String newName,
                                   RedirectAttributes redirectAttributes) {
        Department department = departmentService.findDepartmentByName(oldName);

        if (department != null) {

            department.setName(newName);
            departmentService.update(department.getId(), department);


            redirectAttributes.addFlashAttribute("message", "Department updated successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Department not found.");
        }

        return "redirect:/admin/department/findAll";
    }


    @GetMapping("/create")
    public String showCreateForm(Model model) {

        model.addAttribute("department", new DepartmentRequest());
        return "admin/department/departmentCreation";
    }


//    @GetMapping("/find/{id}")
//    public String findDepartmentById(@PathVariable Long id, Model model) {
//        Department department = adminDepartmentService.findById(id);
//
//
//        List<Long> userIds = department.getUsers().stream()
//                .map(User::getId)
//                .collect(Collectors.toList());
//        DepartmentRequest departmentRequest = new DepartmentRequest(
//                department.getId(),
//                department.getName(),
//                userIds
//        );
//
//        model.addAttribute("department", departmentRequest);
//        return "department/departmentDetails";
//    }
    @PostMapping("/create")
    public String createDepartment(@ModelAttribute DepartmentRequest departmentRequest, RedirectAttributes redirectAttributes) {
        Department department = new Department();
        department.setName(departmentRequest.getName());


        departmentService.create(department);



        redirectAttributes.addFlashAttribute("message", "Department created and users assigned successfully.");
        return "redirect:/admin/department/actions";
    }
    @PostMapping("/delete")
    public String deleteDepartment(@RequestParam String name , RedirectAttributes redirectAttributes) {
        Department department = departmentService.findDepartmentByName(name);




        departmentService.delete(department.getId());



        redirectAttributes.addFlashAttribute("message", "Department deleted");
        return "redirect:/admin/department/actions";
    }

//    @DeleteMapping("/delete/")
//    public String deleteDepartment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
//        adminDepartmentService.delete(id);
//        redirectAttributes.addFlashAttribute("message", "Department deleted successfully.");
//        return "redirect:/admin/departments";
//    }
//
//
//    @PostMapping("/assignUser")
//    public String assignUserToDepartment(@RequestParam Long userId, @RequestParam Long departmentId, RedirectAttributes redirectAttributes) {
//        adminDepartmentService.assignUserToDepartment(userId, departmentId);
//        redirectAttributes.addFlashAttribute("message", "User assigned to department successfully.");
//        return "redirect:/admin/department/find/" + departmentId;
//    }
//
//
//    @DeleteMapping("/removeUser")
//    public String removeUserFromDepartment(@RequestParam Long userId, @RequestParam Long departmentId, RedirectAttributes redirectAttributes) {
//        adminDepartmentService.removeUserFromDepartment(userId, departmentId);
//        redirectAttributes.addFlashAttribute("message", "User removed from department.");
//        return "redirect:/admin/department/find/" + departmentId;
//    }


}
