package xyz.dreature.cms.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.dreature.cms.common.entity.User;
import xyz.dreature.cms.common.util.CookieUtils;
import xyz.dreature.cms.common.vo.Result;
import xyz.dreature.cms.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/check")
    public ResponseEntity<Result> checkIfUserNameExist(String username) {
        Integer exist = userService.checkIfUserNameExist(username);
        Result result;
        if (exist == 0)
            result = Result.success();
        else
            result = Result.error("已存在");
        return ResponseEntity.ok().body(result);
    }

    @RequestMapping("/register")
    public ResponseEntity<Result> registerUser(User user) {
        // 1.检查输入的用户是否存在
        Integer a = userService.checkIfUserNameExist(user.getUserName());
        Result result;
        if (a > 0) {
            result = Result.error("用户名已存在");
        } else {
            // 2.注册
            try {
                userService.registerUser(user);
                result = Result.success();
            } catch (Exception e) {
                e.printStackTrace();
                result = Result.error(e.getMessage());
            }
        }
        return ResponseEntity.ok().body(result);
    }

    // 用户登录的校验功能，校验成功，将数据保存在redis供后续访问
    @RequestMapping("/login")
    public ResponseEntity<Result> doLoginWithRedis(User user, HttpServletRequest request, HttpServletResponse response) {

        // 调用业务层确定合法并且存储数据
        String ticket = userService.doLoginWithRedis(user);

        Result result;
        if (StringUtils.equals(ticket, "-1")) {
            result = Result.error("账号被封禁");    //-1表示被封禁
        } else if (StringUtils.equals(ticket, "-2")) {
            result = Result.error("该用户不存在");    //-2表示该用户不存在
        }
        // 控制层将业务存储登录成功的rediskey值
        else if (StringUtils.isNotEmpty(ticket)) {
            // ticket非空，表示redis已经存好登录的查询结果
            // 将ticket作为cookie的值返回，cookie的名称将根据接口文件的规定来定义
            // 调用cookieUtils工具类，将ticket添加到cookie返回给前端
            CookieUtils.setCookie(request, response, "DC_USER_TICKET", ticket);
            result = Result.success();
        } else {
            // 直接返回登录失败
            result = Result.error("登录失败");
        }
        return ResponseEntity.ok().body(result);
    }

    @RequestMapping("/logout/{ticket}")
    public ResponseEntity<Result> logOutUser(@PathVariable String ticket) {
        Result result;
        try {
            userService.logOutUser(ticket);
            result = Result.success();
        } catch (Exception e) {
            result = Result.error("登出失败");
        }
        return ResponseEntity.ok().body(result);
    }

    //	 通过cookie携带的ticket值查询redisuser数据
    @RequestMapping("/query/{ticket}")
    public ResponseEntity<Result> checkLoginUser(@PathVariable String ticket) throws JsonProcessingException {
        String userJson = userService.queryUserJson(ticket);
        Result result;
        // 判断非空
        if (StringUtils.isNotEmpty(userJson)) {
            // 确实曾经登录过，也正在登录使用状态中
            ObjectMapper objectMapper = new ObjectMapper();
            User user = objectMapper.readValue(userJson, User.class);
            result = Result.success(user);

        } else {
            result = Result.error("登录失败");
        }
        return ResponseEntity.ok().body(result);
    }
}
