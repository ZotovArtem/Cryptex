package art.dual.firstLevel;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import art.dual.mainMenu.MainMenuScreen;

/**
 * Typical stage class representing all actors player can see and interact.
 * I use AssetManager class to manage all of my assets in this game.
 */
public class TableStage extends Image {

    private Stage stageTable;
    private StaticObjects1 main_stage, branch1, branch2, view_in_window, lamp_overlap, back_to_main_stage_from_table;

    public TableStage(){
        StretchViewport stretchViewport = new StretchViewport(800f, 480f);
        stageTable = new Stage(stretchViewport);

        MainMenuScreen.manager.load("FirstLevel/Table/branch1.png", Texture.class);
        MainMenuScreen.manager.load("FirstLevel/Table/branch2.png", Texture.class);
        MainMenuScreen.manager.load("FirstLevel/Table/main_stage.png", Texture.class);
        MainMenuScreen.manager.load("FirstLevel/Table/view_in_window.jpg", Texture.class);
        MainMenuScreen.manager.load("FirstLevel/Table/lamp_overlap.png", Texture.class);
        MainMenuScreen.manager.finishLoading();

        branch1 = new StaticObjects1("stageTable", MainMenuScreen.manager.get("FirstLevel/Table/branch1.png", Texture.class), 244f, 371f, true, "branch1");
        branch2 = new StaticObjects1("stageTable", MainMenuScreen.manager.get("FirstLevel/Table/branch2.png", Texture.class), 551f, 273f, true, "branch2");
        main_stage = new StaticObjects1("stageTable", MainMenuScreen.manager.get("FirstLevel/Table/main_stage.png", Texture.class), 0f, 0f, true, "main_stage");
        view_in_window = new StaticObjects1("stageTable", MainMenuScreen.manager.get("FirstLevel/Table/view_in_window.jpg", Texture.class), 108f, 334, true, "view_in_window");
        lamp_overlap = new StaticObjects1("stageTable", MainMenuScreen.manager.get("FirstLevel/Table/lamp_overlap.png", Texture.class), 196, 196, true, "lamp_overlap");
        back_to_main_stage_from_table = new StaticObjects1("stageTable", MainMenuScreen.manager.get("FirstLevel/Workshop/back_to_main_stage.png", Texture.class), 690f, 400f, true, "back_to_main_stage_from_table");

        lamp_overlap.setObjectColor(Color.CLEAR);

        stageTable.addActor(view_in_window);
        stageTable.addActor(branch1);
        stageTable.addActor(branch2);
        stageTable.addActor(main_stage);
        stageTable.addActor(MainScreen1.staticObjectsOnStage.getLamp_active_and_unlocked());
        stageTable.addActor(MainScreen1.staticObjectsOnStage.getValve_lighted());
        stageTable.addActor(MainScreen1.staticObjectsOnStage.getValve_unlighted());
        stageTable.addActor(lamp_overlap);
        stageTable.addActor(back_to_main_stage_from_table);
        stageTable.addActor(MainScreen1.activeObjectsOnStage.getKnife_unlighted());
        stageTable.addActor(MainScreen1.activeObjectsOnStage.getKnife_lighted());
        stageTable.addActor(MainScreen1.activeObjectsOnStage.getKey_from_table_box());
        stageTable.addActor(MainScreen1.activeObjectsOnStage.getBinoculars_unlighted());
        stageTable.addActor(MainScreen1.activeObjectsOnStage.getBinoculars_lighted());
    }

    public Stage getStage() {
        return stageTable;
    }
}

