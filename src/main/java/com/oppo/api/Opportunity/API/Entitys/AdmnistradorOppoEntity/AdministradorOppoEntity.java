package com.oppo.api.Opportunity.API.Entitys.AdmnistradorOppoEntity;

import com.oppo.api.Opportunity.API.Models.InformacoesPessoaisModel.InformacoesPessoaisModel;
import com.oppo.api.Opportunity.API.Models.TagsENUM;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
public class AdministradorOppoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String senha;
    @Embedded
    private InformacoesPessoaisModel informacoesPessoais;
    private TagsENUM role;
    public AdministradorOppoEntity(){

    }

}
