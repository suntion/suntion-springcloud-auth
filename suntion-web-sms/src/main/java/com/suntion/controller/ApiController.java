package com.suntion.controller;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.suntion.common.lang.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author suntion
 */
@RestController
@RequestMapping("sms/api/")
public class ApiController {
    private final Logger logger = LoggerFactory.getLogger(ApiController.class);

    @HystrixCommand(fallbackMethod = "error")
    @RequestMapping("/{phone}/{content}")
    public ResponseEntity sms(@PathVariable String phone, @PathVariable String content) throws Exception{
        //DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI109kOmJOeMlj", "5u7Ef7uX9emLqkNYjluUr1AbrUbT8e");
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "123123", "123123");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "大迷髦");
        request.putQueryParameter("TemplateCode", "SMS_165416562");
        request.putQueryParameter("TemplateParam", "{code:"+content+"}");
        CommonResponse response = client.getCommonResponse(request);
        logger.info("Send SMS {}, response:{}",phone, response.getData());


        logger.info("短信发送成功");
        return ResponseEntity.success().setResult("短信发送成功");
    }

    public ResponseEntity error(@PathVariable String phone, @PathVariable String content) {
        //发起某个网络请求（可能失败）
        System.out.println("sms中发生熔断");
        return ResponseEntity.failed().setResult("sms中发生熔断");
    }
}
