package com.example.noobchain.entity;

import com.example.noobchain.util.StringUtil;

import java.security.PublicKey;

/**
 * 交易输出，将显示交易发送给各方的最终金额。
 * 在新交易中被引用为输出时，可以作为有金额发送的证据
 */
public class TransactionOutput {
    public String id;
    public PublicKey reciepient;
    public float value;
    public String parentTransactionId;

    public TransactionOutput(PublicKey reciepient, float value, String parentTransactionId) {
        this.reciepient = reciepient;
        this.value = value;
        this.parentTransactionId = parentTransactionId;
        this.id = StringUtil.applySha256(StringUtil.getStringFromKey(reciepient)+Float.toString(value)+parentTransactionId);
    }
    public boolean isMine(PublicKey publicKey){
        return (publicKey == reciepient);
    }
}
