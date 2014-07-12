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
				confirmDialog();
	            dialogStage.close();
			} catch (NumberFormatException e) {
				pinCodeField.setText(null);
                pinCodeField.setStyle("-fx-border-color: red");
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
    
    private void setBorderColorToDefault()
    {
    	  firstNameField.setStyle("-fx-border-color: null");
    	  lastNameField.setStyle("-fx-border-color: null");
    	  emailField.setStyle("-fx-border-color: null");
    	  phoneField.setStyle("-fx-border-color: null");
    	  addressField.setStyle("-fx-border-color: null");
          cityField.setStyle("-fx-border-color: null");
          stateField.setStyle("-fx-border-color: null");
          pinCodeField.setStyle("-fx-border-color: null");
          countryField.setStyle("-fx-border-color: null");
    }
    
    private boolean isInputValid() {

        boolean error = false;
        
        boolean isEmailMatched = false;
    	try {
    		Pattern emailPattern=Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    		Matcher emailMatch=emailPattern.matcher(emailField.getText());
    		isEmailMatched=emailMatch.matches();
    	} catch(NullPointerException nullPointerException) {
    	}
        
    	boolean isPhoneMatched = false;
    	try {
    		Pattern phonePattern=Pattern.compile("\\d{10}");
    		Matcher phoneMatch=phonePattern.matcher(phoneField.getText());
    		isPhoneMatched=phoneMatch.matches();
    	} catch(NullPointerException nullPointerException) {
    	  }
    	
		setBorderColorToDefault();
        
        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
        	error = true; 
            firstNameField.setStyle("-fx-border-color: red");
            firstNameField.setText(null);
        } else if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            error = true;
        	lastNameField.setText(null);
        	lastNameField.setStyle("-fx-border-color: red");
        }else if (isEmailMatched==false || emailField.getText() == null || emailField.getText().length() == 0) {
        	
     		
     			error = true;
     			emailField.setText(null);
     			emailField.setStyle("-fx-border-color: red");
     		
        } else if ( isPhoneMatched == false || phoneField.getText() == null || phoneField.getText().length() == 0) {
        	
    		
    			error = true;
             phoneField.setText(null);
             phoneField.setStyle("-fx-border-color: red");
    		
        } else if (addressField.getText() == null || addressField.getText().length() == 0) {
        	error = true;
             addressField.setText(null);
             addressField.setStyle("-fx-border-color: red");
        }
        
        else if (cityField.getText() == null || cityField.getText().length() == 0) {
        	 error = true;
             cityField.setText(null);
             cityField.setStyle("-fx-border-color: red");
        } else if (stateField.getText() == null || stateField.getText().length() == 0) {
        	 error = true;
             stateField.setText(null);
             stateField.setStyle("-fx-border-color: red");
        } else if (pinCodeField.getText() == null || pinCodeField.getText().length() == 0) {
    	    error = true; 
            pinCodeField.setStyle("-fx-border-color: red");
            
        } else if (countryField.getText() == null || countryField.getText().length() == 0) {
        	error = true;
       	countryField.setStyle("-fx-border-color: red");
       	 return false;
       } 
        if (error == false) {
            return true;
        } else {     
            return false;
        }
    }  
}
