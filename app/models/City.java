package models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

/**
 * Created by apple on 15/7/13.
 */

public class City {

    public String WOEID;
    public String cityName;
    public String getWOEID(){
        return WOEID;
    }
    public void setWOEID(String WOEID){
        this.WOEID=WOEID;
    }
    public String getCityName(){
        return cityName;
    }
    public void setCityName(String cityName){
        this.cityName=cityName;
    }
    public void setWoeidFromCityName(){
        JsonNode json = null;
        try {
            String url="https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20geo.placefinder%20where%20text%3D%22"+cityName+"%22&format=json&diagnostics=true&callback=";
            json = new ObjectMapper().readTree(new URL(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        WOEID=json.findPath("woeid").textValue();
    }
}
