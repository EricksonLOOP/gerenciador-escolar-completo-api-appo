package com.oppo.api.Opportunity.API.Repositories.ProfessoresRespository;

import com.oppo.api.Opportunity.API.Models.Professores.ProfessoresEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfessorRepository extends JpaRepository<ProfessoresEntity, UUID> {

    Optional<ProfessoresEntity> findByCPF(String cpf);
}
