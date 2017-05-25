package com.lawrencefoley.flappy;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.glfw.Callbacks.*;
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
		//glfwSetWindowPos(window, vidmode.width() - width / 2, vidmode.height() - height / 2);
		
		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable/disable v-sync
		glfwSwapInterval(0);
		// Make the window visible
		glfwShowWindow(window);
		
		GL.createCapabilities();
		
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		
		glEnable(GL_DEPTH_TEST);
		glActiveTexture(GL_TEXTURE1);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		System.out.println("OpenGL: " + glGetString(GL_VERSION));
		Shader.loadAll();
		
		Shader.BG.enable();

		Matrix4f pr_matrix = Matrix4f.orthographic(-10.0f, 10.0f, -10.0f * 9.0f / 16.0f, 10.0f * 9.0f / 16.0f, -1.0f, 1.0f); 
		Shader.BG.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.BG.disable();
		Shader.BG.setUniform1i("tex", 1); // Relates to "GL_TEXTURE1"
		level = new Level();
		
		Shader.BIRD.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.BIRD.setUniform1i("tex", 1); // Relates to "GL_TEXTURE1"
		
		Shader.PIPE.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.PIPE.setUniform1i("tex", 1); // Relates to "GL_TEXTURE1"
		
		//Shader.FADE.setUniformMat4f("pr_matrix", Matrix4f.orthographic(-1.0f, 1.0f, -1.0f, 1.0f, -1.0f, 1.0f));
		
		
		
	}
	
	public void run()
	{
		init();
		
		long lastTime = System.nanoTime();
		double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		while(this.running)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1.0)
			{
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			if(System.currentTimeMillis() - timer > 1000)
			{
				timer += 1000;
				glfwSetWindowTitle(window, "Flappy - " + frames + " FPS");
				//System.out.println(updates + " ups, " + frames + " fps");
				updates = 0;
				frames = 0;
			}
			if(glfwWindowShouldClose(window))
			{
				running = false;
			}
		}
		
		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		
		// Terminate GLFW and free the error callback
		glfwTerminate();
	}
	
	private void update()
	{
		glfwPollEvents();
		if(Input.keys[GLFW_KEY_SPACE])
		{
			//System.out.println("Flap!");
		}
		level.update();
		
		if(level.isGameOver())
		{
			level = new Level();
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
