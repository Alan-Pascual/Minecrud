package net.RoyAl.minecraftplatformer;

import java.awt.image.*;
import javax.imageio.*;

public class Tile {
	public static int tileSize = 20;
	public static int invLength = 8;
	public static int invHeight = 4;
	public static int invCellSize = 25;
	public static int invCellSpace = 4;
	public static int invBorderSpace = 4;
	public static int invItemBorder = 3;
	public static int maxMobs = 6;
	
	public static final int[] air = {-1, -1};
	public static final int[] earth = {0, 0};
	public static final int[] grass = {1, 0};
	public static final int[] sand = {2, 0};
	public static final int[] solidair = {3, 0};
	public static final int[] wood = {4, 0};
	public static final int[] bedrock = {5, 0};
	public static final int[] leaves = {6, 0};
	public static final int[] stone = {7, 0};
	
	public static int[] mobEmerald = {0, 16};
	public static int[] character = {0, 18};
	
	public static BufferedImage tileset_terrain;
	public static BufferedImage tile_cell;
	public static BufferedImage tile_select;
	
	public Tile() {
		try {
			Tile.tileset_terrain = ImageIO.read(getClass().getResource("/tileset_terrain.png"));
			Tile.tile_cell = ImageIO.read(getClass().getResource("/tile_cell.png"));
			Tile.tile_select = ImageIO.read(getClass().getResource("/tile_select.png"));
		} catch(Exception e) { }
	}
}
