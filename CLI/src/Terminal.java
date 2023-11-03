import java.nio.file.CopyOption;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.stream.Stream;


public class Terminal {
    Parser parser;
    Path CWD;

    public Terminal() {
        this.parser = new Parser();
        this.CWD = Paths.get(System.getProperty("user.dir"));
    }

    //Implement each command in a method, for example:
    
    public String pwd() {
        return System.getProperty("user.dir");
    }
    public String echo(){
        return parser.args[0];
    }

    public void cd() {
        Path newPath = Paths.get(parser.args[0]);
        Path CWD = Paths.get(System.getProperty("user.dir"));

        if (parser.args[0].equals("..") || parser.args[0].equals("../")){
            CWD = CWD.getParent().toAbsolutePath();
//            System.out.println(CWD.toString());
            System.setProperty("user.dir", CWD.toString());
        } else if (parser.args[0].equals(".") || parser.args[0].equals("./")){
            System.out.print("");
        } else {
            if(Files.exists(newPath) && Files.isDirectory(newPath)){
                newPath = newPath.toAbsolutePath();
                String absPath = newPath.toString();
                System.setProperty("user.dir", absPath);
            } else {
                System.out.println("Err: Invalid working dir!");
            }
        }


    }

    public void ls(){
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

    public void ls_r(){
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

    public void mkdir(){
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

    public void rmdir(){
        CWD = Paths.get(System.getProperty("user.dir")).toAbsolutePath();
        try {
            Path targetPath = Paths.get(CWD.toString(), parser.args[0]);
            Files.deleteIfExists(targetPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void touch(){
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

    public void rm(){
        CWD = Paths.get(System.getProperty("user.dir")).toAbsolutePath();
        try {
            Path targetPath = Paths.get(CWD.toString(), parser.args[0]);
            Files.deleteIfExists(targetPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cp(){
//        CWD = Paths.get(System.getProperty("user.dir")).toAbsolutePath();
        try {
            Path source = Paths.get(CWD.toString(), parser.args[0]);
            Path destination = Paths.get(CWD.toString(), parser.args[1]);
            if(Files.exists(source)){
                Files.copy(source, destination);
            } else {
                System.out.println("Err: There is no such file!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //FIXME
    public void cp_r(){
//        CWD = Paths.get(System.getProperty("user.dir")).toAbsolutePath();
//        try {
//            Path source = Paths.get(CWD.toString(), parser.args[0]);
//            Path destination = Paths.get(CWD.toString(), parser.args[1]);
//            if(Files.exists(source)){
//                FileUtils.copy(source, destination,);
//            } else {
//                System.out.println("Err: There is no such file!");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public void cat_1(){
//        List<String> list;
//
//        try {
//            Path path = Paths.get(parser.args[0]);
//            if(Files.exists(path)){
//                list = new List<String>(Files.readString(path));
//            } else {
//                System.out.println("File Does Not Exist!");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public void cat_2(){

    }

    public void chooseCommandAction() {
        //todo PWD //Done
        if ((parser.commandName).equals("pwd")){
            if (parser.args.length == 0) {
                System.out.println(pwd());
            } else {
                System.out.println("Err: Invvalid number of Arguments!");
            }
        }
        //todo ECHO //Done
        else if ((parser.commandName).equals("echo")){
            if (parser.args.length == 1) {
                System.out.println(echo());
            } else {
                System.out.println("Err: Invvalid number of Arguments!");
            }
        }
        //todo CD //Done
        else if ((parser.commandName).equals("cd")) {
            if (parser.args.length == 1) {
                cd();
            } else {
                System.out.println("Err: Invvalid number of Arguments!");
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
                System.out.println("Err: Invvalid number of Arguments!");
            }
        }
        //todo MKDIR //Done
        else if ((parser.commandName).equals("mkdir")) {
            if (parser.args.length == 1) {
                mkdir();
            } else {
                System.out.println("Err: Invvalid number of Arguments!");
            }
        }
        //todo RMDIR //Done
        else if ((parser.commandName).equals("rmdir")) {
            if (parser.args.length == 1) {
                rmdir();
            } else {
                System.out.println("Err: Invvalid number of Arguments!");
            }
        }
        //todo TOUCH //Done
        else if ((parser.commandName).equals("touch")) {
            if (parser.args.length == 1) {
                touch();
            } else {
                System.out.println("Err: Invvalid number of Arguments!");
            }
        }
        //todo RM //Done
        else if ((parser.commandName).equals("rm")) {
            if (parser.args.length == 1) {
                rm();
            } else {
                System.out.println("Err: Invvalid number of Arguments!");
            }
        }
        //TODO: CP
        else if ((parser.commandName).equals("cp")) {
            if (parser.args.length == 2) {
                cp();
            } else if (parser.args.length == 3 && parser.args[0].equals("-r")) {
                //FIXME
                cp_r();
            } else {
                System.out.println("Err: Invvalid number of Arguments!");
            }
        }
        //TODO CAT
        else if ((parser.commandName).equals("cat")) {
            if (parser.args.length == 1) {
                cat_1();
            } else if (parser.args.length == 2) {
                cat_2();
            } else {
                System.out.println("Err: Invvalid number of Arguments!");
            }
        }
        else if ((parser.commandName).equals("exit")) {
            System.out.println("Session Complete!");;
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
