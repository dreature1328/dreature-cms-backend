package xyz.dreature.cms.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.dreature.cms.common.entity.User;
import xyz.dreature.cms.common.vo.Result;
import xyz.dreature.cms.user.service.UserService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/admin")
public class AdminUserController {
    @Autowired
    private UserService userService;

    // ===== 网关内部微服务的请求 =====
    @RequestMapping("/inner/adminquery/{ticket}")
    public boolean checkAdminUserin(@PathVariable String ticket) {
        try {
            return userService.checkAdminUser(ticket);
        } catch (Exception e) {
            return false;
        }
    }

    @RequestMapping("/innerquery/{ticket}")
    public ResponseEntity<Result> checkUser(@PathVariable String ticket) {
        Result result;
        try {
            result = Result.success(userService.checkUser(ticket));
            //传出用户类型
        } catch (Exception e) {
            //传出查询失败信息
            result = Result.error(e.getMessage());
        }
        return ResponseEntity.ok().body(result);
    }


    // ===== 后台用户管理 =====
    // 通过cookie携带的ticket值查询redis user数据
    @RequestMapping("/adminquery/{ticket}")
    public ResponseEntity<Result> checkAdminUser(@PathVariable String ticket) {
        Result result;
        try {
            if (userService.checkAdminUser(ticket)) result = Result.success();
            else result = Result.error("非管理员用户，禁止访问后台");
        } catch (Exception e) {
            result = Result.error(e.getMessage());
        }
        return ResponseEntity.ok().body(result);
    }

    @RequestMapping("/query")
    public ResponseEntity<Result> queryUserByPage(Integer page, Integer rows) {
        if (page == null) page = 1;
        if (rows == null) rows = 10;
        Result result = Result.success(userService.queryUserByPage(page, rows));
        return ResponseEntity.ok().body(result);
    }

    @RequestMapping("/update")
    public ResponseEntity<Result> updateUser(User user, HttpServletRequest request) {
        Result result;
        try {
            String ticket = request.getParameter("ticket");
            //身份检测
            if (!userService.checkAdminUser(ticket)) result = Result.error("非法访问");
            else {
                userService.updateUser(user);
                result = Result.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = Result.error(e.getMessage());
        }
        return ResponseEntity.ok().body(result);
    }

    @RequestMapping("/ban")
    public ResponseEntity<Result> banUser(HttpServletRequest request) {
        Result result;
        try {
            String userids = request.getParameter("userids");
            String ticket = request.getParameter("ticket");
            //身份检测
            if (!userService.checkAdminUser(ticket)) result = Result.error("非法访问");
            else {
                userService.banUser(userids);
                result = Result.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = Result.error(e.getMessage());
        }
        return ResponseEntity.ok().body(result);
    }

    @RequestMapping("/unban")
    public ResponseEntity<Result> unbanUser(HttpServletRequest request) {
        Result result;
        try {
            String userids = request.getParameter("userids");
            String ticket = request.getParameter("ticket");
            //身份检测
            if (!userService.checkAdminUser(ticket)) result = Result.error("非法访问");
            else {
                userService.unbanUser(userids);
                result = Result.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = Result.error(e.getMessage());
        }
        return ResponseEntity.ok().body(result);
    }

    @RequestMapping("/grant")
    public ResponseEntity<Result> grantUser(HttpServletRequest request) {
        Result result;
        try {
            String userids = request.getParameter("userids");
            String ticket = request.getParameter("ticket");
            Integer type = Integer.parseInt(request.getParameter("type"));
            //身份检测
            if (!userService.checkAdminUser(ticket)) result = Result.error("非法访问");
            else {
                userService.grantUser(userids, type);
                result = Result.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = Result.error(e.getMessage());
        }
        return ResponseEntity.ok().body(result);
    }
}
