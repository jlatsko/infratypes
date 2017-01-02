package com.hephaestus.infratypes.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;

import org.hibernate.Hibernate;
import org.hibernate.Session;

/**
 * BlobToByte - I'm having issues with hibernate converting from byte[] to Blob.
 * ClasCastException. Maybe this can help, it is a implementation of
 * http://hansonchar.blogspot.com/2005/06/oracle-blob-mapped-to-byte-in.html
 * also this article looks helpful too.
 * https://www.hibernate.org/73.html
 * 
 * @author jlatsko
 *
 */
public class BlobToByte implements Serializable
{
    byte[] image;

    public byte[] getImage() {
     return image;
    }

    public void setImage(byte[] image) {
     this.image = image;
    }

    /** Don't invoke this.  Used by Hibernate only. */
    public void setImageBlob(Blob imageBlob) {
     this.image = this.toByteArray(imageBlob);
    }

    /** Don't invoke this.  Used by Hibernate only. */
    public Blob getImageBlob(Session session) {
     return Hibernate.getLobCreator(session).createBlob(this.image);
    }

    private byte[] toByteArray(Blob fromBlob) {
     ByteArrayOutputStream baos = new ByteArrayOutputStream();
     try {
      return toByteArrayImpl(fromBlob, baos);
     } catch (SQLException e) {
      throw new RuntimeException(e);
     } catch (IOException e) {
      throw new RuntimeException(e);
     } finally {
      if (baos != null) {
       try {
        baos.close();
       } catch (IOException ex) {
       }
      }
     }
    }

    private byte[] toByteArrayImpl(Blob fromBlob, ByteArrayOutputStream baos)
     throws SQLException, IOException {
	
//     byte[] buf = new byte[4000];
     Long llen = new Long(fromBlob.length());	
     byte[] buf = new byte[llen.intValue()];	
     InputStream is = fromBlob.getBinaryStream();
     try {
      for (;;) {
       int dataSize = is.read(buf);

       if (dataSize == -1)
        break;
       baos.write(buf, 0, dataSize);
      }
     } finally {
      if (is != null) {
       try {
        is.close();
       } catch (IOException ex) {
       }
      }
     }
     return baos.toByteArray();
    }
}
