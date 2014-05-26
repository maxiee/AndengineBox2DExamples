package com.maxiee.andenginebox2dexamples;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

public class WheelActivity extends Box2DInitActivity {
	private Body wheelBody;
	private Sprite wheelFace;
	private BitmapTextureAtlas carAtlas;
	private TextureRegion wheelRegion;
	private RevoluteJoint mWheelRev;
	
	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback) {
		super.onCreateResources(pOnCreateResourcesCallback);
		carAtlas = new BitmapTextureAtlas(getTextureManager(), 512, 512);
		wheelRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				carAtlas, 
				this, 
				"gfx/chrome_wheel.png", 
				0, 0);
		carAtlas.load();
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) {
		super.onPopulateScene(pScene, pOnPopulateSceneCallback);
		
		wheelFace = new Sprite(
				(cameraWidth / 2), 
				(cameraHeight / 2),
				wheelRegion, super.getVertexBufferObjectManager());
		mScene.attachChild(wheelFace);

		
		wheelBody = PhysicsFactory.createCircleBody(
				mPhysicsWorld, 
				wheelFace, 
				BodyType.DynamicBody, 
				ONLY_WALL_FIXTURE_DEF);
		
		Sprite axleFace = new Sprite(
				(cameraWidth / 2), 
				(cameraHeight / 2),
				wheelRegion, super.getVertexBufferObjectManager());
		axleFace.setScale((float) 0.5);
		axleFace.setVisible(false);
		
		Body axleBody = PhysicsFactory.createCircleBody(mPhysicsWorld,
				axleFace, BodyType.DynamicBody, NO_FIXTURE_DEF);
		
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(axleFace, axleBody, true, true));
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(wheelFace, wheelBody, true, true));
		
		RevoluteJointDef wheelRev = new RevoluteJointDef();
		wheelRev.initialize(axleBody, wheelBody, axleBody.getWorldCenter());
		wheelRev.enableMotor = true;
		wheelRev.motorSpeed = 100;
		wheelRev.maxMotorTorque = 2000;
		mWheelRev = (RevoluteJoint)mPhysicsWorld.createJoint(wheelRev);
		
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

}
