package cnn.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtil {
	
	public static BufferedImage readImage(String path)
	{
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("ImageUtil.readIamge\r\n" + e);
			return null;
		}
	}
	
	public static int[] getPixel(BufferedImage src, int x, int y) throws Exception
	{
		int[] argb = new int[4];
		if(x >= src.getWidth() || x < 0 || y >= src.getHeight() || y < 0)
		{
			throw new Exception("ImageUtil.getPixel\r\n");
		}
		int pixel = src.getRGB(x, y);
		argb[0] = (pixel & 0xff000000)>>24;//屏蔽低位，并移位到最低位  
	    argb[1] = (pixel & 0xff0000) >> 16;  
	    argb[2] = (pixel & 0xff00) >> 8;  
	    argb[3] = (pixel & 0xff);
	    return argb;
	}
	
}
