package com.cq.excelLib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
/**
 * this class contains methods to perform operations on excel
 * @author mkarthik
 *
 */
public class ExcelLib 
{
	public WorkbookFactory obj;
	public FileInputStream fis;
	public FileOutputStream fos;
	public File fil;
	public Workbook wBook;
	public Sheet sheet;
	public Row row;
	public Cell cell;
	public ExcelLib eLib;
	
	/**
	 * this function reads data from the excel from a specified cell
	 * @param path
	 * @param sheetName
	 * @param rowNum
	 * @param column
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
    public String readData(String path,String sheetName,int rowNum,int column) throws InvalidFormatException, IOException
    {
    	String data="";
    	try
    	{
    		obj=new WorkbookFactory();
    		fil=new File(path);
    		fis=new FileInputStream(fil);
    		wBook=obj.create(fis);
    		sheet=wBook.getSheet(sheetName);
    		row=sheet.getRow(rowNum);
    		cell=row.getCell(column);
    		data=cell.getStringCellValue();
    	}
    	catch(Exception e)
    	{
    	
    	}
    	finally
    	{
           fis.close();
   	       return data;
    	}
    }
    /**
     * this function is used to read all the data in the excel
     * @param path
     * @param sheetName
     * @return
     * @throws InvalidFormatException
     * @throws IOException
     */
    public String readAllData(String path,String sheetName) throws InvalidFormatException, IOException
    {
    	String datas="";
    	try
    	{
    	int rowCnt=0;
    	int colCnt=0;
    	obj=new WorkbookFactory();
    	eLib=new ExcelLib();
  	    fil=new File(path);
  	    fis=new FileInputStream(fil);
  	    wBook=obj.create(fis);
  	    sheet=wBook.getSheet(sheetName);
  	    row=sheet.getRow(1);
  	    rowCnt=sheet.getLastRowNum();
  	    colCnt=(int)row.getLastCellNum();
  	    for(int i=1;i<=rowCnt;i++)
  	    {
  	    	for(int j=0;j<=colCnt;j++)
  	    	{	
  	    		datas=datas+eLib.readData(path, sheetName,i,j)+" ";
  	    	}
  	    }
    	}
    	catch(Exception e)
    	{
    		
    	}
    	finally
    	{
    		fis.close();
    		return datas;
    	}
    }
    /**
     * this function is used to write data to particular cell in an excel sheet
     * @param path
     * @param sheetName
     * @param rowNum
     * @param col
     * @param data
     * @throws InvalidFormatException
     * @throws IOException
     */
    public void writeDataToExcel(String path, String sheetName,int rowNum,int col,String data) throws InvalidFormatException, IOException
    {
    	System.out.println(path+" "+sheetName+" "+rowNum+" "+col+" "+data);
       fis=new FileInputStream(path);
       wBook=WorkbookFactory.create(fis);
       sheet=wBook.getSheet(sheetName);
       row=sheet.getRow(rowNum);
       cell=row.createCell(col);
       cell.setCellType(cell.CELL_TYPE_STRING);
       cell.setCellValue(data);
       fos=new FileOutputStream(path);
       wBook.write(fos);
       fos.close();
     }
    /**
     * this function is used to get the number of rows used in the excel
     * @param path
     * @param sheetName
     * @return
     * @throws InvalidFormatException
     * @throws IOException
     */
    public int getNoOfUsedRows(String path, String sheetName) throws InvalidFormatException, IOException
    {
      	int rowCnt=0;
    	try{
    	obj=new WorkbookFactory();
  	    fil=new File(path);
  	    fis=new FileInputStream(fil);
  	    wBook=obj.create(fis);
  	    sheet=wBook.getSheet(sheetName);
  	    row=sheet.getRow(1);
  	    rowCnt=sheet.getLastRowNum();
    	}
    	catch(Exception e)
    	{
    		
    	}
    	finally
    	{
    		fis.close();
    		return rowCnt;	
    	}
    
    }
    /**
     * this function is used to get the number of columns in the excel
     * @param path
     * @param sheetName
     * @return
     * @throws InvalidFormatException
     * @throws IOException
     */
    public int getNoOfUsedCoulmns(String path, String sheetName) throws InvalidFormatException, IOException
    {
    	int colCnt=0;
    	try{
    	obj=new WorkbookFactory();
  	    fil=new File(path);
  	    fis=new FileInputStream(fil);
  	    wBook=obj.create(fis);
  	    sheet=wBook.getSheet(sheetName);
  	    row=sheet.getRow(1);
  	    String data;
  	    Iterator<Cell>ite=row.cellIterator();
  	    while(ite.hasNext())
  	    {
  	    	data=ite.next().getStringCellValue();
  	    	colCnt=colCnt+1;
  	    	if(data==null)
  	    	{
  	    		break;
  	    	}
  	    }
    	}
    	catch(Exception e)
    	{
    		
    	}
    	finally
    	{
    		fis.close();
    		return colCnt;
    	}   
    }
}
