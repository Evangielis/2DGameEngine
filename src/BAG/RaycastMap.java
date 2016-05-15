package BAG;

import java.awt.Color;

import javax.vecmath.Vector2d;

public class RaycastMap extends GridMap {

	Camera camera;
	int[] canvas;
	
	
	public RaycastMap(BAGame game, int[][] grid, Texture[] textures,
			Camera cam, int tileSize) {
		super(game, grid, textures, tileSize, 0, 0);
				
		this.camera = cam;
	}
	
	@Override
	public int[] renderMap(int[] canvas)
	{
		camera.update(grid);
		
    	for(int n=0; n<canvas.length/2; n++) {
            if(canvas[n] != Color.DARK_GRAY.getRGB()) canvas[n] = Color.DARK_GRAY.getRGB();
        }
    	for(int i=canvas.length/2; i<canvas.length; i++) {
            if(canvas[i] != Color.GRAY.getRGB()) canvas[i] = Color.GRAY.getRGB();
        }    	
    	
        for(int x=0; x<game.getFrameWidth(); x=x+1) 
        {
            double cameraX = (2 * (x / (double)(game.getWidth())))-1;
            double rayDirX = camera.getXFacing() + camera.getXPlane() * cameraX;
            double rayDirY = camera.getYFacing() + camera.getYPlane() * cameraX;
            
            //Map position
            int mapX = (int)camera.getXPos();
            int mapY = (int)camera.getYPos();
            
            //length of ray from current position to next x or y-side
            double sideDistX;
            double sideDistY;
            
            //Length of ray from one side to next in map
            double deltaDistX = Math.sqrt(1 + (rayDirY*rayDirY) / (rayDirX*rayDirX));
            double deltaDistY = Math.sqrt(1 + (rayDirX*rayDirX) / (rayDirY*rayDirY));
            
            double perpWallDist;
            //Direction to go in x and y
            int stepX, stepY;
            boolean hit = false;//was a wall hit
            int side=0;//was the wall vertical or horizontal
            
            //Figure out the step direction and initial distance to a side
            if (rayDirX < 0)
            {
                stepX = -1;
                sideDistX = (camera.getXPos() - mapX) * deltaDistX;
            }
            else
            {
                stepX = 1;
                sideDistX = (mapX + 1.0 - camera.getXPos()) * deltaDistX;
            }
            if (rayDirY < 0)
            {
                stepY = -1;
                sideDistY = (camera.getYPos() - mapY) * deltaDistY;
            }
            else
            {
                stepY = 1;
                sideDistY = (mapY + 1.0 - camera.getYPos()) * deltaDistY;
            }         

            //Loop to find where the ray hits a wall
            while(!hit) {
                //Jump to next square
                if (sideDistX < sideDistY)
                {
                    sideDistX += deltaDistX;
                    mapX += stepX;
                    side = 0;
                }
                else
                {
                    sideDistY += deltaDistY;
                    mapY += stepY;
                    side = 1;
                }
                //Check if ray has hit a wall
                if(grid[mapX][mapY] > 0) hit = true;
            }
            
            //Calculate distance to the point of impact
            if(side==0)
                perpWallDist = Math.abs((mapX - camera.getXPos() + (1 - stepX) / 2) / rayDirX);
            else
                perpWallDist = Math.abs((mapY - camera.getYPos() + (1 - stepY) / 2) / rayDirY);    
            //Now calculate the height of the wall based on the distance from the camera
            int lineHeight;
            if(perpWallDist > 0) lineHeight = Math.abs((int)(game.getFrameHeight() / perpWallDist));
            else lineHeight = game.getFrameHeight();
            //calculate lowest and highest pixel to fill in current stripe
            int drawStart = -lineHeight/2 + game.getFrameHeight()/2;
            if(drawStart < 0)
                drawStart = 0;
            int drawEnd = lineHeight/2 + game.getFrameHeight()/2;
            if(drawEnd >= game.getFrameHeight()) 
                drawEnd = game.getFrameHeight() - 1;

            //add a texture
            int texNum = grid[mapX][mapY] - 1;
            double wallX;//Exact position of where wall was hit
            if(side==1) {//If its a y-axis wall
                wallX = (camera.getXPos() + ((mapY - camera.getYPos() + (1 - stepY) / 2) / rayDirY) * rayDirX);
            } else {//X-axis wall
                wallX = (camera.getYPos() + ((mapX - camera.getXPos() + (1 - stepX) / 2) / rayDirX) * rayDirY);
            }
            wallX-=Math.floor(wallX);
            //x coordinate on the texture
            int texX = (int)(wallX * (textures[texNum].getWidth()));
            if(side == 0 && rayDirX > 0) texX = textures[texNum].getWidth() - texX - 1;
            if(side == 1 && rayDirY < 0) texX = textures[texNum].getHeight() - texX - 1;
            
            //calculate y coordinate on texture
            for(int y=drawStart; y<drawEnd; y++) {
                int texY = (((y*2 - game.getFrameHeight() + lineHeight) << 6) / lineHeight) / 2;
                int color;
                if(side==0) 
                	color = textures[texNum].getPixel(texY,texX);
                else 
                	color = (textures[texNum].getPixel(texY,texX)>>1) & 8355711;//Make y sides darker
                canvas[x + y*(game.getFrameWidth())] = color;
            }
        }
        return canvas;
	}
}
