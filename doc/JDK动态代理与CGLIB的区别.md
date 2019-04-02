## 1.动态代理/静态代理
区别
名称 | 备注
---  |---
静态代理     | 简单，代理模式，是动态代理的理论基础。常见使用在代理模式
jdk动态代理  | 需要有顶层接口才能使用，但是在只有顶层接口的时候也可以使用，常见是mybatis的mapper文件是代理。使用反射完成。使用了动态生成字节码技术。
cglib动态代理| 可以直接代理类，使用字节码技术，不能对 final类进行继承。使用了动态生成字节码技术。

一个对象（客户端）不能或者不想直接引用另一个对象（目标对象），这时可以应用代理模式在这两者之间构建一个桥梁--代理对象。

按照代理对象的创建时期不同，可以分为两种：
- 静态代理：事先写好代理对象类，在程序发布前就已经存在了；
- 动态代理：应用程序发布后，通过动态创建代理对象。

<font face="微软雅黑" color=#DC143C>静态代理其实就是一个典型的代理模式实现，在代理类中包装一个被代理对象，然后影响被代理对象的行为</font>。

```
/**
* 最顶层接口 歌手
*/
interface Singer {
    void sing();
}

/**
* 真实实现，一个歌星
*/
class Star implements Singer {
    @Override
    public void sing() {
        System.out.println("Star Singing~~~");
    }
}

/**
* 代理实现，代理了歌星，唱歌的时候 会先在歌手唱歌之前收钱，然后再唱歌
*/
class Agent implements Singer {
    Star s;
    public Agent(Star s) {
        super();
        this.s = s;
    }
    @Override
    public void sing() {
        System.out.println("在歌手唱歌之前收钱....");
        s.sing();
    }
}

public class StaticProxy {
    public static void main(String[] args) {
        Singer singer = new Agent(new Star());
        singer.sing();
    }
}

```

其中动态代理又可分为：**JDK动态代理**和**CGLIB代理**。

**JDK动态代理**

- 只能代理有实现的接口类
- 自己观察可以从Proxy.newProxyInstance( ClassLoader paramClassLoader,  Class<?>[] paramArrayOfClass,  InvocationHandler paramInvocationHandler)进行观察
**，简单来看就是先生成新的class文件，然后加载到jvm中，然后使用反射，先用class取得他的构造方法，然后使用构造方法反射得到他的一个实例**。

```
// 必须继承 InvocationHandler 并实现invoke方法
public class JDKProxy implements InvocationHandler {
    private Object target;
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("------插入前置通知代码-------------");
        // 执行相应的目标方法
        Object rs = method.invoke(target,args);
        System.out.println("------插入后置处理代码-------------");
        return rs;

    }

    public JDKProxy(Object target) {
        this.target = target;
    }


// 通过实现Proxy.newProxyInstance()返回被代理对象
    public static void main(String[] args)
            throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        MyBean  iHello2 = (MyBean) Proxy.newProxyInstance(
                MyBean.class.getClassLoader(), // 加载接口的类加载器
                new Class[]{MyBean.class}, // 一组接口
                new JDKProxy(new MyBeanImpl())); // 定义的InvocationHandler
        iHello2.say("222222222");// 方法调用
    }
```


**CGLIB代理**
- 不能代理final修饰的类
- 通过生成代理类的子类来实现
- 必须继承MethodInterceptor并实现intercept方法
- 

```
// 继承MethodInterceptor并实现intercept方法
public class BookFacadeCglib implements MethodInterceptor {
    private Object target;

    // 获取代理对象
    public Object getInstance(Object target) {
        this.target = target;
        // 创建代理类的子类
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        // 回调方法
        enhancer.setCallback(this);
        // 创建代理对象
        return enhancer.create();
    }


    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("事物开始");
        methodProxy.invokeSuper(o,objects);
        System.out.println("事物开始");
        return null;
    }

    public static void main(String[] args) {
        BookFacadeCglib cglib=new BookFacadeCglib();
        BookFacadeImpl1 bookCglib=(BookFacadeImpl1)cglib.getInstance(new BookFacadeImpl1());
        bookCglib.addBook();

    }
}
```


