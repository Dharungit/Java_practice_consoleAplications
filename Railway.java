import java.util.*;

//TrainInfo => [ { id, name, route, stations }, ... ]
// Users => [ { name, id, password }, ... ]
// Tricket Details => [ {train1, route, Booked},{train2, route, Booked},{train3, route, Cancelled} ]

public class Railway {
    private static Scanner sc;

    static void clear() {
        System.out.print("\033[H\033[2J");
    }

    static void railwayMain() {
        clear();
        System.out.println("1.Admin Login");
        System.out.println("2.User Login");
        System.out.println("3.Exit");
        int n = sc.nextInt();

        if (n == 1) {
            Admin.login();
        } else if (n == 2) {
            User.userMain1();
        } else if (n == 3) {
            System.exit(0);

        } else {
            System.out.println("Invalid option");
            System.out.println();
            System.out.println("Press enter to continue...");
            try {
                System.in.read();
                railwayMain();
            } catch (Exception e) {
            }
        }
    }

    public static void main(String[] args) {
        sc = new Scanner(System.in);

        for (int i = 0; i < Admin.x_axis_length; i++) {
            Admin.Train.add(new ArrayList<ArrayList<String>>(Admin.y_axis_length));
            for (int j = 0; j < Admin.y_axis_length; j++) {
                Admin.Train.get(i).add(new ArrayList<String>(Arrays.asList("0", "0", "0")));
            }
        }
        Admin.TrainInfo.add(new TrainClass(String.valueOf(Admin.TrainIdGenerator), "ChennaiExpress", "Chennai-Bombay",
                "Chennai Hydrabad Bombay"));
        Admin.TrainIdGenerator++;

        User.Users.add(new User("Us0", "User1", "123"));

        railwayMain();
    }
}

class TrainClass {
    public String TrainId;
    public String TrainName;
    public String TrainRoute;
    public String TrainStations;

    TrainClass(String trainId, String trainName, String trainRoute, String trainStations) {
        this.TrainId = trainId;
        this.TrainName = trainName;
        this.TrainRoute = trainRoute;
        this.TrainStations = trainStations;
    }

}

class Admin {
    static boolean called = false;
    static int TrainIdGenerator = 1;

    static int x_axis_length = 1;
    static int y_axis_length = 5;

    static ArrayList<ArrayList<ArrayList<String>>> Train = new ArrayList<>(x_axis_length);
    static List<TrainClass> TrainInfo = new ArrayList<>();

    private static Scanner sc = new Scanner(System.in);
    static String AdminID = "admin";
    static String AdminPassword = "admin";

    static void login() {
        Railway.clear();
        System.out.println("\tAdmin Login");
        System.out.println("Enter Admin Id: ");
        String adminId = sc.next();
        System.out.println("Enter Password: ");
        String password = sc.next();

        if (adminId.equals(AdminID) && password.equals(AdminPassword)) {
            adminMain();
        } else {
            System.out.println("Invalid Credentials");
            try {
                System.in.read();
                Railway.railwayMain();
            } catch (Exception e) {
            }
        }
    }

    static void adminMain() {
        Railway.clear();
        System.out.println("1. Add Train");
        System.out.println("2. Declare Seats Availability");
        System.out.println("3. View trains");
        System.out.println("4. Back");

        int AdminOption = sc.nextInt();

        if (AdminOption == 1) {
            addTrain();
        } else if (AdminOption == 2) {
            declareSeatsAvailability();
        } else if (AdminOption == 3) {
            called = true;
            printTrains();
        } else if (AdminOption == 4) {
            Railway.railwayMain();
        } else {
            System.out.println("Invalid Option");
            try {
                System.in.read();
                adminMain();
            } catch (Exception e) {
            }
        }

    }

    static void addTrain() {
        Railway.clear();

        System.out.print("Enter Name : ");
        String Name = sc.next();

        System.out.print("Enter Route : ");
        String Route = sc.next();

        System.out.print("Enter Stations : ");
        sc.nextLine();
        String Stations = sc.nextLine();

        Train.add(new ArrayList<ArrayList<String>>()); //[[],[]]
        TrainInfo.add(new TrainClass(String.valueOf(TrainIdGenerator), Name, Route, Stations));
        TrainIdGenerator++;

        System.out.println("Press enter to continue...");
        try {
            System.in.read();
            adminMain();
        } catch (Exception e) {
        }
    }

    static int getAvailability(int index) {
        int res = 0;
        for (int j = 0; j < Train.get(index).size(); j++) {
            for (int k = 0; k < Train.get(index).get(j).size(); k++) {
                if (Train.get(index).get(j).get(k).equals("0")) {
                    res++;
                }
            }
        }

        return res;
    }

    static void printTrains() {
        Railway.clear();
        System.out.println("--------------------------------");
        for (int i = 0; i < Train.size(); i++) {
            for (int j = 0; j < Train.get(i).size(); j++) {
                for (int k = 0; k < Train.get(i).get(j).size(); k++) {
                    System.out.printf("%-5s", Train.get(i).get(j).get(k));
                }
                System.out.println();
            }

            System.out.println("Train Id : " + TrainInfo.get(i).TrainId + "\n" + "Train Name : "
                    + TrainInfo.get(i).TrainName + "\n" + "Train Route : " + TrainInfo.get(i).TrainRoute + "\n"
                    + "Train Stations : " + TrainInfo.get(i).TrainStations);
            System.out.println(
                    "Availability of Seats : " + getAvailability(Integer.parseInt(TrainInfo.get(i).TrainId) - 1));
            System.out.println("..............");
        }
        System.out.println("Press enter to continue...");

        try {
            System.in.read();
            if (called) {
                adminMain();
            } else {
                User.userMain();
            }
        } catch (Exception e) {
        }
    }

    static void declareSeatsAvailability() {
        Railway.clear();

        System.out.println("Enter Train Id : ");
        String id = sc.next();
        boolean isIdPresent = false;
        // TrainInfo = [{1,...},{2,..},{3,...}]
        for (int i = 0; i < TrainInfo.size(); i++) {
            if (TrainInfo.get(i).TrainId.equals(id)) {
                isIdPresent = true;
            }
        }

        if (isIdPresent) {

            System.out.println("Enter No. of Seats : ");
            int No = sc.nextInt();

            int Id = Integer.parseInt(id) - 1;

            if (Train.get(Id).size() == 0) {
                for (int i = 0; i < No; i++) {
                    String[] temp1 = TrainInfo.get(Id).TrainStations.split(" "); 
                    ArrayList<String> temp = new ArrayList<String>();
                    for (int j = 0; j < temp1.length; j++) {
                        temp.add("0"); // [0,0,0]
                    }
                    Train.get(Id).add(temp);
                }
            } else {
                Train.get(Id).clear();
                for (int i = 0; i < No; i++) {
                    String[] temp1 = TrainInfo.get(Id).TrainStations.split(" ");
                    ArrayList<String> temp = new ArrayList<String>();
                    for (int j = 0; j < temp1.length; j++) {
                        temp.add("0");
                    }
                    Train.get(Id).add(temp);
                }
            }

            System.out.println();
            System.out.println("Seat Declaration Done Successfully");
            System.out.println();
            System.out.println("Press enter to continue...");
            try {
                System.in.read();
                adminMain();
            } catch (Exception e) {
            }
        } else {
            System.out.println();
            System.out.println("Invalid Train ID");
            System.out.println();
            System.out.println("Press enter to continue...");
            try {
                System.in.read();
                adminMain();
            } catch (Exception e) {
            }
        }

    }

}

class User {

    private static Scanner sc = new Scanner(System.in);

    static List<User> Users = new ArrayList<>();
    static List<Tickets> TicketDetails = new ArrayList<>();
    static Formatter fmt = new Formatter();

    static int userIdGenerator = 1;
    static boolean isBook = false;

    public String UserId;
    public String UserName;
    public String UserPassword;

    static String CurrentUser = null;

    User(String userId, String userName, String userPassword) {
        this.UserId = userId;
        this.UserName = userName;
        this.UserPassword = userPassword;
    }

    static void userMain1() {
        Railway.clear();
        System.out.print("----- Welcome To User Panel -----\n" +
                "\n1. New User Registration" +
                "\n2. Existing User Login" +
                "\n3. Back\n" +
                "\nEnter your Choice : ");
        int choice = sc.nextInt();
        if (choice == 1) {
            registerUser();
        } else if (choice == 2) {
            login();
        } else if (choice == 3) {
            Railway.railwayMain();
        } else {
            System.out.println("Invalid Options");
            System.out.println();
            System.out.println("Press enter to continue...");
            try {
                System.in.read();
                userMain1();
            } catch (Exception e) {
            }
        }
    }

    static void login() {
        Railway.clear();
        System.out.println("Enter your username");
        String username = sc.next();

        System.out.println("Enter your password");
        String password = sc.next();

        for (int i = 0; i < Users.size(); i++) {
            if (Users.get(i).UserName.equals(username) && Users.get(i).UserPassword.equals(password)) {
                CurrentUser = Users.get(i).UserId;
                userMain();
            }
        }

        System.out.println("Invalid Credentials");
        System.out.println();
        System.out.println("Press enter to continue...");
        try {
            System.in.read();
            Railway.railwayMain();
        } catch (Exception e) {
        }

    }

    static void userMain() {
        Railway.clear();
        System.out.println("1. Trains And Availability ");
        System.out.println("2. Book Ticket");
        System.out.println("3. View Bookings");
        System.out.println("4. Cancle Bookings");
        System.out.println("5. Back");

        int n = sc.nextInt();

        if (n == 1) {
            Admin.called = false;
            Admin.printTrains();
        } else if (n == 2) {
            bookTickets();
        } else if (n == 3) {
            viewBookings();
        } else if (n == 4) {
            cancleBooking();
        } else if (n == 5) {
            Railway.railwayMain();
        } else {
            System.out.println("Invalid option");
            System.out.println();
            System.out.println("Press enter to continue...");
            try {
                System.in.read();
                userMain();
            } catch (Exception e) {
            }
        }

    }

    static void registerUser() {
        Railway.clear();
        System.out.println("Enter your username");
        sc.nextLine();
        String username = sc.nextLine();

        System.out.println("Enter your password");
        String password = sc.next();

        String uId = username.substring(0, 2) + String.valueOf(userIdGenerator);
        userIdGenerator++;

        Users.add(new User(uId, username, password));

        System.out.println();
        System.out.println("Press enter to continue...");
        try {
            System.in.read();
            userMain();
        } catch (Exception e) {
        }
    }

    static void bookTickets() {
        Railway.clear();
        System.out.println();
        System.out.println("****************************** Trains ******************************");
        System.out.println();
        System.out.printf("%-8s%-20s%-20s%-20s\n", "S.no", "Train Name", "Train Route", "Train ID");
        for (int i = 0; i < Admin.TrainInfo.size(); i++) {
            System.out.printf("%-8s%-20s%-20s%-20s\n", i + 1, Admin.TrainInfo.get(i).TrainName,
                    Admin.TrainInfo.get(i).TrainRoute, Admin.TrainInfo.get(i).TrainId);
        }
        System.out.println();
        System.out.println("Enter No. of Booking : ");
        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            Railway.clear();
            System.out.println("Enter Train ID");
            String trainId_book = sc.next();

            boolean isIdPresent = false;
            // TrainInfo = [{1,...},{2,..},{3,...}]
            for (int j = 0; j < Admin.TrainInfo.size(); j++) {
                if (Admin.TrainInfo.get(j).TrainId.equals(trainId_book)) {
                    isIdPresent = true;
                }
            }

            if (isIdPresent) {

                printTrain(Integer.parseInt(trainId_book) - 1);

                System.out.println("Enter Station In and Station Out : ");
                int stationIn = sc.nextInt();
                int stationOut = sc.nextInt();

                int tId = Integer.parseInt(trainId_book) - 1;
                isBook = true;
                int SeatNo = bookSeat(stationIn, stationOut, tId, User.CurrentUser);

                if (SeatNo != -1) {
                    System.out.println("Your Seat No : " + SeatNo);
                } 
                else {
                    TicketDetails.add(new Tickets(-1, CurrentUser, Admin.TrainInfo.get(tId).TrainName,
                            Admin.TrainInfo.get(tId).TrainRoute, "Pending",
                            Integer.parseInt(Admin.TrainInfo.get(tId).TrainId),
                            -1, stationIn, stationOut));
                    System.out.println("Currenly No Seats Available...you are in the waiting list");
                }

                System.out.println();
                System.out.println("Press enter to continue...");
                try {
                    System.in.read();
                } catch (Exception e) {
                }
            } else {
                System.out.println();
                System.out.println("Invalid Train ID");
                System.out.println();
                System.out.println("Press enter to continue...");
                try {
                    System.in.read();
                    bookTickets();
                } catch (Exception e) {
                }
            }
        }

        System.out.println();
        System.out.println("Press enter to continue...");
        try {
            System.in.read();
            userMain();
        } catch (Exception e) {
        }

    }

    static void viewBookings() {
        Railway.clear();

        System.out.printf("%-15s%-20s%-20s%-13s%-13s%-13s%-5s\n", "Ticket No.", "Train Name", "Train Route", "Seat No.",
                "Station In", "Station Out", "Status");
        for (int i = 0; i < TicketDetails.size(); i++) {
            if (TicketDetails.get(i).PassangerId.equals(User.CurrentUser)) {

                System.out.printf("%-15d%-20s%-20s%-13s%-13d%-13d%-5s\n", TicketDetails.get(i).TicketNo,
                        TicketDetails.get(i).Ticket_TrainName, TicketDetails.get(i).Ticket_TrainRoute,
                        TicketDetails.get(i).Ticket_TrainSeat, TicketDetails.get(i).Ticket_Start,
                        TicketDetails.get(i).Ticket_End, TicketDetails.get(i).Ticket_TicketStatus);
            }
        }
        System.out.println();
        System.out.println("Press enter to continue...");
        try {
            System.in.read();
            userMain();
        } catch (Exception e) {
        }
    }

    static void printTrain(int tId) {
        System.out.println("--------------------------------");
        for (int j = 0; j < Admin.Train.get(tId).size(); j++) {
            for (int k = 0; k < Admin.Train.get(tId).get(j).size(); k++) {
                System.out.printf("%-5s", Admin.Train.get(tId).get(j).get(k));
            }
            System.out.println();
        }
        System.out.println("Train Id : " + Admin.TrainInfo.get(tId).TrainId + "\n" + "Train Name : "
                + Admin.TrainInfo.get(tId).TrainName + "\n" + "Train Route : " + Admin.TrainInfo.get(tId).TrainRoute
                + "\n"
                + "Train Stations : " + Admin.TrainInfo.get(tId).TrainStations);
        System.out.println(
                "Availability of Seats : "
                        + Admin.getAvailability(Integer.parseInt(Admin.TrainInfo.get(tId).TrainId) - 1));
        System.out.println("..............");
        System.out.println();
    }

    static int bookSeat(int in, int out, int tId, String setUser) {

        int res = -1;
        for (int i = 0; i < Admin.Train.get(tId).size(); i++) {
            int tot = 0, size = 0;
            for (int k = in - 1; k < out; k++) { 
                size++;
                if (Admin.Train.get(tId).get(i).get(k).equals("0")) {
                    tot++;
                }
            }
            if (tot == size) {

                for (int k = in - 1; k < out; k++) {
                    Admin.Train.get(tId).get(i).set(k, setUser);
                }
                res = i;
                if (isBook) {
                    TicketDetails.add(new Tickets(Tickets.TicketNoGenerator, User.CurrentUser,
                            Admin.TrainInfo.get(tId).TrainName, Admin.TrainInfo.get(tId).TrainRoute, "Booked",
                            Integer.parseInt(Admin.TrainInfo.get(tId).TrainId), i, in, out));
                    Tickets.TicketNoGenerator++;
                }
                return res;
            }
        }

        return res;
    }

    static void bookPending() {
        isBook = false;

        for (int i = 0; i < TicketDetails.size(); i++) {
            if (TicketDetails.get(i).Ticket_TicketStatus.equals("Pending")) {
                int res = bookSeat(TicketDetails.get(i).Ticket_Start, TicketDetails.get(i).Ticket_End,
                        TicketDetails.get(i).Ticket_TrainId - 1, TicketDetails.get(i).PassangerId);
                if (res != -1) {
                    TicketDetails.get(i).Ticket_TicketStatus = "Booked";
                    TicketDetails.get(i).TicketNo = Tickets.TicketNoGenerator;
                    TicketDetails.get(i).Ticket_TrainSeat = res;

                    Tickets.TicketNoGenerator++;
                }
            }
        }
    }

    static void cancleBooking() {
        Railway.clear();
        System.out.println("Enter Ticket No : ");
        int ticker_no = sc.nextInt();

        boolean isTicket = false;
        // TrainInfo = [{1,...},{2,..},{3,...}]
        for (int i = 0; i < TicketDetails.size(); i++) {
            if (TicketDetails.get(i).TicketNo == ticker_no) {
                isTicket = true;
            }
        }

        if (isTicket) {

            for (int i = 0; i < TicketDetails.size(); i++) {
                if (TicketDetails.get(i).TicketNo == ticker_no) {
                    removeSeat(TicketDetails.get(i).Ticket_TrainId - 1, TicketDetails.get(i).Ticket_TrainSeat,
                            TicketDetails.get(i).Ticket_Start, TicketDetails.get(i).Ticket_End);
                    TicketDetails.remove(i);
                    bookPending();
                    break;
                }
            }

            System.out.println();
            System.out.println("Ticket No. " + ticker_no + " Called Successfully!");
        } else {
            System.out.println();
            System.out.println("Ticket No. " + ticker_no + " Not Found Please Enter Valid Ticket Number!");
        }
        System.out.println();
        System.out.println("Press enter to continue...");
        try {
            System.in.read();
            userMain();
        } catch (Exception e) {
        }
    }

    static void removeSeat(int tId, int tSeat, int tStart, int tEnd) {
        for (int j = tStart - 1; j < tEnd; j++) {
            Admin.Train.get(tId).get(tSeat).set(j, "0");
        }
    }

}

class Tickets {

    static int TicketNoGenerator = 1;

    public int TicketNo;
    public String PassangerId;
    public String Ticket_TrainName;
    public String Ticket_TrainRoute;
    public String Ticket_TicketStatus;
    public int Ticket_TrainId;
    public int Ticket_TrainSeat;
    public int Ticket_Start;
    public int Ticket_End;

    Tickets(int TicketNo, String PassangerId, String Ticket_TrainName, String Ticket_TrainRoute,
            String Ticket_TicketStatus, int Ticket_TrainId, int Ticket_TrainSeat, int Ticket_Start, int Ticket_End) {
        this.TicketNo = TicketNo;
        this.PassangerId = PassangerId;
        this.Ticket_TrainName = Ticket_TrainName;
        this.Ticket_TrainRoute = Ticket_TrainRoute;
        this.Ticket_TicketStatus = Ticket_TicketStatus;
        this.Ticket_TrainId = Ticket_TrainId;
        this.Ticket_TrainSeat = Ticket_TrainSeat;
        this.Ticket_Start = Ticket_Start;
        this.Ticket_End = Ticket_End;
    }
}
