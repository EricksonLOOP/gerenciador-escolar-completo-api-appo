package com.oppo.api.Opportunity.API.Entitys.MateriasEntity;

import com.oppo.api.Opportunity.API.Entitys.AlunosEntity.AlunosEntity;
import com.oppo.api.Opportunity.API.Entitys.ProfessoresEntity.ProfessoresEntity;
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
