package ptithcm.tttn.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ptithcm.tttn.entity.Role;
import ptithcm.tttn.repository.RoleRepo;
import ptithcm.tttn.service.RoleService;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepo roleRepo;

    @Override
    public List<Role> findAll() {
        return roleRepo.findAll();
    }
}
