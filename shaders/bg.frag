#version 330 core

layout (location = 0) out vec4 color;

in DATA
{
	vec2 tc;
	vec3 position;
} fs_in;

uniform sampler2D tex;
uniform vec2 bird;

void main()
{
	//color = vec4(0.2, 0.3, 0.8, 1.0);
	color = texture(tex, fs_in.tc);
	
	color *= 1.0 /(length(bird - fs_in.position.xy) + 2.5) + 0.8;
	//color *= 2.0 /(length(bird.y - fs_in.position.y) + 2.5);
		
}