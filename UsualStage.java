package art.dual;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.ArrayList;

/**
 * It's a stage template class.
 */

public class UsualStage {
    private Stage stage;
    private StaticObjects back_to_previous_stage;
    StretchViewport stretchViewport;

    public UsualStage(ArrayList<Actor> actors, EventListener el){
        String stageName = actors.get(0).getName().substring(0, 4);

        stretchViewport = new StretchViewport(800, 480);
        stage = new Stage(stretchViewport);

        for(int i = 0; i < actors.size(); i++){
            if(actors.get(i).getClass().getName().charAt(9) == 'S') {
                actors.get(i).addListener(el);
            }
            stage.addActor(actors.get(i));
        }

        if(!stageName.equals("caen") & !stageName.equals("cabi") & !stageName.equals("grav") & !stageName.equals("cave")) {
            back_to_previous_stage = new StaticObjects(Cryptex.manager.get("GlobalObjects/back_to_previous_stage.png", Texture.class));
            back_to_previous_stage.setVisible(true);
            if(stageName.equals("cubo")) back_to_previous_stage.setPosition(15, 400);
            else back_to_previous_stage.setPosition(690, 400);
            back_to_previous_stage.setName("exit_" + stageName);
            back_to_previous_stage.addListener(el);
            stage.addActor(back_to_previous_stage);
        }
    }

    public Stage getStage() {
        return stage;
    }
    public StaticObjects getBack_to_previous_stage() {
        return back_to_previous_stage;
    }
}