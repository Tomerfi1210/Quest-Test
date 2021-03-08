package Weather;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class Main {

    public static CityWeather[] parseWeather() throws FileNotFoundException {
        JsonParser parser = new JsonParser();
        JsonArray jsonArray = (JsonArray) parser.parse(new FileReader("configuration.json"));

        CityWeather[] cityWeathers = new CityWeather[jsonArray.size()];
        for(int i = 0; i < Objects.requireNonNull(jsonArray).size() ; i++)
        {
            JsonObject jsonObject = (JsonObject) jsonArray.get(i);
            cityWeathers[i] = new CityWeather(jsonObject.get("city_id").getAsLong(), jsonObject.get("city_name").getAsString(),
                    jsonObject.get("frequency").getAsDouble(), jsonObject.get("threshold").getAsDouble());
        }

        return cityWeathers;
    }


    public static void main(String[] args) {
        CityWeather[] cityWeathers = null;
        try{
            cityWeathers = parseWeather();
        }catch (FileNotFoundException e){
            System.out.println("Can't parse the json object or not exist");
        }
        assert cityWeathers != null;
        Thread[] threads = new Thread[cityWeathers.length];


        while(true)
        {
            for(int i = 0 ; i < threads.length ; i++)
            {
                WeatherAPI weatherAPI = new WeatherAPI(cityWeathers[i]);
                threads[i] = new Thread(weatherAPI);
                threads[i].run();

            }
        }


    }
}
