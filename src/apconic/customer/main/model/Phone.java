package apconic.customer.main.model;

public class Phone implements SearchBy {

	@Override
	public String search() {
		return "select * from customers where Phone=?";
	}
	
}
