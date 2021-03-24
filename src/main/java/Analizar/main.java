package Analizar;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java_cup.runtime.Symbol;
import jflex.exceptions.SilentExit;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author willi
 */
public class main {

    public static final int ID = 5;
    public static final int NUMERO = 6;
    public static final int PARENTESISA = 2;
    public static final int PARENTESISC = 3;
    public static final int SUMA = 1;
    public static final int POR = 4;
    public static final int FINDECADENA = 7;
    public static Lexer lex;
    public static int token;
    public static boolean isError = false;

    public static void main(String[] args) throws IOException, SilentExit, Exception {
        analizarSintacticamente("2+(2+7)");
    }

    public static void avanzar() {
        try {
            Symbol n = lex.next_token();
            if (n.value == null) {
                token = 7;
            } else {
                token = n.sym;
            }
        } catch (Exception ex) {
            System.out.println("Error por: " + ex.toString());
        }
    }
    
    public static void consumir(int tok) {
        if (tok == token){
            avanzar();
        } else {
            System.out.println("error - se esperaba "+nombreToken(tok)+" y se le envio "+nombreToken(token));
            isError = true;
        }
    }
    
    public static String nombreToken(int ave){
        switch(ave){
            case ID:
                return "ID";
            case PARENTESISA:
                return "(";
            case PARENTESISC:
                return ")";
            case NUMERO:
                return "NUMERO";
            case SUMA:
                return "+";
            case POR:
                return "*";
            case FINDECADENA:
                return "$";
            default:
                return "ERROR";
        }
    }

    public static void generar() throws UnsupportedEncodingException, SilentExit, IOException, Exception {
        String path2 = System.getProperty("user.dir");
        String decodedPath = URLDecoder.decode(path2, "UTF-8");
        String[] rutaS2 = {"C:/Users/willi/OneDrive/Documentos/NetBeansProjects/AnalizadorLL1/src/main/java/Analizar/Lexer.flex"};
        jflex.Main.generate(rutaS2);
    }

    public static void analizarSintacticamente(String texto) {
        lex = new Lexer(new StringReader(texto));
        avanzar();
        E();
        if (!isError) {
            System.out.println("Analizado correctamente");
	}
    }

    public static void E() {
        switch (token) {
            case ID:
                T();
                Ep();
                break;
            case NUMERO:
                T();
                Ep();
                break;
            case PARENTESISA:
                T();
                Ep();
                break;
            default:
                isError = true;
                break;
        }
    }

    public static void Ep() {
        switch (token) {
            case PARENTESISC:
                break;
            case FINDECADENA:
                break;
            case SUMA:
                consumir(SUMA);
                T();
                Ep();
                break;
            default:
                isError = true;
                break;
        }
    }

    public static void T() {
        switch (token) {
            case ID:
                F();Tp();
                break;
            case NUMERO:
                F();Tp();
                break;
            case PARENTESISA:
                F();Tp();
                break;
            default:
                isError = true;
                break;
        }
    }

    public static void Tp() {
        switch (token) {
            case PARENTESISC:
                break;
            case SUMA:
                break;
            case FINDECADENA:
                break;
            case POR:
                consumir(POR);
                F();
                Tp();
                break;
            default:
                isError = true;
                break;
        }
    }

    public static void F() {
        switch (token) {
            case ID:
                consumir(ID);
                break;
            case NUMERO:
                consumir(NUMERO);
                break;
            case PARENTESISA:
                consumir(PARENTESISA); E();
                consumir(PARENTESISC);
                break;
            default:
                isError = true;
                break;
        }
    }
}
