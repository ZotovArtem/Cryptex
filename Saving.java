package art.dual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;

/**
 * Here i keep all necessary files to save game data.
 */

public class Saving {
    private boolean flagSave1, flagSave2, flagSave3, flagSave4, flagSave1Visual, flagSave2Visual, flagSave3Visual, flagSave4Visual;
    private Preferences prefs1, prefs2, prefs3, prefs4, prefsLevelNumber, prefsMountedObjects, prefsEPDoor,
            prefsJOTransition,
            prefs4Level1, prefs4Level2, prefs4Level3, prefs4Level4, prefs5Level1, prefs5Level2, prefs5Level3, prefs5Level4, prefs6Level1, prefs6Level2,
            prefs6Level3, prefs6Level4, prefsPause;
    private FileHandle fileObjectsInISAVE1, fileObjectsInISAVE2, fileObjectsInISAVE3, fileObjectsInISAVE4, fileObjectsInIPause,
            fileCakeInISAVE1, fileCakeInISAVE2, fileCakeInISAVE3, fileCakeInISAVE4, fileCakeInIPause,
            fileObjectsOnStageSAVE1, fileObjectsOnStageSAVE2, fileObjectsOnStageSAVE3, fileObjectsOnStageSAVE4, fileObjectsOnStagePause,
            fileStaticObjectsSAVE1, fileStaticObjectsSAVE2, fileStaticObjectsSAVE3, fileStaticObjectsSAVE4, fileStaticObjectsPause,
            fileObjectsInITransfer,

            fileObjectsOnStage4COMPLevelSAVE, fileStaticObjects4COMPLevelSAVE, fileObjectsOnStage5LevelSAVE, fileStaticObjects5LevelSAVE,
            fileObjectsOnStage6LevelSAVE, fileStaticObjects6LevelSAVE,

            fileObjectsOnStage4COMPLevelSAVE1, fileStaticObjects4COMPLevelSAVE1, fileObjectsOnStage4COMPLevelSAVE2, fileStaticObjects4COMPLevelSAVE2,
            fileObjectsOnStage4COMPLevelSAVE3, fileStaticObjects4COMPLevelSAVE3, fileObjectsOnStage4COMPLevelSAVE4, fileStaticObjects4COMPLevelSAVE4,
            fileObjectsOnStage4COMPLevelPause, fileStaticObjects4COMPLevelPause,

            fileObjectsOnStage5LevelSAVE1, fileStaticObjects5LevelSAVE1, fileObjectsOnStage5LevelSAVE2, fileStaticObjects5LevelSAVE2,
            fileObjectsOnStage5LevelSAVE3, fileStaticObjects5LevelSAVE3, fileObjectsOnStage5LevelSAVE4, fileStaticObjects5LevelSAVE4,
            fileObjectsOnStage5LevelPause, fileStaticObjects5LevelPause,

            fileObjectsOnStage6LevelSAVE1, fileStaticObjects6LevelSAVE1, fileObjectsOnStage6LevelSAVE2, fileStaticObjects6LevelSAVE2,
            fileObjectsOnStage6LevelSAVE3, fileStaticObjects6LevelSAVE3, fileObjectsOnStage6LevelSAVE4, fileStaticObjects6LevelSAVE4,
            fileObjectsOnStage6LevelPause, fileStaticObjects6LevelPause,

            fileJOSAVE1, fileJOSAVE2, fileJOSAVE3, fileJOSAVE4;
    private boolean flagLoad1, flagLoad2, flagLoad3, flagLoad4, flag1LOADED, flag2LOADED, flag3LOADED, flag4LOADED, flagPause;

    public Saving(){
        fileObjectsInISAVE1 = Gdx.files.local("Saves/fileObjectsInISAVE1.json");
        fileObjectsOnStageSAVE1 = Gdx.files.local("Saves/fileObjectsOnStageSAVE1.json");
        fileStaticObjectsSAVE1 = Gdx.files.local("Saves/fileStaticObjectsSAVE1.json");
        fileJOSAVE1 = Gdx.files.local("Saves/fileJOSAVE1.json");
        fileCakeInISAVE1 = Gdx.files.local("Saves/fileCakeInISAVE1.json");

        fileObjectsInISAVE2 = Gdx.files.local("Saves/fileObjectsInISAVE2.json");
        fileObjectsOnStageSAVE2 = Gdx.files.local("Saves/fileObjectsOnStageSAVE2.json");
        fileStaticObjectsSAVE2 = Gdx.files.local("Saves/fileStaticObjectsSAVE2.json");
        fileJOSAVE2 = Gdx.files.local("Saves/fileJOSAVE2.json");
        fileCakeInISAVE2 = Gdx.files.local("Saves/fileCakeInISAVE2.json");

        fileObjectsInISAVE3 = Gdx.files.local("Saves/fileObjectsInISAVE3.json");
        fileObjectsOnStageSAVE3 = Gdx.files.local("Saves/fileObjectsOnStageSAVE3.json");
        fileStaticObjectsSAVE3 = Gdx.files.local("Saves/fileStaticObjectsSAVE3.json");
        fileJOSAVE3 = Gdx.files.local("Saves/fileJOSAVE3.json");
        fileCakeInISAVE3 = Gdx.files.local("Saves/fileCakeInISAVE3.json");

        fileObjectsInISAVE4 = Gdx.files.local("Saves/fileObjectsInISAVE4.json");
        fileObjectsOnStageSAVE4 = Gdx.files.local("Saves/fileObjectsOnStageSAVE4.json");
        fileStaticObjectsSAVE4 = Gdx.files.local("Saves/fileStaticObjectsSAVE4.json");
        fileJOSAVE4 = Gdx.files.local("Saves/fileJOSAVE4.json");
        fileCakeInISAVE4 = Gdx.files.local("Saves/fileCakeInISAVE4.json");

        fileObjectsInITransfer = Gdx.files.local("Saves/fileObjectsInITransfer.json");

        fileObjectsOnStage4COMPLevelSAVE = Gdx.files.local("Saves/fileObjectsOnStage4COMPLevelSAVE.json");
        fileStaticObjects4COMPLevelSAVE = Gdx.files.local("Saves/fileStaticObjects4COMPLevelSAVE.json");

        fileObjectsOnStage5LevelSAVE = Gdx.files.local("Saves/fileObjectsOnStage5LevelSAVE.json");
        fileStaticObjects5LevelSAVE = Gdx.files.local("Saves/fileStaticObjects5LevelSAVE.json");

        fileObjectsOnStage6LevelSAVE = Gdx.files.local("Saves/fileObjectsOnStage6LevelSAVE.json");
        fileStaticObjects6LevelSAVE = Gdx.files.local("Saves/fileStaticObjects6LevelSAVE.json");

        fileObjectsOnStage4COMPLevelSAVE1 = Gdx.files.local("Saves/fileObjectsOnStage4COMPLevelSAVE1.json");
        fileStaticObjects4COMPLevelSAVE1 = Gdx.files.local("Saves/fileStaticObjects4COMPLevelSAVE1.json");
        fileObjectsOnStage4COMPLevelSAVE2 = Gdx.files.local("Saves/fileObjectsOnStage4COMPLevelSAVE2.json");
        fileStaticObjects4COMPLevelSAVE2 = Gdx.files.local("Saves/fileStaticObjects4COMPLevelSAVE2.json");
        fileObjectsOnStage4COMPLevelSAVE3 = Gdx.files.local("Saves/fileObjectsOnStage4COMPLevelSAVE3.json");
        fileStaticObjects4COMPLevelSAVE3 = Gdx.files.local("Saves/fileStaticObjects4COMPLevelSAVE3.json");
        fileObjectsOnStage4COMPLevelSAVE4 = Gdx.files.local("Saves/fileObjectsOnStage4COMPLevelSAVE4.json");
        fileStaticObjects4COMPLevelSAVE4 = Gdx.files.local("Saves/fileStaticObjects4COMPLevelSAVE4.json");

        fileObjectsOnStage5LevelSAVE1 = Gdx.files.local("Saves/fileObjectsOnStage5LevelSAVE1.json");
        fileStaticObjects5LevelSAVE1 = Gdx.files.local("Saves/fileStaticObjects5LevelSAVE1.json");
        fileObjectsOnStage5LevelSAVE2 = Gdx.files.local("Saves/fileObjectsOnStage5LevelSAVE2.json");
        fileStaticObjects5LevelSAVE2 = Gdx.files.local("Saves/fileStaticObjects5LevelSAVE2.json");
        fileObjectsOnStage5LevelSAVE3 = Gdx.files.local("Saves/fileObjectsOnStage5LevelSAVE3.json");
        fileStaticObjects5LevelSAVE3 = Gdx.files.local("Saves/fileStaticObjects5LevelSAVE3.json");
        fileObjectsOnStage5LevelSAVE4 = Gdx.files.local("Saves/fileObjectsOnStage5LevelSAVE4.json");
        fileStaticObjects5LevelSAVE4 = Gdx.files.local("Saves/fileStaticObjects5LevelSAVE4.json");

        fileObjectsOnStage6LevelSAVE1 = Gdx.files.local("Saves/fileObjectsOnStage6LevelSAVE1.json");
        fileStaticObjects6LevelSAVE1 = Gdx.files.local("Saves/fileStaticObjects6LevelSAVE1.json");
        fileObjectsOnStage6LevelSAVE2 = Gdx.files.local("Saves/fileObjectsOnStage6LevelSAVE2.json");
        fileStaticObjects6LevelSAVE2 = Gdx.files.local("Saves/fileStaticObjects6LevelSAVE2.json");
        fileObjectsOnStage6LevelSAVE3 = Gdx.files.local("Saves/fileObjectsOnStage6LevelSAVE3.json");
        fileStaticObjects6LevelSAVE3 = Gdx.files.local("Saves/fileStaticObjects6LevelSAVE3.json");
        fileObjectsOnStage6LevelSAVE4 = Gdx.files.local("Saves/fileObjectsOnStage6LevelSAVE4.json");
        fileStaticObjects6LevelSAVE4 = Gdx.files.local("Saves/fileStaticObjects6LevelSAVE4.json");

        fileObjectsOnStage4COMPLevelPause = Gdx.files.local("Saves/fileObjectsOnStage4COMPLevelPause.json");
        fileStaticObjects4COMPLevelPause = Gdx.files.local("Saves/fileStaticObjects4COMPLevelPause.json");
        fileObjectsOnStage5LevelPause = Gdx.files.local("Saves/fileObjectsOnStage5LevelPause.json");
        fileStaticObjects5LevelPause = Gdx.files.local("Saves/fileStaticObjects5LevelPause.json");
        fileObjectsOnStage6LevelPause = Gdx.files.local("Saves/fileObjectsOnStage6LevelPause.json");
        fileStaticObjects6LevelPause = Gdx.files.local("Saves/fileStaticObjects6LevelPause.json");

        fileObjectsInIPause = Gdx.files.local("Saves/fileObjectsInIPause.json");
        fileCakeInIPause = Gdx.files.local("Saves/fileCakeInIPause.json");
        fileObjectsOnStagePause = Gdx.files.local("Saves/fileObjectsOnStagePause.json");
        fileStaticObjectsPause = Gdx.files.local("Saves/fileStaticObjectsPause.json");

        prefs1 = Gdx.app.getPreferences("prefs1");
        prefs2 = Gdx.app.getPreferences("prefs2");
        prefs3 = Gdx.app.getPreferences("prefs3");
        prefs4 = Gdx.app.getPreferences("prefs4");
        prefsLevelNumber = Gdx.app.getPreferences("LevelNumber");
        prefsMountedObjects = Gdx.app.getPreferences("prefsMountedObjects");
        prefsEPDoor = Gdx.app.getPreferences("prefsEPDoor");
        prefsJOTransition = Gdx.app.getPreferences("prefsJOTransition");

        prefs4Level1 = Gdx.app.getPreferences("prefs4Level1");
        prefs4Level2 = Gdx.app.getPreferences("prefs4Level2");
        prefs4Level3 = Gdx.app.getPreferences("prefs4Level3");
        prefs4Level4 = Gdx.app.getPreferences("prefs4Level4");

        prefs5Level1 = Gdx.app.getPreferences("prefs5Level1");
        prefs5Level2 = Gdx.app.getPreferences("prefs5Level2");
        prefs5Level3 = Gdx.app.getPreferences("prefs5Level3");
        prefs5Level4 = Gdx.app.getPreferences("prefs5Level4");

        prefs6Level1 = Gdx.app.getPreferences("prefs6Level1");
        prefs6Level2 = Gdx.app.getPreferences("prefs6Level2");
        prefs6Level3 = Gdx.app.getPreferences("prefs6Level3");
        prefs6Level4 = Gdx.app.getPreferences("prefs6Level4");

        prefsPause = Gdx.app.getPreferences("prefsPause");
    }

    public boolean getFlagSave1() {
        return flagSave1;
    }
    public boolean getFlagSave2() {
        return flagSave2;
    }
    public boolean getFlagSave3() {
        return flagSave3;
    }
    public boolean getFlagSave4() {
        return flagSave4;
    }
    public boolean getFlagSave1Visual() {
        return flagSave1Visual;
    }
    public boolean getFlagSave2Visual() {
        return flagSave2Visual;
    }
    public boolean getFlagSave3Visual() {
        return flagSave3Visual;
    }
    public boolean getFlagSave4Visual() {
        return flagSave4Visual;
    }
    public Preferences getPrefs1() {
        return prefs1;
    }
    public Preferences getPrefs2() {
        return prefs2;
    }
    public Preferences getPrefs3() {
        return prefs3;
    }
    public Preferences getPrefs4() {
        return prefs4;
    }
    public Preferences getPrefs4Level1() {
        return prefs4Level1;
    }
    public Preferences getPrefs4Level2() {
        return prefs4Level2;
    }
    public Preferences getPrefs4Level3() {
        return prefs4Level3;
    }
    public Preferences getPrefs4Level4() {
        return prefs4Level4;
    }
    public Preferences getPrefs5Level1() {
        return prefs5Level1;
    }
    public Preferences getPrefs5Level2() {
        return prefs5Level2;
    }
    public Preferences getPrefs5Level3() {
        return prefs5Level3;
    }
    public Preferences getPrefs5Level4() {
        return prefs5Level4;
    }
    public Preferences getPrefs6Level1() {
        return prefs6Level1;
    }
    public Preferences getPrefs6Level2() {
        return prefs6Level2;
    }
    public Preferences getPrefs6Level3() {
        return prefs6Level3;
    }
    public Preferences getPrefs6Level4() {
        return prefs6Level4;
    }

    public Preferences getPrefsLevelNumber() {
        return prefsLevelNumber;
    }
    public FileHandle getFileObjectsInISAVE1() {
        return fileObjectsInISAVE1;
    }
    public FileHandle getFileObjectsInISAVE2() {
        return fileObjectsInISAVE2;
    }
    public FileHandle getFileObjectsInISAVE3() {
        return fileObjectsInISAVE3;
    }
    public FileHandle getFileObjectsInISAVE4() {
        return fileObjectsInISAVE4;
    }
    public FileHandle getFileCakeInISAVE1() {
        return fileCakeInISAVE1;
    }
    public FileHandle getFileCakeInISAVE2() {
        return fileCakeInISAVE2;
    }
    public FileHandle getFileCakeInISAVE3() {
        return fileCakeInISAVE3;
    }
    public FileHandle getFileCakeInISAVE4() {
        return fileCakeInISAVE4;
    }
    public FileHandle getFileObjectsOnStageSAVE1() {
        return fileObjectsOnStageSAVE1;
    }
    public FileHandle getFileObjectsOnStageSAVE2() {
        return fileObjectsOnStageSAVE2;
    }
    public FileHandle getFileObjectsOnStageSAVE3() {
        return fileObjectsOnStageSAVE3;
    }
    public FileHandle getFileObjectsOnStageSAVE4() {
        return fileObjectsOnStageSAVE4;
    }
    public FileHandle getFileStaticObjectsSAVE1() {
        return fileStaticObjectsSAVE1;
    }
    public FileHandle getFileStaticObjectsSAVE2() {
        return fileStaticObjectsSAVE2;
    }
    public FileHandle getFileStaticObjectsSAVE3() {
        return fileStaticObjectsSAVE3;
    }
    public FileHandle getFileStaticObjectsSAVE4() {
        return fileStaticObjectsSAVE4;
    }
    public FileHandle getFileObjectsInITransfer() {
        return fileObjectsInITransfer;
    }
    public FileHandle getFileObjectsOnStage4COMPLevelSAVE() {
        return fileObjectsOnStage4COMPLevelSAVE;
    }
    public FileHandle getFileStaticObjects4COMPLevelSAVE() {
        return fileStaticObjects4COMPLevelSAVE;
    }
    public boolean getFlagLoad1() {
        return flagLoad1;
    }
    public boolean getFlagLoad2() {
        return flagLoad2;
    }
    public boolean getFlagLoad3() {
        return flagLoad3;
    }
    public boolean getFlagLoad4() {
        return flagLoad4;
    }
    public boolean getFlag1LOADED() {
        return flag1LOADED;
    }
    public boolean getFlag2LOADED() {
        return flag2LOADED;
    }
    public boolean getFlag3LOADED() {
        return flag3LOADED;
    }
    public boolean getFlag4LOADED() {
        return flag4LOADED;
    }
    public boolean getFlagPause() {
        return flagPause;
    }

    public FileHandle getFileJOSAVE1() {
        return fileJOSAVE1;
    }
    public FileHandle getFileJOSAVE2() {
        return fileJOSAVE2;
    }
    public FileHandle getFileJOSAVE3() {
        return fileJOSAVE3;
    }
    public FileHandle getFileJOSAVE4() {
        return fileJOSAVE4;
    }
    public Preferences getPrefsMountedObjects() {
        return prefsMountedObjects;
    }
    public Preferences getPrefsEPDoor() {
        return prefsEPDoor;
    }
    public FileHandle getFileObjectsOnStage5LevelSAVE() {
        return fileObjectsOnStage5LevelSAVE;
    }
    public FileHandle getFileObjectsOnStage6LevelSAVE() {
        return fileObjectsOnStage6LevelSAVE;
    }
    public FileHandle getFileStaticObjects5LevelSAVE() {
        return fileStaticObjects5LevelSAVE;
    }
    public FileHandle getFileStaticObjects6LevelSAVE() {
        return fileStaticObjects6LevelSAVE;
    }
    public FileHandle getFileObjectsOnStage4COMPLevelSAVE1() {
        return fileObjectsOnStage4COMPLevelSAVE1;
    }
    public FileHandle getFileStaticObjects4COMPLevelSAVE1() {
        return fileStaticObjects4COMPLevelSAVE1;
    }
    public FileHandle getFileObjectsOnStage4COMPLevelSAVE2() {
        return fileObjectsOnStage4COMPLevelSAVE2;
    }
    public FileHandle getFileStaticObjects4COMPLevelSAVE2() {
        return fileStaticObjects4COMPLevelSAVE2;
    }
    public FileHandle getFileObjectsOnStage4COMPLevelSAVE3() {
        return fileObjectsOnStage4COMPLevelSAVE3;
    }
    public FileHandle getFileStaticObjects4COMPLevelSAVE3() {
        return fileStaticObjects4COMPLevelSAVE3;
    }
    public FileHandle getFileObjectsOnStage4COMPLevelSAVE4() {
        return fileObjectsOnStage4COMPLevelSAVE4;
    }
    public FileHandle getFileStaticObjects4COMPLevelSAVE4() {
        return fileStaticObjects4COMPLevelSAVE4;
    }
    public FileHandle getFileObjectsOnStage5LevelSAVE1() {
        return fileObjectsOnStage5LevelSAVE1;
    }
    public FileHandle getFileStaticObjects5LevelSAVE1() {
        return fileStaticObjects5LevelSAVE1;
    }
    public FileHandle getFileObjectsOnStage5LevelSAVE2() {
        return fileObjectsOnStage5LevelSAVE2;
    }
    public FileHandle getFileStaticObjects5LevelSAVE2() {
        return fileStaticObjects5LevelSAVE2;
    }
    public FileHandle getFileObjectsOnStage5LevelSAVE3() {
        return fileObjectsOnStage5LevelSAVE3;
    }
    public FileHandle getFileStaticObjects5LevelSAVE3() {
        return fileStaticObjects5LevelSAVE3;
    }
    public FileHandle getFileObjectsOnStage5LevelSAVE4() {
        return fileObjectsOnStage5LevelSAVE4;
    }
    public FileHandle getFileStaticObjects5LevelSAVE4() {
        return fileStaticObjects5LevelSAVE4;
    }
    public FileHandle getFileObjectsOnStage6LevelSAVE1() {
        return fileObjectsOnStage6LevelSAVE1;
    }
    public FileHandle getFileStaticObjects6LevelSAVE1() {
        return fileStaticObjects6LevelSAVE1;
    }
    public FileHandle getFileObjectsOnStage6LevelSAVE2() {
        return fileObjectsOnStage6LevelSAVE2;
    }
    public FileHandle getFileStaticObjects6LevelSAVE2() {
        return fileStaticObjects6LevelSAVE2;
    }
    public FileHandle getFileObjectsOnStage6LevelSAVE3() {
        return fileObjectsOnStage6LevelSAVE3;
    }
    public FileHandle getFileStaticObjects6LevelSAVE3() {
        return fileStaticObjects6LevelSAVE3;
    }
    public FileHandle getFileObjectsOnStage6LevelSAVE4() {
        return fileObjectsOnStage6LevelSAVE4;
    }
    public FileHandle getFileStaticObjects6LevelSAVE4() {
        return fileStaticObjects6LevelSAVE4;
    }

    public FileHandle getFileObjectsOnStage4COMPLevelPause() {
        return fileObjectsOnStage4COMPLevelPause;
    }
    public FileHandle getFileStaticObjects4COMPLevelPause() {
        return fileStaticObjects4COMPLevelPause;
    }
    public FileHandle getFileObjectsOnStage5LevelPause() {
        return fileObjectsOnStage5LevelPause;
    }
    public FileHandle getFileStaticObjects5LevelPause() {
        return fileStaticObjects5LevelPause;
    }
    public FileHandle getFileObjectsOnStage6LevelPause() {
        return fileObjectsOnStage6LevelPause;
    }
    public FileHandle getFileStaticObjects6LevelPause() {
        return fileStaticObjects6LevelPause;
    }

    public Preferences getPrefsPause() {
        return prefsPause;
    }
    public FileHandle getFileObjectsInIPause() {
        return fileObjectsInIPause;
    }
    public FileHandle getFileObjectsOnStagePause() {
        return fileObjectsOnStagePause;
    }
    public FileHandle getFileStaticObjectsPause() {
        return fileStaticObjectsPause;
    }
    public FileHandle getFileCakeInIPause() {
        return fileCakeInIPause;
    }

    public void setFlagSave1(boolean flagSave1) {
        this.flagSave1 = flagSave1;
    }
    public void setFlagSave2(boolean flagSave2) {
        this.flagSave2 = flagSave2;
    }
    public void setFlagSave3(boolean flagSave3) {
        this.flagSave3 = flagSave3;
    }
    public void setFlagSave4(boolean flagSave4) {
        this.flagSave4 = flagSave4;
    }
    public void setFlagSave1Visual(boolean flagSave1Visual) {
        this.flagSave1Visual = flagSave1Visual;
    }
    public void setFlagSave2Visual(boolean flagSave2Visual) {
        this.flagSave2Visual = flagSave2Visual;
    }
    public void setFlagSave3Visual(boolean flagSave3Visual) {
        this.flagSave3Visual = flagSave3Visual;
    }
    public void setFlagSave4Visual(boolean flagSave4Visual) {
        this.flagSave4Visual = flagSave4Visual;
    }
    public void setFlagLoad1(boolean flagLoad1) {
        this.flagLoad1 = flagLoad1;
    }
    public void setFlagLoad2(boolean flagLoad2) {
        this.flagLoad2 = flagLoad2;
    }
    public void setFlagLoad3(boolean flagLoad3) {
        this.flagLoad3 = flagLoad3;
    }
    public void setFlagLoad4(boolean flagLoad4) {
        this.flagLoad4 = flagLoad4;
    }
    public void setFlag1LOADED(boolean flag1LOADED) {
        this.flag1LOADED = flag1LOADED;
    }
    public void setFlag2LOADED(boolean flag2LOADED) {
        this.flag2LOADED = flag2LOADED;
    }
    public void setFlag3LOADED(boolean flag3LOADED) {
        this.flag3LOADED = flag3LOADED;
    }
    public void setFlag4LOADED(boolean flag4LOADED) {
        this.flag4LOADED = flag4LOADED;
    }
    public void setFlagPause(boolean flagPause) {
        this.flagPause = flagPause;
    }

    public Preferences getPrefsJOTransition() {
        return prefsJOTransition;
    }

    public void saveJournalMaterials(int prefsNumber){
        for(int i = 0; i < Cryptex.main.getJo().getArrAL().length; i++){
            String length = Integer.toString(i) + "length";
            Cryptex.main.getSaving().getPrefsGetter(prefsNumber - 1).putInteger(length, Cryptex.main.getJo().getArrAL()[i].size());
            for(int j = 0; j < Cryptex.main.getJo().getArrAL()[i].size(); j++){
                String name = Integer.toString(i) + "_" + Integer.toString(j);
                Cryptex.main.getSaving().getPrefsGetter(prefsNumber - 1).putInteger(name, Cryptex.main.getJo().getArrAL()[i].get(j));
            }
        }
    }

    public void savePauseJournalMaterials(){
        for(int i = 0; i < Cryptex.main.getJo().getArrAL().length; i++){
            String length = Integer.toString(i) + "length";
            Cryptex.main.getSaving().getPrefsPause().putInteger(length, Cryptex.main.getJo().getArrAL()[i].size());
            for(int j = 0; j < Cryptex.main.getJo().getArrAL()[i].size(); j++){
                String name = Integer.toString(i) + "_" + Integer.toString(j);
                Cryptex.main.getSaving().getPrefsPause().putInteger(name, Cryptex.main.getJo().getArrAL()[i].get(j));
            }
        }
    }

    public void loadJournalMaterials(int prefsNumber){
        for(int j = 0; j < Cryptex.main.getJo().getArrAL().length; j++) {
            String index = Integer.toString(j) + "length";
            for (int i = 0; i < Cryptex.main.getSaving().getPrefsGetter(prefsNumber - 1).getInteger(index); i++) {
                String name = Integer.toString(j) + "_" + Integer.toString(i);
                Cryptex.main.getJo().getArrAL()[j].add(Cryptex.main.getSaving().getPrefsGetter(prefsNumber - 1).getInteger(name));
                if(Cryptex.main.getSaving().getPrefsGetter(prefsNumber - 1).getInteger(name) != -1) {
                    Cryptex.main.getJournalStage().getStage().addActor(Cryptex.main.getJo().getArrJO()[j][Cryptex.main.getSaving().getPrefsGetter(prefsNumber - 1).getInteger(name)]);
                }
            }
        }
    }

    public void loadPauseJournalMaterials(){
        for(int j = 0; j < Cryptex.main.getJo().getArrAL().length; j++) {
            String index = Integer.toString(j) + "length";
            for (int i = 0; i < Cryptex.main.getSaving().getPrefsPause().getInteger(index); i++) {
                String name = Integer.toString(j) + "_" + Integer.toString(i);
                Cryptex.main.getJo().getArrAL()[j].add(Cryptex.main.getSaving().getPrefsPause().getInteger(name));
                if(Cryptex.main.getSaving().getPrefsPause().getInteger(name) != -1) {
                    Cryptex.main.getJournalStage().getStage().addActor(Cryptex.main.getJo().getArrJO()[j][Cryptex.main.getSaving().getPrefsPause().getInteger(name)]);
                }
            }
        }
    }

//4th COMP level file STATIC
    interface interGet4STATIC{
        FileHandle get();
    }

    private interGet4STATIC[] arr4STATIC = new interGet4STATIC[]{
            new interGet4STATIC() {@Override public FileHandle get() {return getFileStaticObjects4COMPLevelSAVE1();}},
            new interGet4STATIC() {@Override public FileHandle get() {return getFileStaticObjects4COMPLevelSAVE2();}},
            new interGet4STATIC() {@Override public FileHandle get() {return getFileStaticObjects4COMPLevelSAVE3();}},
            new interGet4STATIC() {@Override public FileHandle get() {return getFileStaticObjects4COMPLevelSAVE4();}},
    };

    public FileHandle get4STATIC(int i){
        return arr4STATIC[i].get();
    }

//5th level file STATIC
    interface interGet5STATIC{
        FileHandle get();
    }

    private interGet5STATIC[] arr5STATIC = new interGet5STATIC[]{
            new interGet5STATIC() {@Override public FileHandle get() {return getFileStaticObjects5LevelSAVE1();}},
            new interGet5STATIC() {@Override public FileHandle get() {return getFileStaticObjects5LevelSAVE2();}},
            new interGet5STATIC() {@Override public FileHandle get() {return getFileStaticObjects5LevelSAVE3();}},
            new interGet5STATIC() {@Override public FileHandle get() {return getFileStaticObjects5LevelSAVE4();}},
    };

    public FileHandle get5STATIC(int i){
        return arr5STATIC[i].get();
    }

//6th level file STATIC
    interface interGet6STATIC{
        FileHandle get();
    }

    private interGet6STATIC[] arr6STATIC = new interGet6STATIC[]{
            new interGet6STATIC() {@Override public FileHandle get() {return getFileStaticObjects6LevelSAVE1();}},
            new interGet6STATIC() {@Override public FileHandle get() {return getFileStaticObjects6LevelSAVE2();}},
            new interGet6STATIC() {@Override public FileHandle get() {return getFileStaticObjects6LevelSAVE3();}},
            new interGet6STATIC() {@Override public FileHandle get() {return getFileStaticObjects6LevelSAVE4();}},
    };

    public FileHandle get6STATIC(int i){
        return arr6STATIC[i].get();
    }


//4th COMP level file ACTIVE
    interface interGet4ACTIVE{
        FileHandle get();
    }

    private interGet4ACTIVE[] arr4ACTIVE = new interGet4ACTIVE[]{
            new interGet4ACTIVE() {@Override public FileHandle get() {return getFileObjectsOnStage4COMPLevelSAVE1();}},
            new interGet4ACTIVE() {@Override public FileHandle get() {return getFileObjectsOnStage4COMPLevelSAVE2();}},
            new interGet4ACTIVE() {@Override public FileHandle get() {return getFileObjectsOnStage4COMPLevelSAVE3();}},
            new interGet4ACTIVE() {@Override public FileHandle get() {return getFileObjectsOnStage4COMPLevelSAVE4();}},
    };

    public FileHandle get4ACTIVE(int i){
        return arr4ACTIVE[i].get();
    }

//5th level file ACTIVE
    interface interGet5ACTIVE{
        FileHandle get();
    }

    private interGet5ACTIVE[] arr5ACTIVE = new interGet5ACTIVE[]{
            new interGet5ACTIVE() {@Override public FileHandle get() {return getFileObjectsOnStage5LevelSAVE1();}},
            new interGet5ACTIVE() {@Override public FileHandle get() {return getFileObjectsOnStage5LevelSAVE2();}},
            new interGet5ACTIVE() {@Override public FileHandle get() {return getFileObjectsOnStage5LevelSAVE3();}},
            new interGet5ACTIVE() {@Override public FileHandle get() {return getFileObjectsOnStage5LevelSAVE4();}},
    };

    public FileHandle get5ACTIVE(int i){
        return arr5ACTIVE[i].get();
    }

//6th level file ACTIVE
    interface interGet6ACTIVE{
        FileHandle get();
    }

    private interGet6ACTIVE[] arr6ACTIVE = new interGet6ACTIVE[]{
            new interGet6ACTIVE() {@Override public FileHandle get() {return getFileObjectsOnStage6LevelSAVE1();}},
            new interGet6ACTIVE() {@Override public FileHandle get() {return getFileObjectsOnStage6LevelSAVE2();}},
            new interGet6ACTIVE() {@Override public FileHandle get() {return getFileObjectsOnStage6LevelSAVE3();}},
            new interGet6ACTIVE() {@Override public FileHandle get() {return getFileObjectsOnStage6LevelSAVE4();}},
    };

    public FileHandle get6ACTIVE(int i){
        return arr6ACTIVE[i].get();
    }



    interface interGetPrefs{
        Preferences get();
    }

    private interGetPrefs[] arrPrefsGetters = new interGetPrefs[]{
            new interGetPrefs() {@Override public Preferences get() {return getPrefs1();}},
            new interGetPrefs() {@Override public Preferences get() {return getPrefs2();}},
            new interGetPrefs() {@Override public Preferences get() {return getPrefs3();}},
            new interGetPrefs() {@Override public Preferences get() {return getPrefs4();}},
    };

    public Preferences getPrefsGetter(int i){
        return arrPrefsGetters[i].get();
    }

    public void loadFlagToDefault(){
        setFlagLoad1(false);
        setFlagLoad2(false);
        setFlagLoad3(false);
        setFlagLoad4(false);
        setFlag1LOADED(false);
        setFlag2LOADED(false);
        setFlag3LOADED(false);
        setFlag4LOADED(false);
        setFlagPause(false);
    }
}
