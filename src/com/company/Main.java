package com.company;

import java.util.*;

public class Main {

    public static List<Joke> jokes = new ArrayList<>();

    public static void main(String[] args) {
        while(true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("How many jokes do you want to download?");
            int size = Integer.parseInt(scanner.nextLine());
            jokes.addAll(downloadJokes(size));
            System.out.println("You now have " + jokes.size() + " jokes at your list");

            SortJokeListByLength(jokes);
            System.out.println("Shortest joke is: ");
            System.out.println(jokes.get(0) + "\n");

            religiousJokesAsk();
            twoPartJokesAsk();
            deleteJokesAsk();
        }
    }

    private static void deleteJokesAsk() {
        System.out.println("Do you want to delete downloaded jokes? yes / no");
        String delete = scanNextString();
        if (delete.equals("yes")) {
            jokes.clear();
        }
        System.out.println("Do you want to download more jokes? yes / no");
        String downloadMore = scanNextString();
        if (!downloadMore.equals("yes")) {
            System.exit(0);
        }
    }

    private static void twoPartJokesAsk() {
        System.out.println("Do you want to see how many two part jokes were downloaded? yes / no");
        String twoPart = scanNextString();
        if (twoPart.equals("yes")) {
            var twoPartJokesCounter = countTwoPartJokes(jokes);
            System.out.println("You downloaded " + twoPartJokesCounter + " two part jokes");
            for(Joke j: jokes)
                System.out.println(j);
            System.out.println("\n");
        }
    }

    private static void religiousJokesAsk() {
        var religiousJokes = getReligiousJokes(jokes);
        if (religiousJokes.size() != 0) {
            System.out.println("Do you want to see downloaded religious jokes? yes / no");
            String religious = scanNextString();
            if (religious.equals("yes")) {
                System.out.println("Downloaded religious jokes: ");
                for(Joke j: religiousJokes)
                    System.out.println(j);
            }
        }
    }

    private static List<Joke> downloadJokes(int size) {
        List<Joke> jokes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            try {
                jokes.add(Joke.getJoke("Any"));
            } catch (Exception e) {
                System.out.println("Sorry, we have a problem" + e);
            }
        }
        return jokes;
    }


    private static void SortJokeListByLength(List<Joke> jokes) {
        Comparator<Joke> jokeComparator = Comparator.comparing(Joke::getJokeLength);
        Collections.sort(jokes, jokeComparator);
    }

    private static List<Joke> getReligiousJokes(List<Joke> jokes) {
        List<Joke> religiousJokes = new ArrayList<>();
        for (var joke : jokes) {
            if (joke.religious)
                religiousJokes.add(joke);
        }
        return religiousJokes;
    }

    private static int countTwoPartJokes(List<Joke> jokes) {
        int twoPartJokesCounter = 0;
        for (var joke : jokes) {
            if (joke.setup == null)
                continue;
            twoPartJokesCounter++;
        }
        return twoPartJokesCounter;
    }

    public static String scanNextString(){
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }
}
