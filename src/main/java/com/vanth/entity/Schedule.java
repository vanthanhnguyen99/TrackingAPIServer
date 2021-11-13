// Generated with g9.

package com.vanth.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="SCHEDULE")
@IdClass(Schedule.ScheduleId.class)
public class Schedule implements Serializable {

    /**
     * IdClass for primary key when using JPA annotations
     */
    public class ScheduleId implements Serializable {
        Vehicle vehicle;
        java.time.LocalDateTime startTime;
    }

    /** Primary key. */
    protected static final String PK = "SchedulePkSchedule";

    @Id
    @Column(name="START_TIME", nullable=false)
    private LocalDateTime startTime;
    @Column(name="START_X", nullable=false, precision=53)
    private double startX;
    @Column(name="START_Y", nullable=false, precision=53)
    private double startY;
    @Column(name="FINISH_TIME", nullable=false)
    private LocalDateTime finishTime;
    @Column(name="FINISH_X", nullable=false, precision=53)
    private double finishX;
    @Column(name="FINISH_Y", nullable=false, precision=53)
    private double finishY;
    @Column(name="STATUS", precision=10)
    private int status;
    @Column(name="FINISH")
    private LocalDateTime finish;
    @ManyToOne(optional=false)
    @JsonIgnore
    @Id
    @JoinColumn(name="VEHICLE_ID", nullable=false)
    private Vehicle vehicle;

    /** Default constructor. */
    public Schedule() {
        super();
    }

    /**
     * Access method for startTime.
     *
     * @return the current value of startTime
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Setter method for startTime.
     *
     * @param aStartTime the new value for startTime
     */
    public void setStartTime(LocalDateTime aStartTime) {
        startTime = aStartTime;
    }

    /**
     * Access method for startX.
     *
     * @return the current value of startX
     */
    public double getStartX() {
        return startX;
    }

    /**
     * Setter method for startX.
     *
     * @param aStartX the new value for startX
     */
    public void setStartX(double aStartX) {
        startX = aStartX;
    }

    /**
     * Access method for startY.
     *
     * @return the current value of startY
     */
    public double getStartY() {
        return startY;
    }

    /**
     * Setter method for startY.
     *
     * @param aStartY the new value for startY
     */
    public void setStartY(double aStartY) {
        startY = aStartY;
    }

    /**
     * Access method for finishTime.
     *
     * @return the current value of finishTime
     */
    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    /**
     * Setter method for finishTime.
     *
     * @param aFinishTime the new value for finishTime
     */
    public void setFinishTime(LocalDateTime aFinishTime) {
        finishTime = aFinishTime;
    }

    /**
     * Access method for finishX.
     *
     * @return the current value of finishX
     */
    public double getFinishX() {
        return finishX;
    }

    /**
     * Setter method for finishX.
     *
     * @param aFinishX the new value for finishX
     */
    public void setFinishX(double aFinishX) {
        finishX = aFinishX;
    }

    /**
     * Access method for finishY.
     *
     * @return the current value of finishY
     */
    public double getFinishY() {
        return finishY;
    }

    /**
     * Setter method for finishY.
     *
     * @param aFinishY the new value for finishY
     */
    public void setFinishY(double aFinishY) {
        finishY = aFinishY;
    }

    /**
     * Access method for status.
     *
     * @return the current value of status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Setter method for status.
     *
     * @param aStatus the new value for status
     */
    public void setStatus(int aStatus) {
        status = aStatus;
    }

    /**
     * Access method for finish.
     *
     * @return the current value of finish
     */
    public LocalDateTime getFinish() {
        return finish;
    }

    /**
     * Setter method for finish.
     *
     * @param aFinish the new value for finish
     */
    public void setFinish(LocalDateTime aFinish) {
        finish = aFinish;
    }

    /**
     * Access method for vehicle.
     *
     * @return the current value of vehicle
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * Setter method for vehicle.
     *
     * @param aVehicle the new value for vehicle
     */
    public void setVehicle(Vehicle aVehicle) {
        vehicle = aVehicle;
    }

    /** Temporary value holder for group key fragment vehicleId */
    private transient String tempVehicleId;

    /**
     * Gets the key fragment id for member vehicle.
     * If this.vehicle is null, a temporary stored value for the key
     * fragment will be returned. The temporary value is set by setVehicleId.
     * This behavior is required by some persistence libraries to allow
     * fetching of objects in arbitrary order.
     *
     * @return Current (or temporary) value of the key fragment
     * @see Vehicle
     */
    public String getVehicleId() {
        return (getVehicle() == null ? tempVehicleId : getVehicle().getId());
    }

    /**
     * Sets the key fragment id from member vehicle.
     * If this.vehicle is null, the passed value will be temporary
     * stored, and returned by subsequent calls to getVehicleId.
     * This behaviour is required by some persistence libraries to allow
     * fetching of objects in arbitrary order.
     *
     * @param aId New value for the key fragment
     * @see Vehicle
     */
    public void setVehicleId(String aId) {
        if (getVehicle() == null) {
            tempVehicleId = aId;
        } else {
            getVehicle().setId(aId);
        }
    }

    /**
     * Compares the key for this instance with another Schedule.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class Schedule and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof Schedule)) {
            return false;
        }
        Schedule that = (Schedule) other;
        Object myVehicleId = this.getVehicleId();
        Object yourVehicleId = that.getVehicleId();
        if (myVehicleId==null ? yourVehicleId!=null : !myVehicleId.equals(yourVehicleId)) {
            return false;
        }
        Object myStartTime = this.getStartTime();
        Object yourStartTime = that.getStartTime();
        if (myStartTime==null ? yourStartTime!=null : !myStartTime.equals(yourStartTime)) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another Schedule.
     *
     * @param other The object to compare to
     * @return True if the objects are the same
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Schedule)) return false;
        return this.equalKeys(other) && ((Schedule)other).equalKeys(this);
    }

    /**
     * Returns a hash code for this instance.
     *
     * @return Hash code
     */
    @Override
    public int hashCode() {
        int i;
        int result = 17;
        if (getVehicleId() == null) {
            i = 0;
        } else {
            i = getVehicleId().hashCode();
        }
        result = 37*result + i;
        if (getStartTime() == null) {
            i = 0;
        } else {
            i = getStartTime().hashCode();
        }
        result = 37*result + i;
        return result;
    }

    /**
     * Returns a debug-friendly String representation of this instance.
     *
     * @return String representation of this instance
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("[Schedule |");
        sb.append(" vehicleId=").append(getVehicleId());
        sb.append(" startTime=").append(getStartTime());
        sb.append("]");
        return sb.toString();
    }

    /**
     * Return all elements of the primary key.
     *
     * @return Map of key names to values
     */
    public Map<String, Object> getPrimaryKey() {
        Map<String, Object> ret = new LinkedHashMap<String, Object>(6);
        ret.put("vehicleId", getVehicleId());
        ret.put("startTime", getStartTime());
        return ret;
    }

}
