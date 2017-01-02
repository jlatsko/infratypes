package com.hephaestus.infratypes.utils;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import org.apache.log4j.Logger;

public class WRHCommonUtils
{
	private static final Logger log = Logger.getLogger(WRHCommonUtils.class);
	private static final byte[] JFIF_CONSTANT = new byte[] { 'J', 'F', 'I', 'F' };
	private static final byte[] EXIF_CONSTANT = new byte[] { 'E', 'x', 'i', 'F' };

	static public Calendar getGregorianCalendar()
	{
		Calendar cal = GregorianCalendar.getInstance();
		return cal;
	}

	/**
	 * Feb 18, 2008 at 6:40pm getGTime(2008, 1, 18, 18, 40);
	 * 
	 * @param year
	 * @param mon
	 *           - zero based 0=January;
	 * @return
	 */
	public static Date getGregDate(int year, int mon, int day, int hour, int min)
	{
		Calendar cal = getGregorianCalendar();
		cal.set(year, mon, day, hour, min);
		return cal.getTime();
	}

	/**
	 * Feb 18, 2008 at 6:40pm getGTime(2008, 1, 18, 18, 40);
	 * 
	 * @param year
	 * @param mon
	 *           - zero based 0=January;
	 * @return
	 */
	public static Timestamp getSQLTimestamp(int year, int mon, int day,
	      int hour, int min)
	{
		Calendar cal = getGregorianCalendar();
		cal.set(year, mon, day, hour, min);

		return new Timestamp(cal.getTimeInMillis());
	}

	/**
	 * convertImageToByteAr - using nio
	 * 
	 * @param image
	 * @return byte[] Second implementation Long llen = new Long(image.length());
	 *         content = new byte[llen.intValue()]; while (fis.available() > 0) {
	 *         fis.read(content); }
	 */
	public static byte[] convertImageToByteAr(File image)
	{
		byte[] content = null;
		FileInputStream fis = null;
		try
		{
			fis = new FileInputStream(image);
			FileChannel fc = fis.getChannel();

			ByteBuffer buf = fc.map(FileChannel.MapMode.READ_ONLY, 0, (int) fc
			      .size());
			Long llen = new Long(image.length());
			content = new byte[llen.intValue()];
			buf.get(content, 0, content.length);
		}
		catch (FileNotFoundException fne)
		{
			log.error(fne);
		}
		catch (IOException ioe)
		{
			log.error(ioe);
		}

		return content;
	}

	/**
	 * converts BufferedImage to Byte[].
	 * 
	 * @param img
	 * @return
	 * @throws IOException
	 */
	public static byte[] bufferedImageToByteArray(BufferedImage img)
	      throws IOException
	{
		byte[] imageInByte = null;
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(img, "jpg", baos);
			baos.flush();
			imageInByte = baos.toByteArray();
			baos.close();
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}

		return imageInByte;
	}

	/**
	 * creates thumbnail images
	 * 
	 * @param is
	 * @param os
	 * @param boxSize
	 */
	public static void constrain(InputStream is, ImageOutputStream os,
	      int boxSize)
	{
		try
		{
			// Read the source file
			BufferedImage input = ImageIO.read(is);

			// Get the original size of the image
			int srcHeight = input.getHeight();
			int srcWidth = input.getWidth();

			// Constrain the thumbnail to a predefined box size
			int height = boxSize;
			int width = boxSize;
			if (srcHeight > srcWidth)
			{
				width = (int) (((float) height / (float) srcHeight) * (float) srcWidth);
			}
			else if (srcWidth > srcHeight)
			{
				height = (int) (((float) width / (float) srcWidth) * (float) srcHeight);
			}

			// Create a new thumbnail BufferedImage
			BufferedImage thumb = new BufferedImage(width, height,
			      BufferedImage.TYPE_USHORT_565_RGB);
			Graphics g = thumb.getGraphics();
			g.drawImage(input, 0, 0, width, height, null);

			// Get Writer and set compression
			Iterator iter = ImageIO.getImageWritersByFormatName("JPG");
			if (iter.hasNext())
			{
				ImageWriter writer = (ImageWriter) iter.next();
				ImageWriteParam iwp = writer.getDefaultWriteParam();
				iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				iwp.setCompressionQuality(0.75f);
				writer.setOutput(os);
				IIOImage image = new IIOImage(thumb, null, null);
				writer.write(null, image, iwp);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * convertByteArToImage -
	 * 
	 * @param ar
	 * @return BufferedImage - TODO this can be turned into Thumbnail images
	 *         instead of storing Thumbnail images in the db.
	 */
	public static BufferedImage convertByteArToImage(byte[] ar)
	{
		BufferedImage bi = null;

		// reference
		// http://java.sun.com/j2se/1.5.0/docs/guide/imageio/spec/apps.fm3.html
		try
		{
			Iterator readers = ImageIO.getImageReadersByFormatName("jpeg");
			ImageReader reader = (ImageReader) readers.next();
			ImageInputStream iis = ImageIO.createImageInputStream(ar);
			reader.setInput(iis, true);
			bi = reader.read(0);
		}
		catch (Exception ex)
		{
			log.error(ex);
		}

		if (bi == null)
		{
			log.error("Null Image generated from byte[]");
		}
		return bi;
	}

	/**
	 * isjpeg
	 * 
	 * @return
	 */
	private boolean isJpeg(byte[] img_data)
	{

		return (img_data[0] == 0xFF)
		      && (img_data[1] == 0xD8)
		      && (Arrays.equals(JFIF_CONSTANT, Arrays
		            .copyOfRange(img_data, 6, 10)))
		      || (Arrays.equals(EXIF_CONSTANT, Arrays
		            .copyOfRange(img_data, 6, 10)));
	}

}
