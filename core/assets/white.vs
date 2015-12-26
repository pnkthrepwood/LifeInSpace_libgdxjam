attribute highp vec4 a_position;
attribute highp vec4 a_color;
attribute highp vec2 a_texCoord0;
uniform mat4 u_projTrans;

varying highp vec4 v_color;
varying highp vec2 v_texCoords;

void main() {
    v_color = a_color;
    v_texCoords = a_texCoord0;
    gl_Position = u_projTrans * a_position ;
}