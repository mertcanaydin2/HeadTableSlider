package greedz.corp.headtablemasaustu;

import javafx.scene.control.Alert;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.sl.usermodel.TextParagraph;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xslf.usermodel.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javafx.scene.Node;
import javafx.scene.control.TextField;

public class PowerPointGenerator {

    public void createPowerPoint(List<Node> nameFields, String fontName, int fontSize, File bgImageFile, TextField fileNameField) throws IOException {
        XMLSlideShow ppt = new XMLSlideShow();

        for (Node node : nameFields) {
            if (node instanceof TextField) {
                String name = ((TextField) node).getText();
                XSLFSlide slide = ppt.createSlide();

                // Arka plan görselini ekle
                if (bgImageFile != null) {
                    try (FileInputStream is = new FileInputStream(bgImageFile)) {
                        byte[] pictureData = IOUtils.toByteArray(is);
                        PictureData pictureIndex = ppt.addPicture(pictureData, XSLFPictureData.PictureType.PNG);
                        XSLFPictureShape pic = slide.createPicture(pictureIndex);
                        pic.setAnchor(new Rectangle2D.Double(0, 0, slide.getSlideShow().getPageSize().getWidth(), slide.getSlideShow().getPageSize().getHeight()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                // İsim metnini ekle
                XSLFTextBox textBox = slide.createTextBox();
                textBox.setAnchor(new Rectangle(0, 230, 600, 400));
                textBox.setHorizontalCentered(true);

                XSLFTextParagraph paragraph = textBox.addNewTextParagraph();
                paragraph.setTextAlign(TextParagraph.TextAlign.CENTER);
                XSLFTextRun textRun = paragraph.addNewTextRun();
                textRun.setText(name);
                textRun.setFontColor(Color.BLACK);
                textRun.setFontSize((double) fontSize);
                textRun.setFontFamily(fontName);
                textBox.setHorizontalCentered(true);
            }
        }
        String fileName = fileNameField.getText();
        File outputFile = new File(System.getProperty("user.home") + "/Downloads/" + fileName + ".pptx");
        savePowerPointFile(ppt, outputFile);
        showSuccessAlert();
    }

    private void savePowerPointFile(XMLSlideShow ppt, File file) throws IOException {
        FileOutputStream out = new FileOutputStream(file);
        ppt.write(out);
        out.close();
        ppt.close();
    }

    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Başarılı");
        alert.setHeaderText(null);
        alert.setContentText("Slayt oluşturuldu ve indirilenler klasörüne kaydedildi.");

        alert.showAndWait();
    }

    public void convertToImages(String pptFilePath, String outputDir) throws IOException {
        File outputDirectory = new File(outputDir);
        if (!outputDirectory.exists()) {
            outputDirectory.mkdirs();
        }

        try (XMLSlideShow ppt = new XMLSlideShow(new FileInputStream(pptFilePath))) {
            Dimension pgsize = ppt.getPageSize();
            List<XSLFSlide> slides = ppt.getSlides();

            for (int i = 0; i < slides.size(); i++) {
                BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = img.createGraphics();

                // clear the drawing area
                graphics.setPaint(Color.white);
                graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));

                // render
                slides.get(i).draw(graphics);

                // save the output
                String fileName = outputDir + "/slide-" + (i + 1) + ".png";
                FileOutputStream out = new FileOutputStream(fileName);
                ImageIO.write(img, "png", out);
                out.close();
            }
        }
    }
}
