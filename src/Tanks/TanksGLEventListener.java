package Tanks;



import Texture.TextureReader;
import com.sun.opengl.util.Animator;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.media.opengl.*;

import java.util.BitSet;
import javax.media.opengl.glu.GLU;

public class TanksGLEventListener extends TanksListener {
    int animationIndex = 0;

    enum Directions {

        up,
        down,
        left,
        right,
        down_right,
        up_left,
        up_right,
        down_left,
        W,
        A,
        S,
        D,
        Q,

        W_A,
        W_D,
        S_A,
        S_D

    }

    Directions direction2 = Directions.S;
    Directions direction = Directions.up;

    int maxWidth = 60;
    int maxHeight = 60;

    int x = 0, y = 0;
    int x2 = 0, y2 = 0;


    String textureNames[] = {"tankUp.png", "tankRight.png","tankLeft.png","tankDown.png", "bull.png", "Back.jpg"};
    TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
    int textures[] = new int[textureNames.length];

    /*
     5 means gun in array pos
     x and y coordinate for gun
     */
    public void init(GLAutoDrawable gld) {

        GL gl = gld.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);    //This Will Clear The Background Color To Black

        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);

        for (int i = 0; i < textureNames.length; i++) {
            try {
                texture[i] = TextureReader.readTexture(assetsFolderName + "//" + textureNames[i], true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);


                new GLU().gluBuild2DMipmaps(
                        GL.GL_TEXTURE_2D,
                        GL.GL_RGBA, // Internal Texel Format,
                        texture[i].getWidth(), texture[i].getHeight(),
                        GL.GL_RGBA, // External format from image,
                        GL.GL_UNSIGNED_BYTE,
                        texture[i].getPixels() // Imagedata
                );
            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
    }

    public void display(GLAutoDrawable gld) {
        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer
        gl.glLoadIdentity();

        DrawBackground(gl);
        handleKeyPress();
        animationIndex = animationIndex % 4;
            DrawTank2(gl, x2, y2, animationIndex, 1, direction2);
            DrawTank(gl, x, y, animationIndex, 1, direction);

    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    public void DrawTank(GL gl, int x, int y, int index, float scale, Directions dir){
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);    // Turn Blending On
        int angle =0 ; //gl.glRotated(angle,0,0,1); for rotated the to correct dir
        if (dir == Directions.up){
            angle = 0;
        } else if (dir == Directions.down) {
            angle = 180;
        } else if (dir == Directions.left) {
            angle = 90;

        } else if (dir == Directions.right) {
            angle = -90;
        } else if (dir == Directions.down_right) {
            angle =-135;
        }else if (dir == Directions.down_left){
            angle =135;
        } else if (dir == Directions.up_left) {
            angle = 45;
        }else if (dir == Directions.up_right) {
            angle = -45;
        }

        gl.glPushMatrix();
        gl.glTranslated( x/(maxWidth/2.0) - 0.9, y/(maxHeight/2.0) - 0.9, 0);
        gl.glScaled(0.1*scale, 0.1*scale, 1);
        gl.glRotated(angle,0,0,1);
        //System.out.println(x +" " + y);
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }
    public void DrawTank2(GL gl, int x, int y, int index, float scale, Directions dir){
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);    // Turn Blending On
        int angle =0 ; //gl.glRotated(angle,0,0,1); for rotated the to correct dir
        if (dir == Directions.W) {
            angle = 0;
        } else if (dir == Directions.S) {
            angle = 180;
        } else if (dir == Directions.A) {
            angle = 90;
        } else if (dir == Directions.D) {
            angle = -90;
        } else if (dir == Directions.S_D) {
            angle = 135;
        } else if (dir == Directions.S_A) {
            angle = -135;
        } else if (dir == Directions.W_A) {
            angle = -45;
        } else if (dir == Directions.W_D) {
            angle = 45;
        }


        gl.glPushMatrix();
        gl.glTranslated(x / (maxWidth / 2.0) + 0.9, y / (maxHeight / 2.0) + 0.9, 0);
        gl.glScaled(0.1*scale, 0.1*scale, 1);
        gl.glRotated(angle,0,0,1);
        //System.out.println(x +" " + y);
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }
    public void DrawBackground(GL gl){
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[texture.length-1]);    // Turn Blending On

        gl.glPushMatrix();
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }
    public double getDistance(){
        return  Math.sqrt((x - (x2+54))*(x - (x2+54))+(y - (y2+54))*(y - (y2+54)));
    }
    public void handleKeyPress () {
        if (isKeyPressed(KeyEvent.VK_SPACE)) {
            // fire
        } else if (isKeyPressed(KeyEvent.VK_LEFT) && isKeyPressed(KeyEvent.VK_DOWN)) {
            if (x > 0 && y > 0) {
                x--;
                y--;
                if (getDistance() < 6) {
                    x++;
                    y++;
                }
            }
            direction = Directions.down_left;
        } else if (isKeyPressed(KeyEvent.VK_RIGHT) && isKeyPressed(KeyEvent.VK_DOWN)) {
            // Handle down_right
            if (x < maxWidth - 6 && y > 0) {
                x++;
                y--;
                if (getDistance() < 6) {
                    x--;
                    y++;
                }
            }
            direction = Directions.down_right;
        } else if (isKeyPressed(KeyEvent.VK_UP) && isKeyPressed(KeyEvent.VK_LEFT)) {
            // Handle up_left
            if (y < maxHeight - 6 && x > 0) {
                y++;
                x--;
                if (getDistance() < 6) {
                    y--;
                    x++;
                }
            }
            direction = Directions.up_left;
        } else if (isKeyPressed(KeyEvent.VK_UP) && isKeyPressed(KeyEvent.VK_RIGHT)) {
            // Handle up_right
            if (y < maxHeight - 6 && x < maxWidth - 6) {
                y++;
                x++;
                if (getDistance() < 6) {
                    y--;
                    x--;
                }
            }
            direction = Directions.up_right;
        } else if (isKeyPressed(KeyEvent.VK_LEFT)) {
            // Handle left
            if (x > 0) {
                x--;
                if (getDistance() < 6) {
                    x++;
                }
            }
            direction = Directions.left;
        } else if (isKeyPressed(KeyEvent.VK_RIGHT)) {
            // Handle right
            if (x < maxWidth - 6) {
                x++;
                if (getDistance() < 6) {
                    x--;
                }
            }
            direction = Directions.right;
        } else if (isKeyPressed(KeyEvent.VK_UP)) {
            // Handle up
            if (y < maxHeight - 6) {
                y++;
                if (getDistance() < 6) {
                    y--;
                }
            }
            direction = Directions.up;
        } else if (isKeyPressed(KeyEvent.VK_DOWN)) {
            // Handle down
            if (y > 0) {
                y--;
                if (getDistance() < 6) {
                    y++;
                }
            }
            direction = Directions.down;
        }

        //  player2
        if (isKeyPressed(KeyEvent.VK_Q)) {
            // fire
        }else if (isKeyPressed(KeyEvent.VK_A)) {
            if (x2 > -maxWidth + 6) {
                x2--;
                if (getDistance() < 6) {
                    x2++;
                }
            }
            direction2 = Directions.A;
        } else if (isKeyPressed(KeyEvent.VK_D)) {
            if (x2 < 0) {
                x2++;
                if (getDistance() < 6) {
                    x2--;
                }
            }
            direction2 = Directions.D;
        } else if (isKeyPressed(KeyEvent.VK_W)) {
            if (y2 < 0) {
                y2++;
                if (getDistance() < 6) {
                    y2--;
                }
            }
            direction2 = Directions.W;
        } else if (isKeyPressed(KeyEvent.VK_S)) {
            if (y2 > -maxHeight + 6) {
                y2--;
                if (getDistance() < 6) {
                    y2++;
                }
            }
            direction2 = Directions.S;
        } else if (isKeyPressed(KeyEvent.VK_A) && isKeyPressed(KeyEvent.VK_W)) {
            if (x2 > -maxWidth + 6 && y2 < 0) {
                x2--;
                y2++;
                if (getDistance() < 6) {
                    x2++;
                    y2--;
                }
            }
            direction2 = Directions.W_A;
        } else if (isKeyPressed(KeyEvent.VK_D) && isKeyPressed(KeyEvent.VK_W)) {
            if (x2 < 0 && y2 < 0) {
                x2++;
                y2++;
                if (getDistance() < 6) {
                    x2--;
                    y2--;
                }
            }
            direction2 = Directions.W_D;
        } else if (isKeyPressed(KeyEvent.VK_A) && isKeyPressed(KeyEvent.VK_S)) {
            if (x2 > -maxWidth + 6 && y2 > -maxHeight + 6) {
                x2--;
                y2--;
                if (getDistance() < 6) {
                    x2++;
                    y2++;
                }
            }
            direction2 = Directions.S_A;
        } else if (isKeyPressed(KeyEvent.VK_D) && isKeyPressed(KeyEvent.VK_S)) {
            if (x2 < 0 && y2 > -maxHeight + 6) {
                x2++;
                y2--;
                if (getDistance() < 6) {
                    x2--;
                    y2++;
                }
            }
            direction2 = Directions.S_D;
        }

    }



    public BitSet keyBits = new BitSet(256);

    @Override
    public void keyPressed(final KeyEvent event) {
        int keyCode = event.getKeyCode();
        keyBits.set(keyCode);
    }

    @Override
    public void keyReleased(final KeyEvent event) {
        int keyCode = event.getKeyCode();
        keyBits.clear(keyCode);
    }

    @Override
    public void keyTyped(final KeyEvent event) {
        // don't care
    }

    public boolean isKeyPressed(final int keyCode) {
        return keyBits.get(keyCode);
    }
}
