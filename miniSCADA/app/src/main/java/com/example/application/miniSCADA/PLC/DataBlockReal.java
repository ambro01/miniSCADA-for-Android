package com.example.application.miniSCADA.PLC;


public class DataBlockReal extends DataBlock{
    private static final int sizeReal = 4;

    public DataBlockReal(int number, int position, byte[] data){
        super(number, position, sizeReal, data);
    }
}
