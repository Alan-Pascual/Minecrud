package net.RoyAl.minecraftplatformer;

import java.awt.*;
import java.awt.event.*;

import net.RoyAl.minecraftplatformer.Component;

public class Inventory {
	public static Cell[] invBar = new Cell[Tile.invLength];
	public static Cell[] invBag = new Cell[Tile.invLength * Tile.invHeight];
	
	public static boolean isOpen = false;
	public static boolean isHolding = false;
	
	public static int selected = 0;
	public static int[] holdingID = Tile.air;
	public static int holdingAmount = -1;
	public static int invMax = 99;
	
	public Inventory() {
		for(int i = 0; i < invBar.length; i++) {
			invBar[i] = new Cell(new Rectangle(Tile.invBorderSpace + (i * (Tile.invCellSize + Tile.invCellSpace)), Tile.invBorderSpace, Tile.invCellSize, Tile.invCellSize), Tile.air, -1);
		}
		
		int x = 0, y = 0;
		for(int i = 0; i < invBag.length; i++) {
			invBag[i] = new Cell(new Rectangle(Tile.invBorderSpace + (x * (Tile.invCellSize + Tile.invCellSpace)), Tile.invBorderSpace + Tile.invCellSize + Tile.invCellSpace + (y * (Tile.invCellSize + Tile.invCellSpace)), Tile.invCellSize, Tile.invCellSize), Tile.air, -1);
		
			x++;
			if(x == Tile.invLength) {
				x = 0;
				y++;
			}
		}
		
		invBar[0].id = Tile.earth;
		invBar[0].amount = 50;
		invBar[1].id = Tile.grass;
		invBar[1].amount = 50;
		invBar[2].id = Tile.sand;
		invBar[2].amount = 50;
		invBar[3].id = Tile.wood;
		invBar[3].amount = 50;
		invBar[4].id = Tile.leaves;
		invBar[4].amount = 50;
		invBar[5].id = Tile.stone;
		invBar[5].amount = 50;
	}
	
	public static void click(MouseEvent e) {
		if(e.getButton() == 1) {
			if(isOpen) {
				for(int i = 0; i < invBar.length; i++) {
					if(invBar[i].contains(new Point(Component.mse.x / Component.pixelSize, Component.mse.y / Component.pixelSize))) {
						if(invBar[i].id != Tile.air && !isHolding){
							holdingID = invBar[i].id;
							holdingAmount = invBar[i].amount;
							invBar[i].id = Tile.air;
							invBar[i].amount = -1;
							
							isHolding = true;
						} else if(isHolding && invBar[i].id == Tile.air) {
							invBar[i].id = holdingID;
							invBar[i].amount = holdingAmount;
							
							isHolding = false;
						} else if(isHolding && invBar[i].id == holdingID && invBar[i].id != Tile.air && invBar[i].amount < invMax) {
							invBar[i].amount += holdingAmount;
							holdingAmount = 0;
							if(invBar[i].amount > invMax) {
								holdingAmount = invBar[i].amount - invMax;
								invBar[i].amount = invMax;
							}
							if(holdingAmount == 0) {
								isHolding = false;
							}
						} else if(isHolding && invBar[i].id != Tile.air) {
							int[] con = invBar[i].id;
							int temp = invBar[i].amount;
							
							invBar[i].id = holdingID;
							invBar[i].amount = holdingAmount;
							holdingID = con;
							holdingAmount = temp;
						}
					}
				}
				
				for(int i = 0; i < invBag.length; i++) {
					if(invBag[i].contains(new Point(Component.mse.x / Component.pixelSize, Component.mse.y / Component.pixelSize))) {
						if(invBag[i].id != Tile.air && !isHolding){
							holdingID = invBag[i].id;
							holdingAmount = invBag[i].amount;
							invBag[i].id = Tile.air;
							invBag[i].amount = -1;
							
							isHolding = true;
						} else if(isHolding && invBag[i].id == Tile.air) {
							invBag[i].id = holdingID;
							invBag[i].amount = holdingAmount;
							
							isHolding = false;
						} else if(isHolding && invBag[i].id == holdingID && invBag[i].id != Tile.air && invBag[i].amount < invMax) {
							invBag[i].amount += holdingAmount;
							holdingAmount = 0;
							if(invBag[i].amount > invMax) {
								holdingAmount = invBag[i].amount - invMax;
								invBag[i].amount = invMax;
							}
							if(holdingAmount == 0) {
								isHolding = false;
							}
						} else if(isHolding && invBag[i].id != Tile.air) {
							int[] con = invBag[i].id;
							int temp = invBag[i].amount;
							
							invBag[i].id = holdingID;
							invBag[i].amount = holdingAmount;
							holdingID = con;
							holdingAmount = temp;
						}
					}
				}
			}
		}
		
		if(e.getButton() == 3) {
			if(isOpen) {
				for(int i = 0; i < invBar.length; i++) {
					if(invBar[i].contains(new Point(Component.mse.x / Component.pixelSize, Component.mse.y / Component.pixelSize))) {
						if(invBar[i].id == Tile.air && isHolding){
							invBar[i].id = holdingID;
							if(holdingAmount % 2 == 1) {
								invBar[i].amount = holdingAmount / 2 + 1;
								holdingAmount /= 2;
							} else {
								invBar[i].amount = holdingAmount / 2;
								holdingAmount /= 2;
							}
						} else if(isHolding && invBar[i].id == holdingID && invBar[i].id != Tile.air && invBar[i].amount < invMax) {
							invBar[i].amount ++;
							holdingAmount --;
						} else if(!isHolding && invBar[i].id != Tile.air) {
							holdingID = invBar[i].id;
							isHolding = true;
							if(invBar[i].amount % 2 == 0) {
								holdingAmount = invBar[i].amount / 2;
								invBar[i].amount /= 2;
							} else {
								holdingAmount = invBar[i].amount / 2 + 1;
								invBar[i].amount /= 2;
								if(invBar[i].amount == 0) {
									invBar[i].amount = -1;
									invBar[i].id = Tile.air;
								}
							}
						}
						if(holdingAmount == 0) {
							isHolding = false;
						}
					}
				}
				
				for(int i = 0; i < invBag.length; i++) {
					if(invBag[i].contains(new Point(Component.mse.x / Component.pixelSize, Component.mse.y / Component.pixelSize))) {
						if(invBag[i].id == Tile.air && isHolding){
							invBag[i].id = holdingID;
							if(holdingAmount % 2 == 1) {
								invBag[i].amount = holdingAmount / 2 + 1;
								holdingAmount /= 2;
							} else {
								invBag[i].amount = holdingAmount / 2;
								holdingAmount /= 2;
							}
						} else if(isHolding && invBag[i].id == holdingID && invBag[i].id != Tile.air && invBag[i].amount < invMax) {
							invBag[i].amount ++;
							holdingAmount --;
						} else if(!isHolding && invBag[i].id != Tile.air) {
							holdingID = invBag[i].id;
							isHolding = true;
							if(invBag[i].amount % 2 == 0) {
								holdingAmount = invBag[i].amount / 2;
								invBag[i].amount /= 2;
							} else {
								holdingAmount = invBag[i].amount / 2 + 1;
								invBag[i].amount /= 2;
								if(invBag[i].amount == 0) {
									invBag[i].amount = -1;
									invBag[i].id = Tile.air;
								}
							}
						}
						if(holdingAmount == 0) {
							isHolding = false;
						}
					}
				}
			}
		}
	}
	
	public void render(Graphics g) {
		for(int i=0; i<invBar.length;i++) {
			boolean isSelected = false;
			if(i == selected) {
				isSelected = true;
			}
			
			invBar[i].render(g, isSelected);
		}
		
		if(isOpen) {
			for(int i=0; i<invBag.length;i++) {
				invBag[i].render(g, false);
			}
		}
		
		if(isHolding) {
			g.drawImage(Tile.tileset_terrain, (Component.mse.x / Component.pixelSize) - (Tile.invCellSize / 2) + Tile.invItemBorder, (Component.mse.y / Component.pixelSize) - (Tile.invCellSize / 2) + Tile.invItemBorder, (Component.mse.x / Component.pixelSize) - (Tile.invCellSize / 2) + Tile.invCellSize - Tile.invItemBorder, (Component.mse.y / Component.pixelSize) - (Tile.invCellSize / 2) + Tile.invCellSize - Tile.invItemBorder, holdingID[0] * Tile.tileSize, holdingID[1] * Tile.tileSize, holdingID[0] * Tile.tileSize + Tile.tileSize, holdingID[1] * Tile.tileSize + Tile.tileSize, null);
			Cell.drawCenteredStringBox(g, "" + holdingAmount, (Component.mse.x / Component.pixelSize) - (Tile.invCellSize / 2) + Tile.invItemBorder + Tile.invCellSize - Tile.invItemBorder, (Component.mse.y / Component.pixelSize) - (Tile.invCellSize / 2) + Tile.invItemBorder + Tile.invCellSize - Tile.invItemBorder, new Font("TimesRoman", Font.PLAIN, 9));
		}
	}
}
