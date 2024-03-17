package persistence;


import exception.DuplicateEntryException;
import model.Date;
import model.Entry;
import model.Month;
import model.RunningLog;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// Represents a reader that reads a JSON representation of running entries
public class JsonReader {
    private String source;

    // EFFECTS: constructs a reader for reading from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // MODIFIES: rl
    // EFFECTS: reads and returns a list of entries (runninglog), throws IOException if error occurs
    public RunningLog read() throws IOException {

        // use json object to store string data
        JSONObject jsonObject = new JSONObject(readSourceFile(source));

        // creates a running log to store all entries
        RunningLog rl = new RunningLog();

        // parse and return list of entries from jsonObject
        JSONArray jsonArray = jsonObject.getJSONArray("Running Entries");
        for (Object json : jsonArray) {
            JSONObject entry = (JSONObject) json;
            parseEntry(rl, entry);
        }
        return rl;
    }

    // EFFECTS: reads source file and return content in a string, throws IOException if error occurs
    public String readSourceFile(String source) throws IOException {
        String s;
        StringBuilder content = new StringBuilder();

        BufferedReader bf = new BufferedReader(new FileReader(source));
        while ((s = bf.readLine()) != null) {
            content.append(s);
        }

        return content.toString();
    }

    // MODIFIES: rl
    // EFFECTS: parse json representation of entry, adds entry to running log and return it
    public RunningLog parseEntry(RunningLog rl, JSONObject entry) {
        Date date = fromJson((JSONObject) entry.get("Date"));
        int distance = (int) entry.get("Distance");
        int time = (int) entry.get("Time");
        //String pace = (String) entry.get("Pace");
        int avgHR = (int) entry.get("Average HR");
        String notes = entry.getString("Notes");

        Entry newEntry = new Entry(date, distance, time, avgHR);
        newEntry.addNotes(notes);

        rl.addEntry(newEntry);

//        try {
//
//        } catch (DuplicateEntryException e) {
//            System.out.println("Duplicate entry not expected here");
//        }

        return rl;


    }

    // EFFECTS: reads a json object and returns a Date representation
    public Date fromJson(JSONObject jsonObject) {
        int day = (int) jsonObject.get("Day");
        Month month = Month.valueOf(jsonObject.get("Month").toString());
        int year = (int) jsonObject.get("Year");

        return new Date(day, month, year);
    }
}
