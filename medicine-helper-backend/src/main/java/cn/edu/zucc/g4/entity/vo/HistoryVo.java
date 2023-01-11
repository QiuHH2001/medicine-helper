package cn.edu.zucc.g4.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ：qhh
 * @date ：Created in 2022/6/24 13:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryVo implements Serializable {
    private Long userId;
    private String userName;
    private Long medicineId;
    private String name;
    private String dose;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime time;
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
     * 服用方式
     */
    private String administration;

    /**
     * 提醒方式：1闹钟；2弹窗；3语音
     */
    private Long way;

}
