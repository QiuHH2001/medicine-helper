package cn.edu.zucc.g4.service;

import cn.edu.zucc.g4.entity.Eat;
import cn.edu.zucc.g4.entity.vo.HistoryVo;
import cn.edu.zucc.g4.entity.vo.NoticeVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * eat 服务类
 * </p>
 *
 * @author G04
 * @since 2022-06-24
 */
public interface IEatService extends IService<Eat> {

    List<HistoryVo> getHistoryByUserId(Long userId);
    List<NoticeVo> getAllNotice(Long id);
    List<Eat> getThisMonthNotice(Long id);
}
