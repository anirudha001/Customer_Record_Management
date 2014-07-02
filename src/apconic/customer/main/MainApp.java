package apconic.customer.main;

import java.io.IOException;
import apconic.customer.main.model.Customer;
import apconic.customer.main.view.AddCustomerController;
import apconic.customer.main.view.CustomerEditDialogController;
import apconic.customer.main.view.HomeScreenController;
import apconic.customer.main.view.SearchCustomerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    
    public MainApp() {
		
	}

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Customer Record Management");
        initRootLayout(); 
        showHomeScreen();
    }
    
    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        	}
    }
    
    public void showHomeScreen() {
        try {
            FXMLLoader loader1 = new FXMLLoader();
            loader1.setLocation(MainApp.class.getResource("view/HomeScreen.fxml"));
            AnchorPane anchorPane = (AnchorPane) loader1.load();
            Scene scene = new Scene(anchorPane);
            primaryStage.setScene(scene);
            primaryStage.show();
            HomeScreenController controller = loader1.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
        		e.printStackTrace();
        	  }
    }

    public void showAddCustomer() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/AddCustomer.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Add Customer");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			System.out.println("Action");
			AddCustomerController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			System.out.println("before shownWait()");
			dialogStage.showAndWait();
			System.out.println("Final Action");
		} catch (IOException e) {
			e.printStackTrace();
			}
	}

    public void showSearchCustomer() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/CustomerSearch.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Search Customer");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			System.out.println("Search Action");
			SearchCustomerController controller1 = loader.getController();
			controller1.setDialogStage(dialogStage);
			controller1.setMainApp(this);
			System.out.println("Search before shownWait()");
			dialogStage.showAndWait();
			System.out.println("Search Final Action");
		} catch (IOException e) {
			e.printStackTrace();
			}
	}
    
    public void editCustomer(Customer customer) {
    	
    	try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/CustomerEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Person");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			CustomerEditDialogController controller2 = loader.getController();
			controller2.setDialogStage(dialogStage);
			//controller2.setMainApp(this);
			controller2.setCustomer(customer);
			dialogStage.showAndWait();
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
}