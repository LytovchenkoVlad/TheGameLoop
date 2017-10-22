package view;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.application.Platform;
import mainpack.MainClass;
import model.DataModel;
import utilities.MessageBox;
import entity.Student;

public class MainWindowController {
	private static FileChooser fileChooser;
	@FXML
	private TableView<Student> personTable;
	@FXML
	private TableColumn<Student, String> firstNameColumn;
	@FXML
	private TableColumn<Student, String> lastNameColumn;
	@FXML
	private TableColumn<Student, Integer> subject1Column;
	@FXML
	private TableColumn<Student, Integer> subject2Column;
	@FXML
	private TableColumn<Student, Double> averageColumn;
	@FXML
	private TextField groupAverage1Field;
	@FXML
	private TextField groupAverage2Field;

	private static DataModel model;
	private static TableViewSelectionModel<Student> sM;
	private static final String f = "%5.2f";

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		model = DataModel.GetInstance();
		fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Текстовые файлы (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extFilter);
		// Initialize the person table with the five columns.
		firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		subject1Column.setCellValueFactory(new PropertyValueFactory<>("mark1"));
		subject2Column.setCellValueFactory(new PropertyValueFactory<>("mark2"));
		averageColumn.setCellValueFactory(cellData -> cellData.getValue().calculateStudentAverage().asObject());
		sM = personTable.getSelectionModel();
		personTable.setItems(model.getCache());
	}

	public MainWindowController() {
	}

	/**
	 * Opens a dialog to edit details for the specified person. If the user
	 * clicks OK, the changes are saved into the provided person object and true
	 * is returned.
	 * 
	 * @param person
	 *            the person object to be edited
	 * @return true if the user clicked OK, false otherwise.
	 */
	public boolean showPersonEditDialog(Student person) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainClass.class.getResource("/view/StudentEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Person");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(MainClass.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			// Set the person into the controller.
			StudentEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setPerson(person);
			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private void setSummaryInfo() {
		groupAverage1Field.setText(String.format(f, model.calculateAverageMark1()));
		groupAverage2Field.setText(String.format(f, model.calculateAverageMark2()));
	}

	@FXML
	private void handleAdd() {
		Student tempPerson = new Student();
		boolean okClicked = showPersonEditDialog(tempPerson);
		if (okClicked) {
			model.add(tempPerson);
			setSummaryInfo();
		}
	}

	@FXML
	private void handleDelete() {
		int selectedIndex = sM.getSelectedIndex();
		if (selectedIndex >= 0) {
			model.delete(selectedIndex);
			List<Student> lst = model.getCache();
			if (!lst.isEmpty()) {
				setSummaryInfo();
			} else {
				groupAverage1Field.setText("");
				groupAverage2Field.setText("");
			}
		} else {
			// Nothing selected.
			MessageBox.Show(AlertType.WARNING, "No Selection", "No Person Selected",
					"Please select a person to delete!");
		}
	}

	@FXML
	private void handleEdit() {
		int selectedIndex = sM.getSelectedIndex();
		Student selectedPerson = sM.getSelectedItem();
		if (selectedPerson != null) {
			boolean okClicked = showPersonEditDialog(selectedPerson);
			if (okClicked) {
				model.edit(selectedIndex);
				setSummaryInfo();
			}

		} else {
			// Nothing selected.
			MessageBox.Show(AlertType.WARNING, "No Selection", "No Person Selected",
					"Please select a person to edit!");
		}
	}

	@FXML
	private void handleAbout() {
		MessageBox.Show(AlertType.INFORMATION, "StudentMarksApp", "About developer",
				"Ivan Petrenko\n3 Celinogradska Str., Kharkiv 61000");
	}

	@FXML
	private void handleExit() {
		Platform.exit();
	}

	@FXML
	private void handleOpen() {
		File file = fileChooser.showOpenDialog(null);
		if (file != null) {
			try {
				model.clear();
				model.load(file);
				setSummaryInfo();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Opens a FileChooser to let the user select a file to save to.
	 */
	@FXML
	private void handleSaveAs() {
		File file = fileChooser.showSaveDialog(null);
		if (file != null) {
			// Make sure it has the correct extension
			if (!file.getPath().endsWith(".txt")) {
				file = new File(file.getPath() + ".txt");
			}
			try {
				model.persist(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}