package cn.edu.zucc.g4.controller;

import cn.edu.zucc.g4.entity.Allergy;
import cn.edu.zucc.g4.entity.Doctor;
import cn.edu.zucc.g4.entity.Medicine;
import cn.edu.zucc.g4.entity.User;
import cn.edu.zucc.g4.entity.vo.HistoryVo;
import cn.edu.zucc.g4.entity.vo.RespBean;
import cn.edu.zucc.g4.entity.vo.RespBeanEnum;
import cn.edu.zucc.g4.service.IAllergyService;
import cn.edu.zucc.g4.service.IDoctorService;
import cn.edu.zucc.g4.service.IEatService;
import cn.edu.zucc.g4.service.IMedicineService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author G04
 * @since 2022-07-03
 */
@RestController
@RequestMapping("/doctor")
@Api("医生管理")
//@CrossOrigin
public class DoctorController {
    @Autowired
    private IDoctorService doctorService;

    @Autowired
    private IEatService eatService;

    @Autowired
    private IAllergyService allergyService;

    @Autowired
    private IMedicineService medicineService;

    /**
     * 展示该医生负责的所有用户
     * @param doctorId
     * @return
     */
    @ApiOperation("根据医生ID获取所有用户")
    @GetMapping("/getAllUser/{doctorId}")
    public RespBean getAllUser(@PathVariable String doctorId){
        Long id = Long.valueOf(doctorId);
        List<User> userList = doctorService.getAllUser(id);
        return RespBean.success(userList);
    }

    @ApiOperation("根据医生ID获取所有用户用药历史")
    @GetMapping("/{doctorId}/{nickname}")
    public RespBean getHistory(@PathVariable(required = false) String nickname,
                               @PathVariable Long doctorId){
        List<User> userList = doctorService.getAllUser(doctorId);
        List<HistoryVo> historyVoList = new ArrayList<>();
        for (User user : userList) {
            List<HistoryVo> historyVos = eatService.getHistoryByUserId(user.getId());
            historyVoList.addAll(historyVos);
        }
        return RespBean.success(historyVoList);
    }

    @ApiOperation("根据用户Id获取用户的过敏药物")
    @GetMapping("/allergy/{userId}")
    public RespBean getAllergyMedicine(@PathVariable Long userId){
        List<Medicine> medicineList = allergyService.getAll(userId);
        return RespBean.success(medicineList);
    }

    @ApiOperation("为用户添加过敏药物")
    @PostMapping("/allergy/{userId}")
    public RespBean addAllergy(@PathVariable Long userId,
                               @RequestBody Medicine medicine){
        //        用户在自己的药箱中添加药品，若medicine中已有这种药，则不添加，反之添加
        Long medicineId = medicineService.addMedicine(medicine);
//        得到medicineId后，在medicine_chest中添加记录
        try {
            allergyService.save(new Allergy(userId, medicineId));
            return RespBean.success();
        }catch (Exception e){
            return RespBean.error(RespBeanEnum.ALLERGY_REPEAT);
        }
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    public RespBean login(@RequestBody Doctor doctor){
        QueryWrapper<Doctor> wrapper = new QueryWrapper<>();
        wrapper.eq("name", doctor.getName())
                .eq("password", doctor.getPassword());
        Doctor result = doctorService.getOne(wrapper);
        if (result != null){
            Map<String, Object> map = new HashMap();
            map.put("token", result.getId());
            map.put("doctorId", result.getId());
            map.put("name", result.getName());
            return RespBean.success(map);
        }
        return RespBean.error(RespBeanEnum.PASSWORD_WRONG);
    }

    @ApiOperation("获取信息")
    @GetMapping("/info")
    public RespBean getInfo(){
//        Long id = Long.valueOf(token);
//        Doctor doctor = doctorService.getById(id);
//        Map<String, Object> map = new HashMap<>();
//        map.put("roles", "[doctor]");
//        map.put("name", doctor.getName());
//        map.put("avatar", "https://qhh-guli.oss-cn-hangzhou.aliyuncs.com/2022/07/03/b634466961044c458b18a88c09e30bc5file.png");


        Map<String, Object> map = new HashMap<>();
        map.put("roles", "[doctor]");
        map.put("name", "qhh");
        map.put("avatar", "https://qhh-guli.oss-cn-hangzhou.aliyuncs.com/2022/07/03/b634466961044c458b18a88c09e30bc5file.png");
        return RespBean.success(map);
    }


}
