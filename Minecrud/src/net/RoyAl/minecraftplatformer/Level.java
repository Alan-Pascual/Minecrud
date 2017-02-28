package net.RoyAl.minecraftplatformer;

import java.awt.*;
import java.util.*;

import net.RoyAl.minecraftplatformer.Component;

public class Level {
	public static int worldW = 100, worldH = 50;
	public static int timeC = 0;
	public Block[][] block = new Block[worldW][worldH];

	public Level() {
		for(int x=0;x<block.length;x++) {
			for(int y=0;y<block[0].length;y++) {
				block[x][y] = new Block(new Rectangle(x*Tile.tileSize, y*Tile.tileSize, Tile.tileSize, Tile.tileSize), Tile.air);
			}
		}

		generateLevel();
	}

	public boolean isTreeY(int xmin, int xmax) {
		for(int x = xmin; x <= xmax; x++) {
			for(int y = 0; y < block[0].length; y++) {
				if(block[x][y].id == Tile.wood) {
					return true;
				} 
			}
		}
		return false;
	}

	public int treeLength(int x) {
		int count = 0;
		for(int y = 0; y < block[0].length; y++) {
			if(block[x][y].id == Tile.wood) {
				count++;
			}
		}
		return count;
	}

	public void generateLevel() {
		// generating mountains, earth etc.
		for(int y=0;y<block[0].length;y++) {
			for(int x=0;x<block.length;x++) {
				if(y > worldH / 4) {
					if(new Random().nextInt(100) > 30) {
						try {
							if(block[x-1][y-1].id == Tile.earth) {
								block[x][y].id = Tile.earth;
							}
						} catch(Exception e) { }
					}

					if(new Random().nextInt(100) > 30) {
						try {
							if(block[x+1][y-1].id == Tile.earth) {
								block[x][y].id = Tile.earth;
							}
						} catch(Exception e) { }
					}

					try {
						if(block[x][y-1].id == Tile.earth) {
							block[x][y].id = Tile.earth;
						}
					} catch(Exception e) { }

					if(new Random().nextInt(100) < 5) {
						block[x][y].id = Tile.earth;
					}
				}

				if(y > (worldH * 2) / 3) {
					block[x][y].id = Tile.stone;
				}

				if(y >= worldH - 5 && x != 0 && x != block.length - 1) {
					block[x][y].id = Tile.bedrock;
				}
			}
		}

		//Placing out trees.
		for(int y=0;y<block[0].length;y++) {
			for(int x=0;x<block.length;x++) {
				try {
					if(block[x][y+1].id == Tile.earth && block[x][y].id == Tile.air && x > 5 && x < block.length - 6 && !isTreeY(x - 10, x + 10)) {
						if(new Random().nextInt(100) <= 50) {
							for (int i = 0; i < new Random().nextInt(5) + 4; i++) {
								block[x][y - i].id = Tile.wood;
							}
							block[x][y].id = Tile.wood;
						}
					}
				} catch(Exception e) { }
			}
		}

		//Placing out leaves.
		for(int y=0;y<block[0].length;y++) {
			for(int x=0;x<block.length;x++) {
				try {
					if(block[x][y+1].id == Tile.wood && block[x][y].id == Tile.air) {
						block[x][y-1].id = Tile.leaves;
						for(int i = 0; i <= (treeLength(x) / 2); i++) {
							switch(i) {
							case 0:
								block[x][y].id = Tile.leaves;
								block[x-1][y].id = Tile.leaves;
								block[x+1][y].id = Tile.leaves;
								break;
							default:
								block[x][y+i].id = Tile.leaves;
								block[x-1][y+i].id = Tile.leaves;
								block[x-2][y+i].id = Tile.leaves;
								block[x+1][y+i].id = Tile.leaves;
								block[x+2][y+i].id = Tile.leaves;
								break;
							}
						}
					}
				} catch(Exception e) { }
			}
		}

		// Placing out grass blocks
		for(int y=0;y<block[0].length;y++) {
			for(int x=0;x<block.length;x++) {
				if(block[x][y].id == Tile.earth && block[x][y-1].id == Tile.air) {
					block[x][y].id = Tile.grass;
				}
			}
		}

		//Placing out solid air around level.
		for(int y=0;y<block[0].length;y++) {
			for(int x=0;x<block.length;x++) {
				if(x == 0 || y == 0 || x == block.length - 1 || y == block[0].length - 1) {
					block[x][y].id = Tile.solidair;
				}
			}
		}
	}

	public boolean findNonFullIDBar(int[] id) {
		for(int i = 0; i < Inventory.invBar.length; i++) {
			if(Inventory.invBar[i].id == id && Inventory.invBar[i].amount < Inventory.invMax) {
				Inventory.invBar[i].amount ++;
				return true;
			}
		}
		for(int i = 0; i < Inventory.invBar.length; i++) {
			if(Inventory.invBar[i].id == Tile.air) {
				Inventory.invBar[i].id = id;
				Inventory.invBar[i].amount = 1;
				return true;
			}
		}
		return false;
	}

	public boolean findNonFullIDBag(int[] id) {
		for(int i = 0; i < Inventory.invBag.length; i++) {
			if(Inventory.invBag[i].id == id && Inventory.invBag[i].amount < Inventory.invMax) {
				Inventory.invBag[i].amount ++;
				return true;
			}
		}
		for(int i = 0; i < Inventory.invBag.length; i++) {
			if(Inventory.invBag[i].id == Tile.air) {
				Inventory.invBag[i].id = id;
				Inventory.invBag[i].amount = 1;
				return true;
			}
		}
		return false;
	}

	public void addToInventory(int[] id) {
		boolean bar = findNonFullIDBar(id);

		if(bar == false) {
			findNonFullIDBag(id); // Can use this later to drop items on ground.
		}
	}

	public int isIDinBarNotHolding(int[] id) {
		for(int i = 0; i < Inventory.invBar.length; i++) {
			if(Inventory.invBar[i].id == id && i != Inventory.selected) {
				return i;
			}
		}
		return -1;
	}

	public int isIDinBag(int[] id) {
		for(int i = 0; i < Inventory.invBag.length; i++) {
			if(Inventory.invBag[i].id == id) {
				return i;
			}
		}
		return -1;
	}

	public void building(int camX, int camY, int renW, int renH) {
		if(Component.isMouseLeft || Component.isMouseRight) {
			for(int x=(camX / Tile.tileSize);x<(camX/Tile.tileSize) + renW;x++) {
				for(int y=(camY / Tile.tileSize);y<(camY/Tile.tileSize) + renH;y++) {
					if(x >= 0 && y >= 0 && x < worldW && y < worldH){
						if(block[x][y].contains(new Point((Component.mse.x / Component.pixelSize) + (int) Component.sX, (Component.mse.y / Component.pixelSize) + (int) Component.sY))) {
							int sid[] = Inventory.invBar[Inventory.selected].id;

							if(Component.isMouseLeft) {
								if(block[x][y].id != Tile.solidair && block[x][y].id != Tile.bedrock) {
									addToInventory(block[x][y].id);

									block[x][y].id = Tile.air;
								}
							} else if(Component.isMouseRight) {
								if(block[x][y].id == Tile.air) {
									if(sid != Tile.air) {
										block[x][y].id = sid;
										Inventory.invBar[Inventory.selected].amount -= 1;

										if(Inventory.invBar[Inventory.selected].amount == 0) {
											int bar = isIDinBarNotHolding(sid);
											int bag = isIDinBag(sid);

											if(bar != -1) {
												Inventory.invBar[Inventory.selected].amount = Inventory.invBar[bar].amount;
												Inventory.invBar[bar].id = Tile.air;
												Inventory.invBar[bar].amount = -1;
											} else if(bag != -1) {
												Inventory.invBar[Inventory.selected].amount = Inventory.invBag[bag].amount;
												Inventory.invBag[bag].id = Tile.air;
												Inventory.invBag[bag].amount = -1;
											} else {
												Inventory.invBar[Inventory.selected].id = Tile.air;
												Inventory.invBar[Inventory.selected].amount = -1;
											}
										}
									}
								}
							}

							break;
						}
					}
				}
			}
		}
	}

	public void sandFall(int camX, int camY, int renW, int renH) {
		for(int y = (camY / Tile.tileSize) + renH; y > (camY / Tile.tileSize) - renH; y--) {
			for(int x = (camX / Tile.tileSize) - renW; x < (camX / Tile.tileSize) + renW; x++) {
				if (x > 0 && y > 0 && x < block.length-1 && y < block[0].length-1) {
					if (block[x][y].id == Tile.sand && block[x][y + 1].id == Tile.air) {
						block[x][y].id = Tile.air;
						block[x][y + 1].id = Tile.sand;
					} 
				}
			}
		}
	}

	public boolean grassTileNear(int x, int y) {
		for(int i = y - 1; i <= y + 1; i++) {
			if(block[x-1][i].id == Tile.grass || block[x+1][i].id == Tile.grass) {
				return true;
			}
		}
		return false;
	}

	public void grassGen(int camX, int camY, int renW, int renH) {
		for(int x=(camX / Tile.tileSize) - renW;x<(camX/Tile.tileSize) + renW;x++) {
			for(int y=(camY / Tile.tileSize) - renH;y<(camY/Tile.tileSize) + renH;y++) {
				if (x > 0 && y > 0 && x < block.length-1 && y < block[0].length-1) {
					if(block[x][y].id == Tile.earth && block[x][y-1].id == Tile.air && grassTileNear(x, y)) {
						if(new Random().nextInt(100) < 1) {
							block[x][y].id = Tile.grass;
						}
					} else if(block[x][y].id == Tile.grass && block[x][y-1].id != Tile.air) {
						if(new Random().nextInt(100) < 30) {
							block[x][y].id = Tile.earth;
						}
					}
				}
			}
		}
	}

	public boolean isHovering() {
		for(int i = 0; i < Inventory.invBar.length; i++) {
			if(Inventory.invBar[i].contains(new Point(Component.mse.x / Component.pixelSize, Component.mse.y / Component.pixelSize))) {
				return true;
			}
		}
		for(int i = 0; i < Inventory.invBag.length; i++) {
			if(Inventory.invBag[i].contains(new Point(Component.mse.x / Component.pixelSize, Component.mse.y / Component.pixelSize))) {
				return true;
			}
		}
		return false;
	}

	public void tick(int camX, int camY, int renW, int renH) {
		if(Inventory.isOpen == false) {
			building(camX, camY, renW, renH);
		} else if(isHovering() == false) {
			building(camX, camY, renW, renH);
		}

		if(timeC % 15 == 0) {
			sandFall(camX,camY,renW,renH);
		}

		if(timeC % 200 == 0) {
			grassGen(camX,camY,renW+10,renH+10);
		}

		if(timeC == 1000000) {
			timeC = 0;
		} else {
			timeC ++;
		}
	}

	public void render(Graphics g, int camX, int camY, int renW, int renH) {
		for(int x=(camX / Tile.tileSize);x<(camX/Tile.tileSize) + renW;x++) {
			for(int y=(camY / Tile.tileSize);y<(camY/Tile.tileSize) + renH;y++) {
				if(x >=0 && y >= 0 && x < worldW && y < worldH){
					block[x][y].render(g);

					if(block[x][y].id != Tile.air && block[x][y].id != Tile.solidair && !Inventory.isOpen) {
						if(block[x][y].contains(new Point((Component.mse.x / Component.pixelSize) + (int) Component.sX, (Component.mse.y / Component.pixelSize) + (int) Component.sY))) {
							g.setColor(new Color(255, 255, 255, 60));
							g.fillRect(block[x][y].x - camX, block[x][y].y - camY, block[x][y].width, block[x][y].height);
						}
					}
				}
			}
		}
	}
}
