package apconic.customer.main.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Customer.
 */
public class Customer {

	private final StringProperty firstName;
	private final StringProperty lastName;
	private final StringProperty email;
	private final StringProperty phone;
	private final StringProperty address;
	private final StringProperty city;
	private final StringProperty state;
	private final IntegerProperty pinCode;
	private final StringProperty country;

	/**
	 * Default constructor.
	 */
	public Customer() {
		this.firstName = new SimpleStringProperty();
		this.lastName = new SimpleStringProperty();
		this.email = new SimpleStringProperty();
		this.phone = new SimpleStringProperty();
		this.address = new SimpleStringProperty();
		this.city = new SimpleStringProperty();
		this.state = new SimpleStringProperty();
		this.pinCode = new SimpleIntegerProperty();
		this.country = new SimpleStringProperty();
	}
	
	public String getFirstName() {
		return firstName.get();
	}

	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}
	
	public StringProperty firstNameProperty() {
		return firstName;
	}

	public String getLastName() {
		return lastName.get();
	}

	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}
	
	public StringProperty lastNameProperty() {
		return lastName;
	}

	public String getEmail() {
		return email.get();
	}
	
	public void setEmail(String email) {
		this.email.set(email);
	}
	
	public StringProperty emailProperty() {
		return email;
	}
	
	public String getPhone() {
		return phone.get();
	}
	
	public void setPhone(String phone) {
		this.phone.set(phone);
	}
	
	public StringProperty phoneProperty() {
		return phone;
	}
	
	public String getAddress() {
		return address.get();
	}

	public void setAddress(String address) {
		this.address.set(address);
	}
	
	public StringProperty addressProperty() {
		return address;
	}
	
	public String getCity() {
		return city.get();
	}

	public void setCity(String city) {
		this.city.setValue(city);;
	}
	
	public StringProperty cityProperty() {
		return city;
	}
	
	public String getState() {
		return state.get();
	}
	
	public void setState(String state) {
		this.state.set(state);
	}
	
	public StringProperty stateProperty() {
		return state;
	}	
	
	public Integer getPinCode() {
		return pinCode.get();
	}
	
	public void setPinCode(Integer pinCode) {
		this.pinCode.set(pinCode);
	}
	
	public IntegerProperty pinCodeProperty() {
		return pinCode;
	}

	public String getCountry() {
		return country.get();
	}
	
	public void setCountry(String country) {
		this.country.set(country);
	}
	
	public StringProperty countryProperty() {
		return country;
	}

}