package com.example.noobchain.entity;

import com.example.noobchain.NoobChain;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Wallet {
    public PrivateKey privateKey;
    public PublicKey publicKey;

    public HashMap<String, TransactionOutput> UTOXs = new HashMap<>();

    public Wallet() {
        generateKeyPair();
    }

    public void generateKeyPair(){
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
            keyGen.initialize(ecSpec,random);
            KeyPair keyPair = keyGen.generateKeyPair();

            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    /**
     * 更新钱包
     *
     * 生成交易
     */
    public float getBalance(){
        float total = 0;

        for (Map.Entry<String,TransactionOutput> item : NoobChain.UTXOs.entrySet()){
            TransactionOutput UTXO = item.getValue();

            if(UTXO.isMine(publicKey)){
                UTOXs.put(UTXO.id,UTXO);
                total+=UTXO.value;
            }
        }
        return total;
    }

    public Transaction sendFunds(PublicKey _recipient, float value){
        if(getBalance()< value){
            System.out.println("#Not Enough funds to send transaction, Transaction Discarded.");
            return null;
        }

        ArrayList<TransactionInput> inputs = new ArrayList<>();

        float total = 0;
        for (Map.Entry<String, TransactionOutput> item : UTOXs.entrySet()){
            TransactionOutput UTOX = item.getValue();
            total += UTOX.value;
            inputs.add(new TransactionInput(UTOX.id));
            if(total > value) break;
        }

        Transaction transaction = new Transaction(publicKey, _recipient, value, inputs);
        transaction.generateSignature(privateKey);

        for (TransactionInput input : inputs){
            UTOXs.remove(input.transactionOutputId);
        }

        return transaction;


    }
}
