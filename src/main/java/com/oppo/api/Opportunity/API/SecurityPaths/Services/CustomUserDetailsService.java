package com.oppo.api.Opportunity.API.SecurityPaths.Services;

import com.oppo.api.Opportunity.API.Models.Escolas.EscolasEntity;
import com.oppo.api.Opportunity.API.Repositories.EscolasRespository.EscolasRepository;
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

    public CustomUserDetailsService(EscolasRepository escolasRepository) {
        this.escolasRepository = escolasRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String cnpj) throws UsernameNotFoundException {
        Optional<EscolasEntity> escola = escolasRepository.findByCNPJ(cnpj);
        List<String> roles = new ArrayList<>();
        roles.add("ESCOLA");
        UserDetails userDetails =
                org.springframework.security.core.userdetails.User.builder()
                        .username(escola.get().getNome())
                        .password(escola.get().getSenha())
                        .roles(roles.toArray(new String[0]))
                        .build();
        return userDetails;
    }
}
