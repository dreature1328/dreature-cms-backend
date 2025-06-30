package xyz.dreature.cms.comment.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import xyz.dreature.cms.comment.service.CommentService;
import xyz.dreature.cms.common.entity.User;
import xyz.dreature.cms.common.vo.Result;
import xyz.dreature.cms.common.vo.SysResult;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CommentController {
    @Autowired
    CommentService commentService;

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/detail")
    public ResponseEntity<Result> queryCommentByPostId(Integer postid) {
        Result result = Result.success(commentService.queryCommentByPostId(postid));
        return ResponseEntity.ok().body(result);
    }

    @RequestMapping("/add")
    public ResponseEntity<Result> addComment(HttpServletRequest request) {
        try {
            User user = checkUser(request);
            Integer postId = Integer.parseInt(request.getParameter("postId"));
            Integer parentId = Integer.parseInt(request.getParameter("parentId"));
            String content = request.getParameter("content");

            commentService.addComment(user.getUserId(), user.getUserNickname(), user.getUserProfilePictureUrl(), postId, parentId, content);

            return ResponseEntity.ok().body(Result.success());

        } catch (Exception e) {
            return ResponseEntity.ok().body(Result.error());
        }
    }

    // ----- 后台管理 -----

    @RequestMapping("/admin/queryallcomments")
    public ResponseEntity<Result> queryAllComments(Integer page, Integer length) {
        Result result = Result.success(commentService.queryAllComments(page, length));
        return ResponseEntity.ok().body(result);
    }

    @RequestMapping("/admin/hide")
    public ResponseEntity<Result> hideComment(HttpServletRequest request) {
        try {
            checkAdmin(request);
            commentService.hideComment(request.getParameter("ids"));
            return ResponseEntity.ok().body(Result.success());
        } catch (Exception e) {
            return ResponseEntity.ok().body(Result.error());
        }
    }

    @RequestMapping("/admin/show")
    public ResponseEntity<Result> showComment(HttpServletRequest request) {
        try {
            checkAdmin(request);
            commentService.showComment(request.getParameter("ids"));
            return ResponseEntity.ok().body(Result.success());
        } catch (Exception e) {
            return ResponseEntity.ok().body(Result.error());
        }
    }

    // ----- 中间函数，判断身份 -----

    private User checkUser(HttpServletRequest request) throws Exception {

        String ticket = request.getParameter("ticket");
        if (ticket == null) throw new Exception("未登录，请先登录");

        SysResult result = restTemplate.getForObject("http://userservice/user/admin/inner/userquery/"
                + ticket, SysResult.class);
        if (result.getData() == null) throw new Exception(result.getMsg());


//		System.out.println(JSON.toJSONString(result.getData()));
        User user = JSON.parseObject(JSON.toJSONString(result.getData()), User.class);
//		System.out.println(user);
        return user;
    }

    private void checkAdmin(HttpServletRequest request) throws Exception {
        String ticket = request.getParameter("ticket");
        if (ticket == null) throw new Exception("非法访问!");

        Boolean isadmin = restTemplate.getForObject("http://userservice/user/admin/inner/adminquery/"
                + ticket, Boolean.class);

        if (!isadmin) throw new Exception("非法访问!");
    }
}
