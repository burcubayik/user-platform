package com.example.demo.service;

import com.example.demo.dto.CreateUserDto;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final KafkaProducerService kafkaProducerService;

    public UserService(UserRepository userRepository, KafkaProducerService kafkaProducerService) {
        this.userRepository = userRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    public String register(CreateUserDto createUserDto){
        User user = new User();
        user.setName(createUserDto.getName());
        user.setSurname(createUserDto.getSurname());
        user.setEmail(createUserDto.getEmail());
        user.setPassword(createUserDto.getPassword());
        user.setVerificationCode(generateVerificationCode());
        user.setActive(false);
        User savedUser= userRepository.save(user);
        if(savedUser!=null){
            kafkaProducerService.sendUserRegistiration(savedUser);
            return "Registiration succesful. Please check your email for verification code";
        }
        return "Registiration failed. Please try again";
    }

    private String generateVerificationCode(){
        return String.valueOf((int)(Math.random() * 9000) + 1000);
    }

}
