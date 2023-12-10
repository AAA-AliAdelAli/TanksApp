/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package components;

import Tanks.TanksGLEventListener.Directions;
        
public abstract class Sprite {
    public int index;
    public double scale;
    public Directions direction;
    public Point2D position;
    
    public Sprite() {
        this.scale = 0.1;
        this.direction = Directions.up;
        this.position = new Point2D();
    }

    public Sprite(int index, double scale, Directions direction, Point2D position) {
        this.index = index;
        this.scale = scale;
        this.direction = direction;
        this.position = position;
    }   
}
