package art.dual.firstLevel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import java.util.ArrayList;
import art.dual.Cryptex;
import art.dual.mainMenu.MainMenuScreen;
import art.dual.secondLevel.MainScreen2;

/**
 * This is the main class of the level. It initializes all necessary classes and renders the stages.
 * I use it as "point of access" to any part of the level (that is why it contains so much static references =)).
 * Also this class defines either loading or starting new game.
 */
public class MainScreen1 implements Screen {
    public static ArrayList<Boolean> flagsStageDrawing;
    public static ArrayList<Array<Actor>> allActorsFromStage;
    public static WorkshopStage workshopStage;
    public static TableStage tableStage;
    public static BuffetStage buffetStage;
    public static ToolBaffleStage toolBaffleStage;
    public static LampStage lampStage;
    public static TableBoxesStage tableBoxesStage;
    public static BuffetBoxStage buffetBoxStage;
    public static SafeStage safeStage;
    public static StaticObjects1 staticObjects;
    public static ActiveObjectsOnStage1 activeObjectsOnStage;
    public static Serial1 serial;
    public static StaticObjectsOnStage1 staticObjectsOnStage;
    public static Stage[] arrStages;
    public static Array<Actor>[] arrLevelsActors;
    static public Array<Actor> allActorsWorkshopStage, allActorsBuffetStage, allActorsTableStage, allActorsToolBaffleStage, allActorsLampStage,
            allActorsTableBoxesStage, allActorsBuffetBoxStage, allActorsSafeStage;

    Cryptex game;
    StretchViewport stretchViewport;
    SpriteBatch sb;
    FileHandle fileSegoe, fileSegoeName, fileSteam, fileSteamName;
    BitmapFont segoe, steam;
    float ratioX, ratioY;

    public MainScreen1(Cryptex game) {
        stretchViewport = new StretchViewport(800f, 480f);

        ratioX = stretchViewport.getRightGutterWidth()/800f;
        ratioY = stretchViewport.getTopGutterHeight()/480f;

        fileSegoe = Gdx.files.internal("segoeBM.fnt");
        fileSegoeName = Gdx.files.internal("segoeBM.png");
        sb = new SpriteBatch();
        segoe = new BitmapFont(fileSegoe, fileSegoeName, false);
        segoe.getData().setScale(ratioX, ratioY);

        fileSteam = Gdx.files.internal("steamBM.fnt");
        fileSteamName = Gdx.files.internal("steamBM.png");
        steam = new BitmapFont(fileSteam, fileSteamName, false);
        steam.getData().setScale(ratioX, ratioY);

        MainMenuScreen.manager.load("Text/1/hint_message_lamp.png", Texture.class);
        MainMenuScreen.manager.load("FirstLevel/Workshop/back_to_main_stage.png", Texture.class);
        MainMenuScreen.manager.finishLoading();

        this.game = game;

        allActorsFromStage = new ArrayList<Array<Actor>>();
        staticObjects = new StaticObjects1();
        staticObjectsOnStage = new StaticObjectsOnStage1();
        activeObjectsOnStage = new ActiveObjectsOnStage1();
        serial = new Serial1();
        safeStage = new SafeStage();
        buffetBoxStage = new BuffetBoxStage();
        tableBoxesStage = new TableBoxesStage();
        tableStage = new TableStage();
        buffetStage = new BuffetStage();
        toolBaffleStage = new ToolBaffleStage();
        lampStage = new LampStage();
        workshopStage = new WorkshopStage();

        arrStages = new Stage[]{MainMenuScreen.panelStage.getStage(), MainMenuScreen.journalStage.getStage(), MainMenuScreen.cryptexStage.getStage(),
                safeStage.getStage(), buffetBoxStage.getStage(), tableBoxesStage.getStage(), lampStage.getStage(), tableStage.getStage(), buffetStage.getStage(),
                toolBaffleStage.getStage(), workshopStage.getStage()};

        MainMenuScreen.globalReferences.setLevelNumber(1);

        allActorsWorkshopStage = workshopStage.getStage().getActors();
        allActorsBuffetStage = buffetStage.getStage().getActors();
        allActorsTableStage = tableStage.getStage().getActors();
        allActorsToolBaffleStage = toolBaffleStage.getStage().getActors();
        allActorsLampStage = lampStage.getStage().getActors();
        allActorsTableBoxesStage = tableBoxesStage.getStage().getActors();
        allActorsBuffetBoxStage = buffetBoxStage.getStage().getActors();
        allActorsSafeStage = safeStage.getStage().getActors();

        arrLevelsActors = new Array[]{allActorsWorkshopStage, allActorsBuffetStage, allActorsTableStage, allActorsToolBaffleStage, allActorsLampStage,
                allActorsTableBoxesStage, allActorsBuffetBoxStage, allActorsSafeStage};

        for(int i = 0; i < arrLevelsActors.length; i++){
            allActorsFromStage.add(arrLevelsActors[i]);
        }

        for(int i = 0; i < arrStages.length; i++){
            MainMenuScreen.inputMultiplexer.addProcessor(arrStages[i]);
        }

        Gdx.input.setInputProcessor(MainMenuScreen.inputMultiplexer);

        for (int i = 0; i < allActorsFromStage.size(); i++) {
            for (int j = 0; j < allActorsFromStage.get(i).size; j++) {
                allActorsFromStage.get(i).get(j).setTouchable(Touchable.disabled);
            }
        }

        if (MainMenuScreen.saving.getFlag1LOADED() | MainMenuScreen.saving.getFlag2LOADED() | MainMenuScreen.saving.getFlag3LOADED() | MainMenuScreen.saving.getFlag4LOADED()) {
            MainMenuScreen.activeObjectsInI.loadInIObjects();

            if(MainMenuScreen.saving.getFlag1LOADED()) {
                System.out.println("FIRST SAVE LOADING");
                loadFromPrefsWithNumber(1);
            }
            else if(MainMenuScreen.saving.getFlag2LOADED()) {
                System.out.println("SECOND SAVE LOADING");
                loadFromPrefsWithNumber(2);
            }
            else if(MainMenuScreen.saving.getFlag3LOADED()) {
                System.out.println("THIRD SAVE LOADING");
                loadFromPrefsWithNumber(3);
            }
            else if(MainMenuScreen.saving.getFlag4LOADED()) {
                System.out.println("FOURTH SAVE LOADING");
                loadFromPrefsWithNumber(4);
            }

            if(staticObjects.getFlagLampStageStart()) {
                MainMenuScreen.globalReferences.setFlagHint(true);
            }
        }
        else {
            System.out.println("NEW GAME");

            MainMenuScreen.globalReferences.setMaxLevelAchieved(MainMenuScreen.globalReferences.getLevelNumber() - 1);

            staticObjects.setFlagWorkshopStageStart(true);
        }
        setAlOfFlags();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(!MainMenuScreen.globalReferences.getFlagLevelStarted()){
            MainMenuScreen.globalReferences.setFlagLevelStarted(true);

            MainMenuScreen.globalReferences.stageLighteningWithPanel();
        }
        else {

            if (MainMenuScreen.globalReferences.getFlagGoToMainMenu()) {
                MainMenuScreen.globalReferences.setFlagGoToMainMenu(false);
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
            else if (MainMenuScreen.globalReferences.getFlagGoToSecondLevel()) {
                MainMenuScreen.globalReferences.setFlagGoToSecondLevel(false);
                MainMenuScreen.globalReferences.setFlagPuzzle1HintActivated(false);
                MainMenuScreen.globalReferences.setFlagLevelTransition(true);
                MainMenuScreen.globalReferences.setFlagJournalOpenedOnce(false);
                serial.save();
                MainMenuScreen.serialInI.save();
                dispose();
                game.setScreen(new MainScreen2(game));
            }

            if (MainMenuScreen.globalReferences.getItemsWeCanCollect1Level() == 0) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            synchronized (this) {
                                MainMenuScreen.saving.newLevelStarting();
                                wait(2000);
                                MainMenuScreen.globalReferences.setFlagGoToSecondLevel(true);
                            }
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                    }
                });
                thread.start();
            }

            if (staticObjects.getFlagTableStageStart()) {
                tableStage.getStage().act(Gdx.graphics.getDeltaTime());
                tableStage.getStage().draw();
            }
            else if (staticObjects.getFlagWorkshopStageStart()) {
                workshopStage.getStage().act(Gdx.graphics.getDeltaTime());
                workshopStage.getStage().draw();
            }
            else if (staticObjects.getFlagBuffetStageStart()) {
                buffetStage.getStage().act(Gdx.graphics.getDeltaTime());
                buffetStage.getStage().draw();
            }
            else if (staticObjects.getFlagToolBaffleStageStart()) {
                toolBaffleStage.getStage().act(Gdx.graphics.getDeltaTime());
                toolBaffleStage.getStage().draw();
            }
            else if (staticObjects.getFlagTableBoxesStageStart()) {
                tableBoxesStage.getStage().act(Gdx.graphics.getDeltaTime());
                tableBoxesStage.getStage().draw();
            }
            else if (staticObjects.getFlagBuffetBoxStageStart()) {
                buffetBoxStage.getStage().act(Gdx.graphics.getDeltaTime());
                buffetBoxStage.getStage().draw();
            }

            if (staticObjects.getFlagSafeStageStart()) {
                safeStage.getStage().act(Gdx.graphics.getDeltaTime());
                safeStage.getStage().draw();
            }

            if (staticObjects.getFlagLampStageStart()) {
                lampStage.getStage().act(Gdx.graphics.getDeltaTime());
                lampStage.getStage().draw();
            }

            if (MainMenuScreen.globalReferences.getFlagCryptexStageStart()) {
                MainMenuScreen.cryptexStage.getStage().act(Gdx.graphics.getDeltaTime());
                MainMenuScreen.cryptexStage.getStage().draw();
            }

            if (MainMenuScreen.globalReferences.getFlagJournalStageStart()) {
                MainMenuScreen.journalStage.getStage().act(Gdx.graphics.getDeltaTime());
                MainMenuScreen.journalStage.getStage().draw();
            }

            if (MainMenuScreen.globalReferences.getFlagPanelStageStart()) {
                MainMenuScreen.panelStage.getStage().act(Gdx.graphics.getDeltaTime());
                MainMenuScreen.panelStage.getStage().draw();
            }

            if (MainMenuScreen.saving.getFlagSave1Visual()) {
                sb.begin();
                segoe.setColor(0.0f, 0.0f, 0.0f, 1.0f);
                segoe.draw(sb, MainMenuScreen.saving.getPrefs1().getString("dateAndTime"), 191f * ratioX, 307f * ratioY);
                sb.end();
            }
            if (MainMenuScreen.saving.getFlagSave2Visual()) {
                sb.begin();
                segoe.setColor(0.0f, 0.0f, 0.0f, 1.0f);
                segoe.draw(sb, MainMenuScreen.saving.getPrefs2().getString("dateAndTime"), 191f * ratioX, 265f * ratioY);
                sb.end();
            }
            if (MainMenuScreen.saving.getFlagSave3Visual()) {
                sb.begin();
                segoe.setColor(0.0f, 0.0f, 0.0f, 1.0f);
                segoe.draw(sb, MainMenuScreen.saving.getPrefs3().getString("dateAndTime"), 406f * ratioX, 307f * ratioY);
                sb.end();
            }
            if (MainMenuScreen.saving.getFlagSave4Visual()) {
                sb.begin();
                segoe.setColor(0.0f, 0.0f, 0.0f, 1.0f);
                segoe.draw(sb, MainMenuScreen.saving.getPrefs4().getString("dateAndTime"), 406f * ratioX, 265f * ratioY);
                sb.end();
            }
        }
    }

    @Override
    public void dispose() {
        if (MainMenuScreen.globalReferences.getFlagGoToMainMenu()) {
            MainMenuScreen.manager.dispose();
        }

        for(int i = 0; i < arrStages.length; i++){
            if(i >= 0 & i <= 2) {}
            else arrStages[i].dispose();
        }

        MainMenuScreen.globalReferences.setFlagLevelStarted(false);
        MainMenuScreen.inputMultiplexer.clear();
        System.out.println("disposed");
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    public static void setAlOfFlags(){
        flagsStageDrawing = new ArrayList<Boolean>();

        flagsStageDrawing.add(staticObjects.getFlagWorkshopStageStart());
        flagsStageDrawing.add(staticObjects.getFlagBuffetStageStart());
        flagsStageDrawing.add(staticObjects.getFlagTableStageStart());
        flagsStageDrawing.add(staticObjects.getFlagToolBaffleStageStart());
        flagsStageDrawing.add(staticObjects.getFlagLampStageStart());
        flagsStageDrawing.add(staticObjects.getFlagTableBoxesStageStart());
        flagsStageDrawing.add(staticObjects.getFlagBuffetBoxStageStart());
        flagsStageDrawing.add(staticObjects.getFlagSafeStageStart());
    }

    public void loadFromPrefsWithNumber(int prefsNumber){
        for(int i = 0; i < MainScreen1.staticObjects.getArrFlagsNames().length; i++){
            staticObjects.setSetter(i, MainMenuScreen.saving.getPrefsGetter(prefsNumber - 1).getBoolean(MainScreen1.staticObjects.getArrFlagsNames()[i]));
        }
        MainMenuScreen.globalReferences.setMaxLevelAchieved(MainMenuScreen.saving.getPrefsGetter(prefsNumber - 1).getInteger("maxLevelAchieved"));
        MainMenuScreen.globalReferences.setItemsWeCanCollect1Level(MainMenuScreen.saving.getPrefsGetter(prefsNumber - 1).getInteger("itemsWeCanCollect1Level"));
        MainMenuScreen.globalReferences.setJournalCurrentLevelNumber(MainMenuScreen.saving.getPrefsGetter(prefsNumber - 1).getInteger("journalCurrentLevelNumber"));
    }
}