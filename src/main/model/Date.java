package model;

import org.json.JSONObject;
import persistence.Writable;

// a Date class that represents dates with day, month, year
public class Date implements Writable {
    private Month month;
    private int day;
    private int year;

    //EFFECTS: constructs date with day, month and year
    public Date(int day, Month month, int year) {
        this.setDay(day);
        this.setMonth(month);
        this.setYear(year);
    }

    // getters and setters
    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    //EFFECTS: returns a string representation of the date
    public String toString() {
        if (this.getDay() < 10) {
            return "0" + this.getDay() + "/" + this.getMonth().toString() + "/" + this.getYear();
        } else {
            return + this.getDay() + "/" + this.getMonth().toString() + "/" + this.getYear();
        }
    }

    // EFFECTS: creates json object of date
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Day", getDay());
        jsonObject.put("Month", getMonth());
        jsonObject.put("Year", getYear());

        return jsonObject;
    }


}
