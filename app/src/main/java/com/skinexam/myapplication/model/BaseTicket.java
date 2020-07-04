package com.skinexam.myapplication.model;

import java.util.List;

/**
 * Created by webwerks on 17/4/15.
 */
public abstract class BaseTicket {
    abstract public String getTicketId();
    abstract public String getTicketTitle();
    abstract public String getImage_1();
    abstract public String getImage_2();
    abstract public String getImage_3();
    abstract public List<String> getTicketImage();
    abstract public String getDate();
    abstract public String getTicketDesc();
}
