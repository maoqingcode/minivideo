package com.mao.controller;

import com.mao.pojo.Users;
import com.mao.pojo.vo.UsersVO;
import com.mao.service.UserService;
import com.mao.utils.JsonResult;
import com.mao.utils.MD5Utils;
import com.mao.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Api(value="用户注册接口",tags={"注册接口"})
public class RegistUserController {

    public static final String USER_REDIS_SESSION = "user-redis-session";
        @Autowired
        private UserService userService;

        //redis
         @Autowired
        private RedisOperator redis;
        @ApiOperation(value="注册用户",notes="用户注册接口")
        @PostMapping("/regist")
        public JsonResult regist(@RequestBody Users user)throws Exception{
            //1 判断数据合法
            if(user==null || StringUtils.isBlank(user.getUsername())||StringUtils.isBlank(user.getPassword())){
                return new JsonResult("用户名和密码不能为空",500,user);
            }
            // 2 当前用户名是否存在
            boolean isNameExist=userService.isUserNameIsExist(user.getUsername());
            //3注册
            Users newUser=new Users();
            if(isNameExist){
                //名字存在
                return new JsonResult("名字已存在",500,user);
            }else{

                newUser.setUsername(user.getUsername());
                newUser.setNickname(user.getUsername());
                newUser.setFaceImage(null);
                newUser.setFansCounts(0);
                newUser.setFollowCounts(0);
                newUser.setPassword(MD5Utils.getMD5Str(user.getPassword()));
                userService.saveUser(newUser);

            }

            UsersVO vo=setUserRedisSessionToken(newUser);
            return new JsonResult("ok",200,vo);

        }

        @ApiOperation(value="/login",notes = "用户登录")
        @PostMapping("/login")
        public JsonResult login(@RequestBody Users users) throws Exception {
            String username= users.getUsername();
            String password=users.getPassword();
            //判断用户名和密码不能为空
            if(StringUtils.isBlank(username) && StringUtils.isBlank(password)){
                return new JsonResult("用户名或密码不能为空",500,users);
            }
            //2 判断用户是否存在
            Users usersRes=userService.queryIsUserExistForLogin(username,MD5Utils.getMD5Str(users.getPassword()));
            if(usersRes!=null){
                usersRes.setPassword("");
                UsersVO usersVO=setUserRedisSessionToken(usersRes);

                return new JsonResult("ok",200,usersVO);
            }else {
                return new JsonResult("用户名或密码错误",500,null);
            }
        }

        @ApiOperation(value="/logout",notes="用户注销接口")
        @ApiImplicitParam(name="userId",value="用户id",required = true,dataType = "String",paramType = "query")
        @PostMapping("/logout")
        public  JsonResult logOut(String userId) throws Exception{
                redis.del(USER_REDIS_SESSION+":"+userId);
                return new JsonResult("success",200,null);

            }

        //设置redis-session
        public UsersVO setUserRedisSessionToken(Users userModel){
            String uniqueToken= UUID.randomUUID().toString();
            redis.set(USER_REDIS_SESSION+":"+userModel.getId(),uniqueToken,1000*60*30);
            UsersVO usersVO=new UsersVO();
            BeanUtils.copyProperties(userModel,usersVO);
            usersVO.setUserToken(uniqueToken);

            return usersVO;
        }

}
