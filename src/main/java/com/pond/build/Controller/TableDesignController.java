package com.pond.build.Controller;

import com.pond.build.mapper.TableDesignMapper;
import com.pond.build.model.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TableDesignController {
    @Autowired
    private TableDesignMapper tableDesignMapper;

    @GetMapping("/tableDesign/{tableName}")
    public List<Field> getTableDesignByName(@PathVariable("tableName") String tableName){
        return tableDesignMapper.getTableDesignByTableName(tableName);
    }
}
