package cn.pojo.Cassandra;

public class Sea {
    public String id;
    public String StartDate;
    public String StartStation;
    public String StartStationNumber;
    public String EndDate;
    public String EndStation;
    public String EndStationNumber;
    public String TotalDuration;
    public String AccountType;
    public int StartDateTemp;
    public int EndDateTemp;

    public int getStartDateTemp() {
        return StartDateTemp;
    }

    public void setStartDateTemp(int startDateTemp) {
        StartDateTemp = startDateTemp;
    }

    public int getEndDateTemp() {
        return EndDateTemp;
    }

    public void setEndDateTemp(int endDateTemp) {
        EndDateTemp = endDateTemp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getStartStation() {
        return StartStation;
    }

    public void setStartStation(String startStation) {
        StartStation = startStation;
    }

    public String getStartStationNumber() {
        return StartStationNumber;
    }

    public void setStartStationNumber(String startStationNumber) {
        StartStationNumber = startStationNumber;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getEndStation() {
        return EndStation;
    }

    public void setEndStation(String endStation) {
        EndStation = endStation;
    }

    public String getEndStationNumber() {
        return EndStationNumber;
    }

    public void setEndStationNumber(String endStationNumber) {
        EndStationNumber = endStationNumber;
    }

    public String getTotalDuration() {
        return TotalDuration;
    }

    public void setTotalDuration(String totalDuration) {
        TotalDuration = totalDuration;
    }

    public String getAccountType() {
        return AccountType;
    }

    public void setAccountType(String accountType) {
        AccountType = accountType;
    }
}
