package cn.edu.zucc.g4.service.impl;

import cn.edu.zucc.g4.entity.Medicine;
import cn.edu.zucc.g4.entity.MedicineChest;
import cn.edu.zucc.g4.mapper.MedicineChestMapper;
import cn.edu.zucc.g4.mapper.MedicineMapper;
import cn.edu.zucc.g4.service.IMedicineChestService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * medicine_chest 服务实现类
 * </p>
 *
 * @author G04
 * @since 2022-06-24
 */
@Service
public class MedicineChestServiceImpl extends ServiceImpl<MedicineChestMapper, MedicineChest> implements IMedicineChestService {
    @Autowired
    private MedicineChestMapper medicineChestMapper;

    @Autowired
    private MedicineMapper medicineMapper;

    @Override
    public List<Medicine> getAllMedicineByUserId(Long userId) {
        QueryWrapper<MedicineChest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<MedicineChest> medicineChestList = medicineChestMapper.selectList(queryWrapper);

        List<Medicine> medicineList = new ArrayList<>();
        for (int i = 0; i < medicineChestList.size(); i++) {
            Medicine medicine = medicineMapper.selectById(medicineChestList.get(i).getMedicineId());
            medicineList.add(medicine);
        }
        return medicineList;
    }
}
