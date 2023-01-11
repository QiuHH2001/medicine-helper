package cn.edu.zucc.g4.controller;


import cn.edu.zucc.g4.entity.Doctor;
import cn.edu.zucc.g4.entity.User;
import cn.edu.zucc.g4.entity.vo.RespBean;
import cn.edu.zucc.g4.entity.vo.RespBeanEnum;
import cn.edu.zucc.g4.service.IDoctorService;
import cn.edu.zucc.g4.service.IUserService;
import cn.edu.zucc.g4.utils.JwtUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * user 前端控制器
 * </p>
 *
 * @author G04
 * @since 2022-06-24
 */

@RestController
@RequestMapping("/user")
@Api(value = "UserController", tags = "用户管理")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IDoctorService doctorService;

    @PostMapping("/login")
    @ApiOperation("登录")
    public RespBean login(@RequestBody User user) {
        return userService.login(user.getPhone(), user.getPassword());
    }

    @PostMapping("/register")
    @ApiOperation("注册")
    public RespBean register(@RequestBody User user) {
        return userService.register(user.getPhone(), user.getPassword());
    }

    @PostMapping("/modify/{id}")
    @ApiOperation("根据用户UID修改用户的昵称")
    public RespBean modifyName(@PathVariable String id, @RequestBody User user) {
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("uid", id)
                .set("nickname", user.getNickname());
        boolean b = userService.update(wrapper);
        if (b) {
            return RespBean.success();
        }

        return RespBean.error(RespBeanEnum.ERROR);
    }

    @GetMapping
    @ApiOperation("获取用户个人信息")
    public RespBean getUserInfo(@RequestHeader("Authorization") String token) {
        String phone = JwtUtil.getPhone(token);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        User user = userService.getOne(wrapper);
        if (user != null) {
            return RespBean.success(user);
        }
        return RespBean.error(RespBeanEnum.ERROR);
    }

    /**
     * 展示医生列表
     * @return
     */
    @GetMapping("/doctor")
    public RespBean getAllDoctor(){
        return RespBean.success(doctorService.list());
    }

    /**
     * 绑定医生
     * @param doctorId
     * @param token
     * @return
     */
    @PostMapping("/doctor/{doctorId}")
    public RespBean bindDoctor(@PathVariable Long doctorId,
                               @RequestHeader("Authorization") String token){
//        查看用户是否已绑定医生，若已绑定，则返回失败
        Long userId = JwtUtil.getId(token);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);
        User user = userService.getOne(queryWrapper);
        Long result = user.getDoctorId();

        if (result != null)
            return RespBean.error(RespBeanEnum.REPEAT_BIND);

//        若未绑定医生，则绑定
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", userId).set("doctor_id", doctorId);
        boolean update = userService.update(wrapper);
        if (update)
            return RespBean.success();
        return RespBean.error(RespBeanEnum.BIND_ERROR);
    }
}
