package apconic.customer.main.view;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import apconic.customer.main.model.Customer;
import apconic.customer.main.model.DbManager;

public class CustomerEditDialogController {

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
	    private ObservableList<Customer> data;
	    private Stage dialogStage;
		private Customer customer;
		@FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
        firstNameField.setText(customer.getFirstName());
        lastNameField.setText(customer.getLastName());
        emailField.setText(customer.getEmail());
        phoneField.setText(customer.getPhone());
        addressField.setText(customer.getAddress());
        cityField.setText(customer.getCity());
        stateField.setText(customer.getState());
        pinCodeField.setText(customer.getPinCode().toString());
        countryField.setText(customer.getCountry());
    }
    
    @FXML
    private void handleEdit() {
    	 Action response = Dialogs.create()
 		        .owner(dialogStage)
 		        .title("Confirm Dialog")
 		        .masthead("Look, a Confirm Dialog")
 		        .message("Do you want to continue?")
 		        .actions(Dialog.Actions.YES,Dialog.Actions.NO)
 		        .showConfirm();    	
        if (isInputValid() && response == Dialog.Actions.YES) {
        	DbManager dbmanager = new DbManager();
			try(PreparedStatement preparedStatement = dbmanager.getConnection().prepareStatement("update customers set firstname=?,lastname=?,email=?,phone=?,address=?,city=?,state=?,pincode=?,country=? where firstname=? ") ) {
				preparedStatement.setString(1,firstNameField.getText());				
				preparedStatement.setString(2,lastNameField.getText());
				preparedStatement.setString(3,emailField.getText());
				preparedStatement.setString(4,phoneField.getText());
				preparedStatement.setString(5,addressField.getText());
				preparedStatement.setString(6,cityField.getText());
				preparedStatement.setString(7,stateField.getText());
				preparedStatement.setInt(8,Integer.parseInt( pinCodeField.getText()) );
				preparedStatement.setString(9,countryField.getText());
				preparedStatement.setString(10,customer.getFirstName());
				preparedStatement.executeUpdate();
				dbmanager.disConnect();
				
			} catch (ClassNotFoundException | SQLException exception) {
					exception.printStackTrace();
				}
        	
            dialogStage.close();
            updateTableView();
        }
        else {
 		   	dialogStage.close();
 		}
    }
    
    private void updateTableView() {	
		data = FXCollections.observableArrayList();
		customer.setFirstName(firstNameField.getText());
        customer.setLastName(lastNameField.getText());
        customer.setEmail(emailField.getText());
        customer.setPhone(phoneField.getText());
        customer.setAddress(addressField.getText());
        customer.setCity(cityField.getText());
        customer.setState(stateField.getText());
        customer.setPinCode(Integer.parseInt( pinCodeField.getText()));
        customer.setCountry(countryField.getText());  
        data.add(customer);
        SearchCustomerController.updateTableView(data);
	}

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    
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