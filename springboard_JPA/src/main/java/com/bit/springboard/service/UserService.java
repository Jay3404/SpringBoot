package com.bit.springboard.service;

import com.bit.springboard.entity.User;

public interface UserService {
    User idCheck(String userId);

    void join(User user);

}
