/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the comboBoxEditor.
 */
package com.tantech.cards.utils;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;

/**
 *
 * @author pfholden
 */
public class MyComboBox extends JComboBox{
	
    static final long serialVersionUID = 4321421L;
    
    private class MyPlainDocument extends PlainDocument{
        @Override
        public void remove(int offs, int len) throws BadLocationException {
            // return immediately when selecting an item
            if (selecting) return;
            if (hitBackspace) {
                // user hit backspace => move the selection backwards
                // old item keeps being selected
                if (offs>0) {
                    if (hitBackspaceOnSelection) offs--;
                    if (offs != 0){
                        Object item = lookupItem(getText(0, offs));
                        System.out.println("offs: "+offs+" item: "+getText(0, offs));
                        if (item != null) {
                            setItem(item);
                        } else {
                            // keep old item selected if there is no match
                            item = comboBox.getSelectedItem();
                        }
                        setText(item.toString());
                    }else {
                        comboBox.setSelectedIndex(0);
                        comboBox.setSelectedIndex(-1);
                    }
                } else {
                    super.remove(offs, len);
                    // User hit backspace with the cursor positioned on the start => beep
                    comboBox.getToolkit().beep(); // when available use: UIManager.getLookAndFeel().provideErrorFeedback(comboBox);
                }
                highlightCompletedText(offs);
            } else {
                super.remove(offs, len);
            }
        }

        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            // return immediately when selecting an item
            if (selecting) return;
            // insert the string into the document
            super.insertString(offs, str, a);
            // lookup and select a matching item
            Object item = lookupItem(getText(0, getLength()));
            if (item != null) {
                setItem(item);
            } else {
                // keep old item selected if there is no match
                item = comboBox.getSelectedItem();
                // imitate no insert (later on offs will be incremented by str.length(): selection won't move forward)
                offs = offs-str.length();
                // provide feedback to the user that his input has been received but can not be accepted
                comboBox.getToolkit().beep(); // when available use: UIManager.getLookAndFeel().provideErrorFeedback(comboBox);
            }
            setText(item.toString());
            // select the completed part
            highlightCompletedText(offs+str.length());
        }

        public void setText(String text) {
            try {
                // remove all text and insert the completed string
                super.remove(0, getLength());
                super.insertString(0, text, null);
            } catch (BadLocationException e) {
                throw new RuntimeException(e.toString());
            }
        }
    }
    
    private JComboBox comboBox;
    private ComboBoxModel model;
    private JTextComponent comboBoxEditor;
    
    // flag to indicate if setSelectedItem has been called
    // subsequent calls to remove/insertString should be ignored
    private boolean selecting=false;
    private boolean hidePopupOnFocusLoss;
    private boolean hitBackspace=false;
    private boolean hitBackspaceOnSelection;

    /**
     * Constructs a new object based upon the parameter searchable
     * @param combo
     */

    public MyComboBox(JComboBox combo){
        this.comboBox = combo;
        model = combo.getModel();
        comboBox.setEditable(true);
        comboBox.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXX");
        Component c = combo.getEditor().getEditorComponent();
        if ( c instanceof JTextComponent ){
            comboBoxEditor = (JTextComponent)c;        
            comboBoxEditor.setDocument(new MyPlainDocument());
        }else{
                throw new IllegalStateException("Editing component is not a JTextComponent!");
        }
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!selecting) highlightCompletedText(0);
            }
        });
        comboBoxEditor.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (comboBox.isDisplayable()) comboBox.setPopupVisible(true);
                hitBackspace=false;
                switch (e.getKeyCode()) {
                    // determine if the pressed key is backspace (needed by the remove method)
                    case KeyEvent.VK_BACK_SPACE : hitBackspace=true;
                    hitBackspaceOnSelection=comboBoxEditor.getSelectionStart()!=comboBoxEditor.getSelectionEnd();
                    break;
                    // ignore delete key
                    case KeyEvent.VK_DELETE : e.consume();
                    comboBox.getToolkit().beep();
                    break;
                }
            }
        });
        // Bug 5100422 on Java 1.5: Editable JComboBox won't hide popup when tabbing out
        hidePopupOnFocusLoss=System.getProperty("java.version").startsWith("1.5");
        // Highlight whole text when gaining focus
        comboBoxEditor.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                highlightCompletedText(0);
            }
            public void focusLost(FocusEvent e) {
                // Workaround for Bug 5100422 - Hide Popup on focus loss
                if (hidePopupOnFocusLoss) comboBox.setPopupVisible(false);
            }
        });
        // Handle initially selected object
        comboBox.setSelectedIndex(-1);
        Object selected = comboBox.getSelectedItem();
        if (selected!=null) ((MyPlainDocument)comboBoxEditor.getDocument()).setText(selected.toString());
        highlightCompletedText(0);
    }
    
    private void highlightCompletedText(int start) {
        comboBoxEditor.setCaretPosition(comboBoxEditor.getDocument().getLength());
        comboBoxEditor.moveCaretPosition(start);
    }
   
    private void setItem(Object item) {
        selecting = true;
        model.setSelectedItem(item);
        selecting = false;
    }
    
    private Object lookupItem(String pattern) {
        Object selectedItem = model.getSelectedItem();
        // only search for a different item if the currently selected does not match
        //if (selectedItem != null && startsWithIgnoreCase(selectedItem.toString(), pattern)) {
        if(false){
            return selectedItem;
        } else {
            // iterate over all items
            for (int i=0, n=model.getSize(); i < n; i++) {
                Object currentItem = model.getElementAt(i);
                // current item starts with the pattern?
                if (startsWithIgnoreCase(currentItem.toString(), pattern)) return currentItem;
            }
        }
        // no item starts with the pattern => return null
        return null;
    }
    
    // checks if str1 starts with str2 - ignores case
    private boolean startsWithIgnoreCase(String str1, String str2) {
        return str1.toUpperCase().startsWith(str2.toUpperCase());
    }
}

