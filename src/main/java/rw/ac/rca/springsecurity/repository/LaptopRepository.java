package rw.ac.rca.springsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.rca.springsecurity.entity.Laptop;
@Repository
public interface LaptopRepository extends JpaRepository<Laptop, Integer> {
}
