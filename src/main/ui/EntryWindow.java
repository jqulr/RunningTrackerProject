package ui;

import java.io.FileNotFoundException;
import java.util.Calendar;
import com.toedter.calendar.JDateChooser;
import model.Date;
import model.Entry;
import model.Month;
import model.RunningLog;
import persistence.JsonWriter;
import javax.swing.*;
import java.awt.*;

// representation of the window displayed when user wants to add an entry to the existing list
public class EntryWindow {
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

    JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private static final String JSON_STORE = "./data/runningLog.json";


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

    // EFFECTS: constructs the frame to display date selection field and text areas to enter
    //          time, distance, heart rate, and notes information about the entry
    public EntryWindow(RunningLog log) {

        panel = new JPanel();
        GridLayout gl = new GridLayout(6, 2);
        panel.setLayout(gl);

        entryFrame = new JFrame("Adding a new running entry...");
        entryFrame.setContentPane(panel);
        entryFrame.setSize(400, 450);
        entryFrame.setVisible(true);

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
            entryFrame.dispose();
            JOptionPane.showConfirmDialog(null, "Entry added successfully!",
                    "Saving Entry...", JOptionPane.PLAIN_MESSAGE);
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

    }

    // EFFECTS: instantiates and returns a new entry based on entry information entered by the user
    public Entry createNewEntry() {
        Date date = parseJDateChooser();
        int time = Integer.parseInt(getTimeText().getText());
        double distance = Double.parseDouble(getDistanceText().getText());
        int heartRate = Integer.parseInt(getHrText().getText());

        return new Entry(date, distance, time, heartRate);

    }


    // EFFECTS: returns a Date representation of the date selected from the Calendar
    public Date parseJDateChooser() {
        String date = getDateString();
        String month = date.substring(0,3).toUpperCase();
        Month selectedMonth = Month.valueOf(month);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateChooser.getDate());
        // Get the day of the month
        int day = calendar.get(java.util.Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);

        return new Date(day, selectedMonth, year);

    }


}
