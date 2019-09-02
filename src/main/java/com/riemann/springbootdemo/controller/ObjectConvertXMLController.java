package com.riemann.springbootdemo.controller;

import com.riemann.springbootdemo.model.ApiResponse;
import com.riemann.springbootdemo.model.ExportExcelData;
import com.riemann.springbootdemo.model.objectConvertXML.Policy;
import com.riemann.springbootdemo.service.impl.EmailTask;
import com.riemann.springbootdemo.util.ConstantsUtil;
import com.riemann.springbootdemo.util.XMLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author riemann
 * @date 2019/08/29 23:05
 */
@RestController
@RequestMapping(value = "/jaxb")
public class ObjectConvertXMLController {

    private static final Logger logger = LoggerFactory.getLogger(ObjectConvertXMLController.class);

    @RequestMapping(value = "/objectToXML", method= RequestMethod.POST)
    public ApiResponse objectToXML(@RequestBody Policy policy) {
        String path = "D:\\Policy.xml";
        logger.info("---将对象转换成File类型的xml Start---");
        String str = XMLUtil.convertToXml(policy, path);
        logger.info(str);
        logger.info("---将对象转换成File类型的xml End---");

        logger.info("---将File类型的xml转换成对象 Start---");
        Policy policyObj = (Policy) XMLUtil.convertXmlFileToObject(Policy.class, path);
        logger.info(policyObj.toString());
        logger.info("---将File类型的xml转换成对象 End---");
        return new ApiResponse(ConstantsUtil.SUCCESS_CODE, null, ConstantsUtil.SUCCESS);
    }
}
