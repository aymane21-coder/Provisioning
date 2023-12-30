package org.app.provisioning360api.Security;
import org.app.provisioning360api.Entitie.Compte;
import org.app.provisioning360api.Services.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    CompteService compteService;
    @Override
    public UserDetails loadUserByUsername(String Email) throws UsernameNotFoundException {
       Compte compte= compteService.loadUserByEmail(Email);
        if (compte==null)
            throw new UsernameNotFoundException("user not found");
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(compte.getRole()));
        return new User(compte.getEmail(),compte.getPassword(), authorities);
    }
}
