package xyz.dreature.cms.comment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.dreature.cms.comment.service.CommentService;
import xyz.dreature.cms.common.vo.Result;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/admin")
public class AdminCommentController {

    @Autowired
    CommentService commentService;

    // ===== 后台管理 =====
    @RequestMapping("/queryallcomments")
    public ResponseEntity<Result> queryAllComments(Integer page, Integer length) {
        Result result = Result.success(commentService.queryAllComments(page, length));
        return ResponseEntity.ok().body(result);
    }

    @RequestMapping("/hide")
    public ResponseEntity<Result> hideComment(HttpServletRequest request) {
        try {
            commentService.checkAdmin(request);
            commentService.hideComment(request.getParameter("ids"));
            return ResponseEntity.ok().body(Result.success());
        } catch (Exception e) {
            return ResponseEntity.ok().body(Result.error());
        }
    }

    @RequestMapping("/show")
    public ResponseEntity<Result> showComment(HttpServletRequest request) {
        try {
            commentService.checkAdmin(request);
            commentService.showComment(request.getParameter("ids"));
            return ResponseEntity.ok().body(Result.success());
        } catch (Exception e) {
            return ResponseEntity.ok().body(Result.error());
        }
    }
}
