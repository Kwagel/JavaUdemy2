package MusicDB.Model;

 class Song {
	private int id;
	private int track;
	private String name;
	private String albumId;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getTrack() {
		return track;
	}
	
	public void setTrack(int track) {
		this.track = track;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAlbumId() {
		return albumId;
	}
	
	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}
}
