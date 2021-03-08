package Weather;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;

public class WeatherAPI implements Runnable{
    private CityWeather cityWeather;
    private static final String API_KEY = "e735b6b632e6c008be941b8dbdb346d4";

    public WeatherAPI(CityWeather cityWeather)
    {
        this.cityWeather = cityWeather;
    }


    private Date UnixToDate(String unixTime)
    {
        long epoch = Long.parseLong( unixTime );
        Date d = new Date( epoch * 1000 ); //convert epoch seconds to microseconds
        return d;
    }


    @Override
    public void run() {
        try {
            final String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + cityWeather.getM_city_name() + "&units=metric&appid=" + API_KEY ;
            String result = "";
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null)
                result += line;
            rd.close();

            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse(result);

            //get the temp from the map
            JsonObject main = (JsonObject) jsonObject.get("main");
            double temp = main.get("temp").getAsDouble();

            //get the dt from the map
            String dt = jsonObject.get("dt").getAsString();

            //get wind speed from map
            JsonObject wind = (JsonObject) jsonObject.get("wind");
            double wind_speed = wind.get("speed").getAsDouble();

            if((Math.abs(temp - cityWeather.getLast_known_temp()) * 100) / cityWeather.getLast_known_temp() >= cityWeather.getThreshold())
                System.out.printf("temp was changed the temp was %f and now is: %f%n", cityWeather.getLast_known_temp(), temp);

            cityWeather.setLast_known_temp(temp);
            cityWeather.setTimestamp(UnixToDate(dt));
            cityWeather.setWind_speed(wind_speed);

            System.out.printf("time: %s, city name: %s, temp: %f, wind speed: %f%n", cityWeather.getTimestamp().toString(), cityWeather.getM_city_name(),
                    cityWeather.getLast_known_temp(), cityWeather.getWind_speed());
            Thread.sleep((long) (cityWeather.getFreq() * 1000));//go to sleep
        }catch (IOException | InterruptedException e){
            System.out.println(e.getMessage());
        }
    }
}
