package com.hh.jushuitan.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author gx
 * @version 1.0.0
 * @since 6/26/23
 **/
@Data
@TableName("demo")
public class Demo {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
