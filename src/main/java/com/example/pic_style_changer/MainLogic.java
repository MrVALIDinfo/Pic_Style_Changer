package com.example.pic_style_changer;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MainLogic {

    public static Image applyFilter(Image inputImage, String filterType) {
        BufferedImage buffered = SwingFXUtils.fromFXImage(inputImage, null);

        int width = buffered.getWidth();
        int height = buffered.getHeight();

        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        switch (filterType) {
            case "Grayscale", "Invert" -> {
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        int rgba = buffered.getRGB(x, y);
                        Color color = new Color(rgba, true);

                        if (filterType.equals("Grayscale")) {
                            int gray = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                            color = new Color(gray, gray, gray, color.getAlpha());
                        } else if (filterType.equals("Invert")) {
                            color = new Color(
                                    255 - color.getRed(),
                                    255 - color.getGreen(),
                                    255 - color.getBlue(),
                                    color.getAlpha()
                            );
                        }

                        result.setRGB(x, y, color.getRGB());
                    }
                }
            }

            case "Blur" -> {
                int blurRadius = 1;

                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        int rSum = 0, gSum = 0, bSum = 0, aSum = 0, count = 0;

                        for (int dy = -blurRadius; dy <= blurRadius; dy++) {
                            for (int dx = -blurRadius; dx <= blurRadius; dx++) {
                                int nx = x + dx;
                                int ny = y + dy;

                                if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                                    Color neighborColor = new Color(buffered.getRGB(nx, ny), true);
                                    rSum += neighborColor.getRed();
                                    gSum += neighborColor.getGreen();
                                    bSum += neighborColor.getBlue();
                                    aSum += neighborColor.getAlpha();
                                    count++;
                                }
                            }
                        }

                        Color blurredColor = new Color(
                                rSum / count,
                                gSum / count,
                                bSum / count,
                                aSum / count
                        );
                        result.setRGB(x, y, blurredColor.getRGB());
                    }
                }
            }
        }

        return SwingFXUtils.toFXImage(result, null);
    }
}
