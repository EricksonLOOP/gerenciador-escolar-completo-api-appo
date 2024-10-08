package com.oppo.api.Opportunity.API.Entitys.CoodernadorEntity;

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
public class CoordenadorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String senha;
    @Embedded
    private InformacoesPessoaisModel informacoesPessoais;
    @Embedded
    private EnderecoModel endereco;
    @Embedded
    private ContatoModel contato;
    @Embedded
    private InformacoesProfissionaisModel informacoesProfissionais;
    @ManyToOne
    @JoinColumn(name = "escola_id")
    private EscolasEntity escola;


}
