package cn.edu.zucc.g4.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author ：qhh
 * @date ：Created in 2022/6/24 10:11
 * 公共返回对象枚举
 */
@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum {
    //    通用
    SUCCESS(200L, "SUCCESS"),
    ERROR(500L, "服务端异常"),

    LOGIN_SUCCESS(201L, "登陆成功"),
    REGISTER_SUCCESS(202L, "注册成功"),
    // 登录
    USER_UNEXIST(401L, "用户不存在"),
    PASSWORD_WRONG(402L, "密码错误"),
    USER_ERROR(401L, "登陆已过期，请重新登陆"),
    // 注册
    USER_EXIST(403L, "用户已存在"),
    // 添加药品
    MEDICINE_REPEAT(501L, "药箱中已有该商品"),
    ALLERGY_REPEAT(502L, "该药品已是过敏药物"),
    // 用户
//    MODIFY_NICKNAME_ERROR(503L,"修改失败")
    REPEAT_BIND(504L, "不可重复绑定医生"),
    BIND_ERROR(505L,"绑定失败"),
    ;

    private final Long code;
    private final String message;
}