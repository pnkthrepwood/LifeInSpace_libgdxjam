varying highp vec4 v_color;
varying highp vec2 v_texCoords;
uniform sampler2D u_texture;
void main() {

    gl_FragColor = vec4(0.0);

    highp vec4 color = texture2D(u_texture, v_texCoords);

    if(color.a > 0.0) {
       gl_FragColor = vec4(1.0,0,0,1.0);
}

}