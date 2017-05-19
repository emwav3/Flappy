package com.lawrencefoley.flappy.level;

import com.lawrencefoley.flappy.graphics.Shader;
import com.lawrencefoley.flappy.graphics.VertexArray;

public class Level
{
	private VertexArray background;
	
	public Level()
	{
		float[] vertices = new float[] {
				-10.0f, -10.0f * 9.0f / 16.0f, 0.0f, // top left
				-10.0f,  10.0f * 9.0f / 16.0f, 0.0f, // top right
				  0.0f,  10.0f * 9.0f / 16.0f, 0.0f, // middle right
				  0.0f, -10.0f * 9.0f / 16.0f, 0.0f, // middle left
		};
		
		byte[] indices = new byte[] {
			0, 1, 2,
			2, 3, 0
		};
		
		float[] tcs = new float[] {
			0, 1,
			0, 0, // top left
			1, 0,
			1, 0
		};
		
		background = new VertexArray(vertices, indices, tcs);
	}
	
	public void render()
	{
		Shader.BG.enable();
		background.render();
		Shader.BG.disable();
		
	}
}
