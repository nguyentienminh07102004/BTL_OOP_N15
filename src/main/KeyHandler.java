 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Admin
 */
public class KeyHandler implements KeyListener{
    GamePanel gp ;
    
    public boolean upPressed , downPressed , leftPressed , rightPressed, enterPressed;
    //Debug
    boolean checkDrawTime = false ;
    
    public KeyHandler(GamePanel gp){
        this.gp = gp ;
        
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }
            
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode() ;
        // TITLE STATE
        if(gp.gameState == gp.titleState) {
            titleState(code);
        }
        // PLAY STATE
        else if(gp.gameState == gp.playState) {
            playState(code);
        }
        // PAUSE STATE
        else if(gp.gameState == gp.pauseState) {
            pauseState(code);
        }
        // DIALOG STATE
        else if(gp.gameState == gp.dialogState) {
            dialogState(code);
        }
        // CHARACTER STATE
        else if(gp.gameState == gp.characterState) {
            characterState(code);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode() ;
        if(code == KeyEvent.VK_W) {
            upPressed = false ;
        }
       if(code == KeyEvent.VK_S) {
            downPressed = false ;
        }
       if(code == KeyEvent.VK_A) {
            leftPressed = false ;
        }
       if(code == KeyEvent.VK_D) {
            rightPressed = false ;
        }
    }
    
    public void titleState(int code) {
        if(gp.ui.titleScreenState == 0) {
            if(code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
            }
            if(code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER) {
                if(gp.ui.commandNum == 0) {
                    gp.ui.titleScreenState = 1;
                    //gp.playMusic(0);
                }
                if(gp.ui.commandNum == 1) {
                    
                }
                if(gp.ui.commandNum == 2) {
                    System.exit(0);
                }
            }
        }
        else if(gp.ui.titleScreenState == 1) {
            if(code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 3;
                }
            }
            if(code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 3) {
                    gp.ui.commandNum = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER) {
                if(gp.ui.commandNum == 0) {
                    System.out.println("Do some fighter specific stuff!");
                    gp.gameState = gp.playState;
                    gp.playMusic(0);
                }
                if(gp.ui.commandNum == 1) {
                    System.out.println("Do some theif specific stuff!");
                    gp.gameState = gp.playState;
                    gp.playMusic(0);
                }
                if(gp.ui.commandNum == 2) {
                    System.out.println("Do some sorcerer specific stuff!");
                    gp.gameState = gp.playState;
                    gp.playMusic(0);
                }
                if(gp.ui.commandNum == 3) {
                    gp.ui.titleScreenState = 0;
                }
            }
        }
    }
    public void playState(int code) {
        if(code == KeyEvent.VK_W) {
            upPressed = true ;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = true ;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed = true ;
            }
        if(code == KeyEvent.VK_D) {
            rightPressed = true ;
        }
        if(code == KeyEvent.VK_P) {
            gp.gameState = gp.pauseState;
        }
        if(code == KeyEvent.VK_C) {
            gp.gameState = gp.characterState;
        }
        if(code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        // DEBUG
        if(code == KeyEvent.VK_T) {
            if(checkDrawTime == false) {
                checkDrawTime = true ;
            } 
            else if(checkDrawTime == true) {
                checkDrawTime = false ;
            }
        }
    }
    public void pauseState(int code) {
        if(code == KeyEvent.VK_P) {
            gp.gameState = gp.playState;
        }
    }
    public void dialogState(int code) {
        if(code == KeyEvent.VK_ENTER) {
            gp.gameState = gp.playState;
        }
    }
    public void characterState(int code) {
        if(code == KeyEvent.VK_C) {
            gp.gameState = gp.playState;
        }
    }
}
