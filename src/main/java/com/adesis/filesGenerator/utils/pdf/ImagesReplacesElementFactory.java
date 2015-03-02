package com.adesis.filesGenerator.utils.pdf;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Element;
import org.xhtmlrenderer.extend.FSImage;
import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.extend.ReplacedElementFactory;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.pdf.ITextFSImage;
import org.xhtmlrenderer.pdf.ITextImageElement;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.simple.extend.FormSubmissionListener;

import com.lowagie.text.Image;

/**
 * @author Javier Lacalle
 *
 */
public class ImagesReplacesElementFactory implements ReplacedElementFactory {
	private static final String DIV = "div";
	private static final String CLASS = "class";
	private static final String CLASS_VALUE_REPLACE = "icon";
	private static final String CUSTOM_ATTRIBUTE_SRC = "data-img-src";
	private static final String IMGS_PATH = "templates/pdf/img/";

	private final ReplacedElementFactory superFactory;

	public ImagesReplacesElementFactory(final ReplacedElementFactory superFactory) {
		this.superFactory = superFactory;
	}

	@Override
	public ReplacedElement createReplacedElement(final LayoutContext c, final BlockBox box, final UserAgentCallback uac,
			final int cssWidth, final int cssHeight) {
		final Element element = box.getElement();

		final String nodeName = element.getNodeName();
		final String className = element.getAttribute(CLASS);

		if (DIV.equals(nodeName) && CLASS_VALUE_REPLACE.equals(className)) {
			if (!element.hasAttribute(CUSTOM_ATTRIBUTE_SRC)) {
				throw new UnsupportedOperationException(CUSTOM_ATTRIBUTE_SRC + " attribute is mandatory");
			}

			InputStream input = null;
			try {

				input = this.getClass().getClassLoader().getResourceAsStream(IMGS_PATH + element.getAttribute(CUSTOM_ATTRIBUTE_SRC));

				final byte[] bytes = IOUtils.toByteArray(input);
				final Image image = Image.getInstance(bytes);
				image.setAlignment(Image.LEFT);
				image.setBorder(0);
				image.setBorderWidth(0);
				final FSImage fsImage = new ITextFSImage(image);
				if (fsImage != null) {
					if (cssWidth != -1 || cssHeight != -1) {
						fsImage.scale(cssWidth, cssHeight);
					}
					return new ITextImageElement(fsImage);
				}
			} catch (final Exception e) {
				e.printStackTrace();
				throw new RuntimeException("Error trying to read embedded image", e);
			} finally {
				IOUtils.closeQuietly(input);
			}
		}
		return null;
	}

	@Override
	public void reset() {
		this.superFactory.reset();
	}

	@Override
	public void remove(final Element e) {
		this.superFactory.remove(e);

	}

	@Override
	public void setFormSubmissionListener(final FormSubmissionListener listener) {
		this.superFactory.setFormSubmissionListener(listener);
	}

}
