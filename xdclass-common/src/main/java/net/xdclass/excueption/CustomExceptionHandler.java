package net.xdclass.excueption;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;
import net.xdclass.util.JsonData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {
    @RequestBody
    @ExceptionHandler
    public JsonData handler(Exception e) {
        if (e instanceof BizException) {
            BizException bizException = (BizException) e;
            log.error("[业务异常{}]", e);
            return JsonData.buildCodeAndMsg(bizException.getCode(), bizException.getMsg());
        }
        log.error("[非业务异常{}]", e);
        return JsonData.buildError("全局异常, 未知错误");
    }
}
