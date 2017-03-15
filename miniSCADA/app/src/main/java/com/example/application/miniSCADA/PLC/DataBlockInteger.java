package com.example.application.miniSCADA.PLC;


public class DataBlockInteger extends DataBlock{
    public static final int sizeInteger = 2;

    public DataBlockInteger(int number, int position, byte[] data){
        super(number, position, sizeInteger, data);
    }
}
