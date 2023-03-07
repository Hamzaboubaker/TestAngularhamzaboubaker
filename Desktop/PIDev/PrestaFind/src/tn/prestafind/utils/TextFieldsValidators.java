package tn.prestafind.utils;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

public class TextFieldsValidators {

    private static final FontAwesomeIcon WARNING_ICON = FontAwesomeIcon.EXCLAMATION_TRIANGLE;
    private static final String MESSAGE = "Obligatory field";
    private static final String EMAIL_MESSAGE = "Please, provide a valid email address.";

    public static void toRequiredJFXTextField(JFXTextField txt) {
        RequiredFieldValidator validator = new RequiredFieldValidator(MESSAGE);
        validator.setIcon(new FontAwesomeIconView(WARNING_ICON));

        txt.getValidators().add(validator);
        txt.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                txt.validate();
            }
        });
    }

    public static void toJFXEmailField(JFXTextField txt) {
        RegexValidator validator = new RegexValidator(EMAIL_MESSAGE);
        validator.setRegexPattern("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        validator.setIcon(new FontAwesomeIconView(WARNING_ICON));

        txt.getValidators().add(validator);
        txt.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                txt.validate();
            }
        });
    }

    public static void toJFXPasswordField(JFXPasswordField txt) {
        RequiredFieldValidator validator = new RequiredFieldValidator(MESSAGE);
        validator.setIcon(new FontAwesomeIconView(WARNING_ICON));

        txt.getValidators().add(validator);
        txt.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                txt.validate();
            }
        });
    }

    public static void toJFXTextArea(JFXTextArea txt) {
        RequiredFieldValidator validator = new RequiredFieldValidator(MESSAGE);
        validator.setIcon(new FontAwesomeIconView(WARNING_ICON));

        txt.getValidators().add(validator);
        txt.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                txt.validate();
            }
        });
    }

    public static void toJFXComboBox(JFXComboBox comboBox) {
        RequiredFieldValidator validator = new RequiredFieldValidator(MESSAGE);
        validator.setIcon(new FontAwesomeIconView(WARNING_ICON));

        comboBox.getValidators().add(validator);
        comboBox.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                comboBox.validate();
            }
        });
    }

    public static void toJFXDatePicker(JFXDatePicker datePicker) {
        RequiredFieldValidator validator = new RequiredFieldValidator(MESSAGE);
        validator.setIcon(new FontAwesomeIconView(WARNING_ICON));

        datePicker.getValidators().add(validator);
        datePicker.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                datePicker.validate();
            }
        });
    }

}
