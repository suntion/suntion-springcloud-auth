package com.suntion.core.jwt;

import com.suntion.common.constants.AuthConstants;
import com.suntion.core.exception.SuntionException;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JwtAuthorizingRealm extends AuthorizingRealm {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Autowired
//    @Lazy
//    private AuthService authService;

    @Override
    public boolean supports(AuthenticationToken token) {
        /**
         * 仅支持StatelessToken 类型的Token，
         * 那么如果在StatelessAuthcFilter类中返回的是UsernamePasswordToken，那么将会报如下错误信息：
         * Please ensure that the appropriate Realm implementation is configured correctly or
         * that the realm accepts AuthenticationTokens of this type.JwtAccessControlFilterFilter.isAccessAllowed()
         */
        return token instanceof JwtAuthenticationToken;
    }

    /**
     * 认证方法
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws RuntimeException {
        logger.info("Jwt Relam");
        String statelessToken = (String) token.getCredentials();
        Claims claims = JwtTokenUtil.parseToken(((JwtAuthenticationToken) token).getJwtToken(), AuthConstants.SECRETKET);
        if(claims != null){
            //验证用户是否在缓存中，如果不存在则添加到缓存
//            AuthUser authUser = this.authService.getUserByAccount(claims.getSubject());
//            if(authUser == null){
//                throw new AuthenticationException("用户不存在或已被删除！");
//            }
            //直接通过认证
            return new SimpleAuthenticationInfo(claims.getSubject(), ((JwtAuthenticationToken) token).getJwtToken(), getName());
        } else {
            throw new SuntionException("授权认证失败，请重新登录！");
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
