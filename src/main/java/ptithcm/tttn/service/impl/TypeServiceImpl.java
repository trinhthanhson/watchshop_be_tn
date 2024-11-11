package ptithcm.tttn.service.impl;

import org.springframework.stereotype.Service;
import ptithcm.tttn.entity.Type;
import ptithcm.tttn.repository.TypeRepo;
import ptithcm.tttn.service.TypeService;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    private final TypeRepo typeRepo;

    public TypeServiceImpl(TypeRepo typeRepo) {
        this.typeRepo = typeRepo;
    }

    @Override
    public List<Type> findAll() {
        return typeRepo.findAll();
    }
}
