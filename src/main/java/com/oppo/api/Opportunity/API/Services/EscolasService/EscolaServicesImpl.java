package com.oppo.api.Opportunity.API.Services.EscolasService;

import com.oppo.api.Opportunity.API.DTOs.EscolasDTOs.EscolasDTO;
import com.oppo.api.Opportunity.API.Models.Escolas.EscolasEntity;
import com.oppo.api.Opportunity.API.Repositories.EscolasRespository.EscolasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EscolaServicesImpl implements EscolaServices {

    @Autowired
    private EscolasRepository escolasRepository;

    @Override
    public ResponseEntity<?> create(EscolasDTO escolasDTO) {
        try {
            // Verifica se a escola já existe pelo CNPJ
            EscolasEntity encontrarEscola = escolasRepository.findByCNPJ(escolasDTO.CNPJ());
            if (encontrarEscola != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("CNPJ já registrado. Não é possível criar uma escola com este CNPJ");
            }

            // Cria uma nova entidade de escola a partir do DTO
            EscolasEntity escola = new EscolasEntity();
            escola.setNome(escolasDTO.nome());
            escola.setCNPJ(escolasDTO.CNPJ());
            escola.setSenha(escolasDTO.senha());
            escola.setEndereco(escolasDTO.endereco());
            escola.setDiretor(escolasDTO.diretor());

            // Salva a nova escola no repositório
            EscolasEntity savedEscola = escolasRepository.save(escola);
            return ResponseEntity.status(HttpStatus.CREATED).body("Escola criada com sucesso! ID: " + savedEscola.getId());

        } catch (Exception e) {
            // Aqui você pode fazer um log do erro para depuração
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar a escola: " + e.getMessage());
        }
    }
}
