package ch.makery.address.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import ch.makery.address.MainApp;
import ch.makery.address.model.MusicEntry;

public class InnerOverviewController {
	@FXML
    private TableView<MusicEntry> musicTable;
	@FXML
    private TextField filterField;
    @FXML
    private TableColumn<MusicEntry, String> artistColumn;
    @FXML
    private TableColumn<MusicEntry, String> songColumn;
    @FXML
    private TableColumn<MusicEntry, String> yearColumn;
    @FXML
    private TableColumn<MusicEntry, String> combinedColumn;
    
   


//    @FXML
//    private Label artistNameLabel;
//    @FXML
//    private Label songLabel;
//    @FXML
//    private Label yearLabel;
//    @FXML
//    private Label combinedLabel;

    // Reference to the main application.
    //just a so we can use the getter method for the ObservableList in MainApp.java
    private MainApp mainApp;
    private ObservableList<MusicEntry> list;
    
    //trying to copy main dataset
    //private ObservableList<MusicEntry> masterDataCopy = FXCollections.observableArrayList();

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public InnerOverviewController() {
    	list = FXCollections.observableArrayList(MainApp.musicData);
    	System.out.println(list.get(0).getArtist() + "/" + list.get(0).getSong() + "/" + 
    	list.get(0).getYear() + "/" + list.get(0).getCombined());
    	
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	
    	System.out.println("initialize ran");
    	
        // Initialize the music table with the four columns.
        artistColumn.setCellValueFactory(cellData -> cellData.getValue().getArtistProperty());     
        songColumn.setCellValueFactory(cellData -> cellData.getValue().getSongProperty());
        yearColumn.setCellValueFactory(cellData -> cellData.getValue().getYearProperty());
        combinedColumn.setCellValueFactory(cellData -> cellData.getValue().getCombinedProperty());
        
        //search filter for search text field
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<MusicEntry> filteredData = new FilteredList<>(list, p -> true);
        System.out.println("is list empty: " +list.isEmpty());
        System.out.println("is filteredData empty: " +filteredData.isEmpty());

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
        	//test user input in field
        	System.out.println("observed : " + observable);
        	System.out.println("old : " + oldValue);
        	System.out.println("new : " + newValue);
        	
            filteredData.setPredicate(musicEntry -> {
                // If filter text is empty, display all music.
            	
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare Song Artist year and combined with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (musicEntry.getArtist().toLowerCase().contains(lowerCaseFilter)){                	
                	System.out.println("found artist");
                    return true; // Filter matches artist name.
                }else if (musicEntry.getSong().toLowerCase().contains(lowerCaseFilter)) {
                	System.out.println("found song");
                    return true; // Filter matches song name.
                }else if (musicEntry.getYear().toLowerCase().contains(lowerCaseFilter)) {
                	System.out.println("found year");
                    return true; // Filter matches year .
                }else if (musicEntry.getCombined().toLowerCase().contains(lowerCaseFilter)) {
                	System.out.println("found combined");
                    return true; // Filter matches combined .
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<MusicEntry> sortedData = new SortedList<>(filteredData);
        
        System.out.println(filteredData.getPredicate() +" "+  sortedData.get(0).getArtist() + "/" + sortedData.get(0).getSong() + "/" + 
            	sortedData.get(0).getYear() + "/" + sortedData.get(0).getCombined());
        // 4. Bind the SortedList comparator to the TableView comparator. So clicking the button on-top of column sorts new data also
        
        sortedData.comparatorProperty().bind(musicTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        System.out.println("checking....");
        System.out.println("changing tables");
        
        //list.removeAll(list);
        //FXCollections.copy(list, sortedData);
        musicTable.setItems(sortedData);
        
    }
    

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
    	System.out.println("setMainApp Object ran");
        this.mainApp = mainApp;
        // Add observable list data to the table        
        this.musicTable.setItems(list);
        
    }
}
