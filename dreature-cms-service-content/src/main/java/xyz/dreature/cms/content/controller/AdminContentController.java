package xyz.dreature.cms.content.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.dreature.cms.common.entity.Post;
import xyz.dreature.cms.common.vo.Result;
import xyz.dreature.cms.content.service.ContentService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/admin")
public class AdminContentController {
    @Autowired
    private ContentService contentService;

    //后台发布文章
    @RequestMapping("/add")
    public ResponseEntity<Result> addPost(@RequestBody Post post) {
//		System.out.println(getRequestBody(request));
        // 使用异常信息来表示成功和失败
        try {
//			checkAdmin(request);	//检查身份
            contentService.postPost(post);
            // 没有异常，返回ok
            return ResponseEntity.ok().body(Result.success());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(Result.error(e.getMessage()));
        }
    }

    //后台修改文章
    @RequestMapping("/update")
    public ResponseEntity<Result> updatePost(@RequestBody Post post) {
        System.out.println(post);
        try {
//			checkAdmin(request);	//检查身份
            contentService.updatePostWithRedis(new Post(post));
            return ResponseEntity.ok().body(Result.success());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(Result.error(e.getMessage()));
        }
    }

    //后台删除文章
    @RequestMapping("/delete")
    public ResponseEntity<Result> deletePost(String ids) {
        try {
//			checkAdmin(request);	//检查身份
            contentService.deletePostWithRedis(ids);

            return ResponseEntity.ok().body(Result.success());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(Result.error(e.getMessage()));
        }
    }

    //
    //后台隐藏文章
    @RequestMapping("/hide")
    public ResponseEntity<Result> hidePost(HttpServletRequest request) {
        try {
//			checkAdmin(request);	//检查身份
            contentService.hidePostWithRedis(request.getParameter("ids"));
            return ResponseEntity.ok().body(Result.success());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(Result.error(e.getMessage()));
        }
    }

    //后台显示文章
    @RequestMapping("/show")
    public ResponseEntity<Result> showPost(HttpServletRequest request) {
        try {
//			checkAdmin(request);	//检查身份
            contentService.showPost(request.getParameter("ids"));
            return ResponseEntity.ok().body(Result.success());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(Result.error(e.getMessage()));
        }
    }
}
