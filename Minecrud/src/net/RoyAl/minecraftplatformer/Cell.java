package net.RoyAl.minecraftplatformer;

import java.awt.*;

public class Cell extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	public int[] id = {0, 0};
	public int amount = 0;
	public int amountPanelSize = 10;
	
	public static void drawCenteredStringBox(Graphics g, String text, int x, int y, Font font) {
	    FontMetrics metrics = g.getFontMetrics(font);
	    
	    int boxSpace = 2;
	    int xStart = (x - metrics.stringWidth(text)) - boxSpace;
	    int yStart = y - 1;
	    
	    g.setColor(new Color(0, 0, 0, 180));
		g.fillRect(xStart - boxSpace, y - metrics.getAscent(), metrics.stringWidth(text) + (boxSpace * 2), metrics.getAscent());
		
		g.setColor(new Color(255, 255, 255, 200));
	    g.setFont(font);
	    g.drawString(text, xStart, yStart);
	}
	
	public Cell(Rectangle size, int[] id, int amount) {
		setBounds(size);
		this.id = id;
		this.amount = amount;
	}
	
	public void render(Graphics g, boolean isSelected) {
		if(Inventory.isOpen && contains(new Point(Component.mse.x / Component.pixelSize, Component.mse.y / Component.pixelSize))) {
			g.setColor(new Color(255, 255, 255, 130));
			g.fillRect(x, y, width, height);
		}
		
		g.setColor(new Color(160, 210, 255, 50));
		g.fillRect(x, y, width, height);
		g.drawImage(Tile.tile_cell, x, y, width, height, null);
		
		if(id != Tile.air) {
			g.drawImage(Tile.tileset_terrain, x + Tile.invItemBorder, y + Tile.invItemBorder, x - Tile.invItemBorder + width, y - Tile.invItemBorder + height, id[0] * Tile.tileSize, id[1] * Tile.tileSize, id[0] * Tile.tileSize + Tile.tileSize, id[1] * Tile.tileSize + Tile.tileSize, null);
		}
		
		if(isSelected) {
			g.drawImage(Tile.tile_select, x-1, y-1, width + 2, height + 2, null);
		}
		
		if(amount != -1 && id != Tile.air) {
			drawCenteredStringBox(g, "" + amount, x + Tile.invCellSize - Tile.invItemBorder, y + Tile.invCellSize - Tile.invItemBorder, new Font("TimesRoman", Font.PLAIN, 9));
		}
	}
}
