package xyz.dreature.cms.content.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import xyz.dreature.cms.common.vo.Result;
import xyz.dreature.cms.content.service.ContentService;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

@RestController
public class ContentController {
    @Autowired
    private ContentService contentService;

    @Autowired
    private RestTemplate restTemplate;

    //-----数量查询-----
//  查询文章总数
    @RequestMapping("/query/total")
    public Integer queryTotalOfPost() {
        return contentService.queryTotalOfPost();
    }

    //	查询某个类别下的文章总数
    @RequestMapping("/category/query/total")
    public Integer queryTotalOfPostInCategory(Integer categoryid) {
        if (categoryid == null) categoryid = 0;
        return contentService.queryTotalOfPostInCategory(categoryid);
    }

//-----前台文章与类别相关-----
//	文章按页数显示

    @RequestMapping("/recommend")
    public ResponseEntity<Result> queryPostByPage(Integer page, Integer length) {
        if (page == null) page = 1;
        if (length == null) length = 10;
        Result result = Result.success(contentService.queryPostByPage(page, length));
        return ResponseEntity.ok().body(result);
    }

    //  获取文章简略信息
    @RequestMapping("/brief")
    public ResponseEntity<Result> queryPostBriefByPostId(Integer postid) {
        Result result = Result.success(contentService.queryPostBriefByPostId(postid));
        return ResponseEntity.ok().body(result);
    }

    //	查询所有类别
    @RequestMapping("/category/query/all")
    public ResponseEntity<Result> queryAllCategory() {
        Result result = Result.success(contentService.queryAllCategory());
        return ResponseEntity.ok().body(result);
    }

    //  文章搜索
    @RequestMapping("/search")
    public ResponseEntity<Result> searchPostByQAndPage(String q, Integer page, Integer length) {
        Result result;
//		System.err.println("进行查询"+query);
        try {

            result = Result.success(contentService.searchPostByQAndPage(q, page, length));
//			System.out.println("成功查询");
//			for(int i = 0;i < result.size();i++) {
//				System.out.println(result.get(i));
//			}

        } catch (Exception e) {
            result = Result.error(e.getMessage());
        }
        return ResponseEntity.ok().body(result);
    }

    //-----文章阅读量、热度值相关-----
//  阅览数+1
    @RequestMapping("/view")
    public ResponseEntity<Result> addPostViewAndHot(Integer postid) {
        try {
            contentService.addPostView(postid);
            contentService.updatePostHot(postid, 1);
            // 没有异常，返回ok
            return ResponseEntity.ok().body(Result.success());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(Result.error(e.getMessage()));
        }
    }

    //重新计算热度值
    @RequestMapping("/hot/reset")
    public ResponseEntity<Result> resetPostHot(Integer postid) {
        try {
            contentService.updatePostHot(postid);
            // 没有异常，返回ok
            return ResponseEntity.ok().body(Result.success());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(Result.error(e.getMessage()));
        }
    }

    @RequestMapping("/rank")
    public ResponseEntity<Result> listHotRankByCategoryIdAndPage(Integer categoryid, Integer page, Integer length) {
        //如果categoryid为空，则用0表示罗列所有分类的文章
        if (categoryid == null) categoryid = 0;
        Result result = Result.success(contentService.listHotRankByCategoryIdAndPage(categoryid, page, length));
        return ResponseEntity.ok().body(result);
    }

    @RequestMapping("/detail")
    public ResponseEntity<Result> queryPostById(Integer postid) {
        Result result = Result.success(contentService.queryPostById(postid));
        return ResponseEntity.ok().body(result);
    }

    //------后台文章管理------
    //后台查询文章
    @RequestMapping("/admin/query")
    public ResponseEntity<Result> manageByPage(Integer page, Integer rows) {
        if (page == null) page = 1;
        if (rows == null) rows = 10;
        Result result = Result.success(contentService.queryPostByPage(page, rows));
        return ResponseEntity.ok().body(result);
    }

    public String getRequestBody(HttpServletRequest request) {

        BufferedReader reader;
        String line;
        StringBuffer buffer = new StringBuffer();
        try {
            reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (IOException e) {
            return null;
        }
        return buffer.toString();
    }

}
