package apconic.customer.main;

import java.io.IOException;
import apconic.customer.main.model.Customer;
import apconic.customer.main.view.AddCustomerDialogController;
import apconic.customer.main.view.CustomerEditDialogController;
import apconic.customer.main.view.HomeScreenController;
import apconic.customer.main.view.SearchCustomerDialogController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApplication extends Application {

    private Stage primaryStage;
    private Stage searchCustomerDialogStage;
    private BorderPane rootLayout;
    private SearchCustomerDialogController searchCustomerDialogController;
    
    public MainApplication() {
		
	}
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Customer Record Management");
        initRootLayout(); 
        showHomeScreen();
    }
    
    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        	}
    }
    
    /**
     * Shows the Home Screen inside the root layout.
     */
    public void showHomeScreen() {
        try {
            FXMLLoader loader1 = new FXMLLoader();
            loader1.setLocation(MainApplication.class.getResource("view/HomeScreen.fxml"));
            AnchorPane anchorPane = (AnchorPane) loader1.load();
            rootLayout.setCenter(anchorPane);
            HomeScreenController controller = loader1.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
        		e.printStackTrace();
        	  }
    }

    /**
	 * Opens a dialog to Add details of the customer. If the user
	 * clicks OK, the changes are saved into the provided Customer Database 
	 */
    public void showAddCustomerDialog() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("view/AddCustomerDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Add Customer");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			AddCustomerDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			dialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
			}
	}

    /**
	 * Opens a dialog to search customer's details. 
	 */
    public void showSearchCustomerDialog() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("view/SearchCustomerDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage searchCustomerDialogStage = new Stage();
			this.searchCustomerDialogStage = searchCustomerDialogStage;
			searchCustomerDialogStage.setTitle("Search Customer");
			searchCustomerDialogStage.initModality(Modality.WINDOW_MODAL);
			searchCustomerDialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			searchCustomerDialogStage.setScene(scene);
			SearchCustomerDialogController searchCustomerDialogController = loader.getController();
			searchCustomerDialogController.setDialogStage(searchCustomerDialogStage);
			searchCustomerDialogController.setMainApp(this);
			setSearchCustomerController(searchCustomerDialogController);
			searchCustomerDialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		  }
	}
    
    /**
	 * Opens a dialog to edit specified customer's details. If the user
	 * clicks OK, the changes are updated into the provided Customer Database 
	 */
    public void showEditCustomerDialog(Customer customer) {
    	
    	try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("view/CustomerEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage editCustomerDialogStage = new Stage(); 
			editCustomerDialogStage.setTitle("Edit Person");
			editCustomerDialogStage.initModality(Modality.WINDOW_MODAL);
			editCustomerDialogStage.initOwner(searchCustomerDialogStage);
			Scene scene = new Scene(page);
			editCustomerDialogStage.setScene(scene);
			CustomerEditDialogController customerEditDialogController = loader.getController();
			customerEditDialogController.setDialogStage(editCustomerDialogStage);
			customerEditDialogController.setCustomer(customer);
			customerEditDialogController.setSearchCustomerController(this.searchCustomerDialogController);
			editCustomerDialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
			
		  }
    }
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

	public SearchCustomerDialogController getSearchCustomerController() {
		return searchCustomerDialogController;
	}

	public void setSearchCustomerController(SearchCustomerDialogController searchCustomerDialogController) {
		this.searchCustomerDialogController = searchCustomerDialogController;
	}
}