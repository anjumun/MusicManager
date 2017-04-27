package ch.makery.address;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import ch.makery.address.model.MusicEntry;
import ch.makery.address.view.InnerOverviewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	static public ObservableList<MusicEntry> musicData = FXCollections.observableArrayList();

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("MusicManager");

		initRootLayout();

		showInner();
	}

	/** 
	 * Initializes the root layout.
	 */
	public void initRootLayout() {
		try {
			System.out.println("initRootLayout ran");
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Shows the music overview inside the root layout.
	 */
	public void showInner() {
		
		System.out.println("ShowInner ran");
	
		
		try {	
			// Load music overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Inner.fxml"));
			
			AnchorPane musicOverview = (AnchorPane) loader.load();
			// Set music overview into the center of root layout.
			rootLayout.setCenter(musicOverview);
			// Give the controller access to the main app.
			InnerOverviewController controller = loader.getController();
			//controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Shows the music overview inside the root layout.
	 */
	// public void showMusicOverview() {
	// try {
	// // Load music overview.
	// FXMLLoader loader = new FXMLLoader();
	// loader.setLocation(MainApp.class.getResource("view/Inner.fxml"));
	// AnchorPane musicOverview = (AnchorPane) loader.load();
	//
	// // Set music overview into the center of root layout.
	// rootLayout.setCenter(musicOverview);
	//
	// // Give the controller access to the main app.
	// InnerOverviewController controller = loader.getController();
	// controller.setMainApp(this);
	// System.out.println("am i herE?");
	//
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	//

	/**
	 * Returns the main stage.
	 * 
	 * @return
	 */

	public Stage getPrimaryStage() {
		System.out.println("getPrimaryStage in MainApp ran");
		return primaryStage;
	}

	// load all the data through constructor so it can be accessed with getter from other classes
	public MainApp() {
		
		try {
			String currentLine;

			BufferedReader br = new BufferedReader(new FileReader("data.txt"));
			br.readLine(); // skip header
			while ((currentLine = br.readLine()) != null) {
				String[] split = currentLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

				for (int i = 0; i < split.length; i++) {
					 //remove null values
					if (split[i].equals(null))
						split[i] = "";
					// remove trailing quotes if any
					split[i].replaceAll("^\"|$\"", " ");
					split[i].trim();
				}
				musicData.add(new MusicEntry(split[0], split[1], split[2], split[3]));
			}
			
			br.close();
		} catch (IOException e) {
			System.out.println("The file for student data was not found");
		}
		System.out.println("MainApp ran");
	}

	/**
	 * Returns the data as an observable list of songs.
	 * 
	 * @return
	 */
	public ObservableList<MusicEntry> getMusicEntry() {
		return musicData;
	}

	public static void main(String[] args) {
		
		launch(args);
	}
}