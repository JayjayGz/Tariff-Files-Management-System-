//Import
import java.util.NoSuchElementException;

public class TariffList implements TariffPolicy 
{
	//Inner class for LinkedList
	public class TariffNode
	{		
		//Tariff object attribute 
		private Tariff tariff;		
				
		//TariffNode object attribute
		private TariffNode tariffNode;
				
		//Default constructor
		public TariffNode()
		{
			tariff = null;
			tariffNode = null;
		}
				
		//Parameterized constructor. 
		public TariffNode(Tariff tariff, TariffNode tariffNode)
		{
			this.tariff = new Tariff(tariff);
			this.tariffNode = (tariffNode != null) ? new TariffNode(tariffNode) : null;
		}
				
		//Copy constructor (creates deep copy)
		public TariffNode(TariffNode otherTariffNode)
		{
			if(otherTariffNode == null)
			{	
				throw new NullPointerException("Null node cannot be copied.");
			}
	
			//Deep copy
			this.tariff = new Tariff(otherTariffNode.tariff);
			this.tariffNode = null; // Initialize TariffNode to null
			
			if (otherTariffNode.tariffNode != null)
			{
		        this.tariffNode = new TariffNode(otherTariffNode.tariffNode);
			}
			
		}
					
		//Clone method (returns deep copy).
		public TariffNode clone()
		{
			return new TariffNode(this);
		}
				
		//Equals method. Compares Tariff and TariffNode objects. 
		public boolean equals(Object otherObject)
		{
			if(otherObject == null)
				return false;
				
			else if(getClass() != otherObject.getClass())
				return false;		
			else
			{
				TariffNode otherTariffNode = (TariffNode)otherObject;		
				return getTariff().equals(otherTariffNode.getTariff()) &&
					   ((getTariffNode() == null) ? 
						otherTariffNode.getTariffNode() == null : 
						getTariffNode().equals(otherTariffNode.getTariffNode()));
			}
		}
		
		//Getter: Tariff object
		public Tariff getTariff()
		{
			return new Tariff(tariff);
		}
						
		//Setter: Tariff object
		public void setTariff(Tariff tariff)
		{
			this.tariff = new Tariff(tariff);
		}
						
		//Getter: TariffNode object
		public TariffNode getTariffNode()
		{
			return tariffNode != null ? new TariffNode(tariffNode) : null;
		}
						
		//Setter: TariffNode object
		public void setTariffNode(TariffNode tariffNode)
		{
			this.tariffNode = new TariffNode(tariffNode);
		}
}
	
	//TariffList Attributes
	private TariffNode head;
	private int size;
		
	//Default constructor
	public TariffList()
	{
		head = null;
		size = 0;
	}
	
	//Parameterized constructor
	public TariffList(TariffNode head, int size)
	{
		this.head = head;
		this.size = size;
	}
	
	// Copy constructor for TariffList (deep copy).
	public TariffList(TariffList otherTariffList)
	{
	    if (otherTariffList == null) 
	    {
	        throw new NullPointerException("Cannot copy a null TariffList.");
	    }

	    this.size = otherTariffList.size;
	    this.head = null; // Initialize head to null

	    if (otherTariffList.head != null)
	    {
	        // Deep copy the first node
	        this.head = new TariffNode(otherTariffList.head.tariff, null);  
	           
	        TariffNode currentOriginal = otherTariffList.head.tariffNode;
	        TariffNode currentCopy = this.head;

	        // Copy remaining nodes
	        while (currentOriginal != null) 
	        { 
	            currentCopy.setTariffNode(new TariffNode(currentOriginal.tariff, null)); 
	            currentCopy = currentCopy.getTariffNode();
	            currentOriginal = currentOriginal.getTariffNode();
	        }
	    }
	}
	
	//Getter: Head
	public TariffNode getHead() 
	{
		return head;
	}

	//Setter: Head
	public void setHead(TariffNode head) 
	{
		this.head = head;
	}

	//Getter: Size
	public int getSize()
	{
		return size;
	}

	//Setter: Size
	public void setSize(int size) 
	{
		this.size = size;
	}
	
	//toString() method
	public String toString() 
	{
		return "TariffList [head=" + head + ", size=" + size + "]";
	}

	//Method creates TariffNode with that passed object and inserts it at the head of the list. 
	public void addToStart(Tariff otherTariff)
	{
		if (otherTariff == null) 
	    {
	        throw new NullPointerException("Tariff cannot be null.");
	    }   
		
		head = new TariffNode(otherTariff, head);
		size++;
	}
	
	//Method creates a TariffNode with the passed Tariff object and inserts it at the given index.
	public void insertAtIndex(Tariff otherTariff, int index)
	{
		// Null check
	    if (otherTariff == null) 
	    {
	        throw new NullPointerException("Tariff cannot be null.");
	    }   
	    
	    //Valid index check
		if(index < 0 || index >= size)
		{
			throw new NoSuchElementException("Index # " + index + " is out of bounds. Valid range: 0 to "
														+ (size-1) + ".");
		}
		
		//Insert at head, ie. index = 0
		if (index == 0) 
		{
			addToStart(otherTariff);   
		}

		//Insert elsewhere
		TariffNode current = head;
		    
		//Traverse to the node before target index
		for (int i = 0; i < index - 1; i++)
		{
		    current = current.tariffNode;
		}

	    //Create new node and link it
		current.tariffNode = new TariffNode(otherTariff, current.tariffNode);
		size++;   
			
	}
	
	
	//Method deletes TariffNode pointed to by entered index from the list.
	public void deleteFromIndex(int index)
	{
	    //Valid index check
	    if (index < 0 || index >= size)
	    {
	    	throw new NoSuchElementException("Index # " + index + " is out of bounds. Valid range: 0 to "
					 									+ (size-1) + ".");
	    }
	    
	    if (head == null) 
	    {
	        throw new NoSuchElementException("Cannot delete from empty list.");
	    }

	    //Delete head, ie. index = 0
	    if (index == 0) 
	    {
	        deleteFromStart();
	        return;
	    }

	    //Find node before the one getting deleted
	    TariffNode current = head;
	    
	    for (int i = 0; i < index - 1; i++) 
	    {
	        current = current.tariffNode;
	    }

	    //Bypass node to delete
	    current.tariffNode = current.tariffNode.tariffNode;
	    size--;
	}
	
	//Method deletes TariffNode at head from the list.
	public void deleteFromStart() 
	{
	    if (head == null) 
	    {
	        throw new NoSuchElementException("Cannot delete from empty list.");
	    }
	    
	    head = head.tariffNode;
	    size--;
	}
	
	
	//Method replaces tariff at specified index by a new tariff.
	public void replaceAtIndex(Tariff otherTariff, int index) 
	{
	    // Validate input
	    if (otherTariff == null) 
	    {
	        throw new NullPointerException("Tariff cannot be null.");
	    }
	    if (index < 0 || index >= size) 
	    {
	        throw new NoSuchElementException("Index " + index + " is invalid. Valid range: 0 to " + (size-1) + ".");
	    }

	    // Find the target node
	    TariffNode current = head;
	    for (int i = 0; i < index; i++) 
	    {
	        current = current.tariffNode;
	    }

	    // Replace with deep copy
	    current.tariff = new Tariff(otherTariff);
	}
	
	//Method searches list for a TariffNode with that Tariff.  
	public TariffNode find(String originCountry, String destinationCountry, String productCategory) 
	{
	    // Validate parameters
	    if (originCountry == null || destinationCountry == null || productCategory == null) 
	    {
	        throw new NullPointerException("Search parameters (origin, destination, or category) cannot be null.");
	    }

	    TariffNode current = head;
	    int iterations = 0;
	    
	    //Search for TariffNode with specific Tariff and keeps tracks of iteration until found.
	    while (current != null) 
	    {
	        iterations++;
	        Tariff t = current.tariff;
	        
	        if (t.getOriginCountry().equals(originCountry) &&
	            t.getDestinationCountry().equals(destinationCountry) &&
	            t.getProductCategory().equals(productCategory)) 
	        {
	            System.out.println("Found after " + iterations + " iterations");
	            return current; // Return the node directly
	        }
	        
	        current = current.tariffNode;
	    }
	    
	    System.out.println("Not found after " + iterations + " iterations");
	    return null;
	}
	
	//Method returns true if a TariffNode contains matching info in the list. 
	public boolean contains(String originCountry, String destinationCountry, String productCategory) 
	{
	    return find(originCountry, destinationCountry, productCategory) != null;
	}
	
	//Method returns true if two list contain similar TariffNodes.
	public boolean equals(Object otherObject) 
	{
	    if (otherObject == null) 
	    {
	    	return false;
	    }
	    else if (getClass() != otherObject.getClass())
	    {	
	    	return false;
	    }
	    else
	    {
	    	TariffList otherTariffList = (TariffList)otherObject;
	    	
	    	if (this.size != otherTariffList.size) 
	    	{
	    		return false;
	    	}
	    	
	    	TariffNode currentThis = this.head;
	    	TariffNode currentOther = otherTariffList.head;
	    
	    	//Iteration through both loops to compare corresponding nodes
	    	while (currentThis != null && currentOther != null) 
	    	{
	    		if (!currentThis.tariff.equals(currentOther.tariff))
	    		{
	    			return false;
	    		}
	        
	    		currentThis = currentThis.tariffNode;
	    		currentOther = currentOther.tariffNode;
	    	}
	    	return true;
		}
	}
	
	@Override
	public String evaluateTrade(double proposedTariff, double minimumTariff) 
	{
	    //Check for invalid tariff input (negative values)
	    if (proposedTariff < 0 || minimumTariff < 0) 
	    {
	        throw new IllegalArgumentException("Tariff rates cannot be negative");
	    }
	    //Trade request accepted (proposed tariff meets or exceeds minimum required tariff)
	    else if (proposedTariff >= minimumTariff) 
	    {
	        return "Accepted";
	    }
	    //Trade request conditionally accepted (proposed tariff is lower than minimum tariff, but within 20% of it) 
	    else if (proposedTariff >= minimumTariff*0.8) 
	    {
	        return "Conditionally Accepted";
	    }
	    //Trade request rejected (proposed tariff is more than 20% below minimum tariff)
	    else {
	        return "Rejected";
	    }
	}	
}
