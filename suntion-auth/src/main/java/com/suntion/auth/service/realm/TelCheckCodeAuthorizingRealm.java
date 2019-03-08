package com.suntion.auth.service.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import suntion.auth.service.AuthService;

/**
 * 手机号验证码登陆
 */
public class TelCheckCodeAuthorizingRealm extends AuthorizingRealm {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Lazy
    private AuthService authService;

    @Override
    public boolean supports(AuthenticationToken token) {
        /**
         * 仅支持StatelessToken 类型的Token，
         * 那么如果在StatelessAuthcFilter类中返回的是UsernamePasswordToken，那么将会报如下错误信息：
         * Please ensure that the appropriate Realm implementation is configured correctly or
         * that the realm accepts AuthenticationTokens of this type.JwtAccessControlFilterFilter.isAccessAllowed()
         */
        return token instanceof TelCheckCodeAuthenticationToken;
    }

    /**
     * 认证方法
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException  {
        logger.info("TelCheckCode Relams");
        TelCheckCodeAuthenticationToken authenticationToken = (TelCheckCodeAuthenticationToken) token;
        String tel = (String) authenticationToken.getPrincipal();
        String checkCode = (String) authenticationToken.getCredentials();
        String type = authenticationToken.getType();

        if("13629711009".equals(tel) && "123456".equals(checkCode)){
            //直接通过认证
            return new SimpleAuthenticationInfo(tel, authenticationToken.getCredentials(), getName());
        } else {
            throw new UnknownAccountException("授权认证失败，请重新登录！");
        }
    }

    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //根据用户名查找角色，请根据需求实现
        String username = (String) principals.getPrimaryPrincipal();
//        SysUser sysUser = (SysUser) CacheUtil.get(SystemCacheUtil.CACHE_USER, SystemCacheUtil.SYS_USER_USERNAME + username);
//
//        List<SysResource> resourcesList = this.resourceService.listResourceByUserId(sysUser.getId());
//        // 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        if(resourcesList != null){
//            for(SysResource resource: resourcesList){
//                if (StringUtil.isNotBlank(resource.getPermission())){
//                    //添加基于Permission的权限信息
//                    for (String permission : StringUtil.split(resource.getPermission(),",")){
//                        info.addStringPermission(permission);
//                    }
//                }
//            }
//        }

        return info;
    }
}
