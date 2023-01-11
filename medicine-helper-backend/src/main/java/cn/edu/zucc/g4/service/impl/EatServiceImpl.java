package cn.edu.zucc.g4.service.impl;

import cn.edu.zucc.g4.entity.Eat;
import cn.edu.zucc.g4.entity.Medicine;
import cn.edu.zucc.g4.entity.User;
import cn.edu.zucc.g4.entity.vo.HistoryVo;
import cn.edu.zucc.g4.entity.vo.NoticeVo;
import cn.edu.zucc.g4.mapper.EatMapper;
import cn.edu.zucc.g4.mapper.MedicineMapper;
import cn.edu.zucc.g4.mapper.UserMapper;
import cn.edu.zucc.g4.service.IEatService;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * eat 服务实现类
 * </p>
 *
 * @author G04
 * @since 2022-06-24
 */
@Service
public class EatServiceImpl extends ServiceImpl<EatMapper, Eat> implements IEatService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EatMapper eatMapper;

    @Autowired
    private MedicineMapper medicineMapper;

    @Override
    public List<HistoryVo> getHistoryByUserId(Long userId) {
        QueryWrapper<Eat> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).eq("is_taken", 1);
        List<Eat> eatList = eatMapper.selectList(queryWrapper);

        List<HistoryVo> historyVoList = new ArrayList<>();
        for (Eat eat : eatList) {
            HistoryVo historyVo = new HistoryVo();

            BeanUtil.copyProperties(eat, historyVo);
            Medicine medicine = medicineMapper.selectById(eat.getMedicineId());
            BeanUtil.copyProperties(medicine, historyVo);

            User user = userMapper.selectById(historyVo.getUserId());
            historyVo.setUserName(user.getNickname());
            historyVoList.add(historyVo);
        }
        return historyVoList;
    }

    @Override
    public List<NoticeVo> getAllNotice(Long id) {
        QueryWrapper<Eat> wrapper = new QueryWrapper<>();
        Date loadingTime = new Date();
        Date d = new Date(loadingTime.getYear(), loadingTime.getMonth(), loadingTime.getDate());
        wrapper.gt("time", d)
                .eq("user_id", id);
        List<Eat> eatList = eatMapper.selectList(wrapper);
        List<NoticeVo> noticeVoList = new ArrayList<>();
        for (Eat eat : eatList) {
            NoticeVo noticeVo = new NoticeVo();
            BeanUtil.copyProperties(eat, noticeVo);
            Medicine medicine = medicineMapper.selectById(eat.getMedicineId());
            noticeVo.setMedicineName(medicine.getName());
            noticeVoList.add(noticeVo);
        }
        return noticeVoList;
    }

    @Override
    public List<Eat> getThisMonthNotice(Long id) {

        QueryWrapper<Eat> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", id);
        Date d = new Date();
        int month1 = d.getMonth();
        int month2 = (d.getMonth()+1) % 12;
        if (month1 == 10) {
            month2 = 11;
        }
        int year1 = d.getYear();
        int year2 = d.getYear();
        if (month1 == 11) {
            year2++;
        }
        Date sd1 = new Date(year1, month1, 1);
        Date sd2 = new Date(year2, month2, 1);
        wrapper.ge("time", sd1)
                .lt("time", sd2)
        ;
        return eatMapper.selectList(wrapper);
    }
}
