package xyz.dreature.cms.interation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.dreature.cms.interation.service.FavoriteService;

@RestController

public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;

    //	查询某个用户的收藏文章总数
    @RequestMapping("/favorite/query/total")
    public Integer queryTotalOfFavoriteOfUser(Integer userid) {
        return favoriteService.queryTotalOfFavoriteOfUser(userid);
    }

    @RequestMapping("/favorite/check")
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
    @RequestMapping("/favorite/add")
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
    @RequestMapping("/favorite/cancel")
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
    @RequestMapping("/favorite/mine")
    public ResponseEntity<Result> queryFavoriteByUserIdAndPage(Integer userid, Integer favoritepage, Integer favoritelength) {
        Result result = Result.success(favoriteService.queryFavoriteByUserIdAndPage(userid, favoritepage, favoritelength));
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
////	public SysResult updateNum(Collect favorite) {
////		try {
////			favoriteService.updateNum(favorite);
////			return SysResult.ok();
////		} catch (Exception e) {
////			e.printStackTrace();
////			return SysResult.build(201, "", null);
////		}
////	}
//
    //后台收藏图表
    @CrossOrigin
    @RequestMapping("/favorite/admin/chart")
    public String showFavoriteChart() {
        String postJson = favoriteService.showFavoriteChart();
        return postJson;
    }
}
