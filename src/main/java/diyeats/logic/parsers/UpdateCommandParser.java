package diyeats.logic.parsers;

import diyeats.commons.exceptions.ProgramException;
import diyeats.logic.commands.CGraphCommand;
import diyeats.logic.commands.UpdateCommand;

import java.util.HashMap;

//@@author koushireo
/**
 * Parser class to handle addition of breakfast item to model.
 */
public class UpdateCommandParser implements ParserInterface<UpdateCommand> {

    /**
     * Parses user input and returns an UpdateCommand encapsulating an object containing information to be updated.
     * @param userInputStr String input by user.
     * @return <code>UpdateCommand</code> Command object encapsulating information to be updated
     */
    @Override
    public UpdateCommand parse(String userInputStr) {
        String age;
        String weight;
        String date;
        String height;
        String name;
        String activity;
        boolean flag = false;
        String[] temp = {"age", "weight", "date", "height", "name", "activity"};
        HashMap<String, String> map = ArgumentSplitter.splitForwardSlashArguments(userInputStr);

        if (map.containsKey("age")) {
            flag = true;
            age = map.get("age");
        } else {
            age = null;
        }
        if (map.containsKey("weight")) {
            flag = true;
            weight = map.get("weight");
        } else {
            weight = null;
        }
        if (map.containsKey("date")) {
            date = map.get("date");
        } else {
            date = null;
        }
        if (map.containsKey("height")) {
            flag = true;
            height = map.get("height");
        } else {
            height = null;
        }
        if (map.containsKey("name")) {
            flag = true;
            name = map.get("name");
        } else {
            name = null;
        }
        if (map.containsKey("activity")) {
            flag = true;
            activity = map.get("activity");
        } else {
            activity = null;
        }

        if (flag == false) {
            return new UpdateCommand(false, "Please enter proper parameters(/name, /age, /weight [/date],\n"
                    + "/height /activity level");
        }

        return new UpdateCommand(age, weight, date, height, name, activity);
    }
}
