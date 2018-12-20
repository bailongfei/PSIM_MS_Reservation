package com.controller;

import com.utils.IniUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ConfigController {

    private static Stage window;

    @FXML
    private TextArea srvGroupIDSTextArea;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    void showAndWait() {
        window = new Stage();
        window.setTitle("配置");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/configPane.fxml"));
        AnchorPane layout;
        try {
            layout = loader.load();
            Scene scene = new Scene(layout);
            window.setScene(scene);
            sceneBindEvent(scene);
            window.setAlwaysOnTop(true);
            window.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        bindEvent();
        String readIds = IniUtil.readSrvGroup();
        Platform.runLater(()-> srvGroupIDSTextArea.setText(readIds));
    }


    void close(){
        window.close();
    }

    private void sceneBindEvent(Scene scene){
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ESCAPE)){
                close();
            }
        });
    }

    private void bindEvent(){
        saveButton.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)){
                System.out.println("保存按钮被点击了");
                IniUtil.modifyIni(srvGroupIDSTextArea.getText());
                close();
            }
        });

        cancelButton.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)){
                close();
            }
        });

    }

}
