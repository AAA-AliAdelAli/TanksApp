/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tanks;

/**
 *
 * @author dell
 */
public class Point2D {
    double x, y;
    
    public Point2D() {
        
    }
    
    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public double getDistanceFrom(Point2D p) {
        return Math.sqrt( Math.pow(x - p.x, 2) + Math.pow(y - p.y, 2) );
    }
}
