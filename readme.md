### 微信小程序 minivideo

##### 一  使用技术

微信小程序  作前台页面

springboot 后端交互 

swagger-ui 提供后台接口相关文档

mybatis 自动生成mybatis

redis 存储登录用户session，保持会话

mysql 

##### 二 业务开发

1 前端 

1 视频上传
2  bgm上传：
              将视频页面的参数通过转发携带到选择bgm页面，通过函数的params来获取携带参数

2  后端  

- 查询bgm接口 queryBGM

- 上传视频：

​           上传bgm 需要后台管理系统上传，采用分布式zookeeper

​           上传视频时 header 需要指定 content-type= multipart/form-data

​            ffmpeg技术 将bgm和video合并  mergeVideoMp3 videoContriller

- 上传封面：

