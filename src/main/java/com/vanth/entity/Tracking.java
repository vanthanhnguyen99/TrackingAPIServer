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

@Entity(name="TRACKING")
@IdClass(Tracking.TrackingId.class)
public class Tracking implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * IdClass for primary key when using JPA annotations
     */
    public class TrackingId implements Serializable {
        Vehicle vehicle;
        java.time.LocalDateTime tracktime;
    }

    /** Primary key. */
    protected static final String PK = "TrackingPkTracking";

    @Id
    @Column(name="TRACKTIME", nullable=false)
    private LocalDateTime tracktime;
    @Column(name="X", nullable=false, precision=53)
    private double x;
    @Column(name="Y", nullable=false, precision=53)
    private double y;
    @ManyToOne(optional=false)
    @Id
    @JoinColumn(name="ID_VEHICLE", nullable=false)
    private Vehicle vehicle;

    /** Default constructor. */
    public Tracking() {
        super();
    }

    /**
     * Access method for tracktime.
     *
     * @return the current value of tracktime
     */
    public LocalDateTime getTracktime() {
        return tracktime;
    }

    /**
     * Setter method for tracktime.
     *
     * @param aTracktime the new value for tracktime
     */
    public void setTracktime(LocalDateTime aTracktime) {
        tracktime = aTracktime;
    }

    /**
     * Access method for x.
     *
     * @return the current value of x
     */
    public double getX() {
        return x;
    }

    /**
     * Setter method for x.
     *
     * @param aX the new value for x
     */
    public void setX(double aX) {
        x = aX;
    }

    /**
     * Access method for y.
     *
     * @return the current value of y
     */
    public double getY() {
        return y;
    }

    /**
     * Setter method for y.
     *
     * @param aY the new value for y
     */
    public void setY(double aY) {
        y = aY;
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
    
    public Tracking(Vehicle vehicle, double x, double y)
    {
    	this.tracktime = LocalDateTime.now();
    	this.vehicle = vehicle;
    	this.x = x;
    	this.y = y;
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
     * Compares the key for this instance with another Tracking.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class Tracking and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof Tracking)) {
            return false;
        }
        Tracking that = (Tracking) other;
        Object myVehicleId = this.getVehicleId();
        Object yourVehicleId = that.getVehicleId();
        if (myVehicleId==null ? yourVehicleId!=null : !myVehicleId.equals(yourVehicleId)) {
            return false;
        }
        Object myTracktime = this.getTracktime();
        Object yourTracktime = that.getTracktime();
        if (myTracktime==null ? yourTracktime!=null : !myTracktime.equals(yourTracktime)) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another Tracking.
     *
     * @param other The object to compare to
     * @return True if the objects are the same
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Tracking)) return false;
        return this.equalKeys(other) && ((Tracking)other).equalKeys(this);
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
        if (getTracktime() == null) {
            i = 0;
        } else {
            i = getTracktime().hashCode();
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
        StringBuffer sb = new StringBuffer("[Tracking |");
        sb.append(" vehicleId=").append(getVehicleId());
        sb.append(" tracktime=").append(getTracktime());
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
        ret.put("tracktime", getTracktime());
        return ret;
    }

}
