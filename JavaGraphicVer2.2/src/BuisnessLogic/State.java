package BuisnessLogic;

import java.awt.geom.Path2D;
import java.util.ArrayList;

public class State
{
    public State(String name, ArrayList<Path2D.Double> polygons)
    {
        Name = name;
        Polygons = polygons;
        ChangePoly = polygons;
    }

    // Название
    public String Name;

    // Многоугольники из которых состоит
    public ArrayList<Path2D.Double> Polygons;

    public ArrayList<Path2D.Double> ChangePoly;

    public String toString()
    {
        return String.format("Name: " + Name);
    }
}