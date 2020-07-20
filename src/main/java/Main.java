import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.lang3.time.DurationFormatUtils;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {

        //Read logs from URL
        URL url = new URL("https://raw.githubusercontent.com/alexwaw/readlogs/master/test.log");
        Scanner scan = new Scanner(url.openStream());

        String fileContent = "";

        while (scan.hasNextLine()) {
            fileContent = fileContent.concat(scan.nextLine() + "\n");
        }

        String lines[] = fileContent.split("\\n");
        List<String> list = Arrays.asList(lines);
        List<List<String>> listOfLog = new ArrayList<>();

        for (String l : list) {
            List<String> listOfTimeServiceRequestId = new ArrayList<>();
            listOfTimeServiceRequestId.add(Utils.getTime(l));
            listOfTimeServiceRequestId.add(Utils.getService(l));
            listOfTimeServiceRequestId.add(Utils.getRequestId(l));
            listOfLog.add(listOfTimeServiceRequestId);
        }

        Map<String, List<List<String>>> mappedByService = null;

        try {
            mappedByService = listOfLog.stream().collect(Collectors.groupingBy(li -> li.get(1)));
        } catch (Exception e) {
            System.out.println("Please verify the source of log file, " + e);
        }

        Collection<List<List<String>>> groupedListByService = mappedByService.values();

        for (List<List<String>> allRequestsToOneService : groupedListByService) {
            int numberOfRequests = allRequestsToOneService.size() / 2;

            Map<String, List<List<String>>> mappedByService1RequestId = allRequestsToOneService.stream().collect(Collectors.groupingBy(li -> li.get(2)));
            Collection<List<List<String>>> groupedListRequestId = mappedByService1RequestId.values();
            List<Long> requestTime = new ArrayList<>();

            for (List<List<String>> requestsToId : groupedListRequestId) {
                Date requestEntryTime = Utils.convertStringToDateFormat(requestsToId.get(0).get(0));
                Date responseExitTime = Utils.convertStringToDateFormat(requestsToId.get(1).get(0));
                long responseTimeInMillis = responseExitTime.getTime() - requestEntryTime.getTime();
                requestTime.add(responseTimeInMillis);
            }
            String maxWaitTime = DurationFormatUtils.formatDuration(Collections.max(requestTime), "s,SSS");
            System.out.println("1. Service: " + allRequestsToOneService.get(0).get(1) + "\n"
                    + "2. Number of requests: " + numberOfRequests + "\n"
                    + "3. Maximum time of request execution: " + maxWaitTime + " sec." + "\n");
        }
    }
}
