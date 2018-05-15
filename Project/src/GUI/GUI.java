package GUI;

import java.awt.*;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Stock.Item;
import Stock.Stock;
/**
 *  
 *@author Alex Holm
 *
 **/
public class GUI extends JFrame implements Observer, ActionListener
{
	
	/**
	 * I Have no idea what I've done but this is a generated serial 
	 * instead of a default
	**/
	private static final long serialVersionUID = 4465181114406422996L;
	
	public static final int WIDTH = 800;

	public static final int HEIGHT = 600;
	
	public static String itemPropertiesFileName;
	public static String importManifestFileName;
	public static String exportManifestFileName;
	public static String salesLogFileName;
	
	public static Stock storeInventory;
	
	
	JFrame mainFrame = new JFrame();
	JTabbedPane pane = new JTabbedPane();
	
	//Store Variables
	JPanel storeTab = new JPanel(); 
	
	//Inventory Variables
    JPanel inventoryTab  = new JPanel();
    String[] inventoryColumnNames
    = {"Name", "Cost", "Price", "Reorder Point", "Reorder Amount", "Temperature", "Quantity"};
   
    public Object[][]data
    = {{"Name", "Cost", "Price", "Reorder Point", "Reorder Amount", "Temperature", "Quantity"}};
    
    JTable inventoryTable = new JTable(data, inventoryColumnNames);
    
    //Document Variables
    JPanel documentTab = new JPanel();
    
    ImageIcon folderIcon = new ImageIcon("images/OpenFolder.png", "Open the File Chooser");
    
    
    //Items for the properties section. With A label, text area, a filechooser
    //icon and buttons
    JLabel itemPropertiesLabel = new JLabel("Item Properties");
    JTextArea itemPropertiesTextArea = new JTextArea(1, 20);
    JFileChooser itemPropertiesChooser = new JFileChooser();
    JButton itemPropertiesChooseButton = new JButton(folderIcon);
    JButton itemPropertiesButton = new JButton("Load");

    JLabel importManifestLabel = new JLabel("Manifests");
    JTextArea importManifestTextArea = new JTextArea(1, 20);
    JFileChooser importManifestChooser = new JFileChooser();
    JButton importManifestChooseButton = new JButton(folderIcon);
    JButton importManifestButton = new JButton("Load");
    
    JLabel exportManifestLabel = new JLabel("Export Manifest");
    JTextArea exportManifestTextArea = new JTextArea(1, 20);
    JFileChooser exportManifestChooser = new JFileChooser();
    JButton exportManifestChooseButton = new JButton(folderIcon);
    JButton exportManifestButton = new JButton("Export");
    
    JLabel salesLogLabel = new JLabel("Sales Log");
    JTextArea salesLogTextArea = new JTextArea(1, 20);
    JFileChooser salesLogChooser = new JFileChooser();
    JButton salesLogChooseButton = new JButton(folderIcon);
    JButton salesLogButton = new JButton("Load");
    

    
    
	public GUI() {
        super("Title");
        loadLayout();
	}
	

	//this is to initialize components
	
	private void initComponents() {
		//THis can be directories only but CEEB'd doing that rn
		
		mainFrame = new JFrame("Title");
		mainFrame.setSize(HEIGHT, WIDTH);		
		mainFrame.setLayout(new GridBagLayout());
		mainFrame.add(pane);
		

        pane.add("Store", storeTab);
        storeTab.add(new JLabel("Tab 1"));
		
        pane.add("Inventory", inventoryTab);
        //inventoryTab.add(new JLabel("Please load in item properties"));
        inventoryTab.add(inventoryTable);
        inventoryTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        pane.add("Documents", documentTab);
        
        itemPropertiesTextArea.setEditable(true);
        exportManifestTextArea.setEditable(true);
        importManifestTextArea.setEditable(true);
        salesLogTextArea.setEditable(true);
        
        //This can change to files only or files and directories but i dunno what was a good choice
        itemPropertiesChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		exportManifestChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		importManifestChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		salesLogChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		//Sets the directory of the file choosers to Home
	    itemPropertiesChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
	    exportManifestChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
	    importManifestChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        salesLogChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
	    //Make properties Text area and button to open file choose
        itemPropertiesChooseButton.addActionListener(this);
        importManifestChooseButton.addActionListener(this);
        exportManifestChooseButton.addActionListener(this);
        salesLogChooseButton.addActionListener(this);
        
        itemPropertiesButton.addActionListener(this);
        importManifestButton.addActionListener(this);
        exportManifestButton.addActionListener(this);
        salesLogButton.addActionListener(this);
        
        
        documentTab.setLayout(new GridBagLayout());
        GridBagConstraints docuTabLayout = new GridBagConstraints();
        docuTabLayout.fill = GridBagConstraints.HORIZONTAL;
        docuTabLayout.anchor = GridBagConstraints.WEST;

        docuTabLayout.gridy = 0;
        docuTabLayout.gridx = 0;
        documentTab.add(itemPropertiesLabel, docuTabLayout);
        docuTabLayout.gridy = 1;
        documentTab.add(itemPropertiesTextArea, docuTabLayout);
        docuTabLayout.gridx = 1;
        documentTab.add(itemPropertiesChooseButton, docuTabLayout);
        docuTabLayout.gridx = 2;
        documentTab.add(itemPropertiesButton, docuTabLayout);
        
        docuTabLayout.gridy = 2;
        docuTabLayout.gridx = 0;
        documentTab.add(importManifestLabel, docuTabLayout);
        docuTabLayout.gridy = 3;
        documentTab.add(importManifestTextArea, docuTabLayout);
        docuTabLayout.gridx = 1;
        documentTab.add(importManifestChooseButton, docuTabLayout);
        docuTabLayout.gridx = 2;
        documentTab.add(importManifestButton, docuTabLayout);

        docuTabLayout.gridy = 4;
        docuTabLayout.gridx = 0;
        documentTab.add(exportManifestLabel, docuTabLayout);
        docuTabLayout.gridy = 5;
        documentTab.add(exportManifestTextArea, docuTabLayout);
        docuTabLayout.gridx = 1;
        documentTab.add(exportManifestChooseButton, docuTabLayout);
        docuTabLayout.gridx = 2;
        documentTab.add(exportManifestButton, docuTabLayout);
        
        docuTabLayout.gridy = 8;
        docuTabLayout.gridx = 0;
        documentTab.add(salesLogLabel, docuTabLayout);
        docuTabLayout.gridy = 9;
        documentTab.add(salesLogTextArea, docuTabLayout);
        docuTabLayout.gridx = 1;
        documentTab.add(salesLogChooseButton, docuTabLayout);
        docuTabLayout.gridx = 2;
        documentTab.add(salesLogButton, docuTabLayout);
        
        getContentPane().add(pane);
        
	}
	
	private void loadLayout() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLocation(new Point(100, 100));
        pack();
        setVisible(true);
    }
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == itemPropertiesChooseButton) {
			int returnVal = itemPropertiesChooser.showOpenDialog(GUI.this);

		      if (returnVal == JFileChooser.APPROVE_OPTION) {
		    	  // Maybe store filename as global?
		    	  
		        File file = itemPropertiesChooser.getSelectedFile();
		        itemPropertiesFileName = file.getAbsolutePath();
		        itemPropertiesTextArea.setText("");
		        itemPropertiesTextArea.append(itemPropertiesFileName);
		      }
		}
		//this SAVES a file
		else if (e.getSource() == exportManifestChooseButton) {
		      int returnVal = exportManifestChooser.showSaveDialog(GUI.this);
		      if (returnVal == JFileChooser.APPROVE_OPTION) {
		        File file = exportManifestChooser.getSelectedFile();
		        exportManifestFileName = file.getAbsolutePath();
		        exportManifestTextArea.setText("");
		        exportManifestTextArea.append(exportManifestFileName);
		      }
		}
		else if (e.getSource() == importManifestChooseButton) {
			int returnVal = importManifestChooser.showOpenDialog(GUI.this);

		      if (returnVal == JFileChooser.APPROVE_OPTION) {
		        File file = importManifestChooser.getSelectedFile();
		        importManifestFileName = file.getAbsolutePath();
		        importManifestTextArea.setText("");
		        importManifestTextArea.append(importManifestFileName);
		      }
		}
		else if (e.getSource() == salesLogChooseButton) {
			int returnVal = salesLogChooser.showOpenDialog(GUI.this);
			System.out.println("Alex is a little gay homo");

		      if (returnVal == JFileChooser.APPROVE_OPTION) {
		        File file = salesLogChooser.getSelectedFile();
		        salesLogFileName = file.getAbsolutePath();
		        salesLogTextArea.setText("");
		        salesLogTextArea.append(salesLogFileName);
		      }
		} 
		else if (e.getSource() == itemPropertiesButton) {
			if (itemPropertiesTextArea.getText() != "") {
				System.out.println("This Occured");
				List<Item> items = new ArrayList<>();
				
				try {
					items = FileReader.ReadItemProperties(itemPropertiesTextArea.getText());
					storeInventory = new Stock(items);
					DefaultTableModel dtm = new DefaultTableModel(0, 0);
					dtm.addColumn("Name");
					dtm.addColumn("Manufacturing Cost");
					dtm.addColumn("Price");
					dtm.addColumn("Reorder Point");
					dtm.addColumn("Reorder Amount");
					dtm.addColumn("Storage Temp");
					dtm.addColumn("Quantity");
					
					
					for (Item item: storeInventory.inventory()) {
						dtm.addRow(new Object[] { item.name(), item.manufacturingcost(), item.sellprice(),
								item.reorderpoint(), item.reorderamount(), item.storageTemp(), item.quantity() });
					}
					inventoryTable.setModel(dtm);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println("Did I poop?");
					e1.printStackTrace();
				}
			}
			
		}
		else if (e.getSource() == importManifestButton) {
			if (importManifestTextArea.getText() != null || importManifestTextArea.getText() != "") {
				
			}
		}
		else if (e.getSource() == exportManifestButton) {
			if (exportManifestTextArea.getText() != null || exportManifestTextArea.getText() != "") {
				
			}
		}
		else if (e.getSource() == salesLogButton) {
			if (salesLogTextArea.getText() != null || salesLogTextArea.getText() != "") {
				
			}
		}
	}
	
	  public static void main(String[] args) {
	        JFrame.setDefaultLookAndFeelDecorated(true);
	        new GUI();
	    }

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	}

