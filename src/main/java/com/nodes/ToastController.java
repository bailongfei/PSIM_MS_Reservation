package com.nodes;

import javafx.stage.Stage;

public class ToastController {

    private static Toast toast;

    private ToastController(){}

    private static class innerClass{
        private static ToastController impl = new ToastController();
    }

    public static ToastController getInstance(){
        return innerClass.impl;
    }

    /**
     * 发送消息窗体
     */
    public void makeToast(Stage stage,String message){
        if (toast==null){
            toast = new Toast(stage, message, 2000, 500, 500);
            toast.doSomething(() -> System.out.println(message));
        } else {
            toast.closeStage();
            toast = new Toast(stage, message, 2000, 500, 500);
            toast.doSomething(() -> System.out.println(message));
        }
    }

}
