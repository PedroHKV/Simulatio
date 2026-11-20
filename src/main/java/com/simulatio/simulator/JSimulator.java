package com.simulatio.simulator;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;


import com.simulatio.simulator.campos.Gravidade;
import com.simulatio.simulator.entidades.Entidade;

//a classe que efetivamente possui a simulação, a responsabilidade é:
//-desenhar os resultados da simulação
//-gerenciar os objetos que representam os corpos simulados

public class JSimulator extends JPanel implements Runnable{
    
    private ArrayList<Entidade> entidades = new ArrayList<>();
    private Gravidade g = new Gravidade();
   
    //variáveis internas de estado
    private volatile boolean pause = false; //deve ser volatile: CRÍTICO, sem isso a thread não vê caso o pause acabe
    private boolean notRunning = true;
    private volatile int adiantamento_de_tempo = 1;

    public void add(Entidade entidadeNova){
        entidades.add(entidadeNova);
        //para que ele só comece apos haver uma entidade
        if( notRunning ){
            Thread t = new Thread(this);
            t.start();
            notRunning = false;
        }
        repaint();
    }

    public void remove(int indice){
        entidades.get(indice).setDeveSerRemovido(true);
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        //pinta o fundo de preto
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        //inicia o loop
        for (Entidade entidade : entidades)
            entidade.desenhar(g);
    }

    @Override
    public void run(){
        double ultimoFrame = System.nanoTime() / 1_000_000_000.0;
                
        while (true)
            if (!pause){
                //obtem o tempo passado em segundos entre o ultimo quadro e o novo
                double agora = System.nanoTime() / 1_000_000_000.0;
                double dt = agora - ultimoFrame;
                ultimoFrame = agora;

                dt *= adiantamento_de_tempo;

                g.agirSobre(entidades, dt);

                //atualiza os estados
                Iterator<Entidade> it = entidades.iterator();
                while (it.hasNext()) {
                    Entidade entidade = it.next(); 
                    if (entidade.getDeveSerRemovido()) 
                        it.remove(); 
                     else 
                        entidade.update(dt); 
                }
                
                //desenha os estados, porém com a thread EDT
                repaint();

                //para o CPU ficar bem longe de 100% - 60 ciclos por segundo
                try {
                    Thread.sleep(16); 
                } catch (InterruptedException e) {
                    break;
                }
            }
    }

    @Override
    public void addMouseListener(MouseListener e){
        super.addMouseListener(e);
    }

    public void setAdiantamentoDeTempo(int adiantamento_de_tempo) {
        this.adiantamento_de_tempo = adiantamento_de_tempo;
    }

}
