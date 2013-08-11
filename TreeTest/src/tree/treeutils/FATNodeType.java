package tree.treeutils;

/**
 * User: luc  | Date: 8/7/13  |  Time: 9:46 PM
 */
public class FATNodeType {
    private String name;
    private String path;

    public FATNodeType(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
