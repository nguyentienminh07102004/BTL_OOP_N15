package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_OldMan extends Entity {
	public NPC_OldMan(GamePanel gp) {
		super(gp);
		direction = "down";
		speed = 1;
		getImage();
		setDialogues();
	}

	public void getImage() {
        up1 = setup("/res/NPC/oldman_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/res/NPC/oldman_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/res/NPC/oldman_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/res/NPC/oldman_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/res/NPC/oldman_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/res/NPC/oldman_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/res/NPC/oldman_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/res/NPC/oldman_right_2", gp.tileSize, gp.tileSize);    
    }

	public void setDialogues() {
		dialogue[0] = "Hello!";
		dialogue[1] = "So you've come to this island \nto find the treasure!";
		dialogue[2] = "I used to be a great wizard  but now...\nI'm a bit too old for talking an adventure !";
		dialogue[3] = "Good luck for you!";
		dialogue[4] = "Good bye!";
	}

	@Override
	public void setAction() {
		actionLockCounter++;
		if(actionLockCounter == 120) {
			Random random = new Random();
			int i = random.nextInt(100) + 1;
			if(i <= 25) {
				direction = "up";
			}
			if(i > 25 && i <= 50) {
				direction = "down";
			}
			if(i > 50 && i <= 75) {
				direction = "left";
			}
			if(i > 75 && i <= 100) {
				direction = "right";
			}
			actionLockCounter = 0;
		}
	}

	@Override
	public void speak() {
		super.speak();
	}
}