package apconic.customer.main.view;

import javafx.fxml.FXML;
import apconic.customer.main.MainApplication;

public class HomeScreenController {
	 private MainApplication mainApp;
	       
	 public HomeScreenController() {
		 
	 }
	 
	 @FXML
	 private void initialize() {
	 }
	 
      public void setMainApp(MainApplication mainApp) {
          this.mainApp = mainApp;
      }
        
	 @FXML
	private void handleAddCustomer() {
		mainApp.showAddCustomerDialog();
	}

	@FXML
	private void handleSearchCustomer() {
		mainApp.showSearchCustomerDialog();
		}
}

