package com.techx.authservice.security;

import com.techx.dbhandler.repository.userservice.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {

        com.techx.dbhandler.models.userservice.UserDetails userFromDB = userDetailsRepository.findByPhoneNumber(phoneNumber);

        if(Objects.nonNull(userFromDB)){
            List<GrantedAuthority> grantedAuthorities = userFromDB.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole()))
                    .collect(Collectors.toList());

            return new User(userFromDB.getPhoneNumber(), encoder.encode("12345"), grantedAuthorities);
        }

        throw new UsernameNotFoundException("Username: " + phoneNumber + " not found");
    }
}
