package com.example.application.miniSCADA;

public class DataBlock {
    private int dbNumber;
    private int position;
    private int size;
    private byte[] data;

    public DataBlock(int dbNumber, int position, int size, byte[] data){
        this.dbNumber = dbNumber;
        this.position = position;
        this.size = size;
        this.data = data;
    }

    public int getDbNumber(){
        return dbNumber;
    }

    public int getPosition(){
        return position;
    }

    public int getSize(){
        return size;
    }

    public byte[] getData(){
        return data;
    }
}
