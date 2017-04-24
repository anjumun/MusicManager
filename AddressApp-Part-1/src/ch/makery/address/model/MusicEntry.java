package ch.makery.address.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MusicEntry {
	private StringProperty song;
	private StringProperty artist;
	private SimpleStringProperty year;
	private StringProperty combined;
	
	
	
	
	public MusicEntry (String song, String artist, String year, String combined){
		
		this.song = new SimpleStringProperty(song.trim());
		this.artist = new SimpleStringProperty(artist.trim());
		this.year = new SimpleStringProperty(year);
		this.combined = new SimpleStringProperty(combined.trim());
	}
	
	public String getSong(){
		return song.getValue();
	}
	public void setSong(String s){
		song.set(s);
	}
	public StringProperty getSongProperty(){
		return song;
	}
	
	////////////////////////////////////////
	public String getArtist(){
		return artist.get();
	}
	public void setArtist(String s){
		artist.set(s);
	}
	public StringProperty getArtistProperty(){
		return artist;
	}
	/////////////////////////////////////////
	public String getYear(){
		return year.get();
	}
	public void setYear(String y){
		year.set(y);
	}
	public StringProperty getYearProperty(){
		return year;
	}
	/////////////////////////////////////////
	public String getCombined(){
		return song.get();
	}
	public void setCombined(String s){
		combined.set(s);
	}
	public StringProperty getCombinedProperty(){
		return combined;
	}
}
