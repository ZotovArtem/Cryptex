package art.dual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

import java.io.IOException;
import java.io.InputStream;

import art.dual.eightLevel.MainScreen8;
import art.dual.firstLevel.MainScreen1;
import art.dual.fourthLevel.MainScreen4;
import art.dual.ninthLevel.MainScreen9;
import art.dual.secondLevel.MainScreen2;
import art.dual.seventhLevel.MainScreen7;
import art.dual.sixthLevel.MainScreen6;
import art.dual.sixthLevelCOMPLETED.MainScreen6COMLETED;
import art.dual.thirdLevel.MainScreen3;

/**
 * This class describes active objects (e.g. objects player can interact with) behaviour
 */
public class ActiveListener {
    private String name = new String();
    private Actor currentAO = new Actor();
    private int cell1CubeCounter = -1, cell2CubeCounter = -1, cell3CubeCounter = -1, cell4CubeCounter = -1;
    private boolean flagObjectsInIMoved, flagObjectWentBigger;
    private Texture textureBIG, cakeTexture1, cakeTexture2, cakeTexture3, cakeTexture4, cakeTexture5, cakeTexture6, cakeTexture7, cakeTexture8, cakeTexture9, cakeTexture10, cakeTexture11, cakeTexture12;
    private ActiveObjects actorBIG, cakeActor1, cakeActor2, cakeActor3, cakeActor4, cakeActor5, cakeActor6, cakeActor7, cakeActor8, cakeActor9, cakeActor10, cakeActor11, cakeActor12;
    private ActiveObjects[] arrCakeActors = new ActiveObjects[]{cakeActor1, cakeActor2, cakeActor3, cakeActor4, cakeActor5, cakeActor6, cakeActor7, cakeActor8, cakeActor9, cakeActor10, cakeActor11, cakeActor12};
    private Texture[] arrCakeTextures = new Texture[]{cakeTexture1, cakeTexture2, cakeTexture3, cakeTexture4, cakeTexture5, cakeTexture6, cakeTexture7, cakeTexture8, cakeTexture9, cakeTexture10, cakeTexture11, cakeTexture12};


    public ActiveListener(){
    }

    public void removeBIGandBackground(String name){
        Cryptex.main.getGlobalReferences().setFlagBIGisVisible(false);
        Cryptex.main.getPanelStage().getObjectBackground().setVisible(false);
        Cryptex.main.getPanelStage().getClearScreenOverPanelActors().setVisible(false);

        if(name.startsWith("cake_")){
            for(int i = 0; i < arrCakeActors.length; i++){
                if(arrCakeActors[i] != null) arrCakeActors[i].remove();
                if(arrCakeTextures[i] != null) arrCakeTextures[i].dispose();
            }
        }
        else {
            actorBIG.remove();
            textureBIG.dispose();
        }

        if(Cryptex.main.getGlobalReferences().getLevelNumber() == 6) {
            if (name.equals("maze_map_BIG")){
                Cryptex.main.getMainScreen6().getMazeStage().getMap_indicator().remove();
                Cryptex.main.getMainScreen6().getMazeStage().getMap_indicator().setVisible(false);
            }
        }
    }

    public void moveActiveItemToDefault(){
        MoveToAction mta = new MoveToAction();
        mta.setPosition(130, 17);
        Cryptex.main.getPanelStage().getGroupActiveItem().getChildren().get(0).addAction(mta);
    }

    public void addObjectToInventory(Actor aoInI){
        if(Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().toArray().length > 0 &
                Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().toArray().length < 6) {

            float lastElementInInventoryX = Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().toArray()[Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().toArray().length - 1].getX();
            aoInI.setPosition(lastElementInInventoryX + 73, -79);
        }
        else if(Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().toArray().length >= 6){

            float lastElementInInventoryX = Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().toArray()[Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().toArray().length - 1].getX();

            for(int i = 0; i < Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().toArray().length; i++){
                Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().toArray()[i].moveBy(-lastElementInInventoryX + 571 - 73, 0);
            }
            aoInI.setPosition(571, -79);
        }
        else aoInI.setPosition(206, -79);

        Cryptex.main.getPanelStage().getGroupObjectsInInventory().addActor(aoInI);
        makeBorderElementsInvisible();
    }

    public void addObjectToInventoryFromStage(){
        Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getGs().getTaking_to_inventory(), 0.7f);
        ((ActiveObjects) currentAO).setVis(false);

        showBlackBackground();
        showBigObject(name, "inv");
        disableAllExceptBIG();

        if(name.equals("cake"))addObjectToInventory(Cryptex.main.getActiveObjectsInI().getCake_InI());
        else addObjectToInventory(Cryptex.main.getActiveObjectsInI().getActorInIByName(name + "_InI"));
    }

    public void addObjectToInventoryFromActiveCell(Actor aoInI){
        Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getGs().getGoes_bigger(), 0.5f);
        if(aoInI.getName().equals("latchkey_InI") && Cryptex.main.getMainScreen6().getStaticObjects().getFlagLockWithLatchkeyStageStart()){
            Cryptex.main.getMainScreen6().getLockWithLatchkeyStage().getLatchkey().setVisible(false);
        }
        addObjectToInventory(aoInI);
        Cryptex.main.getGlobalReferences().setFlagActiveCellOccupied(false);
    }

    public void makeBorderElementsInvisible(){
        for(int i = 0; i < Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().toArray().length; i++){
            if(Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().toArray()[i].getX() < 206 |
                    Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().toArray()[i].getX() > 571){
                Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().toArray()[i].setVisible(false);
            }
            else if(Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().toArray()[i].getX() >= 206 &
                    Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().toArray()[i].getX() <= 571){
                Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().toArray()[i].setVisible(true);
            }
        }
    }

    void loadCakeActor(String name){
        InputStream is;
        try {
            is = Cryptex.expFile.getInputStream("BIG/cake/" + name + ".png");
            Gdx.files.local("BIGfiles/" + name + ".png").write(is, false);
            is.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    void createCakeActor(int index, String name){
        arrCakeTextures[index] = new Texture(Gdx.files.local("BIGfiles/" + name + ".png"));
        arrCakeActors[index] = new ActiveObjects(arrCakeTextures[index]);
        arrCakeActors[index].setName(name);
        arrCakeActors[index].setObjectX(400 - arrCakeActors[index].getWidth() / 2);
        arrCakeActors[index].setObjectY(240 - arrCakeActors[index].getHeight() / 2);
        arrCakeActors[index].setObjectType("BIG");
        arrCakeActors[index].addListener(Cryptex.main.getActiveListener().getActiveObjectsIL());
        Cryptex.main.getPanelStage().getStage().addActor(arrCakeActors[index]);
    }

    public void showBigObject(String name, String param){
        Cryptex.main.getGlobalReferences().setFlagBIGisVisible(true);

        if(name.equals("cake")){
            Group cakeGroup = new Group(); cakeGroup.setName("cakeGroup");

            System.out.println("cake_shortcake_lower_light_BIG " + Cryptex.main.getMainScreen7().getFlags().getFlagCake_shortcake_lower_light_BIG());
            System.out.println("cake_shortcake_lower_dark_BIG " + Cryptex.main.getMainScreen7().getFlags().getFlagCake_shortcake_lower_dark_BIG());
            System.out.println("cake_cream_lower_pink_BIG " + Cryptex.main.getMainScreen7().getFlags().getFlagCake_cream_lower_pink_BIG());
            System.out.println("cake_cream_lower_white_BIG " + Cryptex.main.getMainScreen7().getFlags().getFlagCake_cream_lower_white_BIG());
            System.out.println("cake_shortcake_upper_dark_BIG " + Cryptex.main.getMainScreen7().getFlags().getFlagCake_shortcake_upper_dark_BIG());
            System.out.println("cake_shortcake_upper_light_BIG " + Cryptex.main.getMainScreen7().getFlags().getFlagCake_shortcake_upper_light_BIG());
            System.out.println("cake_cream_upper_pink_BIG " + Cryptex.main.getMainScreen7().getFlags().getFlagCake_cream_upper_pink_BIG());
            System.out.println("cake_cream_upper_white_BIG " + Cryptex.main.getMainScreen7().getFlags().getFlagCake_cream_upper_white_BIG());
            System.out.println("cake_decoration_berries_BIG " + Cryptex.main.getMainScreen7().getFlags().getFlagCake_decoration_berries_BIG());
            System.out.println("cake_decoration_kiwis_BIG " + Cryptex.main.getMainScreen7().getFlags().getFlagCake_decoration_kiwis_BIG());
            System.out.println("cake_decoration_oranges_BIG " + Cryptex.main.getMainScreen7().getFlags().getFlagCake_decoration_oranges_BIG());
            System.out.println("cake_decoration_strawberries_BIG " + Cryptex.main.getMainScreen7().getFlags().getFlagCake_decoration_strawberries_BIG());

            if(Cryptex.main.getMainScreen7().getFlags().getFlagCake_shortcake_lower_light_BIG()){
                loadCakeActor("cake_shortcake_lower_light_BIG");
                createCakeActor(0, "cake_shortcake_lower_light_BIG");
            }
            if(Cryptex.main.getMainScreen7().getFlags().getFlagCake_shortcake_lower_dark_BIG()){
                loadCakeActor("cake_shortcake_lower_dark_BIG");
                createCakeActor(1, "cake_shortcake_lower_dark_BIG");
            }
            if(Cryptex.main.getMainScreen7().getFlags().getFlagCake_cream_lower_pink_BIG()){
                loadCakeActor("cake_cream_lower_pink_BIG");
                createCakeActor(2, "cake_cream_lower_pink_BIG");
            }
            if(Cryptex.main.getMainScreen7().getFlags().getFlagCake_cream_lower_white_BIG()){
                loadCakeActor("cake_cream_lower_white_BIG");
                createCakeActor(3, "cake_cream_lower_white_BIG");
            }
            if(Cryptex.main.getMainScreen7().getFlags().getFlagCake_shortcake_upper_dark_BIG()){
                loadCakeActor("cake_shortcake_upper_dark_BIG");
                createCakeActor(4, "cake_shortcake_upper_dark_BIG");
            }
            if(Cryptex.main.getMainScreen7().getFlags().getFlagCake_shortcake_upper_light_BIG()){
                loadCakeActor("cake_shortcake_upper_light_BIG");
                createCakeActor(5, "cake_shortcake_upper_light_BIG");
            }
            if(Cryptex.main.getMainScreen7().getFlags().getFlagCake_cream_upper_pink_BIG()){
                loadCakeActor("cake_cream_upper_pink_BIG");
                createCakeActor(6, "cake_cream_upper_pink_BIG");
            }
            if(Cryptex.main.getMainScreen7().getFlags().getFlagCake_cream_upper_white_BIG()){
                loadCakeActor("cake_cream_upper_white_BIG");
                createCakeActor(7, "cake_cream_upper_white_BIG");
            }
            if(Cryptex.main.getMainScreen7().getFlags().getFlagCake_decoration_berries_BIG()){
                loadCakeActor("cake_decoration_berries_BIG");
                createCakeActor(8, "cake_decoration_berries_BIG");
            }
            if(Cryptex.main.getMainScreen7().getFlags().getFlagCake_decoration_kiwis_BIG()){
                loadCakeActor("cake_decoration_kiwis_BIG");
                createCakeActor(9, "cake_decoration_kiwis_BIG");
            }
            if(Cryptex.main.getMainScreen7().getFlags().getFlagCake_decoration_oranges_BIG()){
                loadCakeActor("cake_decoration_oranges_BIG");
                createCakeActor(10, "cake_decoration_oranges_BIG");
            }
            if(Cryptex.main.getMainScreen7().getFlags().getFlagCake_decoration_strawberries_BIG()){
                loadCakeActor("cake_decoration_strawberries_BIG");
                createCakeActor(11, "cake_decoration_strawberries_BIG");
            }
        }
        else {
            InputStream is;
            String nameBIG = name + "_BIG", nameBIGext = name + "_BIG";

            if (Cryptex.main.getGlobalReferences().getLevelNumber() == 1) {
                if (name.equals("knife_lighted") | name.equals("knife_unlighted"))
                    nameBIGext = "knife_BIG";
                else if (name.equals("binoculars_lighted") | name.equals("binoculars_unlighted"))
                    nameBIGext = "binoculars_BIG";
            } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 3) {
                if (name.equals("hose_lighted") | name.equals("hose_unlighted"))
                    nameBIGext = "hose_BIG";
                else if (name.equals("motor_lighted") | name.equals("motor_unlighted"))
                    nameBIGext = "motor_BIG";
            } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 7) {
                if (name.equals("butter1") | name.equals("butter2") | name.equals("butter3"))
                    nameBIGext = "butter_BIG";
            }

            try {
                is = Cryptex.expFile.getInputStream("BIG/" + nameBIGext + ".png");
                Gdx.files.local("BIGfiles/" + nameBIG + ".png").write(is, false);
                is.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

            textureBIG = new Texture(Gdx.files.local("BIGfiles/" + name + "_BIG.png"));
            actorBIG = new ActiveObjects(textureBIG);
            actorBIG.setName(nameBIG);
            if (name.equals("maze_map")) {
                actorBIG.setObjectX(144);
                actorBIG.setObjectY(91);
            } else {
                actorBIG.setObjectX(400 - actorBIG.getWidth() / 2);
                actorBIG.setObjectY(240 - actorBIG.getHeight() / 2);
            }
            actorBIG.setObjectType("BIG");
            actorBIG.addListener(Cryptex.main.getActiveListener().getActiveObjectsIL());

            if (!param.equals("cube")){
                System.out.println(".............................................INV: " + nameBIG);
                Cryptex.main.getPanelStage().getStage().addActor(actorBIG);
            }
            else {
                System.out.println(".............................................CUBE: " + nameBIG);
                Cryptex.main.getCubeStage().getStage().addActor(actorBIG);
                actorBIG.setZIndex(Cryptex.main.getCubeStage().getGroupReflection().getZIndex() + 1);
                Cryptex.main.getActiveListener().addObjectToInventory(Cryptex.main.getActiveObjectsInI().getActorInIByName(name + "_InI"));
                Cryptex.main.getCubeStage().cleanCubeCells();
            }
        }
    }

    public void disableAllExceptBIG(){
        Cryptex.main.getPanelStage().getClearScreenOverPanelActors().setVisible(true);
    }

    public void showBlackBackground(){
        Cryptex.main.getPanelStage().getObjectBackground().setVisible(true);
    }

    public void moveRightElementsToLeft(String name){
        for(int i = Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().toArray().length - 1; i > 0; i--){
            if(Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().toArray()[i].getName().equals(name)){
                break;
            }
            else Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().toArray()[i].moveBy(-73, 0);
        }
    }

    public void moveLeftElementsToRight(String name){
        for(int i = 0; i < Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().toArray().length; i++){
            if(Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().toArray()[i].getName().equals(name)){
                break;
            }
            else Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().toArray()[i].moveBy(73, 0);
        }
    }

    public void addObjectToActiveCell(String name, Actor aoInI){
        boolean firstElementIsVisible = false, lastElementIsVisible = false;

        if(Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().toArray()[0].isVisible()){
            firstElementIsVisible = true;
        }

        if(Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().toArray()[Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().toArray().length - 1].isVisible()){
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

        Cryptex.main.getPanelStage().getGroupObjectsInInventory().findActor(name).setPosition(130, 17);
        Cryptex.main.getPanelStage().getGroupObjectsInInventory().findActor(name).remove();

        Cryptex.main.getPanelStage().getGroupActiveItem().addActor(aoInI);
        Cryptex.main.getGlobalReferences().setFlagActiveCellOccupied(true);

        makeBorderElementsInvisible();
    }

    public void addThisToActiveCell(Actor actor){

        String name = actor.getName();

        int aoInIIndex = -1;

        for(int i = 0; i < Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().toArray().length; i++){
            if(Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().toArray()[i].getName().equals(name)){
                aoInIIndex = i;
            }
        }

        if(!Cryptex.main.getGlobalReferences().getFlagActiveCellOccupied()){
            addObjectToActiveCell(name, actor);
        }
        else{
            Actor aoInA = Cryptex.main.getPanelStage().getGroupActiveItem().getChildren().toArray()[0];
            switchInIWithInA(actor, aoInA, aoInIIndex);
        }
    }

    public void addObjectToActiveCellFromInventory(){
        Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getGs().getGoes_bigger(), 0.5f);
        String nameOfInventoryObject = name;
        Actor aoInI = currentAO;

        int aoInIIndex = -1;

        for(int i = 0; i < Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().toArray().length; i++){
            if(Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().toArray()[i].getName().equals(nameOfInventoryObject)){
                aoInIIndex = i;
            }
        }

        if(name.equals("bottle_with_potion_InI")) Cryptex.main.getPanelStage().getPower().setVisible(true);
        else if(Cryptex.main.getGlobalReferences().getLevelNumber() == 6) {
            if (name.equals("latchkey_InI") && Cryptex.main.getMainScreen6().getStaticObjects().getFlagLockWithLatchkeyStageStart()) {
                Cryptex.main.getMainScreen6().getLockWithLatchkeyStage().getLatchkey().setVisible(true);
                Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen6().getSound().getStick_latchkey(), 1);
            }
        }


        if(!Cryptex.main.getGlobalReferences().getFlagActiveCellOccupied()){
            addObjectToActiveCell(nameOfInventoryObject, aoInI);
        }
        else{
            Actor aoInA = Cryptex.main.getPanelStage().getGroupActiveItem().getChildren().toArray()[0];
            switchInIWithInA(aoInI, aoInA, aoInIIndex);
            String aoInAname = aoInA.getName();

            if(!Cryptex.main.getGlobalReferences().getFlagCubeCalled()) {
                if(Cryptex.main.getGlobalReferences().getLevelNumber() == 4) {
                    if(aoInA.getName().equals("hacksaw_InI") | aoInA.getName().equals("hacksaw_part2_InI")){
                        Cryptex.main.getCubeStage().getCube_icon().setVisible(false);
                        Cryptex.main.getGlobalReferences().setFlagCubeStageStart(false);
                    }
                }
                else if(Cryptex.main.getGlobalReferences().getLevelNumber() == 7) {
                    if(aoInAname.startsWith("egg") | aoInAname.equals("beaker_filled_InI") |
                            aoInAname.startsWith("butter") | aoInAname.equals("cocoa_InI") | aoInAname.equals("yellow_jelly_jar_InI") | aoInAname.equals("jelly_jar_InI") |
                            aoInAname.equals("milk_InI") | aoInAname.equals("flour_weighed_InI") | aoInAname.equals("salt_weighed_InI") | aoInAname.equals("sugar_weighed_InI") |
                            aoInAname.equals("yeast_weighed_InI")){
                        Cryptex.main.getCubeStage().getCube_icon().setVisible(false);
                        Cryptex.main.getGlobalReferences().setFlagCubeStageStart(false);
                    }
                }
                else if(Cryptex.main.getGlobalReferences().getLevelNumber() == 8) {
                    if(aoInAname.equals("nails_InI") | aoInAname.equals("shovel_bottom_InI") | aoInAname.equals("shovel_top_InI") | aoInAname.equals("shovel_top2_InI") |
                            aoInAname.equals("hummer2_InI")){
                        Cryptex.main.getCubeStage().getCube_icon().setVisible(false);
                        Cryptex.main.getGlobalReferences().setFlagCubeStageStart(false);
                    }
                }
            }
        }

        if(!Cryptex.main.getGlobalReferences().getFlagCubeCalled()) {
            if(Cryptex.main.getGlobalReferences().getLevelNumber() == 4) {
                if (name.equals("hacksaw_InI") | name.equals("hacksaw_part2_InI")) {
                    Cryptex.main.getGlobalReferences().setFlagCubeStageStart(true);
                    Cryptex.main.getCubeStage().getCube_icon().setVisible(true);
                }
            }
            else if(Cryptex.main.getGlobalReferences().getLevelNumber() == 7) {
                if(name.startsWith("egg") | name.equals("beaker_filled_InI") | name.startsWith("butter") | name.equals("cocoa_InI") | name.equals("yellow_jelly_jar_InI")
                        | name.equals("jelly_jar_InI") | name.equals("milk_InI") | name.equals("flour_weighed_InI") | name.equals("salt_weighed_InI")
                        | name.equals("sugar_weighed_InI") | name.equals("yeast_weighed_InI")){
                    Cryptex.main.getGlobalReferences().setFlagCubeStageStart(true);
                    Cryptex.main.getCubeStage().getCube_icon().setVisible(true);
                }
            }
            else if(Cryptex.main.getGlobalReferences().getLevelNumber() == 8) {
                if(name.equals("nails_InI") | name.equals("shovel_bottom_InI") | name.equals("shovel_top_InI") | name.equals("shovel_top2_InI") | name.equals("hummer2_InI")){
                    Cryptex.main.getGlobalReferences().setFlagCubeStageStart(true);
                    Cryptex.main.getCubeStage().getCube_icon().setVisible(true);
                }
            }
        }
    }

    public void switchInIWithInA(Actor aoInI, Actor aoInA, int index){
        float inIObjectX = aoInI.getX();
        float inIObjectY = aoInI.getY();
        float inAObjectX = aoInA.getX();
        float inAObjectY = aoInA.getY();

        aoInI.setPosition(inAObjectX, inAObjectY);
        aoInA.setPosition(inIObjectX, inIObjectY);

        Cryptex.main.getPanelStage().getGroupActiveItem().getChildren().toArray()[0].remove();
        Cryptex.main.getGlobalReferences().setFlagActiveCellOccupied(false);
        Cryptex.main.getPanelStage().getGroupObjectsInInventory().addActorAt(index, aoInA);

        Cryptex.main.getPanelStage().getGroupActiveItem().addActor(aoInI);
        Cryptex.main.getGlobalReferences().setFlagActiveCellOccupied(true);
    }

    EventListener activeObjectsIL = new InputListener() {

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            name = event.getListenerActor().getName();
            currentAO = event.getListenerActor();

            System.out.println("(ActiveObjects) My name is ----------> " + name);
            System.out.println("(ActiveObjects) My group is ----------> " + currentAO.getParent().getName());

            if(!currentAO.getName().equals("cake_InI") & !currentAO.getName().equals("cake_BIG")) {
                if (((ActiveObjects) currentAO).getObjectType().equals("STAGE")) {
                    addObjectToInventoryFromStage();
                    if (Cryptex.main.getGlobalReferences().getLevelNumber() == 1) {
                        Cryptex.main.getGlobalReferences().setItemsWeCanCollect1Level(Cryptex.main.getGlobalReferences().getItemsWeCanCollect1Level() - 1);
                    } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 2) {
                        Cryptex.main.getGlobalReferences().setItemsWeCanCollect2Level(Cryptex.main.getGlobalReferences().getItemsWeCanCollect2Level() - 1);
                    } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 3) {
                        Cryptex.main.getGlobalReferences().setItemsWeCanCollect3Level(Cryptex.main.getGlobalReferences().getItemsWeCanCollect3Level() - 1);
                    } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 4) {
                        Cryptex.main.getGlobalReferences().setItemsWeCanCollect4Level(Cryptex.main.getGlobalReferences().getItemsWeCanCollect4Level() - 1);
                    } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 5) {
                        Cryptex.main.getGlobalReferences().setItemsWeCanCollect5Level(Cryptex.main.getGlobalReferences().getItemsWeCanCollect5Level() - 1);
                    } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 6) {
                        Cryptex.main.getGlobalReferences().setItemsWeCanCollect6Level(Cryptex.main.getGlobalReferences().getItemsWeCanCollect6Level() - 1);
                    } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 7 && (name.equals("beaker") | name.equals("kitchen_knife") | name.equals("rolling_pin") |
                            name.equals("spanner_for_oven") | name.equals("tap_valve") | name.startsWith("recipe"))) {
                        Cryptex.main.getGlobalReferences().setItemsWeCanCollect7Level(Cryptex.main.getGlobalReferences().getItemsWeCanCollect7Level() - 1);
                    } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == -6) {
                        Cryptex.main.getGlobalReferences().setItemsWeCanCollect6CompLevel(Cryptex.main.getGlobalReferences().getItemsWeCanCollect6CompLevel() - 1);
                    } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 8) {
                        Cryptex.main.getGlobalReferences().setItemsWeCanCollect8Level(Cryptex.main.getGlobalReferences().getItemsWeCanCollect8Level() - 1);
                    } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 9) {
                        Cryptex.main.getGlobalReferences().setItemsWeCanCollect9Level(Cryptex.main.getGlobalReferences().getItemsWeCanCollect9Level() - 1);
                    }
                    Cryptex.main.getJournalStage().setArrowICRotation();
                }
            }

//1st level exceptions
            if(Cryptex.main.getGlobalReferences().getLevelNumber() == 1) {
                if (name.equals("key_from_buffet")) {
                    Cryptex.main.getMainScreen1().getStaticObjects().setFlagKeyFromBuffetTaken(true);
                } else if (name.equals("well_lever")) {
                    MainScreen1.staticObjectsOnStage.getWell_lever_static().setVis(false);
                } else if (name.equals("chain")) {
                    MainScreen1.staticObjectsOnStage.getChain_static().setVis(false);
                } else if (name.equals("hummer")) {
                    MainScreen1.staticObjectsOnStage.getHummer_static().setVis(false);
                } else if (name.equals("insulation_tape")) {
                    MainScreen1.staticObjectsOnStage.getInsulation_tape_static().setVis(false);
                } else if (name.equals("screwdriver_X")) {
                    MainScreen1.staticObjectsOnStage.getScrewdriver_X_static().setVis(false);
                } else if (name.equals("screwdriver_I")) {
                    MainScreen1.staticObjectsOnStage.getScrewdriver_I_static().setVis(false);
                } else if (name.equals("knife_unlighted")) {
                    MainScreen1.activeObjectsOnStage.getKnife_lighted().setVis(false);
                    MainScreen1.staticObjectsOnStage.getKnife_static().setVis(false);
                } else if (name.equals("knife_lighted")) {
                    MainScreen1.activeObjectsOnStage.getKnife_unlighted().setVis(false);
                    MainScreen1.staticObjectsOnStage.getKnife_static().setVis(false);
                } else if (name.equals("binoculars_unlighted")) {
                    MainScreen1.activeObjectsOnStage.getBinoculars_lighted().setVis(false);
                    MainScreen1.staticObjectsOnStage.getBinoculars_static().setVis(true);
                } else if (name.equals("binoculars_lighted")) {
                    MainScreen1.activeObjectsOnStage.getBinoculars_unlighted().setVis(false);
                    MainScreen1.staticObjectsOnStage.getBinoculars_static().setVis(true);
                } else if (name.equals("lamps")) {
                    Cryptex.main.getMainScreen1().getStaticObjects().setFlagLampsTaken(true);
                } else if (name.equals("cryptex")) {
                    Cryptex.main.getMainScreen1().getStaticObjects().setFlagCryptexTaken(true);
                } else if (name.equals("money")) {
                    Cryptex.main.getMainScreen1().getStaticObjects().setFlagMoneyTaken(true);
                }
            }

//2nd level exceptions
            else if(Cryptex.main.getGlobalReferences().getLevelNumber() == 2) {
                if (name.equals("cogwheel_lower")) MainScreen2.staticObjectsOnStage.getCogwheel_lower_picture().setVis(false);
                else if (name.equals("lighter")) {
                    Cryptex.main.getMainScreen2().getStaticObjects().setFlagLighterTaken(true);
                    Cryptex.main.getMainScreen2().getStaticObjects().playButtonAnim(1);
                } else if (name.equals("gas_cylinder")) {
                    MainScreen2.staticObjectsOnStage.getGas_cylinder_static().setVis(false);
                    MainScreen2.staticObjectsOnStage.getGas_cylinder_picture().setVis(false);
                } else if (name.equals("jerrican")) {
                    MainScreen2.staticObjectsOnStage.getJerrican_static().setVis(false);
                    MainScreen2.staticObjectsOnStage.getJerrican_shed_absent().setVis(true);
                    MainScreen2.staticObjectsOnStage.getElpa_jerrican().setVis(false);
                } else if (name.equals("kerosene_lamp")) {
                    MainScreen2.staticObjectsOnStage.getKerosene_lamp_static().setVis(false);
                    if(!Cryptex.main.getMainScreen2().getStaticObjects().getFlagKeroseneLampFC()){
                        Cryptex.main.getMainScreen2().getStaticObjects().setFlagKeroseneLampFC(true);
                        Cryptex.main.getJo().addIndexToAL("kerosene_lamp_FC_1", Cryptex.main.getGlobalReferences().getLevelNumber(), 3);
                    }
                } else if (name.equals("letter")) {
                    MainScreen2.staticObjectsOnStage.getKerosene_lamp_static().setVis(false);
                    if(!Cryptex.main.getMainScreen2().getStaticObjects().getFlagLetterFC()){
                        Cryptex.main.getMainScreen2().getStaticObjects().setFlagLetterFC(true);
                        Cryptex.main.getJo().addIndexToAL("letter_FC_1", Cryptex.main.getGlobalReferences().getLevelNumber(), 6);
                    }
                }
                else if (name.equals("bolt_cutter")) Cryptex.main.getMainScreen2().getStaticObjects().setFlagBoltCutterTaken(true);
                else if (name.equals("cogwheel_outer")) Cryptex.main.getMainScreen2().getStaticObjects().setFlagCogwheelOuterTaken(true);
                else if (name.equals("roller_with_fish")) Cryptex.main.getMainScreen2().getStaticObjects().setFlagBigFishRollerTaken(true);
            }

//3rd level exceptions
            else if(Cryptex.main.getGlobalReferences().getLevelNumber() == 3) {
                if (name.equals("hose_lighted") | name.equals("hose_unlighted")) {
                    MainScreen3.staticObjectsOnStage.getHose_untaken_on_main().setVis(false);
                } else if (name.equals("motor_lighted")) {
                    MainScreen3.staticObjectsOnStage.getHelix_lighted_shedrightside().setVis(false);
                    MainScreen3.staticObjectsOnStage.getHelix_mounted_lighted_table().setVis(false);
                    MainScreen3.staticObjectsOnStage.getMotor_lighted_on_main().setVis(false);
                    MainScreen3.staticObjectsOnStage.getMotor_lighted_shedrightside().setVis(false);
                } else if (name.equals("motor_unlighted")) {
                    MainScreen3.staticObjectsOnStage.getHelix_unlighted_shedrightside().setVis(false);
                    MainScreen3.staticObjectsOnStage.getHelix_mounted_unlighted_table().setVis(false);
                    MainScreen3.staticObjectsOnStage.getMotor_unlighted_on_main().setVis(false);
                    MainScreen3.staticObjectsOnStage.getMotor_unlighted_shedrightside().setVis(false);
                } else if (name.equals("boat_button")) {
                    if(MainScreen3.staticObjectsOnStage.getMain_stage_lighted().isVisible()) MainScreen3.staticObjectsOnStage.getBoat_button_taken_shedrightside_lighted().setVis(true);
                    else MainScreen3.staticObjectsOnStage.getBoat_button_taken_shedrightside_unlighted().setVis(true);
                } else if (name.equals("helix")) {
                    MainScreen3.staticObjectsOnStage.getHelix_boat().setVis(false);
                } else if (name.equals("steering_wheel")) {
                    MainScreen3.staticObjectsOnStage.getSteering_wheel_untaken_lighted_on_main().setVis(false);
                }
            }

//4th level exceptions
            else if(Cryptex.main.getGlobalReferences().getLevelNumber() == 4) {
                if (name.equals("hacksaw")) {
                    if(!Cryptex.main.getMainScreen4().getStaticObjects().getFlagFirstHacksawPartFound())
                        Cryptex.main.getMainScreen4().getStaticObjects().setFlagFirstHacksawPartFound(true);
                    else Cryptex.main.getJo().addIndexToAL("second_hacksaw_part_got_1", Cryptex.main.getGlobalReferences().getLevelNumber(), 7);

                    MainScreen4.staticObjectsOnStage.getHacksaw_attic().setVis(false);
                } else if (name.equals("hacksaw_part2")) {
                    if(!Cryptex.main.getMainScreen4().getStaticObjects().getFlagFirstHacksawPartFound())
                        Cryptex.main.getMainScreen4().getStaticObjects().setFlagFirstHacksawPartFound(true);
                    else Cryptex.main.getJo().addIndexToAL("second_hacksaw_part_got_1", Cryptex.main.getGlobalReferences().getLevelNumber(), 7);
                } else if (name.equals("pipe")) {
                    MainScreen4.staticObjectsOnStage.getPipe_shed().setVis(false);
                } else if (name.equals("wheel")) {
                    MainScreen4.staticObjectsOnStage.getWheel_on_moto_shed().setVis(false);
                } else if (name.equals("flask")) {
                    MainScreen4.staticObjectsOnStage.getFlask_pier().setVis(false);
                } else if (name.equals("fishing_rod")) {
                    MainScreen4.staticObjectsOnStage.getFishing_rod_pier().setVis(false);
                } else if (name.equals("fish")) {
                    Cryptex.main.getFlags456().setFlagFishCaught(true);
                    Cryptex.main.getMainScreen4().getFishingStage().getFishing_rod_with_fish().setVis(false);
                    Cryptex.main.getActiveListener().addObjectToInventory(Cryptex.main.getActiveObjectsInI().getActorInIByName("fishing_rod_InI"));
                    Cryptex.main.getGlobalReferences().exitPuzzleStage();
                    Cryptex.main.getGlobalReferences().setFlagPuzzle3HintActivated(true);
                } else if (name.equals("primus")) {
                    MainScreen4.staticObjectsOnStage.getPrimus_picture().setVis(false);
                    MainScreen4.staticObjectsOnStage.getPrimus_room().setVis(false);
                } else if (name.equals("belt")) {
                    MainScreen4.staticObjectsOnStage.getBelt_room().setVis(false);
                } else if (name.equals("bag")) {
                    MainScreen4.staticObjectsOnStage.getBag_yard().setVis(false);
                }
            }

//5th level exceptions
            else if(Cryptex.main.getGlobalReferences().getLevelNumber() == 5) {
                if (name.equals("album")) {

                    if(!Cryptex.main.getFlags456().getFlagAlbumReceived()){
                        Cryptex.main.getFlags456().setFlagAlbumReceived(true);
                        Cryptex.main.getJo().addIndexToAL("album_received_1", Cryptex.main.getGlobalReferences().getLevelNumber(), 5);
                    }

                    Cryptex.main.getFlags456().setFlagAlbumTaken(true);
                } else if (name.equals("keys")) {
                    Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen5().getSound().getGet_keys(), 0.65f);
                }
            }

//4thCOMPLETED level exceptions
            else if(Cryptex.main.getGlobalReferences().getLevelNumber() == -4) {
                if (name.equals("fish")) {
                    Cryptex.main.getFlags456().setFlagFishCaught(true);
                    Cryptex.main.getMainScreen4COMPLETED().getFishingStage().getFishing_rod_with_fish().setVis(false);
                    Cryptex.main.getActiveListener().addObjectToInventory(Cryptex.main.getActiveObjectsInI().getActorInIByName("fishing_rod_InI"));
                }
            }

//6th level exceptions
            else if(Cryptex.main.getGlobalReferences().getLevelNumber() == 6) {

                if (name.equals("spoon")) {
                    MainScreen6.staticObjectsOnStage.getSpoon_table().setVis(false);
                } else if (name.equals("jelly_jar")) {
                    MainScreen6.staticObjectsOnStage.getJelly_jar_table().setVis(false);
                } else if (name.equals("key_from_dressing_room")) {
                    MainScreen6.staticObjectsOnStage.getPaper_sheet_on_floor().setVis(false);
                    MainScreen6.staticObjectsOnStage.getPaper_sheet_overlap_for_typing().setVis(false);
                } else if (name.equals("nine")) {
                    Cryptex.main.getFlags456().setFlagPwcFromTableBoxTaken(true);
                }
            }

//6th level COMP exceptions
            else if(Cryptex.main.getGlobalReferences().getLevelNumber() == -6) {

                if (name.equals("spider")) {
                    MainScreen6COMLETED.soosJSON.getParc_spider().setVis(false);
                }
            }

//7th level exceptions
            else if(Cryptex.main.getGlobalReferences().getLevelNumber() == 7) {
                if (name.equals("salt")) {
                    MainScreen7.soosJSON.getJars_salt_picture().setVis(false);
                    MainScreen7.soosJSON.getRita_salt().setVis(false);
                } else if (name.equals("sugar")) {
                    MainScreen7.soosJSON.getJars_sugar_picture().setVis(false);
                    MainScreen7.soosJSON.getRita_sugar().setVis(false);
                }  else if (name.equals("cocoa")) {
                    MainScreen7.soosJSON.getCuri_middle_door_closed_with_cocoa().setVis(false);
                    if(!Cryptex.main.getMainScreen7().getFlags().getFlagCocoaGot()){
                        Cryptex.main.getMainScreen7().getFlags().setFlagCocoaGot(true);
                        Cryptex.main.getJo().addIndexToAL("cocoa_got_1", Cryptex.main.getGlobalReferences().getLevelNumber(), 4);
                    }
                } else if (name.equals("rolling_pin")) {
                    MainScreen7.soosJSON.getCubo_rolling_pin_picture().setVis(false);
                    MainScreen7.soosJSON.getCeta_rolling_pin().setVis(false);
                } else if(name.startsWith("egg")) {
                    if (name.equals("egg1")) {
                        Cryptex.main.getMainScreen7().getFlags().setFlagEgg1Taken(true);
                        Cryptex.main.getMainScreen7().getListeners().setEggCounter(Cryptex.main.getMainScreen7().getListeners().getEggCounter() + 1);
                    } else if (name.equals("egg2")) {
                        Cryptex.main.getMainScreen7().getFlags().setFlagEgg2Taken(true);
                        Cryptex.main.getMainScreen7().getListeners().setEggCounter(Cryptex.main.getMainScreen7().getListeners().getEggCounter() + 1);
                    } else if (name.equals("egg3")) {
                        Cryptex.main.getMainScreen7().getFlags().setFlagEgg3Taken(true);
                        Cryptex.main.getMainScreen7().getListeners().setEggCounter(Cryptex.main.getMainScreen7().getListeners().getEggCounter() + 1);
                    } else if (name.equals("egg4")) {
                        Cryptex.main.getMainScreen7().getFlags().setFlagEgg4Taken(true);
                        Cryptex.main.getMainScreen7().getListeners().setEggCounter(Cryptex.main.getMainScreen7().getListeners().getEggCounter() + 1);
                    } else if (name.equals("egg5")) {
                        Cryptex.main.getMainScreen7().getFlags().setFlagEgg5Taken(true);
                        Cryptex.main.getMainScreen7().getListeners().setEggCounter(Cryptex.main.getMainScreen7().getListeners().getEggCounter() + 1);
                    } else if (name.equals("egg6")) {
                        Cryptex.main.getMainScreen7().getFlags().setFlagEgg6Taken(true);
                        Cryptex.main.getMainScreen7().getListeners().setEggCounter(Cryptex.main.getMainScreen7().getListeners().getEggCounter() + 1);
                    }
                } else if(name.startsWith("butter")) {
                    if (name.equals("butter1")) {
                        Cryptex.main.getMainScreen7().getFlags().setFlagButter1Taken(true);
                        Cryptex.main.getMainScreen7().getListeners().setButterCounter(Cryptex.main.getMainScreen7().getListeners().getButterCounter() + 1);
                    } else if (name.equals("butter2")) {
                        Cryptex.main.getMainScreen7().getFlags().setFlagButter2Taken(true);
                        Cryptex.main.getMainScreen7().getListeners().setButterCounter(Cryptex.main.getMainScreen7().getListeners().getButterCounter() + 1);
                    } else if (name.equals("butter3")) {
                        Cryptex.main.getMainScreen7().getFlags().setFlagButter3Taken(true);
                        Cryptex.main.getMainScreen7().getListeners().setButterCounter(Cryptex.main.getMainScreen7().getListeners().getButterCounter() + 1);
                    }
                } else if (name.equals("milk")) {
                    Cryptex.main.getMainScreen7().getFlags().setFlagMilkTaken(true);
                } else if (name.equals("yellow_jelly_jar")) {
                    MainScreen7.soosJSON.getCule_jelly_behind_door().setVis(false);
                    Cryptex.main.getMainScreen7().getFlags().setFlagJellyTaken(true);
                } else if (name.equals("yeast")) {
                    MainScreen7.soosJSON.getCubo_yeast_picture().setVis(false);
                    MainScreen7.soosJSON.getCeta_yeast().setVis(false);
                } else if (name.equals("beaker")) {
                    MainScreen7.soosJSON.getMixe_beaker_picture().setVis(false);
                } else if (name.equals("recipe3")) {
                    MainScreen7.soosJSON.getWacl_recipe3().setVis(false);
                } else if (name.equals("spanner_for_oven")) {
                    MainScreen7.soosJSON.getWacl_spanner_for_oven().setVis(false);
                    MainScreen7.soosJSON.getSink_spanner_for_oven_picture().setVis(false);
                } else if (name.equals("beaker_filled")) {

                    if(!Cryptex.main.getMainScreen7().getFlags().getFlagFilledBeakerReceived()){
                        Cryptex.main.getMainScreen7().getFlags().setFlagFilledBeakerReceived(true);
                        Cryptex.main.getJo().addIndexToAL("filled_beaker_received_1", Cryptex.main.getGlobalReferences().getLevelNumber(), 6);
                    }

                    MainScreen7.soosJSON.getKiwt_water_in_beaker().setVis(false);
                    MainScreen7.soosJSON.getKiwt_beaker().setVis(false);
                    MainScreen7.soosJSON.getKitc_beaker().setVis(false);

                    float waterLevelY = MainScreen7.soosJSON.getBeak_water().getY();

                    if(Cryptex.main.getMainScreen7().getListeners().getWaterLevelInBeaker1() == 0) {
                        if (waterLevelY < -155) Cryptex.main.getMainScreen7().getListeners().setWaterLevelInBeaker1(50);
                        else if (waterLevelY >= -155 & waterLevelY < -130)
                            Cryptex.main.getMainScreen7().getListeners().setWaterLevelInBeaker1(100);
                        else if (waterLevelY >= -130 & waterLevelY < -110)
                            Cryptex.main.getMainScreen7().getListeners().setWaterLevelInBeaker1(150);
                        else if (waterLevelY >= -110 & waterLevelY < -85)
                            Cryptex.main.getMainScreen7().getListeners().setWaterLevelInBeaker1(200);
                        else if (waterLevelY >= -85 & waterLevelY < -65)
                            Cryptex.main.getMainScreen7().getListeners().setWaterLevelInBeaker1(250);
                        else if (waterLevelY >= -65 & waterLevelY < -40)
                            Cryptex.main.getMainScreen7().getListeners().setWaterLevelInBeaker1(300);
                        else if (waterLevelY >= -40 & waterLevelY < -15)
                            Cryptex.main.getMainScreen7().getListeners().setWaterLevelInBeaker1(350);
                        else if (waterLevelY >= -15 & waterLevelY < 10)
                            Cryptex.main.getMainScreen7().getListeners().setWaterLevelInBeaker1(400);
                        else if (waterLevelY >= 10)
                            Cryptex.main.getMainScreen7().getListeners().setWaterLevelInBeaker1(450);
                    }
                    else{
                        if (waterLevelY < -155) Cryptex.main.getMainScreen7().getListeners().setWaterLevelInBeaker2(50);
                        else if (waterLevelY >= -155 & waterLevelY < -130)
                            Cryptex.main.getMainScreen7().getListeners().setWaterLevelInBeaker2(100);
                        else if (waterLevelY >= -130 & waterLevelY < -110)
                            Cryptex.main.getMainScreen7().getListeners().setWaterLevelInBeaker2(150);
                        else if (waterLevelY >= -110 & waterLevelY < -85)
                            Cryptex.main.getMainScreen7().getListeners().setWaterLevelInBeaker2(200);
                        else if (waterLevelY >= -85 & waterLevelY < -65)
                            Cryptex.main.getMainScreen7().getListeners().setWaterLevelInBeaker2(250);
                        else if (waterLevelY >= -65 & waterLevelY < -40)
                            Cryptex.main.getMainScreen7().getListeners().setWaterLevelInBeaker2(300);
                        else if (waterLevelY >= -40 & waterLevelY < -15)
                            Cryptex.main.getMainScreen7().getListeners().setWaterLevelInBeaker2(350);
                        else if (waterLevelY >= -15 & waterLevelY < 10)
                            Cryptex.main.getMainScreen7().getListeners().setWaterLevelInBeaker2(400);
                        else if (waterLevelY >= 10)
                            Cryptex.main.getMainScreen7().getListeners().setWaterLevelInBeaker2(450);
                    }

                    MainScreen7.soosJSON.getBeak_water().setPosition(317, -194);
                    Cryptex.main.getMainScreen7().getFlags().setFlagWaterTapBlocked(false);
                    Cryptex.main.getMainScreen7().getFlags().setFlagBeakerFilled(false);
                } else if (name.equals("kiwis_cut")) {
                    MainScreen7.soosJSON.getCubo_kiwis_cut().setVis(false);
                    MainScreen7.soosJSON.getCeta_kiwis_cut().setVis(false);
                } else if (name.equals("oranges_cut")) {
                    MainScreen7.soosJSON.getCubo_oranges_cut().setVis(false);
                    MainScreen7.soosJSON.getCeta_oranges_cut().setVis(false);
                } else if (name.equals("shortcake_uncooked")) {
                    MainScreen7.soosJSON.getCubo_dough_rolled().setVis(false);
                    MainScreen7.soosJSON.getCeta_dough_rolled().setVis(false);
                } else if (name.equals("shortcake_uncooked_dark")) {
                    MainScreen7.soosJSON.getCubo_dough_rolled_dark().setVis(false);
                    MainScreen7.soosJSON.getCeta_dough_rolled_dark().setVis(false);
                } else if (name.equals("cream_pink") | name.equals("cream_white")) {
                    MainScreen7.soosJSON.getMixe_cream_mixed().setVis(false);
                } else if (name.equals("shortcake_dark")) {
                    Cryptex.main.getMainScreen7().getFlags().setFlagShortcakeInOven(false);
                    Cryptex.main.getMainScreen7().getFlags().setFlagShortcakeCooked(false);
                    MainScreen7.soosJSON.getOven_shortcake_dark_opened_door().setVis(false);
                    MainScreen7.soosJSON.getOvvi_shortcake_dark().setVis(false);
                    Cryptex.main.getMainScreen7().getFlags().setFlagBlockSpanner(false);
                    Cryptex.main.getMainScreen7().getFlags().setFlagDarkShortcakeObtained(true);
                    Cryptex.main.getMainScreen7().getFlags().setFlagCocoaDoughGot(false);
                } else if (name.equals("shortcake_light")) {
                    Cryptex.main.getMainScreen7().getFlags().setFlagShortcakeInOven(false);
                    Cryptex.main.getMainScreen7().getFlags().setFlagShortcakeCooked(false);
                    MainScreen7.soosJSON.getOven_shortcake_light_opened_door().setVis(false);
                    MainScreen7.soosJSON.getOvvi_shortcake_light().setVis(false);
                    Cryptex.main.getMainScreen7().getFlags().setFlagBlockSpanner(false);
                    Cryptex.main.getMainScreen7().getFlags().setFlagLightShortcakeObtained(true);
                    Cryptex.main.getMainScreen7().getFlags().setFlagDoughGot(false);
                } else if (name.equals("cake")) {
                    for(int i = 0; i < MainScreen7.soosJSON.getCake().length; i++){
                        MainScreen7.soosJSON.getCake()[i].setVis(false);
                    }
                    Cryptex.main.getMainScreen7().getFlags().setFlagCakeIsDone(true);
                }
            }

//8th level exceptions
            else if(Cryptex.main.getGlobalReferences().getLevelNumber() == 8) {
                if (name.equals("nails")) {
                    MainScreen8.soosJSON.getGrav_nails().setVis(false);
                } else if (name.equals("shovel_bottom")) {
                    MainScreen8.soosJSON.getGrav_shovel_bottom().setVis(false);
                } else if (name.equals("wooden_wheel")) {
                    MainScreen8.soosJSON.getGrav_wheel().setVis(false);
                } else if (name.equals("story")) {
                    Cryptex.main.getMainScreen8().getFlags().setFlagStoryTaken(true);
                } else if (name.equals("ladle_empty")) {
                    MainScreen8.soosJSON.getCryp_ladle().setVis(false);
                } else if (name.equals("bottle_empty")) {
                    MainScreen8.soosJSON.getSavi_bottle_taken().setVis(true);
                } else if (name.equals("pipe_rubber")) {
                    MainScreen8.soosJSON.getSavi_pipe_taken().setVis(true);
                } else if (name.equals("totem")) {
                    Cryptex.main.getGlobalReferences().removeObjectFromInventory("story_InI");
                } else if (name.equals("bottle_with_potion")) {
                    MainScreen8.soosJSON.getTabl_potion().setVis(false);
                    MainScreen8.soosJSON.getTabl_bottle().setVis(false);
                } else if (name.equals("hummer2")) {
                    MainScreen8.soosJSON.getGrav_hummer().setVis(false);
                }
            }

//9th level exceptions
            else if(Cryptex.main.getGlobalReferences().getLevelNumber() == 9) {
                if (name.equals("alphabet")) {
                    if(!Cryptex.main.getMainScreen9().getFlags().getFlagAlphabetTaken()){
                        Cryptex.main.getMainScreen9().getFlags().setFlagAlphabetTaken(true);
                        Cryptex.main.getJo().addIndexToAL("alphabet_taken_1", Cryptex.main.getGlobalReferences().getLevelNumber(), 5);
                    }
                }
                else if (name.equals("riddle")) {
                    if(!Cryptex.main.getMainScreen9().getFlags().getFlagRiddleTaken()){
                        Cryptex.main.getMainScreen9().getFlags().setFlagRiddleTaken(true);
                        Cryptex.main.getJo().addIndexToAL("riddle_taken_1", Cryptex.main.getGlobalReferences().getLevelNumber(), 4);
                    }
                }
            }

            return true;
        }

        @Override
        public void touchUp(final InputEvent event, float x, float y, int pointer, int button) {
            if (name.endsWith("BIG")) {
                if (Cryptex.main.getGlobalReferences().getLevelNumber() == 1) {
                    if(name.equals("newspaper_BIG")){
                        Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen1().getSound().getNewspaper_put(), 0.7f);
                        removeBIGandBackground(name);
                    }

                    if (Cryptex.main.getGlobalReferences().getItemsWeCanCollect1Level() == 0) {
                        removeBIGandBackground(name);
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    synchronized (this) {
                                        Cryptex.main.getGlobalReferences().removeObjectFromInventory("safe_password_InI");
                                        Cryptex.main.getGlobalReferences().threadKilling("soundtrackThread");
                                        Cryptex.main.getGlobalReferences().soundtrackFadeOut(Cryptex.main.getMainScreen1().getMusic().getTrack1(), 15, Cryptex.main.getMainScreen1().getMusic().getTrack1().getVolume());
                                        Cryptex.main.getSaving().loadFlagToDefault();
                                        Cryptex.main.getGlobalReferences().stageDarkeningWithPanel();
                                        wait(2000);
                                        Cryptex.main.getMainScreen1().getMusic().music1Dispose();
                                        Cryptex.main.getGlobalReferences().setFlagRender(false);
                                        Cryptex.main.getGlobalReferences().setFlagGoTo2Lvl(true);
                                    }
                                } catch (InterruptedException ex) {
                                    Thread.currentThread().interrupt();
                                }
                            }
                        });
                        thread.start();
                    }
                    else removeBIGandBackground(name);
                }
                else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 9) {
                    if ((name.equals("symbol_from_LC_BIG") | name.equals("symbol_from_DC_BIG")) & !Cryptex.main.getMainScreen9().getFlags().getFlagEvilmanAppeared()) {
                        Cryptex.main.getGlobalReferences().setItemsWeCanCollect9Level(Cryptex.main.getGlobalReferences().getItemsWeCanCollect9Level() - 1);
                        removeBIGandBackground(name);
                        if(Cryptex.main.getGlobalReferences().getFlagDigitalCryptexStageStart()) Cryptex.main.getCryptexStageDig().getBlack_back().setVisible(true);
                        else Cryptex.main.getCryptexStageLit().getBlack_back().setVisible(true);

                        int counter = 0;

                        for(int i = 0; i < Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().size; i++){
                            if(Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().get(i).getName().startsWith("symbol")){
                                counter++;
                            }
                        }

                        if(counter == 2){
                            if (!Cryptex.main.getMainScreen9().getFlags().getFlagBridFE()) {
                                Cryptex.main.getMainScreen9().getFlags().setFlagBridFE(true);
                                Cryptex.main.getJo().addIndexToAL("brid_FE_1", Cryptex.main.getGlobalReferences().getLevelNumber(), 6);
                            }
                            Cryptex.main.getMainScreen9().getFlags().setFlagEvilmanAppeared(true);
                            Thread thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        synchronized (this) {
                                            Cryptex.main.getPanelStage().getClearScreenOverPanelActors().setVisible(true);
                                            Cryptex.main.getGlobalReferences().stageDarkeningWithPanel();
                                            wait(2000);

                                            if(Cryptex.main.getGlobalReferences().getFlagDigitalCryptexStageStart()){
                                                for (int i = 0; i < Cryptex.main.getCryptexStageDig().getAllActorsCryptexStage().size; i++) {
                                                    Cryptex.main.getCryptexStageDig().getAllActorsCryptexStage().get(i).setTouchable(Touchable.disabled);
                                                }
                                                Cryptex.main.getGlobalReferences().setFlagDigitalCryptexStageStart(false);

                                                if(Cryptex.main.getCryptexStageDig().getDc_opened_empty().isVisible()) {
                                                    Cryptex.main.getGlobalReferences().removeObjectFromInventory("cryptex_digital_InI");
                                                }
                                            }
                                            else{
                                                for (int i = 0; i < Cryptex.main.getCryptexStageLit().getAllActorsCryptexStage().size; i++) {
                                                    Cryptex.main.getCryptexStageLit().getAllActorsCryptexStage().get(i).setTouchable(Touchable.disabled);
                                                }
                                                Cryptex.main.getGlobalReferences().setFlagLiteralCryptexStageStart(false);

                                                if(Cryptex.main.getCryptexStageLit().getLc_opened_empty().isVisible()) {
                                                    Cryptex.main.getGlobalReferences().removeObjectFromInventory("cryptex_InI");
                                                }
                                            }

                                            Cryptex.main.getCryptexStageDig().getDc_opened_empty().setVisible(false);
                                            Cryptex.main.getCryptexStageLit().getLc_opened_empty().setVisible(false);
                                            MainScreen9.soosJSON.getBrid_evilman().setVis(true);
                                            Cryptex.main.getMainScreen9().getBrid().getBack_to_previous_stage().setVisible(false);

                                            if(Cryptex.main.getMainScreen9().getFlags().getFlagDoor()){
                                                Cryptex.main.getMainScreen9().getListeners().stageTransition(Cryptex.main.getMainScreen9().getBridActors(), Cryptex.main.getMainScreen9().getDoorActors());
                                                Cryptex.main.getMainScreen9().getFlags().setFlagDoor(false);
                                            }
                                            else if(Cryptex.main.getMainScreen9().getFlags().getFlagWale()){
                                                Cryptex.main.getMainScreen9().getListeners().stageTransition(Cryptex.main.getMainScreen9().getBridActors(), Cryptex.main.getMainScreen9().getWaleActors());
                                                Cryptex.main.getMainScreen9().getFlags().setFlagWale(false);
                                            }
                                            else if(Cryptex.main.getMainScreen9().getFlags().getFlagCave()){
                                                Cryptex.main.getMainScreen9().getListeners().stageTransition(Cryptex.main.getMainScreen9().getBridActors(), Cryptex.main.getMainScreen9().getCaveActors());
                                                Cryptex.main.getMainScreen9().getFlags().setFlagCave(false);
                                            }
                                            else if(Cryptex.main.getMainScreen9().getFlags().getFlagShel()){
                                                Cryptex.main.getMainScreen9().getListeners().stageTransition(Cryptex.main.getMainScreen9().getBridActors(), Cryptex.main.getMainScreen9().getShelActors());
                                                Cryptex.main.getMainScreen9().getFlags().setFlagShel(false);
                                            }
                                            else if(Cryptex.main.getMainScreen9().getFlags().getFlagLeve()){
                                                Cryptex.main.getMainScreen9().getListeners().stageTransition(Cryptex.main.getMainScreen9().getBridActors(), Cryptex.main.getMainScreen9().getLeveActors());
                                                Cryptex.main.getMainScreen9().getFlags().setFlagLeve(false);
                                            }
                                            else if(Cryptex.main.getMainScreen9().getFlags().getFlagEvdi()){
                                                Cryptex.main.getMainScreen9().getListeners().stageTransition(Cryptex.main.getMainScreen9().getBridActors(), Cryptex.main.getMainScreen9().getEvdiActors());
                                                Cryptex.main.getMainScreen9().getFlags().setFlagEvdi(false);
                                            }
                                            Cryptex.main.getMainScreen9().getFlags().setFlagBrid(true);

                                            Cryptex.main.getMainScreen9().getListeners().startEvilmanTorchAnim();
                                            Cryptex.main.getMainScreen9().getListeners().startBridTorchesAnim();

                                            Cryptex.main.getGlobalReferences().stageSimpleLighteningWithPanel();
                                            wait(2000);
                                            Cryptex.main.getPanelStage().getClearScreenOverPanelActors().setVisible(false);

                                        }
                                    } catch (InterruptedException ex) {
                                        Thread.currentThread().interrupt();
                                    }
                                }
                            });
                            thread.start();
                        }
                    }
                    else removeBIGandBackground(name);
                }
                else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 8) {
                    if (name.equals("story_BIG")) {
                        if(!Cryptex.main.getMainScreen8().getFlags().getFlagStoryRead()){
                            Cryptex.main.getMainScreen8().getFlags().setFlagStoryRead(true);
                            Cryptex.main.getJo().addIndexToAL("story_read_1", Cryptex.main.getGlobalReferences().getLevelNumber(), 6);
                        }
                    }
                    removeBIGandBackground(name);
                }
                else removeBIGandBackground(name);
            }
            else if(currentAO.getParent().getName() != null) {
                if (currentAO.getParent().getName().equals("groupCube")) {
                    Actor anotherEgg = new Actor();
                    for(int i = 0; i < Cryptex.main.getCubeStage().getGroupCube().getChildren().size; i++){
                        if(Cryptex.main.getCubeStage().getGroupCube().getChildren().get(i).getName().startsWith("egg") &
                                !Cryptex.main.getCubeStage().getGroupCube().getChildren().get(i).getName().equals(name)) {
                            anotherEgg = Cryptex.main.getCubeStage().getGroupCube().getChildren().get(i);
                            break;
                        }
                    }
                    if(currentAO.getX() < 399 & currentAO.getY() > 217){
                        if(currentAO.getName().startsWith("egg")){
                            if(getCell1CubeCounter() == 2){
                                setCell1CubeCounter(1);
                                anotherEgg.setPosition(240, 289);
                            }
                            else setCell1CubeCounter(-1);
                        }
                        else setCell1CubeCounter(-1);
                    } else if(currentAO.getX() > 399 & currentAO.getY() > 217){
                        if(currentAO.getName().startsWith("egg")){
                            if(getCell2CubeCounter() == 2){
                                setCell2CubeCounter(1);
                                anotherEgg.setPosition(408, 289);
                            }
                            else setCell2CubeCounter(-1);
                        }
                        else setCell2CubeCounter(-1);
                    } else if(currentAO.getX() < 399 & currentAO.getY() < 217){
                        if(currentAO.getName().startsWith("egg")){
                            if(getCell3CubeCounter() == 2){
                                setCell3CubeCounter(1);
                                anotherEgg.setPosition(240, 165);
                            }
                            else setCell3CubeCounter(-1);
                        }
                        else setCell3CubeCounter(-1);
                    } else if(currentAO.getX() > 399 & currentAO.getY() < 217){
                        if(currentAO.getName().startsWith("egg")){
                            if(getCell4CubeCounter() == 2){
                                setCell4CubeCounter(1);
                                anotherEgg.setPosition(408, 165);
                            }
                            else setCell4CubeCounter(-1);
                        }
                        else setCell4CubeCounter(-1);
                    }
                    Cryptex.main.getActiveListener().addObjectToInventory(currentAO);
                } else if (currentAO.getParent().getName().equals("ObjectsInInventory") &
                        /*Math.abs(upX - downX) < 5 & Math.abs(upY - downY) < 5*/ !flagObjectsInIMoved & !flagObjectWentBigger) {
                    addObjectToActiveCellFromInventory();
                } else if (currentAO.getParent().getName().equals("ActiveItem")) {
                    String acoName = Cryptex.main.getPanelStage().getGroupActiveItem().getChildren().get(0).getName();
                    if (currentAO.getX() > 182 & currentAO.getX() < 605 & currentAO.getY() > -10 & currentAO.getY() < 50 & Cryptex.main.getGlobalReferences().getFlagInventoryAppearance()) {
                        if(Cryptex.main.getGlobalReferences().getLevelNumber() == 4) {
                            if (name.equals("hacksaw_InI") | name.equals("hacksaw_part2_InI")) {
                                if(Cryptex.main.getCubeStage().getCube_icon().isVisible()) {
                                    Cryptex.main.getCubeStage().getCube_icon().setVisible(false);
                                    Cryptex.main.getGlobalReferences().setFlagCubeStageStart(false);
                                }
                            }
                        }
                        else if(Cryptex.main.getGlobalReferences().getLevelNumber() == 7) {
                            if (name.startsWith("egg") | name.equals("beaker_filled_InI") | name.startsWith("butter") | name.equals("cocoa_InI") | name.equals("yellow_jelly_jar_InI")
                                    | name.equals("jelly_jar_InI") | name.equals("milk_InI") | name.equals("flour_weighed_InI") | name.equals("salt_weighed_InI")
                                    | name.equals("sugar_weighed_InI") | name.equals("yeast_weighed_InI")) {
                                if(Cryptex.main.getCubeStage().getCube_icon().isVisible()) {
                                    Cryptex.main.getCubeStage().getCube_icon().setVisible(false);
                                    Cryptex.main.getGlobalReferences().setFlagCubeStageStart(false);
                                }
                            }
                        }
                        else if(Cryptex.main.getGlobalReferences().getLevelNumber() == 8) {
                            if (name.equals("nails_InI") | name.equals("shovel_bottom_InI") | name.equals("shovel_top_InI") | name.equals("shovel_top2_InI") | name.equals("hummer2_InI")) {
                                if(Cryptex.main.getCubeStage().getCube_icon().isVisible()) {
                                    Cryptex.main.getCubeStage().getCube_icon().setVisible(false);
                                    Cryptex.main.getGlobalReferences().setFlagCubeStageStart(false);
                                }
                            }
                        }
                        Actor aoInA = Cryptex.main.getPanelStage().getGroupActiveItem().getChildren().toArray()[0];
                        addObjectToInventoryFromActiveCell(aoInA);
                    }
                    else {
                        if(Cryptex.main.getGlobalReferences().getFlagCubeCalled()) {
                            if (currentAO.getX() > 200 & currentAO.getX() < 363 & currentAO.getY() > 237 & currentAO.getY() < 347) {
                                if (getCell1CubeCounter() == -1) {
                                    if (name.startsWith("egg")) {
                                        if (getCell2CubeCounter() <= 0 & getCell3CubeCounter() <= 0 & getCell4CubeCounter() <= 0) {
                                            currentAO.setPosition(240, 289);
                                            Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getGs().getPut_into_cube(), 1);
                                            setCell1CubeCounter(1);
                                            Cryptex.main.getCubeStage().getGroupCube().addActor(currentAO);
                                            Cryptex.main.getGlobalReferences().setFlagActiveCellOccupied(false);
                                        } else moveActiveItemToDefault();
                                    } else {
                                        currentAO.setPosition(275, 289);
                                        Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getGs().getPut_into_cube(), 1);
                                        setCell1CubeCounter(0);
                                        Cryptex.main.getCubeStage().getGroupCube().addActor(currentAO);
                                        Cryptex.main.getGlobalReferences().setFlagActiveCellOccupied(false);
                                    }
                                } else if (getCell1CubeCounter() == 0 | getCell1CubeCounter() == 2)
                                    moveActiveItemToDefault();
                                else {
                                    if (name.startsWith("egg")) {
                                        currentAO.setPosition(313, 289);
                                        Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getGs().getPut_into_cube(), 1);
                                        setCell1CubeCounter(2);
                                        Cryptex.main.getCubeStage().getGroupCube().addActor(currentAO);
                                        Cryptex.main.getGlobalReferences().setFlagActiveCellOccupied(false);
                                    } else moveActiveItemToDefault();
                                }
                            } else if (currentAO.getX() > 373 & currentAO.getX() < 530 & currentAO.getY() > 237 & currentAO.getY() < 347) {
                                if (getCell2CubeCounter() == -1) {
                                    if (name.startsWith("egg")) {
                                        if (getCell1CubeCounter() <= 0 & getCell3CubeCounter() <= 0 & getCell4CubeCounter() <= 0) {
                                            currentAO.setPosition(408, 289);
                                            Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getGs().getPut_into_cube(), 1);
                                            setCell2CubeCounter(1);
                                            Cryptex.main.getCubeStage().getGroupCube().addActor(currentAO);
                                            Cryptex.main.getGlobalReferences().setFlagActiveCellOccupied(false);
                                        } else moveActiveItemToDefault();
                                    } else {
                                        currentAO.setPosition(447, 289);
                                        Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getGs().getPut_into_cube(), 1);
                                        setCell2CubeCounter(0);
                                        Cryptex.main.getCubeStage().getGroupCube().addActor(currentAO);
                                        Cryptex.main.getGlobalReferences().setFlagActiveCellOccupied(false);
                                    }
                                } else if (getCell2CubeCounter() == 0 | getCell2CubeCounter() == 2)
                                    moveActiveItemToDefault();
                                else {
                                    if (name.startsWith("egg")) {
                                        currentAO.setPosition(481, 289);
                                        Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getGs().getPut_into_cube(), 1);
                                        setCell2CubeCounter(2);
                                        Cryptex.main.getCubeStage().getGroupCube().addActor(currentAO);
                                        Cryptex.main.getGlobalReferences().setFlagActiveCellOccupied(false);
                                    } else moveActiveItemToDefault();
                                }
                            } else if (currentAO.getX() > 200 & currentAO.getX() < 363 & currentAO.getY() > 117 & currentAO.getY() < 227) {
                                if (getCell3CubeCounter() == -1) {
                                    if (name.startsWith("egg")) {
                                        if (getCell1CubeCounter() <= 0 & getCell2CubeCounter() <= 0 & getCell4CubeCounter() <= 0) {
                                            currentAO.setPosition(240, 165);
                                            Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getGs().getPut_into_cube(), 1);
                                            setCell3CubeCounter(1);
                                            Cryptex.main.getCubeStage().getGroupCube().addActor(currentAO);
                                            Cryptex.main.getGlobalReferences().setFlagActiveCellOccupied(false);
                                        } else moveActiveItemToDefault();
                                    } else {
                                        currentAO.setPosition(275, 165);
                                        Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getGs().getPut_into_cube(), 1);
                                        setCell3CubeCounter(0);
                                        Cryptex.main.getCubeStage().getGroupCube().addActor(currentAO);
                                        Cryptex.main.getGlobalReferences().setFlagActiveCellOccupied(false);
                                    }
                                } else if (getCell3CubeCounter() == 0 | getCell3CubeCounter() == 2)
                                    moveActiveItemToDefault();
                                else {
                                    if (name.startsWith("egg")) {
                                        currentAO.setPosition(313, 165);
                                        Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getGs().getPut_into_cube(), 1);
                                        setCell3CubeCounter(2);
                                        Cryptex.main.getCubeStage().getGroupCube().addActor(currentAO);
                                        Cryptex.main.getGlobalReferences().setFlagActiveCellOccupied(false);
                                    } else moveActiveItemToDefault();
                                }
                            } else if (currentAO.getX() > 373 & currentAO.getX() < 530 & currentAO.getY() > 117 & currentAO.getY() < 227) {
                                if (getCell4CubeCounter() == -1) {
                                    if (name.startsWith("egg")) {
                                        if (getCell1CubeCounter() <= 0 & getCell2CubeCounter() <= 0 & getCell3CubeCounter() <= 0) {
                                            currentAO.setPosition(408, 165);
                                            Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getGs().getPut_into_cube(), 1);
                                            setCell4CubeCounter(1);
                                            Cryptex.main.getCubeStage().getGroupCube().addActor(currentAO);
                                            Cryptex.main.getGlobalReferences().setFlagActiveCellOccupied(false);
                                        } else moveActiveItemToDefault();
                                    } else {
                                        currentAO.setPosition(447, 165);
                                        Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getGs().getPut_into_cube(), 1);
                                        setCell4CubeCounter(0);
                                        Cryptex.main.getCubeStage().getGroupCube().addActor(currentAO);
                                        Cryptex.main.getGlobalReferences().setFlagActiveCellOccupied(false);
                                    }
                                } else if (getCell4CubeCounter() == 0 | getCell4CubeCounter() == 2)
                                    moveActiveItemToDefault();
                                else {
                                    if (name.startsWith("egg")) {
                                        currentAO.setPosition(481, 165);
                                        Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getGs().getPut_into_cube(), 1);
                                        setCell4CubeCounter(2);
                                        Cryptex.main.getCubeStage().getGroupCube().addActor(currentAO);
                                        Cryptex.main.getGlobalReferences().setFlagActiveCellOccupied(false);
                                    } else moveActiveItemToDefault();
                                }
                            } else {
                                moveActiveItemToDefault();
                            }
                        }
                        else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 1) {
                            if (Cryptex.main.getMainScreen1().getStaticObjects().getFlagTableBoxesStageStart()) {
                                Cryptex.main.getMainScreen1().getStaticObjects().openTableBox(name, currentAO.getX(), currentAO.getY());
                            } else if (Cryptex.main.getMainScreen1().getStaticObjects().getFlagBuffetStageStart()) {
                                Cryptex.main.getMainScreen1().getStaticObjects().openBuffetBox(name, currentAO.getX(), currentAO.getY());
                            } else if (Cryptex.main.getMainScreen1().getStaticObjects().getFlagLampStageStart() & acoName.equals("lamps_InI")) {
                                Cryptex.main.getMainScreen1().getStaticObjects().putLampsOnLampStage(name, currentAO.getX(), currentAO.getY());
                            } else if (Cryptex.main.getMainScreen1().getStaticObjects().getFlagLampStageStart() & acoName.equals("valve_for_lamp_InI")) {
                                Cryptex.main.getMainScreen1().getStaticObjects().mountValveOnLamp(name, currentAO.getX(), currentAO.getY());
                            } else {
                                moveActiveItemToDefault();
                            }
                        } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 2) {
                            if (Cryptex.main.getMainScreen2().getStaticObjects().getFlagCellarStageStart()) {
                                if (name.equals("lighter_InI") & MainScreen2.staticObjectsOnStage.getKerosene_lamp_on_hook().isVisible()) {
                                    if (Cryptex.main.getMainScreen2().getStaticObjects().getFlagKeroseneLampFilled()) {
                                        Cryptex.main.getMainScreen2().getStaticObjects().lightKeroseneLamp(currentAO.getX(), currentAO.getY());
                                    }
                                    else {
                                        Cryptex.main.getGlobalReferences().hintMessageAppearingMethod();
                                        Cryptex.main.getGlobalReferences().hintAppearingMethod();
                                        moveActiveItemToDefault();
                                    }
                                }
                                else if(name.equals("jerrican_InI") & MainScreen2.staticObjectsOnStage.getKerosene_lamp_on_hook().isVisible()){
                                    Cryptex.main.getMainScreen2().getStaticObjects().fillKeroseneLamp(currentAO.getX(), currentAO.getY());
                                }
                                else if(name.equals("kerosene_lamp_InI")){
                                    Cryptex.main.getMainScreen2().getStaticObjects().hangKeroseneLamp(currentAO.getX(), currentAO.getY());
                                }
                                else moveActiveItemToDefault();
                            } else if (Cryptex.main.getMainScreen2().getStaticObjects().getFlagLuggageBootStageStart()) {
                                Cryptex.main.getMainScreen2().getStaticObjects().openLuggageBoot(name, currentAO.getX(), currentAO.getY());
                            } else if (Cryptex.main.getMainScreen2().getStaticObjects().getFlagTowerPuzzleStageStart()) {
                                Cryptex.main.getMainScreen2().getStaticObjects().mountBigFishRoller(name, currentAO.getX(), currentAO.getY());
                            } else if (Cryptex.main.getMainScreen2().getStaticObjects().getFlagWellMechanismStageStart()) {
                                if (acoName.equals("cogwheel_higher_InI")) {
                                    Cryptex.main.getMainScreen2().getStaticObjects().mountHigherCogwheel(name, currentAO.getX(), currentAO.getY());
                                } else if (acoName.equals("cogwheel_middle_InI")) {
                                    Cryptex.main.getMainScreen2().getStaticObjects().mountMiddleCogwheel(name, currentAO.getX(), currentAO.getY());
                                } else if (acoName.equals("cogwheel_lower_InI")) {
                                    Cryptex.main.getMainScreen2().getStaticObjects().mountLowerCogwheel(name, currentAO.getX(), currentAO.getY());
                                } else if (acoName.equals("cogwheel_outer_InI")) {
                                    Cryptex.main.getMainScreen2().getStaticObjects().mountOuterCogwheel(name, currentAO.getX(), currentAO.getY());
                                } else if (acoName.equals("chain_InI")) {
                                    Cryptex.main.getMainScreen2().getStaticObjects().mountChain(name, currentAO.getX(), currentAO.getY());
                                } else if (acoName.equals("well_lever_InI")) {
                                    Cryptex.main.getMainScreen2().getStaticObjects().mountWellLevel(name, currentAO.getX(), currentAO.getY());
                                } else {
                                    moveActiveItemToDefault();
                                }
                            } else if (Cryptex.main.getMainScreen2().getStaticObjects().getFlagShedEntranceStageStart()) {
                                Cryptex.main.getMainScreen2().getStaticObjects().openShedDoor(name, currentAO.getX(), currentAO.getY());
                            } else {
                                moveActiveItemToDefault();
                            }
                        }
                        else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 3) {
                            if (Cryptex.main.getMainScreen3().getStaticObjects().getFlagRopeStageStart()) {
                                Cryptex.main.getMainScreen3().getStaticObjects().cutTheRope(name, currentAO.getX(), currentAO.getY());
                            } else if (Cryptex.main.getMainScreen3().getStaticObjects().getFlagTableStageStart()) {
                                Cryptex.main.getMainScreen3().getStaticObjects().mountHelixOnMotor(name, currentAO.getX(), currentAO.getY());
                            } else if (Cryptex.main.getMainScreen3().getStaticObjects().getFlagDashboardStageStart()) {
                                if (acoName.equals("steering_wheel_InI")) {
                                    Cryptex.main.getMainScreen3().getStaticObjects().mountSteeringWheel(currentAO.getX(), currentAO.getY());
                                } else if (acoName.equals("key_from_boat_InI")) {
                                    Cryptex.main.getMainScreen3().getStaticObjects().insertKeyFromBoat(currentAO.getX(), currentAO.getY());
                                } else if (acoName.equals("boat_button_InI")) {
                                    Cryptex.main.getMainScreen3().getStaticObjects().mountBoatButton(currentAO.getX(), currentAO.getY());
                                } else {
                                    moveActiveItemToDefault();
                                }
                            } else if (Cryptex.main.getMainScreen3().getStaticObjects().getFlagBoatBackStageStart()) {
                                Cryptex.main.getMainScreen3().getStaticObjects().mountMotor(name, currentAO.getX(), currentAO.getY());
                            } else if (Cryptex.main.getMainScreen3().getStaticObjects().getFlagFuelMechanismStageStart()) {
                                Cryptex.main.getMainScreen3().getStaticObjects().fillFuelMechanism(name, currentAO.getX(), currentAO.getY());
                            } else if (Cryptex.main.getMainScreen3().getStaticObjects().getFlagElectricalPanelSecondLevelStageStart()) {
                                Cryptex.main.getMainScreen3().getStaticObjects().mountFuses(name, currentAO.getX(), currentAO.getY());
                            } else if (Cryptex.main.getMainScreen3().getStaticObjects().getFlagControlPanelStageStart()) {
                                Cryptex.main.getMainScreen3().getStaticObjects().putWiresOnStage(name, currentAO.getX(), currentAO.getY());
                            } else {
                                moveActiveItemToDefault();
                            }
                        }
                        else if(Cryptex.main.getGlobalReferences().getLevelNumber() == 4){
                            if (Cryptex.main.getMainScreen4().getStaticObjects().getFlagAtticStageStart()) {
                                if (acoName.equals("rope_InI")) {
                                    Cryptex.main.getMainScreen4().getStaticObjects().repairRightPan(name, currentAO.getX(), currentAO.getY());
                                }
                                else if (acoName.equals("bag_InI")) {
                                    Cryptex.main.getMainScreen4().getStaticObjects().mountBagOnPan(name, currentAO.getX(), currentAO.getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (Cryptex.main.getMainScreen4().getStaticObjects().getFlagLockStageStart()) {
                                Cryptex.main.getMainScreen4().getStaticObjects().hitLockWithHummer(name, currentAO.getX(), currentAO.getY());
                            }
                            else if (Cryptex.main.getMainScreen4().getStaticObjects().getFlagFrontOfMotoStageStart()) {
                                if (acoName.equals("wheel_InI")) {
                                    Cryptex.main.getMainScreen4().getStaticObjects().mountWheel(currentAO.getX(), currentAO.getY());
                                }
                                else if(acoName.equals("spanner_InI")) {
                                    Cryptex.main.getMainScreen4().getStaticObjects().regulateWheel(currentAO.getX(), currentAO.getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (Cryptex.main.getMainScreen4().getStaticObjects().getFlagLockOnChestStageStart()) {
                                if (acoName.equals("links_InI")) {
                                    Cryptex.main.getMainScreen4().getStaticObjects().putLinksOnChestLock(currentAO.getX(), currentAO.getY());
                                }
                                else if(acoName.equals("jar_InI")) {
                                    Cryptex.main.getMainScreen4().getStaticObjects().putCogwheelsOnChestLock(currentAO.getX(), currentAO.getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (Cryptex.main.getMainScreen4().getStaticObjects().getFlagForestmanStageStart()) {
                                if (acoName.equals("money_InI") & Cryptex.main.getMainScreen4().getStaticObjects().getForestmanPhase().equals("0")) {
                                    Cryptex.main.getMainScreen4().getStaticObjects().giveMoneyToForestman(currentAO.getX(), currentAO.getY());
                                }
                                else if(acoName.startsWith("binoculars") & Cryptex.main.getMainScreen4().getStaticObjects().getFlagFishingRodTaped()) {
                                    Cryptex.main.getMainScreen4().getStaticObjects().giveBinocularsToForestman(name, currentAO.getX(), currentAO.getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (Cryptex.main.getMainScreen4().getStaticObjects().getFlagFishingStageStart()) {
                                if (acoName.equals("fishing_rod_InI")) {
                                    Cryptex.main.getMainScreen4().getStaticObjects().getFishingRod(currentAO.getX(), currentAO.getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (Cryptex.main.getMainScreen4().getStaticObjects().getFlagAtticStairsStageStart()) {
                                if (acoName.equals("stair1_InI")) {
                                    Cryptex.main.getMainScreen4().getStaticObjects().mountStair1(currentAO.getX(), currentAO.getY());
                                }
                                else if (acoName.equals("stair2_InI")) {
                                    Cryptex.main.getMainScreen4().getStaticObjects().mountStair2(currentAO.getX(), currentAO.getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (Cryptex.main.getMainScreen4().getStaticObjects().getFlagAtticDoorStageStart()) {
                                if (acoName.equals("bolt_cutter_InI")) {
                                    Cryptex.main.getMainScreen4().getStaticObjects().cutAtticDoorChain(currentAO.getX(), currentAO.getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (Cryptex.main.getMainScreen4().getStaticObjects().getFlagShedEntranceStageStart()) {
                                if (acoName.equals("hacksaw_assembled_InI")) {
                                    Cryptex.main.getMainScreen4().getStaticObjects().cutShedDoorLock(currentAO.getX(), currentAO.getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else {
                                moveActiveItemToDefault();
                            }
                        }
                        else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 5) {

                            if (Cryptex.main.getMainScreen5().getStaticObjects().getFlagSuitcasesStageStart()) {
                                if (acoName.equals("keys_InI")) {
                                    Cryptex.main.getMainScreen5().getStaticObjects().putKeysOnStage(currentAO.getX(), currentAO.getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (Cryptex.main.getMainScreen5().getStaticObjects().getFlagLuggageBootStageStart()) {
                                if (acoName.equals("clip_InI")) {
                                    Cryptex.main.getMainScreen5().getStaticObjects().unlockLuggageBoot(currentAO.getX(), currentAO.getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (Cryptex.main.getMainScreen5().getStaticObjects().getFlagLuggageSpaceDoorStageStart()) {
                                if (acoName.equals("valve_for_door_InI")) {
                                    Cryptex.main.getMainScreen5().getStaticObjects().mountMiddleValve(currentAO.getX(), currentAO.getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else {
                                moveActiveItemToDefault();
                            }
                        }
                        else if (Cryptex.main.getGlobalReferences().getLevelNumber() == -4) {
                            if (Cryptex.main.getMainScreen4COMPLETED().getStaticObjects().getFlagForestmanStageStart()) {
                                if (acoName.equals("album_InI")) {
                                    Cryptex.main.getMainScreen4COMPLETED().getStaticObjects().giveAlbumToForestman(currentAO.getX(), currentAO.getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (Cryptex.main.getMainScreen4COMPLETED().getStaticObjects().getFlagFishingStageStart()) {
                                if (acoName.equals("fishing_rod_InI")) {
                                    Cryptex.main.getMainScreen4COMPLETED().getStaticObjects().getFishingRod(currentAO.getX(), currentAO.getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else {
                                moveActiveItemToDefault();
                            }
                        } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 6) {

                            if (Cryptex.main.getMainScreen6().getStaticObjects().getFlagDoghouseStageStart()) {
                                if (acoName.equals("fish_InI")) {
                                    Cryptex.main.getMainScreen6().getStaticObjects().putFishOnPlate(currentAO.getX(), currentAO.getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (Cryptex.main.getMainScreen6().getStaticObjects().getFlagDressingRoomDoorStageStart()) {
                                if (acoName.equals("metal_sheet_InI")) {
                                    Cryptex.main.getMainScreen6().getStaticObjects().putPaperSheetOnFloor(currentAO.getX(), currentAO.getY());
                                }
                                else if (acoName.equals("key_from_dressing_room_InI")) {
                                    Cryptex.main.getMainScreen6().getStaticObjects().unlockDressingRoomDoor(currentAO.getX(), currentAO.getY());
                                } else {
                                    moveActiveItemToDefault();
                                }
                            } else if (Cryptex.main.getMainScreen6().getStaticObjects().getFlagTelephoneStageStart()) {
                                if (acoName.equals("telephone_dial_InI")) {
                                    Cryptex.main.getMainScreen6().getStaticObjects().mountTelephoneDial(currentAO.getX(), currentAO.getY());
                                } else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (Cryptex.main.getMainScreen6().getStaticObjects().getFlagKeyholeStageStart()) {
                                if (acoName.equals("spoon_InI")) {
                                    Cryptex.main.getMainScreen6().getStaticObjects().pushKeyOutOfKeyhole(currentAO.getX(), currentAO.getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (Cryptex.main.getMainScreen6().getStaticObjects().getFlagMazeStageStart()) {
                                if (acoName.equals("key_from_chest_InI")) {
                                    Cryptex.main.getMainScreen6().getStaticObjects().unlockChest(currentAO.getX(), currentAO.getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (Cryptex.main.getMainScreen6().getStaticObjects().getFlagCarDoorStageStart()) {
                                if (acoName.equals("extinguisher_InI")) {
                                    Cryptex.main.getMainScreen6().getStaticObjects().breakDoorGlass(currentAO.getX(), currentAO.getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else {
                                moveActiveItemToDefault();
                            }
                        }
                        else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 7) {
                            if (Cryptex.main.getMainScreen7().getFlags().getFlagWapi()) {
                                if (acoName.equals("tap_valve_InI")) {
                                    Cryptex.main.getMainScreen7().getListeners().mountTapValve(currentAO.getX(), currentAO.getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (Cryptex.main.getMainScreen7().getFlags().getFlagKiwt()) {
                                if (acoName.equals("pipe_InI")) {
                                    Cryptex.main.getMainScreen7().getListeners().mountPipe(currentAO.getX(), currentAO.getY());
                                }
                                else if (acoName.equals("beaker_InI")) {
                                    Cryptex.main.getMainScreen7().getListeners().putBeakerOnSink(currentAO.getX(), currentAO.getY());
                                }
                                else if (acoName.equals("beaker_filled_InI")) {
                                    Cryptex.main.getMainScreen7().getListeners().emptyBeaker(currentAO.getX(), currentAO.getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (Cryptex.main.getMainScreen7().getFlags().getFlagOvvi()) {
                                if (acoName.equals("gas_cylinder_InI")) {
                                    Cryptex.main.getMainScreen7().getListeners().mountGasCylinder(currentAO.getX(), currentAO.getY());
                                }
                                else if (acoName.equals("hose_lighted_InI") | acoName.equals("hose_unlighted_InI")) {
                                    Cryptex.main.getMainScreen7().getListeners().mountHose(acoName, currentAO.getX(), currentAO.getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (Cryptex.main.getMainScreen7().getFlags().getFlagOven()) {
                                if (acoName.equals("spanner_for_oven_InI")) {
                                    Cryptex.main.getMainScreen7().getListeners().mountSpanner(currentAO.getX(), currentAO.getY());
                                }
                                else if ((acoName.equals("shortcake_uncooked_InI") | acoName.equals("shortcake_uncooked_dark_InI")) &
                                        !MainScreen7.soosJSON.getOven_oven_door_closed().isVisible() & !Cryptex.main.getMainScreen7().getFlags().getFlagShortcakeCooked()) {
                                    if(acoName.equals("shortcake_uncooked_InI"))
                                    Cryptex.main.getMainScreen7().getListeners().putUncookedShortcakeIntoOven(currentAO.getX(), currentAO.getY(), "shortcake_uncooked_InI");
                                    else Cryptex.main.getMainScreen7().getListeners().putUncookedShortcakeIntoOven(currentAO.getX(), currentAO.getY(), "shortcake_uncooked_dark_InI");
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (Cryptex.main.getMainScreen7().getFlags().getFlagScal()) {
                                if (acoName.equals("sugar_InI")) {
                                    Cryptex.main.getMainScreen7().getListeners().fillBowlWithSugar(currentAO.getX(), currentAO.getY());
                                }
                                else if (acoName.equals("salt_InI")) {
                                    Cryptex.main.getMainScreen7().getListeners().fillBowlWithSalt(currentAO.getX(), currentAO.getY());
                                }
                                else if (acoName.equals("flour_InI")) {
                                    Cryptex.main.getMainScreen7().getListeners().fillBowlWithFlour(currentAO.getX(), currentAO.getY());
                                }
                                else if (acoName.equals("yeast_InI")) {
                                    Cryptex.main.getMainScreen7().getListeners().fillBowlWithYeast(currentAO.getX(), currentAO.getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (Cryptex.main.getMainScreen7().getFlags().getFlagTusw()) {
                                if (acoName.equals("insulation_tape_InI")) {
                                    Cryptex.main.getMainScreen7().getListeners().stickTapeOnSwitch(currentAO.getX(), currentAO.getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (Cryptex.main.getMainScreen7().getFlags().getFlagCubo()) {
                                if (acoName.equals("kiwis_InI")) {
                                    Cryptex.main.getMainScreen7().getListeners().putKiwisOnCubo(currentAO.getX(), currentAO.getY());
                                }
                                else if (acoName.equals("oranges_InI")) {
                                    Cryptex.main.getMainScreen7().getListeners().putOrangesOnCubo(currentAO.getX(), currentAO.getY());
                                }
                                else if (acoName.equals("dough_InI")) {
                                    Cryptex.main.getMainScreen7().getListeners().putDoughOnCubo(currentAO.getX(), currentAO.getY(), "light");
                                }
                                else if (acoName.equals("dough_dark_InI")) {
                                    Cryptex.main.getMainScreen7().getListeners().putDoughOnCubo(currentAO.getX(), currentAO.getY(), "dark");
                                }
                                else if (acoName.equals("rolling_pin_InI") & MainScreen7.soosJSON.getCubo_dough_unrolled().isVisible()) {
                                    Cryptex.main.getMainScreen7().getListeners().rollDough(currentAO.getX(), currentAO.getY(), "light");
                                }
                                else if (acoName.equals("rolling_pin_InI") & MainScreen7.soosJSON.getCubo_dough_unrolled_dark().isVisible()) {
                                    Cryptex.main.getMainScreen7().getListeners().rollDough(currentAO.getX(), currentAO.getY(), "dark");
                                }
                                else if (acoName.equals("kitchen_knife_InI") & MainScreen7.soosJSON.getCubo_kiwis_whole().isVisible()) {
                                    Cryptex.main.getMainScreen7().getListeners().cutKiwis(currentAO.getX(), currentAO.getY());
                                }
                                else if (acoName.equals("kitchen_knife_InI") & MainScreen7.soosJSON.getCubo_oranges_whole().isVisible()) {
                                    Cryptex.main.getMainScreen7().getListeners().cutOranges(currentAO.getX(), currentAO.getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (Cryptex.main.getMainScreen7().getFlags().getFlagMixe()) {
                                if (acoName.equals("cream_white_unmixed_InI") | acoName.equals("cream_pink_unmixed_InI")) {
                                    Cryptex.main.getMainScreen7().getListeners().putUnmixedCreamIntoMixer(acoName, currentAO.getX(), currentAO.getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (Cryptex.main.getMainScreen7().getFlags().getFlagCake()) {
                                if ((acoName.equals("shortcake_dark_InI") | acoName.equals("shortcake_light_InI")) &
                                        !MainScreen7.soosJSON.getCake_shortcake_lower_dark().isVisible() & !MainScreen7.soosJSON.getCake_shortcake_lower_light().isVisible()) {
                                    Cryptex.main.getMainScreen7().getListeners().makeFirstLayer(acoName, currentAO.getX(), currentAO.getY());
                                }
                                else if ((acoName.equals("cream_pink_InI") | acoName.equals("cream_white_InI")) &
                                        (MainScreen7.soosJSON.getCake_shortcake_lower_dark().isVisible() | MainScreen7.soosJSON.getCake_shortcake_lower_light().isVisible()) &
                                        (!MainScreen7.soosJSON.getCake_shortcake_upper_dark().isVisible() & !MainScreen7.soosJSON.getCake_shortcake_upper_light().isVisible())) {
                                    Cryptex.main.getMainScreen7().getListeners().makeSecondLayer(acoName, currentAO.getX(), currentAO.getY());
                                }
                                else if ((acoName.equals("shortcake_dark_InI") | acoName.equals("shortcake_light_InI")) &
                                        (MainScreen7.soosJSON.getCake_cream_lower_pink().isVisible() | MainScreen7.soosJSON.getCake_cream_lower_white().isVisible())) {
                                    Cryptex.main.getMainScreen7().getListeners().makeThirdLayer(acoName, currentAO.getX(), currentAO.getY());
                                }
                                else if ((acoName.equals("cream_pink_InI") | acoName.equals("cream_white_InI")) &
                                        (MainScreen7.soosJSON.getCake_shortcake_upper_dark().isVisible() | MainScreen7.soosJSON.getCake_shortcake_upper_light().isVisible())) {
                                    Cryptex.main.getMainScreen7().getListeners().makeFourthLayer(acoName, currentAO.getX(), currentAO.getY());
                                }
                                else if ((acoName.equals("berries_InI") | acoName.equals("kiwis_cut_InI") | acoName.equals("oranges_cut_InI") | acoName.equals("strawberries_InI")) &
                                        (MainScreen7.soosJSON.getCake_cream_upper_pink().isVisible() | MainScreen7.soosJSON.getCake_cream_upper_white().isVisible())) {
                                    Cryptex.main.getMainScreen7().getListeners().makeDecoration(acoName, currentAO.getX(), currentAO.getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (Cryptex.main.getMainScreen7().getFlags().getFlagCafe()) {
                                if (acoName.equals("cake_InI")) {
                                    Cryptex.main.getMainScreen7().getListeners().giveCakeToWaitress(currentAO.getX(), currentAO.getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else {
                                moveActiveItemToDefault();
                            }
                        }
                        else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 8) {
                            if (Cryptex.main.getMainScreen8().getFlags().getFlagEdgr()) {
                                if (acoName.equals("shovel_InI")) {
                                    Cryptex.main.getMainScreen8().getListeners().digEdvardsGrave(currentAO.getX(), currentAO.getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (Cryptex.main.getMainScreen8().getFlags().getFlagCrep()) {
                                if (acoName.equals("totem_InI")) {
                                    Cryptex.main.getMainScreen8().getListeners().putTotemOnButton(currentAO.getX(), currentAO.getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (Cryptex.main.getMainScreen8().getFlags().getFlagTomb()) {
                                if (acoName.equals("stone_InI")) {
                                    Cryptex.main.getMainScreen8().getListeners().mountStone(currentAO.getX(), currentAO.getY());
                                }
                                else if (acoName.equals("shovel_bottom_InI")) {
                                    Cryptex.main.getMainScreen8().getListeners().mountShovelBottom(currentAO.getX(), currentAO.getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (Cryptex.main.getMainScreen8().getFlags().getFlagWegr()) {
                                if (acoName.equals("shovel_InI")) {
                                    Cryptex.main.getMainScreen8().getListeners().digWesliesGrave(currentAO.getX(), currentAO.getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (Cryptex.main.getMainScreen8().getFlags().getFlagMigr()) {
                                if (acoName.equals("shovel_InI")) {
                                    Cryptex.main.getMainScreen8().getListeners().digMillersGrave(currentAO.getX(), currentAO.getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (Cryptex.main.getMainScreen8().getFlags().getFlagBaww()) {
                                if (acoName.equals("ladle_empty_InI")) {
                                    Cryptex.main.getMainScreen8().getListeners().fillLadle(currentAO.getX(), currentAO.getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (Cryptex.main.getMainScreen8().getFlags().getFlagAleq()) {
                                if (acoName.equals("ladle_with_water_InI")) {
                                    Cryptex.main.getMainScreen8().getListeners().fillFlask(currentAO.getX(), currentAO.getY());
                                }
                                else if (acoName.equals("bottle_empty_InI")) {
                                    Cryptex.main.getMainScreen8().getListeners().standBottle(currentAO.getX(), currentAO.getY());
                                }
                                else if (acoName.equals("primus_InI")) {
                                    Cryptex.main.getMainScreen8().getListeners().mountPrimus(currentAO.getX(), currentAO.getY());
                                }
                                else if (acoName.equals("coil_InI")) {
                                    Cryptex.main.getMainScreen8().getListeners().mountCoil(currentAO.getX(), currentAO.getY());
                                }
                                else if (acoName.equals("pipe_rubber_InI")) {
                                    Cryptex.main.getMainScreen8().getListeners().mountPipe(currentAO.getX(), currentAO.getY());
                                }
                                else if (acoName.equals("spider_InI") & Cryptex.main.getMainScreen8().getFlags().getFlagWaterBoils()) {
                                    Cryptex.main.getMainScreen8().getListeners().throwIngrIntoVessel(currentAO.getX(), currentAO.getY(), "spider_InI");
                                }
                                else if (acoName.equals("potion_InI") & Cryptex.main.getMainScreen8().getFlags().getFlagWaterBoils()) {
                                    Cryptex.main.getMainScreen8().getListeners().throwIngrIntoVessel(currentAO.getX(), currentAO.getY(), "potion_InI");
                                }
                                else if (acoName.equals("butterfly_InI") & Cryptex.main.getMainScreen8().getFlags().getFlagWaterBoils()) {
                                    Cryptex.main.getMainScreen8().getListeners().throwIngrIntoVessel(currentAO.getX(), currentAO.getY(), "butterfly_InI");
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (Cryptex.main.getMainScreen8().getFlags().getFlagWhcr()) {
                                if (acoName.equals("wooden_wheel_InI")) {
                                    Cryptex.main.getMainScreen8().getListeners().mountWheel(currentAO.getX(), currentAO.getY());
                                }
                                else if (acoName.equals("belt_InI")) {
                                    Cryptex.main.getMainScreen8().getListeners().mountBelt(currentAO.getX(), currentAO.getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else if (Cryptex.main.getMainScreen8().getFlags().getFlagDaco()) {
                                if (acoName.equals("flashlight_InI")) {
                                    Cryptex.main.getMainScreen8().getListeners().standLighter(currentAO.getX(), currentAO.getY());
                                }
                                else {
                                    moveActiveItemToDefault();
                                }
                            }
                            else {
                                moveActiveItemToDefault();
                            }
                        }
                        else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 9) {
                            if (Cryptex.main.getMainScreen9().getFlags().getFlagBrid()) {
                                if (acoName.equals("scissors_InI")) {
                                    Cryptex.main.getMainScreen9().getListeners().cutRope(currentAO.getX(), currentAO.getY());
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

            flagObjectWentBigger = false;
            flagObjectsInIMoved = false;
        }

        @Override
        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            if(currentAO.getParent().getName() != null) {
                if (currentAO.getParent().getName().equals("ActiveItem")) {
                    currentAO.moveBy(x - 36.5f, y - 36.5f);
                } else if (currentAO.getParent().getName().equals("ObjectsInInventory")) {
                    if (y > 0 & y < 80) {
                        if (x > 100) {
                            flagObjectsInIMoved = true;
                            if (Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().toArray()[0].getX() < 206) {
                                for (int i = 0; i < Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().toArray().length; i++) {
                                    Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().toArray()[i].moveBy(73, 0);
                                    makeBorderElementsInvisible();
                                }
                            }
                        } else if (x < -50) {
                            flagObjectsInIMoved = true;
                            if (Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().toArray()[Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().toArray().length - 1].getX() > 571) {
                                for (int i = 0; i < Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().toArray().length; i++) {
                                    Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().toArray()[i].moveBy(-73, 0);
                                    makeBorderElementsInvisible();
                                }
                            }
                        }
                    }
                    else if (y > 150 & !Cryptex.main.getGlobalReferences().getFlagCubeCalled() & !Cryptex.main.getGlobalReferences().getFlagBIGisVisible()) {
                        flagObjectWentBigger = true;
                        if (name.equals("cryptex_InI")) {
                            for (int i = 0; i < Cryptex.main.getCryptexStageLit().getAllActorsCryptexStage().size; i++) {
                                Cryptex.main.getCryptexStageLit().getAllActorsCryptexStage().get(i).setTouchable(Touchable.enabled);
                            }
                            Cryptex.main.getGlobalReferences().setFlagLiteralCryptexStageStart(true);
                        }
                        else if (name.equals("cryptex_digital_InI")) {
                            for (int i = 0; i < Cryptex.main.getCryptexStageDig().getAllActorsCryptexStage().size; i++) {
                                Cryptex.main.getCryptexStageDig().getAllActorsCryptexStage().get(i).setTouchable(Touchable.enabled);
                            }
                            Cryptex.main.getGlobalReferences().setFlagDigitalCryptexStageStart(true);
                        }
                        else {
                            String nameWithoutInI = name.replace("_InI", "");

                            Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getGs().getGoes_bigger(), 0.5f);
                            showBlackBackground();
                            showBigObject(nameWithoutInI, "inv");
                            if(Cryptex.main.getGlobalReferences().getLevelNumber() == 6) {
                                if(name.equals("maze_map_InI") & Cryptex.main.getMainScreen6().getStaticObjects().getFlagMazeStageStart()){
                                    Cryptex.main.getMainScreen6().getMazeStage().getMap_indicator().setVisible(true);
                                    Cryptex.main.getPanelStage().getStage().addActor(Cryptex.main.getMainScreen6().getMazeStage().getMap_indicator());
                                }
                            }

                            disableAllExceptBIG();
                        }
                    }
                }
            }
        }
    };

    public EventListener getActiveObjectsIL() {
        return activeObjectsIL;
    }

    public int getCell1CubeCounter() {
        return cell1CubeCounter;
    }
    public int getCell2CubeCounter() {
        return cell2CubeCounter;
    }
    public int getCell3CubeCounter() {
        return cell3CubeCounter;
    }
    public int getCell4CubeCounter() {
        return cell4CubeCounter;
    }

    public void setCell1CubeCounter(int cell1CubeCounter) {
        this.cell1CubeCounter = cell1CubeCounter;
    }
    public void setCell2CubeCounter(int cell2CubeCounter) {
        this.cell2CubeCounter = cell2CubeCounter;
    }
    public void setCell3CubeCounter(int cell3CubeCounter) {
        this.cell3CubeCounter = cell3CubeCounter;
    }
    public void setCell4CubeCounter(int cell4CubeCounter) {
        this.cell4CubeCounter = cell4CubeCounter;
    }

    public Texture getTextureBIG() {
        return textureBIG;
    }
    public ActiveObjects getActorBIG() {
        return actorBIG;
    }
}
