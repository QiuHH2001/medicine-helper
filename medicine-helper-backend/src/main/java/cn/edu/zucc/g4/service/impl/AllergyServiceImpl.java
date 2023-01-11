package cn.edu.zucc.g4.service.impl;

import cn.edu.zucc.g4.entity.Allergy;
import cn.edu.zucc.g4.entity.Medicine;
import cn.edu.zucc.g4.mapper.AllergyMapper;
import cn.edu.zucc.g4.mapper.MedicineMapper;
import cn.edu.zucc.g4.service.IAllergyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * allergy 服务实现类
 * </p>
 *
 * @author G04
 * @since 2022-06-24
 */
@Service
public class AllergyServiceImpl extends ServiceImpl<AllergyMapper, Allergy> implements IAllergyService {
    @Autowired
    private AllergyMapper allergyMapper;

    @Autowired
    private MedicineMapper medicineMapper;

    @Override
    public List<Medicine> getAll(Long userId) {
        QueryWrapper<Allergy> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<Allergy> allergies = allergyMapper.selectList(queryWrapper);
        List<Medicine> medicineList = new ArrayList<>();
        for (Allergy allergy : allergies) {
            Medicine medicine = medicineMapper.selectById(allergy.getMedicineId());
            medicineList.add(medicine);
        }
        return medicineList;
    }
}
