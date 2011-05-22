package jmp.ui.utilities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class JMSwingUtilities
{
	private static float hsbColor[] = new float[3];

	public static Color invertColor(Color c)
	{
		Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsbColor);
		hsbColor[2] = 1- hsbColor[2];
		return new Color(Color.HSBtoRGB(hsbColor[0], hsbColor[1],hsbColor[2]));
	}
	public static int extendAngle(int startAngle, int endAngle)
	{
		int extend = endAngle - startAngle;
		return java.lang.Math.abs(extend);
	}
	public static BufferedImage copyBufferedImage(BufferedImage bImage)
	{
		int w = bImage.getWidth(null);
		int h = bImage.getHeight(null);    
		BufferedImage bImage2 = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = bImage2.createGraphics();
		g2.drawImage(bImage, 0, 0, null);
		return bImage2;
	}
}
