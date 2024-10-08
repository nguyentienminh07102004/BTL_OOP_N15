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
public class OBJ_Boots extends Entity {
    public OBJ_Boots(GamePanel gp){
        super(gp);
        down1 = setup("/res/objects/boots", gp.tileSize, gp.tileSize);
        name = "Boots" ;
    }
}
