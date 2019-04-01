

String有两种赋值方式，第一种是通过“字面量”赋值。比如下面这行：

```
String str = "Hello";
```



第二种是通过new关键字创建新对象。比如下面这样：

```
String str = new String("Hello");
```
<p>这两种方式到底有什么不同。程序执行的时候，内存里到底有几个实例？<b>“实例”</b>存在了内存的哪里？<b>”字面量“</b>又存在了哪里？<b>”变量“</b>又存在了哪里？概念很容易搞混。下面我们一个一个来讲。讲之前，先回顾一下内存。</p>
等主线程开始创建str变量的时候，虚拟机就会到字符串常量池里找，看有没有能equals("Hello")的String。如果找到了，就在栈区当前栈帧的局部变量表里创建str变量，然后把字符串常量池里对Hello对象的引用复制给str变量。找不到的话，才会在heap堆重新创建一个对象，然后把引用驻留到字符串常量区。然后再把引用复制栈帧的局部变量表。

![image](http://pic2.zhimg.com/80/20568a6ad0ef2860746533595e400716_hd.jpg)
如果我们当时定义了很多个值为"Hello"的String，比如像下面代码，有三个变量str1,str2,str3，也不会在堆上增加String实例。局部变量表里三个变量统一指向同一个堆内存地址。



```
package com.ciao.shen.java.string;

class Test{
    public void f(String s){...};

    public static void main(String[] args){
        String str1 = "Hello";
        String str2 = "Hello";
        String str3 = "Hello";
        ...
    }
}
```
![image](http://pic4.zhimg.com/80/8e743518809bd37723a4b0e8bf35f332_hd.jpg)

上图中str1,str2,str3之间可以用==来连接。
但如果是用new关键字来创建字符串，情况就不一样了，

```
public static void main(String[] args){
        String str1 = "Hello";
        String str2 = "Hello";
        String str3 = new String("Hello");
        ...
    }
```
这时候，str1和str2还是和之前一样。但str3因为new关键字会在Heap堆申请一块全新的内存，来创建新对象。虽然字面还是"Hello"，但是完全不同的对象，有不同的内存地址。

![image](http://pic4.zhimg.com/80/fe6b27f35b5491eb562138eda573c238_hd.jpg)


----

简单描述一下：


String str1 = “ABC”;

可能创建一个或者不创建对象。

如果”ABC”这个字符串在java String池里不存在，会在java String池里创建一个创建一个String对象(“ABC”)，然后str1指向这个内存地址，无论以后用这种方式创建多少个值为”ABC”的字符串对象，始终只有一个内存地址被分配，之后的都是String的拷贝，Java中称为“字符串驻留”，所有的字符串常量都会在编译之后自动地驻留。

常量池-》创建字符串对象 ABC -》str1指向这个内存地址


String str2 = new String(“ABC”);

至少创建一个对象，也可能两个。因为用到new关键字，肯定会在heap中创建一个str2的String对象，它的value是“ABC”。同时如果这个字符串再java String池里不存在，会在java池里创建这个String对象“ABC”。


heap创建对象str2 - 》 寻找常量池中是否有ABC-》没有就创建-》str2指向内存地址




