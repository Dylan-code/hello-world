package com.marx.handler;

import com.wobangkj.api.Response;
import com.wobangkj.handler.AbstractExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestControllerAdvice
public class ExceptionHandler extends AbstractExceptionHandler {

    @Override
    @org.springframework.web.bind.annotation.ExceptionHandler({Throwable.class})
    public Object handler(Throwable e, HttpServletRequest request, HttpServletResponse response) {
        log.error(e.getMessage(), e);
//        return Response.err();
        if (e instanceof NumberFormatException) {
            return Response.fail("数字格式异常");
        }
        return null;
    }
}
