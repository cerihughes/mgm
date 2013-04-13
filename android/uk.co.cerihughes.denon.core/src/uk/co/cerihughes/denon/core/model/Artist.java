package uk.co.cerihughes.denon.core.model;

public class Artist extends MusicItem
{
	private Integer albumCount;

	@EHT(field = "album")
	public Integer getAlbumCount()
	{
		return albumCount;
	}

	public void setAlbumCount(Integer albumCount)
	{
		this.albumCount = albumCount;
	}
}
