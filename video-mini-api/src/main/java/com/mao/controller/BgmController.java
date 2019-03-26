package com.mao.controller;

import com.mao.service.BgmService;
import com.mao.utils.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "bgm",tags = "背景音乐业务")
@RestController
@RequestMapping("/bgm")
public class BgmController {
    @Autowired
    private BgmService bgmService;

    @ApiOperation(value = "/list",notes = "查询背景音乐列表")
    @RequestMapping("/list")
    public JsonResult list(){
        return new JsonResult("success",200,bgmService.queryBgmList());
    }
}
