package ch.bbw.aal.Procedure_M152.converter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FileConverter {
    public File jpgToPng(File jpgFile) throws IOException {

        File pngFile = new File("pngFile.png");

        BufferedImage img = ImageIO.read(jpgFile);
        ImageIO.write(img, "png", pngFile);

        return pngFile;
    }

    public File setWidthTo(int newWidth, File fullResImage) {
        try {
            Image img = new ImageIcon(ImageIO.read(fullResImage)).getImage();
            int currentWidth = img.getWidth(null);
            int currentHeight = img.getHeight(null);


            int newHeight = (currentHeight * newWidth) / currentWidth;


            Image scaledImage = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

            BufferedImage outImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            Graphics g = outImg.getGraphics();
            g.drawImage(scaledImage, 0, 0, null);
            g.dispose();

            File finalFile = new File("Output_File");

            ImageIO.write(outImg, "png", finalFile);

            return finalFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public File addWatermark(File rawFile, String watermarkText) {
        try {

            BufferedImage sourceImage = ImageIO.read(rawFile);
            Graphics2D g2d = (Graphics2D) sourceImage.getGraphics();

            // initializes necessary graphic properties
            AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
            g2d.setComposite(alphaChannel);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 180));
            FontMetrics fontMetrics = g2d.getFontMetrics();
            Rectangle2D rect = fontMetrics.getStringBounds(watermarkText, g2d);


            // calculates the coordinate where the String is painted
            int centerX = (sourceImage.getWidth() - (int) rect.getWidth()) / 2;
            int centerY = sourceImage.getHeight() / 2;

            // paints the textual watermark
            g2d.drawString(watermarkText, centerX, centerY);

            File watermarkedFile = new File("watermarked_file");

            ImageIO.write(sourceImage, "png", watermarkedFile);
            g2d.dispose();

            return addSecondColor(watermarkedFile, watermarkText);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private File addSecondColor(File rawFile, String watermarkText){
        try {
            BufferedImage sourceImage = ImageIO.read(rawFile);
            Graphics2D g2d = (Graphics2D) sourceImage.getGraphics();

            // initializes necessary graphic properties
            AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
            g2d.setComposite(alphaChannel);
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 180));
            FontMetrics fontMetrics = g2d.getFontMetrics();
            Rectangle2D rect = fontMetrics.getStringBounds(watermarkText, g2d);


            // calculates the coordinate where the String is painted
            int centerX = (sourceImage.getWidth() - (int) rect.getWidth()) / 2 + 5;
            int centerY = sourceImage.getHeight() / 2 + 5;

            // paints the textual watermark
            g2d.drawString(watermarkText, centerX, centerY);

            File watermarkedFile = new File("watermarked_file");

            ImageIO.write(sourceImage, "png", watermarkedFile);
            g2d.dispose();

            return watermarkedFile;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
