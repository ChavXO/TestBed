/* Simple quad
 *
 * This sends a simple quad to the shaders
 *
 * C. Andrews  2015-02-15
 *
 */


/*
 * Draw a quad that we can paint on with the fragment shader
 */
window.onload = function main() {
    var canvas = document.getElementById('gl-canvas');
    
    // Use webgl-util.js to make sure we get a WebGL context
    var gl = WebGLUtils.setupWebGL(canvas);
    
    if (!gl) {
        alert("Could not create WebGL context");
        return;
    }
    
    
    // set the viewport to be sized correctly
    gl.viewport(0,0, canvas.width, canvas.height);
    
    // create program with our shaders and enable it
    program = initShaders(gl, "vertex-shader", "fragment-shader");
    gl.useProgram(program);
    
    // create a simple quad that takes up the entire canvas
    var vertices = new Float32Array([
        -1.0, 1.0,
        -1.0, -1.0,
        1.0, 1.0,
        1.0, -1.0
    ]);
    
    // create the buffer and load it with the data
    var buffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, buffer);
    gl.bufferData(gl.ARRAY_BUFFER, vertices, gl.STATIC_DRAW);
    

    // get the attribute for position
    var a_Position = gl.getAttribLocation(program, "a_Position");

    gl.vertexAttribPointer(a_Position, 2, gl.FLOAT, false, 0,0);
    gl.enableVertexAttribArray(a_Position);
    
    // set the background or clear color
    gl.clearColor(1.0, 1.0, 1.0, 1.0); //that is white used to clear the canvas
    
    // clear the context for new content
    gl.clear(gl.COLOR_BUFFER_BIT);
    
   
    // tell the GPU to draw the point
    gl.drawArrays(gl.TRIANGLE_STRIP, 0, vertices.length/2);
    
  
}


