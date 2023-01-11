package cn.edu.zucc.g4.service;

import cn.edu.zucc.g4.entity.Medicine;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * medicine 服务类
 * </p>
 *
 * @author G04
 * @since 2022-06-24
 */
public interface IMedicineService extends IService<Medicine> {

    Medicine findOrSetMedicine(Medicine medicine);
    Long addMedicine(Medicine medicine);

}
