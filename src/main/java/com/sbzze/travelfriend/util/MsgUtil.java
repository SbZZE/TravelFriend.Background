package com.sbzze.travelfriend.util;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MsgUtil {

    public enum Type {
        AVATAR,
        INFO
    }


    public static Map setMsg( String username, Enum type, String content ){
        Map msg = new HashMap();

        String id = String.valueOf(UUID.randomUUID());

        msg.put("id", id);
        msg.put("type", type);
        msg.put("account", username);
        msg.put("content", content);

        return msg;
    }
}
