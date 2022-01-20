package com.example.crm.settings.service.serviceImpl;

import com.example.crm.exception.LoginException;
import com.example.crm.settings.dao.UserDao;
import com.example.crm.settings.domain.User;
import com.example.crm.settings.service.UserService;
import com.example.crm.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginException {
        User user = userDao.login(loginAct,loginPwd);
        if(user==null){
            throw new LoginException("账号或密码错误");
        }
        //到这里说明账号密码正确
        //验证过期时间
        String expireTime = user.getExpireTime();
        if(expireTime.compareTo(DateTimeUtil.getSysTime())<0){
            throw new LoginException("账号已过期");
        }
        String lockState = user.getLockState();
        if("0".equals(lockState)){
            throw new LoginException("账号已被锁定");
        }
        String allowIps = user.getAllowIps();
        if(!allowIps.contains(ip)){
            throw new LoginException("当前ip受限");
        }
        return user;
    }

    @Override
    public List<User> getUserList() {
        List<User> userList=userDao.getUserList();
        return userList;
    }
}
