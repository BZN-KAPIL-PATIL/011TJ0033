package com.byzan.mapping;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.byzan.utility.MyLogger;
import com.byzan.utility.PathConstant;

public class MappingFile implements PathConstant {

    public ArrayList<ArrayList<String>> mappingFile(ArrayList<ArrayList<String>> filedata,
            ArrayList<ArrayList<String>> validationFileData, File inputFile) {
        ArrayList<ArrayList<String>> mappeddata = new ArrayList<>();

        try {
        	SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            for (int i = Row; i < filedata.size(); i++) {
                ArrayList<String> singleMappedData = new ArrayList<>();
                ArrayList<String> rowData = filedata.get(i);

                int position = 0;

                // 01 Customer Code
                position = Integer.parseInt(validationFileData.get(1).get(5));
                singleMappedData.add(rowData.get(position));

                // 02 Customer Name
                position = Integer.parseInt(validationFileData.get(2).get(5));
                singleMappedData.add(rowData.get(position));

             // 03 Document Number Logic Based on Sign
                String signValue = rowData.get(10).trim();
                String documentNumber = "";

                if (signValue.equals("-")) {
                    documentNumber = rowData.get(6);   // sign "-" → index 6
                } else if (signValue.equals("+")) {
                    documentNumber = rowData.get(18);  // sign "+" → index 18
                }

                singleMappedData.add(documentNumber);


                // --------------------- 04 Document Type Logic ---------------------
                String sign = rowData.get(10).trim(); // "+" or "-"  comes at 10 position in csv
                String dockType = rowData.get(15).trim();           //comes at 15 position in csv
                String docPrefix = dockType.split(" - ")[0].trim();

                String mappedType = "";

                switch (docPrefix) {
                    case "RV":
                        // RV - Billing Doc.Transfer → only for '+'
                        if (sign.contains("+")) {
                            mappedType = "Inv";
                        }
                        break;

                    case "DZ":
                        // DZ - Customer Payment
                        if (sign.contains("+")) {
                            mappedType = "DN";
                        } else if (sign.contains("-")) {
                            mappedType = "CN";
                        }
                        break;

                    case "AB":
                        // AB - Accounting Document
                        if (sign.contains("+")) {
                            mappedType = "DN";
                        } else if (sign.contains("-")) {
                            mappedType = "CN";
                        }
                        break;

                    case "CC":
                        // CC - Cust. Credit Note-SD → only for '-'
                        if (sign.contains("-")) {
                            mappedType = "CN";
                        }
                        break;

                    case "CD":
                        // CD - Cust. Debit Note-SD → only for '+'
                        if (sign.contains("+")) {
                            mappedType = "DN";
                        }
                        break;

                    case "DG":
                        // DG - Customer Credit Memo → only for '-'
                        if (sign.contains("-")) {
                            mappedType = "CN";
                        }
                        break;

                    case "DR":
                        // DR - Customer Debit Memo → only for '+'
                        if (sign.contains("+")) {
                            mappedType = "DN";
                        }
                        break;

                    default:
                        mappedType = "";
                        break;
                }

                singleMappedData.add(mappedType);
                
             // --------------------- 05 & 06 Document Date + Due Date ---------------------
                Date docDate = null;
                Date dueDate = null;
                SimpleDateFormat sdf = new SimpleDateFormat(InputFileDateFormat);

                // -------- Document Date --------
                try {
                    int dateIndex = Integer.parseInt(validationFileData.get(5).get(5));
                    String docDateStr = rowData.get(dateIndex);

                    if (docDateStr != null && !docDateStr.trim().isEmpty()) {
                        docDate = sdf.parse(docDateStr);
                    }
                } catch (Exception e) {
                    MyLogger.error("Error parsing Document Date: " + e.getMessage());
                }

                // -------- Due Date --------
                try {
                    int dueDateIndex = Integer.parseInt(validationFileData.get(6).get(5));
                    String dueDateStr = rowData.get(dueDateIndex);

                    if (dueDateStr != null && !dueDateStr.trim().isEmpty()) {
                        dueDate = sdf.parse(dueDateStr);
                    }
                } catch (Exception e) {
                    MyLogger.error("Error parsing Due Date: " + e.getMessage());
                }

                // -------- CORE LOGIC --------
                // If Due Date < Document Date → Due Date = Document Date
                if (docDate != null && dueDate != null && dueDate.before(docDate)) {
                    //MyLogger.info("Due Date is earlier than Document Date. Setting Due Date = Document Date");
                    dueDate = docDate;
                }

                // -------- Add to output (null-safe) --------
                singleMappedData.add(docDate != null ? df.format(docDate) : "");
                singleMappedData.add(dueDate != null ? df.format(dueDate) : "");

                
                // ---------------------------------------------------------------

                // --------------------- 05 Document Date ---------------------
            /*    try {
                    int dateIndex = Integer.parseInt(validationFileData.get(5).get(5));
                    Date date1 = new SimpleDateFormat(InputFileDateFormat).parse(rowData.get(dateIndex));
                    singleMappedData.add(df.format(date1));
                } catch (Exception e) {
                    MyLogger.error("Error parsing Document Date: " + e.getMessage());
                    singleMappedData.add("");
                }

                // --------------------- 06 Document Due Date ---------------------
                try {
                    int dueDateIndex = Integer.parseInt(validationFileData.get(6).get(5));
                    Date date2 = new SimpleDateFormat(InputFileDateFormat).parse(rowData.get(dueDateIndex));
                    singleMappedData.add(df.format(date2));
                } catch (Exception e) {
                    MyLogger.error("Error parsing Due Date: " + e.getMessage());
                    singleMappedData.add("");
                }*/


                // 07 Total Amount
                position = Integer.parseInt(validationFileData.get(7).get(5));
                singleMappedData.add(rowData.get(position));

                // 08 Outstanding Amount
                position = Integer.parseInt(validationFileData.get(8).get(5));
                singleMappedData.add(rowData.get(position));

                // 09 Gross Amount
                position = Integer.parseInt(validationFileData.get(9).get(5));
                singleMappedData.add(rowData.get(position));

                // 10 Tax Amount
                singleMappedData.add("");

                // 11 Currency Code
                position = Integer.parseInt(validationFileData.get(11).get(5));
                singleMappedData.add(rowData.get(position));

                // 12 Fin Year
                singleMappedData.add(String.valueOf(java.time.LocalDate.now().getYear()));

                // 13 DOC_REF
                singleMappedData.add("");

                // 14 Division_Code
                position = Integer.parseInt(validationFileData.get(14).get(5));
                singleMappedData.add(rowData.get(position));

                // 15 Region_Code
                singleMappedData.add("");

                // 16 CN Description Text
                singleMappedData.add("");

                // 17 ReferenceNumber1 (extract prefix)
                String dockType1 = rowData.get(15);
                String refNumber1 = dockType1.split(" - ")[0].trim();
                singleMappedData.add(refNumber1);

                // 18 ReferenceNumber2
                singleMappedData.add("");

                // 19 ReferenceNumber3
                singleMappedData.add("");

                // 20 ReferenceNumber4
                singleMappedData.add("");

                // 21 ReferenceNumber5
                singleMappedData.add("");

                mappeddata.add(singleMappedData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mappeddata;
    }
}
