package com.riemann.springbootdemo.controller;

import com.riemann.springbootdemo.model.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 * @author riemann
 * @date 2019/12/16 21:17
 */
@Api(description = "用户操作接口")
@RestController
public class SwaggerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SwaggerController.class);

    @ApiOperation(value = "获取OTP", notes = "通过手机号获取OTP验证码")
    @ApiImplicitParam(name = "telephone", value = "电话号码", paramType = "query", required = true, dataType = "Integer")
    @RequestMapping(value = "/user/getotp", method= RequestMethod.GET)
    public ApiResult getOtp(@RequestParam(name = "telephone") String telephone, HttpServletRequest httpServletRequest) {
        // 需要按照一定的规则生成OTP验证码
        Random random = new Random();
        int randomInt = random.nextInt(999999);
        randomInt += 100000;
        String otpCode = String.valueOf(randomInt);

        // 将OTP验证码同对应用户的手机号关联,使用httpsession的方式绑定他的手机号与OTPCode
        httpServletRequest.getSession().setAttribute(telephone, otpCode);
        // 将OTP验证码通过短信通道发送给用户，省略
        LOGGER.info("telphone = {} & otpCode = {}", telephone, otpCode);
        return new ApiResult();
    }
}
