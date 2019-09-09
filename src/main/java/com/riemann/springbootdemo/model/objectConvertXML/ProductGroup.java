package com.riemann.springbootdemo.model.objectConvertXML;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * @author riemann
 * @date 2019/08/29 22:14
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ProductGroup")
@XmlType(propOrder = {
        "product",
        "version",
})
public class ProductGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    // 产品
    private String product;

    // 版本
    private String version;

}
