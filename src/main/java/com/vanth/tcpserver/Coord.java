package com.vanth.tcpserver;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Coord {
	double x;
    double y;
    String name;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public static Coord getObject(byte[] data) throws IOException, ClassNotFoundException, InterruptedException
    {
        Coord res;
        ByteArrayInputStream input = new ByteArrayInputStream(data);
        Thread.sleep(100);
        ObjectInput object = new ObjectInputStream(input);
        
        
        res = (Coord)object.readObject();
        return res;
    }
    
    public static byte[] getByteArray(Coord data) throws IOException
    {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ObjectOutputStream streamOut = new ObjectOutputStream(output);
        streamOut.writeObject(data);
        
        return output.toByteArray();
    }
    public Coord(double x, double y, String name)
    {
        this.x = x;
        this.y = y;
        this.name = name;
    }
}
