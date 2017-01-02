package com.hephaestus.infratypes.exceptions;

public class InvalidDataException extends Exception
{
	public InvalidDataException()
	{
		super();
	}

	public InvalidDataException(String msg)
	{
		super(msg);
	}

	public InvalidDataException(String msg, Throwable tx)
	{
		super(msg, tx);
	}
	
	public InvalidDataException(Throwable tx)
	{
		super(tx);
	}
}
