package com.planus.db.repository;

import com.planus.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User save(User user);

    User findByUserId(long userId);

    void deleteById(long userId);

    User findOneByKakaoId(long kakaoId);

}
