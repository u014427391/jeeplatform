package org.muses.jeeplatform.cas.model;

/**
 * <pre>
 *
 * </pre>
 *
 * @author nicky.ma
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2019年06月07日  修改内容:
 * </pre>
 */
public enum ResponseCode {
    //公共
    SUCCESS(200,"成功"),
    ERROR(500,"系统发生未知错误,请稍后重试"),

    ACCOUNT_EMPTY(1001,"用户名为空,请输入有效的用户名"),
    PASSWORD_INCORRECT(1006,"输入的密码有误,请重新输入"),
    PASSWORD_EMPTY(1002,"输入的密码为空,请输入有效的密码"),
    ACCOUNT_NOT_FOUND(1007,"帐号不存在,请重新输入"),
    ORGCODE_EMPTY(1008,"组织代码为空，请输入有效的组织代码"),
    REQUEST_REFUSE(403,"系统拒绝访问,请联系管理员");

    private int code;
    private String msg;
    ResponseCode(int code, String msg){
        this.code=code;
        this.msg=msg;
    }

}
