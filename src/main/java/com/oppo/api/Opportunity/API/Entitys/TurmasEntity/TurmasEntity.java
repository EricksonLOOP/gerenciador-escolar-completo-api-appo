package com.oppo.api.Opportunity.API.Entitys.TurmasEntity;

import com.oppo.api.Opportunity.API.Entitys.AlunosEntity.AlunosEntity;
import com.oppo.api.Opportunity.API.Entitys.ProfessoresEntity.ProfessoresEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
public class TurmasEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String turma;
    private String lider;
    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL)
    private List<AlunosEntity> alunos;
    @ManyToMany(mappedBy = "turmas")
    private Set<ProfessoresEntity> professoresEntities;
}
