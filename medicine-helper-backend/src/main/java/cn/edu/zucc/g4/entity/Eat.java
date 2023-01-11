package cn.edu.zucc.g4.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * eat
 * </p>
 *
 * @author G04
 * @since 2022-06-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Eat implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 药品id
     */
      private Long medicineId;

    /**
     * 剂量
     */
    private String dose;

    /**
     * 服用时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    /**
     * 是否服用，若已服用，则成为用药历史
     */
    private Boolean isTaken;

    /**
     * 提醒类型：1表单；2药箱；3语音；4用药历史
     */
    private Long type;

    /**
     * 提醒方式：1语音；2闹钟；3弹窗
     */
    private Long way;

    /**
     * 备注
     */
    private String description;

}
