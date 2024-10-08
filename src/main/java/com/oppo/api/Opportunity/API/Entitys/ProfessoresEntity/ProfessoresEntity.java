package com.oppo.api.Opportunity.API.Entitys.ProfessoresEntity;

import com.oppo.api.Opportunity.API.Entitys.EscolasEntity.EscolasEntity;
import com.oppo.api.Opportunity.API.Entitys.MateriasEntity.MateriasEntity;
import com.oppo.api.Opportunity.API.Models.ContatoModel.ContatoModel;
import com.oppo.api.Opportunity.API.Models.EnderecoModel.EnderecoModel;
import com.oppo.api.Opportunity.API.Models.InformacoesPessoaisModel.InformacoesPessoaisModel;
import com.oppo.api.Opportunity.API.Models.InformacoesProfissionaisModel.InformacoesProfissionaisModel;
import com.oppo.api.Opportunity.API.Models.TagsENUM;
import com.oppo.api.Opportunity.API.Entitys.TurmasEntity.TurmasEntity;
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
    private String senha;
    @Embedded
    private InformacoesPessoaisModel informacoesPessoais;
    @Embedded
    private InformacoesProfissionaisModel iformacoesProfissionais;
    @Embedded
    private ContatoModel contatoModel;
    @Embedded
    private EnderecoModel endereco;

    @Enumerated(EnumType.STRING)
    private TagsENUM role;

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
