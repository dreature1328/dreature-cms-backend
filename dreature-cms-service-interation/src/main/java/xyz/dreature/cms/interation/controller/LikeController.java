package xyz.dreature.cms.interation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.dreature.cms.common.vo.Result;
import xyz.dreature.cms.interation.service.LikeService;

@RestController

public class LikeController {
    @Autowired
    private LikeService likeService;

    //	查询某个用户的收藏文章总数
    @RequestMapping("/like/query/total")
    public Integer queryTotalOfLikeOfUser(Integer userid) {
        return likeService.queryTotalOfLikeOfUser(userid);
    }

    @RequestMapping("/like/check")
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
    @RequestMapping("/like/add")
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
    @RequestMapping("/like/cancel")
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
    @RequestMapping("/like/mine")
    public ResponseEntity<Result> queryLikeByUserIdAndPage(Integer userid, Integer likepage, Integer likelength) {
        Result result = Result.success(likeService.queryLikeByUserIdAndPage(userid, likepage, likelength));
        return ResponseEntity.ok().body(result);
    }

//-----后台收藏管理-----
//	private void checkAdmin(HttpServletRequest request) throws Exception {
//		String ticket=request.getParameter("ticket");
//		if(ticket==null)	throw new Exception("非法访问!");
//		Boolean isadmin=restTemplate.getForObject("http://userservice/user/admin/inner/adminquery/"
//				+ticket,Boolean.class);
//		if(!isadmin)	throw new Exception("非法访问!");
//	}


//
////	//根据userId productId更新num
////	@RequestMapping("update")
////	public SysResult updateNum(Collect like) {
////		try {
////			likeService.updateNum(like);
////			return SysResult.ok();
////		} catch (Exception e) {
////			e.printStackTrace();
////			return SysResult.build(201, "", null);
////		}
////	}
//

    //后台收藏图表
    @CrossOrigin
    @RequestMapping("/like/admin/chart")
    public String showLikeChart() {
        String postJson = likeService.showLikeChart();
        return postJson;
    }
}
