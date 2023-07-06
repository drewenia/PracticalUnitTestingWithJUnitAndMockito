package ch07;

public class MySutRefactored {
    public boolean myMethod() {
        MyCollaborator myCollaborator = createCollaborator();
        return true;
    }

    protected MyCollaborator createCollaborator() {
        return new MyCollaborator();
    }
}
