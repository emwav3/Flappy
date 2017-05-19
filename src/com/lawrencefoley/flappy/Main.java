package com.lawrencefoley.flappy;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import com.lawrencefoley.flappy.graphics.Shader;
import com.lawrencefoley.flappy.input.Input;
import com.lawrencefoley.flappy.level.Level;
import com.lawrencefoley.flappy.math.Matrix4f;


public class Main implements Runnable
{
	private int width = 1280;
	private int height = 720;
	private String title = "Flappy";
	
	private boolean running = false;
	private Thread thread;
	
	private long window;
	
	private Level level;
	
	public void start()
	{
		running = true;
		thread = new Thread(this, "Game");
		thread.start();
	}
	
	private void init()
	{
		if(!glfwInit())
		{
			// TODO Handle this
			return;
		}
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		
		window = glfwCreateWindow(width, height, title, NULL, NULL);
		if(window == NULL)
		{
			System.out.println("No window found!");
			return;
		}
		
		glfwSetKeyCallback(window, new Input());
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor()); 
		glfwSetWindowPos(window, vidmode.width() / 2, vidmode.height() / 2);
		
		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(1);
		// Make the window visible
		glfwShowWindow(window);
		
		GL.createCapabilities();
		
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		
		glEnable(GL_DEPTH_TEST);
		System.out.println("OpenGL: " + glGetString(GL_VERSION));
		Shader.loadAll();
		
		Shader.BG.enable();
		Matrix4f pr_matrix = Matrix4f.orthographic(-10.0f, 10.0f, -10.0f * 9.0f / 16.0f, 10.0f * 9.0f / 16.0f, -1.0f, 1.0f); 
		Shader.BG.setUiformMat4f("pr_matrix", pr_matrix);
		Shader.BG.disable();
		
		level = new Level();
	}
	
	public void run()
	{
		init();
		while(this.running)
		{
			
			update();
			render();
			if(glfwWindowShouldClose(window))
			{
				running = false;
			}
		}
	}
	
	private void update()
	{
		glfwPollEvents();
		if(Input.keys[GLFW_KEY_SPACE])
		{
			System.out.println("Flap!");
		}
	}
	private void render()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
		
		level.render();
		
		int errorCode = glGetError();
		if(errorCode != GL_NO_ERROR)
			System.out.println(errorCode);
		glfwSwapBuffers(window);
	}
	public static void main(String[] args)
	{
		new Main().start();
	}

}
