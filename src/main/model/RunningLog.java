package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// represents a collection of all the entries over 12 months
public class RunningLog implements Writable {
    private List<List<Entry>> runningLog;
    private List<Entry> january = new ArrayList<>();
    private List<Entry> february = new ArrayList<>();
    private List<Entry> march = new ArrayList<>();
    private List<Entry> april = new ArrayList<>();
    private List<Entry> may = new ArrayList<>();
    private List<Entry> june = new ArrayList<>();
    private List<Entry> july = new ArrayList<>();
    private List<Entry> august = new ArrayList<>();
    private List<Entry> september = new ArrayList<>();
    private List<Entry> october = new ArrayList<>();
    private List<Entry> november = new ArrayList<>();
    private List<Entry> december = new ArrayList<>();
    private List<Entry> selectedMonth;
    private EventLog log = EventLog.getInstance();
    private final String eventPREFIX = "  - ";

    public EventLog getLog() {
        return log;
    }

    public List<List<Entry>> getRunningLog() {
        return runningLog;
    }

    public List<Entry> getJan() {
        return january;
    }

    public List<Entry> getFeb() {
        return february;
    }

    public List<Entry> getMarch() {
        return march;
    }

    public List<Entry> getApril() {
        return april;
    }

    public List<Entry> getMay() {
        return may;
    }

    public List<Entry> getJune() {
        return june;
    }

    public List<Entry> getJuly() {
        return july;
    }

    public List<Entry> getAugust() {
        return august;
    }

    public List<Entry> getSeptember() {
        return september;
    }

    public List<Entry> getOctober() {
        return october;
    }

    public List<Entry> getNovember() {
        return november;
    }

    public List<Entry> getDecember() {
        return december;
    }

    // EFFECTS: constructs the running log with all months
    public RunningLog() {
        runningLog = new ArrayList<>();
        runningLog.add(january);
        runningLog.add(february);
        runningLog.add(march);
        runningLog.add(april);
        runningLog.add(may);
        runningLog.add(june);
        runningLog.add(july);
        runningLog.add(august);
        runningLog.add(september);
        runningLog.add(october);
        runningLog.add(november);
        runningLog.add(december);
    }

//    public RunningLog(List<List<Entry>> log) {
//        Event newEvent = new Event("testing");
//        EventLog.getInstance().logEvent(newEvent);
//
//        runningLog = log;
//    }

//    public void addEntry() {
//        addEntry(null);
//
//    }


    // MODIFIES: this
    // EFFECTS: adds an entry to the month of corresponding list, then logs entry adding event to the eventlog
    //          or throw DuplicateEntryException if entry already
    //          exists
    @SuppressWarnings("methodlength")
    public void addEntry(Entry entry) {

        Month month = entry.getMonth();

//        if (findEntry(entry, month)) {
//            throw new DuplicateEntryException();
//        }

        switch (month) {
            case JAN: {
                this.getJan().add(entry);
                break;
            }
            case FEB: {
                this.getFeb().add(entry);
                break;
            }
            case MAR: {
                this.getMarch().add(entry);
                break;
            }
            case APR: {
                this.getApril().add(entry);
                break;
            }
            case MAY: {
                this.getMay().add(entry);
                break;
            }
            case JUN: {
                this.getJune().add(entry);
                break;
            }
            case JUL: {
                this.getJuly().add(entry);
                break;
            }
            case AUG: {
                this.getAugust().add(entry);
                break;
            }
            case SEP: {
                this.getSeptember().add(entry);
                break;
            }
            case OCT: {
                this.getOctober().add(entry);
                break;
            }
            case NOV: {
                this.getNovember().add(entry);
                break;
            }
            case DEC: {
                this.getDecember().add(entry);
                break;
            }
        }


        Event newEvent = new Event(eventPREFIX + month + " entry added to running log!");
        log.logEvent(newEvent);

    }


    // EFFECTS: return the total distance ran within the month
    public int totalMonthlyDistance(Month month) {
        int totalDistance = 0;

        selectedMonth = selectMonth(month);

        for (Entry entry : selectedMonth) {
            totalDistance += entry.getDistance();
        }

        return totalDistance;
    }

    // EFFECTS: return the total distance ran in 7 days, including the specified date
    public int totalWeeklyDistance(Month month, int day) {

        int totalDistance = 0;

        selectedMonth = selectMonth(month);

        for (Entry entry : selectedMonth) {
            int currentDay = entry.getDate().getDay();
            if (currentDay >= day && currentDay <= day + 6) {
                totalDistance += entry.getDistance();
            }
        }

        return totalDistance;

    }

    // EFFECTS: return the list of entries corresponding to the input month
    public List<Entry> selectMonth(Month month) {
        Event newEvent = new Event(eventPREFIX + "Viewed " + month + " entries");
        log.logEvent(newEvent);

        int monthIndex = month.ordinal();
        return runningLog.get(monthIndex);

    }

    // EFFECTS: return true if an entry with matching day, month and year is found within the list of entries
    //         for the selected month
    private boolean findEntry(Entry entry, Month month) {
        List<Entry> selectedMonth = this.selectMonth(month);

        int day = entry.getDate().getDay();
        int year = entry.getDate().getYear();

        for (Entry e : selectedMonth) {
            if (e.getDate().getDay() == day && e.getDate().getYear() == year) {
                return true;
            }
        }

        return false;
    }


    @Override
    // EFFECTS: creates a json object of a list of entries
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Running Entries", entriesToJson());
        return jsonObject;
    }

    // EFFECTS: creates an array of entry json objects
    public JSONArray entriesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (List<Entry> entrylist : runningLog) {
            for (Entry e : entrylist) {
                jsonArray.put(e.toJson());
            }
        }
        return jsonArray;
    }

    // EFFECTS: returns a string representation of all the events in the eventlog
    public String printLog(EventLog log) {
        StringBuilder events = new StringBuilder();
//        Iterator<Event> iterator = log.iterator();
//        while (iterator.hasNext()) {
//            events.append(iterator.next().toString() + "\n");
//        }
//
        for (Event e : log) {
            events.append(e.toString() + "\n");
        }
        return events.toString();
    }


}
