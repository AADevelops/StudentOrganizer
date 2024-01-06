/* StudentOrganizer class
 * @description: Student portal for organizer.
 * @author: Amun Ahmad
 * @version: 5/16/2023
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class StudentOrganizer {
    public static void main(String[] args) throws IOException {
        // Main Menu Option Constants
        final String SELECT_CALENDAR = "s";
        final String MODIFY_CALENDAR_NAME = "m";
        final String CREATE_CALENDAR = "n";
        final String DELETE_CALENDAR = "d";
        
        // Calendar Menu Option Constants
        final String CREATE_EVENT = "1";
        final String DELETE_EVENT = "2";
        final String PRINT_DAY_AGENDA = "3";
        final String PRINT_ALL_EVENTS = "4";
        final String SWITCH_CALENDAR = "5";
        final String QUIT = "0";
        final String RESET_CALENDAR = "r";

        // Calendar Management
        ArrayList<Calendar> calendars = new ArrayList<Calendar>();
        Calendar activeCalendar = null;

        // Key Program Variables
        final String DATABASE_FILE = "./database.txt";
        Scanner input = new Scanner(System.in);
        boolean introActive = true;
        boolean programActive = true;

        // Loading Saved Data
        loadInformation(DATABASE_FILE, calendars);

        // Main Program Loop
        while (programActive) {
            // Introduction Loop
            if (introActive) {
                System.out.println("\n  ____  _             _            _      ___                        _"
                                 + "\n / ___|| |_ _   _  __| | ___ _ __ | |_   / _ \\ _ __ __ _  __ _ _ __ (_)_______ _ __"
                                 + "\n \\___ \\| __| | | |/ _` |/ _ | '_ \\| __| | | | | '__/ _` |/ _` | '_ \\| |_  / _ | '__|"
                                 + "\n  ___) | |_| |_| | (_| |  __| | | | |_  | |_| | | | (_| | (_| | | | | |/ |  __| |"
                                 + "\n |____/ \\__|\\__,_|\\__,_|\\___|_| |_|\\__|  \\___/|_|  \\__, |\\__,_|_| |_|_/___\\___|_|"
                                 + "\n                                                   |___/");

                System.out.print("\n|------------------------------------|"
                               + "\n|         s. Select Calendar         |"
                               + "\n|       m. Modify Calendar Name      |"
                               + "\n|       n. Create New Calendar       |"
                               + "\n|         d. Delete Calendar         |"
                               + "\n|------------------------------------|"
                               + "\n\n[SYSTEM]: Please enter an option: > ");
                String introChoice = input.nextLine().toLowerCase();

                if (introChoice.equals(SELECT_CALENDAR)) {
                    activeCalendar = selectCalendarsPrompt(calendars, input);

                    // Empty Active Calendar Check
                    if (activeCalendar != null) {
                        introActive = false;
                    }
                } else if (introChoice.equals(MODIFY_CALENDAR_NAME)) {
                    Calendar targetCalendar = selectCalendarsPrompt(calendars, input);

                    // Invalid Calendar Check
                    if (targetCalendar != null) {
                        System.out.print("[SYSTEM]: Please enter a new name for the calendar: > ");
                        String newName = input.nextLine();

                        targetCalendar.setCalendarName(newName);
                        System.out.println("[SYSTEM]: Calendar name has been modified.");
                    }
                } else if (introChoice.equals(CREATE_CALENDAR)) {
                    System.out.print("[SYSTEM]: Please enter the name for the calendar: > ");
                    String calendarName = input.nextLine();

                    // Creating New Calendar - Setting as Active
                    calendars.add(new Calendar(calendarName));
                    activeCalendar = calendars.get(calendars.size() - 1);
                    introActive = false;
                } else if (introChoice.equals(DELETE_CALENDAR)) {
                    Calendar targetCalendar = selectCalendarsPrompt(calendars, input);

                    // Existing Calendars Check
                    if (calendars.size() > 0) {
                        int index = -1;

                        // Finding Calendar in Master List
                        for (int i = 0; i < calendars.size(); i++) {
                            if (calendars.get(i) == targetCalendar) {
                                index = i;
                            }
                        }

                        calendars.remove(index);
                        System.out.println("[SYSTEM]: Calendar has been deleted.");
                    }
                } else {
                    System.out.println("\n[SYSTEM-ERROR]: Please enter a valid option.");
                }
            } else {
                System.out.print("\n|------------------------------------|"
                               + "\n|        1. Create New Event         |"
                               + "\n|          2. Delete Event           |"
                               + "\n|        3. Print Day Agenda         |"
                               + "\n|        4. Print All Events         |"
                               + "\n|        5. Switch Calendar          |"
                               + "\n|              0. Quit               |"
                               + "\n|------------------------------------|"
                               + "\n|         r. Reset Calendar          |"
                               + "\n|------------------------------------|"
                               + "\n\nActive Calendar: " + activeCalendar.getCalendarName()
                               + "\n[SYSTEM]: Please enter an option: > ");
                String studentChoice = input.nextLine();

                if (studentChoice.equals(CREATE_EVENT)) {
                    // Getting New Event Details
                    System.out.print("\n[SYSTEM]: Please enter the event name: > ");
                    String eventName = input.nextLine();

                    System.out.print("\n[SYSTEM]: Please enter any event notes: > ");
                    String eventNotes = input.nextLine();

                    System.out.println("\n[SYSTEM]: Please enter the following details for the date:");
                    LocalDate eventDate = getDatePrompt(input);

                    System.out.println("\n[SYSTEM]: Please enter the following time details, in 24-hour format - as an integer = ####:");
                    System.out.print("[SYSTEM]: Please enter the start time: > ");
                    int startTime = input.nextInt();
                    System.out.print("[SYSTEM]: Please enter the end time: > ");
                    int endTime = input.nextInt();

                    input.nextLine();

                    // Adding Event to Calendar
                    activeCalendar.addEvent(new Event(eventName, eventNotes, eventDate, startTime, endTime));
                } else if (studentChoice.equals(DELETE_EVENT)) {
                    // Getting Details and Searching for Day
                    System.out.println("[SYSTEM]: Please enter the date details of the event you would like to delete.");
                    LocalDate targetDate = getDatePrompt(input);
                    ArrayList<Event> agendaEvents = activeCalendar.getDayAgenda(targetDate);

                    // Listing Event Options (to delete)
                    for (int i = 0; i < agendaEvents.size(); i++) {
                        System.out.println((i + 1) + ". " + agendaEvents.get(i).getName());
                    }
                    System.out.print("[SYSTEM]: Please enter the event you would like deleted: > ");
                    int eventIndex = input.nextInt();

                    input.nextLine();

                    // Deleting Event
                    if (eventIndex > agendaEvents.size() || eventIndex < 1) {
                        System.out.println("[SYSTEM-ERROR]: Please enter a valid option.");
                    } else {
                        activeCalendar.deleteEvent(agendaEvents.get(eventIndex - 1));
                        System.out.println("[SYSTEM]: Event has been deleted.");
                    }
                } else if (studentChoice.equals(PRINT_DAY_AGENDA)) {
                    // Getting Details and Searching for Day Agenda
                    System.out.println("[SYSTEM]: Please enter the date details that you would like the day agenda displayed for.");
                    LocalDate targetDate = getDatePrompt(input);
                    ArrayList<Event> dayAgenda = activeCalendar.getDayAgenda(targetDate);

                    // Existing Day Agenda (Node) Check
                    if (dayAgenda != null) {
                        for (int i = 0; i < dayAgenda.size(); i++) {
                            System.out.println(dayAgenda.get(i).toString());
                        }
                    } else if (dayAgenda == null || dayAgenda.size() == 0) {
                        System.out.println("[SYSTEM]: There are no events for the date specified.");
                    }
                } else if (studentChoice.equals(PRINT_ALL_EVENTS)) {
                    ArrayList<Event> calendarEvents = activeCalendar.getAllEvents();

                    // Existing Calendar Events Check
                    if (calendarEvents.size() != 0) {
                        for (int i = 0; i < calendarEvents.size(); i++) {
                            System.out.println(calendarEvents.get(i).toString());
                        }
                    } else {
                        System.out.println("[SYSTEM]: There are no events in this calendar.");
                    }
                } else if (studentChoice.equals(SWITCH_CALENDAR)) {
                    introActive = true;
                } else if (studentChoice.equals(QUIT)) {
                    System.out.println("\n[SYSTEM]: Saving all information into database...");
                    saveInformation(DATABASE_FILE, calendars); // Information Save
                    System.out.println("\n[SYSTEM]: Exiting student organizer application.");

                    // Closing Entire Program
                    input.close();
                    introActive = false;
                    programActive = false;
                } else if (studentChoice.equals(RESET_CALENDAR)) {
                    activeCalendar.resetCalendar();

                    System.out.println("[SYSTEM]: Calendar has been reset.");
                } else {
                    System.out.println("\n[SYSTEM-ERROR]: Please enter a valid option.");
                }
            }
        }
    }

    // Loading Information from Database
    public static void loadInformation(String databaseFile, ArrayList<Calendar> calendars) throws IOException {
        final String NEW_CALENDAR = "!nc";
        final String NEW_EVENT = "!ne";
        final String SEPARATOR = " !next ";
        Scanner fileReader = new Scanner(new File(databaseFile));
        Calendar activeCalendar = null;

        while (fileReader.hasNext()) {
            // Decision Tree for Creating New Objects
            String indicator = fileReader.nextLine();
            if (indicator.equals(NEW_CALENDAR)) {
                calendars.add(new Calendar(fileReader.nextLine()));
                activeCalendar = calendars.get(calendars.size() - 1);
            } else if (indicator.equals(NEW_EVENT)) {
                // Separating Event Data
                String[] eventDetails = (fileReader.nextLine()).split(SEPARATOR);
                activeCalendar.addEvent(new Event(eventDetails[0], eventDetails[1], LocalDate.parse(eventDetails[2]), Integer.parseInt(eventDetails[3]), Integer.parseInt(eventDetails[4])));
            }
        }
        fileReader.close();
    }

    public static void printCalendars(ArrayList<Calendar> calendars) {
        // Looping Through Calendar List
        for (int i = 0; i < calendars.size(); i++) {
            System.out.println((i + 1) + ". " + calendars.get(i).getCalendarName());
        }
    }

    // Prompt for Users to Select a Calendar
    public static Calendar selectCalendarsPrompt(ArrayList<Calendar> calendars, Scanner input) {
        if (calendars.size() == 0) {
            System.out.println("[SYSTEM-ERROR]: There are no existing calendars.");
        } else {
            printCalendars(calendars);
            System.out.print("[SYSTEM]: Please select a calendar: > ");
            int calendarChoice = input.nextInt();
            input.nextLine();

            if (calendarChoice > calendars.size() || calendarChoice < 1) {
                System.out.println("[SYSTEM-ERROR]: Please enter a valid option.");
            } else {
                return calendars.get(calendarChoice - 1);
            }
        }

        return null;
    }

    // Prompt for Users to Enter a Date
    public static LocalDate getDatePrompt(Scanner input) {
        System.out.print("[SYSTEM]: Please enter the month number: > ");
        int monthNumber = input.nextInt();
        System.out.print("[SYSTEM]: Please enter the day of the month: > ");
        int dayNumber = input.nextInt();
        System.out.print("[SYSTEM]: Please enter the year: > ");
        int year = input.nextInt();

        input.nextLine();

        return LocalDate.of(year, monthNumber, dayNumber);
    }

    // Saving Information to Database
    public static void saveInformation(String databaseFile, ArrayList<Calendar> calendars) throws IOException {
        FileWriter databaseWriter = new FileWriter(databaseFile);

        for (Calendar calendar: calendars) {
            databaseWriter.write("!nc\n");
            databaseWriter.write(calendar.getCalendarName() + "\n");

            for (Event event: calendar.getAllEvents()) {
                databaseWriter.write("!ne\n");
                databaseWriter.write(event.toFile());
            }
        }

        databaseWriter.close();
    }
}
