package cn.edu.zucc.g4.controller;

import cn.edu.zucc.g4.entity.Medicine;
import cn.edu.zucc.g4.entity.vo.RespBean;
import cn.edu.zucc.g4.entity.vo.RespBeanEnum;
import cn.edu.zucc.g4.service.IMedicineService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * medicine 前端控制器
 * </p>
 *
 * @author G04
 * @since 2022-06-24
 */
@Controller
@RequestMapping("/medicine")
@Api(value = "MedicineController", tags = "药品管理")
public class MedicineController {
    @Autowired
    private IMedicineService medicineService;

    @GetMapping("/{id}")
    public RespBean getMedicineById(@PathVariable Long id) {
        Medicine medicine = medicineService.getById(id);
        if (medicine != null) {
            return RespBean.success(medicine);
        }
        return RespBean.error(RespBeanEnum.ERROR);
    }
}
