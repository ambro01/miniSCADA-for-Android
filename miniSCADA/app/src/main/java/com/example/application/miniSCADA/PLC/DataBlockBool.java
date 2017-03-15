package com.example.application.miniSCADA.PLC;


public class DataBlockBool extends DataBlock{
    public static final int sizeBool = 1;
    private int bitPosition;

    public DataBlockBool(int number, int position, byte[] data, int bitPosition){
        super(number, position, sizeBool, data);
        this.bitPosition = bitPosition;
    }

    public int getBitPosition(){
        return bitPosition;
    }
}
