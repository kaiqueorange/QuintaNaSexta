package br.ufg.inf.quintacalendario.view.console.util;

import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.err;

/**
 * Classe auxiliar para encapsular a leitura de entrada do usuário no console.
 */
public class EntradaConsole {
    private Scanner scanner = new Scanner(in);

    /**
     * Imprime uma pergunta para o usuário e lê a entrada, esperando que seja um Inteiro
     *
     * @param pergunta O texto da pergutna que deve ser feita
     * @return O número já convertido para Inteiro
     * @throws Exception 
     * @see Integer#parseInt(String)
     */
    public Integer pergunteInteiro(String pergunta) throws Exception {
        String entradaString = pergunteString(pergunta);

        boolean entradaValida = false;
        int entradaInteiro = 0;

        while (!entradaValida) {
            try {
                entradaInteiro = Integer.parseInt(entradaString);
                entradaValida = true;
            } catch (NumberFormatException ignored) {
                err.println("Entrada inválida. Tente novamente");
                entradaValida = false;
                entradaString = pergunteString(pergunta);
            }
        }

        return entradaInteiro;
    }

    /**
     * Imprime uma pergunta para o usuário e lê a entrada, esperando que seja um Número
     *
     * @param pergunta O texto da pergutna que deve ser feita
     * @return O número já convertido para Double
     * @throws Exception 
     * @see Double#parseDouble(String)
     */
    public Double pergunteDouble(String pergunta) throws Exception {
        String entradaString = pergunteString(pergunta);
        boolean entradaValida = false;
        double entradaDouble = 0;

        while (!entradaValida) {
            try {
                entradaDouble = Double.parseDouble(entradaString);
                entradaValida = true;
            } catch (NumberFormatException ignored) {
                err.println("Entrada inválida. Tente novamente");
                entradaValida = false;
                entradaString = pergunteString(pergunta);
            }
        }

        return entradaDouble;
    }

    /**
     * Imprime uma pergunta para o usuário e lê o texto de entrada
     *
     * @param pergunta O texto da pergutna que deve ser feita
     * @return O texto que o usuário inseriu
     */
    public String pergunteString(String pergunta) throws Exception {
        err.println(pergunta);
        return scanner.nextLine();
    }

    /**
     * Imprime uma pergunta para o usuário e lê o texto de entrada, podendo
     *
     * @param pergunta       O texto da pergutna que deve ser feita
     * @param obrigarEntrada Flag para indicar se deve bloquear texto em branco
     * @return O texto que o usuário inseriu
     */
    public String pergunteString(String pergunta, boolean obrigarEntrada) throws Exception {
        String entrada = pergunteString(pergunta);

        if (obrigarEntrada) {
            while ("".equals(entrada.trim())) {
                entrada = pergunteString(pergunta);
            }
        }

        return entrada;
    }
}
