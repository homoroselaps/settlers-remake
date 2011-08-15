package jsettlers.common.images;

/**
 * This is a virtual link to a image in a settler image file.
 * 
 * @see EImageLinkType
 * @author michael
 */
public final class ImageLink {
	private final EImageLinkType type;
	private final int file;
	private final int sequence;
	private final int image;

	/**
	 * Creates a new image link description
	 * 
	 * @param type
	 *            The type
	 * @param file
	 *            The file it is in
	 * @param sequence
	 *            The sequence index
	 * @param image
	 *            The image in the sequence, for {@value EImageLinkType#SETTLER}
	 *            images.
	 */
	public ImageLink(EImageLinkType type, int file, int sequence, int image) {
		this.type = type;
		this.file = file;
		this.sequence = sequence;
		this.image = image;
	}

	/**
	 * Gets the type of the image.
	 * 
	 * @return The image type
	 */
	public EImageLinkType getType() {
		return type;
	}

	/**
	 * Gets the file
	 * 
	 * @return The files number.
	 */
	public int getFile() {
		return file;
	}

	/**
	 * Gets the seuqence index inside the file.
	 * <p>
	 * For GUI and LANDSCAPE images, this defines the image.
	 * 
	 * @return The index
	 */
	public int getSequence() {
		return sequence;
	}

	/**
	 * Gets the image index inside the sequence
	 * 
	 * @return The image index
	 */
	public int getImage() {
		return image;
	}

	@Override
	public String toString() {
		return "image[type=" + type + ", file=" + file + ", sequence="
		        + sequence + ", image=" + image + "]";
	}
}
