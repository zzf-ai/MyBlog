package com.zzf.service.impl;

import com.zzf.dao.UserRepository;
import com.zzf.pojo.User;
import com.zzf.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zzf
 * @date 2021-02-14
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, DigestUtils.md5Hex(password));
        return user;
    }
}
