package seedu.organizer.ui.calendar;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.organizer.model.task.Task;
import seedu.organizer.ui.UiPart;

/**
 * !!! ADD COMMENTS !!!
 */
public class EntryCard extends UiPart<Region> {

    private static final String FXML = "EntryCard.fxml";

    @FXML
    private Label entryCard;

    public EntryCard(Task task) {
        super(FXML);

        entryCard.setText(task.getName().fullName);
    }
}
