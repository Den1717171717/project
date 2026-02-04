package org.den.projectmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/mainPage")
public class AdminMainPageController {
    @GetMapping("/actions")
    public String showDepartmentActions(Model model) {

        return "admin/mainPage";
    }
}
