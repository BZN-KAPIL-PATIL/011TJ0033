package com.byzan.reading;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.byzan.utility.MyLogger;

public class ValidationFileReading {
	public ArrayList<ArrayList<String>>Validationfile(String validationfilepath){

		ArrayList<ArrayList<String>>validationfile=new ArrayList<>();

		BufferedReader br=null;
		try {
			br=new BufferedReader(new FileReader(validationfilepath.toString()));

			String line;

			while((line=br.readLine())!=null) {

				String[] splitValues = line.split(",",-1);

				List<String> data = Arrays.asList(splitValues);

				ArrayList<String>data1=new ArrayList<>(data);

				if(data1.size()>0) 

					validationfile.add(data1);
				

			}
		} catch (FileNotFoundException e) {
			String line;
			validationfile=null;
			MyLogger.error("Validation file is not present in following path'" + validationfilepath + "'  ");
		}catch (IOException e) {
			validationfile = null;
			MyLogger.error("IO exception " + e.getMessage());
			e.printStackTrace();
		}catch (Exception ex) {
			validationfile = null;
			MyLogger.error("Exception " + ex.getMessage());
			ex.printStackTrace();
		}
		return validationfile;

	}

}
