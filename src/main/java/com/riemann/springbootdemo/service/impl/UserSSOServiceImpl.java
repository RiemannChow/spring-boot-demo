package com.riemann.springbootdemo.service.impl;

import com.riemann.springbootdemo.model.ResultSSO;
import com.riemann.springbootdemo.model.UserSSO;
import com.riemann.springbootdemo.repository.JedisClient;
import com.riemann.springbootdemo.repository.UserSSORepository;
import com.riemann.springbootdemo.service.UserSSOService;
import com.riemann.springbootdemo.util.CookieUtil;
import com.riemann.springbootdemo.util.HttpClientUtil;
import com.riemann.springbootdemo.util.JsonUtil;
import com.riemann.springbootdemo.util.SSOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author riemann
 * @date 2019/06/29 15:53
 */
@Service
@Transactional
@PropertySources({@PropertySource(value = "classpath:redis.properties"),@PropertySource(value = "classpath:resource.properties")})
public class UserSSOServiceImpl implements UserSSOService {

    @Autowired
    private UserSSORepository userSSORepository;

    @Resource
    private JedisClient jedisClient;

    @Value("${REDIS_USER_SESSION_KEY}")
    private String REDIS_USER_SESSION_KEY;

    @Value("${SSO_SESSION_EXPIRE}")
    private Integer SSO_SESSION_EXPIRE;

    @Value("${SSO_BASE_URL}")
    public String SSO_BASE_URL;

    @Value("${SSO_DOMAIN_BASE_USRL}")
    public String SSO_DOMAIN_BASE_USRL;

    @Value("${SSO_USER_TOKEN}")
    private String SSO_USER_TOKEN;

    @Value("${SSO_PAGE_LOGIN}")
    public String SSO_PAGE_LOGIN;

    @Override
    public ResultSSO registerUser(UserSSO userSSO) {
        // 检查用户名是否注册，一般在前端验证的时候处理，因为注册不存在高并发的情况，这里再加一层查询是不影响性能的
        if (null != userSSORepository.findByAccount(userSSO.getAccount())) {
            return ResultSSO.build(400, "");
        }
        userSSORepository.save(userSSO);
        // 注册成功后选择发送邮件激活。现在一般都是短信验证码
        return ResultSSO.build(200, "");
    }

    @Override
    public ResultSSO userLogin(String account, String password, HttpServletRequest request, HttpServletResponse response) {
        // 判断账号密码是否正确
        UserSSO userSSO = userSSORepository.findByAccount(account);
        if (!SSOUtil.decryptPassword(userSSO, password)) {
            return ResultSSO.build(400, "账号名或密码错误");
        }
        // 生成token
        String token = UUID.randomUUID().toString();
        // 清空密码和盐避免泄漏
        String userPassword = userSSO.getPassword();
        String userSalt = userSSO.getSalt();
        userSSO.setPassword(null);
        userSSO.setSalt(null);
        // 把用户信息写入 redis
        jedisClient.set(REDIS_USER_SESSION_KEY + ":" + token, JsonUtil.bean2Json(userSSO));
        // userSSO 已经是持久化对象了，被保存在了session缓存当中，若userSSO又重新修改了属性值，那么在提交事务时，此时 hibernate对象就会拿当前这个user对象和保存在session缓存中的user对象进行比较，如果两个对象相同，则不会发送update语句，否则，如果两个对象不同，则会发出update语句。
        userSSO.setPassword(userPassword);
        userSSO.setSalt(userSalt);
        // 设置 session 的过期时间
        jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);
        // 添加写 cookie 的逻辑，cookie 的有效期是关闭浏览器就失效。
        CookieUtil.setCookie(request, response, "USER_TOKEN", token);
        // 返回token
        return ResultSSO.ok(token);
    }

    @Override
    public void logout(String token) {
        jedisClient.del(REDIS_USER_SESSION_KEY + ":" + token);
    }

    @Override
    public ResultSSO queryUserByToken(String token) {

        // 根据token从redis中查询用户信息
        String json = jedisClient.get(REDIS_USER_SESSION_KEY + ":" + token);
        // 判断是否为空
        if (StringUtils.isEmpty(json)) {
            return ResultSSO.build(400, "此session已经过期，请重新登录");
        }
        // 更新过期时间
        jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);
        // 返回用户信息
        return ResultSSO.ok(JsonUtil.json2Bean(json, UserSSO.class));
    }

    @Override
    public UserSSO getUserByToken(String token) {
        try {
            // 调用sso系统的服务，根据token取用户信息
            String json = HttpClientUtil.doGet(SSO_BASE_URL + SSO_USER_TOKEN + token);
            System.out.println("json : " + json);
            // 把json转换成ItdragonResult
            ResultSSO result = ResultSSO.formatToModel(json, UserSSO.class);
            if (null != result && result.getStatus() == 200) {
                UserSSO userSSO = (UserSSO) result.getData();
                return userSSO;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
