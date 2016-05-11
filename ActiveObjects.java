package art.dual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import art.dual.firstLevel.MainScreen1;
import art.dual.secondLevel.MainScreen2;
import art.dual.thirdLevel.MainScreen3;
import art.dual.fourthLevel.MainScreen4;
import art.dual.fourthLevelCOMPLETED.MainScreen4COMPLETED;
import art.dual.fifthLevel.MainScreen5;
import art.dual.sixthLevel.MainScreen6;
import art.dual.mainMenu.MainMenuScreen;

/**
 * This class contains all actions player can perform reffering to
 * active objects (e.g. objects player can take to inventory or add to active cell).
 * Also it describes JSON methods to save current objects condition.
 */
public class ActiveObjects extends Image implements Json.Serializable {

    private int index = -1;
    private float x, y, downX, upX, downY, upY;
    private boolean visability;
    private String name, type;

    public ActiveObjects(){}

    public ActiveObjects(Texture texture) {
        super(texture);
        addListener(activeObjectsIL);
    }

    public float getObjectX() {
        return getX();
    }
    public float getObjectY() {
        return getY();
    }
    public boolean getVis() {
        return visability;
    }
    public String getObjectName() {
        return name;
    }
    public String getObjectType() {
        return type;
    }
    public int getIndex() {
        return index;
    }
    public void setVis(boolean visability) {
        this.visability = visability;
        setVisible(this.visability);
    }
    public void setObjectY(float y) {
        this.y = y;
        setY(this.y);
    }
    public void setObjectX(float x) {
        this.x = x;
        setX(this.x);
    }
    public void setObjectType(String type) {
        this.type = type;
    }
    public void setObjectName(String name) {
        this.name = name;
        setName(this.name);
    }
    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public void write(Json json) {
        json.writeValue("objectX", x);
        json.writeValue("objectY", y);
        json.writeValue("objectVis", visability);
        json.writeValue("objectName", name);
        json.writeValue("objectType", type);
        json.writeValue("objectIndex", index);
}

    @Override
    public void read(Json json, JsonValue jsonData) {
        x = jsonData.getFloat("objectX");
        y = jsonData.getFloat("objectY");
        visability = jsonData.getBoolean("objectVis");
        name = jsonData.getString("objectName");
        type = jsonData.getString("objectType");
        index = jsonData.getInt("objectIndex");
    }

    public void removeBIGandBackground(String name){
        MainMenuScreen.globalReferences.setFlagBIGisVisible(false);
        MainMenuScreen.panelStage.getObjectBackground().setVisible(false);
        MainMenuScreen.panelStage.getClearScreenOverPanelActors().setVisible(false);
        MainMenuScreen.activeObjectsBIG.getActorBIGByName(name).remove();
    }

    public void moveActiveItemToDefault(){
        MoveToAction mta = new MoveToAction();
        mta.setPosition(130, 17);
        MainMenuScreen.panelStage.getGroupActiveItem().getChildren().get(0).addAction(mta);
    }

    public void addObjectToInventory(ActiveObjects aoInI){
        if(MainMenuScreen.panelStage.getGroupObjectsInInventory().getChildren().toArray().length > 0 &
                MainMenuScreen.panelStage.getGroupObjectsInInventory().getChildren().toArray().length < 6) {

            float lastElementInInventoryX = MainMenuScreen.panelStage.getGroupObjectsInInventory().getChildren().toArray()[MainMenuScreen.panelStage.getGroupObjectsInInventory().getChildren().toArray().length - 1].getX();
            aoInI.setPosition(lastElementInInventoryX + 73, -79);
        }
        else if(MainMenuScreen.panelStage.getGroupObjectsInInventory().getChildren().toArray().length >= 6){

            float lastElementInInventoryX = MainMenuScreen.panelStage.getGroupObjectsInInventory().getChildren().toArray()[MainMenuScreen.panelStage.getGroupObjectsInInventory().getChildren().toArray().length - 1].getX();

            for(int i = 0; i < MainMenuScreen.panelStage.getGroupObjectsInInventory().getChildren().toArray().length; i++){
                MainMenuScreen.panelStage.getGroupObjectsInInventory().getChildren().toArray()[i].moveBy(-lastElementInInventoryX + 571 - 73, 0);
            }
            aoInI.setPosition(571, -79);
        }
        else aoInI.setPosition(206, -79);

        MainMenuScreen.panelStage.getGroupObjectsInInventory().addActor(aoInI);
        makeBorderElementsInvisible();
    }

    public void addObjectToInventoryFromStage(){
        ActiveObjects.this.setVis(false);
        String nameOfStageObject = ActiveObjects.this.getName();

        showBlackBackground();
        showBigObject(nameOfStageObject);
        disableAllExceptBIG();

        ActiveObjects aoInI = ActiveObjects.this;
        aoInI = MainMenuScreen.activeObjectsInI.getActorInIByName(nameOfStageObject + "_InI");
        addObjectToInventory(aoInI);
    }

    public void addObjectToInventoryFromActiveCell(ActiveObjects aoInI){
        addObjectToInventory(aoInI);
        MainMenuScreen.globalReferences.setFlagActiveCellOccupied(false);
    }

    public void makeBorderElementsInvisible(){
        for(int i = 0; i < MainMenuScreen.panelStage.getGroupObjectsInInventory().getChildren().toArray().length; i++){
            if(MainMenuScreen.panelStage.getGroupObjectsInInventory().getChildren().toArray()[i].getX() < 206 |
                    MainMenuScreen.panelStage.getGroupObjectsInInventory().getChildren().toArray()[i].getX() > 571){
                MainMenuScreen.panelStage.getGroupObjectsInInventory().getChildren().toArray()[i].setVisible(false);
            }
            else if(MainMenuScreen.panelStage.getGroupObjectsInInventory().getChildren().toArray()[i].getX() >= 206 &
                    MainMenuScreen.panelStage.getGroupObjectsInInventory().getChildren().toArray()[i].getX() <= 571){
                MainMenuScreen.panelStage.getGroupObjectsInInventory().getChildren().toArray()[i].setVisible(true);
            }
        }
    }

    public void showBigObject(String name){
        MainMenuScreen.globalReferences.setFlagBIGisVisible(true);
        MainMenuScreen.activeObjectsBIG.getActorBIGByName(name + "_BIG").setVisible(true);
        MainMenuScreen.panelStage.getStage().addActor(MainMenuScreen.activeObjectsBIG.getActorBIGByName(name + "_BIG"));
    }

    public void disableAllExceptBIG(){
        MainMenuScreen.panelStage.getClearScreenOverPanelActors().setVisible(true);
    }

    public void showBlackBackground(){
        MainMenuScreen.panelStage.getObjectBackground().setVisible(true);
    }

    public void moveRightElementsToLeft(String name){
        for(int i = MainMenuScreen.panelStage.getGroupObjectsInInventory().getChildren().toArray().length - 1; i > 0; i--){
            if(MainMenuScreen.panelStage.getGroupObjectsInInventory().getChildren().toArray()[i].getName().equals(name)){
                break;
            }
            else MainMenuScreen.panelStage.getGroupObjectsInInventory().getChildren().toArray()[i].moveBy(-73, 0);
        }
    }

    public void moveLeftElementsToRight(String name){
        for(int i = 0; i < MainMenuScreen.panelStage.getGroupObjectsInInventory().getChildren().toArray().length; i++){
            if(MainMenuScreen.panelStage.getGroupObjectsInInventory().getChildren().toArray()[i].getName().equals(name)){
                break;
            }
            else MainMenuScreen.panelStage.getGroupObjectsInInventory().getChildren().toArray()[i].moveBy(73, 0);
        }
    }

    public void addObjectToActiveCell(String name, ActiveObjects aoInI){
        boolean firstElementIsVisible = false, lastElementIsVisible = false;

        if(MainMenuScreen.panelStage.getGroupObjectsInInventory().getChildren().toArray()[0].isVisible()){
            firstElementIsVisible = true;
        }

        if(MainMenuScreen.panelStage.getGroupObjectsInInventory().getChildren().toArray()[MainMenuScreen.panelStage.getGroupObjectsInInventory().getChildren().toArray().length - 1].isVisible()){
            lastElementIsVisible = true;
        }

        if((firstElementIsVisible & lastElementIsVisible) |
                (!firstElementIsVisible & !lastElementIsVisible) |
                (firstElementIsVisible & !lastElementIsVisible)){
            moveRightElementsToLeft(name);
        }
        else {
            moveLeftElementsToRight(name);
        }

        MainMenuScreen.panelStage.getGroupObjectsInInventory().findActor(name).setPosition(130, 17);
        MainMenuScreen.panelStage.getGroupObjectsInInventory().findActor(name).remove();

        MainMenuScreen.panelStage.getGroupActiveItem().addActor(aoInI);
        MainMenuScreen.globalReferences.setFlagActiveCellOccupied(true);

        makeBorderElementsInvisible();
    }

    public void addObjectToActiveCellFromInventory(){

        String nameOfInventoryObject = ActiveObjects.this.getName();
        ActiveObjects aoInI = ActiveObjects.this;

        int aoInIIndex = -1;

        for(int i = 0; i < MainMenuScreen.panelStage.getGroupObjectsInInventory().getChildren().toArray().length; i++){
            if(MainMenuScreen.panelStage.getGroupObjectsInInventory().getChildren().toArray()[i].getName().equals(nameOfInventoryObject)){
                aoInIIndex = i;
            }
        }

        if(!MainMenuScreen.globalReferences.getFlagActiveCellOccupied()){
            addObjectToActiveCell(nameOfInventoryObject, aoInI);
        }
        else{
            ActiveObjects aoInA = (ActiveObjects) MainMenuScreen.panelStage.getGroupActiveItem().getChildren().toArray()[0];
            switchInIWithInA(aoInI, aoInA, aoInIIndex);

            if(!MainMenuScreen.globalReferences.getFlagCubeCalled()) {
                if(MainMenuScreen.globalReferences.getLevelNumber() == 4) {
                    if(aoInA.getName().equals("hacksaw_InI") | aoInA.getName().equals("hacksaw_part2_InI")){
                        MainScreen4.cubeStage.getCube_icon().setVisible(false);
                        MainScreen4.staticObjects.setFlagCubeStageStart(false);
                    }
                }
            }
        }

        if(!MainMenuScreen.globalReferences.getFlagCubeCalled()) {
            if(MainMenuScreen.globalReferences.getLevelNumber() == 4) {
                if (ActiveObjects.this.getName().equals("hacksaw_InI") |
                        ActiveObjects.this.getName().equals("hacksaw_part2_InI")) {
                    MainScreen4.staticObjects.setFlagCubeStageStart(true);
                    MainScreen4.cubeStage.getCube_icon().setVisible(true);
                }
            }
        }
    }

    public void switchInIWithInA(ActiveObjects aoInI, ActiveObjects aoInA, int index){
        float inIObjectX = aoInI.getX();
        float inIObjectY = aoInI.getY();
        float inAObjectX = aoInA.getX();
        float inAObjectY = aoInA.getY();

        aoInI.setPosition(inAObjectX, inAObjectY);
        aoInA.setPosition(inIObjectX, inIObjectY);

        MainMenuScreen.panelStage.getGroupActiveItem().getChildren().toArray()[0].remove();
        MainMenuScreen.globalReferences.setFlagActiveCellOccupied(false);
        MainMenuScreen.panelStage.getGroupObjectsInInventory().addActorAt(index, aoInA);

        MainMenuScreen.panelStage.getGroupActiveItem().addActor(aoInI);
        MainMenuScreen.globalReferences.setFlagActiveCellOccupied(true);
    }

    EventListener activeObjectsIL = new InputListener() {

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("(ActiveObjects) My name is ----------> " + ActiveObjects.this.getName());
                System.out.println("(ActiveObjects) My group is ----------> " + ActiveObjects.this.getParent().getName());

                downX = x;
                downY = y;

                if (ActiveObjects.this.getObjectType().equals("STAGE")) {
                    addObjectToInventoryFromStage();

                    if(MainMenuScreen.globalReferences.getLevelNumber() == 1){
                        MainMenuScreen.globalReferences.setItemsWeCanCollect1Level(MainMenuScreen.globalReferences.getItemsWeCanCollect1Level() - 1);
                    }
                    else if(MainMenuScreen.globalReferences.getLevelNumber() == 2){
                        MainMenuScreen.globalReferences.setItemsWeCanCollect2Level(MainMenuScreen.globalReferences.getItemsWeCanCollect2Level() - 1);
                    }
                    else if(MainMenuScreen.globalReferences.getLevelNumber() == 3){
                        MainMenuScreen.globalReferences.setItemsWeCanCollect3Level(MainMenuScreen.globalReferences.getItemsWeCanCollect3Level() - 1);
                    }
                    else if(MainMenuScreen.globalReferences.getLevelNumber() == 4){
                        MainMenuScreen.globalReferences.setItemsWeCanCollect4Level(MainMenuScreen.globalReferences.getItemsWeCanCollect4Level() - 1);
                    }
                    else if(MainMenuScreen.globalReferences.getLevelNumber() == 5){
                        MainMenuScreen.globalReferences.setItemsWeCanCollect5Level(MainMenuScreen.globalReferences.getItemsWeCanCollect5Level() - 1);
                    }
                    else if(MainMenuScreen.globalReferences.getLevelNumber() == 6){
                        MainMenuScreen.globalReferences.setItemsWeCanCollect6Level(MainMenuScreen.globalReferences.getItemsWeCanCollect6Level() - 1);
                    }
                    MainMenuScreen.journalStage.setArrowICRotation();
                }

//1st level exceptions
                if(MainMenuScreen.globalReferences.getLevelNumber() == 1) {
                    if (ActiveObjects.this.getName().equals("key_from_buffet")) {
                        MainScreen1.staticObjects.setFlagKeyFromBuffetTaken(true);
                    }
                    else if (ActiveObjects.this.getName().equals("well_lever")) {
                        MainScreen1.staticObjectsOnStage.getWell_lever_static().setVis(false);
                    }
                    else if (ActiveObjects.this.getName().equals("chain")) {
                        MainScreen1.staticObjectsOnStage.getChain_static().setVis(false);
                    }
                    else if (ActiveObjects.this.getName().equals("hummer")) {
                        MainScreen1.staticObjectsOnStage.getHummer_static().setVis(false);
                    }
                    else if (ActiveObjects.this.getName().equals("insulation_tape")) {
                        MainScreen1.staticObjectsOnStage.getInsulation_tape_static().setVis(false);
                    }
                    else if (ActiveObjects.this.getName().equals("screwdriver_X")) {
                        MainScreen1.staticObjectsOnStage.getScrewdriver_X_static().setVis(false);
                    }
                    else if (ActiveObjects.this.getName().equals("screwdriver_I")) {
                        MainScreen1.staticObjectsOnStage.getScrewdriver_I_static().setVis(false);
                    }
                    else if (ActiveObjects.this.getName().equals("knife_unlighted")) {
                        MainScreen1.activeObjectsOnStage.getKnife_lighted().setVis(false);
                        MainScreen1.staticObjectsOnStage.getKnife_static().setVis(false);
                    }
                    else if (ActiveObjects.this.getName().equals("knife_lighted")) {
                        MainScreen1.activeObjectsOnStage.getKnife_unlighted().setVis(false);
                        MainScreen1.staticObjectsOnStage.getKnife_static().setVis(false);
                    }
                    else if (ActiveObjects.this.getName().equals("binoculars_unlighted")) {
                        MainScreen1.activeObjectsOnStage.getBinoculars_lighted().setVis(false);
                        MainScreen1.staticObjectsOnStage.getBinoculars_static().setVis(true);
                    }
                    else if (ActiveObjects.this.getName().equals("binoculars_lighted")) {
                        MainScreen1.activeObjectsOnStage.getBinoculars_unlighted().setVis(false);
                        MainScreen1.staticObjectsOnStage.getBinoculars_static().setVis(true);
                    }
                    else if (ActiveObjects.this.getName().equals("lamps")) {
                        MainScreen1.staticObjects.setFlagLampsTaken(true);
                    }
                }

//2nd level exceptions
                else if(MainMenuScreen.globalReferences.getLevelNumber() == 2) {
                    if (ActiveObjects.this.getName().equals("cogwheel_lower")) {
                        MainScreen2.staticObjectsOnStage.getCogwheel_lower_picture().setVis(false);
                    }
                    else if (ActiveObjects.this.getName().equals("lighter")) {
                        MainScreen2.staticObjects.setFlagLighterTaken(true);
                        MainScreen2.staticObjectsOnStage.getStone_button_pressed().setVis(false);

                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    synchronized (this) {

                                        MainScreen2.towerPuzzleStage.getStoneButtonREVERSED().stateTime = 0;
                                        MainScreen2.towerPuzzleStage.getStoneButtonREVERSED().setVisible(true);
                                        MainScreen2.towerPuzzleStage.getStoneButtonREVERSED().act(Gdx.graphics.getDeltaTime());

                                        wait(500);

                                        MainScreen2.towerPuzzleStage.getStoneButtonREVERSED().setVisible(false);
                                        MainScreen2.staticObjectsOnStage.getStone_button_unpressed().setVis(true);
                                    }
                                } catch (InterruptedException ex) {
                                    Thread.currentThread().interrupt();
                                }
                            }
                        });
                        thread.start();
                    }
                    else if (ActiveObjects.this.getName().equals("gas_cylinder")) {
                        MainScreen2.staticObjectsOnStage.getGas_cylinder_static().setVis(false);
                        MainScreen2.staticObjectsOnStage.getGas_cylinder_picture().setVis(false);
                    }
                    else if (ActiveObjects.this.getName().equals("jerrican")) {
                        MainScreen2.staticObjectsOnStage.getJerrican_static().setVis(false);
                        MainScreen2.staticObjectsOnStage.getJerrican_shed_absent().setVis(true);
                    }
                    else if (ActiveObjects.this.getName().equals("kerosene_lamp")) {
                        MainScreen2.staticObjectsOnStage.getKerosene_lamp_static().setVis(false);
                    }
                }

//3rd level exceptions
                else if(MainMenuScreen.globalReferences.getLevelNumber() == 3) {
                    if (ActiveObjects.this.getName().equals("hose_lighted") | ActiveObjects.this.getName().equals("hose_unlighted")) {
                        MainScreen3.staticObjectsOnStage.getHose_untaken_on_main().setVis(false);
                    }
                    else if (ActiveObjects.this.getName().equals("motor_lighted")) {
                        MainScreen3.staticObjectsOnStage.getHelix_lighted_shedrightside().setVis(false);
                        MainScreen3.staticObjectsOnStage.getHelix_mounted_lighted_table().setVis(false);
                        MainScreen3.staticObjectsOnStage.getMotor_lighted_on_main().setVis(false);
                        MainScreen3.staticObjectsOnStage.getMotor_lighted_shedrightside().setVis(false);
                    }
                    else if (ActiveObjects.this.getName().equals("motor_unlighted")) {
                        MainScreen3.staticObjectsOnStage.getHelix_unlighted_shedrightside().setVis(false);
                        MainScreen3.staticObjectsOnStage.getHelix_mounted_unlighted_table().setVis(false);
                        MainScreen3.staticObjectsOnStage.getMotor_unlighted_on_main().setVis(false);
                        MainScreen3.staticObjectsOnStage.getMotor_unlighted_shedrightside().setVis(false);
                    }
                }

//4th level exceptions
                else if(MainMenuScreen.globalReferences.getLevelNumber() == 4) {
                    if (ActiveObjects.this.getName().equals("hacksaw")) {
                        MainScreen4.staticObjectsOnStage.getHacksaw_attic().setVis(false);
                    }
                    else if (ActiveObjects.this.getName().equals("pipe")) {
                        MainScreen4.staticObjectsOnStage.getPipe_shed().setVis(false);
                    }
                    else if (ActiveObjects.this.getName().equals("wheel")) {
                        MainScreen4.staticObjectsOnStage.getWheel_on_moto_shed().setVis(false);
                    }
                    else if (ActiveObjects.this.getName().equals("flask")) {
                        MainScreen4.staticObjectsOnStage.getFlask_pier().setVis(false);
                    }
                    else if (ActiveObjects.this.getName().equals("fishing_rod")) {
                        MainScreen4.staticObjectsOnStage.getFishing_rod_pier().setVis(false);
                    }
                    else if (ActiveObjects.this.getName().equals("fish")) {
                        MainScreen4.fishingStage.getFishing_rod_with_fish().setVis(false);
                        MainMenuScreen.activeObjects.addObjectToInventory(MainMenuScreen.activeObjectsInI.getActorInIByName("fishing_rod_InI"));

                        MainMenuScreen.globalReferences.setFlagFishCaught(true);
                        MainMenuScreen.saving.getPrefsLevelNumber().putBoolean("flagFishCaught", MainMenuScreen.globalReferences.getFlagFishCaught());
                        MainMenuScreen.saving.getPrefsLevelNumber().flush();

                        MainMenuScreen.globalReferences.exitPuzzleStage();
                        MainMenuScreen.globalReferences.setFlagPuzzle3HintActivated(true);
                    }
                    else if (ActiveObjects.this.getName().equals("primus")) {
                        MainScreen4.staticObjectsOnStage.getPrimus_picture().setVis(false);
                    }
                    else if (ActiveObjects.this.getName().equals("belt")) {
                        MainScreen4.staticObjectsOnStage.getBelt_room().setVis(false);
                    }
                }

//5th level exceptions
                else if(MainMenuScreen.globalReferences.getLevelNumber() == 5) {

                    if (ActiveObjects.this.getName().equals("album")) {
                        MainScreen5.staticObjects.setFlagAlbumTaken(true);
                    }
                }

//4thCOMPLETED level exceptions
                else if(MainMenuScreen.globalReferences.getLevelNumber() == -4) {
                    if (ActiveObjects.this.getName().equals("fish")) {
                        MainScreen4COMPLETED.fishingStage.getFishing_rod_with_fish().setVis(false);
                        MainMenuScreen.activeObjects.addObjectToInventory(MainMenuScreen.activeObjectsInI.getActorInIByName("fishing_rod_InI"));

                        MainMenuScreen.globalReferences.setFlagFishCaught(true);
                        MainMenuScreen.saving.getPrefsLevelNumber().putBoolean("flagFishCaught", MainMenuScreen.globalReferences.getFlagFishCaught());
                        MainMenuScreen.saving.getPrefsLevelNumber().flush();
                    }
                }

//6th level exceptions
                else if(MainMenuScreen.globalReferences.getLevelNumber() == 6) {

                    if (ActiveObjects.this.getName().equals("spoon")) {
                        MainScreen6.staticObjectsOnStage.getSpoon_table().setVis(false);
                    }
                    else if (ActiveObjects.this.getName().equals("jelly_jar")) {
                        MainScreen6.staticObjectsOnStage.getJelly_jar_table().setVis(false);
                    }
                    else if (ActiveObjects.this.getName().equals("key_from_dressing_room")) {
                        MainScreen6.staticObjectsOnStage.getPaper_sheet_on_floor().setVis(false);
                        MainScreen6.staticObjectsOnStage.getPaper_sheet_overlap_for_typing().setVis(false);
                    }
                    else if (ActiveObjects.this.getName().equals("pwc_from_table_box")) {
                        MainScreen6.staticObjects.setFlagPwcFromTableBoxTaken(true);
                    }
                }

                return true;
            }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            System.out.println("(ActiveObjects) My coords are: " + getX() + " " + getY());

            upX = x;
            upY = y;

            if (ActiveObjects.this.getObjectType().equals("BIG")) {
                if(ActiveObjects.this.getName().equals("hacksaw_assembled_BIG")){
                    MainMenuScreen.activeObjectsBIG.getActorBIGByName("hacksaw_assembled_BIG").remove();
                }
                else removeBIGandBackground(ActiveObjects.this.getName());
            }
            else if(ActiveObjects.this.getParent().getName() != null) {
                if (ActiveObjects.this.getParent().getName().equals("groupCube")) {
                    if(ActiveObjects.this.getX() < 399 & ActiveObjects.this.getY() > 217){
                        MainMenuScreen.globalReferences.setFlagFirstCubeCellOccupied(false);
                    }
                    else if(ActiveObjects.this.getX() > 399 & ActiveObjects.this.getY() > 217){
                        MainMenuScreen.globalReferences.setFlagSecondCubeCellOccupied(false);
                    }
                    else if(ActiveObjects.this.getX() < 399 & ActiveObjects.this.getY() < 217){
                        MainMenuScreen.globalReferences.setFlagThirdCubeCellOccupied(false);
                    }
                    else if(ActiveObjects.this.getX() > 399 & ActiveObjects.this.getY() < 217){
                        MainMenuScreen.globalReferences.setFlagFourthCubeCellOccupied(false);
                    }
                    MainMenuScreen.activeObjects.addObjectToInventory(ActiveObjects.this);
                }
                else if (ActiveObjects.this.getParent().getName().equals("ObjectsInInventory") &
                        Math.abs(upX - downX) < 5 & Math.abs(upY - downY) < 5) {
                        addObjectToActiveCellFromInventory();
                }
                else if (ActiveObjects.this.getParent().getName().equals("ActiveItem")) {
                    if (getX() > 182 & getX() < 605 & getY() > -10 & getY() < 50 & MainMenuScreen.globalReferences.getFlagInventoryAppearance()) {
                        if(MainMenuScreen.globalReferences.getLevelNumber() == 4) {
                            if (ActiveObjects.this.getName().equals("hacksaw_InI") | ActiveObjects.this.getName().equals("hacksaw_part2_InI")) {
                                if(MainScreen4.cubeStage.getCube_icon().isVisible()) {
                                    MainScreen4.cubeStage.getCube_icon().setVisible(false);
                                    MainScreen4.staticObjects.setFlagCubeStageStart(false);
                                }
                            }
                        }
                        ActiveObjects aoInA = (ActiveObjects) MainMenuScreen.panelStage.getGroupActiveItem().getChildren().toArray()[0];
                        addObjectToInventoryFromActiveCell(aoInA);
                    }
                    else {
                        if(MainMenuScreen.globalReferences.getFlagCubeCalled()){
                            if(getX() > 200 & getX() < 363 & getY() > 237 & getY() < 347 & !MainMenuScreen.globalReferences.getFlagFirstCubeCellOccupied()){
                                MainMenuScreen.globalReferences.setFlagFirstCubeCellOccupied(true);
                                MainScreen4.cubeStage.getGroupCube().addActor(ActiveObjects.this);
                                MainMenuScreen.globalReferences.setFlagActiveCellOccupied(false);
                                ActiveObjects.this.setPosition(281.5f, 292);
                            }
                            else if(getX() > 373 & getX() < 530 & getY() > 237 & getY() < 347 & !MainMenuScreen.globalReferences.getFlagSecondCubeCellOccupied()){
                                MainMenuScreen.globalReferences.setFlagSecondCubeCellOccupied(true);
                                MainScreen4.cubeStage.getGroupCube().addActor(ActiveObjects.this);
                                MainMenuScreen.globalReferences.setFlagActiveCellOccupied(false);
                                ActiveObjects.this.setPosition(451.5f, 292);
                            }
                            else if(getX() > 200 & getX() < 363 & getY() > 117 & getY() < 227 & !MainMenuScreen.globalReferences.getFlagThirdCubeCellOccupied()){
                                MainMenuScreen.globalReferences.setFlagThirdCubeCellOccupied(true);
                                MainScreen4.cubeStage.getGroupCube().addActor(ActiveObjects.this);
                                MainMenuScreen.globalReferences.setFlagActiveCellOccupied(false);
                                ActiveObjects.this.setPosition(281.5f, 172);
                            }
                            else if(getX() > 373 & getX() < 530 & getY() > 117 & getY() < 227 & !MainMenuScreen.globalReferences.getFlagFourthCubeCellOccupied()){
                                MainMenuScreen.globalReferences.setFlagFourthCubeCellOccupied(true);
                                MainScreen4.cubeStage.getGroupCube().addActor(ActiveObjects.this);
                                MainMenuScreen.globalReferences.setFlagActiveCellOccupied(false);
                                ActiveObjects.this.setPosition(451.5f, 172);
                            } else {
                                moveActiveItemToDefault();
                            }

                        }
                        else if (MainMenuScreen.globalReferences.getLevelNumber() == 1) {
                            if (MainScreen1.staticObjects.getFlagTableBoxesStageStart()) {
                                MainScreen1.staticObjects.openTableBox(ActiveObjects.this.getName(), getX(), getY());
                            } else if (MainScreen1.staticObjects.getFlagBuffetStageStart()) {
                                MainScreen1.staticObjects.openBuffetBox(ActiveObjects.this.getName(), getX(), getY());
                            } else if (MainScreen1.staticObjects.getFlagLampStageStart() & MainMenuScreen.panelStage.getGroupActiveItem().getChildren().get(0).getName().equals("lamps_InI")) {
                                MainScreen1.staticObjects.putLampsOnLampStage(ActiveObjects.this.getName(), getX(), getY());
                            } else if (MainScreen1.staticObjects.getFlagLampStageStart() & MainMenuScreen.panelStage.getGroupActiveItem().getChildren().get(0).getName().equals("valve_for_lamp_InI")) {
                                MainScreen1.staticObjects.mountValveOnLamp(ActiveObjects.this.getName(), getX(), getY());
                            } else {
                                moveActiveItemToDefault();
                            }
                        }
                        else if (MainMenuScreen.globalReferences.getLevelNumber() == 2) {
                            if (MainScreen2.staticObjects.getFlagCellarStageStart()) {
                                if (ActiveObjects.this.getName().equals("lighter_InI") & !MainScreen2.staticObjects.getFlagKeroseneLampFilled() &
                                        MainScreen2.staticObjectsOnStage.getKerosene_lamp_on_hook().isVisible()) {
                                    MainMenuScreen.globalReferences.hintMessageAppearingMethod();
                                    MainMenuScreen.globalReferences.hintAppearingMethod();
                                    moveActiveItemToDefault();
                                } else if (!MainScreen2.staticObjectsOnStage.getKerosene_lamp_on_hook().isVisible()) {
                                    MainScreen2.staticObjects.hangKeroseneLamp(ActiveObjects.this.getName(), getX(), getY());
                                } else if (MainScreen2.staticObjectsOnStage.getKerosene_lamp_on_hook().isVisible() &
                                        MainMenuScreen.panelStage.getGroupActiveItem().getChildren().get(0).getName().equals("jerrican_InI")) {
                                    MainScreen2.staticObjects.fillKeroseneLamp(ActiveObjects.this.getName(), getX(), getY());
                                } else if (MainScreen2.staticObjects.getFlagKeroseneLampFilled()) {
                                    MainScreen2.staticObjects.lightKeroseneLamp(ActiveObjects.this.getName(), getX(), getY());
                                } else {
                                    moveActiveItemToDefault();
                                }
                            } else if (MainScreen2.staticObjects.getFlagLuggageBootStageStart()) {
                                MainScreen2.staticObjects.openLuggageBoot(ActiveObjects.this.getName(), getX(), getY());
                            } else if (MainScreen2.staticObjects.getFlagTowerPuzzleStageStart()) {
                                MainScreen2.staticObjects.mountBigFishRoller(ActiveObjects.this.getName(), getX(), getY());
                            } else if (MainScreen2.staticObjects.getFlagWellMechanismStageStart()) {
                                if (MainMenuScreen.panelStage.getGroupActiveItem().getChildren().get(0).getName().equals("cogwheel_higher_InI")) {
                                    MainScreen2.staticObjects.mountHigherCogwheel(ActiveObjects.this.getName(), getX(), getY());
                                } else if (MainMenuScreen.panelStage.getGroupActiveItem().getChildren().get(0).getName().equals("cogwheel_middle_InI")) {
                                    MainScreen2.staticObjects.mountMiddleCogwheel(ActiveObjects.this.getName(), getX(), getY());
                                } else if (MainMenuScreen.panelStage.getGroupActiveItem().getChildren().get(0).getName().equals("cogwheel_lower_InI")) {
                                    MainScreen2.staticObjects.mountLowerCogwheel(ActiveObjects.this.getName(), getX(), getY());
                                } else if (MainMenuScreen.panelStage.getGroupActiveItem().getChildren().get(0).getName().equals("cogwheel_outer_InI")) {
                                    MainScreen2.staticObjects.mountOuterCogwheel(ActiveObjects.this.getName(), getX(), getY());
                                } else if (MainMenuScreen.panelStage.getGroupActiveItem().getChildren().get(0).getName().equals("chain_InI")) {
                                    MainScreen2.staticObjects.mountChain(ActiveObjects.this.getName(), getX(), getY());
                                } else if (MainMenuScreen.panelStage.getGroupActiveItem().getChildren().get(0).getName().equals("well_lever_InI")) {
                                    MainScreen2.staticObjects.mountWellLevel(ActiveObjects.this.getName(), getX(), getY());
                                } else {
                                    moveActiveItemToDefault();
                                }
                            } else if (MainScreen2.staticObjects.getFlagShedEntranceStageStart()) {
                                MainScreen2.staticObjects.openShedDoor(ActiveObjects.this.getName(), getX(), getY());
                            } else {
                                moveActiveItemToDefault();
                            }
                        }
                        else if (MainMenuScreen.globalReferences.getLevelNumber() == 3) {
                            if (MainScreen3.staticObjects.getFlagBoatStageStart()) {
                                MainScreen3.staticObjects.cutTheRope(ActiveObjects.this.getName(), getX(), getY());
                            } else if (MainScreen3.staticObjects.getFlagTableStageStart()) {
                                MainScreen3.staticObjects.mountHelixOnMotor(ActiveObjects.this.getName(), getX(), getY());
                            } else if (MainScreen3.staticObjects.getFlagDashboardStageStart()) {
                                if (MainMenuScreen.panelStage.getGroupActiveItem().getChildren().get(0).getName().equals("steering_wheel_InI")) {
                                    MainScreen3.staticObjects.mountSteeringWheel(getX(), getY());
                                } else if (MainMenuScreen.panelStage.getGroupActiveItem().getChildren().get(0).getName().equals("key_from_boat_InI")) {
                                    MainScreen3.staticObjects.insertKeyFromBoat(getX(), getY());
                                } else if (MainMenuScreen.panelStage.getGroupActiveItem().getChildren().get(0).getName().equals("boat_button_InI")) {
                                    MainScreen3.staticObjects.mountBoatButton(getX(), getY());
                                } else {
                                    moveActiveItemToDefault();
                                }
                            } else if (MainScreen3.staticObjects.getFlagBoatBackStageStart()) {
                                MainScreen3.staticObjects.mountMotor(ActiveObjects.this.getName(), getX(), getY());
                            } else if (MainScreen3.staticObjects.getFlagFuelMechanismStageStart()) {
                                MainScreen3.staticObjects.fillFuelMechanism(ActiveObjects.this.getName(), getX(), getY());
                            } else if (MainScreen3.staticObjects.getFlagElectricalPanelSecondLevelStageStart()) {
                                MainScreen3.staticObjects.mountFuses(ActiveObjects.this.getName(), getX(), getY());
                            } else if (MainScreen3.staticObjects.getFlagControlPanelStageStart()) {
                                MainScreen3.staticObjects.putWiresOnStage(ActiveObjects.this.getName(), getX(), getY());
                            } else {
                                moveActiveItemToDefault();
                            }
                        }
                        else if(MainMenuScreen.globalReferences.getLevelNumber() == 4){
                            if (MainScreen4.staticObjects.getFlagAtticStageStart()) {
                                if (MainMenuScreen.panelStage.getGroupActiveItem().getChildren().get(0).getName().equals("rope_InI")) {
                                    MainScreen4.staticObjects.repairRightPan(ActiveObjects.this.getName(), getX(), getY());
                                }
                                else if (MainMenuScreen.panelStage.getGroupActiveItem().getChildren().get(0).getName().equals("bag_InI")) {
                                    MainScreen4.staticObjects.mountBagOnPan(ActiveObjects.this.getName(), getX(), getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (MainScreen4.staticObjects.getFlagLockStageStart()) {
                                MainScreen4.staticObjects.hitLockWithHummer(ActiveObjects.this.getName(), getX(), getY());
                            }
                            else if (MainScreen4.staticObjects.getFlagFrontOfMotoStageStart()) {
                                if (MainMenuScreen.panelStage.getGroupActiveItem().getChildren().get(0).getName().equals("wheel_InI")) {
                                    MainScreen4.staticObjects.mountWheel(getX(), getY());
                                }
                                else if(MainMenuScreen.panelStage.getGroupActiveItem().getChildren().get(0).getName().equals("spanner_InI")) {
                                    MainScreen4.staticObjects.regulateWheel(getX(), getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (MainScreen4.staticObjects.getFlagLockOnChestStageStart()) {
                                if (MainMenuScreen.panelStage.getGroupActiveItem().getChildren().get(0).getName().equals("links_InI")) {
                                    MainScreen4.staticObjects.putLinksOnChestLock(getX(), getY());
                                }
                                else if(MainMenuScreen.panelStage.getGroupActiveItem().getChildren().get(0).getName().equals("jar_InI")) {
                                    MainScreen4.staticObjects.putCogwheelsOnChestLock(getX(), getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (MainScreen4.staticObjects.getFlagForestmanStageStart()) {
                                if (MainMenuScreen.panelStage.getGroupActiveItem().getChildren().get(0).getName().equals("money_InI") &
                                        MainScreen4.staticObjects.getForestmanPhase().equals("0")) {
                                    MainScreen4.staticObjects.giveMoneyToForestman(getX(), getY());
                                }
                                else if(MainMenuScreen.panelStage.getGroupActiveItem().getChildren().get(0).getName().startsWith("binoculars") &
                                        MainScreen4.staticObjects.getFlagFishingRodTaped()) {
                                    MainScreen4.staticObjects.giveBinocularsToForestman(ActiveObjects.this.getName(), getX(), getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (MainScreen4.staticObjects.getFlagFishingStageStart()) {
                                if (MainMenuScreen.panelStage.getGroupActiveItem().getChildren().get(0).getName().equals("fishing_rod_InI")) {
                                    MainScreen4.staticObjects.getFishingRod(getX(), getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (MainScreen4.staticObjects.getFlagAtticStairsStageStart()) {
                                if (MainMenuScreen.panelStage.getGroupActiveItem().getChildren().get(0).getName().equals("stair1_InI")) {
                                    MainScreen4.staticObjects.mountStair1(getX(), getY());
                                }
                                else if (MainMenuScreen.panelStage.getGroupActiveItem().getChildren().get(0).getName().equals("stair2_InI")) {
                                    MainScreen4.staticObjects.mountStair2(getX(), getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (MainScreen4.staticObjects.getFlagAtticDoorStageStart()) {
                                if (MainMenuScreen.panelStage.getGroupActiveItem().getChildren().get(0).getName().equals("bolt_cutter_InI")) {
                                    MainScreen4.staticObjects.cutAtticDoorChain(getX(), getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (MainScreen4.staticObjects.getFlagShedEntranceStageStart()) {
                                if (MainMenuScreen.panelStage.getGroupActiveItem().getChildren().get(0).getName().equals("hacksaw_assembled_InI")) {
                                    MainScreen4.staticObjects.cutShedDoorLock(getX(), getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else {
                                moveActiveItemToDefault();
                            }
                        }
                        else if (MainMenuScreen.globalReferences.getLevelNumber() == 5) {

                            if (MainScreen5.staticObjects.getFlagSuitcasesStageStart()) {
                                if (MainMenuScreen.panelStage.getGroupActiveItem().getChildren().get(0).getName().equals("keys_InI")) {
                                    MainScreen5.staticObjects.putKeysOnStage(getX(), getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (MainScreen5.staticObjects.getFlagLuggageBootStageStart()) {
                                if (MainMenuScreen.panelStage.getGroupActiveItem().getChildren().get(0).getName().equals("clip_InI")) {
                                    MainScreen5.staticObjects.unlockLuggageBoot(getX(), getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (MainScreen5.staticObjects.getFlagLuggageSpaceDoorStageStart()) {
                                if (MainMenuScreen.panelStage.getGroupActiveItem().getChildren().get(0).getName().equals("valve_for_door_InI")) {
                                    MainScreen5.staticObjects.mountMiddleValve(getX(), getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else {
                                moveActiveItemToDefault();
                            }
                        }
                        else if (MainMenuScreen.globalReferences.getLevelNumber() == -4) {
                            if (MainScreen4COMPLETED.staticObjects.getFlagForestmanStageStart()) {
                                if (MainMenuScreen.panelStage.getGroupActiveItem().getChildren().get(0).getName().equals("album_InI")) {
                                    MainScreen4COMPLETED.staticObjects.giveAlbumToForestman(getX(), getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (MainScreen4COMPLETED.staticObjects.getFlagFishingStageStart()) {
                                if (MainMenuScreen.panelStage.getGroupActiveItem().getChildren().get(0).getName().equals("fishing_rod_InI")) {
                                    MainScreen4COMPLETED.staticObjects.getFishingRod(getX(), getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else {
                                moveActiveItemToDefault();
                            }
                        }
                        else if (MainMenuScreen.globalReferences.getLevelNumber() == 6) {

                            if (MainScreen6.staticObjects.getFlagDoghouseStageStart()) {
                                if (MainMenuScreen.panelStage.getGroupActiveItem().getChildren().get(0).getName().equals("fish_InI")) {
                                    MainScreen6.staticObjects.putFishOnPlate(getX(), getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (MainScreen6.staticObjects.getFlagDressingRoomDoorStageStart()) {
                                if (MainMenuScreen.panelStage.getGroupActiveItem().getChildren().get(0).getName().equals("paper_sheet_InI")) {
                                    MainScreen6.staticObjects.putPaperSheetOnFloor(getX(), getY());
                                }
                                else if (MainMenuScreen.panelStage.getGroupActiveItem().getChildren().get(0).getName().equals("key_from_dressing_room_InI")) {
                                    MainScreen6.staticObjects.openDressingRoomDoor(getX(), getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            } else if (MainScreen6.staticObjects.getFlagTelephoneStageStart()) {
                                if (MainMenuScreen.panelStage.getGroupActiveItem().getChildren().get(0).getName().equals("telephone_dial_InI")) {
                                    MainScreen6.staticObjects.mountTelephoneDial(getX(), getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (MainScreen6.staticObjects.getFlagKeyholeStageStart()) {
                                if (MainMenuScreen.panelStage.getGroupActiveItem().getChildren().get(0).getName().equals("spoon_InI")) {
                                    MainScreen6.staticObjects.pushKeyOutOfKeyhole(getX(), getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (MainScreen6.staticObjects.getFlagMazeStageStart()) {
                                if (MainMenuScreen.panelStage.getGroupActiveItem().getChildren().get(0).getName().equals("key_from_chest_InI")) {
                                    MainScreen6.staticObjects.unlockChest(getX(), getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else {
                                moveActiveItemToDefault();
                            }
                        }
                        else {
                            moveActiveItemToDefault();
                        }
                    }
                }
            }
        }

        @Override
        public void touchDragged(InputEvent event, float x, float y, int pointer) {

            if(ActiveObjects.this.getParent().getName() != null) {
                if (ActiveObjects.this.getParent().getName().equals("ActiveItem")) {
                    moveBy(x - ActiveObjects.this.getWidth() / 2, y - ActiveObjects.this.getHeight() / 2);
                }
                else if (ActiveObjects.this.getParent().getName().equals("ObjectsInInventory")) {
                    if (y > 0 & y < 80) {
                        if (x > 100) {
                            if (MainMenuScreen.panelStage.getGroupObjectsInInventory().getChildren().toArray()[0].getX() < 206) {
                                for (int i = 0; i < MainMenuScreen.panelStage.getGroupObjectsInInventory().getChildren().toArray().length; i++) {
                                    MainMenuScreen.panelStage.getGroupObjectsInInventory().getChildren().toArray()[i].moveBy(73, 0);
                                    makeBorderElementsInvisible();
                                }
                            }
                        } else if (x < -50) {
                            if (MainMenuScreen.panelStage.getGroupObjectsInInventory().getChildren().toArray()[MainMenuScreen.panelStage.getGroupObjectsInInventory().getChildren().toArray().length - 1].getX() > 571) {
                                for (int i = 0; i < MainMenuScreen.panelStage.getGroupObjectsInInventory().getChildren().toArray().length; i++) {
                                    MainMenuScreen.panelStage.getGroupObjectsInInventory().getChildren().toArray()[i].moveBy(-73, 0);
                                    makeBorderElementsInvisible();
                                }
                            }
                        }
                    }
                    else if (y > 150 & !MainMenuScreen.globalReferences.getFlagCubeCalled() & !MainMenuScreen.globalReferences.getFlagBIGisVisible()) {
                        if (ActiveObjects.this.getName().equals("cryptex_InI")) {
                            for (int i = 0; i < MainMenuScreen.cryptexStage.getAllActorsCryptexStage().size; i++) {
                                MainMenuScreen.cryptexStage.getAllActorsCryptexStage().get(i).setTouchable(Touchable.enabled);
                            }
                            MainMenuScreen.globalReferences.setFlagCryptexStageStart(true);
                        }
                        else {
                            String name = ActiveObjects.this.getName().replace("_InI", "");

                            showBlackBackground();
                            showBigObject(name);
                            disableAllExceptBIG();
                        }
                    }
                }
            }
        }
    };
}

