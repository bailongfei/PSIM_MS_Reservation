package com;

import com.controller.BaseController;
import com.utils.FileLockManager;
import com.utils.LogUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        BaseController.stage = primaryStage;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("/fxml/basePane.fxml"));
        try {
            AnchorPane layout = loader.load();
            Scene scene = new Scene(layout);
            primaryStage.setScene(scene);
            BaseController controller = loader.getController();
            controller.paneBindEvent(scene);
            primaryStage.setTitle("华山北院当日约");
            primaryStage.heightProperty().addListener(lis->{
                if (primaryStage.getHeight()<750){
                    primaryStage.setHeight(750.0);
                }
            });
            primaryStage.widthProperty().addListener(lis->{
                if (primaryStage.getWidth()<1000){
                    primaryStage.setWidth(1000.0);
                }
            });
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/image/xhIcon.png")));
            primaryStage.show();
        } catch (IOException ignore) {

        }
    }

    public static void main(String[] args) {
//        FileLockManager fileLockManager = new FileLockManager("lock.lock");
//        try {
//            if (fileLockManager.Lock()){
//                launch();
//            } else {
//                LogUtil.markLog(2,"程序不允许多开");
//                System.exit(0);
//            }
//        } catch (IOException ignore) {
//        }
        launch();
    }

}
