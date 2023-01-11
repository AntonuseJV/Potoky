import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Basket implements Serializable{

    private int[] prices;
    private String[] productsNames;
    private int[] productsCount;

    public Basket(int[] prices, String[] productsNames) {
        this.prices = prices;
        this.productsNames = productsNames;
        this.productsCount = new int[prices.length];
    }

    public void setProductsCount(int[] productsCount) {
        this.productsCount = productsCount;
    }

    protected void printListAllProductsForBuy() {
        System.out.println("Список возможных товаров для покупки: ");
        for (int i = 0; i < productsNames.length; i++) {
            System.out.println((i + 1) + ". " + productsNames[i] + " " + prices[i] + " руб/шт.");
        }
    }

    protected void addToCart(int productNum, int amount) {
        productsCount[productNum] += amount;
    }

    protected void printCart() {
        System.out.println("Ваша корзина:");
        int sum = 0;
        for (int i = 0; i < productsCount.length; i++) {
            int allCountProduct = productsCount[i];
            int priceSumByProduct = prices[i] * allCountProduct;
            if (allCountProduct > 0) {
                System.out.println(
                        productsNames[i] + " " + allCountProduct + " шт. в сумме " + priceSumByProduct
                                + " руб.");
                sum += priceSumByProduct;
            }
        }
        System.out.println("Итого: " + sum + " руб.");
    }

    public void saveTxt(File textFile) {
        try (PrintWriter writer = new PrintWriter(textFile)) {
            for (int pos = 0; pos < productsNames.length; pos++) {
                writer.println(productsNames[pos] + " " + prices[pos] + " " + productsCount[pos]);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error! File not found!");
        }
    }

    public static Basket loadFromTxtFile(File textFile) throws IOException {
        Path path = textFile.toPath();
        List<String> basketList = Files.readAllLines(path);

        String[] productsNames = new String[basketList.size()];
        int[] prices = new int[basketList.size()];
        int[] productsCount = new int[basketList.size()];

        for (int pos = 0; pos <= basketList.size() - 1; pos++) {
            String[] data = basketList.get(pos).split(" ");
            productsNames[pos] = data[0];
            prices[pos] = Integer.parseInt(data[1]);
            productsCount[pos] = Integer.parseInt(data[2]);
        }

        Basket basket = new Basket(prices, productsNames);
        basket.setProductsCount(productsCount);
        System.out.print("Корзина восстановлена!:" + "\n");
        basket.printCart();
        return basket;
    }

    public void saveBin(File file) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            objectOutputStream.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Basket loadFromBinFile(File binFile) throws IOException, ClassNotFoundException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(binFile))) {
            Basket basket = (Basket) objectInputStream.readObject();
            System.out.print("Корзина восстановлена!:" + "\n");
            basket.printCart();
            return basket;
        }
    }
}