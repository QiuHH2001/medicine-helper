package cn.edu.zucc.g4.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * allergy
 * </p>
 *
 * @author G04
 * @since 2022-06-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Allergy implements Serializable {

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
