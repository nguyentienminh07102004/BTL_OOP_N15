/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

/**
 *
 * @author Admin
 */
public class TileManager {
    
    GamePanel gp ;
    public Tile [] tile ;
   public int mapTileNum[][] ;
    
    
    public TileManager(GamePanel gp){
        this.gp = gp ;
        
        tile = new Tile[50] ;
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow] ;
        
        getTileImage(); 
        loadMap("/res/maps/worldV2.txt");
    }
    
    public void getTileImage (){            
            
            setup(0, "grass",false);
            setup(1, "grass",false);
            setup(2, "grass",false);
            setup(3, "grass",false);
            setup(4, "grass",false);
            setup(5, "grass",false);
            setup(6, "grass",false);
            setup(7, "grass",false);
            setup(8, "grass",false);
            setup(9, "grass",false);
            //PLACEHOLDER
                       
            setup(10,"grass00",false);
            setup(11, "grass01",false);
            setup(12, "water00",true);
            setup(13, "water01",true);
            setup(14, "water02",true);
            setup(15, "water03",true);
            setup(16, "water04",true);
            setup(17, "water05",true);
            setup(18, "water06",true);
            setup(19, "water07",true);
            setup(20, "water08",true);
            setup(21, "water09",true);
            setup(22, "water10",true);
            setup(23, "water11",true);
            setup(24, "water12",true);
            setup(25, "water13",true);
            setup(26, "road00",false);
            setup(27, "road00",false);
            setup(28, "road00",false);
            setup(29, "road00",false);
            setup(30, "road00",false);
            setup(31, "road00",false);
            setup(32, "road00",false);
            setup(33, "road00",false);
            setup(34, "road00",false);
            setup(35, "road00",false);
            setup(36, "road00",false);
            setup(37, "road00",false);
            setup(38, "road00",false);
            setup(39, "earth",false);
            setup(40, "wall",true);
            setup(41, "tree",true);
            
    }
    
    public void setup(int index , String imageName ,boolean collision ) {
        UtilityTool uTool = new UtilityTool() ;
        
        try {
            tile[index] = new Tile() ;
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/" + imageName +     ".png")) ;
            tile[index].image = uTool.scaleImage(tile[index].image ,gp.tileSize ,gp.tileSize) ;
            tile[index].collision = collision ;

            
            
            
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
    
    public void loadMap (String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath) ;
            BufferedReader br = new BufferedReader(new InputStreamReader(is)) ;
            
            int col = 0 ;
            int row = 0 ;
            while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine() ;
                while(col < gp.maxWorldCol) {
                    String numbers[] = line.split(" ") ;
                    
                    int num = Integer.parseInt(numbers[col]) ;
                    
                    mapTileNum [col][row] = num ;
                    col ++ ;
                }
                if(col == gp.maxWorldCol) {
                    col = 0 ;
                    row ++ ;
                }
            }
            br.close(); 
            
        } catch (Exception e) {
        }
    }
    
    public void draw(Graphics2D g2) {
        int worldCol = 0 ;
        int worldRow = 0 ;
         
        
        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            
            int tileNum = mapTileNum[worldCol][worldRow] ;
            
            int worldX = worldCol * gp.tileSize ;
            int worldY = worldRow * gp.tileSize ;
            int screenX = worldX - gp.player.worldX + gp.player.screenX ;
            int screenY = worldY - gp.player.worldY + gp.player.screenY ;
            
             if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
                     worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && 
                     worldY + gp.tileSize> gp.player.worldY - gp.player.screenY && 
                     worldY - gp.tileSize< gp.player.worldY + gp.player.screenY){
                 g2.drawImage(tile[tileNum].image , screenX , screenY ,null) ;
             }
            
            
            worldCol ++ ;
            
            
            if(worldCol == gp.maxWorldCol) {
                worldCol = 0 ;
                
                worldRow ++ ; 
                
            }
        }
    }
}
