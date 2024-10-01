package com.oppo.api.Opportunity.API.SecurityPaths.Services;

import com.oppo.api.Opportunity.API.Models.AdmnistradorOppo.AdministradorOppoEntity;
import com.oppo.api.Opportunity.API.Models.Alunos.AlunosEntity;
import com.oppo.api.Opportunity.API.Models.Escolas.EscolasEntity;
import com.oppo.api.Opportunity.API.Models.Professores.ProfessoresEntity;
import com.oppo.api.Opportunity.API.Repositories.AdministradorRepository.AdmRepository;
import com.oppo.api.Opportunity.API.Repositories.AlunosRepository.AlunosRepository;
import com.oppo.api.Opportunity.API.Repositories.EscolasRespository.EscolasRepository;
import com.oppo.api.Opportunity.API.Repositories.ProfessoresRespository.ProfessorRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final EscolasRepository escolasRepository;
    private final AlunosRepository alunosRepository;
    private final ProfessorRepository professorRepository;
    private final AdmRepository admRepository;
    public CustomUserDetailsService(EscolasRepository escolasRepository, AlunosRepository alunosRepository, ProfessorRepository professorRepository, AdmRepository admRepository) {
        this.escolasRepository = escolasRepository;
        this.alunosRepository = alunosRepository;
        this.professorRepository = professorRepository;
        this.admRepository = admRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
        return fazerValidacao(user);
    }
    public UserDetails fazerValidacao(String user){
        Optional<EscolasEntity> escola = escolasRepository.findByCNPJ(user);
        Optional<AlunosEntity> aluno = alunosRepository.findByCPF(user);
        Optional<ProfessoresEntity> professor = professorRepository.findByCPF(user);
        Optional<AdministradorOppoEntity> administradorOppo = admRepository.findByCPF(user);
        if (escola.isPresent()){
            UserDetails userDetails =
                    org.springframework.security.core.userdetails.User.builder()
                            .username(escola.get().getNome())
                            .password(escola.get().getSenha())
                            .roles("ESCOLA")
                            .build();
            return userDetails;
        }
        if (aluno.isPresent()){
            UserDetails userDetails =
                    org.springframework.security.core.userdetails.User.builder()
                            .username(aluno.get().getNome())
                            .password(aluno.get().getSenha())
                            .roles("ALUNO")
                            .build();
            return userDetails;
        }
        if (professor.isPresent()){
            UserDetails userDetails =
                    org.springframework.security.core.userdetails.User.builder()
                            .username(professor.get().getNome())
                            .password(professor.get().getSenha())
                            .roles("PROFESSOR")
                            .build();
            return userDetails;
        }
        if (administradorOppo.isPresent()){
            UserDetails userDetails =
                    org.springframework.security.core.userdetails.User.builder()
                            .username(administradorOppo.get().getNome())
                            .password(administradorOppo.get().getSenha())
                            .roles("ADMIN")
                            .build();
            return userDetails;
        }
        return null;
    }
}
