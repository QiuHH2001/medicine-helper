package cn.edu.zucc.g4.service.impl;

import cn.edu.zucc.g4.entity.Medicine;
import cn.edu.zucc.g4.entity.vo.RespBean;
import cn.edu.zucc.g4.mapper.MedicineMapper;
import cn.edu.zucc.g4.mapper.UserMapper;
import cn.edu.zucc.g4.service.IMedicineService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * medicine 服务实现类
 * </p>
 *
 * @author G04
 * @since 2022-06-24
 */
@Service
public class MedicineServiceImpl extends ServiceImpl<MedicineMapper, Medicine> implements IMedicineService {
    @Autowired
    private MedicineMapper medicineMapper;


    @Override
    public Medicine findOrSetMedicine(Medicine medicine) {

        QueryWrapper<Medicine> wrapper = new QueryWrapper<>();
        wrapper.eq("name", medicine.getName());
        Medicine m = medicineMapper.selectOne(wrapper);
        if (m != null) {
            return m;
        }

        medicineMapper.insert(medicine);

        return medicine;
    }
    @Override
    public Long addMedicine(Medicine medicine) {
        QueryWrapper<Medicine> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", medicine.getName());
        Medicine result = medicineMapper.selectOne(queryWrapper);
//        若medicine中已有这种药，则返回药品id，若无则添加记录
        if (result != null){
            return result.getId();
        }
        else {
            medicineMapper.addMedicine(medicine);
            return medicine.getId();
        }
    }
}
