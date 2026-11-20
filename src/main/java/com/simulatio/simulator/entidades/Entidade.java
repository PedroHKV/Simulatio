package com.simulatio.simulator.entidades;

import java.awt.Graphics;

public abstract class Entidade {
    
    protected volatile double x;
    protected volatile double y;
    protected volatile double vx;
    protected volatile double vy;
    protected long massa;

    private boolean deveSerRemovido = false;

    public abstract void desenhar( Graphics g);
    public abstract void update( double dt );

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public long getMassa() {
        return massa;
    }

    public void setDeveSerRemovido(boolean deveSerRemovido) {
        this.deveSerRemovido = deveSerRemovido;
    }

     public boolean getDeveSerRemovido() {
        return deveSerRemovido;
    }

}
