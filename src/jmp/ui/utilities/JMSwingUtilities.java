package jmp.ui.utilities;

import java.awt.Color;

public class JMSwingUtilities
{
	private static float hsbColor[] = new float[3];

	public static Color invertColor(Color c)
	{
		Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsbColor);
		hsbColor[2] = 1- hsbColor[2];
		return new Color(Color.HSBtoRGB(hsbColor[0], hsbColor[1],hsbColor[2]));
	}
}
