package cn.edu.zucc.g4.mapper;

import cn.edu.zucc.g4.entity.Medicine;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 * <p>
 * medicine Mapper 接口
 * </p>
 *
 * @author G04
 * @since 2022-06-24
 */
@Mapper
public interface MedicineMapper extends BaseMapper<Medicine> {

    void addMedicine(Medicine medicine);
}
