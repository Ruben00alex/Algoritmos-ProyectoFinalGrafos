/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package proyecto.pkgfinal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.TableColumn;

/**
 *
 * @author macbook
 */
public class GUI extends javax.swing.JFrame {

    //Atributos
    Grafo g;
    ArrayList<Grafo> lista;
    int pos;

    /**
     * Creates new form GUI
     */
    public GUI() {
        initComponents();
        lista = new ArrayList();
        pos = -1;
        actualizarTabla(null);
    }

    public void setGrafo(String direccion) {
        ArrayList<String> grafos = new ArrayList();
        try ( BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(direccion), "utf-8"));) {
            String line = br.readLine();

            while (line != null) {
                grafos.add(line);
                System.out.println("line: " + line);
                line = br.readLine();
            }
        } catch (IOException ioe) {
        }

        for (String s : grafos) {
            if (verificarGrafo(s)) {
                Object[] opciones = {"Aleatorio", "Ingresar valores"};
                int opcionSeleccionada = JOptionPane.showOptionDialog(null,
                        "¿Cómo serían los valores de los vertices?",
                        "Dado gris",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        opciones,
                        opciones[0]);
                if (opcionSeleccionada == -1) {
                    opcionSeleccionada = 0;
                }

                errorLabel.setText("");
                insertarGrafo(s, opcionSeleccionada);
                mostrarGrafo(lista.get(lista.size() - 1));
            } else {
                JOptionPane.showMessageDialog(null, "El grafo " + s + " no es válido.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public boolean verificarGrafo(String grafoString) {
        String[] valores = grafoString.split(",");
        boolean concuerda = false;

        if (valores[0].matches("\\(\\d+")) //Verifica el primer elemento
        {
            if (valores[valores.length - 1].matches("\\d+\\)")) //Verifica el último elemento
            {
                if (valores.length > 2) {
                    for (int i = 1; i < valores.length - 1; i++) {
                        if (valores[i].matches("^[1-9]+$"))//Verifica los elementos interiores
                        {
                            concuerda = true;
                        } else {
                            concuerda = false;
                        }
                    }
                } else {
                    concuerda = true;
                }
            }
        }

        if (concuerda) {
            String[] gradosString = grafoString.split(",");
            Integer[] grados = new Integer[gradosString.length];

            grados[0] = Integer.parseInt(gradosString[0].substring(1));
            for (int i = 1; i < gradosString.length - 1; i++) {
                grados[i] = Integer.parseInt(gradosString[i]);
            }
            grados[grados.length - 1] = Integer.parseInt(gradosString[grados.length - 1].
                    substring(0, gradosString[grados.length - 1].length() - 1));

            concuerda = Grafo.havelHakimi(grados);
        }
        return concuerda;
    }

    public void insertarGrafo(String grafoString, int aleatorio) {
        String[] gradosString = grafoString.split(",");
        Integer[] grados = new Integer[gradosString.length];
        Random rand = new Random();

        grados[0] = Integer.parseInt(gradosString[0].substring(1));
        for (int i = 1; i < gradosString.length - 1; i++) {
            grados[i] = Integer.parseInt(gradosString[i]);
        }
        grados[grados.length - 1] = Integer.parseInt(gradosString[grados.length - 1].
                substring(0, gradosString[grados.length - 1].length() - 1));

        Integer[] valores = new Integer[grados.length];

        if (aleatorio == 0) {
            for (int i = 0; i < valores.length; i++) {
                valores[i] = rand.nextInt(100);
            }
        } else {
            for (int i = 0; i < valores.length; i++) {
                System.out.println("valores:" + valores.length + "; i: " + i);
                String valor;
                do {
                    valor = JOptionPane.showInputDialog(null, "Escriba que numero será el valor " + (i + 1) + ":");
                } while (!valor.matches("[0-9]"));
                valores[i] = Integer.parseInt(valor);
            }
        }

        g = new Grafo(grados, valores);
        //g.generarMatrizDeAdyacencia();

        lista.add(g);
        pos = lista.size() - 1;
        sigButton.setEnabled(true);
    }

    public void mostrarGrafo(Grafo grafo) {
        String textoGrafo = "Nombre: ";
        String textoGrados = "Grados:  ";
        g = grafo;

        for (int i = 0; i < g.getNumVertices(); i++) {
            if (i == g.getNumVertices() - 1) {

                textoGrados += g.getGrados()[i];
                textoGrafo += g.getVertices().get(i).getId();
            } else {
                textoGrados += g.getGrados()[i] + " , ";
                textoGrafo += g.getVertices().get(i).getId() + " , ";
            }
        }
        textoGrados+="\n";
        textoGrafo += "\n";
        grafoLabel1.setText("Valores: " + g);
        gradosLabel.setText(textoGrados);
        actualizarTabla(g.getMatrizAdyacencia());
        textPaneListaAdyacencia.setText(g.listaDeAdyacenciaToString());
        dfsLabel.setText(g.inicioDFS().toString());
        grafoLabel.setText(textoGrafo);
    }

    public void actualizarTabla(Object[][] matriz) {

        mATable.setModel(new javax.swing.table.DefaultTableModel(
                matriz,
                matriz
        )
        );
        for (int i = 0; i < mATable.getColumnModel().getColumnCount(); i++) {
            mATable.setRowHeight(220 / mATable.getColumnModel().getColumnCount());
            TableColumn column = mATable.getColumnModel().getColumn(i);

            column.setPreferredWidth(220 / mATable.getColumnModel().getColumnCount());

            column.setMaxWidth(220 / mATable.getColumnModel().getColumnCount());
            column.setMinWidth(220 / mATable.getColumnModel().getColumnCount());
        }
    }

    /**
     * Debug purposes
     *
     * @param matriz
     */
    public void mostrarMatriz(Integer[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println("\n");
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        archivoButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        grafoTextField = new javax.swing.JTextField();
        grafoLabel = new javax.swing.JLabel();
        errorLabel = new javax.swing.JLabel();
        matrizALabel = new javax.swing.JLabel();
        resultadosLabel = new javax.swing.JLabel();
        dfsLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        mATable = new javax.swing.JTable();
        listaALabel = new javax.swing.JLabel();
        limpiarButton = new javax.swing.JButton();
        sigButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        textPaneListaAdyacencia = new javax.swing.JTextPane();
        grafoLabel1 = new javax.swing.JLabel();
        gradosLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        archivoButton.setText("Seleccionar archivo");
        archivoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                archivoButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Introducir grafo:");

        grafoTextField.setText(" ");
        grafoTextField.setText("");
        grafoTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grafoTextFieldActionPerformed(evt);
            }
        });

        grafoLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        errorLabel.setForeground(new java.awt.Color(255, 0, 0));
        errorLabel.setText("Inserte un grafo válido");
        errorLabel.setText(" ");

        matrizALabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        matrizALabel.setText("Matriz de adyacencia");

        resultadosLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        resultadosLabel.setText("Resultado de DFS:");

        dfsLabel.setBackground(new java.awt.Color(255, 255, 255));
        dfsLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        dfsLabel.setText(" ");

        mATable.setBackground(new java.awt.Color(240, 240, 240));
        mATable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        mATable.setTableHeader(null);

        for (int i = 0; i < mATable.getColumnModel().getColumnCount(); i++) {
            TableColumn column = mATable.getColumnModel().getColumn(i);
            column.setPreferredWidth(10);
            column.setMaxWidth(10);
            column.setMinWidth(10);
        }
        jScrollPane1.setViewportView(mATable);

        listaALabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        listaALabel.setText("Lista de adyacencia");

        limpiarButton.setText("Limpiar");
        limpiarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limpiarButtonActionPerformed(evt);
            }
        });

        sigButton.setText("Siguiente grafo");
        sigButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sigButtonActionPerformed(evt);
            }
        });
        sigButton.setEnabled(false);

        textPaneListaAdyacencia.setEditable(false);
        textPaneListaAdyacencia.setBackground(new java.awt.Color(204, 255, 255));
        textPaneListaAdyacencia.setForeground(new java.awt.Color(0, 0, 102));
        textPaneListaAdyacencia.setCaretColor(new java.awt.Color(255, 153, 51));
        jScrollPane2.setViewportView(textPaneListaAdyacencia);

        grafoLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        gradosLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(resultadosLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dfsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(676, 676, 676)
                                .addComponent(limpiarButton))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(errorLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(archivoButton))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(gradosLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(grafoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(grafoLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(matrizALabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(listaALabel, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(grafoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sigButton)))))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(archivoButton)
                    .addComponent(jLabel1)
                    .addComponent(grafoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(errorLabel)
                    .addComponent(sigButton))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(matrizALabel)
                            .addComponent(listaALabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(gradosLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(grafoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(grafoLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resultadosLabel)
                    .addComponent(dfsLabel)
                    .addComponent(limpiarButton))
                .addGap(8, 8, 8))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void archivoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_archivoButtonActionPerformed
        ChooseFile cF = new ChooseFile(this, true, true);
        cF.setVisible(true);
        cF.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        if (cF.getDireccion() != null) {
            setGrafo(cF.getDireccion());
        }
    }//GEN-LAST:event_archivoButtonActionPerformed

    private void grafoTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grafoTextFieldActionPerformed
        if (verificarGrafo(grafoTextField.getText())) {
            Object[] opciones = {"Aleatorio", "Ingresar valores"};
            int opcionSeleccionada = JOptionPane.showOptionDialog(null,
                    "¿Cómo serían los valores de los vertices?",
                    "Selección de valores:",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]);
            if (opcionSeleccionada == -1) {
                opcionSeleccionada = 0;
            }

            errorLabel.setText("");
            insertarGrafo(grafoTextField.getText(), opcionSeleccionada);
            mostrarGrafo(lista.get(lista.size() - 1));
        } else
            errorLabel.setText("Inserte un grafo válido");
    }//GEN-LAST:event_grafoTextFieldActionPerformed

    private void limpiarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limpiarButtonActionPerformed
        gradosLabel.setText("");
        lista.clear();
        pos = -1;
        actualizarTabla(null);
        grafoLabel.setText("");
        grafoLabel1.setText("");
        textPaneListaAdyacencia.setText("");
        grafoTextField.setText("");
        dfsLabel.setText(" ");
        sigButton.setEnabled(false);
    }//GEN-LAST:event_limpiarButtonActionPerformed

    private void sigButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sigButtonActionPerformed
        pos++;
        if (pos >= lista.size()) {
            pos = 0;
        }
        mostrarGrafo(lista.get(pos));
    }//GEN-LAST:event_sigButtonActionPerformed

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
                    javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException
                | InstantiationException
                | IllegalAccessException
                | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton archivoButton;
    private javax.swing.JLabel dfsLabel;
    private javax.swing.JLabel errorLabel;
    private javax.swing.JLabel gradosLabel;
    private javax.swing.JLabel grafoLabel;
    private javax.swing.JLabel grafoLabel1;
    private javax.swing.JTextField grafoTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton limpiarButton;
    private javax.swing.JLabel listaALabel;
    private javax.swing.JTable mATable;
    private javax.swing.JLabel matrizALabel;
    private javax.swing.JLabel resultadosLabel;
    private javax.swing.JButton sigButton;
    private javax.swing.JTextPane textPaneListaAdyacencia;
    // End of variables declaration//GEN-END:variables
}
