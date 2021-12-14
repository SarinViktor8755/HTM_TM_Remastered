package com.mygdx.game.Characters;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.scenes.scene2d.Actor;

import com.mygdx.game.BloodBullet.PoolBlood;
import com.mygdx.game.Characters.Animation.AnimationPers;
import com.mygdx.game.ClientNetWork.SteckApi.RequestStock;
import com.mygdx.game.Lighting.B2lights;
import com.mygdx.game.Lighting.Lighting;
import com.mygdx.game.MainGaming;
import com.mygdx.game.Service.OperationVector;
import com.mygdx.game.Service.StaticService;
import com.mygdx.game.Service.TimeService;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import java.util.Iterator;


/**
 * Created by 1 on 04.08.2019.
 */

public class MainCharacter extends Actor {
    MainGaming mg;

    AnimationPers animationPers;

    private Vector2 cookAngle; // навправление тела
    private Vector2 acceleration; // навправление движения
    private Vector2 position; // позиция
    private Vector2 velocity; // ускарение
    private boolean live;
    private final float max_velocity = 700;
    private float deathValleyTime = 0; // счетчик времени смерт
    private OtherPlayers otherPlayers;
    private PoolBlood poolBlood;
    public int fragWithLife;
    public int myPositionTablica;
    private Weapons weapons;
    private Lighting lighting;
    private ArrayList<TextureRegion> maksTexture;
    public B2lights lith;
    private float globalAlpha;


    public OtherPlayers getOtherPlayers() {
        return otherPlayers;
    }

    public void clearOtherPlayer() {
        getOtherPlayers().getPlayersList().clear();
    }

//    public Weapon getWeapon() {
//        return weapon;
//    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        if (live == false) deathValleyTime = 1.5f;
        this.live = live;
    }

    public MainCharacter(MainGaming mg) {
        this.mg = mg;
        this.position = new Vector2(0, 0);
        this.startRespawn(position);
        this.velocity = new Vector2(0, 0);
        this.acceleration = new Vector2(0, 0);
        this.cookAngle = new Vector2(1, 0);
        this.animationPers = new AnimationPers(mg);
        this.otherPlayers = new OtherPlayers();
        this.poolBlood = new PoolBlood(mg, 350);
        this.live = true;
        deathValleyTime = 0;
        //this.getOtherPlayers().cleaningSnapShots();
        globalAlpha = 1;
        this.myPositionTablica = 0; // Моя позиция в таблице
        this.weapons = new Weapons();
        this.lighting = new Lighting(mg);

        this.maksTexture = new ArrayList<TextureRegion>();
        for (int i = 1; i < 6; i++) {
            this.maksTexture.add(mg.getAssetsManagerGame().get("character/character", TextureAtlas.class).findRegion("mask" + i));
        }
        if (mg.isLighting_vailable_box2d()) lith = new B2lights(mg);
    }

    public Weapons getWeapons() {
        return weapons;

    }

    public float getGlobalAlpha() {
        return globalAlpha;
    }

    public B2lights getLith() {
        return lith;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        // if(MathUtils.randomBoolean(.1f))lith.startBulletFlash(this.position.x + MathUtils.random(1000),this.position.y + MathUtils.random(1000));

        /////

        //System.out.println(position.x+" :: "+ position.y);
        globalAlpha = MathUtils.sinDeg(mg.getHud().getTimer() / 20);
        //System.out.println(globalAlpha);
        Gdx.gl.glClearColor(1, 1, 1, parentAlpha);
        batch.setColor(1, 1, 1, parentAlpha);
        Gdx.gl.glClear(GL20.GL_BLEND_EQUATION);
        //System.out.println(mg.getHud().getTimer()/200000f);
//        if (mg.getHud().getTimer() > 30000) mg.getBatch().setColor(1, 1, 1, 1);
//        else mg.getBatch().setColor(1, 1, 1, mg.getHud().getTimer() / 30000f + .7f);
//        System.out.println(mg.getHud().getTimer() + 750 / 30000f);
        //batch.setColor(1, 1, 1, .75f);
//        System.out.println(weapons.getWeapon());
//        System.out.println(mg.getHero().weapons.getTemp_weapon());
//        System.out.println("__________________");
        try {


            mg.getIndexMap().renderBottomLevel();
            mg.getBatch().setColor(1, 1, 1, .6f);
            mg.getIndexMap().renderAverageLevel();
            mg.getBatch().setColor(1, 1, 1, 1);
            poolBlood.renders();
            mg.getBatch().setColor(1, 1, 1, 1);
            mg.getIndexMap().renderTopQualityMap();


            renderPlayers(animationPers);
            otherPlayers.getPlayerToID(mg.getMainClient().getMyIdConnect()).updateCoordinatPleyer((int) position.x, (int) position.y, (int) cookAngle.angle());


            if (live) {
                //System.out.println("::::::::::::: "+ getCorrectionAngleBody());
                //TextureRegion texture = animationPers.getTextureBodyFromId(mg.getMainClient().getMyIdConnect());
                TextureRegion body = animationPers.getTextureBodyFromId(mg.getMainClient().getMyIdConnect());
                // System.out.println(body);
                batch.draw(animationPers.getTextureLegsFromId(mg.getMainClient().getMyIdConnect()), (int) (position.x - 125), (int) (position.y - 125), 125, 125, 250, 250, 1, 1, velocity.angle());
                batch.draw(body, (int) (position.x - 125), (int) (position.y - 125), 125, 125, 250, 250, 1.375f, 1.375f, cookAngle.angle() + getCorrectionAngleBody(body));
            }


            //lighting.renderLighting(batch);

            //mg.getIndexMap().renderFakePerspektiveLaier();
            // mg.getAssetsManagerGame().getProgress();
            // Gdx.app.log("Asset  ", String.valueOf(mg.getAssetsManagerGame().getProgress()));
        } catch (NullPointerException e) {
        }
    }

    private float getCorrectionAngleBody(TextureRegion texBody) {
        try {
            float delta = 0;

            String nameTexture = texBody.toString();

            if (nameTexture.equals("hit2")) {
                delta += 10;
            } else if (nameTexture.equals("hit3")) {
                delta += 20;
            } else if (nameTexture.equals("hit4")) {
                delta += 45;
            } else if (nameTexture.equals("hit2D")) {
                delta += 10;
            } else if (nameTexture.equals("hit3D")) {
                delta += 20;
            } else if (nameTexture.equals("hit4D")) {
                delta += 45;
            }

            return delta + MathUtils.sinDeg(mg.getHero().getOtherPlayers().getTacktPlayer(mg.getMainClient().getMyIdConnect()) * 10) * 3;
        } catch (NullPointerException e) {
            return 0;
        }

    }

    @Override
    public void act(float delta) {
        //System.out.println(fragWithLife);
        if (!mg.getApInput().isMove()) { // торможение
            velocity.scl(.850f);
            acceleration.scl(0);
            if (velocity.len() < .9f) { // остановка
                velocity.set(0, 0);
                velocity.setAngle(cookAngle.angle());
            }


        }
        lighting.updateLighting(delta);
        super.act(delta);
        movement(delta);
        mg.getHero().getOtherPlayers().upDateDeltaTimeAllPlayer(delta); // обновление времени всех сетевых пользователей для анимации
        try {
            for (int key : mg.getHero().getOtherPlayers().getPlayersList().keySet()) {  //ConcurrentModificationException
                try {
                    getOtherPlayers().getPlayerToID(key).getAnimatonBody().updateAnimation(delta);
                } catch (NullPointerException e) {
                    System.out.println("getOtherPlayers :: NPE");
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("Animation failed");
        }
        mg.getAudioEngine().act(delta);
        this.deathValleyTime -= delta;
        if (this.deathValleyTime < 0) respawn();
        if (animationPers.isRedyToAttack()) { // смена оружия после анимации
            this.weapons.updateWeapon();
        }

        if(mg.isLighting_vailable_box2d())lith.upDateLights(this.position.x, this.position.y, this.cookAngle.angle());


//
//        if (per < .5f)
//            mainGaming.getAudioEngine().addNewSoundStepToPleyerFromID(idPers); //добавляем еденицу шага в зависимости от звука
        //System.out.println(velocity.len2());

        if (velocity.len2() > 250000)
            mg.getAudioEngine().addNewSoundStepToPleyerFromID(mg.getMainClient().getMyIdConnect());

    }

    private void movement(float delta) { // ldb;tybt
        //System.out.println(position);


        // System.out.println( + "----------");

        if (!live) {
            stopSpeed();
            return;
        }
        int kefVelocety = 1;
        float x = position.x;
        float y = position.y;
        if (OperationVector.sclPrVectarov(getCookAngle(), acceleration)) kefVelocety *= 4;
        acceleration.nor();
        ///
        position.add(OperationVector.getBufferVector(velocity).scl(delta));
        if (!mg.getIndexMap().canMove((int) getPosition().x, (int) getPosition().y)) {
            position.x = x - getVelocity().nor().x * delta;
            position.y = y - getVelocity().nor().y * delta;
            int deltaCor;

            if (getVelocity().y < 0) deltaCor = (int) (420 * -delta);
            else deltaCor = (int) (420 * delta);
            if (mg.getIndexMap().canMove((int) getPosition().x, (int) getPosition().y + deltaCor)) {
                position.add(0, deltaCor);
            } else {
                if (getVelocity().x < 0) deltaCor = (int) (420 * -delta);
                else deltaCor = (int) (420 * delta);
                if (mg.getIndexMap().canMove((int) getPosition().x + deltaCor, (int) getPosition().y)) {
                    position.add(deltaCor, 0);
                }

            }
        }
//////////
        velocity.add(OperationVector.getBufferVector(acceleration).scl(delta * 2500 * kefVelocety)); // 2500 - смена скорости Глав Героя
        velocity.clamp(-1, max_velocity);
    }

    public void stopSpeed() {
        this.acceleration.setZero();
        this.velocity.setZero();
    }

    public void attackPipe(int id) {  // добавленеи анимации удара + отправка на сервер сообщение о нанесение удара атака
        getOtherPlayers().getPlayerToID(id).getAnimatonBody().addAnimationAttackPipe();// добавляем анимацию
        //System.out.println("addAnimationAttackPipe");
        int x = (int) (position.x + cookAngle.x * 80);
        int y = (int) (position.y + cookAngle.y * 80);
        mg.getAudioEngine().pleySoundKickStick();
        mg.getMainClient().getOutStock().addStockInQuery(new RequestStock(// отправить на сервер
                mg.getMainClient().getAndUpdateRealTime(), 1,
                x, y,
                null, null, null, null, null, null
        ));

    }

    public void attackPistol(int id) {  // добавленеи анимации удара + отправка на сервер сообщение о нанесение удара атака
        int x = (int) (position.x + cookAngle.x * 20);  // начальное положение выстрела
        int y = (int) (position.y + cookAngle.y * 20);
        if(mg.isLighting_vailable_box2d())mg.getHero().getLith().startBulletFlash(position.x + cookAngle.x * 20, position.y + cookAngle.x * 20); ///вспышка
        int cookAngle = (int) (getCookAngle().angle());  // направление
        mg.getMainClient().getOutStock().addStockInQuery(new RequestStock(// отправить на сервер
                mg.getMainClient().getAndUpdateRealTime(), 2,
                x, y,
                cookAngle, null, null, null, null, null
        ));
        getOtherPlayers().getPlayerToID(id).getAnimatonBody().addAnimationAttackPistols();// добавляем анимацию
        mg.getAudioEngine().pleySoundKickPistols();
        Vector2 delta = new Vector2(this.cookAngle);
        delta.rotate(40).scl(70);
        poolBlood.getBullet(this.cookAngle, (int) (position.x - delta.x), (int) (position.y - delta.y));
    }


    public void changeCheckWeapons() { // надо дописыть - проверяет фраги и если фораги достигли сменить оружие
//        if (this.fragWithLife > 2) this.weapons = 2;
//        if (this.fragWithLife > 5) this.weapons = 3;

    }

    public void attacShotgun(int id) {
        getOtherPlayers().getPlayerToID(id).getAnimatonBody().addAnimationAttackShotgun();// добавляем анимацию
        int x = (int) (position.x + cookAngle.x * 20);  // начальное положение выстрела
        int y = (int) (position.y + cookAngle.y * 20);
        if(mg.isLighting_vailable_box2d())mg.getHero().getLith().startBulletFlash(position.x + cookAngle.x * 20, position.y + cookAngle.x * 20); ///вспышка

        int cookAngle = (int) (getCookAngle().angle());  // направление

        mg.getAudioEngine().pleySoundKickShotgun();
        mg.getMainClient().getOutStock().addStockInQuery(new RequestStock(// отправить на сервер
                mg.getMainClient().getAndUpdateRealTime(), 3,
                x, y,
                cookAngle, null, null, null, null, null
        ));

        Vector2 delta = new Vector2(this.cookAngle);
        delta.rotate(20).scl(70);
        for (int i = -10; i < 10; i++) {
            poolBlood.getBullet(this.cookAngle.cpy().rotate(i), (int) (position.x - delta.x), (int) (position.y - delta.y));
        }

    }


    public void changeYourWeapon(int idWeapon) {  // сменить оружие - сообщить на сервер и далее всем nbg ^^ 9


        mg.getMainClient().getOutStock().addStockInQuery(new RequestStock(// отправить на сервер
                mg.getMainClient().getAndUpdateRealTime(), 9,
                idWeapon, null,
                null, null, null, null, null, null
        ));
    }

    public void changeWeaponsForOlayer(int idP, int weapon) {
        try {
            if (mg.getMainClient().getMyIdConnect() != idP) {
                mg.getHero().getOtherPlayers().getPlayerToID(idP).setWeapons(weapon);
            } else {
                mg.getHero().weapons.setTemp_weapon(weapon);
            }
        } catch (NullPointerException e) {
        }
    }

    public AnimationPers getAnimationPers(int id) {
        return animationPers;
    }

    public PoolBlood getPoolBlood() {
        return poolBlood;
    }

    ////////////////
    public void goToPoint(float x, float y) { // идти в позицию
        acceleration.set(x, y);
        acceleration.angle(OperationVector.getBufferVector(x, y));
    }

    public void goToPoint(Vector2 vec) {
        goToPoint(vec.x, vec.y);
    }

    ///////////////////////
    public Vector2 getVelocity() {
        return velocity;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getCookAngle() {
        return cookAngle;
    }


    public Vector2 getAcceleration() {
        return acceleration;
    }

    public void renderPlayers(AnimationPers animationPers) {
        // System.out.println(Ra.generateTokken());
        try {
            //mg.getBatch().setColor(1,.7f,.8f,1);
            Iterator<Integer> iter = mg.getHero().getOtherPlayers().getPlayersList().keySet().iterator();
            while (iter.hasNext()) {
                //mg.getBatch().setColor(1, 1, 1, 1);
                Integer key = iter.next();
                if (mg.getMainClient().getMyIdConnect() == key || (mg.getHero().getOtherPlayers().getXplayToId(key) == 0))
                    continue; //или это я же - иил у нас кент в загашнике на позиции - 0
                try {
                    if (!mg.getHero().getOtherPlayers().getLiveTiId(key)) continue;
                    if (mg.getHero().getOtherPlayers().isDeprecated(key)) continue;

                    int xz = mg.getHero().getOtherPlayers().getXplayToId(key);
                    int yz = mg.getHero().getOtherPlayers().getYplayToId(key);

                    if (xz == Integer.MIN_VALUE) continue;

                    mg.getBatch().setColor(1, 1, 1, getOtherPlayers().getAlphaFromId(key));

                    mg.getBatch().draw(animationPers.getTextureLegsFromId(key), (xz - 125), (yz - 125), 125, 125, 250, 250, 1, 1, mg.getHero().getOtherPlayers().getRotationBotsToId(key)); // nogi

                    mg.getBatch().draw(animationPers.getTextureBodyFromId(key),
                            (xz - 125), (yz - 125),
                            125, 125,
                            250, 250,
                            1.375f, 1.375f,
                            mg.getHero().getOtherPlayers().getRotationToId(key)); // telo
                    if (key < 0)
                        mg.getBatch().draw(maksTexture.get(mg.getHero().getOtherPlayers().getMaskToID(key - 1)),
                                (xz - 125), (yz - 125),
                                125, 125,
                                250, 250,
                                1.375f, 1.375f, mg.getHero().getOtherPlayers().getRotationToId(key)); // mask


                } catch (NullPointerException e) {
                    // System.out.println("rener other");
                    //e.printStackTrace();
                }

            }
        } catch (ConcurrentModificationException exception) {
            //System.out.println("rener other");
            //exception.printStackTrace();
        }
        mg.getBatch().setColor(1, 1, 1, 1);
    }

    public boolean updateViseblePlayer(float x, float y, int idP) {
        if (getLith().isAtShadow(x, y)) {
            //System.out.println("+++");
            //getOtherPlayers().setAlphaFromId(idP, getOtherPlayers().getAlphaFromId(idP) + (Gdx.graphics.getDeltaTime() * 1000));
            getOtherPlayers().setAlphaFromId(idP, 1);
        } else {
            //System.out.println("---");
            // getOtherPlayers().setAlphaFromId(idP, getOtherPlayers().getAlphaFromId(idP) - (Gdx.graphics.getDeltaTime() * 1000));
            getOtherPlayers().setAlphaFromId(idP, 1);

        }
        getOtherPlayers().setAlphaFromId(idP, MathUtils.clamp(getOtherPlayers().getAlphaFromId(idP), .3f, 1));
        if (getOtherPlayers().getAlphaFromId(idP) == 0) return false;
        else return true;
    }


    public boolean makeHit() {
        if (!isLive()) return false;
        if (!animationPers.isRedyToAttack()) return false;
        //ystem.out.println(mg.getHero().getWeapons().getWeapon() + "    ---weapons");
        if (getWeapons().getWeapon() == 1) attackPipe(mg.getMainClient().myIdConnect);
        if (getWeapons().getWeapon() == 2) attackPistol(mg.getMainClient().myIdConnect);
        if (getWeapons().getWeapon() == 3) attacShotgun(mg.getMainClient().myIdConnect);
        return true;
    }

    public void respawn() { // возраждение
        boolean commant = mg.getMainClient().getMyIdConnect() != 1;
        if (deathValleyTime > 0) return;
        if (this.isLive()) return;
        boolean truePosition = true;
        int x, y;
        weapons.setWeapon(1);
        weapons.setTemp_weapon(0);
        while (truePosition) {
            Vector2 temp = mg.getIndexMap().getFreeSpace(commant);

            x = (int) temp.x;
            y = (int) temp.y;
            truePosition = false;
//            for (int key : mg.getHero().getOtherPlayers().getPlayersList().keySet()) {
//                float dist = OperationVector.getDistance(x, y, mg.getHero().getOtherPlayers().getPlayerToID(key).getX(), mg.getHero().getOtherPlayers().getPlayerToID(key).getY());
//                if (dist < 700) {
//
//                    break;
//                }
//            }


            getPosition().set(x, y);

            // System.out.println(getPosition());
        }

        this.acceleration.setZero();
        this.velocity.setZero();
        this.setLive(true);
        // this.fragWithLife = 0;
        //this.weapon.setFragWithLife(0);
        //создаем пакет и отправляем
        mg.getMainClient().getOutStock().addStockInQuery(new RequestStock(// отправить на сервер - возраждение
                mg.getMainClient().getAndUpdateRealTime(), -666,
                null, null,
                null, null, null, null, null, null
        ));
    }

    public void startRespawn(Vector2 position) {
        boolean startPos = true;
        float x = 0;
        float y = 0;

        while (startPos) {
            if (StaticService.determineTeamPlayer(mg.getMainClient().getMyIdConnect()) == 1) {
                x = MathUtils.random(0, mg.getIndexMap().lengthMap / 3);
                y = MathUtils.random(0, mg.getIndexMap().lengthMap / 3);
            } else {
                x = MathUtils.random(mg.getIndexMap().lengthMap / 3, mg.getIndexMap().lengthMap);
                y = MathUtils.random(mg.getIndexMap().lengthMap / 3, mg.getIndexMap().lengthMap);
            }
            if (mg.getIndexMap().canMove((int) x, (int) y)) startPos = false;
        }
        position.x = x;
        position.y = y;
    }

    public boolean canIsee(int idPlayer) {
        OperationVector.setTemp_vector(getOtherPlayers().getXplayToId(idPlayer), getOtherPlayers().getYplayToId(idPlayer));
        float l = this.position.cpy().sub(OperationVector.get_Setter_Temp_vector()).len();
        if (l > 1200) return false;
        float x = this.position.cpy().sub(OperationVector.get_Setter_Temp_vector()).x;
        float y = this.position.cpy().sub(OperationVector.get_Setter_Temp_vector()).y;
        return true;
    }


}
