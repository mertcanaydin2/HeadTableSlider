package greedz.corp.headtablemasaustu;

import javafx.embed.swing.SwingFXUtils;
import javafx.embed.swing.SwingFXUtils;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.awt.image.BufferedImage;

public class UIController implements Initializable {

    @FXML
    private FlowPane namesBox;
    @FXML
    private TextField firstNameField;
    @FXML
    private ComboBox<String> fontComboBox;
    @FXML
    private TextField fontSizeField;
    @FXML
    private TextField fileNameField;
    @FXML
    private VBox previewBox;
    private File selectedBgImageFile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fontComboBox.getItems().addAll("Arial", "Verdana", "Times New Roman");
        fontComboBox.setValue("Arial");
    }

    @FXML
    private void addNameField() {
        TextField newNameField = new TextField();
        newNameField.setPromptText("İsim");
        namesBox.getChildren().add(newNameField);
    }

    @FXML
    private void chooseBackgroundImage() {
        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) namesBox.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            selectedBgImageFile = file;
        }
    }

    @FXML
    private void createPowerPoint() throws IOException {
        int fontSize = Integer.valueOf(fontSizeField.getText());
        PowerPointGenerator generator = new PowerPointGenerator();
        generator.createPowerPoint(namesBox.getChildren(), fontComboBox.getValue(), fontSize, selectedBgImageFile, fileNameField);

        // PowerPoint dosyasını görüntülere dönüştür ve önizlemeyi güncelle
        String fileName = fileNameField.getText();
        String pptFilePath = System.getProperty("user.home") + "/Downloads/" + fileName + ".pptx";
        String outputDir = System.getProperty("user.home") + "/Downloads/preview";
        generator.convertToImages(pptFilePath, outputDir);
        updateSlidePreview(outputDir);
    }

    private void updateSlidePreview(String outputDir) throws IOException {
        previewBox.getChildren().clear();
        File dir = new File(outputDir);
        File[] files = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".png"));
        if (files != null) {
            for (File file : files) {
                BufferedImage bufferedImage = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(800); // Görüntü genişliğini ayarla
                imageView.setPreserveRatio(true);
                previewBox.getChildren().add(imageView);
            }
        }
    }

    @FXML
    private void resetForm() {
        namesBox.getChildren().clear();
        TextField initialField = new TextField();
        initialField.setPromptText("İsim");
        namesBox.getChildren().add(initialField);

        fontComboBox.setValue("Arial");
        fontSizeField.setPromptText("Font Boyutu");
        selectedBgImageFile = null;
    }
}
