package cn.edu.zucc.g4.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author G04
 * @since 2022-07-03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Doctor对象", description = "")
public class Doctor implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("医生id")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("医生姓名")
    private String name;

    @ApiModelProperty("医生密码")
    private String password;

    @ApiModelProperty("医生头像")
    private String avatar;

    @ApiModelProperty("医生详情")
    private String introduction;

    @ApiModelProperty("医生更多")
    private String more;
}
