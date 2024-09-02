package com.soguk.soguk.services;
import com.soguk.soguk.models.User;
import com.soguk.soguk.repositories.userRepo; // MongoDB Repository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService{
    private final userRepo userRepository;

    @Autowired
    public CustomUserDetailsService(userRepo userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String nick) throws UsernameNotFoundException {
        User user = userRepository.findByNick(nick);
        if (user == null) {
            throw new UsernameNotFoundException("Kullanıcı bulunamadı: " + nick);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getNick(),
                user.getPassword(),
                new ArrayList<>()
        );
    }
}
