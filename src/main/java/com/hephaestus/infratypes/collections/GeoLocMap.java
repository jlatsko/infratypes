/**
 * 
 */
package com.hephaestus.infratypes.collections;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Map;
import java.util.Observer;
import java.util.TreeMap;

import com.hephaestus.infratypes.data.GeoLoc;
import com.hephaestus.infratypes.exceptions.InvalidDataException;

/**
 * specialize class for arranging JaxbDAO instances
 * as a function of geo position. The algorithm receives
 * a point, and arranges the instances in increasing distance
 * from the given point. Reference GeoLoc.compareTo
 * 
 * The Map's key is the distance from the given point
 * 
 * @author jlatsko
 *
 */
public class GeoLocMap
{
	// point of origin where object are aggranged from, typically this will
	//  be a camera's coordinates
	GeoLoc pntOfOrigin;
	
	// contains observers sorted by increasing distance from point of origin
	//  valid observers are Alerts, RoadConditions, Speeds, WeatherStations and DMS signs.
	// TODO - refactor to List, which is Ordered (maybe) and convert Double to BigDecimal 
	Map<Double, Observer> m_distMap = 
		new TreeMap<Double, Observer>(new Comparator<Double>()
		{
			public int compare(Double a, Double b)
			{
				return a.compareTo(b);
			}
		}); 
	
   public GeoLocMap(GeoLoc pnt)
   {
   	pntOfOrigin = pnt;
   }
   
   public GeoLocMap(Float latitude, Float longitude) throws InvalidDataException
   {
   	pntOfOrigin = new GeoLoc(latitude, longitude);
   }
   
   public GeoLocMap(BigDecimal latitude, BigDecimal longitude) throws InvalidDataException
   {
   	Float lat = new Float(latitude.floatValue());
   	Float lng = new Float(longitude.floatValue());
   	pntOfOrigin = new GeoLoc(lat, lng);
   }
   
   public Map getMap() { return m_distMap; }
}
