package com.hephaestus.infratypes.data;

import java.math.BigDecimal;

import com.hephaestus.infratypes.exceptions.InvalidDataException;

public class GeoLocBigD extends Pair<BigDecimal, BigDecimal>
{
	private GeoLocBigD() throws InvalidDataException {
		super(new BigDecimal(0.0), new BigDecimal(0.0));
	}
	
	// the ctor's access what package protected, default. Not sure why
	// TODO - u could add some validation to lat & long like lat will always be
	//  positive and 2 digits to left of decimal. Long will always be negative.
	public GeoLocBigD(BigDecimal latitude, BigDecimal longitude) throws InvalidDataException
	{
		super(latitude, longitude);
	}

	public BigDecimal getLatitude()
	{
		return getFirst();
	}

	public void setLatitude(BigDecimal latitude)
	{
		super.fst = latitude;
	}

	public BigDecimal getLongitude()
	{
		return getSecond();
	}

	public void setLongitude(BigDecimal longitude)
	{
		super.snd = longitude;
	}

	public int hashCode()
	{
		return 3 * super.fst.hashCode() + 7 * super.snd.hashCode();
	}

	public boolean equals(GeoLocBigD other)
	{
		if (this == other)
			return true;

		if (other == null)
			return false;

		if (getClass() != other.getClass())
			return false;

		GeoLocBigD otherInst = (GeoLocBigD) other;
		return ((this.fst == other.fst) && (this.snd == other.snd));

	}

	// sort by increasing distance from this point
	public int compareTo(GeoLocBigD other)
	{
		// OR IS THIS TRUE
		BigDecimal a = this.getLatitude().subtract(other.getLatitude());
		BigDecimal b = this.getLongitude().subtract(other.getLongitude());
		
		Double dist = Math.sqrt(Math.pow(a.doubleValue(), 2) + Math.pow(b.doubleValue(), 2));
		return dist.intValue();
	}

	/*
	 * make deep copy for self object
	 * 
	 * @param src input object used to copy from
	 */
	public void clone(GeoLocBigD src)
	{
		this.fst = src.fst;
		this.snd = src.snd;
	}
}
