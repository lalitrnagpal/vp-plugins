package com.vp.plugin.aws.util;

import java.awt.*;
import java.awt.geom.*;
import java.io.*;

import org.apache.batik.anim.dom.*;
import org.apache.batik.bridge.*;
import org.apache.batik.gvt.*;
import org.apache.batik.util.*;
import org.w3c.dom.svg.*;

public class SVGPainter {

	private GraphicsNode rootGN;
	private Rectangle2D _bounds;

	public SVGPainter(String aPath) throws IOException {
		String xmlParser = XMLResourceDescriptor.getXMLParserClassName();
		SAXSVGDocumentFactory df = new SAXSVGDocumentFactory(xmlParser);
		SVGDocument doc = df.createSVGDocument(new File(aPath).toURI().toURL().toString());
		UserAgent userAgent = new UserAgentAdapter();
		DocumentLoader loader = new DocumentLoader(userAgent);
		BridgeContext ctx = new BridgeContext(userAgent, loader);
		ctx.setDynamicState(BridgeContext.DYNAMIC);
		GVTBuilder builder = new GVTBuilder();
		rootGN = builder.build(ctx, doc);
		_bounds = rootGN.getBounds();
	}

	public SVGPainter(InputStream aIn) throws IOException {
		String xmlParser = XMLResourceDescriptor.getXMLParserClassName();
		SAXSVGDocumentFactory df = new SAXSVGDocumentFactory(xmlParser);
		SVGDocument doc = df.createSVGDocument("http://", aIn);
		UserAgent userAgent = new UserAgentAdapter();
		DocumentLoader loader = new DocumentLoader(userAgent);
		BridgeContext ctx = new BridgeContext(userAgent, loader);
		ctx.setDynamicState(BridgeContext.DYNAMIC);
		GVTBuilder builder = new GVTBuilder();
		rootGN = builder.build(ctx, doc);
		_bounds= rootGN.getBounds();
	}

	public Rectangle2D getBounds() {
		return _bounds;
	}

	public void paint(Graphics2D aG, Rectangle2D aBounds) {
		
		Rectangle2D lMyBounds = _bounds;
		double lScale = 1;
		double lAlignTopLeftX = 0;
		double lAlignTopLeftY = 0;
		double lAlignCenterX = 0;
		double lAlignCenterY = 0;
		if (lMyBounds != null) {
			
			double lWidthScale = aBounds.getWidth()/lMyBounds.getWidth();
			double lHeightScale = aBounds.getHeight()/lMyBounds.getHeight();
			
			if (lWidthScale < lHeightScale) {
				lScale = lWidthScale;
			}
			else if (lHeightScale < lWidthScale) {
				lScale = lHeightScale;
			}
			
			// 1. scale
			// since rootGN#paint(...) will translate to its Bounds.location, so, all the following alignment need be done after 'scaled'
			aG.scale(lScale, lScale);
			
			// 2. align to top-left
			lAlignTopLeftX = lMyBounds.getX()*-1;
			lAlignTopLeftY = lMyBounds.getY()*-1;
			aG.translate(lAlignTopLeftX, lAlignTopLeftY);
			
			// 3. align to center
			double lScaledWidth = lMyBounds.getWidth()*lScale;
			double lScaledHeight = lMyBounds.getHeight()*lScale;
			lAlignCenterX = (aBounds.getWidth()-lScaledWidth)/2/lScale;
			lAlignCenterY = (aBounds.getHeight()-lScaledHeight)/2/lScale;
			aG.translate(lAlignCenterX, lAlignCenterY);
		}
		else {
			lScale = 1;
		}
		
		// in some case, rootGN.paint will change color's alpha
		Color lColor = aG.getColor();
		rootGN.paint(aG);
		aG.setColor(lColor);
		
		if (lMyBounds != null) {
			aG.translate(lAlignCenterX*-1, lAlignCenterY*-1);
			aG.translate(lAlignTopLeftX*-1, lAlignTopLeftY*-1);
			aG.scale(1/lScale, 1/lScale);
			
		}
	}

}