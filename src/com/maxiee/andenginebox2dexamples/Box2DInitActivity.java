package com.maxiee.andenginebox2dexamples;

import org.andengine.engine.Engine;
import org.andengine.engine.FixedStepEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.input.touch.TouchEvent;
import org.andengine.ui.activity.BaseGameActivity;

import android.hardware.SensorManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;


public class Box2DInitActivity extends BaseGameActivity implements IAccelerationListener, IOnSceneTouchListener{
	private static int cameraWidth = 800;
	private static int cameraHeight = 480;
	private Scene mScene;
	private FixedStepPhysicsWorld mPhysicsWorld;
	//Wall bodies
	private Body groundWallBody;
	private Body roofWallBody;
	private Body leftWallBody;
	private Body rightWallBody;
	
	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) {
		//process 60 updates per second
		return new FixedStepEngine(pEngineOptions, 60);
	}
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED,
				new FillResolutionPolicy(),
				new Camera(0, 0, cameraWidth, cameraHeight));
		return engineOptions;
	}

	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) {
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) {
		mScene = new Scene();
		mScene.setBackground(new Background(0.9f, 0.9f, 0.9f));
		pOnCreateSceneCallback.onCreateSceneFinished(mScene);
	}

	@Override
	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) {
		mPhysicsWorld = new FixedStepPhysicsWorld(60, 
				new Vector2(0f, -SensorManager.GRAVITY_EARTH*2),
				false, 3,	2);
		mScene.registerUpdateHandler(mPhysicsWorld);
		//create wall
		final FixtureDef WALL_FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.1f, 0.5f);
		final Rectangle groundWall = new Rectangle(
				cameraWidth / 2f, 6f,
				cameraWidth - 4f, 8f,
				this.getVertexBufferObjectManager());
		final Rectangle roofWall = new Rectangle(
				cameraWidth / 2f, cameraHeight - 6f,
				cameraWidth - 4f, 8f,
				this.getVertexBufferObjectManager());
		final Rectangle leftWall = new Rectangle(
				6f, cameraHeight / 2f,
				8f, cameraHeight - 4f,
				this.getVertexBufferObjectManager());
		final Rectangle rightWall = new Rectangle(
				cameraWidth - 6f, cameraHeight / 2f,
				8f, cameraHeight - 4f,
				this.getVertexBufferObjectManager());
		groundWall.setColor(0f, 1f, 0f);
		roofWall.setColor(0f, 1f, 0f);
		leftWall.setColor(0f, 1f, 0f);
		rightWall.setColor(0f, 1f, 0f);
		groundWallBody = PhysicsFactory.createBoxBody(
				mPhysicsWorld, 
				groundWall, 
				BodyType.StaticBody, 
				WALL_FIXTURE_DEF);
		roofWallBody = PhysicsFactory.createBoxBody(
				mPhysicsWorld, 
				roofWall, 
				BodyType.StaticBody, 
				WALL_FIXTURE_DEF);
		leftWallBody = PhysicsFactory.createBoxBody(
				mPhysicsWorld, 
				leftWall, 
				BodyType.StaticBody, 
				WALL_FIXTURE_DEF);
		rightWallBody = PhysicsFactory.createBoxBody(
				mPhysicsWorld, 
				rightWall, 
				BodyType.StaticBody, 
				WALL_FIXTURE_DEF);
		mScene.attachChild(groundWall);
		mScene.attachChild(roofWall);
		mScene.attachChild(leftWall);
		mScene.attachChild(rightWall);
		
		mScene.setOnSceneTouchListener(this);
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	@Override
	public void onAccelerationAccuracyChanged(AccelerationData pAccelerationData) {}

	@Override
	public void onAccelerationChanged(AccelerationData pAccelerationData) {}

	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent arg1) {
		return true;
	}

}
