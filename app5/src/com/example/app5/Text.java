package com.example.app5;

public class Text {
	public static void draw(String s, int x, int y, int size){
		Assets.font.setBlend(true);
		for (int n=0;n<s.length();n++){
			if (s.charAt(n)==32) ;
			else if (s.charAt(n)<58)	Assets.font.draw(x,y,x+size,y+size,s.charAt(n)-48);
			else if (s.charAt(n)<91) Assets.font.draw(x,y,x+size,y+size,s.charAt(n)-55);			
			else Assets.font.draw(x,y,x+size,y+size,s.charAt(n)-97+10+26);
			x+=size;
		}
	}
}
