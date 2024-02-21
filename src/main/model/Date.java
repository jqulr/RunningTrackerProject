package model;

import org.json.JSONObject;

public class Date {
    private Month month;
    private int day;
    private int year;

    //EFFECTS: constructs date with day, month and year
    public Date(int day, Month month, int year) {
        this.setDay(day);
        this.setMonth(month);
        this.setYear(year);
    }

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

    //EFFECTS: returns a representation of the date
    public String toString() {
        if (this.getDay() < 10) {
            return "0" + this.getDay() + "/" + this.getMonth().toString() + "/" + this.getYear();
        } else {
            return + this.getDay() + "/" + this.getMonth().toString() + "/" + this.getYear();
        }
    }

    // EFFECTS: converts date into a json object
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Day", getDay());
        jsonObject.put("Month", getMonth());
        jsonObject.put("Year", getYear());

        return jsonObject;
    }


}
