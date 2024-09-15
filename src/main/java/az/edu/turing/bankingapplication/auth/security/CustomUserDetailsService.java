package az.edu.turing.bankingapplication.auth.security;

import az.edu.turing.bankingapplication.auth.model.enums.Role;
import az.edu.turing.bankingapplication.domain.entity.AccountEntity;
import az.edu.turing.bankingapplication.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountEntity accountEntity = accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Account not found" + username));

        Set<GrantedAuthority> authorities = convertRolesToAuthorities(accountEntity.getRoles());


        return User.builder()
                .username(accountEntity.getUsername())
                .password(accountEntity.getPassword())
                .authorities(authorities)
                .accountExpired(!accountEntity.isAccountNonExpired())
                .accountLocked(!accountEntity.isAccountNonLocked())
                .credentialsExpired(!accountEntity.isCredentialsNonExpired())
                .build();
    }
    private Set<GrantedAuthority> convertRolesToAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toSet());
    }
    
}
