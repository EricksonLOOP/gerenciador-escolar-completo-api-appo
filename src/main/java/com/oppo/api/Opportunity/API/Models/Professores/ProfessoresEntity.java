package com.oppo.api.Opportunity.API.Models.Professores;

import com.oppo.api.Opportunity.API.Models.Escolas.EscolasEntity;
import com.oppo.api.Opportunity.API.Models.Materias.MateriasEntity;
import com.oppo.api.Opportunity.API.Models.TagsENUM;
import com.oppo.api.Opportunity.API.Models.Turmas.TurmasEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Entity
@Data
public class ProfessoresEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nome;
    private String senha;
    private String email;
    private Long CPF;
    private Long RG;

    @Enumerated(EnumType.STRING)
    private TagsENUM role;
    private String endereco;

    @ManyToMany
    @JoinTable(
            name = "professores_materias",
            joinColumns = @JoinColumn(name = "professor_id"),
            inverseJoinColumns = @JoinColumn(name = "materia_id")
    )
    private Set<MateriasEntity> materias;

    @ManyToMany
    @JoinTable(
            name = "professores_turmas",
            joinColumns = @JoinColumn(name = "professor_id"),
            inverseJoinColumns = @JoinColumn(name = "turma_id")
    )
    private Set<TurmasEntity> turmas;

    @ManyToOne
    @JoinColumn(name = "escola_id")
    private EscolasEntity escola;
}
