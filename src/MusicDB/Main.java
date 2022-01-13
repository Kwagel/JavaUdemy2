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
		List<Artist> artists = datasource.queryArtist(Datasource.ORDER_BY_DESC);
		if (artists == null){
			System.out.println("No artists!");
			return;
		}
		for (Artist artist : artists){
			System.out.println("ID = " + artist.getId() + ", NAME = " + artist.getName());
		}
		
		List<String> albumsForArtist = datasource.queryAlbumsForArtist("Carole King", Datasource.ORDER_BY_DESC);
				for (String album : albumsForArtist){
					System.out.println(album);
				}
		datasource.close();
	}
}
