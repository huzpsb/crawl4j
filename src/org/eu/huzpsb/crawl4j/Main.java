package org.eu.huzpsb.crawl4j;

import org.eu.huzpsb.crawl4j.user.SearchCLI;

import java.util.Scanner;

public class Main extends SearchCLI {
    public static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        SearchCLI.startCLI();
    }
}
