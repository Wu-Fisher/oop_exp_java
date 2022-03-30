package junit.demo;
public class Calculator {
    public int add(int x, int y) { //加法
        return x + y;
    }
    public int sub(int x, int y) { //减法
        return x - y;
    }
    public int mul(int x, int y) { //乘法
        return x * y;
    }
    public int div(int x, int y) { //除法
        return x / y;
    }
    public int div2(int x, int y) { //除法 做了异常判断
        try {
            int z = x / y;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return x / y;
    }
    public void unCompleted(int x, int y) { //未完成的模块:例如平方、开方等等//TODO
    }
}
