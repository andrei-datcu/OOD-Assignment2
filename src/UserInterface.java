/**
 *
 * @author Brahma Dathan and Sarnath Ramnath
 * @Copyright (c) 2010

 * Redistribution and use with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - the use is for academic purpose only
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   - Neither the name of Brahma Dathan or Sarnath Ramnath
 *     may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * The authors do not make any claims regarding the correctness of the code in this module
 * and are not responsible for any loss or damage resulting from its use.  
 */
import items.models.Item;
import items.Transaction;
import member.Member;

import java.util.*;
import java.text.*;
import java.io.*;
/**
 *
 * This class implements the user interface for the Library project.
 * The commands are encoded as integers using a number of
 * static final variables. A number of utility methods exist to
 * make it easier to parse the input.
 *
 */
public class UserInterface {
    private static UserInterface userInterface;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Library library;
    private static final int EXIT = 0;
    private static final int ADD_MEMBER = 1;
    private static final int ADD_ITEMS = 2;
    private static final int ISSUE_ITEMS = 3;
    private static final int RETURN_ITEMS = 4;
    private static final int RENEW_ITEMS = 5;
    private static final int REMOVE_ITEMS = 6;
    private static final int PLACE_HOLD = 7;
    private static final int REMOVE_HOLD = 8;
    private static final int PROCESS_HOLD = 9;
    private static final int GET_TRANSACTIONS = 10;
    private static final int SAVE = 11;
    private static final int RETRIEVE = 12;
    private static final int HELP = 13;
    private static final int ADD_FINE = 99;
    /**
     * Made private for singleton pattern.
     * Conditionally looks for any saved data. Otherwise, it gets
     * a singleton Library object.
     */
    private UserInterface() {
        if (yesOrNo("Look for saved data and  use it?")) {
            retrieve();
        } else {
            library = Library.instance();
        }
    }
    /**
     * Supports the singleton pattern
     *
     * @return the singleton object
     */
    public static UserInterface instance() {
        if (userInterface == null) {
            return userInterface = new UserInterface();
        } else {
            return userInterface;
        }
    }
    /**
     * Gets a token after prompting
     *
     * @param prompt - whatever the user wants as prompt
     * @return - the token from the keyboard
     *
     */
    public String getToken(String prompt) {
        do {
            try {
                System.out.println(prompt);
                String line = reader.readLine();
                StringTokenizer tokenizer = new StringTokenizer(line,"\n\r\f");
                if (tokenizer.hasMoreTokens()) {
                    return tokenizer.nextToken();
                }
            } catch (IOException ioe) {
                System.exit(0);
            }
        } while (true);
    }
    /**
     * Queries for a yes or no and returns true for yes and false for no
     *
     * @param prompt The string to be prepended to the yes/no prompt
     * @return true for yes and false for no
     *
     */
    private boolean yesOrNo(String prompt) {
        String more = getToken(prompt + " (Y|y)[es] or anything else for no");
        if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
            return false;
        }
        return true;
    }
    /**
     * Converts the string to a number
     * @param prompt the string for prompting
     * @return the integer corresponding to the string
     *
     */
    public int getNumber(String prompt) {
        do {
            try {
                String item = getToken(prompt);
                Integer number = Integer.valueOf(item);
                return number.intValue();
            } catch (NumberFormatException nfe) {
                System.out.println("Please input a number ");
            }
        } while (true);
    }
    /**
     * Prompts for a date and gets a date object
     * @param prompt the prompt
     * @return the data as a Calendar object
     */
    public Calendar getDate(String prompt) {
        do {
            try {
                Calendar date = new GregorianCalendar();
                String item = getToken(prompt);
                DateFormat dateFormat = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
                date.setTime(dateFormat.parse(item));
                return date;
            } catch (Exception fe) {
                System.out.println("Please input a date as mm/dd/yy");
            }
        } while (true);
    }
    /**
     * Prompts for a command from the keyboard
     *
     * @return a valid command
     *
     */
    public int getCommand() {
        do {
            try {
                int value = Integer.parseInt(getToken("Enter command:" + HELP + " for help"));
                if ((value >= EXIT && value <= HELP) || value == ADD_FINE){
                    return value;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Enter a number");
            }
        } while (true);
    }
    /**
     * Displays the help screen
     *
     */
    public void help() {
        System.out.println("Enter a number between 0 and 14 as explained below:");
        System.out.println(EXIT + " to Exit\n");
        System.out.println(ADD_MEMBER + " to  add a member");
        System.out.println(ADD_ITEMS + " to  add items");
        System.out.println(ISSUE_ITEMS + " to  issue items to a  member");
        System.out.println(RETURN_ITEMS + " to  return items ");
        System.out.println(RENEW_ITEMS + " to  renew items ");
        System.out.println(REMOVE_ITEMS + " to  remove items");
        System.out.println(PLACE_HOLD + " to  place a hold on a item");
        System.out.println(REMOVE_HOLD + " to  remove a hold on a item");
        System.out.println(PROCESS_HOLD + " to  process holds");
        System.out.println(GET_TRANSACTIONS + " to print transactions");
        System.out.println(SAVE + " to save data");
        System.out.println(RETRIEVE + " to retrieve");
        System.out.println(HELP + " for help");
        System.out.println(ADD_FINE + " to add fines");
    }
    /**
     * Method to be called for adding a member.
     * Prompts the user for the appropriate values and
     * uses the appropriate Library method for adding the member.
     *
     */
    public void addMember() {
        String name = getToken("Enter member name");
        String address = getToken("Enter address");
        String phone = getToken("Enter phone");
        Member result;
        result = library.addMember(name, address, phone);
        if (result == null) {
            System.out.println("Could not add member");
        }
        System.out.println(result);
    }
    /**
     * Method to be called for adding a item.
     * Prompts the user for the appropriate values and
     * uses the appropriate Library method for adding the item.
     *
     */
    public void addItems() {
        Item result;
        do {
            String type = getToken(String.format("Enter item kind (%s)", GeneralParamsHelper.getSupportedItemTypes()));
            String[] paramNames = GeneralParamsHelper.getParameterNames(type);
            if (paramNames == null) {
                System.out.println("Uknown item type");
                break;
            }

            ArrayList<String> params = new ArrayList<>();
            for (String paramName : paramNames) {
                params.add(getToken(String.format("Enter %s", paramName)));
            }
            result = library.addItem(type, params.toArray());
            if (result != null) {
                System.out.println(result);
            } else {
                System.out.println("Item could not be added");
            }
            if (!yesOrNo("Add more items?")) {
                break;
            }
        } while (true);
    }
    /**
     * Method to be called for issuing items.
     * Prompts the user for the appropriate values and
     * uses the appropriate Library method for issuing items.
     *
     */
    public void issueItems() {
        Item result;
        String memberID = getToken("Enter member id");
        if (library.searchMembership(memberID) == null) {
            System.out.println("No such member");
            return;
        }
        do {
            String itemID = getToken("Enter item id");
            result = library.issueItem(memberID, itemID);
            if (result != null){
                System.out.println(result.toString()+ "   " +  result.getDueDate());
            } else {
                System.out.println("Item could not be issued");
            }
            if (!yesOrNo("Issue more items?")) {
                break;
            }
        } while (true);
    }
    /**
     * Method to be called for renewing items.
     * Prompts the user for the appropriate values and
     * uses the appropriate Library method for renewing items.
     *
     */
    public void renewItems() {
        Item result;
        String memberID = getToken("Enter member id");
        if (library.searchMembership(memberID) == null) {
            System.out.println("No such member");
            return;
        }
        Iterator issuedItems = library.getItems(memberID);
        while (issuedItems.hasNext()){
            Item item = (Item)(issuedItems.next());
            if (yesOrNo(item.toString())) {
                result = library.renewItem(item.getId(), memberID);
                if (result != null){
                    System.out.println(result.toString()+ "   " + result.getDueDate());
                } else {
                    System.out.println("Item is not renewable");
                }
            }
        }
    }
    /**
     * Method to be called for returning items.
     * Prompts the user for the appropriate values and
     * uses the appropriate Library method for returning items.
     *
     */
    public void returnItems() {
        int result;
        do {
            String itemID = getToken("Enter item id");
            result = library.returnItem(itemID);
            switch(result) {
                case Library.ITEM_NOT_FOUND:
                    System.out.println("No such items.models.Item in Library");
                    break;
                case Library.ITEM_NOT_ISSUED:
                    System.out.println(" items.models.Item  was not checked out");
                    break;
                case Library.ITEM_HAS_HOLD:
                    System.out.println("items.models.Item has a hold");
                    break;
                case Library.OPERATION_FAILED:
                    System.out.println("items.models.Item could not be returned");
                    break;
                case Library.OPERATION_COMPLETED:
                    System.out.println(" items.models.Item has been returned");
                    break;
                default:
                    System.out.println("An error has occurred");
            }
            if (!yesOrNo("Return more items?")) {
                break;
            }
        } while (true);
    }
    /**
     * Method to be called for removing items.
     * Prompts the user for the appropriate values and
     * uses the appropriate Library method for removing items.
     *
     */
    public void removeItems() {
        int result;
        do {
            String itemID = getToken("Enter item id");
            result = library.removeItem(itemID);
            switch(result){
                case Library.ITEM_NOT_FOUND:
                    System.out.println("No such items.models.Item in Library");
                    break;
                case Library.ITEM_ISSUED:
                    System.out.println(" items.models.Item is currently checked out");
                    break;
                case Library.ITEM_HAS_HOLD:
                    System.out.println("items.models.Item has a hold");
                    break;
                case Library.OPERATION_FAILED:
                    System.out.println("items.models.Item could not be removed");
                    break;
                case Library.OPERATION_COMPLETED:
                    System.out.println(" items.models.Item has been removed");
                    break;
                default:
                    System.out.println("An error has occurred");
            }
            if (!yesOrNo("Remove more items?")) {
                break;
            }
        } while (true);
    }
    /**
     * Method to be called for placing a hold.
     * Prompts the user for the appropriate values and
     * uses the appropriate Library method for placing a hold.
     *
     */
    public void placeHold() {
        String memberID = getToken("Enter member id");
        String itemID = getToken("Enter item id");
        int duration = getNumber("Enter duration of hold");
        int result = library.placeHold(memberID, itemID, duration);
        switch(result){
            case Library.ITEM_NOT_FOUND:
                System.out.println("No such items.models.Item in Library");
                break;
            case Library.ITEM_NOT_ISSUED:
                System.out.println(" items.models.Item is not checked out");
                break;
            case Library.NO_SUCH_MEMBER:
                System.out.println("Not a valid member ID");
                break;
            case Library.HOLD_PLACED:
                System.out.println("A hold has been placed");
                break;
            default:
                System.out.println("An error has occurred");
        }
    }
    /**
     * Method to be called for removing a holds.
     * Prompts the user for the appropriate values and
     * uses the appropriate Library method for removing a hold.
     *
     */
    public void removeHold() {
        String memberID = getToken("Enter member id");
        String itemID = getToken("Enter item id");
        int result = library.removeHold(memberID, itemID);
        switch(result){
            case Library.ITEM_NOT_FOUND:
                System.out.println("No such items.models.Item in Library");
                break;
            case Library.NO_SUCH_MEMBER:
                System.out.println("Not a valid member ID");
                break;
            case Library.OPERATION_COMPLETED:
                System.out.println("The hold has been removed");
                break;
            default:
                System.out.println("An error has occurred");
        }
    }
    /**
     * Method to be called for processing items.
     * Prompts the user for the appropriate values and
     * uses the appropriate Library method for processing items.
     *
     */
    public void processHolds() {
        Member result;
        do {
            String itemID = getToken("Enter item id");
            result = library.processHold(itemID);
            if (result != null) {
                System.out.println(result);
            } else {
                System.out.println("No valid holds left");
            }
            if (!yesOrNo("Process more items?")) {
                break;
            }
        } while (true);
    }
    /**
     * Method to be called for displaying transactions.
     * Prompts the user for the appropriate values and
     * uses the appropriate Library method for displaying transactions.
     *
     */
    public void getTransactions() {
        Iterator result;
        String memberID = getToken("Enter member id");
        Calendar date  = getDate("Please enter the date for which you want records as mm/dd/yy");
        result = library.getTransactions(memberID,date);
        if (result == null) {
            System.out.println("Invalid member.Member ID");
        } else {
            while(result.hasNext()) {
                Transaction transaction = (Transaction) result.next();
                System.out.println(transaction.getType() + "   "   + transaction.getTitle() + "\n");
            }
            System.out.println("\n  There are no more transactions \n" );
        }
    }
    /**
     * Method to be called for saving the Library object.
     * Uses the appropriate Library method for saving.
     *
     */
    private void save() {
        if (library.save()) {
            System.out.println(" The library has been successfully saved in the file LibraryData \n" );
        } else {
            System.out.println(" There has been an error in saving \n" );
        }
    }
    /**
     * Method to be called for retrieving saved data.
     * Uses the appropriate Library method for retrieval.
     *
     */
    private void retrieve() {
        try {
            Library tempLibrary = Library.retrieve();
            if (tempLibrary != null) {
                System.out.println(" The library has been successfully retrieved from the file LibraryData \n" );
                library = tempLibrary;
            } else {
                System.out.println("File doesn't exist; creating new library" );
                library = Library.instance();
            }
        } catch(Exception cnfe) {
            cnfe.printStackTrace();
        }
    }
    /**
     * Method to be called for adding fines
     */
    public void addFine() {
        String memberId = getToken("Enter member id");
        int fine = getNumber("Enter fine");
        int result = library.addFines(memberId, fine);

        if (result == Library.NO_SUCH_MEMBER) {
            System.out.println("Not a valid member ID");
        }
    }
    /**
     * Orchestrates the whole process.
     * Calls the appropriate method for the different functionalties.
     *
     */
    public void process() {
        int command;
        help();
        while ((command = getCommand()) != EXIT) {
            switch (command) {
                case ADD_MEMBER:        addMember();
                    break;
                case ADD_ITEMS:         addItems();
                    break;
                case ISSUE_ITEMS:       issueItems();
                    break;
                case RETURN_ITEMS:      returnItems();
                    break;
                case REMOVE_ITEMS:      removeItems();
                    break;
                case RENEW_ITEMS:       renewItems();
                    break;
                case PLACE_HOLD:        placeHold();
                    break;
                case REMOVE_HOLD:       removeHold();
                    break;
                case PROCESS_HOLD:      processHolds();
                    break;
                case GET_TRANSACTIONS:  getTransactions();
                    break;
                case SAVE:              save();
                    break;
                case RETRIEVE:          retrieve();
                    break;
                case HELP:              help();
                    break;
                case ADD_FINE:         addFine();
                    break;
            }
        }
    }
    /**
     * The method to start the application. Simply calls process().
     * @param args not used
     */
    public static void main(String[] args) {
        UserInterface.instance().process();
    }
}