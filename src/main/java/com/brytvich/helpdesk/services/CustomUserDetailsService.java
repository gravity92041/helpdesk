package com.brytvich.helpdesk.services;

import com.brytvich.helpdesk.models.User;

import com.brytvich.helpdesk.repositories.UsersRepository;
import com.brytvich.helpdesk.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UsersRepository usersRepository;
    @Autowired
    public CustomUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = usersRepository.findByUsername(username);
        if (user.isEmpty()){
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return new CustomUserDetails(user.get());
    }
    public User findUserByUsername(String username){
        return usersRepository.findByUsername(username).orElse(null);
    }
}
