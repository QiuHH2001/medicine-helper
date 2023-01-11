package cn.edu.zucc.g4.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * medicine
 * </p>
 *
 * @author G04
 * @since 2022-06-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Medicine implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 药品id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 药品名称
     */
    private String name;

    /**
     * 图片
     */
    private String picture;

    /**
     * 性状
     */
    private String property;

    /**
     * 适用症
     */
    private String indication;

    /**
     * 过敏症状
     */
    private String symptom;

    /**
     * 服用方式
     */
    private String administration;

}
