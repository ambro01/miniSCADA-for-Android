package com.example.application.miniSCADA.PLC;

import java.io.Serializable;

public class DataBlock implements Serializable{
    private static final long serialVersionUID = 3L;
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
