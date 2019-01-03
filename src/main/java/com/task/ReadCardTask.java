package com.task;

import com.info.ResultMap;
import com.info.UserInfo;
import com.utils.LogUtil;
import com.utils.cardUtils.CardIdentity;
import javafx.concurrent.Task;

public class ReadCardTask extends Task<ResultMap<UserInfo>> {

    public static final int ID_CARD = 0;
    public static final int MS_CARD = 1;
    public static final int SS_CARD = 2;

    private int cardType = -1;

    private String customerNo;

    public ReadCardTask(int cardType, String customerNo) {
        this.cardType = cardType;
        this.customerNo = customerNo;
    }

    @Override
    protected ResultMap<UserInfo> call() {
        ResultMap<UserInfo> userInfo = new ResultMap<>();
        userInfo.setResultCode("0");
        userInfo.setResultMessage("错误调用读卡，读卡失败");
        try {

            switch (this.cardType) {
                case ID_CARD:
                    userInfo = CardIdentity.getUserInfoByCustomerCard();
                    break;
                case MS_CARD:
                    userInfo = CardIdentity.getUserInfoByCardString(customerNo);
                    break;
                case SS_CARD:
                    userInfo = CardIdentity.getUserInfoBySSCard();
                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            LogUtil.markLog(2, "错误调用读卡，读卡失败");

        }
        return userInfo;
    }

}
