package cn.gathub.controller;

import cn.gathub.beans.UserPojo;
import cn.gathub.service.RepeatedSubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping
public class RepeatedSubmitController {
    @Autowired
    private RepeatedSubmitService repeatedSubmitService;

    @RequestMapping(value = "/saveUserPojo", method = {RequestMethod.GET})
    @ResponseBody
    public Object saveUserPojo() {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        UserPojo userPojo = new UserPojo("1", "程序猿辉辉", 23);
        try {
            repeatedSubmitService.insert(userPojo);
        } catch (DuplicateKeyException e) {
            resultMap.put("code", "400");
            resultMap.put("message", "请勿重复提交！！！");
        }

        resultMap.put("code", "200");
        resultMap.put("message", "保存成功");

        return resultMap;
    }
}
