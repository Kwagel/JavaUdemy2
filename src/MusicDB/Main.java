package MusicDB;

import MusicDB.Model.Artist;
import MusicDB.Model.Datasource;

import java.util.List;

public class Main {
	public static void main(String[] args) {
		Datasource datasource = new Datasource();
		if (!datasource.open()){
			System.out.println("Can't open datasource");
			return;
		}
		List<Artist> artists = datasource.queryArtist();
		if (artists == null){
			System.out.println("No artists!");
			return;
		}
		for (Artist artist : artists){
			System.out.println("ID = " + artist.getId() + ", NAME = " + artist.getName());
		}
		datasource.close();
	}
}
