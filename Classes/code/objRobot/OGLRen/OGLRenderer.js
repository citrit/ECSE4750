//////////////////////////////////////////////////////////////////////////
//
//  This code is for instructional purposes only. It was generated for
//  use in a college course to show certain aspects of data
//  storage algorithms. It has problems and should not be used
//  outside the class environment.
//
//  Author:  Thomas D. Citriniti     citrit@rpi.edu
//  Class:   Computer Graphics 
//           Rensselaer Polytechnic Institute
//  Date:    Sept 2015
//
//////////////////////////////////////////////////////////////////////////

var OGLRen = OGLRen || {};

/**
  * Camera class is a holder of cells into single objects.
  */
OGLRen.OGLRenderer = function(elemTag, vShader, fShader) {
    
    /** Add an Actor to the scene */
    this.addActor = function( actor) { ActorSet.push(actor); }

	/** Add an Light to the scene */
    this.addLight = function( light) { LightSet.push(light); }
	/** Turn lighting off if there are no normals */
    this.lighting = function( onOrOff)
    {
    	var i;
    	for (i=0;i<LightSet.length;i++) {
    		LightSet[i].setState(onOrOff);
    	}
    }

	/** Add an Camera to the scene */
    this.addCamera = function( cam) 
	{ 
        var _this = this;
		CameraSet.push(cam);
		cam.setRenderer(_this);
	}

	/** Get the current active Camera, currently only one camera
	  * supported
	  */
    this.getCamera = function() { return CameraSet[0]; }
    
    var curPointBuffer = [];
    var curNormBuffer = [];
    var curTopology = this.POINT;

	/** Sets the render mode for consequent calls */
    this.beginDraw = function( renderMode)
	{
        curTopology = renderMode;
        /*switch(renderMode) {
		case this.POINT:
			curTopology = gl.POINTS;
			break;
		case this.POLYGON:
			curTopology = gl.POLYGON;
			break;
		case this.POLYLINE:
			curTopology = gl.LINE_STRIP;
			break;
		case this.LINE:
			curTopology = gl.LINES;
			break;
		case this.TRIANGLE:
			curTopology = gl.TRIANGLES;
			break;
		case this.LINE_LOOP:
			curTopology = gl.LINE_LOOP;
			break;
		default:
			break;
		}*/
	}

	/** Signal all vertices have been entered, flush the buffer */
    this.endDraw = function(geomBuff, normBuff)
	{
        gl.bindBuffer(gl.ARRAY_BUFFER, geomBuff);
        gl.vertexAttribPointer(shaderProgram.vertexPositionAttribute, 3, gl.FLOAT, false, 0, 0);
        
        gl.bindBuffer(gl.ARRAY_BUFFER, normBuff);
        gl.vertexAttribPointer(shaderProgram.pNormalUniform, 3, gl.FLOAT, false, 0, 0);
        
        gl.uniformMatrix4fv(shaderProgram.pMatrixUniform, false, pMatrix);
        gl.uniformMatrix4fv(shaderProgram.mvMatrixUniform, false, mvMatrix);
        gl.uniform4fv(shaderProgram.pColorUniform, pColor);
        
        var numItems = geomBuff.length/3;
        gl.drawArrays(curTopology, 0, numItems);
        curPointBuffer = [];
        curNormBuffer = [];

		// Set back to defaults in case someone set these.
		/*gl.end();
		gl.flush();
		gl.disable(gl.TEXTURE_2D);
		gl.shadeModel(gl.SMOOTH);*/
	}
    
    this.getPtsBuffer = function() {
        var geomBuffer = gl.createBuffer();
        gl.bindBuffer(gl.ARRAY_BUFFER, geomBuffer);
        gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(curPointBuffer), gl.STATIC_DRAW);
        geomBuffer.length = curPointBuffer.length;
        return geomBuffer;
    }
    
    this.getNormBuffer = function() {
        var nrmBuffer = gl.createBuffer();
        gl.bindBuffer(gl.ARRAY_BUFFER, nrmBuffer);
        gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(curNormBuffer), gl.STATIC_DRAW);
        nrmBuffer.length = curNormBuffer.length;
        return nrmBuffer;
    }

	/** specify a vector for the previous begin command */
    this.vertex = function( p)
	{
        // System.out.println("vert: "+p.x+", "+p.y+", "+p.z);
		//gl.vertex(p.x, p.y, p.z);
        curPointBuffer = curPointBuffer.concat(p);
	}

    
	/** Set the current rendering color */
    this.setMaterial = function( mat)
	{
        if (mat != null) {
            pColor = mat.getAmbient();
            /* do somehting with the shader here
            gl.material(gl.FRONT_AND_BACK, gl.AMBIENT, mat.ambient);
            gl.material(gl.FRONT_AND_BACK, gl.DIFFUSE, mat.diffuse);
            gl.material(gl.FRONT_AND_BACK, gl.SPECULAR, mat.specular);
            gl.material(gl.FRONT_AND_BACK, gl.SHININESS, 0.8);
            gl.color(mat.diffuse[0],mat.diffuse[1],mat.diffuse[2]);
            */
        }
	};

	/** specify a texture coord for the vertex */
    this.texCoord = function( p)
	{
	        // System.out.println("tex: "+p.x+", "+p.y);
		gl.texCoord(p.x, p.y);		
	}
	/** specify a normal for the vertex */
    this.normal = function( n)
    {
	    //System.out.println("norm: "+n.x+", "+n.y+", "+n.z);
		curNormBuffer = curNormBuffer.concat(n);		
	}


	/** Lets draw something, calls all objects render method,
	  * Camera first, then Lights and Actors. Then swap buffers
	  */
    this.render = function()
	{
		if (initLess) {
			this.initialize();
			initLess = false;
		}
		//gl.use();
		gl.clear(gl.COLOR_BUFFER_BIT | gl.DEPTH_BUFFER_BIT );

        var _this = this;
        if (CameraSet[0] != null)
		  CameraSet[0].render(_this);

		for (var i = 0;i < LightSet.length;i++) {
			LightSet[i].render(_this);
		}

		//gl.matrixMode(gl.MODELVIEW);
		for (var i=0;i<ActorSet.length;i++) {
			ActorSet[i].render(_this);
		}
		//gl.swap();
	}

	/** Lets make sure OpenGL is initialized with some safe settings */
    this.initialize = function()
	{
		/* initialize the widget */
		var width  = gl.drawingBufferWidth;
		var height = gl.drawingBufferHeight;
      
		// Initialize the rendering viewport size to OpenGL
		gl.viewport( 0, 0, width, height );
		gl.clearColor (0.0, 0.0, 0.0, 1.0);
		// initialize blending for transparency
		gl.blendFunc(gl.SRC_ALPHA, gl.ONE_MINUS_SRC_ALPHA);
		gl.enable(gl.DEPTH_TEST);
		gl.enable(gl.DITHER);
		//gl.disable(gl.ALPHA_TEST);
		//gl.disable(gl.COLOR_MATERIAL);
		//gl.shadeModel(gl.SMOOTH);
		gl.depthFunc( gl.LEQUAL );
		//gl.texParameter(gl.TEXTURE_2D, gl.TEXTURE_WRAP_S, gl.REPEAT);
		//gl.texParameter(gl.TEXTURE_2D, gl.TEXTURE_WRAP_T, gl.REPEAT);
		//gl.texParameter(gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.NEAREST);
		//gl.texParameter(gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.NEAREST);
		//gl.pixelStore(gl.UNPACK_ALIGNMENT, 1);
		//gl.texEnv(gl.TEXTURE_ENV, gl.TEXTURE_ENV_MODE, gl.DECAL);
		//gl.lightModel(gl.LIGHT_MODEL_TWO_SIDE, gl.TRUE);
	}

	/** Push the current Model transformation matrix */
	this.pushModelMatrix = function()
	{
		//gl.matrixMode (gl.MODELVIEW);
		//gl.pushMatrix();
        var copy = mat4.create();
        mat4.copy(copy, mvMatrix);
        mvMatrixStack.push(copy);
	}

	/** Set the current orientation for consequent drawing */
	this.setOrientation = function( rotations, scale,  trans)
	{
        if (rotations) {
            mat4.rotateX(mvMatrix, mvMatrix, rotations[0]);
            mat4.rotateY(mvMatrix, mvMatrix, rotations[1]);
            mat4.rotateZ(mvMatrix, mvMatrix, rotations[2]);
        }
        if (scale)
            mat4.scale(mvMatrix, mvMatrix, scale);
        if (trans) 
            mat4.translate(mvMatrix, mvMatrix, trans);
        //gl.translate(pos.x, pos.y,pos.z);
		//gl.scale(scale[0], scale[1], scale[2]);
		//gl.rotate(rotations[0], 1, 0, 0);
		//gl.rotate(rotations[1], 0, 1, 0);
		//gl.rotate(rotations[2], 0, 0, 1);
	}

	/** Pop the current Model transformation matrix */
	this.popModelMatrix = function()
	{
		//gl.matrixMode (gl.MODELVIEW);
		//gl.popMatrix();
        if (mvMatrixStack.length == 0) {
            throw "Invalid popMatrix!";
        }
        mvMatrix = mvMatrixStack.pop();
	}
    
    /** Set the World MAtrix **/
    this.setPerspectiveMatrix = function(pmat) {
        mat4.copy(pMatrix, pmat);
    }

	/** Load the current texture
	 */
	this.loadTexture = function( imageNum, atex)
	{
		gl.enable(gl.TEXTURE_2D);
		gl.shadeModel(gl.FLAT);
		gl.texImage(gl.TEXTURE_2D, imageNum, 3, atex.gTexW, atex.gTexH, 0,
		    gl.BGRA_EXT, gl.UNSIGNED_BYTE, atex.pixels);
	}
    
    
    /**
     * Creates a webgl context. If creation fails it will
     * change the contents of the container of the <canvas>
     * tag to an error message with the correct links for Webgl.
     * @param {Element} canvas. The canvas element to create a
     *     context from.
     * @param {WebGLContextCreationAttirbutes} opt_attribs Any
     *     creation attributes you want to pass in.
     * @param {function:(msg)} opt_onError An function to call
     *     if there is an error during creation.
     * @return {WebGLRenderingContext} The created context.
     */
    var setupWebGL = function(canvas, opt_attribs, opt_onError) {
      function handleCreationError(msg) {
        var container = canvas.parentNode;
        if (container) {
          var str = window.WebGLRenderingContext ?
               OTHER_PROBLEM :
               GET_A_WEBGL_BROWSER;
          if (msg) {
            str += "<br/><br/>Status: " + msg;
          }
          container.innerHTML = makeFailHTML(str);
        }
      };

      opt_onError = opt_onError || handleCreationError;

      if (canvas.addEventListener) {
        canvas.addEventListener("webglcontextcreationerror", function(event) {
              opt_onError(event.statusMessage);
            }, false);
      }
      var context = create3DContext(canvas, opt_attribs);
      if (!context) {
        if (!window.WebGLRenderingContext) {
          opt_onError("");
        }
      }
      return context;
    };

    /**
     * Creates a webgl context.
     * @param {!Canvas} canvas The canvas tag to get context
     *     from. If one is not passed in one will be created.
     * @return {!WebGLContext} The created context.
     */
    var create3DContext = function(canvas, opt_attribs) {
      var names = ["webgl", "experimental-webgl", "webkit-3d", "moz-webgl"];
      var context = null;
      for (var ii = 0; ii < names.length; ++ii) {
        try {
            context = canvas.getContext(names[ii], opt_attribs);
            context = WebGLDebugUtils.makeDebugContext(context, undefined, logGLCall);
        } catch(e) {}
        if (context) {
          break;
        }
      }
      return context;
    }

    /** Debug stuff **/
    var validateNoneOfTheArgsAreUndefined = function(functionName, args) {
      for (var ii = 0; ii < args.length; ++ii) {
        if (args[ii] === undefined) {
          console.error("undefined passed to gl." + functionName + "(" +
                         WebGLDebugUtils.glFunctionArgsToString(functionName, args) + ")");
        }
      }
    } 
    var logGLCall = function(functionName, args) {   
       console.log("gl." + functionName + "(" + 
          WebGLDebugUtils.glFunctionArgsToString(functionName, args) + ")");   
    } 
    
    var getShader = function(gl, str, shType) {

        var shader = gl.createShader(shType);

        gl.shaderSource(shader, str);
        gl.compileShader(shader);

        if (!gl.getShaderParameter(shader, gl.COMPILE_STATUS)) {
            alert(gl.getShaderInfoLog(shader));
            return null;
        }

        return shader;
    }
    
    var initShaders = function(vShad, fShad) {
        var fragmentShader = getShader(gl, fShad, gl.FRAGMENT_SHADER);
        var vertexShader = getShader(gl, vShad, gl.VERTEX_SHADER);

        shaderProgram = gl.createProgram();
        gl.attachShader(shaderProgram, vertexShader);
        gl.attachShader(shaderProgram, fragmentShader);
        gl.linkProgram(shaderProgram);

        if (!gl.getProgramParameter(shaderProgram, gl.LINK_STATUS)) {
            alert("Could not initialise shaders");
        }

        gl.useProgram(shaderProgram);

        shaderProgram.vertexPositionAttribute = gl.getAttribLocation(shaderProgram, "aVertexPosition");
        gl.enableVertexAttribArray(shaderProgram.vertexPositionAttribute);
        shaderProgram.pNormalUniform = gl.getAttribLocation( shaderProgram, "aVertexNormal" );
        gl.enableVertexAttribArray(shaderProgram.pNormalUniform);
        
        shaderProgram.pMatrixUniform = gl.getUniformLocation(shaderProgram, "uPMatrix");
        shaderProgram.mvMatrixUniform = gl.getUniformLocation(shaderProgram, "uMVMatrix");
        shaderProgram.pColorUniform = gl.getUniformLocation(shaderProgram, "uColor");
        

    }
    
    this.getGL = function() { return gl; }
    this.getpMatrix = function() { return pMatrix; }
    this.getmvMatrix = function() { return mvMatrix; }
    this.getWidth = function() { return gl.drawingBufferWidth; }
    this.getHeight = function() { return gl.drawingBufferHeight; }
    
    // Instance Variables
    var canvas = document.getElementById( elemTag );
    var gl = setupWebGL( canvas );
    
    var shaderProgram = null;
    var mvMatrix = mat4.create();
    mat4.identity(mvMatrix);
    var mvMatrixStack = [];

    var pMatrix = mat4.create();
    mat4.identity(pMatrix);
    
    var pColor = [0.8, 0.8, 0.8, 1.0];
    var pNormal = [0.8, 0.8, 0.8, 1.0];

    var fragShaderCode =    "varying highp vec2 vTextureCoord;" +
                            "varying highp vec3 vLighting;" +
                            "uniform highp vec4 uColor;" +
                            "uniform highp sampler2D uSampler;" +

                            "void main(void) {" +
                                "gl_FragColor = uColor * vec4(vLighting, 1.0);" +
                            "}";
    
    var vertShaderCode =    "attribute highp vec3 aVertexNormal;" +
                            "attribute highp vec3 aVertexPosition;" +

                            "uniform highp mat4 uNormalMatrix;" +
                            "uniform highp mat4 uMVMatrix;" +
                            "uniform highp mat4 uPMatrix;" +

                            "varying highp vec3 vLighting;" +

                            "void main(void) {" +
                                "gl_Position = uPMatrix * uMVMatrix * vec4(aVertexPosition, 1.0);" +

                                "highp vec3 ambientLight = vec3(0.6, 0.6, 0.6);" +
                                "highp vec3 directionalLightColor = vec3(0.5, 0.5, 0.75);" +
                                "highp vec3 directionalVector = vec3(0.85, 0.8, 0.75);" +

                                "highp vec4 transformedNormal = vec4(aVertexNormal, 1.0);" +

                                "highp float directional = max(dot(transformedNormal.xyz, directionalVector), 0.0);" +
                                "vLighting = ambientLight + (directionalLightColor * directional);" +
                            "}";
    
    initShaders(vertShaderCode, fragShaderCode);
    
    var ActorSet = [];
    var LightSet = [];
    var CameraSet = [];
    var initLess = true;
    var rotation=[0,0,0];
    var scale = [1,1,1];
    var translate = [0,0,0];
    
    // Enums
    this.POINT = gl.POINTS;
    this.POLYGON = gl.LINE_LOOP;
    this.POLYLINE = gl.LINE_STRIP;
	this.LINES = gl.LINES;
	this.TRIANGLE = gl.TRIANGLES;
	this.LINE_LOOP = gl.LINE_LOOP;
}
OGLRen.OGLRenderer.inheritsFrom(OGLRen.Renderer);