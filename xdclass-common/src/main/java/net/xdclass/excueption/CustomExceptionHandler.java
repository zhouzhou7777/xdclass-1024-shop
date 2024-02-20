package net.xdclass.excueption;

import lombok.extern.slf4j.Slf4j;
import net.xdclass.util.JsonData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ControllerAdvice配合@ExceptionHandler 配合捕捉全局异常
 * instanceof是否属于这个类或者其子类
 */
@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {
    @ResponseBody
    @ExceptionHandler
    public JsonData handler(Exception e) {
        //BizException继承RuntimeException运行时异常 抛出会被捕捉运行异常 运行时异常不受处理
        if (e instanceof BizException) {
            BizException bizException = (BizException) e;
            log.error("[业务异常{}]", e);
            return JsonData.buildCodeAndMsg(bizException.getCode(), bizException.getMsg());
        }
        log.error("[非业务异常{}]", e);
        return JsonData.buildError("全局异常, 未知错误");
    }
}
