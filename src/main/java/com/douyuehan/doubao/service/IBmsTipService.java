package com.douyuehan.doubao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.douyuehan.doubao.model.entity.BmsTip;

/**
 * @author bing  @create 2021/3/1-9:04 下午
 */
public interface IBmsTipService extends IService<BmsTip> {
    BmsTip getRandomTip();
}

