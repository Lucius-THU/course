package com.tsinghua.course.Biz.Controller.Params.UserParams.In;

import com.tsinghua.course.Base.Annotation.BizType;
import com.tsinghua.course.Base.Annotation.Required;
import com.tsinghua.course.Biz.BizTypeEnum;
import com.tsinghua.course.Biz.Controller.Params.CommonInParams;

/**
 * @描述 用户登录的入参
 **/
@BizType(BizTypeEnum.USER_MYLOGIN)
public class MyLoginInParams extends CommonInParams {
    // 登录用的密码
    @Required
    private String phoneNumber;

    private String password;

    private String verificationCode;

    public String getPhoneNumber(){ return phoneNumber;}

    public String getPassword(){return password;}

    public String getVerificationCode() {
        return verificationCode;
    }

}
