package com.controller;

import com.base.BaseController;

import com.beans.UserPojo;
import com.uilts.ExcelUtils;
import com.uilts.GetProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

@Controller
@RequestMapping("/file")
public class FileAction extends BaseController {

    private static final Log logger = LogFactory.getLog(FileAction.class);
    private static Map<String, Object> map = new HashMap<String, Object>();

    /**
     * 文件上传
     */
    @RequestMapping("fileUpload")
    @ResponseBody
    public Map<String, Object> fileUpload(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        long startTime = System.currentTimeMillis();

        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        //检查form中是否有enctype="multipart/form-data"
        if (multipartResolver.isMultipart(request)) {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //获取multiRequest 中所有的文件名
            Iterator iter = multiRequest.getFileNames();

            while (iter.hasNext()) {
                //一次遍历所有文件
                MultipartFile multipartFile = multiRequest.getFile(iter.next().toString());
                if (multipartFile != null) {
                    String pathString = request.getSession().getServletContext().getRealPath("/");
                    String basePath = "static/resource/upload/";
                    // 转换为file
                    String path = pathString + basePath + multipartFile.getOriginalFilename();
                    //上传
                    try {
                        File file = new File(path);
                        if (!file.exists()) {
                            // 先得到文件的上级目录，并创建上级目录，在创建文件
                            file.getParentFile().mkdirs();
                            file.createNewFile();
                        }
                        multipartFile.transferTo(file);
                    } catch (IOException e) {
                        resultMap.put("flag", "error");
                        resultMap.put("message", "文件上传失败!");
                        e.printStackTrace();
                    }
                }
            }

            long endTime = System.currentTimeMillis();
            logger.info("文件上传所用时间：" + String.valueOf(endTime - startTime) + "ms");

            resultMap.put("flag", "success");
            resultMap.put("message", "文件上传成功");
        } else {
            resultMap.put("flag", "error");
            resultMap.put("message", "文件上传失败,检查form中是否有enctype=multipart/form-data");
        }

        return resultMap;
    }

    /**
     * 下载excel模板
     *
     * @throws IOException
     */
    @RequestMapping("/downloadExcel")
    public void downloadExcel(HttpServletResponse response) throws IOException {
        // 下载的模板名称
        String fileName = "模板.xlsx";
        response.setContentType("multipart/form-data");
        // 设置Content-Disposition
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
        // 输出流
        OutputStream out = response.getOutputStream();
        // 获取文件的路径
        String filePath = this.getClass().getClassLoader().getResource("/template/" + fileName).getPath();
        InputStream in = new FileInputStream(new File(filePath));
        // 输出文件
        int b;
        while ((b = in.read()) != -1) {
            out.write(b);
        }
        in.close();
        out.close();
    }

    /**
     * 上传并批量插入模板数据
     *
     * @param objFile
     * @throws IOException
     */
    @RequestMapping(value = "/uploadExcel", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadExcel(@RequestParam(value = "objFile") MultipartFile objFile) throws IOException {
        // 判断参数
        if (null == objFile) {
            toWeb("error");
        }

        // 在根目录下创建一个tempfileDir的临时文件夹
        String contextpath = GetProperties.getValue("tmpPath") + "/tempfileDir";
        File f = new File(contextpath);
        if (!f.exists()) {
            f.mkdirs();
        }
        // 在tempfileDir的临时文件夹下创建一个新的和上传文件名一样的文件
        String filename = objFile.getOriginalFilename();

        // 根据系统时间生成上传后保存的文件名
        String filePath = contextpath + "/" + filename;
        File file = new File(contextpath);
        if (!file.exists()) {
            file.mkdirs();
        }
        InputStream ins = objFile.getInputStream();

        file = new File(filePath);
        try {

            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String isExcelValue = "2007";
        map = uploadExcel(file, isExcelValue);

//        ReturnUtil.writeString(response, JsonUtils.objectToJson(map));
//        ReturnUtil.writeString(response, JSON.toJSONString(map));
//        toWeb(JsonUtils.objectToJson(map));
        return map;
    }

    /**
     * Excel上传并读取excel内容,并批量插入数据库
     *
     * @param objFile
     * @param isExcelValue
     * @return
     * @throws IOException
     */
    public Map<String, Object> uploadExcel(File objFile, String isExcelValue) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        // 参数定义
        FileInputStream objInputStream = null;
        Workbook objHSSFWorkbook;
        Sheet objHSSFSheet;
        int nTotalRow, nFirstRow, nLastRow, nTotalColumn;

        try {
            // 参数判断
            if (objFile == null || objFile.isDirectory()) {
                logger.error("数据信息不合法");
                map.put("code", "error");
                map.put("data", "数据信息不合法");
                return map;
            }

            objInputStream = new FileInputStream(objFile);
            if (objInputStream == null) {
                logger.error("创建输入流失败！");
                map.put("code", "error");
                map.put("data", "创建输入流失败！");
                return map;
            }

            /** 判断文件的类型，是2003还是2007 */
            if (isExcelValue.equals("2007")) {
                objHSSFWorkbook = new XSSFWorkbook(objInputStream);
            } else {
                objHSSFWorkbook = (Workbook) new HSSFWorkbook(objInputStream);
            }

            if (objHSSFWorkbook == null) {
                logger.error("创建EXCEL工作簿失败。");
                map.put("code", "error");
                map.put("data", "创建EXCEL工作簿失败");
                return map;
            }

            // objHSSFWorkbook = new HSSFWorkbook(objInputStream);
            // 只处理第一个Sheet
            objHSSFSheet = objHSSFWorkbook.getSheetAt(0);
            if (objHSSFSheet == null) {
                logger.error("获取EXCEL工作簿第 " + 0 + " 个工作表失败。");
                map.put("code", "error");
                map.put("data", "获取EXCEL工作簿第 " + 0 + " 个工作表失败。");
                return map;
            }

        } catch (Exception objEx) {
            logger.error("解析EXCEL文件异常。文件解析路径：" + objFile.getAbsolutePath(), objEx);
            map.put("code", "error");
            map.put("data", "解析EXCEL文件异常。文件解析路径：" + objFile.getAbsolutePath());
            return map;
        }

        nTotalRow = objHSSFSheet.getPhysicalNumberOfRows(); // 获得总行数--实际也不是很精确，若某行只有某单元格有空格，也会认为是一行数据
        nTotalColumn = objHSSFSheet.getRow(0).getPhysicalNumberOfCells();// 获得总列数
        nFirstRow = objHSSFSheet.getFirstRowNum();
        nLastRow = objHSSFSheet.getLastRowNum();
        logger.info("Excel中记录nRows共: " + nTotalRow + ",表头行是:---" + nFirstRow + "最后一行是：" + nLastRow);

        List<UserPojo> userList = new ArrayList<UserPojo>();
        // 遍历所有数据行
        for (int n = nFirstRow + 1; n <= nLastRow; n++) {
            // 保存数据
            UserPojo userPojo = new UserPojo();
            // 拿到第N+1行
            Row objDataRow = objHSSFSheet.getRow(n);
            if (ExcelUtils.isBlankRow(objDataRow)) {
                logger.info("第" + n + "行数据为空");
                continue;
            }
            userPojo.setUserId(ExcelUtils.getExcelCellData(objDataRow.getCell(0)).trim());//备注
            userPojo.setUserName(ExcelUtils.getExcelCellData(objDataRow.getCell(1)).trim());//备注
            userPojo.setAge(Integer.parseInt(ExcelUtils.getExcelCellData(objDataRow.getCell(2)).trim()));//备注
            userPojo.setPassword(ExcelUtils.getExcelCellData(objDataRow.getCell(3)).trim());//备注
            userPojo.setOldPassword(ExcelUtils.getExcelCellData(objDataRow.getCell(4)).trim());//备注
            userList.add(userPojo);
        }
        map.put("code", "success");
        map.put("data", userList);
        return map;
    }

    /**
     * 显示excel文档接口内容接口
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findExcelList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findExcelList() throws Exception {
        return map;
    }
}