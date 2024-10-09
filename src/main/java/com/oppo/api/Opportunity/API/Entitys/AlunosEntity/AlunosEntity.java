package com.oppo.api.Opportunity.API.Entitys.AlunosEntity;

import com.oppo.api.Opportunity.API.Entitys.EscolasEntity.EscolasEntity;
import com.oppo.api.Opportunity.API.Entitys.MateriasEntity.MateriasEntity;
import com.oppo.api.Opportunity.API.Models.ContatoModel.ContatoModel;
import com.oppo.api.Opportunity.API.Models.EnderecoModel.EnderecoModel;
import com.oppo.api.Opportunity.API.Models.InformacoesPessoaisModel.InformacoesPessoaisModel;
import com.oppo.api.Opportunity.API.Models.TagsENUM;
import com.oppo.api.Opportunity.API.Entitys.TurmasEntity.TurmasEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "Alunos")
public class AlunosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String senha;
   @Embedded
   private InformacoesPessoaisModel informacoesPessoais;
   @Embedded
   private ContatoModel contato;
   @Embedded
   private EnderecoModel endereco;

    @Enumerated(EnumType.STRING)
    private TagsENUM role;

    @ManyToMany
    @JoinTable(
            name = "alunos_materias",
            joinColumns = @JoinColumn(name = "aluno_id"),
            inverseJoinColumns = @JoinColumn(name = "materia_id")
    )
    private Set<MateriasEntity> materias;

    @ManyToOne
    @JoinColumn(name = "turma_id") // Consistente com a entidade TurmasEntity
    private TurmasEntity turma;

    @ManyToOne
    @JoinColumn(name = "escola_id")
    private EscolasEntity escola;
}
