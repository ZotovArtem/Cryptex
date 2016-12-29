package art.dual.seventhLevel;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;

import art.dual.Cryptex;
import art.dual.StaticObjects;

/**
 * One class that contains game logic and processes.
 */

public class Listeners7 extends StaticObjects{

    private String name = new String(), rotationDirection = "-";
    private float currentValue, previousValue, delta, touchY, deltaX, deltaY, angle, previousAngle, currentAngle, valveRotation1, valveRotation2;
    private int weight = 0, measuredSugarValue1, measuredSaltValue1, measuredFlourValue1, measuredYeastValue1, measuredSugarValue2, measuredSaltValue2,
            measuredFlourValue2, measuredYeastValue2, currentFrameNumber = 0, currentFrameNumberWAPI, counter, waterLevelInBeaker1 = 0,
            waterLevelInBeaker2 = 0, renderTime = 1800, eggCounter = 0, butterCounter = 0;

    public Listeners7(){
    }

    //1
//bars
    private InputListener barsIL = new InputListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            name = event.getListenerActor().getName();
            System.out.println("my name is: " + name);

            if(name.equals("exit_bars")){
                stageTransition(Cryptex.main.getMainScreen7().getKienActors(), Cryptex.main.getMainScreen7().getBarsActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagKien(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagBars(false);
            }

            return true;
        }
    };

    //2
//beak
    private InputListener beakIL = new InputListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            name = event.getListenerActor().getName();
            System.out.println("my name is: " + name);

            if(name.equals("beak_screen_overlap")){
                System.out.println(MainScreen7.soosJSON.getBeak_water().getY());
            }
            else if(name.equals("exit_beak")){
                Cryptex.main.getPanelStage().getClearScreenOverPanelActors().setVisible(true);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            synchronized (this) {
                                if(Cryptex.main.getMainScreen7().getListeners().getCurrentFrameNumber() > 0) {

                                    Cryptex.main.getGlobalReferences().musicFadeOut(Cryptex.main.getMainScreen7().getMusic().getWater_flow(), Cryptex.main.getMainScreen7().getListeners().getCurrentFrameNumber() * 50 / 100,
                                            Cryptex.main.getMainScreen7().getMusic().getWater_flow().getVolume());

                                    for (int i = Cryptex.main.getMainScreen7().getListeners().getCurrentFrameNumber(); i > 0; i--) {
                                        Cryptex.main.getMainScreen7().getAnimation().getGroupJoystick().getChildren().get(i).setVisible(false);
                                        Cryptex.main.getMainScreen7().getAnimation().getGroupJoystick().getChildren().get(i - 1).setVisible(true);
                                        if (Cryptex.main.getMainScreen7().getFlags().getFlagValveInWCOpened() & MainScreen7.soosJSON.getKiwt_pipe_mounted().isVisible()) {
                                            Cryptex.main.getMainScreen7().getAnimation().getGroupFlow().getChildren().get(i).setVisible(false);
                                            Cryptex.main.getMainScreen7().getAnimation().getGroupFlow().getChildren().get(i - 1).setVisible(true);
                                        }
                                        wait(50);
                                    }
                                    wait(200);
                                }

                                Cryptex.main.getMainScreen7().getListeners().setCurrentFrameNumber(0);
                                Cryptex.main.getMainScreen7().getFlags().setFlagWaterIsFlowing(false);
                                stageTransition(Cryptex.main.getMainScreen7().getKiwtActors(), Cryptex.main.getMainScreen7().getBeakActors());
                                Cryptex.main.getMainScreen7().getFlags().setFlagKiwt(true);
                                Cryptex.main.getMainScreen7().getFlags().setFlagBeak(false);
                                Cryptex.main.getPanelStage().getClearScreenOverPanelActors().setVisible(false);
                            }
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                    }
                });
                thread.start();
            }

            return true;
        }
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            Cryptex.main.getMainScreen7().getListeners().setCounter(0);
        }
        @Override
        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            Cryptex.main.getMainScreen7().getListeners().setPreviousValue(Cryptex.main.getMainScreen7().getListeners().getCurrentValue());
            Cryptex.main.getMainScreen7().getListeners().setCurrentValue(y);
            Cryptex.main.getMainScreen7().getListeners().setDelta(Cryptex.main.getMainScreen7().getListeners().getCurrentValue() - Cryptex.main.getMainScreen7().getListeners().getPreviousValue());
            Cryptex.main.getMainScreen7().getListeners().setCounter(Cryptex.main.getMainScreen7().getListeners().getCounter() + 1);

            if(Cryptex.main.getMainScreen7().getListeners().getCurrentFrameNumber() == 0){
                Cryptex.main.getMainScreen7().getFlags().setFlagWaterIsFlowing(false);
                Cryptex.main.getGlobalReferences().pauseMusic(Cryptex.main.getMainScreen7().getMusic().getWater_flow());
            }
            else if(Cryptex.main.getMainScreen7().getListeners().getCurrentFrameNumber() > 0 & !Cryptex.main.getMainScreen7().getFlags().getFlagWaterIsFlowing() & !Cryptex.main.getMainScreen7().getFlags().getFlagBeakerFilled()){
                if(Cryptex.main.getMainScreen7().getFlags().getFlagValveInWCOpened() & MainScreen7.soosJSON.getKiwt_pipe_mounted().isVisible()) {
                    Cryptex.main.getGlobalReferences().playMusic(Cryptex.main.getMainScreen7().getMusic().getWater_flow(), 0.1f, true);
                    Cryptex.main.getMainScreen7().getFlags().setFlagWaterIsFlowing(true);
                    MainScreen7.soosJSON.getKiwt_water_in_beaker().setVis(true);
                    MainScreen7.aoos.getBeaker_filled().setVis(true);
                }
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            synchronized (this) {
                                while(Cryptex.main.getMainScreen7().getFlags().getFlagWaterIsFlowing()){
                                    int delay = 425;

                                    for(int i = 0; i < Cryptex.main.getMainScreen7().getAnimation().getGroupJoystick().getChildren().size; i++){
                                        if(Cryptex.main.getMainScreen7().getAnimation().getGroupJoystick().getChildren().get(i).isVisible()){
                                            break;
                                        }
                                        delay -= 25;
                                    }

                                    if(MainScreen7.soosJSON.getBeak_water().getY() < 45 & Cryptex.main.getMainScreen7().getFlags().getFlagValveInWCOpened()
                                            & MainScreen7.soosJSON.getKiwt_pipe_mounted().isVisible())
                                        MainScreen7.soosJSON.getBeak_water().moveBy(0, 1f);
                                    else if(Cryptex.main.getMainScreen7().getFlags().getFlagValveInWCOpened() & MainScreen7.soosJSON.getKiwt_pipe_mounted().isVisible()) {

                                        Cryptex.main.getGlobalReferences().musicFadeOut(Cryptex.main.getMainScreen7().getMusic().getWater_flow(), Cryptex.main.getMainScreen7().getListeners().getCurrentFrameNumber() * 1 / 2,
                                                Cryptex.main.getMainScreen7().getMusic().getWater_flow().getVolume());

                                        for(int i = Cryptex.main.getMainScreen7().getListeners().getCurrentFrameNumber(); i > 0; i--){
                                            Cryptex.main.getMainScreen7().getAnimation().getGroupJoystick().getChildren().get(i).setVisible(false);
                                            Cryptex.main.getMainScreen7().getAnimation().getGroupJoystick().getChildren().get(i - 1).setVisible(true);
                                            Cryptex.main.getMainScreen7().getAnimation().getGroupFlow().getChildren().get(i).setVisible(false);
                                            Cryptex.main.getMainScreen7().getAnimation().getGroupFlow().getChildren().get(i - 1).setVisible(true);
                                            wait(20);
                                        }

                                        MainScreen7.soosJSON.getKiwt_water_in_beaker().setVis(true);
                                        MainScreen7.aoos.getBeaker_filled().setVis(true);
                                        Cryptex.main.getMainScreen7().getFlags().setFlagBeakerFilled(true);
                                        Cryptex.main.getMainScreen7().getFlags().setFlagWaterIsFlowing(false);
                                        Cryptex.main.getMainScreen7().getListeners().setCurrentFrameNumber(0);
                                        Cryptex.main.getMainScreen7().getFlags().setFlagWaterTapBlocked(true);
                                    }

                                    wait(delay);
                                }
                            }
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                    }
                });
                thread.start();
            }

            if(y > 190 & y < 410 & x > 50 & x < 150 & Cryptex.main.getMainScreen7().getListeners().getCounter() % 5 == 0 & !Cryptex.main.getMainScreen7().getFlags().getFlagWaterTapBlocked()) {
                if(!Cryptex.main.getMainScreen7().getFlags().getFlagValveInWCOpened()){
                    if(!Cryptex.main.getMainScreen7().getFlags().getFlagPipeOnlyFixed()){
                        Cryptex.main.getMainScreen7().getFlags().setFlagPipeOnlyFixed(true);
                        Cryptex.main.getJo().addIndexToAL("pipe_only_fixed_1", Cryptex.main.getGlobalReferences().getLevelNumber(), 7);
                    }
                }

                if(Cryptex.main.getMainScreen7().getListeners().getDelta() < 0){
                    if(Cryptex.main.getMainScreen7().getListeners().getCurrentFrameNumber() < Cryptex.main.getMainScreen7().getAnimation().getGroupJoystick().getChildren().size - 1) {
                        Cryptex.main.getMainScreen7().getAnimation().getGroupJoystick().getChildren().get(Cryptex.main.getMainScreen7().getListeners().getCurrentFrameNumber()).setVisible(false);
                        if(Cryptex.main.getMainScreen7().getFlags().getFlagValveInWCOpened() & MainScreen7.soosJSON.getKiwt_pipe_mounted().isVisible()) {
                            Cryptex.main.getMainScreen7().getAnimation().getGroupFlow().getChildren().get(Cryptex.main.getMainScreen7().getListeners().getCurrentFrameNumber()).setVisible(false);
                        }
                    }
                    if(Cryptex.main.getMainScreen7().getListeners().getCurrentFrameNumber() + 1 < Cryptex.main.getMainScreen7().getAnimation().getGroupJoystick().getChildren().size) {
                        Cryptex.main.getMainScreen7().getAnimation().getGroupJoystick().getChildren().get(Cryptex.main.getMainScreen7().getListeners().getCurrentFrameNumber() + 1).setVisible(true);

                        if(!Cryptex.main.getMainScreen7().getFlags().getFlagJoystickFirstTouch() & (!Cryptex.main.getMainScreen7().getFlags().getFlagValveInWCOpened() | !MainScreen7.soosJSON.getKiwt_pipe_mounted().isVisible())){
                            Cryptex.main.getMainScreen7().getFlags().setFlagJoystickFirstTouch(true);
                            Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getNo_water(), 1);
                        }
                        if(Cryptex.main.getMainScreen7().getFlags().getFlagValveInWCOpened() & MainScreen7.soosJSON.getKiwt_pipe_mounted().isVisible()) {
                            Cryptex.main.getMainScreen7().getAnimation().getGroupFlow().getChildren().get(Cryptex.main.getMainScreen7().getListeners().getCurrentFrameNumber() + 1).setVisible(true);
                        }
                        Cryptex.main.getMainScreen7().getListeners().setCurrentFrameNumber(Cryptex.main.getMainScreen7().getListeners().getCurrentFrameNumber() + 1);
                    }
                    Cryptex.main.getMainScreen7().getMusic().getWater_flow().setVolume(Cryptex.main.getMainScreen7().getMusic().getWater_flow().getVolume() + 0.03f);
                }
                else {
                    if(Cryptex.main.getMainScreen7().getListeners().getCurrentFrameNumber() != 0) {
                        Cryptex.main.getMainScreen7().getAnimation().getGroupJoystick().getChildren().get(Cryptex.main.getMainScreen7().getListeners().getCurrentFrameNumber()).setVisible(false);
                        if(Cryptex.main.getMainScreen7().getFlags().getFlagValveInWCOpened() & MainScreen7.soosJSON.getKiwt_pipe_mounted().isVisible()) {
                            Cryptex.main.getMainScreen7().getAnimation().getGroupFlow().getChildren().get(Cryptex.main.getMainScreen7().getListeners().getCurrentFrameNumber()).setVisible(false);
                        }
                    }
                    if(Cryptex.main.getMainScreen7().getListeners().getCurrentFrameNumber() - 1 >= 0) {
                        Cryptex.main.getMainScreen7().getAnimation().getGroupJoystick().getChildren().get(Cryptex.main.getMainScreen7().getListeners().getCurrentFrameNumber() - 1).setVisible(true);
                        if(Cryptex.main.getMainScreen7().getFlags().getFlagValveInWCOpened() & MainScreen7.soosJSON.getKiwt_pipe_mounted().isVisible()) {
                            Cryptex.main.getMainScreen7().getAnimation().getGroupFlow().getChildren().get(Cryptex.main.getMainScreen7().getListeners().getCurrentFrameNumber() - 1).setVisible(true);
                        }
                        Cryptex.main.getMainScreen7().getListeners().setCurrentFrameNumber(Cryptex.main.getMainScreen7().getListeners().getCurrentFrameNumber() - 1);
                    }
                    Cryptex.main.getMainScreen7().getMusic().getWater_flow().setVolume(Cryptex.main.getMainScreen7().getMusic().getWater_flow().getVolume() - 0.03f);
                }

            }
        }
    };

    //3
//cake
    private InputListener cakeIL = new InputListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            name = event.getListenerActor().getName();
            System.out.println("my name is: " + name);

            if(name.equals("exit_cake")){
                stageTransition(Cryptex.main.getMainScreen7().getCetaActors(), Cryptex.main.getMainScreen7().getCakeActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagCeta(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagCake(false);
            }

            return true;
        }
    };

    //4
//ceta
    private InputListener cetaIL = new InputListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            name = event.getListenerActor().getName();
            System.out.println("my name is: " + name);

            if(name.equals("ceta_cake_overlap")){
                stageTransition(Cryptex.main.getMainScreen7().getCakeActors(), Cryptex.main.getMainScreen7().getCetaActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagCake(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagCeta(false);
            }
            else if(name.equals("ceta_cubo_overlap")){
                stageTransition(Cryptex.main.getMainScreen7().getCuboActors(), Cryptex.main.getMainScreen7().getCetaActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagCubo(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagCeta(false);
            }
            else if(name.equals("exit_ceta")){
                stageTransition(Cryptex.main.getMainScreen7().getKitcActors(), Cryptex.main.getMainScreen7().getCetaActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagKitc(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagCeta(false);
            }

            return true;
        }
    };

    //5
//cule
    private InputListener culeIL = new InputListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            name = event.getListenerActor().getName();
            System.out.println("my name is: " + name);

            if(name.equals("cule_left_door_overlap") & !Cryptex.main.getMainScreen7().getFlags().getFlagCakeIsDone()){
                Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getCupboard_opening(), 1);
                if(MainScreen7.soosJSON.getCule_right_door_opened().isVisible()) MainScreen7.soosJSON.getCule_both_doors_opened().setVis(true);
                else MainScreen7.soosJSON.getCule_left_door_opened().setVis(true);
                MainScreen7.soosJSON.getCule_left_door_overlap().setVis(false);

                if(!Cryptex.main.getMainScreen7().getFlags().getFlagJellyTaken()) {
                    MainScreen7.aoos.getYellow_jelly_jar().setVis(true);
                }
            }
            else if(name.equals("cule_right_door_overlap")){
                Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getCupboard_opening(), 1);
                if(MainScreen7.soosJSON.getCule_left_door_opened().isVisible()) MainScreen7.soosJSON.getCule_both_doors_opened().setVis(true);
                else MainScreen7.soosJSON.getCule_right_door_opened().setVis(true);
                MainScreen7.soosJSON.getCule_right_door_overlap().setVis(false);
            }
            else if(name.equals("exit_cule")){
                Cryptex.main.getPanelStage().getClearScreenOverPanelActors().setVisible(true);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            synchronized (this) {
                                if(MainScreen7.soosJSON.getCule_left_door_opened().isVisible() | MainScreen7.soosJSON.getCule_right_door_opened().isVisible() |
                                        MainScreen7.soosJSON.getCule_both_doors_opened().isVisible()) {
                                    Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getCupboard_closing(), 1);
                                    MainScreen7.soosJSON.getCule_left_door_overlap().setVis(true);
                                    MainScreen7.soosJSON.getCule_left_door_opened().setVis(false);
                                    MainScreen7.aoos.getYellow_jelly_jar().setVis(false);
                                    MainScreen7.soosJSON.getCule_right_door_overlap().setVis(true);
                                    MainScreen7.soosJSON.getCule_right_door_opened().setVis(false);
                                    MainScreen7.soosJSON.getCule_both_doors_opened().setVis(false);

                                    wait(350);
                                }

                                stageTransition(Cryptex.main.getMainScreen7().getKitcActors(), Cryptex.main.getMainScreen7().getCuleActors());
                                Cryptex.main.getMainScreen7().getFlags().setFlagKitc(true);
                                Cryptex.main.getMainScreen7().getFlags().setFlagCule(false);
                                Cryptex.main.getPanelStage().getClearScreenOverPanelActors().setVisible(false);
                            }
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                    }
                });
                thread.start();
            }

            return true;
        }
    };

    //6
//curi
    private InputListener curiIL = new InputListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            name = event.getListenerActor().getName();
            System.out.println("my name is: " + name);

            if(name.endsWith("_door_closed") | name.equals("curi_middle_door_closed_with_cocoa")){
                Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getCupboard_opening(), 1);
            }

            if(name.equals("curi_left_door_closed")){
                MainScreen7.soosJSON.getCuri_left_door_closed().setVis(false);
            }
            else if(name.equals("curi_middle_door_closed_with_cocoa")){
                MainScreen7.soosJSON.getCuri_middle_door_closed_with_cocoa().setVis(false);
            }
            else if(name.equals("curi_middle_door_closed")){
                MainScreen7.soosJSON.getCuri_middle_door_closed().setVis(false);
            }
            else if(name.equals("curi_right_door_closed")){
                MainScreen7.soosJSON.getCuri_right_door_closed().setVis(false);
            }
            else if(name.equals("exit_curi")){
                Cryptex.main.getPanelStage().getClearScreenOverPanelActors().setVisible(true);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            synchronized (this) {
                                if(!MainScreen7.soosJSON.getCuri_left_door_closed().isVisible() |
                                        (!MainScreen7.soosJSON.getCuri_middle_door_closed().isVisible() & !MainScreen7.soosJSON.getCuri_middle_door_closed_with_cocoa().isVisible()) |
                                        !MainScreen7.soosJSON.getCuri_right_door_closed().isVisible()) {
                                    MainScreen7.soosJSON.getCuri_left_door_closed().setVis(true);
                                    if(MainScreen7.aoos.getCocoa().isVisible()) MainScreen7.soosJSON.getCuri_middle_door_closed_with_cocoa().setVis(true);
                                    else MainScreen7.soosJSON.getCuri_middle_door_closed().setVis(true);
                                    MainScreen7.soosJSON.getCuri_right_door_closed().setVis(true);
                                    Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getCupboard_closing(), 1);

                                    wait(350);
                                }

                                stageTransition(Cryptex.main.getMainScreen7().getKitcActors(), Cryptex.main.getMainScreen7().getCuriActors());
                                Cryptex.main.getMainScreen7().getFlags().setFlagKitc(true);
                                Cryptex.main.getMainScreen7().getFlags().setFlagCuri(false);
                                Cryptex.main.getPanelStage().getClearScreenOverPanelActors().setVisible(false);
                            }
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                    }
                });
                thread.start();
            }

            return true;
        }
    };

    //7
//cubo
    private InputListener cuboIL = new InputListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            name = event.getListenerActor().getName();
            System.out.println("my name is: " + name);

            if(name.equals("exit_cubo")){
                stageTransition(Cryptex.main.getMainScreen7().getCetaActors(), Cryptex.main.getMainScreen7().getCuboActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagCeta(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagCubo(false);
            }

            return true;
        }
    };

    //8
//frid
    private InputListener fridIL = new InputListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            name = event.getListenerActor().getName();
            System.out.println("my name is: " + name);

            if(name.equals("frid_handle_overlap")){
                Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getFridge_opening(), 1);
                Cryptex.main.getMainScreen7().getMusic().getFridge_works().setVolume(0.4f);
                MainScreen7.soosJSON.getFrid_fridge_opened().setVis(true);

                if(!Cryptex.main.getMainScreen7().getFlags().getFlagEgg1Taken()){
                    MainScreen7.aoos.getEgg1().setVis(true);
                }
                if(!Cryptex.main.getMainScreen7().getFlags().getFlagEgg2Taken()){
                    MainScreen7.aoos.getEgg2().setVis(true);
                }
                if(!Cryptex.main.getMainScreen7().getFlags().getFlagEgg3Taken()){
                    MainScreen7.aoos.getEgg3().setVis(true);
                }
                if(!Cryptex.main.getMainScreen7().getFlags().getFlagEgg4Taken()){
                    MainScreen7.aoos.getEgg4().setVis(true);
                }
                if(!Cryptex.main.getMainScreen7().getFlags().getFlagEgg5Taken()){
                    MainScreen7.aoos.getEgg5().setVis(true);
                }
                if(!Cryptex.main.getMainScreen7().getFlags().getFlagEgg6Taken()){
                    MainScreen7.aoos.getEgg6().setVis(true);
                }
                if(!Cryptex.main.getMainScreen7().getFlags().getFlagButter1Taken()){
                    MainScreen7.aoos.getButter1().setVis(true);
                }
                if(!Cryptex.main.getMainScreen7().getFlags().getFlagButter2Taken()){
                    MainScreen7.aoos.getButter2().setVis(true);
                }
                if(!Cryptex.main.getMainScreen7().getFlags().getFlagButter3Taken()){
                    MainScreen7.aoos.getButter3().setVis(true);
                }
                if(!Cryptex.main.getMainScreen7().getFlags().getFlagMilkTaken()){
                    MainScreen7.aoos.getMilk().setVis(true);
                }
            }
            else if(name.equals("exit_frid")){
                Cryptex.main.getPanelStage().getClearScreenOverPanelActors().setVisible(true);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            synchronized (this) {
                                if(MainScreen7.soosJSON.getFrid_fridge_opened().isVisible()) {
                                    Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getFridge_closing(), 1);
                                    Cryptex.main.getMainScreen7().getMusic().getFridge_works().setVolume(0.2f);
                                    MainScreen7.soosJSON.getFrid_fridge_opened().setVis(false);

                                    for(int i = 0; i < MainScreen7.aoos.getObjectsInFridge().length; i++){
                                        MainScreen7.aoos.getObjectsInFridge()[i].setVis(false);
                                    }

                                    wait(350);
                                }

                                Cryptex.main.getGlobalReferences().pauseMusic(Cryptex.main.getMainScreen7().getMusic().getFridge_works());
                                stageTransition(Cryptex.main.getMainScreen7().getKitcActors(), Cryptex.main.getMainScreen7().getFridActors());
                                Cryptex.main.getMainScreen7().getFlags().setFlagKitc(true);
                                Cryptex.main.getMainScreen7().getFlags().setFlagFrid(false);
                                Cryptex.main.getPanelStage().getClearScreenOverPanelActors().setVisible(false);
                            }
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                    }
                });
                thread.start();
            }

            return true;
        }
    };

    //9
//futa
    private InputListener futaIL = new InputListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            name = event.getListenerActor().getName();
            System.out.println("my name is: " + name);

            if(name.equals("exit_futa")){
                stageTransition(Cryptex.main.getMainScreen7().getCarsActors(), Cryptex.main.getMainScreen7().getFutaActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagCars(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagFuta(false);
            }

            return true;
        }
    };

    //10
//jars
    private InputListener jarsIL = new InputListener(){

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            name = event.getListenerActor().getName();
            System.out.println("my name is: " + name);

            if(name.equals("exit_jars")){
                stageTransition(Cryptex.main.getMainScreen7().getRitaActors(), Cryptex.main.getMainScreen7().getJarsActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagRita(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagJars(false);
            }

            return true;
        }
    };

    //11
//kitc
    private InputListener kitcIL = new InputListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            name = event.getListenerActor().getName();
            System.out.println("my name is: " + name);

            if(name.equals("kitc_ceta_overlap")){
                stageTransition(Cryptex.main.getMainScreen7().getCetaActors(), Cryptex.main.getMainScreen7().getKitcActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagCeta(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagKitc(false);
            }
            else if(name.equals("kitc_cule_overlap")){
                stageTransition(Cryptex.main.getMainScreen7().getCuleActors(), Cryptex.main.getMainScreen7().getKitcActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagCule(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagKitc(false);
            }
            else if(name.equals("kitc_curi_overlap")){
                stageTransition(Cryptex.main.getMainScreen7().getCuriActors(), Cryptex.main.getMainScreen7().getKitcActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagCuri(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagKitc(false);
            }
            else if(name.equals("kitc_frid_overlap")){
                Cryptex.main.getGlobalReferences().playMusic(Cryptex.main.getMainScreen7().getMusic().getFridge_works(), 0.2f, true);
                stageTransition(Cryptex.main.getMainScreen7().getFridActors(), Cryptex.main.getMainScreen7().getKitcActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagFrid(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagKitc(false);
            }
            else if(name.equals("kitc_kiwt_overlap")){

                if(!Cryptex.main.getMainScreen7().getFlags().getFlagKiwtFE()){
                    Cryptex.main.getMainScreen7().getFlags().setFlagKiwtFE(true);
                    Cryptex.main.getJo().addIndexToAL("kiwt_FE_1", Cryptex.main.getGlobalReferences().getLevelNumber(), 3);
                }

                stageTransition(Cryptex.main.getMainScreen7().getKiwtActors(), Cryptex.main.getMainScreen7().getKitcActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagKiwt(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagKitc(false);
            }
            else if(name.equals("kitc_ovvi_overlap")){

                if(!Cryptex.main.getMainScreen7().getFlags().getFlagOvviFE()){
                    Cryptex.main.getMainScreen7().getFlags().setFlagOvviFE(true);
                    Cryptex.main.getJo().addIndexToAL("ovvi_FE_1", Cryptex.main.getGlobalReferences().getLevelNumber(), 3);
                }

                stageTransition(Cryptex.main.getMainScreen7().getOvviActors(), Cryptex.main.getMainScreen7().getKitcActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagOvvi(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagKitc(false);
            }
            else if(name.equals("kitc_rita_overlap")){
                stageTransition(Cryptex.main.getMainScreen7().getRitaActors(), Cryptex.main.getMainScreen7().getKitcActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagRita(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagKitc(false);
            }
            else if(name.equals("exit_kitc")){
                stageTransition(Cryptex.main.getMainScreen7().getKienActors(), Cryptex.main.getMainScreen7().getKitcActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagKien(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagKitc(false);
            }

            return true;
        }
    };

    //12
//kiwt
    private InputListener kiwtIL = new InputListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            name = event.getListenerActor().getName();
            System.out.println("my name is: " + name);

            if(name.equals("kiwt_beak_overlap") & MainScreen7.soosJSON.getKiwt_beaker().isVisible()){
                Cryptex.main.getMainScreen7().getListeners().setCurrentFrameNumber(0);
                stageTransition(Cryptex.main.getMainScreen7().getBeakActors(), Cryptex.main.getMainScreen7().getKiwtActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagBeak(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagKiwt(false);
            }
            else if(name.equals("exit_kiwt")){
                stageTransition(Cryptex.main.getMainScreen7().getKitcActors(), Cryptex.main.getMainScreen7().getKiwtActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagKitc(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagKiwt(false);
            }

            return true;
        }
    };

    //13
//mixe
    private InputListener mixeIL = new InputListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            name = event.getListenerActor().getName();
            System.out.println("my name is: " + name);

            if(name.equals("exit_mixe")){
                stageTransition(Cryptex.main.getMainScreen7().getRitaActors(), Cryptex.main.getMainScreen7().getMixeActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagRita(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagMixe(false);
            }

            return true;
        }
        @Override
        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            Cryptex.main.getMainScreen7().getListeners().setPreviousValue(Cryptex.main.getMainScreen7().getListeners().getCurrentValue());
            Cryptex.main.getMainScreen7().getListeners().setCurrentValue(x);
            Cryptex.main.getMainScreen7().getListeners().setDelta(Cryptex.main.getMainScreen7().getListeners().getCurrentValue() - Cryptex.main.getMainScreen7().getListeners().getPreviousValue());

            if(y > 310 & y < 410 & x > 305 & x < 430 & Cryptex.main.getMainScreen7().getListeners().getDelta() > 0 & !MainScreen7.soosJSON.getTusw_tape_on_switch().isVisible()) {
                if (!Cryptex.main.getMainScreen7().getFlags().getFlagMixerFC()) {
                    Cryptex.main.getMainScreen7().getFlags().setFlagMixerFC(true);
                    Cryptex.main.getJo().addIndexToAL("mixer_FC_1", Cryptex.main.getGlobalReferences().getLevelNumber(), 3);
                }
            }

            if(y > 310 & y < 410 & x > 305 & x < 430) {
                if(Cryptex.main.getMainScreen7().getListeners().getDelta() > 0 & MainScreen7.soosJSON.getTusw_tape_on_switch().isVisible() & MainScreen7.soosJSON.getMixe_cream_unmixed().isVisible() &
                        !Cryptex.main.getMainScreen7().getFlags().getFlagMixingProcess()){

                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                synchronized (this) {
                                    Cryptex.main.getMainScreen7().getFlags().setFlagMixingProcess(true);

                                    Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getMixer_switch(), 1);
                                    Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getMixer(), 0.8f);
                                    MainScreen7.soos.getMixe_switch_on_mixer().setVisible(true);

                                    Cryptex.main.getGlobalReferences().stageDarkeningWithPanel();

                                    wait(2000);

                                    MainScreen7.soos.getMixe_switch_on_mixer().setVisible(false);
                                    MainScreen7.soosJSON.getMixe_cream_unmixed().setVis(false);
                                    MainScreen7.soosJSON.getMixe_cream_mixed().setVis(true);
                                    if(Cryptex.main.getMainScreen7().getFlags().getFlagPinkUnmixedCreamGot()) MainScreen7.aoos.getCream_pink().setVis(true);
                                    else MainScreen7.aoos.getCream_white().setVis(true);

                                    Cryptex.main.getGlobalReferences().stageSimpleLighteningWithPanel();

                                    Cryptex.main.getMainScreen7().getFlags().setFlagMixingProcess(false);
                                }
                            } catch (InterruptedException ex) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    });
                    thread.start();
                }
            }
        }
    };

    //14
//oven
    private InputListener ovenIL = new InputListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            name = event.getListenerActor().getName();
            System.out.println("my name is: " + name);

            if(x > 10 & x < 790 & y > 10 & y < 340 & name.equals("oven_screen_overlap") & !Cryptex.main.getMainScreen7().getFlags().getFlagBlockOvenDoor()){
                if(!MainScreen7.soos.getOven_oven_on().isVisible() & MainScreen7.soosJSON.getOven_oven_door_closed().isVisible()) {
                    Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getOven_opening(), 1);
                    MainScreen7.soosJSON.getOven_oven_door_closed().setVis(false);
                    MainScreen7.soosJSON.getOvvi_oven_door_closed().setVis(false);
                    MainScreen7.soosJSON.getOvvi_oven_door_opened().setVis(true);
                    MainScreen7.soosJSON.getKitc_oven_door_opened().setVis(true);
                    if(MainScreen7.soosJSON.getOven_shortcake_dark_closed_door().isVisible()){
                        MainScreen7.soosJSON.getOven_shortcake_dark_closed_door().setVis(false);
                        MainScreen7.soosJSON.getOven_shortcake_dark_opened_door().setVis(true);

                        if((Cryptex.main.getMainScreen7().getFlags().getFlagShortcakeCooked() | Cryptex.main.getMainScreen7().getListeners().getRenderTime() < 900)){
                            MainScreen7.soosJSON.getOvvi_shortcake_dark().setVis(true);
                        }
                        else MainScreen7.soosJSON.getOvvi_shortcake_uncooked().setVis(true);

                        if(Cryptex.main.getMainScreen7().getFlags().getFlagShortcakeCooked()) MainScreen7.aoos.getShortcake_dark().setVis(true);
                        else {
                            MainScreen7.soosJSON.getOven_shortcake_uncooked_closed_door().setVis(false);
                            MainScreen7.soosJSON.getOven_shortcake_uncooked_opened_door().setVis(true);
                        }
                    }
                    else if(MainScreen7.soosJSON.getOven_shortcake_light_closed_door().isVisible()){
                        MainScreen7.soosJSON.getOven_shortcake_light_closed_door().setVis(false);
                        MainScreen7.soosJSON.getOven_shortcake_light_opened_door().setVis(true);

                        System.out.println(Cryptex.main.getMainScreen7().getFlags().getFlagShortcakeCooked());
                        System.out.println(Cryptex.main.getMainScreen7().getListeners().getRenderTime());

                        if(Cryptex.main.getMainScreen7().getFlags().getFlagShortcakeCooked() | Cryptex.main.getMainScreen7().getListeners().getRenderTime() < 900){
                            MainScreen7.soosJSON.getOvvi_shortcake_light().setVis(true);
                        }
                        else MainScreen7.soosJSON.getOvvi_shortcake_uncooked().setVis(true);

                        if(Cryptex.main.getMainScreen7().getFlags().getFlagShortcakeCooked()) MainScreen7.aoos.getShortcake_light().setVis(true);
                        else {
                            MainScreen7.soosJSON.getOven_shortcake_uncooked_closed_door().setVis(false);
                            MainScreen7.soosJSON.getOven_shortcake_uncooked_opened_door().setVis(true);
                        }
                    }
                    else if(MainScreen7.soosJSON.getOven_shortcake_uncooked_closed_door().isVisible()){
                        MainScreen7.soosJSON.getOven_shortcake_uncooked_closed_door().setVis(false);
                        MainScreen7.soosJSON.getOven_shortcake_uncooked_opened_door().setVis(true);
                        MainScreen7.soosJSON.getOvvi_shortcake_uncooked().setVis(true);
                    }
                }
                else if(!MainScreen7.soosJSON.getOven_oven_door_closed().isVisible()){
                    Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getOven_closing(), 1);
                    MainScreen7.soosJSON.getOven_oven_door_closed().setVis(true);
                    MainScreen7.soosJSON.getOvvi_oven_door_closed().setVis(true);
                    MainScreen7.soosJSON.getOvvi_oven_door_opened().setVis(false);
                    MainScreen7.soosJSON.getKitc_oven_door_opened().setVis(false);
                    if(MainScreen7.soosJSON.getOven_shortcake_dark_opened_door().isVisible()){
                        MainScreen7.soosJSON.getOven_shortcake_dark_opened_door().setVis(false);
                        MainScreen7.soosJSON.getOven_shortcake_dark_closed_door().setVis(true);

                        if(MainScreen7.aoos.getShortcake_dark().isVisible() | Cryptex.main.getMainScreen7().getListeners().getRenderTime() < 900) MainScreen7.soosJSON.getOvvi_shortcake_dark().setVis(false);
                        else MainScreen7.soosJSON.getOvvi_shortcake_uncooked().setVis(false);

                        if(MainScreen7.aoos.getShortcake_dark().isVisible()) MainScreen7.aoos.getShortcake_dark().setVis(false);
                        else {
                            MainScreen7.soosJSON.getOven_shortcake_uncooked_opened_door().setVis(false);
                            MainScreen7.soosJSON.getOven_shortcake_uncooked_closed_door().setVis(true);
                        }
                    }
                    else if(MainScreen7.soosJSON.getOven_shortcake_light_opened_door().isVisible()){
                        MainScreen7.soosJSON.getOven_shortcake_light_opened_door().setVis(false);
                        MainScreen7.soosJSON.getOven_shortcake_light_closed_door().setVis(true);

                        if(MainScreen7.aoos.getShortcake_light().isVisible() | Cryptex.main.getMainScreen7().getListeners().getRenderTime() < 900) MainScreen7.soosJSON.getOvvi_shortcake_light().setVis(false);
                        else MainScreen7.soosJSON.getOvvi_shortcake_uncooked().setVis(false);

                        if(MainScreen7.aoos.getShortcake_light().isVisible()) MainScreen7.aoos.getShortcake_light().setVis(false);
                        else {
                            MainScreen7.soosJSON.getOven_shortcake_uncooked_opened_door().setVis(false);
                            MainScreen7.soosJSON.getOven_shortcake_uncooked_closed_door().setVis(true);
                        }
                    }
                    else if(MainScreen7.soosJSON.getOven_shortcake_uncooked_opened_door().isVisible()){
                        MainScreen7.soosJSON.getOven_shortcake_uncooked_opened_door().setVis(false);
                        MainScreen7.soosJSON.getOven_shortcake_uncooked_closed_door().setVis(true);
                        MainScreen7.soosJSON.getOvvi_shortcake_uncooked().setVis(false);
                    }
                }
            }
            if(name.equals("exit_oven")){
                Cryptex.main.getPanelStage().getClearScreenOverPanelActors().setVisible(true);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            synchronized (this) {
                                if(MainScreen7.soos.getOven_oven_on().isVisible()){
                                    Cryptex.main.getMainScreen7().getFlags().setFlagBlockSpanner(true);

                                    RotateToAction rta1 = new RotateToAction(), rta2 = new RotateToAction(), rta3 = new RotateToAction(), rta4 = new RotateToAction();
                                    RotateToAction[] rtaArr = new RotateToAction[]{rta1, rta2, rta3, rta4};

                                    for(int i = 0; i < rtaArr.length; i++){
                                        if(i == 2 | i == 3) rtaArr[i].setRotation(151);
                                        else rtaArr[i].setRotation(-20);
                                        rtaArr[i].setDuration(0.5f);
                                    }

                                    MainScreen7.soosJSON.getOven_spanner_for_oven().addAction(rta1);
                                    MainScreen7.soosJSON.getOven_spanner_for_oven_shadow().addAction(rta2);
                                    MainScreen7.soos.getOven_arrow().addAction(rta3);
                                    MainScreen7.soos.getOven_arrow_shadow().addAction(rta4);

                                    wait(500);

                                    if(MainScreen7.soos.getOven_shortcake_dark_oven_on().isVisible()){
                                        MainScreen7.soos.getOven_shortcake_dark_oven_on().setVisible(false);
                                        MainScreen7.soosJSON.getOven_shortcake_dark_closed_door().setVis(true);
                                        MainScreen7.soos.getOven_shortcake_uncooked_oven_on().setVis(false);
                                        MainScreen7.soosJSON.getOven_shortcake_uncooked_closed_door().setVis(true);
                                    }
                                    else if(MainScreen7.soos.getOven_shortcake_light_oven_on().isVisible()){
                                        MainScreen7.soos.getOven_shortcake_light_oven_on().setVisible(false);
                                        MainScreen7.soosJSON.getOven_shortcake_light_closed_door().setVis(true);
                                        MainScreen7.soos.getOven_shortcake_uncooked_oven_on().setVis(false);
                                        MainScreen7.soosJSON.getOven_shortcake_uncooked_closed_door().setVis(true);
                                    }

                                    MainScreen7.soos.getOven_oven_on().setVisible(false);
                                    Cryptex.main.getGlobalReferences().pauseMusic(Cryptex.main.getMainScreen7().getMusic().getOven_works());

                                    wait(200);

                                    Cryptex.main.getMainScreen7().getFlags().setFlagBlockSpanner(false);
                                }

                                stageTransition(Cryptex.main.getMainScreen7().getOvviActors(), Cryptex.main.getMainScreen7().getOvenActors());
                                Cryptex.main.getMainScreen7().getFlags().setFlagOvvi(true);
                                Cryptex.main.getMainScreen7().getFlags().setFlagOven(false);
                                Cryptex.main.getPanelStage().getClearScreenOverPanelActors().setVisible(false);
                            }
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                    }
                });
                thread.start();

            }

            return true;
        }
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            Cryptex.main.getMainScreen7().getListeners().setCounter(0);
        }
        @Override
        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            Cryptex.main.getMainScreen7().getListeners().setCounter(Cryptex.main.getMainScreen7().getListeners().getCounter() + 1);
            Cryptex.main.getMainScreen7().getListeners().setPreviousValue(Cryptex.main.getMainScreen7().getListeners().getCurrentValue());
            Cryptex.main.getMainScreen7().getListeners().setCurrentValue(y);
            Cryptex.main.getMainScreen7().getListeners().setDelta(Cryptex.main.getMainScreen7().getListeners().getCurrentValue() - Cryptex.main.getMainScreen7().getListeners().getPreviousValue());

            if(y > 260 & y < 475 & x > 550 & x < 700 & MainScreen7.soosJSON.getOven_oven_door_closed().isVisible() & MainScreen7.soosJSON.getOvvi_gas_cylinder_mounted().isVisible() &
                    MainScreen7.soosJSON.getOvvi_hose_mounted().isVisible() & !Cryptex.main.getMainScreen7().getFlags().getFlagBlockSpanner() & MainScreen7.soosJSON.getOven_spanner_for_oven().isVisible()) {
                if(MainScreen7.soosJSON.getOven_spanner_for_oven().getRotation() < -20 & !MainScreen7.soos.getOven_oven_on().isVisible() & !Cryptex.main.getPanelStage().getGroupTimer().isVisible()) {
                    MainScreen7.soos.getOven_oven_on().setVisible(true);
                    Cryptex.main.getGlobalReferences().playMusic(Cryptex.main.getMainScreen7().getMusic().getOven_works(), 0.1f, true);
                    if(Cryptex.main.getMainScreen7().getFlags().getFlagShortcakeInOven()) {
                        if (Cryptex.main.getMainScreen7().getFlags().getFlagCocoaDoughGot()) {
                            MainScreen7.soosJSON.getOven_shortcake_dark_closed_door().setVis(false);
                            MainScreen7.soos.getOven_shortcake_dark_oven_on().setVisible(true);
                            MainScreen7.soosJSON.getOven_shortcake_uncooked_closed_door().setVis(false);
                            MainScreen7.soos.getOven_shortcake_uncooked_oven_on().setVisible(true);
                        } else if (Cryptex.main.getMainScreen7().getFlags().getFlagDoughGot()) {
                            MainScreen7.soosJSON.getOven_shortcake_light_closed_door().setVis(false);
                            MainScreen7.soos.getOven_shortcake_light_oven_on().setVisible(true);
                            MainScreen7.soosJSON.getOven_shortcake_uncooked_closed_door().setVis(false);
                            MainScreen7.soos.getOven_shortcake_uncooked_oven_on().setVisible(true);
                        }
                    }
                }
                else if(MainScreen7.soosJSON.getOven_spanner_for_oven().getRotation() == -20 & MainScreen7.soos.getOven_oven_on().isVisible()) {
                    MainScreen7.soos.getOven_oven_on().setVisible(false);
                    Cryptex.main.getGlobalReferences().pauseMusic(Cryptex.main.getMainScreen7().getMusic().getOven_works());
                    if(Cryptex.main.getMainScreen7().getFlags().getFlagShortcakeInOven()) {
                        if (Cryptex.main.getMainScreen7().getFlags().getFlagCocoaDoughGot()) {
                            MainScreen7.soos.getOven_shortcake_dark_oven_on().setVisible(false);
                            MainScreen7.soosJSON.getOven_shortcake_dark_closed_door().setVis(true);
                            MainScreen7.soos.getOven_shortcake_uncooked_oven_on().setVisible(false);
                            MainScreen7.soosJSON.getOven_shortcake_uncooked_closed_door().setVis(true);
                        } else if (Cryptex.main.getMainScreen7().getFlags().getFlagDoughGot()) {
                            MainScreen7.soos.getOven_shortcake_light_oven_on().setVisible(false);
                            MainScreen7.soosJSON.getOven_shortcake_light_closed_door().setVis(true);
                            MainScreen7.soos.getOven_shortcake_uncooked_oven_on().setVisible(false);
                            MainScreen7.soosJSON.getOven_shortcake_uncooked_closed_door().setVis(true);
                        }
                    }
                }
                if(Cryptex.main.getMainScreen7().getListeners().getDelta() > 0 & MainScreen7.soosJSON.getOven_spanner_for_oven().getRotation() < -20){
                    playClick();

                    MainScreen7.soosJSON.getOven_spanner_for_oven().rotateBy(0.25f);
                    MainScreen7.soosJSON.getOven_spanner_for_oven_shadow().rotateBy(0.25f);
                    MainScreen7.soos.getOven_arrow().rotateBy(2f);
                    MainScreen7.soos.getOven_arrow_shadow().rotateBy(2f);

                }
                else if(Cryptex.main.getMainScreen7().getListeners().getDelta() < 0 & MainScreen7.soosJSON.getOven_spanner_for_oven().getRotation() > -58.5f){
                    playClick();

                    MainScreen7.soosJSON.getOven_spanner_for_oven().rotateBy(-0.25f);
                    MainScreen7.soosJSON.getOven_spanner_for_oven_shadow().rotateBy(-0.25f);
                    MainScreen7.soos.getOven_arrow().rotateBy(-2f);
                    MainScreen7.soos.getOven_arrow_shadow().rotateBy(-2f);
                }
            }
        }
    };

    //15
//ovvi
    private InputListener ovviIL = new InputListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            name = event.getListenerActor().getName();
            System.out.println("my name is: " + name);

            if(name.equals("ovvi_oven_overlap")){
                stageTransition(Cryptex.main.getMainScreen7().getOvenActors(), Cryptex.main.getMainScreen7().getOvviActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagOven(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagOvvi(false);
            }
            else if(name.equals("exit_ovvi")){
                stageTransition(Cryptex.main.getMainScreen7().getKitcActors(), Cryptex.main.getMainScreen7().getOvviActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagKitc(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagOvvi(false);
            }

            return true;
        }
    };

    //16
//rita
    private InputListener ritaIL = new InputListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            name = event.getListenerActor().getName();
            System.out.println("my name is: " + name);

            if(name.equals("rita_jars_overlap")){
                stageTransition(Cryptex.main.getMainScreen7().getJarsActors(), Cryptex.main.getMainScreen7().getRitaActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagJars(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagRita(false);
            }
            else if(name.equals("rita_mixe_overlap")){
                stageTransition(Cryptex.main.getMainScreen7().getMixeActors(), Cryptex.main.getMainScreen7().getRitaActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagMixe(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagRita(false);
            }
            else if(name.equals("rita_scal_overlap")){

                if(!Cryptex.main.getMainScreen7().getFlags().getFlagScaleFE()){
                    Cryptex.main.getMainScreen7().getFlags().setFlagScaleFE(true);
                    Cryptex.main.getJo().addIndexToAL("scale_FE_1", Cryptex.main.getGlobalReferences().getLevelNumber(), 3);
                }

                stageTransition(Cryptex.main.getMainScreen7().getScalActors(), Cryptex.main.getMainScreen7().getRitaActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagScal(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagRita(false);
            }
            else if(name.equals("rita_tusw_overlap")){
                stageTransition(Cryptex.main.getMainScreen7().getTuswActors(), Cryptex.main.getMainScreen7().getRitaActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagTusw(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagRita(false);
            }
            else if(name.equals("exit_rita")){
                stageTransition(Cryptex.main.getMainScreen7().getKitcActors(), Cryptex.main.getMainScreen7().getRitaActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagKitc(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagRita(false);
            }

            return true;
        }
    };

    //17
//scal
    private InputListener scalIL = new InputListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            name = event.getListenerActor().getName();
            System.out.println("my name is: " + name);
            if (name.equals("scal_smth_on_bowl_overlap")) {
                if (MainScreen7.soos.getScal_salt().isVisible() | MainScreen7.soos.getScal_sugar().isVisible() | MainScreen7.soos.getScal_yeast().isVisible() |
                        MainScreen7.soosJSON.getScal_flour().isVisible()) {
                    Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getTake_from_scales(), 0.8f);
                    if (MainScreen7.soos.getScal_sugar().isVisible()) {
                        Cryptex.main.getMainScreen7().getFlags().setFlagMeasuredSugarReceived(true);
                        if (Cryptex.main.getMainScreen7().getListeners().getMeasuredSugarValue1() == 0)
                            Cryptex.main.getMainScreen7().getListeners().setMeasuredSugarValue1(Cryptex.main.getMainScreen7().getListeners().getWeight());
                        else
                            Cryptex.main.getMainScreen7().getListeners().setMeasuredSugarValue2(Cryptex.main.getMainScreen7().getListeners().getWeight());
                        MainScreen7.soos.getScal_sugar().setVisible(false);
                        MainScreen7.soos.getScal_sugar().setPosition(280, 392);

                        setScalesToDefault();
                        getSmthFromScales("sugar_weighed");
                    } else if (MainScreen7.soos.getScal_salt().isVisible()) {
                        Cryptex.main.getMainScreen7().getFlags().setFlagMeasuredSaltReceived(true);
                        if (Cryptex.main.getMainScreen7().getListeners().getMeasuredSaltValue1() == 0)
                            Cryptex.main.getMainScreen7().getListeners().setMeasuredSaltValue1(Cryptex.main.getMainScreen7().getListeners().getWeight());
                        else
                            Cryptex.main.getMainScreen7().getListeners().setMeasuredSaltValue2(Cryptex.main.getMainScreen7().getListeners().getWeight());
                        MainScreen7.soos.getScal_salt().setVisible(false);
                        MainScreen7.soos.getScal_salt().setPosition(280, 392);

                        setScalesToDefault();
                        getSmthFromScales("salt_weighed");
                    } else if (MainScreen7.soos.getScal_yeast().isVisible()) {
                        Cryptex.main.getMainScreen7().getFlags().setFlagMeasuredYeastReceived(true);
                        if (Cryptex.main.getMainScreen7().getListeners().getMeasuredYeastValue1() == 0)
                            Cryptex.main.getMainScreen7().getListeners().setMeasuredYeastValue1(Cryptex.main.getMainScreen7().getListeners().getWeight());
                        else
                            Cryptex.main.getMainScreen7().getListeners().setMeasuredYeastValue2(Cryptex.main.getMainScreen7().getListeners().getWeight());
                        MainScreen7.soos.getScal_yeast().setVisible(false);
                        MainScreen7.soos.getScal_yeast().setPosition(263, 377);

                        setScalesToDefault();
                        getSmthFromScales("yeast_weighed");
                    } else if (MainScreen7.soosJSON.getScal_flour().isVisible()) {
                        Cryptex.main.getMainScreen7().getFlags().setFlagMeasuredFlourReceived(true);
                        if (Cryptex.main.getMainScreen7().getListeners().getMeasuredFlourValue1() == 0)
                            Cryptex.main.getMainScreen7().getListeners().setMeasuredFlourValue1(Cryptex.main.getMainScreen7().getListeners().getWeight());
                        else
                            Cryptex.main.getMainScreen7().getListeners().setMeasuredFlourValue2(Cryptex.main.getMainScreen7().getListeners().getWeight());
                        MainScreen7.soosJSON.getScal_flour().setVis(false);
                        MainScreen7.soosJSON.getScal_flour().setPosition(268, 392);

                        setScalesToDefault();
                        getSmthFromScales("flour_weighed");
                    }
                }
            }
            else if(name.equals("exit_scal")){
                Cryptex.main.getPanelStage().getClearScreenOverPanelActors().setVisible(true);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            synchronized (this) {
                                if(MainScreen7.soosJSON.getScal_scales_bowl().getY() < 323) {
                                    Cryptex.main.getMainScreen7().getListeners().setScalesToDefault();
                                    Cryptex.main.getMainScreen7().getListeners().setSmthOnScalesToDefault();
                                    wait(750);
                                }

                                stageTransition(Cryptex.main.getMainScreen7().getRitaActors(), Cryptex.main.getMainScreen7().getScalActors());
                                Cryptex.main.getMainScreen7().getFlags().setFlagRita(true);
                                Cryptex.main.getMainScreen7().getFlags().setFlagScal(false);
                                Cryptex.main.getPanelStage().getClearScreenOverPanelActors().setVisible(false);
                            }
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                    }
                });
                thread.start();
            }

            return true;
        }
    };

    //18
//tusw
    private InputListener tuswIL = new InputListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            name = event.getListenerActor().getName();
            System.out.println("my name is: " + name);

            Cryptex.main.getMainScreen7().getListeners().setTouchY(y);

            if(name.equals("exit_tusw")){
                stageTransition(Cryptex.main.getMainScreen7().getRitaActors(), Cryptex.main.getMainScreen7().getTuswActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagRita(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagTusw(false);
            }

            return true;
        }
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            Cryptex.main.getMainScreen7().getFlags().setFlagSwitchOn(false);
        }
        @Override
        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            Cryptex.main.getMainScreen7().getListeners().setPreviousValue(Cryptex.main.getMainScreen7().getListeners().getCurrentValue());
            Cryptex.main.getMainScreen7().getListeners().setCurrentValue(y);
            Cryptex.main.getMainScreen7().getListeners().setDelta(Cryptex.main.getMainScreen7().getListeners().getCurrentValue() - Cryptex.main.getMainScreen7().getListeners().getPreviousValue());

            if(y > 130 & y < 300 & x > 440 & x < 540 & !Cryptex.main.getMainScreen7().getFlags().getFlagSwitchOn() & !MainScreen7.soosJSON.getTusw_tape_on_switch().isVisible()) {
               if(Cryptex.main.getMainScreen7().getListeners().getDelta() > 0 & Cryptex.main.getMainScreen7().getListeners().getTouchY() < 187 & y > 190){
                   Cryptex.main.getPanelStage().getClearScreenOverPanelActors().setVisible(true);
                   Cryptex.main.getMainScreen7().getFlags().setFlagSwitchOn(true);
                   Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getTs_click(), 1);
                   Thread thread = new Thread(new Runnable() {
                       @Override
                       public void run() {
                           try {
                               synchronized (this) {
                                   MainScreen7.soosJSON.getTusw_switch_on().setVis(true);

                                   wait(250);

                                   Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getTs_click(), 1);
                                   MainScreen7.soosJSON.getTusw_switch_on().setVis(false);

                                   if(!Cryptex.main.getMainScreen7().getFlags().getFlagTuswFC()){
                                       Cryptex.main.getMainScreen7().getFlags().setFlagTuswFC(true);
                                       Cryptex.main.getJo().addIndexToAL("tusw_FC_1", Cryptex.main.getGlobalReferences().getLevelNumber(), 6);
                                   }
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
        }
    };

    //19
//cafe
    private InputListener cafeIL = new InputListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            name = event.getListenerActor().getName();
            System.out.println("my name is: " + name);

            if(name.equals("cafe_kien_overlap")){
                stageTransition(Cryptex.main.getMainScreen7().getKienActors(), Cryptex.main.getMainScreen7().getCafeActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagKien(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagCafe(false);
            }
            else if(name.equals("cafe_cars_overlap")){
                stageTransition(Cryptex.main.getMainScreen7().getCarsActors(), Cryptex.main.getMainScreen7().getCafeActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagCars(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagCafe(false);
            }
            else if(name.equals("cafe_wacl_overlap")){
                stageTransition(Cryptex.main.getMainScreen7().getWaclActors(), Cryptex.main.getMainScreen7().getCafeActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagWacl(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagCafe(false);
            }
            else if(name.equals("cafe_wadi_overlap")){

                if(!Cryptex.main.getMainScreen7().getFlags().getFlagWaitressFC()){
                    Cryptex.main.getMainScreen7().getFlags().setFlagWaitressFC(true);
                    Cryptex.main.getJo().addIndexToAL("waitress_FC_1", Cryptex.main.getGlobalReferences().getLevelNumber(), 8);
                }

                int counter = 0;

                for(int i = 0; i < MainScreen7.soosJSON.getDialogPhases().length; i++){
                    if(!MainScreen7.soosJSON.getDialogPhases()[i].isVisible()){
                        counter++;
                    }
                }

                if(counter == 8){
                    MainScreen7.soosJSON.getWadi_phase1().setVis(true);
                    enterWadiStage();
                }
                else if(MainScreen7.soosJSON.getWadi_phase1().isVisible()){
                    if(!MainScreen7.aoos.getRecipe1().isVisible() & !MainScreen7.aoos.getRecipe2().isVisible() & !MainScreen7.aoos.getRecipe3().isVisible()){
                        MainScreen7.soosJSON.getWadi_phase1().setVis(false);
                        MainScreen7.soosJSON.getWadi_phase2().setVis(true);
                        enterWadiStage();
                    }
                }
                else if(MainScreen7.soosJSON.getWadi_phase2().isVisible()){
                    if(!MainScreen7.soosJSON.getKiwt_pipe_broken().isVisible()){
                        MainScreen7.soosJSON.getWadi_phase2().setVis(false);
                        MainScreen7.soosJSON.getWadi_phase3().setVis(true);
                        enterWadiStage();
                    }
                }
                else if(MainScreen7.soosJSON.getWadi_phase3().isVisible()){
                    if(MainScreen7.soosJSON.getOvvi_gas_cylinder_mounted().isVisible() & MainScreen7.soosJSON.getOvvi_hose_mounted().isVisible()){

                        if(!Cryptex.main.getMainScreen7().getFlags().getFlagCakeTaskReceived()){
                            Cryptex.main.getMainScreen7().getFlags().setFlagCakeTaskReceived(true);
                            Cryptex.main.getJo().addIndexToAL("cake_task_received_1", Cryptex.main.getGlobalReferences().getLevelNumber(), 6);
                        }

                        MainScreen7.soosJSON.getWadi_phase3().setVis(false);
                        MainScreen7.soosJSON.getWadi_phase4().setVis(true);
                        enterWadiStage();
                    }
                }
                else if(MainScreen7.soosJSON.getWadi_phase5().isVisible()){

                    if(!Cryptex.main.getMainScreen7().getFlags().getFlagWaitressLastDialog()){
                        Cryptex.main.getMainScreen7().getFlags().setFlagWaitressLastDialog(true);
                        Cryptex.main.getJo().addIndexToAL("waitress_last_dialog_1", Cryptex.main.getGlobalReferences().getLevelNumber(), 4);
                    }

                    MainScreen7.soosJSON.getWadi_phase5().setVis(false);
                    MainScreen7.soosJSON.getWadi_phase6().setVis(true);
                    enterWadiStage();
                }
            }
            else if(name.equals("exit_cafe")){
                stageTransition(Cryptex.main.getMainScreen7().getCaenActors(), Cryptex.main.getMainScreen7().getCafeActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagCaen(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagCafe(false);
            }

            return true;
        }
    };

    //20
//caen
    private InputListener caenIL = new InputListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            name = event.getListenerActor().getName();
            System.out.println("my name is: " + name);

            if(name.equals("caen_cafe_overlap")){

                if(!Cryptex.main.getMainScreen7().getFlags().getFlagCafeFE()){
                    Cryptex.main.getMainScreen7().getFlags().setFlagCafeFE(true);
                    Cryptex.main.getJo().addIndexToAL("cafe_FE_1", Cryptex.main.getGlobalReferences().getLevelNumber(), 12);
                }

                stageTransition(Cryptex.main.getMainScreen7().getCafeActors(), Cryptex.main.getMainScreen7().getCaenActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagCafe(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagCaen(false);
            }
            else if(name.equals("caen_wata_overlap")){
                stageTransition(Cryptex.main.getMainScreen7().getWataActors(), Cryptex.main.getMainScreen7().getCaenActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagWata(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagCaen(false);
            }
            else if(name.equals("caen_exit_overlap") & MainScreen7.soosJSON.getWadi_phase6().isVisible()){
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            synchronized (this) {
                                Cryptex.main.getGlobalReferences().soundtrackFadeOut(Cryptex.main.getMainScreen7().getMusic().getTrack7(), 15, Cryptex.main.getMainScreen7().getMusic().getTrack7().getVolume());
                                Cryptex.main.getSaving().loadFlagToDefault();
                                Cryptex.main.getGlobalReferences().stageDarkeningWithPanel();
                                wait(2000);
                                Cryptex.main.getMainScreen7().getMusic().music7Dispose();
                                Cryptex.main.getGlobalReferences().setFlagRender(false);
                                Cryptex.main.getGlobalReferences().setFlagGoTo6CompLvl(true);
                            }
                        }
                        catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                    }
                });
                thread.start();
            }

            return true;
        }
    };

    //21
//cars
    private InputListener carsIL = new InputListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            name = event.getListenerActor().getName();
            System.out.println("my name is: " + name);

            if(name.equals("cars_futa_overlap")){
                stageTransition(Cryptex.main.getMainScreen7().getFutaActors(), Cryptex.main.getMainScreen7().getCarsActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagFuta(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagCars(false);
            }
            else if(name.equals("exit_cars")){
                stageTransition(Cryptex.main.getMainScreen7().getCafeActors(), Cryptex.main.getMainScreen7().getCarsActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagCafe(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagCars(false);
            }

            return true;
        }
    };

    //22
//kien
    private InputListener kienIL = new InputListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            name = event.getListenerActor().getName();
            System.out.println("my name is: " + name);

            if(name.equals("kien_bars_overlap")){
                stageTransition(Cryptex.main.getMainScreen7().getBarsActors(), Cryptex.main.getMainScreen7().getKienActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagBars(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagKien(false);
            }
            else if(name.equals("kien_kitc_overlap")){
                Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getDoor_opening(), 0.8f);
                stageTransition(Cryptex.main.getMainScreen7().getKitcActors(), Cryptex.main.getMainScreen7().getKienActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagKitc(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagKien(false);
            }
            else if(name.equals("exit_kien")){
                stageTransition(Cryptex.main.getMainScreen7().getCafeActors(), Cryptex.main.getMainScreen7().getKienActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagCafe(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagKien(false);
            }

            return true;
        }
    };

    //23
//wadi
    private InputListener wadiIL = new InputListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            name = event.getListenerActor().getName();
            System.out.println("my name is: " + name);

            Cryptex.main.getMainScreen7().getListeners().setPreviousValue(Cryptex.main.getMainScreen7().getListeners().getCurrentValue());
            Cryptex.main.getMainScreen7().getListeners().setCurrentValue(y);

            if(name.equals("exit_wadi")){
                stageTransition(Cryptex.main.getMainScreen7().getCafeActors(), Cryptex.main.getMainScreen7().getWadiActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagCafe(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagWadi(false);
            }

            return true;
        }
        @Override
        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            Cryptex.main.getMainScreen7().getListeners().setPreviousValue(Cryptex.main.getMainScreen7().getListeners().getCurrentValue());
            Cryptex.main.getMainScreen7().getListeners().setCurrentValue(y);
            Cryptex.main.getMainScreen7().getListeners().setDelta(Cryptex.main.getMainScreen7().getListeners().getCurrentValue() - Cryptex.main.getMainScreen7().getListeners().getPreviousValue());

            if(y > 65 & y < 412 & x > 395 & x < 738) {
                if(MainScreen7.soosJSON.getWadi_phase1().isVisible()) {
                    Actor phase1 = MainScreen7.soosJSON.getWadi_phase1();
                    if (Cryptex.main.getMainScreen7().getListeners().getDelta() < 0 & (phase1.getY() + Cryptex.main.getMainScreen7().getListeners().getDelta()) > -93) {
                        phase1.moveBy(0, Cryptex.main.getMainScreen7().getListeners().getDelta());
                    }
                    else if (Cryptex.main.getMainScreen7().getListeners().getDelta() > 0 & (phase1.getY() + Cryptex.main.getMainScreen7().getListeners().getDelta()) < 110) {
                        phase1.moveBy(0, Cryptex.main.getMainScreen7().getListeners().getDelta());
                    }
                }
                else if(MainScreen7.soosJSON.getWadi_phase3().isVisible()) {
                    Actor phase3 = MainScreen7.soosJSON.getWadi_phase3();
                    if (Cryptex.main.getMainScreen7().getListeners().getDelta() < 0 & (phase3.getY() + Cryptex.main.getMainScreen7().getListeners().getDelta()) > 59) {
                        phase3.moveBy(0, Cryptex.main.getMainScreen7().getListeners().getDelta());
                    }
                    else if (Cryptex.main.getMainScreen7().getListeners().getDelta() > 0 & (phase3.getY() + Cryptex.main.getMainScreen7().getListeners().getDelta()) < 110) {
                        phase3.moveBy(0, Cryptex.main.getMainScreen7().getListeners().getDelta());
                    }
                }
                else if(MainScreen7.soosJSON.getWadi_phase4().isVisible()) {
                    Actor phase4 = MainScreen7.soosJSON.getWadi_phase4();
                    if (Cryptex.main.getMainScreen7().getListeners().getDelta() < 0 & (phase4.getY() + Cryptex.main.getMainScreen7().getListeners().getDelta()) > 96) {
                        phase4.moveBy(0, Cryptex.main.getMainScreen7().getListeners().getDelta());
                    }
                    else if (Cryptex.main.getMainScreen7().getListeners().getDelta() > 0 & (phase4.getY() + Cryptex.main.getMainScreen7().getListeners().getDelta()) < 110) {
                        phase4.moveBy(0, Cryptex.main.getMainScreen7().getListeners().getDelta());
                    }
                }
                else if(MainScreen7.soosJSON.getWadi_phase4_1().isVisible()) {
                    Actor phase4_1 = MainScreen7.soosJSON.getWadi_phase4_1();
                    if (Cryptex.main.getMainScreen7().getListeners().getDelta() < 0 & (phase4_1.getY() + Cryptex.main.getMainScreen7().getListeners().getDelta()) > 60) {
                        phase4_1.moveBy(0, Cryptex.main.getMainScreen7().getListeners().getDelta());
                    }
                    else if (Cryptex.main.getMainScreen7().getListeners().getDelta() > 0 & (phase4_1.getY() + Cryptex.main.getMainScreen7().getListeners().getDelta()) < 110) {
                        phase4_1.moveBy(0, Cryptex.main.getMainScreen7().getListeners().getDelta());
                    }
                }
                else if(MainScreen7.soosJSON.getWadi_phase4_2().isVisible()) {
                    Actor phase4_2 = MainScreen7.soosJSON.getWadi_phase4_2();
                    if (Cryptex.main.getMainScreen7().getListeners().getDelta() < 0 & (phase4_2.getY() + Cryptex.main.getMainScreen7().getListeners().getDelta()) > -93) {
                        phase4_2.moveBy(0, Cryptex.main.getMainScreen7().getListeners().getDelta());
                    }
                    else if (Cryptex.main.getMainScreen7().getListeners().getDelta() > 0 & (phase4_2.getY() + Cryptex.main.getMainScreen7().getListeners().getDelta()) < 110) {
                        phase4_2.moveBy(0, Cryptex.main.getMainScreen7().getListeners().getDelta());
                    }
                }
                else if(MainScreen7.soosJSON.getWadi_phase6().isVisible()) {
                    Actor phase6 = MainScreen7.soosJSON.getWadi_phase6();
                    if (Cryptex.main.getMainScreen7().getListeners().getDelta() < 0 & (phase6.getY() + Cryptex.main.getMainScreen7().getListeners().getDelta()) > -435) {
                        phase6.moveBy(0, Cryptex.main.getMainScreen7().getListeners().getDelta());
                    }
                    else if (Cryptex.main.getMainScreen7().getListeners().getDelta() > 0 & (phase6.getY() + Cryptex.main.getMainScreen7().getListeners().getDelta()) < 110) {
                        phase6.moveBy(0, Cryptex.main.getMainScreen7().getListeners().getDelta());
                    }
                }
            }
        }
    };

    //24
//wata
    private InputListener wataIL = new InputListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            name = event.getListenerActor().getName();
            System.out.println("my name is: " + name);

            if(name.equals("exit_wata")){
                stageTransition(Cryptex.main.getMainScreen7().getCaenActors(), Cryptex.main.getMainScreen7().getWataActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagCaen(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagWata(false);
            }

            return true;
        }
    };

    //25
//sink
    private InputListener sinkIL = new InputListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            name = event.getListenerActor().getName();
            System.out.println("my name is: " + name);

            if(name.equals("exit_sink")){
                stageTransition(Cryptex.main.getMainScreen7().getWaclActors(), Cryptex.main.getMainScreen7().getSinkActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagWacl(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagSink(false);
            }

            return true;
        }
    };

    //26
//toil
    private InputListener toilIL = new InputListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            name = event.getListenerActor().getName();
            System.out.println("my name is: " + name);

            if(name.equals("exit_toil")){
                stageTransition(Cryptex.main.getMainScreen7().getWaclActors(), Cryptex.main.getMainScreen7().getToilActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagWacl(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagToil(false);
            }

            return true;
        }
    };

    //27
//wacl
    private InputListener waclIL = new InputListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            name = event.getListenerActor().getName();
            System.out.println("my name is: " + name);

            if(name.equals("wacl_sink_overlap")){
                stageTransition(Cryptex.main.getMainScreen7().getSinkActors(), Cryptex.main.getMainScreen7().getWaclActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagSink(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagWacl(false);
            }
            else if(name.equals("wacl_toil_overlap")){
                stageTransition(Cryptex.main.getMainScreen7().getToilActors(), Cryptex.main.getMainScreen7().getWaclActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagToil(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagWacl(false);
            }
            else if(name.equals("wacl_wapi_overlap")){
                Cryptex.main.getMainScreen7().getListeners().setCurrentFrameNumber(0);
                stageTransition(Cryptex.main.getMainScreen7().getWapiActors(), Cryptex.main.getMainScreen7().getWaclActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagWapi(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagWacl(false);
            }
            else if(name.equals("exit_wacl")){
                stageTransition(Cryptex.main.getMainScreen7().getCafeActors(), Cryptex.main.getMainScreen7().getWaclActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagCafe(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagWacl(false);
            }

            return true;
        }
    };

    //28
//wapi
    private InputListener wapiIL = new InputListener(){
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            name = event.getListenerActor().getName();
            System.out.println("my name is: " + name);

            if(name.equals("exit_wapi")){
                stageTransition(Cryptex.main.getMainScreen7().getWaclActors(), Cryptex.main.getMainScreen7().getWapiActors());
                Cryptex.main.getMainScreen7().getFlags().setFlagWacl(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagWapi(false);
            }

            return true;
        }
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            Cryptex.main.getMainScreen7().getListeners().setCounter(0);
        }
        @Override
        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            if(x > 185 & x < 385 & y > 175 & y < 375 & Cryptex.main.getMainScreen7().getFlags().getFlagTapValveMounted()){
                Cryptex.main.getMainScreen7().getListeners().setDeltaX(x - 285);
                Cryptex.main.getMainScreen7().getListeners().setDeltaY(y - 275);
                Cryptex.main.getMainScreen7().getListeners().setCounter(Cryptex.main.getMainScreen7().getListeners().getCounter() + 1);

                if (x > 285 & y > 275) {
                    Cryptex.main.getMainScreen7().getListeners().setAngle(270 + (float) (Math.atan(Cryptex.main.getMainScreen7().getListeners().getDeltaY() / Cryptex.main.getMainScreen7().getListeners().getDeltaX()) * 180 / 3.1456));
                    rotateTapValve();
                } else if (x < 285 & y > 275) {
                    Cryptex.main.getMainScreen7().getListeners().setAngle((float) (Math.atan(-Cryptex.main.getMainScreen7().getListeners().getDeltaX() / Cryptex.main.getMainScreen7().getListeners().getDeltaY()) * 180 / 3.1456));
                    rotateTapValve();
                } else if (x < 285 & y < 275) {
                    Cryptex.main.getMainScreen7().getListeners().setAngle(90 + (float) (Math.atan(-Cryptex.main.getMainScreen7().getListeners().getDeltaY() / -Cryptex.main.getMainScreen7().getListeners().getDeltaX()) * 180 / 3.1456));
                    rotateTapValve();
                } else if (x > 285 & y < 275) {
                    Cryptex.main.getMainScreen7().getListeners().setAngle(180 + (float) (Math.atan(Cryptex.main.getMainScreen7().getListeners().getDeltaX() / -Cryptex.main.getMainScreen7().getListeners().getDeltaY()) * 180 / 3.1456));
                    rotateTapValve();
                }
            }
        }
    };

    void playClick (){
        valveRotation2 = MainScreen7.soosJSON.getOven_spanner_for_oven().getRotation();

        if(Math.abs(valveRotation2 - valveRotation1) > 2/* & Cryptex.main.getMainScreen7().getListeners().getValve().getRotation() != -816 &
                Cryptex.main.getMainScreen7().getListeners().getValve().getRotation() != 3*/){
            Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getSpanner_rotation(), 0.7f);
            valveRotation1 = valveRotation2;
        }
    }

    public void getSmthFromScales(String name){
        Cryptex.main.getActiveListener().addObjectToInventory(Cryptex.main.getActiveObjectsInI().getActorInIByName(name + "_InI"));
        Cryptex.main.getActiveListener().showBlackBackground();
        Cryptex.main.getActiveListener().showBigObject(name, "inv");
    }

    public void setSmthOnScalesToDefault(){
        MainScreen7.soosJSON.getScal_flour().setVis(false);
        MainScreen7.soosJSON.getScal_flour().setPosition(268, 392);
        MainScreen7.soos.getScal_yeast().setVisible(false);
        MainScreen7.soos.getScal_yeast().setPosition(263, 377);
        MainScreen7.soos.getScal_salt().setVisible(false);
        MainScreen7.soos.getScal_salt().setPosition(280, 392);
        MainScreen7.soos.getScal_sugar().setVisible(false);
        MainScreen7.soos.getScal_sugar().setPosition(280, 392);
    }

    public void setScalesToDefault(){
        Cryptex.main.getMainScreen7().getListeners().setWeight(0);

        MoveToAction mta = new MoveToAction();
        mta.setPosition(243, 323);
        mta.setDuration(0.5f);

        RotateToAction rta = new RotateToAction();
        rta.setRotation(39f);
        rta.setDuration(0.5f);

        RotateToAction rta2 = new RotateToAction();
        rta2.setRotation(39f);
        rta2.setDuration(0.5f);

        MainScreen7.soosJSON.getScal_scales_bowl().addAction(mta);
        MainScreen7.soosJSON.getScal_scales_arrow().addAction(rta);
        MainScreen7.soosJSON.getScal_scales_arrow_shadow().addAction(rta2);
    }

    public void enterWadiStage(){
        stageTransition(Cryptex.main.getMainScreen7().getWadiActors(), Cryptex.main.getMainScreen7().getCafeActors());
        Cryptex.main.getMainScreen7().getFlags().setFlagWadi(true);
        Cryptex.main.getMainScreen7().getFlags().setFlagCafe(false);
    }

    public void putBeakerOnSink(float x, float y){
        if (x > 300 & x < 400 & y > 100 & y < 200) {
            Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getPut_glass(), 0.7f);
            MainScreen7.soosJSON.getKiwt_beaker().setVis(true);
            MainScreen7.soosJSON.getKitc_beaker().setVis(true);

            Cryptex.main.getPanelStage().getGroupActiveItem().findActor("beaker_InI").remove();
            Cryptex.main.getGlobalReferences().setFlagActiveCellOccupied(false);
        } else {
            Cryptex.main.getActiveListener().moveActiveItemToDefault();
        }
    }

    public void emptyBeaker(float x, float y){
        if (x > 340 & x < 600 & y > 80 & y < 220) {
            Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getWater_pour_out(), 0.8f);
            Cryptex.main.getActiveListener().addObjectToInventory(Cryptex.main.getActiveObjectsInI().getActorInIByName("beaker_InI"));
            Cryptex.main.getMainScreen7().getFlags().setFlagBeakerFilled(false);

            if(Cryptex.main.getMainScreen7().getListeners().getWaterLevelInBeaker1() > 0 & Cryptex.main.getMainScreen7().getListeners().getWaterLevelInBeaker2() > 0) Cryptex.main.getMainScreen7().getListeners().setWaterLevelInBeaker2(0);
            else Cryptex.main.getMainScreen7().getListeners().setWaterLevelInBeaker1(0);

            Cryptex.main.getPanelStage().getGroupActiveItem().findActor("beaker_filled_InI").remove();
            Cryptex.main.getGlobalReferences().setFlagActiveCellOccupied(false);

            Cryptex.main.getCubeStage().getCube_icon().setVisible(false);
        } else {
            Cryptex.main.getActiveListener().moveActiveItemToDefault();
        }
    }

    public void stickTapeOnSwitch(float x, float y){
        if (x > 354 & x < 554 & y > 82 & y < 282) {
            Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getStick_tape(), 1);
            Cryptex.main.getMainScreen7().getFlags().setFlagElectricityOn(true);

            MainScreen7.soosJSON.getTusw_switch_on().setVis(true);
            MainScreen7.soosJSON.getTusw_tape_on_switch().setVis(true);
            MainScreen7.soosJSON.getRita_tape_on_switch().setVis(true);

            Cryptex.main.getPanelStage().getGroupActiveItem().findActor("insulation_tape_InI").remove();
            Cryptex.main.getGlobalReferences().setFlagActiveCellOccupied(false);
        } else {
            Cryptex.main.getActiveListener().moveActiveItemToDefault();
        }
    }

    public void fillBowlWithSugar(float x, float y){
        if (x > 212 & x < 528 & y > 335 & y < 460 & !MainScreen7.soos.getScal_salt().isVisible() & !MainScreen7.soos.getScal_yeast().isVisible() &
                !MainScreen7.soosJSON.getScal_flour().isVisible() & MainScreen7.soos.getScal_sugar().getY() < 403 & !Cryptex.main.getMainScreen7().getFlags().getFlagMeasuredSugarReceived()) {
            MainScreen7.soos.getScal_sugar().setVisible(true);
            MainScreen7.soos.getScal_sugar().moveBy(0, 1);
            putSmthOnScales();
        } else {
            Cryptex.main.getActiveListener().moveActiveItemToDefault();
        }
    }

    public void fillBowlWithSalt(float x, float y){
        if (x > 212 & x < 528 & y > 335 & y < 460 & !MainScreen7.soos.getScal_sugar().isVisible() & !MainScreen7.soos.getScal_yeast().isVisible() &
                !MainScreen7.soosJSON.getScal_flour().isVisible() & MainScreen7.soos.getScal_salt().getY() < 403 & !Cryptex.main.getMainScreen7().getFlags().getFlagMeasuredSaltReceived()) {
            MainScreen7.soos.getScal_salt().setVisible(true);
            MainScreen7.soos.getScal_salt().moveBy(0, 1);
            putSmthOnScales();
        } else {
            Cryptex.main.getActiveListener().moveActiveItemToDefault();
        }
    }

    public void fillBowlWithFlour(float x, float y){
        if (x > 212 & x < 528 & y > 335 & y < 460 & !MainScreen7.soos.getScal_sugar().isVisible() & !MainScreen7.soos.getScal_yeast().isVisible() &
                !MainScreen7.soos.getScal_salt().isVisible() & MainScreen7.soosJSON.getScal_flour().getY() < 403 & !Cryptex.main.getMainScreen7().getFlags().getFlagMeasuredFlourReceived()) {
            MainScreen7.soosJSON.getScal_flour().setVis(true);
            MainScreen7.soosJSON.getScal_flour().moveBy(0, 1);
            putSmthOnScales();
        } else {
            Cryptex.main.getActiveListener().moveActiveItemToDefault();
        }
    }

    public void fillBowlWithYeast(float x, float y){
        if (x > 212 & x < 528 & y > 335 & y < 460 & !MainScreen7.soos.getScal_sugar().isVisible() & !MainScreen7.soosJSON.getScal_flour().isVisible() &
                !MainScreen7.soos.getScal_salt().isVisible() & MainScreen7.soos.getScal_yeast().getY() < 388 & !Cryptex.main.getMainScreen7().getFlags().getFlagMeasuredYeastReceived()) {
            MainScreen7.soos.getScal_yeast().setVisible(true);
            MainScreen7.soos.getScal_yeast().moveBy(0, 1);
            putSmthOnScales();
        } else {
            Cryptex.main.getActiveListener().moveActiveItemToDefault();
        }
    }

    public void putSmthOnScales(){
        Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getPut_on_scales(), 0.5f);
        MainScreen7.soosJSON.getScal_scales_bowl().moveBy(0, -1);

        RotateByAction rba1 = new RotateByAction();
        rba1.setAmount(-30);
        rba1.setDuration(0.25f);

        RotateByAction rba2 = new RotateByAction();
        rba2.setAmount(-30);
        rba2.setDuration(0.25f);

        MainScreen7.soosJSON.getScal_scales_arrow().addAction(rba1);
        MainScreen7.soosJSON.getScal_scales_arrow_shadow().addAction(rba2);

        Cryptex.main.getMainScreen7().getListeners().setWeight(Cryptex.main.getMainScreen7().getListeners().getWeight() + 20);

        Cryptex.main.getActiveListener().moveActiveItemToDefault();
    }

    public void mountHose(String name, float x, float y){
        if (x > 395 & x < 542 & y > 72 & y < 290) {
            Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getMount_hose(), 0.8f);
            MainScreen7.soosJSON.getOvvi_hose_mounted().setVis(true);
            MainScreen7.soosJSON.getKitc_hose_mounted().setVis(true);

            if(name.equals("hose_lighted_InI")) Cryptex.main.getPanelStage().getGroupActiveItem().findActor("hose_lighted_InI").remove();
            else Cryptex.main.getPanelStage().getGroupActiveItem().findActor("hose_unlighted_InI").remove();
            Cryptex.main.getGlobalReferences().setFlagActiveCellOccupied(false);
        } else {
            Cryptex.main.getActiveListener().moveActiveItemToDefault();
        }
    }

    public void mountGasCylinder(float x, float y){
        if (x > -13 & x < 342 & y > 78 & y < 404) {
            Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getPut_gas_cylinder(), 1);
            MainScreen7.soosJSON.getOvvi_gas_cylinder_mounted().setVis(true);
            MainScreen7.soosJSON.getKitc_gas_cylinder_mounted().setVis(true);

            Cryptex.main.getPanelStage().getGroupActiveItem().findActor("gas_cylinder_InI").remove();
            Cryptex.main.getGlobalReferences().setFlagActiveCellOccupied(false);
        } else {
            Cryptex.main.getActiveListener().moveActiveItemToDefault();
        }
    }

    public void mountPipe(float x, float y){
        if (x > -35 & x < 415 & y > 265 & y < 385) {
            Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getPipe_changing(), 1);
            MainScreen7.soosJSON.getKiwt_pipe_broken().setVis(false);
            MainScreen7.soosJSON.getKiwt_pipe_mounted().setVis(true);
            MainScreen7.soosJSON.getKitc_pipe_mounted().setVis(true);

            Cryptex.main.getPanelStage().getGroupActiveItem().findActor("pipe_InI").remove();
            Cryptex.main.getGlobalReferences().setFlagActiveCellOccupied(false);
        } else {
            Cryptex.main.getActiveListener().moveActiveItemToDefault();
        }
    }

    public void giveCakeToWaitress(float x, float y){
        if (x > 200 & x < 300 & y > -15 & y < 280 &
                (MainScreen7.soosJSON.getWadi_phase4().isVisible() | MainScreen7.soosJSON.getWadi_phase4_1().isVisible() | MainScreen7.soosJSON.getWadi_phase4_2().isVisible())) {
            Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getGive_item(), 1);
            if(MainScreen7.soosJSON.getWadi_phase4().isVisible()) MainScreen7.soosJSON.getWadi_phase4().setVis(false);
            else if(MainScreen7.soosJSON.getWadi_phase4_1().isVisible()) MainScreen7.soosJSON.getWadi_phase4_1().setVis(false);
            else if(MainScreen7.soosJSON.getWadi_phase4_2().isVisible()) MainScreen7.soosJSON.getWadi_phase4_2().setVis(false);

            if(Cryptex.main.getMainScreen7().getFlags().getFlag1RecipeUsed() | Cryptex.main.getMainScreen7().getFlags().getFlag3RecipeUsed()){

                if(!Cryptex.main.getMainScreen7().getFlags().getFlagWrongRecipe()){
                    Cryptex.main.getMainScreen7().getFlags().setFlagWrongRecipe(true);
                    Cryptex.main.getJo().addIndexToAL("wrong_recipe_1", Cryptex.main.getGlobalReferences().getLevelNumber(), 7);
                }

                MainScreen7.soosJSON.getWadi_phase4_1().setVis(true);
            }
            else if(Cryptex.main.getMainScreen7().getListeners().getWaterLevelInBeaker1() == 100 & Cryptex.main.getMainScreen7().getListeners().getWaterLevelInBeaker2() == 100 &
                    Cryptex.main.getMainScreen7().getListeners().getMeasuredYeastValue1() == 20 & Cryptex.main.getMainScreen7().getListeners().getMeasuredFlourValue1() == 220 &
                    Cryptex.main.getMainScreen7().getListeners().getMeasuredYeastValue2() == 20 & Cryptex.main.getMainScreen7().getListeners().getMeasuredFlourValue2() == 220 &
                    Cryptex.main.getMainScreen7().getFlags().getFlagPinkUnmixedCreamGot() & Cryptex.main.getActiveObjectsInI().getCake_decoration_kiwis_InI().isVisible() &
                    Cryptex.main.getActiveObjectsInI().getCake_decoration_strawberries_InI().isVisible() & Cryptex.main.getMainScreen7().getFlags().getFlagFirstShortcakeIsLight()){
                MainScreen7.soosJSON.getWadi_phase5().setVis(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagCakeIsDone(true);

                if(!Cryptex.main.getMainScreen7().getFlags().getFlagCakeGiven()){
                    Cryptex.main.getMainScreen7().getFlags().setFlagCakeGiven(true);
                    Cryptex.main.getJo().addIndexToAL("cake_given_1", Cryptex.main.getGlobalReferences().getLevelNumber(), 4);
                }
            }
            else{

                if(!Cryptex.main.getMainScreen7().getFlags().getFlagWrongIngr()){
                    Cryptex.main.getMainScreen7().getFlags().setFlagWrongIngr(true);
                    Cryptex.main.getJo().addIndexToAL("wrong_ingr_1", Cryptex.main.getGlobalReferences().getLevelNumber(), 5);
                }

                MainScreen7.soosJSON.getWadi_phase4_2().setVis(true);
            }

            enterWadiStage();

            Cryptex.main.getPanelStage().getGroupActiveItem().findActor("cake_InI").remove();
            Cryptex.main.getGlobalReferences().setFlagActiveCellOccupied(false);

            if(!MainScreen7.soosJSON.getWadi_phase5().isVisible()) {
                setCakeValuesToDefault();

                for (int i = 0; i < Cryptex.main.getMainScreen7().getFlags().getArrCakeSetters().length; i++) {
                    Cryptex.main.getMainScreen7().getFlags().setCakeSetter(i, false);
                }

                for (int i = 0; i < Cryptex.main.getActiveObjectsInI().getCake().length; i++) {
                    Cryptex.main.getActiveObjectsInI().getCake()[i].setVis(false);
                    //Cryptex.main.getActiveObjectsBIG().getCake()[i].setVis(false);
                }
            }
            else {
                for(int i = 0; i < Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().size; i++){
                    String name = Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().get(i).getName();
                    if(name.startsWith("beak") | name.startsWith("berr") | (name.startsWith("butter") & !name.equals("butterfly_InI")) | name.startsWith("coco") | name.startsWith("cream") |
                            name.startsWith("dough") | name.startsWith("egg") | name.startsWith("flour") | name.startsWith("kitch") | name.startsWith("kiwi") |
                            name.startsWith("milk") | name.startsWith("orang") | name.startsWith("roll") | name.startsWith("salt") | name.startsWith("short") |
                            name.startsWith("straw") | name.startsWith("sugar") | name.startsWith("yeast") | name.startsWith("yellow") | name.startsWith("jelly") |
                            name.startsWith("recipe")){
                        Cryptex.main.getActiveListener().addThisToActiveCell(Cryptex.main.getPanelStage().getGroupObjectsInInventory().getChildren().get(i));
                        Cryptex.main.getPanelStage().getGroupActiveItem().getChildren().get(0).remove();
                        Cryptex.main.getGlobalReferences().setFlagActiveCellOccupied(false);
                        i -= 1;
                    }
                }

                disableCakeActivity();
            }
        } else {
            Cryptex.main.getActiveListener().moveActiveItemToDefault();
        }
    }

    public void mountTapValve(float x, float y){
        if (x > 188 & x < 288 & y > 188 & y < 288) {
            Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getMount_valve(), 1);
            MainScreen7.soosJSON.getTava_frame1().setVis(true);
            MainScreen7.soosJSON.getWacl_tap_valve().setVis(true);
            Cryptex.main.getMainScreen7().getFlags().setFlagTapValveMounted(true);

            Cryptex.main.getPanelStage().getGroupActiveItem().findActor("tap_valve_InI").remove();
            Cryptex.main.getGlobalReferences().setFlagActiveCellOccupied(false);
        } else {
            Cryptex.main.getActiveListener().moveActiveItemToDefault();
        }
    }

    public void putUnmixedCreamIntoMixer(String name, float x, float y){
        if (x > 315 & x < 625 & y > 90 & y < 260) {
            MainScreen7.soosJSON.getMixe_cream_unmixed().setVis(true);

            if(name.equals("cream_white_unmixed_InI")) Cryptex.main.getPanelStage().getGroupActiveItem().findActor("cream_white_unmixed_InI").remove();
            else Cryptex.main.getPanelStage().getGroupActiveItem().findActor("cream_pink_unmixed_InI").remove();
            Cryptex.main.getGlobalReferences().setFlagActiveCellOccupied(false);
        } else {
            Cryptex.main.getActiveListener().moveActiveItemToDefault();
        }
    }

    public void makeFirstLayer(String name, float x, float y){
        if (x > 190 & x < 535 & y > 30 & y < 285) {
            Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getPut_shortcake(), 1);
            if(name.equals("shortcake_dark_InI")){
                MainScreen7.soosJSON.getCake_shortcake_lower_dark().setVis(true);
                MainScreen7.soosJSON.getCeta_shortcake_lower_dark().setVis(true);
                Cryptex.main.getActiveObjectsInI().getCake_shortcake_lower_dark_InI().setVis(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagCake_shortcake_lower_dark_BIG(true);
            }
            else {
                Cryptex.main.getMainScreen7().getFlags().setFlagFirstShortcakeIsLight(true);
                MainScreen7.soosJSON.getCake_shortcake_lower_light().setVis(true);
                MainScreen7.soosJSON.getCeta_shortcake_lower_light().setVis(true);
                Cryptex.main.getActiveObjectsInI().getCake_shortcake_lower_light_InI().setVis(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagCake_shortcake_lower_light_BIG(true);
            }

            if(name.equals("shortcake_dark_InI")) Cryptex.main.getPanelStage().getGroupActiveItem().findActor("shortcake_dark_InI").remove();
            else Cryptex.main.getPanelStage().getGroupActiveItem().findActor("shortcake_light_InI").remove();
            Cryptex.main.getGlobalReferences().setFlagActiveCellOccupied(false);
        } else {
            Cryptex.main.getActiveListener().moveActiveItemToDefault();
        }
    }

    public void makeSecondLayer(String name, float x, float y){
        if (x > 190 & x < 535 & y > 30 & y < 285) {
            Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getPut_cream(), 0.8f);
            if(name.equals("cream_pink_InI")){
                MainScreen7.soosJSON.getCake_cream_lower_pink().setVis(true);
                MainScreen7.soosJSON.getCeta_cream_lower_pink().setVis(true);
                Cryptex.main.getActiveObjectsInI().getCake_cream_lower_pink_InI().setVis(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagCake_cream_lower_pink_BIG(true);
            }
            else {
                MainScreen7.soosJSON.getCake_cream_lower_white().setVis(true);
                MainScreen7.soosJSON.getCeta_cream_lower_white().setVis(true);
                Cryptex.main.getActiveObjectsInI().getCake_cream_lower_white_InI().setVis(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagCake_cream_lower_white_BIG(true);
            }
            Cryptex.main.getActiveListener().moveActiveItemToDefault();
        } else {
            Cryptex.main.getActiveListener().moveActiveItemToDefault();
        }
    }

    public void makeThirdLayer(String name, float x, float y){
        if (x > 190 & x < 535 & y > 30 & y < 285) {
            Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getPut_shortcake(), 1);
            if(name.equals("shortcake_dark_InI")){
                MainScreen7.soosJSON.getCake_shortcake_upper_dark().setVis(true);
                MainScreen7.soosJSON.getCeta_shortcake_upper_dark().setVis(true);
                Cryptex.main.getActiveObjectsInI().getCake_shortcake_upper_dark_InI().setVis(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagCake_shortcake_upper_dark_BIG(true);
            }
            else {
                MainScreen7.soosJSON.getCake_shortcake_upper_light().setVis(true);
                MainScreen7.soosJSON.getCeta_shortcake_upper_light().setVis(true);
                Cryptex.main.getActiveObjectsInI().getCake_shortcake_upper_light_InI().setVis(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagCake_shortcake_upper_light_BIG(true);
            }

            if(name.equals("shortcake_dark_InI")) Cryptex.main.getPanelStage().getGroupActiveItem().findActor("shortcake_dark_InI").remove();
            else Cryptex.main.getPanelStage().getGroupActiveItem().findActor("shortcake_light_InI").remove();
            Cryptex.main.getGlobalReferences().setFlagActiveCellOccupied(false);
        } else {
            Cryptex.main.getActiveListener().moveActiveItemToDefault();
        }
    }

    public void makeFourthLayer(String name, float x, float y){
        if (x > 190 & x < 535 & y > 30 & y < 285) {
            Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getPut_cream(), 0.8f);
            if(name.equals("cream_pink_InI")){
                MainScreen7.soosJSON.getCake_cream_upper_pink().setVis(true);
                MainScreen7.soosJSON.getCeta_cream_upper_pink().setVis(true);
                Cryptex.main.getActiveObjectsInI().getCake_cream_upper_pink_InI().setVis(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagCake_cream_upper_pink_BIG(true);
            }
            else {
                MainScreen7.soosJSON.getCake_cream_upper_white().setVis(true);
                MainScreen7.soosJSON.getCeta_cream_upper_white().setVis(true);
                Cryptex.main.getActiveObjectsInI().getCake_cream_upper_white_InI().setVis(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagCake_cream_upper_white_BIG(true);
            }

            if(name.equals("cream_pink_InI")) Cryptex.main.getPanelStage().getGroupActiveItem().findActor("cream_pink_InI").remove();
            else Cryptex.main.getPanelStage().getGroupActiveItem().findActor("cream_white_InI").remove();
            Cryptex.main.getGlobalReferences().setFlagActiveCellOccupied(false);
        } else {
            Cryptex.main.getActiveListener().moveActiveItemToDefault();
        }
    }

    public void makeDecoration(String name, float x, float y){
        if (x > 190 & x < 535 & y > 30 & y < 285) {
            Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getPut_shortcake(), 1);
            if(name.equals("berries_InI") & !MainScreen7.soosJSON.getCake_decoration_berries().isVisible()){
                MainScreen7.soosJSON.getCake_decoration_berries().setVis(true);
                MainScreen7.soosJSON.getCeta_decoration_berries().setVis(true);
                Cryptex.main.getActiveObjectsInI().getCake_decoration_berries_InI().setVis(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagCake_decoration_berries_BIG(true);
            }
            else if(name.equals("kiwis_cut_InI") & !MainScreen7.soosJSON.getCake_decoration_kiwis().isVisible()){
                MainScreen7.soosJSON.getCake_decoration_kiwis().setVis(true);
                MainScreen7.soosJSON.getCeta_decoration_kiwis().setVis(true);
                Cryptex.main.getActiveObjectsInI().getCake_decoration_kiwis_InI().setVis(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagCake_decoration_kiwis_BIG(true);
            }
            else if(name.equals("oranges_cut_InI") & !MainScreen7.soosJSON.getCake_decoration_oranges().isVisible()){
                MainScreen7.soosJSON.getCake_decoration_oranges().setVis(true);
                MainScreen7.soosJSON.getCeta_decoration_oranges().setVis(true);
                Cryptex.main.getActiveObjectsInI().getCake_decoration_oranges_InI().setVis(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagCake_decoration_oranges_BIG(true);
            }
            else if(name.equals("strawberries_InI") & !MainScreen7.soosJSON.getCake_decoration_strawberries().isVisible()){
                MainScreen7.soosJSON.getCake_decoration_strawberries().setVis(true);
                MainScreen7.soosJSON.getCeta_decoration_strawberries().setVis(true);
                Cryptex.main.getActiveObjectsInI().getCake_decoration_strawberries_InI().setVis(true);
                Cryptex.main.getMainScreen7().getFlags().setFlagCake_decoration_strawberries_BIG(true);
            }

            MainScreen7.aoos.getCake().setVis(true);

            Cryptex.main.getActiveListener().moveActiveItemToDefault();
        } else {
            Cryptex.main.getActiveListener().moveActiveItemToDefault();
        }
    }

    public void cutOranges(float x, float y){
        if (x > 203 & x < 403 & y > 86 & y < 286) {
            Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getCut_fruits(), 1);
            MainScreen7.soosJSON.getCubo_oranges_whole().setVis(false);
            MainScreen7.soosJSON.getCeta_oranges_whole().setVis(false);

            MainScreen7.soosJSON.getCubo_oranges_cut().setVis(true);
            MainScreen7.soosJSON.getCeta_oranges_cut().setVis(true);
            MainScreen7.aoos.getOranges_cut().setVis(true);

            Cryptex.main.getActiveListener().moveActiveItemToDefault();
        } else {
            Cryptex.main.getActiveListener().moveActiveItemToDefault();
        }
    }

    public void cutKiwis(float x, float y){
        if (x > 203 & x < 403 & y > 86 & y < 286) {
            Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getCut_fruits(), 1);
            MainScreen7.soosJSON.getCubo_kiwis_whole().setVis(false);
            MainScreen7.soosJSON.getCeta_kiwis_whole().setVis(false);

            MainScreen7.soosJSON.getCubo_kiwis_cut().setVis(true);
            MainScreen7.soosJSON.getCeta_kiwis_cut().setVis(true);
            MainScreen7.aoos.getKiwis_cut().setVis(true);

            Cryptex.main.getActiveListener().moveActiveItemToDefault();
        } else {
            Cryptex.main.getActiveListener().moveActiveItemToDefault();
        }
    }

    public void mountSpanner(float x, float y){
        if (x > 431 & x < 531 & y > 334 & y < 434) {
            Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getMount_spanner(), 1);
            MainScreen7.soosJSON.getOvvi_spanner_for_oven_mounted().setVis(true);
            MainScreen7.soosJSON.getOven_spanner_for_oven().setVis(true);
            MainScreen7.soosJSON.getOven_spanner_for_oven_shadow().setVis(true);
            MainScreen7.soosJSON.getKitc_spanner_for_oven_mounted().setVis(true);
            MainScreen7.soosJSON.getOven_pin().setVis(false);

            Cryptex.main.getPanelStage().getGroupActiveItem().findActor("spanner_for_oven_InI").remove();
            Cryptex.main.getGlobalReferences().setFlagActiveCellOccupied(false);
        } else {
            Cryptex.main.getActiveListener().moveActiveItemToDefault();
        }
    }

    public void rollDough(float x, float y, String color){
        if (x > 203 & x < 403 & y > 86 & y < 286) {

            Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getDough_rolling(), 1);

            if (color.equals("light")) {
                MainScreen7.soosJSON.getCubo_dough_unrolled().setVis(false);
                MainScreen7.soosJSON.getCeta_dough_unrolled().setVis(false);

                MainScreen7.soosJSON.getCubo_dough_rolled().setVis(true);
                MainScreen7.soosJSON.getCeta_dough_rolled().setVis(true);

                MainScreen7.aoos.getShortcake_uncooked().setVis(true);
            }
            else{
                MainScreen7.soosJSON.getCubo_dough_unrolled_dark().setVis(false);
                MainScreen7.soosJSON.getCeta_dough_unrolled_dark().setVis(false);

                MainScreen7.soosJSON.getCubo_dough_rolled_dark().setVis(true);
                MainScreen7.soosJSON.getCeta_dough_rolled_dark().setVis(true);

                MainScreen7.aoos.getShortcake_uncooked_dark().setVis(true);
            }

            Cryptex.main.getActiveListener().moveActiveItemToDefault();
        } else {
            Cryptex.main.getActiveListener().moveActiveItemToDefault();
        }
    }

    public void putDoughOnCubo(float x, float y, String color){
        if (x > 203 & x < 403 & y > 86 & y < 286 & !smthIsOnCubo()) {
            Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getPut_fruits(), 1);
            if(color.equals("light")) {
                MainScreen7.soosJSON.getCubo_dough_unrolled().setVis(true);
                MainScreen7.soosJSON.getCeta_dough_unrolled().setVis(true);

                Cryptex.main.getPanelStage().getGroupActiveItem().findActor("dough_InI").remove();
            }
            else{
                MainScreen7.soosJSON.getCubo_dough_unrolled_dark().setVis(true);
                MainScreen7.soosJSON.getCeta_dough_unrolled_dark().setVis(true);

                Cryptex.main.getPanelStage().getGroupActiveItem().findActor("dough_dark_InI").remove();
            }

            Cryptex.main.getGlobalReferences().setFlagActiveCellOccupied(false);
        } else {
            Cryptex.main.getActiveListener().moveActiveItemToDefault();
        }
    }

    public void putOrangesOnCubo(float x, float y){
        if (x > 203 & x < 403 & y > 86 & y < 286 & !smthIsOnCubo()) {
            Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getPut_fruits(), 1);
            MainScreen7.soosJSON.getCubo_oranges_whole().setVis(true);
            MainScreen7.soosJSON.getCeta_oranges_whole().setVis(true);

            Cryptex.main.getPanelStage().getGroupActiveItem().findActor("oranges_InI").remove();
            Cryptex.main.getGlobalReferences().setFlagActiveCellOccupied(false);
        } else {
            Cryptex.main.getActiveListener().moveActiveItemToDefault();
        }
    }

    boolean smthIsOnCubo(){
        if(MainScreen7.soosJSON.getCubo_dough_unrolled_dark().isVisible() | MainScreen7.soosJSON.getCubo_dough_rolled_dark().isVisible() |
                MainScreen7.soosJSON.getCubo_dough_unrolled().isVisible() | MainScreen7.soosJSON.getCubo_dough_rolled().isVisible() |
                MainScreen7.soosJSON.getCubo_kiwis_cut().isVisible() | MainScreen7.soosJSON.getCubo_kiwis_whole().isVisible() |
                MainScreen7.soosJSON.getCubo_oranges_cut().isVisible() | MainScreen7.soosJSON.getCubo_oranges_whole().isVisible()
                ) return true;

        return false;
    }

    public void putKiwisOnCubo(float x, float y){
        if (x > 203 & x < 403 & y > 86 & y < 286 & !smthIsOnCubo()) {
            Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getPut_fruits(), 1);
            MainScreen7.soosJSON.getCubo_kiwis_whole().setVis(true);
            MainScreen7.soosJSON.getCeta_kiwis_whole().setVis(true);

            Cryptex.main.getPanelStage().getGroupActiveItem().findActor("kiwis_InI").remove();
            Cryptex.main.getGlobalReferences().setFlagActiveCellOccupied(false);
        } else {
            Cryptex.main.getActiveListener().moveActiveItemToDefault();
        }
    }

    public void putUncookedShortcakeIntoOven(float x, float y, String name){
        if (x > 205 & x < 450 & y > -5 & y < 95) {
            Cryptex.main.getMainScreen7().getFlags().setFlagShortcakeInOven(true);
            Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getPut_into_oven(), 0.8f);

            MainScreen7.soosJSON.getOven_shortcake_uncooked_opened_door().setVis(true);
            MainScreen7.soosJSON.getOvvi_shortcake_uncooked().setVis(true);

            if(name.equals("shortcake_uncooked_InI")) Cryptex.main.getPanelStage().getGroupActiveItem().findActor("shortcake_uncooked_InI").remove();
            else Cryptex.main.getPanelStage().getGroupActiveItem().findActor("shortcake_uncooked_dark_InI").remove();

            Cryptex.main.getGlobalReferences().setFlagActiveCellOccupied(false);
        } else {
            Cryptex.main.getActiveListener().moveActiveItemToDefault();
        }
    }

    void stageTransition(Actor[] toEnable, Actor[] toDisable){
        for (int i = 0; i < toEnable.length; i++) {
            toEnable[i].setTouchable(Touchable.enabled);
        }

        for (int i = 0; i < toDisable.length; i++) {
            toDisable[i].setTouchable(Touchable.disabled);
        }
    }

    void rotateTapValve(){
        Cryptex.main.getMainScreen7().getListeners().setPreviousAngle(Cryptex.main.getMainScreen7().getListeners().getCurrentAngle());
        Cryptex.main.getMainScreen7().getListeners().setCurrentAngle(Cryptex.main.getMainScreen7().getListeners().getAngle());

        if (Math.abs(Cryptex.main.getMainScreen7().getListeners().getCurrentAngle() - Cryptex.main.getMainScreen7().getListeners().getPreviousAngle()) < 100) {
            if (Cryptex.main.getMainScreen7().getListeners().getCurrentAngle() - Cryptex.main.getMainScreen7().getListeners().getPreviousAngle() > 0) Cryptex.main.getMainScreen7().getListeners().setRotationDirection("<-");
            else Cryptex.main.getMainScreen7().getListeners().setRotationDirection("->");
        }

        if (Cryptex.main.getMainScreen7().getListeners().getCounter() % 10 == 0) {
            if(Cryptex.main.getMainScreen7().getListeners().getRotationDirection().equals("<-")){
                if(Cryptex.main.getMainScreen7().getListeners().getCurrentFrameNumberWAPI() < MainScreen7.soosJSON.getTava_frames().length - 1) {
                    MainScreen7.soosJSON.getTava_frames()[Cryptex.main.getMainScreen7().getListeners().getCurrentFrameNumberWAPI()].setVis(false);
                }
                if(Cryptex.main.getMainScreen7().getListeners().getCurrentFrameNumberWAPI() + 1 < MainScreen7.soosJSON.getTava_frames().length) {
                    MainScreen7.soosJSON.getTava_frames()[Cryptex.main.getMainScreen7().getListeners().getCurrentFrameNumberWAPI() + 1].setVis(true);
                    Cryptex.main.getMainScreen7().getListeners().setCurrentFrameNumberWAPI(Cryptex.main.getMainScreen7().getListeners().getCurrentFrameNumberWAPI() + 1);
                    Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getValve_turning(), 0.7f);
                }
                Cryptex.main.getMainScreen7().getListeners().setFlagValveInWC();

            }
            else {
                if(Cryptex.main.getMainScreen7().getListeners().getCurrentFrameNumberWAPI() != 0) {
                    MainScreen7.soosJSON.getTava_frames()[Cryptex.main.getMainScreen7().getListeners().getCurrentFrameNumberWAPI()].setVis(false);
                }
                if(Cryptex.main.getMainScreen7().getListeners().getCurrentFrameNumberWAPI() - 1 >= 0) {
                    MainScreen7.soosJSON.getTava_frames()[Cryptex.main.getMainScreen7().getListeners().getCurrentFrameNumberWAPI() - 1].setVis(true);
                    Cryptex.main.getMainScreen7().getListeners().setCurrentFrameNumberWAPI(Cryptex.main.getMainScreen7().getListeners().getCurrentFrameNumberWAPI() - 1);
                    Cryptex.main.getGlobalReferences().playSound(Cryptex.main.getMainScreen7().getSound().getValve_turning(), 0.7f);
                }
                Cryptex.main.getMainScreen7().getListeners().setFlagValveInWC();
            }
        }
    }

    void setFlagValveInWC(){
        if(MainScreen7.soosJSON.getTava_frame11().isVisible() || MainScreen7.soosJSON.getTava_frame12().isVisible() || MainScreen7.soosJSON.getTava_frame13().isVisible() ||
                MainScreen7.soosJSON.getTava_frame14().isVisible() || MainScreen7.soosJSON.getTava_frame15().isVisible()) Cryptex.main.getMainScreen7().getFlags().setFlagValveInWCOpened(true);
        else Cryptex.main.getMainScreen7().getFlags().setFlagValveInWCOpened(false);
    }

    public void setCakeValuesToDefault(){
        Cryptex.main.getMainScreen7().getListeners().setMeasuredYeastValue1(0);
        Cryptex.main.getMainScreen7().getListeners().setMeasuredYeastValue2(0);
        Cryptex.main.getMainScreen7().getListeners().setMeasuredFlourValue1(0);
        Cryptex.main.getMainScreen7().getListeners().setMeasuredFlourValue2(0);
        Cryptex.main.getMainScreen7().getListeners().setMeasuredSaltValue1(0);
        Cryptex.main.getMainScreen7().getListeners().setMeasuredSaltValue2(0);
        Cryptex.main.getMainScreen7().getListeners().setMeasuredSugarValue1(0);
        Cryptex.main.getMainScreen7().getListeners().setMeasuredSugarValue2(0);
        Cryptex.main.getMainScreen7().getListeners().setWaterLevelInBeaker1(0);
        Cryptex.main.getMainScreen7().getListeners().setWaterLevelInBeaker2(0);
        Cryptex.main.getMainScreen7().getFlags().setFlagCake_cream_lower_pink_BIG(false);
        Cryptex.main.getMainScreen7().getFlags().setFlagCake_cream_lower_white_BIG(false);
        Cryptex.main.getMainScreen7().getFlags().setFlagCake_cream_upper_pink_BIG(false);
        Cryptex.main.getMainScreen7().getFlags().setFlagCake_cream_upper_white_BIG(false);
        Cryptex.main.getMainScreen7().getFlags().setFlagCake_shortcake_lower_dark_BIG(false);
        Cryptex.main.getMainScreen7().getFlags().setFlagCake_shortcake_lower_light_BIG(false);
        Cryptex.main.getMainScreen7().getFlags().setFlagCake_shortcake_upper_dark_BIG(false);
        Cryptex.main.getMainScreen7().getFlags().setFlagCake_shortcake_upper_light_BIG(false);
        Cryptex.main.getMainScreen7().getFlags().setFlagCake_decoration_berries_BIG(false);
        Cryptex.main.getMainScreen7().getFlags().setFlagCake_decoration_strawberries_BIG(false);
        Cryptex.main.getMainScreen7().getFlags().setFlagCake_decoration_oranges_BIG(false);
        Cryptex.main.getMainScreen7().getFlags().setFlagCake_decoration_kiwis_BIG(false);
    }

    public void disableCakeActivity(){
        Cryptex.main.getMainScreen7().getFlags().setFlagEgg1Taken(true);
        Cryptex.main.getMainScreen7().getFlags().setFlagEgg2Taken(true);
        Cryptex.main.getMainScreen7().getFlags().setFlagEgg3Taken(true);
        Cryptex.main.getMainScreen7().getFlags().setFlagEgg4Taken(true);
        Cryptex.main.getMainScreen7().getFlags().setFlagEgg5Taken(true);
        Cryptex.main.getMainScreen7().getFlags().setFlagEgg6Taken(true);
        Cryptex.main.getMainScreen7().getFlags().setFlagButter1Taken(true);
        Cryptex.main.getMainScreen7().getFlags().setFlagButter2Taken(true);
        Cryptex.main.getMainScreen7().getFlags().setFlagButter3Taken(true);

        MainScreen7.aoos.getBerries().setVis(false);
        MainScreen7.aoos.getOranges().setVis(false);
        MainScreen7.aoos.getSalt().setVis(false);
        MainScreen7.aoos.getSugar().setVis(false);
    }

    public float getCurrentValue() {
        return currentValue;
    }
    public float getPreviousValue() {
        return previousValue;
    }
    public float getDelta() {
        return delta;
    }
    public int getWeight() {
        return weight;
    }
    public int getMeasuredSugarValue1() {
        return measuredSugarValue1;
    }
    public int getMeasuredSaltValue1() {
        return measuredSaltValue1;
    }
    public int getMeasuredFlourValue1() {
        return measuredFlourValue1;
    }
    public int getMeasuredYeastValue1() {
        return measuredYeastValue1;
    }
    public float getTouchY() {
        return touchY;
    }
    public int getCurrentFrameNumber() {
        return currentFrameNumber;
    }
    public int getCounter() {
        return counter;
    }
    public int getWaterLevelInBeaker1() {
        return waterLevelInBeaker1;
    }
    public float getDeltaX() {
        return deltaX;
    }
    public float getDeltaY() {
        return deltaY;
    }
    public float getAngle() {
        return angle;
    }
    public String getRotationDirection() {
        return rotationDirection;
    }
    public float getPreviousAngle() {
        return previousAngle;
    }
    public float getCurrentAngle() {
        return currentAngle;
    }
    public int getRenderTime() {
        return renderTime;
    }
    public int getWaterLevelInBeaker2() {
        return waterLevelInBeaker2;
    }
    public int getMeasuredSugarValue2() {
        return measuredSugarValue2;
    }
    public int getMeasuredSaltValue2() {
        return measuredSaltValue2;
    }
    public int getMeasuredFlourValue2() {
        return measuredFlourValue2;
    }
    public int getMeasuredYeastValue2() {
        return measuredYeastValue2;
    }
    public int getEggCounter() {
        return eggCounter;
    }
    public int getButterCounter() {
        return butterCounter;
    }
    public int getCurrentFrameNumberWAPI() {
        return currentFrameNumberWAPI;
    }

    public void setCurrentValue(float currentValue) {
        this.currentValue = currentValue;
    }
    public void setPreviousValue(float previousValue) {
        this.previousValue = previousValue;
    }
    public void setDelta(float delta) {
        this.delta = delta;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public void setMeasuredSugarValue1(int measuredSugarValue1) {
        this.measuredSugarValue1 = measuredSugarValue1;
    }
    public void setMeasuredSaltValue1(int measuredSaltValue1) {
        this.measuredSaltValue1 = measuredSaltValue1;
    }
    public void setMeasuredFlourValue1(int measuredFlourValue1) {
        this.measuredFlourValue1 = measuredFlourValue1;
    }
    public void setMeasuredYeastValue1(int measuredYeastValue1) {
        this.measuredYeastValue1 = measuredYeastValue1;
    }
    public void setTouchY(float touchY) {
        this.touchY = touchY;
    }
    public void setCurrentFrameNumber(int currentFrameNumber) {
        this.currentFrameNumber = currentFrameNumber;
    }
    public void setCounter(int counter) {
        this.counter = counter;
    }
    public void setWaterLevelInBeaker1(int waterLevelInBeaker1) {
        this.waterLevelInBeaker1 = waterLevelInBeaker1;
    }
    public void setDeltaX(float deltaX) {
        this.deltaX = deltaX;
    }
    public void setDeltaY(float deltaY) {
        this.deltaY = deltaY;
    }
    public void setAngle(float angle) {
        this.angle = angle;
    }
    public void setRotationDirection(String rotationDirection) {
        this.rotationDirection = rotationDirection;
    }
    public void setPreviousAngle(float previousAngle) {
        this.previousAngle = previousAngle;
    }
    public void setCurrentAngle(float currentAngle) {
        this.currentAngle = currentAngle;
    }
    public void setRenderTime(int renderTime) {
        this.renderTime = renderTime;
    }
    public void setWaterLevelInBeaker2(int waterLevelInBeaker2) {
        this.waterLevelInBeaker2 = waterLevelInBeaker2;
    }
    public void setMeasuredSugarValue2(int measuredSugarValue2) {
        this.measuredSugarValue2 = measuredSugarValue2;
    }
    public void setMeasuredSaltValue2(int measuredSaltValue2) {
        this.measuredSaltValue2 = measuredSaltValue2;
    }
    public void setMeasuredFlourValue2(int measuredFlourValue2) {
        this.measuredFlourValue2 = measuredFlourValue2;
    }
    public void setMeasuredYeastValue2(int measuredYeastValue2) {
        this.measuredYeastValue2 = measuredYeastValue2;
    }
    public void setEggCounter(int eggCounter) {
        this.eggCounter = eggCounter;
    }
    public void setButterCounter(int butterCounter) {
        this.butterCounter = butterCounter;
    }
    public void setCurrentFrameNumberWAPI(int currentFrameNumberWAPI) {
        this.currentFrameNumberWAPI = currentFrameNumberWAPI;
    }

    public InputListener getBarsIL() {
        return barsIL;
    }
    public InputListener getBeakIL() {
        return beakIL;
    }
    public InputListener getCakeIL() {
        return cakeIL;
    }
    public InputListener getCetaIL() {
        return cetaIL;
    }
    public InputListener getCuleIL() {
        return culeIL;
    }
    public InputListener getCuriIL() {
        return curiIL;
    }
    public InputListener getCuboIL() {
        return cuboIL;
    }
    public InputListener getFridIL() {
        return fridIL;
    }
    public InputListener getFutaIL() {
        return futaIL;
    }
    public InputListener getJarsIL() {
        return jarsIL;
    }
    public InputListener getKitcIL() {
        return kitcIL;
    }
    public InputListener getKiwtIL() {
        return kiwtIL;
    }
    public InputListener getMixeIL() {
        return mixeIL;
    }
    public InputListener getOvenIL() {
        return ovenIL;
    }
    public InputListener getOvviIL() {
        return ovviIL;
    }
    public InputListener getRitaIL() {
        return ritaIL;
    }
    public InputListener getScalIL() {
        return scalIL;
    }
    public InputListener getTuswIL() {
        return tuswIL;
    }
    public InputListener getCafeIL() {
        return cafeIL;
    }
    public InputListener getCaenIL() {
        return caenIL;
    }
    public InputListener getCarsIL() {
        return carsIL;
    }
    public InputListener getKienIL() {
        return kienIL;
    }
    public InputListener getWadiIL() {
        return wadiIL;
    }
    public InputListener getWataIL() {
        return wataIL;
    }
    public InputListener getSinkIL() {
        return sinkIL;
    }
    public InputListener getToilIL() {
        return toilIL;
    }
    public InputListener getWaclIL() {
        return waclIL;
    }
    public InputListener getWapiIL() {
        return wapiIL;
    }
}
