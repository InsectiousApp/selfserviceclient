package com.selfservice.codev.aaaselfservice.Models;

import java.io.Serializable;

/**
 * Created by Codev on 3/29/2016.
 */
public class BikeItem implements Serializable{

    public String name;
    public String brand;
    public String price;
    public String baseprice;
    public String milage;
    public String style;
    public String imageurl;
    public String VAT;
    public String ST;
    public String RTO;
    public String OC;
    public String EMI;
    public String description;


    public BikeItem(String name, String brand, String price, String baseprice, String milage, String style, String imageurl, String VAT, String ST, String RTO, String OC, String EMI, String description )
    {
        this.name=name;
        this.brand=brand;
        this.price=price;
        this.baseprice=baseprice;
        this.milage=milage;
        this.style=style;
        this.imageurl=imageurl;
        this.VAT=VAT;
        this.ST=ST;
        this.RTO=RTO;
        this.OC=OC;
        this.EMI=EMI;
        this.description=description;
    }

}
