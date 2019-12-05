## 先上结论
#### **模运算**比**与**运算慢20%到30%
> 这是通过实验的方式得到的结论.因为没有大大可以进行明确指导,所以我以最终运行的结果为准.欢迎指正.
## 测试代码
```java
@Test
public void test10() {
    int a, b, temp, count = 100000000;
    long start, time;

    Random random = new Random();


    while (true) {
        System.out.println("-----------------------------------------------");
        a = random.nextInt();
        b = random.nextInt();

        // 模运算部分
        start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            temp = a % b;
            preventOptimization(temp);
        }
        long modulo = System.currentTimeMillis() - start;

        // 与运算部分
        start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            temp = a & b;
            preventOptimization(temp);
        }
        long and = System.currentTimeMillis() - start;

        // 计算两种运算的比
        BigDecimal bigDecimal = new BigDecimal(and)
                .divide(new BigDecimal(modulo), 6, ROUND_DOWN);
        System.out.println(String.format("modulo: %sms, and: %sms, scale: %s", modulo, and, bigDecimal));
    }
}

//    private static int preventOptimizationVar = 0; // A

/**
 * 用于阻止jvm的字节码优化技术生效,优化掉for循环中的代码<br>
 *
 * @param num
 */
private static void preventOptimization(int num) {
//        preventOptimizationVar += num; // A
}
```
## 注意事项
启动时,需要添加**JVM参数**`-Djava.compiler=NONE`阻止虚拟机的**JIT**优化  
## 一些迷之调用的解释
1. 方法`preventOptimization`的作用本来是为了防止**for**循环内的运算单元被*优化*.从结果看,无法抵御**JIT**优化,实际上只阻止了**字节码优化**.
2. 同样是方法`preventOptimization`.内部添加了一个对成员变量`preventOptimizationVar`的操作,本意同样是是为了阻止**JIT**优化,但效果出现偏差.  
3. 最终,只能使用**JVM参数**`-Djava.compiler=NONE`阻止**JIT**优化,同时调用空方法`preventOptimization`阻止**字节码优化**
4. 为了整理其中的关联,进行如下整理  

## 针对`preventOptimization`方法,`preventOptimizationVar`操作与`-Djava.compiler=NONE`参数之间的交叉测试
|---------------------------|-Djava.compiler=NONE|不使用-Djava.compiler=NONE|
|:-------------------------:|:------------------:|:-----------------------:|
|preventOptimization(空方法) |**目标结果**|只能执行第一次`while`,之后**除数为0**抛出异常.应该是`for`循环被优化掉了|
|preventOptimization        |执行变得**极慢**且比值随机位于1两侧.可能是因为操作成员变量的时间已经把**模**与**与**运算的时间稀释了|两种运算的`for`的平均用时只有个位数的毫秒数.一般为4ms或5ms.可以断定发生了**JIT优化**.但是这又与完全跳过`for`循环的行为不同.中间产生了数毫秒的操作时间.|
|不调用preventOptimization   |**目标结果**|只能执行第一次`while`,之后除数为0抛出异常.应该是`for`循环被优化掉了|
> 注: 上面的测试结果基于*intel*平台上的*ubuntu18.4*操作系统.后面我会补上*AMD*平台上的*Windows*系统的执行表格.它们之间会有轻微的不同
## 后记
1. 针对`-Djava.compiler=NONE`,明确的禁用了**JIT优化**
2. 针对**字节码优化**需要回去翻书确认实际作用与效果
3. 这个代码在不同的物理机上出现了一些差异.家中的电脑是*AMD的锐龙CPU*,其结果恒定为**0.73**左右.而在公司的*Intel CPU*上运行,结果稳定在**0.87**左右.
4. 目前我无法这种计算方式是否是正确的,虽然得出了*模运算*比*与*运算慢的结论.身边没有大大,无法对我的这种做法进行评价.也就无法确定结果的正确性.也就是说,我有可能用了错误的方式,得到这正确的答案.这种可能的本末倒置实在让人头痛.

## 后记的后记
对于目前的这种情况.要想完全弄清谁快谁慢,其实可以用一个最简单也最直观的方法进行确认.那就是去翻**JDK**源码.先在字节码里找到负责*与*运算与*模*运算的**指令**,然后去找这个**指令**的**JDK**实现

## 更新
#### 看了下编译后的字节码,代码中使用了如下的指令进行操作
1. *模*运算使用指令`irem`.将*操作数栈*的两个值进行**余运算**(注意,这里用的是**余运算**.它不同于**模运算**)
2. *与*运算使用指令`iand`.将*操作数栈*的两个值进行**与运算**

#### 下面需要找到**JDK**是怎么执行字节码文件中的每个指令的.
不要和我说什么几个阶段,每个阶段都干什么.这可能是个大坑.所以我得翻书(鸽了)啊