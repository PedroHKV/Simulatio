package com.simulatio.simulator.entidades;

import java.awt.Color;
import java.awt.Graphics;

public class Planeta extends Entidade{

    private int R;
    private Color color = Color.BLUE;
    

    public Planeta(int RAIO, double xCentro0, double yCentro0, double vx0, double vy0, long massa, Color color){
        this.R = RAIO;
        this.x = xCentro0;
        this.y = yCentro0;
        this.vx = vx0;
        this.vy = vy0;
        this.massa = massa;
        this.color = color;
    }

    @Override
    public void desenhar(Graphics g) {
        // o desenho é feito a partir do canto superior esquerdo do circulo
        //por isso x - R e y - R
        g.setColor(color);
        g.fillOval( (int) (x - R) ,  (int) (y - R) ,  (int) (2*R),  (int) (2*R));
        
    }

    @Override
    public void update(double dt) {
        this.x += vx * dt ;
        this.y += vy * dt ;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
}
