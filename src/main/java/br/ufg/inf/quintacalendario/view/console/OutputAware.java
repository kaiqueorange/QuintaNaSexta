package br.ufg.inf.quintacalendario.view.console;

import java.io.PrintStream;

@FunctionalInterface
public interface OutputAware {
    void setOutput(PrintStream output);
}
