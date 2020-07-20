import org.apache.commons.lang3.StringUtils;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    static String getService(String lineOfLog) {
        String serviceAndRequestId = StringUtils.substringBetween(lineOfLog, "(", ")");
        String service = StringUtils.substringBetween(serviceAndRequestId, "", ":");
        return service;
    }

    static String getRequestId(String lineOfLog) {
        String serviceAndRequestId = StringUtils.substringBetween(lineOfLog, "(", ")");
        String requestId = StringUtils.substringAfter(serviceAndRequestId, ":");
        return requestId;
    }

    static String getTime(String lineOfLog) {
        String time = StringUtils.substring(lineOfLog, 0, 23);
        return time;
    }

    static Date convertStringToDateFormat(String stringTime) throws ParseException {
        DateFormat timeOfLogFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss,SSS");
        Date date = timeOfLogFormat.parse(stringTime);
        return date;
    }
}
