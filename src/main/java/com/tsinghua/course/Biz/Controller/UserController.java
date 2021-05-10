package com.tsinghua.course.Biz.Controller;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Biz.BizTypeEnum;
import com.tsinghua.course.Base.Error.CourseWarn;
import com.tsinghua.course.Base.Error.UserWarnEnum;
import com.tsinghua.course.Base.Model.User;
import com.tsinghua.course.Biz.Controller.Params.CommonOutParams;
import com.tsinghua.course.Biz.Controller.Params.UserParams.In.LoginInParams;
import com.tsinghua.course.Biz.Controller.Params.UserParams.In.MyLoginInParams;
import com.tsinghua.course.Biz.Controller.Params.UserParams.In.SendCodeInParams;
import com.tsinghua.course.Biz.Processor.UserProcessor;
import com.tsinghua.course.Frame.Util.*;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @描述 用户控制器，用于执行用户相关的业务
 **/
@Component
public class UserController {

    @Autowired
    UserProcessor userProcessor;

    /** 用户登录业务 */
    @BizType(BizTypeEnum.USER_LOGIN)
    public CommonOutParams userLogin(LoginInParams inParams) throws Exception {
        String username = inParams.getUsername();
        if (username == null)
            throw new CourseWarn(UserWarnEnum.LOGIN_FAILED);
        User user = userProcessor.getUserByUsername(username);
        if (user == null || !user.getPassword().equals(inParams.getPassword()))
            throw new CourseWarn(UserWarnEnum.LOGIN_FAILED);

        /** 登录成功，记录登录状态 */
        ChannelHandlerContext ctx =  ThreadUtil.getCtx();
        /** ctx不为空记录WebSocket状态，否则记录http状态 */
        if (ctx != null)
            SocketUtil.setUserSocket(username, ctx);
        else {
            HttpSession httpSession = ThreadUtil.getHttpSession();
            if (httpSession != null) {
                httpSession.setUsername(username);
            }
        }
        return new CommonOutParams(true);
    }

    @BizType(BizTypeEnum.USER_SENDCODE)
    public CommonOutParams userSendCode(SendCodeInParams inParams) throws Exception{
        String phoneNumber=inParams.getPhoneNumber();
        if (!userProcessor.checkPhoneNumber(phoneNumber))
            throw new CourseWarn(UserWarnEnum.SENDCODE_FAILED_PNUMBER_INVALID);
        userProcessor.sendCode();
        return new CommonOutParams(true);
    }

    @BizType(BizTypeEnum.USER_MYLOGIN)
    public CommonOutParams userMyLogin(MyLoginInParams inParams) throws Exception{
        String phoneNumber=inParams.getPhoneNumber();
        String verificationCode=inParams.getVerificationCode();
        if(!userProcessor.checkPhoneNumber(phoneNumber)||!userProcessor.checkVerificationCode(phoneNumber)){
            throw new CourseWarn(UserWarnEnum.MYLOGIN_FAILED_CODE_OR_PNUMBER_INVALID);
        }
        User user = userProcessor.getUserByPhoneNumber(phoneNumber);
        if(user==null){
            userProcessor.CreateNewUser(phoneNumber);
        }
        return new CommonOutParams(true);
    }
}
