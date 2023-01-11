package cn.edu.zucc.g4.service;

import cn.edu.zucc.g4.entity.User;
import cn.edu.zucc.g4.entity.vo.RespBean;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * user 服务类
 * </p>
 *
 * @author G04
 * @since 2022-06-24
 */

public interface IUserService extends IService<User> {
    RespBean login(String phone, String password);
    RespBean register(String phone, String password);
}

