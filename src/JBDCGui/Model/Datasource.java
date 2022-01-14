package JBDCGui.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Datasource {
	public static final String DB_NAME = "music.db";
	public static final String CONNECTION_STRING =
			"jdbc:sqlite:C:\\Users\\kenneth.a.lau\\IdeaProjects\\JavaLearning" + "\\JavaUdemy2\\src\\JBDCGui\\" +
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
	
	

	
	
	public static final String INSERT_ARTISTS =
			"INSERT INTO " + TABLE_ARTISTS + '(' + COLUMN_ARTIST_NAME + ") VALUES(?)";
	public static final String INSERT_ALBUMS =
			"INSERT INTO " + TABLE_ALBUMS + '(' + COLUMN_ALBUM_NAME + ", " + COLUMN_ALBUM_ARTIST + ") VALUES(?, ?)";
	public static final String INSERT_SONGS =
			"INSERT INTO " + TABLE_SONGS + '(' + COLUMN_SONGS_TRACK + ", " + COLUMN_SONGS_TITLE + ", " +
			COLUMN_SONGS_ALBUM + ") VALUES(?, ?, ?)";
	
	public static final String QUERY_ARTIST =
			"SELECT " + COLUMN_ARTIST_ID + " FROM " + TABLE_ARTISTS + " WHERE " + COLUMN_ARTIST_NAME + " = ?";
	public static final String QUERY_ALBUM =
			"SELECT " + COLUMN_ALBUM_ID + " FROM " + TABLE_ALBUMS + " WHERE " + COLUMN_ALBUM_NAME + " = ?";
	
	private Connection conn;
	//	using a prepared statement to prevent SQL injection
	private PreparedStatement insertIntoArtists;
	private PreparedStatement insertIntoAlbums;

	
	private PreparedStatement queryArtist;
	private PreparedStatement queryAlbum;
	
	private static Datasource instance = new Datasource();
	private Datasource(){
	}
	
	public static Datasource getInstance(){
		return instance;
	}
	public boolean open() {
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING);
//			set the prepared statement like a normal statement except with prepareStatement instead of
//			createStatement()

			insertIntoArtists = conn.prepareStatement(INSERT_ARTISTS, Statement.RETURN_GENERATED_KEYS);
			insertIntoAlbums = conn.prepareStatement(INSERT_ALBUMS, Statement.RETURN_GENERATED_KEYS);
			queryArtist = conn.prepareStatement(QUERY_ARTIST);
			queryAlbum = conn.prepareStatement(QUERY_ALBUM);
			return true;
			
		} catch (SQLException e) {
			System.out.println("Couldn't connect to database: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	public void close() {
		try {
			
			if (insertIntoArtists != null) {
				insertIntoArtists.close();
			}
			if (insertIntoAlbums != null) {
				insertIntoAlbums.close();
			}
			
			if (queryArtist != null) {
				queryArtist.close();
			}
			if (queryAlbum != null) {
				queryAlbum.close();
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
	
	
	private int insertArtist(String name) throws SQLException {
		queryArtist.setString(1, name);
		ResultSet results = queryArtist.executeQuery();
		if (results.next()) {
			return results.getInt(1);
		} else {
//			Insert new artist as they don't exist
			insertIntoArtists.setString(1, name);
//			execute update returns the number of affected rows
			int affectedRows = insertIntoArtists.executeUpdate();
//			it should always be 1, if not something went wrong
			if (affectedRows != 1) {
				throw new SQLException("Could'nt insert artist!");
			}
//			returns the generated key, which is _id
			ResultSet generatedKeys = insertIntoArtists.getGeneratedKeys();
			if (generatedKeys.next()) {
				return generatedKeys.getInt(1);
			} else {
				throw new SQLException("Couldn't get _id for artist");
			}
		}
	}
	
	private int insertAlbum(String name, int artistId) throws SQLException {
		queryAlbum.setString(1, name);
		ResultSet results = queryAlbum.executeQuery();
		if (results.next()) {
			return results.getInt(1);
		} else {
//			Insert new album as they don't exist
			insertIntoAlbums.setString(1, name);
			insertIntoAlbums.setInt(2, artistId);
//			execute update returns the number of affected rows
			int affectedRows = insertIntoAlbums.executeUpdate();
//			it should always be 1, if not something went wrong
			if (affectedRows != 1) {
				throw new SQLException("Could'nt insert album!");
			}
//			returns the generated key, which is _id
			ResultSet generatedKeys = insertIntoAlbums.getGeneratedKeys();
			if (generatedKeys.next()) {
				return generatedKeys.getInt(1);
			} else {
				throw new SQLException("Couldn't get _id for album");
			}
		}
	}

}

