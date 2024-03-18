package shop.mtcoding.blog._core;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.sql.Date;
import java.sql.Timestamp;

public class MyDateUtil {
    public static String timestampFormat(Timestamp borardDate) {
        Date currentDate = new Date(borardDate.getTime());
        return DateFormatUtils.format(currentDate, "yyyy-MM-dd HH:mm");
    }
}