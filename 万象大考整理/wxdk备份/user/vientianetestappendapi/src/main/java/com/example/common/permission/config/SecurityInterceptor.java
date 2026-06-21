package com.example.common.permission.config;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicResultObject;
import com.example.common.domain.BasicTagState;
import com.example.vientianetest.redis.SessionRedis;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * @author blueriver
 * @description 权限拦截器
 * @date 2017/11/17
 * @since 1.0
 */
public class SecurityInterceptor implements HandlerInterceptor {
    @Autowired
    private SessionRedis sessionRedis;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 验证权限
        if (this.hasPermission(request,handler)) {
            return true;
        }
        // 请求失败
        BasicResultObject obj =new BasicResultObject();
        obj.Tag = BasicTagState.TagSignOut;
        OutputStream outputStream = response.getOutputStream();//获取OutputStream输出流
        response.setHeader("content-type", "text/html;charset=UTF-8");
        byte[] dataByteArr = JSONObject.toJSONString(obj).getBytes("UTF-8");//将字符转换成字节数组，指定以UTF-8编码进行转换
        outputStream.write(dataByteArr);//使用OutputStream流向客户端输出字节数组
        if(outputStream != null ) {
            outputStream.close();
        }
        return false;
    }

    /**
     * 是否有权限
     *
     * @param handler
     * @return
     */
    private boolean hasPermission(HttpServletRequest request,Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 获取方法上的注解
            RequiredPermission requiredPermission = handlerMethod.getMethod().getAnnotation(RequiredPermission.class);
            // 如果方法上的注解为空 则获取类的注解
            if (requiredPermission == null) {
                requiredPermission = handlerMethod.getMethod().getDeclaringClass().getAnnotation(RequiredPermission.class);
            }
            // 如果标记了注解，则判断权限
            if (requiredPermission != null && StringUtils.isNotBlank(requiredPermission.value())) {

                //region TAGSIGNOUT
                if (requiredPermission.value().equals(PermissionConstants.TAGSIGNOUT)) {
                    Cookie[] cookies = request.getCookies();
                    if(cookies == null){
                        return false;
                    }
                    boolean isPermission = false;
                    for(Cookie cookie : cookies){
                        if(cookie.getName().equals("JSESSIONID") && sessionRedis.isLogin(cookie.getValue())){
                            isPermission = true;
                            break;
                        }
                    }
                    return isPermission;
                }
                //endregion
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // TODO
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // TODO
    }
}