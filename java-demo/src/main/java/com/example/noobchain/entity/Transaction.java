package com.example.noobchain.entity;

import com.example.noobchain.NoobChain;
import com.example.noobchain.util.StringUtil;

import java.security.*;
import java.util.ArrayList;
public class Transaction {
    public String transactionId;
    public PublicKey sender; // 资金发送方公钥
    public PublicKey reciepient; // 资金接收方公钥

    public float value; // 转移资金的价值/金额

    /**
     * 签名的目的是防止其他人篡改提交的交易
     *
     * 私钥对数据进行签名，公钥可用于验证完整性
     */
    public byte[] signature; // 加密签名
    public ArrayList<TransactionInput> inputs = new ArrayList<>(); // 输入，先对先前交易的引用，证明发送者有资金可以发送
    public ArrayList<TransactionOutput> outputs = new ArrayList<>(); // 输出，显示交易中收到的相关地址的金额

    private static int sequence = 0;

    public Transaction(PublicKey from, PublicKey to, float value, ArrayList<TransactionInput> inputs){
        this.sender = from;
        this.reciepient = to;
        this.value =value;
        this.inputs = inputs;
    }

    private String calulateHash(){
        sequence ++;
        return StringUtil.applySha256(
                StringUtil.getStringFromKey(sender)+
                        StringUtil.getStringFromKey(reciepient)+
                        Float.toHexString(value)+sequence
        );
    }

    /**
     * 对交易进行签名
     * @param privateKey
     */
    public void  generateSignature(PrivateKey privateKey){
        String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient) + Float.toString(value)	;
        signature = StringUtil.applyECDSAig(privateKey,data);
    }

    /**
     * 签名是否有效，交易是否被篡改
     * @return
     */
    public boolean verifiySignature() {
        String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient) + Float.toString(value)	;
        return StringUtil.verifyECDSASig(sender, data, signature);
    }


    /**
     * 事务方法，处理交易
     *
     * 检查交易是否有效
     */
    public boolean processTransaction(){
        if(verifiySignature() ==false){
            System.out.println("#Transaction Signature failed to verify");
        }

        /**
         * 是否存在未消费的交易
         */
        for (TransactionInput i : inputs){
            i.UTXO = NoobChain.UTXOs.get(i.transactionOutputId);
        }

        if(getInputsValue() < NoobChain.minimumTransaction){
            System.out.println("#Transaction Inputs to small: "+getInputsValue());
            return false;
        }


        float leftOver = getInputsValue();
        transactionId = calulateHash();
        outputs.add(new TransactionOutput(this.reciepient, value, transactionId));
        outputs.add(new TransactionOutput(this.sender,leftOver,transactionId));

        for (TransactionOutput o : outputs){
            NoobChain.UTXOs.put(o.id, o);
        }

        for (TransactionInput  i : inputs){
            if(i.UTXO == null) continue;
            NoobChain.UTXOs.remove(i.UTXO.id);
        }

        return true;
    }


    public float getInputsValue(){
        float total = 0;
        for (TransactionInput i : inputs){
            if(i.UTXO == null) continue;
            total += i.UTXO.value;
        }
        return total;
    }

    public float getOutputsValue(){
        float total =0;
        for (TransactionOutput o : outputs){
            total+=o.value;
        }
        return total;
    }
}
