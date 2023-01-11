package cn.edu.zucc.g4.service;

import cn.edu.zucc.g4.entity.Doctor;
import cn.edu.zucc.g4.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author G04
 * @since 2022-07-03
 */
public interface IDoctorService extends IService<Doctor> {
    List<User> getAllUser(Long doctorId);
}
