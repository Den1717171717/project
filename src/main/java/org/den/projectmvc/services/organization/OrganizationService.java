package org.den.projectmvc.services.organization;

import org.den.projectmvc.entities.organization.Organization;
import org.den.projectmvc.services.CrudService;

public interface OrganizationService extends CrudService<Organization> {
    Organization findOrganizationByName(String name);
}
