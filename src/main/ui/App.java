package ui;

import com.toedter.calendar.JDateChooser;
import model.Entry;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

import model.Month;
import model.RunningLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

public class App {

    //private RunningApp runningApp;


    private JFrame frame;
    private JPanel windowContent;
    private JPanel buttonsPanel;
    private JTextArea displayField;
    private JButton buttonAddEntry;
    private JButton buttonDisplay;
    private JButton buttonLoad;
    private JLabel labelAllEntry;
    private List<Entry> logEntries;
    private RunningLog runningLog;
    private JScrollPane scrollPane;


    JsonReader jsonReader = new JsonReader(JSON_STORE);

    private static final String JSON_STORE = "./data/runningLog.json";

    public RunningLog getRunningLog() {
        return runningLog;
    }

    public void setRunningLog(RunningLog runningLog) {
        this.runningLog = runningLog;
    }

    /**
     * Create the application.
     */

    public App() {
        initialize();
        //frame.setVisible(true);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        runningLog = new RunningLog();
        // initial display set up
        windowContent = new JPanel();
        buttonsPanel = new JPanel();


        // setting layout manager for this panel
        BorderLayout bl = new BorderLayout();
        GridLayout gl = new GridLayout(3, 1);
        windowContent.setLayout(bl);
        buttonsPanel.setLayout(gl);

        // create and place displayfield and Jlabel
        displayField = new JTextArea(10,15);
        displayField.setEditable(false);
        scrollPane = new JScrollPane(displayField);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        labelAllEntry = new JLabel("All entries: ");
        buttonDisplay = new JButton("Display Entries");
        buttonDisplay.addActionListener(actionEvent -> {
            StringBuilder stringBuilder = new StringBuilder();
            for (Entry e : getRunningLog().selectMonth(Month.JAN)) {
                stringBuilder.append(" Date: " + e.getDate() + "\n"
                        + " Distance: " + e.getDistance() + "\n"
                        + " Time: " + e.getTime() + "\n"
                        + " Average HR: " + e.getHeartRate() + "\n"
                        + " Notes: " + e.getNotes() + "\n" + "\n");

            }
            displayField.setText(stringBuilder.toString());
        });

        windowContent.add("North", labelAllEntry);
        windowContent.add("Center", buttonDisplay);
        windowContent.add("West", scrollPane);

        // add entry button
        buttonAddEntry = new JButton("Add Entry");
        buttonAddEntry.setPreferredSize(new Dimension(80, 30));
        buttonAddEntry.addActionListener(actionEvent -> {
            new EntryPane(runningLog);
        });

//        // save button
//        buttonSave = new JButton("Save entries");
//        buttonSave.addActionListener(actionEvent -> {
//            saveRunningLog();
//            JOptionPane.showConfirmDialog(null, "entries saved", "test",
//                    JOptionPane.PLAIN_MESSAGE);
//        });

        // load button
        buttonLoad = new JButton("load entries");
        buttonLoad.addActionListener(actionEvent -> {
            loadRunningLog();
            JOptionPane.showConfirmDialog(null, "entries loaded", "test",
                    JOptionPane.PLAIN_MESSAGE);
        });

        // add button buttons panel
        buttonsPanel.add(buttonAddEntry);
        buttonsPanel.add(buttonLoad);
        //buttonsPanel.add(buttonSave);

        // add button panel to main panel
        windowContent.add("East", buttonsPanel);
        frame = new JFrame("Running Log");
        frame.setContentPane(windowContent);
        frame.setSize(400, 500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // initialize all entry textfield

//        StringBuilder stringBuilder = new StringBuilder();
//        for (Entry e: getRunningLog().selectMonth(Month.JAN)) {
//            stringBuilder.append(e.toString()).append("\n");
//        }

//        displayField.setText(stringBuilder.toString());


    }


//    // EFFECTS: saves all entries to file
//    private void saveRunningLog() {
//        try {
//            jsonWriter.openWriter();
//            jsonWriter.write(runningLog);
//            jsonWriter.close();
//            System.out.println("Running log saved to: " + JSON_STORE + "\n");
//        } catch (FileNotFoundException e) {
//            System.out.println("Unable to write to file: " + JSON_STORE);
//        }
//    }

    // MODIFIES: this
    // EFFECTS: loads all entries from file
    private void loadRunningLog() {
        try {
            runningLog = jsonReader.read();
            System.out.println(" Running log loaded from: " + JSON_STORE + "\n");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
