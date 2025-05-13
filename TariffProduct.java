
public class TariffProduct 
{
	//Attributes
	private String productName;
	private String country;
	private String category;
	private double initialPrice;
	private double updatedPrice;

	//Default constructor
	public TariffProduct() 
    {
		productName = "";
	    country = "";
	    category = "";
	    initialPrice = 0.0;
	    updatedPrice = 0.0;
	}

	//Parameterized constructor
	public TariffProduct(String productName, String country, String category, double initialPrice)
	{
	    this.productName = productName;
	    this.country = country;
	    this.category = category;
	    this.initialPrice = initialPrice;
	    this.updatedPrice = initialPrice; // Initially, no tariff applied
	}

	//Copy constructor not required for this class
	
	// Apply tariff adjustment
	public void applyTariff(double percentage)
	{
	    this.updatedPrice = initialPrice * (1 + percentage / 100);
	}

	//Getter: Product name
	public String getProductName() 
	{ 
		return productName; 
	}
	
	//Setter: Product name
	public void setProductName(String productName) 
	{
		this.productName = productName;
	}
	
	//Getter: Country
	public String getCountry() 
	{ 
		return country; 
	}
	
	//Setter: Country
	public void setCountry(String country) 
	{
		this.country = country;
	}
	
	//Getter: Category
	public String getCategory()
	{
		return category;
	}
	
	//Setter: Category
	public void setCategory(String category) 
	{
		this.category = category;
	}
	
	//Getter: Initial price
	public double getInitialPrice() 
	{ 
		return initialPrice; 
	}
	
	//Setter: Initial price
	public void setInitialPrice(double initialPrice) 
	{
		this.initialPrice = initialPrice;
	}
	
	//Getter: Updated price
	public double getUpdatedPrice() 
	{ 
		return updatedPrice;
	}
	
	//Setter: Updated price
	public void setUpdatedPrice(double updatedPrice) 
	{
		this.updatedPrice = updatedPrice;
	}
	
	// Format for file output in UpdatedTradeData.txt.
	public String toString() 
	{
	    return String.format("%s,%s,%s,%.1f", productName, country, category, updatedPrice);
	}
	
	//Equals method not required
}


