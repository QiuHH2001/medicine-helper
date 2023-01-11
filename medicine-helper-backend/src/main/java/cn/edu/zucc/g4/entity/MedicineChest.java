package cn.edu.zucc.g4.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * medicine_chest
 * </p>
 *
 * @author G04
 * @since 2022-06-24
 */
@TableName("medicine_chest")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicineChest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 药品id
     */
      private Long medicineId;
}
