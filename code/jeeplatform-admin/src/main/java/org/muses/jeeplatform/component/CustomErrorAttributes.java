package org.muses.jeeplatform.component;

import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;

import java.util.Map;

/**
 * <pre>
 *   自定义异常Attributes类
 * </pre>
 *
 * @author nicky
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2019年12月01日  修改内容:
 * </pre>
 */
@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

    //返回值的map就是页面和json能获取的所有字段
    @Override
    public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
        //先将默认的Attributes封装到map
        Map<String, Object> map = super.getErrorAttributes(requestAttributes, includeStackTrace);
        map.put("company","company.com");
        //获取ExceptionHandler设置的Attributes，0表示从Request中拿
        Map<String,Object> ext = (Map<String, Object>) requestAttributes.getAttribute("extend",0);
        map.put("extend",ext);
        return map;
    }

}