package net.RoyAl.minecraftplatformer;

import java.awt.*;

public class Sky {
	public int day = 0;
	public int night = 1;
	public int time = day;
	public int r1 = 70, g1 = 120, b1 = 230; //Day.
	public int r2 = 15, g2 = 15, b2 = 80; //Night.
	public int r = r1, g = g1, b = b1;
	public int colorChangeTime = 100;
	public int dr = (r1-r2) / colorChangeTime + 1, dg = (g1-g2) / colorChangeTime + 1, db = (b1-b2) / colorChangeTime + 1;
	public int dayFrame = 0, dayTime = 10000;
	public int changeFrame = 0, changeTime = 50;
	
	public Sky() {
		if(time == day) {
			r = r1;
			g = g1;
			b = b1;
		} else if(time == night) {
			r = r2;
			g = g2;
			b = b2;
		}
	}
	
	public void tick() {
		if(dayFrame >= dayTime) {
			if(time == day) {
				time = night;
			} else if(time == night) {
				time = day;
			}
			
			dayFrame = 0;
		} else {
			dayFrame += 1;
		}
		if(changeFrame >= changeTime) {
			if(time == day) {
				if(r < r1) {
					r += dr;
					if(r > r1) {
						r = r1;
					}
				}
				if(g < g1) {
					g += dg;
					if(g > g1) {
						g = g1;
					}
				}
				if(b < b1) {
					b += db;
					if(b > b1) {
						b = b1;
					}
				}
			} else if(time == night) {
				if(r > r2) {
					r -= dr;
					if(r < r2) {
						r = r2;
					}
				}
				if(g > g2) {
					g -= dg;
					if(g < g2) {
						g = g2;
					}
				}
				if(b > b2) {
					b -= db;
					if(b < b2) {
						b = b2;
					}
				}
			}
			changeFrame = 0;
		} else {
			changeFrame += 1;
		}
	}
	
	public void render(Graphics gr) {
		gr.setColor(new Color(r, g, b));
		gr.fillRect(0, 0, Component.pixel.width, Component.pixel.height);
	}
}
