package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CJ on 2017/11/16.
 */
@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService iUserService;

    /**
     *用户登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "login.do")
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse<User> serverResponse = iUserService.login(username,password);
        if (serverResponse.isSuccess()){
            session.setAttribute(Const.CURRENT_USER, serverResponse.getData());
        }
        return serverResponse;
    }

    @RequestMapping(value = "goLogin.do")
    public String goLogin(){
        return "portal/login";
    }



    @RequestMapping(value = "loginTest.do")
    public String loginTest(String username, String password, Model model){
        ServerResponse<User> serverResponse = iUserService.login(username,password);
        if (serverResponse.isSuccess()){
            //session.setAttribute(Const.CURRENT_USER, serverResponse.getData());
            model.addAttribute(Const.CURRENT_USER, serverResponse.getData());
        }
        return "index";
    }

    @RequestMapping(value = "logout.do")
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }
    @RequestMapping(value = "register.do")
    @ResponseBody
    public ServerResponse<String> register(User user){
        return iUserService.register(user);
    }

    @RequestMapping(value = "check_valid.do")
    @ResponseBody
    public ServerResponse<String> checkValid(String str, String type){
        return iUserService.checkValid(str, type);
    }

    @RequestMapping(value = "get_userInfo.do")
    @ResponseBody
    public ModelAndView getUserInfo(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        Map<String,Object> map = new HashMap<String, Object>();
        ServerResponse response;
        if (user == null){
            response = ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
            map.put("res",response);
            return new ModelAndView("portal/login",map);
        }
        response = ServerResponse.createBySuccess(user);
        map.put("res",response);
        return new ModelAndView("portal/user-center",map);
    }
    @RequestMapping(value = "user-info-update.do")
    @ResponseBody
    public ModelAndView updateUserInfo(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        Map<String,Object> map = new HashMap<String, Object>();
        ServerResponse response;
        if (user == null){
            response = ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
            map.put("res",response);
            return new ModelAndView("portal/login",map);
        }
        response = ServerResponse.createBySuccess(user);
        map.put("res",response);
        return new ModelAndView("portal/user-info-update",map);
    }

    @RequestMapping(value = "forget_get_question.do")
    @ResponseBody
    public ServerResponse<String> forgetGetQuestion(String username){
        return iUserService.forgetGetQuestion(username);
    }

    @RequestMapping(value = "forget_check_answer.do")
    @ResponseBody
    public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer){
        return iUserService.forgetCheckAnswer(username,question,answer);
    }

    @RequestMapping(value = "forget_reset_password.do")
    @ResponseBody
    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken){
        return iUserService.forgetResetPassword(username,passwordNew,forgetToken);
    }

    @RequestMapping(value = "reset_password.do")
    @ResponseBody
    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return ServerResponse.createByErrorMessage("登录信息获取失败");
        }
        return iUserService.resetPassword(passwordOld,passwordNew,user);
    }

    @RequestMapping(value = "pass-update.do")
    @ResponseBody
    public ModelAndView passUpdate(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        Map<String,Object> map = new HashMap<String, Object>();
        ServerResponse response;
        if(user == null) {
            response = ServerResponse.createByErrorMessage("登录信息获取失败");
            map.put("res",response);
            return new ModelAndView("portal/login",map);
        }
        return new ModelAndView("portal/pass-update",map);
    }



    @RequestMapping(value = "update_information.do")
    @ResponseBody
    public ServerResponse<User> updateInformation(HttpSession session, User user){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("登录信息获取失败");
        }
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> response = iUserService.updateInformation(user);
        return response;
    }

}
