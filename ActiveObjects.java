package art.dual;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

/**
 * It is a template class of all active game objects with parameters.
 * Also it describes JSON methods to save current objects condition.
 */
public class ActiveObjects extends Image implements Json.Serializable {
    private int index = -1;
    private float x, y;
    private boolean visability;
    private String name, type;

    public ActiveObjects(){}

    public ActiveObjects(Texture texture) {
        super(texture);
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

}

