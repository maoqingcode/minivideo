package com.mao.controller;

import com.mao.pojo.Videos;
import com.mao.service.BgmService;
import com.mao.service.VideoService;
import com.mao.utils.JsonResult;
import io.swagger.annotations.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.nio.ch.IOUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;

@RestController
@Api(value = "/video",tags={"视频相关业务"})
@RequestMapping("/video")
public class VideoController {
    @Autowired
    private VideoService videoService;
    @Autowired
    private BgmService bgmService;


    @ApiOperation(value="/upload",notes="上传视频接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value="用户id",required = true,dataType = "String",paramType = "form"),
            @ApiImplicitParam(name="bgmId", value="背景音乐id", required=false, dataType="String", paramType="form"),
            @ApiImplicitParam(name="videoSeconds", value="背景音乐播放长度", required=true, dataType="String", paramType="form"),
            @ApiImplicitParam(name="videoWidth", value="视频宽度", required=true, dataType="String", paramType="form"),
            @ApiImplicitParam(name="videoHeight", value="视频高度", required=true, dataType="String", paramType="form"),
            @ApiImplicitParam(name="desc", value="视频描述", required=false, dataType="String", paramType="form")

    })
    @RequestMapping("/upload")
    public JsonResult upload(String userId, String bgmId, double videoSeconds, int videoWidth, int videoHeight, String desc,
                            @ApiParam(value = "短视频",required = true) MultipartFile file) throws Exception{
        if(StringUtils.isBlank(userId)){
            return new JsonResult("userid is null",500,null);
        }
        //文件保存的命名空间
        String fileSpace="F:/mini_video_dev";
        String uploadPathDB="/"+userId+"/video";
        String coverPathDB="/"+userId+"/video";

        FileOutputStream fileOutputStream=null;
        InputStream inputStream=null;
        String finalVideoPath="";

        try {
            if(file !=null){
                String fileName=file.getOriginalFilename();
                String arrayFileNameItem[]=fileName.split("\\.");
                String fileNamePrefix="";
                for (int i=0;i<arrayFileNameItem.length;i++){
                    fileNamePrefix+=arrayFileNameItem[i];
                }

                if(StringUtils.isNotBlank(fileName)){
                    finalVideoPath=fileSpace+uploadPathDB+"/"+fileName;
                    //设置数据库保存路径
                    uploadPathDB+=("/"+fileName);
                    coverPathDB=coverPathDB+"/"+fileNamePrefix+".jpg";

                    File outFile=new File(finalVideoPath);
                    if(outFile.getParentFile()!=null || !outFile.getParentFile().isDirectory()){
                        //创建父文件
                        outFile.getParentFile().mkdirs();
                    }
                    fileOutputStream=new FileOutputStream(outFile);
                    inputStream=file.getInputStream();
                    IOUtils.copy(inputStream,fileOutputStream);

                }else
                {
                    return new JsonResult("上传出错",500,null);
                }
            }

        }catch (Exception e){
            return new JsonResult("上传出错",500,null);
        }finally {
            if(fileOutputStream!=null){
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }

        //判断bgm是否为空，若不为空就查询bgm信息，并合并视频

        //保存视频信息到数据库
        Videos video=new Videos();
        video.setAudioId(bgmId);
        video.setUserId(userId);
        video.setVideoSeconds((float)videoSeconds);
        video.setVideoHeight(videoHeight);
        video.setVideoWidth(videoWidth);
        video.setVideoDesc(desc);
        video.setVideoPath(uploadPathDB);
        video.setCoverPath(coverPathDB);
        video.setStatus(1);
      //  video.setStatus(VideoStatusEnum.SUCCESS.value);
        video.setCreateTime(new Date());

        String videoId=videoService.saveVideo(video);
        return new JsonResult("upload video success",200,null);


    }

}
