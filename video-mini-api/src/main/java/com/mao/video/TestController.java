package com.mao.video;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @RequestMapping("/getName")
    public String say(String name){

        System.out.println(name);
        return "ok:"+name;

    }
}
