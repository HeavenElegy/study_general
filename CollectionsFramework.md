# 接口 - `Iterable<T>` -  可迭代
### 描述
实现本接口的类,其对象可以被**迭代**.同时支持`forEach`语法
## 方法
### `Iterator<T> iterator()`
#### 类型
抽象方法
#### 描述
返回一个用于执行迭代的`java.util.Iterator`对象
#### 代码
```java
Iterator<T> iterator();
```
### `default void forEach(Consumer<? super T> action)`
#### 类型
抽象默认方法
#### 描述
遍历所有**元素**并分别传递给`action`方法
#### 代码
```java
default void forEach(Consumer<? super T> action) {
    Objects.requireNonNull(action);
    for (T t : this) {
        action.accept(t);
    }
}
```
### `default Spliterator<T> spliterator()`
#### 类型
抽象默认方法
#### 描述
返回一个`Spliterator<T>`对象
#### 代码
```java
default Spliterator<T> spliterator() {
    return Spliterators.spliteratorUnknownSize(iterator(), 0);
}
```
# 接口 - `Collection<E>`
### 继承
`Iterable`
### 实现
### 描述
规定了所有**集合**需要实现的方法
## 方法
### `int size()`
#### 类型
抽象方法
#### 描述
返回当前集合的元素数量.当超过`Integer.MAX_VALUE`时,返回`Integer.MAX_VALUE`.
#### 代码
```java
int size();
```
### `isEmpty()`
#### 类型
抽象方法
#### 描述
当前集合中不包含任何元素时,返回`true`
#### 代码
```java
boolean isEmpty();
```
### `contains(Object o)`
#### 类型
抽象方法
#### 描述
当前集合中包含一个或以上的指定元素时,返回`true`.否则返回`false`
#### 代码
```java
boolean contains(Object o);
```
### `toArray()`
#### 类型
抽象方法
#### 描述
返回一个包含当前集合中所有元素的数组.如果当前集合是有序集合,则这个数组中的元素顺序应该与此有序集合中的顺序相同.  
同时,返回的数组必须是一个新端的数组.可以让调用者自由的修改返回数组的结构.而不影响本集合.
#### 代码
```java
Object[] toArray();
```
### `toArray(T[] a)`
#### 类型
抽象方法
#### 描述
与`toArray()`相似.返回数据类型使用运行时类型(也就是这里的`T`).  
当参数`a`可以容纳当前集合中的元素时,此方法会将当前集合内的元素放入参数`a`中,并在防止最后一个元素的再后面的一个位置设置为`null`.  
当参数`a`无法容纳当前集合中的元素时,会返回一个新数组
#### 代码
```java
<T> T[] toArray(T[] a);
```
### `add(E e)`
#### 类型
抽象方法
#### 描述
添加元素到当前集合中.当集合被修改时,将会返回`true`.否则返回`false`  
在源码的注释中,提到了对于`null`,**重复元素**,与一些特殊情况下的处理措施.如不再返回`false`而是抛出异常等.这要看具体的实现了.
#### 代码
```java
boolean add(E e);
```
### `remove(Object o)`
#### 类型
抽象方法
#### 描述
删除集合中的指定元素.当集合被更改时,返回`true`
#### 代码
```java
boolean remove(Object o);
```
### `containsAll(Collection<?> c)`
#### 类型
抽象方法
#### 描述
给定集合中的所有元素均在当前集合中存在时,返回`true`
#### 代码
```java
boolean containsAll(Collection<?> c);
```
### `addAll(Collection<? extends E> c)`
#### 类型
抽象方法
#### 描述
添加给定集合中的全部元素到当前集合中  
这里源码注释中提出了一些为确认的情况.  
* 参数`c`在操作中被改变(这里一般是多线程共用一个对象时引起的).
* 参数`c`就是当前集合(OOM~).
#### 代码
```java
boolean addAll(Collection<? extends E> c);
```
### `removeAll(Collection<?> c)`
#### 类型
抽象方法
#### 描述
删除当前集合中的出现在参数`c`集合中的全部元素
#### 代码
```java
boolean removeAll(Collection<?> c);
```
### `removeIf(Predicate<? super E> filter)`
#### 类型
抽象默认方法
#### 描述
根据`Predicate`(断言),删除全部元素  
这里直接使用`iterator()`,进行迭代,断言,删除操作
#### 代码
```java
default boolean removeIf(Predicate<? super E> filter) {
    Objects.requireNonNull(filter);
    boolean removed = false;
    final Iterator<E> each = iterator();
    while (each.hasNext()) {
        if (filter.test(each.next())) {
            each.remove();
            removed = true;
        }
    }
    return removed;
}
```
### `retainAll(Collection<?> c)`
#### 类型
抽象方法
#### 描述
删除掉当前集合中,不包含在参数`c`集合中的全部元素
#### 代码
```java
boolean retainAll(Collection<?> c);
```
### `clear()`
#### 类型
抽象方法
#### 描述
清除集合中的全部元素
#### 代码
```java
void clear();
```
### `equals(Object o)`
#### 类型
抽象方法
#### 描述
以重写的方式,将`java.lang.Object.equals`方法设置为*抽象方法*.约束子类实现`equals`方法
#### 代码
```java
boolean equals(Object o);
```
### `hashCode()`
#### 类型
抽象方法
#### 描述
以重写的方式,将`java.lang.Object.hashCode`方法设置为*抽象方法*.约束子类实现`hashCode`方法
#### 代码
```java
int hashCode();
```




# 接口 - ``
### 继承
### 实现
### 描述
## 方法
### ``
#### 类型
#### 描述
#### 代码
```java
```