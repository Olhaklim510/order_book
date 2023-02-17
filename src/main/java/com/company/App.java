package com.company;

import com.company.model.OrderBook;

import java.io.*;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Stream;

public class App {
    private static final String RELATIVE_PATH_FOR_READING = "input.txt";
    private static final String RELATIVE_PATH_FOR_WRITING = "output.txt";
    private static StringBuilder stringBuilder=new StringBuilder();

    public static void main(String[] args) {
        File fileRead = new File(RELATIVE_PATH_FOR_READING);

        TreeSet<OrderBook> setOfOrdersAsk = new TreeSet<>(Comparator.comparing(OrderBook::getPrice).reversed());
        TreeSet<OrderBook> setOfOrdersBid = new TreeSet<>(Comparator.comparing(OrderBook::getPrice).reversed());
        TreeSet<OrderBook> setOfOrdersSpread = new TreeSet<>(Comparator.comparing(OrderBook::getPrice).reversed());

        try (BufferedReader reader = new BufferedReader(new FileReader(fileRead))) {

            while (true) {
                String result = reader.readLine();

                OrderBook orderBook = new OrderBook();
                if (result == null) {
                    break;
                }
                String[] arrayOfData = result.split(",");

                if (arrayOfData[0].equals("u")) {
                    orderBook.setPrice(Integer.parseInt(arrayOfData[1]));
                    orderBook.setSize(Integer.parseInt(arrayOfData[2]));
                    orderBook.setType(arrayOfData[3]);
                    switch (arrayOfData[3]) {
                        case "ask":
                            setOfOrdersAsk.add(orderBook);
                            break;
                        case "bid":
                            setOfOrdersBid.add(orderBook);
                            break;
                        case "spread":
                            setOfOrdersSpread.add(orderBook);
                            break;
                    }
                }

                if (arrayOfData[0].equals("q")) {

                    if (arrayOfData[1].equals("best_bid")) {

                        Iterator<OrderBook> iterator = setOfOrdersBid.iterator();
                        while (iterator.hasNext()) {
                            OrderBook book = iterator.next();
                            if (book.getSize() == 0) {
                                continue;
                            } else {
                                System.out.println(book.getPrice() + "," + book.getSize());
                                stringBuilder.append(book.getPrice()).append(",").append(book.getSize()).append("\n");
                                break;
                            }
                        }
                    }

                    if (arrayOfData[1].equals("best_ask")) {
                        Iterator<OrderBook> iterator = setOfOrdersAsk.descendingIterator();
                        while (iterator.hasNext()) {
                            OrderBook book = iterator.next();
                            if (book.getSize() == 0) {
                                continue;
                            } else {
                                System.out.println(book.getPrice() + "," + book.getSize());
                                stringBuilder.append(book.getPrice()).append(",").append(book.getSize()).append("\n");
                                break;
                            }
                        }
                    }
                    if (arrayOfData[1].equals("size")) {

                        long size = Stream.of(
                                        setOfOrdersBid.stream(),
                                        setOfOrdersAsk.stream(),
                                        setOfOrdersSpread.stream())
                                .flatMap(Function.identity())
                                .filter(n -> n.getPrice() == Integer.parseInt((arrayOfData[2])))
                                .map(OrderBook::getSize)
                                .reduce(Integer::sum)
                                .orElse(0);

                        System.out.println(size);
                        stringBuilder.append(size).append("\n");
                    }
                }
                if (arrayOfData[0].equals("o")) {
                    if (arrayOfData[1].equals("sell")) {
                        OrderBook order = setOfOrdersBid.first();

                        if (order.getSize() > 0 & order.getSize() >= Long.parseLong(arrayOfData[2])) {
                            int newSize = order.getSize() - Integer.parseInt(arrayOfData[2]);
                            order.setSize(newSize);
                        } else {
                            System.out.println("This size is null");
                        }
                    }
                    if (arrayOfData[1].equals("buy")) {
                        OrderBook order = setOfOrdersAsk.last();
                        if (order.getSize() > 0 & order.getSize() >= Long.parseLong(arrayOfData[2])) {
                            int newSize = order.getSize() - Integer.parseInt(arrayOfData[2]);
                            order.setSize(newSize);
                        } else {
                            System.out.println("This size is null");
                        }
                    }
                }
            }
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        File fileWrite = new File(RELATIVE_PATH_FOR_WRITING);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileWrite))) {
            bufferedWriter.write(String.valueOf(stringBuilder));
        } catch (IOException exc) {
            System.err.print(exc.getMessage());
        }
    }
}
