package com.hephaestus.infratypes.data;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.hephaestus.infratypes.exceptions.InvalidDataException;

public class Pair<A, B> implements Serializable
{
	private static final Logger log = Logger.getLogger(Pair.class);

	public A fst;
	public B snd;

	public Pair(A fst, B snd) throws InvalidDataException
	{
		if (fst == null)
		{
			String msg = "NULL element passed to ctor: Pair.fst";
			log.error(msg);
			throw new InvalidDataException(msg);
		}

		if (snd == null)
		{
			String msg = "Null element passed to ctor: Pair.snd";
			log.error(msg);
			throw new InvalidDataException(msg);
		}

		this.fst = fst;
		this.snd = snd;
	}

	public A getFirst()
	{
		return fst;
	}

	public B getSecond()
	{
		return snd;
	}

	public void setFirst(A v)
	{
		fst = v;
	}

	public void setSecond(B v)
	{
		snd = v;
	}

	public String toString()
	{
		return "Pair[" + fst + "," + snd + "]";
	}

	private static boolean equals(Object x, Object y)
	{
		return (x == null && y == null) || (x != null && x.equals(y));
	}

	public boolean equals(Object other)
	{
		return other instanceof Pair && equals(fst, ((Pair) other).fst)
		      && equals(snd, ((Pair) other).snd);
	}

	public int hashCode()
	{
		log.debug("Entered Pair hashCode " + this.fst.getClass().getName() + " "
		      + this.snd.getClass().getName());
		if (fst == null)
			return (snd == null) ? 0 : snd.hashCode() + 1;
		else if (snd == null)
			return fst.hashCode() + 2;
		else
			return fst.hashCode() * 17 + snd.hashCode();
	}

	public <A, B> Pair<A, B> of(A a, B b)
	{
		Pair<A, B> local = null;
		try
		{
			local = new Pair<A, B>(a, b);
		}
		catch(InvalidDataException ide)
		{
			log.error(ide.getMessage(), ide);
			
		}
		return local;
	}
}
