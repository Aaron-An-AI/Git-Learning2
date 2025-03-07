package com.example.demo.controller;

import com.example.demo.service.GetHtmlContentService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class SendHttpRequest {
    @Autowired
    private GetHtmlContentService getHtmlContentService;

    @PostMapping("/send")
    public String sendGet(@RequestHeader(value = "Authorization", required = true) String authorization, @RequestParam(value = "url") String url)
    {
        try {
            if (!authorization.equals("Bearer nanfeng")) {
                return "authorization 错误";
            }
            if (Strings.isBlank(url) || Strings.isEmpty(url)) {
                return "url 格式错误";
            }
            return getHtmlContentService.getHtmlContent(url);
        } catch (Exception e) {
            return "系统内部错误";
        }
    }
}

