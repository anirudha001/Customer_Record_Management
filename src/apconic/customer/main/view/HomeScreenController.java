package apconic.customer.main.view;

import javafx.fxml.FXML;
import apconic.customer.main.MainApp;

public class HomeScreenController {
	 private MainApp mainApp;
	       
	 public HomeScreenController() {
		 
	 }
	 
	 @FXML
	 private void initialize() {
	 }
	 
      public void setMainApp(MainApp mainApp) {
          this.mainApp = mainApp;
      }
        
	 @FXML
	private void handleAddCustomer() {
		mainApp.showAddCustomer();
	}

	@FXML
	private void handleSearchCustomer() {
		mainApp.showSearchCustomer();
		}
}

