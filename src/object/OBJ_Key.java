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
public class OBJ_Key extends Entity {
    public OBJ_Key(GamePanel gp){
        super(gp);
        name = "Key" ;
        down1 = setup("/res/objects/key", gp.tileSize, gp.tileSize);
    }
}
