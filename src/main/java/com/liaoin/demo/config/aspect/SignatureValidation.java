package com.liaoin.demo.config.aspect;

import com.liaoin.demo.common.ResultCode;
import com.liaoin.demo.exception.CustomException;
import com.liaoin.demo.util.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author mc
 * Create date 2020/12/10 12:03
 * Version 1.0
 * Description
 */
@Slf4j
@Aspect
@Component
public class SignatureValidation {
    /**
     * 时间戳请求最小限制(30s)
     * 设置的越小，安全系数越高，但是要注意一定的容错性
     */
    private static final long MAX_REQUEST = 30 * 1000L;
    /**
     * 秘钥
     */
    private static Map<String, String> APP_INFO = new HashMap<>();

    static {
        APP_INFO.put("6749e0e254f74492", "ab2cd45613a6085b49521974e83be9e7");
    }


    /**
     * 验签切点(完整的找到设置的文件地址)
     */
    @Pointcut("execution(@com.liaoin.demo.annotation.SignatureValidation * *(..))")
    private void verifyUserKey() {
    }

    /**
     * 开始验签
     */
    @Before("verifyUserKey()")
    public void doBasicProfiling() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String sign = request.getHeader("sign");
        String appId = request.getHeader("appId");
        String timestamp = request.getHeader("timestamp");
        try {
            //通过appId查询
            if (StringUtils.isEmpty(appId) || !APP_INFO.containsKey(appId)) {
                throw new CustomException(ResultCode.PERMISSION_NO_AUTOGRAPH.getCode(), ResultCode.PERMISSION_NO_AUTOGRAPH.getMsg());
            }
            Boolean check = checkToken(APP_INFO.get(appId), sign, timestamp);
            if (!check) {
                throw new CustomException(ResultCode.PERMISSION_OUT_TIME.getCode(), ResultCode.PERMISSION_OUT_TIME.getMsg());
            }
        } catch (Throwable throwable) {
            throw new CustomException(ResultCode.PERMISSION_NO_AUTOGRAPH.getCode(), ResultCode.PERMISSION_NO_AUTOGRAPH.getMsg());
        }
    }

    /**
     * 校验token
     *
     * @param sign      签名
     * @param timestamp 时间戳
     * @return 校验结果
     */
    private Boolean checkToken(String secret, String sign, String timestamp) {
        if (StringUtils.isAnyBlank(sign, timestamp)) {
            return false;
        }
        long now = System.currentTimeMillis();
        long time = Long.parseLong(timestamp);
        if (now - time > MAX_REQUEST) {
            log.error("时间戳已过期[{}][{}][{}]", now, time, (now - time));
            return false;
        }
        String crypt = MD5Utils.getMD5(secret + timestamp);
        return StringUtils.equals(crypt, sign);
    }

}
