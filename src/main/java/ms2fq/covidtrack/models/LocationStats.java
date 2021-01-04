package ms2fq.covidtrack.models;

public class LocationStats {
    private String county, state;
    private int latestTotal, diffFromPrevDay;

    public int getDiffFromPrevDay() {
        return diffFromPrevDay;
    }

    public void setDiffFromPrevDay(int diffFromPrevDay) {
        this.diffFromPrevDay = diffFromPrevDay;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getLatestTotal() {
        return latestTotal;
    }

    public void setLatestTotal(int latestTotal) {
        this.latestTotal = latestTotal;
    }

    @Override
    public String toString() {
        return "LocationStats{" +
                "county='" + county + '\'' +
                ", state='" + state + '\'' +
                ", latestTotal=" + latestTotal +
                '}';
    }

}
