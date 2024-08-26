package amharic_spell_checker;

import com.sun.javafx.css.Selector;
import java.awt.*;
import java.awt.FileDialog;
import java.awt.datatransfer.*;
import java.io.*;
import java.util.logging.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.*;
import javax.swing.undo.*;

public class SpellChecker extends javax.swing.JFrame {

    public Dictionary dict;
    String filename = "Untitled1";
    UndoManager undo;
    UndoAction undoAction;
    RedoAction redoAction;
    private int counter = 0;
    private int SelectionStart;
    private int SelectionEnd;
    Clipboard clipboard = getToolkit().getSystemClipboard();
    final static String filePath = "../amharic_spell_checker/amharic";
    final static char[] alphabet = "ሀሁሂሃሄህሆለሉሊላሌልሎሐሑሒሓሔሕሖመሙሚማሜምሞሠሡሢሣሤሥሦረሩሪራሬርሮሰሱሲሳሴስሶሸሹሺሻሼሽሾቀቁቂቃቄቅቆበቡቢባቤብቦተቱቲታቴትቶቸቹቺቻቼችቾኅኁኂኃኄኅኆነኑኒናኔንኖኘኙኚኛኜኝኞአኡኢኣኤእኦከኩኪካኬክኮወዉዊዋዌውዎዐዑዒዓዔዕዘዙዚዛዜዝዞዠዡዢዣዤዥዦየዩዪያዬይዮደዱዲዳዴድዶጀጁጂጃጄጅጆገጉጊጋጌግጎጠጡጢጣጤጥጦጨጩጪጫጬጭጮጰጱጳጴጵጶጸጹጺጻጼጽጾፀፁፂፃፄፅፆፈፉፊፋፌፍፎፐፑፒፓፔፕፖ".toCharArray();

    public SpellChecker() {
        dict = new Dictionary();
        dict.build(filePath);
        initComponents();
        //searchField.setText("search");
        Disable();
        undo = new UndoManager();
        undoAction = new UndoAction();
        redoAction = new RedoAction();

        textArea.getDocument().addUndoableEditListener(new UndoableEditListener() {

            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                undo.addEdit(e.getEdit());
                undoAction.update();
                redoAction.update();
            }
        });
    }
/////////////////////////////////////////////
    final String printSuggestions(String input) {
        StringBuilder sb = new StringBuilder();
        ArrayList<String> print = makeSuggestions(input);
        if (print.size() == 0) {
            // JOptionPane.showMessageDialog(this, "There is no suggestion for the word!");;
            //suggestionTA.setText("");
        } else {

            for (String s : print) {
                sb.append(s + "\n");

            }
        }
        return sb.toString();
    }

    private ArrayList<String> makeSuggestions(String input) {
        ArrayList<String> toReturn = new ArrayList<>();
        toReturn.addAll(charsSwapped(input));
        toReturn.addAll(charMissing(input));
        toReturn.addAll(charAppended(input));
        return toReturn;
    }
    private ArrayList<String> charAppended(String input) {
        ArrayList<String> toReturn = new ArrayList<>();
        for (char c : alphabet) {
            String atFront = c + input;
            String atBack = input + c;
            if (dict.contains(atFront)) {
                toReturn.add(atFront);
            }
            if (dict.contains(atBack)) {
                toReturn.add(atBack);
            }
        }
        return toReturn;
    }
    private ArrayList<String> inflectedWord(String input) {
        ArrayList<String> toReturn = new ArrayList<>();
        //String mor;
        String[] mor = {"ው", "አን", "ኩ", "ክ", "ዋል", "ችሁ", "ችሁት", "እን", "ኝ", "ችኝ", "ናቸው", "ን", "ሽ", "ች", "ይ", "አል", "ተ", "ችው", "ችውም", "አልተ", "ዋል"};
        // mor = """";
        for (String m : mor) {
            String atFront = m + input;
            String atBack = input + m;
            try {

                PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
                out.println(input);
                out.close();
//   suggestionTA.setText("");
            } catch (Exception e) {
                System.out.println("Could not create file");
            }
//            if (dict.contains(atFront)) {
//                toReturn.add(atFront);
//            }
//            if (dict.contains(atBack)) {
//                toReturn.add(atBack);
//            }
        }
        return toReturn;
    }

    private ArrayList<String> charMissing(String input) {
        ArrayList<String> toReturn = new ArrayList<>();

        int len = input.length() - 1;
        //try removing char from the front
        if (dict.contains(input.substring(1))) {
            toReturn.add(input.substring(1));
        }
        for (int i = 1; i < len; i++) {
            //try removing each char between (not including) the first and last
            String working = input.substring(0, i);
            working = working.concat(input.substring((i + 1), input.length()));
            if (dict.contains(working)) {
                toReturn.add(working);
            }
        }
        if (dict.contains(input.substring(0, len))) {
            toReturn.add(input.substring(0, len));
        }
        return toReturn;
    }

    private ArrayList<String> charsSwapped(String input) {
        ArrayList<String> toReturn = new ArrayList<>();

        for (int i = 0; i < input.length() - 1; i++) {
            String working = input.substring(0, i);// System.out.println("    0:" + working);
            working = working + input.charAt(i + 1);  //System.out.println("    1:" + working);
            working = working + input.charAt(i); //System.out.println("    2:" + working);
            working = working.concat(input.substring((i + 2)));//System.out.println("    FIN:" + working); 
            if (dict.contains(working)) {
                toReturn.add(working);
            }
        }
        return toReturn;
    }
///////////////////////////////////////////////////////////////////////////////
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        suggestionMenu = new javax.swing.JPopupMenu();
        addToDictionary = new javax.swing.JMenuItem();
        separartor = new javax.swing.JPopupMenu.Separator();
        suggestedItem1 = new javax.swing.JMenuItem();
        suggestedItem2 = new javax.swing.JMenuItem();
        suggestedItem3 = new javax.swing.JMenuItem();
        suggestedItem4 = new javax.swing.JMenuItem();
        suggestedItem5 = new javax.swing.JMenuItem();
        suggestedItem6 = new javax.swing.JMenuItem();
        suggestedItem7 = new javax.swing.JMenuItem();
        suggestedItem8 = new javax.swing.JMenuItem();
        suggestedItem9 = new javax.swing.JMenuItem();
        suggestedItem10 = new javax.swing.JMenuItem();
        suggestedItem11 = new javax.swing.JMenuItem();
        suggestedItem12 = new javax.swing.JMenuItem();
        suggestedItem13 = new javax.swing.JMenuItem();
        suggestedItem14 = new javax.swing.JMenuItem();
        suggestedItem15 = new javax.swing.JMenuItem();
        suggestedItem16 = new javax.swing.JMenuItem();
        suggestedItem17 = new javax.swing.JMenuItem();
        suggestedItem18 = new javax.swing.JMenuItem();
        suggestedItem19 = new javax.swing.JMenuItem();
        suggestedItem20 = new javax.swing.JMenuItem();
        suggestedItem21 = new javax.swing.JMenuItem();
        suggestedItem22 = new javax.swing.JMenuItem();
        suggestedItem23 = new javax.swing.JMenuItem();
        suggestedItem24 = new javax.swing.JMenuItem();
        suggestedItem25 = new javax.swing.JMenuItem();
        suggestedItem26 = new javax.swing.JMenuItem();
        suggestedItem27 = new javax.swing.JMenuItem();
        toolBar = new javax.swing.JToolBar();
        newButton = new javax.swing.JButton();
        openButton = new javax.swing.JButton();
        undoButton = new javax.swing.JButton();
        redoButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        copyButton = new javax.swing.JButton();
        cutButton = new javax.swing.JButton();
        pasteButton = new javax.swing.JButton();
        searchField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        scrollPane = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        ToolBar = new javax.swing.JToolBar();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        newItem = new javax.swing.JMenuItem();
        openItem = new javax.swing.JMenuItem();
        saveItem = new javax.swing.JMenuItem();
        saveasItem = new javax.swing.JMenuItem();
        printItem = new javax.swing.JMenuItem();
        exitItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        copyItem = new javax.swing.JMenuItem();
        cutItem = new javax.swing.JMenuItem();
        pasteItem = new javax.swing.JMenuItem();
        undoItem = new javax.swing.JMenuItem();
        redoItem = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        about_us = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        suggestionMenu.setFont(new java.awt.Font("Visual Geez Unicode", 0, 12)); // NOI18N

        addToDictionary.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        addToDictionary.setText("");
        addToDictionary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addToDictionaryActionPerformed(evt);
            }
        });
        suggestionMenu.add(addToDictionary);
        suggestionMenu.add(separartor);

        suggestedItem1.setFont(new java.awt.Font("Visual Geez Unicode", 0, 12)); // NOI18N
        suggestedItem1.setText("");
        suggestedItem1.setDoubleBuffered(true);
        suggestedItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestedItem1ActionPerformed(evt);
            }
        });
        suggestionMenu.add(suggestedItem1);

        suggestedItem2.setFont(new java.awt.Font("Visual Geez Unicode", 0, 12)); // NOI18N
        suggestedItem2.setText("");
        suggestedItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestedItem2ActionPerformed(evt);
            }
        });
        suggestionMenu.add(suggestedItem2);

        suggestedItem3.setFont(new java.awt.Font("Visual Geez Unicode", 0, 12)); // NOI18N
        suggestedItem3.setText("");
        suggestedItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestedItem3ActionPerformed(evt);
            }
        });
        suggestionMenu.add(suggestedItem3);

        suggestedItem4.setFont(new java.awt.Font("Visual Geez Unicode", 0, 12)); // NOI18N
        suggestedItem4.setText("");
        suggestedItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestedItem4ActionPerformed(evt);
            }
        });
        suggestionMenu.add(suggestedItem4);

        suggestedItem5.setFont(new java.awt.Font("Visual Geez Unicode", 0, 12)); // NOI18N
        suggestedItem5.setText("");
        suggestedItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestedItem5ActionPerformed(evt);
            }
        });
        suggestionMenu.add(suggestedItem5);

        suggestedItem6.setFont(new java.awt.Font("Visual Geez Unicode", 0, 12)); // NOI18N
        suggestedItem6.setText("");
        suggestedItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestedItem6ActionPerformed(evt);
            }
        });
        suggestionMenu.add(suggestedItem6);

        suggestedItem7.setFont(new java.awt.Font("Visual Geez Unicode", 0, 12)); // NOI18N
        suggestedItem7.setText("");
        suggestedItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestedItem7ActionPerformed(evt);
            }
        });
        suggestionMenu.add(suggestedItem7);

        suggestedItem8.setFont(new java.awt.Font("Visual Geez Unicode", 0, 12)); // NOI18N
        suggestedItem8.setText("");
        suggestedItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestedItem8ActionPerformed(evt);
            }
        });
        suggestionMenu.add(suggestedItem8);

        suggestedItem9.setFont(new java.awt.Font("Visual Geez Unicode", 0, 12)); // NOI18N
        suggestedItem9.setText("");
        suggestedItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestedItem9ActionPerformed(evt);
            }
        });
        suggestionMenu.add(suggestedItem9);

        suggestedItem10.setFont(new java.awt.Font("Visual Geez Unicode", 0, 12)); // NOI18N
        suggestedItem10.setText("");
        suggestedItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestedItem10ActionPerformed(evt);
            }
        });
        suggestionMenu.add(suggestedItem10);

        suggestedItem11.setFont(new java.awt.Font("Visual Geez Unicode", 0, 12)); // NOI18N
        suggestedItem11.setText("");
        suggestedItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestedItem11ActionPerformed(evt);
            }
        });
        suggestionMenu.add(suggestedItem11);

        suggestedItem12.setFont(new java.awt.Font("Visual Geez Unicode", 0, 12)); // NOI18N
        suggestedItem12.setText("");
        suggestedItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestedItem12ActionPerformed(evt);
            }
        });
        suggestionMenu.add(suggestedItem12);

        suggestedItem13.setFont(new java.awt.Font("Visual Geez Unicode", 0, 12)); // NOI18N
        suggestedItem13.setText("");
        suggestedItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestedItem13ActionPerformed(evt);
            }
        });
        suggestionMenu.add(suggestedItem13);

        suggestedItem14.setFont(new java.awt.Font("Visual Geez Unicode", 0, 12)); // NOI18N
        suggestedItem14.setText("");
        suggestedItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestedItem14ActionPerformed(evt);
            }
        });
        suggestionMenu.add(suggestedItem14);

        suggestedItem15.setFont(new java.awt.Font("Visual Geez Unicode", 0, 12)); // NOI18N
        suggestedItem15.setText("");
        suggestedItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestedItem15ActionPerformed(evt);
            }
        });
        suggestionMenu.add(suggestedItem15);

        suggestedItem16.setFont(new java.awt.Font("Visual Geez Unicode", 0, 12)); // NOI18N
        suggestedItem16.setText("");
        suggestedItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestedItem16ActionPerformed(evt);
            }
        });
        suggestionMenu.add(suggestedItem16);

        suggestedItem17.setFont(new java.awt.Font("Visual Geez Unicode", 0, 12)); // NOI18N
        suggestedItem17.setText("");
        suggestedItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestedItem17ActionPerformed(evt);
            }
        });
        suggestionMenu.add(suggestedItem17);

        suggestedItem18.setFont(new java.awt.Font("Visual Geez Unicode", 0, 12)); // NOI18N
        suggestedItem18.setText("");
        suggestedItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestedItem18ActionPerformed(evt);
            }
        });
        suggestionMenu.add(suggestedItem18);

        suggestedItem19.setFont(new java.awt.Font("Visual Geez Unicode", 0, 12)); // NOI18N
        suggestedItem19.setText("");
        suggestedItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestedItem19ActionPerformed(evt);
            }
        });
        suggestionMenu.add(suggestedItem19);

        suggestedItem20.setFont(new java.awt.Font("Visual Geez Unicode", 0, 12)); // NOI18N
        suggestedItem20.setText("");
        suggestedItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestedItem20ActionPerformed(evt);
            }
        });
        suggestionMenu.add(suggestedItem20);

        suggestedItem21.setFont(new java.awt.Font("Visual Geez Unicode", 0, 12)); // NOI18N
        suggestedItem21.setText("");
        suggestedItem21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestedItem21ActionPerformed(evt);
            }
        });
        suggestionMenu.add(suggestedItem21);

        suggestedItem22.setFont(new java.awt.Font("Visual Geez Unicode", 0, 12)); // NOI18N
        suggestedItem22.setText("");
        suggestedItem22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestedItem22ActionPerformed(evt);
            }
        });
        suggestionMenu.add(suggestedItem22);

        suggestedItem23.setFont(new java.awt.Font("Visual Geez Unicode", 0, 12)); // NOI18N
        suggestedItem23.setText("");
        suggestedItem23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestedItem23ActionPerformed(evt);
            }
        });
        suggestionMenu.add(suggestedItem23);

        suggestedItem24.setFont(new java.awt.Font("Visual Geez Unicode", 0, 12)); // NOI18N
        suggestedItem24.setText("");
        suggestedItem24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestedItem24ActionPerformed(evt);
            }
        });
        suggestionMenu.add(suggestedItem24);

        suggestedItem25.setFont(new java.awt.Font("Visual Geez Unicode", 0, 12)); // NOI18N
        suggestedItem25.setText("");
        suggestedItem25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestedItem25ActionPerformed(evt);
            }
        });
        suggestionMenu.add(suggestedItem25);

        suggestedItem26.setFont(new java.awt.Font("Visual Geez Unicode", 0, 12)); // NOI18N
        suggestedItem26.setText("");
        suggestedItem26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestedItem26ActionPerformed(evt);
            }
        });
        suggestionMenu.add(suggestedItem26);

        suggestedItem27.setFont(new java.awt.Font("Visual Geez Unicode", 0, 12)); // NOI18N
        suggestedItem27.setText("");
        suggestedItem27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestedItem27ActionPerformed(evt);
            }
        });
        suggestionMenu.add(suggestedItem27);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Amharic Spell Checker v.1.0");
        setBackground(new java.awt.Color(255, 255, 255));

        toolBar.setBackground(new java.awt.Color(255, 255, 255));
        toolBar.setFloatable(false);
        toolBar.setRollover(true);

        newButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/amharic_spell_checker/new.GIF"))); // NOI18N
        newButton.setToolTipText("new (Ctrl+N)");
        newButton.setFocusable(false);
        newButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        newButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        newButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newButtonActionPerformed(evt);
            }
        });
        toolBar.add(newButton);

        openButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/amharic_spell_checker/open.GIF"))); // NOI18N
        openButton.setToolTipText("Open (Ctrl+O)");
        openButton.setFocusable(false);
        openButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        openButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        openButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openButtonActionPerformed(evt);
            }
        });
        toolBar.add(openButton);

        undoButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/amharic_spell_checker/undo.GIF"))); // NOI18N
        undoButton.setToolTipText("Undo (Ctrl+Z)");
        undoButton.setFocusable(false);
        undoButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        undoButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        undoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                undoButtonActionPerformed(evt);
            }
        });
        toolBar.add(undoButton);

        redoButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/amharic_spell_checker/redo_1.GIF"))); // NOI18N
        redoButton.setToolTipText("Redo (Ctrl+Y)");
        redoButton.setFocusable(false);
        redoButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        redoButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        redoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redoButtonActionPerformed(evt);
            }
        });
        toolBar.add(redoButton);

        saveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/amharic_spell_checker/save.GIF"))); // NOI18N
        saveButton.setToolTipText("Save (Ctrl+S)");
        saveButton.setFocusable(false);
        saveButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        saveButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });
        toolBar.add(saveButton);

        copyButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/amharic_spell_checker/copy.GIF"))); // NOI18N
        copyButton.setToolTipText("Copy (Ctrl+C)");
        copyButton.setFocusable(false);
        copyButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        copyButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        copyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyButtonActionPerformed(evt);
            }
        });
        toolBar.add(copyButton);

        cutButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/amharic_spell_checker/cut.GIF"))); // NOI18N
        cutButton.setToolTipText(" Cut (Ctrl+X)");
        cutButton.setFocusable(false);
        cutButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cutButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cutButtonActionPerformed(evt);
            }
        });
        toolBar.add(cutButton);

        pasteButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/amharic_spell_checker/paste.GIF"))); // NOI18N
        pasteButton.setToolTipText("Paste (Ctrl+V)");
        pasteButton.setFocusable(false);
        pasteButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pasteButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        pasteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasteButtonActionPerformed(evt);
            }
        });
        toolBar.add(pasteButton);

        searchField.setBackground(new java.awt.Color(250, 250, 250));
        searchField.setColumns(10);
        searchField.setFont(new java.awt.Font("Visual Geez Unicode", 0, 11)); // NOI18N
        searchField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchFieldActionPerformed(evt);
            }
        });
        toolBar.add(searchField);

        searchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/amharic_spell_checker/search_1.GIF"))); // NOI18N
        searchButton.setToolTipText("Search");
        searchButton.setFocusable(false);
        searchButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        searchButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });
        toolBar.add(searchButton);

        textArea.setColumns(20);
        textArea.setFont(new java.awt.Font("Visual Geez Unicode", 0, 18)); // NOI18N
        textArea.setRows(5);
        textArea.setComponentPopupMenu(null
        );
        textArea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                textAreaMouseClicked(evt);
            }
        });
        textArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textAreaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textAreaKeyTyped(evt);
            }
        });
        scrollPane.setViewportView(textArea);

        ToolBar.setBackground(new java.awt.Color(255, 255, 255));
        ToolBar.setForeground(new java.awt.Color(255, 255, 255));
        ToolBar.setRollover(true);

        fileMenu.setText("File");

        newItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        newItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/amharic_spell_checker/new.GIF"))); // NOI18N
        newItem.setText("New");
        newItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newItemActionPerformed(evt);
            }
        });
        fileMenu.add(newItem);

        openItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/amharic_spell_checker/open.GIF"))); // NOI18N
        openItem.setText("Open");
        openItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openItemActionPerformed(evt);
            }
        });
        fileMenu.add(openItem);

        saveItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/amharic_spell_checker/save.GIF"))); // NOI18N
        saveItem.setText("Save");
        saveItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveItem);

        saveasItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        saveasItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/amharic_spell_checker/save as2.GIF"))); // NOI18N
        saveasItem.setText("Save As");
        saveasItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveasItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveasItem);

        printItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        printItem.setText("Print");
        printItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printItemActionPerformed(evt);
            }
        });
        fileMenu.add(printItem);

        exitItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        exitItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/amharic_spell_checker/exit2.GIF"))); // NOI18N
        exitItem.setText("Exit");
        exitItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);

        editMenu.setText("Edit");

        copyItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        copyItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/amharic_spell_checker/copy.GIF"))); // NOI18N
        copyItem.setText("Copy");
        copyItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyItemActionPerformed(evt);
            }
        });
        editMenu.add(copyItem);

        cutItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        cutItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/amharic_spell_checker/cut.GIF"))); // NOI18N
        cutItem.setText("Cut");
        cutItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cutItemActionPerformed(evt);
            }
        });
        editMenu.add(cutItem);

        pasteItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        pasteItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/amharic_spell_checker/paste2.GIF"))); // NOI18N
        pasteItem.setText("Paste");
        pasteItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasteItemActionPerformed(evt);
            }
        });
        editMenu.add(pasteItem);

        undoItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        undoItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/amharic_spell_checker/undo.GIF"))); // NOI18N
        undoItem.setText("Undo");
        undoItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                undoItemActionPerformed(evt);
            }
        });
        editMenu.add(undoItem);

        redoItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        redoItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/amharic_spell_checker/redo_1.GIF"))); // NOI18N
        redoItem.setText("Redo");
        redoItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redoItemActionPerformed(evt);
            }
        });
        editMenu.add(redoItem);

        menuBar.add(editMenu);

        jMenu1.setText("About");

        about_us.setText("Developers Name");
        about_us.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                about_usActionPerformed(evt);
            }
        });
        jMenu1.add(about_us);

        menuBar.add(jMenu1);

        jMenu2.setText("Feedback");

        jMenuItem1.setText("Feedback");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        menuBar.add(jMenu2);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(toolBar, javax.swing.GroupLayout.DEFAULT_SIZE, 910, Short.MAX_VALUE)
            .addComponent(ToolBar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(scrollPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(ToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void exitItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitItemActionPerformed
        // TODO add your handling code here:

        int choice = JOptionPane.showConfirmDialog(SpellChecker.this, "Do you want to close");
        if (choice == 0) {
            System.exit(0);
        } else if (choice == 1) {

        }
    }//GEN-LAST:event_exitItemActionPerformed

    private void openItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openItemActionPerformed
        // TODO add your handling code here:
        Enable();
        FileDialog filedialog = new FileDialog(SpellChecker.this, "open file", FileDialog.LOAD);
        filedialog.setVisible(true);
        if (filedialog.getFile() != null) {
            filename = filedialog.getDirectory() + filedialog.getFile();
            setTitle(filename);
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
                textArea.setText(sb.toString());

            }
            br.close();
        } catch (FileNotFoundException ex) {
            // Logger.getLogger(System_pr.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {

            // Logger.getLogger(System_pr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_openItemActionPerformed
    class UndoAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                undo.undo();
            } catch (CannotUndoException ex) {
                ex.getMessage();

            }
            update();
            redoAction.update();
        }

        protected void update() {
            if (undo.canUndo()) {
                undoButton.setEnabled(true);
                undoItem.setEnabled(true);

            } else {
                undoButton.setEnabled(false);
                undoItem.setEnabled(false);
            }
        }
    }

    class RedoAction extends AbstractAction {

        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            try {

                undo.redo();
            } catch (CannotRedoException ex) {
                ex.getMessage();
            }
            update();
            undoAction.update();
        }

        public void update() {

            if (undo.canRedo()) {
                redoButton.setEnabled(true);

            } else {
                redoButton.setEnabled(false);
                redoItem.setEnabled(false);
            }
        }
    }
    private void saveItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveItemActionPerformed
        // TODO add your handling code here:
        if (filename.equals("Untitled1")) {
            save();
        } else {
            try {
                FileWriter fr = new FileWriter(filename);
                BufferedWriter br = new BufferedWriter(fr);
                br.write(textArea.getText());
                br.close();
            } catch (IOException ex) {
                // Logger.getLogger(System_pr.this.getName()).log(Level.SEVERE,null,ex);
            }
        }
    }//GEN-LAST:event_saveItemActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        // TODO add your handling code here:
        searchtextarea(textArea, searchField.getText());
    }

    class myhighlighter extends DefaultHighlighter.DefaultHighlightPainter {

        public myhighlighter(Color c) {
            super(c);
        }

    }
    DefaultHighlighter.HighlightPainter highlighter = new myhighlighter(Color.yellow);

    public void removehighlight(JTextComponent textComp) {
        Highlighter removehighlighter = textComp.getHighlighter();
        Highlighter.Highlight[] remove = removehighlighter.getHighlights();
        for (int i = 0; i < remove.length; i++) {
            if (remove[i].getPainter() instanceof myhighlighter) {
                removehighlighter.removeHighlight(remove[i]);
            }
        }
    }

    public void searchtextarea(JTextComponent textComp, String textString) {
        removehighlight(textComp);
        try {
            Highlighter hilight = textComp.getHighlighter();
            Document doc = textComp.getDocument();
            String text = doc.getText(0, doc.getLength());
            int pos = 0;
            while ((pos = text.toUpperCase().indexOf(textString.toUpperCase(), pos)) >= 0) {
                hilight.addHighlight(pos, pos + textString.length(), highlighter);
                pos += textString.length();

            }

        } catch (Exception e) {

        }

    }//GEN-LAST:event_searchButtonActionPerformed

    private void newItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newItemActionPerformed
        // TODO add your handling code here:
        // if(textArea.getText().isEmpty()==false)
        Enable();
        setTitle(filename);
        if (filename.equals("Untitled1")) {
            if (textArea.getText().isEmpty() == false) {
                JDialog.setDefaultLookAndFeelDecorated(true);
                int response = JOptionPane.showConfirmDialog(null, "do you want to save changes ?", "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.NO_OPTION) {
                    textArea.setText("");
                    setTitle(filename);

                } else if (response == JOptionPane.YES_OPTION) {
                    saveItemActionPerformed(evt);
                    textArea.setText("");
                    setTitle(filename);

                } else if (response == JOptionPane.CANCEL_OPTION) {

                }
            } else if (textArea.getText().isEmpty() == true) {
                textArea.setText("");
                setTitle(filename);
            }

        } else {
            filename = "Untitled1";
            textArea.setText("");
            setTitle("Untitled1");
        }

    }//GEN-LAST:event_newItemActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        // TODO add your handling code here:
        saveItemActionPerformed(evt);

    }//GEN-LAST:event_saveButtonActionPerformed

    private void copyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyButtonActionPerformed
        // TODO add your handling code here:
        copyItemActionPerformed(evt);
    }//GEN-LAST:event_copyButtonActionPerformed

    private void cutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cutButtonActionPerformed
        // TODO add your handling code here:
        cutItemActionPerformed(evt);
    }//GEN-LAST:event_cutButtonActionPerformed

    private void pasteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasteButtonActionPerformed
        // TODO add your handling code here:
        pasteItemActionPerformed(evt);
    }//GEN-LAST:event_pasteButtonActionPerformed

    private void saveasItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveasItemActionPerformed
        // TODO add your handling code here:
        save();
    }//GEN-LAST:event_saveasItemActionPerformed

    private void undoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_undoButtonActionPerformed
        // TODO add your handling code here:

        undoItemActionPerformed(evt);
    }//GEN-LAST:event_undoButtonActionPerformed

    private void redoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_redoButtonActionPerformed
        // TODO add your handling code here:
        redoItemActionPerformed(evt);
    }//GEN-LAST:event_redoButtonActionPerformed

    private void newButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButtonActionPerformed
        // TODO add your handling code here:
        newItemActionPerformed(evt);
    }//GEN-LAST:event_newButtonActionPerformed

    private void openButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openButtonActionPerformed
        // TODO add your handling code here:
        openItemActionPerformed(evt);
    }//GEN-LAST:event_openButtonActionPerformed

    private void searchFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchFieldActionPerformed

    }//GEN-LAST:event_searchFieldActionPerformed

    private void textAreaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textAreaKeyReleased

        char space = evt.getKeyChar();

        if (Character.isSpaceChar(space)) {
            String inp = textArea.getText();

            ArrayList<String> wline = new ArrayList();
            String arr[] = inp.split(" ");
            for (String n : arr) {
                wline.add(n);
                wline.remove("");
            }

            for (String wlin : wline) {
                if (!dict.contains(wlin)) {
                    searchtextareas(textArea, wlin);

                }

            }
        }
    }//GEN-LAST:event_textAreaKeyReleased

    private void redoItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_redoItemActionPerformed
        // TODO add your handling code here:
        RedoAction ra = new RedoAction();
        ra.actionPerformed(evt);
    }//GEN-LAST:event_redoItemActionPerformed

    private void undoItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_undoItemActionPerformed
        // TODO add your handling code here:
        UndoAction ua = new UndoAction();
        ua.actionPerformed(evt);

    }//GEN-LAST:event_undoItemActionPerformed

    private void pasteItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasteItemActionPerformed
        // TODO add your handling code here:
        try {
            Transferable pastetext = clipboard.getContents(SpellChecker.this);
            String sel = (String) pastetext.getTransferData(DataFlavor.stringFlavor);
            textArea.replaceRange(sel, textArea.getSelectionStart(), textArea.getSelectionEnd());

        } catch (Exception e) {

        }
    }//GEN-LAST:event_pasteItemActionPerformed

    private void cutItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cutItemActionPerformed
        // TODO add your handling code here:
        String cuts = textArea.getSelectedText();
        StringSelection cutselection = new StringSelection(cuts);
        clipboard.setContents(cutselection, cutselection);
        textArea.replaceRange("", textArea.getSelectionStart(), textArea.getSelectionEnd());

    }//GEN-LAST:event_cutItemActionPerformed

    private void copyItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyItemActionPerformed
        // TODO add your handling code here:
        String copyText = textArea.getSelectedText();
        StringSelection copySelection = new StringSelection(copyText);
        clipboard.setContents(copySelection, copySelection);

    }//GEN-LAST:event_copyItemActionPerformed

    private void textAreaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textAreaKeyTyped
        // TODO add your handling code here:


    }//GEN-LAST:event_textAreaKeyTyped

    private void textAreaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textAreaMouseClicked
        // TODO add your handling code here:
        addToDictionary.setText("");
        suggestedItem1.setText("");
        suggestedItem2.setText("");
        suggestedItem3.setText("");
        suggestedItem4.setText("");
        suggestedItem5.setText("");
        suggestedItem6.setText("");
        suggestedItem7.setText("");
        suggestedItem8.setText("");
        suggestedItem9.setText("");
        suggestedItem10.setText("");
        suggestedItem11.setText("");
        suggestedItem12.setText("");
        suggestedItem13.setText("");
        suggestedItem14.setText("");
        suggestedItem15.setText("");
        suggestedItem16.setText("");
        suggestedItem17.setText("");
        suggestedItem18.setText("");
        suggestedItem19.setText("");
        suggestedItem20.setText("");
        suggestedItem21.setText("");
        suggestedItem22.setText("");
        suggestedItem23.setText("");
        suggestedItem24.setText("");
        suggestedItem25.setText("");
        suggestedItem26.setText("");
        suggestedItem27.setText("");
        suggestedItem27.setText("");
        SelectionStart = textArea.getSelectionStart();
        SelectionEnd = textArea.getSelectionEnd();
        counter = 0;
        String wordm1="";
        String wordm1f="";
        if (textArea.getSelectedText() != null) {

            textArea.setComponentPopupMenu(suggestionMenu);
            try {
                String input = textArea.getSelectedText();
                
char in[]=input.toCharArray();
for(int j=0;j<in.length-1;j++){
    wordm1=wordm1+in[j];    
}                // inflectedWord(input);
                String arr[] = input.split(" "); // Turning into an array
                java.util.List<String> list = Arrays.asList(input);
                for (int i = 0; i < arr.length; i++) {
                    if (dict.contains(arr[i])) {

                        suggestedItem1.setText("no suggestion");
                        suggestedItem1.setEnabled(false);
                        suggestedItem1.setForeground(Color.BLACK);
                    } else {
                        addToDictionary.setText("Add to dictionary");
                        String sugges = printSuggestions(arr[i])+"\n"+printSuggestions(wordm1);
                        String suggest[] = sugges.split("\n");
                        //java.util.List<String> list2 = Arrays.asList(suggest[]);

                        for (String line : suggest) {
                            counter = counter + 1;
                            //JOptionPane.showMessageDialog(this, "<HTML><font face='Visual Geez Unicode'>"+arr[i]+" is not spelled correctly");

                            if (counter == 1) {
                                suggestedItem1.setEnabled(true);
                                suggestedItem1.setText(line);

                            }
                            if (counter == 2) {
                                suggestedItem2.setText(line);

                            }
                            if (counter == 3) {
                                suggestedItem3.setText(line);

                            }
                            if (counter == 4) {
                                suggestedItem4.setText(line);

                            }
                            if (counter == 5) {
                                suggestedItem5.setText(line);

                            }
                            if (counter == 6) {
                                suggestedItem6.setText(line);

                            }
                            if (counter == 7) {
                                suggestedItem7.setText(line);

                            }
                            if (counter == 8) {
                                suggestedItem8.setText(line);

                            }
                            if (counter == 9) {
                                suggestedItem9.setText(line);

                            }
                            if (counter == 10) {
                                suggestedItem10.setText(line);

                            }
                            if (counter == 11) {
                                suggestedItem11.setText(line);

                            }
                            if (counter == 12) {
                                suggestedItem12.setText(line);

                            }
                            if (counter == 13) {
                                suggestedItem13.setText(line);

                            }
                            if (counter == 14) {
                                suggestedItem14.setText(line);

                            }
                            if (counter == 15) {
                                suggestedItem15.setText(line);

                            }
                            if (counter == 16) {
                                suggestedItem16.setText(line);

                            }
                            if (counter == 17) {
                                suggestedItem17.setText(line);

                            }
                            if (counter == 18) {
                                suggestedItem18.setText(line);

                            }
                            if (counter == 19) {
                                suggestedItem19.setText(line);

                            }
                            if (counter == 20) {
                                suggestedItem20.setText(line);

                            }
                            if (counter == 21) {
                                suggestedItem21.setText(line);

                            }
                            if (counter == 22) {
                                suggestedItem22.setText(line);

                            }
                            if (counter == 23) {
                                suggestedItem23.setText(line);

                            }
                            if (counter == 24) {
                                suggestedItem24.setText(line);

                            }
                            if (counter == 25) {
                                suggestedItem25.setText(line);

                            }
                            if (counter == 26) {
                                suggestedItem26.setText(line);

                            }
                            if (counter == 27) {
                                suggestedItem27.setText(line);

                            }
                            //suggestionMenu.add(suggestedItem);

                        }
                    }
                }
            } catch (Exception ex) {

            }

        } else {
            textArea.setComponentPopupMenu(null);
        }

    }//GEN-LAST:event_textAreaMouseClicked

    private void addToDictionaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addToDictionaryActionPerformed
        // TODO add your handling code here:

        try {
            String input = textArea.getSelectedText();

            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
            out.println(input);
            out.close();
            //out.flush();
            JOptionPane.showMessageDialog(this, "The word is added to dictionary!");;

        } catch (Exception e) {
            // System.out.println("Could not create file");
        }
    }//GEN-LAST:event_addToDictionaryActionPerformed

    private void suggestedItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suggestedItem1ActionPerformed
        // TODO add your handling code here:
        changeword(suggestedItem1.getText());
    }//GEN-LAST:event_suggestedItem1ActionPerformed

    private void suggestedItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suggestedItem2ActionPerformed
        // TODO add your handling code here:
        changeword(suggestedItem2.getText());
    }//GEN-LAST:event_suggestedItem2ActionPerformed

    private void suggestedItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suggestedItem3ActionPerformed
        // TODO add your handling code here:
        changeword(suggestedItem3.getText());
    }//GEN-LAST:event_suggestedItem3ActionPerformed

    private void suggestedItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suggestedItem4ActionPerformed
        // TODO add your handling code here:
        changeword(suggestedItem4.getText());
    }//GEN-LAST:event_suggestedItem4ActionPerformed

    private void suggestedItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suggestedItem5ActionPerformed
        // TODO add your handling code here:
        changeword(suggestedItem5.getText());
    }//GEN-LAST:event_suggestedItem5ActionPerformed

    private void suggestedItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suggestedItem6ActionPerformed
        // TODO add your handling code here:
        changeword(suggestedItem6.getText());
    }//GEN-LAST:event_suggestedItem6ActionPerformed

    private void suggestedItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suggestedItem7ActionPerformed
        // TODO add your handling code here:
        changeword(suggestedItem7.getText());
    }//GEN-LAST:event_suggestedItem7ActionPerformed

    private void suggestedItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suggestedItem8ActionPerformed
        // TODO add your handling code here:
        changeword(suggestedItem8.getText());
    }//GEN-LAST:event_suggestedItem8ActionPerformed

    private void suggestedItem27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suggestedItem27ActionPerformed
        // TODO add your handling code here:
        changeword(suggestedItem8.getText());
    }//GEN-LAST:event_suggestedItem27ActionPerformed

    private void suggestedItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suggestedItem19ActionPerformed
        // TODO add your handling code here:
        changeword(suggestedItem19.getText());
    }//GEN-LAST:event_suggestedItem19ActionPerformed

    private void suggestedItem26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suggestedItem26ActionPerformed
        // TODO add your handling code here:
        changeword(suggestedItem26.getText());
    }//GEN-LAST:event_suggestedItem26ActionPerformed

    private void suggestedItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suggestedItem9ActionPerformed
        // TODO add your handling code here:
        changeword(suggestedItem9.getText());
    }//GEN-LAST:event_suggestedItem9ActionPerformed

    private void suggestedItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suggestedItem10ActionPerformed
        // TODO add your handling code here:
        changeword(suggestedItem10.getText());
    }//GEN-LAST:event_suggestedItem10ActionPerformed

    private void suggestedItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suggestedItem11ActionPerformed
        // TODO add your handling code here:
        changeword(suggestedItem11.getText());
    }//GEN-LAST:event_suggestedItem11ActionPerformed

    private void suggestedItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suggestedItem12ActionPerformed
        // TODO add your handling code here:
        changeword(suggestedItem12.getText());
    }//GEN-LAST:event_suggestedItem12ActionPerformed

    private void suggestedItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suggestedItem13ActionPerformed
        // TODO add your handling code here:
        changeword(suggestedItem13.getText());
    }//GEN-LAST:event_suggestedItem13ActionPerformed

    private void suggestedItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suggestedItem14ActionPerformed
        // TODO add your handling code here:
        changeword(suggestedItem14.getText());
    }//GEN-LAST:event_suggestedItem14ActionPerformed

    private void suggestedItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suggestedItem15ActionPerformed
        // TODO add your handling code here:
        changeword(suggestedItem15.getText());
    }//GEN-LAST:event_suggestedItem15ActionPerformed

    private void suggestedItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suggestedItem16ActionPerformed
        // TODO add your handling code here:
        changeword(suggestedItem16.getText());
    }//GEN-LAST:event_suggestedItem16ActionPerformed

    private void suggestedItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suggestedItem17ActionPerformed
        // TODO add your handling code here:
        changeword(suggestedItem17.getText());
    }//GEN-LAST:event_suggestedItem17ActionPerformed

    private void suggestedItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suggestedItem18ActionPerformed
        // TODO add your handling code here:
        changeword(suggestedItem18.getText());
    }//GEN-LAST:event_suggestedItem18ActionPerformed

    private void suggestedItem20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suggestedItem20ActionPerformed
        // TODO add your handling code here:
        changeword(suggestedItem20.getText());
    }//GEN-LAST:event_suggestedItem20ActionPerformed

    private void suggestedItem21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suggestedItem21ActionPerformed
        // TODO add your handling code here:
        changeword(suggestedItem21.getText());
    }//GEN-LAST:event_suggestedItem21ActionPerformed

    private void suggestedItem22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suggestedItem22ActionPerformed
        changeword(suggestedItem22.getText());
    }//GEN-LAST:event_suggestedItem22ActionPerformed

    private void suggestedItem23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suggestedItem23ActionPerformed
        changeword(suggestedItem23.getText());
    }//GEN-LAST:event_suggestedItem23ActionPerformed

    private void suggestedItem24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suggestedItem24ActionPerformed
        // TODO add your handling code here:
        changeword(suggestedItem24.getText());
    }//GEN-LAST:event_suggestedItem24ActionPerformed

    private void suggestedItem25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suggestedItem25ActionPerformed
        // TODO add your handling code here:
        changeword(suggestedItem25.getText());
    }//GEN-LAST:event_suggestedItem25ActionPerformed

    private void about_usActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_about_usActionPerformed
        // TODO add your handling code here:
        DeveloperName dvn = new DeveloperName();
        dvn.setVisible(true);
    }//GEN-LAST:event_about_usActionPerformed

    private void printItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printItemActionPerformed
        try {
            // TODO add your handling code here:\
            textArea.print();
        } catch (PrinterException ex) {
            Logger.getLogger(SpellChecker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_printItemActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        Feedback fd=new Feedback();
        fd.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    DefaultHighlighter.HighlightPainter highlight = new myhighlighter(Color.red);

    public void searchtextareas(JTextComponent textComp, String textString) {

        try {
            Highlighter hilight = textComp.getHighlighter();
            Document doc = textComp.getDocument();
            String text = doc.getText(0, doc.getLength());
            int pos = 0;
            while ((pos = text.toUpperCase().indexOf(textString.toUpperCase(), pos)) >= 0) {
                hilight.addHighlight(pos, pos + textString.length(), highlight);
                pos += textString.length();

            }

        } catch (Exception e) {

        }

    }

    public void removehighlights(JTextComponent textComp) {
        Highlighter removehighlighter = textComp.getHighlighter();
        Highlighter.Highlight[] remove = removehighlighter.getHighlights();
        for (int i = 0; i < remove.length; i++) {
            if (remove[i].getPainter() instanceof myhighlighter) {
                removehighlighter.removeAllHighlights();

            }
        }
    }

    public void changeword(String lines) {
        String inp = textArea.getText();
        //String arr[] = inp.split(" "); // Turning into an array
        java.util.List<String> list = Arrays.asList(inp);

        String input = lines;
        //inp=inp.replace(selectedtext, input);
        textArea.replaceRange(input, SelectionStart, SelectionEnd);

        // textArea.setText(inp);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            //here you can put the selected theme class name in JTattoo
            UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
 
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SpellChecker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SpellChecker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SpellChecker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SpellChecker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SpellChecker().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToolBar ToolBar;
    private javax.swing.JMenuItem about_us;
    private javax.swing.JMenuItem addToDictionary;
    private javax.swing.JButton copyButton;
    private javax.swing.JMenuItem copyItem;
    private javax.swing.JButton cutButton;
    private javax.swing.JMenuItem cutItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exitItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JButton newButton;
    private javax.swing.JMenuItem newItem;
    private javax.swing.JButton openButton;
    private javax.swing.JMenuItem openItem;
    private javax.swing.JButton pasteButton;
    private javax.swing.JMenuItem pasteItem;
    private javax.swing.JMenuItem printItem;
    private javax.swing.JButton redoButton;
    private javax.swing.JMenuItem redoItem;
    private javax.swing.JButton saveButton;
    private javax.swing.JMenuItem saveItem;
    private javax.swing.JMenuItem saveasItem;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchField;
    private javax.swing.JPopupMenu.Separator separartor;
    private javax.swing.JMenuItem suggestedItem1;
    private javax.swing.JMenuItem suggestedItem10;
    private javax.swing.JMenuItem suggestedItem11;
    private javax.swing.JMenuItem suggestedItem12;
    private javax.swing.JMenuItem suggestedItem13;
    private javax.swing.JMenuItem suggestedItem14;
    private javax.swing.JMenuItem suggestedItem15;
    private javax.swing.JMenuItem suggestedItem16;
    private javax.swing.JMenuItem suggestedItem17;
    private javax.swing.JMenuItem suggestedItem18;
    private javax.swing.JMenuItem suggestedItem19;
    private javax.swing.JMenuItem suggestedItem2;
    private javax.swing.JMenuItem suggestedItem20;
    private javax.swing.JMenuItem suggestedItem21;
    private javax.swing.JMenuItem suggestedItem22;
    private javax.swing.JMenuItem suggestedItem23;
    private javax.swing.JMenuItem suggestedItem24;
    private javax.swing.JMenuItem suggestedItem25;
    private javax.swing.JMenuItem suggestedItem26;
    private javax.swing.JMenuItem suggestedItem27;
    private javax.swing.JMenuItem suggestedItem3;
    private javax.swing.JMenuItem suggestedItem4;
    private javax.swing.JMenuItem suggestedItem5;
    private javax.swing.JMenuItem suggestedItem6;
    private javax.swing.JMenuItem suggestedItem7;
    private javax.swing.JMenuItem suggestedItem8;
    private javax.swing.JMenuItem suggestedItem9;
    private javax.swing.JPopupMenu suggestionMenu;
    private javax.swing.JTextArea textArea;
    private javax.swing.JToolBar toolBar;
    private javax.swing.JButton undoButton;
    private javax.swing.JMenuItem undoItem;
    // End of variables declaration//GEN-END:variables
private void Disable() {
        /* runButton.setEnabled(false);
        cutButton.setEnabled(false);
        copyButton.setEnabled(false);
        pasteButton.setEnabled(false);
        searchButton.setEnabled(false);
        searchField.setEnabled(false);
             undoButton.setEnabled(false);
             redoButton.setEnabled(false);
             undoItem.setEnabled(false);
             redoItem.setEnabled(false);
             textArea.setEnabled(false);
             saveItem.setEnabled(false);
             saveButton.setEnabled(false);
             
             saveasItem.setEnabled(false);
         */
    }

    private void Enable() {

        saveButton.setEnabled(true);
        saveItem.setEnabled(true);

        saveasItem.setEnabled(true);

        cutButton.setEnabled(true);
        copyButton.setEnabled(true);
        pasteButton.setEnabled(true);
        searchButton.setEnabled(true);
        searchField.setEnabled(true);
        textArea.setEnabled(true);

    }

    private void save() {
        FileDialog filedialog = new FileDialog(SpellChecker.this, "save file", FileDialog.SAVE);
        filedialog.setVisible(true);
        if (filedialog.getFile() != null) {
            filename = filedialog.getDirectory() + filedialog.getFile();
            setTitle(filename);
            try {
                FileWriter filewriter = new FileWriter(filename);
                filewriter.write(textArea.getText());
                setTitle(filename);
                filewriter.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
