package by.kipind.game.olympicgames;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import by.kipind.game.olympicgames.scenes.BaseScene;
import by.kipind.game.olympicgames.scenes.LoadingScene;
import by.kipind.game.olympicgames.scenes.MainMenuScene;
import by.kipind.game.olympicgames.scenes.SplashScene;
import by.kipind.game.olympicgames.scenes.gameScene.Run100GS;
import by.kipind.game.olympicgames.scenes.gameScene.RunBarGS;

public class SceneManager {
    // ---------------------------------------------
    // SCENES
    // ---------------------------------------------

    private BaseScene splashScene;
    private BaseScene menuScene;
    private BaseScene loadingScene;
    private BaseScene gameScene;

    // ---------------------------------------------
    // VARIABLES
    // ---------------------------------------------

    private static final SceneManager INSTANCE = new SceneManager();

    private SceneType currentSceneType = SceneType.SCENE_SPLASH;

    private BaseScene currentScene;

    private Engine engine = ResourcesManager.getInstance().engine;

    public enum SceneType {
	SCENE_SPLASH, SCENE_MENU, SCENE_GAME, SCENE_LOADING,
    }

    // ---------------------------------------------
    // CLASS LOGIC
    // ---------------------------------------------

    public void setScene(BaseScene scene) {
	engine.setScene(scene);
	currentScene = scene;
	currentSceneType = scene.getSceneType();
    }

    public void setScene(SceneType sceneType) {
	switch (sceneType) {
	case SCENE_MENU:
	    setScene(menuScene);
	    break;
	case SCENE_GAME:
	    setScene(gameScene);
	    break;
	case SCENE_SPLASH:
	    setScene(splashScene);
	    break;
	case SCENE_LOADING:
	    setScene(loadingScene);
	    break;
	default:
	    break;
	}
    }

    // ---------------------------------------------
    // GETTERS AND SETTERS
    // ---------------------------------------------

    public static SceneManager getInstance() {
	return INSTANCE;
    }

    public SceneType getCurrentSceneType() {
	return currentSceneType;
    }

    public BaseScene getCurrentScene() {
	return currentScene;
    }

    public void createSplashScene(OnCreateSceneCallback pOnCreateSceneCallback) {
	ResourcesManager.getInstance().loadSplashScreen();
	splashScene = new SplashScene();
	currentScene = splashScene;
	pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
    }

    private void disposeSplashScene() {
	ResourcesManager.getInstance().unloadSplashScreen();
	splashScene.disposeScene();
	splashScene = null;
    }

    public void createMenuScene() {
	ResourcesManager.getInstance().loadMenuResources();
	menuScene = new MainMenuScene();
	loadingScene = new LoadingScene();
	SceneManager.getInstance().setScene(menuScene);

	disposeSplashScene();
    }

    public void loadGameScene(final Engine mEngine, final int gameNum) {
	setScene(loadingScene);
	ResourcesManager.getInstance().unloadMenuTextures();
	mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
	    public void onTimePassed(final TimerHandler pTimerHandler) {
		mEngine.unregisterUpdateHandler(pTimerHandler);
		
		ResourcesManager  rm=ResourcesManager.getInstance();
		rm.loadGameResources(gameNum);
		
		switch (gameNum) {
		case  1:gameScene = new Run100GS();
		    break;
		case  2:gameScene = new RunBarGS();
		    break;

		default:
		    break;
		}
		setScene(gameScene);
	    }
	}));
    }
    
    public void loadMenuScene(final Engine mEngine)
    {
        setScene(loadingScene);
        gameScene.disposeScene();
        ResourcesManager.getInstance().unloadGameTextures();
        mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() 
        {
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadMenuTextures();
                setScene(menuScene);
            }
        }));
    }

}