package com.sourcey.Hackaroad.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by BSM on 2017-11-05.
 */

public class Case_List implements Serializable {

    public volatile static Case_List list;
    private Case_List() {}

    public static Case_List getInstance()
    {
        if(list ==null)
        {
            synchronized (Case_List.class)
            {
                if(list ==null)
                {
                    list = new Case_List();
                }
            }
        }
        return list;
    }

    public Case_List(String id, String habbitname, String date)
    {
        this.setId(id);
        this.sethabbitname(habbitname);
        this.setcreated_at(date);
    }

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("habbitname")
    @Expose
    private String habbitname;

    @SerializedName("created_at")
    @Expose
    private String created_at;


    public static Case_List getList()
    {
        return list;
    }

    public static void setList(Case_List list)
    {
        Case_List.list = list;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id=id;
    }

    public String gethabbitname()
    {
        return habbitname;
    }

    public void sethabbitname(String habbitname)
    {
        this.habbitname=habbitname;
    }

    public String getcreated_at()
    {
        return created_at;
    }

    public void setcreated_at(String created_at)
    {
        this.created_at=created_at;
    }


}
