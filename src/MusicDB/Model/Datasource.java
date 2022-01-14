package MusicDB.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Datasource {
	public static final String DB_NAME = "music.db";
	public static final String CONNECTION_STRING =
			"jdbc:sqlite:C:\\Users\\kenneth.a.lau\\IdeaProjects\\JavaLearning" + "\\JavaUdemy2\\src\\MusicDB\\" +
			DB_NAME;
	
	public static final String TABLE_ALBUMS = "albums";
	public static final String COLUMN_ALBUM_ID = "_id";
	public static final String COLUMN_ALBUM_NAME = "name";
	public static final String COLUMN_ALBUM_ARTIST = "artist";
	public static final int INDEX_ALBUM_ID = 1;
	public static final int INDEX_ALBUM_NAME = 2;
	public static final int INDEX_ALBUM_ARTIST = 3;
	
	
	public static final String TABLE_ARTISTS = "artists";
	public static final String COLUMN_ARTIST_ID = "_id";
	public static final String COLUMN_ARTIST_NAME = "name";
	public static final int INDEX_ARTIST_ID = 1;
	public static final int INDEX_ARTIST_NAME = 2;
	
	public static final String TABLE_SONGS = "songs";
	public static final String COLUMN_SONGS_ID = "_id";
	public static final String COLUMN_SONGS_TRACK = "track";
	public static final String COLUMN_SONGS_TITLE = "title";
	public static final String COLUMN_SONGS_ALBUM = "album";
	public static final int INDEX_SONG_ID = 1;
	public static final int INDEX_SONG_TRACK = 2;
	public static final int INDEX_SONG_TITLE = 3;
	public static final int INDEX_SONG_ALBUM = 4;
	
	public static final int ORDER_BY_NONE = 1;
	public static final int ORDER_BY_ASC = 2;
	public static final int ORDER_BY_DESC = 3;
	
	public static final String QUERY_ALBUMS_BY_ARTIST_START =
			"SELECT " + TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + " " + "FROM " + TABLE_ALBUMS + " INNER JOIN " +
			TABLE_ARTISTS + " ON " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ARTIST + " = " + TABLE_ARTISTS + "." +
			COLUMN_ARTIST_ID + " WHERE " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + " " + "= \"";
	
	public static final String QUERY_ALBUMS_BY_ARTIST_SORT =
			" ORDER BY " + TABLE_ALBUMS + "." + COLUMN_ARTIST_NAME + " COLLATE NOCASE ";
	
	public static final String QUERY_ARTISTS_START = " SELECT * FROM " + TABLE_ARTISTS;
	
	public static final String QUERY_ARTISTS_SORT = " ORDER BY " + COLUMN_ARTIST_NAME + " COLLATE NOCASE ";
	
	public static final String QUERY_ARTIST_FOR_SONG_START =
			"SELECT " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + "," + " " + TABLE_SONGS + "." + COLUMN_SONGS_TRACK +
			", " + TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + " FROM " + TABLE_ARTISTS + " INNER JOIN " + TABLE_ALBUMS +
			" ON " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ARTIST + " = " + TABLE_ARTISTS + "." + COLUMN_ARTIST_ID +
			" INNER JOIN " + TABLE_SONGS + " ON " + TABLE_SONGS + "." + COLUMN_SONGS_ALBUM + " = " + TABLE_ALBUMS +
			"." + COLUMN_ALBUM_ID + " WHERE " + TABLE_SONGS + "." + COLUMN_SONGS_TITLE + " = \"";
	
	
	public static final String QUERY_ARTIST_FOR_SONG_SORT =
			" ORDER BY " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + ", " + TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME +
			" COLLATE NOCASE ";
	
	
	public static final String TABLE_ARTIST_SONG_VIEW = "artist_list";
	public static final String CREATE_ARTIST_FOR_SONG_VIEW =
			"CREATE VIEW IF NOT EXISTS " + TABLE_ARTIST_SONG_VIEW + " AS SELECT " + TABLE_ARTISTS + "." +
			COLUMN_ARTIST_NAME + ", " + TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + " AS " + COLUMN_SONGS_ALBUM + ", " +
			TABLE_SONGS + "." + COLUMN_SONGS_TRACK + ", " + TABLE_SONGS + "." + COLUMN_SONGS_TITLE + " FROM " +
			TABLE_SONGS + " INNER JOIN " + TABLE_ALBUMS + " ON " + TABLE_SONGS + "." + COLUMN_SONGS_ALBUM + " = " +
			TABLE_ALBUMS + "." + COLUMN_ALBUM_ID + " INNER JOIN " + TABLE_ARTISTS + " ON " + TABLE_ALBUMS + "." +
			COLUMN_ALBUM_ARTIST + " = " + TABLE_ARTISTS + "." + COLUMN_ARTIST_ID + " ORDER BY " + TABLE_ARTISTS + "." +
			COLUMN_ARTIST_NAME + ", " + TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + ", " + TABLE_SONGS + "." +
			COLUMN_SONGS_TRACK;
	
	public static final String QUERY_VIEW_SONG_INFO =
			"SELECT " + COLUMN_ALBUM_NAME + ", " + COLUMN_SONGS_ALBUM + ", " + COLUMN_SONGS_TRACK + " FROM " +
			TABLE_ARTIST_SONG_VIEW + " WHERE " + COLUMN_SONGS_TITLE + " = \"";
	
	public static final String QUERY_VIEW_SONG_INFO_PREP =
			"SELECT " + COLUMN_ALBUM_NAME + ", " + COLUMN_SONGS_ALBUM + ", " + COLUMN_SONGS_TRACK + " FROM " +
			TABLE_ARTIST_SONG_VIEW + " WHERE " + COLUMN_SONGS_TITLE + " = ?";
	
	private Connection conn;
//	using a prepared statement to prevent SQL injection
	private PreparedStatement querySongInfoView;
	
	public boolean open() {
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING);
//			set the prepared statement like a normal statement except with prepareStatement instead of createStatement()
			querySongInfoView = conn.prepareStatement(QUERY_VIEW_SONG_INFO_PREP);
			return true;
			
		} catch (SQLException e) {
			System.out.println("Couldn't connect to database: " + e.getMessage());
			return false;
		}
	}
	
	public void close() {
		try {
			if (querySongInfoView != null){
				querySongInfoView.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("Couldn't close connection: " + e.getMessage());
		}
	}
	
	public List<Artist> queryArtist(int sortOrder) {
		
		StringBuilder sb = new StringBuilder(QUERY_ARTISTS_START);
		if (sortOrder != ORDER_BY_NONE) {
			sb.append(QUERY_ARTISTS_SORT);
			if (sortOrder != ORDER_BY_DESC) {
				sb.append("ASC");
			} else {
				sb.append("DESC");
			}
		}
		
		try (Statement statement = conn.createStatement();
			 ResultSet results = statement.executeQuery(sb.toString())) {
			
			List<Artist> artists = new ArrayList<>();
			while (results.next()) {
				Artist artist = new Artist();
//				Using index are more efficient as they know exactly where the column is, instead of comparing
//				column header names
				artist.setId(results.getInt(INDEX_ARTIST_ID));
				artist.setName(results.getString(INDEX_ARTIST_NAME));
				artists.add(artist);
			}
			
			return artists;
		} catch (SQLException e) {
			System.out.println("Query failed: " + e.getMessage());
			return null;
		}
	}
	
	public List<String> queryAlbumsForArtist(String artistName, int sortOrder) {
		StringBuilder sb = new StringBuilder(QUERY_ALBUMS_BY_ARTIST_START);
		sb.append(artistName);
		sb.append("\"");
		
		if (sortOrder != ORDER_BY_NONE) {
			sb.append(QUERY_ALBUMS_BY_ARTIST_SORT);
			if (sortOrder == ORDER_BY_DESC) {
				sb.append("DESC");
			} else {
				sb.append("ASC");
			}
		}
		System.out.println("SQL statement = " + sb.toString());
		try (Statement statement = conn.createStatement();
			 ResultSet results = statement.executeQuery(sb.toString())) {
			List<String> albums = new ArrayList<>();
			while (results.next()) {
				albums.add(results.getString(1));
			}
			return albums;
		} catch (SQLException e) {
			System.out.println("Query Failed : " + e.getMessage());
			return null;
		}
	}
	
	public List<SongArtist> queryArtistForSong(String songName, int sortOrder) {
		
		StringBuilder sb = new StringBuilder(QUERY_ARTIST_FOR_SONG_START);
		sb.append(songName);
		sb.append("\"");
		if (sortOrder != ORDER_BY_NONE) {
			sb.append(QUERY_ARTIST_FOR_SONG_SORT);
			if (sortOrder == ORDER_BY_DESC) {
				sb.append("DESC");
			} else {
				sb.append("ASC");
			}
		}
		System.out.println("SQL statement: " + sb.toString());
		
		try (Statement statement = conn.createStatement();
			 ResultSet results = statement.executeQuery(sb.toString())) {
			
			List<SongArtist> songArtists = new ArrayList<>();
			while (results.next()) {
				SongArtist songArtist = new SongArtist();
				songArtist.setArtistName(results.getString(1));
				songArtist.setTrack(results.getInt(2));
				songArtist.setAlbumName(results.getString(3));
				songArtists.add(songArtist);
			}
			return songArtists;
		} catch (SQLException e) {
			System.out.println("Error querying artist for song: " + e.getMessage());
			return null;
		}
	}
	
	public void querySongsMetadata() {
		String sql = "SELECT * FROM " + TABLE_SONGS;
		
		try (Statement statement = conn.createStatement();
			 ResultSet results = statement.executeQuery(sql)) {
			ResultSetMetaData meta = results.getMetaData();
			int numColumns = meta.getColumnCount();
			for (int i = 1; i <= numColumns; i++) {
				System.out.printf("Column %d in the songs table is names %s\n", i, meta.getColumnName(i));
			}
		} catch (SQLException e) {
			System.out.println("Query failed:" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public int getCount(String table) {
		String sql = "SELECT COUNT(*) AS count FROM " + table;
		try (Statement statement = conn.createStatement();
			 ResultSet results = statement.executeQuery(sql)) {
			int count = results.getInt("count");
			
			
			System.out.format("Count = %d\n", count);
			
			return count;
		} catch (SQLException e) {
			System.out.println("Error getting count: " + e.getMessage());
			return -1;
		}
	}
	
	public boolean createViewForSongArtists() {
		try (Statement statement = conn.createStatement()) {
			statement.execute(CREATE_ARTIST_FOR_SONG_VIEW);
			return true;
			
		} catch (SQLException e) {
			System.out.println("Create View failed " + e.getMessage());
			return false;
		}
	}
	
	public List<SongArtist> querySongInfoView(String title) {
		
		try {
//			by using ? as a placeholder, sql has a setString method on prepared statements to prevent sql injection.
//			for setString, first you define the placeholder (indexed from 1)  and then the think to replace it with.
//			this is for if you have multiple placeholders
			querySongInfoView.setString(1, title);
			ResultSet results = querySongInfoView.executeQuery();
			List<SongArtist> songArtists = new ArrayList<>();
			while (results.next()) {
				SongArtist artist = new SongArtist();
				artist.setArtistName(results.getString(1));
				artist.setAlbumName(results.getString(2));
				artist.setTrack(results.getInt(3));
				songArtists.add(artist);
			}
			return songArtists;
		} catch (SQLException e) {
			System.out.println("Error printing song info " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		
	}
}

