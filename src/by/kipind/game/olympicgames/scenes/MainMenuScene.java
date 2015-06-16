package by.kipind.game.olympicgames.scenes;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import by.kipind.game.olympicgames.SceneManager;
import by.kipind.game.olympicgames.SceneManager.SceneType;

public class MainMenuScene extends BaseScene implements IOnMenuItemClickListener {

    private MenuScene menuChildScene;
    private final int MENU_PLAY = 0;
    private final int MENU_OPTIONS = 1;

    @Override
    public void createScene() {
	createBackground();
	createMenuChildScene();
    }

    @Override
    public void onBackKeyPressed() {
	System.exit(0);
    }

    @Override
    public SceneType getSceneType() {
	return SceneType.SCENE_MENU;
    }

    @Override
    public void disposeScene() {
	// TODO Auto-generated method stub

    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
	switch (pMenuItem.getID()) {
	case MENU_PLAY:
	    // Load Game Scene!
	    SceneManager.getInstance().loadGameScene(engine);
	    return true;
	case MENU_OPTIONS:
	    return true;
	default:
	    return false;
	}
    }

    private void createBackground() {
	attachChild(new Sprite(SCENE_WIDTH, SCENE_HEIGHT, resourcesManager.menu_background_region, vbom) {
	    @Override
	    protected void preDraw(GLState pGLState, Camera pCamera) {
		super.preDraw(pGLState, pCamera);
		pGLState.enableDither();
	    }
	});
    }

    private void createMenuChildScene() {
	menuChildScene = new MenuScene(camera);
	menuChildScene.setPosition(SCENE_WIDTH, SCENE_HEIGHT);

	final IMenuItem playMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_PLAY, resourcesManager.play_region, vbom), 1.2f, 1);
	final IMenuItem optionsMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_OPTIONS, resourcesManager.options_region, vbom), 1.2f, 1);

	menuChildScene.addMenuItem(playMenuItem);
	menuChildScene.addMenuItem(optionsMenuItem);

	menuChildScene.buildAnimations();
	menuChildScene.setBackgroundEnabled(false);

	playMenuItem.setPosition((SCENE_WIDTH - playMenuItem.getWidth()) / 2, (SCENE_HEIGHT - playMenuItem.getHeight()) / 2);
	optionsMenuItem.setPosition((SCENE_WIDTH - optionsMenuItem.getWidth()) / 2, (SCENE_HEIGHT - optionsMenuItem.getHeight()) / 2 - playMenuItem.getHeight());

	menuChildScene.setOnMenuItemClickListener(this);

	setChildScene(menuChildScene);
    }

}
