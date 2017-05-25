#version 330 core

layout (location = 0) out vec4 color;

in DATA
{
	vec2 tc;
	vec3 position;
} fs_in;

uniform sampler2D tex;
uniform vec2 bird;

//uniform int top;

void main()
{
	//if(top == 1)
		//fs_in.tc.y = 1.0 - fs_in.tc.y;
	
	//color = vec4(0.2, 0.3, 0.8, 1.0);	
	color = texture(tex, fs_in.tc);
	
	// If the color has an alpha value less than 1, don't render it
	// w = alpha, x = red, y = green, z = blue
	if(color.w < 1.0)
		discard;
		
	color *= 3.0 /(length(bird - fs_in.position.xy) + 2.5) + 0.2;
	color.w = 1.0;
}