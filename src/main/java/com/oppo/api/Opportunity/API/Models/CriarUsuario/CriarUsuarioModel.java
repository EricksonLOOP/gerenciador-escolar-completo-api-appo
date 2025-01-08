package com.oppo.api.Opportunity.API.Models.CriarUsuario;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class CriarUsuarioModel {
    @Email
    private String cpf;
    private String password;
}
