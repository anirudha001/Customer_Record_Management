package apconic.customer.main.model;

public class Email implements SearchBy {

	/**
	 * Search Customer by Email.
	 */
	@Override
	public String search() {
		return "select * from customers where Email like ?";
	}

}
