/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author sushantprasad
 */
public class Dues {
    private String libid;
    private int amount;
    
    public Dues(String libid, int amount){
        this.libid = libid;
        this.amount = amount;
    }
    
    //getter methods
    public String getLibid(){  return libid;}
    public int getAmount(){    return amount;}
}
