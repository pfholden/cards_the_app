/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tantech.cards.ui;

import com.tantech.cards.db.Card;
import com.tantech.cards.db.CardRepository;
import com.tantech.cards.db.OwnedCard;
import com.tantech.cards.dbimport.DbImportService;
import com.tantech.cards.search.CardSearchService;
import com.tantech.cards.utils.MtgSymbolConvert;
import com.tantech.cards.utils.MyComboBox;
import com.tantech.cards.utils.StringSearchable;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author pfholden
 */

@org.springframework.stereotype.Component
public class CardsSwingUI extends javax.swing.JFrame {

    @Autowired
    private CardSearchService cardSearchService;
    
    @Autowired
    private CardRepository cardRepo;
    
    @Autowired
    private DbImportService dbImportService;
    
    StringSearchable searchable;
    MyComboBox combo;
    boolean nameSelected = false;
    
    /**
     * Creates new form CardsSwingUI
     */
    public CardsSwingUI() {
        initComponents();
        
        setupTable(allCardTable);
        
        nameSearch.getEditor().getEditorComponent().addKeyListener(new java.awt.event.KeyAdapter() {
             public void keyTyped(java.awt.event.KeyEvent evt) {
                 nameSearchKeyTyped(evt);
             }
         });
        
//        List<String> cardNames = new ArrayList<>();
//        
//        searchable = new StringSearchable(cardNames);
//        combo = new MyComboBox(nameSearch, searchable);
        
      /*  
        nameSearch.setModel(new DefaultComboBoxModel());
        nameSearch.setEditable(true);
        nameSearch.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXX");
        nameSearch.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                System.out.println("ItemStateChanged fired: "+e.getStateChange());
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    nameSearch.setPopupVisible(false);
                    nameSelected = true;
                } else {
                    nameSearch.setPopupVisible(true);
                    nameSelected = false;
                }
            }
        });
        Component c = nameSearch.getEditor().getEditorComponent();
        if ( c instanceof JTextComponent ){
            final JTextComponent tc = (JTextComponent)c;

            tc.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent evt) {
                    nameSearchKeyTyped(evt);
                }
            });
            tc.getDocument().addDocumentListener(new DocumentListener(){

                @Override
                public void changedUpdate(DocumentEvent arg0) {}

                @Override
                public void insertUpdate(DocumentEvent arg0) {
                    update();
                }

                @Override
                public void removeUpdate(DocumentEvent arg0) {
                    update();
                }

                public void update(){
                    //perform separately, as listener conflicts between the editing component
                    //and JComboBox will result in an IllegalStateException due to editing 
                    //the component when it is locked. 
                    SwingUtilities.invokeLater(new Runnable(){

                        @Override
                        public void run() {
                                List<String> founds = new ArrayList<String>(searchable.search(tc.getText().toLowerCase()));
                                Set<String> foundSet = new HashSet<String>();
                                for ( String s : founds ){
                                        foundSet.add(s.toLowerCase());
                                }
                                Collections.sort(founds);//sort alphabetically


                                nameSearch.setEditable(false);
                                nameSearch.removeAllItems();
                                //if founds contains the search text, then only add once.
                                if ( !foundSet.contains( tc.getText().toLowerCase()) ){
                                        nameSearch.addItem( tc.getText() );
                                }

                                for (String s : founds) {
                                        nameSearch.addItem(s);
                                }
                                nameSearch.setEditable(true);
//                                    nameSearch.setPopupVisible(true);
                                tc.requestFocus(); 
                        }

                    });

                }

            });
            //When the text component changes, focus is gained 
            //and the menu disappears. To account for this, whenever the focus
            //is gained by the JTextComponent and it has searchable values, we show the popup.
            tc.addFocusListener(new FocusListener(){

                @Override
                public void focusGained(FocusEvent arg0) {
                        if ( tc.getText().length() >= 3 ){
                            nameSearch.setPopupVisible(!nameSelected);
                        }else{
                            nameSearch.setPopupVisible(false);
                        }
                }

                @Override
                public void focusLost(FocusEvent arg0) {						
                }

            });
        }else{
                throw new IllegalStateException("Editing component is not a JTextComponent!");
        }*/
	
//        combo = new AutocompleteJComboBox(searchable);
//		
//        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
//        jPanel3.setLayout(jPanel3Layout);
//        jPanel3Layout.setHorizontalGroup(
//            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel3Layout.createSequentialGroup()
//                .addGap(16, 16, 16)
//                .addComponent(combo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//        );
//        jPanel3Layout.setVerticalGroup(
//            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel3Layout.createSequentialGroup()
//                .addContainerGap()
//                .addComponent(combo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addContainerGap(128, Short.MAX_VALUE))
//        );
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        cardImageLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        cardTextPane = new javax.swing.JTextPane();
        jPanel4 = new javax.swing.JPanel();
        CardTab = new javax.swing.JTabbedPane();
        allCardScroll = new javax.swing.JScrollPane();
        allCardTable = new javax.swing.JTable();
        ownedCardScroll = new javax.swing.JScrollPane();
        ownedCardTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        nameSearch1 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        clearButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        colorSearch = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        typeSearch = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        textSearch = new javax.swing.JTextField();
        ownedCheckBox = new javax.swing.JCheckBox();
        nameSearch = new javax.swing.JComboBox<>();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        toolsMenu = new javax.swing.JMenu();
        importCsv = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        updateDbMenu = new javax.swing.JMenuItem();
        reindexSearch = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setMinimumSize(new java.awt.Dimension(254, 330));
        jPanel1.setPreferredSize(new java.awt.Dimension(250, 330));

        cardImageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cardImageLabel.setAlignmentX(0.5F);
        cardImageLabel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));
        cardImageLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        cardImageLabel.setPreferredSize(new java.awt.Dimension(254, 330));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setViewportBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        cardTextPane.setEditable(false);
        cardTextPane.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        cardTextPane.setAutoscrolls(false);
        cardTextPane.setMargin(new java.awt.Insets(10, 10, 10, 10));
        cardTextPane.setPreferredSize(new java.awt.Dimension(250, 100));
        jScrollPane1.setViewportView(cardTextPane);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cardImageLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(cardImageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        CardTab.setName(""); // NOI18N

        allCardTable.setAutoCreateRowSorter(true);
        allCardTable.setModel(new AllCardsModel(new Object [][] {
            {"", "", "", "", "", "", ""}
        }));
        allCardTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        allCardTable.setMinimumSize(new java.awt.Dimension(600, 0));
        //This is the post-listener code area

        allCardTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        ListSelectionModel allSelectionModel = allCardTable.getSelectionModel();

        allSelectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                handleAllSelectionEvent(e);
            }
        });
        allCardScroll.setViewportView(allCardTable);

        CardTab.addTab("All Cards", allCardScroll);

        ownedCardTable.setAutoCreateRowSorter(true);
        ownedCardTable.setModel(new OwnedCardsModel(new Object [][] {
            {"", "", "", "", "", "", ""}
        }));
        ownedCardTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        ownedCardTable.setMinimumSize(new java.awt.Dimension(600, 0));
        //This is the post-listener code area

        ownedCardTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        ListSelectionModel ownedSelectionModel = ownedCardTable.getSelectionModel();

        ownedSelectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                handleOwnedSelectionEvent(e);
            }
        });
        ownedCardScroll.setViewportView(ownedCardTable);

        CardTab.addTab("Owned Cards", ownedCardScroll);

        nameSearch1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameSearch1ActionPerformed(evt);
            }
        });
        nameSearch1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nameSearch1KeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nameSearch1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(nameSearch1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(89, Short.MAX_VALUE))
        );

        clearButton.setText("Clear");
        clearButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("Type:");

        jLabel4.setText("Color:");

        colorSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                colorSearchKeyTyped(evt);
            }
        });

        searchButton.setText("Search");
        searchButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        searchButton.setEnabled(false);
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Name:");

        typeSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                typeSearchKeyTyped(evt);
            }
        });

        jLabel2.setText("Text:");

        textSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textSearchKeyTyped(evt);
            }
        });

        ownedCheckBox.setText("Owned");
        ownedCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ownedCheckBoxActionPerformed(evt);
            }
        });

        nameSearch.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        nameSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        nameSearch.setMinimumSize(new java.awt.Dimension(69, 23));
        nameSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameSearchActionPerformed(evt);
            }
        });
        nameSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nameSearchKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(ownedCheckBox)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(searchButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(clearButton))
                        .addComponent(typeSearch, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(colorSearch, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(textSearch))
                    .addComponent(nameSearch, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addComponent(nameSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(typeSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(colorSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clearButton)
                    .addComponent(searchButton)
                    .addComponent(ownedCheckBox))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(CardTab, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(CardTab, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        toolsMenu.setText("Tools");

        importCsv.setText("Import CSV");
        importCsv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importCsvActionPerformed(evt);
            }
        });
        toolsMenu.add(importCsv);
        toolsMenu.add(jSeparator1);

        updateDbMenu.setText("Update DB");
        updateDbMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateDbMenuActionPerformed(evt);
            }
        });
        toolsMenu.add(updateDbMenu);

        reindexSearch.setText("Re-Index Search");
        reindexSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reindexSearchActionPerformed(evt);
            }
        });
        toolsMenu.add(reindexSearch);

        jMenuBar1.add(toolsMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    public void addNameSearchSuggestions(){
        nameSearch.setModel(new javax.swing.DefaultComboBoxModel<>(
                new Vector<String>(this.cardSearchService.searchNames())));
        combo = new MyComboBox(nameSearch);
        
//        searchable = new StringSearchable(this.cardSearchService.searchNames());
//	combo = new AutocompleteJComboBox(searchable);
//		
//        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
//        jPanel3.setLayout(jPanel3Layout);
//        jPanel3Layout.setHorizontalGroup(
//            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel3Layout.createSequentialGroup()
//                .addGap(16, 16, 16)
//                .addComponent(combo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//        );
//        jPanel3Layout.setVerticalGroup(
//            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel3Layout.createSequentialGroup()
//                .addContainerGap()
//                .addComponent(combo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addContainerGap(128, Short.MAX_VALUE))
//        );
    }
    
    private void setupTable(javax.swing.JTable table){
        TableColumnModel columnModel = table.getColumnModel();
        String columName = "Name";
        if (columnModel.getColumnIndex((Object) columName) >= 0){
            columnModel.removeColumn(columnModel.getColumn(columnModel.getColumnIndex((Object) "Card ID")));
        }
        columnModel.getColumn(0).setPreferredWidth(200);    //Name
        columnModel.getColumn(1).setPreferredWidth(200);    //Colors
        columnModel.getColumn(2).setPreferredWidth(200);    //Type
        columnModel.getColumn(3).setPreferredWidth(100);    //Power
        columnModel.getColumn(4).setPreferredWidth(100);    //Toughness
        columnModel.getColumn(5).setPreferredWidth(100);    //CMC
    }
    
    private void ownedCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ownedCheckBoxActionPerformed
        if (ownedCheckBox.isSelected()){
            searchButton.setEnabled(true);
        }else{
            searchButton.setEnabled(false);
        }
    }//GEN-LAST:event_ownedCheckBoxActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        performSearch();
    }//GEN-LAST:event_searchButtonActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        nameSearch.setSelectedIndex(-1);
        textSearch.setText("");
        typeSearch.setText("");
        colorSearch.setText("");
        ownedCheckBox.setSelected(false);
        searchButton.setEnabled(false);
    }//GEN-LAST:event_clearButtonActionPerformed

    private void nameSearch1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nameSearch1KeyTyped
        if (nameSearch1.getText().length()>0){
            if(!searchButton.isEnabled()){
                searchButton.setEnabled(true);
            }else{
                if (evt.getID() == KeyEvent.KEY_TYPED) {
                    if((evt.getKeyChar() == KeyEvent.VK_ENTER)&&searchButton.isEnabled()){
                        performSearch();
                    }
                }
            }
        }else{
            if(searchButton.isEnabled()){
                searchButton.setEnabled(false);
            }
        }
    }//GEN-LAST:event_nameSearch1KeyTyped

    private void textSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textSearchKeyTyped
       if (textSearch.getText().length()>0){
            if(!searchButton.isEnabled()){
                searchButton.setEnabled(true);
            }else{
                if (evt.getID() == KeyEvent.KEY_TYPED) {
                    if((evt.getKeyChar() == KeyEvent.VK_ENTER)&&searchButton.isEnabled()){
                        performSearch();
                    }
                }
            }
        }else{
            if(searchButton.isEnabled()){
                searchButton.setEnabled(false);
            }
        }
    }//GEN-LAST:event_textSearchKeyTyped

    private void typeSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_typeSearchKeyTyped
       if (typeSearch.getText().length()>0){
            if(!searchButton.isEnabled()){
                searchButton.setEnabled(true);
            }else{
                if (evt.getID() == KeyEvent.KEY_TYPED) {
                    if((evt.getKeyChar() == KeyEvent.VK_ENTER)&&searchButton.isEnabled()){
                        performSearch();
                    }
                }
            }
        }else{
            if(searchButton.isEnabled()){
                searchButton.setEnabled(false);
            }
        }
    }//GEN-LAST:event_typeSearchKeyTyped

    private void colorSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_colorSearchKeyTyped
        if (colorSearch.getText().length()>0){
            if(!searchButton.isEnabled()){
                searchButton.setEnabled(true);
            }else{
                if (evt.getID() == KeyEvent.KEY_TYPED) {
                    if((evt.getKeyChar() == KeyEvent.VK_ENTER)&&searchButton.isEnabled()){
                        performSearch();
                    }
                }
            }
        }else{
            if(searchButton.isEnabled()){
                searchButton.setEnabled(false);
            }
        }
    }//GEN-LAST:event_colorSearchKeyTyped

    private void updateDbMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateDbMenuActionPerformed
        Thread t=new Thread(new Runnable() {
            public void run() {
                try {                    
                    dbImportService.importDb();
                } catch (IOException ex) {
                    Logger.getLogger(CardsSwingUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        t.start();
    }//GEN-LAST:event_updateDbMenuActionPerformed

    private void reindexSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reindexSearchActionPerformed
        Thread t=new Thread(new Runnable() {
            public void run() {
                try {
                    cardSearchService.reloadIndex();
                } catch (InterruptedException ex) {
                    Logger.getLogger(CardsSwingUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        t.start();
    }//GEN-LAST:event_reindexSearchActionPerformed

    private void importCsvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importCsvActionPerformed
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(CardsSwingUI.this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            //System.out.println("File: "+file.getName());
            try {
                dbImportService.importOwnedCsv(file.getAbsolutePath());
            } catch (IOException ex) {
                Logger.getLogger(AppWindowUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(jPanel4, "Import cards complete");

        }               
    }//GEN-LAST:event_importCsvActionPerformed

    private void nameSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameSearchActionPerformed

    private void nameSearch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameSearch1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameSearch1ActionPerformed

    private void nameSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nameSearchKeyTyped
        if ((nameSearch.getSelectedItem() != null) && ((String) nameSearch.getSelectedItem()).length()>0){
            if(!searchButton.isEnabled()){
                searchButton.setEnabled(true);
            }else{
                if (evt.getID() == KeyEvent.KEY_TYPED) {
                    if((evt.getKeyChar() == KeyEvent.VK_ENTER)&&searchButton.isEnabled()){
                        performSearch();
                    }
                }
            }
        }
    }//GEN-LAST:event_nameSearchKeyTyped

    private void performSearch(){
        if (ownedCheckBox.isSelected()){
            updateOwnedTable((String) nameSearch.getSelectedItem(), textSearch.getText(), 
                    typeSearch.getText(), colorSearch.getText(), "");
            ownedCardTable.revalidate();
            CardTab.setSelectedIndex(1);
        } else{
            updateAllTable((String) nameSearch.getSelectedItem(), textSearch.getText(), 
                    typeSearch.getText(), colorSearch.getText(), "");
            allCardTable.revalidate();
            CardTab.setSelectedIndex(0);
        }
    }
    
    
    private void displaySingleCard(Integer cardId){
        
        // TODO: need to work on various format of cards. For example, split cards.
        //   These could be displayed and then have a click to swap to show the 
        //   other card text. 
        
        //Make sure cardId is not null or empty.
        if (cardId >= 0){
            Card lookupCard =  cardRepo.findByCardId((Integer) cardId);

           // System.out.println("Card image URL: "+ lookupCard.getImageUrl());

            if ( lookupCard.getImageUrl() != null){
                URL url=null;

                try {
                    url = new URL(lookupCard.getImageUrl().replace("http:", "https:"));
                } catch (MalformedURLException ex) {
                    Logger.getLogger(CardsSwingUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                BufferedImage image;
                try {
                    image = ImageIO.read(url);
                    cardImageLabel.setIcon(new ImageIcon(image));
                } catch (IOException ex) {
                    Logger.getLogger(CardsSwingUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                cardImageLabel.setIcon(new ImageIcon());
                cardImageLabel.setText("No image available");
            }


            StyledDocument doc = (StyledDocument) cardTextPane.getStyledDocument();
            Style iconStyle = doc.addStyle("StyleName", null);
            Style textStyle = doc.addStyle("TextStyle", null);
            StyleConstants.setFontSize(textStyle, 14);

            try {
                doc.remove(0, doc.getLength());

                // If cardName is not empty, add to cardTextPane
                if (lookupCard.getName() != null){
                    StyleConstants.setBold(textStyle, true);
                    doc.insertString(doc.getLength(), lookupCard.getName()+"\n\n", textStyle);
                    StyleConstants.setBold(textStyle, false);
                }

                // If cardText is not empty, add to cardTextPane
                if (lookupCard.getText() != null){
                    Map<String, String> textMap = MtgSymbolConvert.parseManaSymbols(lookupCard.getText()); 
                    for(Map.Entry<String, String> entry : textMap.entrySet()){
                        //System.out.println(entry);
                        if(entry.getKey().startsWith("image")){
                            //Set the image icon style to the appropriate icon
                            StyleConstants.setIcon(iconStyle, createImageIcon(entry.getValue(), ""));

                            doc.insertString(doc.getLength(), "ignored text", iconStyle);
                            int fontSize = StyleConstants.getFontSize(textStyle);
                            StyleConstants.setFontSize(textStyle, 5);
                            doc.insertString(doc.getLength(), " ", textStyle);
                            StyleConstants.setFontSize(textStyle, fontSize);
                        } else {
                            doc.insertString(doc.getLength(), entry.getValue(), textStyle);
                        }
                    }
                }
                // If cardFlavor text is not empty, add to cardTextPane
                if (lookupCard.getFlavor() != null){
                    StyleConstants.setItalic(textStyle, true);
                    doc.insertString(doc.getLength(), "\n\n"+lookupCard.getFlavor(), textStyle);
                    StyleConstants.setItalic(textStyle, false);
                }
                cardTextPane.setCaretPosition(0);
            }catch (BadLocationException ex) {
                Logger.getLogger(CardsSwingUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    protected void handleAllSelectionEvent(ListSelectionEvent e) {
        if (e.getValueIsAdjusting())
            return;
        
        ListSelectionModel lsm = (ListSelectionModel)e.getSource();
        
        if(!lsm.isSelectionEmpty()){
            Integer selectedRow = 0;
             // Find out which indexes are selected.
            int minIndex = lsm.getMinSelectionIndex();
            int maxIndex = lsm.getMaxSelectionIndex();
            for (int i = minIndex; i <= maxIndex; i++) {
                if (lsm.isSelectedIndex(i)) {
                    selectedRow=i;
                }
            }
            lsm.clearSelection();
            displaySingleCard((Integer) allCardTable.getModel()
                    .getValueAt(allCardTable.convertRowIndexToModel(selectedRow),6));
        } 
    }
    
    protected void handleOwnedSelectionEvent(ListSelectionEvent e) {
        if (e.getValueIsAdjusting())
            return;
        
        ListSelectionModel lsm = (ListSelectionModel)e.getSource();
        if (!lsm.isSelectionEmpty()){
            Integer selectedRow = 0;
             // Find out which indexes are selected.
            int minIndex = lsm.getMinSelectionIndex();
            int maxIndex = lsm.getMaxSelectionIndex();
            for (int i = minIndex; i <= maxIndex; i++) {
                if (lsm.isSelectedIndex(i)) {
                    selectedRow=i;
                }
            }
            lsm.clearSelection();     
            
            displaySingleCard((Integer) ownedCardTable.getModel()
                    .getValueAt(ownedCardTable.convertRowIndexToModel(selectedRow),6));
        }
    }
    
    /** Returns an ImageIcon, or null if the path was invalid. */
    protected ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = this.getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    
    private void clearCardDisplay(){
        StyledDocument doc = (StyledDocument) cardTextPane.getDocument();
        try {
            doc.remove(0, doc.getLength());
            cardImageLabel.setIcon(null);
        } catch (BadLocationException ex) {
            Logger.getLogger(CardsSwingUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void updateOwnedTable(String name, String text, String type, String colors, String set){
        
        clearCardDisplay();
        
        java.util.List<OwnedCard> cardsTempList = cardSearchService.searchOwnedCards(name, text, type, colors, set);
        
        Object[][] data = new Object[1][7];
        
        if (cardsTempList.size() > 0){
            data = new Object[cardsTempList.size()][7];
            int i = 0;       
        
            for(OwnedCard card:cardsTempList) {
                    int j = 0;

                    data[i][j++] = card.getCardMaster().getName();
                    data[i][j++] = card.getCardMaster().getColors();
                    data[i][j++] = card.getCardMaster().getType();
                    data[i][j++] = card.getCardMaster().getPower();
                    data[i][j++] = card.getCardMaster().getToughness();
                    data[i][j++] = card.getCardMaster().getCmc();
                    data[i][j++] = card.getCardMaster().getCardId();

                    i++;
            }
        }else{            
            data[0][0]="Nothing found...";
            data[0][6]=-1;
            for(int i=1; i<6; i++){
                data[0][i] = "";
            }
        }
        
        OwnedCardsModel updatedModel = new OwnedCardsModel(data);
        ownedCardTable.setModel(updatedModel);
        
        setupTable(ownedCardTable);
    }
    
    public void updateAllTable(String name, String text, String type, String colors, String set){
        clearCardDisplay();
        java.util.List<Card> cardsTempList = cardSearchService.searchCards(name, text, type, colors, set);
        
        Object[][] data = new Object[1][7];
        
        if (cardsTempList.size() > 0){
            data = new Object[cardsTempList.size()][7];
            int i = 0;
            if (cardsTempList.size() > 0){
                for(Card card:cardsTempList) {
                        int j = 0;

                        data[i][j++] = card.getName();
                        data[i][j++] = card.getColors();
                        data[i][j++] = card.getType();
                        data[i][j++] = (card.getPower() != null ? card.getPower():"");
                        data[i][j++] = (card.getToughness() != null ? card.getToughness():"");
                        data[i][j++] = card.getCmc();
                        data[i][j++] = card.getCardId();

                        i++;
                }
            }
        }else{            
            data[0][0]="Nothing found...";
            data[0][6]=-1;
            for(int i=1; i<6; i++){
                data[0][i] = "";
            }
        }
        
        AllCardsModel updatedModel = new AllCardsModel(data);
        allCardTable.setModel(updatedModel);
        setupTable(allCardTable);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane CardTab;
    private javax.swing.JScrollPane allCardScroll;
    private javax.swing.JTable allCardTable;
    private javax.swing.JLabel cardImageLabel;
    private javax.swing.JTextPane cardTextPane;
    private javax.swing.JButton clearButton;
    private javax.swing.JTextField colorSearch;
    private javax.swing.JMenuItem importCsv;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JComboBox<String> nameSearch;
    private javax.swing.JTextField nameSearch1;
    private javax.swing.JScrollPane ownedCardScroll;
    private javax.swing.JTable ownedCardTable;
    private javax.swing.JCheckBox ownedCheckBox;
    private javax.swing.JMenuItem reindexSearch;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField textSearch;
    private javax.swing.JMenu toolsMenu;
    private javax.swing.JTextField typeSearch;
    private javax.swing.JMenuItem updateDbMenu;
    // End of variables declaration//GEN-END:variables
}
