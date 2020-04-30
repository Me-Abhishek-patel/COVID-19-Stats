package com.example.covid_19stats;

public class IndvCountry {

    /*variables to store data*/
    private String todayCases,deaths,todayDeaths,recovered,active,flag,country,cases;

    public IndvCountry() {

    }

    /**
     * @param todayCases
     * @param deaths
     * @param todayDeaths
     * @param recovered
     * @param active
     * @param flag
     * @param country
     * @param cases
     */
    public IndvCountry(String todayCases, String deaths, String todayDeaths, String recovered, String active, String flag, String country, String cases){
        this.todayCases = todayCases;
        this.flag = flag;
        this.country = country;
        this.cases = cases;
        this.deaths = deaths;
        this.todayDeaths = todayDeaths;
        this.recovered = recovered;
        this.active = active;

    }

    /**
     * getter and setter methods
     * @return
     */
    public String getTodayCases() {
        return todayCases;
    }

    public void setTodayCases(String todayCases) {
        this.todayCases = todayCases;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getTodayDeaths() {
        return todayDeaths;
    }

    public void setTodayDeaths(String todayDeaths) {
        this.todayDeaths = todayDeaths;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }
}
