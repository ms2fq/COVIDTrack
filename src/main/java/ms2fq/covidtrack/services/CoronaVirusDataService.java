package ms2fq.covidtrack.services;

import ms2fq.covidtrack.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

/** This service calls to the URL containing the raw CSV data of
 * coronavirus cases
 */
@Service
public class CoronaVirusDataService {

    private static String dataURL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_US.csv";

    private List<LocationStats> statsList = new ArrayList<>();

    public List<LocationStats> getStatsList() {
        return statsList;
    }

    @PostConstruct  //tells Springs to execute this method after the service runs
    @Scheduled(cron = "* * 1 * * *")    //tells Spring to execute this method at the first hour of each day
    public void fetchVirusData() throws IOException, InterruptedException {
        List<LocationStats> newStats = new ArrayList<>();
        //this will make an HTTP call to the URL
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(dataURL)).build();

        //obtain response by sending client the request
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //parse CSV using apache commons CSV
        StringReader reader = new StringReader(response.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
        for (CSVRecord record : records) {
            LocationStats locationStat = new LocationStats();
            locationStat.setCounty(record.get("Admin2"));
            locationStat.setState(record.get("Province_State"));
            locationStat.setLatestTotal(Integer.parseInt(record.get(record.size() - 1)));
            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int prevCases = Integer.parseInt(record.get(record.size() - 2));
            locationStat.setDiffFromPrevDay(latestCases - prevCases);
            System.out.println(locationStat);
            newStats.add(locationStat);
        }
        this.statsList = newStats;
    }

}
