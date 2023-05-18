import java.util.Random;
import java.util.Scanner;

class DumplingShop {
    private int porkDumplings;
    private int beefDumplings;
    private int vegetableDumplings;

    public DumplingShop(int porkDumplings, int beefDumplings, int vegetableDumplings) {
        this.porkDumplings = porkDumplings;
        this.beefDumplings = beefDumplings;
        this.vegetableDumplings = vegetableDumplings;
    }

    public synchronized boolean sellDumplings(String type, int quantity) {
        switch (type) {
            case "豬肉水餃":
                if (porkDumplings >= quantity) {
                    porkDumplings -= quantity;
                    return true;
                }
                break;
            case "牛肉水餃":
                if (beefDumplings >= quantity) {
                    beefDumplings -= quantity;
                    return true;
                }
                break;
            case "蔬菜水餃":
                if (vegetableDumplings >= quantity) {
                    vegetableDumplings -= quantity;
                    return true;
                }
                break;
        }
        return false;
    }
}

class Customer implements Runnable {
    private static final int MIN_QUANTITY = 10;
    private static final int MAX_QUANTITY = 50;

    private String name;
    private DumplingShop shop;

    public Customer(String name, DumplingShop shop) {
        this.name = name;
        this.shop = shop;
    }

    private String getRandomDumplingType() {
        Random random = new Random();
        int num = random.nextInt(3);
        switch (num) {
            case 0:
                return "豬肉水餃";
            case 1:
                return "牛肉水餃";
            case 2:
                return "蔬菜水餃";
        }
        return "";
    }

    private int getRandomQuantity() {
        Random random = new Random();
        return random.nextInt(MAX_QUANTITY - MIN_QUANTITY + 1) + MIN_QUANTITY;
    }
    

    @Override
    public void run() {
        String dumplingType = getRandomDumplingType();
        int quantity = getRandomQuantity();
        boolean sold = shop.sellDumplings(dumplingType, quantity);
        if (sold) {
            System.out.println(name + " 已經購買了 " + dumplingType + " x" + quantity);
        } else {
            System.out.println(name + " 好想買~ " + dumplingType + " x" + quantity + "，但已經賣光了~");
        }
    }
}

public class A1093376_0512 {
    public static void main(String[] args) {
        int porkDumplings = 5000;
        int beefDumplings = 3000;
        int vegetableDumplings = 1000;

        DumplingShop shop = new DumplingShop(porkDumplings, beefDumplings, vegetableDumplings);
        Scanner scanner = new Scanner(System.in);
        
    
        while (porkDumplings > 0 || beefDumplings > 0 || vegetableDumplings > 0) {
            System.out.print("請輸入顧客的數量：");
            int numCustomers = scanner.nextInt();
            Thread[] threads = new Thread[numCustomers];
            if (numCustomers == 0) {
                break;
            }

        for (int i = 0; i < numCustomers; i++) {
            if(porkDumplings > 0 || beefDumplings > 0 || vegetableDumplings > 0){
            threads[i] = new Thread(new Customer("顧客 " + (i + 1), shop));
            threads[i].start();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else if(porkDumplings <= 0 && beefDumplings <= 0 && vegetableDumplings <= 0){
            System.out.println("售完");
                    break;
        }
        }
        
        
    
        for (int i = 0; i < numCustomers; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    }
}
