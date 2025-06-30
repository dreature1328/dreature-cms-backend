package xyz.dreature.cms.interation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import xyz.dreature.cms.common.entity.Favorite;
import xyz.dreature.cms.common.entity.Post;
import xyz.dreature.cms.common.vo.TableResult;
import xyz.dreature.cms.interation.mapper.FavoriteMapper;

import java.util.List;

@Service
public class FavoriteService {
    @Autowired
    private FavoriteMapper favoriteMapper;

    @Autowired
    private RestTemplate restTemplate;

//	//通过postID获取对应的收藏数
//	public int getFavoriteNumByPostId(String postId) {
//		return favoriteMapper.getFavoriteNumByPostId(postId);
//	}

    //查询某用户的收藏文章总数
    public Integer queryTotalOfFavoriteOfUser(Integer userId) {
        return favoriteMapper.queryTotalOfFavoriteOfUser(userId);
    }

    public boolean checkIfFavorited(Integer userId, Integer postId) {
        Favorite exist = favoriteMapper.queryFavoriteByUserIdAndPostId(userId, postId);    //判断是否已收藏该文章
        if (exist != null) {
            return true;
        }
        return false;
    }

    //按页数查询某用户的收藏文章
    public TableResult queryFavoriteByUserIdAndPage(Integer userId, Integer page, Integer length) {

        TableResult result = new TableResult();
        Integer total = favoriteMapper.queryTotalOfFavoriteOfUser(userId);
        Integer start = (page - 1) * length;
//
        List<Post> pList = favoriteMapper.queryFavoriteByUserIdAndPage(userId, start, length);
        result.setTotal(total);
        result.setItems(pList);
        return result;
    }

    public void addFavorite(Integer userId, Integer postId) {
        if (checkIfFavorited(userId, postId)) {
//			exist.setNum(exist.getNum()+favorite.getNum());
//			favoriteMapper.updateNum(exist);
            System.out.println("收藏已存在");
        } else {
            Favorite favorite = new Favorite();
            favorite.setFavoriteFavoriterId(userId);
            favorite.setFavoritePostId(postId);
            //不存在数据，新增前要补充完整所有信息
            //发起响商品中心调用服务的代码
//			Post post=restTemplate.getForObject("http://productservice/product/admin/item/"
//					+favorite.getFavoritePostId(),Post.class);
//			favorite.setPostPrice(post.getPostFavoritenumc());
//			favorite.setPostName(post.getPostName());
//			favorite.setPostImage(post.getPostImgurl());
            favoriteMapper.addFavorite(favorite);
//			favoriteMapper.addfavorite(favorite.getFavoritePostId());
        }
    }

    //
////	public void updateNum(Favorite favorite) {
////		favoriteMapper.updateNum(favorite);
////	}
//
    public void cancelFavorite(Integer userId, Integer postId) {
        if (!checkIfFavorited(userId, postId)) {
//			exist.setNum(exist.getNum()+favorite.getNum());
//			favoriteMapper.updateNum(exist);
            System.out.println("收藏不存在");
        } else {
            Favorite favorite = new Favorite();
            favorite.setFavoriteFavoriterId(userId);
            favorite.setFavoritePostId(postId);
            favoriteMapper.cancelFavorite(favorite);
        }
    }

    //增加热度值
    public void updatePostHot(Integer postId, Integer addedValue) {
        favoriteMapper.addPostHot(postId, addedValue);
    }

    public String showFavoriteChart() {
        List<Post> posts = favoriteMapper.showFavoriteChart();
        StringBuffer name = new StringBuffer("");
        StringBuffer num = new StringBuffer("");
        StringBuffer fdata = new StringBuffer("");

        for (Post post : posts) {
            name.append(post.getPostTitle()).append(',');
            num.append(post.getPostFavorite()).append(",");
        }

        name.deleteCharAt(name.length() - 1);
        num.deleteCharAt(num.length() - 1);

        String postJson = "{\"name\":\"" + name + "\",\"num\":\"" + num + "\"}";

        return postJson;

    }

}
