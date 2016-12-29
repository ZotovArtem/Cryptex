package art.dual;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.android.vending.expansion.zipfile.ZipResourceFile;

import java.io.File;
import java.io.IOException;

import art.dual.intro.IntroScreen;
import art.dual.mainMenu.MainMenuScreen;

/**
 * We start game by creating this class object.
 * Main class, which creates all other classes, is in create() method.
 */

public class Cryptex extends Game {
    private WaitingScreen waitingScreen;
    public static AssetManager manager, managerExt;
    public static Main main;
    private TextureLoader textureLoader, textureLoaderExt;
    private InternalFileHandleResolver resolver;
    private ExternalFileHandleResolver resolverExt;
    public static String path;
    public static ZipResourceFile expFile;

    public Cryptex(String path){
        this.path = path;
        try {
            expFile = new ZipResourceFile(path);
        }
        catch(IOException ioe){
            System.out.println("ERROR WHILE READING > > > " + ioe);
        }
    }

    @Override
    public void create() {
        manager = new AssetManager();
        resolver = new InternalFileHandleResolver();
        textureLoader = new TextureLoader(resolver);
        manager.setLoader(Texture.class, textureLoader);

        managerExt = new AssetManager();
        resolverExt = new ExternalFileHandleResolver();
        textureLoaderExt = new TextureLoader(resolverExt);
        managerExt.setLoader(Texture.class, textureLoaderExt);

        waitingScreen = new WaitingScreen(Cryptex.this);
        Cryptex.this.setScreen(waitingScreen);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                            wait(50);
                            Gdx.app.postRunnable(new Runnable() {
                                @Override
                                public void run() {
                                    main = new Main();
                                    waitingScreen.dispose();
                                    IntroScreen introScreen = main.createIntroScreen(Cryptex.this);
                                    Cryptex.this.setScreen(introScreen);
                                }
                            });
                    }
                } catch (Exception ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "loadingIntroMaterialsThreadCryptex");
        thread.start();
    }
}
