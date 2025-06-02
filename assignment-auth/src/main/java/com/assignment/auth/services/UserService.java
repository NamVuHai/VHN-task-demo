package com.assignment.auth.services;

import com.assignment.auth.entities.UserEntity;
import com.assignment.auth.repositories.UserRepository;
import com.assignment.core.common.CommonService;
import com.assignment.core.constant.ErrorCode;
import com.assignment.core.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class UserService extends CommonService<UserEntity,String,UserRepository> {

    protected UserService(UserRepository repo) {
        super(repo);
    }

    public UserEntity getUserByUserName(String userName){
        return repo.findByUserName(userName).orElseThrow(() -> new BusinessException("User Not Found"));
    }


    @Override
    public UserEntity delete(UserEntity entity) {
        return null;
    }

    @Override
    public UserEntity deleteById(String s) {
        return null;
    }

    @Override
    protected String notFoundMessage() {
        return "User Not Found";
    }
}
