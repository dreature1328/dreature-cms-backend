package xyz.dreature.cms.interaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.dreature.cms.interaction.service.FavoriteService;

@RestController
@RequestMapping("/favorite/admin")
public class AdminFavoriteController {
    @Autowired
    private FavoriteService favoriteService;

    //=====后台收藏管理=====
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
    @RequestMapping("/chart")
    public String showFavoriteChart() {
        String postJson = favoriteService.showFavoriteChart();
        return postJson;
    }
}
