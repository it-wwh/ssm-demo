package com.controller;

import com.beans.UserPojo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/echarts")
public class EChartsController {
    private static final Log logger = LogFactory.getLog(LogFactory.class);


    @RequestMapping(value = "/data", method = {RequestMethod.GET})
    @ResponseBody
    public Object returnData(HttpServletRequest request) {
        List<UserPojo> list = new ArrayList<UserPojo>();

        UserPojo userPojo1 = new UserPojo("1", "小赵", 18);
        UserPojo userPojo2 = new UserPojo("2", "小钱", 20);
        UserPojo userPojo3 = new UserPojo("3", "小孙", 19);
        UserPojo userPojo4 = new UserPojo("4", "小李", 22);
        UserPojo userPojo5 = new UserPojo("5", "小周", 21);
        UserPojo userPojo6 = new UserPojo("6", "小王", 23);

        list.add(userPojo1);
        list.add(userPojo2);
        list.add(userPojo3);
        list.add(userPojo4);
        list.add(userPojo5);
        list.add(userPojo6);

        Map<String, Object> map = new HashMap<String, Object>();

        String[] names = {"小赵", "小钱", "小孙", "小李", "小周", "小王"};
        Integer[] ages = {18, 20, 19, 22, 21, 23};

        map.put("name", names);
        map.put("age", ages);
        map.put("list", list);

        logger.info("请求json数据:" + map);

        return map;
    }
}
