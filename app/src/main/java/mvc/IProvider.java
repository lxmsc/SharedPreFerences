package mvc;

public interface IProvider<T extends IData> {
    T getData();
}
