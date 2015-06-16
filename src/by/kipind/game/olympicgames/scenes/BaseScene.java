package by.kipind.game.olympicgames.scenes;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.app.Activity;
import by.kipind.game.olympicgames.ResourcesManager;
import by.kipind.game.olympicgames.SceneManager.SceneType;

public abstract class BaseScene extends Scene {
    protected static Integer SCENE_WIDTH=400;
    protected static Integer SCENE_HEIGHT=240;
   
    
    // ---------------------------------------------
    // VARIABLES
    // ---------------------------------------------

    protected Engine engine;
    protected Activity activity;
    protected ResourcesManager resourcesManager;
    protected VertexBufferObjectManager vbom;
    protected BoundCamera camera;

    // ---------------------------------------------
    // CONSTRUCTOR
    // ---------------------------------------------

    public BaseScene() {
	this.resourcesManager = ResourcesManager.getInstance();
	this.engine = resourcesManager.engine;
	this.activity = resourcesManager.activity;
	this.vbom = resourcesManager.vbom;
	this.camera = resourcesManager.camera;
	createScene();
    }

    // ---------------------------------------------
    // ABSTRACTION
    // ---------------------------------------------

    public abstract void createScene();

    public abstract void onBackKeyPressed();

    public abstract SceneType getSceneType();

    public abstract void disposeScene();
}
