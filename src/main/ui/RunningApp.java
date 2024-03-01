package ui;

import exception.*;

import model.Date;
import model.Entry;
import model.Month;
import model.RunningLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

//running log application
public class RunningApp {

    List<Month> thirtyOneDays = new ArrayList<>(Arrays.asList(Month.JAN, Month.MARCH,Month.MAY,
                                Month.JULY, Month.AUGUST, Month.OCT, Month.DEC));
    List<Month> thirtyDays = new ArrayList<>(Arrays.asList(Month.APRIL, Month.JUNE,Month.SEPT, Month.NOV));

    private Scanner input = new Scanner(System.in);
    private RunningLog runningLog;
    private Month newMonth;
    private boolean onGoing = true;
    private String choice;
    private int choiceDay;
    private int choiceYear;
    private String choiceMonth;
    private int distance;
    private String note;

    private String selectedMonth;
    private int selectedDay;
    private int selectedYear;
    private int time;
    private int heartRate;
    private int startFrom;
    private int endAt;
    private Date date;

    JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    JsonReader jsonReader = new JsonReader(JSON_STORE);

    private static final String JSON_STORE = "./data/runningLog.json";

    // EFFECTS: launches the running app
    public RunningApp() {
        runningLog = new RunningLog();
        runApp();
    }

    // EFFECTS: display and handle different options
    public void runApp() {
        while (onGoing) {

            System.out.println("please select an option: " + "\n"
                               + "(enter '0' to exit the application)");
            displayOptions();

            int option = input.nextByte();

            if (option == 0) {
                onGoing = false;
                System.out.println("exiting program...");
                break;
            } else {
                handleInput(option);
            }
        }


    }

    // EFFECTS: display the list of options to choose from
    public void displayOptions() {
        System.out.println("1) Add a running entry");
        System.out.println("2) View a list of running entries");
        System.out.println("3) View monthly running distance");
        System.out.println("4) View weekly running distance");
        System.out.println("5) Save entries to file");
        System.out.println("6) Load entries to file");
    }

    @SuppressWarnings("methodlength")
    // EFFECTS: handles difference input choices
    //         1 - add a new entry
    //         2 - choose to view weekly or monthly entries
    //         3 - view total distance in a selected month
    //         4 - view total distance in a selected week
    //         5 - save all entries
    //         6 - load all entries
    public void handleInput(int selectedOption) {

        switch (selectedOption) {
            case 1:
                if (handleLogEntry()) {
                    System.out.println("New entry has been added!" + "\n");
                }
                break;
            case 2:
                System.out.println("please choose WEEKLY or MONTHLY entries: ");
                choice = input.next().toLowerCase();
                if (choice.equals("weekly")) {
                    if (handleWeeklyInput(choice)) {
                        showEntries(findMonth(choiceMonth), choiceDay, choiceYear);
                    }
                    break;
                } else if (choice.equals("monthly")) {
                    if (handleMonthlyInput()) {
                        showEntries(newMonth);
                    }
                    break;
                } else {
                    System.out.println("please select either WEEKLY / MONTHLY" + "\n");
                    break;
                }
            case 3:
                if (handleMonthlyInput()) {
                    distance = runningLog.totalMonthlyDistance(newMonth);
                    displayMonthlyDistanceInfo(distance, newMonth);
                }
                break;
            case 4:
                if (handleWeeklyInput(choice)) {
                    distance = runningLog.totalWeeklyDistance(newMonth, choiceDay);
                    displayWeeklyDistanceInfo(distance, newMonth, choiceDay);
                }
            case 5:
                saveRunningLog();
                break;
            case 6:
                loadRunningLog();
                break;

        }

    }

    // EFFECTS: returns true if info verifies to be all correct, false if InvalidMonthException, InvalidNumberException,
    //          DuplicateEntryException, or InvalidYearException is thrown
    public boolean handleLogEntry() {

        try {
            collectInfo();
            Entry newEntry = new Entry(date, distance, time, heartRate);
            runningLog.addEntry(newEntry);
            newEntry.addNotes(note);
            return true;
        } catch (InvalidMonthException ivme) {
            System.out.println("please make sure valid month entry is entered\n"
                    + "-------------------------------------------------------------------------");
            return false;
        } catch (InvalidNumberException ine) {
            System.out.println("selected day is not in: " + newMonth + "." + selectedYear + "\n");
            return false;
        } catch (DuplicateEntryException dee) {
            System.out.println("This date is already logged, please enter another date" + "\n");
            return false;
        } catch (InvalidYearException iye) {
            System.out.println("please make sure valid year entry is entered\n");
            return false;
        }

    }

    //EFFECTS: returns true if input month, day, year are valid entries, false if exceptions are caught
    public boolean handleWeeklyInput(String choice) {
        System.out.println("please choose a year: ");
        try {
            verifyYear(choiceYear = input.nextInt());
        } catch (InvalidYearException iye) {
            System.out.println("please make sure valid year entry is entered\n");
            return false;
        }
        System.out.println("please choose a month (in all uppercase): ");
        try {
            verifyMonth(choiceMonth = input.next());
            System.out.println("please choose a day in the month: ");
            verifyDate(findMonth(choiceMonth), choiceDay = input.nextInt(), choiceYear);
        } catch (InvalidMonthException ivme) {
            System.out.println("please make sure valid month entry is entered\n"
                        + "-------------------------------------------------------------------------");
            return false;
        } catch (InvalidNumberException ive) {
            System.out.println("selected day is not in: " + newMonth + "." + selectedYear + "\n");
            return false;
        }
        return true;
    }

    //EFFECTS: returns true if month is valid entry, false if either entry is not a valid month
    public boolean handleMonthlyInput() {
        System.out.println("please choose a month (in all uppercase): ");
        choiceMonth = input.next();

        try {
            verifyMonth(choiceMonth);
            newMonth = findMonth(choiceMonth);
        } catch (InvalidMonthException ivme) {
            System.out.println("please make sure valid month entry is entered\n"
                    + "-------------------------------------------------------------------------");
            return false;
        }

        return true;
    }

    //EFFECTS: store user's running entry information, such as date, distance, time, heart rate, and throws
    //         InvalidMonthException, InvalidNumberException, InvalidYearException for invalid entries
    public void collectInfo() throws InvalidMonthException, InvalidNumberException, InvalidYearException {
        System.out.println("Please select one of the following months: ");
        System.out.println("JAN / FEB / MARCH / APRIL / MAY / JUNE / JULY / AUGUST / SEPT / OCT / NOV / DEC");
        selectedMonth = input.next();
        verifyMonth(selectedMonth);

        System.out.println("Please enter the day of the month: ");
        selectedDay = input.nextByte();

        System.out.println("Please enter the year: ");
        verifyYear(selectedYear = input.nextInt());

        verifyDate(findMonth(selectedMonth), selectedDay, selectedYear);

        System.out.println("Please enter distance ran (km): ");
        distance = input.nextInt();

        System.out.println("Please enter time (min): ");
        time = input.nextInt();

        System.out.println("Please enter average Heart Rate: ");
        heartRate = input.nextInt();

        System.out.println("Add note to this entry: ");
        input.nextLine();
        note = input.nextLine();

        date = createDate(selectedDay, newMonth, selectedYear);

    }

    //EFFECTS: create a date
    public Date createDate(int day, Month month, int year) {
        return new Date(day, month, year);
    }

    // EFFECTS: returns the Month corresponding to the input month
    public Month findMonth(String month) {
        // reset month to null, otherwise if month not found, return value is the previously set value
        // newMonth = null;
//        for (Month m : Month.values()) {
//            if (m.toString().equals(month.toUpperCase())) {
//                newMonth = m;
//                break;
//            }
//        }

        // since verifyMonth already checks input, just set newMonth to the Month enum
        newMonth = Month.valueOf(month);
        return  newMonth;

    }

    // REQUIRES: day and year are valid numbers
    // EFFECTS: return entry with matching month, day and year
    public Entry findEntry(Month month, int day, int year) {
        List<Entry> selectedMonth = runningLog.selectMonth(month);

        for (Entry entry : selectedMonth) {
            if (entry.getDate().getDay() == day && entry.getDate().getYear() == year) {
                return entry;
            }
        }

        return null;
    }

    // EFFECTS: return true if date is valid, or throw InvalidNumberException if day is not contained in the month
    public boolean verifyDate(Month month, int day, int year) throws InvalidNumberException {
        //verifyYear(year);

        boolean isLeapYear = year % 4 == 0;

        if (thirtyOneDays.contains(month)) {
            if (day <= 0 || day > 31) {
                throw new InvalidNumberException();
            }
        }
        if (thirtyDays.contains(month)) {
            if (day <= 0 || day > 30) {
                throw new InvalidNumberException();
            }
        }

        checkFebInLeapYear(month, day, isLeapYear);

        return true;
    }

    // EFFECTS: verify month entry, or throw InvalidMonthException if month is not valid
    public void verifyMonth(String inputMonth) throws InvalidMonthException {
//        List<String> monthInYear = new ArrayList<>(Arrays.asList("jan", "feb", "march", "april", "may", "june",
//                                                    "july", "august", "sept", "oct", "nov", "dec"));

//        if (!monthInYear.contains(inputMonth)) {
//            throw new InvalidMonthException();
//        }

        try {
            Month.valueOf(inputMonth);
        } catch (IllegalArgumentException iae) {
            throw new InvalidMonthException();
        }
    }

    // EFFECTS: verify year entry, or throw InvalidYearException if year is beyond 2024
    public void verifyYear(int inputYear) throws InvalidYearException {
        if (inputYear > 2024) {
            throw new InvalidYearException();
        }
    }


    // REQUIRES: has to start on a day with an entry
    // EFFECTS: show list of weekly entries, 7 days including the starting day
    public void showEntries(Month month, int day, int year) {
        List<Entry> selectedMonth = runningLog.selectMonth(month);

        if (selectedMonth.size() == 0) {
            System.out.println("The selected month has no running entries, please select another month"
                    + "\n");
            return;
        }

        Entry entry = findEntry(month, day, year);

        if (findStartEnd(month, selectedMonth, entry)) {

            List<Entry> selectedWeek = selectedMonth.subList(startFrom, endAt);

            int entryCount = 0;
            for (Entry e : selectedWeek) {
                entryCount++;
                System.out.println("Date: " + e.getDate() + " Distance: " + e.getDistance()
                        + " Time: " + e.getTime() + " Average HR: " + e.getHeartRate()
                        + "\n" + e.getNotes() + "\n");
            }
            System.out.println("Total number of entries for the week of " + month + "." + day + " is: "
                                + entryCount + "\n");
        }

    }

    // EFFECTS: show list of entries within month
    public void showEntries(Month month) {

        List<Entry> selectedMonth = runningLog.selectMonth(month);

        if (selectedMonth.size() == 0) {
            System.out.println("The selected month has no running entries, please select another month"
                                + "\n");
            return;
        }

        for (Entry entry : selectedMonth) {
            System.out.println("Date: " + entry.getDate() + " Distance: " + entry.getDistance()
                                + " Time: " + entry.getTime() + " Average HR: " + entry.getHeartRate()
                                + "\n" + entry.getNotes() + "\n");
        }
    }

    // EFFECTS: return true if successfully sets the start and end indexes to create a weekly sublist from
    //          the list of entries for the month
    public boolean findStartEnd(Month month, List<Entry> selectedMonth, Entry entry) {

        if (entry != null) {
            startFrom = selectedMonth.indexOf(entry);
            endAt = startFrom + 7;
        } else {
            System.out.println("There is no entry in the month of " + month + " with the selected day and year"
                    + "\n");
            return false;
        }

        endAt = Math.min(selectedMonth.size(), endAt);
        return true;
    }

    // EFFECTS: check whether feb date is valid in year, throw InvalidNumberException if date is absent in given year
    public void checkFebInLeapYear(Month month, int day, boolean isLeapYear) throws InvalidNumberException {

        if (month == Month.FEB) {
            if (isLeapYear) {
                if (day <= 0 || day >= 29) {
                    throw new InvalidNumberException();
                }
            } else if (day <= 0 || day >= 28) {
                throw new InvalidNumberException();
            }
        }
    }

    // EFFECTS: show monthly distance information
    public void displayMonthlyDistanceInfo(int distance, Month month) {
        System.out.println("You total running distance for the month of "
                            + month + " is: " + distance + " km" + "\n");
    }

    // EFFECTS: show weekly distance information
    public void displayWeeklyDistanceInfo(int distance, Month month, int day) {
        System.out.println("You total running distance for the week of "
                             + day + "/" + month + " is: " + distance + " km" + "\n");
    }

    // EFFECTS: saves all entries to file
    private void saveRunningLog() {
        try {
            jsonWriter.openWriter();
            jsonWriter.write(runningLog);
            jsonWriter.close();
            System.out.println("Running log saved to: " + JSON_STORE + "\n");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

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
