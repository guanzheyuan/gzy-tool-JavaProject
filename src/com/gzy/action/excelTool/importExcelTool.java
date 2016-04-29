package com.gzy.action.excelTool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 导入Excel工具类
 * @author sway
 */
public class importExcelTool {
	/** 总行*/  
    private static int totalRows = 0;  
    /** 总列*/  
    private static int totalCells = 0; 
    /**总sheet**/
    private static int totalSheets=0;
    
    /**构造方法 **/
    public importExcelTool(){
    } 
    /**
     * 功能描述：得到总sheet数量
     * @return
     */
	public static int getTotalSheets() {
		return totalSheets;
	}
	/**
	 * 功能描述：得到�?行数 
	 * @return int
	 */
    public static int getTotalRows(){  
    	return totalRows;  
    }  
    /**
     * 功能描述：得到�?列数
     * @return int
     */
    public static int getTotalCells(){  
        return totalCells;  
    }
    /**
     * 功能描述：根据文件名读取excel文件 (支持 2003||2007||2010)
     * @param filePath 文件路径
     * @return excel数据集合
     * @throws FileNotFoundException 
     */
    public static List<String[]> read(String filePath) throws FileNotFoundException{
    	List<String[]> dataList=new ArrayList<String[]>();
    	//判断文件是否2003
    	boolean isExcel2003 = true;
    	if(isExcel2007(filePath)){//判断文件格式
    		isExcel2003=false;
    	} 
    	File file = new File(filePath);
    	//读取文件
    	dataList=readFile(filePath,file,isExcel2003);
    	return dataList;
    }
    /**
     * 功能描述：根据流读取Excel文件 
     * @param inputStream 文件�?
     * @param isExcel2003 文件格式
     * @return excel表格数据集合
     */
    private static List<String[]> readFile(String filePath,File file, boolean isExcel2003){
    	 List<String[]> dataList = null;
    	 Workbook wb = null;
		 try {
			 //根据版本选择创建Workbook方法
			 if(isExcel2003){
				wb = new HSSFWorkbook(new FileInputStream(filePath));
			  }else{
				wb = new XSSFWorkbook(new FileInputStream(file));  
			  }
			 dataList = read(wb); //读取数据 
			} catch (Exception e){
				//logger.error("【读取Excel文件流报错"+e.getMessage());
				System.out.println(e.getMessage());
			}  
			return dataList;
    }  
    /**
     * 功能描述：读取数
     * @param wb 创建工作
     * @return 数据集合
     */
	private static List<String[]> read(Workbook wb){
    	List<String[]> dataList=new ArrayList<String[]>();
    	String[] values = null;
    	//得到第一个shell
        Sheet sheet = wb.getSheetAt(0);  
        // 得到Excel的行�?  
        totalRows = sheet.getPhysicalNumberOfRows(); 
        //得到Excel的列�? 
        if (totalRows >= 1 && sheet.getRow(0) != null){  
            totalCells = sheet.getRow(0).getPhysicalNumberOfCells();  
        }
        //循环Excel的行  
        for (int r = 1; r < totalRows; r++){
        	 Row row = sheet.getRow(r); 
        	 if (row == null){  
                 continue;  
             }
        	 values = new String[getTotalCells()];
        	 for (int c = 0; c < getTotalCells(); c++){
        		  Cell cell = row.getCell(c);
        		  if (null != cell){
        			  switch (cell.getCellType()){  
	                      case HSSFCell.CELL_TYPE_NUMERIC: // 数字  
	                    	  values[c] = cell.getNumericCellValue() + "";  
	                          break;  
	                      case HSSFCell.CELL_TYPE_STRING: // 字符�? 
	                    	  values[c] = cell.getStringCellValue();  
	                          break;  
	                      case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean  
	                    	  values[c] = cell.getBooleanCellValue() + "";  
	                          break;  
	                      case HSSFCell.CELL_TYPE_FORMULA: // 公式  
	                    	  values[c] = cell.getCellFormula() + "";  
	                          break;  
	                      case HSSFCell.CELL_TYPE_BLANK: // 空�?  
	                    	  values[c] = "";  
	                          break;  
	                      case HSSFCell.CELL_TYPE_ERROR: // 故障  
	                    	  values[c] = "非法字符";  
	                          break;  
	                      default:  
	                    	  values[c] = "未知类型";  
	                          break;  
                      }  
        		  }  
        	 }  
        	 dataList.add(values);
        }  
       return dataList;
    } 
	/**
	 * 功能描述：判断Excel格式是否003
	 * @param filePath 文件 路径
	 * @return true：是,false：不
	 */
    public static boolean isExcel2003(String filePath){  
        return filePath.matches("^.+\\.(?i)(xls)$");  
    } 
    /**
     * 功能描述：判断Excel格式是否2007
     * @param filePath 文件
     * @return true：是,false：不
     */
    public static boolean isExcel2007(String filePath){  
        return filePath.matches("^.+\\.(?i)(xlsx)$");  
    }  
}
