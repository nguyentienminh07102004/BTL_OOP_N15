/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Shield_Normal;
import object.OBJ_Sword_Wood;

/**
 *
 * @author Admin
 */
public class Player extends Entity {
    KeyHandler keyH ;
    
    public final int screenX ;
    public final int screenY ;
   // public int hasKey = 0 ;

   public boolean attackCancel = false;
    
    public Player (GamePanel gp ,KeyHandler keyH){
        super(gp);
        this.keyH = keyH ;
        
        screenX = gp.screenWidth / 2  - (gp.tileSize/2) ;
        screenY = gp.screenHeight / 2 - (gp.tileSize/2)  ;
        
        solidArea = new Rectangle() ;
        solidArea.x = 8 ;
        solidArea.y = 16 ;
        solidAreaDefaultX = solidArea.x ;
        solidAreaDefaultY = solidArea.y ;
        solidArea.width = 32 ;
        solidArea.height = 25 ;
        
        attackArea.width = 36;
        attackArea.height = 36;


        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }
    public void setDefaultValues(){
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4 ;
        direction = "down";

        // PLAYER status
        level = 1;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 0;
        currentWeapon = new OBJ_Sword_Wood(gp);
        currentShield = new OBJ_Shield_Normal(gp);
        attack = getAttack();
        defense = getDefense();
        maxLife = 6;
        life = maxLife;
    }
    
    public int getAttack() {
        return attack = strength * currentWeapon.attackValue;
    }

    public int getDefense() {
        return defense = dexterity * currentShield.defenseValue;
    }

    public void getPlayerImage() {  
        up1 = setup("/res/player/boy_up_1", gp.tileSize, gp.tileSize) ;
        up2 = setup("/res/player/boy_up_2", gp.tileSize, gp.tileSize) ;
        down1 = setup("/res/player/boy_down_1", gp.tileSize, gp.tileSize) ;
        down2 = setup("/res/player/boy_down_2", gp.tileSize, gp.tileSize) ;
        left1 = setup("/res/player/boy_left_1", gp.tileSize, gp.tileSize) ;
        left2 = setup("/res/player/boy_left_2", gp.tileSize, gp.tileSize) ;
        right1 = setup("/res/player/boy_right_1", gp.tileSize, gp.tileSize) ;
        right2 = setup("/res/player/boy_right_2", gp.tileSize, gp.tileSize) ;    
    }
    public void getPlayerAttackImage() {
        attackUp1 = setup("/res/player/boy_attack_up_1", gp.tileSize, gp.tileSize * 2) ;
        attackUp2 = setup("/res/player/boy_attack_up_2", gp.tileSize, gp.tileSize * 2) ;
        attackDown1 = setup("/res/player/boy_attack_down_1", gp.tileSize, gp.tileSize * 2) ;
        attackDown2 = setup("/res/player/boy_attack_down_2", gp.tileSize, gp.tileSize * 2) ;
        attackRight1 = setup("/res/player/boy_attack_right_1", gp.tileSize * 2, gp.tileSize) ;
        attackRight2 = setup("/res/player/boy_attack_right_2", gp.tileSize * 2, gp.tileSize) ;
        attackLeft1 = setup("/res/player/boy_attack_left_1", gp.tileSize * 2, gp.tileSize) ;
        attackLeft2 = setup("/res/player/boy_attack_left_2", gp.tileSize * 2, gp.tileSize) ;
    }
    public void update (){
        if(attacking == true) {
            attacking();
        }
        else if(keyH.upPressed == true || keyH.downPressed == true || 
                keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true) {
            if(keyH.upPressed == true ){
                direction = "up" ;
            }
            else if(keyH.downPressed == true){
                direction = "down" ;
                
            }
            else if(keyH.leftPressed == true) {
                direction = "left" ;
                
            }
            else if(keyH.rightPressed == true) {
                direction = "right" ;
                
            }
            // Check tile collision
            collisionOn = false ;
            gp.cChecker.checkTile(this) ;
            
            //check object collision
            int objIndex = gp.cChecker.checkObject(this,true) ;
            pickUpObject(objIndex);

            // Check NPC COLLECTIONS
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);


            // CHECK MONSTER COLLECTIONS
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            // CHECK EventHandler
            gp.eHandler.checkEvent();
                
            // if collision is false, player can move 
            if(collisionOn == false && keyH.enterPressed == false) {
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

            if(keyH.enterPressed && !attackCancel) {
                gp.playSE(7);
                attacking = true;
                spriteCounter = 0;
            }

            attackCancel = false;
            gp.keyH.enterPressed = false;
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
        }
        // This need to be outside of key if statement
        if(invincible) {
            invincibleCounter++;
            if(invincibleCounter > 60) {
                invincibleCounter = 0;
                invincible = false;
            }
        }
    }

    public void attacking() {
        spriteCounter++;
        if(spriteCounter <= 5) {
            spriteNum = 1;
        }
        if(spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            switch (direction) {
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
            }

            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex);

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if(spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }
    
    private void damageMonster(int i) {
        if(i != 999) {
            if(gp.monster[i].invincible == false) {
                gp.playSE(5);

                int damage = attack - gp.monster[i].defense;
                if(damage < 0) { damage = 0; }
                gp.monster[i].life -= damage;
                gp.ui.addMessage(damage + " damage!");
                gp.monster[i].invincible = true;
                gp.monster[i].damageReaction();
                if(gp.monster[i].life <= 0) {
                    gp.monster[i].dying = true;
                    gp.ui.addMessage("killed the " + gp.monster[i].name + "!");
                    gp.ui.addMessage("EXP +" + gp.monster[i].exp);
                    exp += gp.monster[i].exp;
                    checkLevelUp();
                }
            }
        }
    }
    public void checkLevelUp() {
        if(exp >= nextLevelExp) {
            level++;
            nextLevelExp *= 2;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();
            gp.playSE(8);
            gp.gameState = gp.dialogState;
            gp.ui.currentDialog = "You are level " + level + " now!\nYou feel stronger!";
        }
    }
    public void pickUpObject(int i){
        if(i != 999){
            
        }
    }
    public void contactMonster(int i) {
        if(i != 999) {
            if(!invincible) {
                gp.playSE(6);
                int damage = gp.monster[i].attack - defense;
                if(damage < 0) { damage = 0; }
                life -= damage;
                invincible = true;
            }
        }
    }
    public void interactNPC(int npcIndex) {
        if(gp.keyH.enterPressed) {
            if(npcIndex != 999) {
                attackCancel = true;
                gp.gameState = gp.dialogState;
                gp.npc[npcIndex].speak();
            }
        }
    } 
    public void draw (Graphics2D g2) {
//        g2.setColor(Color.white);
//        g2.fillRect(x,y, gp.tileSize, gp.tileSize);
           BufferedImage image = null ;
           int tempScreenX = screenX;
           int tempScreenY = screenY;
           switch(direction) {
               case "up" :
                    if(!attacking) {
                        if(spriteNum == 1) {
                            image = up1;
                        }
                        if(spriteNum == 2) {
                            image = up2;
                        }
                    }
                    if(attacking) {
                        tempScreenY = screenY - gp.tileSize;
                        if(spriteNum == 1) {
                            image = attackUp1;
                        }
                        if(spriteNum == 2) {
                            image = attackUp2;
                        }
                    }
                   break ;
               case "down":
                    if(!attacking) {
                        if(spriteNum == 1) {
                            image = down1 ;
                        }
                        if(spriteNum == 2) {
                            image = down2 ;
                        }
                    }
                    if(attacking) {
                        if(spriteNum == 1) {
                            image = attackDown1;
                        }
                        if(spriteNum == 2) {
                            image = attackDown2;
                        }
                    }
                   break ;
               case "left":
                    if(!attacking) {
                        if(spriteNum == 1) {
                            image = left1 ;
                        }
                        if(spriteNum == 2) {
                            image = left2 ;
                        }
                    }
                    if(attacking) {
                        tempScreenX = screenX - gp.tileSize;
                        if(spriteNum == 1) {
                            image = attackLeft1;
                        }
                        if(spriteNum == 2) {
                            image = attackLeft2;
                        }
                    }
                    break ;
               case "right" :
                    if(!attacking) {
                        if(spriteNum == 1) {
                            image = right1;
                        }
                        if(spriteNum == 2) {
                            image = right2;
                        }
                    }
                    if(attacking) {
                        if(spriteNum == 1) {
                            image = attackRight1;
                        }
                        if(spriteNum == 2) {
                            image = attackRight2;
                        }
                    }
                    break ;
           }
           if(invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
           }
           g2.drawImage(image ,tempScreenX , tempScreenY, null ) ;
           g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

           // debug
        //    g2.setFont(new Font("Arial", Font.PLAIN, 26));
        //    g2.setColor(Color.white);
        //    g2.drawString("Invincible: " + invincibleCounter, 10, 400);
    }
}
