package com.tsinghua.course.Biz.Controller.Params.UserParams.Out;

import com.tsinghua.course.Biz.Controller.Params.CommonOutParams;

public class SendCodeOutParams extends CommonOutParams {
    private String verificationCode;

    public SendCodeOutParams(String code) {
        this.verificationCode=code;
    }

    public void setVerificationCode(String verificationCode){
        this.verificationCode=verificationCode;
    }
}
