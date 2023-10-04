package org.eu.huzpsb.crawl4j.user;

import org.eu.huzpsb.crawl4j.control.Collector;

import java.io.File;
import java.util.Scanner;

public class Extender {
    public static void doExtend() {
        File db0 = new File("c4j_.db");
        if (db0.exists()) {
            try {
                System.out.println("【插件】扩充器：启用。");
                Scanner sc = new Scanner(db0, "UTF-8");
                while (sc.hasNextLine()) {
                    String[] line = sc.nextLine().split("淦");
                    if (line.length != 2) {
                        continue;
                    }
                    Collector.Collect(line[0], line[1]);
                }
                sc.close();
                System.out.println("【插件】扩充器：载入状态成功。");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("【插件】扩充器：启用失败。");
            }
        } else {
            System.out.println("【插件】扩充器：未启用。");
            System.out.println("【插件】扩充器：提示！将旧的数据库命为c4j_.db可以扩充之。");
        }
    }
}
