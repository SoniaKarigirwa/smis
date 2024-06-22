package rw.ac.rca.springsecurity.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.ac.rca.springsecurity.dto.LaptopDTO;
import rw.ac.rca.springsecurity.entity.Laptop;
import rw.ac.rca.springsecurity.entity.Student;
import rw.ac.rca.springsecurity.exceptions.ResourceNotFoundException;
import rw.ac.rca.springsecurity.repository.LaptopRepository;
import rw.ac.rca.springsecurity.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LaptopService {

    @Autowired
    private LaptopRepository laptopRepository;
    @Autowired
    private StudentRepository studentRepository;

    public List<Laptop> getAllLaptops() {
        return laptopRepository.findAll();
    }

    public Optional<Laptop> getLaptopById(int lapId) {
        return laptopRepository.findById(lapId);
    }


    public Laptop createLaptop(LaptopDTO laptopDTO) {
        Optional<Student> studentOptional = studentRepository.findById(laptopDTO.getStudentId());
        System.out.println(studentOptional);
        if (!studentOptional.isPresent()) {
            throw new ResourceNotFoundException("Student not found with id " + laptopDTO.getStudentId());
        }
        Student student = studentOptional.get();

        Laptop laptop = new Laptop(laptopDTO.getBrand(), laptopDTO.getSn(), student);
        return laptopRepository.save(laptop);
    }

    public Laptop updateLaptop(int lapId, Laptop laptopDetails) {
        Optional<Laptop> optionalLaptop = laptopRepository.findById(lapId);

        if (optionalLaptop.isPresent()) {
            Laptop laptop = optionalLaptop.get();
            laptop.setBrand(laptopDetails.getBrand());
            laptop.setSn(laptopDetails.getSn());
            laptop.setStudent(laptopDetails.getStudent());
            return laptopRepository.save(laptop);
        } else {
            throw new ResourceNotFoundException("Laptop not found with id " + lapId);
        }
    }

    public void deleteLaptop(int lapId) {
        laptopRepository.deleteById(lapId);
    }
}