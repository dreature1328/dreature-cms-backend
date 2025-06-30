package xyz.dreature.cms.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IndexStringMapper {
    public static IndexStringMapper instance = null;
    private static List<String> user_type = null;
    private static List<String> user_state = null;
    private static List<String> post_state = null;
    private static Object lock = new Object();

    private IndexStringMapper() {
        String user_types[] = new String[]{"普通用户", "会员用户", "管理员"};
        this.user_type = new ArrayList<String>(Arrays.asList(user_types));

        String user_states[] = new String[]{"被封禁", "正常"};
        this.user_state = new ArrayList<String>(Arrays.asList(user_states));

        String post_states[] = new String[]{"隐藏", "显示"};
        this.post_state = new ArrayList<String>(Arrays.asList(post_states));
    }

    public static IndexStringMapper getInstance() {
        //经典多线程单例模式
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new IndexStringMapper();
                }
            }
        }
        return instance;
    }

    public String UserType2String(Integer typeid) {
        return user_type.get(typeid);
    }

    public Integer UserString2Type(String typestr) {
        return user_type.indexOf(typestr);
    }

    public String UserState2String(Integer stateid) {
        return user_state.get(stateid);
    }

    public Integer UserString2State(String statestr) {
        return user_state.indexOf(statestr);
    }

    public String PostState2String(Integer stateid) {
        return post_state.get(stateid);
    }

    public Integer PostString2State(String statestr) {
        return post_state.indexOf(statestr);
    }
}
