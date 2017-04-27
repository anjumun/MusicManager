package ch.makery.address.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.Arrays;
import java.util.Collections;

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
	@FXML
	private Button add;
	@FXML
	private Button remove;
	@FXML
	private Button bubbleSortYear;
	@FXML
	private TextField bottomField;
	@FXML
	private Label timer;
	@FXML
	private Label bubbleTimer;


	// Reference to the main application.
	// just a so we can use the getter method for the ObservableList in
	// MainApp.java
	private MainApp mainApp;
	private ObservableList<MusicEntry> list;
	private ObservableList<MusicEntry> tempList = FXCollections.observableArrayList();
	double time;
	double startTime;
	double endTime;

	

	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public InnerOverviewController() {
		list = FXCollections.observableArrayList(MainApp.musicData);		

	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Initialize binary search action event
		//binarySearch();
		linearSearch();
		addEntry();
		removeSelectedEntry();
		bubbleSortYear();

		System.out.println("initialize ran");

		// Initialize the music table with the four columns.
		artistColumn.setCellValueFactory(cellData -> cellData.getValue().getArtistProperty());
		songColumn.setCellValueFactory(cellData -> cellData.getValue().getSongProperty());
		yearColumn.setCellValueFactory(cellData -> cellData.getValue().getYearProperty());
		combinedColumn.setCellValueFactory(cellData -> cellData.getValue().getCombinedProperty());

	}

	private void linearSearch() {
		// search filter for search text field
		// 1. Wrap the ObservableList in a FilteredList (initially display all
		// data).
		
		FilteredList<MusicEntry> filteredData = new FilteredList<>(list, p -> true);
		System.out.println("is list empty: " + list.isEmpty());
		System.out.println("is filteredData empty: " + filteredData.isEmpty());

		// 2. Set the filter Predicate whenever the filter changes.
		filterField.textProperty().addListener((observable, oldValue, newValue) -> {
			startTime = System.nanoTime();
			
			// test user input in field
			System.out.println("observed : " + observable);
			System.out.println("old : " + oldValue);
			System.out.println("new : " + newValue);

			filteredData.setPredicate(musicEntry -> {
				// If filter text is empty, display all music.
				
				if (newValue == null || newValue.isEmpty()) {
					time=0;
					timer.setText(Double.toString(time));
					return true;
				}

				// Compare Song Artist year and combined with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				endTime = System.nanoTime();
				time = (endTime-startTime);
				
				timer.setText(Double.toString(time));
				if (musicEntry.getArtist().toLowerCase().contains(lowerCaseFilter)) {
					System.out.println("found artist");
					return true; // Filter matches artist name.
				} else if (musicEntry.getSong().toLowerCase().contains(lowerCaseFilter)) {
					System.out.println("found song");
					return true; // Filter matches song name.
				} else if (musicEntry.getYear().toLowerCase().contains(lowerCaseFilter)) {
					System.out.println("found year");
					return true; // Filter matches year .
				} else if (musicEntry.getCombined().toLowerCase().contains(lowerCaseFilter)) {
					System.out.println("found combined");
					return true; // Filter matches combined .
				}
				
				return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList.
		SortedList<MusicEntry> sortedData = new SortedList<>(filteredData);

		System.out.println(
				filteredData.getPredicate() + " " + sortedData.get(0).getArtist() + "/" + sortedData.get(0).getSong()
						+ "/" + sortedData.get(0).getYear() + "/" + sortedData.get(0).getCombined());
		// 4. Bind the SortedList comparator to the TableView comparator. So
		// clicking the button on-top of column sorts new data also

		sortedData.comparatorProperty().bind(musicTable.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		System.out.println("checking....");
		System.out.println("changing tables");
		
		// list.removeAll(list);
		// FXCollections.copy(list, sortedData);
		System.out.println("im herE");
		musicTable.setItems(sortedData);

	}

	private void binarySearch() {
		// FilteredList<MusicEntry> filteredData = new FilteredList<>(list, p ->
		// true);
		System.out.println("is list empty: " + list.isEmpty());

		bottomField.textProperty().addListener((observable, oldValue, newValue) -> {
			tempList.clear();
			int middle = 0;
			// test user input in field
			System.out.println("observed : " + observable);
			System.out.println("old : " + oldValue);
			System.out.println("new : " + newValue);

			// If filter text is empty, display all music.
			System.out.println("binary searching.....");
			// Compare Song filter text.
			String lowerCaseFilter = newValue.toLowerCase();
			int low = 0;
			int high = list.size() - 1;
			while (high >= low ) {
				middle = (low + high) / 2;
				System.out.println("searching...");
				if (list.get(middle).getSong().toLowerCase().compareTo(lowerCaseFilter) < 0) {
					low = middle + 1;
				} else if(list.get(middle).getSong().toLowerCase().compareTo(lowerCaseFilter) > 0){
					high = middle - 1;
				}else{
					tempList.add(list.get(middle));
				}
				//middle = (low + high) / 2;
				
			}
			System.out.print(list.get(middle).getSong());
			
		});
		System.out.println("templist is empty: " + tempList.isEmpty());
		// 3. Wrap the FilteredList in a SortedList.
		SortedList<MusicEntry> sortedData = new SortedList<>(tempList);

		// 4. Bind the SortedList comparator to the TableView comparator. So
		// clicking the button on-top of column sorts new data also

		sortedData.comparatorProperty().bind(musicTable.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		System.out.println("checking....");
		System.out.println("changing tables");

		musicTable.setItems(sortedData);

	}
	
	private void addEntry(){
		add.setOnAction((event) -> {
			String text = bottomField.getText().trim();
			String[] split = text.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
//
//			for (int i = 0; i < split.length; i++) {
//				 //remove null values
//				if (split[i].equals(null))
//					split[i] = "";
//				// remove trailing quotes if any
//				split[i].replaceAll("^\"|$\"", " ");
//				split[i].trim();
//			}			
			list.add(new MusicEntry(split[0], split[1], split[2], split[3]));
		});
		
		
	}
	
	private void removeSelectedEntry(){
		remove.setOnAction((event)->{
			MusicEntry indexSelected = (MusicEntry)musicTable.getSelectionModel().getSelectedItem();
			list.remove(indexSelected);
		    linearSearch();
			//musicTable.refresh();
		});
		
	}

	private void bubbleSortYear() {
		// TODO Auto-generated method stub
		
		bubbleSortYear.setOnAction((event) -> {
			double start = System.currentTimeMillis();
			double time = 0;
			double end=0;
		//ObservableList<MusicEntry> arr = FXCollections.observableArrayList(list);
		//arr = list;
        int n = list.size();  
        MusicEntry temp;  
         for(int i=0; i < n; i++){
                 for(int j=1; j < (n-i); j++){
                	 if((list.get(j-1).getYear().equals(""))){
                		 temp = list.get(j-1);  
                         list.set(j-1,list.get(j));  
                         list.set(j, temp);
                         }
                	 
                	 else if(Double.parseDouble((list.get(j-1).getYear().trim()))> Double.parseDouble(list.get(j).getYear().trim())){  
                                 temp = list.get(j-1);  
                                 list.set(j-1,list.get(j));  
                                 list.set(j, temp); 
                         } 

                          
                 }  
         }  
         end = System.currentTimeMillis();
         time = end - start;
         String timer = Double.toString(time);
         bubbleTimer.setText(timer);
         musicTable.setItems(list);
         musicTable.refresh();
         
		});
		
	}
}// end class
