package org.muses.jeeplatform.cas.authentication.encode;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <pre>
 *   自定义PasswordEncoder
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/04/24 17:02  修改内容:
 * </pre>
 */
public class MD5PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        String encodeStr = charSequence.toString() + "aa";
        if (encodeStr.equals(s)) {
            return true;
        }
        return false;
    }
}
