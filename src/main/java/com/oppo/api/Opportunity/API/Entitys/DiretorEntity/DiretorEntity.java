package com.oppo.api.Opportunity.API.Entitys.DiretorEntity;

import com.oppo.api.Opportunity.API.Entitys.EscolasEntity.EscolasEntity;
import com.oppo.api.Opportunity.API.Models.ContatoModel.ContatoModel;
import com.oppo.api.Opportunity.API.Models.EnderecoModel.EnderecoModel;
import com.oppo.api.Opportunity.API.Models.InformacoesPessoaisModel.InformacoesPessoaisModel;
import com.oppo.api.Opportunity.API.Models.InformacoesProfissionaisModel.InformacoesProfissionaisModel;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class DiretorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String senha;
    @Embedded
    private InformacoesPessoaisModel informacoesPessoais;
    @Embedded
    private InformacoesProfissionaisModel informacoesProfissionais;
    @Embedded
    private ContatoModel contatoModel;
    @Embedded
    private EnderecoModel enderecoModel;
    @ManyToOne
    @JoinColumn(name = "escola_id") // Nome da coluna de chave estrangeira
    private EscolasEntity escola;
}
