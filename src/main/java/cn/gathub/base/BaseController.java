package cn.gathub.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class BaseController {
    protected HttpServletRequest request;

    protected HttpServletResponse response;

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    /**
     * 用于往前台输出:--- 。
     * 1:---常用于Ajax和FreeMaker模版
     * 2:---obj可以是任何对象或String:---如果是文件路径，则用户可以下载此文件
     *
     * @param obj
     * @throws IOException
     */
    protected void toWeb(Object obj) throws IOException {
        HttpServletResponse objResponse = getResponse();
        PrintWriter objPw = objResponse.getWriter();

        objResponse.setCharacterEncoding("utf-8");
        objResponse.setContentType("text/html; charset=utf-8");

        objPw.print(obj);
        objPw.flush();
        objPw.close();
    }

}
