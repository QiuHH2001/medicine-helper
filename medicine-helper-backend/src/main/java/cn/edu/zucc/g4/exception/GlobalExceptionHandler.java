package cn.edu.zucc.g4.exception;

import cn.edu.zucc.g4.entity.vo.RespBean;
import cn.edu.zucc.g4.entity.vo.RespBeanEnum;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public RespBean ExceptionHandler(Exception e) {
        e.printStackTrace();
        if (e instanceof GlobalException) {
            GlobalException ex = (GlobalException) e;
            return RespBean.error(ex.getRespBeanEnum());
        }
        return RespBean.error(RespBeanEnum.ERROR);
    }
}
