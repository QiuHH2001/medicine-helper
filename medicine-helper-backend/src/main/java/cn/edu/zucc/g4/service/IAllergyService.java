package cn.edu.zucc.g4.service;

import cn.edu.zucc.g4.entity.Allergy;
import cn.edu.zucc.g4.entity.Medicine;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * allergy 服务类
 * </p>
 *
 * @author G04
 * @since 2022-06-24
 */
public interface IAllergyService extends IService<Allergy> {

    List<Medicine> getAll(Long userId);
}
