/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import javax.swing.JFrame;

/**
 *
 * @author Admin
 */
public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setResizable(false);
        window.setTitle("2D Adventure");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GamePanel gamePanel  = new GamePanel() ;
        window.add(gamePanel) ;
        
        window.pack();
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        gamePanel.setupGame(); 
        gamePanel.startGameThread(); 
    }
}
