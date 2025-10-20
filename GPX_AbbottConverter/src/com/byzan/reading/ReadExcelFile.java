package com.byzan.reading;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.byzan.utility.MyLogger;
import com.byzan.utility.PathConstant;

public class ReadExcelFile implements PathConstant{
	
	ArrayList<ArrayList<String>>data=new ArrayList<>();

	public ArrayList<ArrayList<String>> readinputfile(File inputfile){

		FileInputStream fileinputStream = null;

		try {
			fileinputStream = new FileInputStream(inputfile);
			Workbook workbook = null;
			try {
				workbook = WorkbookFactory.create(fileinputStream);
			} catch (EncryptedDocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Sheet sheet = workbook.getSheetAt(0);
			DataFormatter dataFormatter = new DataFormatter();
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				ArrayList<String> rowData = new ArrayList<>();
				if (row != null) {
					for (int j = 0; j < row.getLastCellNum(); j++) {
						Cell cell = row.getCell(j);
						String Data = dataFormatter.formatCellValue(cell);
						rowData.add(Data);
					} 
					if(rowData.size()>0) {
						
						this.data.add(rowData);
					}

				} 
			} 
		} catch (FileNotFoundException e) {
			MyLogger.error("Error while reading input file " + inputfile.getName());
			MyLogger.error("Error :" + e.toString());
			this.data.clear();
		} catch (IOException e) {
			MyLogger.error("Error while reading input file " + inputfile.getName());
			MyLogger.error("Error :" + e.toString());
			this.data.clear();
		} 

		return data;


	}


}
