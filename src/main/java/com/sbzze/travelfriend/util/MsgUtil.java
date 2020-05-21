package com.sbzze.travelfriend.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MsgUtil {

    public static Map setMsg( String username ){
        Map msg = new HashMap();
        String msgId = String.valueOf(UUID.randomUUID());
        String msgData = "Info changed by user " + username;
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        msg.put("msgId", msgId);
        msg.put("msgData", msgData);
        msg.put("createTime", createTime);

        return msg;
    }
}
