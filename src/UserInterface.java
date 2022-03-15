import repositores.GroupRepository;
import repositores.Menu;
import repositores.UserRepository;

import java.util.Scanner;

public class UserInterface {



    private UserRepository ur;
    private GroupRepository gr;

    public UserInterface() {
        ur = new UserRepository();
        gr = new GroupRepository();


    }
    public void run() {
        Scanner input = new Scanner(System.in);
        Menu menu = new Menu("Options til brugere i", "Vælg en af overstående",
                new String[]{"1. Opret bruger", "2. Update bruger", "3. Slet bruger", "4. Hent alle brugere",
                            "5. Opret gruppe", "6. Update gruppe", "7. Slet gruppe",
                            "8. Hent gruppe", "9. Luk program"});
        boolean isRunning = true;
        ur.connectToMySQL();
        gr.connectToMySQL();
        int userId;
        int groupID;
        while (isRunning) {
            menu.printMenu();
            switch (menu.readChoice()) {
                case 1:
                    System.out.print("Skriv brugernavn: ");
                    String name = input.nextLine();

                    System.out.print("Skriv kodeord: ");
                    String password = input.nextLine();

                    System.out.print("Skriv gruppeId: ");
                    int gruppeId = Integer.parseInt(input.nextLine());


                    ur.insertUser(name, password, gruppeId);
                    System.out.println("Du har tilføjet " + name + " " + password);
                    break;
                case 2:
                    System.out.println("Skriv ID på den bruger du vil update");
                    userId = input.nextInt();
                    ur.updateUser(userId);
                    break;
                case 3:
                    System.out.println("Skriv ID på den bruger du vil slette");
                    userId = input.nextInt();
                    ur.deleteUser(userId);

                    break;
                case 4:
                    ur.showAllUsers();
                    break;
                case 5:
                    System.out.print("Indtast gruppenavn: ");
                    String groupName = input.nextLine();

                    System.out.print("Indtast gruppens rettigheder: ");
                    String rights = input.nextLine();

                    gr.insertGroup(groupName, rights);
                    break;
                case 6:
                    System.out.print("Skriv ID på den gruppe du vil opdatere: ");
                    groupID = input.nextInt();
                    gr.updateGroup(groupID);
                    break;
                case 7:
                    System.out.println("Skriv ID på den gruppe du vil slette: ");
                    groupID = input.nextInt();
                    gr.deleteGroup(groupID);
                case 8:
                    gr.showAllGroups();
                    break;

                case 9:
                    isRunning = false;
                    break;
            }
        }
    }

    public static void main(String[] args) {
        new UserInterface().run();

}
        }
