package cn.gathub.uilts;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Excel工具类 。
 */
public class ExcelUtils {
    /**
     * 小数点后面 # 的个数代表转换模式后支持的小数位数
     */
    private static final String NUMBERFORMAT_DECIMAL = "#.####";

    /**
     * 拿到单元格中的值 。
     *
     * @param objCell
     * @return
     */
    public static String getExcelCellData(Cell objCell) {
        String strValue = "";
        try {
            if (objCell == null) {
                return "";
            }
            switch (objCell.getCellType()) {
                case HSSFCell.CELL_TYPE_NUMERIC: // 0
                {
                    // 日期型
                    if (HSSFDateUtil.isCellDateFormatted(objCell)) {
                        strValue = new SimpleDateFormat("yyyy-MM-dd").format(objCell.getDateCellValue());
                    } else {
                        strValue = new DecimalFormat(NUMBERFORMAT_DECIMAL).format(objCell.getNumericCellValue());
                    }
                    break;
                }
                case HSSFCell.CELL_TYPE_STRING: // 1
                    strValue = objCell.getRichStringCellValue().toString().replaceAll("'", "''");
                    break;
                case HSSFCell.CELL_TYPE_FORMULA: // 2
                    strValue = String.valueOf(objCell.getNumericCellValue());
                    break;
                case HSSFCell.CELL_TYPE_BLANK: // 3
                    strValue = objCell.getRichStringCellValue().toString();
                    break;
                case HSSFCell.CELL_TYPE_BOOLEAN: // 4
                    strValue = String.valueOf(objCell.getBooleanCellValue());
                    break;
                case HSSFCell.CELL_TYPE_ERROR: // 5
                    strValue = String.valueOf(objCell.getErrorCellValue());
                    break;
                default:
                    strValue = "";
            }

            // 去掉空格换行等
            strValue = strValue.replaceAll(" ", "").replaceAll("　", "").replaceAll("\n", "").replaceAll("\t", "").replaceAll("\r", "").replaceAll("\b", "").replaceAll("'", "").replaceAll("‘", "");

            // 返回结果

        } catch (Exception e) {
            e.printStackTrace();
        }
        return strValue;


    }

    /**
     * 判断Excel行是否为空。
     *
     * @param objDataRow
     * @return
     */
    public static boolean isBlankRow(Row objDataRow) {
        boolean bResult = true;

        if (objDataRow == null) {
            return true;
        }
        for (int i = objDataRow.getFirstCellNum(); i < objDataRow.getLastCellNum(); i++) {
            Cell objCell = objDataRow.getCell(i, HSSFRow.RETURN_BLANK_AS_NULL);
            String strValue = "";

            if (objCell != null) {
                strValue = getExcelCellData(objCell);
                if (!strValue.trim().equals("")) {
                    bResult = false;
                    break;
                }
            }
        }

        return bResult;
    }

    /**
     * 复制excel行数据。
     *
     * @param objHSSFSheetWrong
     * @param nFaileSum         新建行号
     * @param objDataRow        被复制行
     * @param nTotalColumn      列数
     */
    public static void copyRow(HSSFSheet objHSSFSheetWrong, int nFaileSum, HSSFRow objDataRow, int nTotalColumn) {
        HSSFRow objHSSFRowAdd = objHSSFSheetWrong.createRow(nFaileSum);

        for (int i = 0; i < nTotalColumn; i++) {
            HSSFCell objCell = objHSSFRowAdd.createCell(i);

            objCell.setCellValue(ExcelUtils.getExcelCellData(objDataRow.getCell(i)));
        }
    }


    public static boolean isExcel2003(String filePath) {

        return filePath.matches("^.+\\.(?i)(xls)$");
    }


    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }
}
