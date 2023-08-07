package lv.katise.bdd_galaxy.integration.collector.action.defauld;

public class ActionArgument {

    private ArgumentType type;
    private String name;

    public ActionArgument(ArgumentType type, String name) {
        this.type = type;
        this.name = name;
    }

    public ArgumentType getType() {
        return type;
    }

    public void setType(ArgumentType argumentType) {
        this.type = argumentType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
