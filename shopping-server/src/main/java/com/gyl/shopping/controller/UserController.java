package com.gyl.shopping.controller;

import com.gyl.shopping.api.UserService;
import com.gyl.shopping.common.*;
import com.gyl.shopping.dto.User;
import com.gyl.shopping.filter.UserFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;


@RestController
//@RequestMapping("/user")
public class UserController {

    @Autowired
    private JedisPool jedisPool;

    @Resource
    private JavaMailSender javaMailSender;

    @Resource
    private UserService userService;

    @PostMapping("/sendEmail")
    public ResultResponse sendEmail(
            @RequestParam("userName") String userName,
            @RequestParam("emailAddress") String emailAddress){

        if(StringUtils.isEmpty(userName)){
            throw new MallException(ExceptionEnum.USERNAME_NOT_NULL);
        }
        if(StringUtils.isEmpty(emailAddress)){
            throw new MallException(ExceptionEnum.EMAIL_NOT_NULL);
        }
        if(!EmailUtils.checkEmailAddress(emailAddress)){
            throw new MallException(ExceptionEnum.EMAIL_NOT_CORRECT);
        }
        String code = "";
        try {
            code = EmailUtils.generateCode();
            Jedis jedis = jedisPool.getResource();
            jedis.select(0);
            jedis.set(userName, code);
            jedis.expire(userName, 300);
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(Constants.EMAIL_FROM);
            simpleMailMessage.setSubject("登陆验证码");
            simpleMailMessage.setText("登陆验证码为:" + code + "，有效期为5分钟。");
            simpleMailMessage.setTo(emailAddress);
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MallException(ExceptionEnum.EMAIL_SEND_ERROR);
        }

        return ResultResponse.success().put(code);
    }

    @PostMapping("/register")
    public ResultResponse register(
            @RequestParam("userName") String userName,
            @RequestParam("password") String password,
            @RequestParam("emailAddress") String emailAddress,
            @RequestParam("verificationCode") String verificationCode){

        if(StringUtils.isEmpty(userName)){
            throw new MallException(ExceptionEnum.USERNAME_NOT_NULL);
        }
        if(StringUtils.isEmpty(password)){
            throw new MallException(ExceptionEnum.PASSWORD_NOT_NULL);
        }
        if(StringUtils.isEmpty(emailAddress)){
            throw new MallException(ExceptionEnum.EMAIL_NOT_NULL);
        }
        if(!EmailUtils.checkEmailAddress(emailAddress)){
            throw new MallException(ExceptionEnum.EMAIL_NOT_CORRECT);
        }
        if(StringUtils.isEmpty(verificationCode)){
            throw new MallException(ExceptionEnum.EMAILCODE_NOT_NULL);
        }
        String code = "";
        try {
            Jedis jedis = jedisPool.getResource();
            jedis.select(0);
            code = jedis.get(userName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtils.isEmpty(code)){
            throw new MallException(ExceptionEnum.GET_CODE_AGAIN);
        }
        if (!code.equals(verificationCode)){
            throw new MallException(ExceptionEnum.CODE_ERROR);
        }

        userService.createUser(userName, password, emailAddress, UserRule.NORMAL.getCode());

        return ResultResponse.success().put("用户创建成功");
    }

    @PostMapping("/admin/register")
    public ResultResponse adminRegister(
            @RequestParam("userName") String userName,
            @RequestParam("password") String password,
            @RequestParam("emailAddress") String emailAddress,
            @RequestParam("verificationCode") String verificationCode){

        if(StringUtils.isEmpty(userName)){
            throw new MallException(ExceptionEnum.USERNAME_NOT_NULL);
        }
        if(StringUtils.isEmpty(password)){
            throw new MallException(ExceptionEnum.PASSWORD_NOT_NULL);
        }
        if(StringUtils.isEmpty(emailAddress)){
            throw new MallException(ExceptionEnum.EMAIL_NOT_NULL);
        }
        if(!EmailUtils.checkEmailAddress(emailAddress)){
            throw new MallException(ExceptionEnum.EMAIL_NOT_CORRECT);
        }
        if(StringUtils.isEmpty(verificationCode)){
            throw new MallException(ExceptionEnum.EMAILCODE_NOT_NULL);
        }
        String code = "";
        try {
            Jedis jedis = jedisPool.getResource();
            jedis.select(0);
            code = jedis.get(userName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtils.isEmpty(code)){
            throw new MallException(ExceptionEnum.GET_CODE_AGAIN);
        }
        if (!code.equals(verificationCode)){
            throw new MallException(ExceptionEnum.CODE_ERROR);
        }

        userService.createUser(userName, password, emailAddress, UserRule.ADMIN.getCode());

        return ResultResponse.success().put("管理员创建成功");
    }

    @GetMapping("/login")
    public ResultResponse login(
            @RequestParam("userName") String userName,
            @RequestParam("password") String password,
            HttpSession session) throws Exception {
        if (StringUtils.isEmpty(userName)){
            throw new MallException(ExceptionEnum.USERNAME_NOT_NULL);
        }
        if(StringUtils.isEmpty(password)){
            throw new MallException(ExceptionEnum.PASSWORD_NOT_NULL);
        }
        User user = userService.searchUser(userName);
        if (user == null){
            throw new MallException(ExceptionEnum.NOT_USER);
        }
        if(!Md5Utils.getMd5Str(password).equals(user.getPassword())){
            throw new MallException(ExceptionEnum.PASSWORD_ERROR);
        }
        session.setAttribute("user", user);
        String s = TokenUtil.generateToken(user.getId(), userName, user.getRole(), user.getEmailAddress());
        return ResultResponse.success().put(s);
    }

    @PostMapping("/admin/login")
    public ResultResponse adminLogin(@RequestParam("userName") String userName,
                                     @RequestParam("password") String password,
                                     HttpSession session) throws Exception {
        if (StringUtils.isEmpty(userName)){
            throw new MallException(ExceptionEnum.USERNAME_NOT_NULL);
        }
        if(StringUtils.isEmpty(password)){
            throw new MallException(ExceptionEnum.PASSWORD_NOT_NULL);
        }
        User user = userService.searchUser(userName);
        if (user == null){
            throw new MallException(ExceptionEnum.NOT_USER);
        }
        if(!Md5Utils.getMd5Str(password).equals(user.getPassword())){
            throw new MallException(ExceptionEnum.PASSWORD_ERROR);
        }
        session.setAttribute("user", user);
        String s = TokenUtil.generateToken(user.getId(), userName, user.getRole(), user.getEmailAddress());
        return ResultResponse.success().put(s);
    }

    @PostMapping("/user/update")
    public ResultResponse updateUser(@RequestParam("signature") String signature){
        User user = UserFilter.currentUser.get();
        userService.updateSignature(signature, user.getId());
        return ResultResponse.success().put("更新成功!");
    }

    @PostMapping("/user/logout")
    public ResultResponse logout(){
        return ResultResponse.success().put("退出登陆！");
    }

}
