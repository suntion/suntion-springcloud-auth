package com.suntion.common.constants;

public class AuthConstants extends BaseConstants{
    //用于验证使用：Header中的名字
    public static  final String DEFAULT_TOKEN_NAME = "Authorization";

    //私有秘钥secretKey（可以对其base64加密）
    public static final String SECRETKET = "eHh4eGJiYmNjY2RkZGVlZWZmZmRkZA==";

    //12小时
    public static Long TTLMILLS = 43200000L;

}
