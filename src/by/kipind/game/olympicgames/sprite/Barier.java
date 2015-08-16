package by.kipind.game.olympicgames.sprite;

import java.util.Arrays;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import by.kipind.game.olympicgames.ResourcesManager;

import com.badlogic.gdx.physics.box2d.Body;

public class Barier extends AnimatedSprite {
    final String LOG_TAG = "myLogs";

    // int[] ligtStatus = new int[] { -1, Color.RED, Color.YELLOW, Color.GREEN
    // };

    // ---------------------------------------------
    // VARIABLES
    // ---------------------------------------------

    public Body body;
    // private boolean start = false;
    // private int status = -1;

    public int frameDuration = 100;

    private final int[] animFrame = new int[] { 0, 1, 2 };

    // ---------------------------------------------
    // CONSTRUCTOR
    // ---------------------------------------------

    public Barier(float pX, float pY, VertexBufferObjectManager vbo, Camera camera, PhysicsWorld physicsWorld) {
	super(pX, pY, (ITiledTextureRegion) ResourcesManager.getInstance().gameGraf.get("barier_region"), vbo);
	// createPhysics(camera, physicsWorld);

	// this.setWidth(this.getWidth()*4);
	// this.setHeight(this.getHeight()*4);

    }

    // ---------------------------------------------
    // CLASS LOGIC
    // ---------------------------------------------

    /*
     * private void createPhysics(final Camera camera, PhysicsWorld
     * physicsWorld) { body = PhysicsFactory.createBoxBody(physicsWorld, this,
     * BodyType.StaticBody, PhysicsFactory.createFixtureDef(0, 0, 0));
     * 
     * body.setUserData("svetofor");
     * 
     * physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body,
     * true, false));
     * 
     * stopAnimation(0); }
     */

    public void toFall() {
	if (getCurrentTileIndex() == 0) {
	    long[] spriteFameDuration = new long[animFrame.length];
	    Arrays.fill(spriteFameDuration, frameDuration);
	    animate(spriteFameDuration, animFrame, false);
	}
    }

}
