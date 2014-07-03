package apconic.customer.main.view;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

import apconic.customer.main.MainApplication;
import apconic.customer.main.model.Customer;
import apconic.customer.main.model.DbManager;
import apconic.customer.main.model.SearchBy;

public class SearchCustomerDialogController {

	/**
	 * Dialog to search customer details by Name, Email or Phone.
	 */
	@FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, String> firstNameColumn;
    @FXML
    private TableColumn<Customer, String> lastNameColumn;
    @FXML
    private TableColumn<Customer, String> emailColumn;
    @FXML
    private TableColumn<Customer, String> phoneColumn;
    @FXML
    private TableColumn<Customer, String> addressColumn;
    @FXML
    private TableColumn<Customer, String> cityColumn;
    @FXML
    private TableColumn<Customer, String> stateColumn;
    @FXML
    private TableColumn<Customer, Integer> pinCodeColumn;
    @FXML
    private TableColumn<Customer, String> countryColumn;
    @FXML
    private ContextMenu tableContextMenu;
    @FXML
    private MenuItem editMenuItem;
    @FXML
    private MenuItem deleteMenuItem;
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> comboBoxSearchBy;
    
    private DbManager dbManager;
    private MainApplication mainApplication;
	private Stage dialogStage;
	private ObservableList<Customer> data;
	private SearchBy searchBy;
    
	/**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    void initialize(){
    	ObservableList<String> options = 
    		    FXCollections.observableArrayList(
    		        "Name",
    		        "Email",
    		        "Phone"
    		    );
    	 comboBoxSearchBy.setItems(options);
         firstNameColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("firstName"));        
         lastNameColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("lastName"));  
         emailColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("email"));
         phoneColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("phone"));
         addressColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("address"));
         cityColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("city"));
         stateColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("state"));
         pinCodeColumn.setCellValueFactory(new PropertyValueFactory<Customer,Integer>("pinCode"));
         countryColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("country"));      
         deleteMenuItem.disableProperty().bind(Bindings.isEmpty(customerTable.getSelectionModel().getSelectedItems()));
         editMenuItem.disableProperty().bind(Bindings.isEmpty(customerTable.getSelectionModel().getSelectedItems()));
    }
    
    /**
     * Called when clicked on Table row Context menu edit.
     */
    @FXML
    public void editRow() {	
    	 final Customer selectedPeople = customerTable.getSelectionModel().getSelectedItem();
    	 mainApplication.editCustomer(selectedPeople);
    }  				
    
    /**
     * Called when clicked on Table row Context menu delete.
     */
    @FXML
    public void deleteRow() {
    	 final Customer selectedPeople = customerTable.getSelectionModel().getSelectedItem();
         Action response = Dialogs.create()
  		        .owner(dialogStage)
  		        .title("Confirm Dialog")
  		        .masthead("Look, a Confirm Dialog")
  		        .message("Do you want to continue?")
  		        .actions(Dialog.Actions.YES,Dialog.Actions.NO)
  		        .showConfirm();   
         
         if(response == Dialog.Actions.YES) {
        	 customerTable.getItems().removeAll(selectedPeople);
        	 deleteDatabaseRow(selectedPeople);
         } 	
    }
    
    public void deleteDatabaseRow(Customer selectedPeople) {
    	DbManager dbManager = new DbManager();
		try(PreparedStatement preparedStatement = dbManager.getConnection().prepareStatement("delete customers where firstname=? ") ) {
			preparedStatement.setString(1,selectedPeople.getFirstName());
				preparedStatement.executeUpdate();
		} catch (SQLException e) {
				e.printStackTrace();
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

    /**
     * To search by name, email or phone dynamically.
     */
	public SearchBy getSearchBy() {
    	String className = comboBoxSearchBy.getSelectionModel().getSelectedItem().toString();
          try {
              if( className!=null) {
            	  String fullClassName= "apconic.customer.main.model."+className;
            	  SearchBy searchby = (SearchBy)( Class.forName( fullClassName ).newInstance() );
            	  return searchby;
              }
          } catch (ClassNotFoundException | SecurityException | IllegalAccessException | IllegalArgumentException e) {
              e.printStackTrace();
            } catch (InstantiationException e) {
		    	e.printStackTrace();
		    }
          return searchBy;
    	}
    
	/**
     * Called when clicked on search.
     */
   @FXML 
    private void searchData(){
	   String searchParameter = searchField.getText();
	   if( searchParameter.isEmpty() && searchParameter.length() == 0 || comboBoxSearchBy.getSelectionModel().isEmpty()) {
		   Dialogs.create()
	        .title("Invalid Fields")
	        .masthead("Please Fill all the Fields")
	        .showError();
	   }
	   else {
        data = FXCollections.observableArrayList();
        dbManager = new DbManager();   
        try{      
        	PreparedStatement preparedStatement= dbManager.getConnection().prepareStatement(getSearchBy().search());
        	preparedStatement.setString(1,searchParameter+"%");
        	ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Customer customer = new Customer();                 
                customer.setFirstName(rs.getString(1));
                System.out.println("String 1 : "+rs.getString(1));
                customer.setLastName(rs.getString(2));
                customer.setEmail(rs.getString(3));
                customer.setPhone(rs.getString(4));
                customer.setAddress(rs.getString(5));
                System.out.println(rs.getString(6));
                customer.setCity(rs.getString(6));
                System.out.println(rs.getString(7));
                customer.setState(rs.getString(7));
                customer.setPinCode(rs.getInt(8));
                customer.setCountry(rs.getString(9));           
                data.add(customer);      
                System.out.println("DATA : "+data.toString());
                
            }
            customerTable.setItems(data);
        } catch(Exception e){
              e.printStackTrace();
              System.out.println("Error on Building Data");            
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
    
   /**
    * Is called by the main application to give a reference back to itself.
    * 
    * @param mainApplication
    */
   public void setMainApp(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

   /**
    * Sets the stage of this dialog.
    * 
    * @param dialogStage
    */
	public void setDialogStage(Stage dialogStage) {
			 this.dialogStage = dialogStage;		
		}
	
	public void updateTableView(ObservableList<Customer> data) {
		   customerTable.setItems(this.data);
		}
}
