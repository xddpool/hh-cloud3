package com.hh.jushuitan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hh.jushuitan.entity.Demo;
import com.hh.jushuitan.param.DemoParam;

/**
 * @author gx
 * @version 1.0.0
 * @since 6/26/23
 **/
public interface DemoService extends IService<Demo> {
    void saveDemo(DemoParam demoParam);
}
