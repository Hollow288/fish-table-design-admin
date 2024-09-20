package com.pond.build.Controller;


import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.pond.build.model.Field;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
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
    public ResponseEntity<byte[]> exportQuotation(@RequestBody List<Field> fieldDatas){

        try {
            Map<String, Object> innerMap = new HashMap<>();
            innerMap.put("dataList", fieldDatas);


            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
            Configure config = Configure.builder()
                    .bind("dataList", policy).build();

            // 渲染模板并将结果写入 ByteArrayOutputStream
            XWPFTemplate.compile("src/main/resources/WordExcelTemplate/模板.docx",config).render(innerMap).write(byteArrayOutputStream);
            // 将 ByteArrayOutputStream 中的数据转换为 byte[]
            byte[] data = byteArrayOutputStream.toByteArray();

            // 设置响应头，指定文件名和文件类型
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", URLEncoder.encode("output.docx", "UTF-8"));

            // 返回 ResponseEntity 对象，设置状态码为 200 OK
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
