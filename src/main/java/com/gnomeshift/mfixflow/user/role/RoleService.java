package com.gnomeshift.mfixflow.user.role;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return new ArrayList<>(roleRepository.findAll());
    }

    public Role getRoleById(long id) {
        return roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Role not found"));
    }

    public Role addRole(Role role) {
        return roleRepository.save(role);
    }

    public Role updateRole(long id, Role role) {
        Role updatedRole = roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Role not found"));
        updatedRole.setName(role.getName());
        return roleRepository.save(updatedRole);
    }

    public void deleteRole(long id) {
        if (!roleRepository.existsById(id)) {
            throw new EntityNotFoundException("Role not found");
        }

        roleRepository.deleteById(id);
    }
}
