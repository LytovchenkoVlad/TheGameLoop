package view;

import entity.Student;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import utilities.MessageBox;

public class StudentEditDialogController {
	@FXML
	private TextField firstNameField;
	@FXML
	private TextField lastNameField;
	@FXML
	private TextField mark1Field;
	@FXML
	private TextField mark2Field;

	private Stage dialogStage;
	private Student person;
	private boolean okClicked = false;

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
	}

	/**
	 * Sets the stage of this dialog.
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Sets the person to be edited in the dialog.
	 * 
	 * @param person
	 */
	public void setPerson(Student person) {
		this.person = person;
		firstNameField.setText(person.getFirstName());
		lastNameField.setText(person.getLastName());
		mark1Field.setText(Integer.toString(person.getMark1()));
		mark2Field.setText(Integer.toString(person.getMark2()));
	}

	/**
	 * Returns true if the user clicked OK, false otherwise.
	 * 
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Called when the user clicks ok.
	 */
	@FXML
	private void handleOk() {
		if (isInputValid()) {
			person.setFirstName(firstNameField.getText());
			person.setLastName(lastNameField.getText());
			person.setMark1(Integer.parseInt(mark1Field.getText()));
			person.setMark2(Integer.parseInt(mark2Field.getText()));
			okClicked = true;
			dialogStage.close();
		}
	}

	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	/**
	 * Validates the user input in the text fields.
	 * 
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		String errorMessage = "";
		if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
			errorMessage += "No valid first name!\n";
		}
		if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
			errorMessage += "No valid last name!\n";
		}
		if (mark1Field.getText() == null || mark1Field.getText().length() == 0) {
			errorMessage += "No valid mark 1!\n";
		}
		if (mark2Field.getText() == null || mark2Field.getText().length() == 0) {
			errorMessage += "No valid mark 2!\n";
		}
		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			MessageBox.Show(AlertType.ERROR, "Invalid fields", "Please correct invalid fields!", errorMessage);
			return false;
		}
	}
}