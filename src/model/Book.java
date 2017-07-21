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
public class Book {
    
    private String libid;
    private String isbn;
    private String title;
    private String author;
    private String category;
    private String publisher;
    private int year;
    private String status;
    
    public Book(String libid, String isbn, String title, String author, String category, String publisher, int year, String status){
        this.libid = libid;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.category = category;
        this.publisher = publisher;
        this.year = year;
        this.status = status;
    }
    
    //getter methods
    public String getAuthor(){  return author;}
    public String getCategory(){    return category;}
    public String getIsbn(){    return isbn;}
    public String getPublisher(){    return publisher;}
    public String getTitle(){    return title;}
    public int getYear(){    return year;}
    public String getLibid(){   return libid;}
    public String getStatus(){ return status;}
    
    //setter methods - most probably won't need any. Add later if needed.
    
}
