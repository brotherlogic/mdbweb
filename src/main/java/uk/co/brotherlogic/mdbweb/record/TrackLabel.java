package uk.co.brotherlogic.mdbweb.record;

import uk.co.brotherlogic.mdb.record.Track;

public class TrackLabel
{
	int trackNumber;

	public TrackLabel(Track track, String label, String value)
	{
		trackNumber = track.getTrackNumber();
	}

}
