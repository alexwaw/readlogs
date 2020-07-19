import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, ParseException {

        File file = new File("test.log");
        Scanner scan = new Scanner(file);
        String fileContent = "";

        while (scan.hasNextLine()) {
            fileContent = fileContent.concat(scan.nextLine() + "\n");
        }

        String lines[] = fileContent.split("\\n");
        List<String> list = Arrays.asList(lines);
        List<List<String>> listOfLog = new ArrayList<>();

        for (String l : list) {
            List<String> x = new ArrayList<>();
            x.add(getTime(l));
            x.add(getService(l));
            x.add(getRequestId(l));
            listOfLog.add(x);
        }

        Map<String, List<List<String>>> mappedByService = listOfLog.stream().collect(Collectors.groupingBy(li -> li.get(1)));
        Collection<List<List<String>>> groupedListByService = mappedByService.values();

        for (List<List<String>> x : groupedListByService) {
            int numberOfRequests = x.size() / 2;

            Map<String, List<List<String>>> mappedByService1RequestId = x.stream().collect(Collectors.groupingBy(li -> li.get(2)));
            Collection<List<List<String>>> groupedListRequestId = mappedByService1RequestId.values();
            List<Long> requestTime = new ArrayList<>();

            for (List<List<String>> z : groupedListRequestId) {
                Date requestEntryTime = convertStringToDateFormat(z.get(0).get(0));
                Date responseExitTime = convertStringToDateFormat(z.get(1).get(0));
                long responseTimeInMillis = responseExitTime.getTime() - requestEntryTime.getTime();
                requestTime.add(responseTimeInMillis);
            }
            String maxWaitTime = DurationFormatUtils.formatDuration(Collections.max(requestTime), "s,SSS");
            System.out.println("1. Service: " + x.get(0).get(1) + "\n"
                    + "2. Number of requests: " + numberOfRequests + "\n"
                    + "3. Maximum time of request execution: " + maxWaitTime + " sec." + "\n");
        }
    }

    private static String getService(String lineOfLog) {
        String serviceAndRequestId = StringUtils.substringBetween(lineOfLog, "(", ")");
        String service = StringUtils.substringBetween(serviceAndRequestId, "", ":");
        return service;
    }

    private static String getRequestId(String lineOfLog) {
        String serviceAndRequestId = StringUtils.substringBetween(lineOfLog, "(", ")");
        String requestId = StringUtils.substringAfter(serviceAndRequestId, ":");
        return requestId;
    }

    private static String getTime(String lineOfLog) {
        String time = StringUtils.substring(lineOfLog, 0, 23);
        return time;
    }

    private static Date convertStringToDateFormat(String stringTime) throws ParseException {
        DateFormat timeOfLogFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss,SSS");
        Date date = timeOfLogFormat.parse(stringTime);
        return date;
    }
}
