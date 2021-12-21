package com.vanth.tcpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vanth.controller.LoginController;
import com.vanth.entity.Tracking;
import com.vanth.entity.Vehicle;
import com.vanth.repository.TrackingRepository;
import com.vanth.repository.VehicleRepository;

public class TCPServer extends Thread {
	static final int maximumClient = 500; // define max client connect to server
    static SocketChannel[] listClient = new SocketChannel[maximumClient];
    static SocketChannel[] userDevice = new SocketChannel[maximumClient]; // user's devices
    static LocalDateTime[] timeReceive = new LocalDateTime[maximumClient];
    static double[] distance = new double[maximumClient];
  
    static int[] userID = new int[maximumClient];
    public static Coord[] listLocation = new Coord[maximumClient];
    
    
    
    static int numberClient = 0;
    static int numberDevice = 0;
    static int port = 8081;
    
    
    public void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        // TODO code application logic here
        
        //init data for listClient and listLocation
        for (int i = 0; i < maximumClient; i++)
        {
            listClient[i] = null;
            listLocation[i] = null;
            userDevice[i] = null;
            userID[i] = -1;
            distance[i] = 0;
            timeReceive[i] = null;
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
                if(!key.isValid())
                {
                	System.out.println("Not valid");
                	continue;
                }
                if(key.isReadable())
                {
                    
                    SocketChannel socket = (SocketChannel)key.channel();
//                    System.out.println("comming");
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
                                
                                
                                // save distance
                                saveDistance(listLocation[current].getName(),LocalDateTime.now(),distance[current]);
                                distance[current] = 0;
                                
                                listClient[current] = null;
                                listLocation[current] = null;
                                numberClient--;
                                
                                socket.close();
                            }
                            break;
                        }
                    }
                    if (i < maximumClient) continue;
                    for (i = 0; i < maximumClient; i++) // search in listDevice
                    {
                    	if (userDevice[i] == null) continue;
                    	if (userDevice[i] == socket) // in listDevice
                    	{
                    		// handle for user's devices
                    		if (!handleDevice(socket, i))
                    		{
                    			System.out.println("Device disconnected " + socket);
                    			System.out.println("User id " + userID[i] + " has been removed");
                    			userDevice[i] = null;
                    			userID[i] = -1;
                    			numberDevice--;
                    			
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
                    System.out.println("Number: " + type);
                    if (type == 1) // simulator
                    {
                        if (numberClient == maximumClient)
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
                                    numberClient++;
                                    break;
                                }
                            }
                        }
                       
                    }
                    else if (type == 2)
                    {
                    	if (numberDevice == maximumClient)
                    	{
                    		buffer.clear();
                            boolean check = false;
                            byte[] dataByte = new byte[]{(byte)(check?1:0)};
                            buffer = ByteBuffer.wrap(dataByte);
                            socket.write(buffer);
                            socket.close();
                            
                            continue;
                    	}
                    	
                    	// send back to device
                        {
                            
                            for (i = 0; i < maximumClient; i++) // socket to list
                            {
                                if (userDevice[i] == null)
                                {
                                    userDevice[i] = socket;
                                    numberDevice++;
                                    System.out.println("Added");
                                    break;
                                }
                            }
                            
                            // send confirm to client
                            buffer.clear();
                            boolean check = true;
                            byte[] dataByte = new byte[]{(byte)(check?1:0)};
                            buffer = ByteBuffer.wrap(dataByte);
                            socket.write(buffer);
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
                    
//                    ServerSocketChannel serverChannel = (ServerSocketChannel)key.channel();
                    SocketChannel socket = server.accept();
                    socket.configureBlocking(false);
                    
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
        
        
        //save to database
        saveDataTracking(data.getName(),data.getX(), data.getY());
        
        if (listLocation[current]  != null)
        {
        	LocalDateTime now = LocalDateTime.now();
        	if (timeReceive[current] != null && timeReceive[current].getDayOfMonth() != now.getDayOfMonth())
        	{
        		// save distance to db
        		saveDistance(data.getName(),now,distance[current]);
        		
        		distance[current] = 0;
        		timeReceive[current] = now;
        	}
        	else
        	{
        		CoordRequest start = new CoordRequest();
                start.setLatitude(listLocation[current].getX());
                start.setLongitude(listLocation[current].getY());
                
                CoordRequest finish = new CoordRequest();
                finish.setLatitude(data.getX());
                finish.setLongitude(data.getY());
                
                double temp = -1;
                try
                {
                	temp = getDistance(start, finish);
	               	distance[current] = distance[current] + temp;
	               	timeReceive[current] = now;
                }
                catch (Exception e) 
                {
                	// TODO: handle exception
                }
        	}
        	
        }
       
        
        listLocation[current].setX(data.getX());
        listLocation[current].setY(data.getY());
        
        // send confirm to client
        boolean confirm = true;
        byte[] dataByte = new byte[]{(byte)(confirm?1:0)};
        buffer = ByteBuffer.wrap(dataByte);
        socket.write(buffer);
        
        return true;
    }
    
    boolean handleDevice(SocketChannel socket, int current) throws IOException
    {
 
    	ByteBuffer buffer = ByteBuffer.allocate(4);
        int a = -1;
        try
        {
            a = socket.read(buffer);
        }
        catch (IOException e)
        {
            
        }
        if (a <= 0) return false;
        
        
        // Receive user id
        ByteBuffer copy = ByteBuffer.allocate(4);
        // rotate byte
        for (int i = 0; i < buffer.capacity(); i++)  
        {
            copy.put(i,buffer.get(buffer.capacity()-i-1));
        }
        
        int userid = copy.getInt(0); // add to list???
        System.out.println("add user id = " + userid);
        
        
        // send confirm to client
        boolean confirm = true;
        byte[] dataByte = new byte[]{(byte)(confirm?1:0)};
        buffer = ByteBuffer.wrap(dataByte);
        socket.write(buffer);
        
        userID[current] = userid;
    	
    	return true;
    }
    
    // data arg: json data string
    public static void sendDataForUser(String data, int id) throws IOException
    {
    	for (int i = 0; i < maximumClient; i++)
    	{
    		if (userID[i] == -1) continue;
    		
    		if (userID[i] == id)
    		{
    			// send Sting data to client
    			SocketChannel socket = userDevice[i];
    			System.out.println("sent to: " + socket);
    			ByteBuffer buffer = ByteBuffer.allocate(256);
    			
    			buffer = ByteBuffer.wrap(data.getBytes());
    			socket.write(buffer);
//    			System.out.println(new String(buffer.array()));
    		}
    	}
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
    
    public boolean saveDataTracking(String id_vehicle,double x, double y)
    {
    	Connection connect = connectDatabase.getConnection();
        try
        {
        	LocalDateTime now = LocalDateTime.now();
        	
            String query = "INSERT INTO TRACKING(ID_VEHICLE,TRACKTIME,X,Y) VALUES ('" + id_vehicle + "','" + now.toString() + "'," + x + "," + y + ")";
            PreparedStatement ps = connect.prepareStatement(query);
            ps.executeUpdate();
//            System.out.println(query);
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    static double getDistance(CoordRequest start, CoordRequest finish) throws IOException, URISyntaxException {
        
    	RestTemplate restTemplate = new RestTemplate();
    	String resourceUrl = "https://bing-map-api.herokuapp.com/bingmap/distance/coordinates";
    	URI uri = new URI(resourceUrl);
    	
    	DistanceRequest distanceRequest = new DistanceRequest(start, finish);
    	
    	ResponseEntity<DistanceResponse> res = restTemplate.postForEntity(uri, distanceRequest, DistanceResponse.class);

    	
        return res.getBody().getTravelDistance();

    }
    
    public boolean saveDistance(String id_vehicle,LocalDateTime time, double distance)
    {
    	Connection connect = connectDatabase.getConnection();
    	
    	 try
         {
         	
             String query = "EXEC INSERTDISTANCE '" + time.toString() + "' , '" + id_vehicle + "', " + distance ;
             PreparedStatement ps = connect.prepareStatement(query);
             ps.executeUpdate();
//             System.out.println(query);
             
         }
         catch (Exception e)
         {
             e.printStackTrace();
             return false;
         }
         return true;
    }
    
  
    public void run()
    {
    	try
    	{
    		
//    		CoordRequest start = new CoordRequest();
//    		start.setLatitude(10.84993);
//    		start.setLongitude(106.79094);
//    		
//    		CoordRequest finish = new CoordRequest();
//    		finish.setLatitude(10.83876);
//    		finish.setLongitude(106.77443);
//    		
//    		MyGETRequest(start, finish);
    		
    		NotifyTimeTask.current = NotifyTimeTask.getCurrentSchdedule();
    		NotifyTimeTask.timer = new Timer();
    		
    		Timestamp timestamp = Timestamp.valueOf(NotifyTimeTask.current.getStartTime());
    		Date date = new Date(timestamp.getTime());
    		
    		NotifyTimeTask.timer.schedule(new NotifyTimeTask(), date);
    		
    		main(null);
    	}
    	catch (Exception e) 
    	{
			// TODO: handle exception
    		e.printStackTrace();
		}
    }
}
