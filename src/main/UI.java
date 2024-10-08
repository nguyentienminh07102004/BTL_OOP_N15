/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import entity.Entity;
import object.OBJ_Heart;

/**
 *
 * @author Admin
 */
public class UI {
    GamePanel gp ;
    Graphics2D g2 ;
    Font maruMonica, purisaB;
    //BufferedImage keyImage ;
    BufferedImage heart_full, heart_half, heart_blank;
    public boolean messageOn = false ;
    // public String message ="" ;
    // int messageCounter = 0 ; // bien giup lam bien mat chu sau 1 tgian 
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public boolean gameFinished = false ;
    public String currentDialog = "";
    public int commandNum = 0;
    public int titleScreenState = 0; // 0: first screen, 1: the second screen 
    
    double playTime ; 
    DecimalFormat dFormat = new DecimalFormat ("#0.00") ;
    
    
    public UI (GamePanel gp) {
        this.gp = gp ;
        try {
            InputStream is = getClass().getResourceAsStream("/res/font/JetBrainsMono-Italic.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/res/font/JetBrainsMono-Medium.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        //OBJ_Key key = new OBJ_Key(gp) ;
        //keyImage = key.image ;

        // CREATE HUD object
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
    }
    
    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }
    
    
    public void draw (Graphics2D g2) {
        this.g2 = g2 ;
        g2.setFont(maruMonica);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.white);
        // TITLE STATE
        if(gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        //PLAY STATE
        if(gp.gameState == gp.playState) {
            drawPlayerLife();
            drawMessage();
        }
        // PAUSE STATE
        if(gp.gameState == gp.pauseState) {
            drawPlayerLife();
            drawPauseScreen() ;
        }
        // DIALOG STATE
        if(gp.gameState == gp.dialogState) {
            drawPlayerLife();
            drawDialogScreen();
        }
        // CHARACTER STATE
        if(gp.gameState == gp.characterState) {
            drawCharacterScreen();
        }
    }
    public void drawMessage() {
        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 4;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));

        for(int i = 0; i < message.size(); i++) {
            if(message.get(i) != null) {
                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX + 2, messageY + 2);
                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 50;

                if(messageCounter.get(i) > 180) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }
    public void drawPlayerLife() {
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;
        // DRAW BLANK HEART
        while(i < gp.player.maxLife / 2) {
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize;
        }
        x = gp.tileSize / 2;
        y = gp.tileSize / 2;
        i = 0;
        // draw current life
        while (i < gp.player.life) {
            g2.drawImage(heart_half, x, y, null);
            i++;
            if(i < gp.player.life) {
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }
    }
    public void drawTitleScreen() {
        if(titleScreenState == 0) {
            g2.setColor(new Color(70, 120, 80));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 60F));
            String text = "Bài tập lớn nhóm 15";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3;
            // SHADOW
            g2.setColor(Color.BLACK);
            g2.drawString(text, x + 5, y + 5);;
            // MAIN COLOR
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            // BLUE BOY image
            x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
            y = gp.tileSize * 5;
            g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
            // MENU
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
            text = "NEW GAME";
            x = getXforCenteredText(text);
            y = gp.tileSize * 9;
            if(commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }
            g2.drawString(text, x, y);

            text = "LOAD GAME";
            x = getXforCenteredText(text);
            y = gp.tileSize * 10;
            if(commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }
            g2.drawString(text, x, y);

            text = "QUIT";
            x = getXforCenteredText(text);
            y = gp.tileSize * 11;
            if(commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }
            g2.drawString(text, x, y);
        } else if(titleScreenState == 1) {
            // CLASS SELECTION SCREEN
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42F));
            String text = "SELECT YOUR CLASS!";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3;
            g2.drawString(text, x, y);

            text = "FIGHTER";
            x = getXforCenteredText(text);
            y += gp.tileSize * 4;
            g2.drawString(text, x, y);
            if(commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "THIEF";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "SORCERER";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "BACK";
            x = getXforCenteredText(text);
            y += gp.tileSize * 2;
            g2.drawString(text, x, y);
            if(commandNum == 3) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        }
    }
    public void drawDialogScreen() {
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - gp.tileSize * 4;
        int height = gp.tileSize * 4;
        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        x += gp.tileSize;
        y += gp.tileSize;
        for(String line : currentDialog.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }
    public void drawCharacterScreen() {
        // CREATE A FRAME
        final int frameX = gp.tileSize * 2;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize * 5;
        final int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        // CREATE A TEXT
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(20F));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 35;

        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack", textX, textY);
        textY += lineHeight;
        g2.drawString("Defense", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2.drawString("Next Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Coin", textX, textY);
        textY += lineHeight + 25;
        g2.drawString("Weapon", textX, textY);
        textY += lineHeight + 25;
        g2.drawString("Shield", textX, textY);

        // VALUES
        int tailX = frameX + frameWidth - 30;
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.coin);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY - 10, null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY, null);
    }
    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);
        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }
    public void drawPauseScreen () {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN , 80F));
        String text = "PAUSED" ;
        int x = getXforCenteredText(text) ;
        int y = gp.screenHeight / 2  ;
        g2.drawString(text, x, y);
    }
    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth() ;
        int x = gp.screenWidth / 2 - length / 2 ;
        return x ;
    }
    public int getXforAlignToRightText(String text, int tailX) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth() ;
        int x = tailX - length;
        return x ;
    }
}
