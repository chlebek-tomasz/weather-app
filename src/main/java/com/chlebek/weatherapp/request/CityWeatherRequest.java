package com.chlebek.weatherapp.request;

import com.chlebek.weatherapp.model.City;
import com.chlebek.weatherapp.model.CityWeatherInfo;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.WeatherConversion;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class CityWeatherRequest {
    private static final Logger logger = LoggerFactory.getLogger(CityWeatherRequest.class);
    private String baseUrl;
    private URL url;
    private ObjectMapper mapper;
    private WeatherConversion conversion;

    private final String API_KEY = "96c5dfc919c93166025dc0dbf3abeae6";

    public CityWeatherRequest(String baseUrl) {
        this.baseUrl = baseUrl;
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        conversion = new WeatherConversion("yyyy-MM-dd HH:mm:ss");
    }

    public CityWeatherInfo getCityInformation(String cityName) {
        logger.debug("Start getCityInformation");

        CityWeatherInfo cityInfo = null;

        try {
            url = new URL(baseUrl+"?q="+cityName+"&appid="+API_KEY);
            URLConnection connection = url.openConnection();
            City city = mapper.readValue(connection.getInputStream(), City.class);
            cityInfo = conversion.cityToCityWeatherInfo(city);
            logger.debug(cityInfo.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.debug("End getCityInformation");

        return cityInfo;
    }

}

