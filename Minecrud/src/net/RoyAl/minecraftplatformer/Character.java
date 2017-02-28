package net.RoyAl.minecraftplatformer;

import java.awt.*;

public class Character extends DoubleRectangle {
	public double fallingSpeed = 0;
	public double maxFall = 3;
	public double movementSpeed = 1;
	public double jumpingStart = 2;
	public double jumpingSpeed = jumpingStart;
	public double dy = 0.03;
	
	public int animation = 0;
	public int animationFrame = 0, animationTime = 25;
	
	public boolean isJumping = false;
	
	public Character(int width, int height) {
		setBounds((Component.pixel.width / 2)-(width / 2) + Component.sX, (Component.pixel.height / 2)-(height / 2) + Component.sY, width, height);
	}
	
	public void tick() {
		if(y < Level.worldH * Tile.tileSize - Component.pixel.height - Tile.tileSize && y > Component.pixel.height / 2) {
			this.y = (Component.pixel.height / 2)-(height / 2) + Component.sY;
		}
		
		if(!isJumping && !isCollidingWithBlock(new Point((int) x + 2, (int) (y + height)), new Point((int) (x + width -2), (int) (y + height)))) {
			y += fallingSpeed;
			if (Component.sY < Level.worldH * Tile.tileSize - Component.pixel.height - Tile.tileSize && y > Component.pixel.height / 2) {
				Component.sY += fallingSpeed;
			}
			fallingSpeed += dy;
			if(fallingSpeed >= maxFall) {
				fallingSpeed = maxFall;
			}
			
		} else {
			fallingSpeed = 0;
			if(Component.isJumpingPressed) {
				isJumping = true;
			}
		}
		
		if(Component.isMoving) {
			boolean canMove = false;
			
			if(Component.dir == movementSpeed) {
				canMove = !isCollidingWithBlock(new Point((int) (x + width), (int) y), new Point((int) (x+width), (int) (y + height - 2)));
			} else if (Component.dir == -movementSpeed) {
				canMove = !isCollidingWithBlock(new Point((int) x - 1, (int) y), new Point((int) x, (int) (y + height - 2)));
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
			
			if(canMove) {
				x += Component.dir;
				if (x > (Component.pixel.width + Tile.tileSize) / 2 && x < Level.worldW * Tile.tileSize - (Component.pixel.width + Tile.tileSize) / 2 - Tile.tileSize) {
					Component.sX += Component.dir;
				}
			}
		} else {
			animation = 0;
		}
		
		if(isJumping) {
			if(Component.isJumpingPressed && !isCollidingWithBlock(new Point((int) (x + 2), (int) y), new Point((int) (x + width -2), (int) y))) {
				if(jumpingSpeed <= 0) {
					isJumping = false;
					jumpingSpeed = jumpingStart;
				} else {
					y -= jumpingSpeed;
					if (Component.sY > Tile.tileSize && y < Level.worldH * Tile.tileSize - Component.pixel.height / 2 - 2 * Tile.tileSize) {
						Component.sY -= jumpingSpeed;
					}
					jumpingSpeed -= dy;
				}
			} else {
				isJumping = false;
				jumpingSpeed = jumpingStart;
			}
		}
	}
	
	public boolean isCollidingWithBlock(Point pt1, Point pt2) {
		for(int x=(int) (this.x/Tile.tileSize);x<(int) (this.x/Tile.tileSize) + 3;x++) {
			for(int y=(int) (this.y/Tile.tileSize);y<(int) (this.y/Tile.tileSize) + 3;y++) {
				if(x >= 0 && y >= 0 && x < Component.level.block.length && y < Component.level.block[0].length) {
					if(Component.level.block[x][y].id != Tile.air) {
						if(Component.level.block[x][y].contains(pt1) || Component.level.block[x][y].contains(pt2)) {
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}
	
	public void render(Graphics g) {
		if(Component.dir == movementSpeed) {
			g.drawImage(Tile.tileset_terrain, (int) x - (int) Component.sX, (int) y - (int) Component.sY, (int) (x + width) - (int) Component.sX, (int) (y + height) - (int) Component.sY, /**/ Tile.character[0] * Tile.tileSize + (Tile.tileSize * animation), Tile.character[1] * Tile.tileSize, Tile.character[0] * Tile.tileSize + (Tile.tileSize * animation) + (int) width, Tile.character[1] * Tile.tileSize + (int)height, null);
		} else {
			g.drawImage(Tile.tileset_terrain, (int) x - (int) Component.sX, (int) y - (int) Component.sY, (int) (x + width) - (int) Component.sX, (int) (y + height) - (int) Component.sY, /**/ Tile.character[0] * Tile.tileSize + (Tile.tileSize * animation) + (int) width, Tile.character[1] * Tile.tileSize, Tile.character[0] * Tile.tileSize + (Tile.tileSize * animation), Tile.character[1] * Tile.tileSize + (int)height, null);
		}
	}
}
