package cn.edu.zucc.g4.controller;


import cn.edu.zucc.g4.entity.Allergy;
import cn.edu.zucc.g4.entity.Medicine;
import cn.edu.zucc.g4.entity.MedicineChest;
import cn.edu.zucc.g4.entity.vo.RespBean;
import cn.edu.zucc.g4.entity.vo.RespBeanEnum;
import cn.edu.zucc.g4.service.IAllergyService;
import cn.edu.zucc.g4.service.IMedicineService;
import cn.edu.zucc.g4.utils.JwtUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;


/**
 * <p>
 * allergy 前端控制器
 * </p>
 *
 * @author G04
 * @since 2022-06-24
 */

@RestController
@RequestMapping("/allergy")
@Api(value = "AllergyController", tags = "过敏药物管理")
public class AllergyController {
    @Autowired
    private IAllergyService allergyService;

    @Autowired
    private IMedicineService medicineService;

    @GetMapping("/")
    @ApiOperation("根据用户ID获取过敏药物")
    public RespBean getAllergicMedicine(@RequestHeader("Authorization") String token){
        Long userId = JwtUtil.getId(token);
        List<Medicine> medicineList = allergyService.getAll(userId);
        return RespBean.success(medicineList);
    }

    @PostMapping("/")
    @ApiOperation("添加过敏药物")
    public RespBean addAllergicMedicine(@RequestHeader("Authorization") String token,
                                        @RequestBody Medicine medicine){
//        userId = 11L;
//        用户在自己的药箱中添加药品，若medicine中已有这种药，则不添加，反之添加
        Long userId = JwtUtil.getId(token);
        Long medicineId = medicineService.addMedicine(medicine);
//        得到medicineId后，在medicine_chest中添加记录
        try {
            allergyService.save(new Allergy(userId, medicineId));
            return RespBean.success();
        }catch (Exception e){
            return RespBean.error(RespBeanEnum.ALLERGY_REPEAT);
        }
    }

    @DeleteMapping("/{medicineId}")
    public RespBean deleteMedicine(@RequestHeader("Authorization") String token,
                                   @PathVariable Long medicineId){
        Long userId = JwtUtil.getId(token);
        QueryWrapper<Allergy> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId)
                .eq("medicine_id", medicineId);
        boolean remove = allergyService.remove(wrapper);
        if (remove)
            return RespBean.success();
        return RespBean.error(RespBeanEnum.ERROR);
    }

}
