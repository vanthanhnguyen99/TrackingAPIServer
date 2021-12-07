package com.vanth.converter;

import com.vanth.DTO.ScheduleDTO;
import com.vanth.entity.Schedule;
import com.vanth.entity.Vehicle;

public class ScheduleConverter {
	public static ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule)
	{
		ScheduleDTO scheduleDTO = new ScheduleDTO();
		scheduleDTO.setVehicle_id(schedule.getVehicleId());
		scheduleDTO.setStart_time(schedule.getStartTime());
		scheduleDTO.setFinish_time(schedule.getFinishTime());
		scheduleDTO.setStart_x(schedule.getStartX());
		scheduleDTO.setStart_y(schedule.getStartY());
		scheduleDTO.setFinish_x(schedule.getFinishX());
		scheduleDTO.setFinish_y(schedule.getFinishY());
		scheduleDTO.setStatus(schedule.getStatus());
		scheduleDTO.setFinish(schedule.getFinish());
		
		return scheduleDTO;
	}
	
	public static Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO)
	{
		Schedule schedule = new Schedule();
		schedule.setStartTime(scheduleDTO.getStart_time());
		schedule.setFinishTime(scheduleDTO.getFinish_time());
		schedule.setStartX(scheduleDTO.getStart_x());
		schedule.setStartY(scheduleDTO.getStart_y());
		schedule.setFinishX(scheduleDTO.getFinish_x());
		schedule.setFinishY(scheduleDTO.getFinish_y());
		schedule.setStatus(scheduleDTO.getStatus());
		schedule.setFinish(scheduleDTO.getFinish());

		
		return schedule;
	}
}
