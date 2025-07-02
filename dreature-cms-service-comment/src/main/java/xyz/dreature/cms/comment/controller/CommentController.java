package xyz.dreature.cms.comment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import xyz.dreature.cms.comment.service.CommentService;
import xyz.dreature.cms.common.entity.User;
import xyz.dreature.cms.common.vo.Result;

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
            User user = commentService.checkUser(request);
            Integer postId = Integer.parseInt(request.getParameter("postId"));
            Integer parentId = Integer.parseInt(request.getParameter("parentId"));
            String content = request.getParameter("content");

            commentService.addComment(user.getUserId(), user.getUserNickname(), user.getUserProfilePictureUrl(), postId, parentId, content);

            return ResponseEntity.ok().body(Result.success());

        } catch (Exception e) {
            return ResponseEntity.ok().body(Result.error());
        }
    }


}
