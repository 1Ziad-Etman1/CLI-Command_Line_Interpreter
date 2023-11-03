import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.util.stream.Stream;


public class Terminal {
    Parser parser;
    Path CWD;
    ArrayList<String> history;

    public Terminal() {
        this.parser = new Parser();
        this.CWD = Paths.get(System.getProperty("user.dir"));
        this.history = new ArrayList<String>();
    }

    //Implement each command in a method, for example:
    //todo PWD //Done
    public String pwd() {
        history.addLast(parser.commandName);
        return System.getProperty("user.dir");
    }
    //todo ECHO //Done
    public String echo(){
        history.addLast(parser.commandName);
        return parser.args[0];
    }
    //todo CD //Done
    public void cd() {
        Path newPath = Paths.get(parser.args[0]);
        CWD = Paths.get(System.getProperty("user.dir"));
        history.addLast(parser.commandName);
        if (parser.args[0].equals("..") || parser.args[0].equals("../")){
            CWD = CWD.getParent().toAbsolutePath();
            System.setProperty("user.dir", CWD.toString());
        } else if (parser.args[0].equals(".") || parser.args[0].equals("./")){
            System.out.print("");
        } else {
            if(Files.exists(newPath) && Files.isDirectory(newPath)){
                if(!newPath.isAbsolute()){
                    newPath = Paths.get(CWD.toString(),newPath.toString());
                }
                String absPath = newPath.toString();
                System.setProperty("user.dir", absPath);
            } else {
                System.out.println("Err: Invalid working dir!");
            }
        }


    }
    //todo LS //Done
    public void ls(){
        history.addLast(parser.commandName);
        try {
            // Get the current working directory
            Path CWD = Paths.get(System.getProperty("user.dir"));

            // List the files in the current working directory
            try (Stream<Path> files = Files.list(CWD)) {
                files.forEach(path -> System.out.println(path.getFileName()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //todo LS-R //Done
    public void ls_r(){
        history.addLast(parser.commandName);
        try {
            // Get the current working directory
            Path CWD = Paths.get(System.getProperty("user.dir"));

            // List the files in the current working directory
            try (Stream<Path> files = Files.list(CWD).sorted(Comparator.reverseOrder())) {
                files.forEach(path -> System.out.println(path.getFileName()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //todo MKDIR //Done
    public void mkdir(){
        history.addLast(parser.commandName);
        CWD = Paths.get(System.getProperty("user.dir")).toAbsolutePath();
        try {
            Path targetPath = Paths.get(CWD.toString(), parser.args[0]);
            if(!Files.exists(targetPath)){
                Files.createDirectories(targetPath);
            } else {
                System.out.println("Directory with this name already exists!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //todo RMDIR //Done
    public void rmdir(){
        history.addLast(parser.commandName);
        CWD = Paths.get(System.getProperty("user.dir")).toAbsolutePath();
        try {
            Path targetPath = Paths.get(CWD.toString(), parser.args[0]);
            Files.deleteIfExists(targetPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //todo TOUCH //Done
    public void touch(){
        history.addLast(parser.commandName);
        CWD = Paths.get(System.getProperty("user.dir")).toAbsolutePath();
        try {
            Path targetPath = Paths.get(CWD.toString(), parser.args[0]);
            if(!Files.exists(targetPath)){
                Files.createFile(targetPath);
            } else {
                System.out.println("File already exists!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //todo RM //Done
    public void rm(){
        history.addLast(parser.commandName);
        CWD = Paths.get(System.getProperty("user.dir")).toAbsolutePath();
        try {
            Path targetPath = Paths.get(CWD.toString(), parser.args[0]);
            Files.deleteIfExists(targetPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //todo CP //Done
    public void cp(){
        history.addLast(parser.commandName);
        CWD = Paths.get(System.getProperty("user.dir")).toAbsolutePath();
        try {
            Path source = Paths.get(CWD.toString(), parser.args[0]);
            Path destination = Paths.get(parser.args[1]);
            if(Files.exists(source)){
                Files.copy(source, destination);
            } else {
                System.out.println("Err: There is no such file!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    FIXME CP-R
//    public void cp_r(){
//        CWD = Paths.get(System.getProperty("user.dir")).toAbsolutePath();
//        try {
//
//            Path source = Paths.get( parser.args[0]);
//            if(!source.isAbsolute()){
//                source = Paths.get(CWD.toString(), parser.args[0]);
//            }
//            Path destination = Paths.get(parser.args[1]);
//            if(!destination.isAbsolute()){
//                destination = Paths.get(CWD.toString(), parser.args[1]);
//            }
//            if(Files.isDirectory(source) && Files.isDirectory(destination)){
//
//                Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
//            } else {
//                System.out.println("Err: There is no such directory!");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    //todo CAT(1 arg) //Done
    public void cat_1(){
        history.addLast(parser.commandName);
        Path path = Paths.get(parser.args[0]);
        try {

            if(Files.exists(path)){
                List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

                for(String line : lines){
                    System.out.println(line);
                }
            } else {
                System.out.println("File Does Not Exist!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //todo CAT(2 args) //Done
    public void cat_2(){
        history.addLast(parser.commandName);
        Path path1 = Paths.get(parser.args[0]);
        Path path2 = Paths.get(parser.args[1]);
        try {

            if(Files.exists(path1) && Files.exists(path2)){
                List<String> lines1 = Files.readAllLines(path1, StandardCharsets.UTF_8);
                List<String> lines2 = Files.readAllLines(path2, StandardCharsets.UTF_8);
                for(String line : lines1){
                    System.out.println(line);
                }
                for(String line : lines2){
                    System.out.println(line);
                }
            } else {
                System.out.println("Files Does Not Exist!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //todo WC //Done
    public void wc(){
        history.addLast(parser.commandName);
        Path path = Paths.get(parser.args[0]);
        int lines_count = 0 ;
        int words_count = 0 ;
        int char_count  = 0 ;
        try {
            if(Files.exists(path)){
                List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

                for(String line : lines){
                    String[] words = line.split(" ");
                    words_count += words.length;
                    char_count  += line.length();
                }
                lines_count += lines.size();
                System.out.println("lines:" + lines_count + " words" + words_count + " chars" + char_count + " " + path.getFileName().toString());
            } else {
                System.out.println("File Does Not Exist!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //todo HISTORY //Done
    public void history(){
        if (history.size() > 0){
            for (String command : history){
                System.out.println(command);
            }
        } else {
            System.out.println("No commands in the history");
        }
        history.addLast(parser.commandName);
    }
    public void chooseCommandAction() {
        //todo PWD //Done
        if ((parser.commandName).equals("pwd")){
            if (parser.args.length == 0) {
                System.out.println(pwd());
            } else {
                System.out.println("Err: Invalid number of Arguments!");
            }
        }
        //todo ECHO //Done
        else if ((parser.commandName).equals("echo")){
            if (parser.args.length == 1) {
                System.out.println(echo());
            } else {
                System.out.println("Err: Invalid number of Arguments!");
            }
        }
        //todo CD //Done
        else if ((parser.commandName).equals("cd")) {
            if (parser.args.length == 1) {
                cd();
            } else {
                System.out.println("Err: Invalid number of Arguments!");
            }
        }
        //todo LS //Done
        else if ((parser.commandName).equals("ls")) {
            if (parser.args.length == 0) {
                ls();
            } else if (parser.args.length == 1){
                if (parser.args[0].equals("-r")){
                    ls_r();
                }
            } else {
                System.out.println("Err: Invalid number of Arguments!");
            }
        }
        //todo MKDIR //Done
        else if ((parser.commandName).equals("mkdir")) {
            if (parser.args.length == 1) {
                mkdir();
            } else {
                System.out.println("Err: Invalid number of Arguments!");
            }
        }
        //todo RMDIR //Done
        else if ((parser.commandName).equals("rmdir")) {
            if (parser.args.length == 1) {
                rmdir();
            } else {
                System.out.println("Err: Invalid number of Arguments!");
            }
        }
        //todo TOUCH //Done
        else if ((parser.commandName).equals("touch")) {
            if (parser.args.length == 1) {
                touch();
            } else {
                System.out.println("Err: Invalid number of Arguments!");
            }
        }
        //todo RM //Done
        else if ((parser.commandName).equals("rm")) {
            if (parser.args.length == 1) {
                rm();
            } else {
                System.out.println("Err: Invalid number of Arguments!");
            }
        }
        //todo CP //Done
        else if ((parser.commandName).equals("cp")) {
            if (parser.args.length == 2) {
                for (String arg: parser.args){
                    System.out.println(arg);
                }
                cp();
            }
//            else if (parser.args.length == 3 && parser.args[0].equals("-r")) {
//                //FIXME !!!!!!!!!!!!!!!
//                cp_r();
//            }
            else {
                System.out.println("Err: Invalid number of Arguments!");
            }
        }
        //todo CAT //Done
        else if ((parser.commandName).equals("cat")) {
            if (parser.args.length == 1) {
                cat_1();
            } else if (parser.args.length == 2) {
                cat_2();
            } else {
                System.out.println("Err: Invalid number of Arguments!");
            }
        }
        //todo WC //Done
        else if ((parser.commandName).equals("wc")) {
            if (parser.args.length == 1) {
                wc();
            } else {
                System.out.println("Err: Invalid number of Arguments!");
            }
        }
        //todo HISTORY //Done
        else if ((parser.commandName).equals("history")) {
            if (parser.args.length == 0) {
                history();
            } else {
                System.out.println("Err: Invalid number of Arguments!");
            }
        }
        else if ((parser.commandName).equals("exit")) {
            System.out.println("Session Complete!");;
        }
        else {
            System.out.println("Invalid Command");
        }
    }

    public void main() {
        Scanner in = new Scanner(System.in);
        String input ;
        Path path = Paths.get("");
        while (true){
            System.out.print("[" + System.getProperty("user.name") + " " + path.getFileName().toString() + "] ");
            input = in.nextLine();
            if (parser.parse(input)){
                chooseCommandAction();
            }
            if (parser.commandName.equals("exit")){
                break;
            }
        }
    }

}
