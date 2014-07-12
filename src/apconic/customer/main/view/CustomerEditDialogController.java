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

/**
 * Dialog to edit details of a Customer.
 */
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
    private SearchCustomerDialogController searchCustomerDialogController;
    private ObservableList<Customer> data;
    private Stage dialogStage;
	private Customer customer;
		
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
     * Sets the customer to be edited in the dialog.
     * 
     * @param customer
     */
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
    
    /**
     * Called when the user clicks edit.
     */
    @FXML
    private void handleEdit() {    	
    	
    	if (isInputValid()) {
    		Action response = Dialogs.create()
     		        .owner(dialogStage)
     		        .title("Confirm Dialog")
     		        .masthead("Look, a Confirm Dialog")
     		        .message("Do you want to continue?")
     		        .actions(Dialog.Actions.YES,Dialog.Actions.NO)
     		        .showConfirm();	
    		
    		if(response == Dialog.Actions.YES) {
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
    				dialogStage.close();
    				updateTableView();
    				dbmanager.disConnect();
    				
    			} catch (NumberFormatException e) {
    				pinCodeField.setText(null);
                    pinCodeField.setStyle("-fx-border-color: red");
    			} catch (ClassNotFoundException | SQLException exception) {
					exception.printStackTrace();
    			  }		
    		}
    			else {
    				dialogStage.close();
    			}
        }		
    }
    
    /**
     * Updates the tableview after updates on customer details 
     */
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
        
        searchCustomerDialogController.updateTableView(data);
	}

    /**
     * Called when the user clicks Cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    
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
    
    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
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
    
    public SearchCustomerDialogController getSearchCustomerController() {
		return searchCustomerDialogController;
	}

	public void setSearchCustomerController(SearchCustomerDialogController searchCustomerController) {
		this.searchCustomerDialogController = searchCustomerController;
	} 

}