/*
Das Spielfeld kann erweitern werden, wenn man neue Icons hinzufügt 
und die Methoden plusButtonPressed() und minusButtonPressed(), sowie 
defineGridSize() erweitert. 

Wenn zwei gleiche Karten geschätzt wurden, dann geht das Spiel weiter,
wenn nicht, dann muss man auf eine beliebige andere Icon drücken um
weiter zu spielen.

Bei der NameEingabe kann man direkt ENTER drücken anstatt Add zu drücken

Mit dem rechtem Klick auf die Name in der Liste kann Man beim Spiel 
geschätzte Karten sehen und im Editor Mode die Namen löschen.

Es gibt ein Bug im SpielModus. Wenn Spieler sein Score wissen will 
und mit dem rechten Click die Name anclickt, wird Background auch 
geändert. Das kann man verbessern/reparieren in dem man für den JList 
"namesList" einen eigenen CellRenderer schreibt (dauert sehr lang);




+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
Es gibt einen hinterhältiges Bug, der aber nur ein Mal aufgetretten ist:

erstelle Spiel mit 64 Karten, füge ganz ganz viele Spieler hinzu
spiele das Spiel durch 
beende das Spiel
manipuliere mit den Spilern
erstelle Spiel mit nur 4 Karten und lösche die Spielern (es bleiben nur 2)
clicke eine von den Karten an

EXCEPTION (TERMINAL): 
Exception in thread "AWT-EventQueue-0" java.lang.ArrayIndexOutOfBoundsException: Index 2 out of bounds for length 2
	at Memory.getSolved(Memory.java:192)
	at GUI.uncoverCard(GUI.java:433)
	at GUI$3.mouseClicked(GUI.java:346)
	at java.desktop/java.awt.Component.processMouseEvent(Component.java:6635)
	at java.desktop/javax.swing.JComponent.processMouseEvent(JComponent.java:3342)
	at java.desktop/java.awt.Component.processEvent(Component.java:6397)
	at java.desktop/java.awt.Container.processEvent(Container.java:2263)
	at java.desktop/java.awt.Component.dispatchEventImpl(Component.java:5008)
	at java.desktop/java.awt.Container.dispatchEventImpl(Container.java:2321)
	at java.desktop/java.awt.Component.dispatchEvent(Component.java:4840)
	at java.desktop/java.awt.LightweightDispatcher.retargetMouseEvent(Container.java:4918)
	at java.desktop/java.awt.LightweightDispatcher.processMouseEvent(Container.java:4556)
	at java.desktop/java.awt.LightweightDispatcher.dispatchEvent(Container.java:4488)
	at java.desktop/java.awt.Container.dispatchEventImpl(Container.java:2307)
	at java.desktop/java.awt.Window.dispatchEventImpl(Window.java:2772)
	at java.desktop/java.awt.Component.dispatchEvent(Component.java:4840)
	at java.desktop/java.awt.EventQueue.dispatchEventImpl(EventQueue.java:772)
	at java.desktop/java.awt.EventQueue$4.run(EventQueue.java:721)
	at java.desktop/java.awt.EventQueue$4.run(EventQueue.java:715)
	at java.base/java.security.AccessController.doPrivileged(Native Method)
	at java.base/java.security.ProtectionDomain$JavaSecurityAccessImpl.doIntersectionPrivilege(ProtectionDomain.java:85)
	at java.base/java.security.ProtectionDomain$JavaSecurityAccessImpl.doIntersectionPrivilege(ProtectionDomain.java:95)
	at java.desktop/java.awt.EventQueue$5.run(EventQueue.java:745)
	at java.desktop/java.awt.EventQueue$5.run(EventQueue.java:743)
	at java.base/java.security.AccessController.doPrivileged(Native Method)
	at java.base/java.security.ProtectionDomain$JavaSecurityAccessImpl.doIntersectionPrivilege(ProtectionDomain.java:85)
	at java.desktop/java.awt.EventQueue.dispatchEvent(EventQueue.java:742)
	at java.desktop/java.awt.EventDispatchThread.pumpOneEventForFilters(EventDispatchThread.java:203)
	at java.desktop/java.awt.EventDispatchThread.pumpEventsForFilter(EventDispatchThread.java:124)
	at java.desktop/java.awt.EventDispatchThread.pumpEventsForHierarchy(EventDispatchThread.java:113)
	at java.desktop/java.awt.EventDispatchThread.pumpEvents(EventDispatchThread.java:109)
	at java.desktop/java.awt.EventDispatchThread.pumpEvents(EventDispatchThread.java:101)
	at java.desktop/java.awt.EventDispatchThread.run(EventDispatchThread.java:90)
Soll korrigiert werden!!!
 */
import java.awt.Color;
import javax.swing.JLabel;

public class GUI extends javax.swing.JFrame{
    // Variables declaration - do not modify   
    private Memory logic;
    private javax.swing.JPanel rootPanel;

    private javax.swing.JPanel cardsGrid;
    private javax.swing.JLabel[][] cards;

    private javax.swing.JPanel cardsEditor;
    private javax.swing.JLabel const1;
    private javax.swing.JButton plusButton;
    private javax.swing.JButton minusButton;
    private javax.swing.JLabel cardsNumber;

    private javax.swing.JPanel playersEditor;
    private javax.swing.JLabel const2;
    private javax.swing.JTextField nameInput;
    private javax.swing.JButton addName;

    private javax.swing.JList<String> namesList;
    private javax.swing.DefaultListModel namesModel;
    private javax.swing.JScrollPane namesPane;

    private javax.swing.JButton restartButton;
    private javax.swing.JButton startButton;

    private javax.swing.JLabel winner;

    private javax.swing.JLabel itemScore;
    private javax.swing.JMenuItem itemRemove;

    //Hilfsvariablen/atribute

    private final String srct = System.getProperty("user.dir");
    private final String addsrc = "\\img\\";
    private final javax.swing.ImageIcon[] img_library = new javax.swing.ImageIcon[36];

    //Hilfsvariablen/atribute für "checkCards()"
    private int clicks = 0;
    private int solved_cardpairs=0;
    private final int[] first_card = new int[2];
    private final int[] second_card = new int[2];
    private String currentPlayer_name;
    private int currentPlayer_index;

    //HilfsMethoden

    public GUI() {
        initComponents();
        this.setVisible(true);
        //System.out.println(this.srct+"////////////////////////////////////");
    }

    private void initComponents() {

        rootPanel = new javax.swing.JPanel();
        cardsGrid = new javax.swing.JPanel();
        cardsEditor = new javax.swing.JPanel();
        minusButton = new javax.swing.JButton();
        plusButton = new javax.swing.JButton();
        cardsNumber = new javax.swing.JLabel();
        const1 = new javax.swing.JLabel();
        playersEditor = new javax.swing.JPanel();
        const2 = new javax.swing.JLabel();
        nameInput = new javax.swing.JTextField();
        addName = new javax.swing.JButton();
        namesPane = new javax.swing.JScrollPane();
        namesList = new javax.swing.JList<>();
        namesModel = new javax.swing.DefaultListModel();
        winner = new javax.swing.JLabel();
        startButton = new javax.swing.JButton();
        restartButton = new javax.swing.JButton();

        //names.setCellRenderer(new WhiteYellowCellRenderer());

        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(true);

        javax.swing.GroupLayout gridLayout = new javax.swing.GroupLayout(cardsGrid);
        cardsGrid.setLayout(gridLayout);
        gridLayout.setHorizontalGroup(
            gridLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 358, Short.MAX_VALUE)
        );
        gridLayout.setVerticalGroup(
            gridLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        minusButton.setText("-");
        minusButton.addActionListener((java.awt.event.ActionEvent evt) -> {
                minusButtonPressed(evt);
            });

        plusButton.setText("+");
        plusButton.setFont(new java.awt.Font("Source Serif Pro Semibold", 1, 12));
        plusButton.addActionListener((java.awt.event.ActionEvent evt) -> {
                plusButtonPressed(evt);
            });

        cardsNumber.setFont(new java.awt.Font("Source Serif Pro Semibold", 1, 18)); // NOI18N
        cardsNumber.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cardsNumber.setText("4");

        const1.setFont(new java.awt.Font("Source Serif Pro Semibold", 1, 18)); // NOI18N
        const1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        const1.setText("Cards Number");

        javax.swing.GroupLayout cardsLayout = new javax.swing.GroupLayout(cardsEditor);
        cardsEditor.setLayout(cardsLayout);
        cardsLayout.setHorizontalGroup(
            cardsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cardsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(const1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(cardsLayout.createSequentialGroup()
                        .addComponent(plusButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40
                        , javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cardsNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(minusButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        cardsLayout.setVerticalGroup(
            cardsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cardsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(const1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(cardsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(plusButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cardsNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(minusButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        const2.setFont(new java.awt.Font("Source Serif Pro Semibold", 1, 18)); // NOI18N
        const2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        const2.setText("Players");

        addName.setFont(new java.awt.Font("Source Serif Pro Semibold", 1, 14)); // NOI18N
        addName.setText("ADD");
        addName.addActionListener((java.awt.event.ActionEvent evt) -> {
                addButtonPressed(evt);
            });
        nameInput.addKeyListener(new java.awt.event.KeyAdapter() {
                @Override
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    nameInputEnterPressed(evt);
                }
            });

        javax.swing.GroupLayout playersLayout = new javax.swing.GroupLayout(playersEditor);
        playersEditor.setLayout(playersLayout);
        playersLayout.setHorizontalGroup(
            playersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(playersLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(addName, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(playersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(playersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameInput)
                    .addComponent(const2, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
                .addContainerGap())
        );
        playersLayout.setVerticalGroup(
            playersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, playersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(const2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nameInput, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addName)
                .addContainerGap())
        );
        namesList.setModel(this.namesModel);
        namesList.setFont(new java.awt.Font("Source Serif Pro Semibold", 1, 18)); // NOI18N

        itemRemove = new javax.swing.JMenuItem("Remove");

        namesList.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(java.awt.event.MouseEvent e) {
                    if ( javax.swing.SwingUtilities.isRightMouseButton(e) && namesList.getModel().getSize()!=0 ) { 
                        //DEBUG
                        //System.out.println("Goes");
                        choosePlayerInList(namesList.locationToIndex(e.getPoint()));
                        if(namesList.getModel().getElementAt(namesList.locationToIndex(e.getPoint()))!=null){
                            int location = namesList.locationToIndex(e.getPoint());
                            javax.swing.JPopupMenu menu = new javax.swing.JPopupMenu();
                            itemScore = new javax.swing.JLabel();
                            itemScore.setSize(80, 10);
                            itemScore.setBackground(java.awt.Color.CYAN);
                            itemScore.setOpaque(true);
                            itemScore.setFont(new java.awt.Font("Source Serif Pro Semibold", 2, 18));
                            if(logic!=null)itemScore.setText("   score: "+logic.getScore()[location]);
                            itemRemove.addActionListener((java.awt.event.ActionEvent e1) -> {
                                    try{
                                        namesModel.remove(namesList.getSelectedIndex());
                                    }
                                    catch(ArrayIndexOutOfBoundsException exception) {
                                        //DEBUG
                                    }
                                });
                            menu.add(itemRemove);
                            menu.add(itemScore);
                            menu.show(namesList, e.getPoint().x, e.getPoint().y);    
                        }
                    }
                }

            });
        namesPane.setViewportView(namesList);

        winner.setText("");
        winner.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Winner", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Tahoma", 1, 12)));
        winner.setVisible(false);
        startButton.setFont(new java.awt.Font("Source Serif Pro Semibold", 1, 18)); // NOI18N
        startButton.setText("START");
        startButton.addActionListener((java.awt.event.ActionEvent evt) -> {
                startButtonPressed(evt);
            });

        restartButton.setFont(new java.awt.Font("Source Serif Pro Semibold", 1, 18)); // NOI18N
        restartButton.setText("RESTART");
        restartButton.addActionListener((java.awt.event.ActionEvent evt) -> {
                resetButtonPressed(evt);
            });

        javax.swing.GroupLayout coreLayout = new javax.swing.GroupLayout(rootPanel);
        rootPanel.setLayout(coreLayout);
        coreLayout.setHorizontalGroup(
            coreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(coreLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cardsGrid, javax.swing.GroupLayout.PREFERRED_SIZE, 358, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(coreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, coreLayout.createSequentialGroup()
                        .addComponent(cardsEditor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(playersEditor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, coreLayout.createSequentialGroup()
                        .addGroup(coreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)

                            .addGap(12, 12, 12)
                            .addComponent(winner, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addGroup(coreLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(coreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(restartButton, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(5, 5, 5)
                        .addComponent(namesPane, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)))
                .addContainerGap())
        );
        coreLayout.setVerticalGroup(
            coreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(coreLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(coreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cardsGrid, javax.swing.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
                    .addGroup(coreLayout.createSequentialGroup()
                        .addGroup(coreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cardsEditor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(playersEditor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(10,10,10)
                        .addGroup(coreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(coreLayout.createSequentialGroup()
                                .addComponent(winner, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)

                                .addGap(18,18,18)
                                .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15,15,15)
                                .addComponent(restartButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(namesPane, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rootPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rootPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>  

    private void createGrid(int[][] gameGrid){
        //Erstelle Array mit ImageIcon Objekten
        for (int j=0;j<36;j++){
            this.img_library[j]= new javax.swing.ImageIcon("img/"+(j+1)+".png");
        }
        //Fensteranpassung
        this.cardsGrid.setPreferredSize(new java.awt.Dimension(((10+48)*gameGrid[0].length+10+50),((10+48)*gameGrid.length+10+10)));
        this.rootPanel.setPreferredSize(new java.awt.Dimension((10+(10+48+6)*gameGrid[0].length+153+180+10)>=700 ? (10+(10+48+6)*gameGrid[0].length+153+180+10) : 700, ((((10+48)*gameGrid.length+30+30)>400) ? ((10+48)*gameGrid.length+30+30) : 400)));
        this.setSize((10+(10+48+6)*gameGrid[0].length+153+180+10)>=700 ? (10+(10+48+6)*gameGrid[0].length+153+180+10) : 700, ((((10+48)*gameGrid.length+30+30)>400) ? ((10+48)*gameGrid.length+30+30) : 400));

        //Übertrage abstrakte "Grid" auf die "cardsGrid" - > Synchronization mit Memory und Benutzeroberfläche
        cards = new javax.swing.JLabel[gameGrid.length][gameGrid[0].length];
        for (int i=0;i<gameGrid.length;i++) {
            for(int j=0;j<gameGrid[0].length;j++){
                final int FINAL_I=i;
                final int FINAL_J=j;
                cards[i][j]= new javax.swing.JLabel();
                cards[i][j].setPreferredSize(new java.awt.Dimension(48,48));
                cards[i][j].setIcon(new javax.swing.ImageIcon(this.getClass().getResource("img/unknown.png")));
                cards[i][j].setVisible(true);
                //labelCards[i][j].setBorder(javax.swing.BorderFactory.createLineBorder(Color.cyan, 3, true));
                cards[i][j].setBounds(10*(j+1)+48*j, 10*(i+1)+48*i, 48, 48);
                cards[i][j].addMouseListener(new java.awt.event.MouseAdapter() {
                        @Override
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                            uncoverCard(evt,FINAL_I,FINAL_J);
                        }
                    });
                this.cardsGrid.add(cards[i][j]);
                //DEBUG
                //System.out.println(i + " + " + j);
            }
        }
    }

    private void uncoverCard(java.awt.event.MouseEvent evt, int y, int x){
        //FUNKTIONIERT NUR FÜR NICHT GESCHÄTZTE KARTEN
        if(this.logic.getGrid()[y][x]>0){
            clicks++;

            //DEDINGUNG WECHSEL
            switch (clicks) {
                //ERSTE Karte Gewählt
                case 1:
                choosePlayerInList(this.currentPlayer_index);
                showIcon(y,x);
                //INITIALISIERUNG DES 1. CLICKS
                first_card[0]=y;
                first_card[1]=x;
                break;
                //ZWEITE Karte Gewählt
                case 2:
                showIcon(y,x);
                //INITIALISIERUNG DES 2. CLICKS
                //CHECKE OB DER SPIELER NICHT 2 MAL DIE GLEICHE KARTE GEWÄHLT HAT
                if(!(first_card[0]==y && first_card[1]==x)){
                    second_card[0]=y;
                    second_card[1]=x;
                    //INITIALISIERUNG DER LOGIK
                    this.logic.play(first_card, second_card);
                    //PRÜFE DEN ERGEBNISS
                    if(this.logic.getSolved(first_card) && this.logic.getSolved(first_card)){
                        solved_cardpairs++;
                        int max_index=0;
                        if(solved_cardpairs==(this.logic.getGridSize()[0]*this.logic.getGridSize()[1])/2){
                            this.winner.setVisible(true);
                            //DEBUG
                            //System.out.println("SOLVED");
                            //PRÜFE OB DIE KARTEN GLEICH SIND
                            this.winner.setText(this.logic.getWinner());
                            this.winner.setFont(new java.awt.Font("Times New Roman",2,16));
                        } 
                        clicks=0;
                        this.cards[first_card[0]][first_card[1]].setIcon(new javax.swing.ImageIcon(this.getClass().getResource("img/solved.png")));
                        this.cards[second_card[0]][second_card[1]].setIcon(new javax.swing.ImageIcon(this.getClass().getResource("img/solved.png")));
                        this.cards[first_card[0]][first_card[1]].addMouseListener(new java.awt.event.MouseAdapter() {
                                @Override
                                public void mouseClicked(java.awt.event.MouseEvent evt) {
                                    //DEBUG
                                    //System.out.println("DISABLED");
                                }
                            });

                        this.cards[second_card[0]][second_card[1]].addMouseListener(new java.awt.event.MouseAdapter() {
                                @Override
                                public void mouseClicked(java.awt.event.MouseEvent evt) {
                                    //DEBUG
                                    //System.out.println("DISABLED");
                                }
                            });
                    }
                    //BISTIMMUNG DES SPIELERS DER DRAN IST 
                    currentPlayer_name = this.logic.getCurrentPlayer();
                    currentPlayer_index=0;

                    for (int i=0;i<this.logic.getPlayers().length;i++){
                        if(this.currentPlayer_name.equals(this.logic.getPlayers()[i]))currentPlayer_index=i;
                    }

                    break;
                    //SODERFALL (SPIELER WÄHLT DIE SELBE KARTE)
                } else {

                    clicks=1;
                    //DEBUG
                    //System.out.println("FAILED");
                    break;
                }
                //FALSCHE WAHL; DRITTES KLICK UM VOM ANFANG ZU STARTEN
                case 3:
                choosePlayerInList(currentPlayer_index);
                //DREHE DIE KARTEN UM
                if(!this.logic.getSolved(first_card) || !this.logic.getSolved(second_card)){
                    this.cards[first_card[0]][first_card[1]].setIcon(new javax.swing.ImageIcon(this.getClass().getResource("img/unknown.png")));
                    this.cards[second_card[0]][second_card[1]].setIcon(new javax.swing.ImageIcon("img/unknown.png"));
                    this.clicks =0;
                } 
                clicks =0;
                break;
            }
        }   
    }

    void showPopUp(String message){
        Runnable r = () -> {
                String html = "<html><body width='%1s'><h1>"+message+"</h1>";
                int w = 400;
                javax.swing.JOptionPane.showMessageDialog(null, String.format(html, w, w),"!!!", javax.swing.JOptionPane.ERROR_MESSAGE);
            };
        javax.swing.SwingUtilities.invokeLater(r);
    }

    void showIcon(int iY, int iX){
        //DAS "ICON" WIRD VERÄDERT WENN DIESE NICHT BEREITS GESCHÄTZT WURDE(WIRD ZU MINUS WERT)
        //DEBUG
        //System.out.println("PRINTS");
        if(!(this.logic.gameGrid[iY][iX]<0)){
            this.cards[iY][iX].setIcon(this.img_library[this.logic.gameGrid[iY][iX]]); 
        }

    }

    private void choosePlayerInList(int pIndex){
        this.namesList.setSelectedIndex(pIndex);

    }

    private int[] defineGridSize(int cardsNumber){
        //NEUES ARRAY DEKLARIEREN
        int[] arr = new int[2];

        //REGELN DER INITIALISIERUNG DES ARRAYS
        if(cardsNumber==4){
            arr[0] = 2;
            arr[1] = 2;
            return arr;
        } else if(cardsNumber==8){
            arr[0] = 2;
            arr[1] = 4;
            return arr;
        } else if(cardsNumber==12){
            arr[0] = 3;
            arr[1] = 4;
            return arr;
        } else if(cardsNumber==16){
            arr[0] = 4;
            arr[1] = 4;
            return arr;
        } else if (cardsNumber==20){
            arr[0] = 4;
            arr[1] = 5;
            return arr;
        } else if (cardsNumber==24){
            arr[0] = 4;
            arr[1] = 6;
            return arr;
        } else if (cardsNumber==28){
            arr[0] = 4;
            arr[1] = 7;
            return arr;
        } else if (cardsNumber==32){
            arr[0] = 4;
            arr[1] = 8;
            return arr;
        } else if (cardsNumber==40){
            arr[0] = 5;
            arr[1] = 8;
            return arr;
        } else if (cardsNumber==48){
            arr[0] = 6;
            arr[1] = 8;
            return arr;
        } else if (cardsNumber==56){
            arr[0] = 7;
            arr[1] = 8;
            return arr;
        } else if (cardsNumber==64){
            arr[0] = 8;
            arr[1] = 8;
            return arr;
        } 
        //DEBUG
        System.out.println("Unklar Difinierte Maße");
        return null;
    }

    private void resetGameProgress(){
        this.solved_cardpairs=0;
        this.logic=null;

        //KARTEN LÖSCHEN 
        if (cards!=null){
            for (JLabel[] labelCard : this.cards) {
                for (int j = 0; j<cards[0].length; j++) {
                    cardsGrid.remove(labelCard[j]);
                    //DEBUG
                    //System.out.println("Removed");
                }
            }
            //GRID AKTUALISIEREN (TECHNISCHES)
            this.cardsGrid.updateUI();

            //FENSTERGRÖ?E ANPASSEN
            this.cardsGrid.setPreferredSize(new java.awt.Dimension(358,358));
            this.setSize(700,400);
            this.rootPanel.setPreferredSize(new java.awt.Dimension(700,400));

            //NAMEN LÖSCHEN
            //this.model.removeAllElements();

            //WINNER LÖSCHEN
            this.winner.setText("");
            this.winner.setVisible(false);
        }
    }

    private void addNameToList(){
        ////////KERNMETHODE (KEYEVEN UND ACTIONEVENT PROBLEM)
        //NEUES OBJEKT ZUR MODEL HINZUFÜGEN
        boolean valid_name_check=true;
        for(int i=0;i<this.namesModel.getSize();i++){
            if(this.nameInput.getText().equals(this.namesModel.getElementAt(i)))valid_name_check=false;
        }
        if(!this.nameInput.getText().isBlank() && valid_name_check){this.namesModel.addElement(this.nameInput.getText());} else {this.showPopUp("Name can't be empty/can't repeat");}

        //TEXTEINGABEFELD ZURÜCKSETZEN
        this.nameInput.setText("");
    }

    private void enableUserInput(boolean a){
        //WIRD BEI RESET GEBRAUCHT
        this.startButton.setEnabled(a);
        this.addName.setEnabled(a);
        this.nameInput.setEnabled(a);
        this.namesPane.setEnabled(a);
        this.plusButton.setEnabled(a);
        this.minusButton.setEnabled(a);
        this.namesList.setEnabled(a);
        this.itemRemove.setEnabled(a);   

    }

    //Benutzeroberfläche Kontrolle

    private void plusButtonPressed(java.awt.event.ActionEvent evt) {
        //HIER WERDEN KLARDIFINIERTE REGELN GESETZT, OBWOHL DIE REGELUNGEN AUFGEHOBEN WERDEN KÖNNEN
        if ((Integer.parseInt(cardsNumber.getText()))<64) {
            this.cardsNumber.setText((Integer.parseInt(cardsNumber.getText()))>=32 ? ""+((Integer.parseInt(cardsNumber.getText()))+8) : ""+(Integer.parseInt(cardsNumber.getText())+4));
        } // end of if

    }                                          

    private void minusButtonPressed(java.awt.event.ActionEvent evt) { 
        //HIER WERDEN KLARDIFINIERTE REGELN GESETZT, OBWOHL DIE REGELUNGEN AUFGEHOBEN WERDEN KÖNNEN
        if ((Integer.parseInt(cardsNumber.getText()))>4) {
            this.cardsNumber.setText((Integer.parseInt(cardsNumber.getText()))<=32 ? ""+((Integer.parseInt(cardsNumber.getText()))-4) : ""+(Integer.parseInt(cardsNumber.getText())-8));
        } // end of if
    }

    private void nameInputEnterPressed(java.awt.event.KeyEvent evt){
        if(evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER)this.addNameToList();
    }

    private void addButtonPressed(java.awt.event.ActionEvent evt) { 
        this.addNameToList();
    }                                          

    private void startButtonPressed(java.awt.event.ActionEvent evt) { 
        this.choosePlayerInList(0);
        //PLAYERS IM GRAU
        if(this.namesList.getModel().getSize()>=2){
            this.enableUserInput(false);
            //DIE KARTENVERLAGERUNG ZURÜCKSETZEN
            this.resetGameProgress();

            //NAMEN KRIEGEN
            String[] namesArray = new String[namesList.getModel().getSize()];
            for(int i = 0; i< namesList.getModel().getSize();i++){
                namesArray[i]=(namesList.getModel().getElementAt(i));
            }

            this.logic = null;
            this.logic = new Memory(this.defineGridSize(Integer.parseInt(this.cardsNumber.getText())),namesArray);

            //KARTEN ANBAUEN
            this.createGrid(logic.getGrid());
        } else {this.showPopUp("Add at least 2 Players");}

        this.namesList.setSelectionBackground(Color.lightGray);
    } 

    private void resetButtonPressed(java.awt.event.ActionEvent evt) {     
        //GLEICH WIE BEI START_TASTE
        this.enableUserInput(true);
        resetGameProgress();
        this.namesList.setSelectionBackground(Color.CYAN);
    }                                             

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> {
                new GUI().setVisible(true);
            });
    }

}
