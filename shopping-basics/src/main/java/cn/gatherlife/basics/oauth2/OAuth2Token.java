package cn.gatherlife.basics.oauth2;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Author chy
 * @Description 自定义 token对象
 */
public class OAuth2Token implements AuthenticationToken {

    private static final long serialVersionUID = 1L;

    private String token;

    public OAuth2Token(String token){
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
