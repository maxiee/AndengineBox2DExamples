package com.maxiee.andenginebox2dexamples;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

public class CarActivity extends Box2DInitActivity {
	private Body wheelFirstBody;
	private Body wheelLastBody;
	private Sprite wheelFirstFace;
	private Sprite wheelLastFace;
	private BitmapTextureAtlas carAtlas;
	private TextureRegion wheelRegion;
	private RevoluteJoint mWheelFirstRev;
	private RevoluteJoint mWheelLastRev;
	
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
		
		wheelFirstFace = new Sprite(
				400, 240,
				wheelRegion, super.getVertexBufferObjectManager());
		mScene.attachChild(wheelFirstFace);
		wheelFirstBody = PhysicsFactory.createCircleBody(
				mPhysicsWorld, 
				wheelFirstFace, 
				BodyType.DynamicBody, 
				ONLY_WALL_FIXTURE_DEF);
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(wheelFirstFace, wheelFirstBody, true, true));
		Sprite axleFirstFace = new Sprite(
				400, 240,
				wheelRegion, super.getVertexBufferObjectManager());
		axleFirstFace.setScale((float) 0.5);
		axleFirstFace.setVisible(false);
		Body axleFirstBody = PhysicsFactory.createCircleBody(mPhysicsWorld,
				axleFirstFace, BodyType.DynamicBody, NO_FIXTURE_DEF);
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(axleFirstFace, axleFirstBody, true, true));
		RevoluteJointDef wheelFirstRev = new RevoluteJointDef();
		wheelFirstRev.initialize(axleFirstBody, wheelFirstBody, axleFirstBody.getWorldCenter());
		wheelFirstRev.enableMotor = true;
		wheelFirstRev.motorSpeed = 10;
		wheelFirstRev.maxMotorTorque = 2000;
		mWheelFirstRev = (RevoluteJoint)mPhysicsWorld.createJoint(wheelFirstRev);
		
		wheelLastFace = new Sprite(
				700, 240,
				wheelRegion, super.getVertexBufferObjectManager());
		mScene.attachChild(wheelLastFace);
		wheelLastBody = PhysicsFactory.createCircleBody(
				mPhysicsWorld, 
				wheelLastFace, 
				BodyType.DynamicBody, 
				ONLY_WALL_FIXTURE_DEF);
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(wheelLastFace, wheelLastBody, true, true));
		Sprite axleLastFace = new Sprite(
				700, 240,
				wheelRegion, super.getVertexBufferObjectManager());
		axleLastFace.setScale((float) 0.5);
		axleLastFace.setVisible(false);
		Body axleLastBody = PhysicsFactory.createCircleBody(mPhysicsWorld,
				axleLastFace, BodyType.DynamicBody, NO_FIXTURE_DEF);
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(axleLastFace, axleLastBody, true, true));
		RevoluteJointDef wheelLastRev = new RevoluteJointDef();
		wheelLastRev.initialize(axleLastBody, wheelLastBody, axleLastBody.getWorldCenter());
		wheelLastRev.enableMotor = true;
		wheelLastRev.motorSpeed = 10;
		wheelLastRev.maxMotorTorque = 2000;
		mWheelLastRev = (RevoluteJoint)mPhysicsWorld.createJoint(wheelLastRev);
		
		Rectangle carFace = new Rectangle(550, 300, 400, 100, this.getEngine().getVertexBufferObjectManager());
		carFace.setColor(0.5f, 0.5f, 0.5f);
		mScene.attachChild(carFace);
		Body carBody = PhysicsFactory.createBoxBody(mPhysicsWorld, carFace, BodyType.DynamicBody, ONLY_WALL_FIXTURE_DEF);
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(carFace, carBody));
		
		Float a1 = (float) (-(Math.cos(Math.PI / 3)));
		Float a2 = (float) ((Math.sin(Math.PI / 3)));
		PrismaticJointDef springsFirst = new PrismaticJointDef();
		springsFirst.initialize(carBody, axleFirstBody, axleFirstBody.getWorldCenter(), new Vector2(a1,a2));
		springsFirst.collideConnected = true;
		springsFirst.enableLimit = true;
		springsFirst.enableMotor = true;
		springsFirst.lowerTranslation = 0.1f;
		springsFirst.upperTranslation = 0.4f;
		mPhysicsWorld.createJoint(springsFirst);
		
		PrismaticJointDef springsLast = new PrismaticJointDef();
		springsLast.initialize(carBody, axleLastBody, axleLastBody.getWorldCenter(), new Vector2(-1*a1,a2));
		springsLast.collideConnected = true;
		springsLast.enableLimit = true;
		springsLast.enableMotor = true;
		springsLast.lowerTranslation = 0.1f;
		springsLast.upperTranslation = 0.4f;
		mPhysicsWorld.createJoint(springsLast);
		
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
}
