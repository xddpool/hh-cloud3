package com.hh.jushuitan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hh.jushuitan.convert.DemoConvert;
import com.hh.jushuitan.entity.Demo;
import com.hh.jushuitan.mapper.DemoMapper;
import com.hh.jushuitan.param.DemoParam;
import com.hh.jushuitan.service.DemoService;
import org.springframework.stereotype.Service;

/**
 * @author gx
 * @version 1.0.0
 * @since 6/26/23
 **/
@Service
public class DemoServiceImpl extends ServiceImpl<DemoMapper, Demo> implements DemoService {

    @Override
    public void saveDemo(DemoParam demoParam) {
        Demo demo = DemoConvert.INSTANCE.toDemo(demoParam);
        save(demo);
    }
}
