package cn.gathub.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取要发送的图片数据
        byte[] png = createPng();
        resp.setContentType("image/png");
        resp.setContentLength(png.length);
        //将png图片写入body
        resp.getOutputStream().write(png);
        resp.getOutputStream().close();
    }

    //动态生成图片数据
    public byte[] createPng() throws IOException {
        //1. 创建图片对象
        BufferedImage img = new BufferedImage(200, 100, BufferedImage.TYPE_3BYTE_BGR);
        img.setRGB(100, 50, 0xffffff);
        //2. 将图片编码为 png
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(img, "png", out);
        out.close();
        //3. 得到所有的bytes
        byte[] ary = out.toByteArray();
        return ary;
    }
}










