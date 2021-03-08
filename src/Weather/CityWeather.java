package Weather;

import com.google.gson.JsonElement;

import java.sql.Timestamp;
import java.util.Date;

public class CityWeather{
    private long m_city_id;
    private String m_city_name;
    private double freq;
    private double threshold;
    private double last_known_temp;
    private double wind_speed;
    private Date date;

    public CityWeather(long m_city_id, String m_city_name, double freq, double threshold) {
        this.m_city_id = m_city_id;
        this.m_city_name = m_city_name;
        this.freq = freq;
        this.threshold = threshold;
        this.last_known_temp = 0.0;
        this.wind_speed = 0.0;
        date = null;
    }


    public Date getTimestamp() {
        return date;
    }

    public void setTimestamp(Date timestamp) {
        this.date = timestamp;
    }


    public double getM_city_id() {
        return m_city_id;
    }

    public void setM_city_id(long m_city_id) {
        this.m_city_id = m_city_id;
    }

    public String getM_city_name() {
        return m_city_name;
    }

    public void setM_city_name(String m_city_name) {
        this.m_city_name = m_city_name;
    }

    public double getFreq() {
        return freq;
    }

    public void setFreq(double freq) {
        this.freq = freq;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public double getLast_known_temp() {
        return last_known_temp;
    }

    public void setLast_known_temp(double last_known_temp) {
        this.last_known_temp = last_known_temp;
    }

    public double getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(double wind_speed) {
        this.wind_speed = wind_speed;
    }

    @Override
    public String toString() {
        return "{" +
                "city_id:" + m_city_id +
                ",\ncity_name:'" + m_city_name + '\'' +
                ",\nfrequency:" + freq +
                ",\nthreshold:" + threshold +
                '}';
    }
}
