package com.byzan.writting;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.byzan.utility.PathConstant;

public class WriteTextFile implements PathConstant {

    public boolean writeoutputfile(ArrayList<ArrayList<String>> mappedData, File inputFile) {
        boolean isSuccess = false;

        // Define output folder
        File outputFolder = new File(OUTPUT); // Output path from PathConstant
        // Generate timestamp for filename
        String timestamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());

        // Build output filename
        String outputFileName = "EIPP_Outstanding_Ledger_" + timestamp + ".txt";

        // Create file object
        File outputFile = new File(outputFolder, outputFileName);

        // Header columns
        String header = String.join("|",
                "Customer Code",
                "Customer Name",
                "Document Number",
                "Document Type",
                "Document Date",
                "Document Due Date",
                "Total Amount",
                "Outstanding Amount",
                "Gross Amount",
                "Tax Amount",
                "Currency Code",
                "Fin Year",
                "DOC_REF",
                "Division_Code",
                "Region_Code",
                "CN Description Text",
                "ReferenceNumber1",
                "ReferenceNumber2",
                "ReferenceNumber3",
                "ReferenceNumber4",
                "ReferenceNumber5");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {

            // Write header
            bw.write(header);
            bw.newLine();

            // Write each record
            for (ArrayList<String> record : mappedData) {
                String line = String.join("|", record);
                bw.write(line);
                bw.newLine();
            }

            bw.flush();
            isSuccess = true;

         //   System.out.println(" Output file created successfully at: " + outputFile.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return isSuccess;
    }
}
