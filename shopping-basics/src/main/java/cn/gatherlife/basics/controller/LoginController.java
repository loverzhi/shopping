package cn.gatherlife.basics.controller;

import cn.gatherlife.basics.model.User;
import cn.gatherlife.basics.model.UserToken;
import cn.gatherlife.basics.service.UserService;
import cn.gatherlife.basics.service.UserTokenService;
import cn.gatherlife.basics.utils.PasswordUtils;
import cn.gatherlife.basics.utils.ShiroUtils;
import cn.gatherlife.basics.vo.LoginBean;
import cn.gatherlife.common.http.HttpResult;
import cn.gatherlife.common.utils.IOUtils;
import cn.gatherlife.core.utils.Constant;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @Author chy
 * @Description 登录控制器
 */
@RestController
public class LoginController {

    @Autowired
    private Producer producer;

    @Autowired
    private UserService userService;

    @Autowired
    private UserTokenService userTokenService;

    @GetMapping("captcha.jpg")
    public void captcha(HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-Control","no-store,no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        // 生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到验证码搭配session
        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY,text);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image,"jpg",out);
        IOUtils.closeQuietly(out);
    }

    /**
     * 登录接口
     */
    @PostMapping(value = "/login")
    public HttpResult login(@RequestBody LoginBean loginBean) throws  IOException {
        String userName = loginBean.getAccount();
        String password = loginBean.getPassword();
        String captcha = loginBean.getCaptcha();

        // 用户信息
        User user = userService.findByUsername(userName);
        // 账号不存在、密码错误
        if (user == null) {
            return HttpResult.error("账号不存在");
        }

        if (!match(user, password)) {
            return HttpResult.error("密码不正确");
        }

        // 账号锁定
        if (user.getStatus() == 0) {
            return HttpResult.error("账号已被锁定,请联系管理员");
        }

        // 生成token，并保存到数据库
        UserToken data = userTokenService.createToken(user.getId());
        return HttpResult.ok(data);
    }

    /**
     * 验证用户密码
     * @param user
     * @param password
     * @return
     */
    public boolean match(User user, String password) {
        return user.getPassword().equals(PasswordUtils.encrypte(password, user.getSalt()));
    }

    /**
     * 登出接口
     */
    @GetMapping(value = "/logout")
    public HttpResult logout(){
        ShiroUtils.logout();
        return HttpResult.ok();
    }

    public static void main(String args[]){
        System.out.print(PasswordUtils.encrypte("123456", "YzcmCZNvbXocrsz9dm8e"));
        //bd1718f058d8a02468134432b8656a86
        //cdac762d0ba79875489f6a8b430fa8b5dfe0cdd81da38b80f02f33328af7fd4a
    }
}
