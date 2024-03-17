package ui;

import com.toedter.calendar.JDateChooser;
import model.Entry;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

public class App {

    private RunningApp runningApp;


    private JFrame frame;
    private JPanel windowContent;
    private JTextField displayField;
    private JButton buttonAddEntry;
    private JLabel labelAllEntry;
    private List<String> logEntries;

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
        // initial display set up
        windowContent = new JPanel();

        // setting layout manager for this panel
        BorderLayout bl = new BorderLayout();
        windowContent.setLayout(bl);

        // create and place displayfield and Jlabel
        labelAllEntry = new JLabel("All entries: ");
        displayField = new JTextField(10);
        windowContent.add("North", labelAllEntry);
        windowContent.add("West", displayField);

        // create buttons
        buttonAddEntry = new JButton("Add Entry");
        buttonAddEntry.setPreferredSize(new Dimension(80, 30));
        buttonAddEntry.addActionListener(actionEvent -> {
            new EntryPane();
        });

        // add button to panel
        windowContent.add("East", buttonAddEntry);
        frame = new JFrame("Running Log");
        frame.setContentPane(windowContent);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // initialize all entry textfield
        logEntries = new ArrayList<>();
        logEntries.add("entry 1");
        logEntries.add("entry 2");
        displayField.setEditable(false);
        StringBuilder stringBuilder = new StringBuilder();
        for (String e : logEntries) {
            stringBuilder.append(e).append("\n");
        }

        displayField.setText(stringBuilder.toString());


    }
}
