package uk.co.cerihughes.denon.core.dao.impl.spotify;

import java.util.Collection;

import uk.co.cerihughes.denon.core.dao.DaoException;
import uk.co.cerihughes.denon.core.dao.EDaoType;
import uk.co.cerihughes.denon.core.dao.InfiniteServiceDao;
import uk.co.cerihughes.denon.core.dao.impl.RestServiceDao;
import uk.co.cerihughes.denon.core.dao.rest.impl.JsonContentTypeProcessor;
import uk.co.cerihughes.denon.core.model.Album;
import uk.co.cerihughes.denon.core.model.Artist;
import uk.co.cerihughes.denon.core.model.Playlist;
import uk.co.cerihughes.denon.core.model.Track;

public class SpotifyServiceDao extends RestServiceDao implements InfiniteServiceDao
{
	@Override
	public EDaoType getType()
	{
		return EDaoType.SPOTIFY_DIRECT;
	}

	private String getArtistSearchUrl(String predicate)
	{
		return String.format("http://ws.spotify.com/search/1/artist?q=%s", encodeParameter(predicate));
	}

	private String getAlbumSearchUrl(String predicate)
	{
		return String.format("http://ws.spotify.com/search/1/album?q=%s", encodeParameter(predicate));
	}

	private String getAlbumsForArtistId(String artistId)
	{
		return String.format("http://ws.spotify.com/lookup/1/.json?uri=spotify:artist:%s&extras=album", encodeParameter(artistId));
	}

	private String getTrackSearchUrl(String predicate)
	{
		return String.format("http://ws.spotify.com/search/1/track?q=%s", encodeParameter(predicate));
	}

	@Override
	public Collection<Playlist> searchPlaylists(String predicate)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Artist> searchArtists(String predicate) throws DaoException
	{
		final String url = getArtistSearchUrl(predicate);
		return get(url, APPLICATION_JSON, new JsonContentTypeProcessor(), new SpotifyArtistSearchJsonConverter());
	}

	@Override
	public Collection<Album> searchAlbums(String predicate) throws DaoException
	{
		final String url = getAlbumSearchUrl(predicate);
		return get(url, APPLICATION_JSON, new JsonContentTypeProcessor(), new SpotifyAlbumSearchJsonConverter());
	}

	@Override
	public Collection<Album> getAlbums(Artist artist) throws DaoException
	{
		final String url = getAlbumsForArtistId(artist.getId());
		return get(url, APPLICATION_JSON, new JsonContentTypeProcessor(), new SpotifyAlbumsForArtistJsonConverter());
	}

	@Override
	public Collection<Track> searchTracks(String predicate) throws DaoException
	{
		final String url = getTrackSearchUrl(predicate);
		return get(url, APPLICATION_JSON, new JsonContentTypeProcessor(), new SpotifyTrackSearchJsonConverter());
	}

	@Override
	public Collection<Track> getTracks(Album album)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Track> getTracks(Artist artist)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Track> getTracks(Playlist playlist)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
