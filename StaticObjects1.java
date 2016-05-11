package art.dual.firstLevel;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import art.dual.mainMenu.MainMenuScreen;

/**
 * This class is the type of all static objects (e.g. objects player can interact with but can't take to inventory).
 * Secondly it contains all listeners according to the number of stages.
 * Also it describes JSON methods to save current objects condition.
 */
public class StaticObjects1 extends Image implements Json.Serializable{

    private float x, y, previousAngle, currentAngle, angle, deltaX, deltaY;
    private boolean visability;
    private String name;
    private Color color = new Color();
    private String stageName, rotationDirection = "-", previousDirection = "-", currentDirection = "-";
    private int turnoverLeft, turnoverRight;
    private boolean flagLampsTaken, flagLampsOnLamp ,flag1Stage, flag2Stage, flag3Stage, flag4Stage, flag5Stage, flag6Stage, flagBoxWithKeyOpened,
            flagLampHolderOccupied, flagWorkshopStageStart, flagBuffetStageStart, flagSafeStageStart, flagTableStageStart, flagToolBaffleStageStart,
            flagLampStageStart, flagTableBoxesStageStart, flagBuffetBoxStageStart, flagKeyFromBuffetTaken;

    public StaticObjects1(){
    }
    public StaticObjects1(String stageName, Texture texture) {
        super(texture);

        addListenerToStage(stageName);
    }
    public StaticObjects1(String stageName, Texture texture, float x, float y, boolean visability, String name) {
        super(texture);

        this.x = x;
        setX(this.x);
        this.y = y;
        setY(this.y);
        this.visability = visability;
        setVisible(this.visability);
        this.name = name;
        setName(this.name);

        addListenerToStage(stageName);
    }

    public void addListenerToStage(String stageName){
        if(stageName == "stageWorkshop") {
            addListener(stageWorkshopIL);
            //addListener(arrangement);
        }
        else if(stageName == "stageBuffet") {
            addListener(buffetStageIL);
            //addListener(arrangement);
        }
        else if(stageName == "stageLamp") {
            addListener(lampStageIL);
            //addListener(lampStageDL);
        }
        else if(stageName == "stageSafe") {
            addListener(stageSafeIL);
            //addListener(arrangement);
        }
        else if(stageName == "stageTableBoxes") {
            addListener(stageTableBoxesIL);
            //addListener(arrangement);
        }
        else if(stageName == "stageTable") {
            addListener(stageTableIL);
            //addListener(arrangement);
        }
        else if(stageName == "stageToolBaffle") {
            addListener(stageToolBaffleIL);
        }
        else if(stageName == "stageBuffetBox") {
            addListener(stageBuffetBoxIL);
        }
    }

    public void safeArrowPressed(final int index, final String name){
        MainScreen1.staticObjectsOnStage.getRoller().rotateBy(3.6f * index);

        if(name.equals("right")) MainScreen1.staticObjects.setTurnoverRight(MainScreen1.staticObjects.getTurnoverRight() + 1);
        else MainScreen1.staticObjects.setTurnoverLeft(MainScreen1.staticObjects.getTurnoverLeft() + 1);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(500);

                        while(true){
                            MainScreen1.staticObjectsOnStage.getRoller().rotateBy(3.6f * index);

                            if(name.equals("right")) MainScreen1.staticObjects.setTurnoverRight(MainScreen1.staticObjects.getTurnoverRight() + 1);
                            else MainScreen1.staticObjects.setTurnoverLeft(MainScreen1.staticObjects.getTurnoverLeft() + 1);

                            wait(100);
                        }
                    }
                }
                catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "rotationThread");
        thread.start();
    }

    public void rotateValveAndArrow(){
        MainScreen1.staticObjects.setPreviousAngle(MainScreen1.staticObjects.getCurrentAngle());
        MainScreen1.staticObjects.setCurrentAngle(MainScreen1.staticObjects.getAngle());

        if(Math.abs(MainScreen1.staticObjects.getCurrentAngle() - MainScreen1.staticObjects.getPreviousAngle()) < 100) {
            if (MainScreen1.staticObjects.getCurrentAngle() - MainScreen1.staticObjects.getPreviousAngle() > 0) {
                MainScreen1.staticObjects.setRotationDirection("<-");
            } else MainScreen1.staticObjects.setRotationDirection("->");
        }
            System.out.println(MainScreen1.staticObjects.getRotationDirection());
            if (MainScreen1.staticObjects.getRotationDirection().equals("<-") & MainScreen1.staticObjectsOnStage.getArrow_higher().getRotation() < 5) {
                MainScreen1.staticObjectsOnStage.getValve().rotateBy(3f);
                MainScreen1.staticObjectsOnStage.getArrow_higher().rotateBy(1f);
            }
            else if(MainScreen1.staticObjects.getRotationDirection().equals("->") & MainScreen1.staticObjectsOnStage.getArrow_higher().getRotation() > -268) {
                MainScreen1.staticObjectsOnStage.getValve().rotateBy(-3f);
                MainScreen1.staticObjectsOnStage.getArrow_higher().rotateBy(-1f);
            }
    }

    public void openTableBox(String name, float x, float y){
        if(name.equals("key_from_table_box_InI")) {
            if (x > 185 & x < 520 & y > 303 & y < 383) {
                MainScreen1.staticObjectsOnStage.getBox_without_key().setVis(true);
                MainScreen1.activeObjectsOnStage.getKey_from_buffet().setVis(true);
                MainScreen1.staticObjects.setFlagBoxWithKeyOpened(true);

                MainMenuScreen.panelStage.getGroupActiveItem().findActor("key_from_table_box_InI").remove();
                MainMenuScreen.globalReferences.setFlagActiveCellOccupied(false);
            } else {
                MainMenuScreen.activeObjects.moveActiveItemToDefault();
            }
        }
        else {
            MainMenuScreen.activeObjects.moveActiveItemToDefault();
        }
    }

    public void openBuffetBox(String name, float x, float y){
        if(name.equals("key_from_buffet_InI")) {
            if (x > 391 & x < 510 & y > 43 & y < 161) {
                MainScreen1.staticObjectsOnStage.getBox_opened().setVis(true);
                MainScreen1.staticObjectsOnStage.getBuffet_box_closed().setVis(false);
                MainMenuScreen.panelStage.getGroupActiveItem().findActor("key_from_buffet_InI").remove();
                MainMenuScreen.globalReferences.setFlagActiveCellOccupied(false);
            } else {
                MainMenuScreen.activeObjects.moveActiveItemToDefault();
            }
        }
        else {
            MainMenuScreen.activeObjects.moveActiveItemToDefault();
        }
    }

    public void putLampsOnLampStage(String name, float x, float y){
        if(name.equals("lamps_InI")) {
            if (x > -24 & x < 761 & y > 70 & y < 442) {
                MainScreen1.lampStage.getGroupUnactiveLamps().setVisible(true);
                MainScreen1.staticObjects.setFlagLampsOnLamp(true);
                MainMenuScreen.panelStage.getGroupActiveItem().findActor("lamps_InI").remove();
                MainMenuScreen.globalReferences.setFlagActiveCellOccupied(false);
            } else {
                MainMenuScreen.activeObjects.moveActiveItemToDefault();
            }
        }
        else {
            MainMenuScreen.activeObjects.moveActiveItemToDefault();
        }
    }

    public void mountValveOnLamp(String name, float x, float y){
        if(name.equals("valve_for_lamp_InI")) {
            if (x > 288 & x < 345 & y > 179 & y < 231) {
                MainScreen1.staticObjectsOnStage.getValve().setVis(true);
                MainScreen1.staticObjectsOnStage.getValve_unlighted().setVis(true);
                MainScreen1.staticObjectsOnStage.getValve_Workshop().setVis(true);
                MainMenuScreen.panelStage.getGroupActiveItem().findActor("valve_for_lamp_InI").remove();

                MainMenuScreen.globalReferences.setFlagActiveCellOccupied(false);
            } else {
                MainMenuScreen.activeObjects.moveActiveItemToDefault();
            }
        }
        else {
            MainMenuScreen.activeObjects.moveActiveItemToDefault();
        }
    }

    public void threadBranchesMethod(){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        while(true){

                            MoveToAction mbaBranch3ToTheRight = new MoveToAction();
                            mbaBranch3ToTheRight.setPosition(368f, 267f);
                            mbaBranch3ToTheRight.setDuration(0.8f);

                            MoveToAction mbaBranch3ToTheLeft = new MoveToAction();
                            mbaBranch3ToTheLeft.setPosition(358f, 269f);
                            mbaBranch3ToTheLeft.setDuration(2f);

                            SequenceAction saBranch3 = new SequenceAction(mbaBranch3ToTheRight, mbaBranch3ToTheLeft);
                            MainScreen1.workshopStage.getBranch3().addAction(saBranch3);

                            wait(50);

                            MoveToAction mbaBranch1ToTheRight = new MoveToAction();
                            mbaBranch1ToTheRight.setPosition(483f, 354f);
                            mbaBranch1ToTheRight.setDuration(0.8f);

                            MoveToAction mbaBranch1ToTheLeft = new MoveToAction();
                            mbaBranch1ToTheLeft.setPosition(473f, 357f);
                            mbaBranch1ToTheLeft.setDuration(2f);

                            SequenceAction saBranch1 = new SequenceAction(mbaBranch1ToTheRight, mbaBranch1ToTheLeft);
                            MainScreen1.workshopStage.getBranch1().addAction(saBranch1);

                            wait(75);

                            MoveToAction mbaBranch4ToTheRight = new MoveToAction();
                            mbaBranch4ToTheRight.setPosition(458f, 266f);
                            mbaBranch4ToTheRight.setDuration(0.8f);

                            MoveToAction mbaBranch4ToTheLeft = new MoveToAction();
                            mbaBranch4ToTheLeft.setPosition(448f, 276f);
                            mbaBranch4ToTheLeft.setDuration(2f);

                            SequenceAction saBranch4 = new SequenceAction(mbaBranch4ToTheRight, mbaBranch4ToTheLeft);
                            MainScreen1.workshopStage.getBranch4().addAction(saBranch4);

                            wait(100);

                            MoveToAction mbaBranch2ToTheRight = new MoveToAction();
                            mbaBranch2ToTheRight.setPosition(476f, 189f);
                            mbaBranch2ToTheRight.setDuration(0.8f);

                            MoveToAction mbaBranch2ToTheLeft = new MoveToAction();
                            mbaBranch2ToTheLeft.setPosition(470f, 195f);
                            mbaBranch2ToTheLeft.setDuration(2f);

                            SequenceAction saBranch2 = new SequenceAction(mbaBranch2ToTheRight, mbaBranch2ToTheLeft);
                            MainScreen1.workshopStage.getBranch2().addAction(saBranch2);

                            wait(2000);
                        }
                    }
                }
                catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        thread.start();
    }
    public float getObjectX() {
        return x;
    }
    public float getObjectY() {
        return y;
    }
    public boolean getVis() {
        return visability;
    }
    public String getObjectName() {
        return name;
    }
    public Color getObjectColor() {
        return color;
    }
    public void setObjectX(float x) {
        this.x = x;
        setX(this.x);
    }
    public void setObjectY(float y) {
        this.y = y;
        setY(this.y);
    }
    public void setVis(boolean visability) {
        this.visability = visability;
        setVisible(this.visability);
    }
    public void setObjectName(String name) {
        this.name = name;
        setName(this.name);
    }
    public void setObjectColor(Color color) {
        this.color = color;
        setColor(this.color);
    }
    public boolean getFlagLampsTaken() {
        return flagLampsTaken;
    }
    public void setFlagLampsTaken(boolean flagLampsTaken) {
        this.flagLampsTaken = flagLampsTaken;
    }
    public boolean getFlagLampsOnLamp() {
        return flagLampsOnLamp;
    }
    public boolean getFlag1Stage() {
        return flag1Stage;
    }
    public boolean getFlag2Stage() {
        return flag2Stage;
    }
    public boolean getFlag3Stage() {
        return flag3Stage;
    }
    public boolean getFlag4Stage() {
        return flag4Stage;
    }
    public boolean getFlag5Stage() {
        return flag5Stage;
    }
    public boolean getFlag6Stage() {
        return flag6Stage;
    }
    public boolean getFlagBoxWithKeyOpened() {
        return flagBoxWithKeyOpened;
    }
    public boolean getFlagLampHolderOccupied() {
        return flagLampHolderOccupied;
    }
    public void setFlagLampsOnLamp(boolean flagLampsOnLamp) {
        this.flagLampsOnLamp = flagLampsOnLamp;
    }
    public void setFlag1Stage(boolean flag1Stage) {
        this.flag1Stage = flag1Stage;
    }
    public void setFlag2Stage(boolean flag2Stage) {
        this.flag2Stage = flag2Stage;
    }
    public void setFlag3Stage(boolean flag3Stage) {
        this.flag3Stage = flag3Stage;
    }
    public void setFlag4Stage(boolean flag4Stage) {
        this.flag4Stage = flag4Stage;
    }
    public void setFlag5Stage(boolean flag5Stage) {
        this.flag5Stage = flag5Stage;
    }
    public void setFlag6Stage(boolean flag6Stage) {
        this.flag6Stage = flag6Stage;
    }
    public void setFlagBoxWithKeyOpened(boolean flagBoxWithKeyOpened) {
        this.flagBoxWithKeyOpened = flagBoxWithKeyOpened;
    }
    public void setFlagLampHolderOccupied(boolean flagLampHolderOccupied) {
        this.flagLampHolderOccupied = flagLampHolderOccupied;
    }
    public boolean getFlagWorkshopStageStart() {
        return flagWorkshopStageStart;
    }
    public boolean getFlagBuffetStageStart() {
        return flagBuffetStageStart;
    }
    public boolean getFlagSafeStageStart() {
        return flagSafeStageStart;
    }
    public boolean getFlagTableStageStart() {
        return flagTableStageStart;
    }
    public boolean getFlagToolBaffleStageStart() {
        return flagToolBaffleStageStart;
    }
    public boolean getFlagLampStageStart() {
        return flagLampStageStart;
    }
    public boolean getFlagTableBoxesStageStart() {
        return flagTableBoxesStageStart;
    }
    public boolean getFlagBuffetBoxStageStart() {
        return flagBuffetBoxStageStart;
    }
    public void setFlagWorkshopStageStart(boolean flagWorkshopStageStart) {
        this.flagWorkshopStageStart = flagWorkshopStageStart;
    }
    public void setFlagBuffetStageStart(boolean flagBuffetStageStart) {
        this.flagBuffetStageStart = flagBuffetStageStart;
    }
    public void setFlagSafeStageStart(boolean flagSafeStageStart) {
        this.flagSafeStageStart = flagSafeStageStart;
    }
    public void setFlagTableStageStart(boolean flagTableStageStart) {
        this.flagTableStageStart = flagTableStageStart;
    }
    public void setFlagToolBaffleStageStart(boolean flagToolBaffleStageStart) {
        this.flagToolBaffleStageStart = flagToolBaffleStageStart;
    }
    public void setFlagLampStageStart(boolean flagLampStageStart) {
        this.flagLampStageStart = flagLampStageStart;
    }
    public void setFlagTableBoxesStageStart(boolean flagTableBoxesStageStart) {
        this.flagTableBoxesStageStart = flagTableBoxesStageStart;
    }
    public void setFlagBuffetBoxStageStart(boolean flagBuffetBoxStageStart) {
        this.flagBuffetBoxStageStart = flagBuffetBoxStageStart;
    }
    public boolean getFlagKeyFromBuffetTaken() {
        return flagKeyFromBuffetTaken;
    }
    public void setFlagKeyFromBuffetTaken(boolean flagKeyFromBuffetTaken) {
        this.flagKeyFromBuffetTaken = flagKeyFromBuffetTaken;
    }
    public int getTurnoverLeft() {
        return turnoverLeft;
    }
    public int getTurnoverRight() {
        return turnoverRight;
    }
    public void setTurnoverLeft(int turnoverLeft) {
        this.turnoverLeft = turnoverLeft;
    }
    public void setTurnoverRight(int turnoverRight) {
        this.turnoverRight = turnoverRight;
    }
    public String getRotationDirection() {
        return rotationDirection;
    }
    public void setRotationDirection(String rotationDirection) {
        this.rotationDirection = rotationDirection;
    }
    public float getPreviousAngle() {
        return previousAngle;
    }
    public float getCurrentAngle() {
        return currentAngle;
    }
    public void setPreviousAngle(float previousAngle) {
        this.previousAngle = previousAngle;
    }
    public void setCurrentAngle(float currentAngle) {
        this.currentAngle = currentAngle;
    }
    public float getAngle() {
        return angle;
    }
    public void setAngle(float angle) {
        this.angle = angle;
    }
    public float getDeltaX() {
        return deltaX;
    }
    public float getDeltaY() {
        return deltaY;
    }
    public void setDeltaX(float deltaX) {
        this.deltaX = deltaX;
    }
    public void setDeltaY(float deltaY) {
        this.deltaY = deltaY;
    }

    @Override
    public void write(Json json) {
        json.writeValue("objectX", x);
        json.writeValue("objectY", y);
        json.writeValue("objectVis", visability);
        json.writeValue("objectColor", getObjectColor());
        json.writeValue("objectName", name);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        x = jsonData.getFloat("objectX");
        y = jsonData.getFloat("objectY");
        visability = jsonData.getBoolean("objectVis");
        color = getObjectColor();
        name = jsonData.getString("objectName");
    }

/////////////////////////////////////////////////////////////////////////WORKSHOP

    EventListener stageWorkshopIL = new InputListener() {

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

            System.out.println("(WORKSHOP) my name is: " + StaticObjects1.this.getName());


            if(StaticObjects1.this.getName().endsWith("overlap")) {
                for (int i = 0; i < MainScreen1.allActorsWorkshopStage.size; i++) {
                    MainScreen1.allActorsWorkshopStage.get(i).setTouchable(Touchable.disabled);
                }

//enter buffet stage
                if (StaticObjects1.this.getName().equals("buffet_overlap")) {

                    for (int i = 0; i < MainScreen1.allActorsBuffetStage.size; i++) {
                        MainScreen1.allActorsBuffetStage.get(i).setTouchable(Touchable.enabled);
                    }

                    MainScreen1.staticObjects.setFlagBuffetStageStart(true);
                }

//enter table stage
                else if (StaticObjects1.this.getName().equals("table_overlap")) {

                    for (int i = 0; i < MainScreen1.allActorsTableStage.size; i++) {
                        MainScreen1.allActorsTableStage.get(i).setTouchable(Touchable.enabled);
                    }

                    MainScreen1.staticObjects.setFlagTableStageStart(true);
                }

//enter toolbaffle stage
                else if (StaticObjects1.this.getName().equals("tool_baffle_overlap")) {

                    for (int i = 0; i < MainScreen1.allActorsToolBaffleStage.size; i++) {
                        MainScreen1.allActorsToolBaffleStage.get(i).setTouchable(Touchable.enabled);
                    }

                    MainScreen1.staticObjects.setFlagToolBaffleStageStart(true);
                }

//enter tableboxes stage
                else if (StaticObjects1.this.getName().equals("table_boxes_overlap")) {

                    for (int i = 0; i < MainScreen1.allActorsTableBoxesStage.size; i++) {
                        MainScreen1.allActorsTableBoxesStage.get(i).setTouchable(Touchable.enabled);
                    }

                    MainScreen1.staticObjects.setFlagTableBoxesStageStart(true);
                }
                MainScreen1.staticObjects.setFlagWorkshopStageStart(false);
            }
            return true;
        }
    };

///////////////////////////////////////////////////////////////////////BUFFET

    EventListener buffetStageIL = new InputListener() {


        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            System.out.println("(BUFFET) my name is: " + StaticObjects1.this.getName());

           if (StaticObjects1.this.getName().equals("box_opened")) {

//enter buffetbox stage
               for (int i = 0; i < MainScreen1.allActorsBuffetStage.size; i++) {
                   MainScreen1.allActorsBuffetStage.get(i).setTouchable(Touchable.disabled);
               }

               for (int i = 0; i < MainScreen1.allActorsBuffetBoxStage.size; i++) {
                   MainScreen1.allActorsBuffetBoxStage.get(i).setTouchable(Touchable.enabled);
               }

               MainScreen1.staticObjects.setFlagBuffetBoxStageStart(true);
               MainScreen1.staticObjects.setFlagBuffetStageStart(false);

            }

//exit buttet stage
            else if (StaticObjects1.this.getName().equals("back_to_main_stage_from_buffet")) {

                for (int i = 0; i < MainScreen1.allActorsWorkshopStage.size; i++) {
                    MainScreen1.allActorsWorkshopStage.get(i).setTouchable(Touchable.enabled);
                }
                for (int i = 0; i < MainScreen1.allActorsBuffetStage.size; i++) {
                    MainScreen1.allActorsBuffetStage.get(i).setTouchable(Touchable.disabled);
                }

                MainScreen1.staticObjects.setFlagWorkshopStageStart(true);
                MainScreen1.staticObjects.setFlagBuffetStageStart(false);
            }

            return true;
        }
    };

/////////////////////////////////////////////////////TABLE

    EventListener stageTableIL = new InputListener() {

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            System.out.println("(TABLE) my name is: " + StaticObjects1.this.getName());

//enter lamp stage
            if (StaticObjects1.this.getName().equals("lamp_overlap")) {

                Image objectBackground = new Image(MainMenuScreen.manager.get("GlobalObjects/ObjectsBackground.png", Texture.class));
                objectBackground.setName("objectBackground");
                StaticObjects1.this.getStage().addActor(objectBackground);

                if (MainScreen1.staticObjects.getFlagLampsOnLamp()) {
                    MainScreen1.lampStage.getGroupUnactiveLamps().setVisible(true);
                }

                for (int i = 0; i < MainScreen1.allActorsTableStage.size; i++) {
                    MainScreen1.allActorsTableStage.get(i).setTouchable(Touchable.disabled);
                }

                for (int i = 0; i < MainScreen1.allActorsLampStage.size; i++) {
                    MainScreen1.allActorsLampStage.get(i).setTouchable(Touchable.enabled);
                }

                if(!MainScreen1.workshopStage.getFlagLampOpened()) {
                    MainMenuScreen.globalReferences.setFlagHint(true);
                }

                if(!MainMenuScreen.globalReferences.getFlagPuzzle1HintActivated() & !MainScreen1.workshopStage.getFlagLampOpened()) {
                    MainMenuScreen.globalReferences.threadHelpButtonMethod();
                }

                MainScreen1.staticObjects.setFlagLampStageStart(true);
            }

//exit table stage

            else if (StaticObjects1.this.getName().equals("back_to_main_stage_from_table")) {

                for (int i = 0; i < MainScreen1.allActorsWorkshopStage.size; i++) {
                    MainScreen1.allActorsWorkshopStage.get(i).setTouchable(Touchable.enabled);
                }
                for (int i = 0; i < MainScreen1.allActorsTableStage.size; i++) {
                    MainScreen1.allActorsTableStage.get(i).setTouchable(Touchable.disabled);
                }

                MainScreen1.staticObjects.setFlagWorkshopStageStart(true);
                MainScreen1.staticObjects.setFlagTableStageStart(false);
            }

            return true;
        }
    };

/////////////////////////////////////////////////////////////////LAMP

    EventListener lampStageIL = new InputListener() {

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            System.out.println("(LAMP)My name is: " + StaticObjects1.this.getName());
            System.out.println("(LAMP)My parent is: " + StaticObjects1.this.getParent().getName());

            if (StaticObjects1.this.getName().equals("safe_password")) {
                StaticObjects1.this.remove();
            }

//exit lamp stage

            else if (StaticObjects1.this.getName().equals("back_to_table_stage_from_lamp")) {

                if (!MainScreen1.staticObjectsOnStage.getPaper_sheet().isVisible()) {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                synchronized (this) {
                                    if (!MainScreen1.workshopStage.getFlagLampOpened()) {
                                        if (MainScreen1.lampStage.getGroupActiveLampInHolder().getChildren().toArray().length > 0) {
                                            MainScreen1.lampStage.getGroupActiveLampInHolder().getChildren().get(0).remove();
                                            MainScreen1.lampStage.getGroupUnactiveLampInHolder().getChildren().get(0).setVisible(true);
                                            MainScreen1.lampStage.getGroupUnactiveLamps().addActor(MainScreen1.lampStage.getGroupUnactiveLampInHolder().getChildren().get(0));
                                            MainScreen1.staticObjects.setFlagLampHolderOccupied(false);

                                            if (MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).getName().equals("lamp1_unactive")) {
                                                MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).setPosition(26f, 279f);
                                                MainScreen1.staticObjectsOnStage.getArrow_lower().rotateBy(105);
                                            } else if (MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).getName().equals("lamp2_unactive")) {
                                                MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).setPosition(26f, 136f);
                                                MainScreen1.staticObjectsOnStage.getArrow_lower().rotateBy(65);
                                            } else if (MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).getName().equals("lamp3_unactive")) {
                                                MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).setPosition(641f, 316f);
                                                MainScreen1.staticObjectsOnStage.getArrow_lower().rotateBy(24);
                                            } else if (MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).getName().equals("lamp4_unactive")) {
                                                MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).setPosition(661f, 226f);
                                                MainScreen1.staticObjectsOnStage.getArrow_lower().rotateBy(198);
                                            } else if (MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).getName().equals("lamp5_unactive")) {
                                                MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).setPosition(668f, 111f);
                                            }
                                        }
                                    }

                                    if (MainScreen1.lampStage.getGroupUnactiveLamps().isVisible()) {
                                        wait(300);
                                    }

                                    Actor[] arr = MainScreen1.allActorsTableStage.toArray();
                                    for (int i = arr.length - 1; i > 0; i--) {
                                        if (arr[i].getName() == "objectBackground") {
                                            arr[i].remove();
                                            break;
                                        }
                                    }

                                    for (int i = 0; i < MainScreen1.allActorsLampStage.size; i++) {
                                        MainScreen1.allActorsLampStage.get(i).setTouchable(Touchable.disabled);
                                    }
                                    for (int i = 0; i < MainScreen1.allActorsTableStage.size; i++) {
                                        MainScreen1.allActorsTableStage.get(i).setTouchable(Touchable.enabled);
                                    }

                                    MainScreen1.staticObjects.setFlagLampStageStart(false);
                                    MainScreen1.staticObjects.setFlagTableStageStart(true);

                                    MainMenuScreen.globalReferences.exitPuzzleStage();
                                }
                            } catch (InterruptedException ie) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    });
                    thread.start();
                }
            }

            else if (StaticObjects1.this.getName().equals("paper_sheet")) {
                MainScreen1.staticObjectsOnStage.getPaper_sheet().setVis(false);
                MainScreen1.staticObjectsOnStage.getSafe_password().setVis(true);
                StaticObjects1.this.getStage().addActor(MainScreen1.staticObjectsOnStage.getSafe_password());

            }
            else if (StaticObjects1.this.getName().equals("button") & !MainScreen1.workshopStage.getFlagLampOpened()) {
                if (MainScreen1.staticObjectsOnStage.getArrow_higher().getRotation() < -180 & MainScreen1.staticObjectsOnStage.getArrow_higher().getRotation() > -190 &
                        MainScreen1.staticObjectsOnStage.getArrow_lower().getRotation() == -120) {

                    MainScreen1.workshopStage.setFlagLampOpened(true);

                    MainScreen1.staticObjectsOnStage.getPaper_sheet().setVis(true);

                    MainMenuScreen.globalReferences.exitPuzzleStage();
                    MainMenuScreen.globalReferences.setFlagPuzzle1HintActivated(true);

                    MainScreen1.lampStage.getGroupUnactiveLamps().setVisible(false);

                    MainScreen1.staticObjects.setFlagLampsOnLamp(false);

                    MainScreen1.staticObjectsOnStage.getLamp_active_and_unlocked().setVis(true);
                    MainScreen1.staticObjectsOnStage.getCover_closed().setVis(false);
                    MainScreen1.staticObjectsOnStage.getCover_opened().setVis(true);
                    MainScreen1.staticObjectsOnStage.getValve_unlighted().setVis(false);
                    MainScreen1.staticObjectsOnStage.getValve_lighted().setVis(true);
                    MainScreen1.staticObjectsOnStage.getLamp_active().setVis(true);
                    MainScreen1.staticObjectsOnStage.getLamp_closed().setVis(false);

                    if(MainScreen1.activeObjectsOnStage.getKnife_unlighted().isVisible()){
                        MainScreen1.activeObjectsOnStage.getKnife_unlighted().setVis(false);
                        MainScreen1.activeObjectsOnStage.getKnife_lighted().setVis(true);
                    }

                    if(MainScreen1.activeObjectsOnStage.getBinoculars_unlighted().isVisible()){
                        MainScreen1.activeObjectsOnStage.getBinoculars_unlighted().setVis(false);
                        MainScreen1.activeObjectsOnStage.getBinoculars_lighted().setVis(true);
                    }
                }
            }
            return true;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            if (StaticObjects1.this.getName().equals("lamp1_unactive") & StaticObjects1.this.getX() > 120 & StaticObjects1.this.getX() < 220 &
                    StaticObjects1.this.getY() > 195 & StaticObjects1.this.getY() < 355) {
                StaticObjects1.this.setVis(false);
                MainScreen1.staticObjectsOnStage.getLamp1_active().setVis(true);
                MainScreen1.staticObjectsOnStage.getArrow_lower().rotateBy(-105);

                if (MainScreen1.staticObjects.getFlagLampHolderOccupied()) {

                    MainScreen1.lampStage.getGroupUnactiveLampInHolder().getChildren().get(0).setVisible(true);
                    MainScreen1.lampStage.getGroupUnactiveLamps().addActor(MainScreen1.lampStage.getGroupUnactiveLampInHolder().getChildren().get(0));

                    if (MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).getName().equals("lamp2_unactive")) {
                        MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).setPosition(26f, 136f);
                        MainScreen1.staticObjectsOnStage.getArrow_lower().rotateBy(65);
                    } else if (MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).getName().equals("lamp3_unactive")) {
                        MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).setPosition(641f, 316f);
                        MainScreen1.staticObjectsOnStage.getArrow_lower().rotateBy(24);
                    } else if (MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).getName().equals("lamp4_unactive")) {
                        MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).setPosition(661f, 226f);
                        MainScreen1.staticObjectsOnStage.getArrow_lower().rotateBy(198);
                    } else if (MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).getName().equals("lamp5_unactive")) {
                        MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).setPosition(668f, 111f);
                    }

                    MainScreen1.lampStage.getGroupUnactiveLampInHolder().addActor(StaticObjects1.this);

                    MainScreen1.lampStage.getGroupActiveLampInHolder().clear();
                    MainScreen1.lampStage.getGroupActiveLampInHolder().addActor(MainScreen1.staticObjectsOnStage.getLamp1_active());

                } else {

                    MainScreen1.lampStage.getGroupUnactiveLampInHolder().addActor(StaticObjects1.this);
                    MainScreen1.lampStage.getGroupActiveLampInHolder().addActor(MainScreen1.staticObjectsOnStage.getLamp1_active());
                    MainScreen1.staticObjects.setFlagLampHolderOccupied(true);

                }
            }
            else if (StaticObjects1.this.getName().equals("lamp2_unactive") & StaticObjects1.this.getX() > 115 & StaticObjects1.this.getX() < 220 &
                    StaticObjects1.this.getY() > 185 & StaticObjects1.this.getY() < 345) {
                StaticObjects1.this.setVis(false);
                MainScreen1.staticObjectsOnStage.getLamp2_active().setVis(true);
                MainScreen1.staticObjectsOnStage.getArrow_lower().rotateBy(-65);

                if (MainScreen1.staticObjects.getFlagLampHolderOccupied()) {

                    MainScreen1.lampStage.getGroupUnactiveLampInHolder().getChildren().get(0).setVisible(true);
                    MainScreen1.lampStage.getGroupUnactiveLamps().addActor(MainScreen1.lampStage.getGroupUnactiveLampInHolder().getChildren().get(0));

                    if (MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).getName().equals("lamp1_unactive")) {
                        MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).setPosition(26f, 279f);
                        MainScreen1.staticObjectsOnStage.getArrow_lower().rotateBy(105);
                    } else if (MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).getName().equals("lamp3_unactive")) {
                        MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).setPosition(641f, 316f);
                        MainScreen1.staticObjectsOnStage.getArrow_lower().rotateBy(24);
                    } else if (MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).getName().equals("lamp4_unactive")) {
                        MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).setPosition(661f, 226f);
                        MainScreen1.staticObjectsOnStage.getArrow_lower().rotateBy(198);
                    } else if (MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).getName().equals("lamp5_unactive")) {
                        MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).setPosition(668f, 111f);
                    }

                    MainScreen1.lampStage.getGroupUnactiveLampInHolder().addActor(StaticObjects1.this);

                    MainScreen1.lampStage.getGroupActiveLampInHolder().clear();
                    MainScreen1.lampStage.getGroupActiveLampInHolder().addActor(MainScreen1.staticObjectsOnStage.getLamp2_active());


                } else {

                    MainScreen1.lampStage.getGroupUnactiveLampInHolder().addActor(StaticObjects1.this);
                    MainScreen1.lampStage.getGroupActiveLampInHolder().addActor(MainScreen1.staticObjectsOnStage.getLamp2_active());
                    MainScreen1.staticObjects.setFlagLampHolderOccupied(true);
                }
            }
            else if (StaticObjects1.this.getName().equals("lamp3_unactive") & StaticObjects1.this.getX() > 130 & StaticObjects1.this.getX() < 240 &
                    StaticObjects1.this.getY() > 195 & StaticObjects1.this.getY() < 360) {
                StaticObjects1.this.setVis(false);
                MainScreen1.staticObjectsOnStage.getLamp3_active().setVis(true);
                MainScreen1.staticObjectsOnStage.getArrow_lower().rotateBy(-24);

                if (MainScreen1.staticObjects.getFlagLampHolderOccupied()) {

                    MainScreen1.lampStage.getGroupUnactiveLampInHolder().getChildren().get(0).setVisible(true);
                    MainScreen1.lampStage.getGroupUnactiveLamps().addActor(MainScreen1.lampStage.getGroupUnactiveLampInHolder().getChildren().get(0));

                    if (MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).getName().equals("lamp1_unactive")) {
                        MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).setPosition(26f, 279f);
                        MainScreen1.staticObjectsOnStage.getArrow_lower().rotateBy(105);
                    } else if (MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).getName().equals("lamp2_unactive")) {
                        MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).setPosition(26f, 136f);
                        MainScreen1.staticObjectsOnStage.getArrow_lower().rotateBy(65);
                    } else if (MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).getName().equals("lamp4_unactive")) {
                        MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).setPosition(661f, 226f);
                        MainScreen1.staticObjectsOnStage.getArrow_lower().rotateBy(198);
                    } else if (MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).getName().equals("lamp5_unactive")) {
                        MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).setPosition(668f, 111f);
                    }

                    MainScreen1.lampStage.getGroupUnactiveLampInHolder().addActor(StaticObjects1.this);

                    MainScreen1.lampStage.getGroupActiveLampInHolder().clear();
                    MainScreen1.lampStage.getGroupActiveLampInHolder().addActor(MainScreen1.staticObjectsOnStage.getLamp3_active());

                } else {

                    MainScreen1.lampStage.getGroupUnactiveLampInHolder().addActor(StaticObjects1.this);
                    MainScreen1.lampStage.getGroupActiveLampInHolder().addActor(MainScreen1.staticObjectsOnStage.getLamp3_active());
                    MainScreen1.staticObjects.setFlagLampHolderOccupied(true);

                }
            }
            else if (StaticObjects1.this.getName().equals("lamp4_unactive") & StaticObjects1.this.getX() > 140 & StaticObjects1.this.getX() < 250 &
                    StaticObjects1.this.getY() > 210 & StaticObjects1.this.getY() < 375) {
                StaticObjects1.this.setVis(false);
                MainScreen1.staticObjectsOnStage.getLamp4_active().setVis(true);
                MainScreen1.staticObjectsOnStage.getArrow_lower().rotateBy(-198);

                if (MainScreen1.staticObjects.getFlagLampHolderOccupied()) {

                    MainScreen1.lampStage.getGroupUnactiveLampInHolder().getChildren().get(0).setVisible(true);
                    MainScreen1.lampStage.getGroupUnactiveLamps().addActor(MainScreen1.lampStage.getGroupUnactiveLampInHolder().getChildren().get(0));

                    if (MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).getName().equals("lamp1_unactive")) {
                        MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).setPosition(26f, 279f);
                        MainScreen1.staticObjectsOnStage.getArrow_lower().rotateBy(105);
                    } else if (MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).getName().equals("lamp2_unactive")) {
                        MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).setPosition(26f, 136f);
                        MainScreen1.staticObjectsOnStage.getArrow_lower().rotateBy(65);
                    } else if (MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).getName().equals("lamp3_unactive")) {
                        MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).setPosition(641f, 316f);
                        MainScreen1.staticObjectsOnStage.getArrow_lower().rotateBy(24);
                    } else if (MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).getName().equals("lamp5_unactive")) {
                        MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).setPosition(668f, 111f);
                    }

                    MainScreen1.lampStage.getGroupUnactiveLampInHolder().addActor(StaticObjects1.this);

                    MainScreen1.lampStage.getGroupActiveLampInHolder().clear();
                    MainScreen1.lampStage.getGroupActiveLampInHolder().addActor(MainScreen1.staticObjectsOnStage.getLamp4_active());

                } else {

                    MainScreen1.lampStage.getGroupUnactiveLampInHolder().addActor(StaticObjects1.this);
                    MainScreen1.lampStage.getGroupActiveLampInHolder().addActor(MainScreen1.staticObjectsOnStage.getLamp4_active());
                    MainScreen1.staticObjects.setFlagLampHolderOccupied(true);

                }
            }
            else if (StaticObjects1.this.getName().equals("lamp5_unactive") & StaticObjects1.this.getX() > 140 & StaticObjects1.this.getX() < 250 &
                    StaticObjects1.this.getY() > 200 & StaticObjects1.this.getY() < 365) {
                StaticObjects1.this.setVis(false);
                MainScreen1.staticObjectsOnStage.getLamp5_active().setVis(true);

                if (MainScreen1.staticObjects.getFlagLampHolderOccupied()) {

                    MainScreen1.lampStage.getGroupUnactiveLampInHolder().getChildren().get(0).setVisible(true);
                    MainScreen1.lampStage.getGroupUnactiveLamps().addActor(MainScreen1.lampStage.getGroupUnactiveLampInHolder().getChildren().get(0));

                    if (MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).getName().equals("lamp1_unactive")) {
                        MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).setPosition(26f, 279f);
                        MainScreen1.staticObjectsOnStage.getArrow_lower().rotateBy(105);
                    } else if (MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).getName().equals("lamp2_unactive")) {
                        MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).setPosition(26f, 136f);
                        MainScreen1.staticObjectsOnStage.getArrow_lower().rotateBy(65);
                    } else if (MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).getName().equals("lamp3_unactive")) {
                        MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).setPosition(641f, 316f);
                        MainScreen1.staticObjectsOnStage.getArrow_lower().rotateBy(24);
                    } else if (MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).getName().equals("lamp4_unactive")) {
                        MainScreen1.lampStage.getGroupUnactiveLamps().getChildren().get(4).setPosition(661f, 226f);
                        MainScreen1.staticObjectsOnStage.getArrow_lower().rotateBy(198);
                    }

                    MainScreen1.lampStage.getGroupUnactiveLampInHolder().addActor(StaticObjects1.this);

                    MainScreen1.lampStage.getGroupActiveLampInHolder().clear();
                    MainScreen1.lampStage.getGroupActiveLampInHolder().addActor(MainScreen1.staticObjectsOnStage.getLamp5_active());


                } else {

                    MainScreen1.lampStage.getGroupUnactiveLampInHolder().addActor(StaticObjects1.this);
                    MainScreen1.lampStage.getGroupActiveLampInHolder().addActor(MainScreen1.staticObjectsOnStage.getLamp5_active());
                    MainScreen1.staticObjects.setFlagLampHolderOccupied(true);

                }
            }
        }

        @Override
        public void touchDragged(InputEvent event, float x, float y, int pointer) {

            if (StaticObjects1.this.getName().equals("valve_overlap")) {

                MainScreen1.staticObjects.setDeltaX(x - 100);
                MainScreen1.staticObjects.setDeltaY(y - 100);

                if (x > 100 & y > 100) {
                    MainScreen1.staticObjects.setAngle(270 + (float) (Math.atan(MainScreen1.staticObjects.getDeltaY() / MainScreen1.staticObjects.getDeltaX()) * 180 / 3.1456));
                    rotateValveAndArrow();
                } else if (x < 100 & y > 100) {
                    MainScreen1.staticObjects.setAngle((float) (Math.atan(-MainScreen1.staticObjects.getDeltaX() / MainScreen1.staticObjects.getDeltaY()) * 180 / 3.1456));
                    rotateValveAndArrow();
                } else if (x < 100 & y < 100) {
                    MainScreen1.staticObjects.setAngle(90 + (float) (Math.atan(-MainScreen1.staticObjects.getDeltaY() / -MainScreen1.staticObjects.getDeltaX()) * 180 / 3.1456));
                    rotateValveAndArrow();
                } else if (x > 100 & y < 100) {
                    MainScreen1.staticObjects.setAngle(180 + (float) (Math.atan(MainScreen1.staticObjects.getDeltaX() / -MainScreen1.staticObjects.getDeltaY()) * 180 / 3.1456));
                    rotateValveAndArrow();
                }
            }
            else if (StaticObjects1.this.getName().endsWith("unactive")) {
                StaticObjects1.this.moveBy(x - StaticObjects1.this.getWidth() / 2, y - StaticObjects1.this.getHeight() / 2);
            }
        }
    };

////////////////////////////////////////////////////////SAFE

    EventListener stageSafeIL = new InputListener(){

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            MainMenuScreen.globalReferences.threadKilling("rotationThread");

            if (StaticObjects1.this.getName().equals("rotate_to_the_right")) {
                System.out.println(MainScreen1.staticObjects.getTurnoverRight() + " turnover right");
            }
            else if (StaticObjects1.this.getName().equals("rotate_to_the_left")) {
                System.out.println(MainScreen1.staticObjects.getTurnoverLeft() + " turnovers left");
            }
        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

            System.out.println("(SAFE) my name is: " + StaticObjects1.this.getName());

            if (StaticObjects1.this.getName().equals("rotate_to_the_right")) {

                safeArrowPressed(-1, "right");

                if (!MainScreen1.staticObjects.getFlag2Stage() & MainScreen1.staticObjects.getFlag1Stage()) {
                    if (MainScreen1.staticObjects.getTurnoverLeft() == 59) {
                        MainScreen1.staticObjectsOnStage.getLamp2_green().setVis(true);
                        MainScreen1.staticObjects.setFlag2Stage(true);
                    } else {
                        MainScreen1.staticObjectsOnStage.getLamp2_red().setVis(true);
                        MainScreen1.staticObjects.setFlag2Stage(true);
                    }
                }
                else if (!MainScreen1.staticObjects.getFlag4Stage() & MainScreen1.staticObjects.getFlag3Stage() & MainScreen1.staticObjects.getFlag2Stage()
                        & MainScreen1.staticObjects.getFlag1Stage()) {
                    if (MainScreen1.staticObjects.getTurnoverLeft() == 13) {
                        MainScreen1.staticObjectsOnStage.getLamp4_green().setVis(true);
                        MainScreen1.staticObjects.setFlag4Stage(true);
                    } else {
                        MainScreen1.staticObjectsOnStage.getLamp4_red().setVis(true);
                        MainScreen1.staticObjects.setFlag4Stage(true);
                    }
                }
                else if (!MainScreen1.staticObjects.getFlag6Stage() & MainScreen1.staticObjects.getFlag5Stage() & MainScreen1.staticObjects.getFlag4Stage() & MainScreen1.staticObjects.getFlag3Stage()
                        & MainScreen1.staticObjects.getFlag2Stage() & MainScreen1.staticObjects.getFlag1Stage()) {
                    if (MainScreen1.staticObjects.getTurnoverLeft() == 37) {
                        MainScreen1.staticObjectsOnStage.getLamp6_green().setVis(true);
                        MainScreen1.staticObjects.setFlag6Stage(true);
                    } else {
                        MainScreen1.staticObjectsOnStage.getLamp6_red().setVis(true);
                        MainScreen1.staticObjects.setFlag6Stage(true);
                    }

                    Thread thread2 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                synchronized (this) {

                                    wait(200);

                                    int counter = 0;

                                    for(int i = 0; i < MainScreen1.staticObjectsOnStage.getArrGreenLamps().length; i++){
                                        if(MainScreen1.staticObjectsOnStage.getArrGreenLamps()[i].isVisible()) counter++;
                                    }

                                    if (counter == 6) {
                                        MainScreen1.staticObjectsOnStage.getSafe_closed().setVis(false);
                                        MainScreen1.staticObjectsOnStage.getRoller().setVis(false);
                                        MainScreen1.staticObjectsOnStage.getRing_in_roller().setVis(false);
                                        MainScreen1.staticObjectsOnStage.getRotate_to_the_left().setVis(false);
                                        MainScreen1.staticObjectsOnStage.getRotate_to_the_right().setVis(false);

                                        MainScreen1.staticObjectsOnStage.getSafe_opened().setVis(true);

                                        MainScreen1.activeObjectsOnStage.getCryptex().setVis(true);
                                        MainScreen1.activeObjectsOnStage.getMoney().setVis(true);
                                    } else {

                                        for(int i = 0; i < MainScreen1.staticObjectsOnStage.getArrRedLamps().length; i++){
                                            MainScreen1.staticObjectsOnStage.getArrRedLamps()[i].setVis(false);
                                        }

                                        for(int i = 0; i < arrSafeStageSetters.length; i++){
                                            setSafeStageSetter(i, false);
                                        }

                                        MainScreen1.staticObjects.setTurnoverRight(0);
                                        MainScreen1.staticObjects.setTurnoverLeft(0);
                                        MainScreen1.staticObjectsOnStage.getRoller().setRotation(0);

                                    }

                                    for(int i = 0; i < MainScreen1.staticObjectsOnStage.getArrGreenLamps().length; i++){
                                        MainScreen1.staticObjectsOnStage.getArrGreenLamps()[i].setVis(false);
                                    }
                                }
                            } catch (InterruptedException ex) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    });
                    thread2.start();
                }
                MainScreen1.staticObjects.setTurnoverLeft(0);

            }
            else if (StaticObjects1.this.getName().equals("rotate_to_the_left")) {

                safeArrowPressed(1, "left");

                if (!MainScreen1.staticObjects.getFlag1Stage()) {
                    if (MainScreen1.staticObjects.getTurnoverRight() == 46) {
                        MainScreen1.staticObjectsOnStage.getLamp1_green().setVis(true);
                        MainScreen1.staticObjects.setFlag1Stage(true);
                    } else {
                        MainScreen1.staticObjectsOnStage.getLamp1_red().setVis(true);
                        MainScreen1.staticObjects.setFlag1Stage(true);
                    }
                }
                else if (!MainScreen1.staticObjects.getFlag3Stage() & MainScreen1.staticObjects.getFlag2Stage() & MainScreen1.staticObjects.getFlag1Stage()) {
                    if (MainScreen1.staticObjects.getTurnoverRight() == 82) {
                        MainScreen1.staticObjectsOnStage.getLamp3_green().setVis(true);
                        MainScreen1.staticObjects.setFlag3Stage(true);
                    } else {
                        MainScreen1.staticObjectsOnStage.getLamp3_red().setVis(true);
                        MainScreen1.staticObjects.setFlag3Stage(true);
                    }
                }
                else if (!MainScreen1.staticObjects.getFlag5Stage() & MainScreen1.staticObjects.getFlag4Stage() & MainScreen1.staticObjects.getFlag3Stage() & MainScreen1.staticObjects.getFlag2Stage()
                        & MainScreen1.staticObjects.getFlag1Stage()) {
                    if (MainScreen1.staticObjects.getTurnoverRight() == 68) {
                        MainScreen1.staticObjectsOnStage.getLamp5_green().setVis(true);
                        MainScreen1.staticObjects.setFlag5Stage(true);
                    } else {
                        MainScreen1.staticObjectsOnStage.getLamp5_red().setVis(true);
                        MainScreen1.staticObjects.setFlag5Stage(true);
                    }
                }
                MainScreen1.staticObjects.setTurnoverRight(0);
            }

//exit safe stage

            else if (StaticObjects1.this.getName().equals("back_to_main_stage_from_safe")) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            synchronized (this) {
                                for (int i = 0; i < MainScreen1.staticObjectsOnStage.getArrGreenLamps().length; i++) {
                                    if (MainScreen1.staticObjectsOnStage.getArrGreenLamps()[i].isVisible() | MainScreen1.staticObjectsOnStage.getArrRedLamps()[i].isVisible()) {
                                        MainScreen1.staticObjectsOnStage.getArrGreenLamps()[i].setVis(false);
                                        MainScreen1.staticObjectsOnStage.getArrRedLamps()[i].setVis(false);
                                    }
                                    wait(100);
                                }

                                for (int i = 0; i < arrSafeStageSetters.length; i++) {
                                    setSafeStageSetter(i, false);
                                }

                                MainScreen1.staticObjects.setTurnoverRight(0);
                                MainScreen1.staticObjects.setTurnoverLeft(0);

                                if(MainScreen1.staticObjectsOnStage.getRoller().getRotation() != 0) {
                                    MainScreen1.staticObjectsOnStage.getRoller().setRotation(0);
                                    wait(200);
                                }

                                for (int i = 0; i < MainScreen1.allActorsTableBoxesStage.size; i++) {
                                    MainScreen1.allActorsTableBoxesStage.get(i).setTouchable(Touchable.enabled);
                                }

                                for (int i = 0; i < MainScreen1.allActorsSafeStage.size; i++) {
                                    MainScreen1.allActorsSafeStage.get(i).setTouchable(Touchable.disabled);
                                }

                                MainScreen1.staticObjects.setFlagSafeStageStart(false);
                            }
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                        }
                    }
                });
                thread.start();
            }
            return true;
        }
    };


/////////////////////////////////////////////////////TABLEBOXES

    EventListener stageTableBoxesIL = new InputListener() {

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            System.out.println("(TABLEBOXES) my name is: " + StaticObjects1.this.getName());

//enter safe stage

            if (StaticObjects1.this.getName().equals("safe_overlap")) {

                for (int i = 0; i < MainScreen1.allActorsTableBoxesStage.size; i++) {
                    MainScreen1.allActorsTableBoxesStage.get(i).setTouchable(Touchable.disabled);
                }

                for (int i = 0; i < MainScreen1.allActorsSafeStage.size; i++) {
                    MainScreen1.allActorsSafeStage.get(i).setTouchable(Touchable.enabled);
                }

                MainScreen1.staticObjects.setFlagSafeStageStart(true);

                System.out.println(MainScreen1.staticObjects.getFlagTableBoxesStageStart());
            }
            else if (StaticObjects1.this.getName().equals("box_with_key_overlap") & MainScreen1.staticObjects.getFlagBoxWithKeyOpened()) {
                MainScreen1.staticObjectsOnStage.getBox_without_key().setVis(true);
                if(!MainScreen1.staticObjects.getFlagKeyFromBuffetTaken()) {
                    MainScreen1.activeObjectsOnStage.getKey_from_buffet().setVis(true);
                }
            }
            else if (StaticObjects1.this.getName().equals("box_without_key")) {
                MainScreen1.staticObjectsOnStage.getBox_without_key().setVis(false);
                MainScreen1.activeObjectsOnStage.getKey_from_buffet().setVis(false);
            }
            else if (StaticObjects1.this.getName().equals("box_with_trash_overlap")) {
                MainScreen1.staticObjectsOnStage.getBox_with_trash().setVis(true);
            }
            else if (StaticObjects1.this.getName().equals("box_with_trash")) {
                MainScreen1.staticObjectsOnStage.getBox_with_trash().setVis(false);
            }
            else if(StaticObjects1.this.getName().equals("box_without_lamps_overlap")) {
                MainScreen1.staticObjectsOnStage.getBox_without_lamps().setVis(true);
                if(!MainScreen1.staticObjects.getFlagLampsTaken()){
                    MainScreen1.activeObjectsOnStage.getLamps().setVis(true);
                }
            }
            else if (StaticObjects1.this.getName().equals("box_without_lamps")) {
                MainScreen1.staticObjectsOnStage.getBox_without_lamps().setVis(false);
                if(!MainScreen1.staticObjects.getFlagLampsTaken()){
                    MainScreen1.activeObjectsOnStage.getLamps().setVis(false);
                }
            }

//exit tableboxes stage

            else if (StaticObjects1.this.getName().equals("back_to_main_stage_from_table_boxes")) {

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            synchronized (this) {
                                if (MainScreen1.staticObjectsOnStage.getBox_with_trash().isVisible()) {
                                    MainScreen1.staticObjectsOnStage.getBox_with_trash().setVis(false);
                                    wait(300);
                                }

                                if (MainScreen1.staticObjectsOnStage.getBox_without_key().isVisible()) {
                                    MainScreen1.staticObjectsOnStage.getBox_without_key().setVis(false);
                                    MainScreen1.activeObjectsOnStage.getKey_from_buffet().setVis(false);
                                    wait(300);
                                }
                                if (MainScreen1.staticObjectsOnStage.getBox_without_lamps().isVisible()) {
                                    MainScreen1.staticObjectsOnStage.getBox_without_lamps().setVis(false);
                                    MainScreen1.activeObjectsOnStage.getLamps().setVis(false);
                                    wait(300);
                                }

                                for (int i = 0; i < MainScreen1.allActorsWorkshopStage.size; i++) {
                                    MainScreen1.allActorsWorkshopStage.get(i).setTouchable(Touchable.enabled);
                                }
                                for (int i = 0; i < MainScreen1.allActorsTableBoxesStage.size; i++) {
                                    MainScreen1.allActorsTableBoxesStage.get(i).setTouchable(Touchable.disabled);
                                }

                                MainScreen1.staticObjects.setFlagWorkshopStageStart(true);
                                MainScreen1.staticObjects.setFlagTableBoxesStageStart(false);
                            }
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                        }
                    }
                });
                thread.start();
            }
            return true;
        }
    };

////////////////////////////////////////////////BUFFETBOX

    EventListener stageBuffetBoxIL = new InputListener(){

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            System.out.println("(BUFFETBOX) my name is: " + StaticObjects1.this.getName());

//exit buffetbox stage

            if (StaticObjects1.this.getName().equals("back_to_main_stage_from_buffet_box")) {

                for (int i = 0; i < MainScreen1.allActorsBuffetStage.size; i++) {
                    MainScreen1.allActorsBuffetStage.get(i).setTouchable(Touchable.enabled);
                }

                for (int i = 0; i < MainScreen1.allActorsBuffetBoxStage.size; i++) {
                    MainScreen1.allActorsBuffetBoxStage.get(i).setTouchable(Touchable.disabled);
                }

                MainScreen1.staticObjects.setFlagBuffetStageStart(true);
                MainScreen1.staticObjects.setFlagBuffetBoxStageStart(false);
            }
            return true;
        }
    };

////////////////////////////////////////////////TOOLBAFFLE

    EventListener stageToolBaffleIL = new InputListener(){

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            System.out.println("(TOOLBAFFLE) my name is: " + StaticObjects1.this.getName());

//exit toolbaffle stage

            if (StaticObjects1.this.getName().equals("back_to_main_stage_from_tool_baffle")) {

                for (int i = 0; i < MainScreen1.allActorsWorkshopStage.size; i++) {
                    MainScreen1.allActorsWorkshopStage.get(i).setTouchable(Touchable.enabled);
                }
                for (int i = 0; i < MainScreen1.allActorsToolBaffleStage.size; i++) {
                    MainScreen1.allActorsToolBaffleStage.get(i).setTouchable(Touchable.disabled);
                }

                MainScreen1.staticObjects.setFlagWorkshopStageStart(true);
                MainScreen1.staticObjects.setFlagToolBaffleStageStart(false);
            }
            return true;
        }
    };

    interface interGetter{
        boolean get();
    }

    interface interSetter{
        void set(boolean flag);
    }

    private interGetter[] arrGetters = new interGetter[]{
            new interGetter() {@Override public boolean get() {return getFlagBuffetBoxStageStart();}},
            new interGetter() {@Override public boolean get() {return getFlagBuffetStageStart();}},
            new interGetter() {@Override public boolean get() {return getFlagLampStageStart();}},
            new interGetter() {@Override public boolean get() {return getFlagSafeStageStart();}},
            new interGetter() {@Override public boolean get() {return getFlagTableBoxesStageStart();}},
            new interGetter() {@Override public boolean get() {return getFlagTableStageStart();}},
            new interGetter() {@Override public boolean get() {return getFlagToolBaffleStageStart();}},
            new interGetter() {@Override public boolean get() {return getFlagWorkshopStageStart();}},
            new interGetter() {@Override public boolean get() {return MainMenuScreen.globalReferences.getFlagCryptexStageStart();}},
            new interGetter() {@Override public boolean get() {return MainMenuScreen.globalReferences.getFlagPuzzle1HintActivated();}},
            new interGetter() {@Override public boolean get() {return getFlagLampsTaken();}},
            new interGetter() {@Override public boolean get() {return getFlagLampsOnLamp();}},
            new interGetter() {@Override public boolean get() {return getFlagKeyFromBuffetTaken();}},
            new interGetter() {@Override public boolean get() {return getFlagBoxWithKeyOpened();}},
            new interGetter() {@Override public boolean get() {return MainScreen1.workshopStage.getFlagLampOpened();}},
            new interGetter() {@Override public boolean get() {return MainScreen1.lampStage.getGroupUnactiveLamps().isVisible();}},
    };

    private interSetter[] arrSetters = new interSetter[]{
            new interSetter() {@Override public void set(boolean flag) {setFlagBuffetBoxStageStart(flag);}},
            new interSetter() {@Override public void set(boolean flag) {setFlagBuffetStageStart(flag);}},
            new interSetter() {@Override public void set(boolean flag) {setFlagLampStageStart(flag);}},
            new interSetter() {@Override public void set(boolean flag) {setFlagSafeStageStart(flag);}},
            new interSetter() {@Override public void set(boolean flag) {setFlagTableBoxesStageStart(flag);}},
            new interSetter() {@Override public void set(boolean flag) {setFlagTableStageStart(flag);}},
            new interSetter() {@Override public void set(boolean flag) {setFlagToolBaffleStageStart(flag);}},
            new interSetter() {@Override public void set(boolean flag) {setFlagWorkshopStageStart(flag);}},
            new interSetter() {@Override public void set(boolean flag) {MainMenuScreen.globalReferences.setFlagCryptexStageStart(flag);}},
            new interSetter() {@Override public void set(boolean flag) {MainMenuScreen.globalReferences.setFlagPuzzle1HintActivated(flag);}},
            new interSetter() {@Override public void set(boolean flag) {setFlagLampsTaken(flag);}},
            new interSetter() {@Override public void set(boolean flag) {setFlagLampsOnLamp(flag);}},
            new interSetter() {@Override public void set(boolean flag) {setFlagKeyFromBuffetTaken(flag);}},
            new interSetter() {@Override public void set(boolean flag) {setFlagBoxWithKeyOpened(flag);}},
            new interSetter() {@Override public void set(boolean flag) {MainScreen1.workshopStage.setFlagLampOpened(flag);}},
            new interSetter() {@Override public void set(boolean flag) {MainScreen1.lampStage.getGroupUnactiveLamps().setVisible(flag);}},
    };

    public boolean getGetter(int i){
        return arrGetters[i].get();
    }

    public void setSetter(int i, boolean flag){
        arrSetters[i].set(flag);
    }

    private String[] arrFlagsNames = new String[]{"flagBuffetBoxStageStart", "flagBuffetStageStart", "flagLampStageStart", "flagSafeStageStart",
            "flagTableBoxesStageStart", "flagTableStageStart", "flagToolBaffleStageStart", "flagWorkshopStageStart", "flagCryptexStageStart", "flagPuzzle1HintActivated",
            "flagLampsTaken", "flagLampsOnLamp", "flagKeyFromBuffetTaken", "flagBoxWithKeyOpened", "flagLampOpened", "groupUnactiveLampsVisability"};

    public String[] getArrFlagsNames() {
        return arrFlagsNames;
    }

    interface interSafeStageSetter{
        void set(boolean flag);
    }

    private interSafeStageSetter[] arrSafeStageSetters = new interSafeStageSetter[]{
            new interSafeStageSetter() {@Override public void set(boolean flag) {MainScreen1.staticObjects.setFlag1Stage(flag);}},
            new interSafeStageSetter() {@Override public void set(boolean flag) {MainScreen1.staticObjects.setFlag2Stage(flag);}},
            new interSafeStageSetter() {@Override public void set(boolean flag) {MainScreen1.staticObjects.setFlag3Stage(flag);}},
            new interSafeStageSetter() {@Override public void set(boolean flag) {MainScreen1.staticObjects.setFlag4Stage(flag);}},
            new interSafeStageSetter() {@Override public void set(boolean flag) {MainScreen1.staticObjects.setFlag5Stage(flag);}},
            new interSafeStageSetter() {@Override public void set(boolean flag) {MainScreen1.staticObjects.setFlag6Stage(flag);}}
    };

    public void setSafeStageSetter(int i, boolean flag){
        arrSafeStageSetters[i].set(flag);
    }
}
