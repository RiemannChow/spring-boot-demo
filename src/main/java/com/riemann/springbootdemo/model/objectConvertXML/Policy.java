package com.riemann.springbootdemo.model.objectConvertXML;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

/**
 * @author riemann
 * @date 2019/08/29 22:10
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Policy") //XML文件中的根标识
@XmlType(propOrder = {
        "productGroup",
        "sn",
        "updateUser",
        "updateLastDate",
}) //控制JAXB 绑定类中属性和字段的排序
public class Policy implements Serializable {

    private static final long serialVersionUID = 1L;

    // 产品族
    private List<ProductGroup> productGroup;

    // sn号
    private String sn;

    // 更新人员
    private String updateUser;

    // 最后更新时间
    private String updateLastDate;

}
