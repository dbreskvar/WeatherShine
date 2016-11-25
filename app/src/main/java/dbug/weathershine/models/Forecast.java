package dbug.weathershine.models;

import java.util.ArrayList;

public class Forecast {

    City city;
    Coordinates coord;
    String country, cod;
    Float message;
    Integer cnt;
    ArrayList<DayItem> list;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Coordinates getCoord() {
        return coord;
    }

    public void setCoord(Coordinates coord) {
        this.coord = coord;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Float getMessage() {
        return message;
    }

    public void setMessage(Float message) {
        this.message = message;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public ArrayList<DayItem> getList() {
        return list;
    }

    public void setList(ArrayList<DayItem> list) {
        this.list = list;
    }
}
