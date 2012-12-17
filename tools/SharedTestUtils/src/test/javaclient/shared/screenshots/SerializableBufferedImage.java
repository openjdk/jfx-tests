package test.javaclient.shared.screenshots;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import javax.imageio.ImageIO;

public class SerializableBufferedImage implements Serializable {

	private byte[] byteImage = null;

	public SerializableBufferedImage(BufferedImage bufferedImage) {
		this.byteImage = toByteArray(bufferedImage);
	}

	public BufferedImage getBufferedImage() {
		return fromByteArray(byteImage);
	}

	private BufferedImage fromByteArray(byte[] bytes) {
		try {
			if (bytes != null && (bytes.length > 0)) {
				return ImageIO.read(new ByteArrayInputStream(bytes));
			}
			return null;
		} catch (IOException e) {
			throw new IllegalArgumentException(e.toString());
		}
	}

	private byte[] toByteArray(BufferedImage image) {
		if (image != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				ImageIO.write(image, "png", baos);
			} catch (IOException e) {
				throw new IllegalStateException(e.toString());
			}
			byte[] b = baos.toByteArray();
			return b;
		}
		return new byte[0];
	}
}