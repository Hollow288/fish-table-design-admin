package com.pond.build.mapper;

import com.pond.build.model.Field;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface TableDesignMapper {
    List<Field> getTableDesignByTableName(@Param("tableName")String tableName);
//
//    List<String> getAllMenuIdByUserId(String userId);
//
//    List<String> selectMenuParentId(@Param("menuIds") List<String> menuIds);
}
