package greedz.corp.headtablemasaustu;

import org.apache.poi.sl.usermodel.SlideShow;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PowerPointToImages {
    public static void convertToImages(String pptFilePath, String outputDir) throws IOException {
        try (FileInputStream fis = new FileInputStream(pptFilePath);
             XMLSlideShow ppt = new XMLSlideShow(fis)) {
            Dimension pgsize = ppt.getPageSize();
            List<XSLFSlide> slides = ppt.getSlides();
            for (int i = 0; i < slides.size(); i++) {
                XSLFSlide slide = slides.get(i);

                BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D graphics = img.createGraphics();
                graphics.setPaint(Color.white);
                graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));

                slide.draw(graphics);

                File outputfile = new File(outputDir + "/slide-" + (i + 1) + ".png");
                ImageIO.write(img, "png", outputfile);
            }
        }
    }
}


