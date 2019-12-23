package com.riemann.springbootdemo.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author riemann
 * @date 2019/12/19 23:08
 */
@NoArgsConstructor
@Data
@Getter
@Setter
public class EasyExcelData {

    @ExcelProperty(value = "name", index = 0)
    private String name;

    @ExcelProperty(value = "sex", index = 1)
    private String sex;

    @ExcelProperty(value = "age", index = 2)
    private Integer age;

    @ExcelProperty(value = "address", index = 3)
    private String address;

    @ExcelProperty(value = "phone", index = 4)
    private String phone;

}
