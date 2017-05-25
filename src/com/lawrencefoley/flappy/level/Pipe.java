package com.lawrencefoley.flappy.level;

import com.lawrencefoley.flappy.graphics.Texture;
import com.lawrencefoley.flappy.graphics.VertexArray;
import com.lawrencefoley.flappy.math.Matrix4f;
import com.lawrencefoley.flappy.math.Vector3f;

public class Pipe
{
	private Vector3f position = new Vector3f();
	private Matrix4f ml_matrix;
	
	private static float width = 1.5f, height = 8.0f;
	private static Texture texture;
	private static VertexArray mesh;
	
	public static void create()
	{
		float[] vertices = new float[] {
				0.0f, 0.0f, 0.1f,
				0.0f, height, 0.1f,
				width, height, 0.1f,
				width, 0.0f, 0.1f,
		};

		byte[] indices = new byte[] { 0, 1, 2, 2, 3, 0 };

		float[] tcs = new float[] { 0, 1, // top right
				0, 0, // top left
				1, 0, // bottom left
				1, 1 // bottom right
		};

		mesh = new VertexArray(vertices, indices, tcs);
		texture = new Texture("images/pipe.png");
	}
	
	public Pipe(float x, float y)
	{
		position.x = x;
		position.y = y;
		
		ml_matrix = Matrix4f.translate(position);
		
	}
	
	public float getX()
	{
		return position.x;
	}
	
	public float getY()
	{
		return position.y;
	}
	
	public Matrix4f getModelMatrix()
	{
		return ml_matrix;
	}
	
	public void setModelMatrix(Matrix4f matrix)
	{
		this.ml_matrix = matrix;
	}
	
	public static VertexArray getMesh()
	{
		return mesh;
	}
	
	public static Texture getTexture()
	{
		return texture;
	}
	
	public static float getWidth()
	{
		return width;
	}
	
	public static float getHeight()
	{
		return height;
	}
}
