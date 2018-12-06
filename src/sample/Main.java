package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import view.FlipView;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        ImageView image1 = new ImageView(new Image(getClass().getResourceAsStream("/img/A1.png")));
        ImageView image2 = new ImageView(new Image(getClass()
                .getResourceAsStream("/img/A2.png")));
        FlipView flip = new FlipView(image1, image2);
        primaryStage.setScene(new Scene(flip, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
