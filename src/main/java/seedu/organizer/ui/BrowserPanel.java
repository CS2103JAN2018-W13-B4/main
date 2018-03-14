package seedu.organizer.ui;

import java.time.LocalDate;
import java.time.YearMonth;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

//@@author guekling
/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    private static final String FXML = "BrowserPanel.fxml";

    private YearMonth currentYearMonth;
    private int dateCount;
    private String[] datesToBePrinted;

    @FXML
    private Text calendarTitle;

    @FXML
    private GridPane taskCalendar;


    public BrowserPanel() {
        super(FXML);

        // Shows calendar of current month
        currentYearMonth = currentYearMonth.now();
        int currentYear = currentYearMonth.getYear();
        setMonthCalendarTitle(currentYear, currentYearMonth.getMonth().toString());

        setMonthCalendarDates(currentYear, currentYearMonth.getMonthValue());
    }

    /**
     * Sets the title of the calendar according to a specific month and year.
     *
     * @param month Full month name.
     * @param year Year represented as a 4-digit integer.
     */
    private void setMonthCalendarTitle(int year, String month) {
        calendarTitle.setText(month + " " + year);
    }

    /**
     * Sets the dates of a month-view calendar according to the specific month and year
     *
     * @param year Year represented as a 4-digit integer.
     * @param month Month represented by numbers from 1 to 12.
     */
    private void setMonthCalendarDates(int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        int lengthOfMonth = startDate.lengthOfMonth();
        int startDay = getMonthStartDay(startDate);

        datesToBePrinted = new String[36];
        storeMonthDatesToBePrinted(lengthOfMonth);

        setFiveWeeksMonthCalendar(startDay);

        // If month has more than 5 weeks
        if (dateCount != lengthOfMonth) {
            setSixWeeksMonthCalendar(lengthOfMonth);
        }
    }

    /**
     * Sets the dates of a five-weeks month-view calendar into the {@code taskCalendar}.
     *
     * @param startDay Integer value of the day of week of the start day  of the month. Values ranges from 1 - 7,
     *                 representing the different days of the week.
     */
    private void setFiveWeeksMonthCalendar(int startDay) {
        dateCount = 1;
        for (int row = 0; row <= 4; row++) {
            if (row == 0) {
                for (int column = startDay; column <= 6; column++) {
                    Text dateToPrint = new Text(datesToBePrinted[dateCount]);
                    addMonthDate(dateToPrint, column, row);
                    dateCount++;
                }
            } else {
                for (int column = 0; column <= 6; column++) {
                    Text dateToPrint = new Text(datesToBePrinted[dateCount]);
                    addMonthDate(dateToPrint, column, row);
                    dateCount++;
                }
            }
        }
    }

    /**
     * Sets the dates of the sixth week in a six-weeks month-view calendar into the {@code taskCalendar}.
     *
     * @param lengthOfMonth Integer value of the number of days in a month.
     */
    private void setSixWeeksMonthCalendar(int lengthOfMonth) {
        int remainingDays = lengthOfMonth - dateCount;

        for (int column = 0; column <= remainingDays; column++) {
            Text dateToPrint = new Text(datesToBePrinted[dateCount]);
            addMonthDate(dateToPrint, column, 0);
            dateCount++;
        }
    }

    /**
     * Gets the day of week of the start date of a particular month and year.
     *
     * @param startDate A LocalDate variable that represents the date, viewed as year-month-day. The day will always
     *                  be set as 1.
     * @return Integer value of the day of week of the start day  of the month. Values ranges from 1 - 7,
     *         representing the different days of the week.
     */
    private int getMonthStartDay(LocalDate startDate) {
        int startDay = startDate.getDayOfWeek().getValue();

        // Sunday is the first column in the calendar
        if (startDay == 7) {
            startDay = 0;
        }

        return startDay;
    }

    /**
     * Adds a particular date to the correct {@code column} and {@code row} in the {@code taskCalendar}.
     *
     * @param dateToPrint The formatted date text to be printed on the {@code taskCalendar}.
     * @param column The column number in {@code taskCalendar}. Column number should range from 0 to 6.
     * @param row The row number in {@code taskCalendar}. Row number should range from 0 to 4.
     */
    private void addMonthDate(Text dateToPrint, int column, int row) {
        taskCalendar.add(dateToPrint, column, row);
        taskCalendar.setHalignment(dateToPrint, HPos.LEFT);
        taskCalendar.setValignment(dateToPrint, VPos.TOP);
    }

    /**
     * Stores the formatted date String to be printed on the {@code taskCalendar}.
     *
     * @param lengthOfMonth Integer value of the number of days in a month.
     */
    private void storeMonthDatesToBePrinted(int lengthOfMonth) {
        for (int date = 1; date <= 35; date++) {
            if (date <= lengthOfMonth) {
                datesToBePrinted[date] = "  " + String.valueOf(date);
            }
        }
    }

}
