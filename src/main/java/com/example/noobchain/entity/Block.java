package com.example.noobchain.entity;

import com.example.noobchain.util.StringUtil;

import java.util.ArrayList;
import java.util.Date;

public class Block {
    public String hash;
    public String previousHash;
    public String merkleRoot;
    public ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    private Integer nonce;
    public long timeStamp;

    public Block(String previousHash) {
        this.previousHash = previousHash;
        this.nonce = 0;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }

    public String calculateHash(){
        String calculatedHash  = StringUtil.applySha256(
                previousHash +
                    merkleRoot +
                    Long.toString(timeStamp) +
                    Integer.toString(nonce)
                );
        return calculatedHash;
    }

    public void mineBlock(int difficulty){
        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!hash.substring(0, difficulty).equals(target)){
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block Mined!!! : "+hash);
    }


    public boolean addTransaction(Transaction transaction){
        if(transaction == null) return false;

        if(previousHash != "0"){
            if(transaction.processTransaction() != true){
                System.out.println("transaction failed to process. Discarded");
            }
        }
        transactions.add(transaction);
        System.out.println("Transaction Successfully added to Block");
        return true;
    }
}
