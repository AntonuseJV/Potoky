import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int[] prices = {100, 50, 80, 60};
        String[] products = {"Молоко", "Крупа", "Чай","Сахар"};

        Scanner scanner = new Scanner(System.in);
        File binFile = new File("basket.bin");
        Basket basket = new Basket(prices, products);

        if (binFile.exists()) {
            basket = Basket.loadFromBinFile(binFile);
        }
        basket.printListAllProductsForBuy();

        while (true) {
            System.out.println("Выберите товар и количество или введите \"end\" ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("end")) {
                break;
            }
            String[] parts = input.split(" ");
            if (parts.length != 2) {
                continue;
            }

            int productNumber;
            try {
                productNumber = Integer.parseInt(parts[0]) - 1; // выбор продукта
            } catch (NumberFormatException e) {
                System.out.println("Вы ввели текст заместо числа. Попробуйте снова!");
                continue;
            }
            if (productNumber >= 3 || productNumber < 0) {
                System.out.println("Вы ввели некорректное число продукта. Попробуйте снова!");
                continue;
            }

            int productCount;
            try {
                productCount = Integer.parseInt(parts[1]); // выбор количества продуктов
            } catch (NumberFormatException e) {
                System.out.println("Вы ввели текст заместо числа. Попробуйте снова!");
                continue;
            }
            if (productCount > 50 || productCount <= 0) {
                System.out.println("Вы ввели некорректное кол-во продукта. Попробуйте снова!");
                continue;
            }
            basket.addToCart(productNumber, productCount);
        }
        basket.saveBin(binFile);
        basket.printCart();
    }
}