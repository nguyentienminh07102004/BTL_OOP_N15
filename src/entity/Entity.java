/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

/**
 *
 * @author Admin
 */
public class Entity {
    public GamePanel gp;
    public int worldX , worldY ;
    public int speed ;
    
    public BufferedImage up1 ,up2 ,down1,down2 ,left1 ,left2 ,right1 ,right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public String direction = "down";
    
    public int spriteCounter = 0 ;
    public int spriteNum = 1 ;
    public Rectangle solidArea = new Rectangle(0, 0, 40, 40);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX , solidAreaDefaultY ;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;
    public boolean invincible = false;
    public int invincibleCounter = 0;
    public String[] dialogue = new String[20];
    public int dialogueIndex = 0;

    public BufferedImage image, image2, image3; 
    public String name;
    public boolean collision = false;
    public int type; // 0: player 1: npc 2: monster
    public boolean hpBarOn = false;
    public int hpBarCounter = 0;

    // Character status
    public int maxLife;
    public int life;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    public int dyingCounter = 0;

    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public Entity currentWeapon;
    public Entity currentShield;

    // ITEM ATTRIBUTES
    public int attackValue;
    public int defenseValue;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }
    public void setAction() {}
    public void damageReaction() {
        actionLockCounter = 0;
        direction = gp.player.direction;
    }
    public void speak() {
        if(dialogue[dialogueIndex] == null) dialogueIndex = 0;
		gp.ui.currentDialog = dialogue[dialogueIndex];
		dialogueIndex++;
		switch (gp.player.direction) {
			case "up":
				direction = "down";
				break;
			case "down":
				direction = "up";
				break;
			case "left":
				direction = "right";
				break;
			case "right":
				direction = "left";
				break;
			default:
				break;
		}
    }
    public void update() {
        setAction();
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if(contactPlayer && type == 2) {
            if(!gp.player.invincible) {
                // we can give damage
                gp.playSE(6);
                int damage = attack - gp.player.defense;
                if(damage < 0) { damage = 0; }
                gp.player.life -= damage;
                gp.player.invincible = true;
            }
        }
        // IF COLLECTIONS IS FAIL, PLAYER CAN MOVE
        if(collisionOn == false ) {
            switch(direction){
                case "up" : 
                    worldY -= speed ;
                    break;
                case "down" : 
                    worldY += speed ;
                    break;
                case "left" : 
                    worldX -= speed ;
                    break;
                case "right" : 
                    worldX += speed ;
                    break;
            }
        }
        spriteCounter++;
        if(spriteCounter > 12 ) {
            if(spriteNum  == 1){
                spriteNum = 2 ;
            }
            else if (spriteNum == 2 ) {
                spriteNum = 1 ;
            }
            spriteCounter = 0 ;
        }
        if(invincible) {
            invincibleCounter++;
            if(invincibleCounter > 40) {
                invincibleCounter = 0;
                invincible = false;
            }
        }
    }
    public void draw(Graphics2D g2) {
        BufferedImage image = null ;
        int screenX = worldX - gp.player.worldX + gp.player.screenX ;
        int screenY = worldY - gp.player.worldY + gp.player.screenY ;
            
        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
            worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && 
            worldY + gp.tileSize> gp.player.worldY - gp.player.screenY && 
            worldY - gp.tileSize< gp.player.worldY + gp.player.screenY){
            g2.drawImage(image , screenX , screenY ,gp.tileSize ,gp.tileSize ,null) ;
        }
        switch(direction) {
            case "up" :
                if(spriteNum == 1) {
                    image = up1 ;
                }
                if(spriteNum == 2) {
                    image = up2 ;
                }
                break;
            case "down" : 
                if(spriteNum == 1) {
                    image = down1 ;
                }
                if(spriteNum == 2) {
                    image = down2 ;
                }
                break;
            case "left":
                if(spriteNum == 1) {
                    image = left1 ;
                }
                if(spriteNum == 2) {
                    image = left2 ;
                }                  
                break;
            case "right" :
                if(spriteNum == 1){
                    
                image = right1 ;
                }
                if(spriteNum == 2) {
                    image = right2 ;
                }
                break;
            }

            // monster hp bar
            if(type == 2 && hpBarOn == true) {
                double onScale = (double)gp.tileSize / maxLife;
                double hpBarValue = onScale * life;
                g2.setColor(new Color(35, 35, 35));
                g2.fillRect(screenX - 1, screenY - 16, gp.tileSize + 2, 12);
                g2.setColor(new Color(255, 0, 30));
                g2.fillRect(screenX, screenY - 15, (int) hpBarValue, 10);
                hpBarCounter++;
                if(hpBarCounter > 600) {
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }
            
            if(invincible) {
                hpBarOn = true;
                hpBarCounter = 0;
               changeAlpha(g2, 0.4f);
            }
            if(dying) {
                dyingAnimation(g2);
            }
            g2.drawImage(image ,screenX , screenY ,gp.tileSize , gp.tileSize , null );
            changeAlpha(g2, 1f);
    }

    public void dyingAnimation(Graphics2D g2) {
        dyingCounter++;
        int i = 5;
        if(dyingCounter <= i) {
            changeAlpha(g2, 0f);
        }
        if(dyingCounter > i && dyingCounter <= i * 2) {
            changeAlpha(g2, 1f);
        }
        if(dyingCounter > i * 2 && dyingCounter <= i * 3) {
            changeAlpha(g2, 0f);
        }
        if(dyingCounter > i * 3 && dyingCounter <= i * 4) {
            changeAlpha(g2, 1f);
        }
        if(dyingCounter > i * 4 && dyingCounter <= i * 5) {
            changeAlpha(g2, 0f);
        }
        if(dyingCounter > i * 5 && dyingCounter <= i * 6) {
            changeAlpha(g2, 1f);
        }
        if(dyingCounter > i * 6 && dyingCounter <= i * 7) {
            changeAlpha(g2, 0f);
        }
        if(dyingCounter > i * 7 && dyingCounter <= i * 8) {
            changeAlpha(g2, 1f);
        }
        if(dyingCounter > i * 8) {
            dying = false;
            alive = false;
        }
    }

    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }
    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool() ;
        BufferedImage image  = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath +".png"));          
            image = uTool.scaleImage(image,width,height);
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return image ;
    }
}