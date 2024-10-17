package com.oppo.api.Opportunity.API.Entitys.TurmasEntity;

import com.oppo.api.Opportunity.API.Entitys.AlunosEntity.AlunosEntity;
import com.oppo.api.Opportunity.API.Entitys.EscolasEntity.EscolasEntity;
import com.oppo.api.Opportunity.API.Entitys.MateriasEntity.MateriasEntity;
import com.oppo.api.Opportunity.API.Entitys.ProfessoresEntity.ProfessoresEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
public class TurmasEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nome;
    private String serie;
    private String codigo;
    private String turno;
    private Date ano;
    private String lider;
    @ManyToOne
    @JoinColumn(name = "escola_id")
    private EscolasEntity escola;
    @ManyToMany
    @JoinTable(
            name = "materias_turma",
            joinColumns = @JoinColumn(name = "turmas_id"),
            inverseJoinColumns = @JoinColumn(name = "materias_id")
    )
    private List<MateriasEntity> materias;
    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL)
    private List<AlunosEntity> alunos;
    @ManyToMany(mappedBy = "turmas")
    private Set<ProfessoresEntity> professoresEntities;

    public TurmasEntity(){

    }
}
