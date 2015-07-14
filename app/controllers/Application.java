package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.City;
import models.Weather;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import java.io.IOException;
import java.net.URL;

public class Application extends Controller {
    City city=new City();
    Weather weather=new Weather();
    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public Result getWeatherFromWoeid(String WOEID) {
        JsonNode json = null;
        try {
            String url="https://query.yahooapis.com/v1/public/yql?q=select%20item.condition%20from%20weather.forecast%20where%20woeid%20%3D%20"+WOEID+"&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
            json = new ObjectMapper().readTree(new URL(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (json == null) {
            return badRequest("Expecting Json data");
        } else {
            weather.settemperature(json.findPath("temp").textValue());
            weather.setcondition(json.findPath("text").textValue());
            if (weather.getTemperature() == null) {
                return badRequest("Please input right city");
            } else {
                String result="City Name: "+city.cityName+" Temperature: "+weather.temperature+" conditions: "+weather.condition;
                return ok(result);
            }
        }
    }

    public Result getWeatherFromCityName(String cityName){
        JsonNode json = null;
        city.setCityName(cityName);
        city.setWoeidFromCityName();
        try {
            String url="https://query.yahooapis.com/v1/public/yql?q=select%20item.condition%20from%20weather.forecast%20where%20woeid%20%3D%20"+city.getWOEID()+"&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
            json = new ObjectMapper().readTree(new URL(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (json == null) {
            return badRequest("Expecting Json data");
        } else {
            weather.settemperature(json.findPath("temp").textValue());
            weather.setcondition(json.findPath("text").textValue());
            if (weather.getTemperature() == null) {
                return badRequest("Please input right city");
            } else {
                String result="City Name: "+city.cityName+" Temperature: "+weather.temperature+" conditions: "+weather.condition;
                return ok(result);
            }
        }
    }

    public Result getWOEID(String cityName){
        city.setCityName(cityName);
        city.setWoeidFromCityName();
        return ok(city.getWOEID());
    }

    public Result postAndgetResult(){
        city = Form.form(City.class).bindFromRequest().get();
        city.setWoeidFromCityName();
        return redirect("/v1/weather/city/"+city.cityName);
    }
}
