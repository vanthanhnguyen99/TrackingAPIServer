package com.vanth.VehicleTrackingServer;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.vanth.converter.JsonConverter;
import com.vanth.entity.Schedule;
import com.vanth.entity.Vehicle;
import com.vanth.request_response.NotificationRequest;
import com.vanth.tcpserver.TCPServer;
import com.vanth.tcpserver.connectDatabase;

@Component
@EnableScheduling
public class ScheduledTasks {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);
	
	 @Scheduled(fixedRate = 30000)
	 public void scheduleTaskWithFixedRate() 
	 {
//	        LOGGER.info("Send email to producers to inform quantity sold items");
		 
		 	LocalDateTime now = LocalDateTime.now();
		 	Schedule schedule = getCurrentSchdedule();
		 	LocalDateTime time = schedule.getStartTime();
		 	
		 	int day = now.getDayOfMonth();
		 	int month = now.getMonthValue();
		 	int year = now.getYear();
		 	int hour = now.getHour();
		 	int min = now.getMinute();
		 	
//		 	System.out.println("Time " + hour + ":" +min);
		 	
		 	int day1 = time.getDayOfMonth();
		 	int month1 = time.getMonthValue();
		 	int year1 = time.getYear();
		 	int hour1 = time.getHour();
		 	int min1 = time.getMinute();
		 	
//		 	System.out.println("Time " + hour1 + ":" +min1);
		 	if (day == day1 && month == month1 && year == year1 && hour == hour1 && min == min1)
		 	{
		 		NotificationRequest notificationRequest = new NotificationRequest();
				
				notificationRequest.setId_user(getIdUserFromVehicle(schedule.getVehicle().getId()));
				notificationRequest.setTitle("Thông báo xuất phát");
				notificationRequest.setContent("Phương tiên " + schedule.getVehicle().getId() + " đã đến giờ xuất phát theo lịch trình");
				notificationRequest.setVehicle_id(schedule.getVehicle().getId());
				
				try 
				{
					String data = JsonConverter.convertObjectToJson(notificationRequest);
					TCPServer.sendDataForUser(data, notificationRequest.getId_user());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 	}
		 	
		 
	 }
	 
	 public static Schedule getCurrentSchdedule()
	 {
		Connection connect = connectDatabase.getConnection();
		try
	    {
			Statement st = connect.createStatement();
	        String query = "SELECT TOP 1 VEHICLE_ID, START_TIME FROM SCHEDULE WHERE [STATUS] >= 0 AND START_TIME <= GETdate() ORDER BY START_TIME DESC"; 
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
