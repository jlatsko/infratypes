package com.hephaestus.infratypes.data;

public class GeoArea
{
	private GeoLocBigD center;
	private Float radius;
	private Float area;
	
	public GeoArea() {}
	public GeoArea(GeoLocBigD center, Float radius)
	{
		this.center=center;
		this.radius=radius;
	}
	
}
