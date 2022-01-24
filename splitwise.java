package splitwise;

import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class splitwise {
    private static Scanner sc = new Scanner(System.in);

    static void clear() {
        System.out.print("\033[H\033[2J");
    }

    static void appMain() {
        clear();
        System.out.print("\n1. User Login\n2. Register User\n3. Exit\nEnter your choice : ");
        int n = sc.nextInt();
        if (n == 1) {
            User.login();
        } else if (n == 2) {
            User.register();
        } else if (n == 3) {
            System.exit(0);
        } else {
            System.out.print("\nInvalid choice\nPress enter to continue");
            try {
                System.in.read();
                appMain();
            } catch (Exception e) {
            }
        }

    }

    public static void main(String[] args) {
        User.users.add(new User(User.UserIdGenerator, "Dharun", "d", "1", 1000));
        User.UserIdGenerator++;
        User.users.add(new User(User.UserIdGenerator, "Javahar", "j", "1", 1000));
        User.UserIdGenerator++;
        User.users.add(new User(User.UserIdGenerator, "Arun", "a", "1", 1000));
        User.UserIdGenerator++;
        User.users.add(new User(User.UserIdGenerator, "Mukesh", "m", "1", 1000));
        User.UserIdGenerator++;
        ArrayList<Integer> t = new ArrayList<>(Arrays.asList(0, 1, 2));
        User.groups.add(new Groups(User.GrpIdGenerator, "Foodie", "Group", t));
        User.GrpIdGenerator++;
        appMain();
    }
}

class Groups {
    public int gId;
    public String gName, typeOfGroup;
    public ArrayList<Integer> Members;

    Groups(int gId, String gName, String tog, ArrayList<Integer> Members) {
        this.gId = gId;
        this.gName = gName;
        this.typeOfGroup = tog;
        this.Members = Members;
    }
}

class Expense {
    // expence = [ { typeOfExpence, gId, eID, NameOfExpence, totalExpence,
    // createdBy, Datetime, [u1,u2,u3] } ]
    public String TypeOfExpence, NameOfExpence, DateTime;
    public int groupId, expenseId, CreatedUserId, TotalExpense;
    public ArrayList<Integer> partners;

    Expense(String toe, String noe, String dt, int gid, int eid, int cuid, int TotalExpense,
            ArrayList<Integer> partners) {
        this.TypeOfExpence = toe;
        this.NameOfExpence = noe;
        this.DateTime = dt;
        this.groupId = gid;
        this.expenseId = eid;
        this.CreatedUserId = cuid;
        this.TotalExpense = TotalExpense;
        this.partners = partners;
    }
}

class Due {
    // due = [ { dId, uId, gId, eId, gName, NameOfExpence, amount, status } ]
    public String DNameOfExpence, GroupName, Status, DDateTime;
    public int DueId, UserId, GroupId, ExpId;
    public double ExpAmount;

    Due(String DNameOfExpence, String GroupName, String Status, int DueId, int UserId, int GroupId, int ExpId,
            double ExpAmount, String DDateTime) {
        this.DNameOfExpence = DNameOfExpence;
        this.GroupName = GroupName;
        this.Status = Status;
        this.DueId = DueId;
        this.UserId = UserId;
        this.GroupId = GroupId;
        this.ExpId = ExpId;
        this.ExpAmount = ExpAmount;
        this.DDateTime = DDateTime;
    }
}

class User {

    private static Scanner sc = new Scanner(System.in);
    static ArrayList<User> users = new ArrayList<>();
    static ArrayList<Groups> groups = new ArrayList<>();
    static ArrayList<Expense> expenses = new ArrayList<>();
    static ArrayList<Due> dues = new ArrayList<>();

    static int CurrentUser = 0;
    static int UserIdGenerator = 0;
    static int ExpIdGenerator = 0;
    static int GrpIdGenerator = 0;
    static int DueIdGenerator = 0;

    // userDetails = [ { uId, name, email, password, wallet } ]
    // expence = [ { typeOfExpence, gId, eID, NameOfExpence, totalExpence,
    // createdBy, Datetime, [u1,u2,u3] } ]
    // group = [ { gId, gName, [u1,u2,u3] } ]
    // due = [ { dId, uId, gId, eId, gName, NameOfExpence, amount, status, DateTime
    // } ]

    public String Name, Email, Password;
    public int Wallet, uId;

    User(int uId, String name, String email, String password, int wallet) {
        this.uId = uId;
        this.Name = name;
        this.Email = email;
        this.Password = password;
        this.Wallet = wallet;
    }

    static String getDateTime() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return myDateObj.format(myFormatObj);
    }

     
    static void clear() {
        System.out.print("\033[H\033[2J");
    }

    static void login() {
        clear();
        System.out.print("\nEnter your email : ");
        String email = sc.next();
        System.out.print("\nEnter your password : ");
        String password = sc.next();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).Email.equals(email) && users.get(i).Password.equals(password)) {
                CurrentUser = users.get(i).uId;
                userMain();
            }
        }
        System.out.print("\nInvalid Credentials\nPress enter to continue");
        try {
            System.in.read();
            splitwise.appMain();
        } catch (Exception e) {
        }
    }

    static void register() {
        clear();
        System.out.print("\nEnter your name : ");
        String name = sc.next();
        System.out.print("\nEnter your Email : ");
        String email = sc.next();
        System.out.print("\nEnter your password : ");
        String password = sc.next();
        users.add(new User(UserIdGenerator, name, email, password, 1000)); // registration of new user
        CurrentUser = UserIdGenerator;
        UserIdGenerator++;
        System.out.print("\nRegistration Success\nPress enter to continue");
        try {
            System.in.read();
            userMain();
        } catch (Exception e) {
        }
    }

    static void userMain() {
        clear();
        System.out.println("--------------- Welcome " + users.get(CurrentUser).Name + " ---------------");
        System.out.print(
                "\n1. Groups\n2. Non Groups\n3. Update Wallet\n4. Create Group\n5. Logout\nWallet Balence : "
                        + users.get(CurrentUser).Wallet + "\nEnter your choice : ");
        int n = sc.nextInt();
        if (n == 1) {
            groups();
        } else if (n == 2) {
            nonGroupMenu();
        } else if (n == 3) {
            updateWallet();
        } else if (n == 4) {
            createGroup();
        } else if (n == 5) {
            splitwise.appMain();
        } else {
            System.out.print("\nInvalid choice\nPress enter to continue");
            try {
                System.in.read();
                userMain();
            } catch (Exception e) {
            }
        }
    }

    static void nonGroupMenu() {
        clear();
        System.out.print(
                "\n1. Expenses\n2.Add Expenses\n3.View Dues\n4.Transaction History\n5.Back\nEnter your choice : ");
        int n = sc.nextInt();
        if (n == 1) {
            nonGrpExpenses();
        } else if (n == 2) {
            addNonGrpExpense();
        } else if (n == 3) {
            viewNonGrpDues();
        } else if (n == 4) {
            nonGrpTraHis();
        } else if (n == 5) {
            userMain();
        } else {
            System.out.print("\nInvalid choice\nPress enter to continue");
            try {
                System.in.read();
                nonGroupMenu();
            } catch (Exception e) {
            }
        }
    }

    static void nonGrpTraHis() {
        clear();
        System.out.printf("\n%-15s%-20s%-10s%-20s%-25s", "Name", "Expense", "Amount", "Expense By", "Date/Time");
        for (int i = 0; i < dues.size(); i++) {
            if (expenses.get(dues.get(i).ExpId).TypeOfExpence.equals("Non-Group")
                    && dues.get(i).Status.equals("Paid") && dues.get(i).UserId == CurrentUser) {
                System.out.printf("\n%-15s%-20s%-10s%-20s%-25s", users.get(dues.get(i).UserId).Name,
                        dues.get(i).DNameOfExpence,
                        dues.get(i).ExpAmount, users.get(expenses.get(dues.get(i).ExpId).CreatedUserId).Name,
                        dues.get(i).DDateTime);
            }
        }
        System.out.println("\n\nPress enter to continue");
        try {
            System.in.read();
            nonGroupMenu();
        } catch (Exception e) {
        }
    }

    static void viewNonGrpDues() {
        clear();
        boolean flag = false;
        System.out.printf("\n%-10s%-20s%-10s%-20s", "Due Id", "Expense", "Amount", "Expense By");
        for (int i = 0; i < dues.size(); i++) {
            if (dues.get(i).UserId == CurrentUser && dues.get(i).Status.equals("Pending")
                    && expenses.get(dues.get(i).ExpId).TypeOfExpence.equals("Non-Group")) {
                flag = true;
                System.out.printf("\n%-10d%-20s%-10.2f%-20s", dues.get(i).DueId, dues.get(i).DNameOfExpence,
                        dues.get(i).ExpAmount, users.get(expenses.get(dues.get(i).ExpId).CreatedUserId).Name);
            }
        }
        if (flag) {
            System.out.print("\nDo you want to pay your due? (y/n) : ");
            String option = sc.next();
            if (option.equals("y")) {
                System.out.print("\nEnter No. of payments : ");
                int n = sc.nextInt();
                for (int i = 0; i < n; i++) {
                    System.out.print("\nEnter Due Id : ");
                    int dId = sc.nextInt();

                    if (users.get(CurrentUser).Wallet >= dues.get(dId).ExpAmount) {
                        users.get(expenses.get(dues.get(i).ExpId).CreatedUserId).Wallet += dues.get(dId).ExpAmount;
                        users.get(CurrentUser).Wallet -= dues.get(dId).ExpAmount;
                        dues.get(dId).Status = "Paid";
                        dues.get(dId).DDateTime = getDateTime();
                        System.out.print("\nPayment Done Successfully\nPress enter to continue");
                        try {
                            System.in.read();
                        } catch (Exception e) {
                        }

                    } else {
                        System.out.print("\nInsufficient funds in wallet\nPress enter to continue");
                        try {
                            System.in.read();
                            nonGroupMenu();
                        } catch (Exception e) {
                        }
                    }
                }
                System.out.print("\nPayments Done Successfully\nPress enter to continue");
                try {
                    System.in.read();
                    nonGroupMenu();
                } catch (Exception e) {
                }
            } else {
                System.out.print("\nPress enter to go back");
                try {
                    System.in.read();
                    nonGroupMenu();
                } catch (Exception e) {
                }
            }
        } else {
            System.out.print("\n--------No Dues!---------\nPress enter to go back");
            try {
                System.in.read();
                nonGroupMenu();
            } catch (Exception e) {
            }
        }
    }

    static void addNonGrpExpense() {
        clear();
        ArrayList<Integer> tempNg = new ArrayList<>();
        groups.add(new Groups(GrpIdGenerator, users.get(CurrentUser).Name.substring(0, 3) + "NG", "Non-Group", tempNg));
        int newGrpId = GrpIdGenerator;
        GrpIdGenerator++;
        System.out.printf("\n%-10s%-15s", "User ID", "Name");
        for (int i = 0; i < users.size(); i++) {
            System.out.printf("\n%-10d%-15s", users.get(i).uId, users.get(i).Name);
        }
        groups.get(newGrpId).Members.add(CurrentUser);
        System.out.print("\nEnter No. of Friends to add : ");
        int noOfFriends = sc.nextInt();

        // adding friends to group
        for (int i = 0; i < noOfFriends; i++) {
            System.out.println("\nEnter Friend User ID : ");
            int userId = sc.nextInt();
            if (!groups.get(newGrpId).Members.contains(userId)) {
                groups.get(newGrpId).Members.add(userId);
                System.out.print("\nAdded " + users.get(userId).Name + "!");
            } else {
                System.out.print("\nFriend Already Added");
                i--;
            }
        }

        System.out.print("\nExpense Name : ");
        String expenseName = sc.next();
        System.out.print("\nTotal Expense : ");
        int expenseAmount = sc.nextInt();

        String DT = getDateTime();
        ArrayList<Integer> temp = new ArrayList<Integer>();
        temp.addAll(groups.get(newGrpId).Members);
        // creating expense
        expenses.add(
                new Expense("Non-Group", expenseName, DT, newGrpId, ExpIdGenerator, CurrentUser, expenseAmount, temp));
        // creating Dues

        System.out.println("1. Split the expense equally\n2. Split by Individual\nEnter your choice : ");
        int option = sc.nextInt();
        if (option == 1) {

            double IndividualExpenses = expenseAmount / groups.get(newGrpId).Members.size();
            System.out.println(IndividualExpenses);
            for (int i = 0; i < groups.get(newGrpId).Members.size(); i++) {
                if (groups.get(newGrpId).Members.get(i) != CurrentUser) {
                    dues.add(new Due(expenseName, groups.get(newGrpId).gName, "Pending", DueIdGenerator,
                            groups.get(newGrpId).Members.get(i), newGrpId,
                            ExpIdGenerator, IndividualExpenses, "00-00-0000 00:00:00"));
                } else {
                    dues.add(new Due(expenseName, groups.get(newGrpId).gName, "Paid", DueIdGenerator,
                            groups.get(newGrpId).Members.get(i), newGrpId,
                            ExpIdGenerator, IndividualExpenses, getDateTime()));
                }
                DueIdGenerator++;
            }
            ExpIdGenerator++;
        } else if (option == 2) {
            for (int i = 0; i < groups.get(newGrpId).Members.size(); i++) {
                System.out.print("\nEnter Amount for " + users.get(groups.get(newGrpId).Members.get(i)).Name + " : ");
                int amt = sc.nextInt();
                if (groups.get(newGrpId).Members.get(i) != CurrentUser) {
                    dues.add(new Due(expenseName, groups.get(newGrpId).gName, "Pending", DueIdGenerator,
                            groups.get(newGrpId).Members.get(i), newGrpId,
                            ExpIdGenerator, amt, "00-00-0000 00:00:00"));
                } else {
                    dues.add(new Due(expenseName, groups.get(newGrpId).gName, "Paid", DueIdGenerator,
                            groups.get(newGrpId).Members.get(i), newGrpId,
                            ExpIdGenerator, amt, getDateTime()));
                }
                DueIdGenerator++;
            }
            ExpIdGenerator++;
        } else {
            System.out.print("\nInvalid choice\nPress enter to continue");
            try {
                System.in.read();
                nonGroupMenu();
            } catch (Exception e) {
            }
        }

        System.out.println("\n\nExpense Creation Done Successfully!\nPress enter to continue");
        try {
            System.in.read();
            nonGroupMenu();
        } catch (Exception e) {
        }
    }

    static void nonGrpExpDetails(int eid) {
        clear();
        int temp[] = new int[groups.get(expenses.get(eid).groupId).Members.size()];
        int x = 0;
        for (int i = 0; i < groups.get(expenses.get(eid).groupId).Members.size(); i++) {
            for (int j = 0; j < dues.size(); j++) {
                if (dues.get(j).ExpId == eid
                        && dues.get(j).UserId == groups.get(expenses.get(eid).groupId).Members.get(i)) {
                    temp[x] = dues.get(j).DueId;
                    x++;
                }
            }
        }

        System.out.println("\nNon-Groups -> " + groups.get(expenses.get(eid).groupId).gName + " -> "
                + expenses.get(eid).NameOfExpence);
        System.out.println("----------------------------");
        System.out.println("Expense Added by : " + users.get(expenses.get(eid).CreatedUserId).Name);
        System.out.println("Total Expense Amount : " + expenses.get(eid).TotalExpense);
        System.out.print("\n" + expenses.get(eid).NameOfExpence);
        for (int i = 0; i < groups.get(expenses.get(eid).groupId).Members.size(); i++) {
            System.out.printf("\n|\n------ " + users.get(groups.get(expenses.get(eid).groupId).Members.get(i)).Name
                    + " " + dues.get(temp[i]).ExpAmount + " " + dues.get(temp[i]).Status + " " + dues.get(temp[i]).DDateTime);
        }
        System.out.print("\nPress enter to continue");
        try {
            System.in.read();
            nonGroupMenu();
        } catch (Exception e) {
        }
    }

    static void nonGrpExpenses() {
        clear();
        boolean flag = false;
        System.out.println("------------ Expenses ------------");
        System.out.printf("\n%-18s%-20s", "Expense ID", "Expense Name\n");
        for (int i = 0; i < expenses.size(); i++) {
            if (expenses.get(i).partners.contains(CurrentUser) && expenses.get(i).TypeOfExpence.equals("Non-Group")) {
                flag = true;
                System.out.printf("\n%-18d%-20s\n", expenses.get(i).expenseId, expenses.get(i).NameOfExpence);
            }
        }
        System.out.print("\n\nDo you want to view the expense? (y/n) : ");
        String option = sc.next();
        if (option.equals("y")) {
            if (flag) {
                System.out.println("------------------------");
                System.out.print("\nEnter Expense ID : ");
                int eid = sc.nextInt();
                nonGrpExpDetails(eid);
            } else {
                System.out.print("\n---------- No Expense Available ---------\nPress enter to continue");
                try {
                    System.in.read();
                    nonGroupMenu();
                } catch (Exception e) {
                }
            }
        } else {
            System.out.print("\nPress enter to continue");
            try {
                System.in.read();
                nonGroupMenu();
            } catch (Exception e) {
            }
        }
    }

    static void updateWallet() {
        clear();
        System.out.print("\nEnter Amount to Deposit : ");
        users.get(CurrentUser).Wallet += sc.nextInt();
        System.out.print("\nAmount added to wallet : " + users.get(CurrentUser).Wallet + "\nPress enter to continue");
        try {
            System.in.read();
            userMain();
        } catch (Exception e) {
        }
    }

    static void createGroup() {
        clear();
        System.out.print("\nEnter Group Name : ");
        String groupName = sc.next();

        // creating group
        ArrayList<Integer> temp = new ArrayList<>();
        int newGrpId = GrpIdGenerator;
        groups.add(new Groups(GrpIdGenerator, groupName, "Group", temp));
        GrpIdGenerator++;

        // printing available friends
        System.out.printf("\n%-10s%-15s", "User ID", "Name");
        for (int i = 0; i < users.size(); i++) {
            System.out.printf("\n%-10d%-15s", users.get(i).uId, users.get(i).Name);
        }
        groups.get(newGrpId).Members.add(CurrentUser);

        System.out.print("\nEnter No. of Friends to add : ");
        int noOfFriends = sc.nextInt();

        // adding friends to group
        for (int i = 0; i < noOfFriends; i++) {
            System.out.println("\nEnter Friend User ID : ");
            int userId = sc.nextInt();
            if (!groups.get(newGrpId).Members.contains(userId)) {
                groups.get(newGrpId).Members.add(userId);
                System.out.print("\nAdded " + users.get(userId).Name + "!");
            } else {
                System.out.print("\nFriend Already in the Group");
                i--;
            }
        }

        System.out.println("\n\nGroup Creation Done Successfully!\nPress enter to continue");
        try {
            System.in.read();
            userMain();
        } catch (Exception e) {
        }
    }

    static void groups() {
        clear();
        boolean flag = false;
        System.out.println("------------ Groups ------------");
        System.out.printf("\n%-10s%-20s", "Group ID", "Group Name\n");
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).Members.contains(CurrentUser) && groups.get(i).typeOfGroup.equals("Group")) {
                flag = true;
                System.out.printf("\n%-10d%-20s\n", groups.get(i).gId, groups.get(i).gName);
            }
        }
        System.out.print("\n\nWant Enter the group? (y/n) : ");
        String option = sc.next();
        if (option.equals("y")) {
            if (flag) {
                System.out.println("------------------------");
                System.out.print("\nEnter Group ID : ");
                int gid = sc.nextInt();
                groupMenu(gid);
            } else {
                System.out.print("\n----------No Groups---------\nPress enter to continue");
                try {
                    System.in.read();
                    userMain();
                } catch (Exception e) {
                }
            }
        } else {
            System.out.print("\nPress enter to continue");
            try {
                System.in.read();
                userMain();
            } catch (Exception e) {
            }
        }
    }

    static void groupMenu(int gid) {
        clear();
        System.out.print(
                "\nGroups -> " + groups.get(gid).gName
                        + "\n--------------------" + "\n1. Expenses\n2. Add Expense\n3. Add Friends\n4. Remove Friends\n5. View Dues\n6. Transaction History\n7. Back\n\nEnter your choice : ");
        int n = sc.nextInt();
        if (n == 1) {
            expenses(gid);
        } else if (n == 2) {
            addExpense(gid);
        } else if (n == 3) {
            addFriend(gid);
        } else if (n == 4) {
            removeFriend(gid);
        } else if (n == 5) {
            viewDues(gid);
        } else if (n == 6) {
            transactionHistory(gid);
        } else if (n == 7) {
            userMain();
        } else {
            System.out.print("\nInvalid choice\nPress enter to continue");
            try {
                System.in.read();
                groupMenu(gid);
            } catch (Exception e) {
            }
        }

    }

    static void expenseDetails(int eid, int gid) {
        clear();
        int temp[] = new int[groups.get(gid).Members.size()];
        int x = 0;
        for (int i = 0; i < groups.get(gid).Members.size(); i++) {
            for (int j = 0; j < dues.size(); j++) {
                if (dues.get(j).ExpId == eid && dues.get(j).UserId == groups.get(gid).Members.get(i)) {
                    temp[x] = dues.get(j).DueId;
                    x++;
                }
            }
        }

        System.out.println("\nGroups -> " + groups.get(gid).gName + " -> " + expenses.get(eid).NameOfExpence);
        System.out.println("----------------------------");
        System.out.println("\nExpense Added by : " + users.get(expenses.get(eid).CreatedUserId).Name);
        System.out.println("Total Expense Amount : " + expenses.get(eid).TotalExpense);
        System.out.print("\n" + expenses.get(eid).NameOfExpence);
        for (int i = 0; i < groups.get(gid).Members.size(); i++) {
            System.out.printf("\n|\n------ " + users.get(groups.get(gid).Members.get(i)).Name + " "
                    + dues.get(temp[i]).ExpAmount + " " + dues.get(temp[i]).Status + " " + dues.get(temp[i]).DDateTime);
        }
        System.out.print("\nPress enter to continue");
        try {
            System.in.read();
            groupMenu(gid);
        } catch (Exception e) {
        }

    }

    static void expenses(int gid) {
        clear();
        boolean flag = false;
        System.out.println("------------ Expenses ------------");
        System.out.printf("\n%-18s%-20s", "Expense ID", "Expense Name\n");
        for (int i = 0; i < expenses.size(); i++) {
            if (expenses.get(i).groupId == gid && expenses.get(i).TypeOfExpence == "Group") {
                flag = true;
                System.out.printf("\n%-18d%-20s\n", expenses.get(i).expenseId, expenses.get(i).NameOfExpence);
            }
        }
        System.out.print("\n\nDo you want to view the expense? (y/n) : ");
        String option = sc.next();
        if (option.equals("y")) {
            if (flag) {
                System.out.println("------------------------");
                System.out.print("\nEnter Expense ID : ");
                int eid = sc.nextInt();
                expenseDetails(eid, gid);
            } else {
                System.out.print("\n---------- No Expense Available ---------\nPress enter to continue");
                try {
                    System.in.read();
                    groupMenu(gid);
                } catch (Exception e) {
                }
            }
        } else {
            System.out.print("\nPress enter to continue");
            try {
                System.in.read();
                groupMenu(gid);
            } catch (Exception e) {
            }
        }
    }

    static void transactionHistory(int gid) {
        clear();
        System.out.printf("\n%-15s%-20s%-10s%-20s%-25s", "Name", "Expense", "Amount", "Expense By", "Date/Time");
        for (int i = 0; i < dues.size(); i++) {
            if (dues.get(i).GroupId == gid
                    && dues.get(i).Status.equals("Paid")) {
                System.out.printf("\n%-15s%-20s%-10s%-20s%-25s", users.get(dues.get(i).UserId).Name,
                        dues.get(i).DNameOfExpence,
                        dues.get(i).ExpAmount, users.get(expenses.get(dues.get(i).ExpId).CreatedUserId).Name,
                        dues.get(i).DDateTime);
            }
        }
        System.out.println("\n\nPress enter to continue");
        try {
            System.in.read();
            groupMenu(gid);
        } catch (Exception e) {
        }

    }

    static void addFriend(int gid) {
        clear();
        System.out.printf("\n%-10s%-15s", "User ID", "Name");
        for (int i = 0; i < users.size(); i++) {
            System.out.printf("\n%-10d%-15s", users.get(i).uId, users.get(i).Name);
        }
        System.out.print("\nEnter No. of Friends to add : ");
        int noOfFriends = sc.nextInt();

        for (int i = 0; i < noOfFriends; i++) {
            System.out.println("\nEnter Friend User ID : ");
            int userId = sc.nextInt();
            if (!groups.get(gid).Members.contains(userId)) {
                groups.get(gid).Members.add(userId);
                System.out.print("\nAdded " + users.get(userId).Name + "!");
            } else {
                System.out.print("\nFriend Already in the Group");
                i--;
            }
        }

        System.out.println("\nFriends Added Successfully!\nPress enter to continue");
        try {
            System.in.read();
            groupMenu(gid);
        } catch (Exception e) {
        }
    }

    static void removeFriend(int gid) {
        clear();
        System.out.printf("\n%-10s%-15s", "User ID", "Name");
        for (int i = 0; i < groups.get(gid).Members.size(); i++) {
            System.out.printf("\n%-10d%-15s", groups.get(gid).Members.get(i),
                    users.get(groups.get(gid).Members.get(i)).Name);
        }
        System.out.print("\nEnter No. of Friends to Remove : ");
        int noOfFriends = sc.nextInt();
        for (int i = 0; i < noOfFriends; i++) {
            System.out.println("\nEnter Friend User ID : ");
            int userId = sc.nextInt();
            if (groups.get(gid).Members.contains(userId)) {
                int indx = groups.get(gid).Members.indexOf(userId);
                groups.get(gid).Members.remove(indx);
                System.out.print("\nRemoved " + users.get(userId).Name + "!");
            } else {
                System.out.print("\nFriend Not in the Group");
                i--;
            }
        }
        System.out.println("\nFriends Removed Successfully!\nPress enter to continue");
        try {
            System.in.read();
            groupMenu(gid);
        } catch (Exception e) {
        }
    }

    static void viewDues(int gid) {
        clear();
        boolean flag = false;
        System.out.printf("\n%-10s%-20s%-10s%-20s", "Due Id", "Expense", "Amount", "Expense By");
        for (int i = 0; i < dues.size(); i++) {
            if (dues.get(i).UserId == CurrentUser && dues.get(i).GroupId == gid
                    && dues.get(i).Status.equals("Pending")) {
                flag = true;
                System.out.printf("\n%-10d%-20s%-10.2f%-20s", dues.get(i).DueId, dues.get(i).DNameOfExpence,
                        dues.get(i).ExpAmount, users.get(expenses.get(dues.get(i).ExpId).CreatedUserId).Name);
            }
        }
        if (flag) {
            System.out.print("\nDo you want to pay your due? (y/n) : ");
            String option = sc.next();
            if (option.equals("y")) {
                System.out.print("\nEnter No. of payments : ");
                int n = sc.nextInt();
                for (int i = 0; i < n; i++) {
                    System.out.print("\nEnter Due Id : ");
                    int dId = sc.nextInt();

                    if (users.get(CurrentUser).Wallet >= dues.get(dId).ExpAmount) {
                        users.get(expenses.get(dues.get(i).ExpId).CreatedUserId).Wallet += dues.get(dId).ExpAmount;
                        users.get(CurrentUser).Wallet -= dues.get(dId).ExpAmount;
                        dues.get(dId).Status = "Paid";
                        dues.get(dId).DDateTime = getDateTime();
                        System.out.print("\nPayment Done Successfully\nPress enter to continue");
                        try {
                            System.in.read();
                        } catch (Exception e) {
                        }

                    } else {
                        System.out.print("\nInsufficient funds in wallet\nPress enter to continue");
                        try {
                            System.in.read();
                            groupMenu(gid);
                        } catch (Exception e) {
                        }
                    }
                }
                System.out.print("\nPayments Done Successfully\nPress enter to continue");
                try {
                    System.in.read();
                    groupMenu(gid);
                } catch (Exception e) {
                }
            } else {
                System.out.print("\nPress enter to go back");
                try {
                    System.in.read();
                    groupMenu(gid);
                } catch (Exception e) {
                }
            }
        } else {
            System.out.print("\n--------No Dues!---------\nPress enter to go back");
            try {
                System.in.read();
                groupMenu(gid);
            } catch (Exception e) {
            }
        }

    }

    static void addExpense(int gid) {
        clear();
        System.out.print("\nExpense Name : ");
        String expenseName = sc.next();
        System.out.print("\nTotal Expense : ");
        int expenseAmount = sc.nextInt();

        String DT = getDateTime();
        ArrayList<Integer> temp = new ArrayList<Integer>();
        temp.addAll(groups.get(gid).Members);
        // creating expense
        expenses.add(new Expense("Group", expenseName, DT, gid, ExpIdGenerator, CurrentUser, expenseAmount, temp));
        // creating Dues
        double IndividualExpenses = expenseAmount / groups.get(gid).Members.size();
        System.out.println(IndividualExpenses);
        for (int i = 0; i < groups.get(gid).Members.size(); i++) {
            if (groups.get(gid).Members.get(i) != CurrentUser) {
                dues.add(new Due(expenseName, groups.get(gid).gName, "Pending", DueIdGenerator,
                        groups.get(gid).Members.get(i), gid,
                        ExpIdGenerator, IndividualExpenses, "00-00-0000 00:00:00"));
            } else {
                dues.add(new Due(expenseName, groups.get(gid).gName, "Paid", DueIdGenerator,
                        groups.get(gid).Members.get(i), gid,
                        ExpIdGenerator, IndividualExpenses, getDateTime()));
            }
            DueIdGenerator++;
        }
        ExpIdGenerator++;
        System.out.print("\nExpense Added Successfully\nPress enter to continue");
        try {
            System.in.read();
            groupMenu(gid);
        } catch (Exception e) {
        }
    }

}
