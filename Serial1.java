package art.dual.firstLevel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import art.dual.ActiveObjects;
import art.dual.mainMenu.MainMenuScreen;

/**
 * There are some JSON serialization and preferences methods for game saving in this class.
 */
public class Serial1 {

    public Serial1(){}

    public void save(){
        setLampPuzzleStateToDefault();
        setSafePuzzleStateToDefault();

        ArrayList<ActiveObjects> alActiveObjectsOnStage = new ArrayList<ActiveObjects>();
        ArrayList<StaticObjects1> alStaticObjects = new ArrayList<StaticObjects1>();

        for(int i = 0; i < MainScreen1.activeObjectsOnStage.getActorsOnStage().length; i++){
            alActiveObjectsOnStage.add(MainScreen1.activeObjectsOnStage.getActorsOnStage()[i]);
        }

        for(int i = 0; i < MainScreen1.staticObjectsOnStage.getStaticObjects().length; i++){
            alStaticObjects.add(MainScreen1.staticObjectsOnStage.getStaticObjects()[i]);
        }

        String alStringOnStage = MainScreen1.activeObjectsOnStage.getJsonOnStage().toJson(alActiveObjectsOnStage);
        String alStringStatic = MainScreen1.staticObjectsOnStage.getJsonStatic().toJson(alStaticObjects);

        if(MainMenuScreen.saving.getFlagSave1()){
            MainMenuScreen.saving.getFileObjectsOnStageSAVE1().writeString(alStringOnStage, false);
            MainMenuScreen.saving.getFileStaticObjectsSAVE1().writeString(alStringStatic, false);

            saveInPrefsWithNumber(1);

            MainMenuScreen.saving.getPrefsLevelNumber().putInteger("firstSaveCell", MainMenuScreen.globalReferences.getLevelNumber());
            MainMenuScreen.saving.getPrefsLevelNumber().flush();
        }
        else if(MainMenuScreen.saving.getFlagSave2()){
            MainMenuScreen.saving.getFileObjectsOnStageSAVE2().writeString(alStringOnStage, false);
            MainMenuScreen.saving.getFileStaticObjectsSAVE2().writeString(alStringStatic, false);

            saveInPrefsWithNumber(2);

            MainMenuScreen.saving.getPrefsLevelNumber().putInteger("secondSaveCell", MainMenuScreen.globalReferences.getLevelNumber());
            MainMenuScreen.saving.getPrefsLevelNumber().flush();
        }
        else if(MainMenuScreen.saving.getFlagSave3()){
            MainMenuScreen.saving.getFileObjectsOnStageSAVE3().writeString(alStringOnStage, false);
            MainMenuScreen.saving.getFileStaticObjectsSAVE3().writeString(alStringStatic, false);

            saveInPrefsWithNumber(3);

            MainMenuScreen.saving.getPrefsLevelNumber().putInteger("thirdSaveCell", MainMenuScreen.globalReferences.getLevelNumber());
            MainMenuScreen.saving.getPrefsLevelNumber().flush();
        }
        else if(MainMenuScreen.saving.getFlagSave4()){
            MainMenuScreen.saving.getFileObjectsOnStageSAVE4().writeString(alStringOnStage, false);
            MainMenuScreen.saving.getFileStaticObjectsSAVE4().writeString(alStringStatic, false);

            saveInPrefsWithNumber(4);

            MainMenuScreen.saving.getPrefsLevelNumber().putInteger("fourthSaveCell", MainMenuScreen.globalReferences.getLevelNumber());
            MainMenuScreen.saving.getPrefsLevelNumber().flush();
        }
    }

    public void saveInPrefsWithNumber(int prefsNumber){
        for(int i = 0; i < MainScreen1.staticObjects.getArrFlagsNames().length; i++){
            MainMenuScreen.saving.getPrefsGetter(prefsNumber - 1).putBoolean(MainScreen1.staticObjects.getArrFlagsNames()[i], MainScreen1.staticObjects.getGetter(i));
        }

        MainMenuScreen.saving.getPrefsGetter(prefsNumber - 1).putInteger("maxLevelAchieved", MainMenuScreen.globalReferences.getMaxLevelAchieved());
        MainMenuScreen.saving.getPrefsGetter(prefsNumber - 1).putInteger("itemsWeCanCollect1Level", MainMenuScreen.globalReferences.getItemsWeCanCollect1Level());
        MainMenuScreen.saving.getPrefsGetter(prefsNumber - 1).putInteger("journalCurrentLevelNumber", MainMenuScreen.globalReferences.getJournalCurrentLevelNumber());
        MainMenuScreen.saving.getPrefsGetter(prefsNumber - 1).flush();
    }

    public void saveDateAndTimeInPrefsWithNumber(int prefsNumber){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM HH:mm");
        MainMenuScreen.saving.getPrefsGetter(prefsNumber - 1).putString("dateAndTime", dateFormat.format(date));
        MainMenuScreen.saving.getPrefsGetter(prefsNumber - 1).flush();
    }

    public void setLampPuzzleStateToDefault(){
        if(MainMenuScreen.panelStage.getGroupActiveItem().getChildren().toArray().length > 0) {
            MainMenuScreen.globalReferences.setFlagActiveCellOccupied(false);
            MainMenuScreen.activeObjects.addObjectToInventory((ActiveObjects) MainMenuScreen.panelStage.getGroupActiveItem().getChildren().get(0));
        }

        if(MainScreen1.staticObjects.getFlagLampsOnLamp()) {
            if(MainScreen1.lampStage.getGroupUnactiveLampInHolder().getChildren().toArray().length > 0) {
                MainScreen1.lampStage.getGroupUnactiveLampInHolder().getChildren().get(0).setVisible(true);
                MainScreen1.lampStage.getGroupUnactiveLamps().addActor(MainScreen1.lampStage.getGroupUnactiveLampInHolder().getChildren().get(0));
                MainScreen1.staticObjectsOnStage.getArrow_lower().setRotation(0f);
            }

            MainScreen1.lampStage.getGroupActiveLampInHolder().clear();
            MainScreen1.lampStage.getGroupUnactiveLampInHolder().clear();
            MainScreen1.staticObjects.setFlagLampHolderOccupied(false);

            MainScreen1.staticObjectsOnStage.getLamp1_active().setVis(false);
            MainScreen1.staticObjectsOnStage.getLamp2_active().setVis(false);
            MainScreen1.staticObjectsOnStage.getLamp3_active().setVis(false);
            MainScreen1.staticObjectsOnStage.getLamp4_active().setVis(false);
            MainScreen1.staticObjectsOnStage.getLamp5_active().setVis(false);

            MainScreen1.staticObjectsOnStage.getLamp1_unactive().setVis(true);
            MainScreen1.staticObjectsOnStage.getLamp2_unactive().setVis(true);
            MainScreen1.staticObjectsOnStage.getLamp3_unactive().setVis(true);
            MainScreen1.staticObjectsOnStage.getLamp4_unactive().setVis(true);
            MainScreen1.staticObjectsOnStage.getLamp5_unactive().setVis(true);

            MainScreen1.staticObjectsOnStage.getLamp1_unactive().setPosition(26f, 279f);
            MainScreen1.staticObjectsOnStage.getLamp2_unactive().setPosition(26f, 136f);
            MainScreen1.staticObjectsOnStage.getLamp3_unactive().setPosition(641f, 316f);
            MainScreen1.staticObjectsOnStage.getLamp4_unactive().setPosition(661f, 226f);
            MainScreen1.staticObjectsOnStage.getLamp5_unactive().setPosition(668f, 111f);

            MainScreen1.lampStage.getGroupUnactiveLamps().clear();
            MainScreen1.lampStage.getGroupUnactiveLamps().addActor(MainScreen1.staticObjectsOnStage.getLamp1_unactive());
            MainScreen1.lampStage.getGroupUnactiveLamps().addActor(MainScreen1.staticObjectsOnStage.getLamp2_unactive());
            MainScreen1.lampStage.getGroupUnactiveLamps().addActor(MainScreen1.staticObjectsOnStage.getLamp3_unactive());
            MainScreen1.lampStage.getGroupUnactiveLamps().addActor(MainScreen1.staticObjectsOnStage.getLamp4_unactive());
            MainScreen1.lampStage.getGroupUnactiveLamps().addActor(MainScreen1.staticObjectsOnStage.getLamp5_unactive());
            MainScreen1.lampStage.getGroupUnactiveLamps().setVisible(true);
        }
    }

    public void setSafePuzzleStateToDefault(){
        for(int i = 0; i < MainScreen1.staticObjectsOnStage.getArrGreenLamps().length; i++){
            MainScreen1.staticObjectsOnStage.getArrGreenLamps()[i].setVis(false);
            MainScreen1.staticObjectsOnStage.getArrRedLamps()[i].setVis(false);
        }

        MainScreen1.staticObjectsOnStage.getRoller().setRotation(0f);
    }
}
