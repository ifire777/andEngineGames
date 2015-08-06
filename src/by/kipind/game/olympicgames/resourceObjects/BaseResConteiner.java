package by.kipind.game.olympicgames.resourceObjects;



import java.util.HashMap;
import java.util.Map;

import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;

import by.kipind.game.olympicgames.GameActivity;

public abstract class BaseResConteiner {

    protected Map<String,ITextureRegion>  textureRegionRes;
   
    public GameActivity activity;
    public BuildableBitmapTextureAtlas gameTextureAtlas;
    
    public BaseResConteiner(GameActivity activity) {
	this.activity = activity;
	textureRegionRes=new HashMap<String, ITextureRegion>();
    }

    public Map<String, ITextureRegion> getTextureRegionMap() {
        return textureRegionRes;
    }

   
}
