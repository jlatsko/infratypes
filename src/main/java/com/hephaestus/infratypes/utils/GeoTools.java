package com.hephaestus.infratypes.utils;

import java.math.BigDecimal;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.hephaestus.infratypes.data.GeoLoc;
import com.hephaestus.infratypes.exceptions.InvalidDataException;

public class GeoTools {
	private static final Logger log = LogManager.getLogger(GeoTools.class);
	/**
	 * determines the distance from a geoposition of origin to another geoposition
	 * reference - http://www.arubin.org/files/geo_search.pdf
	 * @param lat
	 * @param longitude
	 * @return
	 */
	public static BigDecimal calcDistanceFromOrigin(GeoLoc origin, Float lat, Float longitude)
	{
		BigDecimal distance;
		
		distance = new BigDecimal(3956 * 2 * 
				Math.asin(Math.sqrt(
						Math.pow(Math.sin((origin.getLatitude() - Math.abs(lat.floatValue())) * Math.PI/180 / 2), 2) 
						+  Math.cos(origin.getLatitude() * Math.PI/180 ) * Math.cos(Math.abs(lat.floatValue()) * Math.PI/180) 
						*  Math.pow(Math.sin((origin.getLongitude() - longitude.doubleValue()) * Math.PI/180 / 2), 2) 
						)));		
		return distance;
	}
	
	public static Float calcFloatDistanceFromOrigin(GeoLoc origin, Float lat, Float longitude)
	{
		Float distance;
		
		distance = new Float(3956 * 2 * 
				Math.asin(Math.sqrt(
						Math.pow(Math.sin((origin.getLatitude() - Math.abs(lat.floatValue())) * Math.PI/180 / 2), 2) 
						+  Math.cos(origin.getLatitude() * Math.PI/180 ) * Math.cos(Math.abs(lat.floatValue()) * Math.PI/180) 
						*  Math.pow(Math.sin((origin.getLongitude() - longitude.doubleValue()) * Math.PI/180 / 2), 2) 
						)));		
		return distance;
	}
	
	/**
	 * creates a rectangle around the origin. Can be used to reduce the dataset size
	 * from calcDistanceFromOrigin.
	 * 
	 * reference http://www.arubin.org/files/geo_search.pdf page 12
	 * 
	 * @param origin
	 * @param area, in square miles, defaults to 10 sq miles if <1 or >200
	 * @return
	 */
	public static GeoLoc[] boxAroundOrigin(GeoLoc origin, int area)
	{
		if ((area<1) || (area>200))
			area = 10;
		
		// conversion between degrees and miles
		//		1° of latitude ~= 69 miles
		//		1°of longitude ~= cos(latitude)*69
		
		//		To calculate lon and lat for the rectangle:
		Float lon1 = (float) (origin.getLongitude()-area/Math.abs(Math.cos(Math.toRadians(origin.getLatitude()))*69)); 
		Float lon2 = (float) (origin.getLongitude()+area/Math.abs(Math.cos(Math.toRadians(origin.getLatitude()))*69)); 
		Float lat1 = origin.getLatitude()-(area/69); 
		Float lat2 = origin.getLatitude()+(area/69);	
		
		GeoLoc[] boxAr = null;
		try {
		// clockwise, start at upper left corner and assuming the origin is in North Amererica where lat>0 and lng<0
		   GeoLoc[] boxAr1 = {new GeoLoc(lat2, lon1), new GeoLoc(lat2, lon2), new GeoLoc(lat1, lon2), new GeoLoc(lat1, lon1)};
		   boxAr = boxAr1;
		} catch (InvalidDataException ide) {
			log.error(ide);
		}
		return boxAr;
	}
}
