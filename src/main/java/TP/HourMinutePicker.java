package TP;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;

import java.util.function.UnaryOperator;

public class HourMinutePicker extends HBox {
    private Spinner<Integer> hourSpinner;
    private Spinner<Integer> minuteSpinner;

    public HourMinutePicker() {
        hourSpinner = new Spinner<>(0, 23, 0);
        hourSpinner.setEditable(true);
        UnaryOperator<TextFormatter.Change> hourFilter = change -> {
            String text = change.getText();
            if (text.matches("\\d*")) {
                return change;
            }
            return null;
        };
        TextFormatter<Integer> hourFormatter = new TextFormatter<>(hourFilter);
        hourSpinner.getEditor().setTextFormatter(hourFormatter);
        minuteSpinner = new Spinner<>(0, 59, 0);
        minuteSpinner.setEditable(true);

        UnaryOperator<TextFormatter.Change> minuteFilter = change -> {
            String text = change.getText();
            if (text.matches("\\d*")) {
                return change;
            }
            return null;
        };
        TextFormatter<Integer> minuteFormatter = new TextFormatter<>(minuteFilter);
        minuteSpinner.getEditor().setTextFormatter(minuteFormatter);
        getChildren().addAll(hourSpinner, minuteSpinner);
    }

    public int getHour() {
        return hourSpinner.getValue();
    }

    public int getMinute() {
        return minuteSpinner.getValue();
    }
}

