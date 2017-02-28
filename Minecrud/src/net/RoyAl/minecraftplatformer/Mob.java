package net.RoyAl.minecraftplatformer;

import java.awt.*;
import java.util.*;

import net.RoyAl.minecraftplatformer.Component;

public class Mob extends DoubleRectangle {
	public int[] id;
	
	public boolean isJumping = false;
	public boolean isMoving = false;
	public boolean isFalling = false;
	
	public double movementSpeed = 0.8;
	public double fallingSpeed = 1;
	public double jumpingSpeed = 1;
	public double dir = movementSpeed;
	public int jumpingHeight = 50, jumpingCount = 0;
	public int animation = 0;
	public int animationFrame = 0, animationTime = 25;
	
	public Mob(int x, int y, int width, int height, int[] id) {
		setBounds(x, y, width, height);
		
		this.id = id;
	}
	
	public void tick() {
		if(!isJumping && !isCollidingWithBlock(new Point((int) x + 2, (int) (y + height)), new Point((int) (x + width -2), (int) (y + height)))) {
			y += fallingSpeed;
			isFalling = true;
			
		} else {
			isFalling = false;
			
			if(new Random().nextInt(100) < 1) {
				isMoving = true;
				
				if(new Random().nextInt(100) > 50) {
					dir = -movementSpeed;
				} else {
					dir = movementSpeed;
				}
			}
		}
		
		if(isMoving) {
			boolean canMove = false;
			
			if(dir == movementSpeed) {
				canMove = isCollidingWithBlock(new Point((int) (x + width), (int) y), new Point((int) (x+width), (int) (y + height - 2)));
			} else if (dir == -movementSpeed) {
				canMove = isCollidingWithBlock(new Point((int) x - 1, (int) y), new Point((int) x, (int) (y + height - 2)));
			}
			
			if(!canMove && !isFalling) {
				isJumping = true;
			}
			
			if(animationFrame >= animationTime) {
				if(animation > 1) {
					animation = 1;
				} else {
					animation += 1;
				}
				
				animationFrame = 0;
			} else {
				animationFrame += 1;
			}
			
			if(!canMove) {
				x += dir;
			}
		} else {
			animation = 0;
		}
		
		if(isJumping) {
			if(!isCollidingWithBlock(new Point((int) (x + 2), (int) y), new Point((int) (x+ width -2), (int) y))) {
				if(jumpingCount >= jumpingHeight) {
					isJumping = false;
					jumpingCount = 0;
				} else {
					y -= jumpingSpeed;
					
					jumpingCount += 1;
				}
			} else {
				isJumping = false;
				jumpingCount = 0;
			}
		}
	}
	
	public boolean isCollidingWithBlock(Point pt1, Point pt2) {
		for(int x=(int) (this.x/Tile.tileSize);x<(int) (this.x/Tile.tileSize) + 3;x++) {
			for(int y=(int) (this.y/Tile.tileSize);y<(int) (this.y/Tile.tileSize) + 3;y++) {
				if(x >= 0 && y >= 0 && x < Component.level.block.length && y < Component.level.block[0].length)
				if(Component.level.block[x][y].id != Tile.air) {
					if(Component.level.block[x][y].contains(pt1) || Component.level.block[x][y].contains(pt2)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public void render(Graphics g) {
		if(dir == movementSpeed) {
			g.drawImage(Tile.tileset_terrain, (int) x - (int) Component.sX, (int) y - (int) Component.sY, (int) (x + width) - (int) Component.sX, (int) (y + height) - (int) Component.sY, /**/ id[0] * Tile.tileSize + (Tile.tileSize * animation), id[1] * Tile.tileSize, id[0] * Tile.tileSize + (Tile.tileSize * animation) + (int) width, id[1] * Tile.tileSize + (int)height, null);
		} else {
			g.drawImage(Tile.tileset_terrain, (int) x - (int) Component.sX, (int) y - (int) Component.sY, (int) (x + width) - (int) Component.sX, (int) (y + height) - (int) Component.sY, /**/ id[0] * Tile.tileSize + (Tile.tileSize * animation) + (int) width, id[1] * Tile.tileSize, id[0] * Tile.tileSize + (Tile.tileSize * animation), id[1] * Tile.tileSize + (int)height, null);
		}
	}
}
