package cn.edu.zucc.g4.service;

import cn.edu.zucc.g4.entity.Medicine;
import cn.edu.zucc.g4.entity.MedicineChest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * medicine_chest 服务类
 * </p>
 *
 * @author G04
 * @since 2022-06-24
 */
public interface IMedicineChestService extends IService<MedicineChest> {

    List<Medicine> getAllMedicineByUserId(Long userId);
}
