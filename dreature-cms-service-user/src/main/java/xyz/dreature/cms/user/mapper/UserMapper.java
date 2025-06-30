package xyz.dreature.cms.user.mapper;

import org.apache.ibatis.annotations.Param;
import xyz.dreature.cms.common.entity.User;

import java.util.List;

public interface UserMapper {

    public Integer queryTotalOfUser();

    public User queryUserById(Integer userId);

    public User queryUserByNameAndPassword(User user);

    public Integer checkIfUserNameExist(String userName);

    public void registerUser(User user);

    //-----后台用户管理-----
    // 交给sql语句的参数一般只有一个，两个还能否使用#{}
    // 多个参数传递给SQLSession根据映射文件#{}拼接数据时，可以用@Param注解定义参数变量名称
    public List<User> queryUserByPage(@Param("start") Integer start, @Param("rows") Integer rows);

    public void updateUser(User user);

    //下列操作可对多个用户（多行信息）同时进行
    public void banUser(String[] ids);

    public void unbanUser(String[] ids);

    public void grantUser(@Param("userids") String[] ids, @Param("type") Integer type);


}
