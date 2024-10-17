package com.oppo.api.Opportunity.API.Entitys.MateriasEntity;

import com.oppo.api.Opportunity.API.Entitys.AlunosEntity.AlunosEntity;
import com.oppo.api.Opportunity.API.Entitys.ProfessoresEntity.ProfessoresEntity;
import com.oppo.api.Opportunity.API.Entitys.TurmasEntity.TurmasEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
public class MateriasEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String materia;
    private String descricao;
    private List<String> nota;
    // Corrigindo a relação ManyToMany com alunos
    @ManyToMany(mappedBy = "materias")
    private Set<AlunosEntity> alunos;
    @ManyToMany(mappedBy = "materias")
    private List<TurmasEntity> turmas;
    @ManyToMany(mappedBy = "materias")
    private Set<ProfessoresEntity> professores;
    public MateriasEntity(){

    }
}
