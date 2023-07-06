package ch07;

public class MyPartialSut {
    public boolean myMethod(){
        MyCollaborator myCollaborator = createCollaborator();
        return true;
    }

    MyCollaborator createCollaborator() {
        return new MyCollaborator();
    }
}
