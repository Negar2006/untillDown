// grayscale.frag (fragment)
#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_texCoords;
varying vec4 v_color;
uniform sampler2D u_texture;

void main() {
    vec4 color = texture2D(u_texture, v_texCoords);
    float gray = dot(color.rgb, vec3(0.3, 0.59, 0.11));
    gl_FragColor = vec4(vec3(gray), color.a) * v_color;
}
