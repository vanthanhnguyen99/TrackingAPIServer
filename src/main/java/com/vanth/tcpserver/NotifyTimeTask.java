package com.vanth.tcpserver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vanth.converter.JsonConverter;
import com.vanth.entity.Schedule;
import com.vanth.entity.Vehicle;
import com.vanth.request_response.NotificationRequest;

public class NotifyTimeTask extends TimerTask {
	public static Schedule current = null;
	public static Timer timer = null;
	public void run()
	{
		NotificationRequest notificationRequest = new NotificationRequest();
		
		notificationRequest.setId_user(getIdUserFromVehicle(current.getVehicle().getId()));
		notificationRequest.setTitle("Thông báo xuất phát");
		notificationRequest.setContent("Phương tiên " + current.getVehicle().getId() + " đã đến giờ xuất phát theo lịch trình");
		notificationRequest.setVehicle_id(current.getVehicle().getId());
		
		
		
		try {
			String data = JsonConverter.convertObjectToJson(notificationRequest);
			TCPServer.sendDataForUser(data, notificationRequest.getId_user());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Timestamp timestamp = Timestamp.valueOf(current.getStartTime());
		Date date = new Date(timestamp.getTime());
		
		current = getCurrentSchdedule();
		timer.schedule(new NotifyTimeTask(),date);
		
		
		
		
	}
	
	
	public static Schedule getCurrentSchdedule()
	{
		Connection connect = connectDatabase.getConnection();
        try
        {
            Statement st = connect.createStatement();
            String query = "SELECT TOP 1 VEHICLE_ID, START_TIME FROM SCHEDULE WHERE [STATUS] >= 0 AND START_TIME >= GETdate()"; 
            ResultSet rs = st.executeQuery(query);
            Schedule schedule = new Schedule();
            if (rs.next())
            {
            	Vehicle vehicle = new Vehicle();
            	vehicle.setId(rs.getString("VEHICLE_ID"));
            	schedule.setVehicle(vehicle);
            	
            	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            	
            	Timestamp timestamp = Timestamp.valueOf(rs.getString("START_TIME"));
            	LocalDateTime localDateTime = timestamp.toLocalDateTime();
            	
            	schedule.setStartTime(localDateTime);
            	
            	return schedule;
            }
            return null;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    	
	}
	
	int getIdUserFromVehicle(String vehicle_id)
	{
		Connection connect = connectDatabase.getConnection();
        try
        {
            Statement st = connect.createStatement();
            String query = "SELECT TOP 1 [ID_USER] FROM VEHICLE WHERE ID = '" + vehicle_id + "'"; 
            ResultSet rs = st.executeQuery(query);
            Schedule schedule = new Schedule();
            if (rs.next())
            {
            	int res = Integer.parseInt(rs.getString("ID_USER"));
            	
            	return res;
            }
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return -1;
	}
}
