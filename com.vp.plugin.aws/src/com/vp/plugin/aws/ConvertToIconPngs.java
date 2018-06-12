package com.vp.plugin.aws;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;

/*
 * makes the PNGs to be 18x18 for diagram palette.
 */
public class ConvertToIconPngs {
	
	public static void main(String[] args) {
		
		File lFromPngs = new File("original_pngs/shape");
		File lToPngs = new File("icons/shape");
		lToPngs.mkdir();
		
		for (File lFrom : lFromPngs.listFiles()) {
			convertTo(lFrom, new File(lToPngs, lFrom.getName()));
		}
	}
	private static void convertTo(File aFrom, File aTo) {
		if (aFrom.isDirectory()) {
			aTo.mkdir();
			
			for (File lFrom : aFrom.listFiles()) {
				convertTo(lFrom, new File(aTo, lFrom.getName()));
			}
		}
		else {
			try {
				BufferedImage lImage = ImageIO.read(aFrom);
				int lSize = Math.max(lImage.getWidth(), lImage.getHeight());
				BufferedImage lRectImage = new BufferedImage(lSize, lSize, BufferedImage.TYPE_4BYTE_ABGR);
				
				int lX;
				int lY;
				if (lImage.getWidth() > lImage.getHeight()) {
					lX = 0;
					lY = (int) ((lImage.getWidth()-lImage.getHeight())/2f);
				}
				else if (lImage.getHeight() > lImage.getWidth()) {
					lX = (int) ((lImage.getHeight()-lImage.getWidth())/2f);
					lY = 0;
				}
				else {
					lX = 0;
					lY = 0;
				}
				
				lRectImage.createGraphics().drawImage(lImage, lX, lY, null);
				
				BufferedImage lScaledImage = new BufferedImage(18, 18, BufferedImage.TYPE_4BYTE_ABGR);
				lScaledImage.createGraphics().drawImage(lRectImage.getScaledInstance(18, 18, Image.SCALE_SMOOTH), 0, 0, null);
				
				ImageIO.write(lScaledImage, "PNG", aTo);
				
			} catch (Exception lE) {
				lE.printStackTrace();
			}
		}
	}

}
