package com.vickmwas;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    static String[] KEYWORDS = {
            "int", "char","return","include","stdio.h","main","printf"
    };

    static char[] SYMBOLS = {
            '#','=',';','{','}','(',')','<','>','%','\\','+','-'
    };

    static String STRING_LITERAL_REGEX = "[\"][a-zA-Z0-9 ]∗[\"]";       //todo : correct string literal regex

    static String IDENTIFIER_REGEX = "([a-zA-Z]*[a-zA-Z0-9]?[ ])";

    static String NUMBER_REGEX = "([+-]?[0-9]*)";                       //todo : remove space that gets read by this regex

    public static void main(String[] args) {
        System.out.println("TOKEN\t\tTYPE\t\tLINE");
        readFile();
    }


    private static void readFile(){
        Scanner sc;
        String text;
        try {
            sc = new Scanner(new File(  "lexical.c"));
            int line = 1;
            while (sc.hasNextLine()) {
                text = sc.nextLine();
                tokenize(text,line);
                line++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



    private static void tokenize(String s, int line){
        char[] charArray = s.toCharArray();

        String currentString = "";
        for (char c : charArray){

            //After a token has been identified, the space should be ignored.
            if (c == ' ') {
                if(!currentString.equals("")) currentString = currentString.concat(String.valueOf(c));
            }else
                currentString = currentString.concat(String.valueOf(c));
//            System.out.println("Checking : " + currentString);



            if (currentString.length() == 1){
                if (contains(SYMBOLS,currentString.charAt(0))){
                    printToken(currentString,"OPERATOR",line);
                    currentString = "";
                }
            }else{

                if (contains(KEYWORDS,currentString)){
                    printToken(currentString,"KEYWORD",line);
                    currentString = "";
                }else{
                    if((Pattern.compile(STRING_LITERAL_REGEX).matcher(currentString).matches())){
                        printToken(currentString,"String",line);
                        currentString = "";
                    }else if((Pattern.compile(NUMBER_REGEX).matcher(currentString).matches())){
                        printToken(currentString,"NUMBER",line);
                        currentString = "";
                    }else if (Pattern.compile(IDENTIFIER_REGEX).matcher(currentString).matches()){
                        printToken(currentString,"IDENTIFIER",line);
                        currentString = "";
                    }
                }
            }


        }
        System.out.println("");

    }


    private static boolean contains(String[] array, String character){
        boolean found = false;
        for(String string : array){
            if (string.equals(character)){
                found = true;
            }
        }
        return found;
    }



    private static boolean contains(char[] array, char character){
        boolean found = false;
        for(char string : array){
            if (string == character){
                found = true;
            }
        }
        return found;
    }

    private static void printToken(String token,String type,int line){
        System.out.println(token + "\t\t\t"+type +"\t\t\t"+line);
    }



}
