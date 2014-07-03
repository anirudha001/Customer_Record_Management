package apconic.customer.main.model;

public class Name implements SearchBy {

	/**
	 * Search Customer by Name.
	 */
	@Override
	public String search() {
		return "select * from customers where upper(firstname) like upper(?)";
	}
	
}
