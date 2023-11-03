class Parser {
    String commandName;
    String[] args;

    public Parser() {
        this.commandName = "";
        this.args = new String[0];
    }

    public boolean parse(String input){
        String[] inputArgs = input.split(" ");
        if (input != ""){
            commandName = inputArgs[0];
            args = new String[inputArgs.length - 1];
            for (int i = 1; i < inputArgs.length; i++) {
                args[i-1] = inputArgs[i];
            }
            return true;
        }
        else {
            return false;
        }
    }
    public String getCommandName(){
        return commandName;
    }
    public String[] getArgs(){
        return args;
    }
}
