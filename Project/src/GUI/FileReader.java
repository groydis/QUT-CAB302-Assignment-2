package GUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import Stock.Item;
import Stock.Stock;

/**
 * @author Greyden Scott
 *
 */
public class FileReader {
	private static Stock storeInventory;
	
	public static void main(String... args) throws IOException {
		storeInventory = new Stock(ReadItemProperties("./Files/item_properties.csv"));
		LoadSalesLog();
		for (Item i: storeInventory.inventory()) {
			System.out.println(i.toString());
		}
	}
	
	/**
	 * This method parses a file into a List of Items line by line
	 *
	 * @param  fileName  File location to read Item Properties from
	 * @return List of Items produces by the Item Properties Document
	 * @see Item
	 */
	public static List<Item> ReadItemProperties(String fileName) throws IOException {
		List<Item> items = new ArrayList<>();
		
		Path pathToFile = Paths.get(fileName);
		
		try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
			
			String line = br.readLine();
			
			while (line != null) {
				String[] attributes = line.split(",");
				Item item = Item.CreateItem(attributes);
				items.add(item);
				
				line = br.readLine();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return items;
	}
	
	/**
	 * This method parses a Sales Log and modifies the inventory of Items based 
	 * on the sales numbers.
	 *
	 * @param  fileName  File location of the Sales Log to be imported
	 */
	public static void LoadSalesLog() {
		String fileName = "./Files/sales_log_0.csv";
		
		Path pathToFile = Paths.get(fileName);
		
		try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
			String line = br.readLine();
			
			while (line != null) {
				String[] attributes = line.split(",");
				storeInventory.updateSales(attributes);
				line = br.readLine();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public static void LoadManifest() {
		
	}
	
}
