package net.RoyAl.minecraftplatformer;

import java.util.*;

public class Spawner implements Runnable {
	public boolean isRunning = true;
	
	public Spawner() {
		new Thread(this).start();
	}
	
	public void spawnMob(Mob mob) {
		Component.mob.add(mob);
	}
	
	public void run() {
		while(isRunning) {
			if(Component.mob.toArray().length < Tile.maxMobs) {
				spawnMob(new Emerald(new Random().nextInt((Level.worldW - 2) * Tile.tileSize) + Tile.tileSize + 30, 50, Tile.tileSize, Tile.tileSize * 2, Tile.mobEmerald));
			}
			
			try {
				Thread.sleep(new Random().nextInt(8000) + 5000);
			} catch(Exception e) { }
		}
	}
}
