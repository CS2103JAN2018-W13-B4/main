package seedu.organizer.ui.calendar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.organizer.model.task.Task;
import seedu.organizer.ui.TaskCard;
import seedu.organizer.ui.TaskListPanel;
import seedu.organizer.ui.UiPart;

import org.fxmisc.easybind.EasyBind;

//@@author guekling
/**
 * Supports the display of the month view of the calendar.
 */
public class MonthView extends UiPart<Region> {

    private static final String FXML = "MonthView.fxml";

    private int dateCount;
    private String[] datesToBePrinted;
    private ObservableList<Task> taskList;

    @FXML
    private Text calendarTitle;

    @FXML
    private GridPane taskCalendar;

    public MonthView(ObservableList<Task> taskList) {
        super(FXML);

        this.taskList = taskList;
    }

    /**
     * Sets the title of the calendar according to a specific month and year.
     *
     * @param month Full month name.
     * @param year Year represented as a 4-digit integer.
     */
    protected void setMonthCalendarTitle(int year, String month) {
        calendarTitle.setText(month + " " + year);
    }

    /**
     * Sets the dates of a month-view calendar according to the specific month and year
     *
     * @param year Year represented as a 4-digit integer.
     * @param month Month represented by numbers from 1 to 12.
     */
    protected void setMonthCalendarDates(int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        int lengthOfMonth = startDate.lengthOfMonth();
        int startDay = getMonthStartDay(startDate);

        // !!! Calendar Entry

        //!!! THIS SHIT WORKS!!!
        //List<EntryCard> list = new ArrayList<EntryCard>();
        //list.add(new EntryCard("test"));

        ListView<EntryCard> entries = new ListView<>();
        ObservableList<EntryCard> mappedList = EasyBind.map(
                taskList, (person) -> new EntryCard(person));

        //ObservableList<EntryCard> mappedList = FXCollections.observableList(list);
        entries.setItems(mappedList);
        entries.setCellFactory(listView -> new MonthView.EntryListViewCell());

        //ObservableList<String> items = FXCollections.observableArrayList ("Single", "Double");
        //list.setItems(items);
        entries.setMaxHeight(60);
        taskCalendar.add(entries, 1, 4);
        taskCalendar.setValignment(entries, VPos.BOTTOM);
        // !!! Calendar Entry

        datesToBePrinted = new String[36];
        storeMonthDatesToBePrinted(lengthOfMonth);

        setFiveWeeksMonthCalendar(startDay);

        // If month has more than 5 weeks
        if (dateCount != lengthOfMonth) {
            setSixWeeksMonthCalendar(lengthOfMonth);
        }
    }

    /**
     * !!! ADD COMMENTS - IF NOT REMOVED BY THEN LOLS !!!
     * Custom {@code ListCell} that displays the graphics of a {@code TaskCard}.
     */
    class EntryListViewCell extends ListCell<EntryCard> {

        @Override
        protected void updateItem(EntryCard entry, boolean empty) {
            super.updateItem(entry, empty);

            if (empty || entry == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(entry.getRoot());
            }
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
