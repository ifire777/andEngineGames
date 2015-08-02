package by.kipind.game.olympicgames;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
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
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

import android.graphics.Color;

public class ResourcesManager {
    // ---------------------------------------------
    // VARIABLES
    // ---------------------------------------------

    private static final ResourcesManager INSTANCE = new ResourcesManager();

    public Engine engine;
    public GameActivity activity;
    public BoundCamera  camera;
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

    // Game Texture
    public BuildableBitmapTextureAtlas gameTextureAtlas;

    // Game Texture Regions
    public ITextureRegion game_background_region;
    public ITextureRegion game_ground_line;
    public ITextureRegion game_hud_borders_region;
    public ITextureRegion game_hud_run_left;
    public ITextureRegion game_hud_run_right;
    public ITextureRegion timer_img;
    public ITextureRegion ge_ai_fon;
    public ITextureRegion ge_ai_red;
    public ITextureRegion ge_ai_runner_on;
    public ITextureRegion ge_ai_runner_off;
    
    public ITextureRegion ge_pi_fon;
    public ITextureRegion ge_pi_red;
    public ITextureRegion ge_pi_skin;
    
    
    public ITextureRegion stop_line;
    
    public ITextureRegion metraj_20;
    public ITextureRegion metraj_40;
    public ITextureRegion metraj_60;
    public ITextureRegion metraj_80;
    public ITextureRegion metraj_line;
    
    
    public ITiledTextureRegion player_region;
    public ITiledTextureRegion svetofor_region;
    public ITiledTextureRegion bt_run_region;
    // ---------------------------------------------
    // CLASS LOGIC
    // ---------------------------------------------

    public void loadMenuResources() {
	loadMenuGraphics();
	loadMenuAudio();
	loadMenuFonts();
    }

    public void loadGameResources() {
	loadGameGraphics();
	loadGameFonts();
	loadGameAudio();
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
	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");
	    gameTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 4096, 4096, TextureOptions.BILINEAR);
	    
	    
	    game_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "fon_beg.jpg");
	    game_ground_line = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "ground_line2.png");
	    game_hud_borders_region= BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "hud_borders.png");
	    game_hud_run_left= BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "bt_l_leg.png");
	    game_hud_run_right= BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "bt_r_leg.png");
	    
	    timer_img = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "run_timer.png");
	    stop_line = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "finish_line.png");
	    
	    metraj_20 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "metraj_20.png");
	    metraj_40 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "metraj_40.png");
	    metraj_60 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "metraj_60.png");
	    metraj_80 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "metraj_80.png");
	    metraj_line = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "metraj_line.png");
	    
	    ge_ai_fon = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "indik_bg.png");
	    ge_ai_red = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "indik_red.png");
	    ge_ai_runner_on = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "indik_begun_on.png");
	    ge_ai_runner_off = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "indik_begun_off.png");
	    
	    ge_pi_fon = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "pi_fon.png");
	    ge_pi_red = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "pi_red.png");
	    ge_pi_skin = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "pi_form.png");
		    
	     
	    player_region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "atlas_beg.png", 5, 2);
	    svetofor_region= BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "atlas_svetfor.png", 5, 1);
	    bt_run_region= BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "atlas_bt_run.png", 3, 1);
	    
	    
	    try 
	    {
	        this.gameTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
	        this.gameTextureAtlas.load();
	    } 
	    catch (final TextureAtlasBuilderException e)
	    {
	        Debug.e(e);
	    }
	    
	    
	    
    }

    private void loadGameFonts() {
	
	

    }

    private void loadGameAudio() {

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
