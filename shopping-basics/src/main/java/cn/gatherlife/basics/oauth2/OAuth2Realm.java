package cn.gatherlife.basics.oauth2;

import cn.gatherlife.basics.model.User;
import cn.gatherlife.basics.model.UserToken;
import cn.gatherlife.basics.service.UserService;
import cn.gatherlife.basics.service.UserTokenService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * @Author chy
 * @Description
 */
public class OAuth2Realm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    @Autowired
    UserTokenService userTokenService;

    @Override
    public boolean supports(AuthenticationToken token){
        return token instanceof OAuth2Token;
    }

    /**
     * 授权（接口保护  验证接口调用权限是调用）
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User)principals.getPrimaryPrincipal();
        //用户权限列表  根据用户拥有的权限标识 与 如@permission标注的接口对比，决定是否可以调用接口
        Set<String> permsSet = userService.findPermissions(user.getUsername());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证  （登录时调用）
     * @Param [token]
     * @return org.apache.shiro.authc.AuthenticationInfo
     **/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String)authenticationToken.getPrincipal();
        //根据accessToken 查询用户token信息
        UserToken userToken = userTokenService.findByToken(token);
        if(userToken == null || userToken.getExpireTime().getTime() < System.currentTimeMillis()){
            //token已失效
            throw new IncorrectCredentialsException("token失效，请重新登录");
        }

        //查询用户信息
        User user = userService.findByTokenUserId(userToken.getUserId());
        //账号被锁定
        if(user.getStatus() == 0){
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,token,user.getUsername());
        return info;
    }
}
