package net.xdclass.controller;


import io.swagger.annotations.Api;
import net.xdclass.util.JsonData;
import net.xdclass.util.JwtUtil;
import net.xdclass.util.ThreadLocalUtil;
import org.intellij.lang.annotations.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Api("用户模块")
@RestController
@RequestMapping("/api/user/v1/")
public class UserController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/login")
    public JsonData login(@Pattern(value = "^\\S{5,16}$") String username,
                          @Pattern(value = "^\\S{5,16}$") String password) {

        //根据用户名查询是否由此用户
        //判断密码是否正确
        HashMap<String, Object> claims = new HashMap<>();
        //逻辑代码 TODO
        claims.put("id", username);
        claims.put("username", username);
        String token = JwtUtil.genToken(claims);
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set(token,token,1, TimeUnit.HOURS);
        return JsonData.buildSuccess(token);
    }

    @PatchMapping("/updatePwd")
    public JsonData updatePwd(@RequestBody Map<String, String> params, @RequestHeader("Authorization") String token) {
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return JsonData.buildError("缺少必要的参数");
        }

        //根据用户名查询密码
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        //原密码和填写密码是否一直
        if (!rePwd.equals(newPwd)) {
            return JsonData.buildError("两次填写新密码不一样");
        }
        //更新结束把redis中token删除
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        //更新密码
        return JsonData.buildSuccess();
    }
}
