// Generated with g9.

package com.vanth.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="VEHICLE")
public class Vehicle implements Serializable {

    /** Primary key. */
    protected static final String PK = "id";

    @Id
    @Column(name="ID", unique=true, nullable=false, length=9)
    private String id;
    @Column(name="TITLE", nullable=false, length=100)
    private String title;
    @Column(name="DELAY_TIME", precision=10)
    private int delayTime;
    @OneToMany(mappedBy="vehicle")
    private Set<Schedule> schedule;
    @OneToMany(mappedBy="vehicle")
    private Set<Tracking> tracking;
    @ManyToOne(optional=false)
    @JoinColumn(name="ID_USER", nullable=false)
    @JsonIgnore
    private Users users;

    /** Default constructor. */
    public Vehicle() {
        super();
    }

    /**
     * Access method for id.
     *
     * @return the current value of id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter method for id.
     *
     * @param aId the new value for id
     */
    public void setId(String aId) {
        id = aId;
    }

    /**
     * Access method for title.
     *
     * @return the current value of title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter method for title.
     *
     * @param aTitle the new value for title
     */
    public void setTitle(String aTitle) {
        title = aTitle;
    }

    /**
     * Access method for delayTime.
     *
     * @return the current value of delayTime
     */
    public int getDelayTime() {
        return delayTime;
    }

    /**
     * Setter method for delayTime.
     *
     * @param aDelayTime the new value for delayTime
     */
    public void setDelayTime(int aDelayTime) {
        delayTime = aDelayTime;
    }

    /**
     * Access method for schedule.
     *
     * @return the current value of schedule
     */
    public Set<Schedule> getSchedule() {
        return schedule;
    }

    /**
     * Setter method for schedule.
     *
     * @param aSchedule the new value for schedule
     */
    public void setSchedule(Set<Schedule> aSchedule) {
        schedule = aSchedule;
    }

    /**
     * Access method for tracking.
     *
     * @return the current value of tracking
     */
    public Set<Tracking> getTracking() {
        return tracking;
    }

    /**
     * Setter method for tracking.
     *
     * @param aTracking the new value for tracking
     */
    public void setTracking(Set<Tracking> aTracking) {
        tracking = aTracking;
    }

    /**
     * Access method for users.
     *
     * @return the current value of users
     */
    public Users getUsers() {
        return users;
    }

    /**
     * Setter method for users.
     *
     * @param aUsers the new value for users
     */
    public void setUsers(Users aUsers) {
        users = aUsers;
    }

    /**
     * Compares the key for this instance with another Vehicle.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class Vehicle and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof Vehicle)) {
            return false;
        }
        Vehicle that = (Vehicle) other;
        Object myId = this.getId();
        Object yourId = that.getId();
        if (myId==null ? yourId!=null : !myId.equals(yourId)) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another Vehicle.
     *
     * @param other The object to compare to
     * @return True if the objects are the same
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Vehicle)) return false;
        return this.equalKeys(other) && ((Vehicle)other).equalKeys(this);
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
        if (getId() == null) {
            i = 0;
        } else {
            i = getId().hashCode();
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
        StringBuffer sb = new StringBuffer("[Vehicle |");
        sb.append(" id=").append(getId());
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
        ret.put("id", getId());
        return ret;
    }

}
