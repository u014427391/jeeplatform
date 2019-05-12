package org.muses.jeeplatform.core;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <pre>
 *  接口返回类
 * </pre>
 *
 * @author nicky
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2019年05月12日  修改内容:
 * </pre>
 */

@Data
public class ResultVO<T> {

    @ApiModelProperty("状态码")
    private Integer status;
    @ApiModelProperty("返回信息")
    private String message;
    @ApiModelProperty("返回数据")
    private T data;

    public ResultVO(Integer status, String message){
        this.status = status;
        this.message = message;
    }

    public ResultVO(Integer status, String message, T data){
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> ResultVO error(String message) {
        return new ResultVO(0, message, null);
    }

    public static <T> ResultVO error(String message,T data) {
        return new ResultVO(0, message, data);
    }

    public static <T> ResultVO successful(String message){
        return new ResultVO(1,message,null);
    }

    public static <T> ResultVO successful(String message, T data){
        return new ResultVO(1, message, data);
    }

}
