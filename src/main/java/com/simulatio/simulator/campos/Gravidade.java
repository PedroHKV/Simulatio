package com.simulatio.simulator.campos;

import java.util.ArrayList;

import com.simulatio.simulator.entidades.Entidade;


public class Gravidade {
    private final double G = 6.6743 * Math.pow(10, -11);
    
    //para cada entidade acrescenta a aceleração da gravidade
    //embora seja possivel resolver a equação do movimento, esta 
    //simulação se dará aplicando os deslocamentos "infinitesimais"
    //a cada frame

    // F = (G (m1 m2) / r²) radial 
    // com r² = (y2 - y1)² + (x2 - x1)²

    // já nas direções x e y (i, j):
    // F = Fx i + Fy j = F * (cos(th) i + sen(th) j)
    
    // =>
    
    // dvx = (G * m2 * cos(th)/ r²) * dt
    // dvy = (G * m2 * sen(th)/ r²) * dt

    // basta calcular o modulo do impulso: (G * m2 * m1 / r²) * dt,
    // e multiplicar por seno e cosseno e para cada planeta somar sua velocidade
    // com este valor dividido pela massa
    // pois se q = mv -> dq = F dt (impulso e conservação de momento linear)

    // onde th = angulo da reta direcionada por (x2 - x1, y2 - y1)
    // ou seja th = arctan [ (y2 - y1) / (x2 - x1) ]
    // porém só preciso do seno e do cosseno de theta, não dele em si
    // e sen th = (y2 - y1) / r e cos th = (x2 - x1) / r
    
    public void agirSobre(ArrayList<Entidade> entidades, double dt){

        int len = entidades.size();
        for(int i = 0; i < len; i++)
            for(int j = i; j < len; j++)
                if( i != j ){
                    Entidade e1 = entidades.get(i);
                    Entidade e2 = entidades.get(j);
                    
                    double dy = e2.getY() - e1.getY();
                    double dx = e2.getX() - e1.getX();
                    double quadrado_da_distancia = dy*dy + dx*dx;
                    double distancia = Math.sqrt(quadrado_da_distancia);
                    
                    double seno_theta = dy / distancia;
                    double cosseno_theta = dx / distancia;

                    double modulo_da_forca = G * e2.getMassa() * e1.getMassa() / quadrado_da_distancia;
                    double modulo_do_impulso = modulo_da_forca * dt; // "infinitesimal" (entre muitas aspas)
                    double modulo_do_impulso_Y = modulo_do_impulso * seno_theta;
                    double modulo_do_impulso_X = modulo_do_impulso * cosseno_theta;


                    //para e1:
                    double vx = e1.getVx(); //inicial
                    double vy = e1.getVy(); //inicial
                    vx += modulo_do_impulso_X / e1.getMassa();
                    vy += modulo_do_impulso_Y / e1.getMassa();
                    e1.setVx(vx);
                    e1.setVy(vy);
                    //para e2:
                    vx = e2.getVx(); //inicial
                    vy = e2.getVy(); //inicial
                    vy += -modulo_do_impulso_Y / e2.getMassa(); 
                    vx += -modulo_do_impulso_X / e2.getMassa(); 
                    e2.setVx(vx);
                    e2.setVy(vy);
                }
    }

}
