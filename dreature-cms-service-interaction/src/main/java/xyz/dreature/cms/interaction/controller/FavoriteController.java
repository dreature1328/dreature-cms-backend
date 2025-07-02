package xyz.dreature.cms.interaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.dreature.cms.common.vo.Result;
import xyz.dreature.cms.interaction.service.FavoriteService;

@RestController
@RequestMapping("/favorite")
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;

    //	查询某个用户的收藏文章总数
    @RequestMapping("/query/total")
    public Integer queryTotalOfFavoriteOfUser(Integer userid) {
        return favoriteService.queryTotalOfFavoriteOfUser(userid);
    }

    @RequestMapping("/check")
    public ResponseEntity<Result> checkIfFavorited(Integer userid, Integer postid) {
        try {
            if (favoriteService.checkIfFavorited(userid, postid)) return ResponseEntity.ok().body(Result.success());
            else return ResponseEntity.ok().body(Result.error("文章未收藏"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(Result.error("检查是否已收藏文章失败"));
        }
    }

    //新增收藏
    @RequestMapping("/add")
    public ResponseEntity<Result> addFavoriteAndPostHot(Integer userid, Integer postid) {
        try {
            favoriteService.addFavorite(userid, postid);
            favoriteService.updatePostHot(postid, 10);
            return ResponseEntity.ok().body(Result.success());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(Result.error("添加收藏文章失败"));
        }
    }

    //取消收藏
    @RequestMapping("/cancel")
    public ResponseEntity<Result> cancelFavoriteAndReducePostHot(Integer userid, Integer postid) {
        try {
            favoriteService.cancelFavorite(userid, postid);
            favoriteService.updatePostHot(postid, -10);
            return ResponseEntity.ok().body(Result.success());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(Result.error("取消收藏文章失败"));
        }
    }

    //按页数查询某用户的收藏文章
    @RequestMapping("/mine")
    public ResponseEntity<Result> queryFavoriteByUserIdAndPage(Integer userid, Integer favoritepage, Integer favoritelength) {
        Result result = Result.success(favoriteService.queryFavoriteByUserIdAndPage(userid, favoritepage, favoritelength));
        return ResponseEntity.ok().body(result);
    }

}
