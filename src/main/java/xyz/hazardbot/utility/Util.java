package xyz.hazardbot.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Util {
    public static void print(String... ss) {
        Arrays.stream(ss).forEach(System.out::println);
    }
    
    @SafeVarargs
    public static <L> List<L> newList(L... ll) {
        List<L> list = new ArrayList<>();
        Arrays.stream(ll).forEach(list::add);
        return list;
    }
    
    public static <L> List<L> newList(Collection<? extends L> ll) {
        List<L> list = new ArrayList<>();
        ll.forEach(list::add);
        return list;
    }
    
    public static <K, V> Map<K, V> newMap() {
        Map<K, V> map = new HashMap<K, V>();
        return map;
    }
    
    public static void waitThenDo(long millis, Runnable task) {
        Timer timer = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                task.run();
                cancel();
            }
        };
        timer.schedule(tt, millis);
    }
}