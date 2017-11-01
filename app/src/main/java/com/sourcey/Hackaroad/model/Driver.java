package com.sourcey.Hackaroad.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by BSM on 2017-10-25.
 */


public class Driver implements Serializable {

    public volatile static Driver driver;
    private Driver() {}

    public static Driver getInstance()
    {
        if(driver ==null)
        {
            synchronized (Driver.class)
            {
                if(driver ==null)
                {
                    driver = new Driver();
                }
            }
        }
        return driver;
    }

    public Driver(String id, String name, String loginid, String password)
    {
        this.setId(id);
        this.setname(name);
        this.setloginid(loginid);
        this.setpassword(password);
    }

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("username")
    @Expose
    private String name;

    @SerializedName("userid")
    @Expose
    private String loginid;


    @SerializedName("userpw")
    @Expose
    private String password;

    public static Driver getDriver()
    {
        return driver;
    }

    public static void setDriver(Driver driver)
    {
        Driver.driver = driver;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id=id;
    }

    public String getname()
    {
        return name;
    }

    public void setname(String name)
    {
        this.name=name;
    }

    public String getLoginid()
    {
        return loginid;
    }

    public void setloginid(String loginid)
    {
        this.loginid=loginid;
    }

    public String getPassword()
    {
        return password;
    }

    public void setpassword(String password)
    {
        this.password=password;
    }


}
