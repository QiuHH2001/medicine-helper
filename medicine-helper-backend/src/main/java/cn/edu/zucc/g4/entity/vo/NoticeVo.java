package cn.edu.zucc.g4.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

/**
 * @Author: QiuHH
 * @CreateTime: 2022-07-05  14:53
 * @Description: TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeVo {
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
     * 药品名称
     */
    private String medicineName;

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
