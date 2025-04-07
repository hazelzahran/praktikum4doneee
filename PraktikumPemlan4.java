import java.util.Scanner;

class Customer {
    private String customerNumber;
    private String name;
    private double balance;
    private String pin;

    public Customer(String customerNumber, String name, double balance, String pin) {
        this.customerNumber = customerNumber;
        this.name = name;
        this.balance = balance;
        this.pin = pin;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public boolean authenticate(String pin) {
        return this.pin.equals(pin);
    }

    public void topUp(double amount) {
        balance += amount;
    }

    public boolean purchase(double amount) {
        if (balance - amount < 10000) {
            System.out.println("Transaksi gagal: Saldo setelah transaksi kurang dari Rp10.000");
            return false;
        }
        double cashback = calculateCashback(amount);
        balance -= amount;
        balance += cashback;
        return true;
    }

    private double calculateCashback(double amount) {
        double cashback = 0;
        if (amount > 1000000) {
            switch (customerNumber.substring(0, 2)) {
                case "38": // Silver
                    cashback = amount * 0.05;
                    break;
                case "56": // Gold
                    cashback = amount * 0.07;
                    break;
                case "74": // Platinum
                    cashback = amount * 0.10;
                    break;
            }
        }
        // Cashback tambahan
        switch (customerNumber.substring(0, 2)) {
            case "56": // Gold
                cashback += amount * 0.02;
                break;
            case "74": // Platinum
                cashback += amount * 0.05;
                break;
        }
        return cashback;
    }
}

public class PraktikumPemlan4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Customer customer = new Customer("3812345678", "John Doe", 500000, "1234");

        int attempts = 0;
        while (attempts < 3) {
            System.out.print("Masukkan PIN: ");
            String pin = scanner.nextLine();
            if (customer.authenticate(pin)) {
                System.out.println("Autentikasi berhasil!");
                break;
            } else {
                attempts++;
                System.out.println("PIN salah. Coba lagi.");
            }
        }

        if (attempts == 3) {
            System.out.println("Akun terkunci setelah 3 kali kesalahan.");
            return;
        }

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Top Up");
            System.out.println("2. Pembelian");
            System.out.println("3. Cek Saldo");
            System.out.println("4. Keluar");
            System.out.print("Pilih opsi: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.print("Masukkan jumlah top up: ");
                    double topUpAmount = scanner.nextDouble();
                    customer.topUp(topUpAmount);
                    System.out.println("Top up berhasil. Saldo sekarang: Rp" + customer.getBalance());
                    break;
                case 2:
                    System.out.print("Masukkan jumlah pembelian: ");
                    double purchaseAmount = scanner.nextDouble();
                    if (customer.purchase(purchaseAmount)) {
                        System.out.println("Pembelian berhasil. Saldo sekarang: Rp" + customer.getBalance());
                    }
                    break;
                case 3:
                    System.out.println("Saldo Anda: Rp" + customer.getBalance());
                    break;
                case 4:
                    System.out.println("Terima kasih! Sampai jumpa.");
                    return;
                default:
                    System.out.println("Opsi tidak valid.");
            }
        }
    }
}