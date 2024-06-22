package rw.ac.rca.springsecurity.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rw.ac.rca.springsecurity.entity.UserData;
import rw.ac.rca.springsecurity.repository.UserDataRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserDataService implements UserDetailsService {
    @Autowired
    private UserDataRepository repository;

    private final BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserData> userData = repository.findByEmail(username);
        //Convert userData to UserDetails
        return userData.map(UserDataDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }
    public UserData loadCurrentUser(String username) throws UsernameNotFoundException {
        Optional<UserData> userDetail = repository.findByEmail(username);
        return userDetail
                .orElseThrow(() -> new UsernameNotFoundException("User not found " +
                        username));
    }
    public String addUser(UserData userData) {
        userData.setPassword(passwordEncoder.encode(userData.getPassword()));
        repository.save(userData);
        return "User Added Successfully";
    }
    public List<UserData> listAll() {
        return repository.findAll();
    }

}
