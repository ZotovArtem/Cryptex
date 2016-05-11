package art.dual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;

/**
 * Here i keep all necessary files to save game data.
 */
public class Saving {

    private boolean flagSave1, flagSave2, flagSave3, flagSave4, flagSave1Visual, flagSave2Visual, flagSave3Visual, flagSave4Visual;
    private Preferences prefs1, prefs2, prefs3, prefs4, prefsLevelNumber, prefsMountedObjects, prefsEPDoor, prefs4COMPLETEDLevel, prefs5Level, prefs6Level;
    private FileHandle fileObjectsInISAVE1, fileObjectsInISAVE2, fileObjectsInISAVE3, fileObjectsInISAVE4, fileObjectsOnStageSAVE1, fileObjectsOnStageSAVE2,
            fileObjectsOnStageSAVE3, fileObjectsOnStageSAVE4, fileStaticObjectsSAVE1, fileStaticObjectsSAVE2, fileStaticObjectsSAVE3, fileStaticObjectsSAVE4,
            fileObjectsInITransfer, fileObjectsOnStage5LevelSAVE, fileObjectsOnStage6LevelSAVE, fileStaticObjects5LevelSAVE, fileStaticObjects6LevelSAVE;
    private boolean flagLoad1, flagLoad2, flagLoad3, flagLoad4, flag1LOADED, flag2LOADED, flag3LOADED, flag4LOADED;

    public Saving(){
        fileObjectsInISAVE1 = Gdx.files.local("fileObjectsInISAVE1.json");
        fileObjectsOnStageSAVE1 = Gdx.files.local("fileObjectsOnStageSAVE1.json");
        fileStaticObjectsSAVE1 = Gdx.files.local("fileStaticObjectsSAVE1.json");

        fileObjectsInISAVE2 = Gdx.files.local("fileObjectsInISAVE2.json");
        fileObjectsOnStageSAVE2 = Gdx.files.local("fileObjectsOnStageSAVE2.json");
        fileStaticObjectsSAVE2 = Gdx.files.local("fileStaticObjectsSAVE2.json");

        fileObjectsInISAVE3 = Gdx.files.local("fileObjectsInISAVE3.json");
        fileObjectsOnStageSAVE3 = Gdx.files.local("fileObjectsOnStageSAVE3.json");
        fileStaticObjectsSAVE3 = Gdx.files.local("fileStaticObjectsSAVE3.json");

        fileObjectsInISAVE4 = Gdx.files.local("fileObjectsInISAVE4.json");
        fileObjectsOnStageSAVE4 = Gdx.files.local("fileObjectsOnStageSAVE4.json");
        fileStaticObjectsSAVE4 = Gdx.files.local("fileStaticObjectsSAVE4.json");

        fileObjectsInITransfer = Gdx.files.local("fileObjectsInITransfer.json");

        fileObjectsOnStage5LevelSAVE = Gdx.files.local("fileObjectsOnStage5LevelSAVE.json");
        fileStaticObjects5LevelSAVE = Gdx.files.local("fileStaticObjects5LevelSAVE.json");

        fileObjectsOnStage6LevelSAVE = Gdx.files.local("fileObjectsOnStage6LevelSAVE.json");
        fileStaticObjects6LevelSAVE = Gdx.files.local("fileStaticObjects6LevelSAVE.json");

        prefs1 = Gdx.app.getPreferences("prefs1");
        prefs2 = Gdx.app.getPreferences("prefs2");
        prefs3 = Gdx.app.getPreferences("prefs3");
        prefs4 = Gdx.app.getPreferences("prefs4");
        prefsLevelNumber = Gdx.app.getPreferences("LevelNumber");
        prefsMountedObjects = Gdx.app.getPreferences("prefsMountedObjects");
        prefsEPDoor =  Gdx.app.getPreferences("prefsEPDoor");
        prefs4COMPLETEDLevel =  Gdx.app.getPreferences("prefs4COMPLETEDLevel");
        prefs5Level =  Gdx.app.getPreferences("prefs5Level");
        prefs6Level =  Gdx.app.getPreferences("prefs6Level");

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
    public Preferences getPrefs5Level() {
        return prefs5Level;
    }
    public Preferences getPrefs6Level() {
        return prefs6Level;
    }
    public Preferences getPrefs4COMPLETEDLevel() {
        return prefs4COMPLETEDLevel;
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

    public void newLevelStarting(){
        setFlagLoad1(false);
        setFlagLoad2(false);
        setFlagLoad3(false);
        setFlagLoad4(false);
        setFlag1LOADED(false);
        setFlag2LOADED(false);
        setFlag3LOADED(false);
        setFlag4LOADED(false);
    }
}
