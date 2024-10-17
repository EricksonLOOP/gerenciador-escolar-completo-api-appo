package com.oppo.api.Opportunity.API.Repositories.MateriasRepositories;

import com.oppo.api.Opportunity.API.Entitys.MateriasEntity.MateriasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface MateriasRepository extends JpaRepository<MateriasEntity, UUID> {
    Optional<MateriasEntity> findByMateria(String materia);
}
