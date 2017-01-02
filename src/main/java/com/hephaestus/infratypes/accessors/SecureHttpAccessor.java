package com.hephaestus.infratypes.accessors;

/**
 * The CDOT streams are protected by ssl, https
 * I HttpComponent talk, these streams are not repeatable so the
 * Entity's are either BasicHttpEntity or HttpWrapper. 
 * 
 * TODO REVISIT This
 * 
 * @author jlatsko
 *
 */
public interface SecureHttpAccessor {
	public byte[] getByteArray();
}
