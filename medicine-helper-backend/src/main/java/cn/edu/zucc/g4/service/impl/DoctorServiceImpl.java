package cn.edu.zucc.g4.service.impl;

import cn.edu.zucc.g4.entity.Doctor;
import cn.edu.zucc.g4.entity.User;
import cn.edu.zucc.g4.mapper.DoctorMapper;
import cn.edu.zucc.g4.mapper.UserMapper;
import cn.edu.zucc.g4.service.IDoctorService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author G04
 * @since 2022-07-03
 */
@Service
public class DoctorServiceImpl extends ServiceImpl<DoctorMapper, Doctor> implements IDoctorService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getAllUser(Long doctorId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("doctor_id", doctorId);
        return userMapper.selectList(queryWrapper);
    }
}
