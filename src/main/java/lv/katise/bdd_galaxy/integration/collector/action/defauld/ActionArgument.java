package lv.katise.bdd_galaxy.integration.collector.action.defauld;

public class ActionArgument {

    private ArgumentType argumentType;
    private String name;

    public ActionArgument(ArgumentType argumentType, String name) {
        this.argumentType = argumentType;
        this.name = name;
    }

    public ArgumentType getArgumentType() {
        return argumentType;
    }

    public void setArgumentType(ArgumentType argumentType) {
        this.argumentType = argumentType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
