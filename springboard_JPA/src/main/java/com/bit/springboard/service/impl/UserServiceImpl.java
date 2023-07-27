package com.bit.springboard.service.impl;

import com.bit.springboard.entity.User;
import com.bit.springboard.repository.UserRepository;
import com.bit.springboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User idCheck(String userId) {
        Optional<User> userOptional = userRepository.findByUserId(userId);

        if(userOptional.isEmpty()) {
            return null;
        }

        return userOptional.get();
    }

    @Override
    public void join(User user) {
        userRepository.save(user);
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id).get();
    }

    //update나 delete가 바로 반영되게 하는 어노테이션
    @Override
    public void modify(User modifyUser) {
        userRepository.save(modifyUser);
    }


}
