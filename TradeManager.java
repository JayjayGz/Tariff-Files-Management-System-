// ------------------------------------------------
// Assignment (3) 
//  
// Written by: Jeffrey Gueyie, Student ID: 40315016 
//
// This program simulates the effects of international trade policies by using the concepts
// File I/O, ArrayList, and Linked Lists. It acts as a Tariff Management System, which can
// analyze and adjust tariffs, track and manage trade disputes, develop a system, and
// simulate how tariffs impact global trade dynamics.
// ------------------------------------------------

//Imports
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.NoSuchElementException;


public class TradeManager 
{
	//Main method
	public static void main(String[] args) 
	{
		//Welcome message
        System.out.println("---------------------------------------------");
        System.out.println("     Welcome to the Global Tariff Toolkit!");
        System.out.println("---------------------------------------------");
        System.out.println();

        //Process TradeData.txt 
        processTradeDataFile();
        
        //Try/Catch clause to read files. Accounts for ioe and nsee.
        try 
        {
            //Creation of two empty TariffList objects
            TariffList tariffList1 = new TariffList();
            TariffList tariffList2 = new TariffList();

            //Open Tariff.txt file and read its contents line by line.
            System.out.println("Reading tariff data from Tariffs.txt...");
            BufferedReader tariffReader = new BufferedReader(new FileReader("Tariffs.txt"));
            String line;
            
            //Loop to read each line in text file.
            while((line = tariffReader.readLine()) != null) 
            {
                String[] parts = line.split(" ");
                
                //Checks if each line has the right attribute format
                if(parts.length != 4) 
                {
                    throw new IllegalArgumentException("Invalid format in Tariffs.txt: " + line);
                }
                
                //Try/Catch in order to account for attributes in wrong format (Parse won't work)
                try
                {
                	//Separate each part of the line with respect to its attribute
                    String destination = parts[0].trim();
                    String origin = parts[1].trim();
                    String category = parts[2].trim();
                    double minTariff = Double.parseDouble(parts[3].trim());
                    
                    //Create new Tariff object that will be inserted in TariffList array if does not already exist.
                    Tariff tariff = new Tariff(destination, origin, category, minTariff);
                    
                    // Check for duplicate entries before adding.
                    if(!tariffList1.contains(origin, destination, category)) 
                    {
                        tariffList1.addToStart(tariff);
                    }
                }
                catch(NumberFormatException nfe) 
                {
                    System.out.println("Invalid tariff value in line: " + line);
                }    
            }
          
            //Close reader when all lines have been read.
            tariffReader.close();
            
            // Test copy constructor
            tariffList2 = new TariffList(tariffList1);
            
            //Confirmation message
            System.out.println("Tariff lists created successfully.");
            System.out.println("List1 size: " + tariffList1.getSize());
            System.out.println("List2 size: " + tariffList2.getSize());
            System.out.println("Lists equal: " + tariffList1.equals(tariffList2));

            //Process TradeRequests.txt
            System.out.println("\nProcessing trade requests from TradeRequests.txt...");
            
            //Creation of ArrayList of type String Array
            ArrayList<String[]> tradeRequests = new ArrayList<>();
            
            //Read TradeRequests file
            BufferedReader requestReader = new BufferedReader(new FileReader("TradeRequests.txt"));
            
            //Loop to read each line in text file
            while((line = requestReader.readLine()) != null)
            {
                String[] parts = line.split(" ");
                
                //Checks if each line has the right attribute format
                if(parts.length != 6) 
                {
                    throw new IllegalArgumentException("Invalid tariff value in line: " + line);
                }
                
                try 
                {
                    //Parse tradeValue to check format
                    double tradeValue = Double.parseDouble(parts[4].trim());
                    double proposedTariff = Double.parseDouble(parts[5].trim());

                    // Add valid and properly formatted line to ArrayList
                    tradeRequests.add(parts);
                } 
                catch(NumberFormatException nfe) 
                {
                    System.out.println("Invalid value in line: " + line);
                }
            }
            
            //Close reader when all lines in TradeRequests.txt have been read
            requestReader.close();
            
            //Process each trade request
            for(String[] request : tradeRequests) 
            {
            	//Separate each part of the line with respect to its attribute
                String reqId = request[0].trim();
                String origin = request[1].trim();
                String destination = request[2].trim();
                String category = request[3].trim();
                double tradeValue = Double.parseDouble(request[4].trim());
                double proposedTariff = Double.parseDouble(request[5].trim());
                
                // Find matching tariff policy
                TariffList.TariffNode node = tariffList1.find(origin, destination, category);
                
                if(node != null) 
                {
                    double minTariff = ((Tariff)node.getTariff()).getMinimumTariff();
                    String result = tariffList1.evaluateTrade(proposedTariff, minTariff);
                    
                    //Print the outcome
                    System.out.print("\n" + reqId + " - ");
                    
                    if(result.equals("Accepted"))
                    {
                        System.out.println("Accepted.");
                        System.out.println("Proposed tariff meets or exceeds the minimum requirement.");
                    } 
                    else if(result.equals("Conditionally Accepted")) 
                    {
                        double surcharge = tradeValue * ((minTariff - proposedTariff) / 100);
                        System.out.println("Conditionally Accepted.");
                        System.out.printf("Proposed tariff %.1f%% is within 20%% of the required minimum tariff %.1f%%.\n",
                                proposedTariff, minTariff);
                        System.out.printf("A surcharge of $%.2f is applied.\n", surcharge);
                    } 
                    else 
                    {
                        System.out.println("Rejected.");
                        System.out.printf("Proposed tariff %.1f%% is more than 20%% below the required minimum tariff %.1f%%.\n",
                                proposedTariff, minTariff);
                    }
                } 
                else 
                {
                    System.out.println("\n" + reqId + " - No tariff policy found for this trade request.");
                }
            }

            //Interactive tariff search
            Scanner keyin = new Scanner(System.in);
            System.out.println("\n--- Interactive Tariff Search ---");
            System.out.println("Enter tariff details to search:");
            System.out.print("Origin Country: ");
            String origin = keyin.nextLine();
            System.out.print("Destination Country: ");
            String destination = keyin.nextLine();
            System.out.print("Product Category: ");
            String category = keyin.nextLine();
            
            TariffList.TariffNode foundNode = tariffList1.find(origin, destination, category);
            
            if(foundNode != null) 
            {
                System.out.println("Tariff found: " + foundNode.getTariff());
            } 
            else 
            {
                System.out.println("No matching tariff found.");
            }

            //Close scanner
            keyin.close();
            
            // Call method that tests all TariffList methods
            testAllMethods(tariffList1, tariffList2);
            
        } 
        catch(IOException ioe) 
        {
            System.out.println("There was an issue while trying to read the file.");
        } 

        //Closing message
        System.out.println("\n-------------------------------------------");
        System.out.println("  Thank you for using the Tariff Management System");
        System.out.println("               Program terminated");
        System.out.println("---------------------------------------------");
    }
	
	//Applies tariff percentage to TariffProduct based on country and product type.
	private static void applyTariffToProduct(TariffProduct p)
	{
		String country = p.getCountry();
	    String category = p.getCategory();

	    //Switch to apply tariffs according to country
	    switch(country) 
	    {
	        case "China":
	        {
	            p.applyTariff(25);
	            break;
	        }
	        case "USA":
	        {
	            if(category.equalsIgnoreCase("Electronics"))
	            {
	                p.applyTariff(10);
	            }
	            break;
	        }
	        case "Japan":
	        {
	            if(category.equalsIgnoreCase("Automobiles"))
	            {
	                p.applyTariff(15);
	            }
	            break;
	        }
	        case "India":
	        {
	            if(category.equalsIgnoreCase("Agriculture"))
	            {
	                p.applyTariff(5);
	            }
	            break;
	        }
	        case "South Korea":
	        {
	            if(category.equalsIgnoreCase("Electronics"))
	            {
	                p.applyTariff(8);
	            }
	            break;
	        }
	        case "Saudi Arabia":
	        {
	            if(category.equalsIgnoreCase("Energy"))
	            {
	                p.applyTariff(12);
	            }
	            break;
	        }
	        case "Germany":
	        {
	            if(category.equalsIgnoreCase("Manufacturing"))
	            {
	                p.applyTariff(6);
	            }
	            break;
	        }
	        case "Bangladesh":
	        {
	            if(category.equalsIgnoreCase("Textile"))
	            {
	                p.applyTariff(4);
	            }
	            break;
	        }
	        case "Brazil":
	        {
	            if(category.equalsIgnoreCase("Agriculture"))
	            {
	                p.applyTariff(9);
	            }
	            break;
	        }
	    }	
	}
	
	/*Method reads TradeData.txt, applies tariffs by country/category rules, 
	  sorts products, and writes updated prices to UpdatedTradeData.txt."
	*/
	public static void processTradeDataFile() 
	{
		//Creation of ArrayList of type TariffProduct
        ArrayList<TariffProduct> products = new ArrayList<>();
        
		//Try/Catch for reading of TradeData file
	    try 
	    {
	    	
	        //Read TradeData file
	        BufferedReader reader = new BufferedReader(new FileReader("TradeData.txt"));
	        String line;

	        //Loop to read each line in text file.
	        while((line = reader.readLine()) != null) 
	        {
	        	line = line.trim();
	            String[] parts = line.split(",");
	            
	            //Checks if each line has the right attribute format
                if(parts.length != 4) 
                {
                    throw new IllegalArgumentException("Invalid format in TradeData.txt: " + line);
                }
                
	            try 
	            {
	                String name = parts[0].trim();
	                String country = parts[1].trim();
	                String category = parts[2].trim();
	                
	                //Parse to check format
	                double price = Double.parseDouble(parts[3].trim());

	                TariffProduct product = new TariffProduct(name, country, category, price);
	                applyTariffToProduct(product);
	                products.add(product);
	            }
	            catch(NumberFormatException nfe)
	            {
	            	System.out.println("Invalid value in line: " + line);
	            }
	        }
	        
	        //Close file when all lines have been read
	        reader.close();

	        // Sort alphabetically by product name
	        products.sort((p1, p2) -> p1.getProductName().compareToIgnoreCase(p2.getProductName()));

	        // Write to UpdatedTradeData.txt
	        PrintWriter writer = new PrintWriter(new FileWriter("UpdatedTradeData.txt"));
	        
	        //Converts products data to strings
	        for (TariffProduct p : products) 
	        {
	            writer.println(p.toString());
	        }
	        writer.close();

	        System.out.println("UpdatedTradeData.txt has been created with adjusted tariffs.");

	    } 
	    catch(IOException e) 
	    {
	        System.out.println("Error processing trade data file");
	    }
	}
	
	/**
     * Tests all methods of the TariffList class
     * @param list1 The first tariff list
     * @param list2 The second tariff list (copy of the first)
     */
    private static void testAllMethods(TariffList list1, TariffList list2) 
    {
        System.out.println("\n--- Testing TariffList Methods ---");
        
        //Create test tariffs
        Tariff testTariff1 = new Tariff("France", "Italy", "Wine", 12.5);
        Tariff testTariff2 = new Tariff("Canada", "Mexico", "Oil", 8.0);
        
        //Test addToStart
        System.out.println("\nTesting addToStart()...");
        list1.addToStart(testTariff1);
        
        try 
        {
            list1.addToStart(null);
        } 
        catch (NullPointerException npe) 
        {
            System.out.println("Caught NullPointerException for null input.");
        }
        System.out.println("Added tariff to start: " + testTariff1);
        
        //Test insertAtIndex
        System.out.println("\nTesting insertAtIndex()...");
   
        try
        {
            list1.insertAtIndex(testTariff2, 1); // Valid 
            System.out.println("Inserted tariff at index 1: " + testTariff2);
            list1.insertAtIndex(testTariff1, 999); //Invalid
        } 
        catch (NoSuchElementException nsee) 
        {
            System.out.println("Insert failed: " + nsee.getMessage());
        }
        
        //Test replaceAtIndex
        System.out.println("\nTesting replaceAtIndex()...");
        
        try 
        {
        	Tariff replacementTariff = new Tariff("Germany", "UK", "Cars", 15.0);
            list1.replaceAtIndex(replacementTariff, 0);
            System.out.println("Replaced tariff at index 0 with: " + replacementTariff);
            
        } 
        catch (NoSuchElementException nsee) 
        {
            System.out.println("Replace failed: " + nsee.getMessage());
        }
        
        //Test deleteFromStart
        System.out.println("\nTesting deleteFromStart()...");
        
        try 
        {
            list1.deleteFromStart();
            System.out.println("Deleted first tariff");
        } 
        catch (NoSuchElementException nsee) 
        {
            System.out.println("Delete failed: " + nsee.getMessage());
        }
        
        //Test deleteFromIndex
        System.out.println("\nTesting deleteFromIndex()...");
        
        try 
        {
            list1.deleteFromIndex(0);
            System.out.println("Deleted tariff at index 0");
        } 
        catch (NoSuchElementException nsee) 
        {
            System.out.println("Delete failed: " + nsee.getMessage());
        }
        
        //Test find() method
        TariffList.TariffNode foundNode = list1.find("Germany", "UK", "Cars");
        System.out.println("Explicit find() test: " + (foundNode != null ? "FOUND" : "NOT FOUND"));
        
        //Test contains() method
        System.out.println("\nTesting contains()...");
        boolean contains = list1.contains("China", "USA", "Electronics");
        System.out.println("List contains China->USA Electronics tariff: " + contains);
        
        //Test clone() method via copy constructor
        System.out.println("\nTesting clone()...");
        TariffList clonedList = new TariffList(list1);
        System.out.println("Clone equals original: " + list1.equals(clonedList));
        
        //Test equals() method
        System.out.println("\nTesting equals()...");
        boolean listsEqual = list1.equals(list2);
        System.out.println("List1 equals List2: " + listsEqual);
        
        //Test evaluateTrade() abstract method from TariffPolicy interface
        System.out.println("\nTesting evaluateTrade()...");
        String result1 = list1.evaluateTrade(25.0, 20.0); // Accepted
        String result2 = list1.evaluateTrade(18.0, 20.0); // Conditionally Accepted
        String result3 = list1.evaluateTrade(15.0, 20.0); // Rejected
        
        System.out.println("Result for 25% vs 20%: " + result1);
        System.out.println("Result for 18% vs 20%: " + result2);
        System.out.println("Result for 15% vs 20%: " + result3);
        
        System.out.println("\nAll methods tested successfully.");
	}

}
