package com.zackeryrobinson.zacksfirebase.model;

/**
 * Created by Zack on 9/21/2017.
 */

public class Movie {
    String name;
    String year;
    String director;
    String leadActor;

    public Movie(){ //need to have an empty constructor because I'm passing movie as a type

    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", year='" + year + '\'' +
                ", director='" + director + '\'' +
                ", leadActor='" + leadActor + '\'' +
                '}';
    }

    public Movie(String name, String year, String director, String leadActor) {
        this.name = name;
        this.year = year;
        this.director = director;
        this.leadActor = leadActor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getLeadActor() {
        return leadActor;
    }

    public void setLeadActor(String leadActor) {
        this.leadActor = leadActor;
    }
}
