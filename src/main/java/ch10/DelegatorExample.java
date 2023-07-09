package ch10;


public class DelegatorExample {
    private Collaborator collaborator;

    public void delegate(){
        collaborator.doSomething();
    }
}
