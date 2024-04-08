# My Personal Project: Running Log

## A Tool for Tracking Running Progress

This is an application that enables users to track their running 
progress by logging information such as distance, duration, average pace,
heart rate and the type of run they did on different days. Users could set running goals for upcoming days
and mark activities as complete when they finish each run. Additionally, one could
include special notes regarding their running activity on that day. At the end of the week
or month, one would be able to view a list of their running activities along with
information such as the total running distance, number of different types of runs and 
the number of goals achieved. This project is of interest to me because I would like to see an overview of my 
running progress and be able to set different goals during training. 



**User Stories**
- As a user, I want to be able to add a running entry for a certain day.
- As a user, I want to view a list of my runs at the end of the week or month.
- As a user, I want to be able to see my weekly and monthly total running distance.
- As a user, I want to be able to add notes, for example about the weather, to my entries.
- As a user, I want to be able to save all my entries to file.
- As a user, I want to be able to load all my entries from file.

**Instructions for Grader**

- You can generate the first required action related to the user story "adding multiple Xs to a Y" by
  clicking the button labelled "Add Entry" to add a new running entry to the current list.
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by
  selecting a month from the drop-down list, then click "display entries".
- You can locate my visual component by clicking the "view progress" button to
  view a current summary of the monthly total distance represented by a line chart.
- You can save the state of my application by clicking the "SaveAll" button to save the current state of the
  application to file 
- You can reload the state of my application by clicking the "load entries" button to 
  load the state of the application from file

**Phase 4: Task 2**

Sun Apr 07 15:18:29 PDT 2024
  - Loaded all entries to running log
Sun Apr 07 15:18:29 PDT 2024
  - FEB entry added to running log!
Sun Apr 07 15:18:29 PDT 2024
  - MAR entry added to running log!




**Phase 4: Task 3**

I would change the running log type from being represented as a list of a list of entries to a map with each 
month as the key and a list of entries as the value for each month. This refactoring would overall improve
readability and efficiency of this application. For instance, if I want to retrieve entries for a particular month,
instead of iterating through the entire list, I could just retrieve them with month as the key which would be more 
efficient once the list gets large. Also, currently, the RunningApp class is associated with a single month as well as 
a list of months. The single month stores the value entered by a user and is used in multiple places,
but the list of months is only used within a method to verify that the user has entered a valid date. 
Since the list is only used in one place, I think it would be better to declare and instantiate the list within 
the method so that it has local scope instead. 