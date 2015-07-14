package models;


/**
 * Created by apple on 15/7/13.
 */
public class Weather {
    public String temperature;
    public String condition;
    public String getTemperature(){
        return temperature;
    }
    public void settemperature(String temperature){
        this.temperature=temperature;
    }
    public String getcondition(){
        return condition;
    }
    public void setcondition(String condition){
        this.condition=condition;
    }
}
