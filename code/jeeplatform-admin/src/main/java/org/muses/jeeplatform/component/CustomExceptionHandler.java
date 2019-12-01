package org.muses.jeeplatform.component;

import org.muses.jeeplatform.exception.CustomException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *  自定义异常处理类
 * </pre>
 *
 * @author nicky
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2019年12月01日  修改内容:
 * </pre>
 */
//@RestControllerAdvice
@ControllerAdvice
public class CustomExceptionHandler {

//    @ExceptionHandler(NotFoundException.class)
//    @ResponseBody
//    //@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
//    @Deprecated
//    public Map<String,Object> handleException(Exception e){
//        Map<String, Object> map = new HashMap<>(16);
//        map.put("code", "404");
//        map.put("message", e.getMessage());
//        return map;
//    }

    @ExceptionHandler({CustomException.class})
    public String handleException(Exception e, HttpServletRequest request){
        Map<String, Object> map = new HashMap<>(16);
        map.put("code", "404");
        map.put("message", e.getMessage());
        request.setAttribute("javax.servlet.error.status_code",404);
        request.setAttribute("extend",map);
        return "forward:/error";//BasicErrorController的接口
    }


}
