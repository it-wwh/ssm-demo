package cn.gathub.uilts;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class ReturnUtil {
    /**
     * 将boolean型的转换成字符串输出
     *
     * @param response
     * @param fl       true返回 success, false返回error
     * @return true 表示成功输出  false 表示输出失败
     */
    public static boolean writeBoolean(HttpServletResponse response, boolean fl) {
        boolean flag = false;
        String result = "error";
        PrintWriter out = null;
        try {
            out = response.getWriter();
            if (fl)
                result = "success";
            out.write(result);
            out.flush();
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
        return flag;
    }

    /**
     * 将字符串直接输出
     *
     * @param response
     * @param result
     * @return true 表示成功输出   false 表示输出失败
     */
    public static boolean writeString(HttpServletResponse response, String result) {
        boolean flag = false;
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(result);
            out.flush();
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
        return flag;
    }

}
