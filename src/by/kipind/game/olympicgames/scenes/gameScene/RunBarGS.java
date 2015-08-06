package by.kipind.game.olympicgames.scenes.gameScene;

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
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.util.SAXUtils;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.level.EntityLoader;
import org.andengine.util.level.constants.LevelConstants;
import org.andengine.util.level.simple.SimpleLevelEntityLoaderData;
import org.andengine.util.level.simple.SimpleLevelLoader;
import org.xml.sax.Attributes;

import android.graphics.Color;
import by.kipind.game.olympicgames.ResourcesManager;
import by.kipind.game.olympicgames.SceneManager;
import by.kipind.game.olympicgames.SceneManager.SceneType;
import by.kipind.game.olympicgames.sceneElements.ActionIndicator;
import by.kipind.game.olympicgames.sceneElements.PowerInidcator;
import by.kipind.game.olympicgames.scenes.BaseScene;
import by.kipind.game.olympicgames.sprite.Player;
import by.kipind.game.olympicgames.sprite.Svetofor;
import by.kipind.game.olympicgames.sprite.buttons.AnimBtn;
import by.kipind.game.olympicgames.sprite.buttons.BtnRun;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;

public class RunBarGS extends BaseScene implements IOnSceneTouchListener {
    final String LOG_TAG = "myLogs";

    private static int STEPS_PER_SECOND = 60;
    // ---------
    private static final String TAG_ENTITY = "entity";
    private static final String TAG_ENTITY_ATTRIBUTE_X = "x";
    private static final String TAG_ENTITY_ATTRIBUTE_Y = "y";
    private static final String TAG_ENTITY_ATTRIBUTE_TYPE = "type";

    private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLATFORM2 = "platform2";
    private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_STOP = "stop";
    private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_GROUND = "ground";

    private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLAYER = "player";
    private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_SVETOFOR = "svetofor";

    private static final Object TAG_GAME_ELEMENT_FROM_FILE_METR_20 = "metr_20";
    private static final Object TAG_GAME_ELEMENT_FROM_FILE_METR_40 = "metr_40";
    private static final Object TAG_GAME_ELEMENT_FROM_FILE_METR_60 = "metr_60";
    private static final Object TAG_GAME_ELEMENT_FROM_FILE_METR_80 = "metr_80";
    private static final Object TAG_GAME_ELEMENT_FROM_FILE_METR_LINE = "metr_line";

    // ----------
    private final int lvlWidth = 3200;
    private final int lvlHeight = 450;

    private HUD gameHUD;

    private Sprite hudAreaBorders;
    private Sprite hudTimer;
    
    private BtnRun hudRunRight;
    private BtnRun hudRunJump;
    
    private PowerInidcator hudPowerInidcator;

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

    }

    private void createBackground() {
	attachChild(new Sprite(this.lvlWidth / 2, this.lvlHeight / 2, resourcesManager.gameGraf.get("game_background_region"), vbom));

    }

    private void createHUD() {
	gameHUD = new HUD();
	hudAreaBorders = new Sprite(SCENE_WIDTH / 2, SCENE_HEIGHT / 2, resourcesManager.gameGraf.get("game_hud_borders_region"), vbom);
	hudTimer = new Sprite(0, 0, resourcesManager.gameGraf.get("timer_img"), vbom);
	hudTimer.setPosition(hudTimer.getWidth() / 1.3f, SCENE_HEIGHT - hudTimer.getHeight() / 1.5f);
	hudPowerInidcator = new PowerInidcator(SCENE_WIDTH / 2,  SCENE_HEIGHT / 6, 15f, camera, vbom);

	hudRunRight = new BtnRun(SCENE_WIDTH / 2f, SCENE_HEIGHT / 2f, (ITiledTextureRegion) ResourcesManager.getInstance().gameGraf.get("bt_run_region"), vbom) {
	    @Override
	    public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
		return SceneObjectTouch(this);
	    }
	};
	hudRunRight.setPosition(SCENE_WIDTH - (hudRunRight.getHeight() / 2 + 25), hudRunRight.getWidth() / 2 + 8);

	hudRunJump = new BtnRun(SCENE_WIDTH / 2f, SCENE_HEIGHT / 2f, (ITiledTextureRegion) ResourcesManager.getInstance().gameGraf.get("bt_run_jump_region"), vbom) {
	    @Override
	    public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
		return SceneObjectTouch(this);
	    }
	};
	hudRunJump.setPosition((hudRunJump.getHeight() / 2 + 25), hudRunJump.getWidth() / 2 + 8);

	
	
	
	// CREATE SCORE TEXT
	final Text scoreText = new Text(0, 0, resourcesManager.font, "Time: 0.1234567890", new TextOptions(HorizontalAlign.LEFT), vbom);
	scoreText.setAnchorCenter(0, 0);
	scoreText.setText("0.000");
	scoreText.setPosition(hudTimer.getX() + hudTimer.getWidth() / 2, SCENE_HEIGHT - scoreText.getHeight());
	// scoreText.setSize(scoreText.getWidth(), scoreText.getHeight() / 5);
	// scoreText.setHeight(5);

	scoreText.registerUpdateHandler(new TimerHandler(1 / 230f, true, new ITimerCallback() {
	    private Integer tCounter = 0;

	    @Override
	    public void onTimePassed(final TimerHandler pTimerHandler) {
		if (!player.isFinish() && svetofor.getStatus() == Color.GREEN && !falseStartDisplayed) {
		    tCounter++;
		    scoreText.setText(String.valueOf((double) tCounter / 1000));

		}
	    }
	}));

	gameHUD.registerTouchArea(hudRunRight);
	gameHUD.registerTouchArea(hudRunJump);
	gameHUD.setTouchAreaBindingOnActionDownEnabled(true);

	gameHUD.attachChild(hudAreaBorders);
	gameHUD.attachChild(hudTimer);
	gameHUD.attachChild(hudRunRight);
	gameHUD.attachChild(hudRunJump);
	gameHUD.attachChild(hudPowerInidcator);
	gameHUD.attachChild(scoreText);

	gameHUD.registerUpdateHandler(new TimerHandler(1 / 60f, true, new ITimerCallback() {
	    @Override
	    public void onTimePassed(final TimerHandler pTimerHandler) {
		hudPowerInidcator.changeValue(player.getSpeed()-1);
	    }
	}));

	camera.setHUD(gameHUD);
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

		return RunBarGS.this;
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
		    levelObject = new Sprite(x, y, resourcesManager.gameGraf.get("ge_ai_fon"), vbom);
		    final Body body = PhysicsFactory.createBoxBody(physicsWorld, levelObject, BodyType.StaticBody, FIXTURE_DEF);
		    body.setUserData("platform2");
		    physicsWorld.registerPhysicsConnector(new PhysicsConnector(levelObject, body, true, false));
		} else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_GROUND)) {
		    levelObject = new Sprite(x, y, resourcesManager.gameGraf.get("game_ground_line"), vbom);
		    final Body body = PhysicsFactory.createBoxBody(physicsWorld, levelObject, BodyType.StaticBody, FIXTURE_DEF);
		    body.setUserData("ground");
		    physicsWorld.registerPhysicsConnector(new PhysicsConnector(levelObject, body, true, false));
		} else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_STOP)) {
		    levelObject = new Sprite(x, y, resourcesManager.gameGraf.get("stop_line"), vbom) {
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

			}
		    };
		    levelObject = player;
		} else if (type.equals(TAG_GAME_ELEMENT_FROM_FILE_METR_20)) {
		    levelObject = new Sprite(x, y, resourcesManager.gameGraf.get("metraj_20"), vbom);

		} else if (type.equals(TAG_GAME_ELEMENT_FROM_FILE_METR_40)) {
		    levelObject = new Sprite(x, y, resourcesManager.gameGraf.get("metraj_40"), vbom);

		} else if (type.equals(TAG_GAME_ELEMENT_FROM_FILE_METR_60)) {
		    levelObject = new Sprite(x, y, resourcesManager.gameGraf.get("metraj_60"), vbom);

		} else if (type.equals(TAG_GAME_ELEMENT_FROM_FILE_METR_80)) {
		    levelObject = new Sprite(x, y, resourcesManager.gameGraf.get("metraj_80"), vbom);

		} else if (type.equals(TAG_GAME_ELEMENT_FROM_FILE_METR_LINE)) {
		    levelObject = new Sprite(x, y, resourcesManager.gameGraf.get("metraj_line"), vbom);

		} else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_SVETOFOR)) {
		    svetofor = new Svetofor(SCENE_WIDTH / 2, 5 * SCENE_HEIGHT / 8, vbom, camera, physicsWorld);
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

	levelLoader.loadLevelFromAsset(activity.getAssets(), "level/run100.lvl");
    }

    private boolean SceneObjectTouch(Object touchedObj) {
	boolean res = false;

	if (firstStep && touchedObj.equals(hudRunRight)) {
	    if (!falseStartDisplayed && svetofor.getStatus() != Color.GREEN) {
		svetofor.stopAnimation();
		displayFalseStartText();
	    } else {
		svetofor.setVisible(false);
	    }
	    firstStep = false;
	}

	if (touchedObj.equals(hudRunRight) && hudRunRight.isVisible() && !falseStartDisplayed) {
	    player.powFunctionRun();
	    firstStep = false;
	    res = true;
	}
	
	if (touchedObj.equals(hudRunJump) && hudRunJump.getCurrentTileIndex()!=AnimBtn.BTN_STATE_UNACTIVE && !firstStep) {
	    player.jumpUp();
	    res = true;
	}
	return res;
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

    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
	// TODO Auto-generated method stub
	return false;
    }

}
