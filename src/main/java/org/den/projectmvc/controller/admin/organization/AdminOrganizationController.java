package org.den.projectmvc.controller.admin.organization;


import org.den.projectmvc.dto.OrganizationRequest;
import org.den.projectmvc.entities.organization.Organization;
import org.den.projectmvc.entities.user.User;
import org.den.projectmvc.services.organization.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/organization")
public class AdminOrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @GetMapping("/actions")
    public String showOrganizationActions(Model model) {
        model.addAttribute("organizations", organizationService.findAll());
        return "admin/organization/actions";
    }

    @GetMapping("/findAll")
    public String findAllOrganizations(Model model) {
        List<Organization> organizations = organizationService.findAll();
        List<OrganizationRequest> organizationRequests = organizations.stream()
                .map(organization -> new OrganizationRequest(
                        organization.getId(),
                        organization.getName() , organization.getUsers().stream().map(User::getId).toList()))
                .collect(Collectors.toList());

        model.addAttribute("organizations", organizationRequests);
        return "admin/organization/organizationList";
    }


    @PostMapping("/update")
    public String updateOrganization(@RequestParam String oldName,
                                     @RequestParam String newName,
                                     RedirectAttributes redirectAttributes) {
        Organization organization = organizationService.findOrganizationByName(oldName);

        if (organization != null) {
            organization.setName(newName);
            organizationService.update(organization.getId(), organization);

            redirectAttributes.addFlashAttribute("message", "Organization updated successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Organization not found.");
        }

        return "redirect:/admin/organization/findAll";
    }


    @PostMapping("/create")
    public String createOrganization(@ModelAttribute OrganizationRequest organizationRequest,
                                     RedirectAttributes redirectAttributes) {
        Organization organization = new Organization();
        organization.setName(organizationRequest.getName());

        organizationService.create(organization);

        redirectAttributes.addFlashAttribute("message", "Organization created successfully.");
        return "redirect:/admin/organization/actions";
    }

    @PostMapping("/delete")
    public String deleteOrganization(@RequestParam String name, RedirectAttributes redirectAttributes) {
        Organization organization = organizationService.findOrganizationByName(name);

        if (organization != null) {
            organizationService.delete(organization.getId());
            redirectAttributes.addFlashAttribute("message", "Organization deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Organization not found.");
        }

        return "redirect:/admin/organization/actions";
    }
}
