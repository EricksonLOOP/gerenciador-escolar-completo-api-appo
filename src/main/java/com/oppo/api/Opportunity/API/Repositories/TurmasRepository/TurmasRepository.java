package com.oppo.api.Opportunity.API.Repositories.TurmasRepository;

import com.oppo.api.Opportunity.API.Entitys.TurmasEntity.TurmasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TurmasRepository extends JpaRepository<TurmasEntity, UUID> {
    List<TurmasEntity> findByNome(String nome);
    List<TurmasEntity> findByAno(Date ano);

}
