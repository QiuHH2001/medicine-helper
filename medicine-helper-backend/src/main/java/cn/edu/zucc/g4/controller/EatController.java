package cn.edu.zucc.g4.controller;


import cn.edu.zucc.g4.entity.Eat;
import cn.edu.zucc.g4.entity.Medicine;
import cn.edu.zucc.g4.entity.User;
import cn.edu.zucc.g4.entity.vo.*;
import cn.edu.zucc.g4.service.IEatService;
import cn.edu.zucc.g4.service.IMedicineService;
import cn.edu.zucc.g4.service.IUserService;
import cn.edu.zucc.g4.utils.CommonUtil;
import cn.edu.zucc.g4.utils.JwtUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * eat 前端控制器
 * </p>
 *
 * @author G04
 * @since 2022-06-24
 */
@RestController
@RequestMapping("/eat")
@Api(value = "EatController", tags = "用药记录/用药历史")
public class EatController {
    @Autowired
    private IEatService eatService;
    @Autowired
    private IMedicineService medicineService;
    @Autowired
    private IUserService userService;

    @GetMapping("/history")
    @ApiOperation("根据用户ID获取用药历史")
    public RespBean getHistoryByUserId(@RequestHeader("Authorization") String token) {
        Long userId = JwtUtil.getId(token);
        List<HistoryVo> historyVoList = eatService.getHistoryByUserId(userId);
        return RespBean.success(historyVoList);
    }


    @GetMapping("/today")
    @ApiOperation("展示今日用药记录")
    public RespBean getAllNotice(@RequestHeader("Authorization") String token) {
        Long userId = JwtUtil.getId(token);
        List<NoticeVo> allNotice = eatService.getAllNotice(userId);
        List<NoticeVo> todayNotice = new ArrayList<>();
        for (NoticeVo e : allNotice) {
            if (CommonUtil.isToday(e.getTime())) {
                todayNotice.add(e);
            }
        }
        return RespBean.success(todayNotice);
    }

    @PostMapping("/{type}")
    @ApiOperation("设置用药提醒")
    public RespBean setNotice(@PathVariable Long type,
                              @RequestHeader("Authorization") String token,
                              @RequestBody Medicine medicine) {
        Long userId = JwtUtil.getId(token);

        Medicine m = medicineService.findOrSetMedicine(medicine);

        Eat notice = new Eat();
        notice.setUserId(userId);
        notice.setMedicineId(m.getId());
        notice.setTime(new Date());
        notice.setIsTaken(false);
        notice.setType(type);

        eatService.save(notice);
        return RespBean.success();
    }

    @GetMapping("/month")
    @ApiOperation("查看当月的所有用药提醒")
    public RespBean getThisMonthNotice(@RequestHeader("Authorization") String token) {
        String phone = JwtUtil.getPhone(token);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
//        User user = userService.getOne(wrapper);
//        Long id = user.getId();
        Long id = JwtUtil.getId(token);
        List<Eat> thisMonthNotice = eatService.getThisMonthNotice(id);
        return RespBean.success(thisMonthNotice);
    }

    @GetMapping("/month/{year}/{month}")
    @ApiOperation("根据月的查看当月的用药提醒")
    public RespBean getNoticeByMonth(@RequestHeader("Authorization") String token,
                                     @PathVariable Integer month,
                                     @PathVariable Integer year) {
        Long userId = JwtUtil.getId(token);
        QueryWrapper<Eat> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        month = month - 1;
        year = year - 1900;
        Date d = new Date(year, month, 1);
        int month2 = (month + 1);
        if (month == 11) {
            month2 = 0;
        }
        if (month == 10) {
            month2 = 11;
        }
        int year2 = year;
        if (month == 11) {
            year2 += 1;
        }
        Date sd1 = new Date(year, month, 1);
        Date sd2 = new Date(year2, month2, 1);
        wrapper.ge("time", sd1)
                .lt("time", sd2);

        List<Eat> notices = eatService.list(wrapper);
        return RespBean.success(notices);
    }

    @PostMapping("/FormAdd")
    @ApiOperation("添加用药提醒")
    public RespBean addRecord(@RequestBody EatVo eatVo,
                              @RequestHeader("Authorization") String token) {
        //        如果medicine中没有这种药，则添加到medicine中
        Long userId = JwtUtil.getId(token);
        eatVo.setUserId(userId);
        Medicine medicine = new Medicine();
        BeanUtil.copyProperties(eatVo, medicine);
        Long medicineId = medicineService.addMedicine(medicine);

//        添加一条用药提醒
        Eat eat = new Eat();
        BeanUtil.copyProperties(eatVo, eat);
        eat.setMedicineId(medicineId);
        eat.setIsTaken(false);
        eatService.save(eat);

        return RespBean.success();
    }

    @DeleteMapping("/{recordId}")
    public RespBean deleteRecord(@PathVariable String recordId) {
        boolean b = eatService.removeById(recordId);
        if (b)
            return RespBean.success();
        return RespBean.error(RespBeanEnum.ERROR);
    }

    @GetMapping
    public RespBean getNoticeById(@RequestHeader("Authorization") String token) {
        Long userId = JwtUtil.getId(token);
        QueryWrapper<Eat> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId)
                .eq("is_taken", 0);
        List<Eat> notices = eatService.list(wrapper);

        List<NoticeVo> noticeVoList = new ArrayList<>();
        for (Eat eat : notices) {
            NoticeVo noticeVo = new NoticeVo();
            BeanUtil.copyProperties(eat, noticeVo);
            Medicine medicine = medicineService.getById(eat.getMedicineId());
            noticeVo.setMedicineName(medicine.getName());
            noticeVoList.add(noticeVo);
        }

        return RespBean.success(noticeVoList);
    }

    /**
     * 已用药
     */
    @PostMapping("/finish/{eatId}")
    public RespBean finished(@PathVariable Long eatId){
        UpdateWrapper<Eat> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", eatId).set("is_taken", 1);
        boolean update = eatService.update(wrapper);
        if (update)
            return RespBean.success();
        return RespBean.error(RespBeanEnum.ERROR);
    }
}
