package com.riemann.springbootdemo.aop;

import com.riemann.springbootdemo.enums.ExceptionEnum;
import com.riemann.springbootdemo.exception.BusinessException;
import com.riemann.springbootdemo.model.vo.CommonVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class ExceptionHandle {

    private static final String message = "服务器开小差啦,请稍后再试哦！";

    /**
     * Controller层参数校验异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public CommonVO handleBindException(BindException e){
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder errorMsg = new StringBuilder();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        allErrors.forEach(
                error->{
                    errorMsg.append(error.getDefaultMessage()).append(";");
                }
        );
        return CommonVO.error(ExceptionEnum.ARGUMENT_NOT_VALIDATE_EXCEPTION.getCode(),errorMsg.toString());
    }

    /**
     * 处理业务类型异常
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public CommonVO handleBusinessException(BusinessException e){
        CommonVO commonVO = CommonVO.error(e.getCode(),e.getErrorMsg());
        if (e.getTimestamp() != null) {
            commonVO.setTimestamp(e.getTimestamp());
        }
        return commonVO;
    }

    /**
     * 405错误  GET POST 请求不对应异常
     * @param ex
     * @return
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    public CommonVO request405(HttpRequestMethodNotSupportedException ex) {
        log.error("异常 {}", ex.getMessage());
        return CommonVO.error(ExceptionEnum.METHOD_NOT_ALLOWED.getCode(), ex.getMessage());
    }

    @ExceptionHandler({IllegalStateException.class})
    @ResponseBody
    public CommonVO IllegalStateException(Exception ex) {
        log.error("异常 {}", ex.getMessage());
        return CommonVO.error(ExceptionEnum.BUSINESS_EXCEPTION.getCode(),ex.getMessage());
    }

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public CommonVO Exception(Exception ex) {
        log.error("兜底异常", ex);
        return CommonVO.error(ExceptionEnum.INTERNAL_SERVER_ERROR.getCode(),ExceptionEnum.INTERNAL_SERVER_ERROR.getErrorMsg());
    }

}
