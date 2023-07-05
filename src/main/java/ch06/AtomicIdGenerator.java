package ch06;

public class AtomicIdGenerator implements IdGenerator{
    private static Long nextId = System.currentTimeMillis();
    @Override
    public Long nextId() {
        return nextId++;
    }
}
