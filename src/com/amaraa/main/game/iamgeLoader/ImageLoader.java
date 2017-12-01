package com.amaraa.main.game.iamgeLoader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class ImageLoader {

    public static BufferedImage getImage(String src, Object obj) {
        try {
            BufferedImage img = ImageIO.read(obj.getClass().getResourceAsStream(src));
            return img;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
