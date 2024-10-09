package com.oppo.api.Opportunity.API.Entitys.EscolasEntity;

import com.oppo.api.Opportunity.API.Entitys.AlunosEntity.AlunosEntity;
import com.oppo.api.Opportunity.API.Entitys.CoodernadorEntity.CoordenadorEntity;
import com.oppo.api.Opportunity.API.Entitys.DiretorEntity.DiretorEntity;
import com.oppo.api.Opportunity.API.Entitys.FuncionarioEntity.FuncionarioEntity;
import com.oppo.api.Opportunity.API.Entitys.ProfessoresEntity.ProfessoresEntity;
import com.oppo.api.Opportunity.API.Models.ContatoModel.ContatoModel;
import com.oppo.api.Opportunity.API.Models.EnderecoModel.EnderecoModel;
import com.oppo.api.Opportunity.API.Models.InformacoesEscola.InformacoesEscola;
import com.oppo.api.Opportunity.API.Models.TagsENUM;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
public class EscolasEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String senha;
    @Embedded
    private EnderecoModel endereco;
    @Embedded
    private ContatoModel contatoEscola;
    @Embedded
    private InformacoesEscola informacoesEscola;
    @Enumerated(EnumType.STRING)
    private TagsENUM role;
    @OneToMany(mappedBy = "escola", cascade = CascadeType.ALL)
    private List<AlunosEntity> alunos;
    @OneToMany(mappedBy = "escola", cascade = CascadeType.ALL)
    private List<ProfessoresEntity> professores;
    @OneToMany(mappedBy = "escola", cascade = CascadeType.ALL)
    private List<CoordenadorEntity> coordenadores;
    @OneToMany(mappedBy = "escola", cascade = CascadeType.ALL)
    private List<DiretorEntity> diretores;
    @OneToMany(mappedBy = "escola", cascade = CascadeType.ALL)
    private List<FuncionarioEntity> funcionarios;
    public EscolasEntity(){

    }

}
