package fr.ecn.common.desktop.utils;

import java.awt.image.BufferedImage;

import fr.ecn.common.core.image.ColorImage;

public class ImageUtils {
	
	public static ColorImage fromAwt(java.awt.image.BufferedImage awtImage) {
		ColorImage colorImage = new ColorImage(awtImage.getWidth(), awtImage.getHeight());
		
		for (int i=0; i<awtImage.getWidth(); i++) {
			for (int j=0; j<awtImage.getHeight(); j++) {
				colorImage.setPixel(i, j, awtImage.getRGB(i, j));
			}
		}
		
		return colorImage;
	}
	
	public static java.awt.image.BufferedImage toAwt(ColorImage image) {
		BufferedImage awtImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		for (int i=0; i<image.getWidth(); i++) {
			for (int j=0; j<image.getHeight(); j++) {
				awtImage.setRGB(i, j, image.getPixel(i, j));
			}
		}
		
		return awtImage;
	}
}
