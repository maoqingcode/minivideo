package com.mao.controller;

import com.mao.pojo.Users;
import com.mao.pojo.vo.UsersVO;
import com.mao.service.UserService;
import com.mao.utils.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.nio.ch.IOUtil;

import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@RestController
@Api(value="UserController",tags ={"用户相关业务接口"} )
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value ="/uploadFace",notes="用户上传头像接口")
    @ApiImplicitParam(name="userId",value="用户id",required = true,dataType = "String",paramType = "query")
    @PostMapping("/uploadFace")
    public JsonResult uploadFace(String userId, @RequestParam("file")MultipartFile[] files)throws Exception{
        if(StringUtils.isBlank(userId)){
            return new JsonResult("用户id不为空",500,null);
        }
    // 1 文件保存的命名空间
        String fileSpace="F:/mini_video_dev";
        String uploadPathDB="/"+userId+"/face";

        FileOutputStream fileOutputStream=null;
        InputStream inputStream=null;
        try{

            if(files!=null && files.length>0){
                String fileName=files[0].getOriginalFilename();
                if (StringUtils.isNotBlank(fileName)) {
                    //文件上传的保存路径
                    String finalFacePath=fileSpace+uploadPathDB+"/"+fileName;
                    //设置数据库保存路径
                    uploadPathDB+=("/"+fileName);

                    File outFile=new File(finalFacePath);
                    if(outFile.getParentFile()!=null || !outFile.getParentFile().isDirectory()){
                        //创建父文件夹
                        outFile.getParentFile().mkdirs();
                    }

                    fileOutputStream=new FileOutputStream(outFile);
                    inputStream=files[0].getInputStream();
                    IOUtils.copy(inputStream,fileOutputStream);
                }
                }else{

                return new JsonResult("上传出错",500,null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return  new JsonResult("上传出错",500,null);

        }finally {
            if(fileOutputStream!=null){
                fileOutputStream.flush();
                fileOutputStream.close();
                inputStream.close();
            }

        }
        Users user=new Users();
        user.setId(userId);
        user.setFaceImage(uploadPathDB);
        userService.updateUserInfo(user);
        return  new JsonResult("上传成功",200,uploadPathDB);

    }

    //查询用户信息
    @ApiOperation(value ="/query",notes="查询用户信息")
    @ApiImplicitParam(name="userId",value="用户id",required = true,dataType = "String",paramType = "query")
    @RequestMapping("/query")
    public JsonResult query(String userId,String fanid)throws Exception{
        if(StringUtils.isBlank(userId)){
            return  new JsonResult("userId 不能为null",500,null);
        }

        Users userInfo=userService.queryUserInfo(userId);
        UsersVO usersVO=new UsersVO();
        BeanUtils.copyProperties(userInfo,usersVO);

        usersVO.setFollow(userService.queryIfFollow(userId,fanid));
        return  new JsonResult("success",200,usersVO);
    }
}
