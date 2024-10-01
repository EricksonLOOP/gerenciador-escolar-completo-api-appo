package com.oppo.api.Opportunity.API.Models.Escolas;

import com.oppo.api.Opportunity.API.Models.Alunos.AlunosEntity;
import com.oppo.api.Opportunity.API.Models.Professores.ProfessoresEntity;
import com.oppo.api.Opportunity.API.Models.TagsENUM;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Data
public class EscolasEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nome;
    private String CNPJ;
    private String senha;
    private String endereco;
    private String diretor;

    @Enumerated(EnumType.STRING)
    private TagsENUM tagsENUM;

    @OneToMany(mappedBy = "escola", cascade = CascadeType.ALL)
    private List<AlunosEntity> alunos;

    @OneToMany(mappedBy = "escola", cascade = CascadeType.ALL)
    private List<ProfessoresEntity> professores;
}
