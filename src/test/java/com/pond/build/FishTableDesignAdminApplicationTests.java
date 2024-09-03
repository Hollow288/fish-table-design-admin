package com.pond.build;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@SpringBootTest
class FishTableDesignAdminApplicationTests {

    @Test
    void contextLoads() {
//        Object test = null;
//        String testString = String.valueOf(test);

        Map<String, Object> stringObjectHashMap = new HashMap<>();
        Object o = null;

        String test = (String)o;
        System.out.println(test);
    }


    public Map<String,String> autoModifyRemarkOld(Map<String,Object> paraMap){
        String resultModifyRemark = "";

        Map<String,Object> origMap = (Map<String,Object>)paraMap.get("origMap");
        Map<String,Object> modifyMap = (Map<String,Object>)paraMap.get("modifyMap");
        List<String> commonFieldList =  (List<String>)paraMap.get("commonField");
        List<String> commonFieldNameList = (List<String>)paraMap.get("commonFieldName");

        for (int i = 0; i < commonFieldList.size(); i++) {

            if(commonFieldList.get(i).contains("-date")){
                String trueCommonField = commonFieldList.get(i).split("-")[0];

                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy");
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                // System.out.println("================"+origMap.get(trueCommonField)+"==========================");
                // int x = 1/0

                if((origMap.get(trueCommonField) == null || origMap.get(trueCommonField).equals("")) && modifyMap.get(trueCommonField) != null && !modifyMap.get(trueCommonField).equals("")){
                    resultModifyRemark += commonFieldNameList.get(i) + ":变更前【】变更后【" + modifyMap.get(trueCommonField) +"】\n";
                    continue;
                }

                if(origMap.get(trueCommonField) != null && !origMap.get(trueCommonField).equals("") && (modifyMap.get(trueCommonField) == null || modifyMap.get(trueCommonField).equals(""))){

                    ZonedDateTime dateTime = ZonedDateTime.parse(origMap.get(trueCommonField).toString(), inputFormatter);
                    String formattedDate = dateTime.format(outputFormatter);
                    resultModifyRemark += commonFieldNameList.get(i) + ":变更前【" + formattedDate +"】变更后【】\n";
                    continue;
                }
                if(origMap.get(trueCommonField) != null && !origMap.get(trueCommonField).equals("") && modifyMap.get(trueCommonField) != null && !modifyMap.get(trueCommonField).equals("")){
                    ZonedDateTime dateTime = ZonedDateTime.parse(origMap.get(trueCommonField).toString(), inputFormatter);
                    String formattedDate = dateTime.format(outputFormatter);
                    if(!Objects.equals(formattedDate,modifyMap.get(trueCommonField))){
                        resultModifyRemark += commonFieldNameList.get(i) + ":变更前【" + formattedDate +"】变更后【" + modifyMap.get(trueCommonField) +"】\n";
                    }
                }
            }else if(commonFieldList.get(i).contains("-wy")){
                String trueCommonField = commonFieldList.get(i).split("-")[0];
                if((origMap.get(trueCommonField) == null || origMap.get(trueCommonField).equals("")) && modifyMap.get(trueCommonField) != null && !modifyMap.get(trueCommonField).equals("")){
                    BigDecimal modifyAmount =  new BigDecimal((String)modifyMap.get(trueCommonField));
                    BigDecimal resultModifyAmount = modifyAmount.divide(new BigDecimal("10000"), 6, RoundingMode.HALF_UP);
                    resultModifyRemark += commonFieldNameList.get(i) + ":变更前【】变更后【" + resultModifyAmount.stripTrailingZeros().toPlainString() +"万元】\n";
                    continue;
                }

                if(origMap.get(trueCommonField) != null && !origMap.get(trueCommonField).equals("") && (modifyMap.get(trueCommonField) == null || modifyMap.get(trueCommonField).equals(""))){
                    BigDecimal origAmount =  new BigDecimal((String)origMap.get(trueCommonField));
                    BigDecimal resultOrigAmount = origAmount.divide(new BigDecimal("10000"), 6, RoundingMode.HALF_UP);
                    resultModifyRemark += commonFieldNameList.get(i) + ":变更前【"+ resultOrigAmount.stripTrailingZeros().toPlainString() +"万元】变更后【】\n";
                    continue;
                }


                if(origMap.get(trueCommonField) != null && !origMap.get(trueCommonField).equals("") && modifyMap.get(trueCommonField) != null && !modifyMap.get(trueCommonField).equals("")){
                    BigDecimal modifyAmount =  new BigDecimal((String)modifyMap.get(trueCommonField));
                    BigDecimal resultModifyAmount = modifyAmount.divide(new BigDecimal("10000"), 6, RoundingMode.HALF_UP);

                    BigDecimal origAmount =  new BigDecimal((String)origMap.get(trueCommonField));
                    BigDecimal resultOrigAmount = origAmount.divide(new BigDecimal("10000"), 6, RoundingMode.HALF_UP);

                    if(modifyAmount.compareTo(origAmount) != 0){
                        resultModifyRemark += commonFieldNameList.get(i) + ":变更前【"+ resultOrigAmount.stripTrailingZeros().toPlainString() +"万元】变更后【"+ resultModifyAmount.stripTrailingZeros().toPlainString() +"万元】\n";
                    }
                }

            }else if(commonFieldList.get(i).contains("-y")){

                String trueCommonField = commonFieldList.get(i).split("-")[0];
                if((origMap.get(trueCommonField) == null || origMap.get(trueCommonField).equals("")) && modifyMap.get(trueCommonField) != null && !modifyMap.get(trueCommonField).equals("")){
                    BigDecimal modifyAmount =  new BigDecimal((String)modifyMap.get(trueCommonField));
                    resultModifyRemark += commonFieldNameList.get(i) + ":变更前【】变更后【" + modifyAmount.toPlainString() +"元】\n";
                    continue;
                }

                if(origMap.get(trueCommonField) != null && !origMap.get(trueCommonField).equals("") && (modifyMap.get(trueCommonField) == null || modifyMap.get(trueCommonField).equals(""))){
                    BigDecimal origAmount =  new BigDecimal((String)origMap.get(trueCommonField));
                    resultModifyRemark += commonFieldNameList.get(i) + ":变更前【"+ origAmount.toPlainString() +"元】变更后【】\n";
                    continue;
                }


                if(origMap.get(trueCommonField) != null && !origMap.get(trueCommonField).equals("") && modifyMap.get(trueCommonField) != null && !modifyMap.get(trueCommonField).equals("")){
                    BigDecimal modifyAmount =  new BigDecimal((String)modifyMap.get(trueCommonField));
                    BigDecimal origAmount =  new BigDecimal((String)origMap.get(trueCommonField));

                    if(modifyAmount.compareTo(origAmount) != 0){
                        resultModifyRemark += commonFieldNameList.get(i) + ":变更前【"+ origAmount.toPlainString() +"元】变更后【"+ modifyAmount.toPlainString() +"元】\n";
                    }
                }

            }else if(commonFieldList.get(i).contains("-rate")){

                String trueCommonField = commonFieldList.get(i).split("-")[0];
                if((origMap.get(trueCommonField) == null || origMap.get(trueCommonField).equals("")) && modifyMap.get(trueCommonField) != null && !modifyMap.get(trueCommonField).equals("")){
                    BigDecimal modifyAmount =  new BigDecimal((String)modifyMap.get(trueCommonField));
                    BigDecimal resultModifyAmount = modifyAmount.multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP);
                    resultModifyRemark += commonFieldNameList.get(i) + ":变更前【】变更后【" + resultModifyAmount.toPlainString() +"%】\n";
                    continue;
                }

                if(origMap.get(trueCommonField) != null && !origMap.get(trueCommonField).equals("") && (modifyMap.get(trueCommonField) == null || modifyMap.get(trueCommonField).equals(""))){
                    BigDecimal origAmount =  new BigDecimal((String)origMap.get(trueCommonField));
                    BigDecimal resultOrigAmount = origAmount.multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP);
                    resultModifyRemark += commonFieldNameList.get(i) + ":变更前【"+ resultOrigAmount.toPlainString() +"%】变更后【】\n";
                    continue;
                }


                if(origMap.get(trueCommonField) != null && !origMap.get(trueCommonField).equals("") && modifyMap.get(trueCommonField) != null && !modifyMap.get(trueCommonField).equals("")){
                    BigDecimal modifyAmount =  new BigDecimal((String)modifyMap.get(trueCommonField));
                    BigDecimal resultModifyAmount = modifyAmount.multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP);

                    BigDecimal origAmount =  new BigDecimal((String)origMap.get(trueCommonField));
                    BigDecimal resultOrigAmount = origAmount.multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP);

                    if(modifyAmount.compareTo(origAmount) != 0){
                        resultModifyRemark += commonFieldNameList.get(i) + ":变更前【"+ resultOrigAmount.toPlainString() +"%】变更后【"+ resultModifyAmount.toPlainString() +"%】\n";
                    }
                }


            }else if(commonFieldList.get(i).contains("-curr")){
                String trueCommonField = commonFieldList.get(i).split("-")[0];
                //原表单币种
                String origCurrency = (String)origMap.get("origCurrency") == null ? "": (String) origMap.get("origCurrency");
                //变更表单币种
                String modifyCurrency = (String)modifyMap.get("origCurrency") == null ? "": (String) modifyMap.get("origCurrency");

                //处理币种
                if (origMap.get("origCurrencyCode")==null||((String)origMap.get("origCurrencyCode")).isEmpty()||"156".equals((String)origMap.get("origCurrencyCode"))){
                    origCurrency="万元";
                }else {
                    origCurrency="万"+origCurrency;
                }

                if (modifyMap.get("origCurrencyCode")==null||((String)modifyMap.get("origCurrencyCode")).isEmpty()||"156".equals((String)modifyMap.get("origCurrencyCode"))){
                    modifyCurrency="万元";
                }else {
                    modifyCurrency="万"+modifyCurrency;
                }


                if((origMap.get(trueCommonField) == null || origMap.get(trueCommonField).equals("")) && modifyMap.get(trueCommonField) != null && !modifyMap.get(trueCommonField).equals("")){
                    BigDecimal modifyAmount =  new BigDecimal((String)modifyMap.get(trueCommonField));
                    BigDecimal resultModifyAmount = modifyAmount.divide(new BigDecimal("10000"), 6, RoundingMode.HALF_UP);
                    resultModifyRemark += commonFieldNameList.get(i) + ":变更前【】变更后【" + resultModifyAmount.stripTrailingZeros().toPlainString() + modifyCurrency +"】\n";
                    continue;
                }

                if(origMap.get(trueCommonField) != null && !origMap.get(trueCommonField).equals("") && (modifyMap.get(trueCommonField) == null || modifyMap.get(trueCommonField).equals(""))){
                    BigDecimal origAmount =  new BigDecimal((String)origMap.get(trueCommonField));
                    BigDecimal resultOrigAmount = origAmount.divide(new BigDecimal("10000"), 6, RoundingMode.HALF_UP);
                    resultModifyRemark += commonFieldNameList.get(i) + ":变更前【"+ resultOrigAmount.stripTrailingZeros().toPlainString() + origCurrency +"】变更后【】\n";
                    continue;
                }


                if(origMap.get(trueCommonField) != null && !origMap.get(trueCommonField).equals("") && modifyMap.get(trueCommonField) != null && !modifyMap.get(trueCommonField).equals("")){
                    BigDecimal modifyAmount =  new BigDecimal((String)modifyMap.get(trueCommonField));
                    String resultModifyAmount = modifyAmount.divide(new BigDecimal("10000"), 6, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();

                    BigDecimal origAmount =  new BigDecimal((String)origMap.get(trueCommonField));
                    String resultOrigAmount = origAmount.divide(new BigDecimal("10000"), 6, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();

                    //前面将没用的0去除，这里要拼接上币种进行判断


                    if(!(resultOrigAmount + origCurrency).equals((resultModifyAmount + modifyCurrency))){
                        resultModifyRemark += commonFieldNameList.get(i) + ":变更前【"+ resultOrigAmount + origCurrency +"】变更后【"+ resultModifyAmount + modifyCurrency +"】\n";
                    }
                }

            }else{
                if((origMap.get(commonFieldList.get(i)) == null || origMap.get(commonFieldList.get(i)).equals("")) && modifyMap.get(commonFieldList.get(i)) != null && !modifyMap.get(commonFieldList.get(i)).equals("")){
                    resultModifyRemark += commonFieldNameList.get(i) + ":变更前【】变更后【" + modifyMap.get(commonFieldList.get(i)) +"】\n";
                    continue;
                }

                if(origMap.get(commonFieldList.get(i)) != null && !origMap.get(commonFieldList.get(i)).equals("") && (modifyMap.get(commonFieldList.get(i)) == null || modifyMap.get(commonFieldList.get(i)).equals(""))){
                    resultModifyRemark += commonFieldNameList.get(i) + ":变更前【" + origMap.get(commonFieldList.get(i)) +"】变更后【】\n";
                    continue;
                }

                if(!Objects.equals(String.valueOf(origMap.get(commonFieldList.get(i)) == null ? "" : origMap.get(commonFieldList.get(i))),modifyMap.get(commonFieldList.get(i)))){
                    resultModifyRemark += commonFieldNameList.get(i) + ":变更前【"+ origMap.get(commonFieldList.get(i)) +"】变更后【" + modifyMap.get(commonFieldList.get(i)) +"】\n";
                }
            }

        }


        Map<String,String> result = new HashMap<String,String>();
        result.put("result",resultModifyRemark);

        return result;
    }


    public static BigDecimal parseDecimal(Object value ) {
        BigDecimal ret = null;
        if( value != null ) {
            if( value instanceof BigDecimal ) {
                ret = (BigDecimal) value;
            }else if( value instanceof Double ){
                return new BigDecimal(((Double) value)).setScale(2, RoundingMode.HALF_UP);
            } else if( value instanceof String ) {
                if((StringUtils.isNotBlank((String) value))){
                    ret = new BigDecimal( (String) value );
                }
            } else if( value instanceof BigInteger) {
                ret = new BigDecimal( (BigInteger) value );
            } else if( value instanceof Number ) {
                ret = new BigDecimal( ((Number)value).doubleValue() );
            } else {
                return ret;
            }
        }
        return ret;
    }


    public static BigDecimal parseDecimalRate(Object value ) {
        BigDecimal ret = null;
        if( value != null ) {
            if( value instanceof BigDecimal ) {
                ret = (BigDecimal) value;
            }else if( value instanceof Double ){
                return new BigDecimal(((Double) value)).setScale(6, RoundingMode.HALF_UP);
            } else if( value instanceof String ) {
                if((StringUtils.isNotBlank((String) value))){
                    ret = new BigDecimal( (String) value );
                }
            } else if( value instanceof BigInteger) {
                ret = new BigDecimal( (BigInteger) value );
            } else if( value instanceof Number ) {
                ret = new BigDecimal( ((Number)value).doubleValue() );
            } else {
                return ret;
            }
        }
        return ret;
    }

    public Date parseDate(Object str) {
        if(str instanceof LocalDateTime){
            // 获取系统默认时区
            ZoneId zoneId = ZoneId.systemDefault();
            // 将 LocalDateTime 转换为 ZonedDateTime
            ZonedDateTime zonedDateTime = ((LocalDateTime)str).atZone(zoneId);
            // 将 ZonedDateTime 转换为 Instant
            Instant instant = zonedDateTime.toInstant();

            // 将 Instant 转换为 Date
            return Date.from(instant);
        }

        if(str instanceof ZonedDateTime){
            // 将 ZonedDateTime 转换为 Instant
            Instant instant = ((ZonedDateTime)str).toInstant();

            // 将 Instant 转换为 Date
            return Date.from(instant);
        }
        if(str instanceof Date){
            return (Date)str;
        }
        String[] parsePatterns = {
        "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
                "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
                "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM",
                "EEE MMM dd HH:mm:ss z yyyy"};
        if (str == null||Objects.equals(str, "")) {
            return null;
        }
        try {
            return org.apache.commons.lang3.time.DateUtils.parseDate(str.toString(), parsePatterns);
        } catch (Exception e) {
            return null;
        }
    }

    public String toSimpleBigDecimalString(BigDecimal bigDecimal){
        if(bigDecimal == null){
            return "";
        }
        return bigDecimal.stripTrailingZeros().toPlainString();
    }



    public String toSimpleDateString(Date date){

        if(date == null){
            return "";
        }
        // 将 Date 转换为 Instant
        Instant instant = date.toInstant();
        // 获取系统默认时区
        ZoneId zoneId = ZoneId.systemDefault();
        // 将 Instant 转换为 ZonedDateTime
        ZonedDateTime zonedDateTime = instant.atZone(zoneId);
        // 定义格式化器
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 将 ZonedDateTime 格式化为字符串
        return zonedDateTime.format(formatter);
    }

    @Test
    public void testDate(){

//        Date testDate = new Date();
//
//        LocalDateTime dateTime = LocalDateTime.now();
//
//        ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.systemDefault());
//
//
//        Date date = parseDate(testDate);
//        Date date1 = parseDate(dateTime);
//        Date date2 = parseDate(zonedDateTime);
//
//        Date a = null;
//        Date b = null;

//        if(Objects.equals(a,b)){
//
//        }
        Double value = 1.235;

        System.out.println(value instanceof Number);

//        BigDecimal bigDecimal = new BigDecimal("");
//        System.out.println(toSimpleBigDecimalString(bigDecimal));


    }
    //变更摘要生成方法:
    // origMap 源数据Map
    // modifyMap 变更数据Map
    // commonField 需要生成摘要的字段
    // commonFieldName 需要生成摘要的字段名称(后缀-date为日期 -wy为万元 -y为元 -rate为百分比 -curr为特殊币种 -num为数字)
    // origCurrencyField (可选) 告诉方法哪个字段是源表的货币字段
    // modifyCurrencyField (可选) 告诉方法哪个字段是变更表的货币字段
    // 如果不给货币字段,并且使用了-curr后缀,默认按"origCurrency"为源/变更货币字段
    public Map<String, String> autoModifyRemark(Map<String, Object> paraMap) {
        String resultModifyRemark = "";

        Map<String, Object> origMap = (Map<String, Object>) paraMap.get("origMap");
        Map<String, Object> modifyMap = (Map<String, Object>) paraMap.get("modifyMap");
        List<String> commonFieldList = (List<String>) paraMap.get("commonField");
        List<String> commonFieldNameList = (List<String>) paraMap.get("commonFieldName");

        for (int i = 0; i < commonFieldList.size(); i++) {

            if (commonFieldList.get(i).contains("-date")) {
                String trueCommonField = commonFieldList.get(i).split("-")[0];
                if (!Objects.equals(parseDate(origMap.get(trueCommonField)), parseDate(modifyMap.get(trueCommonField)))) {
                    resultModifyRemark += commonFieldNameList.get(i) + ":变更前【" + toSimpleDateString(parseDate(origMap.get(trueCommonField))) + "】变更后【" + toSimpleDateString(parseDate(modifyMap.get(trueCommonField))) + "】\n";
                }
            } else if (commonFieldList.get(i).contains("-wy")) {
                String trueCommonField = commonFieldList.get(i).split("-")[0];
                if ((parseDecimal(origMap.get(trueCommonField)) == null ? new BigDecimal(0) : parseDecimal(origMap.get(trueCommonField))).compareTo(parseDecimal(modifyMap.get(trueCommonField)) == null ? new BigDecimal(0) : parseDecimal(modifyMap.get(trueCommonField))) != 0) {
                    resultModifyRemark += commonFieldNameList.get(i) + ":变更前【" + (parseDecimal(origMap.get(trueCommonField)) == null ? "" : toSimpleBigDecimalString(parseDecimal(origMap.get(trueCommonField)).divide(new BigDecimal("10000"), 6, RoundingMode.HALF_UP)) + "万元") + "】变更后【" + (parseDecimal(modifyMap.get(trueCommonField)) == null ? "" : toSimpleBigDecimalString(parseDecimal(modifyMap.get(trueCommonField)).divide(new BigDecimal("10000"), 6, RoundingMode.HALF_UP)) + "万元") + "】\n";
                }
            } else if (commonFieldList.get(i).contains("-y")) {
                String trueCommonField = commonFieldList.get(i).split("-")[0];
                if ((parseDecimal(origMap.get(trueCommonField)) == null ? new BigDecimal(0) : parseDecimal(origMap.get(trueCommonField))).compareTo(parseDecimal(modifyMap.get(trueCommonField)) == null ? new BigDecimal(0) : parseDecimal(modifyMap.get(trueCommonField))) != 0) {
                    resultModifyRemark += commonFieldNameList.get(i) + ":变更前【" + (parseDecimal(origMap.get(trueCommonField)) == null ? "" : toSimpleBigDecimalString(parseDecimal(origMap.get(trueCommonField))) + "元") + "】变更后【" + (parseDecimal(modifyMap.get(trueCommonField)) == null ? "" : toSimpleBigDecimalString(parseDecimal(modifyMap.get(trueCommonField))) + "元") + "】\n";
                }
            } else if (commonFieldList.get(i).contains("-rate")) {
                String trueCommonField = commonFieldList.get(i).split("-")[0];
                if ((parseDecimalRate(origMap.get(trueCommonField)) == null ? new BigDecimal(0) : parseDecimalRate(origMap.get(trueCommonField))).compareTo(parseDecimalRate(modifyMap.get(trueCommonField)) == null ? new BigDecimal(0) : parseDecimalRate(modifyMap.get(trueCommonField))) != 0) {
                    resultModifyRemark += commonFieldNameList.get(i) + ":变更前【" + (parseDecimalRate(origMap.get(trueCommonField)) == null ? "" : toSimpleBigDecimalString(parseDecimalRate(origMap.get(trueCommonField)).multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP)) + "%") + "】变更后【" + (parseDecimalRate(modifyMap.get(trueCommonField)) == null ? "" : toSimpleBigDecimalString(parseDecimalRate(modifyMap.get(trueCommonField)).multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP)) + "%") + "】\n";
                }
            } else if (commonFieldList.get(i).contains("-curr")) {
                String trueCommonField = commonFieldList.get(i).split("-")[0];
                String origCurrency;
                String modifyCurrency;
                //先检查是否传入了币种参数
                if (paraMap.get("origCurrencyField") != null && paraMap.get("modifyCurrencyField") != null) {
                    origCurrency = (String) origMap.get(paraMap.get("origCurrencyField").toString());
                    modifyCurrency = (String) origMap.get(paraMap.get("modifyCurrencyField").toString());
                } else {
                    //没有传入币种就使用默认字段
                    //原表单币种
                    origCurrency = origMap.get("origCurrency") == null ? "" : (String) origMap.get("origCurrency");
                    //变更表单币种
                    modifyCurrency = modifyMap.get("origCurrency") == null ? "" : (String) modifyMap.get("origCurrency");
                }
                //处理币种
                if (StringUtils.isBlank(origCurrency) || origCurrency.equals("人民币元")) {
                    origCurrency = "万元";
                } else {
                    origCurrency = "万" + origCurrency;
                }
                if (StringUtils.isBlank(modifyCurrency) || modifyCurrency.equals("人民币元")) {
                    modifyCurrency = "万元";
                } else {
                    modifyCurrency = "万" + modifyCurrency;
                }
                if ((parseDecimal(origMap.get(trueCommonField)) == null ? new BigDecimal(0) : parseDecimal(origMap.get(trueCommonField))).compareTo(parseDecimal(modifyMap.get(trueCommonField)) == null ? new BigDecimal(0) : parseDecimal(modifyMap.get(trueCommonField))) != 0) {
                    resultModifyRemark += commonFieldNameList.get(i) + ":变更前【" + (parseDecimal(origMap.get(trueCommonField)) == null ? "" : toSimpleBigDecimalString(parseDecimal(origMap.get(trueCommonField)).divide(new BigDecimal("10000"), 6, RoundingMode.HALF_UP)) + origCurrency) + "】变更后【" + (parseDecimal(modifyMap.get(trueCommonField)) == null ? "" : toSimpleBigDecimalString(parseDecimal(modifyMap.get(trueCommonField)).divide(new BigDecimal("10000"), 6, RoundingMode.HALF_UP)) + modifyCurrency) + "】\n";
                }
            }else if(commonFieldList.get(i).contains("-num")){
                String trueCommonField = commonFieldList.get(i).split("-")[0];
                if ((parseDecimal(origMap.get(trueCommonField)) == null ? new BigDecimal(0) : parseDecimal(origMap.get(trueCommonField))).compareTo(parseDecimal(modifyMap.get(trueCommonField)) == null ? new BigDecimal(0) : parseDecimal(modifyMap.get(trueCommonField))) != 0) {
                    resultModifyRemark += commonFieldNameList.get(i) + ":变更前【" + (parseDecimal(origMap.get(trueCommonField)) == null ? "" : toSimpleBigDecimalString(parseDecimal(origMap.get(trueCommonField)))) + "】变更后【" + (parseDecimal(modifyMap.get(trueCommonField)) == null ? "" : toSimpleBigDecimalString(parseDecimal(modifyMap.get(trueCommonField)))) + "】\n";
                }
            } else {
                if (!Objects.equals(String.valueOf(origMap.get(commonFieldList.get(i)) == null ? "" : origMap.get(commonFieldList.get(i))), String.valueOf(modifyMap.get(commonFieldList.get(i)) == null ? "" : modifyMap.get(commonFieldList.get(i))))) {
                    resultModifyRemark += commonFieldNameList.get(i) + ":变更前【" + (origMap.get(commonFieldList.get(i)) == null ? "" : origMap.get(commonFieldList.get(i))) + "】变更后【" + (modifyMap.get(commonFieldList.get(i)) == null ? "" : modifyMap.get(commonFieldList.get(i))) + "】\n";
                }
            }

        }
        Map<String, String> result = new HashMap<String, String>();
        result.put("result", resultModifyRemark);

        return result;
    }


    public static String convertToDatabaseField(String className) {
        // 将每个大写字母前面添加下划线
        String result = className.replaceAll("([a-z])([A-Z])", "$1_$2");
        // 将所有字母转换为小写
        result = result.toLowerCase();
        // 返回最终的数据库字段格式
        return result;
    }


    @Test
    public void testField(){
//        System.out.println(convertToDatabaseField("robxzhNm"));
//        System.out.println(convertToDatabaseField("FyMdm41"));
//        System.out.println(convertToDatabaseField("invoicecode"));
        String[] compareArr = {};


        String[] compareNameArr = {};

        findStringIndex(compareNameArr,"进度款支付比例");
        System.out.println(compareArr[51]);
//        System.out.println(compareArr[149]);
//        System.out.println(compareArr[156]);


    }


    public static void findStringIndex(String[] array, String target) {

        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (target.equals(array[i])) {
                System.out.println("第" + (count+1) + "次出现,下标为:" + i );
                count++;
//                return i; // 找到字符串，返回下标
            }
        }
    }





}
