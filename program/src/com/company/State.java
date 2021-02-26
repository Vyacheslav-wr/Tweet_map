package com.company;

import java.awt.geom.Path2D;
import java.util.ArrayList;

public class State
{
    State (String name, ArrayList<Path2D.Double> polygons)
    {
        Name = name;
        Polygons = polygons;
    }

    String Name;
    ArrayList<Path2D.Double> Polygons;
    double Opinion;

    public String toString()
    {
        return String.format("Name: " + Name);
    }
}