  
public class Currency {
	public double rate;
	public String name;
	
	//empty constructor.
	public Currency() {
		this.rate = 0;
		this.name = "unnamed";
	}
	
	//constructor with parameters.
	public Currency(String _name, double _rate) {
		this.rate = _rate;
		this.name = _name;
	}
	
}
