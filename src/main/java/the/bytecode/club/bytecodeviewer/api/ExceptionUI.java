package the.bytecode.club.bytecodeviewer.api;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import the.bytecode.club.bytecodeviewer.Configuration;
import the.bytecode.club.bytecodeviewer.resources.Resources;

import static the.bytecode.club.bytecodeviewer.Constants.*;

/***************************************************************************
 * Bytecode Viewer (BCV) - Java & Android Reverse Engineering Suite        *
 * Copyright (C) 2014 Kalen 'Konloch' Kinloch - http://bytecodeviewer.com  *
 *                                                                         *
 * This program is free software: you can redistribute it and/or modify    *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation, either version 3 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 *   This program is distributed in the hope that it will be useful,       *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *   GNU General Public License for more details.                          *
 *                                                                         *
 *   You should have received a copy of the GNU General Public License     *
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>. *
 ***************************************************************************/

/**
 * A simple class designed to show exceptions in the UI.
 *
 * @author Konloch
 */

public class ExceptionUI extends JFrame
{
    public static final String KONLOCH = "https://github.com/Konloch/bytecode-viewer/issues or Konloch at https://the.bytcode.club or konloch@gmail.com";
    public static final String SEND_STACKTRACE_TO = buildErrorLogHeader(KONLOCH);
    
    /**
     * @param e The exception to be shown
     */
    public ExceptionUI(Throwable e) {
        setupException(e, KONLOCH);
    }

    /**
     * @param e The exception to be shown
     */
    public ExceptionUI(String e) {
        setupFrame(e, KONLOCH);
    }

    /**
     * @param e      The exception to be shown
     * @param author the author of the plugin throwing this exception.
     */
    public ExceptionUI(Throwable e, String author) {
        setupException(e, author);
    }

    /**
     * @param e      The exception to be shown
     * @param author the author of the plugin throwing this exception.
     */
    public ExceptionUI(String e, String author) {
        setupFrame(e, author);
    }
    
    /**
     * Handles error suppression and prints stacktraces to strings
     */
    private void setupException(Throwable error, String author)
    {
        //exceptions are completely hidden
        if(Configuration.silenceExceptionGUI > 0)
            return;
    
        //exception GUI is disabled but printstack is still enabled
        if(Configuration.pauseExceptionGUI > 0)
        {
            error.printStackTrace();
            return;
        }
        
        StringWriter sw = new StringWriter();
        error.printStackTrace(new PrintWriter(sw));
        error.printStackTrace();
        
        setupFrame(sw.toString(), author);
    }
    
    /**
     * Creates a new frame and fills it with the error log
     */
    private void setupFrame(String error, String author)
    {
        this.setIconImages(Resources.iconList);
        setSize(new Dimension(600, 400));
        setTitle("Bytecode Viewer " + VERSION + " - Error Log - Send this to " + author);
        getContentPane().setLayout(new CardLayout(0, 0));

        JTextArea textArea = new JTextArea();
        textArea.setDisabledTextColor(Color.BLACK);
        textArea.setWrapStyleWord(true);
        getContentPane().add(new JScrollPane(textArea));

        textArea.setText(buildErrorLogHeader(author) + nl + nl + error);
        
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    /**
     * Returns the error log header
     */
    public static String buildErrorLogHeader(String author)
    {
        String fatJar = FAT_JAR ? " [FatJar] " : "";
        
        return "Please send this error log to " + author +
                "\nIf you hold appropriate legal rights to the relevant class/jar/apk file please include that as well." +
                "\nBytecode Viewer Version: " + VERSION + fatJar +
                ", OS: " + System.getProperty("os.name") +
                ", Java: " + System.getProperty("java.version");
    }
    
    private static final long serialVersionUID = -5230501978224926296L;
}