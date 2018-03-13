package seedu.organizer.logic.parser;

import seedu.organizer.commons.core.index.Index;
import seedu.organizer.commons.exceptions.IllegalValueException;
import seedu.organizer.logic.parser.exceptions.ParseException;

import seedu.organizer.logic.commands.ToggleCommand;

import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new ToggleCommand object
 */
public class ToggleCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the ToggleCommand
     * and returns an ToggleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ToggleCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ToggleCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ToggleCommand.MESSAGE_USAGE));
        }
    }
}
