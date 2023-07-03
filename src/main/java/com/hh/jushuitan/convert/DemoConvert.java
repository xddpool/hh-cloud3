package com.hh.jushuitan.convert;

import com.hh.jushuitan.entity.Demo;
import com.hh.jushuitan.param.DemoParam;
import com.hh.jushuitan.vo.DemoVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author gx
 * @version 1.0.0
 * @since 6/26/23
 **/
@Mapper(componentModel = "spring")
public interface DemoConvert {
    DemoConvert INSTANCE = Mappers.getMapper(DemoConvert.class);

    DemoVO toDemoVO(Demo demo);

    Demo toDemo(DemoParam demoParam);
}
