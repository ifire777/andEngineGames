package by.kipind.game.olympicgames;

import java.util.Map;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

import android.graphics.Color;
import by.kipind.game.olympicgames.resourceObjects.BaseResConteiner;
import by.kipind.game.olympicgames.resourceObjects.Run100Graf;
import by.kipind.game.olympicgames.resourceObjects.RunBarGraf;

public class ResourcesManager {
    // ---------------------------------------------
    // VARIABLES
    // ---------------------------------------------
    static final int GAME_ID_RUN100 = 1;
    static final int GAME_ID_RUN_BARIER = 2;
    static final int GAME_ID_LONG_JUMP = 3;
    static final int GAME_ID_TRIPL_JUMP = 4;
    static final int GAME_ID_SHOOTING = 5;
    static final int GAME_ID_BOW_SHOOTING = 6;
    static final int GAME_ID_BOW_SPEAR = 6;

    private static final ResourcesManager INSTANCE = new ResourcesManager();

    public Engine engine;
    public GameActivity activity;
    public BoundCamera camera;
    public VertexBufferObjectManager vbom;

    public Font font;
    // ---------------------------------------------
    // TEXTURES & TEXTURE REGIONS
    // ---------------------------------------------
    public ITextureRegion splash_region;
    private BitmapTextureAtlas splashTextureAtlas;

    public ITextureRegion menu_background_region;
    public ITextureRegion play_region;
    public ITextureRegion options_region;
    private BuildableBitmapTextureAtlas menuTextureAtlas;
    public Map<String, ITextureRegion> gameGraf;

    // ---------------------------------------------
    // CLASS LOGIC
    // ---------------------------------------------

    public void loadMenuResources() {
	loadMenuGraphics();
	loadMenuAudio();
	loadMenuFonts();
    }

    public void loadGameResources(int gameId) {
	switch (gameId) {
	case GAME_ID_RUN100:
	    gameGraf = new Run100Graf(activity).getTextureRegionMap();

	    loadRun100Audio();
	    break;
	case GAME_ID_RUN_BARIER:
	    gameGraf = new RunBarGraf(activity).getTextureRegionMap();
	    loadRun100Audio();
	    break;
	case GAME_ID_LONG_JUMP:

	    break;

	default:
	    break;
	}
	loadGameFonts();

    }

    private void loadMenuGraphics() {
	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
	menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	menu_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "menu_background.png");
	play_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "play.png");
	options_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "options.png");

	try {
	    this.menuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
	    this.menuTextureAtlas.load();
	} catch (final TextureAtlasBuilderException e) {
	    Debug.e(e);
	}
    }

    private void loadMenuFonts() {
	FontFactory.setAssetBasePath("font/");
	final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	font = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "chat_noir.ttf", 50, true, Color.WHITE, 2, Color.BLACK);
	font.load();
    }

    private void loadMenuAudio() {

    }

    private void loadGameGraphics() {
    }

    private void loadGameFonts() {

    }

    private void loadRun100Audio() {

    }

    public void loadSplashScreen() {
	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
	splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);

	splash_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "splash.jpg", 0, 0);
	splashTextureAtlas.load();
    }

    public void unloadSplashScreen() {
	splashTextureAtlas.unload();
	splash_region = null;
    }

    public void unloadMenuTextures() {
	menuTextureAtlas.unload();
    }

    public void loadMenuTextures() {
	menuTextureAtlas.load();
    }

    public void unloadGameTextures() {
	// TODO (Since we did not create any textures for game scene yet)
    }

    /**
     * @param engine
     * @param activity
     * @param camera
     * @param vbom
     * <br>
     * <br>
     *            We use this method at beginning of game loading, to prepare
     *            Resources Manager properly, setting all needed parameters, so
     *            we can latter access them from different classes (eg. scenes)
     */
    public static void prepareManager(Engine engine, GameActivity activity, BoundCamera camera, VertexBufferObjectManager vbom) {
	getInstance().engine = engine;
	getInstance().activity = activity;
	getInstance().camera = camera;
	getInstance().vbom = vbom;
    }

    // ---------------------------------------------
    // GETTERS AND SETTERS
    // ---------------------------------------------

    public static ResourcesManager getInstance() {
	return INSTANCE;
    }
}
