
public class Tariff 
{
	//Attributes 
	private String destinationCountry;
	private String originCountry;
	private String productCategory;
	private double minimumTariff;
	
	//Default constructor
	public Tariff()
	{
		destinationCountry = "";
		originCountry = "";
		productCategory = "";
		minimumTariff = 0;
	}
	
	//Parameterized constructor
	public Tariff(String destinationCountry, String originCountry, String productCategory, double minimumTariff)
	{
		this.destinationCountry = destinationCountry;
		this.originCountry = originCountry;
		this.productCategory = productCategory;
		this.minimumTariff = minimumTariff;
	}
	
	//Copy constructor. No leak possible (String are immutable and minimumTariff is primitive double).
	public Tariff(Tariff otherTariff)
	{
		this.destinationCountry = otherTariff.destinationCountry;
		this.originCountry = otherTariff.originCountry;
		this.productCategory = otherTariff.productCategory;
		this.minimumTariff = otherTariff.minimumTariff;
	}
	
	//Getter: Country destination
	public String getDestinationCountry() 
	{
		return destinationCountry;
	}
	
	//Setter: Country destination
	public void setDestinationCountry(String destinationCountry) 
	{
		this.destinationCountry = destinationCountry;
	}
	
	//Getter: Country origin
	public String getOriginCountry() 
	{
		return originCountry;
	}
	
	//Setter: Country origin
	public void setOriginCountry(String originCountry)
	{
		this.originCountry = originCountry;
	}
	
	//Getter: Product category
	public String getProductCategory() 
	{
		return productCategory;
	}
	
	//Setter: Product category
	public void setProductCategory(String productCategory)
	{
		this.productCategory = productCategory;
	}
	
	//Getter: Minimum tariff
	public double getMinimumTariff() 
	{
		return minimumTariff;
	}
	
	//Setter: Minimum tariff
	public void setMinimumTariff(double minimumTariff)
	{
		this.minimumTariff = minimumTariff;
	}
	
	//Clone method. No leak possible (String are immutable and minimumTariff is primitive double).
	public Tariff clone()
	{
		return new Tariff(this);
	}
	
	//To string method 
	public String toString() 
	{
		return "Tariff [destinationCountry=" + getDestinationCountry() + ", originCountry=" 
				                             + getOriginCountry() + ", productCategory="
				                             + getProductCategory()+ ", minimumTariff=" 			                             + getMinimumTariff() + "]";
	}
	
	//Equals method
	public boolean equals(Object otherObject) 
	{
		if (otherObject == null)
			return  false;
		
		else if (getClass() != otherObject.getClass())
			return false;
		
		else
		{
			Tariff otherTariff = (Tariff) otherObject;	
			return getDestinationCountry().equals(otherTariff.getDestinationCountry()) &&
				   getOriginCountry().equals(otherTariff.getOriginCountry()) &&
				   getProductCategory().equals(otherTariff.getProductCategory()) &&
				   getMinimumTariff() == otherTariff.getMinimumTariff();	
		}
	}
}
