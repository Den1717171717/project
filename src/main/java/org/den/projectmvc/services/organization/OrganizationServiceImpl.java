package org.den.projectmvc.services.organization;

import org.den.projectmvc.entities.organization.Organization;
import org.den.projectmvc.repositories.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Transactional
    @Override
    public Organization findOrganizationByName(String name) {
        return organizationRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException("Organization with name " + name + " not found"));
    }

    @Transactional
    @Override
    public List<Organization> findAll() {
        return organizationRepository.findAll();
    }

    @Transactional
    @Override
    public Organization findById(Long id) {
        return organizationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Organization with id " + id + " not found"));
    }

    @Transactional
    @Override
    public Organization create(Organization entity) {
        return organizationRepository.save(entity);
    }

    @Transactional
    @Override
    public Organization update(Long id, Organization entity) {
        Organization organization = organizationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Organization with id " + id + " not found"));
        organization.setName(entity.getName());
        return organizationRepository.save(organization);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        organizationRepository.deleteById(id);
    }
}
