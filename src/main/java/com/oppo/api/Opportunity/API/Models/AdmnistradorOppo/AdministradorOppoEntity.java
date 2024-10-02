package com.oppo.api.Opportunity.API.Models.AdmnistradorOppo;

import com.oppo.api.Opportunity.API.Models.TagsENUM;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class AdministradorOppoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String senha;
    private String nome;
    private String CPF;
    private String rg;
    private TagsENUM role;

}
