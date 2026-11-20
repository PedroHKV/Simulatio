package com.simulatio.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.simulatio.simulator.JSimulator;
import com.simulatio.simulator.entidades.Planeta;

public class DialogoNovoPlaneta {
    
    private JFrame janela;

    private JSimulator simulator;
    private DefaultTableModel modelo;

    private JTextField x_in = new JTextField(); //estao como atributos para que possam ser controlados de fora
    private JTextField y_in = new JTextField(); //estao como atributos para que possam ser controlados de fora
    private JTextField vx_in = new JTextField(); //estao como atributos para que possam ser controlados de fora
    private JTextField vy_in = new JTextField(); //estao como atributos para que possam ser controlados de fora


    private Map<String, Color> cores = Map.of(
        "Azul", Color.BLUE, 
        "Branco", Color.WHITE, 
        "Vermelho", Color.RED,
        "Amarelo", Color.YELLOW
    );

    public DialogoNovoPlaneta(JSimulator simulator, DefaultTableModel modelo){
        this.simulator = simulator;
        this.modelo = modelo;
        simulator.setPause(true);
        janela = new JFrame("Novo planeta");
        janela.setSize(new Dimension(500, 310));
        janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    //_______METODOS PUBLICOS_____

    public void show(){
        construirInterface(janela);
        janela.setVisible(true);
    }

    public void close(){
            simulator.setPause(false);
            janela.dispose();
    }

    public void preInputX(int x){
        x_in.setText(x + "");
    }

    public void preInputY(int y){
        y_in.setText(y + "");
    }

    public void preInputVX( int vx){
        vx_in.setText(vx + "");
    }

    public void preInputVY( int vy){
        vy_in.setText(vy + "");
    }



    //______METODOS PRIVADOS______

    private void construirInterface(JFrame j) {

        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
       
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        int linha = 0;

        // título
        JLabel titulo = new JLabel("Informe os dados do novo corpo");

        gbc.gridx = 0;
        gbc.gridy = linha++;
        gbc.gridwidth = 4;
        painel.add(titulo, gbc);

        linha++;

        // tamanho
        JLabel tamanho_lbl = new JLabel("Tamanho");

        gbc.gridy = linha++;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        painel.add(tamanho_lbl, gbc);

        JLabel raio_lbl = new JLabel("Raio:");
        JTextField tamanho_in = new JTextField();

        gbc.gridwidth = 1;
        gbc.gridy = linha;

        gbc.gridx = 0;
        painel.add(raio_lbl, gbc);

        gbc.gridx = 1;
        painel.add(tamanho_in, gbc);

        linha++;
        //posição inicial
        JLabel posicao_lbl = new JLabel("Posição inicial");

        gbc.gridx = 0;
        gbc.gridy = linha++;
        gbc.gridwidth = 1;
        painel.add(posicao_lbl, gbc);


        JLabel x_lbl = new JLabel("X0:");

        JLabel y_lbl = new JLabel("Y0:");


        gbc.gridy = linha;
        
        gbc.gridx = 0;
        painel.add(x_lbl, gbc);

        gbc.gridx = 1;
        painel.add(this.x_in, gbc);

        gbc.gridx = 2;
        painel.add(y_lbl, gbc);

        gbc.gridx = 3;
        painel.add(this.y_in, gbc);

        linha++;

        // velocidade inicial
        JLabel velocidade_lbl = new JLabel("Velocidade inicial");

        gbc.gridx = 0;
        gbc.gridy = linha++;
        painel.add(velocidade_lbl, gbc);


        JLabel vx_lbl = new JLabel("Vx0:");

        JLabel vy_lbl = new JLabel("Vy0:");

        gbc.gridy = linha;

        gbc.gridx = 0;
        painel.add(vx_lbl, gbc);

        gbc.gridx = 1;
        painel.add(vx_in, gbc);

        gbc.gridx = 2;
        painel.add(vy_lbl, gbc);

        gbc.gridx = 3;
        painel.add(vy_in, gbc);

        linha++;


        // massa e cor
        JLabel massa_lbl = new JLabel("Massa:");
        JTextField massa_in = new JTextField();

        JLabel cor_lbl = new JLabel("Cor:");

        JComboBox<String> cor_in = new JComboBox<>(( (String[]) cores.keySet().toArray(new String[0])));

        gbc.gridy = linha;

        gbc.gridx = 0;
        painel.add(massa_lbl, gbc);

        gbc.gridx = 1;
        painel.add(massa_in, gbc);

        gbc.gridx = 2;
        painel.add(cor_lbl, gbc);

        gbc.gridx = 3;
        painel.add(cor_in, gbc);

        linha++;


        // botão
        JButton add_btn = new JButton("Adicionar novo planeta");

        gbc.gridy = linha;
        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.EAST;

        painel.add(add_btn, gbc);
        
        j.add(painel);

        //______EVENTOS CONFIGURADOS
        
        add_btn.addActionListener(e -> {
            double x = Double.parseDouble(x_in.getText());
            double y = Double.parseDouble(y_in.getText());
            double vx = Double.parseDouble(vx_in.getText());
            double vy = Double.parseDouble(vy_in.getText());
            long massa = Long.parseLong(massa_in.getText());
            int R = Integer.parseInt(tamanho_in.getText());
            adicionarPlaneta(R, x, y, vx, vy, massa, ( (String) cor_in.getSelectedItem()));

            int id = modelo.getRowCount() + 1;
            modelo.addRow(new Object[]{ id, cor_in.getSelectedItem(), R, massa });
        });
    }

    private void adicionarPlaneta(int R, double x, double y, double vx, double vy, long massa, String cor){
        Planeta p = new Planeta(R, x, y, vx, vy, massa, cores.get(cor));
        simulator.add(p);
        simulator.setPause(false);
        this.close();
    }

}
