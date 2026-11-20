package com.simulatio;

import com.simulatio.view.TelaPrincipal;

public class Main {
    
    private static final int W0 = 900;
    private static final int H0 = 700;
    private static final String TITULO = "JGravitas";
    
    public static void main(String[] args){

        TelaPrincipal tela = new TelaPrincipal(TITULO, W0, H0);
        tela.show();

    }


}
