package com.tsinghua.course.Biz.Processor;

import com.tsinghua.course.Base.Constant.KeyConstant;
import com.tsinghua.course.Base.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import com.tsinghua.course.Frame.Util.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @描述 用户原子处理器，所有与用户相关的原子操作都在此处理器中执行
 **/
@Component
public class UserProcessor {
    @Autowired
    MongoTemplate mongoTemplate;

    /** 根据用户名从数据库中获取用户 */
    public User getUserByUsername(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.USERNAME).is(username));
        return mongoTemplate.findOne(query, User.class);
    }

    public boolean checkPhoneNumber(String phoneNumber) {
        int len=phoneNumber.length();
        if(len!=11)
            return false;
        for(int z=0;z<len;z++){
            char ch=phoneNumber.charAt(z);
            if(ch>'9'||ch<'0'){
                return false;
            }
        }
        return true;
    }

    public User getUserByPhoneNumber(String phoneNumber) {
        Query query = new Query();
        query.addCriteria(Criteria.where(KeyConstant.PHONE_NUMBER).is(phoneNumber));
        return mongoTemplate.findOne(query, User.class);
    }

    public void CreateNewUser(String phoneNumber) {
        User newUser= new User(phoneNumber);
    }

    public void sendCode() {
        /**
         *  发送短信的代码
         */
        //TODO:添加Mob的相关API。
        /**
         *暂时使用比较幼崽的方式测试
         */
        int len=4;
        String random_str=MathUtil.randomString(len);
        try{
            ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream("mailsnotworking.lyx"));
            out.writeObject(random_str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
