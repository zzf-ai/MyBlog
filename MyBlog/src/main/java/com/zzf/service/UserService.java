package com.zzf.service;

import com.zzf.pojo.User;
import org.springframework.stereotype.Service;

/**
 * @author zzf
 * @date 2021-02-14
 */
public interface UserService {
    public User checkUser(String username,String password);
}
