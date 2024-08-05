package greedz.corp.headtablemasaustu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("PowerPoint Creator");



        Image logoImage = new Image("C:\\Projects\\HeadTableMasaustu\\src\\main\\resources\\greedz\\corp\\headtablemasaustu\\static\\logo.png");
        ImageView logoImageView = new ImageView(logoImage);

        VBox vbox = new VBox();
        vbox.getChildren().add(logoImageView);
        vbox.getChildren().add(new Text("PowerPoint Creator"));
        vbox.setAlignment(Pos.CENTER);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root,700,500);

        primaryStage.setTitle("PowerPoint Creator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
