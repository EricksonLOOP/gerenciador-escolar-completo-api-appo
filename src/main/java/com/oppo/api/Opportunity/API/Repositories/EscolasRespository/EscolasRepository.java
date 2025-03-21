package com.oppo.api.Opportunity.API.Repositories.EscolasRespository;

import com.oppo.api.Opportunity.API.Entitys.EscolasEntity.EscolasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EscolasRepository extends JpaRepository<EscolasEntity, UUID> {
    Optional<EscolasEntity> findByInformacoesEscola_Cnpj(String cnpj);
}
