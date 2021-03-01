package com.douyuehan.doubao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.douyuehan.doubao.model.entity.BmsTip;
import org.springframework.stereotype.Repository;

/** 每日一句
 * @author bing  @create 2021/3/1-8:47 下午
 */
@Repository
public interface BmsTipMapper extends BaseMapper<BmsTip> {
    BmsTip getRandomTip();
}

