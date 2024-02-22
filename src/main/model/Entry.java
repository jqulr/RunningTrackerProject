package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents an entry with date, running distance, time, and heart rate
public class Entry implements Writable {
    private double distance;
    private int time;
    private String pace;
    private int heartRate;
    private Date date;
    private String notes;

    //getters and setters
    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getPace() {
        return pace;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public Date getDate() {
        return date;
    }

    public Month getMonth() {
        return this.date.getMonth();
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    //EFFECTS: constructs an entry with date, distance, time(in minutes), average heart rate, empty notes, and
    //         sets the pace
    public Entry(Date date, double distance, int time, int avgHR) {
        this.setDate(date);
        this.setDistance(distance);
        this.setTime(time);
        this.setHeartRate(avgHR);
        this.setNotes("");
        this.setPace();
    }

    //EFFECTS: sets the average pace of time / distance in min/km for an entry
    public void setPace() {
        this.pace = String.format("%.2f", time / distance) + " min/km";
    }

    //MODIFIES: this
    //EFFECTS: adds a note to this entry
    public void addNotes(String note) {
        this.notes += " - " + note;
    }

    @Override
    // EFFECTS: creates a json object for an entry
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Pace", getPace());
        jsonObject.put("Date", getDate().toJson());
        jsonObject.put("Distance", getDistance());
        jsonObject.put("Time", getTime());
        jsonObject.put("Average HR", getHeartRate());
        jsonObject.put("Notes", getNotes());

        return jsonObject;
    }

}
