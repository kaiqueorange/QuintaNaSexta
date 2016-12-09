package br.ufg.inf.quintacalendario.view.console;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import br.ufg.inf.quintacalendario.view.TelaCabecalho;

public class AbstractTelaCabecalho implements TelaCabecalho, OutputAware {
	
	private static final Logger logger = Logger.getLogger(AbstractTelaCabecalho.class);
    private static final String CAMINHO_CABECALHO = "/view/cabecalho.txt";
    private String conteudoCabecalho;
    private PrintStream output;

    public AbstractTelaCabecalho(PrintStream output) {
        this();
        this.output = output;
    }

    public AbstractTelaCabecalho() {
        this.carregueCabecalho();
    }
    
    @Override
    public void exibaCabecalho() {
        output.println(conteudoCabecalho);
    }

    private void carregueCabecalho() {
        InputStream inputStream = this.getClass().getResourceAsStream(CAMINHO_CABECALHO);
        try {
            this.conteudoCabecalho = IOUtils.toString(inputStream, "UTF-8");
        } catch (IOException ignored) {
        	logger.info(ignored.getMessage());
            this.conteudoCabecalho = "";
        }
    }

    @Override
    public void setOutput(PrintStream output) {
        this.output = output;
    }
}
