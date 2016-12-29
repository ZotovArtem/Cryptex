package art.dual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.ColorAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.Set;

import art.dual.Parchment.ParchmentScreenRender;
import art.dual.eightLevel.MainScreen8Render;
import art.dual.fifthLevel.MainScreen5Render;
import art.dual.firstLevel.MainScreen1Render;
import art.dual.fourthLevel.MainScreen4Render;
import art.dual.fourthLevelCOMPLETED.MainScreen4COMPLETEDRender;
import art.dual.mainMenu.MainMenuScreenRender;
import art.dual.ninthLevel.MainScreen9Render;
import art.dual.options.OptionsScreenRender;
import art.dual.saveAndLoad.LoadingScreenRender;
import art.dual.secondLevel.MainScreen2Render;
import art.dual.seventhLevel.MainScreen7;
import art.dual.seventhLevel.MainScreen7Render;
import art.dual.sixthLevel.MainScreen6Render;
import art.dual.sixthLevelCOMPLETED.MainScreen6COMLETEDRender;
import art.dual.thirdLevel.MainScreen3Render;

/**
 * Here i keep all global variables and methods.
 */

public class GlobalReferences {

    private boolean flagHint, flagGoToMainMenu, flagGoTo2Lvl, flagGoTo3Lvl, flagGoTo4Lvl, flagGoTo4CompLvl, flagGoTo5Lvl, flagGoTo6Lvl, flagGoTo7Lvl,
            flagGoTo6CompLvl, flagGoTo8Lvl, flagGoTo9Lvl,
            flagInventoryAppearance, flagPuzzle1HintActivated, flagPuzzle2HintActivated, flagPuzzle3HintActivated, flagBIGisVisible, flagPanelStageStart = true,
            flagLiteralCryptexStageStart, flagDigitalCryptexStageStart, flagJournalStageStart, flagMapStageStart, flagParchmentStageStart, flagOptionsStageStart,
            flagLevelStarted, flagCubeCalled, flagFirstCubeCellOccupied, flagSecondCubeCellOccupied, flagThirdCubeCellOccupied, flagFourthCubeCellOccupied,
            flagLevelTransition, flagActiveCellOccupied, flagPanelAppearance, flag4FreeTransition, flag5FreeTransition, flag6FreeTransition, flagJournalOpenedOnce,
            flagCubeStageStart, flagEggIn1Cell, flagEggIn2Cell, flagEggIn3Cell, flagEggIn4Cell, flagGameCompleted, flagInDE, flag456Transition, flag4Visited,
            flag5Visited, flag6Visited, soundFlag, musicFlag, journalFlag, flagInventoryMoving, flagPanelMoving, flagTimerIsVisible, flagHintInProgress,
            flagTimerDisabled, flagMenuActivated, flagRender = true;
    private int levelNumber, itemsWeCanCollect1Level, itemsWeCanCollect2Level, itemsWeCanCollect3Level, itemsWeCanCollect4Level, itemsWeCanCollect5Level,
            itemsWeCanCollect6Level, itemsWeCanCollect7Level, itemsWeCanCollect6CompLevel, itemsWeCanCollect8Level, itemsWeCanCollect9Level, journalCurrentLevelNumber = 0,
            maxLevelAchieved = 0, cakeInIIndex = -1, xHigher = 421, xLower = 165, yHigher = 358, yLower = 283;
    private Image hint_message;
    public GlobalReferences() {
        hint_message = new Image();
    }

    public void stageLighteningWithPanel() {
        Cryptex.main.getPanelStage().getClearScreenOverPanelActors().setColor(Color.BLACK);

        final ColorAction ca = new ColorAction();
        ca.setEndColor(Color.CLEAR);
        ca.setDuration(1);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(100);

                        Cryptex.main.getPanelStage().getClearScreenOverPanelActors().addAction(ca);

                        wait(1000);

                        if (Cryptex.main.getSaving().getFlag1LOADED() | Cryptex.main.getSaving().getFlag2LOADED() | Cryptex.main.getSaving().getFlag3LOADED() | Cryptex.main.getSaving().getFlag4LOADED() |
                                Cryptex.main.getSaving().getFlagPause()) {
                            if (Cryptex.main.getGlobalReferences().getLevelNumber() == 1) {
                                for (int i = 0; i < Cryptex.main.getMainScreen1().getAllActorsFromStage().size(); i++) {
                                    if (Cryptex.main.getMainScreen1().getFlagsStageDrawing().get(i)) {
                                        for (int j = 0; j < Cryptex.main.getMainScreen1().getAllActorsFromStage().get(i).size; j++) {
                                            Cryptex.main.getMainScreen1().getAllActorsFromStage().get(i).get(j).setTouchable(Touchable.enabled);
                                        }
                                    }
                                }
                            } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 2) {
                                for (int i = 0; i < Cryptex.main.getMainScreen2().getAllActorsFromStage().size(); i++) {
                                    if (Cryptex.main.getMainScreen2().getFlagsStageDrawing().get(i)) {
                                        for (int j = 0; j < Cryptex.main.getMainScreen2().getAllActorsFromStage().get(i).size; j++) {
                                            Cryptex.main.getMainScreen2().getAllActorsFromStage().get(i).get(j).setTouchable(Touchable.enabled);
                                        }
                                    }
                                }
                            } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 3) {
                                for (int i = 0; i < Cryptex.main.getMainScreen3().getAllActorsFromStage().size(); i++) {
                                    if (Cryptex.main.getMainScreen3().getFlagsStageDrawing().get(i)) {
                                        for (int j = 0; j < Cryptex.main.getMainScreen3().getAllActorsFromStage().get(i).size; j++) {
                                            Cryptex.main.getMainScreen3().getAllActorsFromStage().get(i).get(j).setTouchable(Touchable.enabled);
                                        }
                                    }
                                }
                            } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 4) {
                                for (int i = 0; i < Cryptex.main.getMainScreen4().getAllActorsFromStage().size(); i++) {
                                    if (Cryptex.main.getMainScreen4().getFlagsStageDrawing().get(i)) {
                                        for (int j = 0; j < Cryptex.main.getMainScreen4().getAllActorsFromStage().get(i).size; j++) {
                                            Cryptex.main.getMainScreen4().getAllActorsFromStage().get(i).get(j).setTouchable(Touchable.enabled);
                                        }
                                    }
                                }
                            } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == -4) {
                                for (int i = 0; i < Cryptex.main.getMainScreen4COMPLETED().getAllActorsFromStage().size(); i++) {
                                    if (Cryptex.main.getMainScreen4COMPLETED().getFlagsStageDrawing().get(i)) {
                                        for (int j = 0; j < Cryptex.main.getMainScreen4COMPLETED().getAllActorsFromStage().get(i).size; j++) {
                                            Cryptex.main.getMainScreen4COMPLETED().getAllActorsFromStage().get(i).get(j).setTouchable(Touchable.enabled);
                                        }
                                    }
                                }
                            } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 5) {
                                for (int i = 0; i < Cryptex.main.getMainScreen5().getAllActorsFromStage().size(); i++) {
                                    if (Cryptex.main.getMainScreen5().getFlagsStageDrawing().get(i)) {
                                        for (int j = 0; j < Cryptex.main.getMainScreen5().getAllActorsFromStage().get(i).size; j++) {
                                            Cryptex.main.getMainScreen5().getAllActorsFromStage().get(i).get(j).setTouchable(Touchable.enabled);
                                        }
                                    }
                                }
                            } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 6) {
                                for (int i = 0; i < Cryptex.main.getMainScreen6().getAllActorsFromStage().size(); i++) {
                                    if (Cryptex.main.getMainScreen6().getFlagsStageDrawing().get(i)) {
                                        for (int j = 0; j < Cryptex.main.getMainScreen6().getAllActorsFromStage().get(i).size; j++) {
                                            Cryptex.main.getMainScreen6().getAllActorsFromStage().get(i).get(j).setTouchable(Touchable.enabled);
                                        }
                                    }
                                }
                            } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 7) {
                                for (int i = 0; i < Cryptex.main.getMainScreen7().getAllActors().length; i++) {
                                    if (Cryptex.main.getMainScreen7().getAllFlags().get(i)) {
                                        for (int j = 0; j < Cryptex.main.getMainScreen7().getAllActors()[i].length; j++) {
                                            Cryptex.main.getMainScreen7().getAllActors()[i][j].setTouchable(Touchable.enabled);
                                        }
                                    }
                                }
                            } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == -6) {
                                for (int i = 0; i < Cryptex.main.getMainScreen6COMPLETED().getAllActors().length; i++) {
                                    if (Cryptex.main.getMainScreen6COMPLETED().getAllFlags().get(i)) {
                                        for (int j = 0; j < Cryptex.main.getMainScreen6COMPLETED().getAllActors()[i].length; j++) {
                                            Cryptex.main.getMainScreen6COMPLETED().getAllActors()[i][j].setTouchable(Touchable.enabled);
                                        }
                                    }
                                }
                            } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 8) {
                                for (int i = 0; i < Cryptex.main.getMainScreen8().getAllActors().length; i++) {
                                    if (Cryptex.main.getMainScreen8().getAllFlags().get(i)) {
                                        for (int j = 0; j < Cryptex.main.getMainScreen8().getAllActors()[i].length; j++) {
                                            Cryptex.main.getMainScreen8().getAllActors()[i][j].setTouchable(Touchable.enabled);
                                        }
                                    }
                                }
                            } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 9) {
                                for (int i = 0; i < Cryptex.main.getMainScreen9().getAllActors().length; i++) {
                                    if (Cryptex.main.getMainScreen9().getAllFlags().get(i)) {
                                        for (int j = 0; j < Cryptex.main.getMainScreen9().getAllActors()[i].length; j++) {
                                            Cryptex.main.getMainScreen9().getAllActors()[i][j].setTouchable(Touchable.enabled);
                                        }
                                    }
                                }
                            }
                        } else {
                            if (Cryptex.main.getGlobalReferences().getLevelNumber() == 1) {
                                for (int i = 0; i < Cryptex.main.getMainScreen1().getAllActorsWorkshopStage().size; i++) {
                                    Cryptex.main.getMainScreen1().getAllActorsWorkshopStage().get(i).setTouchable(Touchable.enabled);
                                }
                            } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 2) {
                                for (int i = 0; i < Cryptex.main.getMainScreen2().getAllActorsYardStage().size; i++) {
                                    Cryptex.main.getMainScreen2().getAllActorsYardStage().get(i).setTouchable(Touchable.enabled);
                                }
                            } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 3) {
                                for (int i = 0; i < Cryptex.main.getMainScreen3().getAllActorsShedStage().size; i++) {
                                    Cryptex.main.getMainScreen3().getAllActorsShedStage().get(i).setTouchable(Touchable.enabled);
                                }
                            } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 4) {
                                for (int i = 0; i < Cryptex.main.getMainScreen4().getAllActorsFromBoatStage().size; i++) {
                                    Cryptex.main.getMainScreen4().getAllActorsFromBoatStage().get(i).setTouchable(Touchable.enabled);
                                }
                            } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == -4) {
                                for (int i = 0; i < Cryptex.main.getMainScreen4COMPLETED().getAllActorsOnMotoStage().size; i++) {
                                    Cryptex.main.getMainScreen4COMPLETED().getAllActorsOnMotoStage().get(i).setTouchable(Touchable.enabled);
                                }
                            } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 5) {
                                for (int i = 0; i < Cryptex.main.getMainScreen5().getAllActorsPlaneWreckageStage().size; i++) {
                                    Cryptex.main.getMainScreen5().getAllActorsPlaneWreckageStage().get(i).setTouchable(Touchable.enabled);
                                }
                            } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 6) {
                                for (int i = 0; i < Cryptex.main.getMainScreen6().getAllActorsFromMotoStage().size; i++) {
                                    Cryptex.main.getMainScreen6().getAllActorsFromMotoStage().get(i).setTouchable(Touchable.enabled);
                                }
                            } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 7) {
                                for (int i = 0; i < Cryptex.main.getMainScreen7().getCaenActors().length; i++) {
                                    Cryptex.main.getMainScreen7().getCaenActors()[i].setTouchable(Touchable.enabled);
                                }
                            } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == -6) {
                                for (int i = 0; i < Cryptex.main.getMainScreen6COMPLETED().getCabiActors().length; i++) {
                                    Cryptex.main.getMainScreen6COMPLETED().getCabiActors()[i].setTouchable(Touchable.enabled);
                                }
                            } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 8) {
                                for (int i = 0; i < Cryptex.main.getMainScreen8().getGravActors().length; i++) {
                                    Cryptex.main.getMainScreen8().getGravActors()[i].setTouchable(Touchable.enabled);
                                }
                            } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 9) {
                                for (int i = 0; i < Cryptex.main.getMainScreen9().getCaveActors().length; i++) {
                                    Cryptex.main.getMainScreen9().getCaveActors()[i].setTouchable(Touchable.enabled);
                                }
                            }
                        }

                        Cryptex.main.getPanelStage().getClearScreenOverPanelActors().setVisible(false);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        thread.start();
    }

    public void stageDarkeningWithPanel() {
        Cryptex.main.getPanelStage().getClearScreenOverPanelActors().setVisible(true);

        ColorAction ca = new ColorAction();
        ca.setEndColor(Color.BLACK);
        ca.setDuration(2f);

        Cryptex.main.getPanelStage().getClearScreenOverPanelActors().addAction(ca);
    }

    public void stageSimpleLighteningWithPanel() {
        Cryptex.main.getPanelStage().getClearScreenOverPanelActors().setVisible(true);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        ColorAction ca = new ColorAction();
                        ca.setEndColor(Color.CLEAR);
                        ca.setDuration(2f);

                        Cryptex.main.getPanelStage().getClearScreenOverPanelActors().addAction(ca);

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

    public void stageDarkeningWithoutPanel(Stage stage) {
        Image clearScreen = new Image(Cryptex.manager.get("GlobalObjects/clear_screen2.png", Texture.class));
        clearScreen.setColor(Color.CLEAR);
        clearScreen.setName("clearScreen");
        stage.addActor(clearScreen);

        ColorAction ca = new ColorAction();
        ca.setEndColor(Color.BLACK);
        ca.setDuration(2f);

        stage.getActors().get(stage.getActors().toArray().length - 1).addAction(ca);
    }

    public void exitPuzzleStage() {
        Cryptex.main.getGlobalReferences().setFlagHint(false);
        Cryptex.main.getGlobalReferences().threadKilling("threadHelpButton");
        Cryptex.main.getPanelStage().getButton_help_active_phase2().setVisible(false);
        Cryptex.main.getPanelStage().getButton_help_active_phase1().setVisible(false);
        Cryptex.main.getPanelStage().getButton_help_unactive().setVisible(true);

        if (Cryptex.main.getGlobalReferences().getFlagHintInProgress()) {
            Cryptex.main.getGlobalReferences().hintDisappearingMethod();
            Cryptex.main.getGlobalReferences().hintMessageDisappearingMethod();
        }
    }

    public void hintAppearingMethod() {
        if(!Cryptex.main.getGlobalReferences().getFlagHintInProgress()){
            Cryptex.main.getPanelStage().getButton_help_unactive().setVisible(false);
            Cryptex.main.getPanelStage().getButton_help_active_phase1().setVisible(true);
            Cryptex.main.getGlobalReferences().setFlagHintInProgress(true);
            playSound(Cryptex.main.getGs().getHint_falling(), 0.6f);
            Cryptex.main.getPanelStage().playHA();
        }
    }

    public void hintDisappearingMethod() {
        Cryptex.main.getPanelStage().getClearScreenOverPanelActors().setVisible(true);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        Cryptex.main.getPanelStage().getHint_overlap().setVisible(false);
                        playSound(Cryptex.main.getGs().getHint_rising(), 0.7f);
                        Cryptex.main.getPanelStage().playHD();
                        Cryptex.main.getPanelStage().getButton_help_unactive().setVisible(true);
                        Cryptex.main.getPanelStage().getButton_help_active_phase1().setVisible(false);

                        wait(1200);

                        Cryptex.main.getGlobalReferences().setFlagHintInProgress(false);
                        Cryptex.main.getPanelStage().getClearScreenOverPanelActors().setVisible(false);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        thread.start();
    }

    public void lackOfItems(){
        Cryptex.main.getPanelStage().getClearScreenOverPanelActors().setVisible(true);
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        Cryptex.main.getGlobalReferences().setFlagHintInProgress(true);
                        playSound(Cryptex.main.getGs().getHint_falling(), 0.6f);
                        Cryptex.main.getPanelStage().playHA();

                        wait(1200);

                        createHintActor("hint_message_lack_of_items");
                        Cryptex.main.getPanelStage().getHint_overlap().setVisible(true);
                        Cryptex.main.getPanelStage().getClearScreenOverPanelActors().setVisible(false);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        if(!Cryptex.main.getGlobalReferences().getFlagHintInProgress()) th.start();
    }

    public void hintMessageAppearingMethod() {
        Thread threadWaitForHintMessage = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        Cryptex.main.getPanelStage().getClearScreenOverPanelActors().setVisible(true);
                        wait(1200);
                        if (Cryptex.main.getGlobalReferences().getLevelNumber() == 1) {
                            if (Cryptex.main.getMainScreen1().getStaticObjects().getFlagLampStageStart()) {
                               createHintActor("hint_message_lamp");
                            }
                        } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 2) {
                            if (Cryptex.main.getMainScreen2().getStaticObjects().getFlagTowerPuzzleStageStart()) {
                                createHintActor("hint_message_tower_puzzle");
                            } else if (Cryptex.main.getMainScreen2().getStaticObjects().getFlagCellarStageStart()) {
                                if (Cryptex.main.getMainScreen2().getStaticObjects().getFlagKeroseneLampFilled()) {
                                    createHintActor("hint_message_kerosene_lamp_filled");
                                } else if (!Cryptex.main.getMainScreen2().getStaticObjects().getFlagKeroseneLampFilled()) {
                                    createHintActor("hint_message_kerosene_lamp_empty");
                                }
                            } else if (Cryptex.main.getMainScreen2().getStaticObjects().getFlagWellMechanismStageStart()) {
                                createHintActor("hint_message_well");
                            }
                        } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 3) {
                            if (Cryptex.main.getMainScreen3().getStaticObjects().getFlagFuelMechanismStageStart()) {
                                createHintActor("hint_message_fuel_mechanism");
                            }
                            else if (Cryptex.main.getMainScreen3().getStaticObjects().getFlagControlPanelStageStart()) {
                                createHintActor("hint_message_control_panel");
                            }
                        } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 4) {
                            if (Cryptex.main.getMainScreen4().getStaticObjects().getFlagLockStageStart()) {
                                if (Cryptex.main.getMainScreen4().getAtticStage().getRightPanObjectsWeight() + Cryptex.main.getMainScreen4().getAtticStage().getLeftPanObjectsWeight() < 46) {
                                    createHintActor("hint_message_not_enough_weight");
                                } else if (Cryptex.main.getMainScreen4().getAtticStage().getRightPanObjectsWeight() != Cryptex.main.getMainScreen4().getAtticStage().getLeftPanObjectsWeight()) {
                                    createHintActor("hint_message_weigher_not_balanced");
                                }
                            } else if (Cryptex.main.getMainScreen4().getStaticObjects().getFlagAtticStageStart()) {
                                createHintActor("hint_message_attic");
                            } else if (Cryptex.main.getMainScreen4().getStaticObjects().getFlagLockOnChestStageStart()) {
                                createHintActor("hint_message_lock_on_chest");
                            } else if (Cryptex.main.getMainScreen4().getStaticObjects().getFlagFishingStageStart()) {
                                createHintActor("hint_message_fishing");
                            }
                        } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 5) {
                            if (Cryptex.main.getMainScreen5().getStaticObjects().getFlagLuggageSpaceDoorStageStart()) {
                                createHintActor("hint_message_LSD");
                            }
                        } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 6) {
                            if (Cryptex.main.getMainScreen6().getStaticObjects().getFlagMazeStageStart()) {
                                createHintActor("hint_message_maze");
                            }
                            else if (Cryptex.main.getMainScreen6().getStaticObjects().getFlagTableBottomStageStart()) {
                                createHintActor("hint_message_under_table");
                            }
                        } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 8) {
                            if (Cryptex.main.getMainScreen8().getFlags().getFlagCrep()) {
                                createHintActor("hint_message_crep");
                            }
                            else if (Cryptex.main.getMainScreen8().getFlags().getFlagAleq()) {
                                createHintActor("hint_message_aleq");
                            }
                            else if (Cryptex.main.getMainScreen8().getFlags().getFlagTomb()) {
                                createHintActor("hint_message_tomb");
                            }
                        } else if (Cryptex.main.getGlobalReferences().getLevelNumber() == 9) {
                            if (Cryptex.main.getMainScreen9().getFlags().getFlagDoor()) {
                                createHintActor("hint_message_door");
                            }
                            else if (Cryptex.main.getMainScreen9().getFlags().getFlagLeve()) {
                                createHintActor("hint_message_leve");
                            }
                            else if (Cryptex.main.getMainScreen9().getFlags().getFlagWale()) {
                                createHintActor("hint_message_wall");
                            }
                        }
                        Cryptex.main.getPanelStage().getHint_overlap().setVisible(true);
                        Cryptex.main.getPanelStage().getClearScreenOverPanelActors().setVisible(false);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        if (!Cryptex.main.getGlobalReferences().getFlagHintInProgress()) threadWaitForHintMessage.start();
    }

    void createHintActor(String name){
        hint_message = new Image(Cryptex.manager.get("Text/" + name + ".png", Texture.class));
        hint_message.setName(name);
        hint_message.setPosition(138, 369);
        Cryptex.main.getPanelStage().getStage().addActor(Cryptex.main.getGlobalReferences().getHint_message());
        Cryptex.main.getPanelStage().getStage().getActors().get(Cryptex.main.getPanelStage().getStage().getActors().toArray().length - 1).setZIndex(Cryptex.main.getPanelStage().getGroupHD().getZIndex() + 1);
    }

    public void hintMessageDisappearingMethod() {
        MoveByAction mbaHintMessage = new MoveByAction();
        mbaHintMessage.setAmount(0, 170);
        mbaHintMessage.setDuration(1.2f);

        Cryptex.main.getGlobalReferences().getHint_message().addAction(mbaHintMessage);
    }

    public void threadHelpButtonMethod() {
        Thread threadHelpButton = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        while (true) {
                            wait(1000);
                            playSound(Cryptex.main.getGs().getHint_button(), 1);
                            Cryptex.main.getPanelStage().getButton_help_unactive().setVisible(false);
                            Cryptex.main.getPanelStage().getButton_help_active_phase2().setVisible(true);

                            wait(730);
                            Cryptex.main.getPanelStage().getButton_help_active_phase2().setVisible(false);
                            Cryptex.main.getPanelStage().getButton_help_unactive().setVisible(true);
                        }
                    }
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "threadHelpButton");
        threadHelpButton.start();
    }

    public void globalFlagsToDefault() {
        Cryptex.main.getGlobalReferences().setFlagPuzzle1HintActivated(false);
        Cryptex.main.getGlobalReferences().setFlagPuzzle2HintActivated(false);
        Cryptex.main.getGlobalReferences().setFlagPuzzle3HintActivated(false);
        Cryptex.main.getGlobalReferences().setFlagGoTo2Lvl(false);
        Cryptex.main.getGlobalReferences().setFlagGoTo3Lvl(false);
        Cryptex.main.getGlobalReferences().setFlagGoTo4Lvl(false);
        Cryptex.main.getGlobalReferences().setFlagGoTo4CompLvl(false);
        Cryptex.main.getGlobalReferences().setFlagGoTo5Lvl(false);
        Cryptex.main.getGlobalReferences().setFlagGoTo6Lvl(false);
        Cryptex.main.getGlobalReferences().setFlagGoTo6CompLvl(false);
        Cryptex.main.getGlobalReferences().setFlagGoTo7Lvl(false);
        Cryptex.main.getGlobalReferences().setFlagGoTo8Lvl(false);
        Cryptex.main.getGlobalReferences().setFlagGoTo9Lvl(false);
        Cryptex.main.getGlobalReferences().setFlagJournalOpenedOnce(false);
    }

    public void threadKilling(String name) {
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();

        Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);

        for (int j = 0; j < threadArray.length; j++) {
            if (threadArray[j].getName().equals(name)) {
                threadArray[j].interrupt();
            }
        }
    }

    public void killAllCryptexThreads(){
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();

        Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);

        for (int j = 0; j < threadArray.length; j++) {
            if (threadArray[j].getName().endsWith("ThreadCryptex")) {
                threadArray[j].interrupt();
            }
        }
    }

    public void removeObjectFromInventory(String name){
        if(Cryptex.main.getPanelStage().getGroupActiveItem().getChildren().size > 0){
            Cryptex.main.getActiveListener().addObjectToInventory(Cryptex.main.getPanelStage().getGroupActiveItem().getChildren().get(0));
            Cryptex.main.getGlobalReferences().setFlagActiveCellOccupied(false);
        }

        for(int i = 0; i < Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().size; i++){
            String iniName = Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().get(i).getName();
            if(iniName.equals(name)){
                Cryptex.main.getActiveListener().addThisToActiveCell(Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().get(i));
                Cryptex.main.getPanelStage().getGroupActiveItem().getChildren().get(0).remove();
                Cryptex.main.getGlobalReferences().setFlagActiveCellOccupied(false);
                break;
            }
        }
    }

    public void mainScreenDispose(int levelNumber){
        if(levelNumber == 1){
            for(int i = 0; i < Cryptex.main.getMainScreen1().getArrStages().length; i++) Cryptex.main.getMainScreen1().getArrStages()[i].dispose();
        } else if(levelNumber == 2){
            for(int i = 0; i < Cryptex.main.getMainScreen2().getArrStages().length; i++) Cryptex.main.getMainScreen2().getArrStages()[i].dispose();
        } else if(levelNumber == 3){
            for(int i = 0; i < Cryptex.main.getMainScreen3().getArrStages().length; i++) Cryptex.main.getMainScreen3().getArrStages()[i].dispose();
        } else if(levelNumber == 4){
            for(int i = 0; i < Cryptex.main.getMainScreen4().getArrStages().length; i++) Cryptex.main.getMainScreen4().getArrStages()[i].dispose();
        } else if(levelNumber == -4){
            for(int i = 0; i < Cryptex.main.getMainScreen4COMPLETED().getArrStages().length; i++) Cryptex.main.getMainScreen4COMPLETED().getArrStages()[i].dispose();
        } else if(levelNumber == 5){
            for(int i = 0; i < Cryptex.main.getMainScreen5().getArrStages().length; i++) Cryptex.main.getMainScreen5().getArrStages()[i].dispose();
        } else if(levelNumber == 6){
            for(int i = 0; i < Cryptex.main.getMainScreen6().getArrStages().length; i++) Cryptex.main.getMainScreen6().getArrStages()[i].dispose();
        } else if(levelNumber == -6){
            for(int i = 0; i < Cryptex.main.getMainScreen6COMPLETED().getArrStages().length; i++) Cryptex.main.getMainScreen6COMPLETED().getArrStages()[i].dispose();
        } else if(levelNumber == 7){
            for(int i = 0; i < Cryptex.main.getMainScreen7().getArrStages().length; i++) Cryptex.main.getMainScreen7().getArrStages()[i].dispose();
        } else if(levelNumber == 8){
            for(int i = 0; i < Cryptex.main.getMainScreen8().getArrStages().length; i++) Cryptex.main.getMainScreen8().getArrStages()[i].dispose();
        } else if(levelNumber == 9){
            for(int i = 0; i < Cryptex.main.getMainScreen9().getArrStages().length; i++) Cryptex.main.getMainScreen9().getArrStages()[i].dispose();
        }

        for(int i = 0; i < Cryptex.main.getJo().getArrAL().length; i++) Cryptex.main.getJo().getArrAL()[i].clear();

        Cryptex.main.disposeAnimTextures();
        Cryptex.main.getGlobalReferences().setFlagLevelStarted(false);
        Cryptex.main.getGlobalReferences().setFlagInventoryAppearance(false);
        Cryptex.main.getInputMultiplexer().clear();
        Cryptex.manager.clear();

        if(Cryptex.main.getGlobalReferences().getFlagGoToMainMenu()) Cryptex.main.getGlobalReferences().setFlagsToDefault();
        System.out.println("disposed");
    }

    public void newLevelStarted(){
        Cryptex.main.getActiveObjectsInI().loadInIObjects();
        loadJOFromTranstion();
        Cryptex.main.getGlobalReferences().setFlagLevelTransition(false);
        Cryptex.main.getGlobalReferences().setFlagPanelAppearance(false);
        if(Cryptex.main.getGlobalReferences().getLevelNumber() > 0 & !(Cryptex.main.getGlobalReferences().getLevelNumber() == 5 & Cryptex.main.getGlobalReferences().getFlag6Visited()))
            Cryptex.main.getGlobalReferences().setMaxLevelAchieved(Cryptex.main.getGlobalReferences().getLevelNumber() - 1);
    }

    public void setFlagsToDefault(){
        Cryptex.main.getGlobalReferences().setFlagHint(false);
        Cryptex.main.getGlobalReferences().setFlagGoToMainMenu(false);
        Cryptex.main.getGlobalReferences().setFlagGoTo2Lvl(false);
        Cryptex.main.getGlobalReferences().setFlagGoTo3Lvl(false);
        Cryptex.main.getGlobalReferences().setFlagGoTo4Lvl(false);
        Cryptex.main.getGlobalReferences().setFlagGoTo4CompLvl(false);
        Cryptex.main.getGlobalReferences().setFlagGoTo5Lvl(false);
        Cryptex.main.getGlobalReferences().setFlagGoTo6Lvl(false);
        Cryptex.main.getGlobalReferences().setFlagGoTo7Lvl(false);
        Cryptex.main.getGlobalReferences().setFlagGoTo6CompLvl(false);
        Cryptex.main.getGlobalReferences().setFlagGoTo8Lvl(false);
        Cryptex.main.getGlobalReferences().setFlagGoTo9Lvl(false);
        Cryptex.main.getGlobalReferences().setFlagInventoryAppearance(false);
        Cryptex.main.getGlobalReferences().setFlagPuzzle1HintActivated(false);
        Cryptex.main.getGlobalReferences().setFlagPuzzle2HintActivated(false);
        Cryptex.main.getGlobalReferences().setFlagPuzzle3HintActivated(false);
        Cryptex.main.getGlobalReferences().setFlagBIGisVisible(false);
        Cryptex.main.getGlobalReferences().setFlagPanelStageStart(false);
        Cryptex.main.getGlobalReferences().setFlagLiteralCryptexStageStart(false);
        Cryptex.main.getGlobalReferences().setFlagDigitalCryptexStageStart(false);
        Cryptex.main.getGlobalReferences().setFlagJournalStageStart(false);
        Cryptex.main.getGlobalReferences().setFlagMapStageStart(false);
        Cryptex.main.getGlobalReferences().setFlagParchmentStageStart(false);
        Cryptex.main.getGlobalReferences().setFlagLevelStarted(false);
        Cryptex.main.getGlobalReferences().setFlagCubeCalled(false);
        Cryptex.main.getGlobalReferences().setFlagFirstCubeCellOccupied(false);
        Cryptex.main.getGlobalReferences().setFlagSecondCubeCellOccupied(false);
        Cryptex.main.getGlobalReferences().setFlagThirdCubeCellOccupied(false);
        Cryptex.main.getGlobalReferences().setFlagFourthCubeCellOccupied(false);
        Cryptex.main.getGlobalReferences().setFlagLevelTransition(false);
        Cryptex.main.getGlobalReferences().setFlagActiveCellOccupied(false);
        Cryptex.main.getGlobalReferences().setFlagPanelAppearance(false);
        Cryptex.main.getGlobalReferences().setFlag4FreeTransition(false);
        Cryptex.main.getGlobalReferences().setFlag5FreeTransition(false);
        Cryptex.main.getGlobalReferences().setFlag6FreeTransition(false);
        Cryptex.main.getGlobalReferences().setFlagJournalOpenedOnce(false);
        Cryptex.main.getGlobalReferences().setFlagCubeStageStart(false);
        Cryptex.main.getGlobalReferences().setFlagEggIn1Cell(false);
        Cryptex.main.getGlobalReferences().setFlagEggIn2Cell(false);
        Cryptex.main.getGlobalReferences().setFlagEggIn3Cell(false);
        Cryptex.main.getGlobalReferences().setFlagEggIn4Cell(false);
        Cryptex.main.getGlobalReferences().setFlagGameCompleted(false);
        Cryptex.main.getGlobalReferences().setFlagInDE(false);
        Cryptex.main.getGlobalReferences().setFlag4Visited(false);
        Cryptex.main.getGlobalReferences().setFlag5Visited(false);
        Cryptex.main.getGlobalReferences().setFlag6Visited(false);
        Cryptex.main.getGlobalReferences().setFlagTimerIsVisible(false);
        Cryptex.main.getGlobalReferences().setFlagHintInProgress(false);
        Cryptex.main.getGlobalReferences().setFlagTimerDisabled(false);
        Cryptex.main.getGlobalReferences().setFlagMenuActivated(false);
        Cryptex.main.getGlobalReferences().setFlagRender(true);
    }

    public void loadJOFromTranstion(){
        for(int j = 0; j < Cryptex.main.getJo().getArrAL().length; j++) {
            String index = Integer.toString(j) + "length";
            for (int i = 0; i < Cryptex.main.getSaving().getPrefsJOTransition().getInteger(index); i++) {
                String name = Integer.toString(j) + "_" + Integer.toString(i);
                Cryptex.main.getJo().getArrAL()[j].add(Cryptex.main.getSaving().getPrefsJOTransition().getInteger(name));

                if(Cryptex.main.getSaving().getPrefsJOTransition().getInteger(name) != -1) {
                    Cryptex.main.getJournalStage().getStage().addActor(Cryptex.main.getJo().getArrJO()[j][Cryptex.main.getSaving().getPrefsJOTransition().getInteger(name)]);
                }

            }
        }
    }

    public void puzzleUnraveled(int number){
        exitPuzzleStage();
        if(number == 1) Cryptex.main.getGlobalReferences().setFlagPuzzle1HintActivated(true);
        else if(number == 2) Cryptex.main.getGlobalReferences().setFlagPuzzle2HintActivated(true);
        else if(number == 3) Cryptex.main.getGlobalReferences().setFlagPuzzle3HintActivated(true);
    }

    public void startHint(int number, boolean flag){
        if(flag) Cryptex.main.getGlobalReferences().setFlagHint(true);
        boolean flag2 = false;
        if(number == 1) flag2 = !Cryptex.main.getGlobalReferences().getFlagPuzzle1HintActivated();
        else if(number == 2) flag2 = !Cryptex.main.getGlobalReferences().getFlagPuzzle2HintActivated();
        else if(number == 3) flag2 = !Cryptex.main.getGlobalReferences().getFlagPuzzle3HintActivated();
        if(flag2 & flag) Cryptex.main.getGlobalReferences().threadHelpButtonMethod();
    }

    public long playSound(Sound sound, float volume){
        long id = -1;
        if(Cryptex.main.getGlobalReferences().getSoundFlag()) {
            id = sound.play(volume);
        }
        return  id;
    }

    public void playMusic(Music music, float volume, boolean looping){
        if(Cryptex.main.getGlobalReferences().getSoundFlag()) {
            music.setVolume(volume);
            music.setLooping(looping);
            music.play();
        }
    }

    public void pauseMusic(Music music){
        if(Cryptex.main.getGlobalReferences().getSoundFlag()) {
            music.pause();
        }
    }

    public void musicFadeOut(final Music music, final int miliSec, final float initialVolume){
        if(Cryptex.main.getGlobalReferences().getSoundFlag()) {
            Thread th = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        synchronized (this) {
                            for (int i = 100; i > 0; i--) {
                                float volume = ((float) i) * initialVolume / 100f;
                                music.setVolume(volume);
                                wait(miliSec);
                            }

                            Cryptex.main.getGlobalReferences().pauseMusic(music);
                            music.setVolume(initialVolume);
                        }
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            th.start();
        }
    }


    public void playSoundtrack(Music music, float volume, boolean looping){
        if(Cryptex.main.getGlobalReferences().getMusicFlag()) {
            music.setVolume(volume);
            music.setLooping(looping);
            music.play();
        }
    }

    public void pauseSoundtrack(Music music){
        if(Cryptex.main.getGlobalReferences().getMusicFlag()) {
            music.pause();
        }
    }

    public void soundtrackFadeOut(final Music music, final int miliSec, final float initialVolume){
        if(Cryptex.main.getGlobalReferences().getMusicFlag()) {
            Thread th = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        synchronized (this) {
                            for (int i = 100; i > 0; i--) {
                                float volume = ((float) i) * initialVolume / 100f;
                                music.setVolume(volume);
                                wait(miliSec);
                            }

                            Cryptex.main.getGlobalReferences().pauseSoundtrack(music);
                            music.setVolume(initialVolume);

                        }
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            th.start();
        }
    }

    public void timer(int time, String par){
        if(!Cryptex.main.getGlobalReferences().getFlagTimerDisabled()) {
            if(par.equals("oven") && time % 60 == 0 && MainScreen7.soosJSON.getOven_spanner_for_oven().getRotation() < -42.5f && MainScreen7.soosJSON.getOven_spanner_for_oven().getRotation() > -44.5f){
                Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getGs().getTick(), 0.4f);
            }
            else if (!par.equals("oven") & time % 60 == 0)
                Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getGs().getTick(), 0.4f);

            int firstNumeral, secondNumeral, thirdNumeral;
            firstNumeral = time / 3600;
            secondNumeral = ((time + 2400 * (time / 3600)) / 600) % 10;
            thirdNumeral = (time / 60) % 10;

            hideAllDigits();

            if (firstNumeral == 0) Cryptex.main.getPanelStage().getMinute_0().setVisible(true);
            else if (firstNumeral == 1) Cryptex.main.getPanelStage().getMinute_1().setVisible(true);
            else if (firstNumeral == 2) Cryptex.main.getPanelStage().getMinute_2().setVisible(true);

            if (secondNumeral == 0) Cryptex.main.getPanelStage().getDecasecond_0().setVisible(true);
            else if (secondNumeral == 1)
                Cryptex.main.getPanelStage().getDecasecond_1().setVisible(true);
            else if (secondNumeral == 2)
                Cryptex.main.getPanelStage().getDecasecond_2().setVisible(true);
            else if (secondNumeral == 3)
                Cryptex.main.getPanelStage().getDecasecond_3().setVisible(true);
            else if (secondNumeral == 4)
                Cryptex.main.getPanelStage().getDecasecond_4().setVisible(true);
            else if (secondNumeral == 5)
                Cryptex.main.getPanelStage().getDecasecond_5().setVisible(true);

            if (thirdNumeral == 0) Cryptex.main.getPanelStage().getSecond_0().setVisible(true);
            else if (thirdNumeral == 1) Cryptex.main.getPanelStage().getSecond_1().setVisible(true);
            else if (thirdNumeral == 2) Cryptex.main.getPanelStage().getSecond_2().setVisible(true);
            else if (thirdNumeral == 3) Cryptex.main.getPanelStage().getSecond_3().setVisible(true);
            else if (thirdNumeral == 4) Cryptex.main.getPanelStage().getSecond_4().setVisible(true);
            else if (thirdNumeral == 5) Cryptex.main.getPanelStage().getSecond_5().setVisible(true);
            else if (thirdNumeral == 6) Cryptex.main.getPanelStage().getSecond_6().setVisible(true);
            else if (thirdNumeral == 7) Cryptex.main.getPanelStage().getSecond_7().setVisible(true);
            else if (thirdNumeral == 8) Cryptex.main.getPanelStage().getSecond_8().setVisible(true);
            else if (thirdNumeral == 9) Cryptex.main.getPanelStage().getSecond_9().setVisible(true);
        }
    }

    void hideAllDigits(){
        Group gr = Cryptex.main.getPanelStage().getGroupDigits();
        for(int i = 0; i < gr.getChildren().size; i++){
            gr.getChildren().get(i).setVisible(false);
        }
    }

    public void timerIn(){
        System.out.println("....................................................timerIn!!!");
        Cryptex.main.getGlobalReferences().setFlagTimerDisabled(false);
        Cryptex.main.getGlobalReferences().setFlagTimerIsVisible(true);
        Cryptex.main.getPanelStage().getGroupTimer().setVisible(true);

        MoveByAction mba = new MoveByAction();
        mba.setAmountY(-100);
        mba.setDuration(0.4f);

        Cryptex.main.getPanelStage().getGroupTimer().addAction(mba);

        System.out.println("getY " + Cryptex.main.getPanelStage().getGroupTimer().getY());
        System.out.println("isVisible " + Cryptex.main.getPanelStage().getGroupTimer().isVisible());
    }

    public void timerOut(){
        Cryptex.main.getPanelStage().getClearScreenOverPanelActors().setVisible(true);
        System.out.println("...................................................timerOut!!!");
        Cryptex.main.getGlobalReferences().setFlagTimerIsVisible(false);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        MoveByAction mba = new MoveByAction();
                        mba.setAmountY(100);
                        mba.setDuration(0.4f);

                        Cryptex.main.getPanelStage().getGroupTimer().addAction(mba);

                        wait(400);

                        Cryptex.main.getPanelStage().getGroupTimer().setVisible(false);
                        Cryptex.main.getGlobalReferences().setFlagTimerDisabled(true);
                        System.out.println("...................................................setFlagTimerDisabled(true)!!!");
                        Cryptex.main.getPanelStage().getClearScreenOverPanelActors().setVisible(false);
                    }
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        thread.start();
    }

    public void startLevelNumber(final int number, final Cryptex game){
        WaitingScreen waitingScreen = new WaitingScreen(game);
        game.setScreen(waitingScreen);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(50);
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                                public void run() {
                                if(number == 0) {
                                    Cryptex.main.createMMS();
                                    MainMenuScreenRender mmsr = Cryptex.main.createMMSR(game);
                                    game.setScreen(mmsr);
                                }
                                else if(number == 1) {
                                    Cryptex.main.createMainScreen1();
                                    MainScreen1Render ms1r = Cryptex.main.createMainScreen1Render(game);
                                    game.setScreen(ms1r);
                                }
                                else if(number == 2) {
                                    Cryptex.main.createMainScreen2();
                                    MainScreen2Render ms2r = Cryptex.main.createMainScreen2Render(game);
                                    game.setScreen(ms2r);
                                }
                                else if(number == 3) {
                                    Cryptex.main.createMainScreen3();
                                    MainScreen3Render ms3r = Cryptex.main.createMainScreen3Render(game);
                                    game.setScreen(ms3r);
                                }
                                else if(number == 4) {
                                    Cryptex.main.createMainScreen4();
                                    MainScreen4Render ms4r = Cryptex.main.createMainScreen4Render(game);
                                    game.setScreen(ms4r);
                                }
                                else if(number == -4) {
                                    Cryptex.main.createMainScreen4COMP();
                                    MainScreen4COMPLETEDRender ms4COMPr = Cryptex.main.createMainScreen4COMPLETEDRender(game);
                                    game.setScreen(ms4COMPr);
                                }
                                else if(number == 5) {
                                    Cryptex.main.createMainScreen5();
                                    MainScreen5Render ms5r = Cryptex.main.createMainScreen5Render(game);
                                    game.setScreen(ms5r);
                                }
                                else if(number == 6) {
                                    Cryptex.main.createMainScreen6();
                                    MainScreen6Render ms6r = Cryptex.main.createMainScreen6Render(game);
                                    game.setScreen(ms6r);
                                }
                                else if(number == -6) {
                                    Cryptex.main.createMainScreen6COMP();
                                    MainScreen6COMLETEDRender ms6COMPr = Cryptex.main.createMainScreen6COMLETEDRender(game);
                                    game.setScreen(ms6COMPr);
                                }
                                else if(number == 7) {
                                    Cryptex.main.createMainScreen7();
                                    MainScreen7Render ms7r = Cryptex.main.createMainScreen7Render(game);
                                    game.setScreen(ms7r);
                                }
                                else if(number == 8) {
                                    Cryptex.main.createMainScreen8();
                                    MainScreen8Render ms8r = Cryptex.main.createMainScreen8Render(game);
                                    game.setScreen(ms8r);
                                }
                                else if(number == 9) {
                                    Cryptex.main.createMainScreen9();
                                    MainScreen9Render ms9r = Cryptex.main.createMainScreen9Render(game);
                                    game.setScreen(ms9r);
                                }
                                else if(number == 10) {
                                    Cryptex.main.createLoadingSreen();
                                    LoadingScreenRender lsr = Cryptex.main.createLoadingScreenRender(game);
                                    game.setScreen(lsr);
                                }
                                else if(number == 12) {
                                    Cryptex.main.createOptionsSreen();
                                    OptionsScreenRender osr = Cryptex.main.createOptionsScreenRender(game);
                                    game.setScreen(osr);
                                }
                            }
                        });
                    }
                } catch (Exception ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "startLevelThreadCryptex");
        thread.start();
    }

    public void startParchment(final String level, final Cryptex game){
        WaitingScreen waitingScreen = new WaitingScreen(game);
        game.setScreen(waitingScreen);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(50);
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                Cryptex.main.createParchmentScreen(level);
                                ParchmentScreenRender psr = Cryptex.main.createParchmentScreenRender(game);
                                game.setScreen(psr);
                            }
                        });
                    }
                } catch (Exception ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "parchmentThreadCryptex");
        thread.start();
    }

    public boolean getFlagHint() {
        return flagHint;
    }
    public boolean getFlagGoToMainMenu() {
        return flagGoToMainMenu;
    }
    public boolean getFlagGoTo2Lvl() {
        return flagGoTo2Lvl;
    }
    public boolean getFlagInventoryAppearance() {
        return flagInventoryAppearance;
    }
    public boolean getFlagPuzzle1HintActivated() {
        return flagPuzzle1HintActivated;
    }
    public boolean getFlagGoTo3Lvl() {
        return flagGoTo3Lvl;
    }
    public boolean getFlagPuzzle2HintActivated() {
        return flagPuzzle2HintActivated;
    }
    public boolean getFlagBIGisVisible() {
        return flagBIGisVisible;
    }
    public boolean getFlagPuzzle3HintActivated() {
        return flagPuzzle3HintActivated;
    }
    public boolean getFlagLiteralCryptexStageStart() {
        return flagLiteralCryptexStageStart;
    }
    public boolean getFlagGoTo4Lvl() {
        return flagGoTo4Lvl;
    }
    public boolean getFlagLevelStarted() {
        return flagLevelStarted;
    }
    public boolean getFlagCubeCalled() {
        return flagCubeCalled;
    }
    public boolean getFlagFirstCubeCellOccupied() {
        return flagFirstCubeCellOccupied;
    }
    public boolean getFlagSecondCubeCellOccupied() {
        return flagSecondCubeCellOccupied;
    }
    public boolean getFlagThirdCubeCellOccupied() {
        return flagThirdCubeCellOccupied;
    }
    public boolean getFlagFourthCubeCellOccupied() {
        return flagFourthCubeCellOccupied;
    }
    public boolean getFlagGoTo5Lvl() {
        return flagGoTo5Lvl;
    }
    public boolean getFlagGoTo4CompLvl() {
        return flagGoTo4CompLvl;
    }
    public boolean getFlagGoTo6Lvl() {
        return flagGoTo6Lvl;
    }
    public boolean getFlagLevelTransition() {
        return flagLevelTransition;
    }
    public boolean getFlagActiveCellOccupied() {
        return flagActiveCellOccupied;
    }
    public boolean getFlagPanelAppearance() {
        return flagPanelAppearance;
    }
    public boolean getFlagJournalStageStart() {
        return flagJournalStageStart;
    }
    public boolean getFlagMapStageStart() {
        return flagMapStageStart;
    }
    public boolean getFlag5FreeTransition() {
        return flag5FreeTransition;
    }
    public boolean getFlag6FreeTransition() {
        return flag6FreeTransition;
    }
    public boolean getFlag4FreeTransition() {
        return flag4FreeTransition;
    }

    public boolean getFlagJournalOpenedOnce() {
        return flagJournalOpenedOnce;
    }
    public boolean getFlagPanelStageStart() {
        return flagPanelStageStart;
    }
    public boolean getFlagGoTo7Lvl() {
        return flagGoTo7Lvl;
    }
    public boolean getFlagCubeStageStart() {
        return flagCubeStageStart;
    }
    public boolean getFlagEggIn1Cell() {
        return flagEggIn1Cell;
    }
    public boolean getFlagEggIn2Cell() {
        return flagEggIn2Cell;
    }
    public boolean getFlagEggIn3Cell() {
        return flagEggIn3Cell;
    }
    public boolean getFlagEggIn4Cell() {
        return flagEggIn4Cell;
    }
    public boolean getFlagGoTo6CompLvl() {
        return flagGoTo6CompLvl;
    }
    public boolean getFlagGoTo8Lvl() {
        return flagGoTo8Lvl;
    }
    public boolean getFlagParchmentStageStart() {
        return flagParchmentStageStart;
    }
    public boolean getFlagGoTo9Lvl() {
        return flagGoTo9Lvl;
    }
    public boolean getFlagInventoryMoving() {
        return flagInventoryMoving;
    }
    public boolean getFlagPanelMoving() {
        return flagPanelMoving;
    }
    public boolean getSoundFlag() {
        return soundFlag;
    }
    public boolean getMusicFlag() {
        return musicFlag;
    }
    public boolean getJournalFlag() {
        return journalFlag;
    }
    public int getCakeInIIndex() {
        return cakeInIIndex;
    }
    public boolean getFlagMenuActivated() {
        return flagMenuActivated;
    }

    public void setFlagHint(boolean flagHint) {
        this.flagHint = flagHint;
    }
    public void setFlagGoToMainMenu(boolean flagGoToMainMenu) {
        this.flagGoToMainMenu = flagGoToMainMenu;
    }
    public void setFlagGoTo2Lvl(boolean flagGoTo2Lvl) {
        this.flagGoTo2Lvl = flagGoTo2Lvl;
    }
    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }
    public void setFlagInventoryAppearance(boolean flagInventoryAppearance) {
        this.flagInventoryAppearance = flagInventoryAppearance;
    }
    public void setFlagPuzzle1HintActivated(boolean flagPuzzle1HintActivated) {
        this.flagPuzzle1HintActivated = flagPuzzle1HintActivated;
    }
    public void setFlagGoTo3Lvl(boolean flagGoTo3Lvl) {
        this.flagGoTo3Lvl = flagGoTo3Lvl;
    }
    public void setFlagPuzzle2HintActivated(boolean flagPuzzle2HintActivated) {
        this.flagPuzzle2HintActivated = flagPuzzle2HintActivated;
    }
    public void setFlagBIGisVisible(boolean flagBIGisVisible) {
        this.flagBIGisVisible = flagBIGisVisible;
    }
    public void setFlagPuzzle3HintActivated(boolean flagPuzzle3HintActivated) {
        this.flagPuzzle3HintActivated = flagPuzzle3HintActivated;
    }
    public void setFlagLiteralCryptexStageStart(boolean flagLiteralCryptexStageStart) {
        this.flagLiteralCryptexStageStart = flagLiteralCryptexStageStart;
    }
    public void setFlagGoTo4Lvl(boolean flagGoTo4Lvl) {
        this.flagGoTo4Lvl = flagGoTo4Lvl;
    }
    public void setFlagLevelStarted(boolean flagLevelStarted) {
        this.flagLevelStarted = flagLevelStarted;
    }
    public void setFlagCubeCalled(boolean flagCubeCalled) {
        this.flagCubeCalled = flagCubeCalled;
    }
    public void setFlagFirstCubeCellOccupied(boolean flagFirstCubeCellOccupied) {
        this.flagFirstCubeCellOccupied = flagFirstCubeCellOccupied;
    }
    public void setFlagSecondCubeCellOccupied(boolean flagSecondCubeCellOccupied) {
        this.flagSecondCubeCellOccupied = flagSecondCubeCellOccupied;
    }
    public void setFlagThirdCubeCellOccupied(boolean flagThirdCubeCellOccupied) {
        this.flagThirdCubeCellOccupied = flagThirdCubeCellOccupied;
    }
    public void setFlagFourthCubeCellOccupied(boolean flagFourthCubeCellOccupied) {
        this.flagFourthCubeCellOccupied = flagFourthCubeCellOccupied;
    }
    public void setFlagGoTo5Lvl(boolean flagGoTo5Lvl) {
        this.flagGoTo5Lvl = flagGoTo5Lvl;
    }
    public void setFlagGoTo4CompLvl(boolean flagGoTo4CompLvl) {
        this.flagGoTo4CompLvl = flagGoTo4CompLvl;
    }
    public void setFlagGoTo6Lvl(boolean flagGoTo6Lvl) {
        this.flagGoTo6Lvl = flagGoTo6Lvl;
    }
    public void setFlagActiveCellOccupied(boolean flagActiveCellOccupied) {
        this.flagActiveCellOccupied = flagActiveCellOccupied;
    }
    public void setFlagPanelAppearance(boolean flagPanelAppearance) {
        this.flagPanelAppearance = flagPanelAppearance;
    }
    public void setFlagJournalStageStart(boolean flagJournalStageStart) {
        this.flagJournalStageStart = flagJournalStageStart;
    }
    public void setFlagMapStageStart(boolean flagMapStageStart) {
        this.flagMapStageStart = flagMapStageStart;
    }
    public void setJournalCurrentLevelNumber(int journalCurrentLevelNumber) {
        this.journalCurrentLevelNumber = journalCurrentLevelNumber;
    }
    public void setFlag5FreeTransition(boolean flag5FreeTransition) {
        this.flag5FreeTransition = flag5FreeTransition;
    }
    public void setFlag6FreeTransition(boolean flag6FreeTransition) {
        this.flag6FreeTransition = flag6FreeTransition;
    }
    public void setFlag4FreeTransition(boolean flag4FreeTransition) {
        this.flag4FreeTransition = flag4FreeTransition;
    }

    public void setFlagJournalOpenedOnce(boolean flagJournalOpenedOnce) {
        this.flagJournalOpenedOnce = flagJournalOpenedOnce;
    }
    public void setFlagGoTo7Lvl(boolean flagGoTo7Lvl) {
        this.flagGoTo7Lvl = flagGoTo7Lvl;
    }
    public void setFlagGoTo6CompLvl(boolean flagGoTo6CompLvl) {
        this.flagGoTo6CompLvl = flagGoTo6CompLvl;
    }
    public void setFlagGoTo8Lvl(boolean flagGoTo8Lvl) {
        this.flagGoTo8Lvl = flagGoTo8Lvl;
    }
    public void setFlagParchmentStageStart(boolean flagParchmentStageStart) {
        this.flagParchmentStageStart = flagParchmentStageStart;
    }
    public void setFlagGoTo9Lvl(boolean flagGoTo9Lvl) {
        this.flagGoTo9Lvl = flagGoTo9Lvl;
    }
    public void setFlagMenuActivated(boolean flagMenuActivated) {
        this.flagMenuActivated = flagMenuActivated;
    }

    public int getLevelNumber() {
        return levelNumber;
    }
    public int getJournalCurrentLevelNumber() {
        return journalCurrentLevelNumber;
    }
    public int getMaxLevelAchieved() {
        return maxLevelAchieved;
    }
    public int getItemsWeCanCollect1Level() {
        return itemsWeCanCollect1Level;
    }
    public int getItemsWeCanCollect2Level() {
        return itemsWeCanCollect2Level;
    }
    public int getItemsWeCanCollect3Level() {
        return itemsWeCanCollect3Level;
    }
    public int getItemsWeCanCollect4Level() {
        return itemsWeCanCollect4Level;
    }
    public int getItemsWeCanCollect5Level() {
        return itemsWeCanCollect5Level;
    }
    public int getItemsWeCanCollect6Level() {
        return itemsWeCanCollect6Level;
    }
    public int getItemsWeCanCollect7Level() {
        return itemsWeCanCollect7Level;
    }
    public int getItemsWeCanCollect8Level() {
        return itemsWeCanCollect8Level;
    }
    public int getItemsWeCanCollect9Level() {
        return itemsWeCanCollect9Level;
    }
    public int getItemsWeCanCollect6CompLevel() {
        return itemsWeCanCollect6CompLevel;
    }
    public boolean getFlagGameCompleted() {
        return flagGameCompleted;
    }
    public boolean getFlagDigitalCryptexStageStart() {
        return flagDigitalCryptexStageStart;
    }
    public boolean getFlagInDE() {
        return flagInDE;
    }
    public boolean getFlag456Transition() {
        return flag456Transition;
    }
    public boolean getFlag4Visited() {
        return flag4Visited;
    }
    public boolean getFlag5Visited() {
        return flag5Visited;
    }
    public boolean getFlag6Visited() {
        return flag6Visited;
    }
    public boolean getFlagOptionsStageStart() {
        return flagOptionsStageStart;
    }
    public boolean getFlagTimerIsVisible() {
        return flagTimerIsVisible;
    }
    public boolean getFlagHintInProgress() {
        return flagHintInProgress;
    }
    public boolean getFlagTimerDisabled() {
        return flagTimerDisabled;
    }
    public boolean getFlagRender() {
        return flagRender;
    }

    public void setItemsWeCanCollect1Level(int itemsWeCanCollect1Level) {
        this.itemsWeCanCollect1Level = itemsWeCanCollect1Level;
    }
    public void setMaxLevelAchieved(int maxLevelAchieved) {
        this.maxLevelAchieved = maxLevelAchieved;
    }
    public void setItemsWeCanCollect2Level(int itemsWeCanCollect2Level) {
        this.itemsWeCanCollect2Level = itemsWeCanCollect2Level;
    }
    public void setItemsWeCanCollect3Level(int itemsWeCanCollect3Level) {
        this.itemsWeCanCollect3Level = itemsWeCanCollect3Level;
    }
    public void setItemsWeCanCollect4Level(int itemsWeCanCollect4Level) {
        this.itemsWeCanCollect4Level = itemsWeCanCollect4Level;
    }
    public void setItemsWeCanCollect5Level(int itemsWeCanCollect5Level) {
        this.itemsWeCanCollect5Level = itemsWeCanCollect5Level;
    }
    public void setItemsWeCanCollect6Level(int itemsWeCanCollect6Level) {
        this.itemsWeCanCollect6Level = itemsWeCanCollect6Level;
    }
    public void setItemsWeCanCollect7Level(int itemsWeCanCollect7Level) {
        this.itemsWeCanCollect7Level = itemsWeCanCollect7Level;
    }
    public void setItemsWeCanCollect6CompLevel(int itemsWeCanCollect6CompLevel) {
        this.itemsWeCanCollect6CompLevel = itemsWeCanCollect6CompLevel;
    }
    public void setItemsWeCanCollect8Level(int itemsWeCanCollect8Level) {
        this.itemsWeCanCollect8Level = itemsWeCanCollect8Level;
    }
    public void setItemsWeCanCollect9Level(int itemsWeCanCollect9Level) {
        this.itemsWeCanCollect9Level = itemsWeCanCollect9Level;
    }
    public void setFlagPanelStageStart(boolean flagPanelStageStart) {
        this.flagPanelStageStart = flagPanelStageStart;
    }
    public void setFlagCubeStageStart(boolean flagCubeStageStart) {
        this.flagCubeStageStart = flagCubeStageStart;
    }
    public void setFlagEggIn1Cell(boolean flagEggIn1Cell) {
        this.flagEggIn1Cell = flagEggIn1Cell;
    }
    public void setFlagEggIn2Cell(boolean flagEggIn2Cell) {
        this.flagEggIn2Cell = flagEggIn2Cell;
    }
    public void setFlagEggIn3Cell(boolean flagEggIn3Cell) {
        this.flagEggIn3Cell = flagEggIn3Cell;
    }
    public void setFlagEggIn4Cell(boolean flagEggIn4Cell) {
        this.flagEggIn4Cell = flagEggIn4Cell;
    }
    public void setFlagLevelTransition(boolean flagLevelTransition) {
        this.flagLevelTransition = flagLevelTransition;
    }
    public void setFlagGameCompleted(boolean flagGameCompleted) {
        this.flagGameCompleted = flagGameCompleted;
    }
    public void setFlagDigitalCryptexStageStart(boolean flagDigitalCryptexStageStart) {
        this.flagDigitalCryptexStageStart = flagDigitalCryptexStageStart;
    }
    public void setFlagInDE(boolean flagInDE) {
        this.flagInDE = flagInDE;
    }
    public void setFlag456Transition(boolean flag456Transition) {
        this.flag456Transition = flag456Transition;
    }
    public void setFlag4Visited(boolean flag4Visited) {
        this.flag4Visited = flag4Visited;
    }
    public void setFlag5Visited(boolean flag5Visited) {
        this.flag5Visited = flag5Visited;
    }
    public void setFlag6Visited(boolean flag6Visited) {
        this.flag6Visited = flag6Visited;
    }
    public void setFlagInventoryMoving(boolean flagInventoryMoving) {
        this.flagInventoryMoving = flagInventoryMoving;
    }
    public void setFlagPanelMoving(boolean flagPanelMoving) {
        this.flagPanelMoving = flagPanelMoving;
    }
    public void setSoundFlag(boolean soundFlag) {
        this.soundFlag = soundFlag;
    }
    public void setMusicFlag(boolean musicFlag) {
        this.musicFlag = musicFlag;
    }
    public void setJournalFlag(boolean journalFlag) {
        this.journalFlag = journalFlag;
    }
    public void setFlagOptionsStageStart(boolean flagOptionsStageStart) {
        this.flagOptionsStageStart = flagOptionsStageStart;
    }
    public void setCakeInIIndex(int cakeInIIndex) {
        this.cakeInIIndex = cakeInIIndex;
    }
    public void setFlagTimerIsVisible(boolean flagTimerIsVisible) {
        this.flagTimerIsVisible = flagTimerIsVisible;
    }
    public void setFlagHintInProgress(boolean flagHintInProgress) {
        this.flagHintInProgress = flagHintInProgress;
    }
    public void setFlagTimerDisabled(boolean flagTimerDisabled) {
        this.flagTimerDisabled = flagTimerDisabled;
    }
    public void setFlagRender(boolean flagRender) {
        this.flagRender = flagRender;
    }

    public int getxHigher() {
        return xHigher;
    }
    public int getxLower() {
        return xLower;
    }
    public int getyHigher() {
        return yHigher;
    }
    public int getyLower() {
        return yLower;
    }

    public Image getHint_message() {
        return hint_message;
    }
}
