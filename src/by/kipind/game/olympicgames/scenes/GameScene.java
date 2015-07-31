package by.kipind.game.olympicgames.scenes;

import java.io.IOException;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.SAXUtils;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.level.EntityLoader;
import org.andengine.util.level.constants.LevelConstants;
import org.andengine.util.level.simple.SimpleLevelEntityLoaderData;
import org.andengine.util.level.simple.SimpleLevelLoader;
import org.xml.sax.Attributes;

import android.graphics.Color;
import android.util.Log;
import by.kipind.game.olympicgames.SceneManager;
import by.kipind.game.olympicgames.SceneManager.SceneType;
import by.kipind.game.olympicgames.sceneElements.ActionInidcation;
import by.kipind.game.olympicgames.sprite.Player;
import by.kipind.game.olympicgames.sprite.Svetofor;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;

public class GameScene extends BaseScene implements IOnSceneTouchListener {
    final String LOG_TAG = "myLogs";

    private static int STEPS_PER_SECOND = 60;
    // ---------
    private static final String TAG_ENTITY = "entity";
    private static final String TAG_ENTITY_ATTRIBUTE_X = "x";
    private static final String TAG_ENTITY_ATTRIBUTE_Y = "y";
    private static final String TAG_ENTITY_ATTRIBUTE_TYPE = "type";

    // private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_timer =
    // "platform1";
    private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLATFORM2 = "platform2";
    private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_STOP = "stop";
    private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_GROUND = "ground";

    private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLAYER = "player";
    private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_SVETOFOR = "svetofor";
    // ----------
    private static int lvlWidth = 3200;
    private static int lvlHeight = 450;

    private HUD gameHUD;
    private ActionInidcation aiRun;

    private Sprite hudAreaBorders;
    private Sprite hudTimer;
    // private Sprite hudRunLeft;
    private Sprite hudRunRight;

    private PhysicsWorld physicsWorld;

    private Player player;
    private Svetofor svetofor;
    private Text gameOverText;
    private boolean falseStartDisplayed = false;
    private boolean firstStep = true;

    @Override
    public void createScene() {
	createBackground();
	createHUD();
	createPhysics();
	loadLevel(1);
	createGameOverText();
	setOnSceneTouchListener(this);

    }

    @Override
    public void onBackKeyPressed() {
	SceneManager.getInstance().loadMenuScene(engine);
    }

    @Override
    public SceneType getSceneType() {
	return SceneType.SCENE_GAME;
    }

    @Override
    public void disposeScene() {
	camera.setHUD(null);
	camera.setCenter(SCENE_WIDTH / 2, SCENE_HEIGHT / 2);
	camera.setChaseEntity(null);
	// TODO code responsible for disposing scene
	// removing all game scene objects.
    }

    private void createBackground() {
	// setBackground(new SpriteBackground(new Sprite(SCENE_WIDTH,
	// SCENE_HEIGHT, resourcesManager.game_background_region, vbom)));
	attachChild(new Sprite(GameScene.lvlWidth / 2, GameScene.lvlHeight / 2, resourcesManager.game_background_region, vbom));

    }

    private void createHUD() {
	gameHUD = new HUD();
	hudAreaBorders = new Sprite(SCENE_WIDTH / 2, SCENE_HEIGHT / 2, resourcesManager.game_hud_borders_region, vbom);
	hudTimer = new Sprite(0, 0, resourcesManager.timer_img, vbom);
	hudTimer.setPosition(hudTimer.getWidth() / 1.3f, SCENE_HEIGHT - hudTimer.getHeight() / 1.5f);

	/*
	 * hudRunLeft = new Sprite(SCENE_WIDTH / 2, SCENE_HEIGHT / 2,
	 * resourcesManager.game_hud_run_left, vbom) {
	 * 
	 * @Override public boolean onAreaTouched(final TouchEvent
	 * pSceneTouchEvent, final float pTouchAreaLocalX, final float
	 * pTouchAreaLocalY) { return SceneObjectTouch(this); } };
	 */
	hudRunRight = new Sprite(SCENE_WIDTH / 2, SCENE_HEIGHT / 2, resourcesManager.game_hud_run_right, vbom) {
	    @Override
	    public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		return SceneObjectTouch(this);
	    }
	};
	// hudRunLeft.setPosition(hudRunLeft.getHeight() / 2 + 25,
	// hudRunLeft.getWidth() / 2 + 8);
	hudRunRight.setPosition(SCENE_WIDTH - (hudRunRight.getHeight() / 2 + 25), hudRunRight.getWidth() / 2 + 8);

	aiRun = new ActionInidcation(0f, 0f, 0.5f, 1f, 0, camera, vbom);
	aiRun.setPosition(SCENE_WIDTH / 2, SCENE_HEIGHT / 8);

	// CREATE SCORE TEXT
	final Text scoreText = new Text(0, 0, resourcesManager.font, "Time: 0.1234567890", new TextOptions(HorizontalAlign.LEFT), vbom);
	scoreText.setAnchorCenter(0, 0);
	scoreText.setText("0.000");
	scoreText.setPosition(hudTimer.getX() + hudTimer.getWidth() / 2, SCENE_HEIGHT - scoreText.getHeight());
	scoreText.setSize(scoreText.getWidth(), scoreText.getHeight() / 2);

	// gameHUD.registerTouchArea(hudRunLeft);
	gameHUD.registerTouchArea(hudRunRight);
	gameHUD.setTouchAreaBindingOnActionDownEnabled(true);

	gameHUD.attachChild(hudAreaBorders);
	gameHUD.attachChild(hudTimer);
	gameHUD.attachChild(hudRunRight);
	// gameHUD.attachChild(hudRunLeft);
	gameHUD.attachChild(aiRun);
	gameHUD.attachChild(scoreText);

	gameHUD.registerUpdateHandler(new TimerHandler(1 / 230f, true, new ITimerCallback() {
	    private Integer tCounter = 0;

	    @Override
	    public void onTimePassed(final TimerHandler pTimerHandler) {
		if (!player.isFinish() && !firstStep && !falseStartDisplayed) {
		    tCounter++;
		    scoreText.setText(String.valueOf((double) tCounter / 1000));

		    aiRun.setRedAreaWH( (float) (1/Math.sqrt(player.getSpeed())), 1);
		    // Log.d(LOG_TAG, tCounter + "    sum---->" +
		    // String.valueOf(tCounter / 1000d));

		}
	    }
	}));

	camera.setHUD(gameHUD);
    }

    private void addToScore(int i) {
	// this.score = i;
	// this.scoreText.setText("Score: " + score);
    }

    private void createPhysics() {
	physicsWorld = new FixedStepPhysicsWorld(STEPS_PER_SECOND, new Vector2(0, -9), false);
	physicsWorld.setContactListener(contactListener());
	registerUpdateHandler(physicsWorld);
    }

    private void loadLevel(int levelID) {
	final SimpleLevelLoader levelLoader = new SimpleLevelLoader(vbom);
	final FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.01f, 0.5f);

	levelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(LevelConstants.TAG_LEVEL) {
	    public IEntity onLoadEntity(final String pEntityName, final IEntity pParent, final Attributes pAttributes,
		    final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData) throws IOException {
		final int width = SAXUtils.getIntAttributeOrThrow(pAttributes, LevelConstants.TAG_LEVEL_ATTRIBUTE_WIDTH);
		final int height = SAXUtils.getIntAttributeOrThrow(pAttributes, LevelConstants.TAG_LEVEL_ATTRIBUTE_HEIGHT);

		camera.setBounds(0, 0, width, height);
		camera.setBoundsEnabled(true);

		return GameScene.this;
	    }
	});

	levelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(TAG_ENTITY) {
	    public IEntity onLoadEntity(final String pEntityName, final IEntity pParent, final Attributes pAttributes,
		    final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData) throws IOException {
		final int x = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_X);
		final int y = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_Y);
		final String type = SAXUtils.getAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_TYPE);

		final Sprite levelObject;

		if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLATFORM2)) {
		    levelObject = new Sprite(x, y, resourcesManager.ge_ai_fon, vbom);
		    final Body body = PhysicsFactory.createBoxBody(physicsWorld, levelObject, BodyType.StaticBody, FIXTURE_DEF);
		    body.setUserData("platform2");
		    physicsWorld.registerPhysicsConnector(new PhysicsConnector(levelObject, body, true, false));
		} else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_GROUND)) {
		    levelObject = new Sprite(x, y, resourcesManager.game_ground_line, vbom);
		    final Body body = PhysicsFactory.createBoxBody(physicsWorld, levelObject, BodyType.StaticBody, FIXTURE_DEF);
		    body.setUserData("ground");
		    physicsWorld.registerPhysicsConnector(new PhysicsConnector(levelObject, body, true, false));
		} else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_STOP)) {
		    levelObject = new Sprite(x, y, resourcesManager.stop_line, vbom) {
			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
			    super.onManagedUpdate(pSecondsElapsed);

			    if (player.collidesWith(this)) {
				this.setIgnoreUpdate(true);
				player.onFinish();
			    }
			}
		    };
		} else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLAYER)) {
		    player = new Player(x, y, vbom, camera, physicsWorld) {
			public void onFinish() {
			    super.onFinish();
			    stopAnimation(0);
			    body.setLinearVelocity(0, 0);
			    body.applyLinearImpulse(4, 0, body.getPosition().x, body.getPosition().y);

			    if (!falseStartDisplayed) {
				displayFalseStartText();
			    }
			}
		    };
		    levelObject = player;
		} else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_SVETOFOR)) {
		    svetofor = new Svetofor(x, y, vbom, camera, physicsWorld);
		    levelObject = svetofor;
		    svetofor.Start();

		}

		else {
		    throw new IllegalArgumentException();
		}

		levelObject.setCullingEnabled(true);

		return levelObject;
	    }
	});

	levelLoader.loadLevelFromAsset(activity.getAssets(), "level/" + levelID + ".lvl");
    }

    private boolean SceneObjectTouch(Object touchedObj) {
	boolean res = false;

	if (firstStep && touchedObj.equals(hudRunRight) && svetofor.getStatus() != Color.GREEN) {
	    if (!falseStartDisplayed) {
		displayFalseStartText();
	    }
	    firstStep = false;

	}
	if (touchedObj.equals(hudRunRight) && hudRunRight.isVisible() && !falseStartDisplayed) {
	    	if (aiRun.getResult() == 0 && aiRun.getBorderReachFlag() != 0) {
			player.run();
			firstStep = false;
			res = true;
		    }
	/*if (aiRun.getBorderReachFlag() == -1) {
		aiRun.inverMoveDerection();
		
	    }*/
	    aiRun.setBorderReachFlag(0);
	   

	} /*
	   * else if (touchedObj.equals(hudRunRight) && hudRunRight.isVisible()
	   * && !falseStartDisplayed) { if (aiRun.getResult() == 0 &&
	   * aiRun.getBorderReachFlag() != 0) { player.run(); //
	   * hudRunLeft.setVisible(true); // hudRunRight.setVisible(false);
	   * aiRun.inverMoveDerection(); aiRun.setBorderReachFlag(0); firstStep
	   * = false; res = true; }
	   * 
	   * }
	   */
	return res;
    }

    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {

	/*
	 * if (pSceneTouchEvent.isActionDown()) { if (!firstTouch) {
	 * player.setRunning(); firstTouch = true; } else { player.run(); } }
	 */
	return false;
    }

    private ContactListener contactListener() {
	ContactListener contactListener = new ContactListener() {
	    public void beginContact(Contact contact) {
		final Fixture x1 = contact.getFixtureA();
		final Fixture x2 = contact.getFixtureB();

		if (x1.getBody().getUserData() != null && x2.getBody().getUserData() != null) {
		    if (x2.getBody().getUserData().equals("player")) {
			player.increaseFootContacts();
		    }
		}
	    }

	    public void endContact(Contact contact) {
		final Fixture x1 = contact.getFixtureA();
		final Fixture x2 = contact.getFixtureB();

		if (x1.getBody().getUserData() != null && x2.getBody().getUserData() != null) {
		    if (x2.getBody().getUserData().equals("player")) {
			player.decreaseFootContacts();
		    }
		}
	    }

	    public void preSolve(Contact contact, Manifold oldManifold) {

	    }

	    public void postSolve(Contact contact, ContactImpulse impulse) {

	    }
	};
	return contactListener;

    }

    private void createGameOverText() {
	gameOverText = new Text(0, 0, resourcesManager.font, "фальш старт!", vbom);
    }

    private void displayFalseStartText() {
	camera.setChaseEntity(null);
	gameOverText.setPosition(camera.getCenterX(), camera.getCenterY());
	attachChild(gameOverText);
	falseStartDisplayed = true;
    }

}
