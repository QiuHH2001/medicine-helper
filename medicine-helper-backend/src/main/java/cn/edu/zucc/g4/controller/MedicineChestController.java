package cn.edu.zucc.g4.controller;

import cn.edu.zucc.g4.entity.Medicine;
import cn.edu.zucc.g4.entity.MedicineChest;
import cn.edu.zucc.g4.entity.vo.RespBean;
import cn.edu.zucc.g4.entity.vo.RespBeanEnum;
import cn.edu.zucc.g4.service.IMedicineChestService;
import cn.edu.zucc.g4.service.IMedicineService;
import cn.edu.zucc.g4.utils.JwtUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.sql.SQLException;
import java.util.List;

/**
 * <p>
 * medicine_chest 前端控制器
 * </p>
 *
 * @author G04
 * @since 2022-06-24
 */
@RestController
@RequestMapping("/medicineChest")
@Api(value = "MedicineChestController", tags = "药箱管理")
public class MedicineChestController {
    @Autowired
    private IMedicineChestService medicineChestService;

    @Autowired
    private IMedicineService medicineService;

    @GetMapping("/")
    @ApiOperation("根据用户ID获取药箱所有药")
    public RespBean getAllMedicine(@RequestHeader("Authorization") String token) {
        Long userId = JwtUtil.getId(token);
        List<Medicine> medicineList = medicineChestService.getAllMedicineByUserId(userId);
        return RespBean.success(medicineList);
    }

    @PostMapping("/")
    @ApiOperation("药箱中添加药品")
    public RespBean addMedicine(@RequestBody Medicine medicine,
                                @RequestHeader("Authorization") String token) {
        Long userId = JwtUtil.getId(token);
//        用户在自己的药箱中添加药品，若medicine中已有这种药，则不添加，反之添加
        Long medicineId = medicineService.addMedicine(medicine);
//        得到medicineId后，在medicine_chest中添加记录
        try {
            medicineChestService.save(new MedicineChest(userId, medicineId));
            return RespBean.success();
        } catch (Exception e) {
            return RespBean.error(RespBeanEnum.MEDICINE_REPEAT);
        }
    }

    @DeleteMapping("/{medicineId}")
    @ApiOperation("删除药箱中的药品")
    public RespBean deleteMedicine(@RequestHeader("Authorization") String token,
                                   @PathVariable Long medicineId) {
        Long userId = JwtUtil.getId(token);
        QueryWrapper<MedicineChest> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId)
                .eq("medicine_id", medicineId);
        boolean remove = medicineChestService.remove(wrapper);
        if (remove)
            return RespBean.success();
        return RespBean.error(RespBeanEnum.ERROR);
    }

}
