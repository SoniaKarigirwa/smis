package rw.ac.rca.springsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.rca.springsecurity.entity.UserData;

import java.util.Optional;
@Repository
public interface UserDataRepository extends JpaRepository<UserData, Integer> {
    Optional<UserData> findByEmail(String email);
    }
