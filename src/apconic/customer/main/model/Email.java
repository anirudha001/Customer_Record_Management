package apconic.customer.main.model;

public class Email implements SearchBy {

	@Override
	public String search() {
		return "select * from customers where Email=?";
	}

}
