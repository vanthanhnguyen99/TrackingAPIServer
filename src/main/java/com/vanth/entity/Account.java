// Generated with g9.

package com.vanth.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name="ACCOUNT")
public class Account implements Serializable {

    /** Primary key. */
    protected static final String PK = "username";

    @Id
    @Column(name="USERNAME", unique=true, nullable=false, length=100)
    private String username;
    @Column(name="PASSWORD", nullable=false, length=32)
    private String password;
    @OneToMany(mappedBy="account", cascade = CascadeType.ALL)
    private Set<Users> users;

    /** Default constructor. */
    public Account() {
        super();
    }

    /**
     * Access method for username.
     *
     * @return the current value of username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter method for username.
     *
     * @param aUsername the new value for username
     */
    public void setUsername(String aUsername) {
        username = aUsername;
    }

    /**
     * Access method for password.
     *
     * @return the current value of password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter method for password.
     *
     * @param aPassword the new value for password
     */
    public void setPassword(String aPassword) {
        password = aPassword;
    }

    /**
     * Access method for users.
     *
     * @return the current value of users
     */
    public Set<Users> getUsers() {
        return users;
    }

    /**
     * Setter method for users.
     *
     * @param aUsers the new value for users
     */
    public void setUsers(Set<Users> aUsers) {
        users = aUsers;
    }

    /**
     * Compares the key for this instance with another Account.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class Account and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof Account)) {
            return false;
        }
        Account that = (Account) other;
        Object myUsername = this.getUsername();
        Object yourUsername = that.getUsername();
        if (myUsername==null ? yourUsername!=null : !myUsername.equals(yourUsername)) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another Account.
     *
     * @param other The object to compare to
     * @return True if the objects are the same
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Account)) return false;
        return this.equalKeys(other) && ((Account)other).equalKeys(this);
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
        if (getUsername() == null) {
            i = 0;
        } else {
            i = getUsername().hashCode();
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
        StringBuffer sb = new StringBuffer("[Account |");
        sb.append(" username=").append(getUsername());
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
        ret.put("username", getUsername());
        return ret;
    }

}
