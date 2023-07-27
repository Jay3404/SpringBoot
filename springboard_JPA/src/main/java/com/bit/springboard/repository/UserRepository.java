package com.bit.springboard.repository;

import com.bit.springboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

//update나 delete가 발생했을 때 곧장 커밋 롤백 처리
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);

}
