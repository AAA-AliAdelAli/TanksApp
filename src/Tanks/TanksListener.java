package Tanks;

import org.w3c.dom.events.MouseEvent;

import javax.media.opengl.GLEventListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

public abstract class TanksListener implements GLEventListener, KeyListener  , MouseListener {

    protected String assetsFolderName = "Assets//gun";
}