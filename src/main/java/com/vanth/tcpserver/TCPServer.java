package com.vanth.tcpserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import com.vanth.controller.LoginController;
import com.vanth.entity.Vehicle;
import com.vanth.repository.TrackingRepository;
import com.vanth.repository.VehicleRepository;

public class TCPServer extends Thread {
	static final int maximumClient = 100; // define max client connect to server
    static SocketChannel[] listClient = new SocketChannel[maximumClient];
    public static Coord[] listLocation = new Coord[maximumClient];
    static int number = 0;
    static int port = 8081;
    
    
    public void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        // TODO code application logic here
        
        //init data for listClient and listLocation
        for (int i = 0; i < maximumClient; i++)
        {
            listClient[i] = null;
            listLocation[i] = null;
        }
        
        Selector selector = Selector.open();
        
        ServerSocketChannel server = ServerSocketChannel.open();
        server.configureBlocking(false);
        server.socket().bind(new InetSocketAddress(port));
        server.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Bound to " + server);
        
        int current = -1;
        while(selector.isOpen())
        {
            selector.select();
            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
            while(keys.hasNext())
            {
                SelectionKey key = keys.next();
                if(!key.isValid()) continue;
                if(key.isReadable())
                {
                    
                    SocketChannel socket = (SocketChannel)key.channel();
                    
                    
                    int i = 0;
                    for (i = 0; i < maximumClient; i++) // search in listClient
                    {
                        if (listClient[i] == null) continue;
                        if (listClient[i] == socket) // has in listClient
                        {
                            current = i;
                            
                            //handle client here
                            if (!handleClient(socket, current))
                            {
                                System.out.println("Client Disconnected " + socket);
                                listClient[current] = null;
                                listLocation[current] = null;
                                number--;
                                
                                socket.close();
                            }
                            break;
                        }
                    }
                    
                    if (i < maximumClient) continue;
                    // not in listClient
                    // read client type and add to list
                    ByteBuffer buffer = ByteBuffer.allocate(4);
                    System.out.println("Lenght: " + buffer.toString());
                    socket.read(buffer);
                    
                    ByteBuffer copy = ByteBuffer.allocate(4);
                    // rotate byte
                    for (i = 0; i < buffer.capacity(); i++)  
                    {
                        copy.put(i,buffer.get(buffer.capacity()-i-1));
                    }
                    
                    int type = copy.getInt(0);  
//                    System.out.println("Number: " + type);
                    if (type == 1) // simulator
                    {
                        if (number == maximumClient)
                        {
                            buffer.clear();
                            boolean check = false;
                            byte[] dataByte = new byte[]{(byte)(check?1:0)};
                            buffer = ByteBuffer.wrap(dataByte);
                            socket.write(buffer);
                            socket.close();
                            
                            continue;
                        }
                        
                        
                        // send back to client
                        {
                            // send confirm to client
                            buffer.clear();
                            boolean check = true;
                            byte[] dataByte = new byte[]{(byte)(check?1:0)};
                            buffer = ByteBuffer.wrap(dataByte);
                            socket.write(buffer);
                            
                            for (i = 0; i < maximumClient; i++) // socket to list
                            {
                                if (listClient[i] == null)
                                {
                                    listClient[i] = socket;
                                    number++;
                                    break;
                                }
                            }
                        }
                       
                    }
                    /*
                    if(socket.read(buffer) == -1) //-1 is end of stream
                    { 
                        System.out.println("Client Disconnected " + socket);
                        socket.close();
                        continue;
                    }
                    else
                    {
                        // received data
                        buffer.flip();
                        socket.write(buffer); //echo data back to client
                    }
                    */
                    
                }
                else if(key.isAcceptable())
                {
                    
                    ServerSocketChannel serverChannel = (ServerSocketChannel)key.channel();
                    SocketChannel socket = serverChannel.accept();
                    socket.configureBlocking(false);
//                    socket.register(selector, SelectionKey.OP_WRITE);
                    socket.register(selector, SelectionKey.OP_READ);
                    System.out.println("Client Connected " + socket);  

                    /*
                    ByteBuffer buffer = ByteBuffer.allocate(100);
                    System.out.println("Lenght: " + buffer.toString());
                    socket.read(buffer);
                    System.out.println("Lenght: " + buffer.toString());
                    int type = buffer.getInt();
                    System.out.println("type = " + Integer.parseInt(new String(buffer.array())));
//                    type = 1;
                    if (type == 1) // simulator
                    {
                        if (number == maximumClient)
                        {
                            buffer.clear();
                            boolean check = false;
                            byte[] dataByte = new byte[]{(byte)(check?1:0)};
                            buffer = ByteBuffer.wrap(dataByte);
                            socket.write(buffer);
                            socket.close();
                            
                            continue;
                        }
                        if (true) // check registration in database
                        {
                            // send confirm to client
                            buffer.clear();
                            boolean check = true;
                            byte[] dataByte = new byte[]{(byte)(check?1:0)};
                            buffer = ByteBuffer.wrap(dataByte);
                            socket.write(buffer);
                            
                            for (int i = 0; i < maximumClient; i++) // socket to list
                            {
                                if (listClient[i] == null)
                                {
                                    listClient[i] = socket;
                                    number++;
                                    break;
                                }
                            }
                        }
                        else
                        {
                            buffer.clear();
                            boolean check = false;
                            byte[] dataByte = new byte[]{(byte)(check?1:0)};
                            buffer = ByteBuffer.wrap(dataByte);
                            socket.write(buffer);
                        }
                    } */
                    
                }
//                else if (key.isWritable())
//                {
//                    System.out.println("Hello world");
//                }
           }
           selector.selectedKeys().clear();
        }
    }
    
    public boolean handleClient(SocketChannel socket, int current) throws IOException, ClassNotFoundException, InterruptedException
    {
        ByteBuffer buffer = ByteBuffer.allocate(40);
        int a = -1;
        try
        {
            a = socket.read(buffer);
        }
        catch (IOException e)
        {
            
        }
        System.out.println("Received: " + a);
        if (a <= 0) return false;
        
        ByteBuffer copy = ByteBuffer.allocate(buffer.capacity());
        // rotate byte
        for (int i = 0; i < 16; i++)  
        {
            copy.put(i,buffer.get(8 - i%8 - 1 + (i/8)*8));
        }
        
        
        // retrieve data
        double x = copy.getDouble(0);
        double y = copy.getDouble(8);
       
        copy.clear();
        for (int i = 16; i < buffer.capacity(); i++)
        {
            copy.put(i-16,buffer.get(i));
        }
        
        // convert ByteBuffer to Byte array
        byte[] arr = new byte[copy.capacity()];
        copy.get(arr);
        String name = new String(arr);
        name = name.trim();
        // get data from byte array
        Coord data = new Coord(x,y,name);
        System.out.println("X = " + data.getX());
        System.out.println("Y = " + data.getY());
        System.out.println("Name: " + data.getName());
        
//        buffer.clear();
        if (listLocation[current] == null) // check duplicate name in the first connection
        {
            boolean check = true; 
            for (int i = 0; i < maximumClient; i++)
            {
                if (listLocation[i] == null) continue;
                if (data.getName().equalsIgnoreCase(listLocation[i].getName())) // duplicate name
                {
                	
                    check = false;
                    byte[] dataByte = new byte[]{(byte)(check?1:0)};
                    buffer = ByteBuffer.wrap(dataByte);
                    socket.write(buffer);
                    
                    
                    return true;
                }
            }
            
            if (!checkRetristration(name)) // check registration in database
            {
            	System.out.println("???");
                check = false;
                byte[] dataByte = new byte[]{(byte)(check?1:0)};
                buffer = ByteBuffer.wrap(dataByte);
                socket.write(buffer);
                    
                return true;
            }
            
            byte[] dataByte = new byte[]{(byte)(check?1:0)};
            buffer = ByteBuffer.wrap(dataByte);
            socket.write(buffer);
            listLocation[current] = data;
            listLocation[current].setX(-1);
            
            return true;
            
        }
        listLocation[current].setX(data.getX());
        listLocation[current].setY(data.getY());
        
        //save to database
//        Vehicle vehicle = new Vehicle();
//        vehicle.setId(name);
//        Tracking tracking = new Tracking(vehicle, x, y);
//        trackingRepo.save(tracking);
        
        // send confirm to client
        boolean confirm = false;
        byte[] dataByte = new byte[]{(byte)(confirm?1:0)};
        buffer = ByteBuffer.wrap(dataByte);
        socket.write(buffer);
        
        return true;
    }
    public boolean checkRetristration(String name)
    {
    	
        Connection connect = connectDatabase.getConnection();
        try
        {
            Statement st = connect.createStatement();
            String query = "SELECT TOP 1 ID FROM VEHICLE WHERE ID = '" + name + "'"; 
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    	
        
        return false;
    }
    
  
    public void run()
    {
    	try
    	{
    		main(null);
    	}
    	catch (Exception e) 
    	{
			// TODO: handle exception
    		e.printStackTrace();
		}
    }
}
