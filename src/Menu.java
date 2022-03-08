import java.util.Scanner;

public class Menu {
    private String menuHeader;
    private String leadText;
    private String[] menuItems;

    public Menu(String menuHeader, String leadText, String[] menuItems) {
        this.menuHeader = menuHeader;
        this.leadText = leadText;
        this.menuItems = menuItems;
    }

    public void printMenu() {
        System.out.println(menuHeader);
        for (String string: menuItems) {
            System.out.println(string);
        }
        System.out.println(leadText);

    }

    public int readChoice() {
        Scanner in = new Scanner(System.in);
        while(!in.hasNextInt()) {
            System.out.println(leadText);

            // Til at tømme scanneren, for at unggå et infinite loop
            in.nextLine();
        }
        return in.nextInt();
    }
}