package ui;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.toedter.calendar.JDateChooser;
import model.Date;
import model.Entry;
import model.Month;
import model.RunningLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;
import java.awt.*;

public class EntryPane {
    private JFrame entryFrame;
    private JPanel panel;
    private JLabel timeLabel;
    private JLabel distanceLabel;
    private JLabel heartRateLabel;
    private JLabel notesLabel;
    private JLabel dateLabel;
    private JTextField timeText;
    private JTextField distanceText;
    private JTextField hrText;
    private JTextField notesText;
    private JButton buttonAdd;
    private String saveStatusMessage;

    private RunningLog runningLog = new RunningLog();

    JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private static final String JSON_STORE = "./data/runningLog.json";



   // private List<Entry> allEntries;
   // private Entry newEntry;

    //private EntryOperations entryOperations;

    private JDateChooser dateChooser;

    public JTextField getTimeText() {
        return timeText;
    }

    public JTextField getDistanceText() {
        return distanceText;
    }

    public JTextField getHrText() {
        return hrText;
    }

    public JTextField getNotesText() {
        return notesText;
    }

    public String getDateString() {
        return ((JTextField) dateChooser.getDateEditor().getUiComponent()).getText();
    }

    public EntryPane(RunningLog log) {
        //super(runningLog);

        //allEntries = new ArrayList<>();

        panel = new JPanel();
        GridLayout gl = new GridLayout(6, 2);
        panel.setLayout(gl);

        dateLabel = new JLabel("Date:");
        timeLabel = new JLabel("Time:");
        distanceLabel = new JLabel("Distance:");
        heartRateLabel = new JLabel("Average Heart Rate:");
        notesLabel = new JLabel("Notes:");

        timeText = new JTextField(10);
        distanceText = new JTextField(10);
        hrText = new JTextField(10);
        notesText = new JTextField(10);

        buttonAdd = new JButton("Save Entry");
        buttonAdd.addActionListener(actionEvent -> {
            Entry newEntry = createNewEntry();
            newEntry.addNotes(notesText.getText());
            log.addEntry(newEntry);
            //System.out.println(log.getJan().size());
            saveRunningLog(log);
            JOptionPane.showConfirmDialog(null, saveStatusMessage,
                    "Confirming entry", JOptionPane.PLAIN_MESSAGE);
        });

        dateChooser = new JDateChooser();
        dateChooser.setBounds(20, 20, 200, 20);

        panel.add(dateLabel);
        panel.add(dateChooser);
        panel.add(timeLabel);
        panel.add(timeText);
        panel.add(distanceLabel);
        panel.add(distanceText);
        panel.add(heartRateLabel);
        panel.add(hrText);
        panel.add(notesLabel);
        panel.add(notesText);
        panel.add(buttonAdd);

        entryFrame = new JFrame("Adding a new running entry...");
        entryFrame.setContentPane(panel);
        entryFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        entryFrame.setSize(400, 450);
        entryFrame.setVisible(true);

    }

    public Entry createNewEntry() {
        Date date = parseJDateChooser();
        int time = Integer.parseInt(getTimeText().getText());
        double distance = Double.parseDouble(getDistanceText().getText());
        int heartRate = Integer.parseInt(getHrText().getText());

        //newEntry = new Entry(date, distance, time, heartRate);
        //newEntry.addNotes(getNotesText().getText());

        return new Entry(date, distance, time, heartRate);

    }

    public String entryToString(Entry entry) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" Date: " + entry.getDate() + "\n"
                + " Distance: " + entry.getDistance() + "\n"
                + " Time: " + entry.getTime() + "\n"
                + " Average HR: " + entry.getHeartRate() + "\n"
                + " Notes: " + entry.getNotes() + "\n");

        return stringBuilder.toString();
    }

    public Date parseJDateChooser() {
        String date = getDateString();
        String month = date.substring(0,3).toUpperCase();
        Month selectedMonth = Month.valueOf(month);
        //int day = dateChooser.getDate();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateChooser.getDate());
        // Get the day of the month
        int day = calendar.get(java.util.Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);

        return new Date(day, selectedMonth, year);

    }

    // EFFECTS: saves all entries to file
    private void saveRunningLog(RunningLog log) {
        try {
            jsonWriter.openWriter();
            jsonWriter.write(log);
            jsonWriter.close();
            saveStatusMessage = "Running log saved to: " + JSON_STORE + "\n";
        } catch (FileNotFoundException e) {
            saveStatusMessage = "Unable to write to file: " + JSON_STORE;
        }
    }


}
