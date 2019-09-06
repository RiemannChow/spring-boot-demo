package com.riemann.springbootdemo.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * @author riemann
 * @date 2019/05/28 22:07
 */
public class CommonUtil {

    private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    /**
     * Log Forging漏洞校验
     * @param logs
     * @return
     */
    public static String vaildLog(String logs) {
        List<String> list = new ArrayList<String>();
        list.add("%0a");
        list.add("%0A");
        list.add("%0d");
        list.add("%0D");
        list.add("\r");
        list.add("\n");
        String normalize = Normalizer.normalize(logs, Normalizer.Form.NFKC);
        for (String str : list) {
            normalize = normalize.replace(str, "");
        }
        return normalize;
    }

    /**
     * 使用Calendar类获取系统时间
     * @return
     */
    public static String dateFormat() {

        Calendar calendar = Calendar.getInstance();//可以对每个时间域单独修改
        return calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DATE) + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);

        /*long start1 = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println(sdf.format(new Date()));// new Date()为获取当前系统时间
        long end1 = System.currentTimeMillis();
        System.out.println((end1 - start1) + "ms");*/

        /*long start2 = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();//可以对每个时间域单独修改
        System.out.println(calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DATE) + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND));
        long end2 = System.currentTimeMillis();
        System.out.println((end2 - start2) + "ms");*/

        /*long start3 = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        long end3 = System.currentTimeMillis();
        System.out.println(zero);
        System.out.println((end3 - start3) + "ms");*/

        /*long start4 = System.currentTimeMillis();
        long current = System.currentTimeMillis();
        long zero = current/(1000*3600*24)*(1000*3600*24) - TimeZone.getDefault().getRawOffset();
        System.out.println(zero);
        long end4 = System.currentTimeMillis();
        System.out.println((end4 - start4) + "ms");*/
    }

    /**
     * map转json格式
     * @param map
     * @return
     */
    public static JSONObject map2Json(Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject();
        if (map != null) {
            Set<Map.Entry<String, Object>> entrySet = map.entrySet();
            for (Map.Entry<String, Object> entry : entrySet) {
                jsonObject.put(entry.getKey(), entry.getValue());
            }
        }
        return jsonObject;
    }

    /**
     * json转map格式
     * @param jsonObject
     * @return
     */
    public static Map<String, Object> json2Map(JSONObject jsonObject) {
        if (jsonObject == null) {
            return new HashMap<String, Object>(1);
        }
        Map<String, Object> map = new HashMap<String, Object>((int) (jsonObject.size() / 0.75 + 1));
        Set<Map.Entry<String, Object>> entrySet = jsonObject.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    /**
     * list转jsonArray格式
     * @param list
     * @return
     */
    public static JSONArray list2JsonArray(List<Map<String, Object>> list) {
        JSONArray jsonArray = new JSONArray();
        if (CollectionUtils.isEmpty(list)) {
            return jsonArray;
        }
        for (int i = 0; i < list.size(); i++) {
            jsonArray.add(map2Json(list.get(i)));
        }
        return jsonArray;
    }

    /**
     * 构建返回json字符串
     * @param resultCode
     * @param msg
     * @param data
     * @return
     */
    public static String buildResponseData(String resultCode, String msg, Object data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("resultCode", resultCode);
        jsonObject.put("resultMsg", msg);
        jsonObject.put("dataList", data);
        return JSONObject.toJSONString(jsonObject);

    }

    /**
     * 判断时间的格式是否为“yyyyMMddHHmmss”格式的合法日期字符串
     * @param str
     * @return
     */
    public static boolean isValidDate(String str) {
        try {
            if (StringUtils.isNotEmpty(str)) {
                if (str.length() == 14) {
                    // 闰年标志
                    boolean isLeapYear = false;
                    String year = str.substring(0, 4);
                    String month = str.substring(4, 6);
                    String day = str.substring(6, 8);
                    String hour = str.substring(8, 10);
                    String minute = str.substring(10, 12);
                    String second = str.substring(12, 14);
                    int intYear = Integer.parseInt(year);
                    // 判断年份是否合法
                    if (intYear < 1900 || intYear > 2200) {
                        return false;
                    }
                    // 判断是否为闰年
                    if (intYear % 4 == 0 && intYear % 100 != 0 || intYear % 400 == 0) {
                        isLeapYear = true;
                    }
                    // 判断月份
                    // 1.判断月份
                    if (month.startsWith("0")) {
                        String unitMonth = month.substring(1, 2);
                        int intUnitMonth = Integer.parseInt(unitMonth);
                        if (intUnitMonth == 0) {
                            return false;
                        }
                        if (intUnitMonth == 2) {
                            // 获取2月的天数
                            int intDay4February = Integer.parseInt(day);
                            if (isLeapYear) {
                                if (intDay4February > 29) {
                                    return false;
                                }
                            } else {
                                if (intDay4February > 28) {
                                    return false;
                                }
                            }
                        }
                    } else {
                        // 2.判断非0打头的月份是否合法
                        int intMonth = Integer.parseInt(month);
                        if (intMonth != 10 && intMonth != 11 && intMonth != 12) {
                            return false;
                        }
                    }
                    // 判断日期
                    // 1.判断日期
                    if (day.startsWith("0")) {
                        String unit4Day = day.substring(1, 2);
                        int intUnit4Day = Integer.parseInt(unit4Day);
                        if (intUnit4Day == 0) {
                            return false;
                        }
                    } else {
                        // 2.判断非0打头的日期是否合法
                        int intDay = Integer.parseInt(day);
                        if (intDay < 10 || intDay > 31) {
                            return false;
                        }
                    }
                    // 判断时间
                    int intHour = Integer.parseInt(hour);
                    int intMinute = Integer.parseInt(minute);
                    int intSecond = Integer.parseInt(second);
                    if (intHour < 0 || intHour > 23 || intMinute < 0 || intMinute > 59 || intSecond < 0 || intSecond > 59) {
                        return false;
                    }
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkDateTime(String str) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime ldt = null;
        boolean flag = true;
        try {
            ldt = LocalDateTime.parse(str, dtf);
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }


    public static void main(String[] args) {
        /*Calendar calendar = Calendar.getInstance();
        System.out.println("目前时间: " + calendar.getTime());
        System.out.println("Calendar时区: " + calendar.getTimeZone().getID());
        System.out.println("user.timezone: " + System.getProperty("user.timezone"));
        System.out.println("user.country: " + System.getProperty("user.country"));
        System.out.println("默认时区: " + TimeZone.getDefault().getID());*/

        //getSystemTime();

        //isValidDate("20190810240000");

        //System.out.println(checkDateTime("20190907232500"));
    }

}
