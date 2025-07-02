package xyz.dreature.cms.comment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import xyz.dreature.cms.comment.mapper.CommentMapper;
import xyz.dreature.cms.common.entity.Comment;
import xyz.dreature.cms.common.entity.User;
import xyz.dreature.cms.common.vo.SysResult;
import xyz.dreature.cms.common.vo.TableResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    RestTemplate restTemplate;

    public List<Comment> queryCommentByPostId(Integer postId) {
        List<Comment> comments = commentMapper.queryCommentByPostId(postId);
        //将mapper层查到的这篇文章所有的评论转化为stream（即将里面的所有评论放进流水线），
        //通过filter（分类/过滤）选出符合条件的comment，
        //进行peek（对符合条件的每一个comment进行处理，处理完后不return）找到它的所有子评论，
        //并sorted（对所有filter出来的comment进行排序），按发表时间排序，
        //最后collect（收集剩余的comment，其中流水线停止作业为filter没有过滤出合适的comment时）收集。

        List<Comment> result = comments.stream().filter(
                //lambda表达式，comment为局部变量，类型为Comment，返回一个boolean，表示该comment是否符合条件
                comment -> comment.getCommentDepth() == 0 && comment.getCommentStatus() == 1    //当该评论层级等于0时，即父评论;只选择可显示的评论
        ).peek(comment -> {
            Integer parentCommentId = comment.getCommentId();
            //找到该评论的所有子评论并收集成List，赋给comment的childcomment
            List<Comment> childComments = comments.stream().filter(
                    childcomment -> childcomment.getCommentParentId() == parentCommentId && childcomment.getCommentStatus() == 1
            ).sorted(
                    //按照时间先后排序
                    (cm1, cm2) -> (cm1.getCommentId() == null ? 0 : cm1.getCommentId()) - (cm2.getCommentId() == null ? 0 : cm2.getCommentId())
            ).collect(Collectors.toList());
            comment.setChildComments(childComments);
        }).sorted(
                (cm1, cm2) -> (cm1.getCommentId() == null ? 0 : cm1.getCommentId()) - (cm2.getCommentId() == null ? 0 : cm2.getCommentId())
        ).collect(Collectors.toList());
        System.out.println(result);
        return result;
    }

    public void addComment(Integer commenterId, String commenterNickname, String commenterProfilePictureUrl, Integer postId, Integer parentId, String context) {
        Comment comment = new Comment(
                commenterId,                //评论人id
                commenterNickname,            //评论人名称
                commenterProfilePictureUrl,
                postId,                    //文章id
                parentId,                    //父评论id
                parentId == 0 ? 0 : 1,            //若父评论id为0，即为最顶级评论
                context,                    //评论内容
                null,
                1);                            //评论状态（显示）

        commentMapper.addComment(comment);
    }

    public void hideComment(String commentIds) {
        String temp[] = commentIds.split(",");
        commentMapper.hideComment(temp);
    }

    public void showComment(String commentIds) {
        String temp[] = commentIds.split(",");
        commentMapper.showComment(temp);
    }

    public TableResult queryAllComments(Integer page, Integer length) {
        // 封装数据到EasyUIResult对象
        // 1.创建一个返回的对象,将查询数据set进去然后返回
        TableResult result = new TableResult();
        // 2封装第一个属性 total 的数量
        Integer total = commentMapper.queryTotal();
        // 3封装第二个属性List<Comment> pList
        // 根据页数计算起始位置
        Integer start = (page - 1) * length;
        //page指页数，length指每页长度（能容纳多少个对象）
        List<Comment> pList = commentMapper.queryAllComments(start, length);
        System.out.println(pList);

        // 4封装对象属性
        result.setTotal(total);
        result.setItems(pList);
        return result;
    }

    // ===== 中间函数，判断身份 =====

    public User checkUser(HttpServletRequest request) throws Exception {

        String ticket = request.getParameter("ticket");
        if (ticket == null) throw new Exception("未登录，请先登录");

        SysResult result = restTemplate.getForObject("http://userservice/user/admin/inner/userquery/"
                + ticket, SysResult.class);
        if (result.getData() == null) throw new Exception(result.getMsg());

        ObjectMapper objectMapper = new ObjectMapper();

        User user = objectMapper.convertValue(result.getData(), User.class);
        return user;
    }

    public void checkAdmin(HttpServletRequest request) throws Exception {
        String ticket = request.getParameter("ticket");
        if (ticket == null) throw new Exception("非法访问!");

        Boolean isadmin = restTemplate.getForObject("http://userservice/user/admin/inner/adminquery/"
                + ticket, Boolean.class);

        if (!isadmin) throw new Exception("非法访问!");
    }
}
