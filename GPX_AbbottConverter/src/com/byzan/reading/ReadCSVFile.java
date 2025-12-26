package com.byzan.reading;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.byzan.utility.BZNUtils;
import com.byzan.utility.MyLogger;
import com.byzan.utility.PathConstant;

public class ReadCSVFile implements PathConstant{
	
	public static ArrayList<ArrayList<String>> readCSVFile(File inputFile) {
		ArrayList<ArrayList<String>> fileData = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {

			String line;
			while ((line = br.readLine()) != null) {

				  String[] splitValues = line.split("\\,", -1);
				List<String> data = Arrays.asList(splitValues);
				ArrayList<String> data1 = new ArrayList<String>(data);
				if (data1.size() > 0) {
					fileData.add(data1);
				}
			}
		} catch (Exception e) {
			MyLogger.error("Error reading file: " + inputFile.getName() + " | " + e.getMessage());
			try {
				BZNUtils.moveFile(INPUT + inputFile.getName(), FAILURE + inputFile.getName());
				MyLogger.error("File moved to FAILURE folder: " + inputFile.getName());
			} catch (IOException ex) {
				MyLogger.error("Failed to move file: " + ex.getMessage());
			}
		}

		return fileData;
	}

}
