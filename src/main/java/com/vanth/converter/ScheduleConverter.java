package com.vanth.converter;

import com.vanth.DTO.ScheduleDTO;
import com.vanth.entity.Schedule;

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
}
