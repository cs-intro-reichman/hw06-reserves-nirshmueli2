
// This class uses the Color class, which is part of a package called awt,
// which is part of Java's standard class library.
import java.awt.Color;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {

		//// Hide / change / add to the testing code below, as needed.

		// // Tests the reading and printing of an image:
		// Color[][] tinypic = read("tinypic.ppm");
		// print(tinypic);

		// Color[][] ironman = read("ironman.ppm");

		// // Creates an image which will be the result of various
		// // image processing operations:

		// Color[][] Horizontalimage;
		// Color[][] VerticalImage;
		// Color[][] grayScaledImage;
		// Color[][] ScaledImage;
		// Color[][] blendImage;

		// // Tests the horizontal flipping of an image:
		// Horizontalimage = flippedHorizontally(tinypic);
		// System.out.println();
		// print(Horizontalimage);

		// VerticalImage = flippedVertically(tinypic);
		// System.out.println();
		// print(VerticalImage);

		// grayScaledImage = grayScaled(tinypic);
		// System.out.println();
		// print(grayScaledImage);

		// Color n1 = new Color(100, 40, 100);
		// Color n2 = new Color(200, 20, 40);
		// Color BlendColor = blend(n1, n2, 0.25);
		// System.out.println();
		// print(BlendColor);

		// blendImage = blend(tinypic, ironman, 0.5);
		// System.out.println();
		// print(blendImage);

	}

	/**
	 * Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file.
	 */
	public static Color[][] read(String fileName) {
		In in = new In(fileName);
		// Reads the file header, ignoring the first and the third lines.
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
		// Creates the image array
		Color[][] image = new Color[numRows][numCols];
		// Reads the RGB values from the file, into the image array.
		// For each pixel (i,j), reads 3 values from the file,
		// creates from the 3 colors a new Color object, and
		// makes pixel (i,j) refer to that object.
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				image[i][j] = new Color(in.readInt(), in.readInt(), in.readInt());
			}
		}
		return image;
	}

	// Prints the RGB values of a given color.
	private static void print(Color c) {
		System.out.print("(");
		System.out.printf("%3s,", c.getRed()); // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
		System.out.printf("%3s", c.getBlue()); // Prints the blue component
		System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting
	// image.
	private static void print(Color[][] image) {
		//// Replace this comment with your code
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[0].length; j++) {
				print(image[i][j]);
			}
			System.out.println();
		}
	}

	/**
	 * Returns an image which is the horizontally flipped version of the given
	 * image.
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {
		Color[][] Horizontalimage = new Color[image.length][image[0].length];
		for (int i = 0; i < Horizontalimage.length; i++) {
			for (int j = 0; j < Horizontalimage[0].length; j++) {
				Horizontalimage[i][j] = image[i][image[0].length - (j + 1)];
			}
		}
		return Horizontalimage;
	}

	/**
	 * Returns an image which is the vertically flipped version of the given image.
	 */
	public static Color[][] flippedVertically(Color[][] image) {
		Color[][] VerticalImage = new Color[image.length][image[0].length];
		for (int i = 0; i < VerticalImage.length; i++) {
			for (int j = 0; j < VerticalImage[0].length; j++) {
				VerticalImage[i][j] = image[image.length - (i + 1)][j];
			}
		}
		return VerticalImage;
	}

	// Computes the luminance of the RGB values of the given pixel, using the
	// formula
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object
	// consisting
	// the three values r = lum, g = lum, b = lum.
	public static Color luminance(Color pixel) {
		int lum = (int) (0.299 * pixel.getRed() + 0.587 * pixel.getGreen() + 0.114 * pixel.getBlue());
		Color lumPixel = new Color(lum, lum, lum);
		return lumPixel;
	}

	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) {
		Color[][] grayScaledImage = new Color[image.length][image[0].length];
		for (int i = 0; i < grayScaledImage.length; i++) {
			for (int j = 0; j < grayScaledImage[0].length; j++) {
				grayScaledImage[i][j] = luminance(image[i][j]);
			}
		}
		return grayScaledImage;
	}

	/**
	 * Returns an image which is the scaled version of the given image.
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int height, int width) {
		int H0 = image.length;
		int W0 = image[0].length;
		Color[][] scaledImage = new Color[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int newi = (int) (i * H0 / height);
				int newj = (int) (j * W0 / width);
				scaledImage[i][j] = image[newi][newj];
			}
		}
		return scaledImage;
	}

	/**
	 * Computes and returns a blended color which is a linear combination of the two
	 * given
	 * colors. Each r, g, b, value v in the returned color is calculated using the
	 * formula
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r,
	 * g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha) {
		int newRed = (int) (alpha * c1.getRed() + (1 - alpha) * c2.getRed());
		int newGreen = (int) (alpha * c1.getGreen() + (1 - alpha) * c2.getGreen());
		int newBlue = (int) (alpha * c1.getBlue() + (1 - alpha) * c2.getBlue());
		Color BlendColor = new Color(newRed, newGreen, newBlue);
		return BlendColor;
	}

	/**
	 * Cosntructs and returns an image which is the blending of the two given
	 * images.
	 * The blended image is the linear combination of (alpha) part of the first
	 * image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
		Color[][] scaledImage2;
		scaledImage2 = scaled(image2, image1.length, image1[0].length);
		Color[][] blendImage = new Color[image1.length][image1[0].length];
		for (int i = 0; i < image1.length; i++) {
			for (int j = 0; j < image1[0].length; j++) {
				blendImage[i][j] = blend(image1[i][j], scaledImage2[i][j], alpha);
			}
		}
		return blendImage;
	}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	public static void morph(Color[][] source, Color[][] target, int n) {
		Color[][] scaledTarget;
		// if (source.length != target.length || source[0].length != target[0].length) {
		scaledTarget = scaled(target, source.length, source[0].length);
		// System.out.println("displaying source");
		// display(source);
		// StdDraw.pause(1000);
		// System.out.println("displaying target");
		// display(target);
		for (int i = 0; i <= n; i++) {
			double w = (double) (n - i) / n;
			Color[][] curImage = blend(source, scaledTarget, w);
			// System.out.println("n:" + n + " i:" + i + " w:" + w);
			StdDraw.pause(500);
			display(curImage);
		}
		// } else {
		// for (int i = 0; i < n; i++) {
		// Color[][] curImage = blend(source, target, (n - i) / n);
		// display(curImage);
		// StdDraw.pause(500);
		// }
		// }
	}

	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(height, width);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
		// Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor(image[i][j].getRed(),
						image[i][j].getGreen(),
						image[i][j].getBlue());
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}
