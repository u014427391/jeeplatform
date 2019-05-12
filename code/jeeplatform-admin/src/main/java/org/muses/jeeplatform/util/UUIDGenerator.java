package org.muses.jeeplatform.util;

import java.util.UUID;

/**
 * Created by Nicky on 2017/11/19.
 */
public class UUIDGenerator {

    public static String getRandomNum(){
        String s = UUID.randomUUID().toString();
        s =  s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
        return s.substring(0, 12);
    }
}
