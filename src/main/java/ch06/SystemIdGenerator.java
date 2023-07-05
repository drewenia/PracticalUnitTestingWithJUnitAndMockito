package ch06;

public class SystemIdGenerator implements IdGenerator{
    @Override
    public Long nextId() {
        return System.currentTimeMillis();
    }
}
