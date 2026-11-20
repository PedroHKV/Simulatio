package com.simulatio.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatDarkLaf;
import com.simulatio.simulator.JSimulator;

public class TelaPrincipal {
    
    private JFrame janela;
    private JSimulator simulator;

    private DefaultTableModel modelo;
    private JTable tabela;

    public TelaPrincipal(String TITULO, int W0, int H0){
        configurarLookAndFeel();
        janela = new JFrame(TITULO);
        janela.setSize(new Dimension(W0, H0));
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        simulator = new JSimulator();
   }

    //_______METODOS PUBLICOS__________

    public void show(){
        construirInterface(janela);
        janela.setVisible(true);
    }

    public void close(){
            janela.dispose();
    }


    //________METODOS PRIVADOS___________

    
    private void configurarLookAndFeel(){
        try {
            UIManager.setLookAndFeel( new FlatDarkLaf() );
        } catch (Exception e) {
            System.out.println("[FALHA AO CARREGAR LOOK AND FEEL]");
        }
    }
    
    private void construirInterface(JFrame j){
        j.setLayout( new BorderLayout() );

        // painel superior
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel superior = new JPanel(new GridBagLayout());
        superior.setPreferredSize(new Dimension(500, 50));
        gbc.gridy = 0;

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        JLabel titulo = new JLabel("   SIMULADOR DE GRAVIDADE: clique na tela de simulação para adicionar planetas");
        superior.add( titulo , gbc);

        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 1, 0, 10);
        gbc.gridx = 1;
        JButton retardar_btn = new JButton("<");
        superior.add( retardar_btn , gbc);
        gbc.gridx = 2;
        JTextField visor_adiantamento = new JTextField();
        visor_adiantamento.setEditable(false);
        visor_adiantamento.setEnabled(false);
        visor_adiantamento.setText("1");
        superior.add( visor_adiantamento , gbc);
        gbc.gridx = 3;
        JButton adiantar_btn = new JButton(">");
        superior.add( adiantar_btn , gbc);

        gbc.gridx = 4;
        gbc.insets = new Insets(0, 10, 0, 10);
        JButton play_pause_btn = new JButton("pause");
        superior.add( play_pause_btn, gbc);
        j.add(superior, BorderLayout.NORTH);


        // centro (SIMULADOR)
        j.add(simulator, BorderLayout.CENTER);

        // lista inferior
        String[] colunas = {"Id", "Cor", "Raio", "massa"};
        modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(colunas);
        tabela = new JTable(modelo);
        JScrollPane painelRolagem = new JScrollPane(tabela);
        painelRolagem.setPreferredSize(new Dimension(500, 230));  //ignora width e respeita height aqui, por que ??? um mistério
        j.add(painelRolagem, BorderLayout.SOUTH);

        // EVENTOS CONFIGURADOS:

        simulator.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                DialogoNovoPlaneta d = new DialogoNovoPlaneta(simulator, modelo);
                d.preInputX( e.getX() );
                d.preInputY( e.getY() );
                d.preInputVX(0);
                d.preInputVY(0);
                visor_adiantamento.setText("1");
                simulator.setAdiantamentoDeTempo(1);
                d.show();
            }
        });

        adiantar_btn.addActionListener(e -> {
            int adiantamento = Integer.parseInt(visor_adiantamento.getText());
            adiantamento += (adiantamento == 1 ) ? 9 : 10;
            visor_adiantamento.setText(adiantamento + "");
            simulator.setAdiantamentoDeTempo(adiantamento);
        });

        retardar_btn.addActionListener(e -> {
            int adiantamento = Integer.parseInt(visor_adiantamento.getText());
            adiantamento -= 10;
            if (adiantamento < 1)
                adiantamento = 1;
            visor_adiantamento.setText(adiantamento + "");
            simulator.setAdiantamentoDeTempo(adiantamento);
        });

        play_pause_btn.addActionListener(e -> {
            String pause = play_pause_btn.getText();
            if(pause.equals("pause")){
                play_pause_btn.setText("play");
                simulator.setPause(true);
                visor_adiantamento.setText("1");
                simulator.setAdiantamentoDeTempo(1);
            } else {
                play_pause_btn.setText("pause");
                simulator.setPause(false);
            }
        });

        tabela.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e){
                if (e.getKeyCode() == KeyEvent.VK_DELETE){
                    int selecionada = tabela.getSelectedRow();
                    DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
                    modelo.removeRow(selecionada);
                    simulator.remove(selecionada);
                }
            }

            @Override public void keyTyped(KeyEvent e) {}
            @Override public void keyReleased(KeyEvent e) {}
        });
    }

}
