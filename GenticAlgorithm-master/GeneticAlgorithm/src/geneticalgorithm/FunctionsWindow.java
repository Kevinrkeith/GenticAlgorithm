/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm;

/**
 *
 * @author Kevin Keith Student# 6267728 login: kk17kz
 */
import static java.lang.Math.abs;

import static java.lang.Math.random;
import java.util.Random;
import java.util.Scanner;

public class FunctionsWindow {

    //Decrypt text using the provided key
    public static String decrypt(String k, String c) {
        //Sanitize the cipher and the key
        String cipher = c.toLowerCase();
        cipher = cipher.replaceAll("[^a-z]", "");
        cipher = cipher.replaceAll("\\s", "");

        String ke = k.toLowerCase();
        ke = ke.replaceAll("[^a-z]", "");
        ke = ke.replaceAll("\\s", "");

        char[] key = ke.toCharArray();
        for (int i = 0; i < key.length; i++) {
            key[i] = (char) (key[i] - 97);
        }

        //Run the decryption
        String plain = "";
        int keyPtr = 0;
        for (int i = 0; i < cipher.length(); i++) {
            char keyChar = (char) 0;
            if (key.length > 0) {
                //Ignore any value not in the expected range
                while (key[keyPtr] > 25 || key[keyPtr] < 0) {
                    keyPtr = (keyPtr + 1) % key.length;
                }
                keyChar = key[keyPtr];
                keyPtr = (keyPtr + 1) % key.length;
            }
            plain += ((char) (((cipher.charAt(i) - 97 + 26 - keyChar) % 26) + 97));
        }
        return plain;
    }

    public static String randomString(int popSize) {
        String s = "";
        int leftLimit = 97;
        int rightLimit = 122;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(popSize);
        for (int i = 0; i < popSize; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        s = buffer.toString();
        return s;
    }

    //Encrypt text using the provided key
    public static String encrypt(String k, String p) {

        //Sanitize the cipher and the key
        String plain = p.toLowerCase();
        plain = plain.replaceAll("[^a-z]", "");
        plain = plain.replaceAll("\\s", "");
        String cipher = "";

        String ke = k.toLowerCase();
        ke = ke.replaceAll("[^a-z]", "");
        ke = ke.replaceAll("\\s", "");

        char[] key = ke.toCharArray();
        for (int i = 0; i < key.length; i++) {
            key[i] = (char) (key[i] - 97);
        }

        //Encrypt the text
        int keyPtr = 0;
        for (int i = 0; i < plain.length(); i++) {
            char keyChar = (char) 0;
            if (key.length > 0) {
                //Ignore any value not in the expected range
                while (key[keyPtr] > 25 || key[keyPtr] < 0) {
                    keyPtr = (keyPtr + 1) % key.length;
                }
                keyChar = key[keyPtr];
                keyPtr = (keyPtr + 1) % key.length;
            }
            cipher += ((char) (((plain.charAt(i) - 97 + keyChar) % 26) + 97));
        }
        return cipher;
    }
    //This is a very simple fitness function based on the expected frequency of each letter in english 
    //There is lots of room for improvement in this function.

    public static double fitness(String k, String c) {
        //The expected frequency of each character in english language text according to 
        //http://practicalcryptography.com/cryptanalysis/letter-frequencies-various-languages/english-letter-frequencies/
        double[] expectedFrequencies = new double[26];
        expectedFrequencies[0] = 0.085; //Expected frequency of a
        expectedFrequencies[1] = 0.016; //Expected frequency of b
        expectedFrequencies[2] = 0.0316; //Expected frequency of c
        expectedFrequencies[3] = 0.0387; //Expected frequency of d
        expectedFrequencies[4] = 0.121; //Expected frequency of e
        expectedFrequencies[5] = 0.0218; //Expected frequency of f
        expectedFrequencies[6] = 0.0209; //Expected frequency of g
        expectedFrequencies[7] = 0.0496; //Expected frequency of h
        expectedFrequencies[8] = 0.0733; //Expected frequency of i
        expectedFrequencies[9] = 0.0022; //Expected frequency of j
        expectedFrequencies[10] = 0.0081; //Expected frequency of k
        expectedFrequencies[11] = 0.0421; //Expected frequency of l
        expectedFrequencies[12] = 0.0253; //Expected frequency of m
        expectedFrequencies[13] = 0.0717; //Expected frequency of n
        expectedFrequencies[14] = 0.0747; //Expected frequency of o
        expectedFrequencies[15] = 0.0207; //Expected frequency of p
        expectedFrequencies[16] = 0.001; //Expected frequency of q
        expectedFrequencies[17] = 0.0633; //Expected frequency of r
        expectedFrequencies[18] = 0.0673; //Expected frequency of s
        expectedFrequencies[19] = 0.0894;//Expected frequency of t
        expectedFrequencies[20] = 0.0268;//Expected frequency of u
        expectedFrequencies[21] = 0.0106; //Expected frequency of v
        expectedFrequencies[22] = 0.0183;//Expected frequency of w
        expectedFrequencies[23] = 0.0019;//Expected frequency of x
        expectedFrequencies[24] = 0.0172;//Expected frequency of y
        expectedFrequencies[25] = 0.0011;//Expected frequency of z

        //Sanitize the cipher text and key
        String d = c.toLowerCase();
        d = d.replaceAll("[^a-z]", "");
        d = d.replaceAll("\\s", "");
        int[] cipher = new int[c.length()];
        for (int x = 0; x < c.length(); x++) {
            cipher[x] = ((int) d.charAt(x)) - 97;
        }

        String ke = k.toLowerCase();
        ke = ke.replaceAll("[^a-z]", "");
        ke = ke.replaceAll("\\s", "");

        char[] key = ke.toCharArray();
        for (int i = 0; i < key.length; i++) {
            key[i] = (char) (key[i] - 97);
        }

        int[] charCounts = new int[26];
        for (int i = 0; i < charCounts.length; i++) {
            charCounts[i] = 0;
        }

        int[] plain = new int[cipher.length];

        //Decrypt each character
        int keyPtr = 0;
        for (int i = 0; i < cipher.length; i++) {
            char keyChar = (char) 0;
            if (key.length > 0) {
                //Ignore any value not in the expected range
                while (key[keyPtr] > 25 || key[keyPtr] < 0) {
                    keyPtr = (keyPtr + 1) % key.length;
                }
                keyChar = key[keyPtr];
                keyPtr = (keyPtr + 1) % key.length;
            }
            plain[i] = ((26 + cipher[i] - keyChar) % 26);

        }

        //Count the occurences of each character
        for (int x : plain) {
            charCounts[x]++;
        }
        //Calculate the total difference between the expected frequencies and the actual frequencies 
        double score = 0;
        for (int y = 0; y < charCounts.length; y++) {
            score += abs((((float) charCounts[y]) / plain.length) - expectedFrequencies[y]);
        }

        return score;
    }

    public static void main(String[] args) {

        /*String p = "xbwdesmhihslwhkktefvktkktcwfpiibihwmosfilojvooegvefwnochsuuspsureifakbnlalzsrsroiejwzgfpjczldokrceoahzshpbdwpcjstacgbarfwifwohylckafckzwwomlalghrtafchfetcgfpfrgxclwzocdctmjebx";
        String k = "password";
        System.out.println(k);
        String c = encrypt(k, p);
        double fit = fitness(k, c);
        System.out.println(fit);
        String d = decrypt(k, c);
        System.out.println(c);
        System.out.println(d);
        System.out.println(fit);
        char[] arr = c.toCharArray();
        //Note the relatively high fitness value, this is because the String p is short. 
        //When messages are too short the frequency distribution of characters will not necessarily match the expected values 
        //Longer messages will be easier to decrypt, since in general the frequency distribution of their characters will match the expected distribution
        //For example*/
        String p2 = "xbwdesmhihslwhkktefvktkktcwfpiibihwmosfilojvooegvefwnochsuuspsureifakbnlalzsrsroiejwzgfpjczldokrceoahzshpbdwpcjstacgbarfwifwohylckafckzwwomlalghrtafchfetcgfpfrgxclwzocdctmjebx";

        /*String c2 = encrypt(k, p2);
        System.out.println(p2);
        System.out.println(c2);
        System.out.println(fitness(k, c2));*/
        
        GeneticAlgorithm(200, 200, 0, 0.9, p2);
        //GeneticAlgorithm(200, 200, 1,0,c2);
    }

    static void GeneticAlgorithm(int maxGen, int popSize, double crossProb, double mutProb, String parent) {
        String[] best = new String[popSize];
        Scanner s = new Scanner(System.in);
        System.out.println("For this generation, what do you want to use, (1) one point crossover, or (2) two point crossover");
        int selection = s.nextInt();
        for (int i = 0; i < best.length; i++) {
            best[i] = randomString(8);
        }

        for (int gen = 1; gen <= maxGen; gen++) {
            for (int i = 0; i < best.length; i++) { //Seperates the best values from five random values
                best[i] = tournamentSelection(parent, best[(int) (Math.random() * best.length)], best[(int) (Math.random() * best.length)], best[(int) (Math.random() * best.length)], best[(int) (Math.random() * best.length)]);
                //System.out.println("Best value of: "+best[i] + " With a fitness of: "+ fitness(key, best[i]));
            }
            double average = 0, top = Double.MAX_VALUE;
            String bestString="";
            for (int i = 1; i < best.length; i += 2) {
                String p1 = best[i - 1];
                String p2 = best[i];
                String[] sArr;
                if (Math.random() >= crossProb) {
                    if (selection == 1) {
                        sArr = onePointCrossOver(p1, p2);
                        best[i - 1] = sArr[0];
                        best[i] = sArr[1];
                    } else {
                        sArr = onePointCrossOver(p1, p2);
                        best[i - 1] = sArr[0];
                        best[i] = sArr[1];
                    }
                }
                if (Math.random() >= mutProb) {
                    best[i - 1] = mutator(p1);
                    best[i] = mutator(p2);
                }
            }
            for (int i = 0; i < best.length; i++) {
                double fit = fitness(best[i], parent);
                average += fit;
                if (fit < top) {
                    bestString = best[i];
                    top = fit;
                }
                System.out.println("String: " + best[i] + " Fitness: " + fit + " At index: " + i);
            }
            System.out.println("Average of the Fitness: " + (average / best.length) + " Top Fitness: " + top);
            String d = decrypt(bestString, parent);
            System.out.println(d);
        }
    }

    static String mutator(String mutate) {
        char[] arr = mutate.toCharArray();
        int swapPoint1 = (int) (Math.random() * mutate.length());
        int swapPoint2 = (int) (Math.random() * mutate.length());
        char temp = arr[swapPoint1];
        arr[swapPoint1] = arr[swapPoint2];
        arr[swapPoint2] = temp;
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            buffer.append(arr[i]);
        }
        mutate = buffer.toString();
        return mutate;
    }

    static String tournamentSelection(String parent, String... arr) {
        String best = "";
        double fit = Double.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            double cur = fitness(arr[i], parent);
            if (best == "") {
                best = arr[i];
            } else {
                if (cur < fit) {
                    fit = cur;
                    best = arr[i];
                }
            }
        }
        return best;
    }

    static String[] onePointCrossOver(String pointA, String pointB) {
        String[] arr = new String[2];
        StringBuilder buffer = new StringBuilder();
        StringBuilder buffer1 = new StringBuilder();
        int randomPoint = (int) (Math.random() * pointA.length());
        char[] charA = pointA.toCharArray(), charB = pointB.toCharArray();
        for (int i = 0; i < pointA.length(); i++) {
            if (i >= randomPoint) {
                char tempA = charA[i];
                charA[i] = charB[i];
                charB[i] = tempA;
            }
            buffer.append(charA[i]);
            buffer1.append(charB[i]);
        }
        arr[0] = buffer.toString();
        arr[1] = buffer.toString();
        return arr;
    }

    static String[] twoPointCrossOver(String pointA, String pointB) {
        String[] arr = new String[2];
        StringBuilder buffer = new StringBuilder();
        StringBuilder buffer1 = new StringBuilder();
        int randomStart = (int) (Math.random() * pointA.length()), randomEnd = (int) (Math.random() * pointA.length() + randomStart);
        char[] charA = pointA.toCharArray(), charB = pointB.toCharArray();
        for (int i = 0; i < pointA.length(); i++) {
            if (i >= randomStart && i <= randomEnd) {
                char tempA = charA[i];
                charA[i] = charB[i];
                charB[i] = tempA;
            }
            buffer.append(charA[i]);
            buffer1.append(charB[i]);
        }
        arr[0] = buffer.toString();
        arr[1] = buffer.toString();
        return arr;
    }
}
