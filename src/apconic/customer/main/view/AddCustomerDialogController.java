package apconic.customer.main.view;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

import apconic.customer.main.model.DbManager;

/**
 * Dialog to add details of a customer.
 */
public class AddCustomerDialogController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField stateField;
    @FXML
    private TextField pinCodeField;
    @FXML
    private TextField countryField;
    private Stage dialogStage;
    
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
     * Called when the user clicks save.
     */
    @FXML
    private void handleSave() {
        if (isInputValid()) {
        	DbManager dbManager = new DbManager();
			try(PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement("insert into customers values (?,?,?,?,?,?,?,?,?)") ) {
				preparedStatement.setString(1,firstNameField.getText());				
				preparedStatement.setString(2,lastNameField.getText());
				preparedStatement.setString(3,emailField.getText());
				preparedStatement.setString(4,phoneField.getText());
				preparedStatement.setString(5,addressField.getText());
				preparedStatement.setString(6,cityField.getText());
				preparedStatement.setString(7,stateField.getText());
				preparedStatement.setInt(8,Integer.parseInt( pinCodeField.getText()) );
				preparedStatement.setString(9,countryField.getText());
				preparedStatement.executeUpdate();
				
			} catch (SQLException sqlException) {
			
					sqlException.printStackTrace();
			  }
			finally {
				if(dbManager.getConnection()!=null) {
					try {
						dbManager.disConnect();
					}  catch (ClassNotFoundException | SQLException exception) {
						exception.printStackTrace();
					   }
				}
			}
        	confirmDialog();
            dialogStage.close();
        }
    }
    
    private void confirmDialog() {
    	 		Dialogs.create()
   		        .owner(dialogStage)
   		        .title("Confirm Message")
   		        .masthead("Confirmation Message")
   		        .message("Data Added Sucessfully")
   		        .actions(Dialog.Actions.OK)
   		        .showConfirm();   
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
        Pattern emailPattern=Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher emailMatch=emailPattern.matcher(emailField.getText());
		boolean isEmailMatched=emailMatch.matches();
        if (isEmailMatched == false || emailField.getText() == null || emailField.getText().length() == 0) {
            errorMessage += "No valid email!\n"; 
        }
        Pattern phonePattern=Pattern.compile("[0-9]+[0-9]+[0-9]+[0-9]+[0-9]+[0-9]+[0-9]+[0-9]+[0-9]+[0-9]");
		Matcher phoneMatch=phonePattern.matcher(phoneField.getText());
		boolean isPhoneMatched=phoneMatch.matches();
        if (isPhoneMatched== false || phoneField.getText() == null || phoneField.getText().length() == 0) {
            errorMessage += "No valid phone!\n"; 
        }
        if (addressField.getText() == null || addressField.getText().length() == 0) {
            errorMessage += "No valid address!\n"; 
        }
        if (cityField.getText() == null || cityField.getText().length() == 0) {
            errorMessage += "No valid city!\n"; 
        }
        if (stateField.getText() == null || stateField.getText().length() == 0) {
            errorMessage += "No valid state!\n";
        }
        if (pinCodeField.getText() == null || pinCodeField.getText().length() == 0) {
            errorMessage += "No valid postal code!\n"; 
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(pinCodeField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid pin code (must be an integer)!\n"; 
            }
        }
        
        if (countryField.getText() == null || countryField.getText().length() == 0) {
            errorMessage += "No valid country!\n"; 
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
        	Dialogs.create()
		        .title("Invalid Fields")
		        .masthead("Please correct invalid fields")
		        .message(errorMessage)
		        .showError();
            return false;
        }
    }
}
