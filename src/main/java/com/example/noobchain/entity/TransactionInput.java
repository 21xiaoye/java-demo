package com.example.noobchain.entity;

public class TransactionInput {
    public String transactionOutputId;
    /**
     * 未花费的交易输出
     */
    public TransactionOutput UTXO;

    public TransactionInput(String transactionOutputId){
        this.transactionOutputId = transactionOutputId;
    }
}
