package com.comitfs.fileToExcel.app;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * 
 * @author Deepthi grandhi
 *
 */

public class FileToExcelMain {

	public static void main(String[] args) throws FileNotFoundException, InvocationTargetException {
		System.out.println(args[0]);
		System.out.println(args[1]);
		System.out.println(args[2]);
		searchForName();
		
	}

	public static void searchForName() throws FileNotFoundException {
		
		
        String fileName = "xmldebugger.log";
        String line = null;
        int count = 0;
       
        int rowCount = 0;
        
        String[] parts = null; 
        int currentIteration = 0;
        
        System.out.println("Enter callid ");
        Scanner scanner2 = new Scanner(System.in);
        String inputValue = scanner2.nextLine(); 
        int length = inputValue.length( );
        System.out.println("length of id is  "+length);
       // System.out.println("entered callid is" + inputValue);
        inputValue = ">"+inputValue;
        
       // if(length==13)
      //  {                       
                
                
        System.out.println("Enter number of iterations: ");      
        Scanner scanner = new Scanner(System.in);        
        int iterations = scanner.nextInt();
        
        System.out.println("Enter count of the input value you need : ");
        Scanner scanner1 = new Scanner(System.in);
        int enteredcount = scanner1.nextInt();
        
        
        try {
        	
            FileReader fileReader = null;
            BufferedReader bufferedReader = null;
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("FirstSheet");  
            
            CellStyle cellStyle1 = workbook.createCellStyle();
           // cellStyle1.setFillForegroundColor(IndexedColors.BLACK.index);
            cellStyle1.setFillBackgroundColor(IndexedColors.RED.index);
            
            CellStyle cellStyle2 = workbook.createCellStyle();
            //cellStyle2.setFillForegroundColor(IndexedColors.WHITE.index);
            cellStyle2.setFillBackgroundColor(IndexedColors.GREEN.index);
            

            HSSFRow rowhead = sheet.createRow((short)rowCount);
            rowhead.createCell(0).setCellValue("call id");
            rowhead.createCell(1).setCellValue("count of callid in log");
            rowhead.createCell(3).setCellValue("Message");
            rowhead.createCell(2).setCellValue("Status");
            while(currentIteration < iterations){
            	StringBuilder str = new StringBuilder(inputValue);
                BigInteger  enteredId = new BigInteger(str.substring(1));  
            	String status = "";
//            	new ResourceTest().getClass().getResource(fileName);
//            	InputStream some = getClass().getResourceAsStream("/com/comitfs/fileToExcel.app/xmldebugger.log");
            	ArrayList<String> msgList = new ArrayList<String>();
            	fileReader =  new FileReader(fileName);
            	bufferedReader = new BufferedReader(fileReader);
            	System.out.println(bufferedReader.readLine());
            	while((line = bufferedReader.readLine()) != null) {
                    
                    if(line.contains(inputValue)) {
                    	count++; 
                    		if((line.contains("error")) || count!=enteredcount || length!=13)                
                        		status = "fail";
                    			
                    		else 
                        		status ="pass";  
                    		
                    	Pattern pattern = Pattern.compile("<(.*?)>");
                    	Matcher matcher = pattern.matcher(line);
                    	parts = line.split(": "); 
                    	msgList.add(parts[1]);
                    }
                    /*else{
                    	status= "fail";
                    }*/
                }   
            	currentIteration++;
                enteredId = enteredId.add(BigInteger.ONE);
            	 inputValue = ">"+enteredId;          	
            	
            	 HSSFRow firstRow = sheet.createRow((short) ++rowCount);
                 firstRow.createCell(0).setCellValue(str.substring(1));
                 firstRow.createCell(1).setCellValue(count);
                 firstRow.createCell(2).setCellValue(status);
                 //firstRow.createCell(3).setCellValue(parts[1]); //printing count instead of message as getting many messages
                

         	     //style.setFillPattern(CellStyle.SOLID_FOREGROUND);
                 
                /* HSSFCellStyle styleGreen = workbook.createCellStyle();
                 styleGreen.setFillForegroundColor(HSSFColor.LIME.index);
                 styleGreen.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                 styleGreen.setFillForegroundColor(IndexedColors.GREEN.index);
                 
                 HSSFCellStyle styleRed = workbook.createCellStyle();
                 styleRed.setFillForegroundColor(HSSFColor.LIME.index);
                 styleRed.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                 styleRed.setFillForegroundColor(IndexedColors.RED.index);*/
                 
         	       
                 HSSFCell cell = firstRow.createCell(2); 
            	 cell.setCellValue(status);
            	 
               /*  if(status=="pass")                                	 
                	 cell.setCellStyle(styleGreen);                 
                 else               
               	     cell.setCellStyle(styleRed);*/                                              
                 
                 
                 Iterator<String> iterator =  msgList.iterator();
                 System.out.println("array size"+msgList.size());
                 System.out.println(str.substring(1));
                 if(count!=0) {
                 String msg = iterator.next();
                 firstRow.createCell(3).setCellValue(msg);
                 
                 for(int i=1;i<msgList.size();i++) 
                 {
                	 msg = iterator.next();
                	 System.out.println(msg);
                	 HSSFRow row = sheet.createRow((short) ++rowCount);
                     row.createCell(3).setCellValue(msg);                  	
                 }
                 msgList = null;
                 count = 0;
                 }
            }          
              
            
            File excelFile = new File("abc1.xls");
            FileOutputStream fileOut = new FileOutputStream(excelFile);
            workbook.write(fileOut);
            fileOut.close();
            //workbook.clone();
            
            System.out.println("Your excel file has been generated!");                    
            bufferedReader.close();         
        }
        
        catch(FileNotFoundException ex) {
            System.out.println(ex);                
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");                  
        }
        catch (Exception ex) {
        	System.out.println("cant create more than 256 columns or something went wrong"+ ex);
        }
      //  }
        /*else {
        	System.out.println("invalid call id - expected callid length should be 13");  
        	searchForName();
             }*/
        
    }

	private static HSSFCell cell(int i) {
		// TODO Auto-generated method stub
		return null;
	}
}
