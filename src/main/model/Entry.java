package model;

// Represents an entry with date, running distance, time, and heart rate
public class Entry {
    private double distance;
    private int time;
    private String pace;
    private int heartRate;
    private Date date;
    private String notes = "Notes: ";

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

    //EFFECTS: constructs an entry with date, distance, time(in minutes), and average heart rate
    public Entry(Date date, int distance, int time, int avgHR) {
        this.date = date;
        this.distance = distance;
        this.time = time;
        heartRate = avgHR;
    }

    //EFFECTS: sets the average pace for this entry
    public void setPace() {
        this.pace = time / distance + " min/km";
    }

    //MODIFIES: this
    //EFFECTS: adds a note to this entry
    public void addNotes(String note) {
        this.notes += " " + note;
    }



}
