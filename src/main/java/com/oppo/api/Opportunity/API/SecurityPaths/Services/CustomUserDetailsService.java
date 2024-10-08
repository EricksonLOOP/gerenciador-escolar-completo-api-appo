package com.oppo.api.Opportunity.API.SecurityPaths.Services;

import com.oppo.api.Opportunity.API.Entitys.AdmnistradorOppoEntity.AdministradorOppoEntity;
import com.oppo.api.Opportunity.API.Entitys.AlunosEntity.AlunosEntity;
import com.oppo.api.Opportunity.API.Entitys.EscolasEntity.EscolasEntity;
import com.oppo.api.Opportunity.API.Entitys.ProfessoresEntity.ProfessoresEntity;
import com.oppo.api.Opportunity.API.Repositories.AdministradorRepository.AdmRepository;
import com.oppo.api.Opportunity.API.Repositories.AlunosRepository.AlunosRepository;
import com.oppo.api.Opportunity.API.Repositories.EscolasRespository.EscolasRepository;
import com.oppo.api.Opportunity.API.Repositories.ProfessoresRespository.ProfessorRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
        Optional<EscolasEntity> escola = escolasRepository.findByInformacoesEscola_Cnpj(user);
        Optional<AlunosEntity> aluno = alunosRepository.findByInformacoesPessoais_Cpf(user);
        Optional<ProfessoresEntity> professor = professorRepository.findByInformacoesPessoais_Cpf(user);
        Optional<AdministradorOppoEntity> administradorOppo = admRepository.findByInformacoesPessoais_Cpf(user);
        if (escola.isPresent()){
            UserDetails userDetails =
                    org.springframework.security.core.userdetails.User.builder()
                            .username(escola.get().getInformacoesEscola().getNome())
                            .password(escola.get().getSenha())
                            .roles("ESCOLA")
                            .build();
            return userDetails;
        }
        if (aluno.isPresent()){
            UserDetails userDetails =
                    org.springframework.security.core.userdetails.User.builder()
                            .username(aluno.get().getInformacoesPessoais().getNome())
                            .password(aluno.get().getSenha())
                            .roles("ALUNO")
                            .build();
            return userDetails;
        }
        if (professor.isPresent()){
            UserDetails userDetails =
                    org.springframework.security.core.userdetails.User.builder()
                            .username(professor.get().getInformacoesPessoais().getNome())
                            .password(professor.get().getSenha())
                            .roles("PROFESSOR")
                            .build();
            return userDetails;
        }
        if (administradorOppo.isPresent()){
            UserDetails userDetails =
                    org.springframework.security.core.userdetails.User.builder()
                            .username(administradorOppo.get().getInformacoesPessoais().getNome())
                            .password(administradorOppo.get().getSenha())
                            .roles("ADMIN")
                            .build();
            return userDetails;
        }
        return null;
    }
}
