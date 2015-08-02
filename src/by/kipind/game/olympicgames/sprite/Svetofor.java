package by.kipind.game.olympicgames.sprite;

import java.util.Arrays;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.graphics.Color;
import android.util.Log;
import by.kipind.game.olympicgames.ResourcesManager;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Svetofor extends AnimatedSprite {
    final String LOG_TAG = "myLogs";

    int[] ligtStatus = new int[] { -1, Color.RED, Color.YELLOW, Color.GREEN };

    // ---------------------------------------------
    // VARIABLES
    // ---------------------------------------------

    public Body body;
    private boolean start = false;
    private int status = -1;

    public int frameDuration = 700;

    private final int[] animFrame = new int[] { 0, 1, 2, 3, 4 };

    // ---------------------------------------------
    // CONSTRUCTOR
    // ---------------------------------------------

    public Svetofor(float pX, float pY, VertexBufferObjectManager vbo, Camera camera, PhysicsWorld physicsWorld) {
	super(pX, pY, ResourcesManager.getInstance().svetofor_region, vbo);
	createPhysics(camera, physicsWorld);
	
	//this.setWidth(this.getWidth()*4);
	//this.setHeight(this.getHeight()*4);
	
    }

    // ---------------------------------------------
    // CLASS LOGIC
    // ---------------------------------------------

    private void createPhysics(final Camera camera, PhysicsWorld physicsWorld) {
	body = PhysicsFactory.createBoxBody(physicsWorld, this, BodyType.StaticBody, PhysicsFactory.createFixtureDef(0, 0, 0));

	body.setUserData("svetofor");

	physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body, true, false));

	stopAnimation(0);
    }

    public void Start() {
	if (!start) {
	    this.start = true;
	    long[] spriteFameDuration = new long[animFrame.length];
	    Arrays.fill(spriteFameDuration, frameDuration);
	    animate(spriteFameDuration, animFrame, false);
	}
    }

    public int getStatus() {
	int res = -1;
	switch (getCurrentTileIndex()) {
	case 0:
	    res = -1;
	    break;
	case 1:
	    res = Color.RED;
	    break;
	case 2:
	    res = Color.RED;
	    break;
	case 3:
	    res = Color.YELLOW;
	    break;
	case 4:
	    res = Color.GREEN;

	    break;

	default:
	    break;
	}
	return res;
    }

}
