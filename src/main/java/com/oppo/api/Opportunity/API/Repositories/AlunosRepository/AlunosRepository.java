package com.oppo.api.Opportunity.API.Repositories.AlunosRepository;

import com.oppo.api.Opportunity.API.Models.Alunos.AlunosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AlunosRepository extends JpaRepository<AlunosEntity, UUID> {
    Optional<AlunosEntity> findByCPF(String cpf);
}
