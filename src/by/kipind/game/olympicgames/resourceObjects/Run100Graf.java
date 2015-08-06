package by.kipind.game.olympicgames.resourceObjects;

import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.util.debug.Debug;

import by.kipind.game.olympicgames.GameActivity;

public class Run100Graf  extends BaseResConteiner{
    // Game Texture
   
    public Run100Graf(GameActivity activity) {

	super(activity);
	loadRun100Graphics();
    }

    private void loadRun100Graphics() {

	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
	gameTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 4096, 4096, TextureOptions.BILINEAR);

	textureRegionRes.put("game_ground_line", BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game/ground_line2.png"));
	textureRegionRes.put("game_hud_borders_region", BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game/hud_borders.png"));

	textureRegionRes.put("game_background_region", BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game/running/fon_beg.jpg"));
	textureRegionRes.put("stop_line", BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game/running/finish_line.png"));
	textureRegionRes.put("metraj_20", BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game/running/metraj_20.png"));
	textureRegionRes.put("metraj_40", BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game/running/metraj_40.png"));
	textureRegionRes.put("metraj_60", BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game/running/metraj_60.png"));
	textureRegionRes.put("metraj_80", BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game/running/metraj_80.png"));
	textureRegionRes.put("metraj_line", BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game/running/metraj_line.png"));

	textureRegionRes.put("timer_img", BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "sprites/run_timer.png"));

	textureRegionRes.put("ge_pi_fon", BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "sprites/power_identific/pi_fon.png"));
	textureRegionRes.put("ge_pi_red", BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "sprites/power_identific/pi_red.png"));
	textureRegionRes.put("ge_pi_skin", BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "sprites/power_identific/pi_form.png"));
	// --Tiled
	textureRegionRes.put("player_region", BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "sprites/atlas_beg.png", 5, 2));
	textureRegionRes.put("svetofor_region", BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "sprites/atlas_svetfor.png", 5, 1));

	textureRegionRes.put("bt_run_region", BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "btn/atlas_bt_run.png", 3, 1));

	
	/*game_ground_line = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game/ground_line2.png");
	game_hud_borders_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game/hud_borders.png");
	
	game_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game/running/fon_beg.jpg");
	stop_line = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game/running/finish_line.png");
        metraj_20 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game/running/metraj_20.png");
	metraj_40 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game/running/metraj_40.png");
	metraj_60 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game/running/metraj_60.png");
	metraj_80 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game/running/metraj_80.png");
	metraj_line = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "game/running/metraj_line.png");

	timer_img = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "sprites/run_timer.png");
		
	ge_pi_fon = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "sprites/power_identific/pi_fon.png");
	ge_pi_red = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "sprites/power_identific/pi_red.png");
	ge_pi_skin = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "sprites/power_identific/pi_form.png");
//--Tiled
	player_region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "sprites/atlas_beg.png", 5, 2);
	svetofor_region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "sprites/atlas_svetfor.png", 5, 1);
	
	bt_run_region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity, "btn/atlas_bt_run.png", 3, 1);
*/
	try {
	    this.gameTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
	    this.gameTextureAtlas.load();
	} catch (final TextureAtlasBuilderException e) {
	    Debug.e(e);
	}

    }
}
