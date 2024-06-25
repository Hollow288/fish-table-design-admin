package com.pond.build.Controller;


import com.pond.build.model.Field;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


@RestController
public class UploadController {


    @PostMapping("/upload")
    public Map<String,Object> uploadQuotation(@RequestParam(value = "file") MultipartFile[] files){

        Map<String, Object> resultMap = new HashMap<>();

        try {
            List<Map<String, Object>> dataList = new ArrayList<>();
            MultipartFile file = files[0];
            InputStream inputStream = file.getInputStream();
            XWPFDocument document = new XWPFDocument(inputStream);
            List<XWPFTable> tables = document.getTables();
            int targetTableIndex = 0; // 指定的表格在文档中的索引

            XWPFTable targetTable = tables.get(targetTableIndex);
            for (int i = 1; i < targetTable.getNumberOfRows(); i++) {
                XWPFTableRow row = targetTable.getRow(i);
                Map<String, Object> temMap = new HashMap<>();
                for (int j = 0; j < row.getTableCells().size(); j++) {
                    XWPFTableCell cell = row.getCell(j);
                    switch (j){
                        case 0:
                            temMap.put("fieldName",cell.getText());
                            break;
                        case 1:
                            temMap.put("fieldType",cell.getText());
                            break;
                        case 2:
                            temMap.put("remark",cell.getText());
                            break;
                        case 3:
                            break;
                    }
                }
                temMap.put("uuid",UUID.randomUUID().toString());
                temMap.put("isChecked",false);
                temMap.put("isPrimaryKey",false);
                dataList.add(temMap);
            }

            resultMap.put("status","200");
            resultMap.put("data",dataList);
            resultMap.put("message","成功");
            return resultMap;
        } catch (Exception e) {
            resultMap.put("status","500");
            resultMap.put("message",e.getMessage());
            return resultMap;
        }
    }


    @PostMapping("export")
    public ResponseEntity<byte[]> exportQuotation(@RequestBody List<Field> data){


        System.out.println(data);

        return  null;
    }
}
