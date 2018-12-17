package com.controller;

import com.info.ResultMessage;
import com.info.UserInfo;
import com.nodes.ProgressFrom;
import com.nodes.ToastController;
import com.task.ReadCardTask;
import com.utils.FileLockManager;
import com.utils.IniUtil;
import com.utils.NumberUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.io.IOException;


public class BaseController {

    @FXML
    private TextField customerIdTextField;
    @FXML
    private TextField customerNoTextField;
    @FXML
    private TextField customerNameTextField;
    @FXML
    private Button SSCReadButton;
    @FXML
    private Button MSCReadButton;
    @FXML
    private Button IDCReadButton;
    @FXML
    private Button registerButton;
    @FXML
    private Button clearButton;
    @FXML
    private TextField customerIdField;
    @FXML
    private Button searchButton;
    @FXML
    private TextField srvGroupTextField;
    @FXML
    private TextField customerTypeTextField;
    @FXML
    private TextField staffTextField;
    @FXML
    private ComboBox<String> srvGroupComboBox;
    @FXML
    private ComboBox<String> customerTypeComboBox;
    @FXML
    private ComboBox<String> staffComboBox;

    public static Stage stage;
    private UserInfo userInfo;

    @FXML
    private void initialize() {
        setButtonEnable();
        bindEvent();
    }

    private void bindEvent() {
//        社保卡
        SSCReadButton.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)){
                readCard(ReadCardTask.SS_CARD,"");
            }
        });
//        磁条卡
        MSCReadButton.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)){
                readCard(ReadCardTask.MS_CARD,customerIdTextField.getText());
            }
        });
        customerIdTextField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)){
                readCard(ReadCardTask.MS_CARD,customerIdTextField.getText());
            }
        });

//        身份证
        IDCReadButton.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)){
                readCard(ReadCardTask.ID_CARD,"");
            }
        });
//        预约
        registerButton.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)){
//                System.out.println("预约");
                register();
            }
        });
//        清除
        clearButton.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)){
                cleanInfo();
            }
        });
//        右侧查找病人
        customerIdField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)){
                ResultMessage rs = NumberUtil.isID(customerIdField.getText());
                if (rs.isFlag()){
//                    右侧查询操作
                    System.out.println("右侧回车查询操作");
                } else {
                    ToastController.getInstance().makeToast(stage,rs.getInformation());
                }

            }
        });
        searchButton.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)){
                ResultMessage rs = NumberUtil.isID(customerIdField.getText());
                if (rs.isFlag()){
//                    右侧查询操作
                    System.out.println("右侧鼠标查询操作");
                } else {
                    ToastController.getInstance().makeToast(stage,rs.getInformation());
                }
            }
        });

//        关闭程序
        stage.setOnCloseRequest(event -> {
            FileLockManager fileLockManager = new FileLockManager("lock.lock");
            try {
                fileLockManager.unLock();
            } catch (IOException ignore) {
            }
            finally {
                System.exit(0);
            }
        });

    }

//    面板事件监听
    public void paneBindEvent(Scene scene){
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ESCAPE)) {
//                清空
                cleanInfo();
            }
            if (event.getCode().equals(KeyCode.F7)){
//                社保卡
                readCard(ReadCardTask.SS_CARD,"");
            }
            if (event.getCode().equals(KeyCode.F8)){
//                磁条卡
                readCard(ReadCardTask.MS_CARD,customerIdTextField.getText());
            }
            if (event.getCode().equals(KeyCode.F9)){
//                身份证
                readCard(ReadCardTask.ID_CARD,"");
            }
            if (event.getCode().equals(KeyCode.F5)){
//                预约
//                System.out.println("F5");
                register();
            }
            if (event.getCode().equals(KeyCode.F4)){
                ResultMessage rs = NumberUtil.isID(customerIdField.getText());
                if (rs.isFlag()){
//                    右侧查询操作
                    System.out.println("右侧快捷键查询操作");
                } else {
                    ToastController.getInstance().makeToast(stage,rs.getInformation());
                }
            }
        });

    }

//    读卡操作
    private void readCard(int readCardType ,String card){
        ReadCardTask readCardTask = new ReadCardTask(readCardType,card);
        ProgressFrom progressFrom = new ProgressFrom(readCardTask,stage,"读卡中，请稍后");
        progressFrom.activateProgressBar();
        readCardTask.valueProperty().addListener(lis -> verifyInfo(readCardTask.getValue()));
    }

//    验证是否读卡后有信息
    private void verifyInfo(ResultMessage<UserInfo> resultInfo) {
        if (resultInfo.getT() == null) {
            ToastController.getInstance().makeToast(stage,resultInfo.getInformation());
        } else {
            userInfo = resultInfo.getT();
            Platform.runLater(()->{
                customerIdTextField.setText(userInfo.getId());
                customerNameTextField.setText(userInfo.getUserName());
                customerNoTextField.setText(userInfo.getSFZ());
            });
        }
    }

//    显示提示信息
//    private void makeToast(String message){
//        if (toast==null){
//            toast = new Toast(stage, message, 2000, 500, 500);
//            toast.doSomething(() -> System.out.println(message));
//        } else {
//            toast.closeStage();
//            toast = new Toast(stage, message, 2000, 500, 500);
//            toast.doSomething(() -> System.out.println(message));
//        }
//    }

//    清空信息
    private void cleanInfo(){
        userInfo = null;
        Platform.runLater(()->{
            customerIdTextField.setText("");
            customerNameTextField.setText("");
            customerNoTextField.setText("");
        });
    }

//    登记
    private void register(){
        if (userInfo==null ){
            ToastController.getInstance().makeToast(stage,"当前无刷卡病人");
        } else {
            SchedulingController scheduling = new SchedulingController();
            scheduling.showAndWait();
        }

    }

//    允许选择其他功能
    private void setButtonEnable(){
        srvGroupTextField.setDisable(IniUtil.readButtonInfo("srvGroupTextField").equals("0"));
        srvGroupComboBox.setDisable(IniUtil.readButtonInfo("srvGroupTextField").equals("0"));
        customerTypeTextField.setDisable(IniUtil.readButtonInfo("srvGroupTextField").equals("0"));
        customerTypeComboBox.setDisable(IniUtil.readButtonInfo("srvGroupTextField").equals("0"));
        staffTextField.setDisable(IniUtil.readButtonInfo("srvGroupTextField").equals("0"));
        staffComboBox.setDisable(IniUtil.readButtonInfo("srvGroupTextField").equals("0"));
    }
}
