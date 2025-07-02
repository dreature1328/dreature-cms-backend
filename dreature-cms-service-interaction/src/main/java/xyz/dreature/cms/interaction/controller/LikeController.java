package xyz.dreature.cms.interaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.dreature.cms.common.vo.Result;
import xyz.dreature.cms.interaction.service.LikeService;

@RestController
@RequestMapping("/like")
public class LikeController {
    @Autowired
    private LikeService likeService;

    //	查询某个用户的收藏文章总数
    @RequestMapping("/query/total")
    public Integer queryTotalOfLikeOfUser(Integer userid) {
        return likeService.queryTotalOfLikeOfUser(userid);
    }

    @RequestMapping("/check")
    public ResponseEntity<Result> checkIfLiked(Integer userid, Integer postid) {
        try {
            if (likeService.checkIfLiked(userid, postid)) return ResponseEntity.ok().body(Result.success());
            else return ResponseEntity.ok().body(Result.error("文章未收藏"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(Result.error("检查是否已收藏文章失败"));
        }
    }

    //新增收藏
    @RequestMapping("/add")
    public ResponseEntity<Result> addLikeAndPostHot(Integer userid, Integer postid) {
        try {
            likeService.addLike(userid, postid);
            likeService.updatePostHot(postid, 5);
            return ResponseEntity.ok().body(Result.success());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(Result.error("收藏文章失败"));
        }
    }

    //取消收藏
    @RequestMapping("/cancel")
    public ResponseEntity<Result> cancelLikeAndReducePostHot(Integer userid, Integer postid) {
        try {
            likeService.cancelLike(userid, postid);
            likeService.updatePostHot(postid, -10);
            return ResponseEntity.ok().body(Result.success());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(Result.error("取消收藏文章失败"));
        }
    }

    //按页数查询某用户的收藏文章
    @RequestMapping("/mine")
    public ResponseEntity<Result> queryLikeByUserIdAndPage(Integer userid, Integer likepage, Integer likelength) {
        Result result = Result.success(likeService.queryLikeByUserIdAndPage(userid, likepage, likelength));
        return ResponseEntity.ok().body(result);
    }


}
