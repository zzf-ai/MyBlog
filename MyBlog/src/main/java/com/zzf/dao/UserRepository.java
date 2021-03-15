package com.zzf.dao;

import com.zzf.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zzf
 * @date 2021-02-14
 */
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsernameAndPassword(String username,String password);
}
