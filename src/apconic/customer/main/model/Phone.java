package apconic.customer.main.model;

public class Phone implements SearchBy {

	/**
	 * Search customer by Phone.
	 */
	@Override
	public String search() {
		return "select * from customers where Phone like ?";
	}
	
}
