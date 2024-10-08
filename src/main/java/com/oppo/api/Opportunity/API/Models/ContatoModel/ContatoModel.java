package com.oppo.api.Opportunity.API.Models.ContatoModel;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Embeddable
@Data
@Builder
@AllArgsConstructor
public class ContatoModel {
    private String telefone;
    private String email;

}
