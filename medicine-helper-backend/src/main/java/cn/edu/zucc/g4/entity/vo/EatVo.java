package cn.edu.zucc.g4.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: QiuHH
 * @CreateTime: 2022-07-02  11:13
 * @Description: TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EatVo {

    private Long userId;
//    private Long medicineId;
    private String name;
    /**
     * 性状
     */
    private String property;
    /**
     * 剂量
     */
    private String dose;
    /**
     * 服用方式
     */
    private String administration;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;
    /**
     * 提醒类型：1表单；2药箱；3语音；4用药历史
     */
    private Long type;
    /**
     * 提醒方式：1语音；2闹钟；3弹窗
     */
    private Long way;
    private String description;
}
