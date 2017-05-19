package com.lawrencefoley.flappy.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.util.HashMap;
import java.util.Map;

import com.lawrencefoley.flappy.math.Matrix4f;
import com.lawrencefoley.flappy.math.Vector3f;
import com.lawrencefoley.flappy.utils.ShaderUtils;

public class Shader
{
	public static final int VERTEX_ATRIB = 0;
	public static final int TCOORD_ATRIB = 1;
	private final int ID;
	private Map<String, Integer> locationCache = new HashMap<String, Integer>();
	public static Shader BG;
	

	public Shader(String vertex, String fragment)
	{
		ID = ShaderUtils.load(vertex, fragment);
	}
	
	public static void loadAll()
	{
		BG = new Shader("shaders/bg.vert", "shaders/bg.frag");
	}
	
	public int getUniform(String name)
	{
		if(locationCache.containsKey(name))
			return locationCache.get(name);
		
		int result = glGetUniformLocation(ID, name);
		if(result == -1)
			System.err.println("Could not find uniform variable : " + name);
		
		locationCache.put(name, result);
		return result;
	}

	public void setUniform1i(String name, int value)
	{
		glUniform1i(getUniform(name), value);
	}
	
	public void setUniform1f(String name, int value)
	{
		glUniform1f(getUniform(name), value);
	}
	
	public void setUniform2f(String name, int value, float x, float y)
	{
		glUniform2f(getUniform(name), x, y);
	}
	
	public void setUniform3f(String name, int value, Vector3f vector)
	{
		glUniform3f(getUniform(name), vector.x, vector.y, vector.z);
	}

	public void setUiformMat4f(String name, Matrix4f matrix)
	{
		glUniformMatrix4fv(getUniform(name), false, matrix.toFloatBuffer());
	}

	public void enable()
	{
		glUseProgram(ID);
	}

	public void disable()
	{
		glUseProgram(0);
	}
}
