package ch07;

public class SUT {
    private final Collaborator collaborator;
    private final OtherCollaborator otherCollaborator;

    public SUT(Collaborator collaborator, OtherCollaborator otherCollaborator) {
        this.collaborator = collaborator;
        this.otherCollaborator = otherCollaborator;
    }

    public void someMethod(){};
}
