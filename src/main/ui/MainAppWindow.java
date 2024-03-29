package ui;

import model.Entry;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.util.List;

import model.Month;
import model.RunningLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;

// representation of the main window displayed when user starts the application
public class MainAppWindow {

    private JFrame frame;
    private JPanel windowContent;
    private JPanel northPanel;
    private JPanel southPanel;
    private JPanel centerPanel;
    private JTextArea displayField;
    private JButton buttonAddEntry;
    private JButton buttonDisplay;
    private JButton buttonLoad;
    private JButton buttonProgress;
    private JButton buttonShowAllEntries;
    private JButton refresh;
    private JButton buttonSaveAll;
    private JComboBox monthSelection;
    private JLabel labelAllEntry;
    private JLabel selectionLabel;
    private RunningLog runningLog;
    private JScrollPane scrollPane;
    private Month month;
    private String loadStatusMessage;
    private String saveStatusMessage;


    JsonReader jsonReader = new JsonReader(JSON_STORE);
    JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private static final String JSON_STORE = "./data/runningLog.json";

    public RunningLog getRunningLog() {
        return runningLog;
    }

    public void setRunningLog(RunningLog runningLog) {
        this.runningLog = runningLog;
    }


    // EFFECTS: initializes all the contents of the main window
    public MainAppWindow() {
        initialize();
    }

    // EFFECTS: sets the layout of the frame and initializes buttons in the frame
    private void initialize() {

        runningLog = new RunningLog();
        windowContent = new JPanel();
        southPanel = new JPanel();
        centerPanel = new JPanel();
        northPanel = new JPanel();

        // setting layout manager for different panels
        BorderLayout bl = new BorderLayout();
        GridLayout glNorth = new GridLayout(3,1);
        BorderLayout scrollPaneGl = new BorderLayout();
        GridLayout glSouth = new GridLayout(6, 1);
        windowContent.setLayout(bl);
        northPanel.setLayout(glNorth);
        centerPanel.setLayout(scrollPaneGl);
        southPanel.setLayout(glSouth);


        initializeAllButtons();

        // create and place displayfield and Jlabel
        initializeMainDisplayField();
        labelAllEntry = new JLabel("Running Entries: ");
        selectionLabel = new JLabel("Please select a month to view running entries: ");

        addAllButtonstoPanels();
        addPanelstoWindow();
        setMainFrame();
        displayEventLog();
    }

    // EFFECTS: sets up the main display field as a scrollable panel
    private void initializeMainDisplayField() {
        displayField = new JTextArea(100, 100);
        displayField.setEditable(false);
        scrollPane = new JScrollPane(displayField);
        scrollPane.setPreferredSize(new Dimension(100,100));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    }

    // EFFECTS: sets up the main "Running Log" frame with a panel and a set dimension of 650x650
    private void setMainFrame() {
        frame = new JFrame("Running Log");
        windowContent.setBorder(new EmptyBorder(13, 13, 13, 13));
        frame.setContentPane(windowContent);
        frame.setSize(new Dimension(650, 650));
        frame.setVisible(true);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // EFFECTS: sets the windowContent panel by adding northPanel, scrollPane and southPanel to North, Center
    //          and South regions of the panel respectively
    private void addPanelstoWindow() {
        windowContent.add(northPanel, BorderLayout.NORTH);
        windowContent.add(scrollPane, BorderLayout.CENTER);
        windowContent.add(southPanel, BorderLayout.SOUTH);
    }

    // EFFECTS: sets up the northPanel, centerPanel, southPanel by adding different buttons and labels to each panel
    private void addAllButtonstoPanels() {
        northPanel.add(buttonLoad);
        northPanel.add(buttonShowAllEntries);
        northPanel.add(labelAllEntry);
        centerPanel.add(scrollPane);
        southPanel.add(selectionLabel);
        southPanel.add(monthSelection);
        southPanel.add(buttonDisplay);
        southPanel.add(buttonProgress);
        southPanel.add(buttonAddEntry);
        southPanel.add(buttonSaveAll);
    }

    // EFFECTS: initialize all the buttons
    private void initializeAllButtons() {
        initializeLoadButton();
        initializeShowAllEntriesButton();
        initializeDisplayButton();
        initializeMonthSelectionBox();
        initializeProgressButton();
        initializeEntryButton();
        initializeButtonSaveAll();
    }

    // EFFECTS: initialize the save all button and displays a dialog showing a confirmation message when button clicked
    private void initializeButtonSaveAll() {
        buttonSaveAll = new JButton("SaveAll");
        buttonSaveAll.setPreferredSize(new Dimension(80, 40));
        buttonSaveAll.addActionListener(actionEvent -> {
            saveRunningLog(runningLog);
            JOptionPane.showConfirmDialog(null, saveStatusMessage,
                    "Saving Entry...", JOptionPane.PLAIN_MESSAGE);
        });
    }


    // MODIFIES: this
    // EFFECTS: constructs a drop-down box for all the months and sets the month to the selected month
    private void initializeMonthSelectionBox() {
        String[] months = {"...",
                "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
        monthSelection = new JComboBox(months);
        monthSelection.setPreferredSize(new Dimension(80, 40));
        monthSelection.addActionListener(actionEvent -> {
            JComboBox selectedMonth = (JComboBox) actionEvent.getSource();
            String monthString = (String) selectedMonth.getSelectedItem();
            month = Month.valueOf(monthString);
        });
    }

    // EFFECTS: constructs a button to display all the entries within the selected month when clicked
    private void initializeDisplayButton() {
        buttonDisplay = new JButton("Display Entries");
        buttonDisplay.setPreferredSize(new Dimension(80, 40));

        buttonDisplay.addActionListener(actionEvent -> {
            displayField.setText("");
            StringBuilder stringBuilder = new StringBuilder();
            try {
                for (Entry e : getRunningLog().selectMonth(month)) {
                    entryToString(e, stringBuilder);
                }
            } catch (NullPointerException e) {
                JOptionPane.showConfirmDialog(null, "Please select a valid month",
                        "Error", JOptionPane.PLAIN_MESSAGE);
            }
            displayField.setText(stringBuilder.toString());
            displayField.setCaretPosition(0);
        });
    }

    // EFFECTS: constructs a button to view all current entries and
    //          displays all entries in currently in the list when clicked
    private void initializeShowAllEntriesButton() {
        buttonShowAllEntries = new JButton("View Current Entries");
        ImageIcon refreshIcon = new ImageIcon("refresh.png");
        Image resizeRefreshIcon = refreshIcon.getImage().getScaledInstance(20,20, Image.SCALE_SMOOTH);
        buttonShowAllEntries.setIcon(new ImageIcon(resizeRefreshIcon));
        buttonShowAllEntries.setPreferredSize(new Dimension(80, 40));
        buttonShowAllEntries.setFont(buttonShowAllEntries.getFont().deriveFont(Font.PLAIN, 30));
        buttonShowAllEntries.addActionListener(actionEvent -> {
            displayField.setText("");
            StringBuilder stringBuilder = new StringBuilder();
            for (List<Entry> list : runningLog.getRunningLog()) {
                for (Entry e : list) {
                    entryToString(e, stringBuilder);
                }
            }
            displayField.setText(stringBuilder.toString());
            displayField.setCaretPosition(0);
        });

    }

    // EFFECTS: appends a string representation of the entry to the provided string
    private void entryToString(Entry e, StringBuilder string) {
        string.append(" Date: " + e.getDate() + "\n"
                + " Distance (km): " + e.getDistance() + "\n"
                + " Time (min): " + e.getTime() + "\n"
                + " Average HR: " + e.getHeartRate() + "\n"
                + " Notes: " + e.getNotes() + "\n" + "\n");
    }

    // EFFECTS: constructs a button to view progress by displaying a line chart of total monthly running
    //          distance
    private void initializeProgressButton() {
        buttonProgress = new JButton("View Current Progress");
        buttonProgress.setPreferredSize(new Dimension(80, 40));
        buttonProgress.addActionListener(actionEvent -> {
            new LineChartWindow("Summary of Monthly Total Distance", runningLog);
        });
    }

    // EFFECTS: constructs a button to add a new entry to the list with an image icon,
    //          displays a window to record details about the new entry when clicked
    private void initializeEntryButton() {
        ImageIcon addEntryIcon = new ImageIcon("shoe.jpeg");
        Image resizeAddEntryIcon = addEntryIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        buttonAddEntry = new JButton("Add Entry");
        buttonAddEntry.setIcon(new ImageIcon(resizeAddEntryIcon));
        buttonAddEntry.setPreferredSize(new Dimension(80, 40));
        buttonAddEntry.setFont(buttonLoad.getFont());
        buttonAddEntry.addActionListener(actionEvent -> {
            new EntryWindow(runningLog);
        });
    }

    // EFFECTS: constructs a load entries button to load all previously saved entries,
    //          displays a window to show all entries loaded if file was loaded successfully
    private void initializeLoadButton() {
        ImageIcon runIcon = new ImageIcon("running.png");
        Image resizedImage = runIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        buttonLoad = new JButton("Load All Entries");
        buttonLoad.setPreferredSize(new Dimension(80, 40));
        buttonLoad.setFocusable(true);
        buttonLoad.addActionListener(actionEvent -> {
            loadRunningLog();
            JOptionPane.showMessageDialog(null, loadStatusMessage, "Loading Entries...",
                    JOptionPane.PLAIN_MESSAGE, new ImageIcon(resizedImage));
        });
    }

    // EFFECTS: displays eventlog to console when window closed
    private void displayEventLog() {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println(runningLog.printLog(runningLog.getLog()));
                System.exit(0);
            }
//            @Override
//            public void windowClosed(WindowEvent e) {
//                super.windowClosed(e);
//                System.out.println("window closed");
//            }
        });
    }


    // EFFECTS: saves all entries to file
    private void saveRunningLog(RunningLog log) {
        try {
            jsonWriter.openWriter();
            jsonWriter.write(log);
            jsonWriter.close();
            saveStatusMessage = "Current state saved successfully! \n";
        } catch (FileNotFoundException e) {
            saveStatusMessage = "Unable to write to file: " + JSON_STORE;
        }
    }

    // MODIFIES: this
    // EFFECTS: loads all entries from file
    private void loadRunningLog() {
        try {
            runningLog = jsonReader.read();
            loadStatusMessage = "All entries loaded!";
        } catch (IOException e) {
            loadStatusMessage = "Unable to read from file: " + JSON_STORE;
        }
    }
}
