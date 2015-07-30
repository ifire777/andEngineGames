package by.kipind.game.olympicgames.sceneElements;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import by.kipind.game.olympicgames.ResourcesManager;

public class ActionInidcation extends HUD {
    // ===========================================================
    // Constants
    // ===========================================================

    private final float RUNNER_FPS = 100f;

    // ===========================================================
    // Fields
    // ===========================================================

    private Sprite mIndicFon;
    private Sprite mIndicRedArea;
    private Sprite mIndRunner;

    private float mRunnerValueX, mRunnerValueY;

    private float mRedXAprox, mRedHalfWightAprox;

   // private float mBgValueX, mBgValueWight;
    private float mIndStartX, mIndEndX, mIndHalfWight;

    private Float mIndicVal = 0f, mIndicShag = 0.005f;

    // ===========================================================
    // Constructors
    // ===========================================================
    public ActionInidcation(final float pX, final float pY, final float iWKaf, final float iHKaf, final Camera pCamera, VertexBufferObjectManager vbo) {
	this.setCamera(pCamera);

	this.mIndicFon = new Sprite(pX, pY, ResourcesManager.getInstance().ge_ai_fon, vbo);
	this.mIndicRedArea = new Sprite(pX, pY, ResourcesManager.getInstance().ge_ai_red, vbo);
	this.mIndRunner = new Sprite(pX, pY, ResourcesManager.getInstance().ge_ai_runner, vbo);

	setIndWH(iWKaf, iHKaf);
	setRedAreaWH(iWKaf, iHKaf);
	
	this.registerUpdateHandler(new TimerHandler(1 / RUNNER_FPS, true, new ITimerCallback() {
	    @Override
	    public void onTimePassed(final TimerHandler pTimerHandler) {
		ActionInidcation.this.onMoveAction();
	    }
	}));

	this.attachChild(this.mIndicFon);
	this.attachChild(this.mIndicRedArea);
	this.attachChild(this.mIndRunner);

	mIndHalfWight = this.mIndicFon.getWidth() / 2;
	mIndStartX = this.mIndicFon.getX() - mIndHalfWight;
	mIndEndX = this.mIndicFon.getX() + mIndHalfWight;

	mIndicShag = (2 * mIndHalfWight) / (RUNNER_FPS * 5);

	mIndicVal = this.mIndicFon.getX();
	
    }

    // ===========================================================
    // Methods
    // ===========================================================

    private void setIndWH(float iWigthKaf, float iHeightKaf) {
	this.mIndicFon.setWidth(this.mIndicFon.getWidth() * iWigthKaf);
	this.mIndicFon.setHeight(this.mIndicFon.getHeight() * iHeightKaf);
    }

    private void setRedAreaWH(float iWigthKaf, float iHeightKaf) {
	this.mIndicRedArea.setWidth(this.mIndicRedArea.getWidth() * iWigthKaf);
	this.mIndicRedArea.setHeight(this.mIndicRedArea.getHeight() * iHeightKaf);
    }

    private void onMoveAction() {
	mIndicVal = mIndicVal + mIndicShag;
	if (mIndicVal > mIndEndX) {
	    mIndicVal = mIndEndX;
	    mIndicShag *= -1;
	} else if (mIndicVal < mIndStartX) {
	    mIndicVal = mIndStartX;
	    mIndicShag *= -1;
	}
	this.mIndRunner.setPosition(mIndicVal, mIndRunner.getY());
   }

    // ===========================================================
    // Getters & Setters
    // ===========================================================

    public Float getmIndicShag() {
	return mIndicShag;
    }

    public void setmIndicShag(Float mIndicShag) {
	this.mIndicShag = mIndicShag;
    }

    public Float getmIndicVal() {
	return mIndicVal;
    }

}
