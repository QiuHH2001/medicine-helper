package cn.edu.zucc.g4.service.impl;

import cn.edu.zucc.g4.entity.User;
import cn.edu.zucc.g4.entity.vo.RespBean;
import cn.edu.zucc.g4.entity.vo.RespBeanEnum;
import cn.edu.zucc.g4.mapper.UserMapper;
import cn.edu.zucc.g4.service.IUserService;
import cn.edu.zucc.g4.utils.JwtUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Random;
import java.util.UUID;

/**
 * <p>
 * user 服务实现类
 * </p>
 *
 * @author G04
 * @since 2022-06-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public RespBean login(String phone, String password) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        User user = userMapper.selectOne(wrapper);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                String token = JwtUtil.create(user.getId(), phone);
                return RespBean.login_success(token);
            } else {
                return RespBean.error(RespBeanEnum.PASSWORD_WRONG);
            }
        }
        return RespBean.error(RespBeanEnum.USER_UNEXIST);
    }

    @Override
    public RespBean register(String phone, String password) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        User user = userMapper.selectOne(wrapper);
        if (user != null) {
            return RespBean.error(RespBeanEnum.USER_EXIST);
        }
        User u = new User();
        Random random = new Random();
        String s = UUID.randomUUID().toString().replace('-', 'w');
        int lenR = random.nextInt(s.length() - 8);
        StringBuilder sb = new StringBuilder(s);
        String uid = 'U' + sb.substring(lenR, lenR + 7).toUpperCase(Locale.ROOT);
        u.setPhone(phone);
        u.setPassword(password);
        u.setUid(uid);
        int insert = userMapper.insert(u);
        System.out.println(insert);
        return RespBean.register_success();
    }
}
