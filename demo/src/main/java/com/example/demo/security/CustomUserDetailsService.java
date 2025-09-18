package com.example.demo.security;

import com.example.demo.model.Administrator;
import com.example.demo.model.Donor;
import com.example.demo.model.Role;
import com.example.demo.model.UserEntity;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserEntityRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userDB = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User " + username + " not found")
        );
        return new User(userDB.getUsername(), userDB.getPassword(), mapToGrantedAuthorities(userDB.getRoles()));
    }

    private Collection<GrantedAuthority> mapToGrantedAuthorities(List<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    // ---------- ADMIN ----------
    public UserEntity adminToUser(Administrator administrator) {
        UserEntity user = new UserEntity();
        user.setUsername(administrator.getUsername());
        user.setPassword(passwordEncoder.encode(administrator.getPassword()));

        Role role = roleRepository.findByName("ADMIN")
                .orElseThrow(() -> new RuntimeException("Role ADMIN not found"));
        user.setRoles(List.of(role));
        return user;
    }

    // ---------- DONOR ----------
    public UserEntity donorToUser(Donor donor) {
        UserEntity user = new UserEntity();
        user.setUsername(donor.getUsername());
        user.setPassword(passwordEncoder.encode(donor.getPassword()));

        Role role = roleRepository.findByName("DONOR")
                .orElseThrow(() -> new RuntimeException("Role DONOR not found"));
        user.setRoles(List.of(role));
        return user;
    }
}