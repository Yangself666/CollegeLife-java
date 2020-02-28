package xyz.xiaoyu995.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateStringFormatUtils {
    /**
     * 将数据库时间格式化为没有小数点后面的.0
     * @param date
     * @return 格式化之后的时间
     */
    public static String[] dateFormat(String date){
        return date.substring(0,date.indexOf(".")).split(" ");
    }

    public static String getDate(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}
