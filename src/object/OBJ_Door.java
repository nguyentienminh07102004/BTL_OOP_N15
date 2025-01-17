/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package object;

import entity.Entity;
import main.GamePanel;
/**
 *
 * @author Admin
 */
public class OBJ_Door extends Entity {
    GamePanel gp ;
    public OBJ_Door(GamePanel gp){
        super(gp);
        name = "Door" ;
        down1 = setup("/res/objects/door", gp.tileSize, gp.tileSize);
        collision = true ;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 72;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
