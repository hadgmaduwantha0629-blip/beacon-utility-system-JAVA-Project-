/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author gevindu
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public  class CHECK_BUS extends javax.swing.JFrame {
    
 public static void showBusInfo(JPanel PANEL_1) {
     
  // Remove all existing components from PANEL_1
  PANEL_1.removeAll();

    // Create DefaultTableModel with column headers matching your table
    DefaultTableModel tableModel = new DefaultTableModel(new String[]{
        "BUS_ID", "ROUTE_NO", "START_DES", "STOP_DES", "AVAILABE_DAYS", "AC_Status"
    }, 0);

    // Create JTable and add it to a JScrollPane
    JTable table = new JTable(tableModel);
    JScrollPane scrollPane = new JScrollPane(table);

    // Add JScrollPane to PANEL_1
    PANEL_1.add(scrollPane);

    // Database connection and data retrieval
  try {
        // Connect to the database
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/BUS", "root", "");
         String checkroute = "to be initializing in next steps"; 
             if(   ROUTE.getSelectedItem() == "Route_01 - (Between Kalutara And Colombo)" ) {checkroute = "R1";}
             else if(   ROUTE.getSelectedItem() == "Route_02 - (Between Panadura And Horana)"  ) {checkroute = "R2";}
              else if(   ROUTE.getSelectedItem() == "Route_03 - (Between Panadura And MahaNuwara)"  ) {checkroute = "R3";}
               else if(   ROUTE.getSelectedItem() == "Route_04 - (Between Galle And Colombo)"  ) {checkroute = "R4";}
             
    // Query to fetch data 
    String query = "SELECT * FROM bus_info WHERE ROUTE_NO = ?";
    PreparedStatement pstmt = conn.prepareStatement(query);
    pstmt.setString(1, checkroute);

    ResultSet rs = pstmt.executeQuery();

        // Populate the table with data from ResultSet
        while (rs.next()) {
            int BUS_ID = rs.getInt("BUS_ID");
            String ROUTE_NO = rs.getString("ROUTE_NO");
            String START_DES = rs.getString("START_DES");
            String STOP_DES = rs.getString("STOP_DES");
            String AVAILABE_DAYS = rs.getString("AVAILABE_DAYS");
            String AC_Status = rs.getString("AC_Status");
           
            // Add a row to the table model
            tableModel.addRow(new Object[]{BUS_ID, ROUTE_NO, START_DES, STOP_DES, AVAILABE_DAYS, AC_Status});
        }

        // Close connections
        rs.close();
        pstmt.close();
        conn.close();

  } catch (SQLException e) {
        // Show error message in case of a database error
        JOptionPane.showMessageDialog(PANEL_1, "Error loading data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    }

    // Refresh the panel to show the newly added components
    PANEL_1.revalidate(); // Revalidate to refresh the panel
    PANEL_1.repaint();   // Repaint to ensure the changes are displayed
    
       }
    
    public static void showARRIVAL_TIMES(JPanel PANAL_22) {
   
    // Remove all existing components from PANEL_1
    PANAL_22.removeAll();

    // Create DefaultTableModel with column headers matching your table
    DefaultTableModel tableModel = new DefaultTableModel(new String[]{
        "BUS_ID","START_DESTINATION_NAME","ARRIVAL_TIME","STOP_DESTINATION_NAME","ARRIVAL_TIME"
    }, 0);

    // Create JTable and add it to a JScrollPane
    JTable table = new JTable(tableModel);
    JScrollPane scrollPane = new JScrollPane(table);

    // Add JScrollPane to PANEL_1
    PANAL_22.add(scrollPane);

    // Database connection and data retrieval
    try {
        // Connect to the database
        
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/BUS", "root", "");
        
        // Preliminary validation query
        String validationQuery = "SELECT COUNT(*) AS count FROM route_1_times WHERE DESTINATION_NAME IN (?, ?) AND BUS_ID = ?";
        PreparedStatement validationStmt = conn.prepareStatement(validationQuery);
        validationStmt.setString(1, (String) START_DESTINATION.getSelectedItem());
        validationStmt.setString(2, (String) STOP_DESTINATION.getSelectedItem());
        validationStmt.setString(3, (String) BUSID.getSelectedItem());

        ResultSet validationRs = validationStmt.executeQuery();

        // Check if records exist
        if (validationRs.next() && validationRs.getInt("count") < 2) {
            // If less than 2 records (start and stop), show error
            JOptionPane.showMessageDialog(PANAL_22, "Invalid input! Check BUS_ID, Start Destination, or Stop Destination.", "Input Error", JOptionPane.ERROR_MESSAGE);

            validationRs.close();
            validationStmt.close();
            conn.close();
            return; // Exit the method if validation fails
        }

        validationRs.close();
        validationStmt.close();
      //------------------------------------------------------
        Map<String, String[]> routeMap = new HashMap<>();
        routeMap.put("Route_01 - (Between Kalutara And Colombo)", Needmethod.aryRoute_1);
         routeMap.put("Route_02 - (Between Panadura And Horana)", Needmethod.aryRoute_2);
          routeMap.put("Route_03 - (Between Panadura And MahaNuwara)", Needmethod.aryRoute_3);
           routeMap.put("Route_04 - (Between Galle And Colombo)", Needmethod.aryRoute_4);
           
        String selectedRoute = (String) ROUTE.getSelectedItem(); // ROUTE is the combo box for selecting the route
        String[] currentRouteArray = routeMap.get(selectedRoute);

        int startdes =0; 
        int stopdes =0;

            if (START_DESTINATION.getSelectedItem() != STOP_DESTINATION.getSelectedItem()) {
                Object selectedItem = START_DESTINATION.getSelectedItem();
                int aryno1 = 0;

                 while (aryno1 < currentRouteArray.length && !selectedItem.equals(currentRouteArray[aryno1])) {
                    aryno1++;
                 }

            if (aryno1 >= currentRouteArray.length) {
                javax.swing.JOptionPane.showMessageDialog(null, "Invalid start destination selected.");
                    return;
                }
                startdes = aryno1 + 1;

                Object selectedItem2 = STOP_DESTINATION.getSelectedItem();
                int aryno2 = 0;

                while (aryno2 < currentRouteArray.length && !selectedItem2.equals(currentRouteArray[aryno2])) {
                    aryno2++;
                }

                if (aryno2 >= currentRouteArray.length) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Invalid stop destination selected.");
                    return;
                }

                stopdes = aryno2 + 1;
            }
       // ------------------------------------------------------        
         String query = "";
         String query2 = "";
         String Time ="";
        
        
            String turnQuery = "SELECT DESTINATION_NAME,ARRIVAL_TIME,BUS_ID FROM route_1_times WHERE DESTINATION_NAME = ? AND BUS_ID=?";
            String turnQuery2 = "SELECT DESTINATION_NAME,ARRIVAL_TIME,BUS_ID FROM route_1_times WHERE DESTINATION_NAME = ? AND BUS_ID=?";

            String returnQuery = "SELECT DESTINATION_NAME,RETURN_ARRIVAL_TIME,BUS_ID FROM route_1_times WHERE DESTINATION_NAME = ? AND BUS_ID=?";
            String returnQuery2 = "SELECT DESTINATION_NAME,RETURN_ARRIVAL_TIME,BUS_ID FROM route_1_times WHERE DESTINATION_NAME = ? AND BUS_ID=?";

            String TURN_TIME = "ARRIVAL_TIME";
            String RETURN_TIME = "RETURN_ARRIVAL_TIME";
            
         //Query to fetch data 
        if(startdes < stopdes)
        {
         query = turnQuery;
         query2 = turnQuery2;
         Time = TURN_TIME;
         
         PreparedStatement pstmt = conn.prepareStatement(query);
         PreparedStatement pstmt1 = conn.prepareStatement(query2);
         pstmt.setString(1,(String)START_DESTINATION.getSelectedItem());
         pstmt.setString(2,(String)BUSID.getSelectedItem());
         pstmt1.setString(1,(String)STOP_DESTINATION.getSelectedItem());
         pstmt1.setString(2,(String)BUSID.getSelectedItem());

     ResultSet rs = pstmt.executeQuery() ;
     ResultSet rs1 = pstmt1.executeQuery();
        // Populate the table with data from ResultSet
        while (rs.next()&& rs1.next()) {
            
            String START_DESTINATION_NAME = rs.getString("DESTINATION_NAME");
            String START_ARRIVAL_TIME = rs.getString(Time);
            String STOP_DESTINATION_NAME = rs1.getString("DESTINATION_NAME");
            String STOP_ARRIVAL_TIME = rs1.getString(Time);
            String BUS_ID = rs.getString("BUS_ID");
            // Add a row to the table model
            tableModel.addRow(new Object[]{BUS_ID,START_DESTINATION_NAME,START_ARRIVAL_TIME,STOP_DESTINATION_NAME,STOP_ARRIVAL_TIME});
        }
        rs.close();
        pstmt.close();
        rs1.close();
        pstmt1.close();
        conn.close();
        }
        else if(startdes > stopdes)
        {
         query = returnQuery;
         query2 = returnQuery2;   
         Time = RETURN_TIME;
         
         PreparedStatement pstmt = conn.prepareStatement(query);
         PreparedStatement pstmt1 = conn.prepareStatement(query2);
         pstmt.setString(1,(String)START_DESTINATION.getSelectedItem());
         pstmt.setString(2,(String)BUSID.getSelectedItem());
         pstmt1.setString(1,(String)STOP_DESTINATION.getSelectedItem());
         pstmt1.setString(2,(String)BUSID.getSelectedItem());

        ResultSet rs = pstmt.executeQuery() ;
        ResultSet rs1 = pstmt1.executeQuery();
        // Populate the table with data from ResultSet
        while (rs.next()&& rs1.next()) {
            
            String START_DESTINATION_NAME = rs1.getString("DESTINATION_NAME");
            String START_ARRIVAL_TIME = rs1.getString(Time);
            String STOP_DESTINATION_NAME = rs.getString("DESTINATION_NAME");
            String STOP_ARRIVAL_TIME = rs.getString(Time);
            String BUS_ID = rs.getString("BUS_ID");
            // Add a row to the table model
            tableModel.addRow(new Object[]{BUS_ID,STOP_DESTINATION_NAME,START_ARRIVAL_TIME,START_DESTINATION_NAME,STOP_ARRIVAL_TIME});
        }
        rs.close();
        pstmt.close();
        rs1.close();
        pstmt1.close();
        conn.close();
        }

    } catch (SQLException e) {
        // Show error message in case of a database error
        JOptionPane.showMessageDialog(PANAL_22, "Error loading data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    }

    // Refresh the panel to show the newly added components
    PANAL_22.revalidate(); // Revalidate to refresh the panel
    PANAL_22.repaint();   // Repaint to ensure the changes are displayed

}   
   
       
    

public static void CALCULATE_PRICE() {
    try {
        // Map to store route names and corresponding arrays
        Map<String, String[]> routeMap = new HashMap<>();
        routeMap.put("Route_01 - (Between Kalutara And Colombo)", Needmethod.aryRoute_1);
         routeMap.put("Route_02 - (Between Panadura And Horana)", Needmethod.aryRoute_2);
          routeMap.put("Route_03 - (Between Panadura And MahaNuwara)", Needmethod.aryRoute_3);
           routeMap.put("Route_04 - (Between Galle And Colombo)", Needmethod.aryRoute_4);
     
        // Add more routes as needed

        // Get the selected route
        String selectedRoute = (String) ROUTE.getSelectedItem(); // ROUTE is the combo box for selecting the route
        String[] currentRouteArray = routeMap.get(selectedRoute);

        if (currentRouteArray == null) {
            javax.swing.JOptionPane.showMessageDialog(null, "Invalid route selected.");
            return;
        }

        // Connect to the database
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/BUS", "root", "");

        // Query to fetch data
        String query = "SELECT MAX3Halts, OVER3Halts FROM TICKET_PRICE";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        // Populate the table with data from ResultSet
        while (rs.next()) {
            int max3halts = rs.getInt("MAX3Halts");
            int over3halts = rs.getInt("OVER3Halts");

            if (START_DESTINATION.getSelectedItem() != STOP_DESTINATION.getSelectedItem()) {
                Object selectedItem = START_DESTINATION.getSelectedItem();
                int aryno1 = 0;

                while (aryno1 < currentRouteArray.length && !selectedItem.equals(currentRouteArray[aryno1])) {
                    aryno1++;
                }

                if (aryno1 >= currentRouteArray.length) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Invalid start destination selected.");
                    return;
                }

                int startPoint = aryno1 + 1;

                Object selectedItem2 = STOP_DESTINATION.getSelectedItem();
                int aryno2 = 0;

                while (aryno2 < currentRouteArray.length && !selectedItem2.equals(currentRouteArray[aryno2])) {
                    aryno2++;
                }

                if (aryno2 >= currentRouteArray.length) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Invalid stop destination selected.");
                    return;
                }

                int endPoint = aryno2 + 1;

                int noOfbushalts = endPoint - startPoint;
                if (noOfbushalts <= 3 && noOfbushalts >= -3) {
                    TICKET_PRICE.setText("LKR " + max3halts + ".00");
                    System.out.println("TICKET PRICE : LKR " + max3halts + ".00");
                } else {
                    if (noOfbushalts > 0) {
                        int extraBushaltsPlus = noOfbushalts - 3;
                        TICKET_PRICE.setText("LKR " + ((over3halts * extraBushaltsPlus) + max3halts) + ".00");
                        System.out.println("TICKET PRICE : LKR " + ((over3halts * extraBushaltsPlus) + max3halts) + ".00");
                    } else {
                        int extraBushaltsMinus = noOfbushalts + 3;
                        TICKET_PRICE.setText("LKR " + (((-over3halts) * extraBushaltsMinus) + max3halts) + ".00");
                        System.out.println("TICKET PRICE : LKR " + (((-over3halts) * extraBushaltsMinus) + max3halts) + ".00");
                    }
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Start & Stop Destination Cannot Be Same or null");
            }
        }

        // Close connections
        rs.close();
        stmt.close();
        conn.close();
    } catch (SQLException e) {
        System.out.println("Database error.");
        e.printStackTrace();
    }
}

    public void selectRoute(){
     Object selectedItem = ROUTE.getSelectedItem();
    if (selectedItem == null) {
        return;  // Exit the method if there's no selection
    }

    // Check selected item in combox2 and update combox3 based on its value
    if (selectedItem.equals("Route_01 - (Between Kalutara And Colombo)")) {
       START_DESTINATION.removeAllItems();
       STOP_DESTINATION.removeAllItems();
       for (String busStop : Needmethod.aryRoute_1) {
            START_DESTINATION.addItem(busStop);
            STOP_DESTINATION.addItem(busStop);
        }   
    } 
    else if (selectedItem.equals("Route_02 - (Between Panadura And Horana)")) {
       START_DESTINATION.removeAllItems();
       STOP_DESTINATION.removeAllItems();
       for (String busStop : Needmethod.aryRoute_2) {
            START_DESTINATION.addItem(busStop);
            STOP_DESTINATION.addItem(busStop);
        }   
    } 
    else if (selectedItem.equals("Route_03 - (Between Panadura And MahaNuwara)")) {
       START_DESTINATION.removeAllItems();
       STOP_DESTINATION.removeAllItems();
       for (String busStop : Needmethod.aryRoute_3) {
            START_DESTINATION.addItem(busStop);
            STOP_DESTINATION.addItem(busStop);
        }   
    } else if (selectedItem.equals("Route_04 - (Between Galle And Colombo)")) {
       START_DESTINATION.removeAllItems();
       STOP_DESTINATION.removeAllItems();
       for (String busStop : Needmethod.aryRoute_4) {
            START_DESTINATION.addItem(busStop);
            STOP_DESTINATION.addItem(busStop);
        }   
    } 
    
    }
    public CHECK_BUS() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PANEL_1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        ROUTE = new javax.swing.JComboBox<>();
        START_DESTINATION = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        STOP_DESTINATION = new javax.swing.JComboBox<>();
        TICKET_PRICE = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        CHECK_BUS = new javax.swing.JButton();
        PANAL_2 = new javax.swing.JPanel();
        CHECK_TIME = new javax.swing.JButton();
        BUSID = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        PANAL_22 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PANEL_1.setBackground(new java.awt.Color(204, 204, 255));
        PANEL_1.setAutoscrolls(true);
        PANEL_1.setLayout(new java.awt.BorderLayout());
        getContentPane().add(PANEL_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 497, 1195, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("CHOOSE BUS ROUTE                     :");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 97, 236, 29));

        jLabel2.setFont(new java.awt.Font("Stencil", 0, 24)); // NOI18N
        jLabel2.setText("CHECK BUS STATUS");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(166, 0, -1, 50));

        ROUTE.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Route_01 - (Between Kalutara And Colombo)", "Route_02 - (Between Panadura And Horana)", "Route_03 - (Between Panadura And MahaNuwara)", "Route_04 - (Between Galle And Colombo)" }));
        ROUTE.setSelectedIndex(-1);
        ROUTE.setSelectedItem(-1);
        ROUTE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ROUTEActionPerformed(evt);
            }
        });
        getContentPane().add(ROUTE, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 101, 270, -1));

        START_DESTINATION.setSelectedItem(-1);
        START_DESTINATION.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                START_DESTINATIONActionPerformed(evt);
            }
        });
        getContentPane().add(START_DESTINATION, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 148, 212, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("CHOOSE START DESTINATION     :");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 144, 236, 29));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("TICKET PRICE       :");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 301, 141, 29));

        STOP_DESTINATION.setSelectedItem(-1);
        getContentPane().add(STOP_DESTINATION, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 210, 212, -1));

        TICKET_PRICE.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        getContentPane().add(TICKET_PRICE, new org.netbeans.lib.awtextra.AbsoluteConstraints(239, 291, 157, 45));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("CHOOSE STOP DESTINATION       :");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 236, 29));

        CHECK_BUS.setBackground(new java.awt.Color(255, 102, 0));
        CHECK_BUS.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        CHECK_BUS.setForeground(new java.awt.Color(255, 255, 255));
        CHECK_BUS.setText("CHECK BUS");
        CHECK_BUS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CHECK_BUSActionPerformed(evt);
            }
        });
        getContentPane().add(CHECK_BUS, new org.netbeans.lib.awtextra.AbsoluteConstraints(184, 253, 126, 32));

        PANAL_2.setBackground(new java.awt.Color(102, 102, 102));
        PANAL_2.setLayout(new java.awt.BorderLayout());
        getContentPane().add(PANAL_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(496, 0, -1, 367));

        CHECK_TIME.setBackground(new java.awt.Color(51, 153, 255));
        CHECK_TIME.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        CHECK_TIME.setText("CHECK BUS TIMES");
        CHECK_TIME.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CHECK_TIMEActionPerformed(evt);
            }
        });
        getContentPane().add(CHECK_TIME, new org.netbeans.lib.awtextra.AbsoluteConstraints(234, 374, -1, -1));

        BUSID.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "7", "8", "10", "11" }));
        getContentPane().add(BUSID, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 374, 63, 29));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("CHOOSE BUS ID :");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 373, 141, 29));

        PANAL_22.setLayout(new java.awt.GridLayout(1, 0));
        getContentPane().add(PANAL_22, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 6, -1, -1));
        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jButton1.setBackground(new java.awt.Color(0, 204, 204));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setText("SWAP");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 180, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void ROUTEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ROUTEActionPerformed
        selectRoute();
        // TODO add your handling code here:
    }//GEN-LAST:event_ROUTEActionPerformed

    private void CHECK_BUSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CHECK_BUSActionPerformed
        CALCULATE_PRICE();
        showBusInfo(PANEL_1);
        
        
    }//GEN-LAST:event_CHECK_BUSActionPerformed

    private void START_DESTINATIONActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_START_DESTINATIONActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_START_DESTINATIONActionPerformed

    private void CHECK_TIMEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CHECK_TIMEActionPerformed
           showARRIVAL_TIMES(PANAL_22);       
    }//GEN-LAST:event_CHECK_TIMEActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
           
         Object startSelectedItem = START_DESTINATION.getSelectedItem();  // Store the selected item of START_DESTINATION
START_DESTINATION.setSelectedItem(STOP_DESTINATION.getSelectedItem());  // Set START_DESTINATION to the selected item of STOP_DESTINATION
STOP_DESTINATION.setSelectedItem(startSelectedItem);  // Set STOP_DESTINATION to the previously selected item of START_DESTINATION
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CHECK_BUS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CHECK_BUS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CHECK_BUS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CHECK_BUS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CHECK_BUS().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JComboBox<String> BUSID;
    private static javax.swing.JButton CHECK_BUS;
    private static javax.swing.JButton CHECK_TIME;
    private static javax.swing.JPanel PANAL_2;
    private javax.swing.JPanel PANAL_22;
    private javax.swing.JPanel PANEL_1;
    private static javax.swing.JComboBox<String> ROUTE;
    private static javax.swing.JComboBox<String> START_DESTINATION;
    private static javax.swing.JComboBox<String> STOP_DESTINATION;
    private static javax.swing.JTextField TICKET_PRICE;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables

   
}
