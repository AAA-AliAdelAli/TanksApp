package Tanks;

import com.sun.opengl.util.*;
import java.awt.*;
import javax.media.opengl.*;
import javax.swing.*;

public class TanksGame extends JFrame {

    public static void main(String[] args) {

        new TanksGame();
    }


    public TanksGame() {
        GLCanvas glcanvas;
        Animator animator;

        TanksGLEventListener listener = new TanksGLEventListener();
        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);
        glcanvas.addKeyListener(listener);
        getContentPane().add(glcanvas, BorderLayout.CENTER);
        animator = new FPSAnimator(15);
        animator.add(glcanvas);
        animator.start();

        setTitle("TanksGame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(null);
        setVisible(true);
        setFocusable(true);
        glcanvas.requestFocus();
    }
}
