//FUNÇÃO PRINCIPAL
//1-PONTO INICIAL DO CODIGO
//2-CRIAR OS CONTROLLERS NO SISTEMA

package com.project;

import com.project.controller.MovimentacaoController;
import com.project.controller.ProdutoController;
import com.project.controller.RelatorioController;
import com.project.view.TelaPrincipal;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {

        // Visual do sistema operacional
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // se falhar, segue com o visual padrão do Swing
        }

        // Uma única instância de cada controller, compartilhada por todas as telas
        ProdutoController produtoController = new ProdutoController();
        MovimentacaoController movimentacaoController = new MovimentacaoController(produtoController);
        RelatorioController relatorioController = new RelatorioController(produtoController);

        // Swing deve ser iniciado na thread própria da interface gráfica
        SwingUtilities.invokeLater(() -> {
            TelaPrincipal telaPrincipal = new TelaPrincipal(
                    produtoController,
                    movimentacaoController,
                    relatorioController
            );
            telaPrincipal.setVisible(true);
        });
    }
}