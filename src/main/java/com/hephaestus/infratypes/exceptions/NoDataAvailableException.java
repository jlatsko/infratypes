package com.hephaestus.infratypes.exceptions;

/**
 * Can occur for RoadConditionsDAO if we cannot find
 * any data for a given highway.
 * 
 * @author jlatsko
 *
 */
public class NoDataAvailableException extends Exception
{
	public NoDataAvailableException()
	{
		super();
	}

	public NoDataAvailableException(String msg)
	{
		super(msg);
	}

	public NoDataAvailableException(String msg, Throwable tx)
	{
		super(msg, tx);
	}
	
	public NoDataAvailableException(Throwable tx)
	{
		super(tx);
	}
}
