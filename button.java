/*c
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess2;

import java.awt.Graphics2D;
import java.awt.*;

/**
 *
 * @author vshah-21
 */
public class button {
    int x, y, width,height;
    String name = "aaaaa";
    public button(String name,int x,int y, int width,int height, Graphics2D g2d){
        this.x = x;
        this.y =y; 
        this.name = name;
        this.width = width;
        this.height = height;
        draw(g2d);
    }
    public void draw(Graphics2D g2d){
        g2d.setColor(Color.WHITE);
        Rectangle rect = new Rectangle(x,y,width,height);
        g2d.fill(rect);
        g2d.setColor(Color.RED);
        g2d.drawString(name,x,y);
        
    }
    public boolean isIn(int x, int y){
        if(x>=this.x&&x<=this.x+width&&y>=this.y&&y<=this.y+height){
            return true;
        }else{
            return false;
        }
    }
}
