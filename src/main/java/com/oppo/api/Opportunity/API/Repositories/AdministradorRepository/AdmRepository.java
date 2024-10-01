package com.oppo.api.Opportunity.API.Repositories.AdministradorRepository;

import com.oppo.api.Opportunity.API.Models.AdmnistradorOppo.AdministradorOppoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdmRepository extends JpaRepository<AdministradorOppoEntity, UUID> {
    Optional<AdministradorOppoEntity> findByCPF(String user);
}
