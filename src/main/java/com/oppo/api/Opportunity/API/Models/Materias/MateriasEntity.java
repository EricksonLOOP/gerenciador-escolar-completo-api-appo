package com.oppo.api.Opportunity.API.Models.Materias;

import com.oppo.api.Opportunity.API.Models.Alunos.AlunosEntity;
import com.oppo.api.Opportunity.API.Models.Professores.ProfessoresEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Entity
@Data
public class MateriasEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String materia;

    // Corrigindo a relação ManyToMany com alunos
    @ManyToMany(mappedBy = "materias")
    private Set<AlunosEntity> alunos;

    @ManyToMany(mappedBy = "materias")
    private Set<ProfessoresEntity> professores;
}
