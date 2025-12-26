package com.byzan.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.byzan.mapping.MappingFile;
import com.byzan.reading.ReadCSVFile;
import com.byzan.reading.ValidationFileReading;
import com.byzan.utility.MyLogger;
import com.byzan.utility.PathConstant;
import com.byzan.writting.WriteTextFile;

public class GPX_AbbottConverter_Main implements PathConstant {
	public static void main(String[] args) {

		if (!AUDIT_LOG.isEmpty()) {

			MyLogger.createLogFile(AUDIT_LOG);
		} else {
			MyLogger.error("Path for log file in properties file not found ");
		}

		MyLogger.info("!----------------------Application Started-------------------!");

		GPX_AbbottConverter_Main main = new GPX_AbbottConverter_Main();

		main.run();
	}

	public void run() {
		if (createFolders() != 1) {
			MyLogger.error("Failed to create required directories.");
			return;
		}

		File file = new File(INPUT);

		File[] files = file.listFiles();

		if (files.length > 0) {

			ValidationFileReading v = new ValidationFileReading();

			ArrayList<ArrayList<String>> ValidationFileData = v.Validationfile(Validationfilepath);

			if (ValidationFileData.size() > 0 && ValidationFileData != null) {
				for (File inputFile : files) {
					if (!inputFile.isDirectory()) {
						MyLogger.info("Started reading input file " + inputFile.getName());
						ReadCSVFile r = new ReadCSVFile();

						ArrayList<ArrayList<String>> filedata = r.readCSVFile(inputFile);
						if (filedata.size() > 0 && filedata != null) {

							MappingFile m = new MappingFile();

							ArrayList<ArrayList<String>> mappedData = m.mappingFile(filedata, ValidationFileData,
									inputFile);

							if (mappedData.size() > 0 && mappedData != null) {
								
								WriteTextFile w=new WriteTextFile();
								boolean flag = w.writeoutputfile(mappedData,inputFile);
								
								if(flag==true) {
									MyLogger.info("OutputFile Written successfully done");
									try {
										com.byzan.utility.BZNUtils.moveFile(INPUT+inputFile.getName(), SUCCESS+inputFile.getName());
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								

							}
						}

					}
				}
			}
		} else {
			MyLogger.info("Input folder does not contain any file to process");
		}

	}

	private static int createFolders() {
		try {
			String[] folders = { INPUT, OUTPUT, SUCCESS, FAILURE, AUDIT_LOG };
			for (String folderPath : folders) {
				File folder = new File(folderPath);
				if (!folder.exists()) {
					folder.mkdirs();
					MyLogger.info("Creating folder: " + folderPath);
				}
			}
			return 1;
		} catch (Exception e) {
			MyLogger.error("Error creating folders: " + e.getMessage());
			return 0;
		}
	}

}
