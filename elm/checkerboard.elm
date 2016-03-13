import Graphics.Element exposing (..)
import Math.Vector3 exposing (..)
import Math.Vector4 exposing (..)
import Math.Matrix4 exposing (..)
import Time exposing (..)
import WebGL exposing (..)

type alias Vertex = { a_Position : Vec3}

vertices : Drawable Vertex
vertices = TriangleStrip 
        [ Vertex (vec3 -1  1 0)
        , Vertex (vec3 -1 -1 0)
        , Vertex (vec3  1  1 0)
        , Vertex (vec3  1 -1 0)
        ]
        
main : Element
main = webgl (500, 500) 
      [ render vertexShader fragmentShader vertices { perspective = Math.Matrix4.identity}]
      
vertexShader : Shader {a_Position : Vec3} a { v_Location : Vec3 }
vertexShader = [glsl|

attribute vec3 a_Position;
varying vec3 v_Location;
void main(){
  gl_Position = a_Position;
  v_Location = gl_Position;
}

|]


fragmentShader : Shader {} u { v_Location : Vec3 }
fragmentShader = [glsl|
precision mediump float;
varying vec3 v_Location;
highp float x, y;
int iteration = 0;

void main(){
    x = v_Location.x;
    y = v_Location.y;
    
    
    if (ceil(mod(x*5.0, 2.0)) == ceil(mod(y*5.0, 2.0))){
        gl_FragColor = vec4(0.0,0.0,0.0,1.0);  //light color (RGB)
    }else{
        gl_FragColor = vec4(1.0,1.0,1.0,1.0);  //black color
    }
}
|]
