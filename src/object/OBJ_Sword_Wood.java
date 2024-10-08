package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Wood extends Entity {

	public OBJ_Sword_Wood(GamePanel gp) {
		super(gp);
		name = "Normal Sword";
		down1 = setup("/res/objects/sword_normal", gp.tileSize, gp.tileSize);
		attackValue = 1;
	}

}
