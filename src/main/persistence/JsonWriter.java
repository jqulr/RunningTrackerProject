package persistence;


import model.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

// Represents a writer that writes JSON representation of all running entries to file
public class JsonWriter {
    private static final int TAB = 4;
    private String destination;
    private PrintWriter printWriter;
    private final String eventPREFIX = "  - ";


    // EFFECTS: constructs a jsonWriter with a destination
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens destination file for writing, throws FileNotFound exception if file can't be opened
    public void openWriter() throws FileNotFoundException {
        printWriter = new PrintWriter(destination);
    }


    // MODIFIES: this
    // EFFECTS: writes json representation of entry object to file
    public void write(RunningLog log) {
        Event newEvent = new Event(eventPREFIX + "Saved all entries to file");
        EventLog.getInstance().logEvent(newEvent);

        JSONObject jsonObject = log.toJson();
        printWriter.print(jsonObject.toString(TAB));
    }


//    // MODIFIES: this
//    // EFFECTS: writes json representation of entry object to file, prints out the date as a string rather than
//    //          json object with key value pairs
//    public void write(RunningLog log) {
//        JSONObject jsonObject = log.toJson();
//        JSONArray entries = (JSONArray) jsonObject.get("Running Entries");
//
//        for (Object object : entries) {
//            JSONObject entryJson = (JSONObject) object;
//            Iterator<String> keysIterator = entryJson.keys();
//            String s = keysIterator.next();
//
//            if (keysIterator.hasNext() && s.equals("Date")) {
//                JSONObject dateObject = (JSONObject) entryJson.get("Date");
//                Date date = new Date((int) dateObject.get("Day"),
//                        Month.valueOf(dateObject.get("Month").toString()),
//                        (int) dateObject.get("Year"));
//                printWriter.print(date);
//            } else {
//                printWriter.print(entryJson.toString(TAB));
//            }
//        }
//
//    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        printWriter.close();
    }

}
