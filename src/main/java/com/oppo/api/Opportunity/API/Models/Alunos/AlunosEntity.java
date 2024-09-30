package com.oppo.api.Opportunity.API.Models.Alunos;

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
@Table(name = "Alunos")
public class AlunosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nome;
    private String mae;
    private String pai;
    private String endereco;
    private String email;
    private String senha;
    private Long tel;
    private Long CPF;
    private Long rg;
    private String instagram;

    @Enumerated(EnumType.STRING)
    private TagsENUM tagsENUM;

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
