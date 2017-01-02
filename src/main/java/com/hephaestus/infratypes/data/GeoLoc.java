package com.hephaestus.infratypes.data;

import java.io.Serializable;

import com.hephaestus.infratypes.exceptions.InvalidDataException;

/**
 * Specialization of com.cotrafficpics.common.data.Pair
 * 
 * TODO: convert from Float to BigDecimal for better precision
 * 
 * @author jlatsko
 * 
 */
public class GeoLoc extends Pair<Float, Float> implements Comparable<GeoLoc>,
      Serializable, Cloneable
{
	// the ctor's access what package protected, default. Not sure why
	// TODO - u could add some validation to lat & long like lat will always be
	//  positive and 2 digits to left of decimal. Long will always be negative.
	public GeoLoc(Float latitude, Float longitude) throws InvalidDataException
	{
		super(latitude, longitude);
	}

	public Float getLatitude()
	{
		return getFirst();
	}

	public void setLatitude(Float latitude)
	{
		super.fst = latitude;
	}

	public Float getLongitude()
	{
		return getSecond();
	}

	public void setLongitude(Float longitude)
	{
		super.snd = longitude;
	}

	public int hashCode()
	{
		return 3 * super.fst.hashCode() + 7 * super.snd.hashCode();
	}

	public boolean equals(GeoLoc other)
	{
		if (this == other)
			return true;

		if (other == null)
			return false;

		if (getClass() != other.getClass())
			return false;

		GeoLoc otherInst = (GeoLoc) other;
		return ((this.fst == other.fst) && (this.snd == other.snd));

	}

	// sort by increasing distance from this point
	public int compareTo(GeoLoc other)
	{
		// OR IS THIS TRUE
		Float a = this.getLatitude() - other.getLatitude();
		Float b = this.getLongitude() - other.getLongitude();
		Double dist = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
		return dist.intValue();
	}

	/*
	 * make deep copy for self object
	 * 
	 * @param src input object used to copy from
	 */
	public void clone(GeoLoc src)
	{
		this.fst = src.fst;
		this.snd = src.snd;
	}
}
