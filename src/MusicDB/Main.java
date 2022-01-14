package MusicDB;

import MusicDB.Model.Artist;
import MusicDB.Model.Datasource;
import MusicDB.Model.SongArtist;

import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Datasource datasource = new Datasource();
		if (!datasource.open()) {
			System.out.println("Can't open datasource");
			return;
		}
		List<Artist> artists = datasource.queryArtist(Datasource.ORDER_BY_DESC);
		if (artists == null) {
			System.out.println("No artists!");
			return;
		}
		for (Artist artist : artists) {
			System.out.println("ID = " + artist.getId() + ", NAME = " + artist.getName());
		}
		
		List<String> albumsForArtist = datasource.queryAlbumsForArtist("Carole King", Datasource.ORDER_BY_DESC);
		for (String album : albumsForArtist) {
			System.out.println(album);
		}
		
		List<SongArtist> songArtists = datasource.queryArtistForSong("Go Your Own Way", Datasource.ORDER_BY_DESC);
		if (songArtists == null) {
			System.out.println("Couldn't find the artist for the song");
			return;
		}
		for (SongArtist artist : songArtists) {
			System.out.println(
					"Artist name = " + artist.getArtistName() + " Album name = " + artist.getAlbumName() + " Track =" +
					" " +
					artist.getTrack());
		}
		datasource.querySongsMetadata();
		
		int count = datasource.getCount(Datasource.TABLE_SONGS);
		System.out.println("Number of songs is : " + count);
		
		datasource.createViewForSongArtists();
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("enter a song title: ");
		String title = scanner.nextLine();
		
		List<SongArtist> songArtist = datasource.querySongInfoView(title);
		if (songArtist.isEmpty()) {
			System.out.println("Couldn't find the artist for the song");
			return;
		}
		for (SongArtist artist : songArtist) {
			System.out.println(
					"FROM VIEW - Artist name = " + artist.getArtistName() + ", Album name = " + artist.getAlbumName() +
					", Track number= " + artist.getTrack());
		}
		
		datasource.close();
		
//		SELECT name, album, track FROM artist_list WHERE title = ? OR artist = ?
	}
}
