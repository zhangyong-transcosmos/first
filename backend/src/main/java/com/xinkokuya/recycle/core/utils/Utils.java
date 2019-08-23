package com.xinkokuya.recycle.core.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

import com.xinkokuya.recycle.core.Result;
import com.xinkokuya.recycle.core.auth.JwtUser;

public final class Utils {

    private static ResourceLoader resourceLoader = new FileSystemResourceLoader();

    private Utils() {
    }

    /**
     * 获取当前登录用户
     * 
     * @return JwtUser / null
     */
    public static JwtUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null)
            return null;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return (JwtUser) principal;
        }
        return null;
    }

    /**
     * 获取当前登录用户的用户名
     * 
     * @return String
     */
    public static String getCurrentUsername() {
        JwtUser user = getCurrentUser();
        if (user == null) {
            return "";
        }
        return user.getUsername();
    }

    /**
     * 获取当前登录用户的用户ID
     * 
     * @return String
     */
    public static String getCurrentUserId() {
        JwtUser user = getCurrentUser();
        if (user == null) {
            return "";
        }
        return user.getId();
    }

    /**
     * 格式化路径 /abc/def/{id}/
     * 
     * @param path
     * @return
     */
    public static String formatPath(String path) {
        if (StringUtils.isEmpty(path))
            return path;
        if (!path.startsWith("/"))
            path = "/" + path;
        return path;
    }
    
    /**
     * 输出结果
     * @param response
     * @param result
     * @throws IOException 
     */
    public static void printResponse(HttpServletResponse response, Result result) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader("Content-type", MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
        response.getWriter().print(JSON.toJSONString(result));
        response.getWriter().close();
    }

    /**
     * base64编码字符串
     * 
     * @param path
     * @param ext;
     *            png,jpeg,gif,html,css,javascript, xls, xlsx
     * @return
     * @throws IOException
     */
    public static String base64(String path, String ext) throws IOException {
        byte[] bData = IOUtils.toByteArray(resourceLoader.getResource("file:" + path).getInputStream());
        return base64(bData, ext);
    }

    /**
     * base64编码字符串
     * 
     * @param bData
     * @param ext;
     *            png,jpeg,gif,html,css,javascript, xls, xlsx
     * @return
     */
    public static String base64(byte[] bData, String ext) {
        byte[] base64Data = Base64.getEncoder().encode(bData);
        StringBuffer base64Str = new StringBuffer();
        switch (ext.toLowerCase()) {
        case "png":
            base64Str.append("data:image/png;base64,");
            break;
        case "jpg":
            base64Str.append("data:image/jpeg;base64,");
            break;
        case "gif":
            base64Str.append("data:image/gif;base64,");
            break;
        case "html":
            base64Str.append("data:text/html;base64,");
            break;
        case "css":
            base64Str.append("data:text/css;base64,");
            break;
        case "javascript":
            base64Str.append("data:text/javascript;base64,");
            break;
        case "xlsx":
        case "xls":
            base64Str.append("data:application/vnd.ms-excel;base64,");
            break;
        }
        base64Str.append(new String(base64Data));
        return base64Str.toString();
    }
}