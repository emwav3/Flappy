package com.lawrencefoley.flappy.level;

import org.lwjgl.glfw.GLFW;

import com.lawrencefoley.flappy.graphics.Shader;
import com.lawrencefoley.flappy.graphics.Texture;
import com.lawrencefoley.flappy.graphics.VertexArray;
import com.lawrencefoley.flappy.input.Input;
import com.lawrencefoley.flappy.math.Matrix4f;
import com.lawrencefoley.flappy.math.Vector3f;

public class Bird
{
	private float SIZE = 1.0f;

	private VertexArray mesh;
	private Texture texture;

	private Vector3f position = new Vector3f();

	private float rot = 0.0f;
	private float delta = 0.0f;
	
	public boolean alive = true;
	public Bird()
	{
		float[] vertices = new float[] {
				-SIZE / 2.0f, -SIZE / 2.0f, 0.2f,
				-SIZE / 2.0f,  SIZE / 2.0f, 0.2f,
				 SIZE / 2.0f,  SIZE / 2.0f, 0.2f,
				 SIZE / 2.0f, -SIZE / 2.0f, 0.2f,
		};

		byte[] indices = new byte[] { 0, 1, 2, 2, 3, 0 };

		float[] tcs = new float[] { 0, 1, // top right
				0, 0, // top left
				1, 0, // bottom left
				1, 1 // bottom right
		};

		mesh = new VertexArray(vertices, indices, tcs);
		texture = new Texture("images/bird.png");
	}
	
	public void update()
	{
//		if(Input.keys[GLFW.GLFW_KEY_UP])
//		{
//			position.y+= 0.1F;
//		}
//		if(Input.keys[GLFW.GLFW_KEY_DOWN])
//		{
//			position.y-= 0.1F;
//		}
//		if(Input.keys[GLFW.GLFW_KEY_LEFT])
//		{
//			position.x-= 0.1F;
//		}
//		if(Input.keys[GLFW.GLFW_KEY_RIGHT])
//		{
//			position.x+= 0.1F;
//		}
		
		position.y -= delta;
		
		if(Input.isKeyDown(GLFW.GLFW_KEY_SPACE) && alive)
		{
			delta = -0.15f;
		}
		else
		{
			delta += 0.01f;
		}
		
		rot = -delta * 90.0f;
		
	}
	
	public void fall()
	{
		delta = -0.15f;
		
	}
	
	public void render()
	{
		Shader.BIRD.enable();
		Shader.BIRD.setUniformMat4f("ml_matrix", Matrix4f.translate(position).multiply(Matrix4f.rotate(rot)));
		texture.bind();
		mesh.render();
		Shader.BIRD.disable();
	}
	
	public float getY()
	{
		return this.position.y;
	}
	
	public float getSize()
	{
		return this.SIZE;
	}
}
