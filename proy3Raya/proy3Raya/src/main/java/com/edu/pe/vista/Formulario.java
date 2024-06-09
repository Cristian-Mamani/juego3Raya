package com.edu.pe.vista;

import com.edu.pe.modelo.Result;
import com.edu.pe.modelo.dao.ResultDAO;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Formulario extends javax.swing.JFrame {

    private ResultDAO resultadoDao = new ResultDAO();
    private JButton botones[][];
    private int turno;
    Result obj = null;

    public Formulario() {
        initComponents();

        Reiniciar();
        DesHabilitarTodos();
        ListarTodos();
    }

    public void ListarTodos() {
        DefaultTableModel df = (DefaultTableModel) tabla.getModel();
        df.setRowCount(0);

        ArrayList<Result> lista = resultadoDao.Listar();

        for (Result obj : lista) {
            df.addRow(new Object[]{
                obj.getNombrePartida(),
                obj.getNombreJugador1(),
                obj.getNombreJugador2(),
                obj.getNombreGanador(),
                obj.getPuntuacion(),
                obj.getEstado()
            });
        }
    }

    public void Reiniciar() {
        botones = new JButton[3][3];
        botones[0][0] = btnSeleccion1;
        botones[0][1] = btnSeleccion2;
        botones[0][2] = btnSeleccion3;
        botones[1][0] = btnSeleccion4;
        botones[1][1] = btnSeleccion5;
        botones[1][2] = btnSeleccion6;
        botones[2][0] = btnSeleccion7;
        botones[2][1] = btnSeleccion8;
        botones[2][2] = btnSeleccion9;

        turno = 1;

        for (int i = 0; i < botones.length; i++) {
            for (int j = 0; j < botones[0].length; j++) {
                JButton boton = botones[i][j];
                boton.setText("");
                boton.setEnabled(true);
                boton.setFont(new java.awt.Font("Segoe UI", 1, 18));
            }
        }
        QuienJuega();
        btnAnular.setEnabled(false);
    }

    public void DesHabilitarTodos() {
        for (int i = 0; i < botones.length; i++) {
            for (int j = 0; j < botones[0].length; j++) {
                JButton boton = botones[i][j];
                boton.setEnabled(false);
            }
        }
    }

    public void MarcarSeleccion(int opcion) {
        int nro = 1;

        for (int i = 0; i < botones.length; i++) {
            for (int j = 0; j < botones[0].length; j++) {
                JButton boton = botones[i][j];
                if (boton.getText().equals("") && nro == opcion) {
                    if (turno % 2 == 0) {
                        boton.setText("X");
                    } else {
                        boton.setText("O");
                    }
                    boton.setEnabled(false);
                    turno++;
                }
                nro++;
            }
        }

        boolean validarGan = ValidarGanador();
        if (validarGan == false) {
            if (ValidarJuegoSiTermino() == false) {
                QuienJuega();
            } else {
                JOptionPane.showMessageDialog(null, "Juego terminado! Nadie gano ; (");
                ActualizarGanador("Empate", 0, "Terminado");
                btnIniciar.setEnabled(true);
                btnAnular.setEnabled(false);
            }
        } else {
            ActualizarGanador(txtTurnoJugador.getText(), 1, "Terminado");
            btnIniciar.setEnabled(true);
            btnAnular.setEnabled(false);
        }

    }

    public boolean ValidarJuegoSiTermino() {
        int contador = 0;
        for (int i = 0; i < botones.length; i++) {
            for (int j = 0; j < botones[0].length; j++) {
                JButton boton = botones[i][j];

                if (boton.getText().equals("")) {
                    contador++;
                }
            }
        }

        if (contador == 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean ValidarGanador() {
        if (ValidarGanadorFila() == true || ValidarGanadorColumna() == true
                || ValidarGanadorDiagonalPrincipal() == true
                || ValidarGanadorDiagonalSecundaria() == true) {
            JOptionPane.showMessageDialog(null, "El Ganador fue " + txtTurnoJugador.getText());
            DesHabilitarTodos();
            return true;
        }
        return false;
    }

    public boolean ValidarGanadorFila() {
        int contadorX = 0, contadorO = 0;
        boolean ganador = false;

        for (int i = 0; i < botones.length; i++) {
            contadorX = 0;
            contadorO = 0;
            for (int j = 0; j < botones[0].length; j++) {
                JButton boton = botones[i][j];

                if (!boton.getText().equals("")) {
                    if (boton.getText().equals("X")) {
                        contadorX++;
                    }
                    if (boton.getText().equals("O")) {
                        contadorO++;
                    }
                }
            }

            if (contadorX == 3 || contadorO == 3) {
                ganador = true;
                break;
            }
        }

        return ganador;
    }

    public boolean ValidarGanadorColumna() {
        int contadorX = 0, contadorO = 0;
        boolean ganador = false;

        for (int i = 0; i < botones[0].length; i++) {
            contadorX = 0;
            contadorO = 0;
            for (int j = 0; j < botones.length; j++) {
                JButton boton = botones[j][i];

                if (!boton.getText().equals("")) {
                    if (boton.getText().equals("X")) {
                        contadorX++;
                    }
                    if (boton.getText().equals("O")) {
                        contadorO++;
                    }
                }
            }

            if (contadorX == 3 || contadorO == 3) {
                ganador = true;
                break;
            }
        }
        return ganador;
    }

    public boolean ValidarGanadorDiagonalPrincipal() {
        int contadorX = 0, contadorO = 0, j = 0;
        boolean ganador = false;

        for (int i = 0; i < botones.length; i++) {
            JButton boton = botones[i][j];
            j++;

            if (!boton.getText().equals("")) {
                if (boton.getText().equals("X")) {
                    contadorX++;
                }
                if (boton.getText().equals("O")) {
                    contadorO++;
                }
            }
            if (contadorX == 3 || contadorO == 3) {
                ganador = true;
                break;
            }
        }

        return ganador;
    }

    public boolean ValidarGanadorDiagonalSecundaria() {
        int contadorX = 0, contadorO = 0, j = botones[0].length - 1;
        boolean ganador = false;

        for (int i = 0; i < botones.length; i++) {
            JButton boton = botones[i][j];
            j--;

            if (!boton.getText().equals("")) {
                if (boton.getText().equals("X")) {
                    contadorX++;
                }
                if (boton.getText().equals("O")) {
                    contadorO++;
                }
            }
            if (contadorX == 3 || contadorO == 3) {
                ganador = true;
                break;
            }
        }
        return ganador;
    }

    public void QuienJuega() {
        if (turno % 2 != 0) {
            txtTurnoJugador.setText(txtJugador1.getText());
        } else {
            txtTurnoJugador.setText(txtJugador2.getText());
        }
    }

    public void ActualizarGanador(String ganador, int puntuacion, String estado) {
        if (obj != null) {
            obj.setNombreGanador(ganador);
            obj.setPuntuacion(puntuacion);
            obj.setEstado(estado);

            int result = resultadoDao.Actualizar(obj);

            if (result == 0) {
                JOptionPane.showMessageDialog(null, "No se pudo actualizar partida!");
                DesHabilitarTodos();
            } else {
                ListarTodos();
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnSeleccion1 = new javax.swing.JButton();
        btnSeleccion2 = new javax.swing.JButton();
        btnSeleccion3 = new javax.swing.JButton();
        btnSeleccion4 = new javax.swing.JButton();
        btnSeleccion6 = new javax.swing.JButton();
        btnSeleccion5 = new javax.swing.JButton();
        btnSeleccion7 = new javax.swing.JButton();
        btnSeleccion8 = new javax.swing.JButton();
        btnSeleccion9 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtTurnoJugador = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtJugador1 = new javax.swing.JTextField();
        txtJugador2 = new javax.swing.JTextField();
        btnIniciar = new javax.swing.JButton();
        btnAnular = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 255, 102));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("EVALUACION UNIDAD 2");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Nombre:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Cristian Yeyson Mamani Morales");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3))
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 153, 153));

        btnSeleccion1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccion1ActionPerformed(evt);
            }
        });

        btnSeleccion2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccion2ActionPerformed(evt);
            }
        });

        btnSeleccion3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccion3ActionPerformed(evt);
            }
        });

        btnSeleccion4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccion4ActionPerformed(evt);
            }
        });

        btnSeleccion6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccion6ActionPerformed(evt);
            }
        });

        btnSeleccion5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccion5ActionPerformed(evt);
            }
        });

        btnSeleccion7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccion7ActionPerformed(evt);
            }
        });

        btnSeleccion8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccion8ActionPerformed(evt);
            }
        });

        btnSeleccion9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccion9ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Turno:");

        txtTurnoJugador.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtTurnoJugador.setText("txtTurnoJugador");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnSeleccion1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSeleccion2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSeleccion3, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnSeleccion4, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSeleccion5, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSeleccion6, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(btnSeleccion7, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnSeleccion8, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSeleccion9, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtTurnoJugador))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSeleccion3, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSeleccion2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSeleccion1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSeleccion5, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSeleccion6, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSeleccion4, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSeleccion8, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSeleccion9, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSeleccion7, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTurnoJugador))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(0, 204, 204));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Nombre Jugador 1:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Nombre Jugador 2:");

        btnIniciar.setText("Iniciar");
        btnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarActionPerformed(evt);
            }
        });

        btnAnular.setText("Anular");
        btnAnular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnularActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 204, 204));

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre Partida", "Nombre Jugador 1", "Nombre Jugador 2", "Nombre Ganador", "Puntacion", "Estado"
            }
        ));
        jScrollPane1.setViewportView(tabla);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtJugador2, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtJugador1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnAnular, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtJugador1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtJugador2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAnular, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSeleccion1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccion1ActionPerformed
        MarcarSeleccion(1);
    }//GEN-LAST:event_btnSeleccion1ActionPerformed

    private void btnSeleccion2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccion2ActionPerformed
        MarcarSeleccion(2);
    }//GEN-LAST:event_btnSeleccion2ActionPerformed

    private void btnSeleccion3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccion3ActionPerformed
        MarcarSeleccion(3);
    }//GEN-LAST:event_btnSeleccion3ActionPerformed

    private void btnSeleccion4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccion4ActionPerformed
        MarcarSeleccion(4);
    }//GEN-LAST:event_btnSeleccion4ActionPerformed

    private void btnSeleccion5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccion5ActionPerformed
        MarcarSeleccion(5);
    }//GEN-LAST:event_btnSeleccion5ActionPerformed

    private void btnSeleccion6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccion6ActionPerformed
        MarcarSeleccion(6);
    }//GEN-LAST:event_btnSeleccion6ActionPerformed

    private void btnSeleccion7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccion7ActionPerformed
        MarcarSeleccion(7);
    }//GEN-LAST:event_btnSeleccion7ActionPerformed

    private void btnSeleccion8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccion8ActionPerformed
        MarcarSeleccion(8);
    }//GEN-LAST:event_btnSeleccion8ActionPerformed

    private void btnSeleccion9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccion9ActionPerformed
        MarcarSeleccion(9);
    }//GEN-LAST:event_btnSeleccion9ActionPerformed

    private void btnAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnularActionPerformed
        if (obj != null) {
            obj.setNombreGanador("Anulado");
            obj.setPuntuacion(0);
            obj.setEstado("Anulado");

            int result = resultadoDao.Actualizar(obj);

            if (result == 0) {
                JOptionPane.showMessageDialog(null, "No se pudo anular partida!");
                DesHabilitarTodos();
            } else {
                ListarTodos();
            }
        }

        txtJugador1.setText("");
        txtJugador2.setText("");
        Reiniciar();
        DesHabilitarTodos();
        btnIniciar.setEnabled(true);
        btnAnular.setEnabled(false);
    }//GEN-LAST:event_btnAnularActionPerformed

    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
        if (txtJugador1.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Ingrese un nombre para el Jugador 1");
            return;
        }

        if (txtJugador2.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Ingrese un nombre para el Jugador 2");
            return;
        }

        obj = new Result();
        obj.setNombreGanador("");
        obj.setNombreJugador1(txtJugador1.getText());
        obj.setNombreJugador2(txtJugador2.getText());
        obj.setPuntuacion(0);
        obj.setEstado("Jugando");
        obj.setNombrePartida("Partida " + resultadoDao.MaximaPartida());

        int result = resultadoDao.Insertar(obj);

        if (result > 0) {
            obj.setId(result);
            Reiniciar();
            ListarTodos();
            btnIniciar.setEnabled(false);
            btnAnular.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo guardar inicio partida!");
            DesHabilitarTodos();
        }
    }//GEN-LAST:event_btnIniciarActionPerformed

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
            java.util.logging.Logger.getLogger(Formulario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Formulario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Formulario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Formulario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Formulario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnular;
    private javax.swing.JButton btnIniciar;
    private javax.swing.JButton btnSeleccion1;
    private javax.swing.JButton btnSeleccion2;
    private javax.swing.JButton btnSeleccion3;
    private javax.swing.JButton btnSeleccion4;
    private javax.swing.JButton btnSeleccion5;
    private javax.swing.JButton btnSeleccion6;
    private javax.swing.JButton btnSeleccion7;
    private javax.swing.JButton btnSeleccion8;
    private javax.swing.JButton btnSeleccion9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla;
    private javax.swing.JTextField txtJugador1;
    private javax.swing.JTextField txtJugador2;
    private javax.swing.JLabel txtTurnoJugador;
    // End of variables declaration//GEN-END:variables
}
