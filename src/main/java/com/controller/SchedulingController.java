package com.controller;

import com.info.ResultMessage;
import com.nodes.ToastController;
import com.utils.NumberUtil;
import com.utils.SMSUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SchedulingController {

    @FXML
    private TextField customerTelTextField;
    @FXML
    private Button registerButton;
    @FXML
    private Button cancelButton;

    private static Stage window;

    void showAndWait() {
        window = new Stage();
        window.setTitle("预约排班");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/scheduling.fxml"));
        AnchorPane layout;
        try {
            layout = loader.load();
            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.setAlwaysOnTop(true);
            sceneBindEvent(scene);
            window.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void initialize() {
        bindEvent();
    }

    private void bindEvent() {
//        关闭窗口
        cancelButton.setOnMouseClicked(event -> {
            System.out.println("号码："+customerTelTextField.getText());
        });
//        注册按钮
        registerButton.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)){
                System.out.println("点击注册按钮");
                sendSMS();
            }
        });
//        回车键监听
        customerTelTextField.setOnKeyPressed(event -> {
//            验证手机号
            if (event.getCode().equals(KeyCode.ENTER)){
                ResultMessage rs = NumberUtil.isPhone(customerTelTextField.getText());
                if (rs.isFlag()){
//                    执行预约操作
                    sendSMS();

                } else {
                    ToastController.getInstance().makeToast(window,rs.getInformation());
                }
            }
        });
    }

    //    面板事件绑定
    private void sceneBindEvent(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ESCAPE)) {
                window.close();
            }
        });
    }

    void close(){
       window.close();
    }

    private void sendSMS(){
        ResultMessage rs = SMSUtil.sendSMS(customerTelTextField.getText(),"");
        if (rs.isFlag()){
            window.close();
        } else {
            ToastController.getInstance().makeToast(window,rs.getInformation());
        }
    }


}
