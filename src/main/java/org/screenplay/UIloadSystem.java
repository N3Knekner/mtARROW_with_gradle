package org.screenplay;

import javafx.application.Preloader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;

public class UIloadSystem extends Preloader {

    private Pane loadBar;
    private Scene scene;
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("Start UI LOAD SCREEN");

        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.getIcons().add(new Image("../../resources/main/icon.png"));

        stage.show();

        this.stage = stage;
    }

    @Override
    public void init() throws Exception {
        System.out.println("Initiate UI LOAD SCREEN");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        float windowX = (float) screenSize.getWidth()  *  0.2f;
        float windowY = (float) screenSize.getHeight() * 0.2f;

        AnchorPane all = new AnchorPane();
        all.setPrefSize(windowX, windowY);
        all.setMaxSize(windowX, windowY);
        all.setStyle("-fx-background-color: rgba(11,11,11,1); -fx-border-radius: 30px; -fx-background-radius: 10px; ");

        this.loadBar = new Pane();
        loadBar.setStyle("-fx-background-color: white; -fx-background-radius: 10px; ");
        loadBar.setPrefSize(100, windowY);

        Label title = new Label("MTS ARROW");
        title.setStyle("-fx-color: rgba(11,11,11,1);-fx-font-size: 36px; -fx-font-weight: bold;");
        title.setPrefSize(windowX,windowY);

        all.getChildren().add(loadBar);
        all.getChildren().add(title);

        AnchorPane.setLeftAnchor(loadBar, 0.0);
        AnchorPane.setBottomAnchor(loadBar, 0.0);

        StackPane.setAlignment(title, Pos.CENTER);

        this.scene = new Scene(all, windowX, windowY);
        scene.setFill(Paint.valueOf("rgba(0,0,0,0)"));
    }

    @Override
    public void handleApplicationNotification(Preloader.PreloaderNotification info) {
        if (info instanceof ProgressNotification) {
            loadBar.setPrefWidth(scene.getWidth() * (((ProgressNotification) info).getProgress() / 100));
        }
    }

    @Override
    public void handleStateChangeNotification(Preloader.StateChangeNotification info) {

        StateChangeNotification.Type type = info.getType();
        switch (type) {
            case BEFORE_START:
                System.out.println("Load Successful");
                this.stage.close();
                break;
        }


    }
}
