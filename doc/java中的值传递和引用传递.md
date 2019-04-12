 java中的值传递和引用传递
---
这个概念在最早开始学习java基础的时候就被反复提及过，只是当时记得了语法却记不得怎么实际运用，有时候会的了运用却解释不出原理。每次被问到的时候都去随机看上一眼，记几分钟后又忘记了。

可巧今天面试的时候又被问到了（感谢面试官的温故知新），凭着记忆，确定一下java中并没有真正意义上的引用传递，完全都是值传递。因为之前自己特意写了一下相关的demo来做过测试，很果断的说了一下自己的见解。但是似乎面试官不太认可这种说法。本着对技术的尊重和严谨，特意收集资料来验证这个问题。

我们再聊聊这个问题，首先我们必须认识到这个问题一般是相对函数而言的，也就是java中的方法参数，那么我们先来回顾一下Java的**数据类型**

**所谓数据类型**，是编程语言中对内存的一种抽象表达方式，我们知道程序是由代码文件和静态资源组成，在程序被运行前，这些代码存在在硬盘里，程序开始运行，这些代码会被转成计算机能识别的内容放到内存中被执行。

### **Java的数据类型有哪些？**

####  1.基本类型 

编程语言中内置的最小粒度的数据类型。

```
    4种整数类型：byte、short、int、long
    2种浮点数类型：float、double
    1种字符类型：char
    1种布尔类型：boolean
```

##### 1.1基本数据类型的存储：
- A. 基本数据类型的局部变量

    定义基本数据类型的局部变量以及数据都是直接存储在内存中的栈上，也就是前面说到的“虚拟机栈”，数据本身的值就是存储在栈空间里面。 
    
    例如：

```
    int age=50;
    int weight=50;
    int grade=6;
```

在JVM中开辟的结构为：

![image](https://github.com/tianhuan168/Doc/raw/master/img/v1.png)
 

当我们写“int age=50；”，其实是分为两步的：

```
 int age;//定义变量
 age=50;//赋值
```
首先JVM创建一个名为age的变量，存于局部变量表中，然后去栈中查找是否存在有字面量值为50的内容，如果有就直接把age指向这个地址，如果没有，JVM会在栈中开辟一块空间来存储“50”这个内容，并且把age指向这个地址。因此我们可以知道：我们声明并初始化基本数据类型的局部变量时，变量名以及字面量值都是存储在栈中，而且是真实的内容。

基本数据类型的数据本身是不会改变的，当局部变量重新赋值时，并不是在内存中改变字面量内容，而是重新在栈中寻找已存在的相同的数据，若栈中不存在，则重新开辟内存存新数据，并且把要重新赋值的局部变量的引用指向新数据所在地址。


- B. 基本数据类型的成员变量
  
成员变量：顾名思义，就是在类体中定义的变量。 


```
public class Person {
	private int age;
	private String name;
	private int grade;

	static void run(){
		System.out.println("run------");
	}

	public static void main(String[] args) {
		Person per = new Person();
	}
}
```
在jvm中具体开辟的内存图如下

![image](https://github.com/tianhuan168/Doc/raw/master/img/v2.png)

同样是局部变量的age、name、grade却被存储到了堆中为per对象开辟的一块空间中。因此可知：基本数据类型的成员变量名和值都存储于堆中，其生命周期和对象的是一致的。


- C. 基本数据类型的静态变量

jvm中的方法区用来存储一些共享数据，因此基本数据类型的静态变量名以及值存储于方法区的运行时常量池中，静态变量随类加载而加载，随类消失而消失


#### 2.引用数据类型 

引用也叫句柄，引用类型，是编程语言中定义的在句柄中存放着实际内容所在地址的地址值的一种数据形式。
 

```
    1.类
    2.接口
    3.数组
```
##### 2.1 引用数据类型的存储:
上面提到：堆是用来存储对象本身和数组，而引用（句柄）存放的是实际内容的地址值，因此通过上面的程序运行图，也可以看出，当我们定义一个对象时

```
Person per=new Person();
```
实际上是被拆分了两部分

```
Person per;//定义变量
per=new Person();//赋值
```
在执行Person per;时，JVM先在虚拟机栈中的变量表中开辟一块内存存放per变量，在执行per=new Person()时，JVM会创建一个Person类的实例对象并在堆中开辟一块内存存储这个实例，同时把实例的地址值赋值给per变量。因此可见：对于引用数据类型的对象/数组，变量名存在栈中，变量值存储的是对象的地址，并不是对象的实际内容。

OK 言归正传，回归到值传递和引用传递上来，代码不会欺骗人，我们就从demo说起。



```
 
public class Person {
 

	// void -int
	public void testInt(int x){
		x = 5;
	}
	// return -int
	public int testIntReturn(int x){
		x = 5;
		return x;
	}
	// void - string
	public void testString(String str){
		str = "change";
	}



	public static void main(String[] args) {
		Person per = new Person();
		int x = 1;
		per.testInt(x);
		System.out.println("x====" + x);
		int y = per.testIntReturn(x);
		System.out.println("y====" + y);

		String str = "string";
		per.testString(str);
		System.out.println("str====" + str);
		int age = 10;
		Obj obj = new Obj(10);
		obj.setAge(11);
		System.out.println(obj.getAge());
		obj.updateObj(obj);
		System.out.println(obj.getAge());
		obj = obj.updateObjReturn(obj);
		System.out.println(obj);
		Obj o = obj.updateObjReturn(obj);
		System.out.println(o);
	}
}
class Obj{
	private int age;

	public Obj(int age) {
		this.age = age;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setAge2(Obj obj){
		obj.setAge(22);
	}

	public void updateObj(Obj obj){
		obj.setAge(22);
	}

	public Obj updateObjReturn(Obj obj){
		obj.setAge(22);
		return obj;
	}
}


#####输出结果如下#########

x====1
y====5
str====string
11
22
dubbo.api.Obj@4554617c
dubbo.api.Obj@4554617c

Process finished with exit code 0
```

从打印结果可以看出。java中是没有引用传递的，即便是对象传递，也是对象中的属性值传递。