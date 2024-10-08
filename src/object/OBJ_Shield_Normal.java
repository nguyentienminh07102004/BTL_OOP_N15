package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Normal extends Entity {

	public OBJ_Shield_Normal(GamePanel gp) {
		super(gp);
		name = "Wood Shield";
		down1 = setup("/res/objects/shield_wood", gp.tileSize, gp.tileSize);
		defenseValue = 1;
	}

}
