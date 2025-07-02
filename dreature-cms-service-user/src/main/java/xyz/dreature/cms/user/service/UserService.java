package xyz.dreature.cms.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import xyz.dreature.cms.common.entity.User;
import xyz.dreature.cms.common.util.MapperUtil;
import xyz.dreature.cms.common.util.PrefixKey;
import xyz.dreature.cms.common.vo.TableResult;
import xyz.dreature.cms.user.mapper.UserMapper;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private Jedis jedis;

    private ObjectMapper mapper = MapperUtil.MP;

    //不借助redis，无缓存功能的通过id查询用户
    public User queryUserById(Integer userId) {
        try {
            User User = userMapper.queryUserById(userId);
            String UserJson = mapper.writeValueAsString(User);
            return User;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String queryUserJson(String ticket) {
        String userJson = "";
        try {
            // 先判断超时剩余时间
            Long leftTime = jedis.pttl(ticket);
            // 少于10分钟，延长5分钟
            if (leftTime < 1000 * 60 * 10l)
                jedis.pexpire(ticket, leftTime + 1000 * 60 * 5);
            userJson = jedis.get(ticket);
            return userJson;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public Integer checkIfUserNameExist(String userName) {
        return userMapper.checkIfUserNameExist(userName);
    }

    public void registerUser(User user) {
        // user的数据填写完整 userId password加密
//		user.setUserId(UUID.randomUUID().toString());
//		user.setUserPassword(MD5Util.md5(user.getUserPassword()));
        // insert 数据到数据库
        userMapper.registerUser(user);
    }

    //借助redis实现用户登录
    public String doLoginWithRedis(User user) {
        try {
//			// 对password做加密操作
//			user.setUserPassword(MD5Util.md5(user.getUserPassword()));
            // 利用user数据查询数据库，判断登录是否合法
            User exist = userMapper.queryUserByNameAndPassword(user);

            if (exist == null) { // 登录失败，没有数据可以存到redis
                return "";
            }
//			else if(exist.getUserState()==0) {	//用户状态为被封禁状态
//				return "-1";
//			}
            else {
                // 为了后续访问能获取到user对象的数据，需要创建一个key值，将userJson作为value
                // 存储在redis中，key值返回给前端
                // 前端页面下次访问就可以携带生成一个cookie将要携带回去的ticket
                String ticket = UUID.randomUUID().toString();
                // jackson的代码，将已存在的exist用户对象转化为json
                String userJson = mapper.writeValueAsString(exist);
                String userkey = PrefixKey.USER_LOGINED_CHECK_PREFIX + exist.getUserId();

                System.out.println(userJson);
                // 将该用户的ticket放入该账户的链表
                jedis.lpush(userkey, ticket);

                jedis.set(ticket, userJson);

                // 记录该ticket所登录的账户，并设置过期时间
                jedis.setex(ticket, 60 * 30, userJson);

                // 仅保留最近三位登录过的用户的ticket，其他用户的ticket将被删除
                if (jedis.llen(userkey) > 3) {
                    // 先删除链表外的ticket，再删除链表内的记录
                    List<String> usertickets = jedis.lrange(userkey, 3, -1);
                    for (String t : usertickets) {
                        jedis.del(t);
                    }
                    jedis.ltrim(userkey, 0, 2);    //只保留3个ticket
                }
                return ticket;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void logOutUser(String ticket) throws Exception {
        jedis.del(ticket);
    }

//=====后台用户管理------

    public boolean checkAdminUser(String ticket) throws Exception {
        if (ticket == null) throw new Exception("未登录");
        String userJson = "";
        Long lefttime = jedis.ttl(ticket);
        if (lefttime < 0) throw new Exception("登录超时,或非法ticket");
        userJson = jedis.get(ticket);
        User user = mapper.readValue(userJson, User.class);
        //延长登录时间，最长不超过30分钟
        jedis.setex(ticket, Math.max(Integer.parseInt(lefttime + "") + 300, 1800), userJson);
        return user.getUserType() == 2;
    }

    public User checkUser(String ticket) throws Exception {
        if (ticket == null) throw new Exception("未登录");
        String userJson = "";
        Long lefttime = jedis.ttl(ticket);
        if (lefttime < 0) throw new Exception("登录超时,或非法ticket");
        userJson = jedis.get(ticket);
        User user = mapper.readValue(userJson, User.class);
        //延长登录时间，最长不超过30分钟
        jedis.setex(ticket, Math.max(Integer.parseInt(lefttime + "") + 300, 1800), userJson);
        return user;
    }

    public TableResult queryUserByPage(Integer page, Integer rows) {
        // 封装数据到EasyUIResult对象
        // 1.创建一个返回的对象,将查询数据set进去然后返回
        TableResult result = new TableResult();
        // 2封装第一个属性 total 的数量
        Integer total = userMapper.queryTotalOfUser();
        // 3封装第二个属性List<OutUser> oList
        // 根据页数计算起始位置
        Integer start = (page - 1) * rows;
        List<User> pList = userMapper.queryUserByPage(start, rows);
//		List<OutUser> oList=new ArrayList<OutUser>();
//		for(User user:pList) {
//			oList.add(new OutUser(user));
//		}
        // 4封装对象属性
        result.setTotal(total);
        result.setItems(pList);
        return result;
    }

    //
    public void updateUser(User user) throws Exception {
        //先更改redis中该用户的ticket对应的userjson
        String userkey = PrefixKey.USER_LOGINED_CHECK_PREFIX + user.getUserId();
        if (jedis.llen(userkey) > 0) {    //先判断redis中是否存在该用户ticket
            List<String> usertickets = jedis.lrange(userkey, 0, -1);

            if (jedis.ttl(usertickets.get(0)) > 0) {
                //在redis中拿到user信息，转类型后更改其中的type再转回去，存回redis中
                String userJson = jedis.get(usertickets.get(0));
                User olduser = mapper.readValue(userJson, User.class);
                olduser.setUserName(user.getUserName());
                olduser.setUserNickname(user.getUserNickname());
                olduser.setUserEmail(user.getUserEmail());
                userJson = mapper.writeValueAsString(user);

                //更改所存储的信息
                for (String t : usertickets) {
                    Long lefttime = jedis.ttl(t);
                    if (lefttime > 0) {
                        jedis.setex(t, Integer.parseInt(lefttime + ""), userJson);
                    }
                }
            }
        }

        userMapper.updateUser(user);
    }

    public void banUser(String userids) {
        //被封禁的账号ticket都不能用，解封后需要重新获得ticket
        //先删除redis中该用户的ticket
        String ids[] = userids.split(",");
        for (String id : ids) {
            String userkey = PrefixKey.USER_LOGINED_CHECK_PREFIX + id;
            if (jedis.llen(userkey) > 0) {
                List<String> usertickets = jedis.lrange(userkey, 0, -1);
                for (String t : usertickets) {
                    jedis.del(t);
                }

                //再删除列表
                jedis.del(userkey);
            }
        }

        //到数据库改状态
        userMapper.banUser(ids);
    }

    public void unbanUser(String userids) {
        String ids[] = userids.split(",");
        //直接到数据库改状态
        userMapper.unbanUser(ids);
    }

    public void grantUser(String userids, Integer type) throws Exception {
        String ids[] = userids.split(",");

//		if(typeid<0||typeid>2)	throw new Exception("权限类型错误");
        for (String id : ids) {
            //先更改redis中该用户的ticket对应的userjson
            String userkey = PrefixKey.USER_LOGINED_CHECK_PREFIX + id;
            if (jedis.llen(userkey) > 0) {    //先判断redis中是否存在该用户ticket
                List<String> usertickets = jedis.lrange(userkey, 0, -1);

                String newuserJson = null;
                //更改所存储的信息
                for (String t : usertickets) {
                    Long lefttime = jedis.ttl(t);
                    if (lefttime > 0) {
                        if (newuserJson == null) {
                            String userJson = jedis.get(t);
                            User user = mapper.readValue(userJson, User.class);
                            user.setUserType(type);
                            newuserJson = mapper.writeValueAsString(user);
                        }
                        jedis.setex(t, Integer.parseInt(lefttime + ""), newuserJson);
                    }
                }
            }
        }

        //到数据库改权限
        userMapper.grantUser(ids, type);
    }
}
