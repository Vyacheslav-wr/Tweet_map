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

    // Название
    String Name;

    // Многоугольники из которых состоит
    ArrayList<Path2D.Double> Polygons;

    public String toString()
    {
        return String.format("Name: " + Name);
    }
}