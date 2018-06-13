/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockrpg;

/**
 *
 * @author L
 */
public class Coord {
    private int x;
    private int y;
    private int z;
    
    public Coord() {
        this.x = 0;
        this.y = 0;
        this.z =0;
    }
    
    public Coord(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void setCoord(int[] arr) {
        this.x = arr[0];
        this.y = arr[1];
        this.y = arr[2];
    }
    
    public int[] getCoord() {
        int[] coords = new int[3];
        coords[0] = this.x;
        coords[1] = this.y;
        coords[2] = this.z;
        
        return coords;
    }
}
