package com.mao.service.impl;

import com.mao.mapper.UsersFansMapper;
import com.mao.mapper.UsersMapper;
import com.mao.pojo.Users;
import com.mao.pojo.UsersFans;
import com.mao.service.UserService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private UsersFansMapper usersFansMapper;
    @Autowired
    private Sid sid;
    @Override
    public boolean isUserNameIsExist(String userName) {
        Users user=new Users();
        user.setUsername(userName);
        Users res=usersMapper.selectOne(user);

        return res==null?false:true;
    }

    @Override
    public boolean saveUser(Users user) {
        String userId=sid.nextShort();
        user.setId(userId);
        usersMapper.insert(user);
        return false;
    }



    @Override
    public Users queryIsUserExistForLogin(String username,String password) {

        Example userEx=new Example(Users.class);
        Example.Criteria criteria=userEx.createCriteria();
        criteria.andEqualTo("username",username);
        criteria.andEqualTo("password",password);
        Users res=usersMapper.selectOneByExample(userEx);

        return res;
    }

    @Override
    public void updateUserInfo(Users user) {
        Example userExample=new Example(Users.class);
        Example.Criteria criteria= userExample.createCriteria();
        criteria.andEqualTo("id",user.getId());
        usersMapper.updateByExampleSelective(user,userExample);
    }

    @Override
    public Users queryUserInfo(String userId) {
        Example userExample=new Example(Users.class);
        Example.Criteria criteria=userExample.createCriteria();
        criteria.andEqualTo("id",userId);
        Users user=usersMapper.selectOneByExample(userExample);
        return user;

    }

    @Override
    public boolean queryIfFollow(String userId, String fanid) {

        Example example=new Example(UsersFans.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("userId",userId);
        criteria.andEqualTo("fanId",fanid);
        List<UsersFans> list=usersFansMapper.selectByExample(example);

        if(list!=null && !list.isEmpty() && list.size()>0){
            return true;
        }
        return false;
    }
}
