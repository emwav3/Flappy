package com.lawrencefoley.flappy.level;

import com.lawrencefoley.flappy.graphics.Shader;
import com.lawrencefoley.flappy.graphics.Texture;
import com.lawrencefoley.flappy.graphics.VertexArray;
import com.lawrencefoley.flappy.math.Matrix4f;
import com.lawrencefoley.flappy.math.Vector3f;

public class Level
{
	private VertexArray background;
	private Texture bgTexture;

	private int xScroll = 0;
	private int map = 0;

	private Bird bird;
	
	public Level()
	{
		float[] vertices = new float[] { -10.0f, -10.0f * 9.0f / 16.0f, 0.0f, // bottom left
				-10.0f, 10.0f * 9.0f / 16.0f, 0.0f, // top left
				0.0f, 10.0f * 9.0f / 16.0f, 0.0f, // top right
				0.0f, -10.0f * 9.0f / 16.0f, 0.0f // bottom right
		};

		byte[] indices = new byte[] { 0, 1, 2, 2, 3, 0 };

		float[] tcs = new float[] { 0, 1, // top right
				0, 0, // top left
				1, 0, // bottom left
				1, 1 // bottom right
		};

		background = new VertexArray(vertices, indices, tcs);
		bgTexture = new Texture("images/bg.jpeg");
		
		bird = new Bird();
	}

	public void update()
	{
		xScroll--;
		if (-xScroll % 335 == 0)
		{
			map++;
		}
		bird.update();
	}

	public void render()
	{
		bgTexture.bind();
		Shader.BG.enable();
		background.bind();
		for (int i = map; i < map + 4; i++)
		{
			Shader.BG.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(i * 10 + xScroll * 0.03f, 0.0f, 0.0f)));
			background.draw();
		}
		background.render();
		Shader.BG.disable();
		bgTexture.unbind();
		
		bird.render();
	}
}
