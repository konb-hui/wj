package com.konb.wj.exception;

import com.konb.wj.pojo.result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;

/**
 * @author konb
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(WjException.class)
    @ResponseBody
    public R error(WjException e) {
        log.error(ExceptionUtil.getMessage(e));
        e.printStackTrace();
        return R.error().message(e.getMessage());
    }

    @ExceptionHandler(ParseException.class)
    @ResponseBody
    public R error(ParseException e) {
        log.error(ExceptionUtil.getMessage(e));
        e.printStackTrace();
        return R.error().code(300).message("日期解析失败");
    }


}
